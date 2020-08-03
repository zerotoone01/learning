package com.huangxi.bytecode;

import java.lang.management.ManagementFactory;

/**
 * @author huang.luo.jun
 * @description TODO
 * @date 2020-07-13
 */
public class BaseJavassist {
    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String s = name.split("@")[0];
        //打印当前Pid
        System.out.println("pid:"+s);
        while (true) {
            try {
                Thread.sleep(5000L);
            } catch (Exception e) {
                break;
            }
            process();
        }
    }

    public static void process() {
        System.out.println("process");
    }
}
