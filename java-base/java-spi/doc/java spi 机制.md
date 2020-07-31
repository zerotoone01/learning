
## java spi

SPI，全称Service Provider Interfaces，服务提供接口。

JDK中，基于SPI的思想，提供了默认具体的实现，ServiceLoader。利用JDK自带的ServiceLoader，可以轻松实现面向服务的注册与发现，完成服务提供与使用的解耦。

缺点：    
子类必须要继承对应的接口才可以使用。如果服务规定定义provider在项目A中，服务具体实现在项目B中，则B项目必须要引入A项目才能实现，耦合度很高。

思考：   
spi服务可以给第三方提供插拔服务， 那么eureka、doubbo服务注册与发现具体怎么实现的呢？和spi有关系？

参考文档    
[Java SPI机制：ServiceLoader实现原理及应用剖析](https://juejin.im/post/6844903891746684941)    
[Java SPI 的原理与应用](https://crossoverjie.top/2020/02/24/wheel/cicada8-spi/)