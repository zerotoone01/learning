package com.huangxi.pattern.creational.abstractfactory;

/**
 *  java工厂在生产article产品
 *
 */
public class JavaArticle extends Article {
    @Override
    public void produce() {
        System.out.println("java article");
    }
}
