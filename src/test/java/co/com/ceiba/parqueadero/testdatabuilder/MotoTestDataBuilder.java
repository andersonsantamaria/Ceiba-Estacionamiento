package co.com.ceiba.parqueadero.testdatabuilder;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.parqueadero.dominio.Moto;
import co.com.ceiba.parqueadero.dominio.Vehiculo;

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
	
	public List<Vehiculo> buildList(int cantidadCarros){
		List<Vehiculo> motos = new ArrayList<>();
		
		for(int i = 0; i < cantidadCarros; i++) {
			motos.add(this.build());
		}
		
		return motos;
	}
}
