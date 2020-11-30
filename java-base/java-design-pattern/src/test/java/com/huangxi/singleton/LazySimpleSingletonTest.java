package com.huangxi.singleton;

/**
 * @author huang.luo.jun
 * @description TODO
 * @date 2020-11-27
 */
public class LazySimpleSingletonTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(new ExecutorThread());
        Thread t2 = new Thread(new ExecutorThread());
        /**
         * idea 线程debug模式下， 断点位置在 getSingleton 方法内部
         * @see com.huangxi.singleton.LazySimpleSingleton.getSingleton
         *
         * 通过切换不同的线程，并观察其内存状态，两个线程都处于running状态，LazySimpleSingleton被实例化了两次
         *  有时候得到的运行结果可能是相同的两个对象，实际上是被后面执行的线程覆盖了，看到的是一个假象
         */

        t1.start();
        t2.start();
        System.out.println(">>>>>>>>>>end");
    }
}
