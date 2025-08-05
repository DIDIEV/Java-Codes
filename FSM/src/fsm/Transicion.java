package fsm;

public class Transicion {
    private String entrada;
    private String salida;
    private Estado estadoDestino;

    public Transicion(String entrada, String salida, Estado estadoDestino) {
        this.entrada = entrada;
        this.salida = salida;
        this.estadoDestino = estadoDestino;
    }

    public String getEntrada() {
        return entrada;
    }

    public String getSalida() {
        return salida;
    }

    public Estado getEstadoDestino() {
        return estadoDestino;
    }
}
