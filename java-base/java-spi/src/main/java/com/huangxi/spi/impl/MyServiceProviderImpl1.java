package com.huangxi.spi.impl;


import com.huangxi.spi.IMyServiceProvider;

/**
 * @description TODO
 * @date 2020-07-31
 */
public class MyServiceProviderImpl1 implements IMyServiceProvider {

    @Override
    public String getName() {
        return "name:ProviderImpl1";
    }

}

