package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controlador.enumAcciones;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Acciones


	private JPanel panelContenedor;
	private VLogin vLogin;




	public Principal() {
		mCrearPanelContenedor();
		mCrearVLogin();

	}

	// *** Creaci�n de paneles ***

	private void mCrearPanelContenedor() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 584);
		panelContenedor = new JPanel();
		panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenedor);
		panelContenedor.setLayout(null);

	}

	private void mCrearVLogin() {
		vLogin = new VLogin();
		vLogin.setLocation(0, 11);
		panelContenedor.add(vLogin);
		panelContenedor.setBounds(vLogin.getBounds());
		vLogin.setVisible(true);
	}


	// *** FIN creaci�n de paneles ***

	public void mVisualizarPaneles(enumAcciones panel) {

		vLogin.setVisible(false);
	


		switch (panel) {
		case CARGAR_PANEL_LOGIN:
			vLogin.setVisible(true);
			break;
		

		default:
			break;

		}
	}

	public VLogin getvLogin() {
		return vLogin;
	}

	public void setvLogin(VLogin vLogin) {
		this.vLogin = vLogin;
	}






}