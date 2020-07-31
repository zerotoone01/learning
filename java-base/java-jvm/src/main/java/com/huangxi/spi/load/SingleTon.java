package com.huangxi.spi.load;



public class SingleTon {
    static {
        System.out.println(" in singleTon");
    }
    private static SingleTon singleTon = new SingleTon();
    public static int count1;
    public static int count2 = 0;

    private SingleTon() {
        count1++;
        count2++;
    }

    public static SingleTon getInstance() {
        return singleTon;
    }



}


