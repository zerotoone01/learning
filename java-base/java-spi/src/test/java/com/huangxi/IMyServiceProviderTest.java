package com.huangxi;

import com.huangxi.spi.IMyServiceProvider;
import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @description TODO
 * @date 2020-07-31
 */

public class IMyServiceProviderTest {

    @Test
    public void ServiceLoaderTest(){
        //spi加载服务，然后可以直接调用子类对应的具体实现
        //src\main\resources\META-INF\services\com.huangxi.spi.IMyServiceProvider  ServiceLoader就是通过这个文件去发现具体的实现
        ServiceLoader<IMyServiceProvider> serviceLoader = ServiceLoader.load(IMyServiceProvider.class);

        Iterator iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            IMyServiceProvider item = (IMyServiceProvider)iterator.next();
            System.out.println(item.getName() +  ": " +  item.hashCode());
        }
    }

}
