package desede;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DESSocketServer {

	public static void main(String[] args) throws Exception{
		ServerSocket sc = new ServerSocket(9090);
		while(true) {
			Socket client = sc.accept();
			processConn(client);
		}
	}

	private static void processConn(Socket sc) throws Exception{
		InputStream ins = sc.getInputStream();
		OutputStream ous = sc.getOutputStream();
		
		OutputStream secOus = encOutStream(ous);
		for (int i = 0; i < 3; i++) {
			String s = "sdjfalkjflajdf";
			secOus.write(s.getBytes());
			secOus.flush();
			ous.flush();
		}
		
		while (true) {
			char c = (char) ins.read();
			secOus.flush();
			ous.flush();
			System.out.println("Read " + c);
		}
		
	}

	private static OutputStream encOutStream(OutputStream ous) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		@SuppressWarnings("resource")
		FileInputStream fins = new FileInputStream("DESedekey.xeon");
		byte[] keyData = new byte[fins.available()];
		fins.read(keyData);
		fins.close();
		
		SecretKeySpec sk = new SecretKeySpec(keyData, "DESede");
		Cipher cp = Cipher.getInstance("DESede");
		cp.init(Cipher.ENCRYPT_MODE, sk);
		CipherOutputStream cout = new CipherOutputStream(ous, cp);
		return cout;
	}
	
}
