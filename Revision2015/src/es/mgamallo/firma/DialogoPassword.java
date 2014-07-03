package es.mgamallo.firma;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class DialogoPassword extends JDialog{

	/**
	 * @param args
	 */
	
	private JPasswordField pField;
	private String password;
	
	public DialogoPassword(String tipoDeClave){
		setTitle("Introducir clave");
		setSize(200,100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		
		JPanel panel = new JPanel();
		JPanel panelSur = new JPanel();
		
		JLabel etiqueta = new JLabel("             Introduce " + tipoDeClave);
		
		pField = new JPasswordField(10);
		
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			char[] caracteres = pField.getPassword();
				
				password = "";
				for(int i=0;i< caracteres.length;i++){
					password += String.valueOf(caracteres[i]);
				}
				// System.out.println(password);
				dispose();
			}
		};
		
		pField.addActionListener(listener);
		
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(listener);
		
		panel.setLayout(new BorderLayout());
		panel.add(etiqueta,BorderLayout.NORTH);
		
		panelSur.setLayout(new FlowLayout());
		
		panelSur.add(pField);
		panelSur.add(botonAceptar);
		panel.add(panelSur,BorderLayout.CENTER);
		
		getContentPane().add(panel);
		
		setVisible(true);
		
		
	}
	
	public String getClave(){
		return password;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DialogoPassword dialogo = new DialogoPassword(" el pin");
		System.out.println(dialogo.getClave());
		// System.exit(0);
	}

}
