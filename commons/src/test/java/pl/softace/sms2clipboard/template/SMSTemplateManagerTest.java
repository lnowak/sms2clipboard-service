package pl.softace.sms2clipboard.template;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * 
 * Used for {@link SMSTemplateManager} tests.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMSTemplateManagerTest {

	/**
	 * Provider with SMS examples 2 parse.
	 * 
	 * @return		parameters
	 */
	@DataProvider(name = "SMS examples")
	public Object[][] examples() {
		return new Object[][]{
				{"3388", "Operacja nr 1 z dn. 17-02-2013 mTransfer z rach.: ...55060959 na rach.: 8111...746759 kwota: 60,00 PLN haslo: 98066528 mBank.", "98066528"},
				{"Alior Bank", "Alior Bank: Przelew na rachunek 32...6925; Odbiorca: UPC Polska; Kwota 144,99 PLN ; Kod SMS nr 72 z dn. 05-03-2013: 547709", "547709"}
	       };
	}
	
	/**
	 * Encrypts templates from text file.
	 */
	@Test
	public final void encryptTemplates() {
		// when
		SMSTemplateManager.getInstance().loadFromFile(false, "./config/templates.txt");
		SMSTemplateManager.getInstance().saveToFile();
		
		// then
		SMSTemplateManager.getInstance().loadFromFile();
		Assert.assertTrue(SMSTemplateManager.getInstance().getTemplates().size() > 0);
	}
		
	/**
	 * Checks the templates.
	 */
	@Test(dependsOnMethods = "encryptTemplates", dataProvider = "SMS examples")
	public final void findsTemplateFromEncryptedFile(String smsSource, String smsText, String password) {
		// given
		
		// when
		SMSTemplateManager.getInstance().loadFromFile();
		SMSTemplate smsTemplate = SMSTemplateManager.getInstance().findSMSTemplate(smsSource, smsText);
		String foundPassword = smsTemplate != null ? smsTemplate.getSMSPassword(smsText) : null;
		
		// then
		Assert.assertEquals(foundPassword, password);
	}
}
