package com.huangxi.practice;

/**
 * @author huang.luo.jun
 * @description 每日白衣数据量的实时分析引擎，如何定位和解决频繁的Full GC问题？
 *
 * 每分钟执行100次数据计算任务，每次是1万条数据需要计算10秒钟，
 * 每条数据平均包含20个字段，可认为平均每条数据在1KB左右，每次计算1W条就对应10M的大小
 * 假设此时已经执行完成了80个任务，还有20个在执行共200M左右的数据，那么此时可以被回收的数据约800M，还存活的对象为200M
 *
 * @date 2020-12-22
 *
 *  -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=209715200 -XX:MaxHeapSize=209715200
 *  -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 -XX:+UseParNewGC
 *  -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 *  堆内存：200M； 年轻代：100M【Eden:80M, Survivor1:10M, Survivor2:10M】；老年代：100M； 大对象阈值：20M
 *
 *
 */
public class Demo5 {
    public static void main(String[] args) throws Exception{
        Thread.sleep(3000);
        while (true){
            loadData();
        }
    }

    private static void loadData() throws Exception{
        byte[] data = null;
        //模拟每秒4次请求
        for (int i = 0; i < 4; i++) {
            //每次请求分配10MB的数组
            data = new byte[10*1024*1024];
        }
        data = null;
        byte[] data1 = new byte[10*1024*1024];
        byte[] data2 = new byte[10*1024*1024];

        byte[] data3 = new byte[10*1024*1024];
        data3 = new byte[10*1024*1024];

        Thread.sleep(1000);
    }
}

/**
 * 调试 jstat -gc 命令，查看GC相关统计
 *
 * 1.执行该程序，获取PID
 * 2.执行jstat命令： jstat -gc PID 1000 1000 #每隔1秒钟打印一次，连续打印1000次
 * 3.观察GC的相关指标
 *
 *
 * 分析：
 * 上面的程序每一秒会产生80M左右的数据，会发生一次Young GC，Survivor区存不下GC之后的数据，直接存入老年代
 * 每次Young GC后进入老年的数据大小约为30M， 老年代总共存储空间为100M，几乎每3秒发生一次Full GC。
 *
 * 调优分析：
 *  每次Young GC存活的对象太多，频繁进入老年代，导致Full GC发生频繁，
 *  因此调大年轻代的内存空间，增加Survivor的内存即可
 *
 * 参数调优：
 * -XX:NewSize=209715200 -XX:MaxNewSize=209715200 -XX:InitialHeapSize=314572800 -XX:MaxHeapSize=314572800
 * -XX:SurvivorRatio=2 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 * 堆内存：300M； 年轻代：200M【Eden:100M, Survivor1:50M, Survivor2:50M】；老年代：100M； 大对象阈值：20M
 *
 *
 */
