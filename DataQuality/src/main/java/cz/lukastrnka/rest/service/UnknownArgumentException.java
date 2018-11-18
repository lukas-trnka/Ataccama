package cz.lukastrnka.rest.service;

import com.fasterxml.jackson.databind.JsonNode;

public class UnknownArgumentException extends Exception  {

	String errNode;
	public UnknownArgumentException(String errNode) {
		super();
		this.errNode = errNode;
	}
	public String getErrNode() {
		return errNode;
	}
	public void setErrNode(String errNode) {
		this.errNode = errNode;
	}
	
}
