package com.demo.dubbo.common.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ProtocolBeanDefinitionParser implements BeanDefinitionParser {
	private Class<?> beanClass;

	public ProtocolBeanDefinitionParser(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();

		beanDefinition.setBeanClass(this.beanClass);
		beanDefinition.setLazyInit(false);

		String name = element.getAttribute("name");
		String host = element.getAttribute("host");
		String port = element.getAttribute("port");

		beanDefinition.getPropertyValues().add("name", name);
		beanDefinition.getPropertyValues().add("host", host);
		beanDefinition.getPropertyValues().add("port", port);

		parserContext.getRegistry().registerBeanDefinition("protocolBean", beanDefinition);

		return beanDefinition;
	}

}
