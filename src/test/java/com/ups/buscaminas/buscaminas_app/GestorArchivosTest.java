package com.ups.buscaminas.buscaminas_app;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.ups.buscaminas.buscaminas_app.modelo.Tablero;
import com.ups.buscaminas.buscaminas_app.persistencia.GestorArchivos;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GestorArchivosTest {
    private final String ruta = "test_guardado.dat";

    @Test
    void testGuardarYCargarTablero() throws Exception {
        Tablero original = new Tablero();
        GestorArchivos.guardar(original, ruta);

        Tablero cargado = GestorArchivos.cargar(ruta);
        assertNotNull(cargado);
        assertEquals(original.getFilas(), cargado.getFilas());
    }

    @AfterEach
    void cleanup() {
        new File(ruta).delete();
    }
}
