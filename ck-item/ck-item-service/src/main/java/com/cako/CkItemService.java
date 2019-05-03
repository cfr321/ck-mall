package com.cako;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cako.item.mapper")
public class CkItemService {
    public static void main(String[] args) {
        SpringApplication.run(CkItemService.class, args);
    }
}
