package com.huangxi.singleton.destroy;

import com.huangxi.singleton.LazyInnerClassSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author huang.luo.jun
 * @description TODO
 * @date 2020-11-27
 */
public class LazyInnerClassSingletonTest {
    public static void main(String[] args) {

        try {
            Class<?> clazz = LazyInnerClassSingleton.class;
            //通过反射获取所有的私有方法
            Constructor<?> c = clazz.getDeclaredConstructor(null);
            c.setAccessible(true);
            Object o1 = c.newInstance();
            Object o2 = c.newInstance();
            System.out.println(o1==o2);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
