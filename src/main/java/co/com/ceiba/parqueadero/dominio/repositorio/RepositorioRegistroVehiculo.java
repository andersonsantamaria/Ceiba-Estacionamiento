package co.com.ceiba.parqueadero.dominio.repositorio;

import java.util.List;

import co.com.ceiba.parqueadero.dominio.RegistroVehiculo;
import co.com.ceiba.parqueadero.dominio.Vehiculo;

public interface RepositorioRegistroVehiculo {	
		
	RegistroVehiculo findByVehiculo(String placa);
	
	void save(RegistroVehiculo autorizacion);
	
	List<Vehiculo> obtenerVehiculosActivos();
}
