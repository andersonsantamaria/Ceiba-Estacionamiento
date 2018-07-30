package co.com.ceiba.parqueadero.dominio;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioParqueadero;

public class Parqueadero implements RepositorioParqueadero{
	private static final byte CAPACIDAD_DE_CARROS = 20;
	private static final byte CAPACIDAD_DE_MOTOS = 10;
	public static final short COSTO_POR_CILINDRAJE = 2000;
	public static final short MAXIMO_CILINDRJE_PERMITIDO_SIN_COSTO = 500;
	
	private ArrayList<Carro> carros;
	private ArrayList<Moto> motos;
	
	public Parqueadero() {
		super();
		this.carros = new ArrayList<>();
		this.motos = new ArrayList<>();
	}

	public boolean hayDisponibilidadParaCarro() {
		return (this.carros.size() < CAPACIDAD_DE_CARROS); 
	}
	
	public boolean hayDisponibilidadParaMoto() {
		return (this.motos.size() < CAPACIDAD_DE_MOTOS); 
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = (ArrayList<Carro>) carros;
	}

	public List<Moto> getMotos() {
		return motos;
	}

	public void setMotos(List<Moto> motos) {
		this.motos = (ArrayList<Moto>) motos;
	}
	
}
