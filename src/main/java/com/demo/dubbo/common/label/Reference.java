package com.demo.dubbo.common.label;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import com.demo.dubbo.common.loadbalance.DubboLoadBalance;
import com.demo.dubbo.common.loadbalance.RandomLoadBalance;
import com.demo.dubbo.common.loadbalance.RoundRobinLoadBalance;
import com.demo.dubbo.common.registry.BaseRegistryDelegate;
import com.demo.dubbo.proxy.advice.InvokerInvocationHandler;
import com.demo.dubbo.proxy.invoker.HttpInvoker;
import com.demo.dubbo.proxy.invoker.Invoker;
import com.demo.dubbo.proxy.invoker.NettyInvoker;
import com.demo.dubbo.proxy.invoker.RmiInvoker;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

@SuppressWarnings("rawtypes")
public class Reference implements Serializable, InitializingBean, FactoryBean, ApplicationContextAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4246711238027965181L;
	private String id;
	private String interfaceName;
	private String protocol;
	private String loadbalance;

	private ApplicationContext context;

	private List<String> registryInfo = new ArrayList<String>();
	private static Map<String, Invoker> invokers = new HashMap<String, Invoker>();
	private static Map<String, DubboLoadBalance> loadBalances = new HashMap<String, DubboLoadBalance>();

	static {
		invokers.put("http", new HttpInvoker());
		invokers.put("rmi", new RmiInvoker());
		invokers.put("netty", new NettyInvoker());

		loadBalances.put("random", new RandomLoadBalance());
		loadBalances.put("roundrob", new RoundRobinLoadBalance());
	}

	public List<String> getRegistryInfo() {
		return registryInfo;
	}

	public Map<String, DubboLoadBalance> getLoadBalances() {
		return this.loadBalances;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getLoadbalance() {
		return loadbalance;
	}

	public void setLoadbalance(String loadbalance) {
		this.loadbalance = loadbalance;
	}

	/**
	 * 拿到一个Bean实例，这个方法是Spring调用的，Spring初始化的时候调用，具体是getBean()方法里面调用
	 * 
	 * ApplicationContext.getBean(String id)
	 * 
	 * getObject()方法的返回值，交由Spring 容器管理
	 * 
	 * Reference类中的getObject()方法返回的是interfaceName这个接口的代理
	 */
	@Override
	public Object getObject() throws Exception {
		System.out.println("返回interfaceName的代理对象");
		Invoker invoker = null;
		/**
		 * 如果reference标签中配置了protocol属性，该protocol优先；
		 * 如果reference未配置，以protocol标签中的为准
		 */
		if (!StringUtils.isEmpty(this.protocol)) {
			invoker = invokers.get(this.protocol);
		} else {
			// Protocol标签的实例，在Spring IoC容器中
			Protocol protocolBean = context.getBean(Protocol.class);

			if (null != protocolBean) {
				invoker = invokers.get(protocolBean.getName());
			} else {
				invoker = invokers.get("http");
			}
		}

		return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] { Class.forName(interfaceName) },
				(InvocationHandler) new InvokerInvocationHandler(invoker, this));
	}

	@Override
	public Class getObjectType() {

		if (!StringUtils.isEmpty(interfaceName)) {
			try {
				return Class.forName(interfaceName);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		registryInfo = BaseRegistryDelegate.getRegistryInfo("", context);
	}

}
