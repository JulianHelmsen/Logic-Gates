package logicGates;

public class Or extends LogicGate {

	public Or() {
		super(2);
	}

	@Override
	public boolean getOutput() {
		return (this.getInputPin(0) | this.getInputPin(1)) == 1;
	}

	@Override
	public String getName() {
		return "Or";
	}

}
