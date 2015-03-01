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
	
	DefaultListModel<String> listaServiciosLista;
	DefaultListModel<String> listaDocumentosDLM;
	DefaultListModel<String> listaComunes;
	DefaultListModel<String> listaHabituales1;
    DefaultListModel<String> listaHabituales2;
    DefaultListModel<String> listaHabitualesUrg;
    
    DefaultListModel<String> vinculacionServicio;
    
    int numServicios = 0;
    int numDocumentos = 0;
    
    String[][] conjuntoHabituales;
    String[] comunes;
    String[] habituales1;
    String[] habituales2;
    String[] habitualesUrg;
    
    Object[][] tablaCoordenadas;
    Object[][] tablaVisor;
    DefaultListModel<String> listaUsuariosLista;
    DefaultListModel<String> listaUsuariosListaUrg;
    DefaultComboBoxModel<String> listaUsuarios;
    DefaultComboBoxModel<String> listaUsuariosUrg;
    
    boolean coordenadasGrabadas = false;
    
    public void getPreferencias(String archivo){
		try {
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setEncoding("ISO-8859-1");
			wbSettings.setLocale(new Locale("es", "ES"));
			wbSettings.setExcelDisplayLanguage("ES");
			wbSettings.setExcelRegionalSettings("ES");
			wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
			Workbook archivoExcel = Workbook.getWorkbook(new File(archivo));
			
			Sheet hoja = archivoExcel.getSheet(0);
			
	        int numColumnas = 0;
	        int numFilas = 0;
	        
            while(!hoja.getCell(numColumnas,0).getContents().toString().equals("#finH")){
            	numColumnas++;
            }

	        while(!hoja.getCell(0,numFilas).getContents().toString().equals("#finV")){
	        	numFilas++;
	        }
			
            tablaCoordenadas = new Object[numFilas][numColumnas];
            
            //	Leer coordenadas usuarios
            for (int fila=0;fila<numFilas-1;fila++){
                for(int columna=0;columna<numColumnas;columna++){
                	if(hoja.getCell(columna,fila+1).getContents()!="")
                		tablaCoordenadas[fila][columna] = hoja.getCell(columna, fila+1).getContents();
                	else
                		tablaCoordenadas[fila][columna] = 0;

                }
            } 
            
            for(int i=0;i<numFilas-1;i++){
            	for(int j=0;j<numColumnas;j++){
            		System.out.print(tablaCoordenadas[i][j] + "   ");
            	}
            	System.out.println();
            }
            
//        	Leer cuadro documentos Visor
            hoja = archivoExcel.getSheet(1);
            int numFilasVi = hoja.getRows();
            int numColumVi = hoja.getColumns();
            
            tablaVisor = new Object[numFilasVi][numColumVi];
            
            for (int fila=0;fila<numFilasVi;fila++){
                    for(int columna=0;columna<numColumVi;columna++){					
                    tablaVisor[fila][columna] = hoja.getCell(columna, fila).getContents();
                    }
            }
            
            archivoExcel.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
    }
	
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
	        int numColumnas = 0;
	        int numFilas = 0;
	        
	        
	        
            while(!hoja.getCell(numColumnas,0).getContents().toString().equals("@finH")){
            	numColumnas++;
            }

	        while(!hoja.getCell(0,numFilas).getContents().toString().equals("@finV")){
	        	numFilas++;
	        }
            
         //  System.out.println("El numero de columnas es... " + numColumnas);
	        
	        numServicios = numColumnas-14;
	        
	     //   System.out.println("El numero de servicios es..." + numServicios);
	        
	        numDocumentos = numFilas-1;
	        
	    //    System.out.println("El numero de filas es..." + numFilas);
	    //    System.out.println("El numero de documentos es..." + numDocumentos);
	        
	        tablaDocumentos = new String[numDocumentos][numServicios];
	        listaServicios = new String[numServicios];
	        listaDocumentos = new String[numDocumentos];
	        
	        //	Tabla de todos los documentos
	        for(int fila=0;fila<numDocumentos;fila++){
	        	for(int columna=0;columna<numServicios;columna++){
	        		 tablaDocumentos[fila][columna] = hoja.getCell(columna+2,fila+1).getContents().toString();
	        		// tablaDocumentos[fila][columna] = hoja.getCell(columna+2,fila).getContents().toString();

	        	}
	        }
	        
	        
	        // Lista de todos los servicios
	        for(int columna = 0;columna<numServicios;columna++){
	        	listaServicios[columna] = hoja.getCell(columna + 2,0).getContents().toString();
	        }
	        
	        // Lista de todos los nombres
	        for(int fila = 0;fila<numDocumentos;fila++){
	        	listaDocumentos[fila] = hoja.getCell(0,fila + 1).getContents().toString();
	        }
	        
	        
     
	        listaServiciosLista = new DefaultListModel<String>();
	        for(int i=0;i<numServicios;i++){
	    //    	System.out.println(listaServicios[i].toString());
	        	listaServiciosLista.addElement(listaServicios[i]);
	        }
	        
	    //  Leer lista de todos los documentos        
            listaDocumentosDLM = new DefaultListModel<String>();
            for(int i=0;i<listaDocumentos.length;i++){
                listaDocumentosDLM.addElement(listaDocumentos[i]);
            }   
	        
	        
	    //  Leer hoja excel habituales
            
	        //  Habituales **********************************************
	        // 0..... comunes
	        // 1..... habituales 1
	        // 2..... habituales 2
	        // 3..... habituales urgencias
	        
	        conjuntoHabituales = new String[numDocumentos][4];
	        
	        int numHabituales1 = 0;
	        int numHabituales2 = 0;
	        int numHabitualesU = 0;
	        int numComunes = 0;
	        
	        listaComunes = new DefaultListModel<String>();
	        listaHabituales1 = new DefaultListModel<String>();
            listaHabituales2 = new DefaultListModel<String>();
            listaHabitualesUrg = new DefaultListModel<String>();
	        
	        for(int fila = 0; fila < numDocumentos;fila++){
	        	for(int columna = 0;columna < 4; columna++){
	        		conjuntoHabituales[fila][columna] = hoja.getCell(columna + numServicios + 2 + 7,fila +1).getContents().toString();
	        		if(columna == 0){
	        			if(conjuntoHabituales[fila][columna].toLowerCase().equals("s")){
	        				numComunes++;
	        				listaComunes.addElement(listaDocumentos[fila]);
	        			}
	        		}
	        		else if(columna == 1){
	        			if(conjuntoHabituales[fila][columna].toLowerCase().equals("s")){
	        				numHabituales1++;
	        				listaHabituales1.addElement(listaDocumentos[fila]);
	        			}
	        		}
	        		else if(columna == 2){
	        			if(conjuntoHabituales[fila][columna].toLowerCase().equals("s")){
	        				numHabituales2++;
	        				listaHabituales2.addElement(listaDocumentos[fila]);
	        			}
	        		}
	        		else if(columna == 3){
	        			if(conjuntoHabituales[fila][columna].toLowerCase().equals("s")){
	        				numHabitualesU++;
	        				listaHabitualesUrg.addElement(listaDocumentos[fila]);
	        			}
	        			
	        		}
	        	}
	        }

	        comunes = new String[numComunes];
	        for(int i=0;i<numComunes;i++){
	        	comunes[i] = listaComunes.getElementAt(i).toString();
	        }
	        habituales1 = new String[numHabituales1];
	        for(int i=0;i<numHabituales1;i++){
	        	habituales1[i] = listaHabituales1.getElementAt(i).toString();
	        }
	        habituales2 = new String[numHabituales2];
	        for(int i=0;i<numHabituales2;i++){
	        	habituales2[i] = listaHabituales2.getElementAt(i).toString();
	        }
	        habitualesUrg = new String[numHabitualesU];
	        for(int i=0;i<numHabitualesU;i++){
	        	habitualesUrg[i] = listaHabitualesUrg.getElementAt(i).toString();
	        }

	        /*
	        for(int i=0;i<habituales2.length;i++){
	        	System.out.println(habituales2[i]);
	        }
	        */
            
//        	Leer hoja excel usuarios Documentacion
            hoja=archivoExcel.getSheet(6); 
            int numFilasUsDoc = 0;
	        while(!hoja.getCell(0,numFilasUsDoc).getContents().toString().equals("#finV")){
	        	numFilasUsDoc++;
	        }
            System.out.println("número de filas " + numFilasUsDoc);
            listaUsuarios = new DefaultComboBoxModel<String>();
            listaUsuariosLista = new DefaultListModel<String>();
            for(int i=0;i<numFilasUsDoc;i++){
            	listaUsuarios.addElement(hoja.getCell(0, i).getContents().toString());
            	listaUsuariosLista.addElement(hoja.getCell(0, i).getContents().toString());
            	System.out.println(hoja.getCell(0, i).getContents().toString());
            }
                                    
            /*
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
            */
            
//        	Leer hoja excel usuariosUrgencias
            hoja=archivoExcel.getSheet(5);
            int numFilasUsUrg = 0;
	        while(!hoja.getCell(0,numFilasUsUrg).getContents().toString().equals("#finV")){
	        	numFilasUsUrg++;
	        }
            System.out.println("Numero filas urgencias " + numFilasUsUrg);
            
            listaUsuariosUrg = new DefaultComboBoxModel<String>();
            listaUsuariosListaUrg = new DefaultListModel<String>();
            for(int i=0;i<numFilasUsUrg;i++){
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
	        
            archivoExcel.close();
            
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
	        	
	        	System.out.println("Fila... " + fila);
	        	
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
	        
        	archivoExcel.close();
	        
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
	
	
    DefaultListModel<String> getDocServicio(String servicio){
        int numVinculaciones = 0;
        int numServicio =1;
        boolean encontrado = false;
        DefaultListModel<String> vinculacionAux = new DefaultListModel<String>();

        for(int i=0;i<listaServicios.length;i++){
        	if(listaServicios[i].contains(servicio)){
        		numServicio = i;
        		break;
        	}
        }
        
        /*
        //  Encontramos el número del servicio
        while(!encontrado && numServicio<=numServicios){
            if(tablaDocumentos[0][numServicio].toString().contains(servicio))
                encontrado = true;
            else
                numServicio++;
        }
        */
        
        for(int i=0;i<numDocumentos;i++){
        	if(!tablaDocumentos[i][numServicio].toString().equals("")){
        		numVinculaciones++;
        	}
        }
        
        /*
        //  Encontramos el número de vinculaciones
        for(int i=1;i<=numDocumentos;i++){
            if (tablaDocumentos[i][numServicio].toString().equals("x"))
                numVinculaciones++;
        }
        */
        
        vinculacionServicio = new DefaultListModel<String>();
        
        //  Devolvemos las vinculaciones en un array de cadena
        String[] vinculaciones = new String[numVinculaciones+1];

        Inicio.documentosServicio = new ArrayList<Object>();

        for(int i =0; i<numDocumentos;i++){
            if(!tablaDocumentos[i][numServicio].toString().equals("")){
                vinculacionServicio.addElement(listaDocumentos[i]);
                Inicio.documentosServicio.add(listaDocumentos[i]);
            }
        }
        
        
        //	Quitamos de la lista de documentos del servicio, los que ya estén en habituales
        //  y en comunes.
        
        int tamaño = habituales1.length + habituales2.length + comunes.length;
        int tamaño1 = habituales1.length;
        int tamaño2 = habituales2.length;
        
        System.out.println(tamaño + " " + tamaño1 + " " + tamaño2);
        
        String[] todosLosHabituales = new String[tamaño];
		for(int i=0;i<tamaño1;i++){
			todosLosHabituales[i] = habituales1[i];
		}
		for(int i=tamaño1;i<tamaño1 + tamaño2;i++){
			todosLosHabituales[i] = habituales2[i-tamaño1];
		}
		for(int i=tamaño1 + tamaño2 ;i<tamaño;i++){
			todosLosHabituales[i] = comunes[i-tamaño1 -tamaño2];
		}
		
		
		
        for(int i = 0; i<vinculacionServicio.size();i++){
        	encontrado = false;
        	for(int j=0;(j<tamaño && !encontrado);j++){
            	if(vinculacionServicio.getElementAt(i).equals(todosLosHabituales[j])){
            		encontrado = true;
             	}
        	}
        	if(!encontrado){
        		vinculacionAux.addElement((String) vinculacionServicio.getElementAt(i));
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
   			 System.out.println("Encontrado nombre de usuario..." + nombreUser);
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
            if(!tablaDocumentos[i][numServicio].toString().equals("")){
                vinculacionServicio[aux]= tablaDocumentos[i][0].toString();
                aux++;
            }
        }
        
        return vinculacionServicio;
    }
    
}
