package and.requerimiento2;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

import and.requerimiento1.EncriptacionSimetrica;

public class SeguridadHash {
	static MessageDigest md;
	static int intentos = 0;
    
    public static void mostrarMenuAutenticacion(ListaUsuarios lu) throws GeneralSecurityException {
        try(Scanner scanner = new Scanner(System.in)){
        boolean salir = false;
        
        	while (!salir) {
	            System.out.println("\n===== Bienvenido =====");
	            System.out.println("1. Iniciar sesión (Log In)");
	            System.out.println("2. Registrarse (Sign Up)");
	            System.out.println("3. Salir");
	            System.out.print("Elige una opción: ");
	            int opcion = scanner.nextInt();
	
	            switch (opcion) {
	                case 1:
	                    iniciarSesion(scanner, lu);
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

    private static void iniciarSesion(Scanner scanner, ListaUsuarios lu) throws GeneralSecurityException {
        scanner.nextLine(); 
        System.out.print("\nUsuario: ");
        while (intentos < 3) {
        	String usuario = scanner.nextLine();
            if (buscarUsuario(usuario, lu) == null) {
    			System.out.println("\nInicia sesión con otro nombre o regístrate");
    			intentos++;
    			System.out.println("Quedan " + (3 - intentos) + " intentos");
            }else {
            	System.out.print("\nContraseña: ");
                String contrasena = scanner.nextLine();
                String cIntroducida = hashearContrasena(contrasena);
                String cUsuario = buscarUsuario(usuario, lu).getPwd();
                if (verificarCredenciales(cIntroducida, cUsuario)) {
                	System.out.println("\nSesión iniciada para el usuario " + usuario + "...");
                	EncriptacionSimetrica.encriptacionSimentrica();
    			}else {
    				System.out.println("Contraseña incorrecta");
    				intentos++;
    				System.out.println("Quedan " + (3 - intentos) + " intentos");
    				//mostrarMenuAutenticacion(lu);
    			}        	
            }
            
		}
		System.out.println("Has superado los 3 intentos");
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
