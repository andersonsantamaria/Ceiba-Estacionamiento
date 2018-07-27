package co.com.ceiba.parqueadero.dominio;

public enum TipoVehiculo {
	CARRO, MOTO;
	
	public int getTipo() {
        switch (this) {
            case CARRO:
                return 1;
            case MOTO:
                return 2;
            default:
                throw new AssertionError("Tipo de Vehiculo desconocido " + this);
        }
    }
}
