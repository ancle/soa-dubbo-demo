package com.demo.dubbo.common.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {
	private Class<?> beanClass;

	public ReferenceBeanDefinitionParser(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();

		beanDefinition.setBeanClass(this.beanClass);
		beanDefinition.setLazyInit(false);

		String id = element.getAttribute("id");
		String intf = element.getAttribute("interface");
		String protocol = element.getAttribute("protocol");

		if (StringUtils.isEmpty(intf)) {
			throw new RuntimeException("Reference interface 不能为空");
		}

		if (StringUtils.isEmpty(protocol)) {
			throw new RuntimeException("Reference protocol 不能为空");
		}

		beanDefinition.getPropertyValues().add("id", id);
		beanDefinition.getPropertyValues().add("interface", intf);
		beanDefinition.getPropertyValues().add("protocol", protocol);

		parserContext.getRegistry().registerBeanDefinition("referenceBean", beanDefinition);

		return null;
	}

}
