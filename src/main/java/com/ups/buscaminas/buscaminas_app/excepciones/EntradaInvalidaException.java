package com.ups.buscaminas.buscaminas_app.excepciones;

/**
 * Excepción personalizada utilizada para manejar entradas no válidas
 * ingresadas por el usuario, ya sea en formato incorrecto o fuera de los
 * rangos permitidos.
 * 
 * Esta excepción permite separar la lógica de validación de entradas del flujo
 * principal del juego, facilitando el mantenimiento y la legibilidad del código.
 * 
 * Autor: Jorge Rivera
 */
public class EntradaInvalidaException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor que permite establecer un mensaje personalizado de error.
     * 
     * @param mensaje Mensaje explicativo de la entrada inválida.
     */
    public EntradaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
