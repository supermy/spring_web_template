package com.supermy.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {

	public void exportExcel(OutputStream out, List<Map> mapList) {
		exportExcel(out, mapList, "yyyy-MM-dd");
	}

	public void exportExcel(OutputStream out, List<Map> mapList, String pattern) {
		exportExcel(out, pattern, mapList);
	}

	/**
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 * @param mapList
	 *            数据集合 Map建议使用LinkedHashMap 键名： headers：Excel数组 sheetName：Excel
	 *            sheet名 dataset：Excel数据列表
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(OutputStream out, String pattern, List<Map> mapList) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		Iterator it = mapList.iterator();
		while (it.hasNext()) {
			Map map = (Map) it.next();
			HSSFSheet sheet = workbook.createSheet(map.get("sheetName")
					.toString()); // 生成一个表格
			sheet.setDefaultColumnWidth((short) 15); // 设置表格默认列宽度为15个字节
			HSSFCellStyle style = workbook.createCellStyle(); // 生成一个样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index); // 设置这些样式
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont font = workbook.createFont(); // 生成一个字体
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font); // 把字体应用到当前的样式
			HSSFCellStyle style2 = workbook.createCellStyle(); // 生成并设置另一个样式
			style2.setFillForegroundColor(HSSFColor.WHITE.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFFont font2 = workbook.createFont(); // 生成另一个字体
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			style2.setFont(font2);// 把字体应用到当前的样式
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); // 声明一个画图的顶级管理器

			HSSFRow row = sheet.createRow(0); // 产生表格标题行
			String[] headers = (String[]) map.get("headers");

			for (short i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			// 遍历集合数据，产生数据行
			Iterator<T> datasetIt = ((List) map.get("dataset")).iterator();
			int index = 0;
			CellStyle style3 = workbook.createCellStyle();
			HSSFFont font3 = workbook.createFont();
			font.setColor(HSSFColor.BLUE.index);
			style.setFont(font);

			// 数据Map键名
			String[] keyName = (String[]) map.get("keyName");

			while (datasetIt.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = (T) datasetIt.next();

				String typeName = t.getClass().getName();
				if (typeName != null && typeName.indexOf("HashMap") != -1) {
					Map m = (HashMap) t;

					if (keyName != null) {
						for (int i = 0; i < keyName.length; i++) {
							HSSFCell cell = row.createCell(i);
							cell.setCellStyle(style2);

							Object value = m.get(keyName[i]);
							// 判断值的类型后进行强制类型转换
							String textValue = null;

							if (value instanceof Boolean) {
								boolean bValue = (Boolean) value;
								textValue = "1";
								if (!bValue) {
									textValue = "0";
								}
							} else if (value instanceof Date) {
								Date date = (Date) value;
								SimpleDateFormat sdf = new SimpleDateFormat(
										pattern);
								textValue = sdf.format(date);
							} else if (value instanceof byte[]) {
								// 有图片时，设置行高为60px;
								row.setHeightInPoints(60);
								// 设置图片所在列宽度为80px,注意这里单位的一个换算
								sheet.setColumnWidth(i, (short) (35.7 * 80));
								// sheet.autoSizeColumn(i);
								byte[] bsValue = (byte[]) value;
								HSSFClientAnchor anchor = new HSSFClientAnchor(
										0, 0, 1023, 255, (short) 6, index,
										(short) 6, index);
								anchor.setAnchorType(2);
								patriarch
										.createPicture(
												anchor,
												workbook
														.addPicture(
																bsValue,
																HSSFWorkbook.PICTURE_TYPE_JPEG));
							} else {
								// 其它数据类型都当作字符串简单处理
								if (null == value) {
									textValue = "";
								} else {
									textValue = value.toString();
								}
							}
							// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
							if (textValue != null) {
								Pattern p = Pattern.compile("^//d+(//.//d+)?$");
								Matcher matcher = p.matcher(textValue);
								if (matcher.matches()) {
									// 是数字当作double处理
									cell.setCellValue(Double
											.parseDouble(textValue));
								} else {
									HSSFRichTextString richString = new HSSFRichTextString(
											textValue);
									cell.setCellStyle(style3);
									cell.setCellValue(richString);
								}
							}
						}
					}
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Excel导出准备
	 */
	public static void doPreExportExcel(String[] headers, String[] keyName,
			List<?> dataList, String name, String path) {
		try {
			// 准备数据
			List<Map> mapList = new ArrayList<Map>();
			Map map = new HashMap();
			map.put("headers", headers);
			map.put("sheetName", name);
			map.put("dataset", dataList);
			map.put("keyName", keyName);
			mapList.add(map);

			// 导出数据
			ExportExcel ex = new ExportExcel();
			FileOutputStream out = new FileOutputStream(path + "exportData.xls");
			ex.exportExcel(out, mapList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		/*
		 * 示例
		 */
		// Excel列头标题
		String[] headers = { "姓名", "年龄", "生日" };
		// 字段名数组
		String[] keyName = { "name", "age", "bir" };
		String name = "沃门户订购量统计数据";
		List<Map> list = new ArrayList<Map>();

		// 装载数据list
		Map map1 = new HashMap();
		map1.put("name", "name1");
		map1.put("age", "age1");
		map1.put("sex", "sex1");
		map1.put("box", "1");
		list.add(map1);
		map1 = new HashMap();
		map1.put("name", "name2");
		map1.put("age", "age2");
		map1.put("sex", "sex2");
		map1.put("box", "2");
		list.add(map1);
		map1 = new HashMap();
		map1.put("name", "张三");
		map1.put("age", 123.1);
		map1.put("sex", "sex2");
		map1.put("box", "2");
		map1.put("bir", new Date());
		list.add(map1);

		// 导出数据
		ExportExcel.doPreExportExcel(headers, keyName, list, name, "");
	}

}