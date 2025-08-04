package com.ups.buscaminas.buscaminas_app.servicios;

import java.util.Random;

import com.ups.buscaminas.buscaminas_app.excepciones.CasillaYaDescubiertaException;
import com.ups.buscaminas.buscaminas_app.modelo.Casilla;
import com.ups.buscaminas.buscaminas_app.modelo.CasillaMina;
import com.ups.buscaminas.buscaminas_app.modelo.CasillaNumero;
import com.ups.buscaminas.buscaminas_app.modelo.CasillaVacia;
import com.ups.buscaminas.buscaminas_app.modelo.Tablero;

public class TableroService {

    public void inicializarTablero(Tablero tablero) {
        // Inicializamos todas como vacías
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                tablero.getCeldas()[i][j] = new CasillaVacia();
            }
        }
        colocarMinas(tablero);
        contarMinas(tablero);
    }

    private void colocarMinas(Tablero tablero) {
        Random rand = new Random();
        int colocadas = 0;
        while (colocadas < tablero.getMinas()) {
            int f = rand.nextInt(tablero.getFilas());
            int c = rand.nextInt(tablero.getColumnas());
            if (!tablero.getCeldas()[f][c].esMina()) {
                tablero.getCeldas()[f][c] = new CasillaMina();
                colocadas++;
            }
        }
    }

    private void contarMinas(Tablero tablero) {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (!tablero.getCeldas()[i][j].esMina()) {
                    int minasAlrededor = contarMinasVecinas(tablero, i, j);
                    if (minasAlrededor > 0) {
                        tablero.getCeldas()[i][j] = new CasillaNumero(minasAlrededor);
                    } else {
                        tablero.getCeldas()[i][j] = new CasillaVacia();
                    }
                }
            }
        }
    }

    private int contarMinasVecinas(Tablero tablero, int fila, int columna) {
        int contador = 0;
        for (int fi = -1; fi <= 1; fi++) {
            for (int co = -1; co <= 1; co++) {
                int ni = fila + fi;
                int nj = columna + co;
                if (enRango(tablero, ni, nj) && tablero.getCeldas()[ni][nj].esMina()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public boolean descubrir(Tablero tablero, int fila, int columna) throws CasillaYaDescubiertaException {
        if (!enRango(tablero, fila, columna)) return false;
        Casilla casilla = tablero.getCeldas()[fila][columna];

        if (casilla.estaDescubierta()) throw new CasillaYaDescubiertaException("Casilla ya descubierta");

        casilla.setDescubierta(true);

        if (casilla.esMina()) {
            tablero.setJuegoTerminado(true);
            return true;
        }

        // Si es CasillaVacia, descubrir vecinos (recursividad)
        if (casilla instanceof CasillaVacia) {
            for (int fi = -1; fi <= 1; fi++) {
                for (int co = -1; co <= 1; co++) {
                    if (!(fi == 0 && co == 0)) {
                        int ni = fila + fi;
                        int nj = columna + co;
                        if (enRango(tablero, ni, nj) && !tablero.getCeldas()[ni][nj].estaDescubierta()) {
                            try {
                                descubrir(tablero, ni, nj);
                            } catch (CasillaYaDescubiertaException ignored) {}
                        }
                    }
                }
            }
        }

        return false;
    }

    public void marcar(Tablero tablero, int fila, int columna) {
        if (enRango(tablero, fila, columna) && !tablero.getCeldas()[fila][columna].estaDescubierta()) {
            tablero.getCeldas()[fila][columna].setMarcada(!tablero.getCeldas()[fila][columna].estaMarcada());
        }
    }

    public boolean ganaste(Tablero tablero) {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Casilla c = tablero.getCeldas()[i][j];
                if (!c.esMina() && !c.estaDescubierta()) return false;
            }
        }
        return true;
    }

    private boolean enRango(Tablero tablero, int fila, int columna) {
        return fila >= 0 && fila < tablero.getFilas() && columna >= 0 && columna < tablero.getColumnas();
    }
}