package com.huangxi.spi.load;

/**
 * https://juejin.im/post/5a810b0e5188257a5c606a85
 * 被动引用
 * 1、子类调用父类的静态变量，子类不会被初始化。只有父类被初始化。。对于静态字段，只有直接定义这个字段的类才会被初始化.
 *
 * 2、通过数组定义来引用类，不会触发类的初始化
 *
 * 3、 访问类的常量，不会初始化类
 *
 * 类初始化顺序
 * 1.类从顶至底的顺序初始化，所以声明在顶部的字段的早于底部的字段初始化
 * 2.超类早于子类和衍生类的初始化
 * 3.如果类的初始化是由于访问静态域而触发，那么只有声明静态域的类才被初始化，而不会触发超类的初始化或者子类的4.初始化即使静态域被子类或子接口或者它的实现类所引用。
 * 5.接口初始化不会导致父接口的初始化。
 * 6.静态域的初始化是在类的静态初始化期间，非静态域的初始化时在类的实例创建期间。这意味这静态域初始化在非静态域之前。
 * 7.非静态域通过构造器初始化，子类在做任何初始化之前构造器会隐含地调用父类的构造器，他保证了非静态或实例变量（父类）初始化早于子类
 *
 */

class SuperClass {
    static {
        System.out.println("superclass init");
    }
    public static int value = 123;
}

class SubClass extends SuperClass {
    static {
        System.out.println("subclass init");
    }
    public static int value1 = 1234;
}


class ConstClass {
    static {
        System.out.println("ConstClass init");
    }
    public static final String HELLOWORLD = "hello world";
}

public class LoadTest1 {
    public static void main(String[] args) {
        System.out.println(SubClass.value);// 被动应用1
//        System.out.println(SubClass.value1);// 被动应用1
        SubClass[] sca = new SubClass[10];// 被动引用2
        System.out.println(ConstClass.HELLOWORLD);// 调用类常量
    }

}
