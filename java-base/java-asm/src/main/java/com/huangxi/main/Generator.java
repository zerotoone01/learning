package com.huangxi.main;

import com.huangxi.bytecode.MyClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 *  字节码增强器， 给 com/huangxi/bytecode/Base 已编译的Base.class字节码， 采用MyClassVisitor来改变已有的字节码
 *   https://tech.meituan.com/2019/09/05/java-bytecode-enhancement.html
 */
public class Generator {
    public static void main(String[] args) throws Exception {
        //读取 已加载类
        ClassReader classReader = new ClassReader("com/huangxi/bytecode/Base");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //处理
        ClassVisitor classVisitor = new MyClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();
        //输出
        File f = new File("java-asm/target/classes/com/huangxi/bytecode/Base.class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(data);
        fout.close();
        System.out.println("now generator cc success!!!!!");
    }
}
