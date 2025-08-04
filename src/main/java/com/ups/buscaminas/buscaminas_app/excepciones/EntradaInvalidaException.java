package com.ups.buscaminas.buscaminas_app.excepciones;


public class EntradaInvalidaException extends Exception {
    private static final long serialVersionUID = 1L;

	public EntradaInvalidaException(String mensaje) {
        super(mensaje);
    }
}