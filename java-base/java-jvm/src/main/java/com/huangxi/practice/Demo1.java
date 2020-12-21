package com.huangxi.practice;

/**
 * @author huang.luo.jun
 * @description TODO
 * @date 2020-12-21
 *
 * -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760
 * -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760
 * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * -Xloggc:gc.log
 *
 * 堆内存： 新生代【Eden:4m, Survivor1:0.5m, Survivor2:0.5m】， 老年代【5m】
 */
public class Demo1 {
    public static void main(String[] args) {
        byte[] array1 = new byte[1024*1024];
        array1 = new byte[1024*1024];
        array1 = new byte[1024*1024];
        array1 = null;

        //Eden区总共就4MB大小，而且里面已经放入了3个1MB的数组了，所以剩余空间只有1MB了，
        // 此时你放一个2MB的数组是放不下的，所以这个时候就会触发年轻代的Young GC
        byte[] array2 = new byte[2*1024*1024];
    }
}

/**
 * gc.log 日志
 *
 * Java HotSpot(TM) 64-Bit Server VM (25.191-b12) for windows-amd64 JRE (1.8.0_191-b12), built on Oct  6 2018 09:29:03 by "java_re" with MS VC++ 10.0 (VS2010)
 * Memory: 4k page, physical 33439348k(20333808k free), swap 38420084k(24792292k free)
 * CommandLine flags: -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=5242880 -XX:NewSize=5242880 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
 *
 * //Eden区内存不够，导致 Allocation Failure，进而出发 Young GC，ParNew垃圾回收器执行GC的
 * // 0.114 --这个意思就是你的系统运行以后过了多少秒发生了本次GC
 * // [ParNew: 3877K->512K(4608K), 0.0023601 secs -- 4608k为4.5m，是Eden+1个Survivor的大小；gc耗时为0.0023601秒
 * //     3877K约为3.8m，我们只指定了3m的数据，多的这些是jvm存储还会附带一些其他信息，所以每个数组实际占用的内存是大于1m的
 * // 3877K->1713K(9728K), 0.0025568 secs -- 是整个Java堆内存是总可用空间9728KB（9.5MB），年轻代4.5MB+老年代5M，然后GC前整个Java堆内存里使用了4030KB，GC之后Java堆内存使用了574KB。
 * // [Times: user=0.00 sys=0.00, real=0.00 secs] -- 本次gc消耗的时间
 *
 * 0.114: [GC (Allocation Failure) 0.114: [ParNew: 3877K->512K(4608K), 0.0023601 secs] 3877K->1713K(9728K), 0.0025568 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *
 * //堆日志
 * // 这段日志是在JVM退出的时候打印出来的当前堆内存的使用情况
 *
 * Heap
 *  par new generation   total 4608K, used 3734K [0x00000000ff600000, 0x00000000ffb00000, 0x00000000ffb00000)
 *   eden space 4096K,  78% used [0x00000000ff600000, 0x00000000ff925ab8, 0x00000000ffa00000)
 *   from space 512K, 100% used [0x00000000ffa80000, 0x00000000ffb00000, 0x00000000ffb00000)
 *   to   space 512K,   0% used [0x00000000ffa00000, 0x00000000ffa00000, 0x00000000ffa80000)
 *  concurrent mark-sweep generation total 5120K, used 1201K [0x00000000ffb00000, 0x0000000100000000, 0x0000000100000000)
 *  Metaspace       used 3166K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 343K, capacity 388K, committed 512K, reserved 1048576K
 */


