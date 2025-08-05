import java.io.*;
import java.util.*;

/**
 * Clase para generar diagramas visuales de la FSM
 * Puede generar diagramas en formato texto y DOT (Graphviz)
 */
public class DiagramaFSM {
    private FSM fsm;

    /**
     * Constructor del generador de diagramas
     * @param fsm FSM para la cual generar el diagrama
     */
    public DiagramaFSM(FSM fsm) {
        this.fsm = fsm;
    }

    /**
     * Genera un diagrama en formato de texto ASCII
     * @return String con el diagrama en formato texto
     */
    public String generarDiagramaTexto() {
        StringBuilder diagrama = new StringBuilder();

        diagrama.append("╔══════════════════════════════════════════════════════════════╗\n");
        diagrama.append("║                    DIAGRAMA DE LA FSM                       ║\n");
        diagrama.append("╚══════════════════════════════════════════════════════════════╝\n\n");

        // Información general
        diagrama.append("Estados: ").append(Arrays.toString(fsm.getEstados())).append("\n");
        diagrama.append("Entradas: ").append(Arrays.toString(fsm.getEntradas())).append("\n");
        diagrama.append("Salidas: ").append(Arrays.toString(fsm.getSalidas())).append("\n");
        diagrama.append("Estado inicial: ").append(fsm.getEstadoInicial()).append("\n");
        diagrama.append("Estado actual: ").append(fsm.getEstadoActual()).append("\n\n");

        // Diagrama visual simplificado
        diagrama.append("REPRESENTACIÓN VISUAL:\n");
        diagrama.append("═══════════════════════\n\n");

        String[] estados = fsm.getEstados();
        String[] entradas = fsm.getEntradas();

        for (String estado : estados) {
            // Mostrar el estado
            if (estado.equals(fsm.getEstadoInicial())) {
                diagrama.append("→ ");  // Flecha para estado inicial
            } else {
                diagrama.append("  ");
            }

            if (estado.equals(fsm.getEstadoActual())) {
                diagrama.append("⦿ ").append(estado).append(" (ACTUAL)");  // Estado actual
            } else {
                diagrama.append("○ ").append(estado);  // Estado normal
            }
            diagrama.append("\n");

            // Mostrar transiciones desde este estado
            for (String entrada : entradas) {
                if (fsm.existeTransicion(estado, entrada)) {
                    Transicion transicion = fsm.getTransicion(estado, entrada);
                    diagrama.append("    │\n");
                    diagrama.append("    ├─[").append(entrada).append("/").append(transicion.getSalida()).append("]─→ ");
                    diagrama.append(transicion.getEstadoDestino()).append("\n");
                }
            }
            diagrama.append("\n");
        }

        return diagrama.toString();
    }

    /**
     * Genera un diagrama en formato DOT (Graphviz)
     * @return String con el código DOT
     */
    public String generarDiagramaDOT() {
        StringBuilder dot = new StringBuilder();

        dot.append("digraph FSM {\n");
        dot.append("    rankdir=LR;\n");
        dot.append("    size=\"8,5\";\n");
        dot.append("    node [shape = circle];\n\n");

        // Definir nodos
        String[] estados = fsm.getEstados();
        for (String estado : estados) {
            if (estado.equals(fsm.getEstadoInicial())) {
                dot.append("    ").append(estado).append(" [shape = doublecircle, style=filled, fillcolor=lightgreen];\n");
            } else if (estado.equals(fsm.getEstadoActual())) {
                dot.append("    ").append(estado).append(" [style=filled, fillcolor=lightblue];\n");
            } else {
                dot.append("    ").append(estado).append(";\n");
            }
        }

        // Nodo invisible para la flecha de inicio
        dot.append("    start [shape=point];\n");
        dot.append("    start -> ").append(fsm.getEstadoInicial()).append(";\n\n");

        // Definir transiciones
        String[] entradas = fsm.getEntradas();
        for (String estado : estados) {
            for (String entrada : entradas) {
                if (fsm.existeTransicion(estado, entrada)) {
                    Transicion transicion = fsm.getTransicion(estado, entrada);
                    dot.append("    ").append(estado).append(" -> ").append(transicion.getEstadoDestino());
                    dot.append(" [ label = \"").append(entrada).append("/").append(transicion.getSalida()).append("\" ];\n");
                }
            }
        }

        dot.append("}\n");
        return dot.toString();
    }

    /**
     * Genera un diagrama matricial de transiciones
     * @return String con la matriz de transiciones
     */
    public String generarMatrizTransiciones() {
        StringBuilder matriz = new StringBuilder();

        String[] estados = fsm.getEstados();
        String[] entradas = fsm.getEntradas();

        matriz.append("MATRIZ DE TRANSICIONES:\n");
        matriz.append("═════════════════════════\n\n");

        // Encabezado
        matriz.append("Estado\\Entrada");
        for (String entrada : entradas) {
            matriz.append(String.format("%12s", entrada));
        }
        matriz.append("\n");
        matriz.append("─".repeat(15 + entradas.length * 12)).append("\n");

        // Filas de estados
        for (String estado : estados) {
            String marcador = "";
            if (estado.equals(fsm.getEstadoInicial())) marcador += "→";
            if (estado.equals(fsm.getEstadoActual())) marcador += "*";

            matriz.append(String.format("%-15s", estado + marcador));

            for (String entrada : entradas) {
                if (fsm.existeTransicion(estado, entrada)) {
                    Transicion transicion = fsm.getTransicion(estado, entrada);
                    String celda = transicion.getEstadoDestino() + "/" + transicion.getSalida();
                    matriz.append(String.format("%12s", celda));
                } else {
                    matriz.append(String.format("%12s", "─"));
                }
            }
            matriz.append("\n");
        }

        matriz.append("\nLeyenda: → Estado inicial, * Estado actual, ─ Sin transición\n");

        return matriz.toString();
    }

    /**
     * Genera un diagrama de flujo simplificado
     * @return String con el diagrama de flujo
     */
    public String generarDiagramaFlujo() {
        StringBuilder flujo = new StringBuilder();

        flujo.append("DIAGRAMA DE FLUJO:\n");
        flujo.append("═══════════════════\n\n");

        String[] estados = fsm.getEstados();
        String[] entradas = fsm.getEntradas();

        // Crear un mapa de conexiones
        Map<String, List<String>> conexiones = new HashMap<>();

        for (String estado : estados) {
            conexiones.put(estado, new ArrayList<>());
            for (String entrada : entradas) {
                if (fsm.existeTransicion(estado, entrada)) {
                    Transicion transicion = fsm.getTransicion(estado, entrada);
                    String conexion = entrada + "/" + transicion.getSalida() + " → " + transicion.getEstadoDestino();
                    conexiones.get(estado).add(conexion);
                }
            }
        }

        // Generar el flujo
        for (String estado : estados) {
            // Mostrar el estado
            flujo.append("┌─────────────────┐\n");

            String estadoLabel = estado;
            if (estado.equals(fsm.getEstadoInicial())) estadoLabel += " (INICIAL)";
            if (estado.equals(fsm.getEstadoActual())) estadoLabel += " (ACTUAL)";

            flujo.append(String.format("│ %-15s │\n", estadoLabel));
            flujo.append("└─────────────────┘\n");

            // Mostrar transiciones
            List<String> trans = conexiones.get(estado);
            for (int i = 0; i < trans.size(); i++) {
                if (i == trans.size() - 1) {
                    flujo.append("         └─ ").append(trans.get(i)).append("\n");
                } else {
                    flujo.append("         ├─ ").append(trans.get(i)).append("\n");
                }
            }

            if (!trans.isEmpty()) {
                flujo.append("         │\n");
                flujo.append("         ▼\n");
            }
        }

        return flujo.toString();
    }

    /**
     * Guarda el diagrama DOT en un archivo
     * @param nombreArchivo Nombre del archivo (sin extensión)
     * @return true si se guardó exitosamente
     */
    public boolean guardarDiagramaDOT(String nombreArchivo) {
        try {
            FileWriter writer = new FileWriter(nombreArchivo + ".dot");
            writer.write(generarDiagramaDOT());
            writer.close();

            // También crear un archivo con instrucciones
            FileWriter instrucciones = new FileWriter(nombreArchivo + "_instrucciones.txt");
            instrucciones.write("Para generar la imagen del diagrama:\n");
            instrucciones.write("1. Instale Graphviz desde: https://graphviz.org/\n");
            instrucciones.write("2. Ejecute el comando:\n");
            instrucciones.write("   dot -Tpng " + nombreArchivo + ".dot -o " + nombreArchivo + ".png\n");
            instrucciones.write("   o\n");
            instrucciones.write("   dot -Tsvg " + nombreArchivo + ".dot -o " + nombreArchivo + ".svg\n\n");
            instrucciones.write("Formatos disponibles: png, svg, pdf, ps, gif, jpg\n");
            instrucciones.close();

            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Guarda todos los diagramas en archivos de texto
     * @param nombreArchivo Nombre base del archivo
     * @return true si se guardaron exitosamente
     */
    public boolean guardarTodosDiagramas(String nombreArchivo) {
        try {
            // Diagrama completo
            FileWriter writer = new FileWriter(nombreArchivo + "_completo.txt");
            writer.write(generarDiagramaTexto());
            writer.write("\n\n");
            writer.write(generarMatrizTransiciones());
            writer.write("\n\n");
            writer.write(generarDiagramaFlujo());
            writer.close();

            // Archivo DOT
            guardarDiagramaDOT(nombreArchivo);

            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar los archivos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Muestra todos los tipos de diagramas
     */
    public void mostrarTodosDiagramas() {
        System.out.println(generarDiagramaTexto());
        System.out.println("\n" + "═".repeat(80) + "\n");
        System.out.println(generarMatrizTransiciones());
        System.out.println("\n" + "═".repeat(80) + "\n");
        System.out.println(generarDiagramaFlujo());
    }

    /**
     * Genera estadísticas de la FSM
     * @return String con las estadísticas
     */
    public String generarEstadisticas() {
        StringBuilder stats = new StringBuilder();

        String[] estados = fsm.getEstados();
        String[] entradas = fsm.getEntradas();

        int totalTransiciones = 0;
        int estadosConexos = 0;
        Map<String, Integer> transicionesPorEstado = new HashMap<>();

        for (String estado : estados) {
            int transicionesEstado = 0;
            for (String entrada : entradas) {
                if (fsm.existeTransicion(estado, entrada)) {
                    totalTransiciones++;
                    transicionesEstado++;
                }
            }
            transicionesPorEstado.put(estado, transicionesEstado);
            if (transicionesEstado > 0) estadosConexos++;
        }

        stats.append("ESTADÍSTICAS DE LA FSM:\n");
        stats.append("═════════════════════════\n");
        stats.append("Número de estados: ").append(estados.length).append("\n");
        stats.append("Número de entradas: ").append(entradas.length).append("\n");
        stats.append("Número de salidas: ").append(fsm.getSalidas().length).append("\n");
        stats.append("Total de transiciones: ").append(totalTransiciones).append("\n");
        stats.append("Estados con transiciones: ").append(estadosConexos).append("\n");
        stats.append("Porcentaje de conexión: ").append(String.format("%.1f%%",
                (double)totalTransiciones / (estados.length * entradas.length) * 100)).append("\n\n");

        stats.append("Transiciones por estado:\n");
        for (String estado : estados) {
            stats.append("  ").append(estado).append(": ").append(transicionesPorEstado.get(estado)).append("\n");
        }

        return stats.toString();
    }
}