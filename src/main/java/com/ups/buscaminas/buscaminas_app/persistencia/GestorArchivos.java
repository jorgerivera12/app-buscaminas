package com.ups.buscaminas.buscaminas_app.persistencia;

import java.io.*;

import com.ups.buscaminas.buscaminas_app.modelo.Tablero;

/**
 * Clase responsable de la persistencia del juego Buscaminas.
 * Permite guardar y cargar el estado del tablero utilizando
 * serialización de objetos.
 *
 * Esta clase facilita la recuperación de partidas guardadas
 * y la continuidad del juego desde el punto donde el usuario lo dejó.
 * 
 * Autor: Jorge Rivera
 */
public class GestorArchivos {

    /**
     * Guarda el estado actual del tablero en un archivo.
     *
     * @param tablero Objeto Tablero que representa el estado del juego.
     * @param ruta    Ruta del archivo donde se guardará el tablero.
     * @throws IOException Si ocurre un error durante la escritura del archivo.
     */
    public static void guardar(Tablero tablero, String ruta) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(tablero);
        }
    }

    /**
     * Carga un tablero desde un archivo previamente guardado.
     *
     * @param ruta Ruta del archivo que contiene el estado serializado del tablero.
     * @return Objeto Tablero cargado desde el archivo.
     * @throws IOException            Si ocurre un error durante la lectura del archivo.
     * @throws ClassNotFoundException Si la clase del objeto serializado no se encuentra.
     */
    public static Tablero cargar(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (Tablero) ois.readObject();
        }
    }
}
