package co.com.ceiba.parqueadero.dominio;

public class Carro extends Vehiculo{

	public Carro(String placa, String fechaEntrada) {
		super(placa, TipoVehiculo.CARRO.getTipo(), fechaEntrada);
	}
	
	public Carro(String placa) {
		super(placa, TipoVehiculo.CARRO.getTipo());
	}
}
