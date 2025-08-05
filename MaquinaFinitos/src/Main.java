import java.util.Scanner;

/**
 * Clase principal para demostrar el uso de la FSM
 * Proporciona una interfaz interactiva para crear y probar la máquina de estados
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StringBuilder stringFinal = new StringBuilder();

    public static void main(String[] args) {
        System.out.println("=== Máquina de Estados Finita (FSM) ===");
        System.out.println("Bienvenido al simulador de FSM\n");

        try {
            // Crear la FSM
            FSM fsm = crearFSM();

            // Configurar transiciones
            configurarTransiciones(fsm);

            // Mostrar información de la FSM
            System.out.println("\n" + fsm.getInformacion());
            fsm.mostrarTablaTransiciones();

            // Probar la FSM
            probarFSM(fsm);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Mostrar información final antes de cerrar
            mostrarResumenFinal();
            scanner.close();
            System.out.println("¡Programa terminado!");
        }
    }

    /**
     * Crea una nueva FSM solicitando los datos al usuario
     * @return FSM creada
     */
    private static FSM crearFSM() {
        System.out.println("=== Creación de la FSM ===");

        // Solicitar entradas
        System.out.print("Ingrese las entradas separadas por comas: ");
        String[] entradas = obtenerArray("entradas");

        // Solicitar salidas
        System.out.print("Ingrese las salidas separadas por comas: ");
        String[] salidas = obtenerArray("salidas");

        // Solicitar estados
        System.out.print("Ingrese los estados separados por comas: ");
        String[] estados = obtenerArray("estados");

        // Validar que los arrays no estén vacíos
        if (entradas.length == 0 || salidas.length == 0 || estados.length == 0) {
            throw new IllegalArgumentException("Los arrays de entradas, salidas y estados no pueden estar vacíos");
        }

        FSM fsm = new FSM(entradas, salidas, estados);
        System.out.println("FSM creada exitosamente!");
        System.out.println("Estado inicial establecido: " + fsm.getEstadoInicial());

        return fsm;
    }

    /**
     * Obtiene un array de strings del usuario
     * @param tipo Tipo de datos que se están solicitando
     * @return Array de strings
     */
    private static String[] obtenerArray(String tipo) {
        String linea = scanner.nextLine().trim();

        if (linea.isEmpty()) {
            throw new IllegalArgumentException("No se pueden ingresar " + tipo + " vacías");
        }

        String[] array = linea.split(",");
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
            if (array[i].isEmpty()) {
                throw new IllegalArgumentException("No se permiten " + tipo + " vacías");
            }
        }

        return array;
    }

    //pie las transiciones
    private static void configurarTransiciones(FSM fsm) {
        System.out.println("\n=== Configuración de Transiciones ===");
        System.out.println("Ingrese las transiciones (escriba 'fin' para terminar):");
        System.out.println("Formato: estado_origen entrada estado_destino salida");
        System.out.println("Ejemplo: S0 0 S1 A");

        int transicionesAgregadas = 0;

        while (true) {
            System.out.print("Transición " + (transicionesAgregadas + 1) + ": ");
            String linea = scanner.nextLine().trim();

            if (linea.equalsIgnoreCase("fin")) {
                break;
            }

            if (procesarTransicion(fsm, linea)) {
                transicionesAgregadas++;
                System.out.println("✓ Transición agregada exitosamente.");
            }
        }

        if (transicionesAgregadas == 0) {
            System.out.println("⚠ Advertencia: No se agregaron transiciones. La FSM no podrá procesar entradas.");
        } else {
            System.out.println("Se agregaron " + transicionesAgregadas + " transiciones.");
        }
    }

    /**
     * Procesa una línea de transición ingresada por el usuario
     * @param fsm FSM a la que agregar la transición
     * @param linea Línea con la transición
     * @return true si la transición se agregó exitosamente
     */
    private static boolean procesarTransicion(FSM fsm, String linea) {
        String[] partes = linea.split("\\s+");

        if (partes.length != 4) {
            System.out.println("✗ Formato incorrecto. Use: estado_origen entrada estado_destino salida");
            return false;
        }

        try {
            fsm.agregarTransicion(partes[0], partes[1], partes[2], partes[3]);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Permite probar la FSM procesando entradas
     * @param fsm FSM a probar
     */
    private static void probarFSM(FSM fsm) {
        System.out.println("\n=== Prueba de la FSM ===");
        System.out.println("Comandos disponibles:");
        System.out.println("- Ingrese una entrada para procesarla");
        System.out.println("- 'reiniciar': vuelve al estado inicial");
        System.out.println("- 'estado': muestra el estado actual");
        System.out.println("- 'info': muestra información de la FSM");
        System.out.println("- 'tabla': muestra la tabla de transiciones");
        System.out.println("- 'string': muestra el string acumulado");
        System.out.println("- 'limpiar': limpia el string acumulado");
        System.out.println("- 'diagrama': muestra el diagrama de la FSM");
        System.out.println("- 'salir': termina el programa");

        System.out.println("\nEstado inicial: " + fsm.getEstadoInicial());
        System.out.println("Estado actual: " + fsm.getEstadoActual());

        while (true) {
            System.out.print("\n> ");
            String comando = scanner.nextLine().trim();

            if (comando.equalsIgnoreCase("salir")) {
                break;
            }

            if (procesarComando(fsm, comando)) {
                continue;
            }

            // Si no es un comando, intentar procesar como entrada
            try {
                String salidaGenerada = fsm.procesar(comando);
                // Agregar la salida al string final
                stringFinal.append(salidaGenerada);

                System.out.println("Salida: " + salidaGenerada);
                System.out.println("Estado actual: " + fsm.getEstadoActual());
                System.out.println("String acumulado: " + stringFinal.toString());
            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
        }
    }

    /**
     * Procesa comandos especiales del usuario
     * @param fsm FSM sobre la que ejecutar el comando
     * @param comando Comando a procesar
     * @return true si se procesó un comando, false si no era un comando válido
     */
    private static boolean procesarComando(FSM fsm, String comando) {
        switch (comando.toLowerCase()) {
            case "reiniciar":
                fsm.reiniciar();
                // Opcional: reiniciar también el string final
                System.out.print("¿Desea reiniciar también el string acumulado? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                    stringFinal.setLength(0);
                    System.out.println("✓ FSM y string acumulado reiniciados. Estado actual: " + fsm.getEstadoActual());
                } else {
                    System.out.println("✓ FSM reiniciada (string acumulado conservado). Estado actual: " + fsm.getEstadoActual());
                }
                return true;

            case "estado":
                System.out.println("Estado actual: " + fsm.getEstadoActual());
                return true;

            case "info":
                System.out.println(fsm.getInformacion());
                System.out.println("String acumulado actual: \"" + stringFinal.toString() + "\"");
                System.out.println("Longitud del string acumulado: " + stringFinal.length());
                return true;

            case "tabla":
                fsm.mostrarTablaTransiciones();
                return true;

            case "string":
                System.out.println("String acumulado: \"" + stringFinal.toString() + "\"");
                System.out.println("Longitud actual: " + stringFinal.length());
                return true;

            case "limpiar":
                stringFinal.setLength(0);
                System.out.println("✓ String acumulado limpiado.");
                return true;

            case "diagrama":
                mostrarMenuDiagramas(fsm);
                return true;

            case "help":
            case "ayuda":
                mostrarAyuda();
                return true;

            default:
                return false;
        }
    }

    /**
     * Muestra la ayuda con los comandos disponibles
     */
    private static void mostrarAyuda() {
        System.out.println("\nComandos disponibles:");
        System.out.println("- [entrada]: Procesa la entrada especificada");
        System.out.println("- reiniciar: Vuelve al estado inicial");
        System.out.println("- estado: Muestra el estado actual");
        System.out.println("- info: Muestra información completa de la FSM");
        System.out.println("- tabla: Muestra la tabla de transiciones");
        System.out.println("- string: Muestra el string acumulado y su longitud");
        System.out.println("- limpiar: Limpia el string acumulado");
        System.out.println("- diagrama: Muestra opciones de diagramas");
        System.out.println("- ayuda: Muestra esta ayuda");
        System.out.println("- salir: Termina el programa");
    }

    /**
     * Muestra el resumen final con la longitud del string acumulado
     */
    private static void mostrarResumenFinal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== RESUMEN FINAL DE LA EJECUCIÓN ===");
        System.out.println("String final acumulado: \"" + stringFinal.toString() + "\"");
        System.out.println("Longitud total del string final: " + stringFinal.length());

        if (stringFinal.length() > 0) {
            System.out.println("Caracteres únicos utilizados: " + contarCaracteresUnicos());
            System.out.println("Primera salida: \"" + (stringFinal.length() > 0 ? stringFinal.charAt(0) : "N/A") + "\"");
            System.out.println("Última salida: \"" + (stringFinal.length() > 0 ? stringFinal.charAt(stringFinal.length() - 1) : "N/A") + "\"");
        } else {
            System.out.println("No se procesaron entradas durante esta ejecución.");
        }
        System.out.println("=".repeat(50));
    }

    /**
     * Cuenta los caracteres únicos en el string final
     * @return Número de caracteres únicos
     */
    private static int contarCaracteresUnicos() {
        return (int) stringFinal.toString().chars().distinct().count();
    }

    /**
     * Muestra el menú de opciones de diagramas
     * @param fsm FSM para generar diagramas
     */
    private static void mostrarMenuDiagramas(FSM fsm) {
        DiagramaFSM diagrama = new DiagramaFSM(fsm);

        System.out.println("\n=== MENÚ DE DIAGRAMAS ===");
        System.out.println("1. Diagrama de texto ASCII");
        System.out.println("2. Matriz de transiciones");
        System.out.println("3. Diagrama de flujo");
        System.out.println("4. Código DOT (Graphviz)");
        System.out.println("5. Estadísticas de la FSM");
        //System.out.println("6. Mostrar todos los diagramas");
        //System.out.println("7. Guardar diagrama DOT");
        //System.out.println("8. Guardar todos los diagramas");
        System.out.println("0. Volver al menú principal");

        System.out.print("Seleccione una opción: ");
        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1":
                System.out.println("\n" + diagrama.generarDiagramaTexto());
                break;

            case "2":
                System.out.println("\n" + diagrama.generarMatrizTransiciones());
                break;

            case "3":
                System.out.println("\n" + diagrama.generarDiagramaFlujo());
                break;

            case "4":
                System.out.println("\nCódigo DOT (para Graphviz):");
                System.out.println("─".repeat(40));
                System.out.println(diagrama.generarDiagramaDOT());
                break;

            case "5":
                System.out.println("\n" + diagrama.generarEstadisticas());
                break;

            case "6":
                diagrama.mostrarTodosDiagramas();
                break;

            case "7":
                System.out.print("Ingrese el nombre del archivo (sin extensión): ");
                String nombreDot = scanner.nextLine().trim();
                if (diagrama.guardarDiagramaDOT(nombreDot)) {
                    System.out.println("✓ Archivo DOT guardado como: " + nombreDot + ".dot");
                    System.out.println("✓ Instrucciones guardadas como: " + nombreDot + "_instrucciones.txt");
                } else {
                    System.out.println("✗ Error al guardar el archivo DOT");
                }
                break;

            case "8":
                System.out.print("Ingrese el nombre base para los archivos: ");
                String nombreBase = scanner.nextLine().trim();
                if (diagrama.guardarTodosDiagramas(nombreBase)) {
                    System.out.println("✓ Diagramas guardados:");
                    System.out.println("  - " + nombreBase + "_completo.txt");
                    System.out.println("  - " + nombreBase + ".dot");
                    System.out.println("  - " + nombreBase + "_instrucciones.txt");
                } else {
                    System.out.println("✗ Error al guardar los diagramas");
                }
                break;

            case "0":
                return;

            default:
                System.out.println("Opción no válida.");
        }

        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}