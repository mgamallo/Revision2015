package es.mgamallo.firma;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;




/**
 * @author Imaginanet
 * 
 * http://www.coderanch.com/how-to/javadoc/itext-2.1.7/com/lowagie/text/pdf/PdfSignatureAppearance.html
 * http://www.imaginanet.com/blog/firmar-y-verificar-firma-digital-en-pdfs-mediante-java-con-itext.html
 * http://www.bouncycastle.org/latest_releases.html
 * http://xml-utils.com/2006/11/26/firmando-documentos-pdf-con-itext/
 * http://switchoffandletsgo.blogspot.com.es/2008/07/importar-certificados-en-java.html#.UxMMNZWPKHs
 */
public class Firma {
	
	static KeyStore ks = null;

	public static boolean firmar(String ficheroOrigen, String ficheroDestino, String certificado, String clave){

		if(comprobarCertificado(certificado, clave)){
			try {
				String alias = (String)ks.aliases().nextElement();
				PrivateKey key = (PrivateKey)ks.getKey(alias, clave.toCharArray());
				Certificate[] chain = ks.getCertificateChain(alias);
				
				// Recibimos como parámetro de entrada el nombre del archivo PDF a firmar
	            try {
					PdfReader reader = new PdfReader(ficheroOrigen); 
					FileOutputStream fout = new FileOutputStream(ficheroDestino);

					// Añadimos firma al documento PDF
					PdfStamper stp = PdfStamper.createSignature(reader, fout, '?');
					PdfSignatureAppearance sap = stp.getSignatureAppearance();
					sap.setCrypto(key, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
					//sap.setReason("Firma PKCS12");
					sap.setLocation("CHOP");
										
					// Añade la firma visible. Podemos comentarla para que no sea visible.
					// sap.setVisibleSignature(new Rectangle(100,100,200,200),1,null);
					stp.close();
					
					return true;
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	
	
	private static boolean comprobarCertificado(String certificado, String clave){
		
		try {
			ks = KeyStore.getInstance("pkcs12");
			ks.load(new FileInputStream(certificado),clave.toCharArray());
			return true;
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}


