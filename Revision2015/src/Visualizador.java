import java.awt.Color;


public class Visualizador {
	Visualizador(int numArchivo){
		Inicio.jBNHC.setText(Inicio.listaDocumentos[numArchivo].nhc);
		Inicio.jBNHCp.setText(Inicio.listaDocumentos[numArchivo].nhc);
		if(Inicio.listaDocumentos[numArchivo].nhc == "NO"){
			Inicio.jBNHCp.setBackground(Color.red);
		}else if(Inicio.listaDocumentos[numArchivo].nhc == "Separador"){
			Inicio.jBNHCp.setText("Sep.");
		}else{
			Inicio.jBNHCp.setBackground(Color.green);
		}
		
		Inicio.jBServicio.setText(Inicio.listaDocumentos[numArchivo].servicio);
		Inicio.jBServiciop.setText(Inicio.listaDocumentos[numArchivo].servicio);
		if(Inicio.listaDocumentos[numArchivo].servicio == "X"){
			Inicio.jBServiciop.setBackground(Color.red);
			Inicio.jBServicio.setBackground(Color.red);
		}
		else{
			Inicio.jBServiciop.setBackground(Color.green);
			Inicio.jBServicio.setBackground(Color.green);
		}
		
		Inicio.jBNombreDoc.setText(Inicio.listaDocumentos[numArchivo].nombreNormalizado);
		Inicio.jBNombreDocp.setText(Inicio.listaDocumentos[numArchivo].nombreNormalizado);
		if(Inicio.listaDocumentos[numArchivo].nombreNormalizado == "X"){
			Inicio.jBNombreDocp.setBackground(Color.red);
			Inicio.jBNombreDoc.setBackground(Color.red);
		}
		else{
			Inicio.jBNombreDocp.setBackground(Color.green);
			Inicio.jBNombreDoc.setBackground(Color.green);
		}
	}
}
