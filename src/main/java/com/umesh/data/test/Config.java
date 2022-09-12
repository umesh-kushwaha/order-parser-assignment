package com.umesh.data.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ukushwaha
 */
@Configuration
public class Config {

    @Bean
    public ExecutorService fixedThreadPoolExecutor(){
        return Executors.newFixedThreadPool(10);
    }
}
