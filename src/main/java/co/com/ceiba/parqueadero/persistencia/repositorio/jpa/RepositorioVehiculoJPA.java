package co.com.ceiba.parqueadero.persistencia.repositorio.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ceiba.parqueadero.persistencia.entidad.VehiculoEntity;

public interface RepositorioVehiculoJPA extends JpaRepository<VehiculoEntity, Long>{
	/**
	 * Permite obtener un vehiculo entity por la placa
	 * @param placa
	 * @return
	 */
	VehiculoEntity findByPlaca(String placa);
	
	@SuppressWarnings("unchecked")
	VehiculoEntity save(VehiculoEntity vehiculoEntity);
}
