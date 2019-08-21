package com.huangxi.pattern.creational.abstractfactory;

/**
 * 产品族工厂
 * article和video构成python课程工厂的产品族
 */
public class PythonCourseFactory  implements CourseFactory{
    @Override
    public Video getVideo() {
        return new PythonVideo();
    }

    @Override
    public Article getArticle() {
        return new PythonArticle();
    }
}
