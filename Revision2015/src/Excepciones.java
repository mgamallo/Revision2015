
public class Excepciones {
	
	static public boolean excepcionesIngresos(int numPdf) {
		// TODO Auto-generated method stub
		if(Inicio.listaDocumentos[numPdf].nombreNormalizado.equals(Inicio.CONSENTIMIENTO) ){
			if(Inicio.listaDocumentos[numPdf].servicio.equals(Inicio.HOSP)){
				Inicio.listaDocumentos[numPdf].servicio = "X";
			}
			
			System.out.println("Esta es una excepción al ingreso");
			
			return true;
		}
		if(Inicio.listaDocumentos[numPdf].nombreNormalizado.equals(Inicio.CRIBADO)){
			Inicio.listaDocumentos[numPdf].servicio = Inicio.ORLC;
			
			System.out.println("Esta es una excepción al ingreso");
			
			return true;
		}

		return false;
	}

	static public boolean excepcionesNeuro(int numPdf){
		
		return false;
	}
	
	static public boolean detectaMonitoriz(int numPdf){
		return false;
	}
	
	static public boolean detectaDocRosaNeuro(int numPdf){
		return false;
	}
}
