package com.baoyun.ins.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 校验登录拦截器配置
 * @author louis
 *
 */
@Configuration
public class CheckLoginConfig implements WebMvcConfigurer {
 
  @Autowired
  private CheckLoginInterceptor checkLoginInterceptor;
  
  // 这个方法是用来配置静态资源的，比如html，js，css，等等
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
  }
 
  // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // addPathPatterns("/**") 表示拦截所有的请求，
    // excludePathPatterns("/login", "/reg") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
    registry.addInterceptor(checkLoginInterceptor)
    	.addPathPatterns("/note/mine/**", "/note/save", "/note/like/**", "/note/unlike/**", "/note/collect/**", "/note/uncollect/**", 
    					 "/comment/save", "/keyword/save/**", "/keyword/del/**", "/profile/get/self", "/profile/update", "/profile/photo");
//    	.excludePathPatterns(
//    			"/login",
//    			"/login/**",
//    			"/wechat",
//    			"/message/**",
//    			"/swagger-ui.html",
//    			"/swagger-resources/**",
//    			"/webjars/**",
//    			"/doc.html"
//	);
  }
}
