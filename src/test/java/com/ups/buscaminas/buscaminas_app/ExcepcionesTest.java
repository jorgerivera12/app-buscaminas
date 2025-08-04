package com.ups.buscaminas.buscaminas_app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ups.buscaminas.buscaminas_app.excepciones.EntradaInvalidaException;
import com.ups.buscaminas.buscaminas_app.excepciones.CasillaYaDescubiertaException;

public class ExcepcionesTest {
	
	@Test
    void testEntradaInvalidaException_mensaje() {
        EntradaInvalidaException ex = new EntradaInvalidaException("Mensaje prueba");
        assertEquals("Mensaje prueba", ex.getMessage());
    }

    @Test
    void testCasillaYaDescubiertaException_mensaje() {
        CasillaYaDescubiertaException ex = new CasillaYaDescubiertaException("Casilla ya descubierta");
        assertEquals("Casilla ya descubierta", ex.getMessage());
    }
}
