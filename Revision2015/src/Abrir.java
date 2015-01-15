import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;




public class Abrir {
	
	File[] pdfs;
	String rutaDirectorio = "";
	
	Abrir() {
		// TODO Auto-generated method stub

		AbrirCarpetaR ac = new AbrirCarpetaR();
		rutaDirectorio = ac.nombreCarpeta;
		
		File directorio = new File(ac.getRuta());
		
		/*
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection texto = new StringSelection(directorio.getAbsolutePath().toString());
		cb.setContents(texto, texto);
		*/
		
		pdfs = directorio.listFiles(new FilenameFilter(){
				public boolean accept(File directorio, String name){
					return name.toLowerCase().endsWith(".pdf");
				}
			});
		
		/*
		LeerPdf pdf = new LeerPdf();
		
		int numErrores = 0;
		int numCorrectos = 0;
		for(int i= 0;i<pdfs.length;i++){
			Boolean error = pdf.getPropiedades(pdfs[i].getAbsolutePath());
			if(error) numErrores++;
			else numCorrectos++;
		}
		
		JOptionPane.showMessageDialog(null, "Añadido formato a " + numCorrectos + " ficheros. " + numErrores + " errores en el proceso.");
		*/
	}
}

class AbrirCarpetaR {
	String ruta = "j:/digitalización/00 documentacion/01 Escaneado"; 
	String rutab = "h:/digitalización/00 documentacion/01 Escaneado";											// trabajo
	String nombreCarpeta;
	JFileChooser explorador;
	boolean eligeDirectorio;
		
	AbrirCarpetaR(){
		/*
		if(!InicioIanus.documentacion){
			ruta = InicioIanus.RUTAURG;
			rutab = InicioIanus.RUTAURGB;
		}
		*/
		eligeDirectorio = listaPdfs();
		if (eligeDirectorio){
			nombreCarpeta = explorador.getSelectedFile().toString();
			System.out.println(nombreCarpeta);
		}
	}

	
	boolean listaPdfs(){
		explorador = new JFileChooser();
		explorador.setDialogTitle("Abrir carpeta...");
		if(!(new File(ruta).exists()))
			ruta = rutab;
		explorador.setCurrentDirectory(new File(ruta));
		explorador.setFileFilter(new FileNameExtensionFilter("Documentos PDF","pdf"));
		explorador.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int seleccion = explorador.showOpenDialog(null);
		
		if( seleccion == JFileChooser.APPROVE_OPTION){			
				return true;
		}
		else
			return false;
	}
	
	String getRuta(){
		return nombreCarpeta;
	}
	
}