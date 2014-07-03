import java.util.ArrayList;


public class Modelo {
	
	String rutaImagen ="";
	String nombreNormalizado ="";
	ArrayList<String> servicios = new ArrayList<String>();           // 0 los propios del nombre Normalizado
	
	Fisica fisica = new Fisica();
	
	Metadatos metadatos = new Metadatos();
	
	String nombreAlternativo = "";
	
	String instruccionesNHC = "";
	
	void setMetadatos(){
		
	}
	
	void setFisica(){
		
	}
	
	void setServiciosModelo(String cadenaExcel){
		if(!cadenaExcel.equals("0")){
			servicios.add(cadenaExcel);
		}
		else{
			servicios.add("X");
		}
	}
}

class Metadatos{
	
	String metaLocalizacion[] = new String[2];
	
	String metaServicioNombre = "";
	String metaNombre = "";
	String metaServicio = "";
	String metaModelo = "";
	
	ArrayList<String> metaAuxiliares = new ArrayList<String>();
	
	
	
}