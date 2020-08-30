package com.huangxi.proxy.jdk.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkDynamicProxyUtil {
    /**
     *  反射的真正实现， InvocationHandler 只是做了切面相关处理
     *
     *  代理类的真正实现，参考jdk的Proxy
     * @see Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)
     *  Proxy.newProxyInstance() 反射的具体实现，需要去研究一下对应的源码
     * @param targetObject 被代理类
     * @param handler
     * @param <T>
     * @return
     */
    public static <T> T newProxyInstance(T targetObject, InvocationHandler handler){
        //获取类加载器
        ClassLoader classLoader = targetObject.getClass().getClassLoader();
        // 采用jdk代理的类必须要有具体的实现接口，否则会抛异常
        Class<?>[] interfaces = targetObject.getClass().getInterfaces();
        return (T)Proxy.newProxyInstance(classLoader,interfaces,handler);
    }
}
