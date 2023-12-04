package simetrica;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	
        // Creamos 3 objetos usuarios, con su nombre y clave.
        Usuario[] usuarios = {new Usuario("Alberto", "123"),
				              new Usuario("Andres", "456"),
				              new Usuario("Alejandro", "789")};

        // Creamos una variable que registrar� los intentos de inicio de sesi�n del usuario.
        //El usuario tiene tres intentos, si los agota se cierra el programa.
        int intentos = 0;
        Usuario user = null;

        while (intentos < 3) {
        	
        	String nombreUsuario;
        	String contrase�a;
        	
            Scanner leer = new Scanner(System.in);
            
            System.out.print("Introduce tu nombre: ");
            nombreUsuario = leer.nextLine();
            System.out.print("Contrase�a: ");
            contrase�a = leer.nextLine();

            // Verificar datos de usuario introducidos          
            
            user = Usuario.validarDatos(usuarios, nombreUsuario, contrase�a);
            
           // Si los datos introducidos son v�lidos entonces se muestra un men� para que el usuario seleccione una opci�n
            
            if (user != null) {                
                System.out.println("Bienvenido, " + user.getNombreUsuario()+ " Escoge una opci�n");
                Menu.ejecutarMenu(user);
                break;
            } else {
                System.out.println("Registro incorrecto, te quedan : " + (2 - intentos) + "intentos");
                //Contador de intentos de registro
                intentos++;
            }
        }

        if (intentos == 3) {
            System.out.println("Has agotado el n�mero de intentos, Cerrar programa");
        }
    }
}
