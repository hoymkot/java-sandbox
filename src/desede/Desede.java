package desede;

import java.io.FileOutputStream;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Desede {

	public static void main(String[] args) throws Exception{
		javax.crypto.KeyGenerator kg = KeyGenerator.getInstance("DESede");
		kg.init(168);
		SecretKey key = kg.generateKey();
		byte[] data = key.getEncoded();
		FileOutputStream fous = new FileOutputStream("DESedekey.xeon");
		fous.write(data);
		fous.flush();
		fous.close();
		System.out.println("DESede key file generation success");
	}
}
