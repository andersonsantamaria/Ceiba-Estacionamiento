package co.com.ceiba.parqueadero.dominio.repositorio;

import java.util.List;

import org.json.simple.JSONObject;

import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.util.RestResponse;

public interface RepositorioVigilante {
	/**
	 * Permite hacer el reporte de un vehiculo que ingrese al parquedero
	 * @param fecha
	 * @return
	 */
	RestResponse permitirIngreso(JSONObject vehiculoJson);
	
	/**
	 * Permite hacer el reporte de un vehiculo que salga del parquedero
	 * @param fecha
	 * @return
	 */
	RestResponse permitirSalida(JSONObject vehiculoJson);
	
	/**
	 * Permite obtener un listdo de los vehiculos que estén en el parqueadero
	 * @param fecha
	 * @return
	 */
	List<Vehiculo> obtenerVehiculosQueEstanEnElParqueadero();
}
