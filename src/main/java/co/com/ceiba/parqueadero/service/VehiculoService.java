package co.com.ceiba.parqueadero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dominio.Moto;
import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioVehiculo;
import co.com.ceiba.parqueadero.persistencia.entidad.TipoVehiculoEntity;
import co.com.ceiba.parqueadero.persistencia.entidad.VehiculoEntity;
import co.com.ceiba.parqueadero.persistencia.repositorio.jpa.RepositorioTipoDeVehiculoJPA;
import co.com.ceiba.parqueadero.persistencia.repositorio.jpa.RepositorioVehiculoJPA;

@Service
public class VehiculoService implements RepositorioVehiculo{
	
	@Autowired
	private RepositorioVehiculoJPA repositorioVehiculoJPA;
	@Autowired
	private RepositorioTipoDeVehiculoJPA repositorioTipoDeVehiculoJPA;

	@Override
	public void save(Vehiculo vehiculo) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		TipoVehiculoEntity tiposDeVehiculoEntity = this.repositorioTipoDeVehiculoJPA.findById(vehiculo.getTipo());
		
		vehiculoEntity.setPlaca(vehiculo.getPlaca());
		if(vehiculo instanceof Moto) {
			vehiculoEntity.setCilindraje(((Moto) vehiculo).getCilindraje());
		}
		vehiculoEntity.setTipoVehiculo(tiposDeVehiculoEntity);
		this.repositorioVehiculoJPA.save(vehiculoEntity);
	}

}
