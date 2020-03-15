package com.baoyun.ins.utils.uoload;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${realPath}")
	private static String realPath;
	
	/**
	 * @Description: 图片上传
	 * @Author cola
	 * @Data: 2020年3月3日
	 * @param file
	 * @return
	 */
	public static String uploadImage(MultipartFile file) {
		String name = file.getOriginalFilename();
		String newName = UUID.randomUUID().toString().replaceAll("-", "") + name.substring(name.lastIndexOf("."));
		// ng图片服务器监听的目录为8080，挂载的目录为/usr/mine/imgs
		File dir = new File("/usr/mine/imgs");
//		File dir = new File("C:\\Users\\Ali\\Desktop\\statics");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			log.info("文件的新名字：" + newName);
			file.transferTo(new File(dir, newName));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("工具类文件上传失败！");
		}
		return "http://www.feedme.ltd:8080/" + newName;
	}

}
