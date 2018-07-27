package co.com.ceiba.parqueadero.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dominio.Carro;
import co.com.ceiba.parqueadero.dominio.Moto;
import co.com.ceiba.parqueadero.dominio.TipoVehiculo;
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
	public Vehiculo findByPlaca(String placa) {
		VehiculoEntity vehiculoEntity = this.repositorioVehiculoJPA.findByPlaca(placa);
		Vehiculo vehiculo = null;
		
		if(vehiculoEntity.getTipoVehiculo().getId() == TipoVehiculo.MOTO.getTipo()) {
			vehiculo = new Moto(vehiculoEntity.getPlaca(), vehiculoEntity.getCilindraje());
		}
		else {
			vehiculo = new Carro(vehiculoEntity.getPlaca());
		}
		
		return vehiculo;
	}

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

	@Override
	public List<Vehiculo> findAll() {
		List<VehiculoEntity> listEntity = this.repositorioVehiculoJPA.findAll();
		ArrayList<Vehiculo> list = new ArrayList<>();
		Vehiculo vehiculo = null;
		
		for (VehiculoEntity vehiculoEntity : listEntity) {
			if(vehiculoEntity.getTipoVehiculo().getId() == TipoVehiculo.MOTO.getTipo()) {
				vehiculo = new Moto(vehiculoEntity.getPlaca(), vehiculoEntity.getCilindraje());
			}
			else if(vehiculoEntity.getTipoVehiculo().getId() == TipoVehiculo.CARRO.getTipo()) {
				vehiculo = new Carro(vehiculoEntity.getPlaca());
			}
			
			list.add(vehiculo);
		}
		return list;
	}


}
