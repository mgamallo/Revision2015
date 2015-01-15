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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class InterfazVisorMeta extends JDialog{

	/**
	 * @param args
	 */
	
	
	JComboBox comboMeta = new JComboBox();
	JComboBox comboColor = new JComboBox();
	JComboBox comboGrafico = new JComboBox();
	JTextField texto = new JTextField(15);
	JButton botonBuscar = new JButton("Buscar");
	JButton botonLimpiar = new JButton("Limpiar");
	
	JLabel obsvLabel = new JLabel("Observaciones:");
	JLabel contObsvLabel = new JLabel();
	
	
	JButton JBmodificar = new JButton("Modificar");
    
	MyTableModel modelo;
	
	ArrayList<String> nombres = new ArrayList<String>();
	ArrayList<String> rutaJpgs = new ArrayList<String>();
	ArrayList<String> observaciones = new ArrayList<String>();

	Object[] filaObjetos = new Object[]{"1","2","3"};

	Object[][] imagenes;
	
	InterfazVisorMeta(){
		setTitle("Visor de metaDatos");
		setModal(true);
			

	    comboMeta.setBackground(new java.awt.Color(255, 204, 204));
	    comboMeta.setMaximumRowCount(20);
	    comboMeta.setModel(this.listaMeta());

	    comboMeta.setSelectedIndex(0);
	    comboMeta.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	String[] docmetaDatos;
	            	docmetaDatos = Inicio.excel.getDocServicioVisor(comboMeta.getSelectedItem().toString());
	
	            	//	Obtiene el nombre de los Documentos
	            	nombres = this.getDocumentosJpg();
	            	int tamArray = nombres.size();

	            	//	Obtiene la ruta de las imagenes
	            	rutaJpgs = this.getRutaJpg();
	            	tamArray = rutaJpgs.size();
	            	
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
	            
	        	//	Método para cargar la lista de nombres de los documentos que contienen el metadato
	            private ArrayList<String> getDocumentosJpg() {
					ArrayList<String> listaNombreDocumentos = new ArrayList<String>();
					int numFilas = Inicio.excel.tablaVisor.length;
					for(int i=1;i<numFilas;i++){
						for(int j=6;j<12;j++){
							if(Inicio.excel.tablaVisor[i][j].toString().contains(comboMeta.getSelectedItem().toString())){
								listaNombreDocumentos.add(Inicio.excel.tablaVisor[i][1].toString());
							}
						}
					}
					
					return listaNombreDocumentos;
				}

		
	        	
				//	Método para cargar la lista de rutas de los jpg que tienen el metadato
				private ArrayList<String> getRutaJpg() {
					ArrayList<String> listaRuta = new ArrayList<String>();
					int numFilas = Inicio.excel.tablaVisor.length;

					for(int i=1;i<numFilas;i++){
						for(int j=6;j<12;j++){
							if(Inicio.excel.tablaVisor[i][j].toString().contains(comboMeta.getSelectedItem().toString())){
								listaRuta.add(Inicio.excel.tablaVisor[i][0].toString() + ".jpg");
							}
						}
					}
					
					return listaRuta;
				}
				
				//	Método para cargar la lista de observaciones de los documentos
				private ArrayList<String> getObservaciones() {
					ArrayList<String> listaObs = new ArrayList<String>();

					int numFilas = Inicio.excel.tablaVisor.length;
					for(int i=1;i<numFilas;i++){
						for(int j=6;j<12;j++){
							if(Inicio.excel.tablaVisor[i][j].toString().contains(comboMeta.getSelectedItem().toString())){
							listaObs.add(Inicio.excel.tablaVisor[i][5].toString());
							}
						}
					}
					
					return listaObs;
				}
				
	        });
		
	    
	//    comboColor.addItem("Color");
	    comboColor.setModel(this.listaColor());
	    comboColor.setMaximumRowCount(15);
	    comboColor.setSelectedIndex(0);
	    comboColor.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String[] docmetaDatos;
            	docmetaDatos = Inicio.excel.getDocServicioVisor(comboMeta.getSelectedItem().toString());

            	//	Obtiene el nombre de los Documentos
            	nombres = this.getDocumentosJpg();
            	int tamArray = nombres.size();

    	
            	//	Obtiene la ruta de las imagenes
            	rutaJpgs = this.getRutaJpg();
            	tamArray = rutaJpgs.size();
            	
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
            
        	//	Método para cargar la lista de nombres de los documentos que contienen el color
            private ArrayList<String> getDocumentosJpg() {
				ArrayList<String> listaNombreDocumentos = new ArrayList<String>();
				int numFilas = Inicio.excel.tablaVisor.length;
				for(int i=1;i<numFilas;i++){
						if(Inicio.excel.tablaVisor[i][3].toString().contains(comboColor.getSelectedItem().toString())){
							listaNombreDocumentos.add(Inicio.excel.tablaVisor[i][1].toString());
						}
				}
				return listaNombreDocumentos;
			}

	
        	
			//	Método para cargar la lista de rutas de los jpg que tienen el color
			private ArrayList<String> getRutaJpg() {
				ArrayList<String> listaRuta = new ArrayList<String>();
				int numFilas = Inicio.excel.tablaVisor.length;

				for(int i=1;i<numFilas;i++){
						if(Inicio.excel.tablaVisor[i][3].toString().contains(comboColor.getSelectedItem().toString())){
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
					if(Inicio.excel.tablaVisor[i][3].toString().contains(comboColor.getSelectedItem().toString())){
						listaObs.add(Inicio.excel.tablaVisor[i][5].toString());
						
					}
				}
				
				return listaObs;
			}
			
	    });
	    
	    comboGrafico.setModel(this.listaApariencia());
	    comboGrafico.setMaximumRowCount(10);
	    comboGrafico.setSelectedIndex(0);
	    comboGrafico.addActionListener(new ActionListener(){
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	String[] docmetaDatos;
	            	docmetaDatos = Inicio.excel.getDocServicioVisor(comboMeta.getSelectedItem().toString());

	            	//	Obtiene el nombre de los Documentos
	            	nombres = this.getDocumentosJpg();
	            	int tamArray = nombres.size();

	    	
	            	//	Obtiene la ruta de las imagenes
	            	rutaJpgs = this.getRutaJpg();
	            	tamArray = rutaJpgs.size();

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
	            
	        	//	Método para cargar la lista de nombres de los documentos que contienen Graficos o imagenes
	            private ArrayList<String> getDocumentosJpg() {
					ArrayList<String> listaNombreDocumentos = new ArrayList<String>();
					int numFilas = Inicio.excel.tablaVisor.length;
					for(int i=1;i<numFilas;i++){
							if(Inicio.excel.tablaVisor[i][4].toString().contains(comboGrafico.getSelectedItem().toString())){
								listaNombreDocumentos.add(Inicio.excel.tablaVisor[i][1].toString());
							}
					}
					return listaNombreDocumentos;
				}

		
	        	
				//	Método para cargar la lista de rutas de los jpg que tienen graficos o imagenes
				private ArrayList<String> getRutaJpg() {
					ArrayList<String> listaRuta = new ArrayList<String>();
					int numFilas = Inicio.excel.tablaVisor.length;

					for(int i=1;i<numFilas;i++){
							if(Inicio.excel.tablaVisor[i][4].toString().contains(comboGrafico.getSelectedItem().toString())){
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
						if(Inicio.excel.tablaVisor[i][4].toString().contains(comboGrafico.getSelectedItem().toString())){
							listaObs.add(Inicio.excel.tablaVisor[i][5].toString());
							
						}
					}
					
					return listaObs;
				}
	              	
	        });

	    
	    botonBuscar.addActionListener(new ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	buscar();
            }
              	
        });

	    botonLimpiar.addActionListener(new ActionListener(){
	    	public void actionPerformed(java.awt.event.ActionEvent evt) {
	    		
	    		texto.setText("");
	    		texto.grabFocus();	//	Transfiere el focus al jtextfield
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
	//	panelSuperior.setLayout();

		
		//	Si se presiona Enter se ejecuta el método buscar metadato
    	texto.addActionListener(new ActionListener(){
 			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				buscar();
			}
    	});
		
		panelSuperior.add(comboMeta);
		panelSuperior.add(comboGrafico);
		panelSuperior.add(comboColor);
		panelSuperior.add(texto);
		panelSuperior.add(botonBuscar);
		panelSuperior.add(botonLimpiar);
		
		texto.setFont(new Font("Serif",Font.BOLD,18));
		texto.setForeground(Color.blue);
		texto.setEditable(true);
		
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
		texto.requestFocus();
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
	

	//	Método para cargar la lista de graficos, fotos, cuadros...
	DefaultComboBoxModel listaApariencia(){
		DefaultComboBoxModel aparienciaDCBM = new DefaultComboBoxModel();
		int numFilas = Inicio.excel.tablaVisor.length;

		ArrayList<String> apariencias = new ArrayList();
		for(int i=1;i<numFilas;i++){
				apariencias.add(Inicio.excel.tablaVisor[i][4].toString());
		}

		HashSet<String> quitaDuplicados = new HashSet<String>();
		quitaDuplicados.addAll(apariencias);
		apariencias.clear();
		apariencias.addAll(quitaDuplicados);
		apariencias.remove("N");
		apariencias.remove("");
		Collections.sort(apariencias,String.CASE_INSENSITIVE_ORDER);
		
		numFilas = apariencias.size();
		for(int i=0;i<numFilas;i++)
			aparienciaDCBM.addElement(apariencias.get(i));
		aparienciaDCBM.insertElementAt("Apariencia", 0);
		return aparienciaDCBM;
	}
	
	
	//	Método para cargar la lista de colores
	DefaultComboBoxModel listaColor(){
		DefaultComboBoxModel colorDCBM = new DefaultComboBoxModel();
		int numFilas = Inicio.excel.tablaVisor.length;

		ArrayList<String> colores = new ArrayList();
		for(int i=1;i<numFilas;i++){
				colores.add(Inicio.excel.tablaVisor[i][3].toString());
		}

		HashSet<String> quitaDuplicados = new HashSet<String>();
		quitaDuplicados.addAll(colores);
		colores.clear();
		colores.addAll(quitaDuplicados);
		colores.remove("N");
		colores.remove("");
		Collections.sort(colores,String.CASE_INSENSITIVE_ORDER);


		numFilas = colores.size();
		for(int i=0;i<numFilas;i++)
			colorDCBM.addElement(colores.get(i));
		colorDCBM.insertElementAt("Color", 0);
		return colorDCBM;
	}
	
	
	
	//	Método para cargar la lista de documentos con metaDatos
	DefaultComboBoxModel listaMeta(){
		
		DefaultComboBoxModel metaDatosDCBM = new DefaultComboBoxModel();
		
		int numFilas = Inicio.excel.tablaVisor.length;

		ArrayList<String> metaDatos = new ArrayList();
		
		for(int i=1;i<numFilas;i++){
			for(int j=6;j<12;j++){
				metaDatos.add(Inicio.excel.tablaVisor[i][j].toString());
			}
		}
		
		HashSet<String> quitaDuplicados = new HashSet<String>();
		quitaDuplicados.addAll(metaDatos);
		metaDatos.clear();
		metaDatos.addAll(quitaDuplicados);
		metaDatos.remove("");
		Collections.sort(metaDatos,String.CASE_INSENSITIVE_ORDER);	
	
		numFilas = metaDatos.size();
		for(int i=0;i<numFilas;i++)
			metaDatosDCBM.addElement(metaDatos.get(i));
		metaDatosDCBM.insertElementAt("Selecciona palabra clave", 0);	
		
		return metaDatosDCBM;
	}
	
	void buscar(){
		String[] docmetaDatos;
    	docmetaDatos = Inicio.excel.getDocServicioVisor(comboMeta.getSelectedItem().toString());

    	String nombreABuscar = texto.getText();
    	
    	if(!nombreABuscar.isEmpty()){
        	
        	//	Obtiene el nombre de los Documentos
        	nombres = this.getDocumentosJpg(nombreABuscar);
        	int tamArray = nombres.size();
	
        	//	Obtiene la ruta de las imagenes
        	rutaJpgs = this.getRutaJpg(nombreABuscar);
        	
        	//	Obtiene las observaciones de cada documento
        	observaciones = this.getObservaciones(nombreABuscar);      
        	
        	tamArray = rutaJpgs.size();
        	
        	if(tamArray == 0){
        		JOptionPane.showMessageDialog(null, "No se ha encontrado ninguna coincidencia");
        	}
        	else{
        		

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
    	}
    }
    
	//	Método para cargar la lista de nombres de los documentos que contienen el metadato
    private ArrayList<String> getDocumentosJpg(String nombreABuscar) {
		ArrayList<String> listaNombreDocumentos = new ArrayList<String>();
		int numFilas = Inicio.excel.tablaVisor.length;
		for(int i=1;i<numFilas;i++){
			for(int j=6;j<12;j++){
				if(Inicio.excel.tablaVisor[i][j].toString().toLowerCase().contains(nombreABuscar.toLowerCase())){
					listaNombreDocumentos.add(Inicio.excel.tablaVisor[i][1].toString());
				}
			}
		}
		
		return listaNombreDocumentos;
	}


	
	//	Método para cargar la lista de rutas de los jpg que tienen el metadato
	private ArrayList<String> getRutaJpg(String nombreABuscar) {
		ArrayList<String> listaRuta = new ArrayList<String>();
		int numFilas = Inicio.excel.tablaVisor.length;

		for(int i=1;i<numFilas;i++){
			for(int j=6;j<12;j++){
				if(Inicio.excel.tablaVisor[i][j].toString().toLowerCase().contains(nombreABuscar.toLowerCase())){
					listaRuta.add(Inicio.excel.tablaVisor[i][0].toString() + ".jpg");
				}
			}
		}
		
		return listaRuta;
	}
	
	//	Método para cargar la lista de observaciones de los documentos
	private ArrayList<String> getObservaciones(String nombreABuscar) {
		ArrayList<String> listaObs = new ArrayList<String>();
		int numFilas = Inicio.excel.tablaVisor.length;
		for(int i=1;i<numFilas;i++){
			for(int j=6;j<12;j++){
				if(Inicio.excel.tablaVisor[i][j].toString().toLowerCase().contains(nombreABuscar.toLowerCase())){
				listaObs.add(Inicio.excel.tablaVisor[i][5].toString());
				}
			}
		}
		
		return listaObs;
	}
	
}
