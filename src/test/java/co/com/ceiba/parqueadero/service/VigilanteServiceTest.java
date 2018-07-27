package co.com.ceiba.parqueadero.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.testdatabuilder.CarroTestDataBuilder;

public class VigilanteServiceTest extends VigilanteService{
	
	@Test
	public void placaPorA() {
		//arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("ADK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();
		
		VigilanteService vigilante = new VigilanteService();
		//act
		boolean iniciaPorA =  vigilante.laPlacaIniciaPorA(vehiculo.getPlaca());
		//assert
		assertTrue(iniciaPorA);
	}
	
	@Test
	public void placaPorT() {
		//arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("TDK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();
		
		VigilanteService vigilante = new VigilanteService();
		//act
		boolean iniciaPorA =  vigilante.laPlacaIniciaPorA(vehiculo.getPlaca());
		//assert
		assertFalse(iniciaPorA);
	}
}
