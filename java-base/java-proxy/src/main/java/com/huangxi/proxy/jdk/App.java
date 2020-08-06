package com.huangxi.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @description TODO
 * @date 2020-08-06
 */
public class App {

    public static void main(String[] args) {
        MyProxyHandler proxyHandler = new MyProxyHandler();
//        HelloInterface helloInterface = (HelloInterface) Proxy.newProxyInstance(HelloInterfaceImpl.class.getClassLoader(), new Class[]{HelloInterface.class}, proxyHandler);
        HelloInterface helloInterface = (HelloInterface) Proxy.newProxyInstance(HelloInterfaceImpl.class.getClassLoader(), new Class[]{HelloInterface.class}, proxyHandler);
        helloInterface.sayHello();

        MyProxyHandler1 proxyHandler1 = new MyProxyHandler1(new HelloInterfaceImpl());
        HelloInterface helloInterface1 = (HelloInterface) Proxy.newProxyInstance(HelloInterface.class.getClassLoader(), new Class<?>[]{HelloInterface.class}, proxyHandler1);
        helloInterface1.sayHello();
    }
}
