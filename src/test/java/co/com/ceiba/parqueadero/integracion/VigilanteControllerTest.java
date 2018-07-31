package co.com.ceiba.parqueadero.integracion;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import co.com.ceiba.parqueadero.CeibaEstacionamiento;

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

}
