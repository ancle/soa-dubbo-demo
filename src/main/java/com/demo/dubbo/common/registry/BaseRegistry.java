package com.demo.dubbo.common.registry;

import java.util.List;

import org.springframework.context.ApplicationContext;

public interface BaseRegistry {
	public boolean registry(String param, ApplicationContext context);

	public List<String> getRegistryInfo(String id, ApplicationContext context);
}
