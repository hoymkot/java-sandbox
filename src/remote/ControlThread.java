package remote;

import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ControlThread extends JFrame implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener{

	private String ip;
	private int port;
	ObjectOutputStream out;
	DataInputStream dins;
	JLabel imageLab;
	
	
	public ControlThread(String remoteIP, int port) {
		this.ip = ip;
		this.port = port;
		this.init();
	
	}

	private void init() {
		imageLab = new JLabel();
		this.add(imageLab);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		this.setVisible(true);
		this.requestFocus();
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void run() {
		try {
		Socket socket = new Socket(this.ip, this.port);
			out = new ObjectOutputStream(socket.getOutputStream());
			dins = new DataInputStream(socket.getInputStream());
			while(true) {
				int imageLen = dins.readInt();
				byte[] iData = new byte[imageLen];
				dins.readFully(iData);
				ByteArrayInputStream bins = new ByteArrayInputStream(iData);
				BufferedImage image =ImageIO.read(bins);
				ImageIcon icon = new ImageIcon(image);
				imageLab.setIcon(icon);
				imageLab.repaint();
			}
		} catch (IOException e) {
			System.out.println("connect failer");
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		sendClientAction(e);		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		sendClientAction(e);		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		sendClientAction(e);		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		sendClientAction(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		sendClientAction(e);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		sendClientAction(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		sendClientAction(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		sendClientAction(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		sendClientAction(e);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		sendClientAction(e);
		
	}
	int count= 0;
	private void sendClientAction(InputEvent event) {
		
//		System.out.println(event.getClass()) ;
		count++;
//		System.out.println(count + "  " + event) ;
		try {
//			if (event == null ) {
//				System.out.println("event is null");
//			}
//			if (out == null ) {
//				System.out.println("out is null");
//			}
			out.writeObject(event);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		sendClientAction(e);
		
	}

	
	
}
