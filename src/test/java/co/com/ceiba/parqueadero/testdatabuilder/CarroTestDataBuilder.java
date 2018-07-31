package co.com.ceiba.parqueadero.testdatabuilder;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import co.com.ceiba.parqueadero.dominio.Carro;
import co.com.ceiba.parqueadero.dominio.Vehiculo;

public class CarroTestDataBuilder {

	private static final String PLACA = "BDC-123";
	
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
	
	public List<Vehiculo> buildList(int cantidadCarros){
		List<Vehiculo> carros = new ArrayList<>();
		
		for(int i = 0; i < cantidadCarros; i++) {
			carros.add(this.build());
		}
		
		return carros;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject buildJSON() {
		JSONObject vehiculoJson = new JSONObject();
		vehiculoJson.put("placa", "MNH-987");
		vehiculoJson.put("tipo", 1);
		return vehiculoJson;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject buildJSONDefault() {
		JSONObject vehiculoJson = new JSONObject();
		vehiculoJson.put("placa", "BDC-123");
		vehiculoJson.put("tipo", 1);
		return vehiculoJson;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject buildJSONPlacaA() {
		JSONObject vehiculoJson = new JSONObject();
		vehiculoJson.put("placa", "ADC-123");
		vehiculoJson.put("tipo", 1);
		return vehiculoJson;
	}
}
