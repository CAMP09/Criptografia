package modelo.entidad;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
/**
 * La clase Usuario representa a un usuario con un nombre de usuario y una contraseña hasheada.
 * Proporciona métodos para acceder al nombre de usuario y la contraseña hasheada,
 * así como para verificar una contraseña ingresada mediante su hash.
 * 
 * @author Alberto Arroyo Santofimia
 * @version 1.0
 */
public class Usuario {
    private String nombreUsuario;
    private String passwordHasheada;
    
    /**
     * Constructor de la clase Usuario. La contraseña que se guardará en el objeto será en hash Base64, al pasarle
     * el resultado del metodo hashearPassword(password)
     * 
     * @param nombreUsuario El nombre de usuario del usuario.
     * @param password La contraseña del usuario (se almacena como hash).
     */
    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.passwordHasheada = hashearPassword(password);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPasswordHasheada() {
        return passwordHasheada;
    }
    
    //Metodo que entra como argumento la contraseña normal, la hashea y la compara con la que tiene el objeto
    
    /**
     * Verifica si la contraseña ingresada coincide con la contraseña almacenada en el objeto Usuario.
     * La contraseña ingresada se hashea y luego se compara con la contraseña almacenada.
     *
     * @param password La contraseña ingresada por el usuario.
     * @return true si la contraseña ingresada es correcta, false de lo contrario.
     */
    public boolean verificarPassword(String password) {
    	// hashea la contraseña pasada por teclado y la compara con la que tiene el objeto
        return passwordHasheada.equals(hashearPassword(password));
    }
    
    
	/**
	 * Hashea una contraseña utilizando el algoritmo SHA-512 y lo convierte a Base64.
	 *
	 * @param password La contraseña a hashear.
	 * @return El hash de la contraseña en formato Base64.
	 */
    private String hashearPassword(String password) {
        //lógica para hashear la contraseña aquí (puede ser con MessageDigest y Base64)
    	byte[] passwordByte = password.getBytes();
    	try {
    		//Creamos un objeto MessageDigest a través del método estático 
    		//getInstance() al que se le pasa el tipo de algoritmo que vamos a 
    		//utilizar.
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(passwordByte);
			byte[] resumen = md.digest();
			//Pasamos a la codificación BASE 64 si queremos reducir el alfa
			//alfabeto resultante. Puede ser util si queremos guardar la información
			//o enviar la información.
			String mensajeHashBase64 = Base64.getEncoder().encodeToString(resumen);
	    	return mensajeHashBase64; 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}    	
    }
}