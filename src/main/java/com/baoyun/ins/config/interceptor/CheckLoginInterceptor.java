package com.baoyun.ins.config.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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

@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
	
	/**
	 * 操作前先判断是否登录，未登录提示未登录
	 * SpringContextUtil.getUserId() 可以直接从accessToken获取，也可以通过拦截器加入的请求属性current_user获取
	 */
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
                	request.setAttribute("current_roles", claims.get("scopes"));
                	request.setAttribute("current_user", claims.getSubject());//用户id
                	System.out.println("拦截了请求，user_id = " + claims.getSubject());
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

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
    
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
