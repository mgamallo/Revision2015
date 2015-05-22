import java.util.Calendar;


public class Fechas {


	int dia;
	int mes;
	int año;
	
	int diaPosible;
	int mesPosible;
	int añoPosible;
	
	String fechaHoy = "";
	String fechaAyer = "";
	String fechaAnteayer = "";
	String fechaAnteAnteayer = "";
	
	String diaAyer = "";
	String diaAnteayer = "";
	String diaAnteAnteayer = "";
	
	public Fechas() {
		// TODO Auto-generated constructor stub
		
		Calendar calendario = Calendar.getInstance();
		dia = calendario.get(Calendar.DAY_OF_MONTH);
		mes = calendario.get(Calendar.MONTH) + 1;
		año = calendario.get(Calendar.YEAR);
		
		fechaHoy = get2digitos(dia) + " / " + get2digitos(mes) + " / " + año;
		
		devuelveFechasAnteriores();
		
	}
	
	private String devuelveCadenaFecha(Calendar cal){
		
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH) + 1;
		int año = cal.get(Calendar.YEAR);
				
		return  get2digitos(dia) + " / " + get2digitos(mes) + " / " + año;
	}

	private void devuelveFechasAnteriores(){
		
		String fecha = "";
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, - 1);
		
		System.out.println("Dia del mes... " + cal.get(Calendar.DAY_OF_MONTH));
		System.out.println("Empieza adivinar dia semana... " + cal.get(Calendar.DAY_OF_WEEK));
		
		while(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
			cal.add(Calendar.DAY_OF_MONTH, -1);
			System.out.println("El día anterior es... " + cal.get(Calendar.DAY_OF_MONTH) + ". " + cal.get(Calendar.DAY_OF_WEEK));
		}
		
		fechaAyer = devuelveCadenaFecha(cal);
		diaAyer = getDiaSemana(cal.get(Calendar.DAY_OF_WEEK));
		
		cal.add(Calendar.DAY_OF_MONTH, - 1);
		
		System.out.println("Dia del mes... " + cal.get(Calendar.DAY_OF_MONTH));
		System.out.println("Empieza adivinar dia semana... " + cal.get(Calendar.DAY_OF_WEEK));
		
		while(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
			cal.add(Calendar.DAY_OF_MONTH, -1);
			System.out.println("El día anterior es... " + cal.get(Calendar.DAY_OF_MONTH) + ". " + cal.get(Calendar.DAY_OF_WEEK));
		}
		
		System.out.println();
		fechaAnteayer = devuelveCadenaFecha(cal);
		diaAnteayer = getDiaSemana(cal.get(Calendar.DAY_OF_WEEK));
		
		cal.add(Calendar.DAY_OF_MONTH, - 1);
		
		System.out.println("Dia del mes... " + cal.get(Calendar.DAY_OF_MONTH));
		System.out.println("Empieza adivinar dia semana... " + cal.get(Calendar.DAY_OF_WEEK));
		
		while(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
			cal.add(Calendar.DAY_OF_MONTH, -1);
			System.out.println("El día anterior es... " + cal.get(Calendar.DAY_OF_MONTH) + ". " + cal.get(Calendar.DAY_OF_WEEK));
		}
		
		System.out.println();
		fechaAnteAnteayer = devuelveCadenaFecha(cal);
		diaAnteAnteayer = getDiaSemana(cal.get(Calendar.DAY_OF_WEEK));

	}
	
	
	private String encuentraDiasHabiles(int index){
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DAY_OF_MONTH, -index);
		
		while(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		return getDiaSemana(cal.get(Calendar.DAY_OF_WEEK));
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
				añoPosible = año;
				
				fecha = get2digitos(diaPosible) + " / " + get2digitos(mesPosible) + " / " + añoPosible;
								
				return fecha;
			}
			else{
				
				System.out.println();
				
				Calendar cal = Calendar.getInstance();
				
				cal.add(Calendar.MONTH, -1);
				
				diaPosible = numero;
				mesPosible = cal.get(Calendar.MONTH) + 1;
				añoPosible = cal.get(Calendar.YEAR);
				
				fecha = get2digitos(diaPosible) + " / " + get2digitos(mesPosible) + " / " + añoPosible;
				
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
					añoPosible = año;
				}
				else{
					añoPosible = año-1;
				}
			}
			else{
				return null;
			}
			
			fecha = get2digitos(diaPosible) + " / " + get2digitos(mesPosible) + " / " + añoPosible;
			
			return fecha;
		}
		else if(longitud == 6){
			String cadenas[] = new String[3];
			cadenas[0] = texto.substring(0,2);
			cadenas[1] = texto.substring(2,4);
			cadenas[2] = texto.substring(4);
			
			diaPosible = Integer.valueOf(cadenas[0]);
			mesPosible = Integer.valueOf(cadenas[1]);
			añoPosible = Integer.valueOf(cadenas[2]);
			
			if(2000 + añoPosible > año){
				añoPosible = 1900 + añoPosible;
			}
			else{
				añoPosible = 2000 + añoPosible;
			}
			
			fecha = get2digitos(diaPosible) + " / " + get2digitos(mesPosible) + " / " + añoPosible;
			
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
	
	
	private String getDiaSemana(int d){
		
		String cadena = "";
		switch (d) {
		case 1:
			cadena = "Domingo";
			break;
		case 2:
			cadena = "Lunes";
			break;
		case 3:
			cadena = "Martes";
			break;
		case 4:
			cadena = "Miércoles";
			break;
		case 5:
			cadena = "Jueves";
			break;
		case 6:
			cadena = "Viernes";
			break;
		case 7:
			cadena = "Sábado";
			break;

		default:
			break;
		}
		
		System.out.println("El dia es " + cadena);
		
		return cadena;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Fechas fechas = new Fechas();
		System.out.println(fechas.dia + " / " + fechas.mes + " / " + fechas.año);
		
		String cadena = fechas.adivinaFecha("0002");
		System.out.println(cadena);
	}
}
