package simetrica;

import java.util.Scanner;
import javax.crypto.Cipher;
import java.util.Base64;

public class Menu {
	//Importamos la clase Scanner que utilizaremos en la clase
    private static final Scanner leer = new Scanner(System.in);
    
    // M�todo para ejecutar el men�,toma un objeto Usuario como par�metro y muestra las opciones del men�.
    
    public static void ejecutarMenu(Usuario usuario) {        
        String fraseEncriptada = null;
        int opcion;
        
     // Bucle do/while, el men� se muestra hasta que el usuario introduzca la opci�n 1, en cuyo caso se cierra el programa 
     //Llamamos al m�todo para mostrar el men� y lee la opci�n seleccionada por el usuario.
        do {
            mostrarMenu();  
            opcion = leer.nextInt(); 
            leer.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Cerrando la aplicaci�n");
                    break;
                case 2:
                    fraseEncriptada = encriptarFrase(usuario);  
                    if (fraseEncriptada != null) {
                        System.out.println("Frase encriptada: " + fraseEncriptada);
                    }
                    break;
                case 3:
                    desencriptarFrase(usuario, fraseEncriptada);
                    break;
                default:
                    System.out.println("Opci�n no v�lida. Int�ntalo de nuevo.");
            }
        } while (opcion != 1);
    }

    
 // M�todo para mostrar el men�.
    public static void mostrarMenu() {
        System.out.println("***************Men�*******************");
        System.out.println("1. Salir del programa");
        System.out.println("2. Encriptar frase");
        System.out.println("3. Desencriptar frase");
    }

    // M�todo privado para encriptar una frase, toma un objeto Usuario como par�metro y devuelve la frase encriptada.
    //Este m�todo es llamado desde la opci�n 2 del men�.
    private static String encriptarFrase(Usuario usuario) {
        try {
        	// Obtiene una instancia del cifrador usando el algoritmo DES y lo inicializa
            Cipher cifrador = Cipher.getInstance("DES");  
            cifrador.init(Cipher.ENCRYPT_MODE, usuario.getClaveSimetrica());  

            System.out.print("Introduce una frase para encriptar: ");
            String frase = leer.nextLine(); 
            
            // Encripta la frase y obtiene los bytes encriptados y convierte los bytes encriptados a una cadena Base64
            byte[] bytesEncriptados = cifrador.doFinal(frase.getBytes());  
            String fraseEncriptada = Base64.getEncoder().encodeToString(bytesEncriptados);  

            return fraseEncriptada;  
        } catch (Exception e) {
            System.out.println("Error al encriptar la frase: " + e.getMessage());
            return null;
        }
    }

    // M�todo privado para desencriptar una frase, toma un objeto Usuario y la frase encriptada como par�metros.
    //Este m�todo es llamado desde la opci�n 3 del men�.
    
    private static void desencriptarFrase(Usuario usuario, String fraseEncriptada) {
        try {
        	// Obtiene una instancia del cifrador usando el algoritmo DES e inicializa el cifrado.
            Cipher cifrador = Cipher.getInstance("DES");  
            cifrador.init(Cipher.DECRYPT_MODE, usuario.getClaveSimetrica());  
            
            // Decodifica la frase encriptada a bytes,desencripta los bytes y obtiene la frase desencriptada.
            byte[] bytesEncriptados = Base64.getDecoder().decode(fraseEncriptada);  
            String fraseDesencriptada = new String(cifrador.doFinal(bytesEncriptados));  

            System.out.println("Frase desencriptada: " + fraseDesencriptada);  
        } catch (Exception e) {
            System.out.println("Error al desencriptar la frase: " + e.getMessage());
        }
    }
}
