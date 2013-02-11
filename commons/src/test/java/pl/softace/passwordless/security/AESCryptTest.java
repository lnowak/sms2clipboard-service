package pl.softace.passwordless.security;

import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * 
 * Used for {@link AESCrypt} test.
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
		String password = "password";
		String text = "To jest tekst do zaszyfrowania i odszyfrowania za pomocą algorytmu AES.";
		
		// when
		byte[] decryptedBytes = AESCrypt.encrypt(password, text.getBytes());
		byte[] encryptedBytes = AESCrypt.decrypt(password, decryptedBytes);
		
		// then
		Assert.assertNotNull(decryptedBytes);
		Assert.assertNotSame(text, new String(decryptedBytes));
		Assert.assertEquals(text, new String(encryptedBytes));
	}
}
