import java.util.ArrayList;


public class TestLeerExcel {

	/**
	 * @param args
	 * 
	 * 
	 * 
	 */
	
	static Documento[] listaDocumentos;
	static Abrir abrir;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LeerExcel excel = new LeerExcel();
		
		excel.getTablaDocumentos("Documentos.xls");
		
		ArrayList<Modelo> modelos = new ArrayList<Modelo>();
		modelos = excel.leerModelos("DocumentosOCR.xls", false);
		
		/*
		for(int i=0; i<modelos.size();i++){
			System.out.println(modelos.get(i).servicios.get(0) + "  " + modelos.get(i).metadatos.metaServicioNombre);
		}
		*/
		
		abrir = new Abrir();
	
		listaDocumentos = new Documento[abrir.pdfs.length];
		for(int i=0;i<abrir.pdfs.length;i++){
			System.out.println(abrir.pdfs[i].getAbsolutePath());
			listaDocumentos[i] = new Documento(abrir.pdfs[i].getAbsolutePath());
			// System.out.println(listaDocumentos[i].cadenaOCR);
			for(int j=0;j<modelos.size();j++){
				if(listaDocumentos[i].detector(modelos.get(j))){
					break;
				}
					
			}
			
			/*
			System.out.println(listaDocumentos[i].nhc);
			System.out.println("Peso: " + listaDocumentos[i].fisica.peso);
			System.out.println("Dimensiones: " + listaDocumentos[i].fisica.dimensiones.alto + " , " + listaDocumentos[i].fisica.dimensiones.ancho);
			System.out.println("Formato: " + listaDocumentos[i].fisica.tamañoPagina);
			System.out.println("Orientación: " + listaDocumentos[i].fisica.vertical);
			System.out.println("Numero Páginas: " + listaDocumentos[i].fisica.numPaginas[0]);
			System.out.println("*************************************************************");
			*/
					
		}
		int errores = 0;
		for(int i=0;i<listaDocumentos.length;i++){
			if(!listaDocumentos[i].renombraFichero(listaDocumentos[0]))
				errores++;
		}
		System.out.println(errores + " errores");
		
		System.out.println(listaDocumentos[0].rutaArchivo);
	}

}
