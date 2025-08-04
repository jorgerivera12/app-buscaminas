package com.ups.buscaminas.buscaminas_app.excepciones;

/**
 * Excepción personalizada que se lanza cuando el jugador intenta descubrir
 * una casilla que ya ha sido descubierta previamente.
 * 
 * Permite manejar este caso específico de error de forma controlada dentro
 * de la lógica del juego.
 * 
 * Autor: Jorge Rivera
 */
public class CasillaYaDescubiertaException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructor que permite definir un mensaje personalizado para la excepción.
     * 
     * @param mensaje Mensaje descriptivo del error.
     */
    public CasillaYaDescubiertaException(String mensaje) {
        super(mensaje);
    }
}
