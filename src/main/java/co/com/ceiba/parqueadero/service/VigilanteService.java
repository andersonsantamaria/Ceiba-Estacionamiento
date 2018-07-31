package co.com.ceiba.parqueadero.service;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dominio.Carro;
import co.com.ceiba.parqueadero.dominio.Moto;
import co.com.ceiba.parqueadero.dominio.Parqueadero;
import co.com.ceiba.parqueadero.dominio.Precio;
import co.com.ceiba.parqueadero.dominio.RegistroVehiculo;
import co.com.ceiba.parqueadero.dominio.TipoTiempo;
import co.com.ceiba.parqueadero.dominio.TipoVehiculo;
import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioVigilante;
import co.com.ceiba.parqueadero.service.excepcion.VigilanteServiceException;
import co.com.ceiba.parqueadero.util.RestResponse;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TcrmResponse;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.test.TCRMTestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VigilanteService implements RepositorioVigilante {
	protected static final int DIA_EN_MILISEGUNDOS = 86400000;
	protected static final int HORA_EN_MILISEGUNDOS = 3600000;
	protected static final String VALUE_QUERY_FORMAT = "#0.00";
	protected static final String NO_HAY_VEHICULO_O_ES_NULL = "No hay vehiculos en el parqueadero o el vehiculo que busca es null";
	protected static final String ERROR_COMUNICACION_CON_WS_TRM = "Hubo un error de comunicacion con el web service de la TRM";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(VigilanteService.class);

	private Parqueadero parqueadero;

	@Autowired
	private VehiculoService vehiculoService;
	@Autowired
	private RegistroVehiculoService registroVehiculoService;
	@Autowired
	private PrecioService precioService;

	public VigilanteService() {
		parqueadero = new Parqueadero();
	}

	protected boolean hayDisponibilidadParaVehiculo(Vehiculo vehiculo) {
		if (vehiculo instanceof Carro) {
			this.inicializarCantidadDeVehiculosPorTipo(TipoVehiculo.CARRO.getTipo());
			return parqueadero.hayDisponibilidadParaCarro();
		} else if (vehiculo instanceof Moto) {
			this.inicializarCantidadDeVehiculosPorTipo(TipoVehiculo.MOTO.getTipo());
			return parqueadero.hayDisponibilidadParaMoto();
		} else {
			return false;
		}
	}

	protected void inicializarCantidadDeVehiculosPorTipo(int tipoVehiculo) {
		ArrayList<Vehiculo> vehiculosActivos = (ArrayList<Vehiculo>) this.obtenerVehiculosQueEstanEnElParqueadero();
		ArrayList<Moto> motos = null;
		ArrayList<Carro> carros = null;

		if (tipoVehiculo == TipoVehiculo.CARRO.getTipo()) {
			carros = new ArrayList<>();
		} else if (tipoVehiculo == TipoVehiculo.MOTO.getTipo()) {
			motos = new ArrayList<>();
		}

		try {
			for (Vehiculo vehiculoActivo : vehiculosActivos) {
				if (carros != null) {
					carros.add((Carro) vehiculoActivo);
				} else if (motos != null) {
					motos.add((Moto) vehiculoActivo);
				}
			}
		} catch (NullPointerException e) {
			LOGGER.info(e.getMessage());
			throw new VigilanteServiceException(NO_HAY_VEHICULO_O_ES_NULL);
		}

		if (tipoVehiculo == TipoVehiculo.CARRO.getTipo()) {
			this.parqueadero.setCarros(carros);
		} else if (tipoVehiculo == TipoVehiculo.MOTO.getTipo()) {
			this.parqueadero.setMotos(motos);
		}
	}

	protected boolean laPlacaIniciaPorA(String placa) {
		return placa.toUpperCase().startsWith("A");
	}

	protected boolean puedeIngresarPorPlaca(String placa) {
		if (laPlacaIniciaPorA(placa)) {
			return esDiaHabil();
		} else {
			return true;
		}
	}

	protected boolean esDiaHabil() {
		LocalDate fechaActual = LocalDate.now();
		int dayOfWeek = fechaActual.getDayOfWeek().getValue();
		return (dayOfWeek == 1 || dayOfWeek == 7);
	}

	protected int obtenerValorAPagar(Vehiculo vehiculo, long fechaEntrada, long fechaSalida) {

		Precio precioPorHora = this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.HORA.getTipo());
		Precio precioPorDia = this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.DIA.getTipo());

		int cantidadHoras = 1;
		cantidadHoras += calcularTiempoEnElParqueadero(fechaEntrada, fechaSalida, HORA_EN_MILISEGUNDOS);
		int cantidadDias = calcularTiempoEnElParqueadero(fechaEntrada, fechaSalida, DIA_EN_MILISEGUNDOS);

		cantidadHoras -= (cantidadDias * 24);

		if (cantidadHoras > 8) {
			cantidadHoras = 0;
			cantidadDias++;
		}

		int valorPorHoras = (int) (cantidadHoras * precioPorHora.getValor());
		int valorPorDias = (int) (cantidadDias * precioPorDia.getValor());
		int costo = valorPorHoras + valorPorDias;

		if (vehiculo instanceof Moto) {
			costo += costoExtraPorCilindraje(((Moto) vehiculo).getCilindraje());
		}

		return costo;
	}

	protected int costoExtraPorCilindraje(int cilindraje) {
		return (cilindraje > Parqueadero.MAXIMO_CILINDRJE_PERMITIDO_SIN_COSTO) ? Parqueadero.COSTO_POR_CILINDRAJE : 0;
	}

	protected int calcularTiempoEnElParqueadero(long fechaEntrada, long fechaSalida, int tiempo) {
		long resta = fechaSalida - fechaEntrada;
		return (int) resta / tiempo;
	}

	protected void reportarIngreso(Vehiculo vehiculo) {
		Date fechaEntrada = new Date();
		RegistroVehiculo registroDeEntrada = new RegistroVehiculo(fechaEntrada, null, vehiculo);
		this.vehiculoService.save(vehiculo);
		this.registroVehiculoService.save(registroDeEntrada);
	}

	protected RestResponse reportarSalida(Date fechaSalida, Vehiculo vehiculo) {
		RegistroVehiculo registroDeSalida = this.registroVehiculoService.findByVehiculo(vehiculo.getPlaca());
		if (registroDeSalida != null) {
			int costo = obtenerValorAPagar(vehiculo, registroDeSalida.getFechaEntrada().getTime(),
					fechaSalida.getTime());
			registroDeSalida.setVehiculo(vehiculo);
			registroDeSalida.setFechaSalida(fechaSalida);
			registroDeSalida.setValor(costo);
			this.registroVehiculoService.save(registroDeSalida);
			return new RestResponse(HttpStatus.OK.value(),
					"El valor a pagar por la estadia en el parqueadero para el vehiculo con placa "
							+ vehiculo.getPlaca() + " es : $" + costo + " fecha de ingreso : "
							+ registroDeSalida.getFechaEntrada());
		} else
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"El vehiculo no esta activo, o no ha ingresado.");
	}

	@Override
	public RestResponse permitirIngreso(JSONObject vehiculoJson) {
		try {
			Vehiculo vehiculo;
			vehiculo = this.createVehiculoFromJson(vehiculoJson);

			if (!comprobarSiEsta(vehiculo)) {
				if (hayDisponibilidadParaVehiculo(vehiculo)) {
					if (puedeIngresarPorPlaca(vehiculo.getPlaca())) {
						reportarIngreso(vehiculo);
						return new RestResponse(HttpStatus.OK.value(),
								"Se ha registrado el ingreso del vehiculo con placa = " + vehiculo.getPlaca());
					} else {
						return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
								"No puede ingresar porque no esta en un dia habil.");
					}
				} else {
					return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "No hay disponibilidad.");
				}
			} else {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
						"Este vehiculo se encuentra actualmente en el parquedero.");
			}
		} catch (NumberFormatException | NullPointerException e) {
			LOGGER.info(e.getMessage());
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"Ocurrio un error con el ingreso de este vehiculo." + e);
		}

	}

	@Override
	public RestResponse permitirSalida(JSONObject vehiculoJson) {
		Vehiculo vehiculo;
		try {
			vehiculo = this.createVehiculoFromJson(vehiculoJson);
			Date fechaSalida = new Date();
			return reportarSalida(fechaSalida, vehiculo);
		} catch (NumberFormatException | NullPointerException e) {
			LOGGER.info(e.getMessage());
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"Ocurrio un error con la salida de este vehiculo " + e);
		}
	}

	@Override
	public List<Vehiculo> obtenerVehiculosQueEstanEnElParqueadero() {
		return this.registroVehiculoService.obtenerVehiculosActivos();
	}

	protected Vehiculo createVehiculoFromJson(JSONObject vehiculoJson) {
		Vehiculo vehiculo = null;

		if (Integer.parseInt(vehiculoJson.get("tipo").toString()) == TipoVehiculo.MOTO.getTipo()) {
			vehiculo = new Moto(vehiculoJson.get("placa").toString(),
					Integer.parseInt(vehiculoJson.get("cilindraje").toString()));
		} else {
			vehiculo = new Carro(vehiculoJson.get("placa").toString());
		}

		return vehiculo;
	}

	protected boolean comprobarSiEsta(Vehiculo vehiculo) {
		try {
			ArrayList<Vehiculo> vehiculosActivos = (ArrayList<Vehiculo>) this.obtenerVehiculosQueEstanEnElParqueadero();
			for (Vehiculo vehiculoActivo : vehiculosActivos) {
				if (vehiculoActivo.getPlaca().equals(vehiculo.getPlaca())) {
					return true;
				}
			}
		} catch (NullPointerException e) {
			LOGGER.info(e.getMessage());
			throw new VigilanteServiceException(NO_HAY_VEHICULO_O_ES_NULL);
		}
		return false;
	}

	public RestResponse obtenerTRM() {
		DecimalFormat decimalFormat = new DecimalFormat(VALUE_QUERY_FORMAT);
		String response = "";

		try {
			TcrmResponse tcrmResponse = TCRMTestClient.devolverTRMDelDia();
			response = "TRM del dia: $" + decimalFormat.format(tcrmResponse.getValue());

		} catch (RemoteException | ParseException e) {
			LOGGER.info(e.getMessage());
			throw new VigilanteServiceException(ERROR_COMUNICACION_CON_WS_TRM);
		}
		return new RestResponse(HttpStatus.OK.value(), response);
	}

	public Parqueadero getParqueadero() {
		return parqueadero;
	}
}
