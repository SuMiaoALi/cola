package com.baoyun.ins.config.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.baoyun.ins.config.annotation.RequiredPermission;
import com.baoyun.ins.config.annotation.RequiredRole;
import com.baoyun.ins.utils.json.GlobalReturnCode;
import com.baoyun.ins.utils.json.JsonUtil;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.string.StringUtil;
import com.baoyun.ins.utils.token.TokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


/**
 * @Description: 自定义SpringMVC拦截器，代码逻辑完成后需要注册
 * @Author cola
 * @Date 2020年4月12日
 */
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
	
	/**
	 * 操作前先判断是否登录，未登录提示未登录
	 * SpringContextUtil.getUserId() 可以直接从accessToken获取，也可以通过拦截器加入的请求属性current_user获取
	 */
	// preHandle() => 返回true，则放行；返回false，则打回去
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if (StringUtil.isNullOrEmpty(request.getHeader("accessToken"))) {
            //状态设置为未授权
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            StringUtil.out(response, JsonUtil.toStr(new Msg<Object>(GlobalReturnCode.NO_AUTH)));
        } else {
        	try {
        		Claims claims = TokenUtil.get(request.getHeader("accessToken"));
        		// 验证权限
                if (this.hasPermission(claims, handler)) {
                	// 在请求里加上userId和role
                	request.setAttribute("current_roles", claims.get("scopes"));
                	request.setAttribute("current_user", claims.getSubject());//用户id
                	System.out.println("拦截了请求，user_id = " + claims.getSubject());
                	// 放行
                    return true;
                }
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                StringUtil.out(response, JsonUtil.toStr(new Msg<Object>(GlobalReturnCode.NO_AUTH)));
            } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            	response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                StringUtil.out(response, JsonUtil.toStr(new Msg<Object>(GlobalReturnCode.AUTH_ERROR)));
            } catch (ExpiredJwtException expiredEx) {
            	response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                StringUtil.out(response, JsonUtil.toStr(new Msg<Object>(GlobalReturnCode.AUTH_EXPIRED)));
            }
        }
    	return false;
    }
     
    // 为什么不用实现postHandle和afterCompletion？
    // 因为HandlerInterceptor接口中的方法都是default默认方法，有默认实现logic，相当于加了一个abstract class。所以我们只需要实现需要的方法即可
    
    /**
     * 权限拦截
     * @Description: TODO
     * @author louis
     * @date 2018年3月29日 下午5:12:44
     * @param claims
     * @param handler
     * @return
     * @throws
     */
    @SuppressWarnings("unchecked")
	private boolean hasPermission(Claims claims,Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            
            // 获取方法上的注解
            RequiredRole requiredRole = handlerMethod.getMethod().getAnnotation(RequiredRole.class);
            if (requiredRole != null && StringUtil.isNotNullOrEmpty(requiredRole.value())) {
                // redis或数据库 中获取该用户的权限信息 并判断是否有权限
            	List<String> roles = (List<String>) claims.get("scopes");
            	if(!roles.contains(requiredRole.value())) {
            		return false;
            	}
            }
            
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果标记了注解，则判断权限
            if (requiredPermission != null && StringUtil.isNotNullOrEmpty(requiredPermission.value())) {
                // redis或数据库 中获取该用户的权限信息 并判断是否有权限
            	List<String> privileges = (List<String>) claims.get("privileges");
            	if(!privileges.contains(requiredPermission.value())) {
            		return false;
            	}
            }
        }
        return true;
    }

}
