
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class VentanaProgreso extends javax.swing.JFrame {

    /**
     * Creates new form VentanaProgreso
     */
	
	//	visualizacion:  0 ventana horizontal, 1 ventana vertical, 2 ventana A3
	
    public VentanaProgreso(CargaListaPdfs pdfs, int visualizacion) {
        panel = new javax.swing.JPanel();
        labelNHC = new javax.swing.JLabel();
        progresoNHCs = new javax.swing.JProgressBar();
        labelServicio = new javax.swing.JLabel();
        progresoServicios = new javax.swing.JProgressBar();
        labelDocumento = new javax.swing.JLabel();
        progresoNombres = new javax.swing.JProgressBar();
        
        labelRenombrando = new javax.swing.JLabel();
        progresoRenombrar = new javax.swing.JProgressBar();
        
        textoPdfExaminado = new javax.swing.JTextField();
        textoPdfExaminado.setFont(new Font("TimesRoman", Font.BOLD, 20));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 320));
        setResizable(false);
        setLocationRelativeTo(null);

        panel.setBackground(new java.awt.Color(255, 255, 153));

        labelNHC.setText("NHCs");

        labelServicio.setText("Nombres Normalizados");

        labelDocumento.setText("Servicios");
        
        labelRenombrando.setText("Renombrando");

        textoPdfExaminado.setText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textoPdfExaminado)
                    .addComponent(progresoNHCs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                    .addComponent(progresoNombres, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progresoServicios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progresoRenombrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNHC)
                            .addComponent(labelDocumento)
                            .addComponent(labelServicio)
                            .addComponent(labelRenombrando))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNHC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progresoNHCs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelDocumento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progresoNombres , javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelServicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progresoServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelRenombrando)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progresoRenombrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20,20,Short.MAX_VALUE)
                .addComponent(textoPdfExaminado, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        
        setVisible(true);
        
        
		Worker worker = new Worker(this,pdfs, visualizacion,progresoNHCs, progresoServicios,progresoNombres, progresoRenombrar, textoPdfExaminado);
		worker.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          



        
                      

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
  
    	new VentanaProgreso(null,0);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel labelNHC;
    private javax.swing.JLabel labelServicio;
    private javax.swing.JLabel labelDocumento;
    private javax.swing.JLabel labelRenombrando;
    private javax.swing.JPanel panel;
    private javax.swing.JProgressBar progresoNHCs;
    private javax.swing.JProgressBar progresoServicios;
    private javax.swing.JProgressBar progresoNombres;
    private javax.swing.JProgressBar progresoRenombrar; 
    private javax.swing.JTextField textoPdfExaminado;
    // End of variables declaration                   
}



