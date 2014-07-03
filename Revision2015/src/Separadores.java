import java.util.ArrayList;


public class Separadores {

	private ArrayList<Integer> separadores = new ArrayList<Integer>();
	
	Separadores(){
		for(int i=0; i< Inicio.listaDocumentos.length;i++){
			if(Inicio.listaDocumentos[i].nhc.equals("Separador")){
				System.out.println("Detectado separador");
				separadores.add(i);
			}
		}
		if (separadores.size() == 0){
			separadores.add(-1);			
		}
		if(separadores.get(0) != 0){
			separadores.add(0,-1);
		}
		separadores.add(Inicio.listaDocumentos.length);
		
		for(int i=0;i<separadores.size();i++){
			System.out.println(separadores.get(i));
		}
	}
	
	ArrayList<Integer> getNumOrdenSeparadores(){
		return separadores;
	}
}
