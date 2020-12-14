/**
 * @company 广州天迅网络科技有限公司 copyright
 * @author jolley
 * @date 2017年11月23日
 * @describe  xxx
 *
 */
package com.label.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:工作簿数据封装类
 * @version 1.0
 * @since 1.0
 * @author jolley
 */
public class ImportData {
	
	private Map<Integer,SheetData> m_data = new HashMap<Integer,SheetData>();
	
	private int sheets = 0;
	
	
	public ImportData() {

	}

	public int getSheets() {
		return this.sheets;
	}

	public void setSheets(int sheets) {
		this.sheets = sheets;
	}
	
	/**
	 * 获取表对象对象
	 * 
	 * @param sheet
	 * @return
	 */
	public SheetData getTableData(int sheet) {
		return m_data.get(new Integer(sheet));
	}
	
	/**
	 * 根据指定的表对象序号获取此表对象的行数
	 * 
	 * @param sheet
	 * @return
	 */
	public int getTableRows(int sheet) {
		SheetData sheetData = m_data.get(new Integer(sheet));
		if (sheetData != null) {
			return sheetData.getRows();
		} else {
			return 0;
		}
	}	
	
	/**
	 * 根据指定的表对象序号获取此表对象的列数
	 * 
	 * @param sheet
	 * @return
	 */
	public int getTableCols(int sheet) {
		SheetData tableData = m_data.get(new Integer(sheet));
		if (tableData != null) {
			return tableData.getCols();
		} else {
			return 0;
		}
	}

	/**
	 * 获取指定的单元格数据
	 * 
	 * @param sheet
	 *            表对象序号
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @return
	 */
	public String getData(int sheet, int row, int col) {
		String result = null;

		SheetData sheetData = m_data.get(new Integer(sheet));
		if (sheetData != null) {
			result = sheetData.getRowColData(row, col);
		}

		return result;
	}

	/**
	 * 新增工作表到map中
	 * @param tableData
	 */
	public void addTableData(SheetData tableData) {
		m_data.put(new Integer(tableData.getTableNo()), tableData);
	}
	

	/**
	 * 添加行列数据对象
	 * 
	 * @param sheet
	 *            表对象序号
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @param data
	 *            数据
	 */
	public void addRowColData(int tableNo, int row, int col, String data) throws Exception {
		Integer oTable = new Integer(tableNo);
		SheetData tableData = null;
		if (m_data.containsKey(oTable)) {
			tableData = m_data.get(oTable);
		} else {
			throw new Exception("请先添加表对象,然后再添加行列数据.");
		}

		tableData.addRowColData(row, col, data);
	}
	
}
