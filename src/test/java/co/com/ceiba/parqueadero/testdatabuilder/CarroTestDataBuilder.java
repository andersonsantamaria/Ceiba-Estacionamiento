package co.com.ceiba.parqueadero.testdatabuilder;

import co.com.ceiba.parqueadero.dominio.Carro;

public class CarroTestDataBuilder {

	private static final String PLACA = "ABC-123";
	
	private String placa;

	public CarroTestDataBuilder() {
		this.placa = PLACA;
	}

	public CarroTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	public Carro build() {
		return new Carro(this.placa);
	}
}
