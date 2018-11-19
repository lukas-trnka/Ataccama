package cz.lukastrnka.rest.service;

public class NonUniqueNameException extends Exception  {

	String name;

	public NonUniqueNameException(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
