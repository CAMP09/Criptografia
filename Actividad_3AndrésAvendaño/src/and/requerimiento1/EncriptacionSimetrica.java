package and.requerimiento1;

import java.security.GeneralSecurityException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncriptacionSimetrica {
	
	private static String frase, fraseCifrada, fraseDesCifrada;
	private static Cipher cifrador;
	private static SecretKey clave;
	private static byte[] fraseEnBytesCifrada;

	    public static void encriptacionSimentrica() throws GeneralSecurityException {
	        Scanner scanner = new Scanner(System.in);
	        int opcion;

	        do {
	            mostrarMenu();
	            System.out.print("Elige una opción: \n");
	            opcion = scanner.nextInt();

	            switch (opcion) {
	                case 1:
	                    encriptarFrase(scanner);
	                    break;
	                case 2:
	                    desencriptarFrase();
	                    break;
	                case 3:
	                    System.out.println("\nSaliendo del programa...");
	                    break;
	                default:
	                    System.out.println("\nOpción no válida. Por favor, intenta de nuevo.");
	            }
	        } while (opcion != 3);
	    }

	    private static void mostrarMenu() {
	        System.out.println("\n===== MENÚ PRINCIPAL =====");
	        System.out.println("1. Encriptar frase");
	        System.out.println("2. Desencriptar frase");
	        System.out.println("3. Salir del programa\n");
	    }

	    private static void encriptarFrase(Scanner scanner) throws GeneralSecurityException {
	        //Preparamos encriptación
	        KeyGenerator generadorClave = KeyGenerator.getInstance("AES");
	        clave = generadorClave.generateKey();
	        
	        cifrador = Cipher.getInstance("AES");
	        cifrador.init(Cipher.ENCRYPT_MODE, clave);
	        
	        //Entrada del usuario
	        System.out.print("\nIngresa la frase a encriptar: ");
	        scanner.nextLine();  // Limpiar buffer
	        frase = scanner.nextLine();
	        
	        //Pasamos a bytes
	        byte[] fraseEnBytes = frase.getBytes();
	        
	        //Ciframos
	        fraseEnBytesCifrada = cifrador.doFinal(fraseEnBytes);
	        
	        //Pasamos a String para presentación al usuario
	        fraseCifrada = new String(fraseEnBytesCifrada);    
	        
	        System.out.println("\nFrase encriptada: " + fraseCifrada); 
	    }

	    //Se desencripta la frase antes introducida
	    private static void desencriptarFrase() throws GeneralSecurityException {
	    	if (fraseEnBytesCifrada == null) {
				System.out.println("No hay una frase encriptada");
				encriptacionSimentrica();
			}
	    	
	    	//Preparamos desencriptación
	        cifrador.init(Cipher.DECRYPT_MODE, clave);
	        
	        //Desencriptamos
	        byte[] fraseEnBytesDesCifrada = cifrador.doFinal(fraseEnBytesCifrada);
	        
	        //Pasamos a String para presentación al usuario
	        fraseDesCifrada = new String(fraseEnBytesDesCifrada);
	        System.out.println("\nFrase desencriptada: " + fraseDesCifrada); 
	    }
	

}
