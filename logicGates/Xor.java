package logicGates;

public class Xor extends LogicGate {

	public Xor() {
		super(2);
	}

	@Override
	public boolean getOutput() {
		return (this.getInputPin(0) ^ this.getInputPin(1)) == 1;
	}

	@Override
	public String getName() {
		return "Xor";
	}

}
