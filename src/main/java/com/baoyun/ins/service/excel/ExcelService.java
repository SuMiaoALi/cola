package com.baoyun.ins.service.excel;

import org.springframework.web.multipart.MultipartFile;

import com.baoyun.ins.utils.json.Msg;

public interface ExcelService {

	/**
	 * @Description: 上传Excel
	 * @Author cola
	 * @Data: 2020年3月31日
	 * @param file
	 * @return
	 */
	Msg<?> upload(MultipartFile file);

	/**
	 * @Description: 查询数据 
	 * @Author cola
	 * @Data: 2020年3月31日
	 * @return
	 */
	Msg<?> get();

}
