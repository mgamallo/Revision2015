public class Etiqueta {

	String nhc = "NO";
	String apellidos;
	String nombre;
	
	String tis;
	String fechaIngreso;
	
	int tipo = 0;	//	0 No localizado
					//	1 Moderno
					//	2 Moderno Ingreso
					//	3 Antiguo
	
	/****************************************************
	 * 	
	 * 	1	NHC: 123.456
	 * 		Apellido 1    (Mayusculas)
	 * 		Apellido 2
	 * 		Nombre
	 * 		NSS: ......  ACTIVO / PENSION
	 * 		.
	 * 		.
	 * 		TIS: ......
	 * 
	 *******************************************************
	 * 
	 * 	2	NHC: 123.456
	 * 		Apellido 1    (Mayusculas)
	 * 		Apellido 2
	 * 		Nombre
	 * 		NSS: ......  ACTIVO / PENSION
	 * 		TIS: ......
	 *	 	.
	 *		.
	 *		F.Ing: fecha [hora]
	 *
	 ******************************************************
	 *
	 *	3	NHC: 123.456
	 *		Apellido1 Apellido2, Nombre
	 *		NSS: .....
	 *		.
	 *		.
	 *		TIS: ......  PENSION
	 * 
	 */
	
	
	
	Etiqueta(String cadenaBruta){

		tipo = detectarEtiqueta(cadenaBruta);

	}
	
	int detectarEtiqueta(String cadenaBruta){
		int indices[] = new int[6];
		
		//	Encontrar NHC
		indices[0] = cadenaBruta.indexOf("NHC");
		if(indices[0] != -1){
			
			boolean fin = false;
			boolean error = false;
			
			int contador = indices[0] + 4;
			String nhcS = "";
			while(!fin){
				char c = cadenaBruta.charAt(contador);
				// System.out.println("Contador " + contador + ". Vale: " + c + ". int: " + (int)c);
				if(c != ' ' && c != 10){
					if(c >= '0' && c <= '9'){
						nhcS += c;
					}
					else{
						error = true;
						fin = true;
					}
				}
				else if(nhcS.length() > 0){
					fin = true;
				}
				
				
				contador++;
			}
			
			if(!error){
				if(nhcS.length()>0){
					nhc = nhcS;
				}
			}
			else
				nhc = nhcS + "ERROR";
						
			indices[1] = cadenaBruta.indexOf("NSS", indices[0]);
			indices[2] = cadenaBruta.indexOf("ACTIVO",indices[1]);
			if(indices[2] == -1){
				indices[2] = cadenaBruta.indexOf("PENSION",indices[1]);
			}
			indices[3] = cadenaBruta.indexOf("DNI",indices[1]);
			indices[4] = cadenaBruta.indexOf("TIS",indices[1]);
			indices[5] = cadenaBruta.indexOf("F.Ing",indices[1]);
			
			
			int aux = 0;
			if ( indices[3] != -1 && indices[4] != -1){
				if(indices[3] < indices[4]){  //  dni < tis
						if( indices[2] != -1){
							if (indices[2] > indices[4]){ // activo < tis
								return 3;
							}
							else{
								return 1;
							}
						}
						else{
							return 1;
						}
				}
				else{
					return 2;
				}
			}

		}
		return 0;
	}
	
}