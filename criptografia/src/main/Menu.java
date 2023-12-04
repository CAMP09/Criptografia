package main;

import java.io.PrintStream;

public class Menu {

	// Método estático para mostrar el menú
	public static void mostrarMenu(PrintStream salida) {

		// Imprime las opciones del menú
		salida.println("MENU DE CRIPTOGRAFIA:");
		salida.println("1. Salir del programa");
		salida.println("2. Encriptar frase");
		salida.println("3. Desencriptar frase");
		salida.println("Elija una opción:");
	}
}
