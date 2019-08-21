package com.huangxi.pattern.creational.abstractfactory;

/**
 * python工厂在生产video
 */
public class PythonVideo extends Video {
    @Override
    public void produce() {
        System.out.println("python video");
    }
}
