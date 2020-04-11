package com.baoyun.ins.service.excel.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baoyun.ins.entity.excel.dto.ExcelTestDto;
import com.baoyun.ins.entity.excel.vo.ExcelTestVo;
import com.baoyun.ins.mapper.excel.ExcelMapper;
import com.baoyun.ins.service.excel.ExcelService;
import com.baoyun.ins.utils.json.Msg;

@Service
public class ExcelServiceImpl implements ExcelService {
	
	@Autowired
	private ExcelMapper excelMapper;

	/**
	 * Excel上传
	 */
	@Override
	public Msg<?> upload(MultipartFile file) {
		// TODO Auto-generated method stub
		String name = file.getOriginalFilename();
		String suffix = name.substring(name.lastIndexOf("."));
		if (!(".xls".equals(suffix)) && !".xlsx".equals(suffix)) {
			return new Msg<>("请选择正确的表格格式");
		}
		InputStream is = null;
		try {
			is = file.getInputStream();
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(0);
			// 物理行数即绝对行数,不包括空行
			int num = sheet.getPhysicalNumberOfRows();
			// 最后一行的索引index
			System.out.println(sheet.getLastRowNum());
			if (num <= 1) {
				return new Msg<>("没有可用数据");
			}
			List<ExcelTestVo> list = new ArrayList<>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				// 行对象
				ExcelTestVo excel = new ExcelTestVo();
				List<String> _list = new ArrayList<>();
				Row row = sheet.getRow(i);
				// 单元格的起始index
				int first = row.getFirstCellNum();
				// 单元格的长度，length
				int last = row.getLastCellNum();
				for (int j = first; j < last; j++) {
					Cell cell = row.getCell(j);
					int type = cell.getCellType();
					String value = null;
					switch (type) {
					// 数字类型
					case 0:
						// 判断是否是日期格式的numeric
//						if (DateUtil.isCellDateFormatted(cell)) {
						if (j == 0) {
							Date date = cell.getDateCellValue();
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							value = formatter.format(date);
							System.out.println(", 格式化以后的是:" + value);
						} else {
							value = (int) cell.getNumericCellValue() + ""; // 转为string
						}
						break;
					// 字符串
					case 1: value = cell.getStringCellValue();
							break;
					// bool
					case 4: boolean flag = cell.getBooleanCellValue();
							value = flag ? "1" : "0";
							break;
					default:
						break;
					}
					_list.add(value);
				}
				excel.setTime(_list.get(0)).setNewPatient(Integer.parseInt(_list.get(1)))
						.setNewDeath(Integer.parseInt(_list.get(2)));
				list.add(excel);
			}
			System.out.println("list长度：" + list);
			excelMapper.upload(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Msg<>();
	}

	/**
	 *查询数据
	 */
	@Override
	public Msg<?> get() {
		// TODO Auto-generated method stub
		List<ExcelTestDto> list1 = excelMapper.get();
		return new Msg<>(list1);
	}

}
