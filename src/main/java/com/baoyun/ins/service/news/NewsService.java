package com.baoyun.ins.service.news;

import com.baoyun.ins.entity.news.vo.GetTokenVo;
import com.baoyun.ins.entity.news.vo.MiniTemplateVo;
import com.baoyun.ins.utils.json.Msg;

public interface NewsService {

	/**
	 * @Description: 小程序发送订阅消息
	 * @Author cola
	 * @Data: 2020年1月13日
	 * @param data
	 * @param tokenVo
	 * @return
	 */
	Msg<?> sendMini(MiniTemplateVo data, GetTokenVo tokenVo);

}
