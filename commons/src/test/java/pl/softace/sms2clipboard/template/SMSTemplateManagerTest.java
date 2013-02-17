package pl.softace.sms2clipboard.template;

import org.testng.Assert;
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
	 * Finds correct template.
	 */
	@Test
	public final void findsTemplate() {
		// given
		String smsText = "Operacja nr 1 z dn. 17-02-2013 mTransfer z rach.: ...55060959 na rach.: 8111...746759 kwota: 60,00 PLN haslo: 98066528 mBank.";
		
		// when
		SMSTemplateManager.getInstance().reload();
		SMSTemplate smsTemplate = SMSTemplateManager.getInstance().findSMSTemplate(smsText);
		String password = smsTemplate.getSMSPassword(smsText);
		
		// then
		Assert.assertNotNull(smsTemplate);
		Assert.assertEquals(password, "98066528");
	}
}
