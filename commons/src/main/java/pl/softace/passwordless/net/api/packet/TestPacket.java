package pl.softace.passwordless.net.api.packet;

public class TestPacket extends ReflectedPacket {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -8160107589012572893L;

	private Integer i1;
	
	private Long l1;
	
	private int test;
	
	private String test2;
	
	private boolean ok;
	
	
	public final int getTest() {
		return test;
	}


	public final void setTest(int test) {
		this.test = test;
	}


	public final String getTest2() {
		return test2;
	}


	public final void setTest2(String test2) {
		this.test2 = test2;
	}


	public final boolean isOk() {
		return ok;
	}


	public final void setOk(boolean ok) {
		this.ok = ok;
	}


	public static void main(String[] args) {
		TestPacket packet = new TestPacket();
		packet.setOk(true);
		packet.setTest2("test123123");
		packet.encodePacket();
	}
}
