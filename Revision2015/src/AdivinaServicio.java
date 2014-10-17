import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AdivinaServicio {
	
	static public String getServicio(int inicio,int fin){
		
		Map<String,Integer> serviciosPosibles = new HashMap<String, Integer>();
		
		for(int j= inicio; j < fin; j++){
			if( !Inicio.listaDocumentos[j].servicio.equals("X") ){
				if(!serviciosPosibles.containsKey(Inicio.listaDocumentos[j].servicio)){
					serviciosPosibles.put(Inicio.listaDocumentos[j].servicio, 1);
				}
				else{
					int z = serviciosPosibles.get(Inicio.listaDocumentos[j].servicio);
					z++;
					serviciosPosibles.put(Inicio.listaDocumentos[j].servicio, z);
				}
				
				// Inicio.listaDocumentos[j].semaforoAmarilloServicio = true;
			}
		}
		
		Iterator<String> iterador = serviciosPosibles.keySet().iterator();
		
		int max = 0;
		String maximoServicio = "";
		while(iterador.hasNext()){
			String serv = iterador.next();
			int numRepeticiones = serviciosPosibles.get(serv);
			System.out.println("Servicio: " + serv + ". " + "Repeticiones: " + numRepeticiones);
			if(numRepeticiones > max){
				max = numRepeticiones;
				maximoServicio = serv;
			}
		}
		
		if(maximoServicio.equals("")){
			int numEkgs = 0;
			int numCursoClinico = 0;
			for(int j= inicio; j < fin; j++){
				if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.EKG)){
					numEkgs++;
				}
				else if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.CURSOCLINICO)){
					numCursoClinico++;
				}
			}
			if(numEkgs > 3 && numCursoClinico <3){
				maximoServicio = Inicio.CARC;
			}
			else if(numEkgs > 3 && numCursoClinico >= 3){
				maximoServicio = Inicio.NRLC;
			}
		}
		
		System.out.println("El máximus servicio es... " + maximoServicio);
		
		return maximoServicio;
	}

	
}

