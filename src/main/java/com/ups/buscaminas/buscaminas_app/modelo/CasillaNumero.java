package com.ups.buscaminas.buscaminas_app.modelo;

public class CasillaNumero extends Casilla {
	
    private static final long serialVersionUID = 1L;
	
    private int minasAlrededor;

    public CasillaNumero(int minasAlrededor) {
        this.minasAlrededor = minasAlrededor;
    }

    @Override
    public boolean esMina() {
        return false;
    }

    public int getMinasAlrededor() {
        return minasAlrededor;
    }
}