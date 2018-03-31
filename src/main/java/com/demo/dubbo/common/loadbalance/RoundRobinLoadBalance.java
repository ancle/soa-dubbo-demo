package com.demo.dubbo.common.loadbalance;

import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class RoundRobinLoadBalance implements DubboLoadBalance {

	private static Integer index = 0;

	@Override
	public NodeInfo doSelect(List<String> registryInfos) {
		synchronized (index) {
			if (index > registryInfos.size()) {
				index = 0;
			}

			String registryInfo = registryInfos.get(index);
			index++;

			JSONObject json = JSONObject.parseObject(registryInfo);
			Collections values = (Collections) json.values();

			JSONObject node = new JSONObject();
			for (Object value : values) {
				node = JSONObject.parseObject(value.toString());
			}

			JSONObject protocol = node.getJSONObject("protocol");
			NodeInfo nodeInfo = new NodeInfo();

			nodeInfo.setHost(protocol.get("host") != null ? protocol.getString("host") : "");
			nodeInfo.setHost(protocol.get("port") != null ? protocol.getString("port") : "");
			nodeInfo.setHost(protocol.get("contextPath") != null ? protocol.getString("contextPath") : "");

			return nodeInfo;

		}

		return null;
	}

}
