package com.hblolj.haichat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.hblolj.haichat.dao")
@SpringBootApplication
public class HaichatApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaichatApplication.class, args);
	}
}
