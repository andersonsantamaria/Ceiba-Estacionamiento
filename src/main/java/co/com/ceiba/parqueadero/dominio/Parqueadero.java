package co.com.ceiba.parqueadero.dominio;

import java.util.ArrayList;

public class Parqueadero {
	private static final byte CAPACIDAD_DE_CARROS = 20;
	private static final byte CAPACIDAD_DE_MOTOS = 10;
	public static final short COSTO_POR_CILINDRAJE = 2000;
	public static final short MAXIMO_CILINDRJE_PERMITIDO_SIN_COSTO = 500;
	
	private ArrayList<Carro> carros = new ArrayList<Carro>();
	private ArrayList<Moto> motos = new ArrayList<Moto>();
	
	public boolean hayDisponibilidadParaCarro() {
		return (this.carros.size() < CAPACIDAD_DE_CARROS); 
	}
	
	public boolean hayDisponibilidadParaMoto() {
		return (this.motos.size() < CAPACIDAD_DE_MOTOS); 
	}

	public ArrayList<Carro> getCarros() {
		return carros;
	}

	public void setCarros(ArrayList<Carro> carros) {
		this.carros = carros;
	}

	public ArrayList<Moto> getMotos() {
		return motos;
	}

	public void setMotos(ArrayList<Moto> motos) {
		this.motos = motos;
	}
	
}
