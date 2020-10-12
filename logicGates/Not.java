package logicGates;

public class Not extends LogicGate {

	public Not() {
		super(1);
	}

	@Override
	public boolean getOutput() {
		return this.getInputPin(0) == 0 ? true : false;
	}

	@Override
	public String getName() {
		return "Not";
	}

}
