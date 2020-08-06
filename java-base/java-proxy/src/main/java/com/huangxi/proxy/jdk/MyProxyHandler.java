package com.huangxi.proxy.jdk;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 这种方式不需要注入
 * @description TODO
 * @date 2020-08-06
 */
public class MyProxyHandler implements InvocationHandler {



    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy begin...");
        //没有传递实体的时候，就不对实体执行invoke
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        System.out.println("method name: "+method.getName());
        System.out.println("proxy end!");
        return null;
    }
}
