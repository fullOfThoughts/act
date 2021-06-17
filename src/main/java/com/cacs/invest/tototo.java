package com.cacs.invest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author sen.Zhang
 * @Date 2021/6/17
 */
@MapperScan("com.cacs.invest.mapper")
@SpringBootApplication
public class tototo {
    public static void main(String[] args) {
        SpringApplication.run(tototo.class, args);
    }
}
