package com.demo.dubbo.common.label;

import java.io.Serializable;

public class Protocol implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8832878214798165660L;
	private String name;
	private String host;
	private String port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
