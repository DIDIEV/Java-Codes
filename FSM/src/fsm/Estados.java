/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fsm;
import java.util.*;
/**
 *
 * @author Didie
 */
public class Estados {
    private String nombre;
    private Map<String, Estados> transiciones;
    private Map<String,String> salidas;

    public Estados(String nombre) {
        this.nombre = nombre;
        this.transiciones = new HashMap<>();
        this.salidas = new HashMap<>();
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void agregarTransicion(String entrada,Estados siguiente,String salida){
        transiciones.put(entrada,siguiente);
        salidas.put(entrada,salida);
    }
    public Estados siguienteEstado(String entrada){
        return transiciones.get(entrada);
    }
    public String calcularSalida(String entrada){
        return salidas.get(entrada);
    }
    @Override
    public String toString(){
        return "estado: " + nombre;
    }
}
