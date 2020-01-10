package com.baoyun.ins.config.restTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author xiewei
 *
 */
@Configuration
public class RestConfig {

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
