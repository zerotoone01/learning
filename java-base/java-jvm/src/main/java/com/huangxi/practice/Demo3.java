package com.huangxi.practice;

/**
 * @author huang.luo.jun
 * @description Full GC
 * @date 2020-12-21
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520
 *  -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 * //-XX:NewSize=10m -XX:MaxNewSize=10m -XX:InitialHeapSize=20m -XX:MaxHeapSize=20m -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 *  堆内存： 新生代【Eden:8m, Survivor1:1m, Survivor2:1m】， 老年代【10m】
 *
 * 关键参数： -XX:PretenureSizeThreshold=3145728， 设置大对象阈值为3MB，也就是超过3MB，就直接进入老年代。
 */
public class Demo3 {
    public static void main(String[] args) {
        //这行代码直接分配了一个4MB的大对象，此时这个对象会直接进入老年代，接着array1不再引用这个对象
        byte[] array1 = new byte[4*1024*1024];
        array1 = null;

        //连续分配了4个数组，其中3个是2MB的数组，1个是128KB的数组，如下图所示，全部会进入Eden区域中
        byte[] array2 = new byte[2*1024*1024];
        byte[] array3 = new byte[2*1024*1024];
        byte[] array4 = new byte[2*1024*1024];
        byte[] array5 = new byte[128*1024];

        //Eden区已经放不下了, 因此此时会直接触发一次Young GC
        byte[] array6 = new byte[2*1024*1024];

    }
}

/**
 * gc.log 日志
 *
 *Java HotSpot(TM) 64-Bit Server VM (25.191-b12) for windows-amd64 JRE (1.8.0_191-b12), built on Oct  6 2018 09:29:03 by "java_re" with MS VC++ 10.0 (VS2010)
 * Memory: 4k page, physical 33439348k(20227492k free), swap 38420084k(24731664k free)
 * CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=3145728 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
 *
 * //[ParNew (promotion failed): 8128K->8776K(9216K), 0.0026208 secs] -- 回收之后发现一个都回收不掉，因为上述几个数组都被变量引用了
 * // 一定会直接把这些对象放入到老年代里去，但是此时老年代里已经有一个4MB的数组了，已经无法放下3个2MB的数组和1个128KB的数组了，此时需要执行full gc
 * // 0.117: [CMS: 8194K->6756K(10240K), 0.0031633 secs] 12224K->6756K(19456K), [Metaspace: 3193K->3193K(1056768K)], 0.0060336 secs]
 * // 执行了CMS垃圾回收器的Full GC, 会对老年代进行Old GC，同时一般会跟一次Young GC关联，还会触发一次元数据区（永久代）的GC。
 * // 执行Young GC，发现老年代放不下Young GC内容，再执行 Full GC，执行完毕之后再把数据放在老年代中
 * // 问题： Young GC ，Full GC ，数据转移 的具体实现是怎么样的？
 *
 *
 * 0.114: [GC (Allocation Failure) 0.114: [ParNew (promotion failed): 8128K->8776K(9216K), 0.0026208 secs]0.117: [CMS: 8194K->6756K(10240K), 0.0031633 secs] 12224K->6756K(19456K), [Metaspace: 3193K->3193K(1056768K)], 0.0060336 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * Heap
 *  par new generation   total 9216K, used 2505K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   eden space 8192K,  30% used [0x00000000fec00000, 0x00000000fee726e8, 0x00000000ff400000)
 *   from space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
 *   to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
 *  concurrent mark-sweep generation total 10240K, used 6756K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *  Metaspace       used 3214K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 349K, capacity 388K, committed 512K, reserved 1048576K
 */


