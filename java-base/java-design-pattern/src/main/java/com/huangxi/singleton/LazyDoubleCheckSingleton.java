package com.huangxi.singleton;

/**
 * @author huang.luo.jun
 * @description 懒汉模式-- 对比LazySynSingleton  多线程是阻塞并不是基于整个类的，而是造getInstance()方法内部的阻塞
 * @see com.huangxi.singleton.LazySynSingleton
 * @date 2020-11-27
 */
public class LazyDoubleCheckSingleton {
    private volatile static LazyDoubleCheckSingleton lazy = null;
    private LazyDoubleCheckSingleton(){
    }
    public static LazyDoubleCheckSingleton getInstance(){
        if(lazy==null){
            synchronized (LazyDoubleCheckSingleton.class){
                lazy = new LazyDoubleCheckSingleton();
            }
        }
        return lazy;
    }
}
