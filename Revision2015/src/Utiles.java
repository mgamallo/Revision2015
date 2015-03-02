import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;


public class Utiles {
	
	



	public void ventanaRenombrarServicios() {
		// TODO Auto-generated method stub
		
		Object seleccion = JOptionPane.showInputDialog(
				Inicio.jBServicio, "¿Fijar el servicio de todos los pdfs hasta el próximo separador?",
				"Fijar servicio en bloque",
				JOptionPane.OK_OPTION,
				null, // icono, // unIcono, // null para icono defecto
				new Object[] { "SI", "NO" },
				"NO"
				);
		if(seleccion.equals("SI")){
			renombraServicios();	
			Inicio.jLServicios.setSelectedValue(Inicio.jBServicio.getText(), true);
			Inicio.jLNombresDoc.setModel(Inicio.excel.getDocServicio(Inicio.jBServicio.getText()));
		}
	}

	public void renombraServicios(){
		for(int i=0;i < Inicio.separadores.size()-1;i++){
			if(Inicio.numeroPdf >= Inicio.separadores.get(i) && Inicio.numeroPdf < Inicio.separadores.get(i+1)){
				
					
				for(int j= Inicio.numeroPdf + 1; j < Inicio.separadores.get(i+1); j++){
					if( Inicio.listaDocumentos[j].servicio != "X" && Inicio.listaDocumentos[j].servicio != Inicio.jBServicio.getText()){
						Inicio.listaDocumentos[j].semaforoAmarilloServicio = true;
					}

						//	Comprobamos si el servicio es anestesia para hacer el cambio anrc - carc
						if(Inicio.jBServicio.getText().equals(Inicio.ANRC)){
							if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.EKG)){
								Inicio.listaDocumentos[j].servicio = Inicio.CARC;
							}
							else{
								Inicio.listaDocumentos[j].servicio = Inicio.jBServicio.getText();
							}
						}
						
						else if(Inicio.jBServicio.getText().equals(Inicio.ORLC)){
							if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.EKG)){
								Inicio.listaDocumentos[j].nombreNormalizado = Inicio.VIDEONISTAGMOGRAFÍA;
							}
							Inicio.listaDocumentos[j].servicio = Inicio.jBServicio.getText();
						}

						
						else if(Inicio.jBServicio.getText().equals(Inicio.DERC) || Inicio.jBServicio.getText().equals(Inicio.ETMC) ){
							if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.ENFERMERIA_QUIRURGICA)){
								Inicio.listaDocumentos[j].servicio = Inicio.CIA;
							}
							Inicio.listaDocumentos[j].servicio = Inicio.jBServicio.getText();
						}
						else if(Inicio.jBServicio.getText().equals(Inicio.CARC) || Inicio.jBServicio.getText().equals(Inicio.PEDC)){
							if(Inicio.listaDocumentos[j].nombreNormalizado.equals(Inicio.ECO)){
								Inicio.listaDocumentos[j].nombreNormalizado = Inicio.ECOCARDIOGRAFIA;
							}
							Inicio.listaDocumentos[j].servicio = Inicio.jBServicio.getText();
						}
						else{
							Inicio.listaDocumentos[j].servicio = Inicio.jBServicio.getText();
						}
						
						
						// Inicio.listaDocumentos[j].revisado = true;
						System.out.println("Modificando pdf numero " + j + " a " + Inicio.listaDocumentos[j].servicio);
						
					}
			}
		}
	}
	
	public void ventanaRenombrarNombres() {
		// TODO Auto-generated method stub
		Object seleccion = JOptionPane.showInputDialog(
				Inicio.jBServicio, "¿Cambiar el nombre de todos los pdfs hasta el próximo separador?",
				"Fijar nombre en bloque",
				JOptionPane.OK_OPTION,
				null, // icono, // unIcono, // null para icono defecto
				new Object[] { "SI", "NO" },
				"NO"
				);
		if(seleccion.equals("SI")){
			renombraNombres();
		}
	}
	
	public void renombraNombres(){
		for(int i=0;i < Inicio.separadores.size()-1;i++){
			if(Inicio.numeroPdf >= Inicio.separadores.get(i) && Inicio.numeroPdf < Inicio.separadores.get(i+1)){
				for(int j= Inicio.numeroPdf + 1; j < Inicio.separadores.get(i+1); j++){
					if( Inicio.listaDocumentos[j].nombreNormalizado != "X" && Inicio.listaDocumentos[j].nombreNormalizado != Inicio.jBNombreDoc.getText()){
						Inicio.listaDocumentos[j].semaforoAmarilloNombre = true;
					}
					Inicio.listaDocumentos[j].nombreNormalizado = Inicio.jBNombreDoc.getText();
					// Inicio.listaDocumentos[j].revisado = true;
					System.out.println("Modificando pdf numero " + j + " a " + Inicio.listaDocumentos[j].nombreNormalizado);
					
				}
			}
		}
	}

	public void actualizaServicio(){
    	Inicio.jBServicio.setText(Inicio.jLServicios.getSelectedValue().toString());
		Inicio.jBServiciop.setText(Inicio.jLServicios.getSelectedValue().toString());
		/*
		if(Inicio.menuVertical){
			Inicio.ventanaMicro.jBServiciom.setText(Inicio.jLServicios.getSelectedValue().toString());
			Inicio.ventanaMicro.jBServiciom.setBackground(new java.awt.Color(153, 255, 153));
		}
		*/
		Inicio.jLNombresDoc.setModel(Inicio.excel.getDocServicio(Inicio.jBServicio.getText()));
		
		Inicio.jBServicio.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBServiciop.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBServicio.setIcon(null);
		Inicio.jBServiciop.setIcon(null);
		Inicio.jCheckBox1.setSelected(false);
		
				
		if(Inicio.jBNHC.getText().equals("Separador")){
			Inicio.jBNHC.setBackground(new java.awt.Color(153, 255, 153));
			Inicio.jBNHCp.setBackground(new java.awt.Color(153, 255, 153));
			/*
			if(Inicio.menuVertical){
				Inicio.ventanaMicro.jBNHCm.setBackground(new java.awt.Color(153, 255, 153));
			}
			*/
			
			Inicio.utiles.renombraServicios();
		}
		
		
		new FocalAdobe(100);
	}

	public void jCheckBox1ActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if (Inicio.jCheckBox1.isSelected()) {
			Inicio.jLNombresDoc.setModel(Inicio.excel.listaDocumentosDLM);
		} else if (!Inicio.jCheckBox1.isSelected()) {
			Inicio.jLNombresDoc.setModel(Inicio.excel.getDocServicio(Inicio.jLServicios
					.getSelectedValue().toString()));
		}
		
	}
	
	public void registraCambiosFinales() {
		// TODO Auto-generated method stub
		
		if(Inicio.ventanaComprobacion != null){
			Inicio.ventanaComprobacion.dispose();
		}
		
    	new Acrobat().guardarPagina();
    	revisarPropiedadesDocumento();
    	
    	Inicio.ventanaComprobacion = new VentanaComprobar();
    	if(Inicio.erroresAntesRegistrar){
    		int opcion = JOptionPane.showOptionDialog(null, "Hay errores en la revisión. ¿Quieres continuar registrando o quieres corregir los errores?", "Registrando", 
    				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[] {"Continuar","Corregir"}, "Corregir");
    		if(opcion == 0){
    			Inicio.erroresAntesRegistrar = false;
    			Inicio.ventanaComprobacion.dispose();
    		}
    		else{
    			Inicio.erroresAntesRegistrar = true;
    		}
    	}
 
    	
    	if(!Inicio.erroresAntesRegistrar){
	    	new CerrarTodo().closePdf();
	    	System.out.println("Cerrados los pdfs");
	    	
	    	
	    	
	    	
	    	for(int i=0;i<Inicio.listaDocumentos.length;i++){
	    		
	    			File nombreAntiguo = new File(Inicio.listaDocumentos[i].rutaArchivo);
	    			
	    			
	    			if(Inicio.listaDocumentos[i].nhc.equals("Eliminar")){
	    				if(nombreAntiguo.delete()){
	    					System.out.println("Fichero eliminado");
	    				}else{
	    					System.out.println("Fichero imposible de eliminar");
	    				}
	    			}
	    			else if(Inicio.listaDocumentos[i].nhc.equals("Apartar")){
	    				
	    				int aux = Inicio.listaDocumentos[i].rutaArchivo.lastIndexOf("\\");
	    				String nombrepdf = Inicio.listaDocumentos[i].rutaArchivo.substring(aux);
	    				
	    				String rutaNueva = Inicio.listaDocumentos[i].apartaFichero() + "Apartado por " + Inicio.usuario + ".";
	    				
	    				File carpetaNueva = new File(rutaNueva);
	    				carpetaNueva.mkdirs();
	    				
	    				rutaNueva = rutaNueva + nombrepdf;
	    				System.out.println("Ruta apartado... " + rutaNueva);
	    				
	    				
	    				File nombreNuevo = new File(rutaNueva);
		    			boolean correcto = nombreAntiguo.renameTo(nombreNuevo);
		    			if(correcto){
		    				System.out.println("Renombrado con exito");
		    			}
		    			else{
		    				System.out.println("Error al renombrar");
		    			}
		    			
	    			}
	    			else{
	    				String rutaNueva = Inicio.listaDocumentos[i].registraFichero();
		    			File nombreNuevo = new File(rutaNueva);
		    			boolean correcto = nombreAntiguo.renameTo(nombreNuevo);
		    			if(correcto){
		    				System.out.println("Renombrado con exito");
		    			}
		    			else{
		    				System.out.println("Error al renombrar");
		    			}
	    			}
	    			
	    			
	    			
	    			
	    		//}
	    		/*
	    		String rutaNueva = Inicio.listaDocumentos[i].registraFichero();
	    		
	    			try {
	    				
	    				//	Formatea las paginas en ancho
	    				
	    				if(Inicio.listaDocumentos[i].fisica.tamañoPagina != 1){
	    					PdfReader pdf = new PdfReader(Inicio.listaDocumentos[i].rutaArchivo);
							
	    					PdfStamper stp = new PdfStamper(pdf, new FileOutputStream(rutaNueva));
							PdfWriter writer = stp.getWriter();
							PdfAction pdfAcc;

							pdfAcc = PdfAction.gotoLocalPage(1,new PdfDestination(PdfDestination.FITH), writer);
							
							writer.setOpenAction(pdfAcc);
							stp.close();
							pdf.close();
							
	    				}
 					
					}  catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			*/
	    		}
	       	
	    	
	    	Inicio.listaCarpetasRegistradas.add(Inicio.rutaCarpetaEscaneadaUsuario);
	    		    	
	    	Inicio.modelo.removeAllElements();
	    	Inicio.jBNHC.setText("");
	    	Inicio.jBNHCp.setText("");
	    	/*
			if(Inicio.menuVertical){
				Inicio.ventanaMicro.jBNHCm.setText("");
				Inicio.ventanaMicro.jBServiciom.setText("");
				Inicio.ventanaMicro.jBNombreDocm.setText("");
			}
			*/
	    	Inicio.jBServicio.setText("");
	    	Inicio.jBServiciop.setText("");
	    	Inicio.jBNombreDoc.setText("");
	    	Inicio.jBNombreDocp.setText("");

    			
    		}

    	//	    	Eliminar la carpeta de asociados
		/*
		File borrarCarpeta = new File(Inicio.rutaCarpetaEscaneadaUsuario);
		
		File[] listaFicheros = borrarCarpeta.listFiles();
		for(int z=0;z<listaFicheros.length;z++){
			if(listaFicheros[z].delete())
				System.out.println("Fichero borrado en la carpeta escaneado");;
		}
		
		if(borrarCarpeta.delete()){
			System.out.println("El directorio ha sido borrado");
		}else{
			System.out.println("Directorio no borrado");
		}
		*/
 
  
    	
		if(Inicio.A3){
			
		  	JOptionPane.showMessageDialog(null, "A continuación se va a abrir el programa de formatear A3");
			
			System.out.println("Iniciando programa");
			File archivo3 = new File("FormatoA3.jar");
			try {
				Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + archivo3);
	
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}


	}

	public void revisarPropiedadesDocumento(){
	    	if(!Inicio.listaDocumentos[Inicio.numeroPdf].nhc.equals(Inicio.jBNHCp.getText())){
	    		System.out.println("Cambiamos el nhc");
	    		Inicio.listaDocumentos[Inicio.numeroPdf].nhc = Inicio.jBNHCp.getText();
	    		Inicio.listaDocumentos[Inicio.numeroPdf].revisado = true;
	    	}
	    	if(!Inicio.listaDocumentos[Inicio.numeroPdf].servicio.equals(Inicio.jBServiciop.getText())){
	    		System.out.println("Cambiamos el servicio");
	    		Inicio.listaDocumentos[Inicio.numeroPdf].servicio = Inicio.jBServiciop.getText();
	    		Inicio.listaDocumentos[Inicio.numeroPdf].revisado = true;
	    	}
	    	if(!Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado.equals(Inicio.jBNombreDocp.getText())){
	    		System.out.println("Cambiamos el nombre del documento");
	    		Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado = Inicio.jBNombreDocp.getText();
	    		Inicio.listaDocumentos[Inicio.numeroPdf].revisado = true;
	    	}
	    	Inicio.listaDocumentos[Inicio.numeroPdf].semaforoAmarilloServicio = false;
	    	Inicio.listaDocumentos[Inicio.numeroPdf].semaforoAmarilloNombre = false;

	    }

	public void jBApartarActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
		Inicio.jBNHC.setText("Apartar");
		Inicio.jBNHCp.setText("Apartar");
		Inicio.jBServicio.setText("Apartar");
		Inicio.jBServiciop.setText("Apartar");
		Inicio.jBNombreDoc.setText("Apartar");
		Inicio.jBNombreDocp.setText("Apartar");
		
		/*
		if(Inicio.menuVertical){
			Inicio.ventanaMicro.jBNHCm.setText("Apartar");
			Inicio.ventanaMicro.jBServiciom.setText("Apartar");
			Inicio.ventanaMicro.jBNombreDocm.setText("Apartar");
			
			Inicio.ventanaMicro.jBNHCm.setBackground(Color.gray);
			Inicio.ventanaMicro.jBServiciom.setBackground(Color.gray);
			Inicio.ventanaMicro.jBNombreDocm.setBackground(Color.gray);
		}
		*/
		
		Inicio.jBNHC.setBackground(Color.gray);
		Inicio.jBNHCp.setBackground(Color.gray);
		Inicio.jBServicio.setBackground(Color.gray);
		Inicio.jBServiciop.setBackground(Color.gray);
		Inicio.jBNombreDoc.setBackground(Color.gray);
		Inicio.jBNombreDocp.setBackground(Color.gray);
	}

	public void jBCarpetaActionPerformed(ActionEvent evt) {
			// TODO Auto-generated method stub
	    	String cadena = "explorer.exe " + Inicio.carpetaActualRevisando;
			try {
				Runtime.getRuntime().exec(cadena);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void habilitarTeclas(String texto, int visualizacion) {
		if (texto.equals("Teclas On")) {
			String cmd = "taskkill.exe /F /IM FocoAcrobat2015.exe /T";
			String cmd2 = "taskkill.exe /F /IM FocoNHC.exe /T";
			String cmd3 = "taskkill.exe /F /IM FocoAcrobat2.exe /T";
			String cmd4 = "taskkill.exe /F /IM FocoAcrobatV.exe /T";
			
			Process hijo, hijo2, hijo3, hijo4;
			try {
				hijo = Runtime.getRuntime().exec(cmd);

				hijo.waitFor();
				hijo2 = Runtime.getRuntime().exec(cmd2);

				hijo2.waitFor();
				
				hijo3 = Runtime.getRuntime().exec(cmd3);

				hijo3.waitFor();
				
				hijo4 = Runtime.getRuntime().exec(cmd4);
				hijo4.waitFor();

				Thread.sleep(300);

			} catch (IOException e) {
				// System.out.println("Incapaz de matar.");
			} catch (InterruptedException e) {
				// System.out.println("Incapaz de matar.");
			}

			Inicio.jBDeshabilitar.setText("Teclas Off");
			Inicio.jBDeshabilitar.setBackground(Color.cyan);

		} else if (texto.equals("Teclas Off")) {

			File archivo2 = null;
			System.out.println("Visualizacion ... " + visualizacion);
			if(visualizacion == 0){
				archivo2 = new File(Inicio.rutaFocoAcrobat);
			}
			else if(visualizacion == 1 || visualizacion == 2){
				archivo2 = new File(Inicio.rutaFocoAcrobatV);
			}
			System.out.println("autohotkey..." + archivo2.getName());
			File archivo3 = new File(Inicio.rutaFocoNHC);
			try {
				Process p = Runtime.getRuntime().exec(
						"rundll32 url.dll,FileProtocolHandler " + archivo2);
				Process pNHC = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + archivo3);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Inicio.jBDeshabilitar.setText("Teclas On");
			Inicio.jBDeshabilitar.setBackground(Color.pink);

		}
	}
	
	public void detectaNHCsecuencial(){
		
		/*
		 System.out.println("Vamos en el pdf numero " + Inicio.numeroPdf);

		System.out.println("Empieza la busqueda secuencial de nhc");
		
		System.out.println(Inicio.listaDocumentos[Inicio.numeroPdf].nhc);
		*/
		
		if(Inicio.listaDocumentos[Inicio.numeroPdf].nhc.contains("ERROR") || 
				Inicio.listaDocumentos[Inicio.numeroPdf].nhc.contains("NO")){
			if(Inicio.numeroPdf >0 && Inicio.numeroPdf < (Inicio.tamañoCarpetaPdf - 2) ){
				/*
				System.out.println("Numero pdf " + Inicio.numeroPdf);
				System.out.println("Tamaño carpeta, a la que se le resta 2: " +  Inicio.tamañoCarpetaPdf);
				*/
				
				int proximoNHC = Inicio.numeroPdf;
				boolean encontrado = false;
				while((proximoNHC +1 < Inicio.tamañoCarpetaPdf) && !encontrado){
					
					// System.out.println("ProximoNHC " + proximoNHC);
					
					if(Inicio.listaDocumentos[proximoNHC+1].nhc.contains("ERROR") || 
						Inicio.listaDocumentos[proximoNHC+1].nhc.contains("NO")){
							proximoNHC++;
					}
					else if(Inicio.listaDocumentos[proximoNHC].nhc.contains("Separador")){
						break;
					}
					else{
						if(Inicio.listaDocumentos[Inicio.numeroPdf-1].nhc.
								equals(Inicio.listaDocumentos[proximoNHC +1 ].nhc)){
							encontrado = true;
							proximoNHC++;
						}
						else{
							break;
						}
					}
				}
				
				if(encontrado){
					for(int z = Inicio.numeroPdf; z < proximoNHC;z++){
						Inicio.listaDocumentos[z].nhc = Inicio.listaDocumentos[proximoNHC].nhc;
						Inicio.listaDocumentos[z].semaforoAmarilloNhc = true;
					}
				}
			
			}
		}
	}
	
	public void jBGrabarPagina() {
		new Acrobat().guardarPagina();

		revisarPropiedadesDocumento();

		System.out.println("Numero de pdf " + Inicio.numeroPdf
				+ ". Tamaño Carpeta: " + Inicio.tamañoCarpetaPdf);
		if (Inicio.numeroPdf < Inicio.tamañoCarpetaPdf - 1) {

			// File archivo = new
			// File(Inicio.rutaCompletaPdfs[++Inicio.numeroPdf]);
			
			//Inicio.ventanaExplorador.listaPdfs.setSelectedIndex(Inicio.numeroPdf);

			File archivo = new File(
					Inicio.listaDocumentos[++Inicio.numeroPdf].rutaArchivo);
			
			
			detectaNHCsecuencial();
			
			if(Inicio.menuVertical){
				Inicio.ventanaIntegral.listaPdfs.setSelectedIndex(Inicio.numeroPdf);
			}
			else{
				Inicio.ventanaExplorador.listaPdfs.setSelectedIndex(Inicio.numeroPdf);
			}

			try {
				// Process p = Runtime.getRuntime().exec(
				//		"rundll32 url.dll,FileProtocolHandler " + archivo);

				Desktop.getDesktop().open(archivo);
				
				Inicio.jBNHC
						.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nhc);
				if (Inicio.listaDocumentos[Inicio.numeroPdf].nhc.equals("Separador")
						|| Inicio.listaDocumentos[Inicio.numeroPdf].nhc.contains("ERROR")
						|| Inicio.listaDocumentos[Inicio.numeroPdf].nhc
								.equals("NO")) {
					Inicio.jBNHC.setBackground(Color.red);
					Inicio.jBNHCp.setBackground(Color.red);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBNHCm.setBackground(Color.red);
					}
					*/
				} else if (Inicio.listaDocumentos[Inicio.numeroPdf].nhc
						.equals("Eliminar")
						|| Inicio.listaDocumentos[Inicio.numeroPdf].nhc
								.equals("Apartar")) {
					Inicio.jBNHC.setBackground(Color.GRAY);
					Inicio.jBNHCp.setBackground(Color.GRAY);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBNHCm.setBackground(Color.GRAY);
					}
					*/
				} else if(Inicio.listaDocumentos[Inicio.numeroPdf].semaforoAmarilloNhc){
					Inicio.jBNHC.setBackground(Color.yellow);
					Inicio.jBNHCp.setBackground(Color.yellow);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBNHCm.setBackground(Color.yellow);
					}
					*/
				}
				else{
					Inicio.jBNHC.setBackground(Color.green);
					Inicio.jBNHCp.setBackground(Color.green);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBNHCm.setBackground(Color.green);
					}
					*/
				}
				
				if (Inicio.listaDocumentos[Inicio.numeroPdf].nhc
						.equals("Separador")
						&& !Inicio.listaDocumentos[Inicio.numeroPdf].servicio
								.equals("X")) {
					Inicio.jBNHC.setBackground(Color.green);
					Inicio.jBNHCp.setBackground(Color.green);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBNHCm.setBackground(Color.green);
					}
					*/
				}

				Inicio.jBServicio
						.setText(Inicio.listaDocumentos[Inicio.numeroPdf].servicio);
				if (Inicio.listaDocumentos[Inicio.numeroPdf].servicio
						.equals("X")) {
					Inicio.jBServicio.setBackground(Color.red);
					Inicio.jBServiciop.setBackground(Color.red);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBServiciom.setBackground(Color.red);
					}
					*/
				} else if (Inicio.listaDocumentos[Inicio.numeroPdf].servicio
						.equals("Eliminar")
						|| Inicio.listaDocumentos[Inicio.numeroPdf].servicio
								.equals("Apartar")) {
					Inicio.jBServicio.setBackground(Color.GRAY);
					Inicio.jBServiciop.setBackground(Color.GRAY);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBServiciom.setBackground(Color.gray);
					}
					*/
				} else {
					Inicio.jBServicio.setBackground(Color.green);
					Inicio.jBServiciop.setBackground(Color.green);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBServiciom.setBackground(Color.green);
					}
					*/
				}

				Inicio.jBNombreDoc
						.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado);
				if (Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado
						.equals("X")) {
					Inicio.jBNombreDoc.setBackground(Color.red);
					Inicio.jBNombreDocp.setBackground(Color.red);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBNombreDocm.setBackground(Color.red);
					}
					*/
				} else if (Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado
						.equals("Eliminar")
						|| Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado
								.equals("Apartar")) {
					Inicio.jBNombreDoc.setBackground(Color.GRAY);
					Inicio.jBNombreDocp.setBackground(Color.GRAY);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBNombreDocm.setBackground(Color.gray);
					}
					*/
				} else {
					Inicio.jBNombreDoc.setBackground(Color.green);
					Inicio.jBNombreDocp.setBackground(Color.green);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBNombreDocm.setBackground(Color.green);
					}
					*/
				}

				Inicio.jBNHCp
						.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nhc);
				Inicio.jBServiciop
						.setText(Inicio.listaDocumentos[Inicio.numeroPdf].servicio);
				Inicio.jBNombreDocp
						.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado);
				/*
				if(Inicio.menuVertical){
					Inicio.ventanaMicro.jBNHCm.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nhc);
					Inicio.ventanaMicro.jBServiciom.setText(Inicio.listaDocumentos[Inicio.numeroPdf].servicio);
					Inicio.ventanaMicro.jBNombreDocm.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado);
				}
				*/
				if (Inicio.listaDocumentos[Inicio.numeroPdf].semaforoAmarilloServicio == true) {
					Inicio.jBServicio.setBackground(Color.yellow);
					Inicio.jBServiciop.setBackground(Color.yellow);
					/*
					if(Inicio.menuVertical){
						Inicio.ventanaMicro.jBServiciom.setBackground(Color.gray);
					}
					*/
				}

				//   Actualiza al servicio del documento
 			   
 			   Inicio.jLServicios.setSelectedValue(Inicio.jBServicio.getText(), true);
 			   Inicio.jLNombresDoc.setModel(Inicio.excel.getDocServicio(Inicio.jLServicios
 						.getSelectedValue().toString()));				
				
				
				if (Inicio.listaDocumentos[Inicio.numeroPdf].nhc.equals("NO")
						|| Inicio.listaDocumentos[Inicio.numeroPdf].nhc
								.contains("ERROR")) {
					/*
					try {
						Robot robot = new Robot();
						robot.delay(1300);
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					*/
					Inicio.ventanaIntroducirNHC = new InterfazIntroducirNHC(null,false, Inicio.jBNHCp);
					Inicio.ventanaIntroducirNHC.setVisible(true);

					// dialog.requestFocus();
					// jPanel1.requestFocus();
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			
			int registrar = 0;
			
			if(!Inicio.menuVertical){
				registrar = JOptionPane.showConfirmDialog(Inicio.ventanaPrincipal, "Carpeta revisada. ¿Quieres registrar?");
			}
			else{
				registrar = JOptionPane.showConfirmDialog(Inicio.ventanaIntegral, "Carpeta revisada. ¿Quieres registrar?");
			}
			if(JOptionPane.OK_OPTION == registrar){
				Inicio.utiles.registraCambiosFinales();
			}
		}
	}
}
