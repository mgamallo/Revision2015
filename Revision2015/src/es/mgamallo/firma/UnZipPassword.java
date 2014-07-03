package es.mgamallo.firma;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/*
 * http://stackoverflow.com/questions/11174851/how-to-use-zip4j-to-extract-an-zip-file-with-password-protection
 http://www.vogella.com/tutorials/EclipseGit/article.html
 */

public class UnZipPassword {

	public boolean descomprimir(String fichero, String destino, String password) {
		System.out.println("Empieza la descomprension");

		// String source = "prueba.zip";
		// String destination = "temp";
		// String password = "agua";

		try {
			ZipFile zipFile = new ZipFile(fichero);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			zipFile.extractAll(destino);
			System.out.println("Descomprimido");
			return true;
			
		} catch (ZipException e) {
			e.printStackTrace();
		}


		
		return false;
	}

}
