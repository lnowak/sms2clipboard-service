package pl.softace.sms2clipboard.locale;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * Used for {@link Translation} testing.
 * 
 * @author Lukasz
 *
 */
public class TranslationTest {

	/**
	 * Gets translation text in PL language.
	 * @throws IOException 
	 */
	@Test
	public final void getTranslationTextInPlLanguage() throws IOException {
		// given
		String key = "test1";
		
		// when
		String value = Translation.getInstance().getProperty(key);
		
		// then
		Properties properties = new Properties();
		properties.load(ClassLoader.getSystemResourceAsStream("locale_pl.properties"));
		Assert.assertEquals(value, properties.getProperty(key));
	}
	
	/**
	 * Gets translation text in EN language.
	 * @throws IOException 
	 */
	@Test(dependsOnMethods = "getTranslationTextInPlLanguage")
	public final void getTranslationTextInEnLanguage() throws IOException {
		// given
		String key = "test1";
		
		// when		
		String value = Translation.getInstance("en").getProperty(key);
		
		// then
		Properties properties = new Properties();
		properties.load(ClassLoader.getSystemResourceAsStream("locale_en.properties"));
		Assert.assertEquals(value, properties.getProperty(key));
	}
}
