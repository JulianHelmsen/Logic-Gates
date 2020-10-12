package logicGates;

public abstract class LogicGate implements State {

	private final byte pinCount;
	private byte pins;
	
	public LogicGate(int pinCount) {
		this.pinCount = (byte) pinCount;
		this.pins = 0;
	}
	
	public byte getPinCount() {
		return this.pinCount;
	}
	
	public final void setPin(int pin, boolean bit) {
		if(pin >= this.pinCount) {
			return;
		}
		
		if(bit) {
			this.pins |= 1 << pin;
		}else{
			this.pins &= ~(1 << pin);
		}
	}
	
	public void setPin(int pin, int newState) {
		this.setPin(pin, newState != 0);
	}
	
	public byte getInputPin(int index) {
		return (byte) (this.pins >> index & 1);
	}
	
	public void setValue(byte b) {
		this.pins = b;
	}
	
	@Override
	public String toString() {
		String binaryStringInputs = Integer.toBinaryString(this.pins);
		for(int i = this.pinCount - 1 - binaryStringInputs.length(); i >= 0; i--) {
			binaryStringInputs = '0' + binaryStringInputs;
		}
		String result = String.format("Inputs: [%s]\tOutput:[%d]", binaryStringInputs, this.getOutput() ? 1 : 0);
		return result;
	}
	
	public abstract String getName();
	
}
