package com.huangxi.spi.base;

public class MyTimeLoger {
    private static long a1=0;
    public static void start(){
        a1 = System.currentTimeMillis();
    }
    public static void end(){
        long a2 = System.currentTimeMillis();
        System.out.println("now invoke method use time=="+(a2-a1));
    }
}
