package vista;



import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;

public class VLogin  extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTextField textFieldUser;
	private JLabel lblUser;
	private JButton btnAceptar;
	private JLabel lblError;
	private JPasswordField textFieldPass;

	public VLogin() {
			
		setBackground(new Color(230, 230, 250));
		setBounds(288, 11, 688, 541);
		setLayout(null);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(192, 124, 211, 20);
		add(textFieldUser);
		textFieldUser.setColumns(10);

		lblUser = new JLabel("Usuario");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setBounds(54, 127, 105, 14);
		add(lblUser);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(192, 241, 216, 23);
		add(btnAceptar);

		lblError = new JLabel("Error al iniciar sesion");
		lblError.setForeground(Color.RED);
		lblError.setBounds(246, 309, 136, 14);
		add(lblError);
		
		JLabel lblPass = new JLabel("Contraseña");
		lblPass.setHorizontalAlignment(SwingConstants.CENTER);
		lblPass.setBounds(54, 173, 113, 14);
		add(lblPass);
		
		textFieldPass = new JPasswordField();
		textFieldPass.setColumns(10);
		textFieldPass.setBounds(192, 170, 211, 20);
		add(textFieldPass);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Arial", Font.BOLD, 16));
		lblLogin.setBounds(314, 37, 196, 54);
		add(lblLogin);
		lblError.setVisible(false);
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public JTextField getTextFieldUser() {
		return textFieldUser;
	}

	public void setTextFieldUser(JTextField textFieldUser) {
		this.textFieldUser = textFieldUser;
	}

	public JTextField getTextFieldPass() {
		return textFieldPass;
	}

	public void setTextPass(JPasswordField textFieldPass) {
		this.textFieldPass = textFieldPass;
	}

	public JLabel getLblError() {
		return lblError;
	}

	public void setLblError(JLabel lblError) {
		this.lblError = lblError;
	}
}
