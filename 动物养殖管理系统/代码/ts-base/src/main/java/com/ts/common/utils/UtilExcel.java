package com.ts.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Excel操作类
 * @author jolley
 */
public class UtilExcel {

	/**
	 * 读取excel文件
	 * @param pathname
	 * @return
	 */
	public static List<Map<String, Object>> readExcel(String pathname) {
		List<Map<String, Object>> ret = new LinkedList<Map<String, Object>>();
		
		try {
			Workbook rwb = null;
			Cell cell = null;
			
			// 创建输入流
			InputStream stream = new FileInputStream(pathname);
			// 获取Excel文件对象
			rwb = Workbook.getWorkbook(stream);
			
			Sheet []sheets = rwb.getSheets(); // 所有工作表
			for(int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
				Sheet sheet = sheets[sheetIndex];

				String [][]color = new String[sheet.getRows()][]; // 第一维为行，第二维为列
				String [][]item = new String[sheet.getRows()][]; // 第一维为行，第二维为列
				// 行数(表头的目录不需要，从1开始)
				for(int i = 0; i < sheet.getRows(); i++) {
					// 创建一个数组 用来存储每一列的数据
					String[] rgb = new String[sheet.getColumns()];
					String[] str = new String[sheet.getColumns()];
					
					// 列数
					for(int j = 0; j < sheet.getColumns(); j++) {
						// 获取第i行，第j列的值
						cell = sheet.getCell(j, i);
						
						if(cell.getCellFormat() != null && cell.getCellFormat().getFont() != null) {
							Colour c = cell.getCellFormat().getFont().getColour();
							if(c != null) {
								rgb[j] = rgb2String(c.getDefaultRGB().getRed(), c.getDefaultRGB().getGreen(), c.getDefaultRGB().getBlue());
							}
						}
						str[j] = cell.getContents();;
					}
					
					color[i] = rgb;
					item[i] = str;
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("data", item);
				map.put("color", color);
				map.put("name", sheet.getName());
				ret.add(map);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * 编写excel文件
	 * @param pathname
	 * @param data
	 * @return
	 */
	public static boolean writeExcel(String pathname, String [][]data) {
		boolean ret = false;
		
		try {
			// 创建上级文件夹
			int lastIndex = pathname.lastIndexOf("/");
			if(lastIndex > 0) {
				File f = new File(pathname.substring(0, lastIndex));
				if(!f.exists()) {
					f.mkdirs();
				}
			}
			// 创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(new File(pathname));
			// 创建新的一页单页行限制为65536
			for(int page = 0; page <= data.length / 60000; page++) {
				WritableSheet sheet = workbook.createSheet("Sheet" + page, page);
				// 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
				for(int i = 60000 * page; (i < data.length && i < 60000 * (page + 1)); i++) {
					for(int j = 0; j < data[i].length; j++) {
						Label label = new Label(j, i, data[i][j]);
				        sheet.addCell(label);
					}
				}
			}
			
			// 把创建的内容写入到输出流中，并关闭输出流
			workbook.write();
			workbook.close();
			
			ret = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	// rgb的int转十六进制颜色编码
	public static String rgb2String(int red, int green, int blue) {
		String R = red < 16 ? ('0' + Integer.toHexString(red)) : Integer.toHexString(red);
		String G = green < 16 ? ('0' + Integer.toHexString(green)) : Integer.toHexString(green);
		String B = blue < 16 ? ('0' + Integer.toHexString(blue)) : Integer.toHexString(blue);
		
		return '#' + R + G + B;
	}
}
