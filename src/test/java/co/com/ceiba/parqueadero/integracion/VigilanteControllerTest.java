package co.com.ceiba.parqueadero.integracion;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import co.com.ceiba.parqueadero.CeibaEstacionamiento;
//
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest(classes = CeibaEstacionamiento.class)
//@TestPropertySource(locations = "classpath:test.properties")
public class VigilanteControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private WebApplicationContext context;
//
//	@Before
//	public void setup() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//	}

//	@Test
//	public void obtenerTRM() throws Exception {
//		//arrange
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/parqueadero/obtenerTRM")
//				.contentType(MediaType.APPLICATION_JSON);
//		// act
//		mockMvc.perform(requestBuilder).andDo(print())
//		.andExpect(status().isOk());
//	}

	/*
	 * @Test public void noHayDisponibilidadParaCarro() { // arrange
	 * CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
	 * Vehiculo vehiculo = carroTestDataBuilder.build(); VigilanteService vigilante
	 * = mock(VigilanteService.class);
	 * when(vigilante.getParqueadero().hayDisponibilidadParaCarro()).thenReturn(
	 * false);
	 * 
	 * // act boolean hayDisponibilidadParaCarro =
	 * vigilante.hayDisponibilidadParaVehiculo(vehiculo); // assert
	 * assertFalse(hayDisponibilidadParaCarro); }
	 * 
	 * @Test public void hayDisponibilidadParaMoto() { // arrange
	 * MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder(); Vehiculo
	 * vehiculo = motoTestDataBuilder.build(); VigilanteService vigilante =
	 * mock(VigilanteService.class);
	 * when(vigilante.getParqueadero().hayDisponibilidadParaMoto()).thenReturn(true)
	 * ;
	 * 
	 * // act boolean hayDisponibilidadParaCarro =
	 * vigilante.hayDisponibilidadParaVehiculo(vehiculo); // assert
	 * assertTrue(hayDisponibilidadParaCarro); }
	 * 
	 * @Test public void noHayDisponibilidadParaMoto() { // arrange
	 * MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder(); Vehiculo
	 * vehiculo = motoTestDataBuilder.build(); VigilanteService vigilante =
	 * mock(VigilanteService.class);
	 * when(vigilante.getParqueadero().hayDisponibilidadParaMoto()).thenReturn(false
	 * );
	 * 
	 * // act boolean hayDisponibilidadParaCarro =
	 * vigilante.hayDisponibilidadParaVehiculo(vehiculo); // assert
	 * assertFalse(hayDisponibilidadParaCarro); }
	 * 
	 * @Test public void ingresarDiaHabil() { // arrange CarroTestDataBuilder
	 * carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("TDK-987");
	 * Vehiculo vehiculo = carroTestDataBuilder.build();
	 * 
	 * VigilanteService vigilante = new VigilanteService(); // act boolean
	 * iniciaPorA = vigilante.laPlacaIniciaPorA(vehiculo.getPlaca()); // assert
	 * assertFalse(iniciaPorA); }
	 * 
	 * @Test public void ingresarDiaNoHabil() { // arrange CarroTestDataBuilder
	 * carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("TDK-987");
	 * Vehiculo vehiculo = carroTestDataBuilder.build();
	 * 
	 * VigilanteService vigilante = new VigilanteService(); // act boolean
	 * iniciaPorA = vigilante.laPlacaIniciaPorA(vehiculo.getPlaca()); // assert
	 * assertFalse(iniciaPorA); }
	 */
}
