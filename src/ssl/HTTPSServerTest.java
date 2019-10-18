package ssl;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

// convert java generated key pair to PKCS12 format for firefox usage 
// keytool -importkeystore -srckeystore clientStore.ks -destkeystore clientStorep12.p12 -srcstoretype JKS -deststoretype PKCS12 -deststorepass password
public class HTTPSServerTest {
	public static void main(String[] args) throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "clientStore.ks");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
		System.setProperty("javax.net.ssl.keyStore", "serverStore.ks"); // only this is necessary : server must have key
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		
		ServerSocketFactory sf =SSLServerSocketFactory.getDefault();
		
		SSLServerSocket server = (SSLServerSocket )sf.createServerSocket(9900);
		server.setNeedClientAuth(true);
		System.out.println("server created at 9900");
		while(true) {
			try {
				SSLSocket client = (SSLSocket) server.accept();
				InputStream ins = client.getInputStream();
				byte[] input = new byte[1024];
				ins.read(input);
				System.out.println("browser requests: " + new String(input).trim());
				
				String output = "<HTML><HEAD><TITLE>HELLO WORLD</TITLE></HEAD><BODY>HELLO WORLD</BODY></HTML>";
				System.out.println(output);

				client.getOutputStream().write(output.getBytes());
				client.getOutputStream().flush();
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
