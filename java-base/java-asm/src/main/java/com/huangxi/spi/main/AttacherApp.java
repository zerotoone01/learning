package com.huangxi.spi.main;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * @description 利用Attach API，将我们打包好的agent jar包Attach到指定的JVM pid上
 *              com.huangxi.agent.TestAgent
 * @date 2020-07-14
 */
public class AttacherApp {
    public static void main(String[] args) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException {

        System.out.println(System.getProperty("user.dir"));
        // 传入目标 JVM pid  com.huangxi.bytecode.BaseJavassist
        VirtualMachine vm = VirtualMachine.attach("3004");
        // maven构建好的jar拷贝到resource文件夹下
        vm.loadAgent("./java-asm/src/main/resources/java-asm-1.0-SNAPSHOT.jar");
    }
}
