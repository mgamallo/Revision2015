import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;


public class FocalAdobe {

	FocalAdobe(int retardo){
		try {
			
			Robot robot = new Robot();
			robot.delay(retardo);
			robot.keyPress(KeyEvent.VK_F9);
			robot.keyRelease(KeyEvent.VK_F9);
			robot.delay(50);
			System.out.println("Foco en acrobat");
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
