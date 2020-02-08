package com.baoyun.ins.config.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baoyun.ins.utils.ali.SmsUtil;

/**
 * @Description: 工具类注入配置
 * @Author cola
 * @Date 2020年2月7日
 */

@Configuration
public class UtilConfig {
	
	// 短信发送注入
	@Bean
	public SmsUtil getSmsutil() {
		return new SmsUtil();
	}

}
