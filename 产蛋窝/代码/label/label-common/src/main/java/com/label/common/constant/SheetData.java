/**
 * @company 广州天迅网络科技有限公司 copyright
 * @author jolley
 * @date 2017年11月23日
 * @describe  xxx
 *
 */
package com.label.common.constant;

import java.util.Hashtable;
import java.util.Map;

/**
 * Description:工作表数据封装类
 * @version 1.0
 * @since 1.0
 * @author jolley
 */
public class SheetData {
	
	private int tableNo = 0;
	private int rows = 0;
	private int cols = 0;
	private Map<String,String> mapData = new Hashtable<String,String>();
	
	public SheetData(int tableNo) {
		this.tableNo = tableNo;
	}
	
	public SheetData(int tableNo, int rows, int cols) {
		this.tableNo = tableNo;
		this.rows = rows;
		this.cols = cols;
	}

	public int getTableNo() {
		return tableNo;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void addRowColData(int row, int col, String data) {
		String key = row + "_" + col;
		this.mapData.put(key, data);
	}

	public String getRowColData(int row, int col) {
		String key = row + "_" + col;
		return this.mapData.get(key);
	}

}
