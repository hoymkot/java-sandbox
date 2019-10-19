package remote;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

public class RemoteScreenThread extends Thread {

	DataOutputStream out = null;
	boolean flag = true;

	Robot robot;

	public RemoteScreenThread(Socket s) {
		try {
			out = new DataOutputStream(s.getOutputStream());
			robot = new Robot();
		} catch (Exception e) {
		}
	}

	public void run() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rec = new Rectangle(0, 0, (int) size.getWidth(), (int) size.getHeight());
		while (true) {
			try {
				BufferedImage image = robot.createScreenCapture(rec);
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ImageIO.write(image, "jpeg", bout);
				byte[] b = bout.toByteArray();
				out.writeInt(b.length);
				out.write(b);
				out.flush();
				Thread.sleep(125);
			} catch (Exception e) {
			}

		}

	}

}
