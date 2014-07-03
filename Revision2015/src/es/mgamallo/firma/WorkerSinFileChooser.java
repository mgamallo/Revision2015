package es.mgamallo.firma;

import java.io.File;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class WorkerSinFileChooser extends SwingWorker<Double, Integer>{

	static final String RUTA = "j:/digitalización/00 documentacion/02 Revisado"; 
	static final String RUTAB = "h:/digitalización/00 documentacion/02 Revisado";
	static final String RUTAURG ="j:/DIGITALIZACIÓN/01 INFORMES URG (Colectiva)"; 
	static final String RUTAURGB ="H:/DIGITALIZACIÓN/01 INFORMES URG (Colectiva)";	
	
	private final JLabel conteoCarpeta;
	private final JLabel conteoTotal;
	private final JProgressBar progresoCarpeta;
	private final JProgressBar progresoTotal;
	
	private String certificado = "";
	private String password = "";
	private boolean usuarioUrgencias;
	private VentanaDialogoSinFileChooser ventana;
	
	private String rutaDirectorio ="";
	
	private File carpetaActual;
	private Carpeta carpeta;
	
	public WorkerSinFileChooser(File carpetaActual, JLabel conteoCarpeta, JLabel conteoTotal, JProgressBar progresoCapeta, JProgressBar progresoTotal, String certificado, String password, boolean usuarioUrgencias){
		this.conteoCarpeta =conteoCarpeta;
		this.conteoTotal = conteoTotal;
		this.progresoCarpeta = progresoCapeta;
		this.progresoTotal = progresoTotal;
		this.certificado = certificado;
		this.password = password;
		this.usuarioUrgencias = usuarioUrgencias;
		//this.ventana = ventana;
		this.carpetaActual = carpetaActual;
	}
	
	@Override
	protected Double doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Estamos en doInBackground, en el hilo " + 
				Thread.currentThread().getName());

		
		carpeta = new Carpeta(carpetaActual);
		
		int numeroCarpetas = 1;
		int numeroPdfs = 0;
		int numeroPdfsAcumulados = 0;
		int numeroPdfsTotales = 0;
		for(int i=0;i < numeroCarpetas;i++){
			numeroPdfsTotales += carpeta.pdfs.length;
		}
		
		// ventana.setVisible(true);
		
		//JOptionPane.showMessageDialog(null, certificado);
		//JOptionPane.showMessageDialog(null, certificado);
		
		//publish(0,0,1,1,50,1);
		System.out.println("Numero de carpetas " + numeroCarpetas);
		System.out.println("Numero de pdfs totales " + numeroPdfsTotales);
		
		for(int i=0;i<numeroCarpetas;i++){
		
			System.out.println("I vale " + i);
			System.out.println("numeroCarpetas vale... " + numeroCarpetas);
			
			boolean pinCorrecto;
			numeroPdfs = carpeta.pdfs.length;
			
			System.out.println("Numero de pdfs " + numeroPdfs);
			
			for(int j=0;j < numeroPdfs;j++){
				
				System.out.println("J vale " + j);
				System.out.println("numeroPdfs vale... " + numeroPdfs);
				
				System.out.println("\t" + carpeta.pdfs[j].getName().toString());
				if(i == 0 && j == 0){
					JOptionPane.showMessageDialog(null, "Empieza la firma");
				}
				
				pinCorrecto = Firma.firmar(carpeta.pdfs[j].getAbsolutePath(), 
						renombrarFicheroFirmado(carpeta.pdfs[j]), certificado, password);

				
				if(!pinCorrecto){
					System.out.println("Fallo en la firma.");
    				JOptionPane.showMessageDialog(null, "Fallo en la firma. Se aborta la firma.");
    				System.exit(0);
				}
				numeroPdfsAcumulados++;
				System.out.println("Numero de pdfs acumulados... " + numeroPdfsAcumulados);
				
				int porcentajePdfs =  ((j + 1)*100)/numeroPdfs;
				int porcentajeCarpetas = (numeroPdfsAcumulados*100)/ numeroPdfsTotales; 
				
				// Thread.sleep(100);
				
				publish(porcentajePdfs, porcentajeCarpetas,j+1,i+1,numeroPdfs,numeroCarpetas);
			}
		}
		
		return 100.0;
	}
	
	protected void done(){
		JOptionPane.showMessageDialog(null, "Firma terminada");
		System.out.println(certificado);
		File f = new File(certificado);
		if(f.delete())
			System.out.println("Certificado borrado");
		if(borrarCarpetas()){
			System.out.println("Carpetas revisadas borradas");
		}
		else{
			JOptionPane.showMessageDialog(null, "Errores en el borrado de las carpetas de revisado");
		}
		 // System.exit(0);
	}
	
	protected void process(List<Integer> chunks){
		progresoCarpeta.setValue(chunks.get(0));
		progresoTotal.setValue(chunks.get(1));
		conteoCarpeta.setText("Pdf " + String.valueOf(chunks.get(2)) + " de " + String.valueOf(chunks.get(4)));
		conteoTotal.setText("Carpeta " + String.valueOf(chunks.get(3)) + " de " + String.valueOf(chunks.get(5)));
	}
	
	private String renombrarFicheroFirmado(File archivoOrigen){
		
		System.out.println(archivoOrigen.getAbsolutePath());
			
		String nombrePdf = archivoOrigen.getName();	
		System.out.println(nombrePdf);
		
		int ultimaR = nombrePdf.lastIndexOf(".");
		
		String nuevoNombrePdf = nombrePdf.substring(0,ultimaR) + "_firmado.pdf";
		System.out.println(nuevoNombrePdf);
		
		String nombreCarpetaPdf = archivoOrigen.getParentFile().getName();
		int quitarUsuario = nombreCarpetaPdf.lastIndexOf(" ");
		nombreCarpetaPdf = nombreCarpetaPdf.substring(0,quitarUsuario);
		System.out.println(nombreCarpetaPdf);
		
		String rutaCarpetaAbuela = archivoOrigen.getParentFile().getParentFile().getParentFile().getAbsolutePath();
		
		System.out.println(rutaCarpetaAbuela);
		
		String rutaFinalDirectorio = rutaCarpetaAbuela + "\\03 Firmado\\" + nombreCarpetaPdf + "\\" ;
		
		File crearCarpetaDestino = new File(rutaFinalDirectorio);
		crearCarpetaDestino.mkdirs();
		
		System.out.println(rutaFinalDirectorio + nuevoNombrePdf);
		
		String rutaFinalCompleta = rutaFinalDirectorio + nuevoNombrePdf;	
	
		return rutaFinalCompleta ;
		
	}
	
	private boolean borrarCarpetas(){
		
		boolean sinErrores = true;
		
		int numeroCarpetas = 1;
		int numeroPdfs = 0;
		for(int i=0;i<numeroCarpetas;i++){

			numeroPdfs = carpeta.pdfs.length;
			for(int j=0;j < numeroPdfs;j++){
				//System.out.println("\t" + fc.carpetas[i].pdfs[j].getName().toString());
				if(carpeta.pdfs[j].delete()){
					sinErrores = false;
					System.out.println("Error en " + carpeta.pdfs[j].getName().toString());
				}
			}
			
			if(carpeta.rutaCarpeta.delete()){
				//sinErrores = false;
				//System.out.println("Error en " + fc.carpetas[i].rutaCarpeta.getName().toString());
			}
			
		}
		
		return sinErrores;
		
	}
}
