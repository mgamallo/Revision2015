import java.io.File;
import java.util.Locale;

import javax.swing.JOptionPane;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CountryCode;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class EscribirExcel {
	
	int numFilaUser;
	int indicePantallas;
	
	public void escribir(String archivoDestino, String user, int pantallas){

		try{
			WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setEncoding("ISO-8859-1");
            wbSettings.setLocale(new Locale("es", "ES"));
            wbSettings.setExcelDisplayLanguage("ES"); 
            wbSettings.setExcelRegionalSettings("ES"); 
            wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
	
            Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino));
            Sheet hoja = archivoExcel.getSheet(13);
            int numC = hoja.getColumns();
            int numF = hoja.getRows(); 
            String[] listaUsers = new String[numF-2];
            int numUsers = listaUsers.length;
            for(int i=0;i<numUsers;i++){
            	listaUsers[i] = hoja.getCell(0,i+2).getContents().toString();
            }
            boolean encontrado = false;
            for(int i=0;i<numUsers;i++){								//	Fila Usuario
            	if(listaUsers[i].contains(user)){
            		numFilaUser = i+2;
            		encontrado = true;
            		break;
            	}
            }
            if(!encontrado){											//	Nuevo Usuario
            	numFilaUser = numUsers+2;
            }
            
            if(pantallas == 1){
            	indicePantallas = 1;
            }else if(pantallas == 2){
            	indicePantallas = 10;
            }
            
            //	System.out.println(listaUsers[i]);
            
            
            WritableWorkbook libroEscritura = Workbook.createWorkbook(new File(archivoDestino), archivoExcel);
            
            archivoExcel.close();
            
            WritableSheet hojaE = libroEscritura.getSheet(13);
            
            jxl.write.Label texto;
            jxl.write.Number numero;

            
            // FAlta por programar....
            
            if(!encontrado){
                texto = new jxl.write.Label(0,numFilaUser,user);
                hojaE.addCell(texto);
                /*
                 * 
                 * 	Posteriormente hay que poner las N que hagan falta
                 * 
                 * 
                 */
            }
            ////////////////////////////////////////////
            
            
            texto = new jxl.write.Label(indicePantallas,numFilaUser,"S");		//	col, fil , val
            hojaE.addCell(texto);
            
            numero = new jxl.write.Number(indicePantallas+1,numFilaUser,Inicio.coordenadas.coordenadas[0].x); 
            hojaE.addCell(numero);
            numero = new jxl.write.Number(indicePantallas+2,numFilaUser,Inicio.coordenadas.coordenadas[0].y); 
            hojaE.addCell(numero);           
            numero = new jxl.write.Number(indicePantallas+3,numFilaUser,Inicio.coordenadas.coordenadas[1].x); 
            hojaE.addCell(numero);
            numero = new jxl.write.Number(indicePantallas+4,numFilaUser,Inicio.coordenadas.coordenadas[1].y); 
            hojaE.addCell(numero);             
            numero = new jxl.write.Number(indicePantallas+5,numFilaUser,Inicio.coordenadas.coordenadas[2].x); 
            hojaE.addCell(numero);
            numero = new jxl.write.Number(indicePantallas+6,numFilaUser,Inicio.coordenadas.coordenadas[2].y); 
            hojaE.addCell(numero);           
            numero = new jxl.write.Number(indicePantallas+7,numFilaUser,Inicio.coordenadas.coordenadas[3].x); 
            hojaE.addCell(numero);
            numero = new jxl.write.Number(indicePantallas+8,numFilaUser,Inicio.coordenadas.coordenadas[3].y); 
            hojaE.addCell(numero); 
            
            int pref2 = 0;
            if(Inicio.numeroPantallas == 2){
            	pref2 = 23;
            }
            else{
            	pref2 = 19;
            }
            
            numero = new jxl.write.Number(pref2,numFilaUser,Inicio.coordenadas.coordenadas[4].x); 
            hojaE.addCell(numero);
            numero = new jxl.write.Number(pref2+1,numFilaUser,Inicio.coordenadas.coordenadas[4].y); 
            hojaE.addCell(numero); 
            numero = new jxl.write.Number(pref2+2,numFilaUser,Inicio.coordenadas.coordenadas[5].x); 
            hojaE.addCell(numero);
            numero = new jxl.write.Number(pref2+3,numFilaUser,Inicio.coordenadas.coordenadas[5].y); 
            hojaE.addCell(numero); 
            
            //System.out.println(Inicio.coordenadas.coordenadas[3]);
            
            libroEscritura.write();
            libroEscritura.close();
            
	
		}catch(Exception ioe){
			// ioe.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fichero en uso. No se puede guardar preferencias");
		}
	}
/*
	public static void main(String[] args){
		EscribirExcel prueba = new EscribirExcel();
		prueba.escribir("Documentos1.xls","pepe",1,1);
	
	}   */

}