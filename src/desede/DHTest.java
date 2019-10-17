package desede;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.spec.DHParameterSpec;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;

public class DHTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {

		System.out.println("A generate key pair");
		KeyPairGenerator A_KpairGen = KeyPairGenerator.getInstance("DH");
		A_KpairGen.initialize(512);
		System.out.println("A public key generation success");
		KeyPair A_Keypair = A_KpairGen.generateKeyPair();
		byte[] A_PubKeyEnc = A_Keypair.getPublic().getEncoded();
		System.out.println("A publish his public key");
		
		
		System.out.println("B received A's public key and decode according DH");
		KeyFactory B_KeyFac = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec (A_PubKeyEnc); 
		PublicKey A_B_PubKey = B_KeyFac.generatePublic(x509KeySpec);
		System.out.println("B successfully decrypt A public key and get A_B_PubKey");
	
		System.out.println("B reads parameters from A_B_Pubkey, and create its own key pair");
		
		DHParameterSpec dhParamSpec = ((DHPublicKey) A_B_PubKey).getParams();
		KeyPairGenerator B_KpairGen = KeyPairGenerator.getInstance("DH");
		B_KpairGen.initialize(dhParamSpec);
		KeyPair B_Keypair = B_KpairGen.generateKeyPair();
		System.out.println("B DH key paire success");
		
		System.out.println("B initialize its secret key");
		KeyAgreement B_keyAgree = KeyAgreement.getInstance("DH");
		B_keyAgree.init(B_Keypair.getPrivate());
		
		
		
		
		

	}

}
