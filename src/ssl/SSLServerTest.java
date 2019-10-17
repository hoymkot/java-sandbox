package ssl;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServerTest {
	public static void main(String[] args) throws Exception {
//		System.setProperty("javax.net.ssl.trustStore", "clientStore.ks");
//		System.setProperty("javax.net.ssl.trustStorePassword", "password");
		System.setProperty("javax.net.ssl.keyStore", "serverStore.ks"); // only this is necessary : server must have key
		System.setProperty("javax.net.ssl.keyStorePassword", "password"); 
		
//		System.setProperty("javax.net.ssl.trustStore", "serverStore.ks");
//		System.setProperty("javax.net.ssl.trustStorePassword", "password");
		ServerSocketFactory sf =SSLServerSocketFactory.getDefault();
		ServerSocket server = sf.createServerSocket(9999);
		System.out.println("created server at 9999");
		while(true) {
			try {
				Socket client = server.accept();
//				System.out.println("enter a connection");
//				String hello = "ABCDEFG hello" ;
//				client.getOutputStream().write(hello.getBytes());
//				client.getOutputStream().flush();
				System.out.println("server finish speaking");
				byte[] s = new byte["client says hello world. you are very nice. ".getBytes().length];
				DataInputStream dins = new DataInputStream(client.getInputStream());
				dins.readFully(s);
				System.out.println("server heard: " + new String(s));

				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
