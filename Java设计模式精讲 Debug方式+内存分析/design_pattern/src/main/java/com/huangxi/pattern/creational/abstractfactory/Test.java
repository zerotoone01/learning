package com.huangxi.pattern.creational.abstractfactory;

public class Test {
    public static void main(String[] args) {
        //应用层不用关心每个产品具体怎么生成的， 需要用的时候直接从对应的工厂中提取即可
        //并且对应而言，返回的对象都是顶层的类， 即使换了工厂，对应用的的逻辑代码不会有影响
        CourseFactory courseFactory = new JavaCourseFactory();
        Article article = courseFactory.getArticle();
        Video video = courseFactory.getVideo();

        video.produce();
        article.produce();
    }
}
