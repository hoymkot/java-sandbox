package desede;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class DESSocketClient {

	public static void main(String[] args) throws Exception {

		Socket sc = new Socket("localhost" , 9090);
		processConn(sc);
	}

	private static void processConn(Socket sc) throws Exception {
		InputStream ins = sc.getInputStream();
		OutputStream out = sc.getOutputStream();
		System.out.println("read folloing data");
		InputStream secInput = dencInputStream(ins);
		while(true) {
			int t = secInput.read();
			System.out.print((char)t);
			
		}
	}

	private static InputStream dencInputStream(InputStream ins) throws Exception {
		@SuppressWarnings("resource")
		FileInputStream fins = new FileInputStream("DESedekey.xeon");
		byte[] keyData = new byte[fins.available()];
		fins.read(keyData);
		fins.close();
		
		SecretKeySpec sk = new SecretKeySpec(keyData, "DESede");
		Cipher cp = Cipher.getInstance("DESede");
		cp.init(Cipher.DECRYPT_MODE, sk);
		CipherInputStream cins= new CipherInputStream(ins, cp);
		return cins;
	}

}
