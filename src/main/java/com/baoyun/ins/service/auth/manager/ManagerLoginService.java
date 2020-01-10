package com.baoyun.ins.service.auth.manager;

import javax.validation.Valid;

import com.baoyun.ins.entity.auth.manager.vo.Login;
import com.baoyun.ins.utils.json.Msg;

/**
 * @Description: 后台登录接口
 * @Author cola
 * @Date 2019年12月20日
 */
public interface ManagerLoginService {

	/**
	 * @Description: 后台登录
	 * @Author cola
	 * @Data: 2019年12月20日
	 * @param login
	 * @return
	 */
	Msg<Object> login(@Valid Login login);

}
