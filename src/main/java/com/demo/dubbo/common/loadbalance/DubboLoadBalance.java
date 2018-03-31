package com.demo.dubbo.common.loadbalance;

import java.util.List;

public interface DubboLoadBalance {
	NodeInfo doSelect(List<String> registryInfos);
}
