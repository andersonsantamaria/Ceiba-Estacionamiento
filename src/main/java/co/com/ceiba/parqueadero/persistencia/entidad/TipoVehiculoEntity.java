package co.com.ceiba.parqueadero.persistencia.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tipovehiculo")
public class TipoVehiculoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "descripcion", nullable= false)
	private String descripcion;
	
	public TipoVehiculoEntity(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}
	public TipoVehiculoEntity( String descripcion) {		
		this.descripcion = descripcion;
	}
	public TipoVehiculoEntity( ) {			
	}
	
	public int getId() {
		return this.id;
	}
	
}
