package com.huangxi.proxy.cglib.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 *
 *
 */
public class CglibUtil {
    /**
     *  创建代理
     * @param targetObject
     * @param methodInterceptor
     * @param <T>
     * @return
     */
    public static <T> T createProxy(T targetObject, MethodInterceptor methodInterceptor){
        return (T)Enhancer.create(targetObject.getClass(), methodInterceptor);
    }
}
