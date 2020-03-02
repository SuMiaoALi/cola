package com.baoyun.ins.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baoyun.ins.entity.profile.vo.ProfileVo;
import com.baoyun.ins.service.profile.ProfileService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 个人中心控制器
 * @Author cola
 * @Date 2020年2月29日
 */

@Api(tags = "个人信息接口")
@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@ApiOperation("获取个人信息")
	@GetMapping("/get/{userId}")
	public Msg<?> get(@PathVariable String userId) {
		return profileService.get(userId);
	}
	
	@ApiOperation("修改个人信息")
	@PutMapping("/update")
	public Msg<?> update(@RequestBody ProfileVo profileVo) {
		return profileService.update(profileVo);
	}
	
	@ApiOperation("上传头像")
	@PostMapping("/photo")
	public Msg<?> photo(MultipartFile file) {
		return profileService.photo(file);
	}
	
}
