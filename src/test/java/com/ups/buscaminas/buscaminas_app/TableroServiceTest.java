package com.ups.buscaminas.buscaminas_app;

import com.ups.buscaminas.buscaminas_app.modelo.Tablero;
import com.ups.buscaminas.buscaminas_app.servicios.TableroService;
import com.ups.buscaminas.buscaminas_app.excepciones.CasillaYaDescubiertaException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableroServiceTest {
    private TableroService service;
    private Tablero tablero;

    @BeforeEach
    void setup() {
        service = new TableroService();
        tablero = new Tablero();
        service.inicializarTablero(tablero);
    }

    @Test
    void testInicializarTablero_debeTenerDiezMinas() {
        int contador = 0;
        for (var fila : tablero.getCeldas()) {
            for (var c : fila) {
                if (c.esMina()) contador++;
            }
        }
        assertEquals(10, contador, "El tablero debe tener exactamente 10 minas");
    }

    @Test
    void testMarcarYDesmarcarCasilla() {
        service.marcar(tablero, 0, 0);
        assertTrue(tablero.getCeldas()[0][0].estaMarcada());
        service.marcar(tablero, 0, 0);
        assertFalse(tablero.getCeldas()[0][0].estaMarcada());
    }

    @Test
    void testDescubrirCasillaYaDescubierta_lanzaExcepcion() throws Exception {
        service.descubrir(tablero, 0, 0);
        assertThrows(CasillaYaDescubiertaException.class, () -> {
            service.descubrir(tablero, 0, 0);
        });
    }

    @Test
    void testGanaste_retornaTrueSiTodasNoMinasEstanDescubiertas() throws Exception {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (!tablero.getCeldas()[i][j].esMina()) {
                    tablero.getCeldas()[i][j].setDescubierta(true);
                }
            }
        }
        assertTrue(service.ganaste(tablero));
    }
}
