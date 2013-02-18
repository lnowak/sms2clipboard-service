package pl.softace.sms2clipboard.security;

import org.junit.Assert;
import org.testng.annotations.Test;

import pl.softace.sms2clipboard.security.AESCrypter;

/**
 * 
 * Used for {@link AESCrypter} test.
 * 
 * @author lkawon@gmail.com
 *
 */
public class AESCryptTest {

	/**
	 * Tries to crypt and decrypt string message.
	 */
	@Test
	public final void crytpAndDecryptText() {
		// given
		AESCrypter crypter = new AESCrypter("password");		
		String text = "To jest tekst do zaszyfrowania i odszyfrowania za pomocą algorytmu AES.";
		
		// when
		byte[] decryptedBytes = crypter.encrypt(text.getBytes());
		byte[] encryptedBytes = crypter.decrypt(decryptedBytes);	
		
		// then
		Assert.assertNotNull(decryptedBytes);
		Assert.assertNotSame(text, new String(decryptedBytes));
		Assert.assertEquals(text, new String(encryptedBytes));
	}
	
	/**
	 * Tries to crypt and decrypt string message many times.
	 */
	@Test
	public final void crytpAndDecrypTextManyTimes() {		
		// given
		AESCrypter crypter = new AESCrypter("password");
		String text = "To jest tekst do zaszyfrowania i odszyfrowania za pomocą algorytmu AES.";
			
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			// when
			String targetText = text + i;
						
			byte[] decryptedBytes = crypter.encrypt(targetText.getBytes());
			byte[] encryptedBytes = crypter.decrypt(decryptedBytes);				
			
			// then
			Assert.assertNotNull(decryptedBytes);			
			Assert.assertNotSame(targetText, new String(decryptedBytes));
			Assert.assertEquals(targetText, new String(encryptedBytes));
		}
		long endTime = System.currentTimeMillis();
		
		Assert.assertTrue(endTime - startTime <= 1000);
	}
	
	/**
	 * Tries to crypt and decrypt string message.
	 */
	@Test
	public final void crytpAndDecryptTextAndWithChangedPasswords() {
		// given
		String password = "password";
		String text = "To jest tekst do zaszyfrowania i odszyfrowania za pomocą algorytmu AES.";
				
		for (int i = 0; i < 10; i++) {
			// when
			AESCrypter crypter = new AESCrypter(password + i);
			byte[] decryptedBytes = crypter.encrypt(text.getBytes());
			byte[] encryptedBytes = crypter.decrypt(decryptedBytes);	
			
			// then
			Assert.assertNotNull(decryptedBytes);
			Assert.assertNotSame(text, new String(decryptedBytes));
			Assert.assertEquals(text, new String(encryptedBytes));
		}
	}
}
