package com.huangxi.pattern.creational.abstractfactory;

/**
 * python工厂在生产article
 */
public class PythonArticle extends Article {
    @Override
    public void produce() {
        System.out.println("python article");
    }
}
