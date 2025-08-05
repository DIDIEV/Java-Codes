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
    private Map<String, Transiciones> transiciones;
    private String entra;  
    
    public Estados(String entra) {
        this.transiciones = new HashMap<>();
        this.entra = entra;
    }
}
