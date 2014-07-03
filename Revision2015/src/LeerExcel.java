import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CountryCode;
import jxl.read.biff.BiffException;


public class LeerExcel {

	String[][] tablaDocumentos;
	String[] listaDocumentos;
	String[] listaServicios;
	
	DefaultListModel listaServiciosLista;
	DefaultListModel listaDocumentosDLM;
	DefaultListModel listaHabituales;
    DefaultListModel listaHabituales2;
    DefaultListModel listaHabitualesUrg;
    
    DefaultListModel vinculacionServicio;
    
    int numServicios = 0;
    int numDocumentos = 0;
    
    String[] habituales;
    String[] habituales2;
    String[] habitualesUrg;
    
    Object[][] tablaCoordenadas;
    Object[][] tablaVisor;
    DefaultListModel listaUsuariosLista;
    DefaultListModel listaUsuariosListaUrg;
    DefaultComboBoxModel listaUsuarios;
    DefaultComboBoxModel listaUsuariosUrg;
    
    boolean coordenadasGrabadas = false;
	
	void getTablaDocumentos(String archivo){
        
        
		try {
		
			
			WorkbookSettings wbSettings = new WorkbookSettings();
	        wbSettings.setEncoding("ISO-8859-1");
	        wbSettings.setLocale(new Locale("es", "ES"));
	        wbSettings.setExcelDisplayLanguage("ES"); 
	        wbSettings.setExcelRegionalSettings("ES"); 
	        wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());

	        Workbook archivoExcel = Workbook.getWorkbook(new File(archivo));
		
	        Sheet hoja = archivoExcel.getSheet(0);
	        int numColumnas =hoja.getColumns();
	        int numFilas = hoja.getRows();
	        
	        //System.out.println("El numero de columnas es... " + numColumnas);
	        
	        numServicios = numColumnas-3;
	        numDocumentos = numFilas-1;
	        
	        tablaDocumentos = new String[numFilas][numColumnas];
	        listaServicios = new String[numColumnas-3];
	        listaDocumentos = new String[numFilas-1];
	        
	        for (int fila=0;fila<numFilas;fila++){
                for(int columna=0;columna<numColumnas;columna++){					
                	tablaDocumentos[fila][columna] = hoja.getCell(columna, fila).getContents();
                	if(fila == 0){
                		if(columna >1 && columna != numColumnas-1){
                			listaServicios[columna-2] = hoja.getCell(columna, fila).getContents();
                		}
                	}
                	if(columna == 0){
                		if(fila >0){
                			listaDocumentos[fila-1] = hoja.getCell(columna, fila).getContents();
                		}
                	}
                		// System.out.print(tablaDocumentos[fila][columna].toString());
                }
                // System.out.println();
	        }
	        
	        listaServiciosLista = new DefaultListModel();
	        for(int i=0;i<numColumnas-4;i++){
	        	System.out.println(listaServicios[i].toString());
	        	listaServiciosLista.addElement(listaServicios[i]);
	        }
	        
	    //  Leer lista de todos los documentos        
            listaDocumentosDLM = new DefaultListModel();
            for(int i=0;i<listaDocumentos.length;i++){
                listaDocumentosDLM.addElement(listaDocumentos[i]);
            }   
	        
	        
	    //  Leer hoja excel habituales
            hoja=archivoExcel.getSheet(1);
            int numFilasH = hoja.getRows();
            habituales = new String[numFilasH];
            listaHabituales = new DefaultListModel();
            for(int i=0;i<numFilasH;i++){
                listaHabituales.addElement(hoja.getCell(0, i).getContents().toString());
                habituales[i] = listaHabituales.getElementAt(i).toString();
            }
            
            //  Leer hoja excel habituales2
            hoja=archivoExcel.getSheet(2);
            int numFilasH2 = hoja.getRows();
            habituales2 = new String[numFilasH2];
            listaHabituales2 = new DefaultListModel();
            for(int i=0;i<numFilasH2;i++){
                listaHabituales2.addElement(hoja.getCell(0, i).getContents().toString());
                habituales2[i] = listaHabituales2.getElementAt(i).toString();
            } 
	        
            //  Leer hoja excel habitualesUrg
            hoja=archivoExcel.getSheet(15);
            int numFilasHu = hoja.getRows();
            habitualesUrg = new String[numFilasHu];
            listaHabitualesUrg = new DefaultListModel();
            for(int i=0;i<numFilasHu;i++){
                listaHabitualesUrg.addElement(hoja.getCell(0, i).getContents().toString());
                habitualesUrg[i] = listaHabitualesUrg.getElementAt(i).toString();
            } 
            
            
//        	Leer hoja excel usuarios
            hoja=archivoExcel.getSheet(13); 
            int numFilasUs = hoja.getRows();
            System.out.println("número de filas " + numFilasUs);
            int numColumUs = hoja.getColumns();
            listaUsuarios = new DefaultComboBoxModel();
            listaUsuariosLista = new DefaultListModel();
            for(int i=1;i<numFilasUs;i++){
            	listaUsuarios.addElement(hoja.getCell(0, i).getContents().toString());
            	listaUsuariosLista.addElement(hoja.getCell(0, i).getContents().toString());
            	System.out.println(hoja.getCell(0, i).getContents().toString());
            }
                                    
            tablaCoordenadas = new Object[numFilasUs-1][numColumUs];
            
            //	Leer coordenadas usuarios
            for (int fila=0;fila<numFilasUs-1;fila++){
                for(int columna=0;columna<numColumUs;columna++){
                	if(hoja.getCell(columna,fila+1).getContents()!="")
                		tablaCoordenadas[fila][columna] = hoja.getCell(columna, fila+1).getContents();
                	else
                		tablaCoordenadas[fila][columna] = 0;

                }
            } 
            
            
            for(int i=0;i<numFilasUs-1;i++){
            	for(int j=0;j<numColumUs;j++){
            		System.out.print(tablaCoordenadas[i][j] + "   ");
            	}
            	System.out.println();
            }
            
            
//        	Leer hoja excel usuariosUrgencias
            hoja=archivoExcel.getSheet(14);
            int numFilasUsUrg = hoja.getRows();
            System.out.println("Numero filas urgencias " + numFilasUsUrg);
            int numColumUsUrg = hoja.getColumns();
            System.out.println("Numero columnas urgencias " + numColumUsUrg);
            
            listaUsuariosUrg = new DefaultComboBoxModel();
            listaUsuariosListaUrg = new DefaultListModel();
            for(int i=1;i<numFilasUsUrg;i++){
            	listaUsuariosUrg.addElement(hoja.getCell(0, i).getContents().toString());
            	listaUsuariosListaUrg.addElement(hoja.getCell(0, i).getContents().toString());
            	System.out.println("hoja 14 " + hoja.getCell(0, i).getContents().toString());
            }
            
            
            
            /*
            tablaCoordenadas = new Object[numFilasUsUrg-1][numColumUsUrg];
                   
            
            //	Leer coordenadas usuariosUrgencias
            for (int fila=0;fila<numFilasUsUrg-1;fila++){
                for(int columna=0;columna<numColumUsUrg;columna++){
                	if(hoja.getCell(columna,fila+1).getContents()!="")
                		tablaCoordenadas[fila][columna] = hoja.getCell(columna, fila+1).getContents();
                	else
                		tablaCoordenadas[fila][columna] = 0;

                }
            } 
            */
            
//        	Leer cuadro documentos Visor
            hoja = archivoExcel.getSheet(4);
            int numFilasVi = hoja.getRows();
            int numColumVi = hoja.getColumns();
            
            tablaVisor = new Object[numFilasVi][numColumVi];
            
            for (int fila=0;fila<numFilasVi;fila++){
                    for(int columna=0;columna<numColumVi;columna++){					
                    tablaVisor[fila][columna] = hoja.getCell(columna, fila).getContents();
                    }
            }
	        
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        			
		

	}
	
	
	public ArrayList<Modelo> leerModelos(String archivo, boolean documentacionDeUrgencias){

		ArrayList<Modelo> listaModelos = new ArrayList<Modelo>();
		
		
		try {
			
			
			
			WorkbookSettings wbSettings = new WorkbookSettings();
	        wbSettings.setEncoding("ISO-8859-1");
	        wbSettings.setLocale(new Locale("es", "ES"));
	        wbSettings.setExcelDisplayLanguage("ES"); 
	        wbSettings.setExcelRegionalSettings("ES"); 
	        wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
		
	        Workbook archivoExcel = Workbook.getWorkbook(new File(archivo));
	        
			
	        int hojaExcel = 0;
	        if(documentacionDeUrgencias){
	        	hojaExcel = 1;
	        	System.out.println("Elegimos urgencias");
	        }
	        
	        Sheet hoja = archivoExcel.getSheet(hojaExcel);
	        int numColumnas = hoja.getColumns() ;
	        int numFilas = hoja.getRows();
	        
	        for(int fila=2;fila<numFilas;fila++){
	        	Modelo modelo = new Modelo();
	        	
	        	modelo.rutaImagen = hoja.getCell(0,fila).getContents();
	        	modelo.nombreNormalizado = hoja.getCell(1,fila).getContents();
	        	modelo.setServiciosModelo(hoja.getCell(2,fila).getContents());
	        	
	        	String aux = hoja.getCell(3,fila).getContents();
	        	int formato = 3;  // Formato o A4, o A5 por defecto
	        	if(aux.equals("A4")){
	        		formato = 0;
	        	}
	        	else if(aux.equals("A5")){
	        		formato = -1;
	        	}
	        	else if(aux.equals("A3")){
	        		formato = 1;
	        	}
	        	modelo.fisica.tamañoPagina = formato;
	        	
	        	aux = hoja.getCell(4,fila).getContents();
	        	int orientacion = 0; // Por defecto vertical u horizontal
	        	if(aux.equals("V")){
	        		orientacion = 1;
	        	}
	        	else if(aux.equals("H")){
	        		orientacion = 2;
	        	}
	        	modelo.fisica.vertical = orientacion;
	        	
	        	modelo.metadatos.metaLocalizacion[0] = hoja.getCell(5,fila).getContents();
	        	modelo.metadatos.metaLocalizacion[1] = hoja.getCell(6,fila).getContents();
	        	modelo.metadatos.metaServicio = hoja.getCell(7,fila).getContents();
	        	modelo.metadatos.metaNombre =  hoja.getCell(8,fila).getContents();
	        	modelo.metadatos.metaModelo =  hoja.getCell(9,fila).getContents();
	        	modelo.metadatos.metaServicioNombre =  hoja.getCell(10,fila).getContents();
	        	for(int i=0;i<6;i++){
	        		aux = hoja.getCell(10 + i ,fila).getContents();
	        		if(aux == ""){
	        			break;
	        		}
	        		else{
	        			modelo.metadatos.metaAuxiliares.add(aux); 
	        		}
	        	}
	        	
	        	modelo.nombreAlternativo = hoja.getCell(21, fila).getContents();
	        	modelo.instruccionesNHC = hoja.getCell(24,fila).getContents();
	        	
	        	
	        	listaModelos.add(modelo);
	        	
	        }
	        
	        return listaModelos;
	        
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;			

	}
	
	
    DefaultListModel getDocServicio(String servicio){
        int numVinculaciones = 0;
        int numServicio =1;
        boolean encontrado = false;
        DefaultListModel vinculacionAux = new DefaultListModel();

        //  Encontramos el número del servicio
        while(!encontrado && numServicio<=numServicios){
            if(tablaDocumentos[0][numServicio].toString().contains(servicio))
                encontrado = true;
            else
                numServicio++;
        }
        
        //  Encontramos el número de vinculaciones
        for(int i=1;i<=numDocumentos;i++){
            if (tablaDocumentos[i][numServicio].toString().equals("x"))
                numVinculaciones++;
        }
        
        vinculacionServicio = new DefaultListModel();
        
        //  Devolvemos las vinculaciones en un array de cadena
        String[] vinculaciones = new String[numVinculaciones];

        Inicio.documentosServicio = new ArrayList<Object>();

        for(int i = 1; i<=numDocumentos;i++){
            if(tablaDocumentos[i][numServicio].toString().equals("x")){
                vinculacionServicio.addElement(tablaDocumentos[i][0].toString());
                Inicio.documentosServicio.add(tablaDocumentos[i][0].toString());
            }
        }
        
        
        //	Quitamos de la lista de documentos del servicio, los que ya estén en habituales
        
        int tamaño = habituales.length + habituales2.length;
        int tamaño1 = habituales.length;
        String[] todosLosHabituales = new String[tamaño];
		for(int i=0;i<tamaño1;i++){
			todosLosHabituales[i] = habituales[i];
		}
		for(int i=tamaño1;i<tamaño;i++){
			todosLosHabituales[i] = habituales2[i-tamaño1];
		}

        for(int i = 0; i<vinculacionServicio.size();i++){
        	encontrado = false;
        	for(int j=0;(j<tamaño && !encontrado);j++){
            	if(vinculacionServicio.getElementAt(i).equals(todosLosHabituales[j])){
            		encontrado = true;
             	}
        	}
        	if(!encontrado){
        		vinculacionAux.addElement(vinculacionServicio.getElementAt(i));
        	}
        }

        return vinculacionAux;
        
        
        
    }
	
    
    Point[] getPreferencias(String nombreUser, int numPantallas){
   	 int numUsers = tablaCoordenadas.length;
   	 Point[] parejaCoordenadas = new Point[6];
   	 for(int i= 0;i<6;i++)
   		 parejaCoordenadas[i]= new Point();

   	 int indice;
   	 int indiceIntegral;
   	 if(numPantallas == 1){
   		 indiceIntegral = 19;
   		 indice = 1;
   	 }
   	 else {
   		 indiceIntegral= 23;
   		 indice = 10;
   	 } 	 
   	 
   	 for(int i=0;i<numUsers;i++){
   	//	 System.out.println(tablaCoordenadas[i][0]);
   		 if(tablaCoordenadas[i][0].toString().contains(nombreUser)){
   			 if(!(tablaCoordenadas[i][1].toString().contains("N"))){
   			//	 System.out.println("hole");
   				 coordenadasGrabadas = true;
	    			 for(int j=0;j<4;j++){
	    				 parejaCoordenadas[j].x = Integer.parseInt(tablaCoordenadas[i][indice +1].toString());indice++;
	    				 parejaCoordenadas[j].y = Integer.parseInt(tablaCoordenadas[i][indice +1].toString());indice++;   				 
	    			 }
	    			 
	    			 //  Coordenadas de las ventanas integrales y de aviso
	    			 if(indiceIntegral == 19){
	    				 indice = 19;
	    			 }else{
	    				 indice = 23;
	    			 }
	    			 System.out.println("Indice es " + indice);
	    			 for(int j=0;j<2;j++){
	    				 System.out.println(Integer.parseInt(tablaCoordenadas[i][indice].toString()));
	    				 parejaCoordenadas[4 + j].x = Integer.parseInt(tablaCoordenadas[i][indice].toString());indice++;
	    				 System.out.println(Integer.parseInt(tablaCoordenadas[i][indice].toString()));
	    				 parejaCoordenadas[4 + j].y = Integer.parseInt(tablaCoordenadas[i][indice].toString());indice++;
	    			 }
	    			 
	    			 break;
   			 }
   		 }
     }

   	 
   	System.out.println("El numero de coordenadas es: " + parejaCoordenadas.length);
   	for(int i=0;i<parejaCoordenadas.length;i++){
   		System.out.println(i + " coordenadas: " + parejaCoordenadas[i].x + ", " +
   					parejaCoordenadas[i].y);
   	}
   	 
   	 return parejaCoordenadas;
    }
    
    
    String[] getDocServicioVisor(String servicio){
        int numVinculaciones = 0;
        int numServicio =1;
        boolean encontrado = false;
        //  Encontramos el número del servicio
        while(!encontrado && numServicio<numServicios){
            if(servicio == tablaDocumentos[0][numServicio])
                encontrado = true;
            else
                numServicio++;
        }

        //  Encontramos el número de vinculaciones
        for(int i=1;i<numDocumentos;i++){
            if (tablaDocumentos[i][numServicio].toString().equals("x"))
                numVinculaciones++;
        }

        String[] vinculacionServicio = new String[numVinculaciones];
        
        //  Devolvemos las vinculaciones en un array de cadena
        String[] vinculaciones = new String[numVinculaciones];
        int aux=0;
        for(int i = 1; i<numDocumentos;i++){
            if(tablaDocumentos[i][numServicio].toString().equals("x")){
                vinculacionServicio[aux]= tablaDocumentos[i][0].toString();
                aux++;
            }
        }
        
        return vinculacionServicio;
    }
    
}
