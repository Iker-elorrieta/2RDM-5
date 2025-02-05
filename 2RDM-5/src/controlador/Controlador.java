package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Profesor;
import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener, MouseListener {

	private vista.Principal vistaPrincipal;
	private Socket cliente;
	private DataOutputStream dos;
	private DataInputStream dis;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ArrayList<Profesor> profesores = new ArrayList<Profesor>();
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.vistaPrincipal.getPanelLogin().getBtnLogin().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnLogin().setActionCommand(Principal.enumAcciones.LOGIN.toString());
		this.vistaPrincipal.getPanelMenu().getBtnDesconectar().addActionListener(this);
		this.vistaPrincipal.getPanelMenu().getBtnDesconectar()
				.setActionCommand(Principal.enumAcciones.DESCONECTAR.toString());

		this.vistaPrincipal.getPanelMenu().getLblFotoAlumno().addMouseListener(this);
		this.vistaPrincipal.getPanelMenu().getLblFotoReuniones().addMouseListener(this);
		this.vistaPrincipal.getPanelMenu().getLblFotoHorario().addMouseListener(this);
		
		this.vistaPrincipal.getPanelHorario().getBtnVolver().addActionListener(this);
        this.vistaPrincipal.getPanelHorario().getBtnVolver().setActionCommand(Principal.enumAcciones.VOLVER.toString());
        
        this.vistaPrincipal.getPanelLista().getBtnSeleccionar().addActionListener(this);
		this.vistaPrincipal.getPanelLista().getBtnSeleccionar()
				.setActionCommand(Principal.enumAcciones.SELECCIONAR_PROFESOR.toString());
        this.vistaPrincipal.getPanelLista().getBtnVolver().addActionListener(this);
        this.vistaPrincipal.getPanelLista().getBtnVolver().setActionCommand(Principal.enumAcciones.VOLVER.toString());     
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		case LOGIN:
			incializarServidor();
			try {
				dos = new DataOutputStream(cliente.getOutputStream());
				oos = new ObjectOutputStream(cliente.getOutputStream());
				dis = new DataInputStream(cliente.getInputStream());
				ois = new ObjectInputStream(cliente.getInputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.mConfirmarLogin(accion);
			break;
		case DESCONECTAR:
			try {
				dos.writeInt(4);
				dos.flush();
				dis.close();
				dos.close();
				this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_LOGIN);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case SELECCIONAR_PROFESOR:
			seleccionarProfesor();
			this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_HORARIO);
			break;
		case VOLVER:
			this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_MENU);
			;
			break;

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
			//System.out.println("Hola");
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

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generate method stub

		Object source = e.getSource();
		this.vistaPrincipal.getPanelHorario().getBtnPendientes().setVisible(false);
		if (source == this.vistaPrincipal.getPanelMenu().getLblFotoHorario()) {
			mAbrirHorario();
		} else if (source == this.vistaPrincipal.getPanelMenu().getLblFotoAlumno()) {
			mAbrirOtrosHorario();
		}

	}

	private void cargarLista(ArrayList<String> datos) { // TODO Auto-generated
		String[] arrayDatos = datos.toArray(new String[0]);

		DefaultListModel<String> modelo = new DefaultListModel<>();
		for (String dato : arrayDatos) {
			modelo.addElement(dato);
		}

		this.vistaPrincipal.getPanelLista().getListaProfesor().setModel(modelo);
	}

	private void mAbrirHorario() {
		// TODO Auto-generated method stub
		this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_HORARIO);

		try {
			dos.writeInt(2);
			dos.flush();
			dos.writeInt(id);
			dos.flush();

			String[][] horario = (String[][]) ois.readObject();

			this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_HORARIO);
			cargarHorario(horario, this.vistaPrincipal.getPanelHorario().getTablaHorario());
		} catch (IOException | ClassNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

private void cargarHorario(String[][] horario, JTable tabla) {
        
        DefaultTableModel modelo = new DefaultTableModel(horario,
                new String[] { "Hora/Día", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" }) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabla.setModel(modelo);

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                
                JTextArea textArea = new JTextArea();
                textArea.setText(value == null ? "" : value.toString());
                textArea.setWrapStyleWord(true); 
                textArea.setLineWrap(true); 
                textArea.setOpaque(true); 
                
                if (value != null && value instanceof String) {
                    String cellValue = (String) value;

                    if (cellValue.contains("-R")) {
                        textArea.setBackground(Color.RED);
                        textArea.setForeground(Color.BLACK);
                    } else if (cellValue.contains("-C")) {
                        textArea.setBackground(Color.GREEN);
                        textArea.setForeground(Color.BLACK);
                    } else if (cellValue.contains("-P")) {
                        textArea.setBackground(Color.GRAY);
                        textArea.setForeground(Color.BLACK);
                    } else if (cellValue.contains("-E")) {
                        textArea.setBackground(Color.ORANGE);
                        textArea.setForeground(Color.BLACK);
                    } else {
                        textArea.setBackground(table.getBackground());
                        textArea.setForeground(table.getForeground());
                    }
                }

                if (isSelected) {
                    textArea.setBackground(table.getSelectionBackground());
                    textArea.setForeground(table.getSelectionForeground());
                }

                return textArea;
            }
        };

        
        for (int i = 1; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }


        tabla.setRowHeight(75); 
    }

	private void mAbrirOtrosHorario() {
		// TODO Auto-generated method stub

		this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_LISTA);
		profesores.removeAll(profesores);
		try {
			dos.writeInt(3);
			dos.flush();
			dos.writeInt(id);
			dos.flush();
			@SuppressWarnings("unchecked")
			ArrayList<String> profesoresLista = (ArrayList<String>) ois.readObject();
			for (String profesor : profesoresLista) {
				profesores.add(new Profesor(Integer.parseInt(profesor.split(";")[0]), profesor.split(";")[1]));
			}
			this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_PANEL_LISTA);
			ArrayList<String> modelo = new ArrayList<String>();
			for (Profesor profesor : profesores) {
				modelo.add(profesor.getNombre());
			}
			this.vistaPrincipal.getPanelLista().getBtnSeleccionar().setVisible(true);
			cargarLista(modelo);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void seleccionarProfesor() {
		// TODO Auto-generated method stub
		if (!this.vistaPrincipal.getPanelLista().getListaProfesor().isSelectionEmpty()) {
			try {
				dos.writeInt(2);
				dos.flush();
				int idprofesor = 0;
				for (Profesor profesor : profesores) {
					if (profesor.getNombre()
							.equals(this.vistaPrincipal.getPanelLista().getListaProfesor().getSelectedValue())) {
						idprofesor = profesor.getId();
					}
				}
				dos.writeInt(idprofesor);
				dos.flush();
				cargarHorario((String[][]) ois.readObject(), this.vistaPrincipal.getPanelHorario().getTablaHorario());

			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(null, "Debes seleccionar un profesor de la lista");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
