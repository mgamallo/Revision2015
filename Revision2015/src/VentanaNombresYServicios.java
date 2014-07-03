import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class VentanaNombresYServicios extends JFrame{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new VentanaNombresYServicios();
	}

	private JPanel panel;
	
	private JScrollPane jScrollServicio = new javax.swing.JScrollPane();
	private JScrollPane jScrollNombresDoc = new javax.swing.JScrollPane();
	private JScrollPane jScrollPane3 = new JScrollPane();
	private JScrollPane jScrollPane4 = new JScrollPane();
	private JList jListHabituales1 = new javax.swing.JList();
	private JList jListHabituales2 = new javax.swing.JList();
	
	

	VentanaNombresYServicios(){
		setSize(200, 500);
		setTitle("Nombres");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		DefaultListModel dLM = new DefaultListModel();
		dLM.removeAllElements();
		dLM = Inicio.excel.listaServiciosLista;
		dLM.addElement("Des");
		
		Inicio.jLServicios.setModel(dLM);
        Inicio.jLServicios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Inicio.jLServicios.setSelectedIndex(0);
        jScrollServicio.setViewportView(Inicio.jLServicios);
		
        Inicio.jLServicios.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				listaServiciosMouseClicked(evt);
			}
		});
		
        
		if(Inicio.documentacionDeUrgencias){
	        Inicio.jLNombresDoc.setModel(Inicio.excel.getDocServicio("URG"));
		}
		else{
	        Inicio.jLNombresDoc.setModel(Inicio.excel.getDocServicio(Inicio.jLServicios.getSelectedValue().toString()));
		}
        Inicio.jLNombresDoc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        // Inicio.jLNombresDoc.setBackground(new java.awt.Color(255, 255, 204));
        jScrollNombresDoc.setViewportView(Inicio.jLNombresDoc);
        
        Inicio.jLNombresDoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaNombresDocMouseClicked(evt);
            }
        });
        
        jListHabituales1.setBackground(new java.awt.Color(255, 241, 182));
        jListHabituales1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        if(Inicio.documentacionDeUrgencias){
        	jListHabituales1.setModel(Inicio.excel.listaHabitualesUrg);
        }else{
        	jListHabituales1.setModel(Inicio.excel.listaHabituales);
        }
        jListHabituales1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(jListHabituales1);
        
        jListHabituales1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	listaHabituales1DocMouseClicked(evt);
            }
        });
        
        jListHabituales2.setBackground(new java.awt.Color(255, 241, 182));
        //jListHabituales2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jListHabituales2.setModel(Inicio.excel.listaHabituales2);
        jListHabituales2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(jListHabituales2);
        
        jListHabituales2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	listaHabituales2DocMouseClicked(evt);
            }
        });
        
        
        
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		

		
		
		panel.add(jScrollServicio);
		panel.add(jScrollNombresDoc);
		panel.add(jScrollPane3);
		panel.add(jScrollPane4);

		
		setContentPane(panel);
	}
	
	protected void listaServiciosMouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		
		actualizaServicio();
	}
	
	protected void listaNombresDocMouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		
		Inicio.jBNombreDoc.setText(Inicio.jLNombresDoc.getSelectedValue().toString());
		Inicio.jBNombreDocp.setText(Inicio.jLNombresDoc.getSelectedValue().toString());
		Inicio.jBNombreDoc.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBNombreDocp.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBNombreDoc.setIcon(null);
		Inicio.jBNombreDocp.setIcon(null);
		jListHabituales1.clearSelection();
		jListHabituales2.clearSelection();
		
		new FocalAdobe(100);
	}
	
	protected void listaHabituales1DocMouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		Inicio.jBNombreDoc.setText(jListHabituales1.getSelectedValue()
				.toString());
		Inicio.jBNombreDocp.setText(jListHabituales1.getSelectedValue()
				.toString());
		Inicio.jBNombreDoc
				.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBNombreDocp
				.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBNombreDoc.setIcon(null);
		Inicio.jBNombreDocp.setIcon(null);
		Inicio.jLNombresDoc.clearSelection();
		jListHabituales2.clearSelection();
		
		new FocalAdobe(100);
	}
	
	protected void listaHabituales2DocMouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		
		Inicio.jBNombreDoc.setText(jListHabituales2.getSelectedValue().toString());
		Inicio.jBNombreDocp.setText(jListHabituales2.getSelectedValue().toString());
		Inicio.jBNombreDoc.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBNombreDocp.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBNombreDoc.setIcon(null);
		Inicio.jBNombreDocp.setIcon(null);
		jListHabituales1.clearSelection();
		Inicio.jLNombresDoc.clearSelection();
		
		new FocalAdobe(100);
	}
	
	private void actualizaServicio(){
    	Inicio.jBServicio.setText(Inicio.jLServicios.getSelectedValue().toString());
		Inicio.jBServiciop.setText(Inicio.jLServicios.getSelectedValue().toString());
		Inicio.jLNombresDoc.setModel(Inicio.excel.getDocServicio(Inicio.jBServicio.getText()));
		
		Inicio.jBServicio.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBServiciop.setBackground(new java.awt.Color(153, 255, 153));
		Inicio.jBServicio.setIcon(null);
		Inicio.jBServiciop.setIcon(null);
		
				
		if(Inicio.jBNHC.getText().equals("Separador")){
			Inicio.jBNHC.setBackground(new java.awt.Color(153, 255, 153));
			Inicio.jBNHCp.setBackground(new java.awt.Color(153, 255, 153));
			

			
			//     renombraServicios();
		}
		else{
			if(Inicio.jBServicio.getText().equals("CIA")){
				if(Inicio.listaDocumentos[Inicio.numeroPdf].fisica.numPaginas > 2){
					Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado = "Quirófano";
					Inicio.jBNombreDoc.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado);
					Inicio.jBNombreDocp.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado);
				}
			}
			if(Inicio.jBServicio.getText().equals("HOSP")){
				if(Inicio.listaDocumentos[Inicio.numeroPdf].fisica.numPaginas > 2){
					Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado = "Hospitalización";
					Inicio.jBNombreDoc.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado);
					Inicio.jBNombreDocp.setText(Inicio.listaDocumentos[Inicio.numeroPdf].nombreNormalizado);
				}
			}
		}
	
		
		
		new FocalAdobe(100);
	}
}
