package com.baoyun.ins.service.profile.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
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
		System.out.println(file);
		String name = file.getOriginalFilename();
		System.out.println("原名字：" + name);
		String newName = UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
		System.out.println("新名字：" + newName);
		// 1.创建源
		String statics = "C:\\Users\\Ali\\Desktop\\statics\\";
		File dir = new File(statics);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File _file = new File(dir, newName);
		try {
			file.transferTo(_file);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profileMapper.photo(SpringContextUtil.getUserId(), statics + newName);
		return new Msg<>();
	}

}
