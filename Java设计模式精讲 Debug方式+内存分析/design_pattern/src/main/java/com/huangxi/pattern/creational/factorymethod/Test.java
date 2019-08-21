package com.huangxi.pattern.creational.factorymethod;

/**
 * Created by geely
 */
public class Test {
    public static void main(String[] args) {

        VideoFactory videoFactory = new JavaVideoFactory();
        VideoFactory videoFactory1 = new PythonVideoFactory();
        VideoFactory videoFactory2 = new FEVideoFactory();
        Video video = videoFactory.getVideo();
        //video.produce();


    }

}
