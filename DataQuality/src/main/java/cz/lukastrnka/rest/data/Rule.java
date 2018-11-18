package cz.lukastrnka.rest.data;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Rule {

	private long id;
	private String name;
	private Boolean enabled;
	private String op;
	@JsonUnwrapped
	private Argument arg1;
	@JsonUnwrapped
	private Argument arg2;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {

		if (!(op.equals("<")
				|| op.equals(">")
				|| op.equals("<=")
				|| op.equals("<=")
				|| op.equals("!=")
				|| op.equals("="))) {
			
			throw new IllegalArgumentException("Ilegal operator: " + op);
		}
		this.op = op;
	}

	public Argument getArg1() {
		return arg1;
	}

	public void setArg1(Argument arg1) {
		this.arg1 = arg1;
	}

	public Argument getArg2() {
		return arg2;
	}

	public void setArg2(Argument arg2) {
		this.arg2 = arg2;
	}

}
