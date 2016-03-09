package com.minxia.model;


public class WsForm extends Form  {

	private static final long serialVersionUID = 3813216946614306919L;

	protected String action;
	protected boolean useSSL;
	protected String properties;
	public WsForm()
	{
		super();
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isUseSSL() {
		return useSSL;
	}
	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}

	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	@Override
	public String toString() {
		return "WsForm [action=" + action + ", useSSL=" + useSSL
				+ ", properties=" + properties + "]";
	}
	
	

}
