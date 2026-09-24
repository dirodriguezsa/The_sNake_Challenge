// Paquete por defecto de Java (no requiere declaración de paquete adicional))

/**
 * Representa una posicion de coordenadas (fila, columna) en un tablero de juego.
 * Se define mediente un 'record' de Java 16+, el cual genera automaticamente el constructor,
 * metodos getters (row(), column()), equals(), hashCode() y toString() de manera inmutable.
 */
public record Position(int row, int column) {
    //No requiere código adicional, los campos 'row' y 'colum' son declarados en la firma del record.
}
