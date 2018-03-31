package com.demo.dubbo.common.loadbalance;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class RandomLoadBalance implements DubboLoadBalance {

	@Override
	public NodeInfo doSelect(List<String> registryInfos) {
		Random random = new Random();
		int index = random.nextInt(registryInfos.size());

		String registryInfoJson = registryInfos.get(index);

		if (!StringUtils.isEmpty(registryInfoJson)) {
			JSONObject json = JSONObject.parseObject(registryInfoJson);
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
