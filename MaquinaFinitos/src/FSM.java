import java.util.*;

/**
 * Clase que implementa una Máquina de Estados Finita (FSM)
 * Permite definir estados, entradas, salidas y transiciones
 */
public class FSM {
    private String[] entradas;
    private String[] salidas;
    private String[] estados;
    private Map<String, Map<String, Transicion>> tablaTransiciones;
    private String estadoActual;
    private String estadoInicial;

    /**
     * Constructor de la FSM
     * @param entradas Array de entradas válidas
     * @param salidas Array de salidas válidas
     * @param estados Array de estados válidos
     */
    public FSM(String[] entradas, String[] salidas, String[] estados) {
        this.entradas = entradas.clone();
        this.salidas = salidas.clone();
        this.estados = estados.clone();
        this.tablaTransiciones = new HashMap<>();

        // Inicializar tabla de transiciones
        for (String estado : estados) {
            tablaTransiciones.put(estado, new HashMap<>());
        }

        // Establecer el primer estado como estado inicial
        if (estados.length > 0) {
            this.estadoInicial = estados[0];
            this.estadoActual = estadoInicial;
        }
    }

    /**
     * Agrega una transición a la FSM
     * @param estadoOrigen Estado desde el cual se hace la transición
     * @param entrada Entrada que activa la transición
     * @param estadoDestino Estado al que se transiciona
     * @param salida Salida generada por la transición
     * @throws IllegalArgumentException Si algún parámetro no es válido
     */
    public void agregarTransicion(String estadoOrigen, String entrada, String estadoDestino, String salida) {
        if (!Arrays.asList(estados).contains(estadoOrigen)) {
            throw new IllegalArgumentException("Estado origen no válido: " + estadoOrigen);
        }
        if (!Arrays.asList(estados).contains(estadoDestino)) {
            throw new IllegalArgumentException("Estado destino no válido: " + estadoDestino);
        }
        if (!Arrays.asList(entradas).contains(entrada)) {
            throw new IllegalArgumentException("Entrada no válida: " + entrada);
        }
        if (!Arrays.asList(salidas).contains(salida)) {
            throw new IllegalArgumentException("Salida no válida: " + salida);
        }

        tablaTransiciones.get(estadoOrigen).put(entrada, new Transicion(estadoDestino, salida));
    }

    /**
     * Procesa una entrada en la FSM
     * @param entrada Entrada a procesar
     * @return Salida generada por la transición
     * @throws IllegalArgumentException Si la entrada no es válida
     * @throws IllegalStateException Si no existe transición para la entrada
     */
    public String procesar(String entrada) {
        if (!Arrays.asList(entradas).contains(entrada)) {
            throw new IllegalArgumentException("Entrada no válida: " + entrada);
        }

        Map<String, Transicion> transicionesEstadoActual = tablaTransiciones.get(estadoActual);

        if (!transicionesEstadoActual.containsKey(entrada)) {
            throw new IllegalStateException("No hay transición definida desde " + estadoActual + " con entrada " + entrada);
        }

        Transicion transicion = transicionesEstadoActual.get(entrada);
        estadoActual = transicion.getEstadoDestino();

        return transicion.getSalida();
    }

    /**
     * Procesa una secuencia de entradas
     * @param secuenciaEntradas Array de entradas a procesar
     * @return Lista de salidas generadas
     */
    public List<String> procesarSecuencia(String[] secuenciaEntradas) {
        List<String> salidas = new ArrayList<>();

        for (String entrada : secuenciaEntradas) {
            salidas.add(procesar(entrada));
        }

        return salidas;
    }

    /**
     * Reinicia la FSM al estado inicial
     */
    public void reiniciar() {
        estadoActual = estadoInicial;
    }

    /**
     * Establece un nuevo estado inicial
     * @param estadoInicial Nuevo estado inicial
     * @throws IllegalArgumentException Si el estado no es válido
     */
    public void setEstadoInicial(String estadoInicial) {
        if (!Arrays.asList(estados).contains(estadoInicial)) {
            throw new IllegalArgumentException("Estado inicial no válido: " + estadoInicial);
        }
        this.estadoInicial = estadoInicial;
        this.estadoActual = estadoInicial;
    }

    /**
     * Verifica si existe una transición para un estado y entrada dados
     * @param estado Estado a verificar
     * @param entrada Entrada a verificar
     * @return true si existe la transición, false en caso contrario
     */
    public boolean existeTransicion(String estado, String entrada) {
        return tablaTransiciones.containsKey(estado) &&
                tablaTransiciones.get(estado).containsKey(entrada);
    }

    /**
     * Obtiene la transición para un estado y entrada dados
     * @param estado Estado origen
     * @param entrada Entrada
     * @return Transición correspondiente o null si no existe
     */
    public Transicion getTransicion(String estado, String entrada) {
        if (existeTransicion(estado, entrada)) {
            return tablaTransiciones.get(estado).get(entrada);
        }
        return null;
    }

    // Getters
    public String getEstadoActual() {
        return estadoActual;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public String[] getEntradas() {
        return entradas.clone();
    }

    public String[] getSalidas() {
        return salidas.clone();
    }

    public String[] getEstados() {
        return estados.clone();
    }

    /**
     * Muestra la tabla de transiciones en formato legible
     */
    public void mostrarTablaTransiciones() {
        System.out.println("Tabla de Transiciones:");
        System.out.println("Estado Actual | Entrada | Estado Siguiente | Salida");
        System.out.println("--------------|---------|------------------|-------");

        for (String estado : estados) {
            Map<String, Transicion> transiciones = tablaTransiciones.get(estado);
            for (Map.Entry<String, Transicion> entry : transiciones.entrySet()) {
                String entrada = entry.getKey();
                Transicion transicion = entry.getValue();
                System.out.printf("%-13s | %-7s | %-16s | %-6s%n",
                        estado, entrada, transicion.getEstadoDestino(), transicion.getSalida());
            }
        }
    }

    /**
     * Obtiene información completa sobre la FSM
     * @return String con la información de la FSM
     */
    public String getInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("=== Información de la FSM ===\n");
        info.append("Estados: ").append(Arrays.toString(estados)).append("\n");
        info.append("Entradas: ").append(Arrays.toString(entradas)).append("\n");
        info.append("Salidas: ").append(Arrays.toString(salidas)).append("\n");
        info.append("Estado inicial: ").append(estadoInicial).append("\n");
        info.append("Estado actual: ").append(estadoActual).append("\n");
        info.append("Número de transiciones: ").append(contarTransiciones()).append("\n");
        return info.toString();
    }

    /**
     * Cuenta el número total de transiciones definidas
     * @return Número de transiciones
     */
    private int contarTransiciones() {
        int total = 0;
        for (Map<String, Transicion> transiciones : tablaTransiciones.values()) {
            total += transiciones.size();
        }
        return total;
    }
}