package com.huangxi.main;

import org.objectweb.asm.*;

import java.io.FileInputStream;

/**
 * @author huang.luo.jun
 * @description https://juejin.im/post/5b549bcbe51d45169c1c8b66
 * @date 2020-07-13
 */
public class App {

    /**
     *  通过asm获取字节码的信息
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("current dir: "+System.getProperty("user.dir"));
        FileInputStream fileInputStream = new FileInputStream("java-asm/target/classes/com/huangxi/bytecode/ByteCodeDemo.class");
        ClassReader classReader = new ClassReader(fileInputStream);
        ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        //Java8选择ASM5
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                System.out.println("field: " + name);
                return super.visitField(access, name, desc, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.println("method: " + name);
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        };
        //忽略调试信息
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);

    }

}
