package pl.softace.passwordless.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * 
 * AES crypt utility.
 * 
 * @author lkawon@gmail.com
 *
 */
public class AESCrypter {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = Logger.getLogger(AESCrypter.class);
	
	/**
	 * Salt used to crypt / decrypt.
	 */
	private static final String SALT = "AES Crypt SALT"; 
		
	/**
	 * AES algorithm parameters.
	 */
	private static final byte[] ALGORITHM_PARAMETERS = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
														0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	/**
	 * Encryption password used to check if cipher needs to be reinitialized.
	 */
	private static String password;
	
	/**
	 * Cipher used for decrypting.
	 */
	private static Cipher decryptCipher;
	
	/**
	 * Cipher used for encryption.
	 */
	private static Cipher encryptCipher;
	
	/**
	 * Generates key from password.
	 * 
	 * @param text		text password
	 * @return			secret key
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	private static final SecretKey generateKey(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 128);
		SecretKey tmp = factory.generateSecret(spec);
		return new SecretKeySpec(tmp.getEncoded(), "AES");
	}	
	
	/**
	 * Initializes ciphers if needed.
	 * 
	 * @param password		password
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 */
	private static final void initializeCiphers(String password) throws InvalidKeyException, InvalidAlgorithmParameterException, 
	InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {				
	    if (encryptCipher == null || decryptCipher == null || password == null || !AESCrypter.password.equals(password)) {
	    	AESCrypter.password = password;
	    	
	    	SecretKey key = generateKey(password);
	    	
	    	encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    	encryptCipher.init(Cipher.ENCRYPT_MODE, key , new IvParameterSpec(ALGORITHM_PARAMETERS));
	    	
	    	decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    	decryptCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ALGORITHM_PARAMETERS));
	    	
	    	System.out.println("Ciphers reinicialized using password " + password + ".");
	    }
	}
	
	/**
	 * Encrypt byte array using AES.
	 * 
	 * @param password			AES password
	 * @param message			message to encrypt
	 * @return					encrypted message
	 */
	public static final byte[] encrypt(String password, byte[] message) {
		byte [] encryptedBytes = null;
		try {
			initializeCiphers(password);		    		   
		    encryptedBytes = encryptCipher.doFinal(message);	
		    
		} catch (NoSuchPaddingException e) {
			LOG.error("Exception during AES crypting.", e);
			throw new CryptException("Exception during AES crypting.", e);			
		} catch (BadPaddingException e) {
			LOG.error("Exception during AES crypting.", e);
			encryptCipher = null;
			throw new CryptException("Exception during AES crypting.", e);
		} catch (IllegalBlockSizeException e) {
			LOG.error("Exception during AES crypting.", e);
			throw new CryptException("Exception during AES crypting.", e);
		} catch (InvalidAlgorithmParameterException e) {
			LOG.error("Exception during AES crypting.", e);
			throw new CryptException("Exception during AES crypting.", e);
		} catch (InvalidKeyException e) {
			LOG.error("Exception during AES crypting.", e);
			throw new CryptException("Exception during AES crypting.", e);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Exception during AES crypting.", e);
			throw new CryptException("Exception during AES crypting.", e);
		} catch (InvalidKeySpecException e) {
			LOG.error("Exception during AES crypting.", e);
			throw new CryptException("Exception during AES crypting.", e);
		}
		
		return encryptedBytes;
	}

	/**
	 * Decrypt AES message using password.
	 * 
	 * @param password		AES password
	 * @param bytes			encoded message
	 * @return				decoded message
	 */
	public static final byte[] decrypt(String password, byte[] bytes) {
		byte [] decryptedBytes = null;		
		try {						
			initializeCiphers(password);			
		    decryptedBytes = decryptCipher.doFinal(bytes);		    
		    
		} catch (Exception e) {
			decryptCipher = null;
			LOG.error("Exception during AES decrypting.", e);			
			throw new CryptException("Exception during AES decrypting.", e);
		}
	    
	    return decryptedBytes;	    	   
	}
}
