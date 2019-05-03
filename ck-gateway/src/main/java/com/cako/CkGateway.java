package com.cako;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication
@EnableZuulProxy
public class CkGateway {
    public static void main(String[] args){
        SpringApplication.run(CkGateway.class,args);
    }
}
