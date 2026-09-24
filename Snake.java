// Importación explícita del record Position (ubicado en el mismo paquete o fuente)

/**
 * Representación eficiente de la Serpiente mediante un arreglo circular nativo(Pregunta 3)
 * Garantiza addHead =O(1) amortizado y removeTail = O(1) worst-case.
 * No utiliza la clase DynamicArray ni colecciones de la biblioteca estandar de Java.
 */
public class Snake {
	// Arreglo nativo tipo Object[] para guardar las posiciones físicas de la serpiente
    private Object[] data;

    // Número actual de posiciones que componen la serpiente (longitud lógica k)
    private int size;

    // Capacidad física total reservada actualmente en el arreglo interno
    private int capacity;

    // Índice físico del arreglo que almacena el elemento lógico 0 (corresponde a la COLA / TAIL)
    private int tail;

    /**
     * Constructor que inicializa la Serpiente con una capacidad física inicial.
     * 
     * @param initialCapacity Capacidad inicial deseada (debe ser >= 1).
     * @throws IllegalArgumentException Si la capacidad es menor que 1.
     */
    public Snake(int initialCapacity) {
        // Valida que la capacidad inicial cumpla con la restricción de ser >= 1
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("La capacidad inicial debe ser >= 1");
        }

        // Asigna la capacidad física inicial
        this.capacity = initialCapacity;

        // Inicializa el número de elementos guardados en 0
        this.size = 0;

        // Establece el índice inicial de la cola (tail) en la posición 0 del arreglo
        this.tail = 0;

        // Inicializa el arreglo físico nativo con el tamaño especificado
        this.data = new Object[initialCapacity];
    }

    /**
     * Retorna la longitud lógica actual de la serpiente (size).
     */
    public int size() {
        return size; // Retorna la variable de estado size
    }

    /**
     * Retorna la capacidad física total actual del arreglo.
     */
    public int capacity() {
        return capacity; // Retorna la variable de estado capacity
    }

    /**
     * Obtiene el índice físico real en el arreglo a partir de un índice lógico i.
     * Mapeo modular: phys(i) = (tail + i) mod capacity
     * 
     * @param logicalIndex Índice lógico en el rango [0, size - 1] (0 = COLA, size - 1 = CABEZA).
     * @return El índice físico correspondiente en el arreglo interno.
     */
    private int physicalIndex(int logicalIndex) {
        // Utiliza la fórmula de aritmética modular para mapear el orden lógico al físico
        return (tail + logicalIndex) % capacity;
    }

    /**
     * Retorna el elemento en la posición lógica solicitada.
     * Convención: logicalIndex = 0 -> COLA (TAIL), logicalIndex = size - 1 -> CABEZA (HEAD).
     * 
     * @param logicalIndex Índice en el rango [0, size - 1].
     * @return La posición (Position) almacenada en dicho índice.
     * @throws IndexOutOfBoundsException Si el índice está fuera del rango [0, size - 1].
     */
    public Position get(int logicalIndex) {
        // Valida que el índice lógico pertenezca al rango de posiciones ocupadas
        if (logicalIndex < 0 || logicalIndex >= size) {
            throw new IndexOutOfBoundsException("Índice lógico fuera de rango: " + logicalIndex);
        }

        // Calcula la celda física equivalente
        int phys = physicalIndex(logicalIndex);

        // Retorna el objeto convirtiéndolo de Object a Position
        return (Position) data[phys];
    }

    /**
     * Agrega una nueva cabeza a la serpiente (addHead).
     * Corresponde a insertar en la posición lógica 'size' (extremo derecho).
     * Si size == capacity, ejecuta resize duplicando la capacidad.
     * Complejidad: O(1) amortizado.
     * 
     * @param p Nueva posición que se convertirá en la cabeza de la serpiente.
     */
    public void addHead(Position p) {
        // Verifica si la serpiente ha llenado la capacidad física actual
        if (size == capacity) {
            // Duplica la capacidad si está llena
            resize(capacity * 2);
        }

        // Calcula el índice físico donde debe almacenarse la nueva cabeza (índice lógico 'size')
        int headPhys = physicalIndex(size);

        // Almacena la nueva posición en esa celda física
        data[headPhys] = p;

        // Incrementa la longitud lógica de la serpiente
        size++;
    }

    /**
     * Elimina y retorna la cola actual de la serpiente (removeTail).
     * Corresponde a remover el elemento lógico 0.
     * No desplaza elementos; únicamente avanza el índice físico 'tail' de forma modular.
     * Complejidad: O(1) worst-case.
     * 
     * @return La posición que fue eliminada de la cola.
     * @throws IllegalStateException Si la serpiente no tiene elementos.
     */
    public Position removeTail() {
        // Verifica que la serpiente tenga al menos un elemento
        if (size == 0) {
            throw new IllegalStateException("No se puede remover la cola de una serpiente vacía");
        }

        // Obtiene el elemento ubicado en el índice físico que representa a la cola (tail)
        Position removed = (Position) data[tail];

        // Anula la celda antigua para ayudar al recolector de basura de Java
        data[tail] = null;

        // Avanza el índice de la cola un espacio hacia la derecha de manera circular
        tail = (tail + 1) % capacity;

        // Decrementa la longitud de la serpiente
        size--;

        // Retorna la posición removida
        return removed;
    }

    /**
     * Redimensiona el arreglo interno preservando estrictamente el orden lógico.
     * Desenrolla la estructura circular ordenando la cola en la celda física 0.
     * 
     * @param newCapacity La nueva capacidad deseada.
     */
    private void resize(int newCapacity) {
        // Instancia un nuevo arreglo de tamaño mayor
        Object[] newData = new Object[newCapacity];

        // Copia los elementos del arreglo antiguo al nuevo en orden desde el índice lógico 0 hasta size - 1
        for (int i = 0; i < size; i++) {
            // Lee el elemento i-ésimo lógico desde la posición física antigua
            newData[i] = data[physicalIndex(i)];
        }

        // Reasigna el arreglo interno al nuevo arreglo
        this.data = newData;

        // Reinicia el índice de la cola en 0, ya que los elementos fueron reordenados consecutivamente
        this.tail = 0;

        // Actualiza la capacidad con el nuevo valor
        this.capacity = newCapacity;
    }

    /**
     * Imprime en consola el contenido del arreglo físico celda por celda.
     * Útil para trazar las operaciones requeridas en la Pregunta 3.f.
     */
    public void printPhysicalState() {
        // Imprime corchete inicial
        System.out.print("[");

        // Recorre todas las casillas del arreglo físico (incluyendo celdas vacías/null)
        for (int i = 0; i < capacity; i++) {
            if (data[i] == null) {
                // Muestra un guion para representar casillas vacías en memoria
                System.out.print("_");
            } else {
                // Imprime la posición en formato (fila,columna)
                Position p = (Position) data[i];
                System.out.print("(" + p.row() + "," + p.column() + ")");
            }

            // Agrega una coma separadora si no es la última celda del arreglo
            if (i < capacity - 1) {
                System.out.print(", ");
            }
        }

        // Imprime corchete final
        System.out.println("]");
    }
}
