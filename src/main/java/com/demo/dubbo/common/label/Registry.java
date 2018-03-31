package com.demo.dubbo.common.label;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.demo.dubbo.common.registry.BaseRegistry;
import com.demo.dubbo.common.registry.BaseRegistryDelegate;
import com.demo.dubbo.common.registry.impl.RedisRegistry;

public class Registry implements Serializable, InitializingBean, ApplicationContextAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5179277449411409778L;
	private String address;
	private String protocol;
	private ApplicationContext applicationContext;
	private static Map<String, BaseRegistry> registries = new HashMap<String, BaseRegistry>();

	static {
		registries.put("redis", new RedisRegistry());
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		BaseRegistryDelegate.registry("", applicationContext);
	}

	public static Map<String, BaseRegistry> getRegistries() {
		return registries;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
