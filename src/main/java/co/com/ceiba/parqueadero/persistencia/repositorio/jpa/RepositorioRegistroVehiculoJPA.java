package co.com.ceiba.parqueadero.persistencia.repositorio.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.ceiba.parqueadero.persistencia.entidad.RegistroVehiculoEntity;
import co.com.ceiba.parqueadero.persistencia.entidad.VehiculoEntity;

public interface RepositorioRegistroVehiculoJPA extends JpaRepository<RegistroVehiculoEntity, Long>{
	/**
	 * Permite obtener una autorizacion entity por la placa
	 * @param id
	 * @return
	 */
	RegistroVehiculoEntity findById(int id);
	
	RegistroVehiculoEntity findByVehiculo(String placa);
	
	@SuppressWarnings("unchecked")
	RegistroVehiculoEntity save(RegistroVehiculoEntity registroVehiculoEntity);
	

	@Query("SELECT v FROM registrovehiculo r join vehiculo v on r.vehiculo = v.placa WHERE r.fechaSalida is null")
	List<VehiculoEntity> obtenerVehiculosActivos();
	
	@Query("SELECT r FROM registrovehiculo r WHERE r.vehiculo = :placa and r.fechaSalida is null")
	RegistroVehiculoEntity obtenerVehiculoActivo(@Param("placa") String placa);
}
