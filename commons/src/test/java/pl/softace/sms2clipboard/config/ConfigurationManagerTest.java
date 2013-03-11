package pl.softace.sms2clipboard.config;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * Class used for testing the {@link ConfigurationManager}.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ConfigurationManagerTest {

	/**
	 * Saves configuration to file and reads.
	 */
	@Test
	public final void saveAndLoadConfigurationToFile() {
		// given 
		Configuration configuration = ConfigurationManager.getInstance().getConfiguration();
		configuration.setPassword("password");
		configuration.setTemplatesDBVersion("1.0.3");
		configuration.setUpdateDelay(60 * 60 * 1000);
		
		// when
		ConfigurationManager.getInstance().saveToFile();
		
		// then
		ConfigurationManager.getInstance().loadFromFile();
		Configuration loadedConfiguration = ConfigurationManager.getInstance().getConfiguration();
		
		Assert.assertNotNull(loadedConfiguration);
	}
}
