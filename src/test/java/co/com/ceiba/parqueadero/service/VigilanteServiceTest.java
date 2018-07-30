package co.com.ceiba.parqueadero.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import co.com.ceiba.parqueadero.dominio.TipoVehiculo;
import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.testdatabuilder.CarroTestDataBuilder;

public class VigilanteServiceTest extends VigilanteService {

	@Test
	public void placaPorA() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("ADK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		// act
		boolean iniciaPorA = vigilante.laPlacaIniciaPorA(vehiculo.getPlaca());
		// assert
		assertTrue(iniciaPorA);
	}

	@Test
	public void placaPorT() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("TDK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		// act
		boolean iniciaPorA = vigilante.laPlacaIniciaPorA(vehiculo.getPlaca());
		// assert
		assertFalse(iniciaPorA);
	}

	@Test
	public void verificarSiEsDiaHabil() {
		// arrange
		VigilanteService vigilante = new VigilanteService();
		LocalDate fechaActual = LocalDate.now();
		int dayOfWeek = fechaActual.getDayOfWeek().getValue();

		if (dayOfWeek == 1 || dayOfWeek == 7) {
			// act
			boolean esDiaHabil = vigilante.esDiaHabil();
			// assert
			assertTrue(esDiaHabil);
		} else {
			// act
			boolean esDiaHabil = vigilante.esDiaHabil();
			// assert
			assertFalse(esDiaHabil);
		}
	}

	@Test
	public void siPuedeIngresarPorPlaca() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("TDK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		// act
		boolean puedeIngresarPorPlaca = vigilante.puedeIngresarPorPlaca(vehiculo.getPlaca());
		// assert
		assertTrue(puedeIngresarPorPlaca);
	}
	
	@Test
	public void verificarSiPuedeIngresarPorPlaca() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("ADK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		LocalDate fechaActual = LocalDate.now();
		int dayOfWeek = fechaActual.getDayOfWeek().getValue();
		// act
		boolean puedeIngresarPorPlaca = vigilante.puedeIngresarPorPlaca(vehiculo.getPlaca());
		
		if (dayOfWeek == 1 || dayOfWeek == 7) {
			// assert
			assertTrue(puedeIngresarPorPlaca);
		} else {
			// assert
			assertFalse(puedeIngresarPorPlaca);
		}
	}
	
//	@Test
//	public void inicializarCantidadDeCarros() {
//		// arrange
//		VigilanteService vigilante = new VigilanteService();
//		// act
//		vigilante.inicializarCantidadDeVehiculosPorTipo(TipoVehiculo.CARRO.getTipo());
//		// assert
////		assertNotNull(vigilante.));
//	}
}
