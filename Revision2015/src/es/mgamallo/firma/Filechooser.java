package es.mgamallo.firma;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

public class Filechooser {

	/**
	 * @param args
	 */
	

	Carpeta carpetas[];
	
	
	static String rutaDePartida = "K:\\DIGITALIZACIÓN\\00 DOCUMENTACION\\04 Asociado\\2014\\03 Marzo";

	
	public void seleccionManual(String rutaDePartida){
		
		JFileChooser fileChooser = new JFileChooser(new File(rutaDePartida));
		fileChooser.setDialogTitle("Seleccionar carpetas...");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Documentos PDF", "pdf"));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		
		File[] directorios;
		
		int seleccion = fileChooser.showOpenDialog(null);
		if(seleccion == JFileChooser.APPROVE_OPTION){
			directorios = fileChooser.getSelectedFiles();

			int tamaño = directorios.length;
			carpetas = new Carpeta[tamaño];
			
			for(int i=0;i < tamaño;i++){
				carpetas[i] = new Carpeta(directorios[i]);
			}
			
			// imprimir(tamaño);
		}
		
	}
	
	/*
	 * Método para imprimir. Sólo es de prueba borrar
	 * 
	 * @param tamaño: número de carpetas elegidas
	 */
	
	public void imprimir(int tamaño){
		for(int i=0;i<tamaño;i++){
			System.out.println("Carpeta nº " + (i+1));
			for(int j=0;j<carpetas[i].pdfs.length;j++){
				System.out.println("\t" + carpetas[i].pdfs[j].getName().toString());
			}
			System.out.println();
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Filechooser().seleccionManual(rutaDePartida);
		
		
	}

}

class Carpeta{
	
	File rutaCarpeta;
	File[] pdfs;
	
	public Carpeta(File rutaCarpeta){
		this.rutaCarpeta = rutaCarpeta;
		setPdfs();
	}
	
	private void setPdfs(){
		pdfs = rutaCarpeta.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File directorio, String name) {
				// TODO Auto-generated method stub
				return name.toLowerCase().endsWith(".pdf");
			}
		});
	}
}