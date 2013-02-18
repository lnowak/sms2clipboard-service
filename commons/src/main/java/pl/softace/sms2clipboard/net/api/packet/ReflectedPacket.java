package pl.softace.sms2clipboard.net.api.packet;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.net.api.packet.annotations.PropertyParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.ParameterType;
import pl.softace.sms2clipboard.security.AESCrypter;

/**
 * 
 * Implementation of packet decoding and encoding based on reflection.
 * 
 * @author lkawon@gmail.com
 *
 */
public abstract class ReflectedPacket extends Packet {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ReflectedPacket.class);
	
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 3986785646106904008L;

	
	/**
	 * Returns the packet as a byte array (body with header).
	 * 
	 * @return byte array
	 */
	public final ByteBuffer encodePacket(AESCrypter crypter) {
		countBodyLength(crypter);
		
		ByteBuffer buffer = ByteBuffer.allocate(Packet.HEADER_LENGTH + getBodyLength());		
		buffer.put(getHeader());						
		
		for (Field field: getClass().getDeclaredFields()) {
			field.setAccessible(true);
			
			PropertyParameter propertyParameter = field.getAnnotation(PropertyParameter.class);	
			if (propertyParameter != null) {				
				
				Object obj = null;
				try {
					obj = field.get(this);
				} catch (IllegalArgumentException e) {
					LOG.error("Exception occurred.", e);
				} catch (IllegalAccessException e) {
					LOG.error("Exception occurred.", e);
				}
				
				if (obj != null) {
					buffer.put(propertyParameter.parameter().getId());
					
					switch (propertyParameter.parameter().getType()) {
					case INTEGER:
						buffer.putInt((Integer) obj);
						break;
					case LONG:
						buffer.putLong((Long) obj);
						break;
					case STRING:
						buffer.putInt(((String) obj).length());
						buffer.put(((String) obj).getBytes());
						break;
					case SECURED_STRING:						
						byte[] bytes = ((String) obj).getBytes();
						if (propertyParameter.parameter().getType().equals(ParameterType.SECURED_STRING)) {						
							bytes = crypter.encrypt(bytes);
						}							
						buffer.putInt(bytes.length);
						buffer.put(bytes);
						break;
					default:
						break;
					}
				}
			}
		}
		
		buffer.flip();				
		
		return buffer;
	}
	
	/**
	 * Gets and sets the packet parameters from the buffer. 
	 * 
	 * @param buffer	byte buffer
	 */
	public final void decodeBody(ByteBuffer buffer, AESCrypter crypter) {
		while (buffer.hasRemaining()) {
			byte id = buffer.get();
			PacketParameter packetParameter = PacketParameter.getById(id);	
			Field field = findField(packetParameter);
			field.setAccessible(true);
			
			Object obj = null;
			switch (packetParameter.getType()) {
			case INTEGER:
				obj = buffer.getInt();
				break;
			case LONG:
				obj = buffer.getLong();
				break;
			case STRING:
				byte[] bytes = new byte[buffer.getInt()];
				buffer.get(bytes);
				obj = new String(bytes);
				break;
			case SECURED_STRING:
				bytes = new byte[buffer.getInt()];
				buffer.get(bytes);	
				obj = new String(crypter.decrypt(bytes));
				break;
			default:
				break;
			}
			
			try {
				field.set(this, obj);
			} catch (IllegalArgumentException e) {
				LOG.debug("Exception occurred.", e);
			} catch (IllegalAccessException e) {
				LOG.debug("Exception occurred.", e);
			}
		}
	}
	
	/**
	 * Finds field by packet parameter.
	 * 
	 * @param id		packet id
	 * @return			field
	 */
	public final Field findField(PacketParameter packetParameter) {
		Field field = null;		
		if (packetParameter != null) {
			for (Field temp : getClass().getDeclaredFields()) {
				PropertyParameter propertyParameter = temp.getAnnotation(PropertyParameter.class);
				if (propertyParameter != null && propertyParameter.parameter().equals(packetParameter)) {
					field = temp;
					break;
				}
			}
		}
		
		return field;
	}
	
	/**
	 * Count body length.
	 */
	protected final void countBodyLength(AESCrypter crypter) {
		int bodyLength = 0;		
		for (Field field: getClass().getDeclaredFields()) {
			field.setAccessible(true);	
			
			PropertyParameter propertyParameter = field.getAnnotation(PropertyParameter.class);
			if (propertyParameter != null) {
				int length = propertyParameter.parameter().getType().getLength();
				if (length > 0) {
					bodyLength += length + 1;
				} else {
					Object obj = null;
					try {
						obj = field.get(this);
					} catch (IllegalArgumentException e) {
						LOG.error("Exception occurred.", e);
					} catch (IllegalAccessException e) {
						LOG.error("Exception occurred.", e);
					}
					if (obj != null) {
						if (obj instanceof String) {
							byte[] bytes = ((String) obj).getBytes();
							if (propertyParameter.parameter().getType().equals(ParameterType.SECURED_STRING)) {						
								bytes = crypter.encrypt(bytes);
							}							
							bodyLength +=  bytes.length + 4 + 1;
						}
					}
				}
			}
		}
		
		setBodyLength(bodyLength);
	}
}
