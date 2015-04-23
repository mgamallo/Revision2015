

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseListener;

public class CapturaRatonYTeclado implements NativeKeyListener,
		NativeMouseInputListener {

	public boolean ventanaFechaOn_borrar = false;

	
	public CapturaRatonYTeclado() {
		// TODO Auto-generated constructor stub

		 GlobalScreen.getInstance().addNativeKeyListener(this);
         GlobalScreen.getInstance().addNativeMouseListener(this);
         GlobalScreen.getInstance().addNativeMouseMotionListener(this);
         
         System.out.println("Hola captura");
         
         try {
			GlobalScreen.registerNativeHook();
			
			System.out.println("Capturando");
			
		} catch (NativeHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * try { GlobalScreen.registerNativeHook(); } catch (NativeHookException
		 * e) { // TODO Auto-generated catch block
		 * System.err.println("There was a problem registering the native hook."
		 * ); System.err.println(e.getMessage()); //e.printStackTrace(); }
		 */
	}

	// Métodos de ratón
	// **********************************************************

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == 3){
			
			//  Boton central
		}
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mousepressed");
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mousereleased");
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mousedragged");
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mousemoved");
	}

	// Métodos de teclado
	// *************************************************************

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		 System.out.println("NativeKeyPressed " + e.getKeyCode());
		 System.out.println("Tecla ... " + ((char) e.getKeyCode()));
		
		if(Inicio.ventanaIntroducirNHC != null){
			 if(Inicio.ventanaIntroducirNHC.isVisible()){
				 
				 int codigo = e.getKeyCode();
				 
				 /*  0 ... 48  ...  96
				  	 1 ... 49  ...  97
				  	 9 ... 57  ... 105
				  */
				 
				 String cadena = Inicio.ventanaIntroducirNHC.jTFnhc.getText();
				 
				 if(	(codigo >= 48 && codigo <= 57) ||
						(codigo >= 96 && codigo <= 105)   ){
					 System.out.println("Es un número");
					 
					 if(codigo >= 96){
						 codigo = codigo - 48;
					 }
					 
					 
					 cadena += ((char) codigo);
					 System.out.println(cadena);
					 Inicio.ventanaIntroducirNHC.jTFnhc.setText(cadena);
					// Inicio.ventanaIntroducirNHC.jTFnhc.requestFocusInWindow();
				 }
				 
				 if( (codigo == 8)){   // Borrar en retroceso
					 int tamaño = cadena.length();
					 cadena = cadena.substring(0,tamaño-1);
					 Inicio.ventanaIntroducirNHC.jTFnhc.setText(cadena);
				 }
				 
				 if( (codigo == 10)){  // Enter
					 Inicio.ventanaIntroducirNHC.aceptar();
				 }
			 }
		}
		
		
		if(ventanaFechaOn_borrar){
			 int codigo = e.getKeyCode();
			 
			 /*  0 ... 48  ...  96
			  	 1 ... 49  ...  97
			  	 9 ... 57  ... 105
			  */
			 
			 String fechaInicial = Inicio.ventanaFechas.jLfechaRegistrada.getText();
			 String cadena = Inicio.ventanaFechas.jTextField1.getText();
			 
			 if(	(codigo >= 48 && codigo <= 57) ||
					(codigo >= 96 && codigo <= 105)   ){
				 System.out.println("Es un número");
				 
				 if(codigo >= 96){
					 codigo = codigo - 48;
				 }
				 
				 
				 cadena += ((char) codigo);
				 System.out.println(cadena);
				 Inicio.ventanaFechas.jTextField1.setText(cadena);
				 Fechas fechas = new Fechas();
				 
				 String fech = fechas.adivinaFecha(cadena);
				 if(fech != null){
					 fechaInicial = fech;
				 }
				 
				 Inicio.ventanaFechas.jLfechaRegistrada.setText(fechaInicial);
				// Inicio.ventanaIntroducirNHC.jTFnhc.requestFocusInWindow();
			 }
			 
			 if( (codigo == 8)){   // Borrar en retroceso
				 int tamaño = cadena.length();
				 cadena = cadena.substring(0,tamaño-1);
				 Inicio.ventanaFechas.jTextField1.setText(cadena);
				 Fechas fechas = new Fechas();
				 
				 String fech = fechas.adivinaFecha(cadena);
				 if(fech != null){
					 fechaInicial = fech;
				 }
				 Inicio.ventanaFechas.jLfechaRegistrada.setText(fechaInicial);
			 }
			 
			 if( (codigo == 10)){  // Enter
				
			 }
		}

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
	//	 System.out.println("NativeKeyReleased " + arg0.getKeyCode());
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
	//	 System.out.println("NativeKeyTyped " + arg0.getKeyCode());
	}

	
	
	static public void main(String arg[]){
		CapturaRatonYTeclado capt = new CapturaRatonYTeclado();
		/*
		 GlobalScreen.getInstance().addNativeKeyListener(capt);
         GlobalScreen.getInstance().addNativeMouseListener(capt);
         GlobalScreen.getInstance().addNativeMouseMotionListener(capt);
         
         System.out.println("Hola");
         
         try {
			GlobalScreen.registerNativeHook();
			
			System.out.println("Capturando");
			
		} catch (NativeHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
