import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;


public class TestEtiqueta {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new PdfPrincipal();
	}
}


class PdfPrincipal {

	/**
	 * @param args
	 */
	

}




class LeerPdf {
	
	Boolean horizontal = false;
	int numPaginas = 0;
	int tamañoHoja = 0;  //	-1 menor que un A4; 1 > A3
	int ancho = 0;
	int alto = 0;
	String orientacion = "V";
	String dimensiones = "A4";
	
	String getMetadatos(String ruta){
		try {
			PdfReader pdf = new PdfReader(ruta);
			
			String textoPag1 = PdfTextExtractor.getTextFromPage(pdf, 1);
			
			//System.out.println(textoPag1 );
			
			Mapa mapa = new Mapa(ruta);
			String apartir = mapa.getApartir(textoPag1);
			System.out.println(apartir);
			if(apartir.equals("NO")){
				Etiqueta etiqueta = new Etiqueta(textoPag1);
				return etiqueta.nhc;
			}
			else if(apartir.equals("SI")){
				return "Separador";
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	boolean getPropiedades(String ruta){
		try{
			PdfReader pdf = new PdfReader(ruta);
			
			boolean error = false;
			
			numPaginas = pdf.getNumberOfPages();
			Rectangle tamañoPagina = pdf.getPageSize(1);
			
			this.getTamañoHoja(tamañoPagina);
			if(horizontal){
				orientacion = "H";
			}else{
				orientacion = "V";
			}
			if(tamañoHoja == 1){
				dimensiones = "A3";
			}else if(tamañoHoja == -1){
				dimensiones = "A5";
			}else{
				dimensiones = "A4";
			}
			
			System.out.println(ruta);
			
			String numHC = getMetadatos(ruta);
			
			int indice= ruta.lastIndexOf(".pdf");
			String nuevaRuta = ruta.substring(0, indice);
			nuevaRuta = nuevaRuta + " " + numHC + " r.pdf";
			
			
			PdfStamper stp = new PdfStamper(pdf, new FileOutputStream(nuevaRuta));
			PdfWriter writer = stp.getWriter();
			PdfAction pdfAcc;
			
			if(tamañoHoja == 1){
				pdfAcc = PdfAction.gotoLocalPage(1, new PdfDestination(PdfDestination.XYZ,ancho*2/3,-1, 1), writer);
			}
			else{
				//pdfAcc = PdfAction.gotoLocalPage(1, new PdfDestination(PdfDestination.FIT,10000), writer);
				pdfAcc = PdfAction.gotoLocalPage(1,new PdfDestination(PdfDestination.FITV), writer);

			}
			
			writer.setOpenAction(pdfAcc);
			stp.close();
			
			
			pdf.close();
			
			/*
			File ficheroBorrar = new File(ruta);
			if(ficheroBorrar.delete()){
				System.out.println("Fichero Borrado");;
			}
			else{
				JOptionPane.showMessageDialog(null, "Fichero no borrado. Debe de estar en uso.");
				error = true;
			}
			*/
			
			return error;
			
			/*
			File fichero = new File(ruta);
	//		System.out.println(ruta);
			int aux = ruta.lastIndexOf(".");
			ruta = ruta.substring(0,aux);
			File ficheroN = new File(ruta + " @" + orientacion + 
					numPaginas + dimensiones + " " + alto + "-" + ancho + ".pdf");
	//		System.out.println(ficheroN.getAbsolutePath());
			boolean renombrado = fichero.renameTo(ficheroN);
			if(renombrado){
				System.out.println("Renombrado");
			}else{
				System.out.println("Error");
			}
			*/
		
		}catch(Exception e){
			e.printStackTrace();
			return true;
		}
		
		
	}
	
	void getTamañoHoja(Rectangle tamañoPagina){
		
		alto = (int)tamañoPagina.getHeight();
		ancho = (int) tamañoPagina.getWidth();
		
		System.out.println("alto: " + alto + " ancho: " + ancho);
		
		if(alto >= ancho){
			horizontal = false;
		}else{
			horizontal = true;
		}
		
		if(alto> 1000 || ancho > 1000 ){
			tamañoHoja = 1;
		}else if(alto< 700 && ancho <700){
			tamañoHoja = -1;
		}else{
			tamañoHoja = 0;
		}
	}
	
}