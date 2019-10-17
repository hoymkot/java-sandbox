package ssl;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class SSLClientTest {
	public static void main(String[] args) throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "serverStore.ks"); // only this is necessary. client only connects to a trusted server.
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
//		System.setProperty("javax.net.ssl.keyStore", "clientStore.ks");
//		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		
//		System.setProperty("javax.net.ssl.keyStore", "clientStore.ks");
//		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		System.out.println("Client Started at 9999");
		
		SocketFactory sf =SSLSocketFactory.getDefault();
		Socket client= sf.createSocket("localhost", 9999);
			try {
//				byte[] s= new byte[ "ABCDEFG hello".length() ];
//				DataInputStream dins = new DataInputStream(client.getInputStream());
//				dins.readFully(s);
//				
				String hello = "client says hello world. you are very nice. " ;
				System.out.println("client starts talking");
				client.getOutputStream().write(hello.getBytes());
				client.getOutputStream().flush();
				System.out.println("client finishes talking ");
				//				
//				
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
