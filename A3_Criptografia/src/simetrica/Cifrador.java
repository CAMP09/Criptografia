package simetrica;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class Cifrador {
    private static final Scanner scanner = new Scanner(System.in);

    // Método para realizar una prueba de cifrado utilizando el algoritmo DES
    public static void realizarPruebaCifrado() {
        try {
            System.out.println("Probando sistema de encriptación con algoritmo DES");

            // Generador de claves simétricas
            KeyGenerator generador = KeyGenerator.getInstance("DES");
            SecretKey claveSimetrica = generador.generateKey();

            // Objeto que nos permitirá encriptar o desencriptar a partir de una clave
            Cipher cifrador = Cipher.getInstance("DES");

            // Configuramos el cifrador para encriptar
            cifrador.init(Cipher.ENCRYPT_MODE, claveSimetrica);

            // Mensaje original
            System.out.print("Introduce una frase para encriptar: ");
            String mensajeOriginal = scanner.nextLine();

            // Ciframos el mensaje original
            byte[] bytesMensajeOriginal = mensajeOriginal.getBytes();
            byte[] bytesMensajeCifrado = cifrador.doFinal(bytesMensajeOriginal);

            // Convertimos a cadena para mostrar
            String mensajeCifrado = Base64.getEncoder().encodeToString(bytesMensajeCifrado);

            System.out.println("Mensaje Original: " + mensajeOriginal);
            System.out.println("Mensaje Cifrado: " + mensajeCifrado);

            // Configuramos el cifrador para desencriptar
            cifrador.init(Cipher.DECRYPT_MODE, claveSimetrica);

            // Desciframos el criptograma
            byte[] bytesMensajeDescifrado = cifrador.doFinal(bytesMensajeCifrado);
            String mensajeDescifrado = new String(bytesMensajeDescifrado);

            System.out.println("Mensaje Descifrado: " + mensajeDescifrado);

        } catch (Exception e) {
            System.out.println("Error en la prueba de cifrado: " + e.getMessage());
        }
    }
}
