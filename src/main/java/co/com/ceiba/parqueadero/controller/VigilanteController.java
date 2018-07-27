package co.com.ceiba.parqueadero.controller;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioVigilante;
import co.com.ceiba.parqueadero.util.RestResponse;

@RequestMapping("/parqueadero")
@RestController
public class VigilanteController {
	@Autowired
	private RepositorioVigilante repositorioVigilante;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/ingreso", method = RequestMethod.POST)
	public RestResponse permitirIngreso(@RequestBody JSONObject vehiculoJson){
		return repositorioVigilante.permitirIngreso(vehiculoJson);
	}
	
	@RequestMapping(value = "/salida", method = RequestMethod.POST)
	public RestResponse permitirSalida(@RequestBody JSONObject vehiculoJson){
		return repositorioVigilante.permitirSalida(vehiculoJson);
	}
	
	@RequestMapping(value = "/vehiculosActivos", method = RequestMethod.GET)
	public List<Vehiculo> consultarVehiculosQueEstanEnElParqueadero() {
		return repositorioVigilante.obtenerVehiculosQueEstanEnElParqueadero();
	}	
}