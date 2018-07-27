package co.com.ceiba.parqueadero.dominio;

import java.util.Date;

public class RegistroVehiculo{
	private int id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private int valor;
	private Vehiculo vehiculo;
	
	public RegistroVehiculo(int id, Date fechaEntrada, Date fechaSalida, Vehiculo vehiculo) {
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.vehiculo = vehiculo;
	}
	
	public RegistroVehiculo(Date fechaEntrada, Date fechaSalida, Vehiculo vehiculo) {
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.vehiculo = vehiculo;
	}

	public int getId() {
		return id;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	
	public Date getFechaSalida() {
		return fechaSalida;
	}
	
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
}
