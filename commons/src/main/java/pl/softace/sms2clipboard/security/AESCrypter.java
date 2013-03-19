package pl.softace.sms2clipboard.security;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger LOG = LoggerFactory.getLogger(AESCrypter.class);
	
	/**
	 * Default password.
	 */
	private static final String DEAFULT_AES_PASSWORD = "sms2clipboard";
	
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
	private String password;
	
	/**
	 * Cipher used for decrypting.
	 */
	private Cipher decryptCipher;
	
	/**
	 * Cipher used for encryption.
	 */
	private Cipher encryptCipher;
	
	
	/**
	 * Default constructor.
	 */
	public AESCrypter() {
		password = DEAFULT_AES_PASSWORD;
		initializeCiphers();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param password		AES password
	 */
	public AESCrypter(String password) {
		this.password = password;
		initializeCiphers();
	}
	
	/**
	 * Generates key from password.
	 * 
	 * @param text		text password
	 * @return			secret key
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	private final SecretKey generateKey(String password) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digester = MessageDigest.getInstance("SHA");
	    digester.update(String.valueOf(SALT + password).getBytes("UTF-8"));
	    byte[] key = digester.digest();
	    byte[] shortKey = new byte[16];
	    for (int i = 0; i < 16; i++) {
	    	shortKey[i] = key[i];
	    }
	    return new SecretKeySpec(shortKey, "AES");
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
	public final void initializeCiphers() {
		try {
			if (encryptCipher == null || decryptCipher == null) {
		    	SecretKey key = generateKey(password);
		    	
		    	if (encryptCipher == null) {
		    		encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    		encryptCipher.init(Cipher.ENCRYPT_MODE, key , new IvParameterSpec(ALGORITHM_PARAMETERS));
		    	}
		    	
		    	if (decryptCipher == null) {
		    		decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    		decryptCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ALGORITHM_PARAMETERS));
		    	}
			}
	    	
	    	LOG.debug("Ciphers inicialized with password " + password + ".");
		} catch (Exception e) {		
			LOG.error("Exception during AES decrypting.", e);			
			throw new CryptException("Exception during AES ciphers initialization.", e);
		}
	}
	
	/**
	 * Encrypt byte array using AES.
	 * 
	 * @param password			AES password
	 * @param message			message to encrypt
	 * @return					encrypted message
	 */
	public final byte[] encrypt(byte[] message) {
		byte [] encryptedBytes = null;
		try {	    		
			initializeCiphers();
		    encryptedBytes = encryptCipher.doFinal(message);			    
		} catch (Exception e) {
			encryptCipher = null;
			LOG.error("Exception during AES decrypting.", e);			
			throw new CryptException("Exception during AES decrypting.", e);
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
	public final byte[] decrypt(byte[] bytes) {
		byte [] decryptedBytes = null;		
		try {			
			initializeCiphers();
		    decryptedBytes = decryptCipher.doFinal(bytes);		    		    
		} catch (Exception e) {			
			decryptCipher = null;
			LOG.error("Exception during AES decrypting.", e);			
			throw new CryptException("Exception during AES decrypting.", e);
		}
	    
	    return decryptedBytes;	    	   
	}
	
	/**
	 * 
	 * Encrypts input stream.
	 * 
	 * @param in			input stream
	 * @param out			output stream
	 */
	public void encrypt(InputStream in, OutputStream out) {	
		byte[] buf = new byte[1024];
		try {
			out = new CipherOutputStream(out, encryptCipher);
	
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			out.close();
		} catch (Exception e) {
			LOG.error("Exception during AES crypting.", e);			
			throw new CryptException("Exception during AES crypting.", e);
		}
	}

	/**
	 * Decrypts input stream.
	 * 
	 * @param in			input stream
	 * @param out			output stream
	 */
	public void decrypt(InputStream in, OutputStream out) {
		byte[] buf = new byte[1024];
		try {
			in = new CipherInputStream(in, decryptCipher);
	
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			out.close();
		}  catch (Exception e) {
			LOG.error("Exception during AES decrypting.", e);			
			throw new CryptException("Exception during AES decrypting.", e);
		}
	}
	
	/**
	 * Generates SHA-256 hash from string.
	 * 
	 * @param text		text string
	 * @return			hash
	 */
	public final String generateHash(String text) {
		String hashString = null;
		if (text != null) {
	        MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				LOG.error("Exception occured.", e);
			}
	        md.update(text.getBytes());
	        byte byteData[] = md.digest();
	 
	        StringBuffer hash = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	        	hash.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        
	        hashString = hash.toString();
		}
		
		return hashString;
	}
}
