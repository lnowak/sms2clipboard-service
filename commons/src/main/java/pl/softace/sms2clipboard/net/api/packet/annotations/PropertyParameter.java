package pl.softace.sms2clipboard.net.api.packet.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import pl.softace.sms2clipboard.net.api.packet.enums.PacketParameter;

/**
 * 
 * Used to sign property as packet parameter.
 * 
 * @author lkawon@gmail.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyParameter {

	/**
	 * Packet parameter.
	 */
	PacketParameter parameter();
}
