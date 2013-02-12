package pl.softace.passwordless.net.api.packet;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import pl.softace.passwordless.net.api.packet.annotations.PropertyParameter;
import pl.softace.passwordless.net.api.packet.enums.PacketParameter;
import pl.softace.passwordless.net.api.packet.enums.ParameterType;
import pl.softace.passwordless.security.AESCrypt;

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
	private static final Logger LOG = Logger.getLogger(ReflectedPacket.class);
	
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 3986785646106904008L;

	
	/**
	 * Returns the packet as a byte array (body with header).
	 * 
	 * @return byte array
	 */
	public final ByteBuffer encodePacket(String password) {
		countBodyLength(password);
		
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
					case STRING:
						buffer.putInt(((String) obj).length());
						buffer.put(((String) obj).getBytes());
						break;
					case SECURED_STRING:						
						String text = (String) obj;
						if (propertyParameter.parameter().getType().equals(ParameterType.SECURED_STRING)) {
							text = new String(AESCrypt.encrypt(password, text.getBytes()));
						}						
						buffer.putInt(text.length());
						buffer.put(text.getBytes());
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
	public final void decodeBody(ByteBuffer buffer, String password) {
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
			case STRING:
				byte[] bytes = new byte[buffer.getInt()];
				buffer.get(bytes);
				obj = new String(bytes);
				break;
			case SECURED_STRING:
				bytes = new byte[buffer.getInt()];
				buffer.get(bytes);			
				obj = new String(AESCrypt.decrypt(password, bytes));
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
	protected final void countBodyLength(String password) {
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
							String text = (String) obj;
							if (propertyParameter.parameter().getType().equals(ParameterType.SECURED_STRING)) {
								text = new String(AESCrypt.encrypt(password, text.getBytes()));
							}
							bodyLength +=  text.length() + 4 + 1;
						}
					}
				}
			}
		}
		
		setBodyLength(bodyLength);
	}
}
