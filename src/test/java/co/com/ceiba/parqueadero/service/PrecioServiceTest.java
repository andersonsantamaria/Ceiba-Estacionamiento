package co.com.ceiba.parqueadero.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.com.ceiba.parqueadero.dominio.Precio;
import co.com.ceiba.parqueadero.dominio.TipoTiempo;
import co.com.ceiba.parqueadero.dominio.TipoVehiculo;
import co.com.ceiba.parqueadero.persistencia.entidad.PrecioEntity;
import co.com.ceiba.parqueadero.persistencia.repositorio.jpa.RepositorioPrecioJPA;
import co.com.ceiba.parqueadero.testdatabuilder.PrecioEntityTestDataBuilder;


public class PrecioServiceTest extends VigilanteService {

	@Mock
	private RepositorioPrecioJPA repositorioPrecioJPA;
	
	@InjectMocks
	private PrecioService precioService;
	
	@org.junit.Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void obtenerPrecioParaCarroPorHora() {
		// arrange
		int idTipoVehiculo = TipoVehiculo.CARRO.getTipo();
		int id_tiempo = TipoTiempo.HORA.getTipo();
		PrecioEntityTestDataBuilder precioEntityTestDataBuilder = new PrecioEntityTestDataBuilder();
		PrecioEntity precioEntity = precioEntityTestDataBuilder.build();
		when(this.repositorioPrecioJPA.obtenerPrecioPorTipoVehiculoYTiempo(idTipoVehiculo, id_tiempo)).thenReturn(precioEntity);

		// act
		Precio precio = precioService.obtenerPrecioPorTipoVehiculoYTiempo(idTipoVehiculo, id_tiempo);
		// assert
		assertEquals(1000, precio.getValor(), 0);
	}

}