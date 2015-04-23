import java.util.Calendar;


public class Fechas {


	int dia;
	int mes;
	int a�o;
	
	int diaPosible;
	int mesPosible;
	int a�oPosible;
	
	String fechaHoy = "";
	
	public Fechas() {
		// TODO Auto-generated constructor stub
		
		Calendar calendario = Calendar.getInstance();
		dia = calendario.get(Calendar.DAY_OF_MONTH);
		mes = calendario.get(Calendar.MONTH) + 1;
		a�o = calendario.get(Calendar.YEAR);
		
		fechaHoy = get2digitos(dia) + " / " + get2digitos(mes) + " / " + a�o;
	}

	
	public String adivinaFecha(String texto){
		String fecha;
		
		int longitud = texto.length();
		if(longitud == 0) return null;
		int numero = Integer.valueOf(texto);
		
		System.out.println("La longitud es... " + longitud );
		System.out.println("El numero es... " + numero );
		System.out.println("Hoy es... " + dia );
		
		if(texto.equals("0")){
			return fechaHoy;
		}
		
		if(texto.equals("00")){
			return "01/01/0001";
		}
		
		if(longitud == 1 || longitud == 2){
			if(dia > numero){
				diaPosible = numero;
				mesPosible = mes;
				a�oPosible = a�o;
				
				fecha = get2digitos(diaPosible) + " / " + get2digitos(mesPosible) + " / " + a�oPosible;
								
				return fecha;
			}
			else{
				
				System.out.println();
				
				Calendar cal = Calendar.getInstance();
				
				cal.add(Calendar.MONTH, -1);
				
				diaPosible = numero;
				mesPosible = cal.get(Calendar.MONTH) + 1;
				a�oPosible = cal.get(Calendar.YEAR);
				
				fecha = get2digitos(diaPosible) + " / " + get2digitos(mesPosible) + " / " + a�oPosible;
				
				return fecha;
			}
		}
		else if(longitud == 3 || longitud == 4){
			
			String cadenas[] = new String[2];
			cadenas[0] = texto.substring(0,2);
			cadenas[1] = texto.substring(2);
			
			diaPosible = Integer.valueOf(cadenas[0]);
			if(diaPosible == 0){
				return null;
			}
			
			mesPosible = Integer.valueOf(cadenas[1]);
			
			if(mesPosible <13){
				
				if(longitud == 3 && mesPosible == 0){
					return null;
				}
				
				if(mes >= mesPosible){
					a�oPosible = a�o;
				}
				else{
					a�oPosible = a�o-1;
				}
			}
			else{
				return null;
			}
			
			fecha = get2digitos(diaPosible) + " / " + get2digitos(mesPosible) + " / " + a�oPosible;
			
			return fecha;
		}
		else if(longitud == 6){
			String cadenas[] = new String[3];
			cadenas[0] = texto.substring(0,2);
			cadenas[1] = texto.substring(2,4);
			cadenas[2] = texto.substring(4);
			
			diaPosible = Integer.valueOf(cadenas[0]);
			mesPosible = Integer.valueOf(cadenas[1]);
			a�oPosible = Integer.valueOf(cadenas[2]);
			
			if(2000 + a�oPosible > a�o){
				a�oPosible = 1900 + a�oPosible;
			}
			else{
				a�oPosible = 2000 + a�oPosible;
			}
			
			fecha = get2digitos(diaPosible) + " / " + get2digitos(mesPosible) + " / " + a�oPosible;
			
			return fecha;
		}
		
		return null;
	}
	
	
	private String get2digitos(int numero){
		
		String cadena = "";
		if(numero < 10){
			cadena = "0" + numero;
		}
		else{
			cadena = "" + numero;
		}
		
		return cadena;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Fechas fechas = new Fechas();
		System.out.println(fechas.dia + " / " + fechas.mes + " / " + fechas.a�o);
		
		String cadena = fechas.adivinaFecha("0002");
		System.out.println(cadena);
	}
}
