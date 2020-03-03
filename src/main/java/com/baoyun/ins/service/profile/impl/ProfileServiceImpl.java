package com.baoyun.ins.service.profile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baoyun.ins.entity.profile.dto.ProfileDto;
import com.baoyun.ins.entity.profile.vo.ProfileVo;
import com.baoyun.ins.mapper.profile.ProfileMapper;
import com.baoyun.ins.service.profile.ProfileService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.spring.SpringContextUtil;
import com.baoyun.ins.utils.string.StringUtil;
import com.baoyun.ins.utils.uoload.FileUploadUtil;

/**
 * @Description: 个人中心接口
 * @Author cola
 * @Date 2020年2月29日
 */

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileMapper profileMapper;
	
	/**
	 *获取个人信息
	 */
	@Override
	public Msg<?> get(String userId) {
		// TODO Auto-generated method stub
		// 查询自己信息
		if (StringUtil.isNullOrEmpty(userId) || "self".equals(userId)) {
			userId = SpringContextUtil.getUserId();
		} 
		ProfileDto profile = profileMapper.get(userId);
		
		Long notes = profileMapper.notes(userId);
		if (notes > 0) {
			profile.setNotes(notes).setLikes(profileMapper.likes(userId)).setCollections(profileMapper.collections(userId));
		}
		return new Msg<>(profile);
	}

	/**
	 *修改个人信息
	 */
	@Override
	public Msg<?> update(ProfileVo profileVo) {
		// TODO Auto-generated method stub
		profileVo.setUserId(SpringContextUtil.getUserId());
		profileMapper.update(profileVo);
		return new Msg<>();
	}

	@Override
	public Msg<?> photo(MultipartFile file) {
		// TODO Auto-generated method stub
		if (file.isEmpty()) {
			return new Msg<>();
		}
		String newName = FileUploadUtil.uploadImage(file);
		profileMapper.photo(SpringContextUtil.getUserId(), newName);
		return new Msg<>();
	}

}
