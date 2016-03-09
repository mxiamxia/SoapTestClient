package com.minxia.model;

import java.io.Serializable;

public class Form implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8816482445983538250L;
	
	protected String name;
	protected String url;
	protected String input;
	protected String output;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	@Override
	public String toString() {
		return "JmsForm [name=" + name + ", url=" + url + ", input=" + input + ", output=" + output + "]";
	}

}
