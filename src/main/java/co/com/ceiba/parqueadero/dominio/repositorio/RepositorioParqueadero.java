package co.com.ceiba.parqueadero.dominio.repositorio;

import java.util.List;

import co.com.ceiba.parqueadero.dominio.Carro;
import co.com.ceiba.parqueadero.dominio.Moto;

public interface RepositorioParqueadero {
	public boolean hayDisponibilidadParaCarro();
	
	public boolean hayDisponibilidadParaMoto();
	
	public List<Carro> getCarros();
	
	public List<Moto> getMotos();
	
	public void setCarros(List<Carro> carros);
	
	public void setMotos(List<Moto> motos);
}
