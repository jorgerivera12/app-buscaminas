package com.ups.buscaminas.buscaminas_app.servicios;

import java.util.Random;

import com.ups.buscaminas.buscaminas_app.excepciones.CasillaYaDescubiertaException;
import com.ups.buscaminas.buscaminas_app.modelo.Casilla;
import com.ups.buscaminas.buscaminas_app.modelo.CasillaMina;
import com.ups.buscaminas.buscaminas_app.modelo.CasillaNumero;
import com.ups.buscaminas.buscaminas_app.modelo.CasillaVacia;
import com.ups.buscaminas.buscaminas_app.modelo.Tablero;

/**
 * Servicio que contiene la lógica del juego Buscaminas.
 * Encargado de inicializar el tablero, colocar minas, contar minas vecinas
 * y ejecutar acciones sobre las casillas como descubrir o marcar.
 *
 * Autor: Jorge Rivera
 */
public class TableroService {

    /**
     * Inicializa el tablero creando casillas vacías,
     * colocando minas aleatoriamente y asignando números a las casillas adyacentes.
     *
     * @param tablero El tablero que será inicializado.
     */
    public void inicializarTablero(Tablero tablero) {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                tablero.getCeldas()[i][j] = new CasillaVacia();
            }
        }
        colocarMinas(tablero);
        contarMinas(tablero);
    }

    /**
     * Coloca minas aleatoriamente en el tablero sin superponerlas.
     *
     * @param tablero Tablero sobre el cual se colocarán las minas.
     */
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

    /**
     * Asigna números a las casillas indicando cuántas minas hay alrededor.
     *
     * @param tablero Tablero en el que se asignarán los números.
     */
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

    /**
     * Cuenta cuántas minas hay alrededor de una casilla específica.
     *
     * @param tablero Tablero de juego.
     * @param fila    Fila de la casilla.
     * @param columna Columna de la casilla.
     * @return Número de minas vecinas.
     */
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

    /**
     * Descubre la casilla indicada. Si es una casilla vacía, descubre sus vecinos recursivamente.
     *
     * @param tablero Tablero actual del juego.
     * @param fila    Fila de la casilla.
     * @param columna Columna de la casilla.
     * @return true si se pisó una mina; false en caso contrario.
     * @throws CasillaYaDescubiertaException Si la casilla ya fue descubierta anteriormente.
     */
    public boolean descubrir(Tablero tablero, int fila, int columna) throws CasillaYaDescubiertaException {
        if (!enRango(tablero, fila, columna)) return false;
        Casilla casilla = tablero.getCeldas()[fila][columna];

        if (casilla.estaDescubierta()) throw new CasillaYaDescubiertaException("Casilla ya descubierta");

        casilla.setDescubierta(true);

        if (casilla.esMina()) {
            tablero.setJuegoTerminado(true);
            return true;
        }

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

    /**
     * Marca o desmarca una casilla como posible mina.
     *
     * @param tablero Tablero actual.
     * @param fila    Fila de la casilla.
     * @param columna Columna de la casilla.
     */
    public void marcar(Tablero tablero, int fila, int columna) {
        if (enRango(tablero, fila, columna) && !tablero.getCeldas()[fila][columna].estaDescubierta()) {
            tablero.getCeldas()[fila][columna].setMarcada(!tablero.getCeldas()[fila][columna].estaMarcada());
        }
    }

    /**
     * Verifica si el jugador ha ganado el juego.
     * La condición es que todas las casillas no mina estén descubiertas.
     *
     * @param tablero Tablero actual.
     * @return true si el jugador ha ganado, false en caso contrario.
     */
    public boolean ganaste(Tablero tablero) {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Casilla c = tablero.getCeldas()[i][j];
                if (!c.esMina() && !c.estaDescubierta()) return false;
            }
        }
        return true;
    }

    /**
     * Verifica si una posición está dentro de los límites del tablero.
     *
     * @param tablero Tablero actual.
     * @param fila    Fila a verificar.
     * @param columna Columna a verificar.
     * @return true si está dentro del rango, false si no.
     */
    private boolean enRango(Tablero tablero, int fila, int columna) {
        return fila >= 0 && fila < tablero.getFilas() && columna >= 0 && columna < tablero.getColumnas();
    }
}
