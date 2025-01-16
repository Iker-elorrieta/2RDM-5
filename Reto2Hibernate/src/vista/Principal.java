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
	private JPanel panelContenedor;


	public static enum enumAcciones {
        CARGAR_PANEL_LOGIN, CARGAR_PANEL_MENU, LOGIN, DESCONECTAR, CARGAR_PANEL_HORARIO, CARGAR_PANEL_LISTA,VOLVER,TAREAS_PENDIENTES, CONFIRMAR_REUNION,RECHAZAR_REUNION,SELECCIONAR_PROFESOR

    }
	
	


	public Principal() {
		mCrearPanelContenedor();
		mCrearVLogin();
	}
	
	public void mVisualizarPaneles(enumAcciones panel) {

		panelLogin.setVisible(false);
        switch (panel) {
        case CARGAR_PANEL_LOGIN:
        	panelLogin.setVisible(true);
            break;
       
        default:
            break;
            }

        
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
		panelLogin = new PanelLogin();
		panelLogin.setLocation(0, 11);
		panelContenedor.add(panelLogin);
		panelContenedor.setBounds(panelLogin.getBounds());
		panelLogin.setVisible(true);
	}


	// *** FIN creaci�n de paneles ***

	

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






}