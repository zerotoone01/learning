package com.huangxi.pattern.creational.abstractfactory;

/**
 * 产品族工厂，创建具体的产品
 * article和video构成java课程工厂的产品族
 */
public class JavaCourseFactory implements CourseFactory{
    @Override
    public Video getVideo() {
        return new JavaVideo();
    }

    @Override
    public Article getArticle() {
        return new JavaArticle();
    }
}
