/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaCajero;

import javax.swing.JOptionPane;

/**
 *
 * @author DIDIER TORRES
 */
public class DevolverPrestamo extends Depositos {
    
    public DevolverPrestamo(Cuentas usuario,int cantidad){
        super(usuario,cantidad);
    }
    public void ingresarDinero(int cantidad){
        
        
        
        if(this.usuario.prestamo == cantidad){
            int valorC = this.usuario.dinero - cantidad;
            this.usuario.dinero = valorC;
            int valorT = this.usuario.prestamo - cantidad;
            this.usuario.prestamo = valorT;
            JOptionPane.showMessageDialog(null, "Pago completado");
        }else if (this.usuario.prestamo < cantidad){
            JOptionPane.showMessageDialog(null, "No puedes ingresar más dinero del que debes");
        }else{
            int valorC = this.usuario.dinero - cantidad;
            this.usuario.dinero = valorC;
            int valorT = this.usuario.prestamo - cantidad;
            this.usuario.prestamo = valorT;
            JOptionPane.showMessageDialog(null, "Deposito completado, tu deuda es ahora de: " + this.usuario.prestamo);
        }
        
        
        
    }
}
