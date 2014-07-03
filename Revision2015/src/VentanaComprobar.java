import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;


public class VentanaComprobar extends JDialog {
	

	JPanel panel = new JPanel();
	JPanel panelSur = new JPanel();
	
	JButton botonSeguir = new JButton("Continuar");
	JButton botonCorregir = new JButton("Corregir");
	
	JTextArea areaTexto = new JTextArea(400, 300);

	ArrayList<Integer> listaPdfsGrandes = new ArrayList<Integer>();
	
	ArrayList<Integer> listaSinNHC = new ArrayList<Integer>();
	ArrayList<Integer> listaSinServicio = new ArrayList<Integer>();
	ArrayList<Integer> listaSinNombre = new ArrayList<Integer>();
	
	VentanaComprobar(){
		
		if(getErrores()){
			Inicio.erroresAntesRegistrar = true;
			init();
		}
		else{
			Inicio.erroresAntesRegistrar = false;
		}
	}
	
	
	void init(){
		setSize(350,250);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModal(false);
		
		panel.setLayout(new BorderLayout());
		
		panelSur.add(botonSeguir);
		panelSur.add(botonCorregir);
		
		panel.add(areaTexto,BorderLayout.CENTER);
		//panel.add(panelSur, BorderLayout.SOUTH);
		areaTexto.setText(imprimeErrores());
		
		botonSeguir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Inicio.erroresAntesRegistrar = false;
				dispose();
			}
		});
		
		botonCorregir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setModal(false);
			}
		});
		
		getContentPane().add(panel);
		
		Rectangle rectangulo = Inicio.ventanaCompacta.getBounds();
        setLocation(rectangulo.x,rectangulo.y + rectangulo.height);
		
		setVisible(true);
		
	}

	
	boolean compruebaTamañoPdf(int indice){
		
		if(Inicio.listaDocumentos[indice].fisica.peso > 11900){
			return false;
		}
		return true;
	}
	
	
	boolean compruebaNHC(int indice){
		if(Inicio.listaDocumentos[indice].nhc.contains("ERROR")){
			return false;
		}
		if(Inicio.listaDocumentos[indice].nhc.equals("")){
			return false;
		}
		if(Inicio.listaDocumentos[indice].nhc.equals("X")){
			return false;
		}
		if(Inicio.listaDocumentos[indice].nhc.equals("NO")){
			return false;
		}
		if(Inicio.listaDocumentos[indice].nhc.equals("Separador")){
			return true;
		}
		return true;
	}
	
	
	boolean compruebaServicio(int indice){
		
		if(Inicio.listaDocumentos[indice].servicio.equals("X") ){
			return false;
		}
		
		return true;
	}
	
	
	boolean compruebaNombresNormalizado(int indice){
		
		if(Inicio.listaDocumentos[indice].nombreNormalizado.equals("X") && !Inicio.listaDocumentos[indice].nhc.equals("Separador")){
			return false;
		}
		return true;
	}
	
	String imprimeErrores(){
		
		String cadena = "";   //"Esta es una prueba. No hacer mucho caso...\n\n";
		
		for(int i=0;i<listaPdfsGrandes.size();i++){
			if(i==0){
				cadena += "Los siguientes pdfs tienen un tamaño demasiado grande: \n\n   ";
			}
			cadena += ((listaPdfsGrandes.get(i) + 1) + ", ");
		}
		if(listaPdfsGrandes.size() > 0)
		{
			cadena += "\n\n";
		}
		
		for(int i=0;i<listaSinNHC.size();i++){
			if(i==0){
				cadena += "Los siguientes pdfs no tienen un nhc adecuado: \n\n   ";
			}
			cadena += ((listaSinNHC.get(i)+1) + ", ");
		}
		if(listaSinNHC.size() > 0)
		{
			cadena += "\n\n";
		}
		
		for(int i=0;i<listaSinServicio.size();i++){
			if(i==0){
				cadena += "Los siguientes pdfs no tienen un servicio adecuado: \n\n   ";
			}
			cadena += ((listaSinServicio.get(i)+1) + ", ");
		}
		if(listaSinServicio.size() > 0)
		{
			cadena += "\n\n";
		}
		
		for(int i=0;i<listaSinNombre.size();i++){
			if(i==0){
				cadena += "Los siguientes pdfs no tienen un nombre normalizado: \n\n   ";
			}
			cadena += ((listaSinNombre.get(i)+1) + ", ");
		}
		if(listaSinNombre.size() > 0)
		{
			cadena += "\n\n";
		}
		
		return cadena;
	}
	
	public boolean getErrores(){
		boolean errores = false;
		
		//
		//	Ojo almacena el pdf más un uno
		//
		
		for(int i=0;i< Inicio.listaDocumentos.length;i++){
						
			if(!compruebaTamañoPdf(i)){
				listaPdfsGrandes.add(i);
				errores = true;
			}
			if(!compruebaNHC(i)){
				listaSinNHC.add(i);
				errores = true;
			}
			if(!compruebaServicio(i)){
				listaSinServicio.add(i);
				errores = true;
			}
			if(!compruebaNombresNormalizado(i)){
				listaSinNombre.add(i);
				errores = true;
			}
			
		}
		
		return errores;
	}
}
