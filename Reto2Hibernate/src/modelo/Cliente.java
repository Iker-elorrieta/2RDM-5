package modelo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente  {

	private final int PUERTO = 5000;
	private final String HOST = "127.0.0.1";
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private Socket socket;
	ArrayList<Usuario> usuarios;

	@SuppressWarnings("unchecked")
	public Cliente() throws IOException, ClassNotFoundException {
		
		socket = new Socket(HOST, PUERTO);
		salida = new ObjectOutputStream(socket.getOutputStream());
		salida.flush();
		entrada = new ObjectInputStream(socket.getInputStream());
		usuarios = (ArrayList<Usuario>) entrada.readObject();
		System.out.println("Usuarios recibidos del servidor: " + usuarios.size());


}
	public void usuarioCorrecto( JTextArea textArea, JTextField textField) throws IOException, ClassNotFoundException {
		HiloRecibir hilo = new HiloRecibir( entrada, textArea);
		hilo.start();

	}

	public void enviarMensaje(String mensaje) {
		try {
			if (salida != null) {
				salida.writeObject(mensaje);
				salida.flush();
			}
		} catch (IOException e) {
			System.out.println("Error al enviar mensaje de cliente a servidor");
			e.printStackTrace();
		}
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}


	public void registrarNuevoUsuario(Usuario usuario) throws IOException {
		//Enviamos comando especial
		salida.writeObject("//registrar"); 
		salida.flush();

		salida.writeObject(usuario); 
		salida.flush();

	}


	public void cerrarConexion() {
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Error al cerrar la conexión.");
			e.printStackTrace();
		}
	}
}
