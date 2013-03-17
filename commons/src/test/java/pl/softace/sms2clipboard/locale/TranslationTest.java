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
		// when
		String value = Translation.getInstance().getProperty(Category.NEW_DB_VERSION_AVAILABLE);
		
		// then
		Properties properties = new Properties();
		properties.load(ClassLoader.getSystemResourceAsStream("locale_pl.properties"));
		Assert.assertEquals(value, properties.getProperty(Category.NEW_DB_VERSION_AVAILABLE.getKey()));
	}
	
	/**
	 * Gets translation text in EN language.
	 * @throws IOException 
	 */
	@Test(dependsOnMethods = "getTranslationTextInPlLanguage")
	public final void getTranslationTextInEnLanguage() throws IOException {		
		// when		
		String value = Translation.getInstance("en").getProperty(Category.NEW_DB_VERSION_AVAILABLE);
		
		// then
		Properties properties = new Properties();
		properties.load(ClassLoader.getSystemResourceAsStream("locale_en.properties"));
		Assert.assertEquals(value, properties.getProperty(Category.NEW_DB_VERSION_AVAILABLE.getKey()));
	}
}
