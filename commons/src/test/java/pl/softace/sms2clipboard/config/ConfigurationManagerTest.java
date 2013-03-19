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
		Configuration configuration = new Configuration();
		configuration.setPassword("password");
		configuration.setTemplatesDBVersion("0.0.0");
		configuration.setUpdateDelay(60 * 60 * 1000);
		configuration.setServerPort(10000);
		ConfigurationManager.getInstance().setConfiguration(configuration);
		
		// when
		ConfigurationManager.getInstance().saveToFile();
		
		// then
		ConfigurationManager.getInstance().loadFromFile();
		Configuration loadedConfiguration = ConfigurationManager.getInstance().getConfiguration();
		
		Assert.assertNotNull(loadedConfiguration);
	}
}
