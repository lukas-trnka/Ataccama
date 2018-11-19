package cz.lukastrnka.rest.data;

public class Column extends Argument {

	private String column;

	public Column(String column) {
		super();
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	@Override
	public String giveArgument() {
		
		return getColumn();
	}

	
}
