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
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;

public class DHTest {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
		System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
		
		System.out.println("A generate key pair");
		// Generate A's key pair
		KeyPairGenerator A_KpairGen = KeyPairGenerator.getInstance("DH");
		A_KpairGen.initialize(512);
		System.out.println("A public key generation success");
		KeyPair A_Keypair = A_KpairGen.generateKeyPair();
		byte[] A_PubKeyEnc = A_Keypair.getPublic().getEncoded();
		System.out.println("A publish his public key");
		
		// A sends it public key to B
		
		System.out.println("B received A's public key and decode according DH");
		KeyFactory B_KeyFac = KeyFactory.getInstance("DH");
		// get key specification from A'Public Key 
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec (A_PubKeyEnc);
		// now have A's Public Key
		PublicKey A_B_PubKey = B_KeyFac.generatePublic(x509KeySpec);
		System.out.println("B successfully decrypt A public key and get A_B_PubKey");
	
		System.out.println("B reads parameters from A_B_Pubkey, and create its own key pair");
		
		// Get A's public key's parameter
		DHParameterSpec dhParamSpec = ((DHPublicKey) A_B_PubKey).getParams();
		
		//Generate B's Key Pair
		KeyPairGenerator B_KpairGen = KeyPairGenerator.getInstance("DH");
		B_KpairGen.initialize(dhParamSpec);
		KeyPair B_Keypair = B_KpairGen.generateKeyPair();
		System.out.println("B DH key pair success");
		
		System.out.println("B initialize its secret key");
		
		// Prepare to build a mutually shared secret key wraped by BPrivate -> APublic ->Text
		KeyAgreement B_keyAgree = KeyAgreement.getInstance("DH");
		B_keyAgree.init(B_Keypair.getPrivate());
		
		System.out.println("B use A's public to generate DES to create its own ");
		// Encrypt A's Public key with B's Private Key;
		B_keyAgree.doPhase(A_B_PubKey, true);
		// generate a new secret key  that is wrap in this way: BPrivate -> APublic ->Text
		// generateSecret is deprecated it requires 	System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true"); to work
		SecretKey B_DesKey = B_keyAgree.generateSecret("DES"); 
		System.out.println("");
		
		System.out.println("B");
		// get B's public Key
		byte[] B_pubKeyEnc = B_Keypair.getPublic().getEncoded();
		
		// B sends its public key to A
		KeyFactory aliceKeyFac =KeyFactory.getInstance("DH");
		x509KeySpec = new X509EncodedKeySpec(B_pubKeyEnc);		
		PublicKey B_PubKey = aliceKeyFac.generatePublic(x509KeySpec);
		System.out.println("A get B's public key" );
		
		// generate key agreement APrivate -> BPublic -> Secret
		KeyAgreement A_KeyAgree = KeyAgreement.getInstance("DH");
		A_KeyAgree.init(A_Keypair.getPrivate());
		A_KeyAgree.doPhase(B_PubKey, true);
		// generateSecret is deprecated it requires 	System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true"); to work
		SecretKey A_DesKey = A_KeyAgree.generateSecret("DES");
		System.out.println("");
		
		
		// B prepares a text to A encrypted
		String BMsg = "This to the text";
		System.out.println("");
		Cipher B_Cipher = Cipher.getInstance("DES");
		B_Cipher.init(Cipher.ENCRYPT_MODE,  B_DesKey);
		byte[] encData = B_Cipher.doFinal(BMsg.getBytes());
		
		// send encode date to A
		
		Cipher A_Cipher = Cipher.getInstance("DES");
		A_Cipher.init(Cipher.DECRYPT_MODE, A_DesKey);
		byte[] decData = A_Cipher.doFinal(encData);
		
		System.out.println(new String(decData));
		
		
		

	}

}
