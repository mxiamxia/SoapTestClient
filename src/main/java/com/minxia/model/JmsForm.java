package com.minxia.model;

public class JmsForm extends Form{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1258269225871927170L;
	
	private String state;
	private String trx;
	private String destination;
	
	public JmsForm()
	{
		super();
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getTrx() {
		return trx;
	}

	public void setTrx(String trx) {
		this.trx = trx;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
}
