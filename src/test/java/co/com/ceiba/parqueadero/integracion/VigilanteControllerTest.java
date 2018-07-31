package co.com.ceiba.parqueadero.integracion;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import co.com.ceiba.parqueadero.CeibaEstacionamiento;
import co.com.ceiba.parqueadero.testdatabuilder.CarroTestDataBuilder;
import co.com.ceiba.parqueadero.testdatabuilder.MotoTestDataBuilder;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = CeibaEstacionamiento.class)
@TestPropertySource(locations = "classpath:test.properties")
public class VigilanteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void obtenerTRM() throws Exception {
		//arrange
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/parqueadero/obtenerTRM")
				.contentType(MediaType.APPLICATION_JSON);
		// act
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void permitirIngresoCarro() throws Exception {
		//arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		JSONObject vehiculoJson = carroTestDataBuilder.buildJSON();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/parqueadero/ingreso")
				.contentType(MediaType.APPLICATION_JSON)
				.content(vehiculoJson.toJSONString());
		// act
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void permitirIngresoMoto() throws Exception {
		//arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		JSONObject vehiculoJson = motoTestDataBuilder.buildJSON();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/parqueadero/ingreso")
				.contentType(MediaType.APPLICATION_JSON)
				.content(vehiculoJson.toJSONString());
		// act
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	@Transactional
	@Rollback(true)
	@Test
	@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:integracion/beforeSaveSalidaMoto.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:integracion/afterSaveSalidaVehiculo.sql") })
	public void permitirSalidaMoto() throws Exception {
		//arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		JSONObject vehiculoJson = motoTestDataBuilder.buildJSON();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/parqueadero/salida")
				.contentType(MediaType.APPLICATION_JSON)
				.content(vehiculoJson.toJSONString());
		// act
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	@Transactional
	@Rollback(true)
	@Test
	@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:integracion/beforeSaveSalidaCarro.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:integracion/afterSaveSalidaVehiculo.sql") })
	public void permitirSalidaCarro() throws Exception {
		//arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		JSONObject vehiculoJson = carroTestDataBuilder.buildJSON();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/parqueadero/salida")
				.contentType(MediaType.APPLICATION_JSON)
				.content(vehiculoJson.toJSONString());
		// act
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void consultarVehiculosQueEstanEnElParqueaderoSinVehiculos() throws Exception {
		//arrange
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/parqueadero/vehiculosActivos")
				.contentType(MediaType.APPLICATION_JSON);
		// act
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}

	@Transactional
	@Rollback(true)
	@Test
	@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:integracion/beforeConsultarVehiculos.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:integracion/afterConsultarVehiculos.sql") })
	public void consultarVehiculosQueEstanEnElParqueaderoConVehiculos() throws Exception {
		//arrange
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/parqueadero/vehiculosActivos")
				.contentType(MediaType.APPLICATION_JSON);
		// act
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
}
