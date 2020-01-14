package com.baoyun.ins.utils.token;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.baoyun.ins.entity.news.vo.GetTokenVo;
import com.baoyun.ins.utils.http.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: AccesssToken工具类
 * @Author cola
 * @Date 2020年1月12日
 */

@Slf4j
public class AccessTokenUtil {

	// 微信小程序配置
	@Value("${wx.qe.appid}")
	private String wxqeAppid;
	
	@Value("${wx.qe.appsecret}")
	private String wxqeAppsecret;
	
	public String getAccessToken(GetTokenVo tokenVo) {
		String accessToken = null;
		String url = null;
		// 微信
		String client = tokenVo.getClient();
		if ("WX".equals(client)) {
			url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxqeAppid
				  + "&secret=" + wxqeAppsecret;
		} else {
			log.info("参数错误！");
		}
		String callback = null;
		try {
			callback = HttpClientUtils.sendPost(url);
			JSONObject json = JSONObject.parseObject(callback);
			if (json.getIntValue("errcode") == 0) {
				accessToken = json.getString("access_token");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	
}
