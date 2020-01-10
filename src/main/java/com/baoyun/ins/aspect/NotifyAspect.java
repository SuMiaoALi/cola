package com.baoyun.ins.aspect;
//import java.lang.reflect.Method;
//import java.util.Map;
//
//import org.apache.commons.collections.map.HashedMap;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//import com.baoyun.ins.entity.info.vo.ProfileVo;
//import com.baoyun.ins.mapper.info.ProfileMapper;
//import com.baoyun.ins.service.note.NoteService;
//import com.baoyun.ins.utils.json.JsonUtil;
//import com.baoyun.ins.utils.spring.SpringContextUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Aspect
//@Component
//@Slf4j
//public class NotifyAspect {
//	 
//	@Autowired
//	private NoteService noteService;
//	
//	@Autowired
//	private ProfileMapper profileMapper;
//	
//	@Pointcut("@annotation(com.baoyun.ins.aspect.Notify)")
//	public void pointCut() {
//		
//	}
//	
//	@SuppressWarnings("unchecked")
//	@AfterReturning("pointCut()")
//	public void saveNotify(JoinPoint joinPoint) {
//		 
//		try {
//			//从切面织入点处通过反射机制获取织入点处的方法
//	        MethodSignature signature =  (MethodSignature) joinPoint.getSignature();
//	        //获取切入点所在的方法
//	        Method method = signature.getMethod();
//
//	        //获取操作
//	        Notify notify = method.getAnnotation(Notify.class);
//	        
//	        if (notify != null) {
//	        	 String type = notify.type();//类型
//	        	 String handeMethod = notify.method();//方法
//	        	 //TODO 不同类型处理不同方法
//	        	 log.info(type,handeMethod);
//	        	//请求的参数
//	           Object[] args = joinPoint.getArgs();
//	           //将参数所在的数组转换成json
//	           String params = JSON.toJSONString(args);
//	           log.info(params);
//	           Map<String, Object> map = new HashedMap();
//	           if(params.indexOf(":") != -1) {
//	        	   map = JsonUtil.toMap(params.replace("[", "").replace("]", ""));
//	           }else {
//	        	   map.put("id", params.replace("[", "").replace("]", ""));
//	           }
//	           
//	           log.info("hahah",map);
//	           ProfileVo profileVo = new ProfileVo();
//	    	   profileVo.setId(SpringContextUtil.getUserId());
//	           //添加评论
//	           if("C".equals(type)) {
//	        	   noteService.operate( Integer.valueOf(map.get("noteId").toString()), 6);
//	        	//添加点赞
//	           }else if("P".equals(type)) {
//	        	   noteService.operate( Integer.valueOf(map.get("noteId").toString()), 0);
//	        	//添加通知
//	           }else {
//		    	   profileVo.setType(type);
//		    	   //我的关注+1-1 收藏+1-1 
//		    	   profileMapper.syncCount(profileVo);
//	           } 
//	           if("attention".equals(type)) {
//	        	   
//	        	 //被关注人粉丝+1-1
//	    		   profileVo.setId(map.get("id").toString());
//	    		   profileVo.setType("fans");
//	        	   profileMapper.syncCount(profileVo);
//	        	   if("A".equals(method.toString())) {
//	        		   profileVo.setType("newFans");
//	            	   profileMapper.syncCount(profileVo);
//	        	   }
//	           } 
//	        }
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
////
////        //获取请求的类名
////        String className = joinPoint.getTarget().getClass().getName();
////        //获取请求的方法名
////        String methodName = method.getName();
////        sysLog.setContent(className + "." + methodName);
////
////        //请求的参数
////        Object[] args = joinPoint.getArgs();
////        //将参数所在的数组转换成json
////        String params = JSON.toJSONString(args);
////        sysLog.setParams(params);
////
////        sysLog.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
////        //获取用户名
////        sysLog.setUserId("管理员");
////        //获取用户ip地址
////        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
////        
////        sysLog.setMethod(request.getScheme() +"://" + request.getServerName()  
////        + ":" +request.getServerPort() 
////        + request.getServletPath());
////        sysLog.setIp(IpUtils.getIpAddr(request));
//        
//        //调用service保存SysLog实体类到数据库
////        sysLogService.save(sysLog);
//	}
//	
//	
//}
