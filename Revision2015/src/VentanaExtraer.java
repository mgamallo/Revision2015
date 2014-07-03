import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class VentanaExtraer extends JFrame{
	
	int indice = 1;
	final JTextField campoNHC = new JTextField(10);
	
	public VentanaExtraer(){
		initComponents();
		setVisible(true);
	}
	
	public void copiarAlPortapapeles(String documento){
	    Clipboard portapapeles=Toolkit.getDefaultToolkit().getSystemClipboard();
	    StringSelection texto=new StringSelection(documento);
	    portapapeles.setContents(texto, texto);
	 }   
	
	private void initComponents(){
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel jPanel1 = new javax.swing.JPanel();
		JPanel jPanelC = new JPanel();
		JPanel jPanelS = new JPanel();
		
		setTitle("Extraer");setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(750, 100));
        setResizable(false);
		
        
        final JComboBox comboServicios = new JComboBox();
        final JComboBox comboNombres = new JComboBox();
        
        final JTextField campoRuta = new JTextField(40);
        JButton jbGenerar = new JButton("Generar nombre");
        JButton jbCerrar = new JButton("Cerrar");
        
        JLabel espacio1 = new JLabel("       ");
        JLabel espacio2 = new JLabel("       ");
        
        comboServicios.setMaximumRowCount(5);
        comboServicios.setToolTipText("Selecciona el Servicio");
        comboServicios.setSelectedItem(3);
                
        comboNombres.setMaximumRowCount(20);
        comboNombres.setToolTipText("Selecciona el el nombre del documento");
       // jbCerrar.setToolTipText("Copia la ruta del archivo en el portapapeles");
        
        for(int i=0;i<Inicio.excel.listaServicios.length;i++){
            comboServicios.addItem(Inicio.excel.listaServicios[i]);
        }
        
        
        for(int i=0;i<Inicio.excel.listaDocumentos.length;i++){
            comboNombres.addItem(Inicio.excel.listaDocumentos[i]);
        }
        
        campoRuta.setText(Inicio.listaDocumentos[Inicio.numeroPdf].rutaArchivo);
        
        comboServicios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        comboNombres.addActionListener(new ActionListener() {
			
  			@Override
  			public void actionPerformed(ActionEvent arg0) {
  				// TODO Auto-generated method stub
  				
  			}
  		});
        
        
        jbGenerar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String cadenaOriginal = Inicio.listaDocumentos[Inicio.numeroPdf].rutaArchivo;
				int aux = cadenaOriginal.indexOf("@");
				cadenaOriginal = cadenaOriginal.substring(0,aux);
				cadenaOriginal += (" " + indice + " @" + campoNHC.getText() + " @" + comboServicios.getSelectedItem().toString() 
						+ " @" + comboNombres.getSelectedItem().toString() + " r"); 
				indice++;
				campoRuta.setText(cadenaOriginal);
				copiarAlPortapapeles(cadenaOriginal);
			}
		});
        
        jbCerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Inicio.utiles.habilitarTeclas(Inicio.jBDeshabilitar.getText());
				Inicio.ventanaA3.habilitarTeclas(Inicio.ventanaA3.jBDeshabilitar.getText());
				Inicio.ventanaExtraer.dispose();
			}
		});
        
        jPanel1.setLayout(new BorderLayout());
        
        jPanelC.setLayout(new FlowLayout());
        jPanelS.setLayout(new FlowLayout());
        
        jPanelC.add(campoNHC);
        jPanelC.add(espacio1);
        jPanelC.add(comboServicios);
        jPanelC.add(espacio2);
        jPanelC.add(comboNombres);
        
        jPanel1.add(jPanelC,BorderLayout.CENTER);
        
        jPanelS.add(campoRuta);
        jPanelS.add(jbGenerar);
        jPanelS.add(jbCerrar);
        
        jPanel1.add(jPanelS,BorderLayout.SOUTH);
        
        this.getContentPane().add(jPanel1);
        
        jPanelS.setBackground(new java.awt.Color(255,241,182));
        jPanelC.setBackground(Color.pink);
        
        Rectangle rectangulo = Inicio.ventanaCompacta.getBounds();
        setLocation(rectangulo.x,rectangulo.y + rectangulo.height);
        
        pack();
	}
}
