package com.demo.dubbo.common.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class RegistryBeanDefinitionParser implements BeanDefinitionParser {
	private Class<?> beanClass;

	public RegistryBeanDefinitionParser(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();

		beanDefinition.setBeanClass(this.beanClass);
		beanDefinition.setLazyInit(false);

		String protocol = element.getAttribute("protocol");
		String address = element.getAttribute("address");

		if (StringUtils.isEmpty(protocol)) {
			new RuntimeException("Registy protocol 属性不能为空");
		}

		if (StringUtils.isEmpty(address)) {
			new RuntimeException("Registy address 属性不能为空");
		}

		beanDefinition.getPropertyValues().add("protocol", protocol);
		beanDefinition.getPropertyValues().add("address", address);

		parserContext.getRegistry().registerBeanDefinition("registryBean", beanDefinition);

		return beanDefinition;
	}

}
