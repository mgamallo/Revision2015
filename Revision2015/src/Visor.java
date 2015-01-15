import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class Visor extends JDialog{

	/**
	 * @param args
	 */
	JLabel nombreDocLabel = new JLabel();
	JButton botonI = new JButton("<");
	JButton botonD = new JButton(">");
	JLabel fotoLabel = new JLabel();
	JScrollPane scroll = new JScrollPane();
	ImageIcon imagen;
	
	JLabel obsvLabel = new JLabel("Observaciones:");
	JLabel contObsvLabel = new JLabel("Aquí van las observaciones");
	
	URL urlDeLaImagen;
	
	int fotoVisible;
	
	Visor(String nombreDocumento, String nombreJpg){
		
		if(Inicio.numeroPantallas == 2){
			this.setLocation(1100, 10);
		}
		else{
			this.setLocation(1100, 10);
		}
		
		setSize(700,900);
		JPanel panelUp = new JPanel();
		JPanel panelDw = new JPanel();
		
		this.setModal(true);

		

		imagen = new ImageIcon( "Imagenes\\600x800\\" + nombreJpg + ".jpg");
		fotoLabel.setIcon(imagen);

		
	    nombreDocLabel.setText(nombreDocumento);
	    nombreDocLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    nombreDocLabel.setFont(new Font("TimesRoman",Font.BOLD,30));
	    nombreDocLabel.setForeground(Color.red);
	    
	    fotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    scroll.setViewportView(fotoLabel);
	    
	    panelUp.setBackground(Color.PINK);
		panelUp.setLayout(new BorderLayout());
		panelUp.add(scroll,BorderLayout.CENTER);
		panelUp.add(nombreDocLabel,BorderLayout.NORTH);
		
		
		setLayout(new BorderLayout());
		add(panelUp,BorderLayout.CENTER);
		add(panelDw,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		
	}
	
	
	
	Visor(final ArrayList<String> nombresDocumentos, final ArrayList<String> rutasJpgs,final int fotoVisionar, final ArrayList<String> observaciones){
		
		if(Inicio.numeroPantallas == 2){
			this.setLocation(1100, 10);
		}
		else{
			this.setLocation(1100, 10);
		}
		
		setSize(700,900);
		JPanel panelUp = new JPanel();
		JPanel panelDw = new JPanel();
		
		fotoVisible = fotoVisionar;
		
	//	urlDeLaImagen = this.getClass().getResource("Imagenes\\600x800\\" + rutasJpgs.get(fotoVisionar));
		imagen = new ImageIcon("Imagenes\\600x800\\" + rutasJpgs.get(fotoVisionar));
	//	imagen.getImage().flush();
		fotoLabel.setIcon(imagen);
		
	    botonD.addActionListener(new ActionListener(){
	    	public void actionPerformed(java.awt.event.ActionEvent evt) {
	    		if(fotoVisible < rutasJpgs.size()-1){
	    			fotoVisible++;
	        		imagen = new ImageIcon("Imagenes\\600x800\\" + rutasJpgs.get(fotoVisible));
		    		imagen.getImage().flush();
		    		fotoLabel.setIcon(imagen);
		    		nombreDocLabel.setText(nombresDocumentos.get(fotoVisible));
		    		contObsvLabel.setText(observaciones.get(fotoVisible));
	    		}
	    	}
	    });
		
	    botonI.addActionListener(new ActionListener(){
	    	public void actionPerformed(java.awt.event.ActionEvent evt) {
	    		if(fotoVisible > 0){
	    			fotoVisible--;
	        		imagen = new ImageIcon("Imagenes\\600x800\\" + rutasJpgs.get(fotoVisible));
		    		imagen.getImage().flush();
		    		fotoLabel.setIcon(imagen);
		    		nombreDocLabel.setText(nombresDocumentos.get(fotoVisible));
		    		contObsvLabel.setText(observaciones.get(fotoVisible));
	    		}
	    	}
	    });
		
	    nombreDocLabel.setText(nombresDocumentos.get(fotoVisible));
	    nombreDocLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    nombreDocLabel.setFont(new Font("TimesRoman",Font.BOLD,30));
	    nombreDocLabel.setForeground(Color.red);
	    
	    fotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    scroll.setViewportView(fotoLabel);
	    
		panelUp.setLayout(new BorderLayout());
		panelUp.add(botonI,BorderLayout.WEST);
		panelUp.add(scroll,BorderLayout.CENTER);
		panelUp.add(botonD,BorderLayout.EAST);
		panelUp.add(nombreDocLabel,BorderLayout.NORTH);
		
		panelDw.setLayout(new BorderLayout());
		panelDw.add(obsvLabel,BorderLayout.WEST);
		panelDw.add(contObsvLabel,BorderLayout.CENTER);
		
		obsvLabel.setForeground(Color.black);
		obsvLabel.setFont(new Font("TimesRoman",Font.BOLD,20));
		
		contObsvLabel.setText(observaciones.get(fotoVisible));
		contObsvLabel.setForeground(Color.red);
		contObsvLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contObsvLabel.setFont(new Font("TimesRoman",Font.PLAIN,25));
		obsvLabel.setFont(new Font("TimesRoman",Font.PLAIN,20));
		
		setLayout(new BorderLayout());
		add(panelUp,BorderLayout.CENTER);
		add(panelDw,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		
	}
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Visor v = new Visor();
	}
*/
}
