package com.baoyun.ins.config.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baoyun.ins.utils.json.GlobalReturnCode;
import com.baoyun.ins.utils.json.Msg;

/***
 * 系统异常处理
 * @author louis
 *
 */
@ControllerAdvice
public class ApiControllerAdvice {
	
	/**
	 * 系统异常处理，比如：404,500
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 * @throws Exception
	 */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Msg<?> defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
    	e.printStackTrace();
        logException(request);
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new Msg(GlobalReturnCode.SYSTEM_PATH_NOEXIST);
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new Msg(GlobalReturnCode.SYSTEM_ERROR);
        }
    }
    
    /**
     * 拦截@Valid请求参数验证不通过的异常
     * @param request   request
     * @param response  response
     * @param exception 验证不通过的异常
     * @return 执行结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Order(0)
    public Msg handler(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException exception) {
        logException(request);
        String validation_message;
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult != null && bindingResult.getFieldError() != null) {
            validation_message = bindingResult.getFieldError().getDefaultMessage();
        } else {
            validation_message = exception.getMessage();
        }
        return new Msg(GlobalReturnCode.PARAM_ERROR, validation_message);
    }

    /**
     * 取得header的所有属性
     *
     * @param headers 请求的headers
     * @param request request
     * @return 把header拼成字符串
     */
    /*private String getHeaderValue(Enumeration<String> headers, HttpServletRequest request) {
        String str = "";
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            str += header + "=" + request.getHeader(header) + "&";
        }
        return str;
    }*/

    /**
     * 打印所有异常信息
     *
     * @param request request
     */
    private void logException(HttpServletRequest request) {
        try {
            /*logger.error("请求路径：" + request.getServletPath());
            logger.error("请求参数：" + request.getParameterMap().toString());
            logger.error("请求header:" + getHeaderValue(request.getHeaderNames(), request));
            logger.error("请求body:" + StringUtil.getBodyString(request.getReader()));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
