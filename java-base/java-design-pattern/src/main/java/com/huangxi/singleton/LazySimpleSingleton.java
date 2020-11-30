package com.huangxi.singleton;


/**
 * @author huang.luo.jun
 * @description 懒汉模式--多线程存在线程不安全
 * @date 2020-11-27
 */
public class LazySimpleSingleton {
    private static LazySimpleSingleton lazySimpleSingleton;
    private LazySimpleSingleton(){}
    public static LazySimpleSingleton getSingleton(){
        if(lazySimpleSingleton==null){
            lazySimpleSingleton = new LazySimpleSingleton();
            //验证多线程情境下，生成的相同的对象，是被覆盖还是只是同一个对象
            //验证结果： 如果不加set属性，两个线程生成的单例为同一个对象的几率很大， 但是加了改属性后，每次生成的对象都不一样
//            lazySimpleSingleton.setName(Thread.currentThread().getName());
        }
        return lazySimpleSingleton;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
