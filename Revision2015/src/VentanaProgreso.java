
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;



public class VentanaProgreso extends JFrame{
	
	JLabel labelNHC = new JLabel("NHC");
	public JProgressBar progresoNHCs = new JProgressBar(0,100);
	JLabel labelServicio = new JLabel("Servicios");
	public JProgressBar progresoServicios = new JProgressBar(0,100);
	JLabel labelDocumento = new JLabel("Nombres");
	public JProgressBar progresoNombres = new JProgressBar(0, 100);
	
	JPanel panel = new JPanel();
	
	VentanaProgreso(CargaListaPdfs pdfs, int visualizacion){
		setSize(700,150);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(labelNHC);
		panel.add(progresoNHCs);
		panel.add(labelServicio);
		panel.add(progresoServicios);
		panel.add(labelDocumento);
		panel.add(progresoNombres);
		
		progresoNombres.setValue(50);
		
		getContentPane().add(panel);
		
		// pack();
		setVisible(true);
		
		Worker worker = new Worker(pdfs, visualizacion,progresoNHCs, progresoServicios,progresoNombres);
		worker.execute();
	}
	
	static public void main(String args[]){
		new VentanaProgreso(null,1);
	}
}
