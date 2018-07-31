package co.com.ceiba.parqueadero.dominio.repositorio;


import co.com.ceiba.parqueadero.dominio.Precio;

public interface RepositorioPrecio {
	Precio obtenerPrecioPorTipoVehiculoYTiempo(int idTipoVehiculo, int idTiempo);
}
