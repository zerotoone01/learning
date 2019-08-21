package com.huangxi.pattern.creational.factorymethod;

/**
 *  工厂的子类继承工厂， 然后
 */
public class JavaVideoFactory extends VideoFactory {

    @Override
    public Video getVideo() {
        return new JavaVideo();
    }
}
