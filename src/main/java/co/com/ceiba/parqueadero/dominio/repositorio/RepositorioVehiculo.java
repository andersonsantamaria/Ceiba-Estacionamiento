package co.com.ceiba.parqueadero.dominio.repositorio;

import java.util.List;

import co.com.ceiba.parqueadero.dominio.Vehiculo;

public interface RepositorioVehiculo {
	/**
	 * Permite obtener un vehiculo por la placa
	 * @param placa
	 * @return
	 */
	Vehiculo findByPlaca(String placa);

	/**
	 * Permite agregar un vehiculo al repositorio
	 * @param vehiculo
	 */
	void save(Vehiculo vehiculo);
	
	List<Vehiculo> findAll();
}
