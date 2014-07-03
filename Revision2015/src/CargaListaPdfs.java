import java.io.File;


public class CargaListaPdfs {

	String[] nombrePdfs;	//Sólo el nombre
	String[] rutaPdfs;		//path + nombre
	String rutaCarpeta;
	
	File[] ficheros;
	
	boolean cargado = false;
	
	

	
	CargaListaPdfs(boolean renombrar) {
		
		AbrirCarpeta carpeta = new AbrirCarpeta(renombrar);
		
		if(carpeta.eligeDirectorio == true){
			cargado = true;
			ficheros = carpeta.getPdfs(renombrar);
			
			int tamañoDir = ficheros.length;
			nombrePdfs = new String[tamañoDir];
			rutaPdfs = new String[tamañoDir];
			rutaCarpeta = carpeta.rutaCarpeta;
			Inicio.rutaDirectorio = rutaCarpeta;
		
			for(int i=0;i<tamañoDir;i++){
				nombrePdfs[i] = ficheros[i].getName();
				rutaPdfs[i] = ficheros[i].getAbsolutePath();
			}
		}
	}
	
	
	
	String[] getNombresPdfs(){
		return nombrePdfs;
	}
	
	String[] getRutaPdfs(){
		return rutaPdfs;
	}
	
	String getRutaCarpeta(){
		return rutaCarpeta;
	}
	
}
