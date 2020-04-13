package com.baoyun.ins.utils.spring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baoyun.ins.utils.string.StringUtil;
import com.baoyun.ins.utils.token.TokenUtil;

import io.jsonwebtoken.Claims;

@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextUtil.applicationContext == null) {
        	SpringContextUtil.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
    
    /**
     * 获取当前登录用户id
     * @Description: TODO
     * @author louis
     * @date 2018年4月27日 下午11:06:39
     * @return
     * @throws
     */
    public static String getUserId() {
    	try {
	    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    	// 1.直接从header中的accessToken解析
	    	if (StringUtil.isNotNullOrEmpty(request.getHeader("accessToken"))) {
	    		Claims claims = TokenUtil.get(request.getHeader("accessToken"));
	    		return claims.getSubject();
	    	}
	    	// 2.通过拦截器拦截的accessToken获取request.setAttribute("current_user")
	    	if(request.getAttribute("current_user") != null) {
	    		return request.getAttribute("current_user").toString();
	    	}
    	}catch(Exception e) {
    		
    	}
    	return "";
    }
    
    /**
     * 判断用户权限
     * @Description: TODO
     * @author xiewei
     * @date Dec 18, 2018 3:26:27 PM
     * @param permission
     * @return
     * @throws
     */
    @SuppressWarnings("unchecked")
	public static boolean hasPermission(String permission) {
    	try {
	    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    	List<String> roles = (List<String>) request.getAttribute("current_roles");
	    	if(roles.contains(permission)) {
	    		return true;
	    	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
     	return false;
    }
    
}
