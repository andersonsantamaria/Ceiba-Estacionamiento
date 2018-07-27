package co.com.ceiba.parqueadero.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dominio.RegistroVehiculo;
import co.com.ceiba.parqueadero.dominio.Carro;
import co.com.ceiba.parqueadero.dominio.Moto;
import co.com.ceiba.parqueadero.dominio.TipoRegistroVehiculo;
import co.com.ceiba.parqueadero.dominio.TipoVehiculo;
import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioRegistroVehiculo;
import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioVehiculo;
import co.com.ceiba.parqueadero.persistencia.entidad.RegistroVehiculoEntity;
import co.com.ceiba.parqueadero.persistencia.entidad.TipoVehiculoEntity;

import co.com.ceiba.parqueadero.persistencia.entidad.VehiculoEntity;
import co.com.ceiba.parqueadero.persistencia.repositorio.jpa.RepositorioRegistroVehiculoJPA;
import co.com.ceiba.parqueadero.persistencia.repositorio.jpa.RepositorioTipoDeVehiculoJPA;
import co.com.ceiba.parqueadero.persistencia.repositorio.jpa.RepositorioVehiculoJPA;

@Service
public class RegistroVehiculoService implements RepositorioRegistroVehiculo {
	@Autowired
	private RepositorioRegistroVehiculoJPA repositorioRegistroVehiculoJPA;
	@Autowired
	private RepositorioTipoDeVehiculoJPA repositorioTipoDeVehiculoJPA;
	@Autowired
	private RepositorioVehiculoJPA repositorioVehiculoJPA;

	@Override
	public void save(RegistroVehiculo registroVehiculo) {
		RegistroVehiculoEntity registroVehiculoEntity = new RegistroVehiculoEntity();
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		TipoVehiculoEntity tiposDeVehiculoEntity = this.repositorioTipoDeVehiculoJPA
				.findById(registroVehiculo.getVehiculo().getTipo());

		vehiculoEntity.setPlaca(registroVehiculo.getVehiculo().getPlaca());
		if (registroVehiculo.getVehiculo() instanceof Moto) {
			vehiculoEntity.setCilindraje(((Moto) registroVehiculo.getVehiculo()).getCilindraje());
		}

		vehiculoEntity.setTipoVehiculo(tiposDeVehiculoEntity);
		registroVehiculoEntity.setId(registroVehiculo.getId());
		registroVehiculoEntity.setFechaEntrada(registroVehiculo.getFechaEntrada());
		registroVehiculoEntity.setFechaSalida(registroVehiculo.getFechaSalida());
		registroVehiculoEntity.setVehiculo(vehiculoEntity.getPlaca());
		registroVehiculoEntity.setValorPagado(registroVehiculo.getValor());
		this.repositorioRegistroVehiculoJPA.save(registroVehiculoEntity);
	}

	@Override
	public List<Vehiculo> obtenerVehiculosActivos() {
		List<VehiculoEntity> listEntity = this.repositorioRegistroVehiculoJPA.obtenerVehiculosActivos();
		ArrayList<Vehiculo> list = new ArrayList<>();
		Vehiculo vehiculo = null;

		for (VehiculoEntity vehiculoEntity : listEntity) {
			if (vehiculoEntity.getTipoVehiculo().getId() == TipoVehiculo.MOTO.getTipo()) {
				vehiculo = new Moto(vehiculoEntity.getPlaca(), vehiculoEntity.getCilindraje());
			} else if (vehiculoEntity.getTipoVehiculo().getId() == TipoVehiculo.CARRO.getTipo()) {
				vehiculo = new Carro(vehiculoEntity.getPlaca());
			}

			list.add(vehiculo);
		}
		return list;
	}

	@Override
	public RegistroVehiculo findByVehiculo(String placa) {
		RegistroVehiculoEntity registroVehiculoEntity = this.repositorioRegistroVehiculoJPA.obtenerVehiculoActivo(placa);
		if(registroVehiculoEntity != null) {
			VehiculoEntity vehiculoEntity = this.repositorioVehiculoJPA.findByPlaca(placa);
			Vehiculo vehiculo = null;
			if (vehiculoEntity.getTipoVehiculo().getId() == TipoVehiculo.MOTO.getTipo()) {
				vehiculo = new Moto(vehiculoEntity.getPlaca(), vehiculoEntity.getCilindraje());
			} else if (vehiculoEntity.getTipoVehiculo().getId() == TipoVehiculo.CARRO.getTipo()) {
				vehiculo = new Carro(vehiculoEntity.getPlaca());
			}
	
			return new RegistroVehiculo(registroVehiculoEntity.getId(), registroVehiculoEntity.getFechaEntrada(),
					registroVehiculoEntity.getFechaSalida(), vehiculo);
		}
		else return null;
	}
}
