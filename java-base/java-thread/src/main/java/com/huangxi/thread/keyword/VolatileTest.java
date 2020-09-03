package com.huangxi.thread.keyword;

/**
 * @date 2020-09-03
 * volatile 变量自增运算测试
 * 结果： 每次运行程序， 输出的结果都不一样， 都是一个小于200000的数字
 * 原因分析：
 * 通过反编译的字节码文件可以看出： 当getstatic指令把race的值取到操作栈顶时， volatile关键字保证了race的值在此时是正确的，
 * 但是在执行iconst_1、 iadd这些指令的时候， 其他线程可能已经把race的值改变了， 而操作栈顶的值就变成了过期的数据，
 * 所以 putstatic 指令执行后就可能把较小的race值同步回主内存之中。
 *
 * 将class反编译的内容输出到text文件： javap -v '.\VolatileTest.class' >> VolatileTest.txt
 *
 * HSDIS是一个被官方推荐的HotSpot虚拟机即时编译代码的反汇编插件
 *
 * ##########
 * 1.volatile变量只能保证可见性
 * 要保证变量的原子性，仍然需要加锁(使用synchronized、 java.util.concurrent中的锁或原子类）
 *
 * 2.volatile 禁止指令重排序
 * ##########
 *
 */
public class VolatileTest {

    public static volatile int race=0;
    private static final int THREADS_COUNT = 20;

    public static void increase(){
        race++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        increase();
                        System.out.println(">>>>>>>>>> "+j);
                    }
                }
            });
            threads[i].start();
            System.out.println("thread>>>>"+i);
        }
        System.out.println("thread start all");
        // 等待所有累加线程都结束
        Thread.sleep(10000);
//        while (Thread.activeCount()>1){
//            Thread.yield();
//        }
        System.out.println(race);
    }

}

//volatile的使用场景
//下面代码 这类场景中就很适合使用volatile变量来控制并发， 当shutdown()方法被调用时， 能保证所有线程中执行的doWork()方法都立即停下来。
//
//volatile boolean shutdownRequested;
//public void shutdown() {
//    shutdownRequested = true;
//}
//public void doWork() {
//    while (!shutdownRequested) {
//        // 代码的业务逻辑
//    }
//}