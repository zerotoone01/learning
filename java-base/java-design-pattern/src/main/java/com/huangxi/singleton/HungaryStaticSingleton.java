package com.huangxi.singleton;

import sun.java2d.pipe.SpanClipRenderer;

/**
 * @author huang.luo.jun
 * @description 饿汉模式静态代码块写法
 * @date 2020-11-27
 */
public class HungaryStaticSingleton {
    private static final HungaryStaticSingleton hungaryStaticSingleton;
    static {
        hungaryStaticSingleton = new HungaryStaticSingleton();
    }
    private HungaryStaticSingleton(){}

    public static HungaryStaticSingleton getHungaryStaticSingleton(){
        return hungaryStaticSingleton;
    }
}
