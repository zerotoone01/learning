package com.huangxi.singleton;

/**
 * @author huang.luo.jun
 * @description 懒汉模式--方法同步锁
 * 线程安全，用synchronized加锁后，在线程数量比较多的情况下，耗费CPU资源，CPU资源不充足情况下，容易导致大批线程阻塞
 * @date 2020-11-27
 */
public class LazySynSingleton {
    private LazySynSingleton(){}
    private static LazySynSingleton lazy = null;
    public synchronized static LazySynSingleton getInstance(){
        if(lazy==null){
            lazy = new LazySynSingleton();
        }
        return lazy;
    }
}
