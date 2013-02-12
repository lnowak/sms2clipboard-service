package pl.softace.passwordless.net.api.packet;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public abstract class ReflectedPacket extends Packet {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 3986785646106904008L;

	/**
	 * Forbidden class fields.
	 */
	private static final List<String> FORBIDDEN_FIELDS = Arrays.asList("serialVersionUID");
	
	
	/**
	 * Returns size of the objects.
	 * 
	 * @param clazz		class
	 * @param value		value of the class
	 * @return			size in bytes
	 */
	private int getSize(Class<?> clazz, Object value) {
		if (clazz.getName().equals("int")) {
			return Integer.SIZE/8;
		} else if (clazz.getName().equals("java.lang.String")) {
			return ((String) value).length();
		} else if (clazz.getName().equals("boolean")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns the packet as a byte array (body with header).
	 * 
	 * @return byte array
	 */
	public final ByteBuffer encodePacket() {
		for (Field field: getClass().getDeclaredFields()) {
			if (!FORBIDDEN_FIELDS.contains(field.getName())) {
				StringBuilder text = new StringBuilder();
				text.append(field.getName());
				text.append(", ");
						
				try {
					field.setAccessible(true);
					text.append(field.getType());
					text.append(", ");
					text.append(field.get(this));
					text.append(", ");
					text.append(getSize(field.getType(), field.get(this)));
					System.out.println(text.toString());
				} catch (IllegalArgumentException e) {
					;
				} catch (IllegalAccessException e) {
					;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Gets and sets the packet parameters from the buffer. 
	 * 
	 * @param in	byte buffer
	 */
	public final void decodeBody(ByteBuffer in) {
		
	}
	
	/**
	 * Count body length.
	 */
	protected final void countBodyLength() {
		
	}
}
