import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class InterfazVisorNomDoc extends JDialog{

	/**
	 * @param args
	 */
	
	JComboBox comboDocumentos = new JComboBox();
	JTextField texto = new JTextField(30);
	JLabel etiqueta = new JLabel("   Nombre del Documento   ");
    
	JLabel obsvLabel = new JLabel("Observaciones:");
	JLabel contObsvLabel = new JLabel();
	
	
	JButton JBmodificar = new JButton("Modificar");
	
	MyTableModel modelo;
	
	ArrayList<String> nombres = new ArrayList<String>();
	ArrayList<String> rutaJpgs = new ArrayList<String>();
	ArrayList<String> observaciones = new ArrayList<String>();

	Object[] filaObjetos = new Object[]{"1","2","3"};

	Object[][] imagenes;
	
	InterfazVisorNomDoc(){
		setTitle("Visor de Documentos");
		setModal(true);

	    comboDocumentos.setBackground(new java.awt.Color(255, 204, 204));
	    comboDocumentos.setMaximumRowCount(15);
	    comboDocumentos.setModel(this.listaDocumentos());

	    comboDocumentos.setSelectedIndex(0);
	    comboDocumentos.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	String[] documentos;
	            	documentos = Inicio.excel.getDocServicioVisor(comboDocumentos.getSelectedItem().toString());
   	

	            	//	Obtiene la ruta de las imagenes
	            	rutaJpgs = this.getRutaJpg();
	            	
	            	//	Obtiene las observaciones de cada documento
	            	observaciones = this.getObservaciones();
	            	
	            	int tamArray = rutaJpgs.size();
	            	String nombreDoc = comboDocumentos.getSelectedItem().toString();
	            	texto.setText(nombreDoc);
	            	
	            	nombres.clear();
	            	for(int i=0;i<tamArray;i++){
	            		nombres.add(nombreDoc);
	            	}
          	
	            	int filas=0;
	            	int numFotos = tamArray;
	            	if(numFotos % 3 == 0){
	            		filas = numFotos/3;
	            	}else{
	            		filas = numFotos/3 +1;
	            	}
	            	
	            	Object[][] objetosM =new Object[filas][3];
	            	Object[][] imagenesR = new Object[filas][3];
	            	int fil=0;
	            	int columnas=0;
	            	int aux=0;
	            	
	            	for(int i=0;i<imagenesR.length;i++)
	            		for(int j=0;j<3;j++)
	            			imagenesR[i][j]="";
	            	
	            	while(aux < numFotos){
	            		if(columnas ==3){
	            			columnas = 0;
	            			fil++;
	            		}
	            		objetosM[fil][columnas]= rutaJpgs.get(aux);
	            		imagenesR[fil][columnas]= crearImagen(objetosM[fil][columnas].toString());
	            		aux++;
	            		columnas++;
	            	}
       	
	    			filas = modelo.getRowCount();
					for(int i=0;i<filas;i++){
						modelo.removeRow(0);
					}

					filas = imagenesR.length;
					aux=0;
					int conteo=0;
					Object[] v = new Object[3];
					for(int i=0;i<filas;i++){
						while(aux < 3 ){
							if(conteo<numFotos){
								v[aux] = new Object();
								v[aux] = imagenesR[i][aux];
								aux++;
								conteo++;
							}
							else{
								v[aux] = "";
								aux++;
							}
						}
						aux = 0;	
						modelo.addRow(v);	
					}
	            }
	            

	        	
				//	Método para cargar la lista de rutas de los jpg del documento
				private ArrayList<String> getRutaJpg() {
					ArrayList<String> listaRuta = new ArrayList<String>();
					int numFilas = Inicio.excel.tablaVisor.length;
					for(int i=1;i<numFilas;i++){
						if(Inicio.excel.tablaVisor[i][1].toString().equalsIgnoreCase(comboDocumentos.getSelectedItem().toString())){
							listaRuta.add(Inicio.excel.tablaVisor[i][0].toString() + ".jpg");
						}
					}
					
					return listaRuta;
				}
				
				//	Método para cargar la lista de observaciones de los documentos
				private ArrayList<String> getObservaciones() {
					ArrayList<String> listaObs = new ArrayList<String>();
					int numFilas = Inicio.excel.tablaVisor.length;
					for(int i=1;i<numFilas;i++){
						if(Inicio.excel.tablaVisor[i][1].toString().equalsIgnoreCase(comboDocumentos.getSelectedItem().toString())){
							listaObs.add(Inicio.excel.tablaVisor[i][5].toString());
						}
					}
					
					return listaObs;
				}
				
	        });
		
		
		modelo = new MyTableModel(imagenes,filaObjetos);

	
		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(380);		
		tabla.setTableHeader(null);
		tabla.setCellSelectionEnabled(true);
		tabla.setEnabled(false);
		tabla.setBackground(Color.black);
		
		JScrollPane scroll = new JScrollPane(tabla);
		
		EventoMouseClicked(tabla);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BorderLayout());

		panelSuperior.add(etiqueta,BorderLayout.EAST);
		panelSuperior.add(comboDocumentos,BorderLayout.WEST);
		panelSuperior.add(texto,BorderLayout.CENTER);
		
		texto.setFont(new Font("Serif",Font.BOLD,18));
		texto.setForeground(Color.blue);
		texto.setEditable(false);
		
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BorderLayout());
		
		JBmodificar.setEnabled(false);
		JBmodificar.setBackground(Color.green);
		panelInferior.add(obsvLabel,BorderLayout.WEST);
		panelInferior.add(contObsvLabel,BorderLayout.SOUTH);
		panelInferior.add(JBmodificar,BorderLayout.EAST);
		
		JBmodificar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// new VentanaModificarDocumentos("Modelo");
			}
			
		});
		
		obsvLabel.setForeground(Color.black);
		obsvLabel.setFont(new Font("TimesRoman",Font.BOLD,20));
		
	//	contObsvLabel.setText(observaciones.get(fotoVisible));
		contObsvLabel.setForeground(Color.red);
		contObsvLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contObsvLabel.setFont(new Font("TimesRoman",Font.PLAIN,25));
		obsvLabel.setFont(new Font("TimesRoman",Font.PLAIN,20));

		panel.add(panelSuperior,BorderLayout.NORTH);
		panel.add(scroll,BorderLayout.CENTER);
		panel.add(panelInferior,BorderLayout.SOUTH);
		setContentPane(panel);

		setSize(800,850);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	//	pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void EventoMouseClicked(final JTable tabla){
		tabla.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mousePressed(MouseEvent e){
				
				int fil = tabla.rowAtPoint(e.getPoint());
				int column = tabla.columnAtPoint(e.getPoint());
				
				if(e.getClickCount() == 2){
					Visor v = new Visor(nombres,rutaJpgs,fil*3+column,observaciones);
				}
				else{
					texto.setText("     " + nombres.get(fil*3 + column));
					contObsvLabel.setText(observaciones.get(fil*3 + column));
				}
				Inicio.auxRutaImagen = rutaJpgs.get(fil*3 + column);
			}

		});
	}	
	
	
	public ImageIcon crearImagen(String ruta){
		BufferedImage miImagen;
		try{
			String rutaCompleta = "Imagenes\\250x350\\" + ruta;
			miImagen = ImageIO.read(new File(rutaCompleta));
			return new ImageIcon(miImagen);		
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}	
	
	
	class MyTableModel extends DefaultTableModel{
		public MyTableModel(Object[][] data, Object[] columnNames){
			super(data, columnNames);
		}
		
		 
		  @Override
		  public Class<?> getColumnClass(int columnIndex) {
		   Class<?> clazz = Object.class;
		   Object aux = getValueAt(0, columnIndex);
		   if (aux != null) {
		    clazz = aux.getClass();
		   }
		 
		   return clazz;
		  }
	}
	
	
	//	Método para cargar la lista de documentos con documentos
	DefaultComboBoxModel listaDocumentos(){
DefaultComboBoxModel nombresDocumentosDCBM = new DefaultComboBoxModel();
		
		int numFilas = Inicio.excel.tablaVisor.length;

		ArrayList<String> nomDocumentos = new ArrayList();
		
		for(int i=1;i<numFilas;i++){
				nomDocumentos.add(Inicio.excel.tablaVisor[i][1].toString());
		}
		
		HashSet<String> quitaDuplicados = new HashSet<String>();
		quitaDuplicados.addAll(nomDocumentos);
		nomDocumentos.clear();
		nomDocumentos.addAll(quitaDuplicados);
	//	metaDatos.remove("");
		Collections.sort(nomDocumentos,String.CASE_INSENSITIVE_ORDER);	
	
		numFilas = nomDocumentos.size();
		for(int i=0;i<numFilas;i++)
			nombresDocumentosDCBM.addElement(nomDocumentos.get(i));
		nombresDocumentosDCBM.insertElementAt("Selecciona Documento", 0);	
		
		return nombresDocumentosDCBM;
	}
	
}
