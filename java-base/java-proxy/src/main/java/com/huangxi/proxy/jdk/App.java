package com.huangxi.proxy.jdk;

import com.huangxi.proxy.jdk.dynamic.AspectInvocationHandler;
import com.huangxi.proxy.jdk.dynamic.JdkDynamicProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @description TODO
 * @date 2020-08-06
 */
public class App {

    public static void main1(String[] args) {
        //静态代理
        MyProxyHandler proxyHandler = new MyProxyHandler();
//        HelloInterface helloInterface = (HelloInterface) Proxy.newProxyInstance(HelloInterfaceImpl.class.getClassLoader(), new Class[]{HelloInterface.class}, proxyHandler);
        HelloInterface helloInterface = (HelloInterface) Proxy.newProxyInstance(HelloInterfaceImpl.class.getClassLoader(), new Class[]{HelloInterface.class}, proxyHandler);
        helloInterface.sayHello();

        MyProxyHandler1 proxyHandler1 = new MyProxyHandler1(new HelloInterfaceImpl());
        HelloInterface helloInterface1 = (HelloInterface) Proxy.newProxyInstance(HelloInterface.class.getClassLoader(), new Class<?>[]{HelloInterface.class}, proxyHandler1);
        helloInterface1.sayHello();
    }

    public static void main(String[] args) {
        //采用jdk 动态代理方式实现切面
        //优势：
        //1.切面逻辑可以复用，不需要再单独去实现具体的子类；
        //2.对目标方法的包装，统一调用newProxyInstance来获取
        HelloInterface helloInterface1 = new HelloInterfaceImpl();
        InvocationHandler invocationHandler = new AspectInvocationHandler(helloInterface1);
        HelloInterface hello1 = JdkDynamicProxyUtil.newProxyInstance(helloInterface1, invocationHandler);
        hello1.sayHello();

        HelloInterface helloInterface2 = new HelloInterfaceImpl2();
        InvocationHandler invocationHandler2 = new AspectInvocationHandler(helloInterface2);
        HelloInterface hello2 = JdkDynamicProxyUtil.newProxyInstance(helloInterface2, invocationHandler2);
        hello2.sayHello();

    }
}
