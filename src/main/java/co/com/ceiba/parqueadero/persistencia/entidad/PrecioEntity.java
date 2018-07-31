package co.com.ceiba.parqueadero.persistencia.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "precio")
public class PrecioEntity {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipovehiculo_id" ,nullable = false)
	private TipoVehiculoEntity tipoVehiculo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tiempo_id" ,nullable = false)
	private TiempoEntity tiempo;
	
	@Column(name= "valor" , nullable = false)
	private double valor;
	
	
	
	public PrecioEntity(int id, TipoVehiculoEntity tipoVehiculo, TiempoEntity tiempo, double valor) {
		super();
		this.id = id;
		this.tipoVehiculo = tipoVehiculo;
		this.tiempo = tiempo;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public TipoVehiculoEntity getTipoVehiculo() {
		return tipoVehiculo;
	}

	public TiempoEntity getTiempo() {
		return tiempo;
	}

	public double getValor() {
		return valor;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTipoVehiculo(TipoVehiculoEntity tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public void setTiempo(TiempoEntity tiempo) {
		this.tiempo = tiempo;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
}
