package co.com.ceiba.parqueadero.persistencia.repositorio.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ceiba.parqueadero.persistencia.entidad.TipoVehiculoEntity;


public interface RepositorioTipoDeVehiculoJPA extends JpaRepository<TipoVehiculoEntity, Integer>{
	TipoVehiculoEntity findById(int id);
}
