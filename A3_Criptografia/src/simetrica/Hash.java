package simetrica;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hash {
    // Método para crear un resumen hash utilizando el algoritmo SHA-512
    public static String crearResumenHash(String mensaje) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytesMensaje = mensaje.getBytes();
            byte[] resumen = md.digest(bytesMensaje);

            // Convertir a Base64
            String mensajeHashBase64 = Base64.getEncoder().encodeToString(resumen);
            return mensajeHashBase64;

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error hash: " + e.getMessage());
            return null;
        }
    }
}
