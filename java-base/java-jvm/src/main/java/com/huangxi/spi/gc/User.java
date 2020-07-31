package com.huangxi.spi.gc;

public class User {
    //目的是让对象填满内存，观察垃圾回收机制
    private byte[] bs = new byte[10*1024];
    private String userId;

    public User(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "userId='" + userId ;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("now finalize userId="+userId);
    }
}
