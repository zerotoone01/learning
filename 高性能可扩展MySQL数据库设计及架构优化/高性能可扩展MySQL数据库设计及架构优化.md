# 高性能可扩展MySQL数据库设计及架构优化

## 导入课程的sql文件

Navicat界面直接运行sql文件会出错，导入不了数据；
解决方法一：将sql文件内容完全复制，粘贴到Navicat的新建查询中，执行即可---速度比较慢
解决方法二： 用mysql的命令导入， `mysql> source D:\hlj\sql\imooc_20160818.sql`, 执行这一条语句的前提是：1.在mysql的配置文件my.ini中添加`secure_file_priv="D:/hlj/sql"`， 一定是`/`，不能是`\`，对于`\`只能有一层目录，不能多级目录； 2.把需要执行的sql文件放入到secure_file_priv指定的文件中； 3.重启mysql服务，保证配置生效，可以用`mysql>  show variables like '%secure%';` 查看配置是否生效。

## 本文档的实验环境
mysql5.7 
win10

## 第1章 数据库开发规范的制定
俗话说：“没有规矩不成方圆”。这一章，我们就先来制定数据库开发的各种规范，包括：数据库命名规范、数据库基本设计规范、数据库索引设计规范、数据库字段设计规范、SQL开发规范以及数据库操作规范。通过这些规范的制定可以指导并规范我们后续的开发工作，为我们以后的工作提供一个良好的基础。

### 1-1 课程说明
本课程主要是涉及到电商常用功能模块的数据库设计；
电商：注册会员-->展示商品-->加入购物车-->生成订单

涉及常见问题的数据库解决方案；
只包含数据库开发部分，不涉及前后端程序开发；

### 1-2 课程准备
MySql实例，推荐mysql5.7版本；
MySQL图形客户端程序，推荐使用SQLyog，本机使用的Navicat；
Linux命令和shell脚本的基础知识--本课程在Linux系统上进行；
### 1-3 电商项目简介
**项目说明**
![img](./img/01-20190417125325.png)  

![img](./img/01-20190417125531.png)

### 1-4 数据库设计规范简介
1.数据结构设计： 逻辑设计 -->物理设计
2.实际工作中：逻辑设计+物理设计
3.物理设计：表名   字段名  字段类型

**数据库设计规范**
数据库命名规范
数据库基本设计规范
数据库索引设计规范
数据库字段设计规范
数据库SQL开发规范
数据库操作规行为范

### 1-5 数据库命名规范
1.所有数据库对应名称必须使用小写字母并用下划线分割； 

  mysql数据库中，对大小写敏感；在Linux系统中，mysql存储的就是一些文件，Linux系统本身对大小写敏感；  

2.所有数据库对象名称禁止使用MySQL保留关键字；

  含有`from`关键字的sql：`select id, username, from, age from tb_user;` ，这将导致mysql执行出现错误。  
  如果原有表中含有关键字字段，在查询的时候需要将关键字加上引号：`select id, username, 'from', age from tb_user;`
  mysql关键字查询网址`https://dev.mysql.com/doc/refman/5.7/en/keywords.html`

3.数据库对象的命名要能做到见名知意，并且最好不要超过32个字符；

4.临时库或表必须以tmp为前缀并且以日期为后缀；
5.备份库或表，必须以bak为前缀并以日期为后缀；
6.所有存储相同数据的列名和列类型必须一致；  
  ![](./img/01-20190417204613.png)
  ```
  CREATE TABLE `customer_inf` (
  `customer_inf_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT 'customer_login表的自增ID',
  .........,
  PRIMARY KEY (`customer_inf_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

CREATE TABLE `order_master` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT '下单人ID',
  `shipping_user` varchar(10) NOT NULL COMMENT '收货人姓名',
  .........
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `ux_ordersn` (`order_sn`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='订单主表';
  
  ```
  例子中的`customer_id`在两个表中的名字和类型都是一样的，这对于数据的查询性能很重要，通常这种id会相互关联使用，如果两个表关联字段的类型不同，数据库就会进行隐式转换，就会造成列上的索引失效，进而导致查询效率大幅度降低。

### 1-6 数据库基础设计规范
1.一般情况下，所有表必须使用Innodb存储引擎；
```
在mysql5.5以及之前版本默认使用的Myisam存储引擎，5.6以后的版本默认使用Innodb，Innodb支持事务、行级锁、更好的恢复性能、高并发下性能更好；  
```
2.数据库和表的字符集统一使用UTF-8
  统一字符集可以避免由于字符集转换产生乱码， 避免字符转换后可能导致索引失效；
  MySQL中UTF-8字符集汉字占3个字节，ASCII码占用1个字节，比如采用varchar(255)来存储中文字符，实际会占用255*3个字节；

3.所有的表和字段都需要添加注释
  目的是： 在数据库建表时，就维护好数据库字典。
4.尽量控制单表数据量的大小，建议控制在500万以内
  500万并不是MySQL数据库的限制，但是太大对于修改表结构、备份、恢复都会有很大的问题。
  mysql最多可以存储多少条数据呢？MySQL本身没有设置限制，这种限制主要取决于存储设备和文件系统。
  控制单表数据大小的方式：历史数据归档、分库分表等手段来控制数量的大小。

5.谨慎使用MySQL分区表
  分区表在物理上表现为多个文件，在逻辑上表现为一个表
  谨慎选择分区键，跨分区查询效率可能更低
  建议采用物理分表的方式来管理大数据

6.尽量做到冷热数据分离，减小表的宽度
  MySQL限制最多存储4096列，并且每一行的字节数不能超过65535字节的，目的：
​    减少磁盘IO，保证热数据的内存缓存命中率
​    更有效利用缓存，避免读入无用的冷数据
     经常使用的列放在一个表中
     
7.禁止在表中建立预留字段
  预留字段的名字很难做到见名知意
  预留字段无法确定存储的数据类型,所以无法选择合适的类型
  对预留字段类型的修改,会对全表进行锁定,严重影响数据库的并发性

8.禁止在数据库中存储图片,文件等二进制数据
  一般数据库只存储图片、文件等数据在文件服务器中的地址即可
9.禁止在线上做数据库压力测试
10.禁止从开发环境、测试环境直接连生产环境数据库

### 1-7 数据库索引设计规范
1.限制每张表上的索引数量，建议单张表索引不超过5个
  索引并不是越多越好，索引可以提高查询效率同样也可以降低效率
  索引可以增加查询效率，但同样也会降低插入和更新的效率

  MySQL5.6之前查询只会用到一个索引，之后的版本会联合索引查询，但是效率比以前稍微低一些
  Innodb是一种逻辑组织表--即数据存储的逻辑顺序和索引的顺序是相同，Innodb是按照哪个索引的顺序来组织表的呢？答案是：主键。因此要求每个Innodb表必须有一个主键，如果表中没有主键，MySQL会选择第一个非空唯一索引来做主键，如果表中没有非空唯一索引的话，mysql会自动生成36字节的主键，这个自动生成的主键性能并不是最好的，所以在建表一定要给指定一个主键。

  主键的一些建议：
  ```
  不使用更新频繁的列作为主键，不适用多列联合主键（联合索引）；
  不使用UUID、MD5、HASH、字符串列作为主键 --为了保证索引的顺序，后面插入的比前面小，就需要把前面插入大于新值的全部移动到新值的后面，会造成大量的IO和CPU损耗；
  主键建议使用自动id值；
  ```
2.常见索引列建议
  select、update、delete语句的where从句中的列，包含在order by、group by、distinct中的字段，通常在这两种情况下建立联合索引更好；
  多表join的关联列；

3.如何选择索引列的顺序
  索引是从左到右顺序来使用的，因此把区分度最高的列放在联合索引的最左侧（区分度：索引中唯一值的个数除以总行数的值，值越大，区分度越高）；
  在区分度不大的情况下，尽量把字段长度小的列放在联合索引的最左侧；
  以上两点都差多不多的情况下，使用最频繁的列放到联合索引的左侧；

4.避免建立冗余索引和重复索引
  重复索引--primary key(id)、index(id)、unique index(id)， MySQL在主键上会自动创建一个非空唯一索引；
  冗余索引--index(a,b,c)、index(a,b)、 index(a)  

5.对于频繁的查询优先考虑使用覆盖索引
  覆盖索引：包含了所有查询字段的索引
  避免Innodb表进行索引的二次查找
  可以把随机IO变为顺序IO加快查询效率

6.尽量避免使用外键
  不建议使用外键约束，但是一定在表与表之间的关联上建立索引
  外键可用于保证数据的参照完整性，但建议在业务端实现
  外键会影响父表和子表的写操作从而降低性能



### 1-8 数据库字段设计规范
**索引设计规范的总结**：
每个Innodb表都要有一个主键；
限制表上索引的数量，避免建立重复和冗余索引；
注意合理选择复合索引键值的顺序；

**数据库字段设计规范：**
优选选择符合存储需要的最小的数据类型;
```
1.将字符串转换为数字类型存储，比如将IP地址转换成数字存储：  INET_ATON('255.255.255.255')=42949672995；   
INET_NTOA(42949672995)='255.255.255.255' ;

2.对于非负数据采用无符号整型进行存储：
SIGNED INT -2147483648-2147483647
UNSIGNED INT 0-4294967295

3.VARCHAR(N) 中的N代表的是字符数，而不是字节数；
4.使用UTF8存储汉字Varchar(255)=765个字节；
5.过大的长度会消耗更多的内存；
```
避免使用TEXT、BLOB数据类型；
```
text的种类：TinyText，Text，MidumText, LongText;
Text可以存储下64K的数据；
MySQL的 内存表不支持Text或者BLOB这样的大数据类型，如果查询并排序中有这种类型， 排序中就不能使用内存表来进行排序，需要使用磁盘表来排序；
对于这类数据，mysql在读取数据时候，需要进行二次查询，导致sql的性能很差；
建议把BLOB或者是TEXT列分离到单独的扩展表中，并且在查询的时候不能用 select * 查询， 需要什么字段，取对应的字段即可；
TEXT或BLOB类型只能使用前缀索引，并text列上是不能有默认值的；
```

避免使用ENUM数据类型
```
修改ENUM值需要使用ALTER语句；
ENUM类型的ORDER BY操作效率低，需要额外操作；
禁止使用数值作为ENUM的枚举值；枚举本身是由索引控制，？
```
尽可能把所有定义为NOT NULL
```
索引NULL需要额外的空间来保存，所以要占用更多的空间；
进行比较和计算时要对NULL值做特别的处理；

```
使用TIMESTAMP或者DATETIME来存储时间
```
采用字符串存储日期类型的数据（不正确做法）：
缺点1.无法用日期函数进行计算和比较；
缺点2.用字符串存储日期要占用更多的空间；


TIMESTAMP时间范围： 1970-01-01 00:00:01 ~2038-01-19 03:14:07
TIMESTAMP占用4字节和INT相同，但比INT可读性高；
超出TIMESTAMP取值范围的，则使用DATETIME类型；
```
同财务相关的金额类数据，必须使用decimal类型
```
1.非精准浮点类型：float ， double
2.精准数据类型：decimal

Decimal类型为精准浮点数，在计算时不会丢失精度；
占用空间由定义的宽度决定；
用户存储比bigint更大的整数数据；
```



### 1-9 SQL开发规范

1.建议使用预编译语句进行数据库操作

```
预编译语句可以重复的实行sql执行计划，减少sql编译所需要的时间；
预编译语句可以有效的防止动态sql所带来的sql注入的问题；

例子：
mysql>PREPARE stmt1
-> FROM 'SELECT SQRT(POW(?,2)+POW(?,2)) as hypotenuser';
mysql>SET @a=3;
mysql>SET @a=4;
-- 执行
mysql>EXECUTE stmt1 USING @a,@b;
-- 释放
mysql>DEALLOCATE PREPARE stmt1;

优点：
只传参数，比传递sql语句更高效；
相同语句可以一次解析，多次使用，提高处理效率；
可以有效防止sql注入
```
避免数据类型的隐式转换
```
缺点：
隐式转换会导致索引失效，比如参数类型和列类型不一致的时候，就会导致隐式转换；
select name, phone, from customer where id='111'; 
原本id是int类型， 但是传递进了的过字符串类型，就会产生隐式转换

```
充分利用表上已存在的索引
```
合理利用存在索引，而不是盲目增加索引
避免使用双%号的查询条件，比如 a like '%123%', 如果只是后置百分号，查询时候可以利用到索引，但是前置的不行；

一个sql只能利用到复合索引中的一列进行范围查询；
使用left join或 not exists来优化not in操作，not in经常导致索引失效；
```
程序连接不同的数据库使用不同的账号，禁止垮库查询
```
为数据库迁移和分库分表留出余地；
降低业务耦合度；
避免权限过大而产生的安全风险；
```

禁止使用 select * 必须使用 select <字段列表> 查询
```
消耗更多的CPU和IO以及网络宽带资源；
无法使用索引覆盖索引（一个表中，不可能一个索引覆盖所有列）；
可以减少表结构变更带来的影响；
```
禁止使用不含字段列表的INSERT语句
```
错误：insert into values('a',d'','v');
正确： insert into t(c1,c2,c3) values('a','v','c');
可减少表结构的变更对已有的数据带来的影响
```

避免使用子查询，可以把子查询优化为join操作
```
子查询的结果集无法使用索引；
子查询会产生临时操作表，如果子查询数量大则严重影响效率 --消耗过多的CPU以及IO资源；
```
避免使用JOIN关联太多的表
```
每join一个表会多占用一部分内存（join_buffer_size可以设置大小）；
会产生临时操作表，影响查询效率--临时表没有索引，查询速度慢；
mysql最多允许关联61个表，建议不超过5个；
```
减少同数据库的交互次数
```
数据更适合处理批量操作；
合并多个相同的操作到一起，可以提高处理效率；
```
使用 in 代替 or
```
in的值不要超过500个；
in 操作可以有效的利用到索引，而or不行；
```
禁止使用order by rand()进行随机排序
```
会把表中所有符合条件的数据装载到内存中进行排序；
如果数据量大，会消耗大量的CPU和IO以及内存资源；

推荐在程序中获取一个随机值，然后从数据库中获取数据；
```
WHERE从句中禁止对列进行函数转换和计算
```
对列进行函数转换或计算会导致无法使用索引， 比如 where date(createtime)='20190901'；
改为： where createtime>='20190901' and createtime<'20190902';

```
在明显不会有重复值时使用UNION ALL，而不是UNION
```
UNION会把所有的数据放到临时表中后再进行去重操作， 如果表数据量大会消耗大量的CPU、IO、内存，导致性能急剧下降；
UNION ALL 不会再对结果集进行去重操作；

```
拆分复杂的大SQL为多个小SQL
```
目前，MySQL一个SQL只能使用一个CPU进行计算；
SQL拆分后可以通过并行执行来提高执行效率；
```


### 1-10 数据库操作规范

超过100万行的批量写操作，要分批多次进行操作
```
主从环境中： 大批量操作可能会造成严重的主从延迟；
binlog日志为row格式时会产生大量的日志，row格式日志会记录每一行数据的修改，一次修改大批量数据，产生的日志量就会越多，日志传输和恢复的时间就越长，容易造成主从延迟；
避免产生大事务操作，大事务可能造成数据库访问阻塞，导致其他应用无法访问数据库；
```

对于大表的操作使用 pt-online-schema-change修改表结构
```
对大表数据结构的修改一定要谨慎，会造成严重的锁表操作。尤其在生产环境中是不能忍受的
pt-online-schema-change 实现过程： 1.复制和原表一样的表结构， 2.将原表的数据和需要修改的数据在新表中操作，3.新表操作完成，产生一个锁，删除原表，把新表的名字改为原表名

```
禁止为程序使用的账号赋予super权限
```
当达到最大连接数限制时，mysql还允许1个有super权限的用户连接；
super权限只能留个DBA处理问题的账号使用

```
对于程序连接数据库账号，遵循权限最小原则
```
程序使用数据库账号只能在一个DB下使用，不准垮库；
程序使用账号原则上不允许有drop权限；
```


### 第2章 电商实例数据库结构设计
 在数据库开发规范的基础之上，如何更好的利用规范设计出易于维护和伸缩性良好的数据库结构，是我们的学习目的。这一章我们根据常用电商项目需求实例，来进行具体的数据库结构的设计。在这一章中我们可以学到，什么是数据库设计的第三范式，如何对需求中所涉及的各个模块遵循数据库开发规范的要求，进行数据库的物理设计和逻...
### 2-1 电商项目用户模块


```
CREATE TABLE `customer_inf` (
  `customer_inf_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT 'customer_login表的自增ID',
  `customer_name` varchar(20) NOT NULL COMMENT '用户真实姓名',
  `identity_card_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '证件类型：1 身份证,2军官证,3护照',
  `identity_card_no` varchar(20) DEFAULT NULL COMMENT '证件号码',
  `mobile_phone` int(10) unsigned DEFAULT NULL COMMENT '手机号',
  `customer_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `user_point` int(11) NOT NULL DEFAULT '0' COMMENT '用户积分',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `birthday` datetime DEFAULT NULL COMMENT '会员生日',
  `customer_level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '会员级别:1普通会员,2青铜会员,3白银会员,4黄金会员,5钻石会员',
  `user_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '用户余额',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`customer_inf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
```
### 2-2 Hash分区表
### 2-3 Range分区
### 2-4 List分区
### 2-5 项目分区表演示
### 2-6 商品模块
### 2-7 订单模块
### 2-8 DB规划

### 第3章 MySQL执行计划（explain）分析
MySQL执行计划可以告诉我们MySQL如何处理我们所提交的查询，通过对执行计划的分析，我们可以了解到MySQL如何使用表中的索引，如何从存储引擎中获取数据等。在这一章里我们会详细的学习MySQL执行计划的具体内容，通过对这一章的学习，相信大家可以更好完成对查询的优化。...
### 3-1 常见业务处理
### 3-2 执行计划分析
### 3-3 如何优化分页查询示例
### 3-4 如何删除重复数据示例
### 3-5 如何进行分区间数据统计示例
### 3-6 捕获有问题的SQL-慢查日志
### 第4章 MySQL数据库备份和恢复
对于任何数据库来说，数据库备份和恢复是最为重要的内容，可以说数据库备份决定了数据库的安全。所以在这一章中咱们就来看看常用的MySQL数据库的备份和恢复方式，包括如何使用mysqldump进行数据库的全备和部分备份，如何使用xtrabackup对数据库进行全备和增量备份，以及相应的恢复方法，如何使用binlog对数据库进行时间点的...
### 4-1 数据库备份
### 4-2 mysqldump全备介绍
### 4-3 mysqldump全备单库实例
### 4-4 mysqldump全备所有库和所有表实例
### 4-5 mysqldump全备Where及脚本备份
### 4-6 mysqldump恢复
### 4-7 mysqldump恢复实例
### 4-8 mysqldump恢复单表实例
### 4-9 指定时点的恢复
### 4-10 指定时点的Binlog恢复
### 4-11 实时binlog备份
### 4-12 xtrabackup备份和恢复
### 4-13 Mysql备份计划

### 第5章 高性能高可用MySQL架构变迁
告别数据库的裸奔时代，对架构进行步步升级。这是我们本章要学习的重点内容。我们首先会从实例学习MySQL主从复制架构，详解主从分离的多种解决方案。通过keepalived+LVS完美组合，一步步打造高性能可扩展的数据库架构；通过数据库中间件MaxScale学习，讲解另类解决高可用的读负载均衡的问题
### 5-1 mysql主从复制配置
### 5-2 mysql主从复制演示
### 5-3 基于GTID的复制链路
### 5-4 高可用keepalived实例
### 5-5 Mysql数据库读写分离
### 5-6 使用LVS解决读负载均衡
### 5-7 使用maxscale数据库中间件解决读负载均衡
### 5-8 使用DB业务拆分解决写压力大问题
### 5-9 课程总结和思考
