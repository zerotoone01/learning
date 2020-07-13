package com.huangxi.bytecode;

public class ByteCodeDemo {
    private static final String name = "xiaoming";

    private int age;

    public ByteCodeDemo(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    public void setAge(){
        this.age = age;
    }
    public static void main(String[] args) {
        ByteCodeDemo byteCodeDeomo = new ByteCodeDemo(12);
        System.out.println("name:" + name + "age:" + byteCodeDeomo.getAge());
    }
}

