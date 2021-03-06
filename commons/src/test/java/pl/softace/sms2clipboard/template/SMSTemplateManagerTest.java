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
				{"Sms2Clipboard", "Greetings from sms2clipboard creators! Sample password: 123456", "123456"},
				{"3388", "Operacja nr 1 z dn. 17-02-2013 mTransfer z rach.: ...55060959 na rach.: 8111...746759 kwota: 60,00 PLN haslo: 98066528 mBank.", "98066528"},
				{"Alior Bank", "Alior Bank: Przelew na rachunek 32...6925; Odbiorca: UPC Polska; Kwota 144,99 PLN ; Kod SMS nr 72 z dn. 05-03-2013: 547709", "547709"},
				{"Alior Bank", "Alior Bank: Przelew ZUS na rachunek 73...0000; Odbiorca: ZUS - Fundusz Pracy ; Kwota 54,58 PLN ; Kod SMS nr 265 z dn. 09-02-2013: 181188", "181188"},
				{"Alior Bank", "Alior Bank: Przelew podatkowy na rachunek 64...0000; Odbiorca: Pierwszy Urzad Skarb; Kwota 239,00 PLN ; Kod SMS nr 271 z dn. 21-03-2013: 111456", "111456"},				
				{"3388", "Operacja nr 8 z dn. 11-03-2013 Zmiana odbiorcy zdef. z rach: ...55060959 na rach: 6219...349135 haslo: 44167226 mBank.", "44167226"},
				{"3388", "Operacja nr 2 z dn. 22-03-2013 Przelew z rach.: ...55060959 na rach.: 4215...013339 kwota: 1 174,68 PLN haslo: 64655855 mBank.", "64655855"} 
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
