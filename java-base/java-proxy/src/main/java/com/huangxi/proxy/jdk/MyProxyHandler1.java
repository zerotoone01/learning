package com.huangxi.proxy.jdk;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 这种方式必须要注入对应的实体，比较局限
 * @description TODO
 * @date 2020-08-06
 */
public class MyProxyHandler1 implements InvocationHandler {
    private HelloInterface object;
    public MyProxyHandler1(HelloInterface object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy begin...");
        method.invoke(object,args);
        if(object!=null && object.getClass().getName().equals(method.getDeclaringClass().getName())){
            //TODO 都不行
            System.out.println("ok....");
        }
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        System.out.println("proxy end!");
        return null;
    }
}
