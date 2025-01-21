package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener {

	private vista.Principal vistaPrincipal;
    private Socket cliente;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int id = 0;

	/*
	 * *** CONSTRUCTORES ***
	 */
    public Controlador(vista.Principal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.inicializarControlador();
    }

    private void inicializarControlador() {

        try {
            cliente = new Socket("localhost", 4500);
            dos = new DataOutputStream(cliente.getOutputStream());
            dis = new DataInputStream(cliente.getInputStream());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // VENTANA LOGIN
        this.vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
        this.vistaPrincipal.getPanelLogin().getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN.toString());
    	this.vistaPrincipal.getPanelMenu().getBtnDesconectar().addActionListener(this);
    	this.vistaPrincipal.getPanelMenu().getBtnDesconectar().setActionCommand(Principal.enumAcciones.DESCONECTAR.toString());}


	@Override
	public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

        switch (accion) {
        case LOGIN:
            incializarServidor();
            this.mConfirmarLogin(accion);
            break;
        case DESCONECTAR:
            try {
                dos.writeInt(4);
                dos.flush();
                dis.close();
                dos.close();
                cliente.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_LOGIN);

        default:
            break;

        }
    }

	private void mConfirmarLogin(enumAcciones accion) {
        // TODO Auto-generated method stub

        try {
            dos.writeInt(1);
            dos.flush();
            dos.writeUTF(this.vistaPrincipal.getPanelLogin().getTextFieldUser().getText());
            dos.flush();
            dos.writeUTF(new String(this.vistaPrincipal.getPanelLogin().getTextFieldPass().getPassword()));
            dos.flush();
            id = (int) dis.readInt();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (id != 0) {
            System.out.println("Hola");
            this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_MENU);
        } else {
            JOptionPane.showMessageDialog(null, "No existe ningun profesor con esas credenciales");
        }
    }
	
	private void incializarServidor() {
        // TODO Auto-generated method stub
        try {
            cliente = new Socket("localhost", 4500);
            dos = new DataOutputStream(cliente.getOutputStream());
            dis = new DataInputStream(cliente.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	public int loginAndroid(String username, String password) {
		int role = 0;
        int tipoId = 0; // para triggerear el default,que probablemente diga error

 
        String query = "SELECT tipo_id FROM users WHERE username = ? AND password = ?";
                                //mete la config de la conexiona a la base
        try (Connection connection = DriverManager.getConnection("localhost", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                role = resultSet.getInt("tipo_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return role;
    }

}
