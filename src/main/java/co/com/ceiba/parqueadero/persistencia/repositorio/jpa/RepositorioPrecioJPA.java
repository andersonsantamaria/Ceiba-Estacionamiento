package co.com.ceiba.parqueadero.persistencia.repositorio.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.ceiba.parqueadero.persistencia.entidad.PrecioEntity;

public interface RepositorioPrecioJPA  extends JpaRepository<PrecioEntity, Long>{
	@Query("SELECT p FROM precio p WHERE p.tipoVehiculo.id = :idTipoVehiculo and p.tiempo.id = :id_tiempo")
	PrecioEntity obtenerPrecioPorTipoVehiculoYTiempo(@Param("idTipoVehiculo") int idTipoVehiculo, @Param("id_tiempo") int idTiempo);
}