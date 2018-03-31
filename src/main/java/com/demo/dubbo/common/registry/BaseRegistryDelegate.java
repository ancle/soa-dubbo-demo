package com.demo.dubbo.common.registry;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.demo.dubbo.common.label.Registry;

public class BaseRegistryDelegate {
	public static void registry(String ref, ApplicationContext context) {
		Registry registry = context.getBean(Registry.class);

		String protocol = registry.getProtocol();

		BaseRegistry registryCenter = registry.getRegistries().get(protocol);
		registryCenter.registry(ref, context);
	}

	public static List<String> getRegistryInfo(String id, ApplicationContext context) {
		Registry registryBean = context.getBean(Registry.class);

		BaseRegistry registrCenter = registryBean.getRegistries().get(registryBean.getProtocol());

		return registrCenter.getRegistryInfo(id, context);
	}
}
