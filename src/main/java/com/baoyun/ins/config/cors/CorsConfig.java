package com.baoyun.ins.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决跨域配置
 * @author louis
 *
 */
@Configuration
public class CorsConfig {
	
	/**
     * 配置跨越的参数
     *
     * @return 配置信息
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1所有URL
        corsConfiguration.addAllowedHeader("*"); // 2所有请求头
        corsConfiguration.addAllowedMethod("*"); // 3所有请求方法
        return corsConfiguration;
    }

    /**
     * 加入过滤器
     *
     * @return 过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

}
