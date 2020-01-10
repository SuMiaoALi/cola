package com.baoyun.ins.utils.dingding;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.baoyun.ins.utils.redis.RedisConstant;
//import com.baoyun.ins.utils.redis.RedisUtil;
//import com.dingtalk.api.DefaultDingTalkClient;
//import com.dingtalk.api.request.OapiGettokenRequest;
//import com.dingtalk.api.response.OapiGettokenResponse;
//import com.taobao.api.ApiException;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class DingdingUtil {
//	
//	@Value("${dingding.appid}")
//	private String appid;
//	
//	@Value("${dingding.appsecret}")
//	private String appsecret;
//	
//	@Value("${dingding.agentid}")
//	private Long agentid;
//	
//	@Autowired
//	RedisUtil redisUtil;
//
//	/**
//	 * 获取钉钉Token
//	 * @return
//	 */
//	public String getToken() {
//		try {
//			if(redisUtil.exists(RedisConstant.TOKEN_DINGDING)) {
//				return redisUtil.get(RedisConstant.TOKEN_DINGDING, String.class);
//			}else {
//				DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
//				OapiGettokenRequest request = new OapiGettokenRequest();
//				request.setAppkey(appid);
//				request.setAppsecret(appsecret);
//				request.setHttpMethod("GET");
//				OapiGettokenResponse response = client.execute(request);
//				
//				String accessToken = response.getAccessToken();
//				redisUtil.set(RedisConstant.TOKEN_DINGDING, accessToken, 7000);
//				return accessToken;
//			}
//		}catch(ApiException e) {
//			log.error(e.getErrMsg());
//		}
//		return "";
//	}
//	
//	/**
//	 * @Description: 发送工作通知消息,Link
//	 * @Author cola
//	 * @Data: 2019年12月18日
//	 * @param SendLinkMsgVo
//	 * @return
//	 * @throws 
//	 */
////	public String sendMsg(SendLinkMsgVo msgVo) {
////		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
////		// token
////		String accessToken = this.getToken();
////		// 请求对象
////		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
////		request.setUseridList(msgVo.getUseridList());
////		request.setAgentId(agentid);
////		request.setToAllUser(msgVo.getToAll());
////		// 消息对象
////		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
////		msg.setMsgtype("link");
////		OapiMessageCorpconversationAsyncsendV2Request.Link link = new OapiMessageCorpconversationAsyncsendV2Request.Link();
////		link.setTitle(msgVo.getTitle());
////		link.setText(msgVo.getContent());
////		link.setMessageUrl(msgVo.getMessageUrl());
////		link.setPicUrl(msgVo.getPicture());
////		msg.setLink(link);
////		
////		request.setMsg(msg);
////		try {
////			OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, accessToken);
////			if (response.getErrcode() != 0) {
////				return "错误代码：" + response.getErrcode() + ", 错误信息：" + response.getErrmsg();
////			} else {
////				return response.getTaskId().toString();
////			}
////		} catch (ApiException e) { 
////			e.printStackTrace();
////		}
////		return "";
////	}
//	
//}
