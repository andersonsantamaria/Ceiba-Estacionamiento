package co.com.ceiba.parqueadero.dominio;

public class Vehiculo{
	private String placa;
	private int tipo;

	public Vehiculo() {}
	
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

	@Override
	public String toString() {
		return "Vehiculo [placa=" + placa + ", tipo=" + tipo +"]";
	}
}