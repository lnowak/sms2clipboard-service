package pl.softace.passwordless.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
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
public class AESCrypt {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = Logger.getLogger(AESCrypt.class);
	
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
	 * Generates key from password.
	 * 
	 * @param text		text password
	 * @return			secret key
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static final SecretKey generateKey(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 128);
		SecretKey tmp = factory.generateSecret(spec);
		return new SecretKeySpec(tmp.getEncoded(), "AES");
	}	
	
	public static byte[] encrypt(String password, byte[] mes) {
		byte [] encryptedBytes = null;
		try {
		    AlgorithmParameterSpec ivSpec = new IvParameterSpec(ALGORITHM_PARAMETERS);
		    Cipher cipher = null;
		    cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, generateKey(password), ivSpec);
		    encryptedBytes = cipher.doFinal(mes);
		} catch (NoSuchPaddingException e) {
			LOG.error("Exception during AES crypting.", e);
			throw new CryptException("Exception during AES crypting.", e);
		} catch (BadPaddingException e) {
			LOG.error("Exception during AES crypting.", e);
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

	public static byte[] decrypt(String password, byte[] bytes) {
		byte [] decryptedBytes = null;		
		try {
		    AlgorithmParameterSpec ivSpec = new IvParameterSpec(ALGORITHM_PARAMETERS);
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, generateKey(password), ivSpec);
		    decryptedBytes = cipher.doFinal(bytes);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Exception during AES decrypting.", e);
			throw new CryptException("Exception during AES decrypting.", e);
		} catch (NoSuchPaddingException e) {
			LOG.error("Exception during AES decrypting.", e);
			throw new CryptException("Exception during AES decrypting.", e);
		} catch (InvalidKeyException e) {
			LOG.error("Exception during AES decrypting.", e);
			throw new CryptException("Exception during AES decrypting.", e);
		} catch (InvalidAlgorithmParameterException e) {
			LOG.error("Exception during AES decrypting.", e);
			throw new CryptException("Exception during AES decrypting.", e);
		} catch (IllegalBlockSizeException e) {
			LOG.error("Exception during AES decrypting.", e);
			throw new CryptException("Exception during AES decrypting.", e);
		} catch (BadPaddingException e) {
			LOG.error("Exception during AES decrypting.", e);
			throw new CryptException("Exception during AES decrypting.", e);
		} catch (InvalidKeySpecException e) {
			LOG.error("Exception during AES decrypting.", e);
			throw new CryptException("Exception during AES decrypting.", e);
		}
	    
	    return decryptedBytes;	    	   
	}
}
