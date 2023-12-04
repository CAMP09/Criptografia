package and.requerimiento2;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class ListaUsuarios {

	private ArrayList<Usuario> listaUsuarios;
	
	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	{
		listaUsuarios = new ArrayList<Usuario>();
		Usuario u1;
		Usuario u2;
		Usuario u3;
		try {
			u1 = new Usuario("Alberto", SeguridadHash.hashearContrasena("alberto1"));
			u2 = new Usuario("Carmen", SeguridadHash.hashearContrasena("carmen2"));
			u3 = new Usuario("Alejandro", SeguridadHash.hashearContrasena("alex3"));
		
			listaUsuarios.add(u1);
			listaUsuarios.add(u2);
			listaUsuarios.add(u3);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
