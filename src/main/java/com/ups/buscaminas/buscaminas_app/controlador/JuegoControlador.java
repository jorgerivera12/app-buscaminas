package com.ups.buscaminas.buscaminas_app.controlador;

import java.io.File;
import java.util.Scanner;

import com.ups.buscaminas.buscaminas_app.excepciones.CasillaYaDescubiertaException;
import com.ups.buscaminas.buscaminas_app.excepciones.EntradaInvalidaException;
import com.ups.buscaminas.buscaminas_app.modelo.Tablero;
import com.ups.buscaminas.buscaminas_app.persistencia.GestorArchivos;
import com.ups.buscaminas.buscaminas_app.servicios.TableroService;
import com.ups.buscaminas.buscaminas_app.vista.ConsolaVista;

/**
 * Controlador principal del juego Buscaminas.
 * 
 * Se encarga de gestionar la lógica de interacción entre la vista (Consola),
 * el modelo (Tablero y Casillas) y los servicios (TableroService).
 * 
 * Controla el ciclo de juego, entradas del usuario, persistencia y finalización.
 * 
 * Autor: Jorge Rivera
 */
public class JuegoControlador {
    
    private Tablero tablero;
    private TableroService tableroService;
    private ConsolaVista vista;
    private Scanner sc;

    /**
     * Constructor del controlador que inicializa componentes del juego.
     */
    public JuegoControlador() {
        this.vista = new ConsolaVista();
        this.sc = new Scanner(System.in);
        this.tableroService = new TableroService();
    }

    /**
     * Inicia el juego: carga partida si existe, muestra menú e itera mientras
     * el juego no haya terminado.
     */
    public void iniciar() {
        vista.mostrarCabecera(); 
        cargarPartida();

        if (tablero.getCeldas()[0][0] == null) {
            tableroService.inicializarTablero(tablero);
        }

        while (!tablero.isJuegoTerminado() && !tableroService.ganaste(tablero)) {
            vista.mostrarTablero(tablero.getCeldas());
            vista.mostrarMenuAcciones();
            char accion = leerAccionUsuario();

            if (accion == 'G') {
                guardarPartida();
                vista.mostrarMensaje("💾 Partida guardada exitosamente. ¡Hasta pronto!");
                return;
            }

            procesarAccion(accion);
        }

        if (tableroService.ganaste(tablero)) {
            vista.mostrarMensaje("🎉 ¡Felicidades! Has ganado.");
        }
    }

    /**
     * Solicita al usuario una opción válida (D, M, G).
     * 
     * @return Acción validada en forma de carácter.
     */
    private char leerAccionUsuario() {
        while (true) {
            String opcion = sc.nextLine().toUpperCase().trim();
            if (opcion.isEmpty()) {
                vista.mostrarMensaje("\n⚠️ Debes ingresar una opción (D/M/G)\n");
                continue;
            }
            char accion = opcion.charAt(0);
            if (accion == 'D' || accion == 'M' || accion == 'G') return accion;

            vista.mostrarMensaje("\n⚠️ Opción inválida.\n");
        }
    }

    /**
     * Procesa la acción del usuario: descubrir o marcar una casilla.
     * 
     * @param accion Caracter correspondiente a la acción.
     */
    private void procesarAccion(char accion) {
        try {
            int fila, columna;
            String entrada = pedirCoordenada();

            char letraFila = entrada.charAt(0);
            fila = letraFila - 'A';
            columna = Integer.parseInt(entrada.substring(1)) - 1;

            if (accion == 'D') {
                boolean mina = tableroService.descubrir(tablero, fila, columna);
                if (mina) {
                    vista.mostrarTablero(tablero.getCeldas());
                    vista.mostrarMensaje("\n💥 ¡Pisaste una mina! Has perdido. 😵\n");
                    tablero.setJuegoTerminado(true);
                    new File("partida.dat").delete(); // Eliminar archivo guardado
                }
            } else if (accion == 'M') {
                tableroService.marcar(tablero, fila, columna);
            }

        } catch (EntradaInvalidaException | CasillaYaDescubiertaException | NumberFormatException e) {
            vista.mostrarMensaje("❌ " + e.getMessage());
        }
    }

    /**
     * Solicita al usuario una coordenada válida del tablero.
     * 
     * @return String con la coordenada (ej: A5).
     * @throws EntradaInvalidaException si la entrada no es válida.
     */
    private String pedirCoordenada() throws EntradaInvalidaException {
        vista.mostrarMensaje("📍 Escribe una coordenada para jugar (ej. A5 → fila A, columna 5):");
        System.out.print("👉 Coordenada: ");
        String entrada = sc.nextLine().toUpperCase().trim();

        if (entrada.length() < 2) {
            throw new EntradaInvalidaException("Entrada demasiado corta. Ejemplo válido: A5");
        }

        char letraFila = entrada.charAt(0);
        if (letraFila < 'A' || letraFila > 'J') {
            throw new EntradaInvalidaException("Fila inválida. Usa letras de A a J.");
        }

        try {
            int columna = Integer.parseInt(entrada.substring(1));
            if (columna < 1 || columna > tablero.getColumnas()) {
                throw new EntradaInvalidaException("Columna fuera de rango. Usa del 1 al 10.");
            }
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Columna inválida. Debe ser un número.");
        }

        return entrada;
    }

    /**
     * Carga una partida guardada si existe, o crea un nuevo tablero.
     */
    private void cargarPartida() {
        File f = new File("partida.dat");
        if (f.exists()) {
            vista.mostrarMensaje("\n💾 Se encontró una partida guardada.");
            vista.mostrarMensaje("¿Deseas continuar desde donde te quedaste? (S = Sí / N = No): ");
            char op = sc.nextLine().toUpperCase().charAt(0);
            if (op == 'S') {
                try {
                    tablero = GestorArchivos.cargar("partida.dat");
                    return;
                } catch (Exception ignored) {}
            }
        }
        tablero = new Tablero();
    }

    /**
     * Guarda la partida actual en un archivo local.
     */
    private void guardarPartida() {
        try {
            GestorArchivos.guardar(tablero, "partida.dat");
        } catch (Exception e) {
            vista.mostrarMensaje("❌ Error: No se pudo guardar la partida. Inténtalo nuevamente.");
        }
    }
}
