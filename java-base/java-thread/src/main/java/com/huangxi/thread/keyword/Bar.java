package com.huangxi.thread.keyword;

/**
 *
 *  https://cloud.tencent.com/developer/article/1082675
 *  如何优雅的查看 Java 代码的汇编码 https://juejin.im/post/6844904038580879367
 *  https://github.com/doexit/hsdis.dll
 *  -XX:+UnlockDiagnosticVMOptions
 *      -XX:+PrintAssembly
 *      -Xcomp
 *      -XX:CompileCommand=dontinline,*Bar.sum
 *      -XX:CompileCommand=compileonly,*Bar.sum
 *
 *  hsdis-amd64.dll  放到JRE_HOME/bin/server路径下
 */
public class Bar {
    int a=1;
    static int b=2;
    public int sum(int c){
        return a+b+c;
    }

    public static void main(String[] args){
        new Bar().sum(3);
    }
}
