package com.huangxi.practice;

/**
 * @author huang.luo.jun
 * @description TODO
 * @date 2020-12-22
 *
 *  -XX:NewSize=104857600 -XX:MaxNewSize=104857600 -XX:InitialHeapSize=209715200 -XX:MaxHeapSize=209715200
 *  -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC
 *  -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 *  堆内存：200M； 年轻代：100M【Eden:80M, Survivor1:10M, Survivor2:10M】；老年代：100M
 */
public class Demo4 {
    public static void main(String[] args) throws Exception{
        Thread.sleep(3000);
        while (true){
            loadData();
        }
    }

    private static void loadData() throws Exception{
        byte[] data = null;
        //模拟每秒50次请求
        for (int i = 0; i < 50; i++) {
            //每次请求分配100KB的数组
            data = new byte[100*1024];
        }
        data = null;
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
 */
