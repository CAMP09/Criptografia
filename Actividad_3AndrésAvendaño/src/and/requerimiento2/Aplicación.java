package and.requerimiento2;

import java.security.GeneralSecurityException;

public class Aplicación {
	public static void main(String[] args) throws GeneralSecurityException {
		ListaUsuarios lu = new ListaUsuarios();
		Logicas.mostrarMenuAutenticacion(lu);
		
	}
}
