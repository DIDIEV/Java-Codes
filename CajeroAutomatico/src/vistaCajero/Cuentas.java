
package vistaCajero;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;

public class Cuentas {

    protected String contrasenia,tarjeta,nombre,apellido,identidad;
    protected int dinero,prestamo,maxPrestamo = dinero * 3;
    protected LocalDate fechaActual = LocalDate.now();
    protected int totalPrestamos = 0;
    
    public Cuentas(String contrasenia, String tarjeta,int dinero,String nombre,String apellido,String identidad ){
        this.contrasenia = contrasenia;
        this.tarjeta = tarjeta;
        this.dinero = dinero;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identidad = identidad;
        
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(int prestamo) {
        this.prestamo = prestamo;
    }

    public int getMaxPrestamo() {
        return maxPrestamo;
    }

    public void setMaxPrestamo(int maxPrestamo) {
        this.maxPrestamo = maxPrestamo;
    }
    
    
}
