import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public class RotarEkg {

	public void rotarEkg(String src, String dest){
		try {
			PdfReader reader = new PdfReader(src);
			
			for(int k = 1; k <= reader.getNumberOfPages();k++){
				for(int z=1;z <= reader.getNumberOfPages();z++){
					Rectangle formatoPagina = reader.getPageSize(z);
					int alto = (int)formatoPagina.getHeight();
					int ancho = (int) formatoPagina.getWidth();
					//	Hoja vertical
					if(alto >= ancho){
						reader.getPageN(z).put(PdfName.ROTATE, new PdfNumber(90));
					}
				}
			}
			
			PdfStamper stp = new PdfStamper(reader, new FileOutputStream(new File(dest)));
			

			stp.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static public void main(String args[]){
		RotarEkg rotar = new RotarEkg();
		rotar.rotarEkg("originalcardio.pdf", "rotado.pdf");
		System.out.println("Terminado");
	}
}
