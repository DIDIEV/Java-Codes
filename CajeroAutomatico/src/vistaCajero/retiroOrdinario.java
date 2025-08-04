/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistaCajero;

import javax.swing.*;

/**
 *
 * @author DIDIER TORRES
 */
public class retiroOrdinario extends Depositos{

    public retiroOrdinario(Cuentas usuario,int cantidad) {
        super(usuario,cantidad);
    }
    
    @Override
    public void ingresarDinero(int cantidad){
        
        int valorT = this.usuario.dinero + cantidad;
        this.usuario.dinero = valorT;
        JOptionPane.showMessageDialog(null, "Ingreso completado");
        
    }
}
