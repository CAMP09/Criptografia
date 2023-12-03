package simetrica;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Usuario {
	
    private String nombreUsuario;
    private String contraseñaHash;
    private SecretKey claveSimetrica;

    // Constructor que inicializa el nombre de usuario y el hash de la contraseña.
    public Usuario(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseñaHash = Hash.crearResumenHash(contraseña);
    }

    // Método privado para generar la clave simétrica utilizando el algoritmo DES
    private void generarClaveSimetrica() {
        try {
            KeyGenerator generador = KeyGenerator.getInstance("DES");
            this.claveSimetrica = generador.generateKey();
        } catch (Exception e) {
            System.out.println("Error al generar la clave simétrica: " + e.getMessage());
        }
    }

    // Métodos getter
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public String getContraseñaHash() {
        return contraseñaHash;
    }
    // Método para obtener la clave simétrica, generándola si aún no existe
    
    public SecretKey getClaveSimetrica() {
        if (claveSimetrica == null) {
            generarClaveSimetrica();
        }
        return claveSimetrica;
    }
    
    
   //Métodos adicionales 
    
    // Método para validar la contraseña ingresada comparándola con el hash almacenado.
    public boolean validarContraseña(String contraseña) {
        String hashIngresado = Hash.crearResumenHash(contraseña);
        return contraseñaHash.equals(hashIngresado);
    }     

    // Método para establecer la clave simétrica manualmente
    public void setClaveSimetrica(SecretKey claveSimetrica) {
        this.claveSimetrica = claveSimetrica;
    }

    // Método estático para validar los datos introducidos por un usuario.
    public static Usuario validarDatos(Usuario[] usuarios, String nombreUsuario, String contraseña) {
        for (Usuario ele : usuarios) {
            if (ele.getNombreUsuario().equals(nombreUsuario) && ele.validarContraseña(contraseña)) {
                System.out.println("Bienvenido, " + ele.getNombreUsuario());
                System.out.println("La clave hash es : " + ele.getContraseñaHash());
                return ele;
            }
        }
        return null;
    }
}
