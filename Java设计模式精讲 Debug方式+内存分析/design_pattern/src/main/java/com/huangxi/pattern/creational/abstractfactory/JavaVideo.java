package com.huangxi.pattern.creational.abstractfactory;

/**
 * 具体产品的定义
 * java工厂在生产video
 */
public class JavaVideo extends Video {
    @Override
    public void produce() {
        System.out.println("java视频");
    }
}
