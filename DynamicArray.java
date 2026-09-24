// Ninguna biblioteca externa es requerida; se utiliza codigo Java puro de la API estandar.

/**
 * Clase generica que implementa un arreglo dinamico desde cero(pregunta 1).
 * Soporta crecimiento dinámico (duplicación de capacidad) cuando el tamaño alcanza la capacidad
 * 
 * @param <T> Tipo de dato de los elementos almacenados en la estructura.
 */
public class DynamicArray{

	// Arreglo nativo interno para almacenar los elementos genéricos
	private T[] data;

	// Numero actual de elementos logicos almacenados en el arreglo
	private int size;

	// Capacidad fisica total reservada en el arreglo interno
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

        // Inicializa la capacidad física interna
        this.capacity = initialCapacity;

        // Inicializa la cantidad de elementos lógicos en 0
        this.size = 0;

        // Crea el arreglo físico subyacente. En Java no se puede hacer 'new T[n]' directamente,
        // por lo cual se crea un arreglo de Object y se realiza un casteo explícito a (T[])
        this.data = (T[]) new Object[initialCapacity];
    }

    /**
     * Retorna la cantidad lógica actual de elementos almacenados.
     * Complejidad: O(1) worst-case.
     */
    public int size() {
        return size; // Retorna el valor del atributo interno size
    }

    /**
     * Retorna la capacidad física total reservada actualmente.
     * Complejidad: O(1) worst-case.
     */
    public int capacity() {
        return capacity; // Retorna el valor del atributo interno capacity
    }

    /**
     * Obtiene el elemento guardado en una posición índice determinada.
     * 
     * @param index Índice del elemento deseado en el rango [0, size - 1].
     * @return El elemento en la posición solicitada.
     * @throws IndexOutOfBoundsException Si el índice está fuera del rango válido [0, size - 1].
     */
    public T get(int index) {
        // Valida que el índice ingresado esté dentro de los límites válidos de elementos lógicos
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        // Retorna el elemento almacenado en la posición física correspondiente
        return data[index];
    }

    /**
     * Reemplaza el valor en la posición índice especificada.
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
        // Asigna el nuevo valor en la celda correspondiente
        data[index] = value;
    }

    /**
     * Agrega un nuevo elemento al final del arreglo dinámico (operación push/append).
     * Si la cantidad de elementos alcanza la capacidad máxima, se duplica la capacidad.
     * 
     * @param value Elemento a insertar al final de la estructura.
     */
    public void append(T value) {
        // Verifica si la capacidad física está al límite
        if (size == capacity) {
            // Duplica la capacidad actual exigido por la especificación (C_new = 2 * C_old)
            resize(capacity * 2);
        }
        // Almacena el nuevo valor en el primer índice libre (size)
        data[size] = value;
        // Incrementa la cantidad de elementos almacenados
        size++;
    }

    /**
     * Elimina y retorna el último elemento del arreglo dinámico.
     * La capacidad física no se reduce tras esta operación (exigencia del enunciado).
     * 
     * @return El último elemento almacenado que fue removido.
     * @throws IllegalStateException Si el arreglo está vacío.
     */
    public T removeLast() {
        // Verifica que la estructura no esté vacía antes de intentar eliminar
        if (size == 0) {
            throw new IllegalStateException("No se puede hacer removeLast sobre una estructura vacía");
        }
        // Decrementa la cantidad de elementos para ignorar el último valor
        size--;
        // Guarda temporalmente el elemento a eliminar
        T value = data[size];
        // Limpia la referencia en la celda para permitir la recolección de basura de Java (Garbage Collection)
        data[size] = null;
        // Retorna el elemento eliminado
        return value;
    }

    /**
     * Método auxiliar privado para cambiar la capacidad física del arreglo interno.
     * La copia se hace elemento por elemento sin usar System.arraycopy ni Arrays.copyOf.
     * 
     * @param newCapacity La nueva capacidad física deseada.
     */
    @SuppressWarnings("unchecked") // Silencia la advertencia de casteo por arreglo genérico
    private void resize(int newCapacity) {
        // Asigna la nueva capacidad en la variable de estado interna
        this.capacity = newCapacity;

        // Instancia un nuevo arreglo físico con la nueva capacidad
        T[] newData = (T[]) new Object[newCapacity];

        // Copia explícita elemento por elemento desde el arreglo antiguo al nuevo (exigencia estricta del enunciado)
        for (int i = 0; i < size; i++) {
            newData[i] = data[i]; // Asigna la celda i del viejo arreglo a la celda i del nuevo arreglo
        }

        // Reemplaza la referencia del arreglo interno con el nuevo arreglo de mayor capacidad
        this.data = newData;
    }
}
