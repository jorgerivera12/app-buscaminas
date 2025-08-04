package com.ups.buscaminas.buscaminas_app.vista;

import com.ups.buscaminas.buscaminas_app.modelo.Casilla;
import com.ups.buscaminas.buscaminas_app.modelo.CasillaNumero;

public class ConsolaVista {
	
	public void mostrarTablero(Casilla[][] celdas) {
        System.out.println("═══════════════════════════════════════════");
		System.out.print("   ");
		for (int j = 0; j < celdas[0].length; j++) {
			System.out.printf("%-3d", j + 1); // ancho fijo para encabezados
		}
		System.out.println();

		char letraFila = 'A';
		for (int i = 0; i < celdas.length; i++) {
			System.out.print(letraFila++ + "  ");
			for (int j = 0; j < celdas[i].length; j++) {
				Casilla c = celdas[i][j];

				if (c.estaDescubierta()) {
					if (c.esMina()) {
						System.out.printf("%-3s", "✹"); // mina
					} else if (c instanceof CasillaNumero) {
						System.out.printf("%-3d", ((CasillaNumero) c).getMinasAlrededor());
					} else {
						System.out.printf("%-3s", "□"); // vacío descubierto
					}
				} else if (c.estaMarcada()) {
					System.out.printf("%-3s", "⚑"); // marcada
				} else {
					System.out.printf("%-3s", "■"); // oculta
				}
			}
			System.out.println();
		}
        System.out.println("═══════════════════════════════════════════");
		// Leyenda al final
		System.out.println("📌 Leyenda:");
		System.out.println("■ Oculta   □ Descubierta vacía   ✹ Mina");
		System.out.println("⚑ Marcada  1-8 Número de minas vecinas");
	}

	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}
	
    public void mostrarCabecera() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("🎮  BUSCAMINAS - ¡Encuentra las minas! 💣");
    }
}
