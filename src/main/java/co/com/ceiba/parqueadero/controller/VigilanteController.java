package co.com.ceiba.parqueadero.controller;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioVigilante;
import co.com.ceiba.parqueadero.util.RestResponse;

@RequestMapping("/parqueadero")
@RestController
public class VigilanteController {
	@Autowired
	private RepositorioVigilante repositorioVigilante;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/ingreso", method = RequestMethod.POST)
	public RestResponse permitirIngreso(@RequestBody JSONObject vehiculoJson){
		return repositorioVigilante.permitirIngreso(vehiculoJson);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/salida", method = RequestMethod.POST)
	public RestResponse permitirSalida(@RequestBody JSONObject vehiculoJson){
		return repositorioVigilante.permitirSalida(vehiculoJson);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/vehiculosActivos", method = RequestMethod.GET)
	public List<Vehiculo> consultarVehiculosQueEstanEnElParqueadero() {
		return repositorioVigilante.obtenerVehiculosQueEstanEnElParqueadero();
	}	
}