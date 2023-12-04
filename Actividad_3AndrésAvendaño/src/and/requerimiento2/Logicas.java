package and.requerimiento2;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

import and.requerimiento1.EncriptacionSimetrica;

public class Logicas {
	static MessageDigest md;
	static int intentos = 0;
	static boolean salir;
    
    
    public static void mostrarMenuAutenticacion(ListaUsuarios lu) throws GeneralSecurityException {
        try(Scanner scanner = new Scanner(System.in)){
        	salir = false;
        
        	while (!salir) {
	            System.out.println("\n===== Bienvenido =====");
	            System.out.println("1. Iniciar sesión (Log In)");
	            System.out.println("2. Registrarse (Sign Up)");
	            System.out.println("3. Salir");
	            System.out.print("Elige una opción: ");
	            int opcion;
	            try {
	                opcion = Integer.parseInt(scanner.nextLine());
	            } catch (NumberFormatException e) {
	                System.out.println("\nPor favor, ingresa un número válido.");
	                continue;
	            }
	            
	            switch (opcion) {
	                case 1:
	                	if (intentos >= 3) {
	                        System.out.println("Has superado el número máximo de intentos. Programa terminado.");
	                        salir = true;
	                    } 
	                	 boolean continuar = iniciarSesion(scanner, lu);
	                     if (!continuar) {
	                         salir = true; 
	                     }
	                     break;

	                case 2:
	                    registrarse(scanner, lu);
	                    break;
	                case 3:
	                    salir = true;
	                    System.out.println("\nSaliendo del programa...");
	                    break;
	                default:
	                    System.out.println("\nOpción no válida. Por favor, intenta de nuevo.");
	                    break;
	            }
		}
	    
        }catch (Exception e) {
        	e.getMessage();
        }
    }

    private static boolean iniciarSesion(Scanner scanner, ListaUsuarios lu) throws GeneralSecurityException {
        scanner.nextLine(); 
        System.out.print("\nUsuario: ");
    	String usuario = scanner.nextLine();
    	
        if (buscarUsuario(usuario, lu) == null) {
			intentos++;
			 if (intentos >= 3) {
	                System.out.println("Número máximo de intentos alcanzado. Programa terminado.");
	                return false;
	         }
			 System.out.println("\nInicia sesión con otro nombre o regístrate");
			 System.out.println("Quedan " + (3-intentos) + " intentos");
			 return true;
        }
        
    	System.out.print("\nContraseña: ");
        String contrasena = scanner.nextLine();
        String cIntroducida = hashearContrasena(contrasena);
        String cUsuario = buscarUsuario(usuario, lu).getPwd();
        
        if (!verificarCredenciales(cIntroducida, cUsuario)) {
        	System.out.println("Contraseña incorrecta");
        	intentos++;
        	if (intentos >= 3) {
                System.out.println("Número máximo de intentos alcanzado. Programa terminado.");
                return false;
        	}
        	System.out.println("Quedan " + (3-intentos) + " intentos");
        	return true;
        }
        
    	System.out.println("\nSesión iniciada para el usuario " + usuario + "...");
    	EncriptacionSimetrica.encriptacionSimentrica();
    	intentos = 0;
    	return true;
       
    }
	

    
    
    public static String hashearContrasena (String contrasena) throws GeneralSecurityException {
    	md = MessageDigest.getInstance("SHA-512");
    	byte[] pwdBytes = contrasena.getBytes();
    	md.update(pwdBytes);
    	
    	byte[] resumenHash = md.digest();
    	String resumenHashBase64 = Base64.getEncoder().encodeToString(resumenHash);
		return resumenHashBase64;
    }

    private static void registrarse(Scanner scanner, ListaUsuarios lu) throws GeneralSecurityException {
        scanner.nextLine(); // Limpiar buffer
        System.out.print("\nCrear nuevo usuario: ");
        String nuevoUsuario = scanner.nextLine();
        System.out.print("\nCrear nueva contraseña: ");
        String nuevaContrasena = scanner.nextLine();
        
        Usuario u = new Usuario(nuevoUsuario, hashearContrasena(nuevaContrasena));
        lu.getListaUsuarios().add(u);

        // Aquí iría la lógica para registrar al nuevo usuario
        System.out.println("\nUsuario " + nuevoUsuario + " registrado con éxito.");
        mostrarMenuAutenticacion(lu);
    }
    
    private static Usuario buscarUsuario(String usuario, ListaUsuarios lu) {
    	for (Usuario u : lu.getListaUsuarios()) {
    		if (u.getUser().equals(usuario)) {
				return u;
			}
		}
    	System.out.println("\nEl nombre de usuario no existe");
		return null;
    }
    
    private static boolean verificarCredenciales(String cIntroducida, String cUsuario) {
    	return cUsuario.equals(cIntroducida);
    }
    
}
