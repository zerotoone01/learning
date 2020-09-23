package com.huangxi.thread.pool;

import java.util.concurrent.*;

/**
 * @description
 * Java线程池实现原理及其在美团业务中的实践 https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
 * 动态修改线程池 https://www.cnblogs.com/thisiswhy/p/12690630.html
 *
 *
 * org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor#setCorePoolSize  This setting can be modified at runtime, for example through JMX
 *
 * //TODO 反射修改线程池 阻塞队列大小 LinkedBlockingQueue
 * https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
 * https://www.cnblogs.com/noKing/p/9038234.html
 * java反射全解 https://juejin.im/post/6844903905483030536
 *  https://www.cnblogs.com/qingguang/p/4316940.html
 *  http://tutorials.jenkov.com/java-reflection/dynamic-class-loading-reloading.html
 * @date 2020-09-22
 */
public class ThreadChangeDemo {

    public static void main(String[] args) throws Exception{
        dynamicModifyExecutor();
    }

    /**
     * 自定义线程池
     * @return
     */
    private static ThreadPoolExecutor buildThreadPoolExecutor(){
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10));
    }

    /**
     * 提交任务给线程池，并修改线程池参数
     * @throws InterruptedException
     */
    private static void dynamicModifyExecutor() throws InterruptedException{
        ThreadPoolExecutor executor = buildThreadPoolExecutor();
        for (int i = 0; i < 15; i++) {
            executor.submit(()->{
                threadPoolStatus(executor,"创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolStatus(executor,"改变之前");
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        threadPoolStatus(executor,"改变之后");
        Thread.currentThread().join();
    }

    /**
     * 打印线程池状态
     * @param executor
     * @param name
     */
    private static void threadPoolStatus(ThreadPoolExecutor executor, String name){
        LinkedBlockingQueue queue = (LinkedBlockingQueue) executor.getQueue();
        System.out.println(Thread.currentThread().getName()+"-"+name+"-:"+
                "核心线程数: "+executor.getCorePoolSize()+
                " 活动线程数："+executor.getActiveCount()+
                " 最大线程数："+executor.getMaximumPoolSize()+
                " 线程池活跃度："+divide(executor.getActiveCount(),executor.getMaximumPoolSize()+executor.getQueue().size())+
                " 任务完成数："+executor.getCompletedTaskCount()+"" +
                " 队列大小："+(queue.size()+queue.remainingCapacity())+
                " 当前排队线程数："+queue.size()+
                " 队列剩余大小："+queue.remainingCapacity()+
                " 队列使用度："+divide(queue.size(),queue.size()+queue.remainingCapacity()));
    }

    /**
     * 保留两位小数
     * @param num1
     * @param num2
     * @return
     */
    private static String divide(int num1, int num2){
        return String.format("%1.2f%%",Double.parseDouble(num1+"")/Double.parseDouble(num2+"")*100);
    }
}
