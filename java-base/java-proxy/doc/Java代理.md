# Java代理

## JDK动态代理

## CGLIB(Code Generation Library--代码生成库)动态代理
>不要求被代理类实现接口
>内部主要封装了ASM Java字节码操控框架
>动态生成子类以覆盖非 final 的方法，绑定钩子回调自定义拦截器
CGLIB 并不是 jdk 自带，需要单独引入依赖（cglib）


net.sf.cglib.proxy.Enhancer /net.sf.cglib.proxy.MethodInterceptor

## jdk proxy VS cglib proxy
>JDK动态代理：基于反射机制实现，要求业务类必须实现接口
>CGLIB：基于ASM机制来实现，生成业务类的子类作为代理类
>JDK的优势：1.JDK原生支持；2.平滑支持JDK版本的升级；
>CGLIB的优势：1.被代理对象无需实现接口，能实现代理类的无侵入；
>性能上：JDK动态代理经过多次优化，已经接近甚至超过CGLIB

## Spring AOP 底层机制：
>JDK 和 CGLIB 动态代理共存
>默认策略：Bean实现了接口则用JDK，否则使用CGLIB