package com.baoyun.ins;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("com.baoyun.ins.mapper")
public class InsColaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsColaApplication.class, args);
	}

}
