package co.com.ceiba.parqueadero.testdatabuilder;

import co.com.ceiba.parqueadero.dominio.TipoTiempo;
import co.com.ceiba.parqueadero.dominio.TipoVehiculo;
import co.com.ceiba.parqueadero.persistencia.entidad.PrecioEntity;
import co.com.ceiba.parqueadero.persistencia.entidad.TiempoEntity;
import co.com.ceiba.parqueadero.persistencia.entidad.TipoVehiculoEntity;

public class PrecioEntityTestDataBuilder {
	private static final int ID = 1;
	private static final double VALOR = 1000;
	
	private int id;
	private TipoVehiculoEntity tipoVehiculo;
	private TiempoEntity tiempo;
	private double valor;
	
	public PrecioEntityTestDataBuilder() {
		this.id = ID;
		this.tipoVehiculo = new TipoVehiculoEntity(TipoVehiculo.CARRO.getTipo(), "Carro");
		this.tiempo = new TiempoEntity(TipoTiempo.HORA.getTipo(), "Hora");
		this.valor = VALOR;
	}
	
	public PrecioEntityTestDataBuilder conTipoVehiculo(int idTipoVehiculo, String descripcion) {
		this.tipoVehiculo = new TipoVehiculoEntity(idTipoVehiculo, descripcion);
		return this;
	}
	
	public PrecioEntityTestDataBuilder conTiempo(int idTipoTiempo, String descripcion) {
		this.tiempo = new TiempoEntity(idTipoTiempo, descripcion);
		return this;
	}

	public PrecioEntity build() {
		return new PrecioEntity(this.id, this.tipoVehiculo, this.tiempo, this.valor);
	}
}
