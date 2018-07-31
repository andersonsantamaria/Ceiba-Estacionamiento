package co.com.ceiba.parqueadero.service.excepcion;

public class VigilanteServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public VigilanteServiceException(String message) {
		super(message);
	}
}