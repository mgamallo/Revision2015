import java.awt.AWTException;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.itextpdf.awt.geom.Rectangle;


class Worker extends SwingWorker<Double, Integer>{

	JProgressBar progresoNHCs;
	JProgressBar progresoServicios;
	JProgressBar progresoNombres;
	JProgressBar progresoRenombrar;
	
	JTextField textoPdfExaminado;
	
	VentanaProgreso vProgreso;
	CargaListaPdfs pdfs;
	int visualizacion;
	

	public Worker(VentanaProgreso vProgreso,CargaListaPdfs pdfs, int visualizacion, JProgressBar progresoNHCs, JProgressBar progresoServicios, JProgressBar progresoNombres, JProgressBar progresoRenombrar, JTextField textoPdfExaminado){
		this.progresoNHCs = progresoNHCs;
		this.progresoServicios = progresoServicios;
		this.progresoNombres = progresoNombres;
		this.progresoRenombrar = progresoRenombrar;
		
		this.textoPdfExaminado = textoPdfExaminado;
		
		this.pdfs = pdfs;
		this.visualizacion = visualizacion;
		this.vProgreso = vProgreso;
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
			Inicio.listaDocumentos[i].getNhc();
			publish( i*100/aux,0,0,0,i);
		}
		
		for(int i=0;i<aux;i++){
			for(int j=0;j<tamModelos;j++){
				if(Inicio.listaDocumentos[i].detector(Inicio.modelos.get(j))){
					break;
				}
			}
			// publish( Porcentaje NHC, PorcentajeDocumentos, PorcentajeServicios, PorcentajeRenombrar, nº de pdf)

			System.out.println("Pdf número nhc..." + i );
			publish( 0,i*100/aux,0,0,i);
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
			
			publish(100,i*100/aux,0,0,i);
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
				if(servicioPosible.equals(Inicio.ANRC) || servicioPosible.equals(Inicio.NRLC)){
					System.out.println("Anestesia o Neurologia");
					if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.EKG)) {
						Inicio.listaDocumentos[j].servicio = Inicio.CARC;
					}
					else{
						Inicio.listaDocumentos[j].servicio = servicioPosible;
					}
					if(j-1 >= i){
						
						System.out.println("Neurologia - interconsulta");
						
						if(servicioPosible.equals(Inicio.NRLC)){
							if(Inicio.listaDocumentos[j-1].nombreNormalizado.equals(Inicio.EKG) 
									&& Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.INTERCONSULTA)) {
								Inicio.listaDocumentos[j].servicio = Inicio.CARC;
							}
						}
					}
				}
				else if(servicioPosible.equals(Inicio.ORLC)){
					
					System.out.println("Otorrino - videonistag");
					
					if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.EKG)){
						Inicio.listaDocumentos[j].nombreNormalizado = Inicio.VIDEONISTAGMOGRAFÍA;
					}
					Inicio.listaDocumentos[j].servicio = servicioPosible;
				}
				else if(servicioPosible.equals(Inicio.UDOC)){
					if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.EKG)){
						Inicio.listaDocumentos[j].nombreNormalizado = Inicio.MAPA_DERMATOMAS;
					}
					Inicio.listaDocumentos[j].servicio = servicioPosible;
				}
				else if(servicioPosible.equals(Inicio.CIA)){
					
					System.out.println("CIAS toas");
					
					Inicio.listaDocumentos[j].servicio = servicioPosible;
				}
				
				else if(servicioPosible.equals(Inicio.HOSP)){
					System.out.println("Hospitalizac. menos las excepciones");
					if(!Excepciones.excepcionesIngresos(j)){
						Inicio.listaDocumentos[j].servicio = servicioPosible;
					}
					
				}
				else if(servicioPosible.equals(Inicio.CARC) || servicioPosible.equals(Inicio.PEDC)){
					if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.ECO)){
						Inicio.listaDocumentos[j].nombreNormalizado = Inicio.ECOCARDIOGRAFIA;
					}
					Inicio.listaDocumentos[j].servicio = servicioPosible;
				}
				else if(!servicioPosible.equals("")){
					
					System.out.println("Toas las demas");
					Inicio.listaDocumentos[j].servicio = servicioPosible;
				}

				
			}
			
			publish(100,100,i*100/Inicio.listaDocumentos.length,0,0);
			
			i= Inicio.separadores.get(numSeparador) -1 ;
			numSeparador++;
		}
		

		
		int errores = 0;
		for(int i=0;i<Inicio.listaDocumentos.length;i++){
			if(!Inicio.listaDocumentos[i].renombraFichero(Inicio.listaDocumentos[0]))
				errores++;

			publish(100,100,100,i*100/Inicio.listaDocumentos.length,i);
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
		
		System.out.println("Determinando el directorio firmados");
		        					
		Inicio.ventanaExplorador.listaPdfs.setModel(Inicio.modelo);
//  					listaPdfs.setFont(new Font("Arial",Font.BOLD,10));
    	Inicio.ventanaExplorador.setTitle(pdfs.getRutaCarpeta());
		Inicio.ficherosCargados= true;
	

    	
    	/*
    	else{
    		if(Inicio.separadores.get(0) == -1){
        		JOptionPane.showMessageDialog(null, "No se ha detectado un separador. Puedes fijar el" +
        				" servicio de los documentos, en el botón fijar servicios");
        	}
    	}
    	*/
    	// vp.dispose();


		return 100.0;
	}
	
	protected void done(){
		System.out.println("hecho");
		Inicio.progreso = true;
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
	    		System.out.println("Las ventanas no estan abiertas");
	    		
	    		System.out.println("Visualización vale..." + visualizacion);

	 
	    		Inicio.ventanaPrincipal = new VentanaPrincipal();
		   //     Inicio.ventanaCompacta = new VentanaCompacta();
		        Inicio.ventanaPrincipal.setBounds(Inicio.coordenadas.coordenadas[3].x, Inicio.coordenadas.coordenadas[3].y, 750, 970);
		        Inicio.ventanaPrincipal.setResizable(false);
		        
		   /*     Inicio.ventanaCompacta.setBounds(Inicio.coordenadas.coordenadas[2].x, Inicio.coordenadas.coordenadas[2].y, 750, 180);
		        Inicio.ventanaCompacta.jPanel1.removeKeyListener(Inicio.ventanaCompacta.listener);
		        Inicio.ventanaCompacta.setVisible(false); */
		        
		        Inicio.ventanaMicro = new VentanaMicro();
    	        Inicio.ventanaMicro.setBounds(Inicio.coordenadas.coordenadas[5].x, Inicio.coordenadas.coordenadas[5].y, 730, 60);
    	        
    		    Inicio.ventanaFechas = new VentanaFechas();
    		    Inicio.ventanaFechas.setVisible(false);
    		    java.awt.Rectangle rect  = Inicio.ventanaFechas.getBounds();
    		    
		        Inicio.ventanaFechas.setBounds(Inicio.coordenadas.coordenadas[3].x, Inicio.coordenadas.coordenadas[3].y + 1000, rect.width, rect.height);

    	        
		        System.out.println("Visualización vale..." + visualizacion);
		        
	    		if(visualizacion == 2 || visualizacion == 1){
		        	Inicio.ventanaPrincipal.setVisible(false);
	    	       //

		    	        Inicio.ventanaIntegral = new VentanaIntegral();
		    	        Inicio.ventanaIntegral.setBounds(Inicio.coordenadas.coordenadas[4].x, Inicio.coordenadas.coordenadas[4].y, 360,1150);

	        

	    	        // Inicio.ventanaNombres = new VentanaNombres();
	    	        // Inicio.ventanaNombresYServicios = new VentanaNombresYServicios();
	    			if(Inicio.nombrePc.equals("mahc13p")){
	    				Inicio.acrobatAntiguo = true;
	    				Inicio.rutaFocoAcrobatV = "cal\\FocoAcrobatV2.exe";
	    			}
	    			else{
	    				Inicio.acrobatAntiguo = false;
	    				Inicio.rutaFocoAcrobatV = "cal\\FocoAcrobatV.exe";
	    			}
	    			/*
	    			else{
	    				Inicio.rutaFocoAcrobat = "cal\\FocoAcrobatV.exe";
	    			}
	    			*/
	    			Inicio.ventanaExplorador.setState(Frame.ICONIFIED);
	    		}
		        
	    		File archivo2 = null;
	    		if(visualizacion == 0){
	    			archivo2 = new File(Inicio.rutaFocoAcrobat);
	    		}
	    		else{
	    			archivo2 = new File(Inicio.rutaFocoAcrobatV);
	    		}
		        
		        System.out.println("El archivo existe?" + archivo2.exists());
		 //       File archivo3 = new File(Inicio.rutaFocoNHC);
		        try {
					 Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + archivo2);
		//			 Process pNHC = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + archivo3);
		        	    		        	        	
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
	        	Inicio.ventanaRevisionAbierta = true;

	    	}
	    	else{
	    		if(visualizacion == 0){
		    		Inicio.ventanaPrincipal = new VentanaPrincipal();
	 		        Inicio.ventanaPrincipal.setBounds(Inicio.coordenadas.coordenadas[3].x, Inicio.coordenadas.coordenadas[3].y, 750, 970);
	 		        Inicio.ventanaPrincipal.setResizable(false);
	    			Inicio.ventanaPrincipal.setVisible(true);
	    			
	    			Inicio.utiles.habilitarTeclas("Teclas On", Inicio.visualizacion);
	    			Inicio.utiles.habilitarTeclas("Teclas Off", Inicio.visualizacion);
	    			
	    			if(Inicio.ventanaIntegral != null)
	    				Inicio.ventanaIntegral.setVisible(false);
	    			
	    			Inicio.ventanaExplorador.setState(Frame.NORMAL);
	    		}
	    		else if(visualizacion == 1 || visualizacion == 2){
	    			
	    			Inicio.utiles.habilitarTeclas("Teclas On", Inicio.visualizacion);
	    			Inicio.utiles.habilitarTeclas("Teclas Off", Inicio.visualizacion);
	    			
	    			if(Inicio.ventanaPrincipal != null)
	    				Inicio.ventanaPrincipal.setVisible(false);
	    			if(Inicio.ventanaIntegral != null)
	    				Inicio.ventanaIntegral.setVisible(true);
	    			else{
		    	        Inicio.ventanaIntegral = new VentanaIntegral();
		    	        Inicio.ventanaIntegral.setBounds(Inicio.coordenadas.coordenadas[4].x, Inicio.coordenadas.coordenadas[4].y, 360,1150);
	    			}
	    		}
	    	}
	    	
	    	if(Inicio.documentacionDeUrgencias){
	    		Inicio.ventanaExplorador.renombraURG();
	    	}
	    }
		vProgreso.dispose();
	}
	
	@Override
    protected void process(List<Integer> chunks) {
        System.out.println("process() esta en el hilo "
                + Thread.currentThread().getName());
        progresoNHCs.setValue(chunks.get(0));
        progresoServicios.setValue(chunks.get(1));
        progresoNombres.setValue(chunks.get(2));
        progresoRenombrar.setValue(chunks.get(3));
        
        File file = new File(Inicio.listaDocumentos[chunks.get(4)].rutaArchivo);
        

		if(file.getName().length() > 40){
			textoPdfExaminado.setFont(new Font("TimesRoman", Font.BOLD, 12));
		}
		else{
			textoPdfExaminado.setFont(new Font("TimesRoman", Font.BOLD, 20));
		}
        
        textoPdfExaminado.setText(file.getName());
    }
	
}