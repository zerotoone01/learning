package com.huangxi.gc;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

public class ReferenceType {
    // 垃圾回收后，存放被回收的对象在rq中
    private static ReferenceQueue<User> rq = new ReferenceQueue<User>();

    private static void printQueue(String sr){
        Reference<? extends User> obj = rq.poll();
        if(obj!=null){
            // the gc obj reference==soft/weak =null 表示对象已经被GC回收
            System.out.println("the gc obj reference=="+sr+" ="+obj.get());
        }
    }

    private static void testSoftReference() throws InterruptedException {
        List<SoftReference<User>> list = new ArrayList<SoftReference<User>>();
        for (int i = 0; i <10 ; i++) {
            SoftReference<User> sr = new SoftReference<User>(new User("soft "+i),rq);
            System.out.println("now the soft user="+sr.get());
            list.add(sr);
        }
        System.gc();
        Thread.sleep(1000L);
        printQueue("soft");
    }

    private static void testWeakReference() throws InterruptedException {
        List<WeakReference<User>> list = new ArrayList<WeakReference<User>>();
        for (int i = 0; i <10 ; i++) {
            WeakReference<User> sr = new WeakReference<User>(new User("weak "+i),rq);
            System.out.println("now the weak user="+sr.get());
            list.add(sr);
        }
        System.gc();
        Thread.sleep(1000L);
        printQueue("weak");
    }

    private static void testPhantomReference() throws InterruptedException {
        List<PhantomReference<User>> list = new ArrayList<PhantomReference<User>>();
        for (int i = 0; i <10 ; i++) {
            PhantomReference<User> sr = new PhantomReference<User>(new User("phantom "+i),rq);
            System.out.println("now the phantom user="+sr.get());
            list.add(sr);
        }
        System.gc();
        Thread.sleep(1000L);
        printQueue("phantom");
    }

    public static void main(String[] args) throws InterruptedException {
        // jdk8 jvm参数： -XX:+UseConcMarkSweepGC -Xmx2m -Xms2m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+PrintGCCause
//        testSoftReference();
        // user的bs为 10*1024 的时候，soft不会发生GC，而weak会发生GC
//        testWeakReference();
        testPhantomReference();

    }
}
