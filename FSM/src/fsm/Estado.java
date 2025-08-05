package fsm;

import fsm.Transicion;

import java.util.HashMap;
import java.util.Map;

public class Estado {
    private String nombre;
    private Map<String, Transicion> transiciones;  // entrada -> transición

    public Estado(String nombre) {
        this.nombre = nombre;
        this.transiciones = new HashMap<>();
    }

    public void agregarTransicion(String entrada, String salida, Estado destino) {
        Transicion transicion = new Transicion(entrada, salida, destino);
        transiciones.put(entrada, transicion);
    }

    public Estado siguienteEstado(String entrada) {
        Transicion transicion = transiciones.get(entrada);
        if (transicion != null) {
            return transicion.getEstadoDestino();
        }
        return null;
    }

    public String calcularSalida(String entrada) {
        Transicion transicion = transiciones.get(entrada);
        if (transicion != null) {
            return transicion.getSalida();
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Estado{" + "nombre='" + nombre + '\'' + '}';
    }
}
