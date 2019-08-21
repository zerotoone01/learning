package com.huangxi.pattern.creational.factorymethod;

/**
 *
 *  抽象类： 里面可以有具体的实现，对于某些已知行为在此实现比较合适
 *
 *  工厂： 指定规范，但是不进行具体实现
 */
public abstract class VideoFactory {

    public abstract Video getVideo();

//    public Video getVideo(Class c){
//
//        Video video = null;
//        try {
//            video = (Video)Class.forName(c.getName()).newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return video;
//    }


    /**
     * 简单工厂模式的缺点：  每增加一个类，就的修改工厂代码
     * @param type
     * @return
     */
//    public Video getVideo(String type){
//        if("java".equalsIgnoreCase(type)){
//            return new JavaVideo();
//        }else if("python".equalsIgnoreCase(type)){
//            return new PythonVideo();
//        }
//        return null;
//    }

}
