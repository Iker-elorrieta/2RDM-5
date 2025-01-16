package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener {

	private vista.Principal vistaPrincipal;
    private Socket cliente;
    private ObjectOutputStream dos;
    private ObjectInputStream dis;
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
            dos = new ObjectOutputStream(cliente.getOutputStream());
            dis = new ObjectInputStream(cliente.getInputStream());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // VENTANA LOGIN
        this.vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
        this.vistaPrincipal.getPanelLogin().getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN.toString());}


	@Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

        switch (accion) {
        case LOGIN:
            this.mConfirmarLogin(accion);
            break;
        case DESCONECTAR:
            this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_LOGIN);
            break;
        default:
            break;
        }
    }

	private void mConfirmarLogin(enumAcciones accion) {
        // TODO Auto-generated method stub

        try {
            dos.writeObject(1);
            dos.flush();
            dos.writeObject(this.vistaPrincipal.getPanelLogin().getTextFieldUser().getText());
            dos.flush();
            dos.writeObject(new String(this.vistaPrincipal.getPanelLogin().getTextFieldPass().getPassword()));
            dos.flush();
            id = (int) dis.readObject();
        } catch (IOException | ClassNotFoundException e) {
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
}
