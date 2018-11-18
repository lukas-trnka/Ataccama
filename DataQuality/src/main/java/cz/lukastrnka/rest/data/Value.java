package cz.lukastrnka.rest.data;

public class Value extends Argument {
	
	private int value;

	public Value(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	

}
