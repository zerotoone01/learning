package com.huangxi.proxy;

import com.huangxi.proxy.cglib.AspectMethodInterceptor;
import com.huangxi.proxy.cglib.impl.CommonOperation;
import com.huangxi.proxy.cglib.util.CglibUtil;
import com.huangxi.proxy.jdk.*;
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

    public static void main2(String[] args) {
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

    public static void main(String[] args) {
//        CommonOperation commonOperation = new CommonOperation();
//        AspectInvocationHandler aspectInvocationHandler = new AspectInvocationHandler(commonOperation);
//        CommonOperation jdkProxy = JdkDynamicProxyUtil.newProxyInstance(commonOperation, aspectInvocationHandler);
//        jdkProxy.pay();
        //Exception in thread "main" java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to com.huangxi.proxy.cglib.impl.CommonOperation
        //	at com.huangxi.proxy.App.main(App.java:50)
        //说明： jdk 动态代理，其被代理的类一定要有具体实现的接口（CommonOperation没有实现任何接口），否则会抛异常

        // cglib 代理
        CommonOperation commonOperation = new CommonOperation();
        AspectMethodInterceptor aspectMethodInterceptor = new AspectMethodInterceptor();
        CommonOperation commonOperationProxy = CglibUtil.createProxy(commonOperation, aspectMethodInterceptor);
        commonOperationProxy.pay();
    }
}
