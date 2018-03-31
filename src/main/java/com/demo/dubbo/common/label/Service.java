package com.demo.dubbo.common.label;

import java.io.Serializable;

public class Service implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2919251489795542429L;
	private String interfaceName;
	private String ref;
	private String protocol;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}
