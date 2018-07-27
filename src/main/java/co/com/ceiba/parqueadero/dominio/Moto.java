package co.com.ceiba.parqueadero.dominio;

public class Moto extends Vehiculo{
	private int  cilindraje;

	public Moto(String placa, int cilindraje) {
		super(placa, TipoVehiculo.MOTO.getTipo());
		this.cilindraje = cilindraje;
	}

	public int getCilindraje() {
		return cilindraje;
	}
}
