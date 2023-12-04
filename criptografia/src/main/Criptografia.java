package main;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import entidad.Usuario;

public class Criptografia {

	// Scanner para leer la entrada del usuario
	private static Scanner sc;
	private static SecretKey paloEspartano;
	private static byte[] bytesMensajeCifrado;
	private static String adminHash;
	private static String user2Hash;
	private static String user3Hash;

	// Bloque estático para inicializar el Scanner
	static {
		sc = new Scanner(System.in);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// Crear usuarios y obtener sus hashes
		Usuario admin = new Usuario("Pepe", "0000");
		Usuario user2 = new Usuario("Elena", "1111");
		Usuario user3 = new Usuario("Alberto", "2222");
		adminHash = obtenerResumenHash(admin.getUsuario(), admin.getPassword());
		user2Hash = obtenerResumenHash(user2.getUsuario(), user2.getPassword());
		user3Hash = obtenerResumenHash(user3.getUsuario(), user3.getPassword());

		// Realizar el inicio de sesión y ejecutar la aplicación si es exitoso
		if (realizarLogin()) {
			ejecutarAplicacion();
		} else {
			System.out.println("Inicio de sesión fallido. Saliendo de la aplicación.");
		}
	}

	// Método para realizar el inicio de sesión
	private static boolean realizarLogin() throws NoSuchAlgorithmException {
		int intentosRestantes = 3;

		// Bucle para permitir múltiples intentos de inicio de sesión
		while (intentosRestantes > 0) {
			System.out.println("Ingrese su nombre de usuario:");
			String usuarioConsola = sc.nextLine();
			System.out.println("Ingrese su contraseña:");
			String passwordConsola = sc.nextLine();

			// Obtener el hash de los datos de la consola
			String usuarioConsolaHash = obtenerResumenHash(usuarioConsola, passwordConsola);

			// Verificar si el hash coincide con algún hash de usuario almacenado
			if (usuarioConsolaHash.equals(adminHash) || usuarioConsolaHash.equals(user2Hash)
					|| usuarioConsolaHash.equals(user3Hash)) {
				System.out.println("¡Bienvenido/a, " + usuarioConsola + "!");
				return true;
			} else {
				intentosRestantes--;
				System.out.println("Inicio de sesión fallido. Intentos restantes: " + intentosRestantes);
			}
		}

		// Si se agotan los intentos, mostrar mensaje y devolver false
		System.out.println("Inicio de sesión fallido. Saliendo de la aplicación.");
		return false;
	}

	// Método para ejecutar la aplicación principal
	private static void ejecutarAplicacion() {
		try {
			boolean continuar = true;

			// Bucle principal del programa
			do {
				Menu.mostrarMenu(System.out);
				String opcion = sc.nextLine();

				// Switch para manejar las opciones del menú
				switch (opcion) {
				case "1":
					System.out.println("Saliendo de la aplicación...");
					continuar = false;
					break;
				case "2":
					cifrar();
					break;
				case "3":
					descifrar();
					break;
				default:
					System.out.println("Opción no válida. Inténtelo de nuevo.");
				}
			} while (continuar);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Método para obtener el resumen hash de usuario y contraseña
	private static String obtenerResumenHash(String usuario, String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(usuario.getBytes());
		md.update(password.getBytes());
		byte[] resumen = md.digest();
		return new String(resumen);
	}

	// Método para cifrar un mensaje
	private static void cifrar() {
		try {
			KeyGenerator generador = KeyGenerator.getInstance("DES");
			paloEspartano = generador.generateKey();
			Cipher cifrador = Cipher.getInstance("DES");
			cifrador.init(Cipher.ENCRYPT_MODE, paloEspartano);
			System.out.println("Introduce la frase que quieres encriptar:");
			String mensaje = sc.nextLine();

			byte[] bytesMensajeOriginal = mensaje.getBytes();
			bytesMensajeCifrado = cifrador.doFinal(bytesMensajeOriginal);
			System.out.println("Mensaje encriptado!!\n");

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			System.out.println("Error al crear y configurar el descifrador");
			System.out.println(e.getMessage());

		} catch (IllegalBlockSizeException | BadPaddingException e) {
			System.out.println("Error al cifrar el mensaje");
			System.out.println(e.getMessage());
		}
	}

	// Método para descifrar un mensaje
	private static void descifrar() {
		try {
			Cipher descifrador = Cipher.getInstance("DES");
			descifrador.init(Cipher.DECRYPT_MODE, paloEspartano);
			byte[] bytesMensajeDescifrado = descifrador.doFinal(bytesMensajeCifrado);
			String mensajeCifrado = new String(bytesMensajeCifrado);
			String mensajeDescifrado = new String(bytesMensajeDescifrado);

			System.out.println("Desencriptando mensaje...");
			System.out.println("Mensaje encriptado: " + mensajeCifrado);
			System.out.println("Mensaje desencriptado: " + mensajeDescifrado + "\n");

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			System.out.println("Error al crear y configurar el descifrador");
			System.out.println(e.getMessage());

		} catch (IllegalBlockSizeException | BadPaddingException e) {
			System.out.println("Error al cifrar el mensaje");
			System.out.println(e.getMessage());
		}
	}
}