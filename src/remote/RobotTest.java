package remote;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.*;

public class RobotTest {

	
	public static void main(String[] args) throws Exception {
		Robot robot = new Robot();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dm = tk.getScreenSize();
		
		int x = (int) dm.getWidth()/2;
		int y = (int) dm.getHeight()/2;
		
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_SHIFT);
		for (int i =0 ;i<10 ;i++) {
			robot.keyPress('A'+i);
			robot.keyRelease('A'+i);
			Thread.sleep(500);
		}
				
		robot.keyRelease(KeyEvent.VK_SHIFT);
		for(int i =0 ; i <11; i++) {
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
			Thread.sleep(500);
		}
		
		
	}
}
