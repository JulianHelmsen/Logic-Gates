package logicGates;

public class Pin extends LogicGate {

	public Pin() {
		super(0);
	}

	@Override
	public boolean getOutput() {
		return this.getInputPin(0) == 1;
	}

	@Override
	public String getName() {
		return "Pin";
	}

	public void toggle() {
		this.setPin(!this.getOutput());
	}
	
	
	public void setPin(boolean value) {
		super.setValue((byte) (value ? 1 : 0));
	}
	
	

}
