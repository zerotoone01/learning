package com.huangxi.proxy.jdk.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 用于处理被代理对象的横切逻辑
 *
 */
public class AspectInvocationHandler implements InvocationHandler {

    private Object targetObject;

    public AspectInvocationHandler(Object targetObject){
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(targetObject,args);
        after();
        return result;
    }
    private void before(){
        System.out.println("before--方法被调用之前的切面逻辑--InvocationHandler");
    }
    private void after(){
        System.out.println("after--方法被调用之后的切面逻辑--InvocationHandler");
    }
}
