import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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


public class InterfazVisor extends JDialog{

	/**
	 * @param args
	 */

	JComboBox comboServicios = new JComboBox();
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
/*	
	Object[][] objetosModelo =new Object[][]{ 	{"tn_IMG_01.JPG","tn_IMG_02.JPG","tn_IMG_03.JPG"},
												{"tn_IMG_04.JPG","tn_IMG_05.JPG","tn_IMG_06.JPG"},
												{"tn_IMG_07.JPG","tn_IMG_08.JPG","tn_IMG_09.JPG"},
												{"tn_IMG_10.JPG","tn_IMG_11.JPG","tn_IMG_12.JPG"},
											  };
*/	
	Object[][] imagenes;
	
	InterfazVisor(){
		setTitle("Visor de Documentos");
		setModal(true);

	    comboServicios.setBackground(new java.awt.Color(255, 204, 204));
	    comboServicios.setMaximumRowCount(15);
	    comboServicios.setModel(this.listaServicios());

	    comboServicios.setSelectedIndex(0);
	    comboServicios.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	String[] docServicios;
	            	docServicios = Inicio.excel.getDocServicioVisor(comboServicios.getSelectedItem().toString());
   	
	            	//	Obtiene el nombre de los Documentos
	            	nombres = this.getDocumentosJpg();

	            	
	            	//	Obtiene la ruta de las imagenes
	            	rutaJpgs = this.getRutaJpg();
	            	int tamArray = rutaJpgs.size();
	            	
	            	//	Obtiene las observaciones de cada documento
	            	observaciones = this.getObservaciones();

	            	
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
				//	modelo.addRow(objetosModelo[2]);
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

	        	//	Método para cargar la lista de documentos del servicio
				private ArrayList<String> getDocumentosJpg() {
					ArrayList<String> listaDocumentos = new ArrayList<String>();
					String[] cadena = new String[2];
					int numFilas = Inicio.excel.tablaVisor.length;
					for(int i=1;i<numFilas;i++){
						if(Inicio.excel.tablaVisor[i][2].toString().contains(comboServicios.getSelectedItem().toString())){
							listaDocumentos.add(Inicio.excel.tablaVisor[i][1].toString());
						}
					}
					
					return listaDocumentos;
				}
				
	        	
				//	Método para cargar la lista de rutas de los jpg del servicio
				private ArrayList<String> getRutaJpg() {
					ArrayList<String> listaRuta = new ArrayList<String>();
					String[] cadena = new String[2];
					int numFilas = Inicio.excel.tablaVisor.length;
					for(int i=1;i<numFilas;i++){
						if(Inicio.excel.tablaVisor[i][2].toString().contains(comboServicios.getSelectedItem().toString())){
							listaRuta.add(Inicio.excel.tablaVisor[i][0].toString() + ".jpg");
						}
					}
					
					return listaRuta;
				}
				
				//	Método para cargar la lista de observaciones de los documentos
				private ArrayList<String> getObservaciones() {
					ArrayList<String> listaObs = new ArrayList<String>();
					String[] cadena = new String[2];
					int numFilas = Inicio.excel.tablaVisor.length;
					for(int i=1;i<numFilas;i++){
						if(Inicio.excel.tablaVisor[i][2].toString().contains(comboServicios.getSelectedItem().toString())){
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
		panelSuperior.add(comboServicios,BorderLayout.WEST);
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
				//new VentanaModificarDocumentos("Modelo");
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
	
	
	//	Método para cargar la lista de servicios con documentos
	DefaultComboBoxModel listaServicios(){
		
		DefaultComboBoxModel servicios = new DefaultComboBoxModel();
		int numFilas = Inicio.excel.tablaVisor.length;
//				Inicio.excel.tablaVisor[j][1].toString().contains(docServicios[i])
		servicios.addElement("Selecciona Servicio");
		servicios.addElement(Inicio.excel.tablaVisor[1][2].toString());
		for(int i=2;i<numFilas;i++){
			if(!Inicio.excel.tablaVisor[i][2].toString().contains(Inicio.excel.tablaVisor[i-1][2].toString())){
				servicios.addElement(Inicio.excel.tablaVisor[i][2].toString());
			}
		}
		
		return servicios;
	}
	

	
/*	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfazVisor intento = new InterfazVisor();
	}
*/
}
