package com.huangxi.singleton;

/**
 * @author huang.luo.jun
 * @description 懒汉模式-- 静态内部类
 *  最优的处理方式，兼顾饿汉单例模式的内存浪费问题  以及 synchronized的性能问题
 * @date 2020-11-27
 */
public class LazyInnerClassSingleton {

    // 使用 LazyInnerClassSingleton 的时候，默认是不加载内部类的
    // 如果没有使用，则不会加载内部类
    private LazyInnerClassSingleton(){
        //针对反射破坏单例情况
        if(LazyHolder.LAZY!=null){
            throw new RuntimeException("不允许创建多实例");
        }
    }

    public static final LazyInnerClassSingleton getInstance(){
        //返回结果之前，一定会加载内部类的 LazyHolder
        return LazyHolder.LAZY;
    }

    private static class LazyHolder{
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
