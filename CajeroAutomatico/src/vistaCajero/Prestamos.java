
package vistaCajero;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class Prestamos extends Depositos {
    private int numeroPrestamo;
    private String telefono,celular;
    private LocalDate fechaPrestamo = this.usuario.fechaActual;
    private int dia = fechaPrestamo.getDayOfMonth();
    
    public Prestamos(Cuentas usuario,int cantidad){
        super(usuario,cantidad);
    }
    @Override
    public void ingresarDinero(int cantidad){
        
        int valorT = this.usuario.dinero + cantidad;
        this.usuario.dinero = valorT;
        this.usuario.prestamo = cantidad;
    }

    public int getNumeroPrestamo() {
        return numeroPrestamo;
    }

    public void setNumeroPrestamo(int numeroPrestamo) {
        this.numeroPrestamo = numeroPrestamo;
    }
    
}
