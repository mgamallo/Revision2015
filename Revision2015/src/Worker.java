import java.awt.Frame;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;


class Worker extends SwingWorker<Double, Integer>{

	JProgressBar progresoNHCs;
	JProgressBar progresoServicios;
	JProgressBar progresoNombres;	
	
	CargaListaPdfs pdfs;
	int visualizacion;
	

	public Worker(CargaListaPdfs pdfs, int visualizacion, JProgressBar progresoNHCs, JProgressBar progresoServicios, JProgressBar progresoNombres){
		this.progresoNHCs = progresoNHCs;
		this.progresoServicios = progresoServicios;
		this.progresoNombres = progresoNombres;
		
		this.pdfs = pdfs;
		this.visualizacion = visualizacion;
	}
	
	
	@Override
	protected Double doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		int tamaño = pdfs.nombrePdfs.length;
		int tamañoLista = tamaño;
		        					
		Inicio.rutaCompletaPdfs = new String[tamaño];
//		rutaCompletaPdfs = new String[tamaño];
//		objetoPuente = new Object[tamañoLista];	//	Para pasar los datos a un jOptionPane (ya subidos)
	
		int aux = pdfs.ficheros.length;
		Inicio.listaDocumentos = new Documento[aux];
		int tamModelos = Inicio.modelos.size();
		System.out.println("Estamos en doInBackground, en el hilo " + 
				Thread.currentThread().getName());
		
		for(int i=0;i<aux;i++){
			Inicio.listaDocumentos[i] = new Documento(pdfs.ficheros[i].getAbsolutePath());
			System.out.println(Inicio.listaDocumentos[i].rutaArchivo);
		}
		
		for(int i=0;i<aux;i++){
			for(int j=0;j<tamModelos;j++){
				if(Inicio.listaDocumentos[i].detector(Inicio.modelos.get(j))){
					break;
				}
			}
			// publish( Porcentaje NHC, PorcentajeDocumentos, PorcentajeServicios)
			Robot robot = new Robot();
			robot.delay(400);
			System.out.println("Pdf número..." + i );
			publish( i,0,0);
		}
		
		System.out.println("Segunda tanda de reconocimiento...");
		
		for(int i=0;i<aux;i++){
			for(int j=0;j<tamModelos;j++){
				if(Inicio.listaDocumentos[i].reDetectorNHC(Inicio.modelos.get(j))){
					break;
				}
			}
		}
		
		// Tercera tanda de reconocimiento solo para urgencias
		for(int i=0;i<aux;i++){
				Inicio.listaDocumentos[i].reDetectorNHCUrgencias();
		}
		
		// Reconocimientos varios
		for(int i=0;i<aux;i++){
			 Inicio.listaDocumentos[i].nhc = NHC.nhcTriaje143(Inicio.listaDocumentos[i]);
		}
		
		//	Reconocimiento de ekg´s y ecos
		
		for(int i=0;i<aux;i++){
			Inicio.listaDocumentos[i].detectaEcos();
			Inicio.listaDocumentos[i].detectaEKGs();
			Inicio.listaDocumentos[i].detectaMonitor();
			Inicio.listaDocumentos[i].detectaDocRosa();
			
			
			System.out.println("Publico... " + i);
			
			Robot robot = new Robot();
			robot.delay(50);
			publish(i,i/aux*100,10);
		}
		
		
		Inicio.separadores = new ArrayList<Integer>();
		Inicio.separadores = new Separadores().getNumOrdenSeparadores();
		System.out.println("El primer separador vale: " + Inicio.separadores);
   		
		
		//	Adivina nombre separador
		int numSeparador = 1;
		for(int i=Inicio.separadores.get(0);i<Inicio.listaDocumentos.length;i++){
			String servicioPosible = AdivinaServicio.getServicio(i + 1,Inicio.separadores.get(numSeparador));
			
			System.out.println("Servicio Posible: " + servicioPosible);
			
			if(i == -1){
				i = 0;
			}
			for(int j=i;j<Inicio.separadores.get(numSeparador);j++){
				
				//Comprobamos si el servicio es anestesia para hacer el cambio anrc - carc
				if(servicioPosible.equals("ANR") || servicioPosible.equals("NRL")){
					System.out.println("Anestesia o Neurologia");
					if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.EKG)) {
						Inicio.listaDocumentos[j].servicio = "CAR";
					}
					else{
						Inicio.listaDocumentos[j].servicio = servicioPosible;
					}
					if(j-1 >= i){
						
						System.out.println("Neurologia - interconsulta");
						
						if(servicioPosible.equals("NRL")){
							if(Inicio.listaDocumentos[j-1].nombreNormalizado.equals(Inicio.EKG) 
									&& Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.INTERCONSULTA)) {
								Inicio.listaDocumentos[j].servicio = "CAR";
							}
						}
					}
				}
				else if(servicioPosible.equals("ORL")){
					
					System.out.println("Otorrino - videonistag");
					
					if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.EKG)){
						Inicio.listaDocumentos[j].nombreNormalizado = "Videonistagmografía";
					}
					Inicio.listaDocumentos[j].servicio = servicioPosible;
				}

				else if(servicioPosible.equals("CIA")){
					
					System.out.println("CIAS toas");
					
					Inicio.listaDocumentos[j].servicio = servicioPosible;
				}
				
				else if(servicioPosible.equals("HOSP")){
					System.out.println("Hospitalizac. menos las excepciones");
					if(!Excepciones.excepcionesIngresos(j)){
						Inicio.listaDocumentos[j].servicio = servicioPosible;
					}
					
				}
				else if(!servicioPosible.equals("")){
					
					System.out.println("Toas las demas");
					Inicio.listaDocumentos[j].servicio = servicioPosible;
				}
				Robot robot = new Robot();
				robot.delay(100);
				publish(100,j,100);
			}
			
			i= Inicio.separadores.get(numSeparador) -1 ;
			numSeparador++;
		}
		

		
		int errores = 0;
		for(int i=0;i<Inicio.listaDocumentos.length;i++){
			if(!Inicio.listaDocumentos[i].renombraFichero(Inicio.listaDocumentos[0]))
				errores++;
		}
		
		System.out.println(errores + " errores");
		        					
		Inicio.modelo = new DefaultListModel();

		//	Almacena las carpetas por las que navega el usuario
		if(tamaño>0){
			String auxS = pdfs.rutaPdfs[0];
			int auxInt = auxS.lastIndexOf("\\");
			auxS = auxS.substring(0,auxInt);
			auxInt = auxS.lastIndexOf("\\");
			auxS = auxS.substring(0, auxInt);
			//System.out.println(aux);
			Inicio.carpetasAbiertas.add(auxS);
		}
		        					
		for(int i=0;i<tamaño;i++){
			Inicio.modelo.addElement(pdfs.nombrePdfs[i]);
	//		objetoPuente[i] = pdfs.nombrePdfs[i];
	//		rutaCompletaPdfs[i] = pdfs.rutaPdfs[i];
			Inicio.rutaCompletaPdfs[i] = pdfs.rutaPdfs[i];
		}
		
		Inicio.tamañoCarpetaPdf = tamaño;
		
		//	Determina el directorio firmados
		        					
		Inicio.ventanaExplorador.listaPdfs.setModel(Inicio.modelo);
//  					listaPdfs.setFont(new Font("Arial",Font.BOLD,10));
    	Inicio.ventanaExplorador.setTitle(pdfs.getRutaCarpeta());
		Inicio.ficherosCargados= true;
	
    if(Inicio.ficherosCargados){
    	// vp.dispose();
    	if(Inicio.ventanaRevisionAbierta == false){
        /*
        	 java.awt.EventQueue.invokeLater(new Runnable() {
        		        		        		public void run() {
        	        jMenu3.setEnabled(true);
        	        jMenu2.setEnabled(true);
        	        jMenuItem51.setEnabled(true);

        		//	Inicio.ventanaD = new InterFazTabla();
        		//	Inicio.ventanaD.setVisible(true);  
        	        
        	        Inicio.ventanaPrincipal = new VentanaPrincipal();
        	        Inicio.ventanaCompacta = new VentanaCompacta();
        			
        		}
        	});
		*/

    		Inicio.ventanaPrincipal = new VentanaPrincipal();
	        Inicio.ventanaCompacta = new VentanaCompacta();
        	Inicio.ventanaPrincipal.setBounds(Inicio.coordenadas.coordenadas[3].x, Inicio.coordenadas.coordenadas[3].y, 750, 650);
	        Inicio.ventanaCompacta.setBounds(Inicio.coordenadas.coordenadas[2].x, Inicio.coordenadas.coordenadas[2].y, 750, 180);

    		if(visualizacion == 2 || visualizacion == 1){
	        	Inicio.ventanaPrincipal.setVisible(false);
    	        Inicio.ventanaCompacta.setVisible(false);
    	        
    	        Inicio.ventanaCompacta.jPanel1.removeKeyListener(Inicio.ventanaCompacta.listener);
    			
    	        //Inicio.ventanaA3 = new VentanaA3(true);
    	        Inicio.ventanaIntegral = new VentanaIntegral();
    	        Inicio.ventanaIntegral.setBounds(Inicio.coordenadas.coordenadas[4].x, Inicio.coordenadas.coordenadas[4].y, 360,1150);
    	        
    	        Inicio.ventanaMicro = new VentanaMicro();
    	        Inicio.ventanaMicro.setBounds(Inicio.coordenadas.coordenadas[5].x, Inicio.coordenadas.coordenadas[5].y, 680, 90);
    	        
    	        // Inicio.ventanaNombres = new VentanaNombres();
    	        // Inicio.ventanaNombresYServicios = new VentanaNombresYServicios();
    			if(Inicio.nombrePc.equals("mahc13p")){
    				Inicio.acrobatAntiguo = true;
    				Inicio.rutaFocoAcrobat = "cal\\FocoAcrobatV2.exe";
    			}
    			else{
    				Inicio.rutaFocoAcrobat = "cal\\FocoAcrobatV.exe";
    			}
    			Inicio.ventanaExplorador.setState(Frame.ICONIFIED);
    		}
	        
	        File archivo2 = new File(Inicio.rutaFocoAcrobat);
	        File archivo3 = new File(Inicio.rutaFocoNHC);
	        try {
				 Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + archivo2);
				 Process pNHC = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + archivo3);
	        	    		        	        	
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
        	Inicio.ventanaRevisionAbierta = true;

    	}
    	
    	if(Inicio.documentacionDeUrgencias){
    		Inicio.ventanaExplorador.renombraURG();
    	}
    	
    	/*
    	else{
    		if(Inicio.separadores.get(0) == -1){
        		JOptionPane.showMessageDialog(null, "No se ha detectado un separador. Puedes fijar el" +
        				" servicio de los documentos, en el botón fijar servicios");
        	}
    	}
    	*/
    	// vp.dispose();

    }
		return 100.0;
	}
	
	protected void done(){
		System.out.println("hecho");
		Inicio.progreso = true;
		//progresoNHCs.setValue(75);
	}
	
	@Override
    protected void process(List<Integer> chunks) {
        System.out.println("process() esta en el hilo "
                + Thread.currentThread().getName());
        progresoNHCs.setValue(chunks.get(0));
        progresoServicios.setValue(chunks.get(1));
        progresoNombres.setValue(60);
    }
	
}