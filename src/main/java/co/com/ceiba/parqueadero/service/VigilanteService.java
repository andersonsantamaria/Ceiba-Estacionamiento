package co.com.ceiba.parqueadero.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.util.RestResponse;
import co.com.ceiba.parqueadero.dominio.RegistroVehiculo;
import co.com.ceiba.parqueadero.dominio.Carro;
import co.com.ceiba.parqueadero.dominio.Moto;
import co.com.ceiba.parqueadero.dominio.Parqueadero;
import co.com.ceiba.parqueadero.dominio.Precio;
import co.com.ceiba.parqueadero.dominio.TipoRegistroVehiculo;
import co.com.ceiba.parqueadero.dominio.TipoTiempo;
import co.com.ceiba.parqueadero.dominio.TipoVehiculo;
import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioVigilante;
import co.com.ceiba.parqueadero.persistencia.entidad.RegistroVehiculoEntity;

@Service
public class VigilanteService implements RepositorioVigilante{
	private static final int DIA_EN_MILISEGUNDOS = 86400000;
	private static final int HORA_EN_MILISEGUNDOS = 3600000;
	
	private Parqueadero parqueadero;
	@Autowired
	private VehiculoService vehiculoService;
	@Autowired
	private RegistroVehiculoService registroVehiculoService;
	@Autowired
	private PrecioService precioService;
	
	public VigilanteService() {
		this.parqueadero = new Parqueadero();
	}
	
	private boolean hayDisponibilidadParaVehiculo(Vehiculo vehiculo) {
		if (vehiculo instanceof Carro) {
			return parqueadero.hayDisponibilidadParaCarro();
		}
		else if (vehiculo instanceof Moto) {
			return parqueadero.hayDisponibilidadParaMoto();
		}
		else{
			return false;
		}
	}
	
	private boolean laPlacaIniciaPorA(String placa){
		return placa.toUpperCase().startsWith("A");
	}
	
	private boolean puedeIngresarPorPlaca(String placa) {
		if(laPlacaIniciaPorA(placa)){
			if(esDiaHabil()){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return true;
		}
	}

	private boolean esDiaHabil() {
		LocalDate fechaActual = LocalDate.now();
		int dayOfWeek = fechaActual.getDayOfWeek().getValue();
		return (dayOfWeek == 1 || dayOfWeek == 7);
	}

	private int obtenerValorAPagar(Vehiculo vehiculo, long fechaEntrada, long fechaSalida) {
		
		Precio precioPorHora = this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(), TipoTiempo.HORA.getTipo());
		Precio precioPorDia = this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(), TipoTiempo.DIA.getTipo());
		
		int cantidadHoras = calcularTiempoEnElParqueadero(fechaEntrada, fechaSalida, HORA_EN_MILISEGUNDOS);
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
		return (cilindraje > 500) ? parqueadero.COSTO_POR_CILINDRAJE : 0;
	}

	private int calcularTiempoEnElParqueadero(long fechaEntrada, long fechaSalida, int tiempo){
		long resta = fechaSalida - fechaEntrada;		
		return (int) resta / tiempo;
	}
	
	private void reportarIngreso(Vehiculo vehiculo){
		Date fechaEntrada = new Date();
		RegistroVehiculo registroDeEntrada = new RegistroVehiculo(fechaEntrada, null,vehiculo);
		this.vehiculoService.save(vehiculo);
		this.registroVehiculoService.save(registroDeEntrada);
	}
	
	private RestResponse reportarSalida(Date fechaSalida, Vehiculo vehiculo){
		RegistroVehiculo registroDeSalida = this.registroVehiculoService.findByVehiculo(vehiculo.getPlaca());
		if(registroDeSalida != null) {
			int costo = obtenerValorAPagar(vehiculo, registroDeSalida.getFechaEntrada().getTime(), fechaSalida.getTime());
			registroDeSalida.setVehiculo(vehiculo);
			registroDeSalida.setFechaSalida(fechaSalida);
			registroDeSalida.setValor(costo);
			this.registroVehiculoService.save(registroDeSalida);
			return new RestResponse(HttpStatus.OK.value(), "Se ha completado la salida de forma correcta");
		}
		else return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "El vehículo no está activo, o no ha ingresado.");
	}
	
	@Override
	public RestResponse permitirIngreso(JSONObject vehiculoJson) {
		Vehiculo vehiculo = this.createVehiculoFromJson(vehiculoJson);
				
		if(hayDisponibilidadParaVehiculo(vehiculo)){
			if(puedeIngresarPorPlaca(vehiculo.getPlaca())){
				reportarIngreso(vehiculo);
				return new RestResponse(HttpStatus.OK.value(), "Se ha registrado el ingreso del vehiculo con placa = " + vehiculo.getPlaca());
			}
			else{
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "No está autorizado a entrar");
			}
		}
		else{
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "No hay disponibilidad");
		}
	}

	@Override
	public RestResponse permitirSalida(JSONObject vehiculoJson) {
		
		Vehiculo vehiculo = this.createVehiculoFromJson(vehiculoJson);
		Date fechaSalida = new Date();
		return reportarSalida(fechaSalida, vehiculo);
	}
	
	@Override
	public List<Vehiculo> obtenerVehiculosQueEstanEnElParqueadero() {
		return this.registroVehiculoService.obtenerVehiculosActivos();
	}
	
	private Vehiculo createVehiculoFromJson(JSONObject vehiculoJson) {
		Vehiculo vehiculo = null;
		
		if(Integer.parseInt(vehiculoJson.get("tipo").toString()) == TipoVehiculo.MOTO.getTipo()) {
			vehiculo = new Moto(vehiculoJson.get("placa").toString(), Short.parseShort(vehiculoJson.get("cilindraje").toString()));
		}
		else if(Integer.parseInt(vehiculoJson.get("tipo").toString()) == TipoVehiculo.CARRO.getTipo()) {
			vehiculo = new Carro(vehiculoJson.get("placa").toString());
		}
		
		return vehiculo;
	}
}
