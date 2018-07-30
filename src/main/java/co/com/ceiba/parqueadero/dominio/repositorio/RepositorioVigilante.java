package co.com.ceiba.parqueadero.dominio.repositorio;

import java.util.List;

import org.json.simple.JSONObject;

import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.util.RestResponse;

public interface RepositorioVigilante {
	/**
	 * Permite hacer el reporte de un vehiculo que ingrese al parquedero
	 * @param vehiculoJson
	 * @return
	 */
	RestResponse permitirIngreso(JSONObject vehiculoJson);
	
	/**
	 * Permite hacer el reporte de un vehiculo que salga del parquedero
	 * @param vehiculoJson
	 * @return
	 */
	RestResponse permitirSalida(JSONObject vehiculoJson);
	
	/**
	 * Permite obtener un listado de los vehiculos que esten en el parqueadero
	 * @param
	 * @return
	 */
	List<Vehiculo> obtenerVehiculosQueEstanEnElParqueadero();
	
	/**
	 * Permite obtener la TRM del dia
	 * @param
	 * @return
	 */
	RestResponse obtenerTRM();
}
