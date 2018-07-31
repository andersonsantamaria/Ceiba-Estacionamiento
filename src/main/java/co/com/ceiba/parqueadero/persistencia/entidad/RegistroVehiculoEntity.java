package co.com.ceiba.parqueadero.persistencia.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "registrovehiculo")
public class RegistroVehiculoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "placa" , nullable= false)
	private String vehiculo;
	
	@Column(name ="fechaentrada" , nullable= false )
	private Date fechaEntrada;
	
	@Column(name ="fechasalida" , nullable= true )
	private Date fechaSalida;
	
	@Column(name ="valorPagado" , nullable= true )
	private double valorPagado;
	
	public RegistroVehiculoEntity() {
		//constructor por defecto
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public double getValorPagado() {
		return valorPagado;
	}

	public void setValorPagado(double valorPagado) {
		this.valorPagado = valorPagado;
	}
	
	
	
	
	
}
