package remote;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScreenTest {
	public static void main(String[] args) throws Exception {
		JFrame jf = new JFrame("small screen show stage");
		
		jf.setSize(200,300);
		JLabel imag_lab = new JLabel();
		jf.add(imag_lab);
		jf.setVisible(true);
		jf.setAlwaysOnTop(true);
		Graphics g = jf.getGraphics();
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		
		Dimension dm = tk.getScreenSize();
		Robot robot = new Robot();
		
		
		for (int i = 0; i<1000; i++) {
			Rectangle rec = new Rectangle(0,0, (int) dm.getWidth(), (int) dm.getHeight());
			BufferedImage bimage = robot.createScreenCapture(rec);
			BufferedImage littleImage = resize(bimage, jf.getWidth(),jf.getHeight());
			FileOutputStream fous = new FileOutputStream(i+"_screen.jpeg") ;
			ImageIO.write(littleImage, "jpeg", fous);
			fous.flush();
			fous.close();
			imag_lab.setIcon(new ImageIcon(littleImage));
			Thread.sleep(50);
		}
	}

	private static BufferedImage resize(BufferedImage img, int width, int height) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(width, height, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, width, height, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}
	
	
}
