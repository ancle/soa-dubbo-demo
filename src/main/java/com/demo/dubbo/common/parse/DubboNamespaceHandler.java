package com.demo.dubbo.common.parse;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.demo.dubbo.common.label.Protocol;
import com.demo.dubbo.common.label.Reference;
import com.demo.dubbo.common.label.Registry;
import com.demo.dubbo.common.label.Service;

public class DubboNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		this.registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParser(Registry.class));

		this.registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser(Reference.class));

		this.registerBeanDefinitionParser("service", new ServiceBeanDefinitionParser(Service.class));

		this.registerBeanDefinitionParser("protocol", new ProtocolBeanDefinitionParser(Protocol.class));
	}

}
