package com.huangxi.singleton;

/**
 * @author huang.luo.jun
 * @description 饿汉模式，在类加载的时候就已经创建单例对象，线程绝对安全
 * 优点：没有任何加锁、执行效率比较高
 * 缺点：如果没有被使用，存在内存浪费
 * @date 2020-11-27
 */
public class HungrySingleton {
    //先静态，后动态；先属性、后方法
    private static final HungrySingleton hungrySingleton = new HungrySingleton();
    private HungrySingleton(){}
    public static HungrySingleton getHungrySingleton(){
        return hungrySingleton;
    }
}
