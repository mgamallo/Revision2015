import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;


public class Mapa {

	static String separadorApartir = "HFTRNT";
	
	String rutaArchivo = "";
	String textoPag1 = "";
	
	String nhc = "";
	
	Fisica fisica = new Fisica();
	
	Mapa(String ruta){
		rutaArchivo = ruta;
		getPropiedades(ruta);
		
		
	}
	
	
	void getPropiedades(String ruta){

		int tamañoHoja = 0;  //	-1 menor que un A4; 1 > A3

		String orientacion = "V";
		String dimensiones = "A4";
	
		try{
			PdfReader pdf = new PdfReader(ruta);
			
			textoPag1 = PdfTextExtractor.getTextFromPage(pdf, 1);
			
			fisica.numPaginas = pdf.getNumberOfPages();
			Rectangle tamañoPagina = pdf.getPageSize(1);
			
			getTamañoHoja(tamañoPagina);
			if(fisica.vertical == 2){
				orientacion = "H";
			}else{
				orientacion = "V";
			}
			if(fisica.tamañoPagina == 1){
				dimensiones = "A3";
			}else if(fisica.tamañoPagina == -1){
				dimensiones = "A5";
			}else{
				dimensiones = "A4";
			}
			
			File fichero = new File(ruta);
			fisica.peso = (int) fichero.length() / 1024;
			
			//	Obtenemos el nhc y renombramos el fichero
			nhc = getNHC();
			// renombraFichero(pdf);
		
			pdf.close();    // Ojjjjjjjjjjjjjjjjjjjjooooooooooooooooooooooooo, me había olvidado
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	void getTamañoHoja(Rectangle tamañoPagina){
			
			int alto = (int)tamañoPagina.getHeight();
			int ancho = (int) tamañoPagina.getWidth();
			
			fisica.dimensiones.alto = alto;
			fisica.dimensiones.ancho = ancho;
			
			System.out.println("alto: " + alto + " ancho: " + ancho);
			
			if(alto >= ancho){
				fisica.vertical = 1;
			}else{
				fisica.vertical = 2;
			}
			
			if(alto> 1000 || ancho > 1000 ){
				fisica.tamañoPagina = 1;
			}else if(alto< 700 && ancho <700){
				fisica.tamañoPagina = -1;
			}else if(alto<450 || ancho < 450){
				fisica.tamañoPagina = -1;
			}
			else{
				fisica.tamañoPagina = 0;
			}
		}
	
	
	String getNHC(){

			String apartir = getApartir(textoPag1);
			if(apartir.equals("NO")){
				Etiqueta etiqueta = new Etiqueta(textoPag1);
				
				System.out.println("El tipo de etiqueta es... " + etiqueta.tipo);
				if(etiqueta.tipo == 0){
					return etiqueta.nhc;  // "NO encontró el tipo de etiqueta"
				}
				else{
					return etiqueta.nhc;
				}
			}
			else if(apartir.equals("SI")){
				return "Separador";
			}
	
		return "";
	}
	
	
	String getApartir(String cadenaBruta){
		String sub = "";
		if(cadenaBruta.length() > 11){
			sub = cadenaBruta.substring(0, 10);
		}
		else{
			return "NO";
		}
		
		System.out.println(sub);
		if (sub.contains(separadorApartir)){
				return "SI";
		}
		else if (cadenaBruta.contains("A PARTIR DE")){
			return "SI";
		}
				
		return "NO";
	}
	
	
	boolean renombraFichero(PdfReader pdf){
				
		int indice= rutaArchivo.lastIndexOf(".pdf");
		String nuevaRuta = rutaArchivo.substring(0, indice);
		nuevaRuta = nuevaRuta + " " + nhc + " r.pdf";
		
		
		 
		try {
			PdfStamper stp = new PdfStamper(pdf, new FileOutputStream(nuevaRuta));
			PdfWriter writer = stp.getWriter();
			PdfAction pdfAcc;
			
			if(this.fisica.tamañoPagina == 1){
				pdfAcc = PdfAction.gotoLocalPage(1, new PdfDestination(PdfDestination.XYZ,fisica.dimensiones.ancho*2/3,-1, 1), writer);
				writer.setOpenAction(pdfAcc);
			}
			
			/*else{
				//pdfAcc = PdfAction.gotoLocalPage(1, new PdfDestination(PdfDestination.FIT,10000), writer);
				pdfAcc = PdfAction.gotoLocalPage(1,new PdfDestination(PdfDestination.FITV), writer);

			}
			*/
			
			stp.close();
			
			
			pdf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*  ***********************************************  Para borrar el fichero original 
		File ficheroBorrar = new File(rutaArchivo);
		if(ficheroBorrar.delete()){
			System.out.println("Fichero Borrado");;
		}
		else{
			JOptionPane.showMessageDialog(null, "Fichero no borrado. Debe de estar en uso.");
			return false;
		}
		*/
		return true;
		
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
	}
	
	
}
