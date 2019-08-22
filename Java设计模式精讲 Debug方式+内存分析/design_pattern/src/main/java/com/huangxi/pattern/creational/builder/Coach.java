package com.huangxi.pattern.creational.builder;

import com.huangxi.pattern.creational.builder.Course;
import com.huangxi.pattern.creational.builder.CourseBuilder;

/**
 *  教练，有的课程需要教练
 */
public class Coach {
    private CourseBuilder courseBuilder;
    public void setCourseBuilder(CourseBuilder courseBuilder){
        this.courseBuilder = courseBuilder;
    }

    /**
     * 讲师制造课程
     * @param courseName
     * @param coursePPT
     * @param courseVideo
     * @param courseArticle
     * @param courseQA
     * @return
     */
    public Course makeCourse(String courseName,String coursePPT,
                             String courseVideo,String courseArticle,
                             String courseQA){
        this.courseBuilder.buildCourseName(courseName);
        this.courseBuilder.buildCoursePPT(coursePPT);
        this.courseBuilder.buildCourseVideo(courseVideo);
        this.courseBuilder.buildCourseArticle(courseArticle);
        this.courseBuilder.buildCourseQA(courseQA);
        return this.courseBuilder.makeCourse();
    }

}
