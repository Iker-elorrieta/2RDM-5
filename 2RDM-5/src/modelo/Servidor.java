package modelo;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Servidor {
	private static final int PUERTO = 4500;
	private ArrayList<Usuario> usuarios = new ArrayList<>();
	;
	private ArrayList<ObjectOutputStream> listaClientes;

	public Servidor() {
		listaClientes = new ArrayList<>();
	}

	private void rellenarUsuariosDefecto() {
		usuarios.add(new Usuario("1", "1", generarHash("1")));
		usuarios.add(new Usuario("usuario2", "Nombre2", generarHash("pass2")));
	}

	private String generarHash(String texto) {
		String resumenString = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte dataBytes[] = texto.getBytes();
			md.update(dataBytes);
			byte resumen [] = md.digest();
			resumenString = new String (resumen);


		} catch (NoSuchAlgorithmException e) {
			resumenString ="Error al generar el hash";
			e.printStackTrace();
		}
		return resumenString;

	}
	public void iniciar() {
		rellenarUsuariosDefecto();
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado en el puerto " + PUERTO);
			System.out.println("Esperando conexion");

			while (true) {
				Socket socket = serverSocket.accept();
				ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

                listaClientes.add(salida);
				salida.writeObject(usuarios);
				salida.flush();

				salida.writeObject("Conexión establecida con el servidor.");
				salida.flush();

				new HiloServidor(salida, entrada, listaClientes, usuarios).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Servidor().iniciar();
	}
}
