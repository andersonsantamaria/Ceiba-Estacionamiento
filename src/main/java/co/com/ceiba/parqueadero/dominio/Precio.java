package co.com.ceiba.parqueadero.dominio;

public class Precio {
	private int id;
	private int tipoTiempo;
	private int tipoVehiculo;
	private double valor;
	
	public Precio(int id, int tipoTiempo, int tipoVehiculo, double valor) {
		this.id = id;
		this.tipoTiempo = tipoTiempo;
		this.tipoVehiculo = tipoVehiculo;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public int getTipoTiempo() {
		return tipoTiempo;
	}

	public int getTipoVehiculo() {
		return tipoVehiculo;
	}

	public double getValor() {
		return valor;
	}
	
	
}
