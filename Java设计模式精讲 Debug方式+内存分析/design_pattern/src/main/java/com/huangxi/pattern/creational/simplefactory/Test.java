package com.huangxi.pattern.creational.simplefactory;

/**
 * Created by geely
 */
public class Test {
    public static void main(String[] args) {

//        Video javaVideo = new JavaVideo();
//        javaVideo.produce();

//        VideoFactory videoFactory = new VideoFactory();
//        Video video = videoFactory.getVideo("java");
//        if(video == null){
//            return;
//        }
//        video.produce();

        VideoFactory videoFactory = new VideoFactory();
        Video video = videoFactory.getVideo(JavaVideo.class);
        if(video == null){
            return;
        }
        video.produce();



    }

}
