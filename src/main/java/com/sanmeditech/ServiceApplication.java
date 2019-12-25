package com.sanmeditech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created by lwq on 2019/11/25.
 */
@EnableAutoConfiguration
@SpringBootApplication
public class ServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(ServiceApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ServiceApplication.class);
    }
}
