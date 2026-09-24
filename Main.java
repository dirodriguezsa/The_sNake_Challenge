// Importación de clases nativas de Java necesarias para la ejecución y salida en pantalla
import java.io.PrintStream;

/**
 * Clase principal que ejecuta y simula la traza exacta requerida en la Pregunta 3.f del Quiz 03.
 * Muestra el estado físico del arreglo, tamaño, capacidad e índices tras cada movimiento N_1 a N_6.
 */
public class Main {

    /**
     * Punto de entrada principal para la ejecución del programa Java.
     * 
     * @param args Argumentos de la línea de comandos (no utilizados en esta simulación).
     */
	
    public static void main(String[] args) {

        // Crea una referencia hacia la salida estándar en consola
        PrintStream out = System.out;

        // Encabezado de la simulación
        out.println("=== SIMULACIÓN DE LA TRAZA - PREGUNTA 3.f ===");

        // Estado Inicial de la Serpiente
        // Inicialmente: size = 2, capacity = 2
        Snake snake = new Snake(2);

        // Agrega la cola inicial (A) y la cabeza inicial (B)
        Position A = new Position(0, 0); // Posición inicial A (Cola)
        Position B = new Position(0, 1); // Posición inicial B (Cabeza)

        // Asigna A como cola y B como cabeza usando addHead
        snake.addHead(A); // i=0 lógico (Cola)
        snake.addHead(B); // i=1 lógico (Cabeza)

        // Imprime el estado inicial
        out.println("\n--- ESTADO INICIAL ---");
        printStepInfo(snake, "Inicio con A y B", false);

        // N_1: No come (Avanza a C)
        // Secuencia lógica de movimiento sin comer: addHead(C) seguido de removeTail()
        out.println("\n--- MOVIMIENTO N_1 (No come) ---");
        // addHead(C): Como size=2 y capacity=2, el paso addHead provocará un crecimiento transitorio
        // de tamaño a 3 y por ende un RESIZE a capacidad 4 antes del removeTail.
        snake.addHead(new Position(0, 2)); // Agrega la nueva cabeza C
        snake.removeTail();                 // Remueve la cola anterior A
        printStepInfo(snake, "N_1 (No come -> C)", true); // Ocurrió resize durante addHead

        // N_2: Come (Avanza a D)
        // Secuencia lógica de movimiento al comer: addHead(D) sin removeTail
        out.println("\n--- MOVIMIENTO N_2 (Come) ---");
        snake.addHead(new Position(0, 3)); // Agrega la nueva cabeza D
        printStepInfo(snake, "N_2 (Come -> D)", false); // No ocurre resize (size=3 <= capacity=4)

        // N_3: No come (Avanza a E)
        out.println("\n--- MOVIMIENTO N_3 (No come) ---");
        snake.addHead(new Position(0, 4)); // Agrega la nueva cabeza E (transitoriamente size=4, capacity=4)
        snake.removeTail();                 // Remueve la cola B
        printStepInfo(snake, "N_3 (No come -> E)", false); // No ocurre resize (size llegó a 4 pero no superó capacity=4)

        // N_4: No come (Avanza a F)
        out.println("\n--- MOVIMIENTO N_4 (No come) ---");
        snake.addHead(new Position(0, 5)); // Agrega F
        snake.removeTail();                 // Remueve C
        printStepInfo(snake, "N_4 (No come -> F)", false);

        // N_5: Come (Avanza a G)
        out.println("\n--- MOVIMIENTO N_5 (Come) ---");
        // Antes de este addHead, size=3, capacity=4. Al agregar la nueva cabeza G,
        // size pasa a ser 4, ocupando totalmente la capacidad 4 sin requerir resize aún.
        snake.addHead(new Position(0, 6)); // Agrega la nueva cabeza G
        printStepInfo(snake, "N_5 (Come -> G)", false);

        // N_6: No come (Avanza a H)
        out.println("\n--- MOVIMIENTO N_6 (No come) ---");
        // Antes de addHead, size=4, capacity=4. Al hacer addHead(H), la Serpiente intenta pasar
        // a size=5 > capacity=4. Esto DISPARA UN RESIZE a capacidad 8.
        // Posterior a ello, se ejecuta removeTail() para mantener el movimiento sin comer.
        snake.addHead(new Position(0, 7)); // Agrega H (dispara RESIZE a capacity=8)
        snake.removeTail();                 // Remueve D
        printStepInfo(snake, "N_6 (No come -> H)", true); // Ocurrió resize durante addHead
    }

    /**
     * Método auxiliar para imprimir los detalles de las variables de estado tras un movimiento.
     * 
     * @param snake Estructura Snake a inspeccionar.
     * @param label Descripción de la operación realizada.
     * @param resized Indica si durante ese movimiento ocurrió un evento de redimensionamiento (resize).
     */
    private static void printStepInfo(Snake snake, String label, boolean resized) {
        // Imprime el nombre de la operación efectuada
        System.out.println("Operación: " + label);

        // Imprime si ocurrió o no un resize
        System.out.println("¿Resize efectuado?: " + (resized ? "SÍ" : "NO"));

        // Imprime el tamaño lógico actual
        System.out.println("size = " + snake.size());

        // Imprime la capacidad física actual
        System.out.println("capacity = " + snake.capacity());

        // Imprime el contenido completo del arreglo físico interno
        System.out.print("Estado del arreglo físico: ");
        snake.printPhysicalState();
    }
}