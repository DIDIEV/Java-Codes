/**
 * Clase que representa una transición en la FSM
 * Contiene el estado destino y la salida asociada a la transición
 */
public class Transicion {
    private String estadoDestino;
    private String salida;

    /**
     * Constructor de la transición
     * @param estadoDestino Estado al que se transiciona
     * @param salida Salida generada por la transición
     */
    public Transicion(String estadoDestino, String salida) {
        this.estadoDestino = estadoDestino;
        this.salida = salida;
    }

    /**
     * Obtiene el estado destino de la transición
     * @return Estado destino
     */
    public String getEstadoDestino() {
        return estadoDestino;
    }

    /**
     * Obtiene la salida de la transición
     * @return Salida de la transición
     */
    public String getSalida() {
        return salida;
    }

    /**
     * Establece el estado destino
     * @param estadoDestino Nuevo estado destino
     */
    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    /**
     * Establece la salida
     * @param salida Nueva salida
     */
    public void setSalida(String salida) {
        this.salida = salida;
    }

    @Override
    public String toString() {
        return "Transicion{" +
                "estadoDestino='" + estadoDestino + '\'' +
                ", salida='" + salida + '\'' +
                '}';
    }
}