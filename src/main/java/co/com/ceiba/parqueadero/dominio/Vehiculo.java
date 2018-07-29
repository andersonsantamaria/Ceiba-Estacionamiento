package co.com.ceiba.parqueadero.dominio;

import java.util.Date;

public class Vehiculo{
	private String placa;
	private int tipo;
	private Date fechaEntrada;

	public Vehiculo() {}
	
	public Vehiculo(String placa, int tipo, Date fechaEntrada) {
		this.placa = placa;
		this.tipo = tipo;
		this.fechaEntrada = fechaEntrada;
	}
	
	public Vehiculo(String placa, int tipo) {
		this.placa = placa;
		this.tipo = tipo;
	}

	public String getPlaca() {
		return placa;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	
	@Override
	public String toString() {
		return "Vehiculo [placa=" + placa + ", tipo=" + tipo +"]";
	}
}