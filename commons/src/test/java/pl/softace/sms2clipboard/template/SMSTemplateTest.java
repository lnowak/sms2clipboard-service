package pl.softace.sms2clipboard.template;

import org.junit.Assert;
import org.testng.annotations.Test;

import pl.softace.sms2clipboard.template.SMSTemplate;

/**
 * 
 * For testing the {@link SMSTemplate}.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMSTemplateTest {

	/**
	 * Tries to match defined regex to SMS text and gets password.
	 */
	@Test
	public final void matchSmsAndGetPasswordWithoutSuffix() {
		// given
		SMSTemplate smsTemplate = new SMSTemplate();
		smsTemplate.setSource("3388");
		smsTemplate.setSmsRegex("Operacja nr \\d+ z dn. [\\d-]+ mTransfer z rach.: ...\\d+ na rach.: \\d+...\\d+ kwota: [\\d,]+ PLN haslo: ${PASSWORD}");
		smsTemplate.setPasswordRegex("\\\\d+");
		
		String smsText = "Operacja nr 1 z dn. 17-02-2013 mTransfer z rach.: ...55060959 na rach.: 8111...746759 kwota: 60,00 PLN haslo: 98066528";		
		
		// when
		boolean matched = smsTemplate.matchText(smsText);
		String prefix = smsTemplate.getSMSPrefix(smsText);
		String password = smsTemplate.getSMSPassword(smsText);
		String suffix = smsTemplate.getSMSSuffix(smsText);
		
		// then
		Assert.assertTrue(matched);
		Assert.assertEquals("98066528", password);
		Assert.assertEquals(smsText, prefix + password + suffix);
	}
	
	/**
	 * Tries to match defined regex to SMS text and gets password.
	 */
	@Test
	public final void matchSmsAndGetPasswordWithSuffix() {
		// given
		SMSTemplate smsTemplate = new SMSTemplate();
		smsTemplate.setSource("3388");
		smsTemplate.setSmsRegex("Operacja nr \\d+ z dn. [\\d-]+ mTransfer z rach.: ...\\d+ na rach.: \\d+...\\d+ kwota: [\\d,]+ PLN haslo: ${PASSWORD} mBank.");
		smsTemplate.setPasswordRegex("\\\\d+");
		
		String smsText = "Operacja nr 1 z dn. 17-02-2013 mTransfer z rach.: ...55060959 na rach.: 8111...746759 kwota: 60,00 PLN haslo: 98066528 mBank.";		
		
		// when
		boolean matched = smsTemplate.matchText(smsText);
		String prefix = smsTemplate.getSMSPrefix(smsText);
		String password = smsTemplate.getSMSPassword(smsText);
		String suffix = smsTemplate.getSMSSuffix(smsText);
		
		// then
		Assert.assertTrue(matched);
		Assert.assertEquals("98066528", password);
		Assert.assertEquals(smsText, prefix + password + suffix);
	}
}
