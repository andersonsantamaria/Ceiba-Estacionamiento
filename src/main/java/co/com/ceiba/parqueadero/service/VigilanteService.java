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
import co.com.ceiba.parqueadero.util.RestResponse;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TcrmResponse;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.test.TCRMTestClient;

@Service
public class VigilanteService implements RepositorioVigilante {
	private static final int DIA_EN_MILISEGUNDOS = 86400000;
	private static final int HORA_EN_MILISEGUNDOS = 3600000;
	private static final String _VALUE_QUERY_FORMAT = "#0.00";

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

	public VigilanteService(Parqueadero parqueadero) {
		this.parqueadero = parqueadero;
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

	public void inicializarCantidadDeVehiculosPorTipo(int tipoVehiculo) {
		ArrayList<Vehiculo> vehiculosActivos = (ArrayList<Vehiculo>) this.obtenerVehiculosQueEstanEnElParqueadero();
		ArrayList<Moto> motos = null;
		ArrayList<Carro> carros = null;

		for (Vehiculo vehiculoActivo : vehiculosActivos) {
			if (vehiculoActivo.getTipo() == tipoVehiculo) {
				if (tipoVehiculo == TipoVehiculo.CARRO.getTipo()) {
					carros = new ArrayList<>();
					carros.add((Carro) vehiculoActivo);
				} else if (tipoVehiculo == TipoVehiculo.MOTO.getTipo()) {
					motos = new ArrayList<>();
					motos.add((Moto) vehiculoActivo);
				}
			}
		}

		if (tipoVehiculo == TipoVehiculo.CARRO.getTipo()) {
			if(carros != null)
				parqueadero.setCarros(carros);
		} else if (tipoVehiculo == TipoVehiculo.MOTO.getTipo()) {
			if(motos != null)
				parqueadero.setMotos(motos);
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

	private int obtenerValorAPagar(Vehiculo vehiculo, long fechaEntrada, long fechaSalida) {

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

	private int costoExtraPorCilindraje(int cilindraje) {
		return (cilindraje > Parqueadero.MAXIMO_CILINDRJE_PERMITIDO_SIN_COSTO) ? Parqueadero.COSTO_POR_CILINDRAJE : 0;
	}

	private int calcularTiempoEnElParqueadero(long fechaEntrada, long fechaSalida, int tiempo) {
		long resta = fechaSalida - fechaEntrada;
		return (int) resta / tiempo;
	}

	private void reportarIngreso(Vehiculo vehiculo) {
		Date fechaEntrada = new Date();
		RegistroVehiculo registroDeEntrada = new RegistroVehiculo(fechaEntrada, null, vehiculo);
		this.vehiculoService.save(vehiculo);
		this.registroVehiculoService.save(registroDeEntrada);
	}

	private RestResponse reportarSalida(Date fechaSalida, Vehiculo vehiculo) {
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
		Vehiculo vehiculo;
		try {
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
						"Este vehículo se encuentra actualmente en el parquedero.");
			}
		} catch (NumberFormatException | ParseException | NullPointerException e) {
			e.printStackTrace();
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Ocurrio un error con la salida de este vehiculo.");

	}

	@Override
	public List<Vehiculo> obtenerVehiculosQueEstanEnElParqueadero() {
		return this.registroVehiculoService.obtenerVehiculosActivos();
	}

	private Vehiculo createVehiculoFromJson(JSONObject vehiculoJson) throws NumberFormatException, ParseException {
		Vehiculo vehiculo = null;

		if (Integer.parseInt(vehiculoJson.get("tipo").toString()) == TipoVehiculo.MOTO.getTipo()) {
			vehiculo = new Moto(vehiculoJson.get("placa").toString(),
					Integer.parseInt(vehiculoJson.get("cilindraje").toString()));
		} else if (Integer.parseInt(vehiculoJson.get("tipo").toString()) == TipoVehiculo.CARRO.getTipo()) {
			vehiculo = new Carro(vehiculoJson.get("placa").toString());
		}

		return vehiculo;
	}

	private boolean comprobarSiEsta(Vehiculo vehiculo) {
		try {
			ArrayList<Vehiculo> vehiculosActivos = (ArrayList<Vehiculo>) this.obtenerVehiculosQueEstanEnElParqueadero();
			for (Vehiculo vehiculoActivo : vehiculosActivos) {
				if (vehiculoActivo.getPlaca().equals(vehiculo.getPlaca())) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public RestResponse obtenerTRM() {
		DecimalFormat decimalFormat = new DecimalFormat(_VALUE_QUERY_FORMAT);
		String response = "";

		try {
			TcrmResponse tcrmResponse = TCRMTestClient.devolverTRMDelDia();
			response = "TRM del día: $" + decimalFormat.format(tcrmResponse.getValue());

		} catch (RemoteException | ParseException e) {
			e.printStackTrace();
		}
		return new RestResponse(HttpStatus.OK.value(), response);
	}

}
