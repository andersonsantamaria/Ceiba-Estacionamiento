package co.com.ceiba.parqueadero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dominio.Precio;
import co.com.ceiba.parqueadero.dominio.repositorio.RepositorioPrecio;
import co.com.ceiba.parqueadero.persistencia.entidad.PrecioEntity;
import co.com.ceiba.parqueadero.persistencia.repositorio.jpa.RepositorioPrecioJPA;

@Service
public class PrecioService  implements RepositorioPrecio {
	@Autowired
	private RepositorioPrecioJPA repositorioPrecioJPA;

	@Override
	public Precio obtenerPrecioPorTipoVehiculoYTiempo(int idTipoVehiculo, int id_tiempo) {
		PrecioEntity precioEntity = this.repositorioPrecioJPA.obtenerPrecioPorTipoVehiculoYTiempo(idTipoVehiculo, id_tiempo);
		Precio precio = new Precio(precioEntity.getId(), precioEntity.getTiempo().getId(), precioEntity.getTipoVehiculo().getId(), precioEntity.getValor());
		return precio;
	}
}
