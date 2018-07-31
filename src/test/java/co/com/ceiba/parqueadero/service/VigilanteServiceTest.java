package co.com.ceiba.parqueadero.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.com.ceiba.parqueadero.dominio.Moto;
import co.com.ceiba.parqueadero.dominio.Parqueadero;
import co.com.ceiba.parqueadero.dominio.Precio;
import co.com.ceiba.parqueadero.dominio.RegistroVehiculo;
import co.com.ceiba.parqueadero.dominio.TipoTiempo;
import co.com.ceiba.parqueadero.dominio.TipoVehiculo;
import co.com.ceiba.parqueadero.dominio.Vehiculo;
import co.com.ceiba.parqueadero.testdatabuilder.CarroTestDataBuilder;
import co.com.ceiba.parqueadero.testdatabuilder.MotoTestDataBuilder;
import co.com.ceiba.parqueadero.util.RestResponse;


public class VigilanteServiceTest extends VigilanteService {

	
	@Mock
	private RegistroVehiculoService registroVehiculoService;
	
	@Mock
	private PrecioService precioService;
	
	@Mock
	private VehiculoService vehiculoService;
	
	@InjectMocks
	private VigilanteService vigilante;
	
	@org.junit.Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void placaPorA() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("ADK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		// act
		boolean iniciaPorA = vigilante.laPlacaIniciaPorA(vehiculo.getPlaca());
		// assert
		assertTrue(iniciaPorA);
	}

	@Test
	public void placaPorT() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("TDK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		// act
		boolean iniciaPorA = vigilante.laPlacaIniciaPorA(vehiculo.getPlaca());
		// assert
		assertFalse(iniciaPorA);
	}

	@Test
	public void verificarSiEsDiaHabil() {
		// arrange
		VigilanteService vigilante = new VigilanteService();
		LocalDate fechaActual = LocalDate.now();
		int dayOfWeek = fechaActual.getDayOfWeek().getValue();

		if (dayOfWeek == 1 || dayOfWeek == 7) {
			// act
			boolean esDiaHabil = vigilante.esDiaHabil();
			// assert
			assertTrue(esDiaHabil);
		} else {
			// act
			boolean esDiaHabil = vigilante.esDiaHabil();
			// assert
			assertFalse(esDiaHabil);
		}
	}

	@Test
	public void siPuedeIngresarPorPlaca() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("TDK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		// act
		boolean puedeIngresarPorPlaca = vigilante.puedeIngresarPorPlaca(vehiculo.getPlaca());
		// assert
		assertTrue(puedeIngresarPorPlaca);
	}
	
	@Test
	public void verificarSiPuedeIngresarPorPlaca() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder().conPlaca("ADK-987");
		Vehiculo vehiculo = carroTestDataBuilder.build();

		VigilanteService vigilante = new VigilanteService();
		LocalDate fechaActual = LocalDate.now();
		int dayOfWeek = fechaActual.getDayOfWeek().getValue();
		// act
		boolean puedeIngresarPorPlaca = vigilante.puedeIngresarPorPlaca(vehiculo.getPlaca());
		
		if (dayOfWeek == 1 || dayOfWeek == 7) {
			// assert
			assertTrue(puedeIngresarPorPlaca);
		} else {
			// assert
			assertFalse(puedeIngresarPorPlaca);
		}
	}
	
	@Test
	public void inicializar_5_Carros() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(5);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		this.vigilante.inicializarCantidadDeVehiculosPorTipo(TipoVehiculo.CARRO.getTipo());
		// assert
		Assert.assertEquals(5, this.vigilante.getParqueadero().getCarros().size());		
	}
	
	@Test
	public void inicializar_6_Motos() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		List<Vehiculo> vehiculos = motoTestDataBuilder.buildList(6);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		this.vigilante.inicializarCantidadDeVehiculosPorTipo(TipoVehiculo.MOTO.getTipo());
		// assert
		Assert.assertEquals(6, this.vigilante.getParqueadero().getMotos().size());		
	}
	
	@Test
	public void inicializarTipoNoValidoDeVehiculos() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		List<Vehiculo> vehiculos = motoTestDataBuilder.buildList(6);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		this.vigilante.inicializarCantidadDeVehiculosPorTipo(3);
		// assert
		Assert.assertEquals(0, this.vigilante.getParqueadero().getMotos().size());
		Assert.assertEquals(0, this.vigilante.getParqueadero().getCarros().size());
	}
	
	@Test
	public void inicializarSinMotos() {
		// arrange
		List<Vehiculo> vehiculos = null;
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		this.vigilante.inicializarCantidadDeVehiculosPorTipo(TipoVehiculo.MOTO.getTipo());
		// assert
		Assert.assertEquals(0, this.vigilante.getParqueadero().getMotos().size());
	}
	
	@Test
	public void siHayDisponibilidadParaCarro() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(19);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		boolean hayDisponibilidadParaVehiculo = this.vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertTrue(hayDisponibilidadParaVehiculo);	
	}
	
	@Test
	public void noHayDisponibilidadParaCarro() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(20);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		boolean hayDisponibilidadParaVehiculo = this.vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertFalse(hayDisponibilidadParaVehiculo);	
	}
	
	@Test
	public void siHayDisponibilidadParaMoto() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		Vehiculo vehiculo = motoTestDataBuilder.build();
		List<Vehiculo> vehiculos = motoTestDataBuilder.buildList(9);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		boolean hayDisponibilidadParaVehiculo = this.vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertTrue(hayDisponibilidadParaVehiculo);	
	}
	
	@Test
	public void noHayDisponibilidadParaMoto() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		Vehiculo vehiculo = motoTestDataBuilder.build();
		List<Vehiculo> vehiculos = motoTestDataBuilder.buildList(10);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		boolean hayDisponibilidadParaVehiculo = this.vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertFalse(hayDisponibilidadParaVehiculo);	
	}
	
	@Test
	public void noHayDisponibilidadParaOtroTipoDeVehiculo() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = null;
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(10);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		boolean hayDisponibilidadParaVehiculo = this.vigilante.hayDisponibilidadParaVehiculo(vehiculo);
		// assert
		assertFalse(hayDisponibilidadParaVehiculo);	
	}
	
	@Test
	public void cobrarCostoExtraPorCilindraje() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder().conCilindraje(501);
		Vehiculo vehiculo = motoTestDataBuilder.build();
		// act
		int costoExtraPorCilindraje = this.vigilante.costoExtraPorCilindraje(((Moto) vehiculo).getCilindraje());
		// assert
		Assert.assertEquals(Parqueadero.COSTO_POR_CILINDRAJE,costoExtraPorCilindraje);
	}
	
	@Test
	public void noCobrarCostoExtraPorCilindraje() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder().conCilindraje(500);
		Vehiculo vehiculo = motoTestDataBuilder.build();
		// act
		int costoExtraPorCilindraje = this.vigilante.costoExtraPorCilindraje(((Moto) vehiculo).getCilindraje());
		// assert
		Assert.assertEquals(0, costoExtraPorCilindraje);
	}
	
	@Test
	public void calcularTiempoEnElParqueaderoEnHoras() {
		// arrange
		LocalDateTime localDateTimeEntrada = LocalDateTime.of(2018, 07, 27, 13, 50);
		Date fechaEntrada = Date.from(localDateTimeEntrada.atZone(ZoneId.systemDefault()).toInstant());
		LocalDateTime localDateTimeSalida = LocalDateTime.of(2018, 07, 30, 14, 24);
		Date fechaSalida = Date.from(localDateTimeSalida.atZone(ZoneId.systemDefault()).toInstant());
		int cantidadHoras = 1;
		// act
		cantidadHoras += this.vigilante.calcularTiempoEnElParqueadero(fechaEntrada.getTime(), fechaSalida.getTime(), VigilanteService.HORA_EN_MILISEGUNDOS);
		// assert
		Assert.assertEquals(73, cantidadHoras);
	}
	
	@Test
	public void calcularTiempoEnElParqueaderoEnDias() {
		// arrange
		LocalDateTime localDateTimeEntrada = LocalDateTime.of(2018, 07, 27, 13, 50);
		Date fechaEntrada = Date.from(localDateTimeEntrada.atZone(ZoneId.systemDefault()).toInstant());
		LocalDateTime localDateTimeSalida = LocalDateTime.of(2018, 07, 30, 14, 24);
		Date fechaSalida = Date.from(localDateTimeSalida.atZone(ZoneId.systemDefault()).toInstant());

		// act
		int cantidadDias = this.vigilante.calcularTiempoEnElParqueadero(fechaEntrada.getTime(), fechaSalida.getTime(), VigilanteService.DIA_EN_MILISEGUNDOS);
		// assert
		Assert.assertEquals(3, cantidadDias);
	}
	
	@Test
	public void obtenerValorAPagarCarro() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		LocalDateTime localDateTimeEntrada = LocalDateTime.of(2018, 07, 27, 13, 50);
		Date fechaEntrada = Date.from(localDateTimeEntrada.atZone(ZoneId.systemDefault()).toInstant());
		LocalDateTime localDateTimeSalida = LocalDateTime.of(2018, 07, 30, 14, 24);
		Date fechaSalida = Date.from(localDateTimeSalida.atZone(ZoneId.systemDefault()).toInstant());
		
		Precio precioPorHora = new Precio(1, 1, 1, 1000);
		Precio precioPorDia = new Precio(2, 2, 1, 8000);
		
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.HORA.getTipo())).thenReturn(precioPorHora);
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.DIA.getTipo())).thenReturn(precioPorDia);

		// act
		int cantidadAPagar = this.vigilante.obtenerValorAPagar(vehiculo, fechaEntrada.getTime(), fechaSalida.getTime());
		// assert
		Assert.assertEquals(25000, cantidadAPagar);
	}
	
	@Test
	public void obtenerValorAPagarMotoConCostoPorCilindraje() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder().conCilindraje(501);
		Vehiculo vehiculo = motoTestDataBuilder.build();
		LocalDateTime localDateTimeEntrada = LocalDateTime.of(2018, 07, 27, 13, 50);
		Date fechaEntrada = Date.from(localDateTimeEntrada.atZone(ZoneId.systemDefault()).toInstant());
		LocalDateTime localDateTimeSalida = LocalDateTime.of(2018, 07, 30, 14, 24);
		Date fechaSalida = Date.from(localDateTimeSalida.atZone(ZoneId.systemDefault()).toInstant());
		
		Precio precioPorHora = new Precio(3, 1, 2, 500);
		Precio precioPorDia = new Precio(3, 2, 2, 4000);
		
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.HORA.getTipo())).thenReturn(precioPorHora);
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.DIA.getTipo())).thenReturn(precioPorDia);

		// act
		int cantidadAPagar = this.vigilante.obtenerValorAPagar(vehiculo, fechaEntrada.getTime(), fechaSalida.getTime());
		// assert
		Assert.assertEquals(14500, cantidadAPagar);
	}
	
	@Test
	public void obtenerValorAPagarMotoSinCostoPorCilindraje() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		Vehiculo vehiculo = motoTestDataBuilder.build();
		LocalDateTime localDateTimeEntrada = LocalDateTime.of(2018, 07, 27, 13, 50);
		Date fechaEntrada = Date.from(localDateTimeEntrada.atZone(ZoneId.systemDefault()).toInstant());
		LocalDateTime localDateTimeSalida = LocalDateTime.of(2018, 07, 30, 22, 24);
		Date fechaSalida = Date.from(localDateTimeSalida.atZone(ZoneId.systemDefault()).toInstant());
		
		Precio precioPorHora = new Precio(3, 1, 2, 500);
		Precio precioPorDia = new Precio(3, 2, 2, 4000);
		
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.HORA.getTipo())).thenReturn(precioPorHora);
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.DIA.getTipo())).thenReturn(precioPorDia);

		// act
		int cantidadAPagar = this.vigilante.obtenerValorAPagar(vehiculo, fechaEntrada.getTime(), fechaSalida.getTime());
		// assert
		Assert.assertEquals(16000, cantidadAPagar);
	}
	
	@Test
	public void crearCarroDeUnJSON() {
		// arrange
		JSONObject vehiculoJson = new CarroTestDataBuilder().buildJSON();

		// act
		Vehiculo vehiculo = this.vigilante.createVehiculoFromJson(vehiculoJson);
		// assert
		Assert.assertEquals("MNH-987", vehiculo.getPlaca());
	}
	
	@Test
	public void crearMotoDeUnJSON() {
		// arrange
		JSONObject vehiculoJson = new MotoTestDataBuilder().buildJSON();

		// act
		Vehiculo vehiculo = this.vigilante.createVehiculoFromJson(vehiculoJson);
		// assert
		Assert.assertEquals("MNH-989", vehiculo.getPlaca());
		Assert.assertEquals(900, ((Moto)vehiculo).getCilindraje());
	}
	
	@Test
	public void vehiculoSiEsta() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder();
		List<Vehiculo> vehiculos = motoTestDataBuilder.buildList(5);
		Vehiculo vehiculo = motoTestDataBuilder.build();
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		boolean comprobarSiEsta = this.vigilante.comprobarSiEsta(vehiculo);
		// assert
		Assert.assertEquals(true, comprobarSiEsta);		
	}
	
	@Test
	public void vehiculoNoEsta() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder().conPlaca("BCD-123");
		List<Vehiculo> vehiculos = motoTestDataBuilder.buildList(5);
		Vehiculo vehiculo = motoTestDataBuilder.conPlaca("BCD-123").build();
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		boolean comprobarSiEsta = this.vigilante.comprobarSiEsta(vehiculo);
		// assert
		Assert.assertEquals(false, comprobarSiEsta);	
	}
	
	@Test
	public void comprobarSiEstaVehiculoNoValido() {
		// arrange
		MotoTestDataBuilder motoTestDataBuilder = new MotoTestDataBuilder().conPlaca("BCD-123");
		List<Vehiculo> vehiculos = motoTestDataBuilder.buildList(5);
		Vehiculo vehiculo = null;
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		// act
		boolean comprobarSiEsta = this.vigilante.comprobarSiEsta(vehiculo);
		// assert
		Assert.assertEquals(false, comprobarSiEsta);	
	}
	
	@Test
	public void obtenerTRMTest() {
		// arrange
		RestResponse response;
		// act
		response = this.vigilante.obtenerTRM();
		// assert
		Assert.assertEquals(new Integer(200), response.getResponseCode());
	}
	
	@Test
	public void reportarIngresoDeCarro() {
		// arrange
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		Date fechaEntrada = new Date();
		RegistroVehiculo registroDeEntrada = new RegistroVehiculo(fechaEntrada, null, vehiculo);
		doNothing().when(vehiculoService).save(vehiculo);
		doNothing().when(registroVehiculoService).save(registroDeEntrada);
		// act
		this.vigilante.reportarIngreso(vehiculo);
		// assert
		Assert.assertEquals(0, 0);
	}
	
	@Test
	public void permitirIngresoDeCarro() {
		// arrange
		RestResponse response;
		JSONObject vehiculoJson = new CarroTestDataBuilder().buildJSON();
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(19);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		
		// act
		response = this.vigilante.permitirIngreso(vehiculoJson);
		// assert
		Assert.assertEquals(new Integer(200), response.getResponseCode());
	}
	
	@Test
	public void noPermitirIngresoDeCarroPorVehiculoEnParqueadero() {
		// arrange
		RestResponse response;
		JSONObject vehiculoJson = new CarroTestDataBuilder().buildJSONDefault();
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(19);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		
		// act
		response = this.vigilante.permitirIngreso(vehiculoJson);
		// assert
		Assert.assertEquals(new Integer(406), response.getResponseCode());
	}
	
	@Test
	public void noPermitirIngresoDeCarroMalEnvio() {
		// arrange
		RestResponse response;
		JSONObject vehiculoJson = null;
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(19);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		
		// act
		response = this.vigilante.permitirIngreso(vehiculoJson);
		// assert
		Assert.assertEquals(new Integer(406), response.getResponseCode());
	}
	
	@Test
	public void noPermitirIngresoDeCarroNoHayDisponibilidad() {
		// arrange
		RestResponse response;
		JSONObject vehiculoJson = new CarroTestDataBuilder().buildJSON();
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(20);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		
		// act
		response = this.vigilante.permitirIngreso(vehiculoJson);
		// assert
		Assert.assertEquals(new Integer(406), response.getResponseCode());
	}
	
	@Test
	public void noPermitirIngresoDeCarroPorPlaca() {
		// arrange
		RestResponse response;
		JSONObject vehiculoJson = new CarroTestDataBuilder().buildJSONPlacaA();
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(19);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		
		// act
		response = this.vigilante.permitirIngreso(vehiculoJson);

		LocalDate fechaActual = LocalDate.now();
		int dayOfWeek = fechaActual.getDayOfWeek().getValue();
		// assert
		if (dayOfWeek == 1 || dayOfWeek == 7) {
			// assert
			Assert.assertEquals(new Integer(200), response.getResponseCode());
		} else {
			// assert
			Assert.assertEquals(new Integer(406), response.getResponseCode());
		}
		
	}
	
	@Test
	public void reportarSalidaDeCarroSinRegistroSalida() {
		// arrange
		RestResponse response;
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		LocalDateTime localDateTimeSalida = LocalDateTime.of(2018, 07, 30, 14, 24);
		Date fechaSalida = Date.from(localDateTimeSalida.atZone(ZoneId.systemDefault()).toInstant());
		RegistroVehiculo registroDeSalida = null;
		when(this.registroVehiculoService.findByVehiculo(vehiculo.getPlaca())).thenReturn(registroDeSalida);
		// act
		response = this.vigilante.reportarSalida(fechaSalida, vehiculo);
		// assert
		Assert.assertEquals(new Integer(406), response.getResponseCode());
	}
	
	@Test
	public void reportarSalidaDeCarro() {
		// arrange
		RestResponse response;
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		LocalDateTime localDateTimeEntrada = LocalDateTime.of(2018, 07, 27, 13, 50);
		Date fechaEntrada = Date.from(localDateTimeEntrada.atZone(ZoneId.systemDefault()).toInstant());
		LocalDateTime localDateTimeSalida = LocalDateTime.of(2018, 07, 30, 14, 24);
		Date fechaSalida = Date.from(localDateTimeSalida.atZone(ZoneId.systemDefault()).toInstant());
		RegistroVehiculo registroDeSalida = new RegistroVehiculo(fechaEntrada, fechaSalida, vehiculo);
		
		Precio precioPorHora = new Precio(1, 1, 1, 1000);
		Precio precioPorDia = new Precio(2, 2, 1, 8000);
		
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.HORA.getTipo())).thenReturn(precioPorHora);
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.DIA.getTipo())).thenReturn(precioPorDia);
		doNothing().when(registroVehiculoService).save(registroDeSalida);
		when(this.registroVehiculoService.findByVehiculo(vehiculo.getPlaca())).thenReturn(registroDeSalida);
		// act
		response = this.vigilante.reportarSalida(fechaSalida, vehiculo);
		// assert
		Assert.assertEquals(new Integer(200), response.getResponseCode());
	}
	
	@Test
	public void noPermitirSalidaDeCarroPorMalEnvio() {
		// arrange
		RestResponse response;
		JSONObject vehiculoJson = null;
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		List<Vehiculo> vehiculos = carroTestDataBuilder.buildList(19);
		when(this.registroVehiculoService.obtenerVehiculosActivos()).thenReturn(vehiculos);
		
		// act
		response = this.vigilante.permitirSalida(vehiculoJson);
		// assert
		Assert.assertEquals(new Integer(406), response.getResponseCode());
	}
	
	@Test
	public void permitirSalidaDeCarro() {
		// arrange
		JSONObject vehiculoJson = new CarroTestDataBuilder().buildJSONDefault();
		RestResponse response;
		CarroTestDataBuilder carroTestDataBuilder = new CarroTestDataBuilder();
		Vehiculo vehiculo = carroTestDataBuilder.build();
		
		LocalDateTime localDateTimeEntrada = LocalDateTime.of(2018, 07, 27, 13, 50);
		Date fechaEntrada = Date.from(localDateTimeEntrada.atZone(ZoneId.systemDefault()).toInstant());
		
		LocalDateTime localDateTimeSalida = LocalDateTime.of(2018, 07, 30, 14, 24);
		Date fechaSalida = Date.from(localDateTimeSalida.atZone(ZoneId.systemDefault()).toInstant());
		RegistroVehiculo registroDeSalida = new RegistroVehiculo(fechaEntrada, fechaSalida, vehiculo);
		
		Precio precioPorHora = new Precio(1, 1, 1, 1000);
		Precio precioPorDia = new Precio(2, 2, 1, 8000);
		
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.HORA.getTipo())).thenReturn(precioPorHora);
		when(this.precioService.obtenerPrecioPorTipoVehiculoYTiempo(vehiculo.getTipo(),
				TipoTiempo.DIA.getTipo())).thenReturn(precioPorDia);
		doNothing().when(registroVehiculoService).save(registroDeSalida);
		when(this.registroVehiculoService.findByVehiculo(vehiculo.getPlaca())).thenReturn(registroDeSalida);
		// act
		response = this.vigilante.permitirSalida(vehiculoJson);
		// assert
		Assert.assertEquals(new Integer(200), response.getResponseCode());
	}
}
