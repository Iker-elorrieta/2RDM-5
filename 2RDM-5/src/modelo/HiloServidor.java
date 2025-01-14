package modelo;

import java.io.*;
import java.util.ArrayList;

public class HiloServidor extends Thread {
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private ArrayList<ObjectOutputStream> listaClientes;
	private ArrayList<Usuario> usuarios;

	public HiloServidor(ObjectOutputStream salida, ObjectInputStream entrada, ArrayList<ObjectOutputStream> listaClientes, ArrayList<Usuario> usuarios) throws IOException {
		this.listaClientes = listaClientes;
		this.salida = salida;
		this.entrada = entrada;
		this.usuarios = usuarios;
	}

	@Override
	public void run() {


		String mensaje;
		try {
		while ((mensaje = (String) entrada.readObject()) != null) {
			if(mensaje.equals("//registrar")) {
				synchronized (usuarios) {
					// Recibimos atraves de un clave especial el obejto
					usuarios.add((Usuario) entrada.readObject());
					System.out.println("Registrado nuevo usuario");
				}
			}else {
				for (ObjectOutputStream out : listaClientes) {
					out.writeObject(mensaje);
					out.flush();
				}
			}
		}
		} catch (IOException e) {
			e.printStackTrace();
			// Si el cliente se desconecta inesperadamente
			listaClientes.remove(salida);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


