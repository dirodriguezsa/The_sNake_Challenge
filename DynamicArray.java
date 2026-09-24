// Ninguna biblioteca externa es requerida; se utiliza codigo Java puro de la API estandar.

/**
 * Implementación completa de la estructura genérica DynamicArray desde cero (Pregunta 1).
 * Soporta crecimiento dinámico (duplicación de capacidad) cuando el tamaño alcanza la capacidad física.
 * Cumple con todas las restricciones del enunciado: no utiliza librerías de Java como ArrayList o System.arraycopy.
 *
 * @param <T> Tipo de dato genérico de los elementos almacenados en la estructura.
 */
public class DynamicArray<T> {

    // Arreglo nativo interno para almacenar los elementos genéricos
    private T[] data;

    // Número actual de elementos lógicos almacenados en el arreglo (0 <= size <= capacity)
    private int size;

    // Capacidad física total reservada en el arreglo interno
    private int capacity;

    /**
     * Constructor para inicializar el DynamicArray con una capacidad dada.
     *
     * @param initialCapacity Capacidad física inicial deseada (debe ser mayor o igual a 1).
     * @throws IllegalArgumentException Si initialCapacity es menor que 1.
     */
    @SuppressWarnings("unchecked") // Silencia la advertencia del compilador por el casteo de Object[] a T[]
    public DynamicArray(int initialCapacity) {
        // Valida la precondición exigida por el enunciado: initialCapacity >= 1
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("La capacidad inicial debe ser >= 1");
        }

        // Asigna la capacidad física interna inicial
        this.capacity = initialCapacity;

        // Inicializa la cantidad de elementos lógicos en 0
        this.size = 0;

        // Crea el arreglo físico subyacente. En Java no se puede hacer 'new T[n]' directamente por el borrado de tipos,
        // por lo cual se crea un arreglo de Object y se realiza un casteo explícito a (T[])
        this.data = (T[]) new Object[initialCapacity];
    }

    /**
     * Retorna la cantidad lógica actual de elementos almacenados en la estructura.
     * Complejidad: O(1) worst-case.
     *
     * @return Número de elementos presentes (size).
     */
    public int size() {
        return size; // Retorna el valor del atributo interno size
    }

    /**
     * Retorna la capacidad física total reservada actualmente en memoria.
     * Complejidad: O(1) worst-case.
     *
     * @return Capacidad física del arreglo (capacity).
     */
    public int capacity() {
        return capacity; // Retorna el valor del atributo interno capacity
    }

    /**
     * Obtiene el elemento guardado en una posición de índice lógico determinada.
     * Complejidad: O(1) worst-case.
     *
     * @param index Índice del elemento deseado en el rango [0, size - 1].
     * @return El elemento almacenado en la posición solicitada.
     * @throws IndexOutOfBoundsException Si el índice está fuera del rango válido [0, size - 1].
     */
    public T get(int index) {
        // Valida que el índice ingresado esté dentro de los límites válidos de elementos lógicos
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }

        // Retorna el elemento almacenado en la casilla del arreglo físico
        return data[index];
    }

    /**
     * Reemplaza el valor en la posición de índice especificada.
     * Complejidad: O(1) worst-case.
     *
     * @param index Índice del elemento a modificar en el rango [0, size - 1].
     * @param value Nuevo valor a almacenar.
     * @throws IndexOutOfBoundsException Si el índice está fuera del rango válido [0, size - 1].
     */
    public void set(int index, T value) {
        // Valida que el índice ingresado se encuentre dentro de los límites válidos
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }

        // Asigna el nuevo valor en la celda correspondiente del arreglo físico
        data[index] = value;
    }

    /**
     * Agrega un nuevo elemento al final del arreglo dinámico (operación append).
     * Si la cantidad de elementos alcanza la capacidad máxima, se duplica la capacidad física (C_new = 2 * C_old).
     * Complejidad: O(n) en el peor caso (cuando ocurre resize), pero O(1) amortizado.
     *
     * @param value Elemento a insertar al final de la estructura.
     */
    public void append(T value) {
        // Verifica si el arreglo físico está completamente lleno
        if (size == capacity) {
            // Duplica la capacidad actual exigido por la especificación (C_new = 2 * C_old)
            resize(capacity * 2);
        }

        // Almacena el nuevo valor en el primer índice disponible (índice 'size')
        data[size] = value;

        // Incrementa en 1 la cantidad de elementos lógicos
        size++;
    }

    /**
     * Elimina y retorna el último elemento del arreglo dinámico.
     * La capacidad física NO se reduce tras esta operación (restricción explícita del enunciado).
     * Complejidad: O(1) worst-case.
     *
     * @return El último elemento almacenado que fue removido.
     * @throws IllegalStateException Si el arreglo dinámico está vacío (size == 0).
     */
    public T removeLast() {
        // Verifica que la estructura no esté vacía antes de intentar eliminar
        if (size == 0) {
            throw new IllegalStateException("No se puede hacer removeLast sobre una estructura vacía");
        }

        // Decrementa la cantidad de elementos lógicos para liberar la última posición
        size--;

        // Guarda temporalmente el elemento que se va a eliminar
        T removedElement = data[size];

        // Limpia la referencia en el arreglo físico para permitir que el Recolector de Basura (Garbage Collector) libere la memoria
        data[size] = null;

        // Retorna el elemento removido
        return removedElement;
    }

    /**
     * Método auxiliar privado para cambiar la capacidad física del arreglo interno.
     * La copia de datos se hace explícitamente elemento por elemento sin usar System.arraycopy ni Arrays.copyOf.
     * Complejidad: O(n) en tiempo y O(n) en espacio.
     *
     * @param newCapacity La nueva capacidad física deseada.
     */
    @SuppressWarnings("unchecked") // Silencia la advertencia de casteo por el arreglo genérico
    private void resize(int newCapacity) {
        // Asigna la nueva capacidad en la variable de estado interna
        this.capacity = newCapacity;

        // Instancia un nuevo arreglo físico en memoria con la nueva capacidad
        T[] newData = (T[]) new Object[newCapacity];

        // Copia explícita elemento por elemento desde el arreglo antiguo al nuevo (exigencia estricta del quiz)
        for (int i = 0; i < size; i++) {
            newData[i] = data[i]; // Asigna la celda i del viejo arreglo a la celda i del nuevo arreglo
        }

        // Reemplaza la referencia del arreglo interno con la nueva dirección de memoria
        this.data = newData;
    }
}