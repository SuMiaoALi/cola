package com.baoyun.ins;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@MapperScan("com.baoyun.ins.mapper")
@EnableScheduling
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class InsColaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsColaApplication.class, args);
	}

}
