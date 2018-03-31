package com.demo.dubbo.common.registry.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.demo.dubbo.common.label.Protocol;
import com.demo.dubbo.common.label.Registry;
import com.demo.dubbo.common.label.Service;
import com.demo.dubbo.common.registry.BaseRegistry;
import com.demo.dubbo.common.util.RedisApi;

public class RedisRegistry implements BaseRegistry {

	/**
	 * { "id" : { "host:port" : { protocol : "", service: "" }, "host:port" : {
	 * protocol : "", service: "" } } }
	 */
	@Override
	public boolean registry(String param, ApplicationContext context) {
		Protocol protocol = context.getBean(Protocol.class);
		Registry registry = context.getBean(Registry.class);
		Map<String, Service> services = context.getBeansOfType(Service.class);

		RedisApi.createJedisPool(registry.getAddress());

		for (Map.Entry<String, Service> entry : services.entrySet()) {
			if (entry.getValue().getRef().equals(param)) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("protocol", JSONObject.toJSONString(protocol));
				jsonObj.put("service", JSONObject.toJSONString(entry.getValue()));

				JSONObject ipPort = new JSONObject();
				ipPort.put(protocol.getHost() + ":" + protocol.getPort(), jsonObj);

				lpush(param, ipPort);
			}

		}

		return true;
	}

	private void lpush(String key, JSONObject json) {
		if (RedisApi.exists(key)) {
			Set<String> keys = json.keySet();
			String ipPortStr = "";
			for (String sKey : keys) {
				ipPortStr = sKey;
			}

			List<String> registryInfo = RedisApi.lrange(key);
			List<String> newRegistryInfo = new ArrayList<String>();

			boolean isOldData = false;

			for (String node : registryInfo) {
				JSONObject jo = JSONObject.parseObject(node);
				if (jo.containsKey(ipPortStr)) {
					newRegistryInfo.add(json.toJSONString());
					isOldData = true;
				} else {
					newRegistryInfo.add(node);
				}
			}

			if (isOldData) {
				if (newRegistryInfo.size() > 0) {
					RedisApi.del(key);
					String[] newRegitryStr = new String[newRegistryInfo.size()];

					for (int i = 0; i < newRegistryInfo.size(); i++) {
						newRegitryStr[i] = newRegistryInfo.get(i);
					}

					RedisApi.lpush(key, newRegitryStr);
				}
			} else {
				RedisApi.lpush(key, json.toJSONString());
			}

		} else {
			RedisApi.lpush(key, json.toJSONString());
		}
	}

	@Override
	public List<String> getRegistryInfo(String id, ApplicationContext context) {
		Registry registryBean = context.getBean(Registry.class);

		RedisApi.createJedisPool(registryBean.getAddress());

		if (RedisApi.exists(id)) {
			return RedisApi.lrange(id);
		}

		return null;
	}

}
