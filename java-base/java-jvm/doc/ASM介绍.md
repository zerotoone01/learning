# ASM 

## 基本概述
>1.ASM是一个Java字节码操作框架，被用来动态生成类火灾增强既有类的功能
>2.ASM可以直接产生二进制class文件
>3.可以在类被加载入虚拟机之前动态改变类行为，从类文件中读入信息后，分析类信息和改变类行为，甚至能根据要求生成新类

提供了ASMMifier工具来帮助开发，可以使用ASMifier工具生成ASM结构来比对   
idea中，可以安装插件 ASM Bytecode Outline / ASM Bytecode Viewer 来处理ASM   
ASM相类似的有Javassist / Javaagent
## ASM编程模型

### CoreAPI
基于事件形式的编程模型。不需要将整个类结构一次性读取到内存中，因此处理更快，所需内存少，但编程方式难度大；

* CoreAPI中操纵字节码的功能基于ClassVisitor接口。这个接口中的每个方法对应了class文件中的每一项
* ASM提供了是三个基于ClassVisitor接口的类来实现class文件的生成和转换
>1.ClassReader: 解析一个类的字节码
>2.ClassAdapter: 是ClassVisitor的实现类，实现要变化的功能
>3.ClassWriter: 是ClassVisitor的实现类，可以用来数据变化后的字节码

### TreeAPI
提供了基于树形的编程模型。该模型需要一次性将一个类的完整结构全部读取到内存中去，因此需要更多的内存，但是编程方式比较简单；



