package desede;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

public class RSATest {

	public static void main(String[] args) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		
		RSAPrivateKey  privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		
		RSATest encrypt = new RSATest();
		String text = "I love you";
		 
		byte[] srcData = text.getBytes();
		byte[] e = encrypt.encrypt(publicKey, srcData);
		
		System.out.println("encrypted text with public key " + new String(e));
		
		byte[] de = encrypt.decrypt(privateKey, e);
		System.out.println("decrypted text with private key " + new String(de));
		
		
		
	}

	private byte[] decrypt(RSAPrivateKey privateKey, byte[] e) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(e);
	}

	private byte[] encrypt(RSAPublicKey publicKey, byte[] srcData) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(srcData);
	}

}
