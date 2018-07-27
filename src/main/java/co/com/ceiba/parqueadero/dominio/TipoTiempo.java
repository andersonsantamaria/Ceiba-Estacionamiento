package co.com.ceiba.parqueadero.dominio;

public enum TipoTiempo {
	HORA, DIA;
	
	public int getTipo() {
        switch (this) {
            case HORA:
                return 1;
            case DIA:
                return 2;
            default:
                throw new AssertionError("Tipo de Tiempo desconocido " + this);
        }
    }
}
