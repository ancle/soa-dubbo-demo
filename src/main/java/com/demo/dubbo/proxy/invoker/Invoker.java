package com.demo.dubbo.proxy.invoker;

/**
 * 返回JSON String进行数据通信
 * 
 * @author ancle
 *
 */
public interface Invoker {
	public String invoke(Invocation invocation);
}
