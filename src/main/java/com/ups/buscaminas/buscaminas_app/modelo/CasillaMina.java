package com.ups.buscaminas.buscaminas_app.modelo;

public class CasillaMina extends Casilla {
	
    private static final long serialVersionUID = 1L;

	@Override
    public boolean esMina() {
        return true;
    }

}