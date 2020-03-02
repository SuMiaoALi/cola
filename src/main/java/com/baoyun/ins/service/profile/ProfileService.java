package com.baoyun.ins.service.profile;

import org.springframework.web.multipart.MultipartFile;

import com.baoyun.ins.entity.profile.vo.ProfileVo;
import com.baoyun.ins.utils.json.Msg;

public interface ProfileService {

	/**
	 * @Description: 获取个人信息
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @param userId
	 * @return
	 */
	Msg<?> get(String userId);

	/**
	 * @param profileVo 
	 * @Description: 
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @return
	 */
	Msg<?> update(ProfileVo profileVo);

	/**
	 * @Description: 上传头像
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @param file
	 * @return
	 */
	Msg<?> photo(MultipartFile file);

}
