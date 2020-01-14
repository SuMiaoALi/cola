package com.baoyun.ins.service.news.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baoyun.ins.entity.news.vo.GetTokenVo;
import com.baoyun.ins.entity.news.vo.MiniTemplateVo;
import com.baoyun.ins.service.news.NewsService;
import com.baoyun.ins.utils.http.HttpClientUtils;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.string.StringUtil;
import com.baoyun.ins.utils.token.AccessTokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 消息接口
 * @Author cola
 * @Date 2020年1月13日
 */

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

	/**
	 *小程序发送订阅消息
	 */
	@Override
	public Msg<?> sendMini(MiniTemplateVo data, GetTokenVo tokenVo) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<Object>();
		// 获取token
		String accessToken = new AccessTokenUtil().getAccessToken(tokenVo);
		
		String url = null;
		String msgType = tokenVo.getMsgType();
		
		if (StringUtil.isNotNullOrEmpty(msgType)) {
			// 微信订阅消息
			if ("WX_SUBSCRIBE".equals(msgType)) {
				url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
			} else {
				msg.setData("参数不正确!");
				return msg;
			}
		} else {
			msg.setData("参数不正确!");
			return msg;
		}
		// json序列化
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(data);
		String callback = null;
		try {
			callback = HttpClientUtils.sendPostJsonStr(url, jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}		
		// 解析
		JSONObject response = JSONObject.parseObject(callback);
		if (response.getIntValue("errcode") == 0) {
			log.info("发送订阅消息成功");
		} else {
			log.info("错误代码:" + response.get("errcode") + ",错误信息:" + response.get("errmsg"));
		}
		return msg;
	}

}
