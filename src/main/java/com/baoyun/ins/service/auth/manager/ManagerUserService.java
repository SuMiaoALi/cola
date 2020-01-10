package com.baoyun.ins.service.auth.manager;

import javax.validation.Valid;

import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.utils.json.Msg;

/**
 * @Description: 后台用户接口
 * @Author cola
 * @Date 2020年1月3日
 */
public interface ManagerUserService {

	/**
	 * @Description: 创建用户
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param user
	 * @return
	 */
	Msg<?> create(@Valid User user);

}
