package com.huangxi.spi.main;

import com.huangxi.spi.bytecode.BaseJavassist;
import javassist.*;

import java.io.IOException;

/**
 * @author huang.luo.jun
 * @description 强调源代码层次操作字节码的框架Javassist
 * @date 2020-07-13
 * 存在疑问： 这个写入成功后，并com.huangxi.bytecode.BaseJavassist的process方法并没有输出最新的； 需要重启才会输出
 *
 */


public class JavassistApp {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("com.huangxi.bytecode.BaseJavassist");
        CtMethod m = cc.getDeclaredMethod("process");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        Class c = cc.toClass();
//        cc.writeFile("/Users/zen/projects");
//        cc.writeFile("./java-asm/target/classes/com/huangxi/bytecode/BaseJavassist.class");
        //class文件所在位置
        cc.writeFile("./java-asm/target/classes");
        BaseJavassist h = (BaseJavassist)c.newInstance();
        h.process();
    }
}
