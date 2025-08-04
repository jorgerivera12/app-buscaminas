package com.ups.buscaminas.buscaminas_app.vista;

import java.util.Scanner;

import com.ups.buscaminas.buscaminas_app.modelo.Casilla;
import com.ups.buscaminas.buscaminas_app.modelo.CasillaNumero;

/**
 * Clase encargada de gestionar la interfaz visual del juego en consola.
 * Presenta el tablero y los mensajes necesarios para la interacción con el usuario.
 *
 * Autor: Jorge Rivera
 */
public class ConsolaVista {
    
    /**
     * Muestra el tablero en consola, incluyendo encabezados de columnas,
     * letras de filas y símbolos para representar cada tipo de casilla.
     *
     * @param celdas Matriz de casillas que representa el estado actual del tablero.
     */
    public void mostrarTablero(Casilla[][] celdas) {
        System.out.println("═══════════════════════════════════════════");

        // Imprimir encabezados de columnas (números del 1 al 10)
        System.out.print("   ");
        for (int j = 0; j < celdas[0].length; j++) {
            System.out.printf("%-3d", j + 1);
        }
        System.out.println();

        // Imprimir filas del tablero (letras A-J) con contenido por casilla
        char letraFila = 'A';
        for (int i = 0; i < celdas.length; i++) {
            System.out.print(letraFila++ + "  ");
            for (int j = 0; j < celdas[i].length; j++) {
                Casilla c = celdas[i][j];

                if (c.estaDescubierta()) {
                    if (c.esMina()) {
                        System.out.printf("%-3s", "✹"); // Casilla con mina
                    } else if (c instanceof CasillaNumero) {
                        System.out.printf("%-3d", ((CasillaNumero) c).getMinasAlrededor()); // Casilla con número
                    } else {
                        System.out.printf("%-3s", "□"); // Casilla vacía descubierta
                    }
                } else if (c.estaMarcada()) {
                    System.out.printf("%-3s", "⚑"); // Casilla marcada como mina
                } else {
                    System.out.printf("%-3s", "■"); // Casilla oculta
                }
            }
            System.out.println();
        }

        System.out.println("═══════════════════════════════════════════");

        // Mostrar leyenda para interpretación de símbolos
        System.out.println("📌 Leyenda:");
        System.out.println("■ Oculta   □ Descubierta vacía   ✹ Mina");
        System.out.println("⚑ Marcada  1-8 Número de minas vecinas");
    }

    /**
     * Muestra un mensaje simple en consola.
     *
     * @param mensaje Texto que se desea imprimir.
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Muestra la cabecera del juego al iniciar la partida.
     * Contiene el título y decoración en consola.
     */
    public void mostrarCabecera() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("🎮  BUSCAMINAS - ¡Encuentra las minas! 💣");
    }
    
    /**
     * Muestra el menú de opciones para el usuario.
     */
    public void mostrarMenuAcciones() {
        mostrarMensaje(
            "\n✨ ¿Qué deseas hacer?\n" +
            "   👉 [D] Descubrir casilla 🕵️\n" +
            "   👉 [M] Marcar/Desmarcar mina 🚩\n" +
            "   👉 [G] Guardar y salir 💾\n"
        );
    }
    
    

}
