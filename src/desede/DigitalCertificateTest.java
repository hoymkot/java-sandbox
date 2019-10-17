package desede;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

public class DigitalCertificateTest {

	public static void main(String[] args) throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream("key.cer");
		X509Certificate t = (X509Certificate ) (cf.generateCertificate(in));
		System.out.println(t.getVersion());
		System.out.println(t.getSubjectDN());
		System.out.println(t.getIssuerDN());
		System.out.println(t.getNotBefore());
		System.out.println(t.getSigAlgName());

		byte[] sig = t.getSignature();
		PublicKey pk = t.getPublicKey();
		byte[] pkEnc = pk.getEncoded();
		System.out.println("Signature Data");
		for (int i = 0; i< sig.length; i++) {
			if(i%10 ==0) System.out.println();
			System.out.print(" " + sig[i]);
		}
		System.out.println();
		
		System.out.println("Key data: ");
		for (int i = 0; i< pkEnc.length; i++) {
			if(i%10 ==0) System.out.println();
			System.out.print(" " + pkEnc[i]);
		}
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, pk);
		byte[] sig_de= cipher.doFinal(sig);

		
		System.out.println("decrypt sig data: ");
		for (int i = 0; i< sig_de.length; i++) {
			if(i%10 ==0) System.out.println();
			System.out.print(" " + sig_de[i]);
		}
		System.out.println(new String(sig_de));
	}
	
	static byte[] decrypt(RSAPrivateKey privateKey, byte[] e) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(e);
	}
	
	static  byte[] encrypt(RSAPublicKey publicKey, byte[] srcData) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(srcData);
	}
}
