package com.huangxi.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 拦截器实现 横切逻辑， 其底层是ASM字节码操作框架
 *
 */
public class AspectMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
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
