package co.com.ceiba.parqueadero.dominio;

public enum TipoRegistroVehiculo {
	ENTRADA, SALIDA;
	
	public int getTipo() {
        switch (this) {
            case ENTRADA:
                return 1;
            case SALIDA:
                return 2;
            default:
                throw new AssertionError("Tipo de Autorizacion desconocida " + this);
        }
    }
}
