package com.ups.buscaminas.buscaminas_app.modelo;

import java.io.Serializable;

public class Tablero implements Serializable {
	
    private static final long serialVersionUID = 1L;
	private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private final int MINAS = 10;

    private Casilla[][] celdas;
    private boolean juegoTerminado;

    public Tablero() {
        this.celdas = new Casilla[FILAS][COLUMNAS];
        this.juegoTerminado = false;
    }

    public Casilla[][] getCeldas() {
        return celdas;
    }

    public int getFilas() {
        return FILAS;
    }

    public int getColumnas() {
        return COLUMNAS;
    }

    public int getMinas() {
        return MINAS;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }
}