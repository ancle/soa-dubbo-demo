package com.demo.dubbo.proxy.invoker;

import java.util.List;

import com.demo.dubbo.common.label.Reference;
import com.demo.dubbo.common.loadbalance.DubboLoadBalance;
import com.demo.dubbo.common.loadbalance.NodeInfo;

public class HttpInvoker implements Invoker {

	@Override
	public String invoke(Invocation invocation) {

		Reference reference = invocation.getReference();
		List<String> registryInfo = reference.getRegistryInfo();

		String loadbalance = reference.getLoadbalance();

		DubboLoadBalance loadBalanceBean = reference.getLoadBalances().get(loadbalance);

		NodeInfo node = loadBalanceBean.doSelect(registryInfo)
		return null;
	}

}
