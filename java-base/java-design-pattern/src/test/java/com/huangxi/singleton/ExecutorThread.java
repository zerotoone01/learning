package com.huangxi.singleton;

/**
 * @author huang.luo.jun
 * @description TODO
 * @date 2020-11-27
 */
public class ExecutorThread implements Runnable{

    @Override
    public void run() {
        /**
         * idea 线程debug模式下， 断点位置在 getSingleton 方法内部
         * @see com.huangxi.singleton.LazySimpleSingleton.getSingleton
         *
         * 通过切换不同的线程，并观察其内存状态，两个线程都处于running状态，LazySimpleSingleton被实例化了两次
         *  有时候得到的运行结果可能是相同的两个对象，实际上是被后面执行的线程覆盖了，看到的是一个假象
         */
//        LazySimpleSingleton singleton = LazySimpleSingleton.getSingleton();

        /**
         * idea 线程debug模式下， 断点位置在 getSingleton 方法内部
         * 通过切换不同的线程，并观察其内存状态，一个线程都处于running状态，其他线程处于Monitor状态
         */
//        LazySynSingleton singleton = LazySynSingleton.getInstance();

        LazyDoubleCheckSingleton singleton = LazyDoubleCheckSingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+":"+singleton);
    }
}
