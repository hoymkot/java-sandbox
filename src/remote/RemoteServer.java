package remote;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class RemoteServer {
	public static void main(String[] arg0) {
		try {
			ServerSocket server = new ServerSocket(9090);
			while (true) {
				Socket client = server.accept();
				new RemoteScreenThread(client).start();
				new RemoteRobotThread(client).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
