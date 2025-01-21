package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PanelLogin panelLogin;
	private PanelMenu panelMenu;
	private PanelHorario panelHorario;
	private PanelLista panelLista;
	private JPanel panelContenedor;

	public static enum enumAcciones {
		CARGAR_PANEL_LOGIN, CARGAR_PANEL_MENU, LOGIN, DESCONECTAR, VOLVER, CARGAR_PANEL_HORARIO, CARGAR_PANEL_LISTA
	}

	public Principal() {
		mCrearPanelContenedor();
		mCrearVLogin();
		mCrearPanelMenu();
		mCrearPanelHorario();
	}

	private void mCrearPanelHorario() {
		// TODO Auto-generated method stub
		panelHorario = new PanelHorario();
		panelHorario.setLocation(0, 11);
		panelContenedor.add(panelHorario);
		panelContenedor.setBounds(panelHorario.getBounds());
		panelHorario.setVisible(false);
	}

	public void mVisualizarPaneles(enumAcciones panel) {

		panelLogin.setVisible(false);
		panelMenu.setVisible(false);
		panelHorario.setVisible(false);
		switch (panel) {
		case CARGAR_PANEL_LOGIN:
			panelLogin.setVisible(true);
			break;
		case CARGAR_PANEL_MENU:
			panelMenu.setVisible(true);
			break;
		case CARGAR_PANEL_HORARIO:
			panelHorario.setVisible(true);
			break;
		default:
			break;
		}
	}

	// *** Creacion de paneles ***

	private void mCrearPanelContenedor() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 584);
		panelContenedor = new JPanel();
		panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenedor);
		panelContenedor.setLayout(null);

	}

	private void mCrearVLogin() {
		panelLogin = new PanelLogin();
		panelLogin.setLocation(0, 11);
		panelContenedor.add(panelLogin);
		panelContenedor.setBounds(panelLogin.getBounds());
		panelLogin.setVisible(true);
	}

	private void mCrearPanelMenu() {
		// TODO Auto-generated method stub
		panelMenu = new PanelMenu();
		panelMenu.setLocation(0, 11);
		panelContenedor.add(panelMenu);
		panelContenedor.setBounds(panelMenu.getBounds());
		panelMenu.setVisible(false);
	}

	// *** FIN creacion de paneles ***

	public JPanel getPanelContenedor() {
		return panelContenedor;
	}

	public void setPanelContenedor(JPanel panelContenedor) {
		this.panelContenedor = panelContenedor;
	}

	public PanelLogin getPanelLogin() {
		return panelLogin;
	}

	public void setPanelLogin(PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
	}

	public PanelMenu getPanelMenu() {
		return panelMenu;
	}

	public void setPanelMenu(PanelMenu panelMenu) {
		this.panelMenu = panelMenu;
	}

	public PanelHorario getPanelHorario() {
		return panelHorario;
	}

	public void setPanelHorario(PanelHorario panelHorario) {
		this.panelHorario = panelHorario;
	}

	public PanelLista getPanelLista() {
		return panelLista;
	}

	public void setPanelLista(PanelLista panelLista) {
		this.panelLista = panelLista;
	}

}