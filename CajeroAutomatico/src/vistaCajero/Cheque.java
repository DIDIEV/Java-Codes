/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaCajero;

import javax.swing.*;

/**
 *
 * @author estudiantes
 */
public class Cheque extends Depositos{
    private String numeroCheque;

    public Cheque(String numeroCheque, Cuentas usuario,int cantidad) {
        super(usuario,cantidad);
        this.numeroCheque = numeroCheque;
    }
    
    

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }
    
    @Override
    public void ingresarDinero(int cantidad){//para cambiar el valor de dinero de este ususrio
        
        if (this.numeroCheque.length() < 7 ){//valodación de el cheque
            while (this.numeroCheque.length() != 7){
                String numero = JOptionPane.showInputDialog(null,"El número de cheque debe contener 7 caracteres");
                setNumeroCheque(numero);
            }
            int ingreso = this.usuario.dinero + cantidad;
            this.usuario.dinero = ingreso;
            JOptionPane.showMessageDialog(null, "Ingreso completado");
            
        }else {
            int ingreso = this.usuario.dinero + cantidad;
            this.usuario.dinero = ingreso;
            JOptionPane.showMessageDialog(null, "Ingreso completado");
        }
        
    }
}
