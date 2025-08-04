import vistaCajero.*;

public class Main {
    public static void main(String[] args) {

        Cuentas usuario = new Cuentas("1522","1522",5000000,null,null,null); //crea el usuario principal con tarjeta y contraseña por defecto 1522
        
        PantallaInicio inicio = new PantallaInicio(usuario);                 //Crea una nueva Pantalla
        
        inicio.setVisible(true);                                             //Inicia el programa   
        inicio.setLocationRelativeTo(null);                                  //Lo deja en el centro de la pantalla   
        
        
    }
    
}
