package com.ups.buscaminas.buscaminas_app.persistencia;

import java.io.*;

import com.ups.buscaminas.buscaminas_app.modelo.Tablero;

public class GestorArchivos {
	
    public static void guardar(Tablero tablero, String ruta) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(tablero);
        }
    }

    public static Tablero cargar(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (Tablero) ois.readObject();
        }
    }
}
