package com.ups.buscaminas.buscaminas_app;

import com.ups.buscaminas.buscaminas_app.controlador.JuegoControlador;

/**
 * Clase principal del juego Buscaminas.
 * 
 * Esta clase representa el punto de entrada de la aplicación. Se encarga de 
 * inicializar el controlador del juego y lanzar el flujo principal de ejecución.
 * 
 * Autor: Jorge Rivera
 */
public class App {

    /**
     * Método principal que arranca la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este proyecto).
     */
    public static void main(String[] args) {
        // Se crea una instancia del controlador principal del juego
        JuegoControlador controller = new JuegoControlador();

        // Se inicia la lógica del juego
        controller.iniciar();
    }
}