package com.huangxi.reflect;

import com.huangxi.reflect.model.Grade;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangxi
 * @description https://www.cnblogs.com/qingguang/p/4316940.html
 * @date 2020-09-23
 */
public class ListReflect {

    public static void main(String[] args) throws Exception{
        Grade grade = new Grade();
        Field f = Grade.class.getDeclaredField("name");
        f.setAccessible(true);
        f.set(grade,"三年级");

        Field field = Grade.class.getDeclaredField("students");
        //list类型判断
        if(List.class.isAssignableFrom(field.getType())){
            List arraylist=new ArrayList();

            Type genericType = field.getGenericType();
            //这样判断type 是不是参数化类型。 如Collection<String>就是一个参数化类型。
            if(genericType instanceof ParameterizedType){
                //获取类型的类型参数类型。  你可以去查看jdk帮助文档对ParameterizedType的解释。
                Class clazz=(Class)((ParameterizedType) genericType).getActualTypeArguments()[0];
                Object obj=clazz.newInstance();
                Field ff=clazz.getDeclaredField("numberId");
                ff.setAccessible(true);
                ff.set(obj, "1001010");
                arraylist.add(obj);
            }
            field.setAccessible(true);
            field.set(grade, arraylist);
        }
        System.out.println(grade);
    }
}
