package remote;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

public class RemoteRobotThread extends Thread {

	ObjectInputStream in = null;
	boolean flag = true;

	Robot robot ;
	private boolean isRun = true;

	public RemoteRobotThread(Socket s) {
		try {
			in = new ObjectInputStream(s.getInputStream());
			robot = new Robot();
		} catch (Exception e) {
		}
	}

	public void run() {
		try {
			while (isRun) {
				InputEvent input = (InputEvent) in.readObject();
//				System.out.println(count + "  " + input) ;

				if (input != null) {
					handleEvent(input);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int count = 0;
	private void handleEvent(InputEvent event) {
		MouseEvent mevent = null;
		MouseWheelEvent mwevent = null;
		KeyEvent kevent = null;
		int mousebuttonmask = -100;
		count++;
		System.out.println(count + "  " + event) ;
		switch (event.getID()) {
		case MouseEvent.MOUSE_MOVED:
			mevent = (MouseEvent) event;

			System.out.println("before mouse move ");
			robot.mouseMove((int) mevent.getX(), (int)mevent.getY());
			System.out.println("after mouse move ");
			break;
		case MouseEvent.MOUSE_PRESSED:
			mevent = (MouseEvent) event;
			mousebuttonmask = getMouseClick(mevent.getButton());
			robot.mousePress(mousebuttonmask);
			break;
		case MouseEvent.MOUSE_RELEASED:
			mevent = (MouseEvent) event;
			mousebuttonmask = getMouseClick(mevent.getButton());
			robot.mousePress(mousebuttonmask);
			break;
		case MouseEvent.MOUSE_WHEEL:
			mwevent = (MouseWheelEvent) event;
			robot.mouseWheel(mwevent.getWheelRotation());
			break;
		case MouseEvent.MOUSE_DRAGGED:
			mevent = (MouseEvent) event;
			robot.mouseMove((int) mevent.getX(), mevent.getY());
			break;
		case KeyEvent.KEY_PRESSED:
			kevent = (KeyEvent) event;
			robot.keyPress(kevent.getKeyCode());
			break;
		case KeyEvent.KEY_RELEASED:
			kevent = (KeyEvent) event;
			robot.keyRelease(kevent.getKeyCode());
			break;
		default:
			System.out.println("unknown event: " + event.getID());
		}

	}

	private int getMouseClick(int button) {
		if(button == MouseEvent.BUTTON1)
			return InputEvent.BUTTON1_MASK;
		if(button == MouseEvent.BUTTON2)
			return InputEvent.BUTTON2_MASK;
		if(button == MouseEvent.BUTTON3)
			return InputEvent.BUTTON3_MASK;
		return -1;
	}

}
