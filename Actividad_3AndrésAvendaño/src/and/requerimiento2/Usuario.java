package and.requerimiento2;

import java.util.Objects;

public class Usuario {

	private String user, pwd;

	
	
	public Usuario(String user, String pwd) {
		super();
		this.user = user;
		this.pwd = pwd;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pwd, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(pwd, other.pwd) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Usuario [user=" + user + ", pwd=" + pwd + "]";
	}

	
}
