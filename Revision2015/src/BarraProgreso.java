import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;


public class BarraProgreso extends JDialog {

	/**
	 * @param args
	 */
	JProgressBar barraProgreso = new JProgressBar(0,100);
	
	BarraProgreso(CargaListaPdfs pdfs){
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		barraProgreso.setValue(0);
		barraProgreso.setStringPainted(true);
		setTitle("Progreso");
		getContentPane().add(barraProgreso);
		this.setLocationRelativeTo(null);

		
	//	Worker worker = new Worker(barraProgreso,pdfs);
	//	worker.execute();
		

		
	}
	
	public void iterate(){
		
		barraProgreso.setValue(10);
		/*
		for(int i=0;i<100;i++){
			barraProgreso.setValue(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}
	

}



	

	
