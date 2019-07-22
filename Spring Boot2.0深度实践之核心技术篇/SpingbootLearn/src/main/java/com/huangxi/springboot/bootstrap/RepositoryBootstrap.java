package com.huangxi.springboot.bootstrap;

import com.huangxi.springboot.repository.MyFirstLevelRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.huangxi.springboot.repository"})
public class RepositoryBootstrap {


    public static void main(String[] args) {

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RepositoryBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);

        //获取指定的bean对象
        MyFirstLevelRepository myFirstLevelRepository = (MyFirstLevelRepository) context.getBean("myFirstLevelRepository");

        System.out.println("myFirstLevelRepository=="+myFirstLevelRepository);
        //关闭上下文
        context.close();

        //SpringApplication.run需要读取默认配置文件， 如果配置文件不在默认位置，启动失败
//        ConfigurableApplicationContext run = SpringApplication.run(RepositoryBootstrap.class, args);
//        MyFirstLevelRepository myFirstLevelRepository22 = run.getBean("myFirstLevelRepository" , MyFirstLevelRepository.class);
//        System.out.println("myFirstLevelRepository22222222=="+myFirstLevelRepository22);
    }
}
