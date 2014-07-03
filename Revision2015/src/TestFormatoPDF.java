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


public class TestFormatoPDF {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestFormatoPDF();
	}










	TestFormatoPDF(){
		
		try{
			String ruta = "h:\\pruebaman.pdf";
			String nuevaRuta = "h:\\pruebaman1.pdf";
		
			PdfReader pdf = new PdfReader(ruta);
			
			PdfStamper stp = new PdfStamper(pdf, new FileOutputStream(nuevaRuta));
			//PdfWriter writer = stp.getWriter();
			// PdfAction pdfAcc;
			
			/*
			if(tamañoHoja == 1){
				pdfAcc = PdfAction.gotoLocalPage(1, new PdfDestination(PdfDestination.XYZ,ancho*2/3,-1, 1), writer);
			}
			else{
				//pdfAcc = PdfAction.gotoLocalPage(1, new PdfDestination(PdfDestination.FIT,10000), writer);
				pdfAcc = PdfAction.gotoLocalPage(1,new PdfDestination(PdfDestination.FITV), writer);

			}
			
			*/
			// writer.setOpenAction(pdfAcc);
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
		}
	}		
		
}

