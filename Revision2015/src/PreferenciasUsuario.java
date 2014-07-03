import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;

import jxl.Sheet;
import jxl.Workbook;


public class PreferenciasUsuario {

	Point[] coordenadas;
	boolean grabadas;
	int numPantallas;
	
	PreferenciasUsuario(){
		coordenadas = new Point[6];
		numPantallas = getNumPantallas();

		setCoordenadas(Inicio.excel.getPreferencias(Inicio.usuario, numPantallas));
		grabadas = Inicio.excel.coordenadasGrabadas; 
	}
	
	int getNumPantallas(){
		
		//	Determinar número de pantallas
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		
		return gs.length;
	}
	
	void setCoordenadas(Point[] coord){
		coordenadas[0] = coord[0];      // VentanaExplorador
		coordenadas[1] = coord[1];		// Ancho y alto ventana explorador
		coordenadas[2] = coord[2];		// VentanaCompacta
		coordenadas[3] = coord[3];		// VentanaPrincipal
		coordenadas[4] = coord[4];		// VentanaIntegral
		coordenadas[5] = coord[5];		// VentanaMicro
	}
	
	
	void getCoordenadasEnPantalla(){
		Rectangle rectExplor = new Rectangle(Inicio.ventanaExplorador.getBounds());
		coordenadas[0].x = rectExplor.x;
		coordenadas[0].y = rectExplor.y;
		coordenadas[1].x = rectExplor.width;
		coordenadas[1].y = rectExplor.height;
		rectExplor = new Rectangle(Inicio.ventanaCompacta.getBounds());
		coordenadas[2].x = rectExplor.x;
		coordenadas[2].y = rectExplor.y;
		rectExplor = new Rectangle(Inicio.ventanaPrincipal.getBounds());
		coordenadas[3].x = rectExplor.x;
		coordenadas[3].y = rectExplor.y;
		if(Inicio.menuVertical){
			rectExplor = new Rectangle(Inicio.ventanaIntegral.getBounds());
			coordenadas[4].x = rectExplor.x;
			coordenadas[4].y = rectExplor.y;
			//rectExplor = new Rectangle(Inicio.ventanaMicro.getBounds());
			// coordenadas[5].x = rectExplor.x;
			// coordenadas[5].y = rectExplor.y;
		}

		
		/*
		System.out.println(coordenadas[1]);
		System.out.println(coordenadas[2]);
		System.out.println(coordenadas[3]);
		*/
	}
	
	
	void leerCoordenadas(){
    	LeerExcel excel = new LeerExcel();
        //excel.leerExcel("Documentos.xls");

	}
	
	void grabarCoordenadas(){
		getCoordenadasEnPantalla();
		EscribirExcel guardarPreferencias = new EscribirExcel();
		guardarPreferencias.escribir("Documentos.xls", Inicio.usuario, Inicio.numeroPantallas);
	}
}

