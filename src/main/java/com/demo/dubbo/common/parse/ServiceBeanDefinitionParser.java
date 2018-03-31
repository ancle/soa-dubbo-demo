package com.demo.dubbo.common.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ServiceBeanDefinitionParser implements BeanDefinitionParser {

	private Class<?> beanClass;

	public ServiceBeanDefinitionParser(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {

		RootBeanDefinition beanDefinition = new RootBeanDefinition();

		beanDefinition.setBeanClass(this.beanClass);
		beanDefinition.setLazyInit(false);

		String interfaceName = element.getAttribute("interface");
		String ref = element.getAttribute("ref");

		beanDefinition.getPropertyValues().add("interface", interfaceName);
		beanDefinition.getPropertyValues().add("ref", ref);

		parserContext.getRegistry().registerBeanDefinition("serviceBean", beanDefinition);

		return beanDefinition;
	}

}
