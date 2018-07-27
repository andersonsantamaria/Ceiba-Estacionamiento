package co.com.ceiba.parqueadero.testdatabuilder;

import co.com.ceiba.parqueadero.dominio.Moto;

public class MotoTestDataBuilder {
	private static final String PLACA = "ABC-123";
	private static final int CILINDRAJE = 499;
	
	private String placa;
	private int cilindraje;

	public MotoTestDataBuilder() {
		this.placa = PLACA;
		this.cilindraje = CILINDRAJE;
	}

	public MotoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	public MotoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}

	public Moto build() {
		return new Moto(this.placa, this.cilindraje);
	}
}
