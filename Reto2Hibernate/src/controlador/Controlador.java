package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import modelo.Usuario;
import modelo.Cliente;

public class Controlador implements ActionListener {

	private vista.VLogin vLogin;
	private vista.Principal vistaPrincipal;

	private ArrayList<Usuario> usuarios;
	private Usuario usarioConectado;
	private Cliente cliente;

	/*
	 * *** CONSTRUCTORES ***
	 */
	public Controlador(vista.Principal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		this.inicializarControlador();

		try {
			conectarConServidor();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void inicializarControlador() {
		inicializarPaneles();
		// PANEL Login
		// btn Aceptar
		this.vLogin.getBtnAceptar().addActionListener(this);
		this.vLogin.getBtnAceptar().setActionCommand(enumAcciones.LOGIN.toString());
	}

	private void inicializarPaneles() {
		vLogin = this.vistaPrincipal.getvLogin();
		
	}

	/*** Tratamiento de las acciones ***/
	@Override
	public void actionPerformed(ActionEvent e) {
		enumAcciones accion = enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		case LOGIN:
			login();
			System.out.println();
			break;
		case REGISTRARSE:
			vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_REGISTRO);
			break;
	
		case DESCONECTAR:
			System.exit(0);
			break;
		default:
			break;
		}
	}

	

	private void login() {
		String usuario = vLogin.getTextFieldUser().getText().toString();
		String contrasenha = vLogin.getTextFieldPass().getText().toString();
		boolean loginCorrecto = false;

		if (!usuario.isEmpty() && !contrasenha.isEmpty()) {
			//Existe el usuario y concide la contraseña con la almaceanada hasheada
			for (Usuario u : usuarios) {
				if (u.getUser().equals(usuario) && u.getContrasenha().equals(generarHash(contrasenha))) {
					loginCorrecto = true;
					usarioConectado = u;
					System.out.println("registrado");
					break;
				}
			}
		}

		/*if (loginCorrecto) {
			try {
				cliente.usuarioCorrecto(vChat.getTextArea(), vChat.getTextField());
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_CHAT);
		} else {
			vLogin.getLblError().setVisible(true);

			vLogin.getLblError().setText("Credenciales incorrectas.");
		}*/
	}

	private String generarHash(String texto) {
		String resumenString = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte dataBytes[] = texto.getBytes();
			md.update(dataBytes);
			byte resumen[] = md.digest();
			resumenString = new String(resumen);
		} catch (NoSuchAlgorithmException e) {
			resumenString = "Error al generar el hash";
			e.printStackTrace();
		}
		return resumenString;
	}

	private void conectarConServidor() throws ClassNotFoundException, IOException {
		cliente = new Cliente();
		usuarios = cliente.getUsuarios();
	}
}
