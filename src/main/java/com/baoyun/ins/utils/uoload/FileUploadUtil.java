package com.baoyun.ins.utils.uoload;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 文件上传工具类
 * @Author cola
 * @Date 2020年3月2日
 */

@Component
@Slf4j
public class FileUploadUtil {
	
	/**
	 * @Description: 图片上传
	 * @Author cola
	 * @Data: 2020年3月3日
	 * @param file
	 * @return
	 */
	public static String uploadImage(MultipartFile file) {
		String name = file.getOriginalFilename();
		String newName = UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
		// 图片根目录
		File dir = new File("C:\\Users\\Ali\\Desktop\\statics");
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
			log.info("文件的新名字：" + newName);
			file.transferTo(new File(dir, newName));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("工具类文件上传失败！");
		}
		return dir + "\\" + newName;
	}

}
