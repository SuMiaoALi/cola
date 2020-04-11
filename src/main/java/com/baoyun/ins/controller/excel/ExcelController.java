package com.baoyun.ins.controller.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baoyun.ins.service.excel.ExcelService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Excel接口")
@RestController
@RequestMapping("/excel")
public class ExcelController {
	
	@Autowired
	private ExcelService excelService;
	
	@ApiOperation("上传excel")
	@PostMapping("/upload")
	public Msg<?> upload(MultipartFile file) {
		if (file.isEmpty()) {
			return new Msg<>("文件不能为空");
		}
		return excelService.upload(file);
	}
	
	@ApiOperation("查询数据")
	@GetMapping()
	public Msg<?> get() {
		return excelService.get();
	}
	
}
