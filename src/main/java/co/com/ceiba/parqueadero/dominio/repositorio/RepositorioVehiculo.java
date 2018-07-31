package co.com.ceiba.parqueadero.dominio.repositorio;

import co.com.ceiba.parqueadero.dominio.Vehiculo;

public interface RepositorioVehiculo {

	/**
	 * Permite agregar un vehiculo al repositorio
	 * @param vehiculo
	 */
	void save(Vehiculo vehiculo);
	
}
