package com.demo.dubbo.proxy.advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.demo.dubbo.common.label.Reference;
import com.demo.dubbo.proxy.invoker.Invoker;

/**
 * InvokerInvovationHandler，这是一个Adivce（通知），在这个advice里面进行了rpc调用
 * rpc：http、rmi、netty
 * 
 * @author ancle
 *
 */
public class InvokerInvocationHandler implements InvocationHandler {

	private Invoker invoker;
	private Reference ref;

	public InvokerInvocationHandler(Invoker invoker, Reference ref) {
		this.invoker = invoker;

		this.ref = ref;
	}

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		// 这个InvokerInvocationHandler最终调用多个远程的Provider

		return null;
	}

}
