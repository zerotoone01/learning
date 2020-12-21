package com.huangxi.practice;

/**
 * @author huang.luo.jun
 * @description gc 进入老年代： 1.年龄设置 2.survivor区放不下gc之后的数据
 * @date 2020-12-21
 *
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520
 *  -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 *
 *
 * //-XX:NewSize=10m -XX:MaxNewSize=10m -XX:InitialHeapSize=20m -XX:MaxHeapSize=20m -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * 堆内存： 新生代【Eden:8m, Survivor1:1m, Survivor2:1m】， 老年代【10m】
 *
 * 通过“-XX:MaxTenuringThreshold=15”设置了，只要对象年龄达到15岁才会直接进入老年代
 * 实际上：根据动态年龄判定规则：年龄1+年龄2+年龄n的对象总大小超过了Survivor区域的50%，年龄n以上的对象进入老年代。
 */
public class Demo2 {
    public static void main(String[] args) {
//        gcEdgeSet();
        gcSurvivorCannotSave();

    }
    //gc 年龄代设置
    public static void gcEdgeSet(){
        byte[] array1 = new byte[2*1024*1024];
        array1 = new byte[2*1024*1024];
        array1 = new byte[2*1024*1024];
        array1 = null;

        byte[] array2 = new byte[128*1024];

        //Eden区总共就4MB大小，而且里面已经放入了3个2MB的数组了，所以剩余空间不足2M，
        // 此时你放一个2MB的数组是放不下的，所以这个时候就会触发年轻代的Young GC
        byte[] array3 = new byte[2*1024*1024];

        array3 = new byte[2*1024*1024];
        array3 = new byte[2*1024*1024];
        array3 = null;

        byte[] array4 = new byte[2*1024*1024];
    }

    //gc之后，survivor空间不足，存活的对象直接进入老年代
    public static void gcSurvivorCannotSave(){
        byte[] array1 = new byte[2*1024*1024];
        array1 = new byte[2*1024*1024];
        array1 = new byte[2*1024*1024];

        byte[] array2 = new byte[128*1024];
        array2 = null;

        byte[] array3 = new byte[2*1024*1024];

    }

}

/**
 * gc.log 日志 【gcEdgeSet()】
 *
 * Java HotSpot(TM) 64-Bit Server VM (25.191-b12) for windows-amd64 JRE (1.8.0_191-b12), built on Oct  6 2018 09:29:03 by "java_re" with MS VC++ 10.0 (VS2010)
 * Memory: 4k page, physical 33439348k(20101084k free), swap 38420084k(23981696k free)
 * CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
 * 0.112: [GC (Allocation Failure) 0.112: [ParNew: 8128K->644K(9216K), 0.0006547 secs] 8128K->644K(19456K), 0.0008279 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 0.113: [GC (Allocation Failure) 0.113: [ParNew: 7074K->128K(9216K), 0.0027838 secs] 7074K->754K(19456K), 0.0028505 secs] [Times: user=0.09 sys=0.02, real=0.00 secs]
 * Heap
 *  par new generation   total 9216K, used 2341K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   eden space 8192K,  27% used [0x00000000fec00000, 0x00000000fee29780, 0x00000000ff400000)
 *   from space 1024K,  12% used [0x00000000ff400000, 0x00000000ff420010, 0x00000000ff500000)
 *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
 *  concurrent mark-sweep generation total 10240K, used 626K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *  Metaspace       used 3209K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 348K, capacity 388K, committed 512K, reserved 1048576K
 *
 *
 *
 * gcSurvivorCannotSave gc日志：
 * Java HotSpot(TM) 64-Bit Server VM (25.191-b12) for windows-amd64 JRE (1.8.0_191-b12), built on Oct  6 2018 09:29:03 by "java_re" with MS VC++ 10.0 (VS2010)
 * Memory: 4k page, physical 33439348k(20052184k free), swap 38420084k(24496688k free)
 * CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
 * 0.120: [GC (Allocation Failure) 0.121: [ParNew: 8128K->652K(9216K), 0.0017555 secs] 8128K->2702K(19456K), 0.0019098 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * Heap
 *  par new generation   total 9216K, used 3151K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   eden space 8192K,  30% used [0x00000000fec00000, 0x00000000fee70c38, 0x00000000ff400000)
 *   from space 1024K,  63% used [0x00000000ff500000, 0x00000000ff5a3160, 0x00000000ff600000)
 *   to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
 *  concurrent mark-sweep generation total 10240K, used 2050K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *  Metaspace       used 3192K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 345K, capacity 388K, committed 512K, reserved 1048576K
 */


