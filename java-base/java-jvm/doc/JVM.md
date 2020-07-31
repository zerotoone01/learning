

|  GC算法类型  | 算法说明  | 具体的GC |
|  ----  | ----  | ----  |
| 标记-复制  | 内存划分为两半，一半为空，空间浪费 | Serial, ParNew, Parallel Scavenge|
| 标记-整理  | 效率较低 | Serial Old, Parallel Old |
| 标记-清除  | 效率高；碎片多 | CMS, |

垃圾回收类型

|  GC类型  | GC类型说明  | 具体的GC |
|  ----  | ----  | ----  |
| MinorGC/YoungGC  | 年轻代 | Serial, ParNew, Parallel Scavenge|
| MajorGC/OldGC  | 老年代 | Serial Old, CMS, Parallel Old |
| MixedGC  | 整个新生代以及部分老年代 | G1 |
| FullGC  | 收集整个Java堆和方法区的GC | -- |

ParNew VS Parallel Scavenge:   
ParNew（以及大部分的GC）关注点是尽可能缩短垃圾收集时用户线程停顿时间，Parallel Scavenge收集器的目标则是达到一个可控制的吞吐量（Throughput）  
吞吐量 = 运行用户代码时间 /（运行用户代码时间 + 垃圾收集时间），虚拟机总共运行了100分钟，其中垃圾收集花掉1分钟，那吞吐量就是99%

JVM的理解，一定要区分规范和具体实现，不能将二者混淆。

方法区（JVM的一种规范）：
方法区是分代设计的扩展，设计之初的目的是可以用同一个垃圾管理器管理类似Java堆空间一样，避免单独开发一个垃圾管理器
方法区： 这区域的内存回收目标主要是针对常量池的回收和对类型的卸载， 一般来说这个区域的回收效果比较难令人满意， 尤其是类型的卸载， 条件相当苛刻， 但是这部分区域的回收有时又确实是必要的。
jdk8+: 已经把永久代(Permanent Generation)的部分内容放在元数据空间(Meta space)，部分放在堆中。
1.那么方法区还有没有？如果存在，其作用是什么？  2.方法区和元数据空间的关系？ 3.元数据空间如何管理？
解释：1.方法区是一种规范，永久代或者元数据空间是规范的具体实现形式
[Java方法区、永久代、元空间 究竟是什么关系呢？](https://blog.csdn.net/weixin_42740530/article/details/105288701)
## HotSpot虚拟机
### chap2 对象的创建
1.空间分配： 指针碰撞（Bump The Pointer），空闲列表（Free List）  
>空间分配线程安全问题： 每个线程在Java堆中预先分配一小块内存， 称为本地线程分配缓冲（Thread Local Allocation Buffer， TLAB） ， 哪个线程要分配内存， 就在那个线程的本地缓冲区中分配， 只有本地缓冲区用完了， 分配新的缓存区时才需要同步锁定。 虚拟机是否使用TLAB， 可以通过-XX： +/-UseTLAB参数来设定。  

2.对象信息设置，例如对象是哪个类的实例、 如何才能找到类的元数据信息、 对象的哈希码、 对象的GC分代年龄等信息，存在在对象头（Object Header）中   
3.执行class文件的<init>()，至此，对象被构建出来

### 对象的内存布局
在HotSpot虚拟机里， 对象在堆内存中的存储布局可以划分为三个部分： 对象头（Header） 、 实例数据（Instance Data） 和对齐填充（Padding） 。

### 对象的访问定位
主流的访问方式主要有使用句柄和直接指针两种


## chap3 垃圾收集器与内存分配策略
垃圾收集需要完成的三件事情:  
·哪些内存需要回收？
·什么时候回收？
·如何回收

引用计数（Reference Counting）算法
可达性分析（Reachability Analysis）算法


回收方法区
方法区的垃圾收集主要回收两部分内容： 废弃的常量和不再使用的类。   
安全点： 
安全点位置的选取基本上是以“是否具有让程序长时间执行的特征”为标准进行选定的。 <这个特征的具体选定标准是什么？具体实现原理？> 
OopMap只存放安全点位置的指令数据，并不是每条指令都存放  

程序在安全点停顿方案： 抢先式中断（Preemptive Suspension） 和主动式中断（Voluntary Suspension） 

安全区域：   
GC回收时候，不会去处理这一区域的对象，一般正在执行线程任务会进入安全区域
离开安全区域方式： 主动检查根节点枚举是否完成，如果完成，则线程可以离开安全区域继续执行；否则一直等待可以离开的信号。（具体怎么实现，先主动再被动？？？）

记忆集与卡表
记忆集是一种用于记录从非收集区域指向收集区域的指针集合的抽象数据结构。 用于解决减少GC Roots扫描的范围。
HotSpot中记忆集的具体实现是采用卡表（Card Table）来实现：
字节数组CARD_TABLE的每一个元素都对应着其标识的内存区域中一块特定大小的内存块， 这个内存块被称作“卡页”（Card Page） 。
一个卡页的内存中通常包含不止一个对象， 只要卡页内有一个（或更多） 对象的字段存在着跨代指针， 那就将对应卡表的数组元素的值标识为1， 称为这个元素变脏（Dirty） ， 没有则标识为0。
在垃圾收集发生时， 只要筛选出卡表中变脏的元素， 就能轻易得出哪些卡页内存块中包含跨代指针， 把它们加入GC Roots中一并扫描。

写屏障（Write Barrier）： 分为写前屏障（Pre-Write Barrier）和写后屏障（Post-Write Barrier）  
类似AOP功能，用于维护卡表状态，在G1收集器出现之前， 其他收集器都只用到了写后屏障。   




