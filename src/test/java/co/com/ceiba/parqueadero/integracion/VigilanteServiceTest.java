package co.com.ceiba.parqueadero.integracion;

import co.com.ceiba.parqueadero.service.VigilanteService;

public class VigilanteServiceTest extends VigilanteService {

	/*@Test
	public void hayDisponibilidadParaCarro() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		RepositorioParqueadero repositorioParqueadero = mock(RepositorioParqueadero.class);
		RepositorioParqueadero repositorioParqueadero = mock(RepositorioParqueadero.class);
		when(repositorioParqueadero.hayDisponibilidadParaCarro()).thenReturn(true);
		
		VigilanteService vigilante = new VigilanteService(repositorioParqueadero);

		// act
		boolean hayDisponibilidadParaCarro = vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertTrue(hayDisponibilidadParaCarro);
	}

	@Test
	public void noHayDisponibilidadParaCarro() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		VigilanteService vigilante = mock(VigilanteService.class);
		when(vigilante.getParqueadero().hayDisponibilidadParaCarro()).thenReturn(false);

		// act
		boolean hayDisponibilidadParaCarro = vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertFalse(hayDisponibilidadParaCarro);
	}

	@Test
	public void hayDisponibilidadParaMoto() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		Vehiculo vehiculo = motoTestDataBuilder.build();
		VigilanteService vigilante = mock(VigilanteService.class);
		when(vigilante.getParqueadero().hayDisponibilidadParaMoto()).thenReturn(true);

		// act
		boolean hayDisponibilidadParaCarro = vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertTrue(hayDisponibilidadParaCarro);
	}

	@Test
	public void noHayDisponibilidadParaMoto() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		Vehiculo vehiculo = motoTestDataBuilder.build();
		VigilanteService vigilante = mock(VigilanteService.class);
		when(vigilante.getParqueadero().hayDisponibilidadParaMoto()).thenReturn(false);

		// act
		boolean hayDisponibilidadParaCarro = vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertFalse(hayDisponibilidadParaCarro);
	}

	@Test
	public void ingresarDiaHabil() {
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
	public void ingresarDiaNoHabil() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("TDK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		// act
		boolean iniciaPorA = vigilante.laPlacaIniciaPorA(vehiculo.getPlaca());
		// assert
		assertFalse(iniciaPorA);
	}*/
}
