package co.com.ceiba.parqueadero.persistencia.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tiempo")
public class TiempoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "descripcion", nullable= false)
	private String descripcion;
	
	public TiempoEntity(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}
	public TiempoEntity( String descripcion) {		
		this.descripcion = descripcion;
	}
	public TiempoEntity( ) {			
	}
	public int getId() {
		return id;
	}
	public String getDescripcion() {
		return descripcion;
	}
}
