package com.huangxi.proxy.jdk;

/**
 * @description TODO
 * @date 2020-08-06
 */
public class HelloInterfaceImpl implements HelloInterface {
    @Override
    public void sayHello() {
        System.out.println("hello world");
    }
}
