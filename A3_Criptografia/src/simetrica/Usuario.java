package simetrica;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Usuario {
	
    private String nombreUsuario;
    private String contrase�aHash;
    private SecretKey claveSimetrica;

    // Constructor que inicializa el nombre de usuario y el hash de la contrase�a.
    public Usuario(String nombreUsuario, String contrase�a) {
        this.nombreUsuario = nombreUsuario;
        this.contrase�aHash = Hash.crearResumenHash(contrase�a);
    }

    // M�todo privado para generar la clave sim�trica utilizando el algoritmo DES
    private void generarClaveSimetrica() {
        try {
            KeyGenerator generador = KeyGenerator.getInstance("DES");
            this.claveSimetrica = generador.generateKey();
        } catch (Exception e) {
            System.out.println("Error al generar la clave sim�trica: " + e.getMessage());
        }
    }

    // M�todos getter
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public String getContrase�aHash() {
        return contrase�aHash;
    }
    // M�todo para obtener la clave sim�trica, gener�ndola si a�n no existe
    
    public SecretKey getClaveSimetrica() {
        if (claveSimetrica == null) {
            generarClaveSimetrica();
        }
        return claveSimetrica;
    }
    
    
   //M�todos adicionales 
    
    // M�todo para validar la contrase�a ingresada compar�ndola con el hash almacenado.
    public boolean validarContrase�a(String contrase�a) {
        String hashIngresado = Hash.crearResumenHash(contrase�a);
        return contrase�aHash.equals(hashIngresado);
    }     

    // M�todo para establecer la clave sim�trica manualmente
    public void setClaveSimetrica(SecretKey claveSimetrica) {
        this.claveSimetrica = claveSimetrica;
    }

    // M�todo est�tico para validar los datos introducidos por un usuario.
    public static Usuario validarDatos(Usuario[] usuarios, String nombreUsuario, String contrase�a) {
        for (Usuario ele : usuarios) {
            if (ele.getNombreUsuario().equals(nombreUsuario) && ele.validarContrase�a(contrase�a)) {
                System.out.println("Bienvenido, " + ele.getNombreUsuario());
                System.out.println("La clave hash es : " + ele.getContrase�aHash());
                return ele;
            }
        }
        return null;
    }
}
