package modelo;

import java.io.Serializable;

public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private String nombre;
	private String contrasenha;
	
	public Usuario() {
		
	}
	public Usuario(String user, String nombre, String contrasenha) {
		this.user = user;
		this.nombre = nombre;
		this.contrasenha = contrasenha;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasenha() {
		return contrasenha;
	}
	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}


}
