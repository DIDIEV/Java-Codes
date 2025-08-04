package vistaCajero;

import javax.swing.*;

public abstract class Depositos {
    protected Cuentas usuario;
    protected int cantidad;

    public Depositos(Cuentas usuario,int cantidad) {
        this.usuario = usuario;
        this.cantidad = cantidad;
    }

    public abstract void ingresarDinero(int dinero);
}
