/**
 * @company 广州天迅网络科技有限公司 copyright
 * @author jolley
 * @date 2017年11月23日
 * @describe  xxx
 *
 */
package com.label.service.common.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.label.common.constant.ImportData;
import com.label.common.constant.SheetData;
import com.label.service.common.CommonImportService;

@Service
public  class CommonImportServiceImpl implements CommonImportService{

	private static Logger _log = LoggerFactory.getLogger(CommonImportServiceImpl.class);
	
	/**
	 * 读取文件 放入封装的工作簿实体类
	 * @param in
	 * @param fileExt
	 * @return
	 * @throws Exception
	 */
	@Override
	public ImportData readExcel(InputStream in,String fileExt) throws Exception {
		ImportData result = new ImportData();

		if (in == null) {
			throw new Exception("InputStream in参数为空");
		}
		Workbook workbook = null;
		if(fileExt.contains("xlsx")){
			  workbook = new XSSFWorkbook(in);
		}else if(fileExt.contains("xls")){
			  workbook = new HSSFWorkbook(in);
		}else{
			throw new Exception("上传的文件不符合要求");
		}
		
		try{
			//工作表个数
			int count = workbook.getNumberOfSheets();
			result.setSheets(count);
			//遍历
			for (int i = 0; i < count; i++) {
				//工作表
				Sheet sheet = workbook.getSheetAt(i);
				//行数
				int rows = sheet.getLastRowNum();
				//列数
				int cols = 0;
				if(rows > 0){
					cols = sheet.getRow(0).getPhysicalNumberOfCells();
				}		
				//工作表数据
				SheetData tableData = new SheetData(i, rows+1, cols);
				//循环
				for (int m = 0; m <= rows; m++) {
					for (int n = 0; n < cols; n++) {
						Row row = sheet.getRow(m);
						//单元格
						Cell cell = row.getCell(n);
						//单元格内容
						String data = "";
						if(cell!=null){
							if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					                data = String.valueOf(cell.getBooleanCellValue());
				            }else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				            	if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
				            		double d = cell.getNumericCellValue();  
				                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(d); 
				                    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
				                    data = dformat.format(date);
				            	}else{
				            		 NumberFormat nf = NumberFormat.getInstance();
				                     nf.setGroupingUsed(false);//true时的格式：1,234,567,890
				                     data= nf.format(cell.getNumericCellValue());
				            	}
				            }else {
				            	cell.setCellType(Cell.CELL_TYPE_STRING);
				            	data = String.valueOf(cell.getStringCellValue());
				            }
						}
			           
						
						if (!StringUtils.isEmpty(data.trim())) {//非空
							_log.debug("读取excel数据:(" + i + "," + m + "," + n + ")=" + data);
							//新增单元格的内容
							tableData.addRowColData(m, n, data);
						}
					}
				}
				//新增工作表
				result.addTableData(tableData);			
			}
		}catch (Exception e) {
			throw new Exception("读取excel出错");
        }
		
		//关闭流
		if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
            	_log.error("readExcel IOException:",e.getMessage());
            	throw new Exception("readExcel IOException:"+e.getMessage());
            }
        }
		
		return result;
	}
	
	
	/**
	 * 校验excel
	 * @param data
	 * @param type
	 * @return
	 */
	@Override
	public ImportData checkImportExcel(ImportData data,String type,HttpServletRequest request,Map<String, Object>  dataMap){
		//返回excel
		ImportData resultData = new ImportData();
		
		for(int i=0;i<data.getSheets();i++){
			SheetData sd = data.getTableData(i);	
			//校验成功的
			SheetData successSheet = new SheetData(2*i);
			int successRows = 0;
			int successCols = sd.getCols();
			//校验失败的
			SheetData errorSheet = new SheetData(2*i+1);
			int errorRows = 0;
			int errorCols = sd.getCols()+1;
			
			//遍历		
			for(int row=1;row<sd.getRows();row++){
				//校验
				String errorRow = check(i,sd,row,type,request,dataMap);
							
				if(!StringUtils.isEmpty(errorRow)){//error
					for(int col=0;col<sd.getCols();col++){						
						String content = sd.getRowColData(row, col);
						if(StringUtils.isEmpty(content)){
							content = "";
						}
						errorSheet.addRowColData(errorRows, col, content);
					}				
					errorSheet.addRowColData(errorRows, sd.getCols(), errorRow.substring(1));
					errorRows++;
				}else if(errorRow==null){//没有对应类型时
					return null;
				}else{//success	
					for(int col=0;col<sd.getCols();col++){						
						String content = sd.getRowColData(row, col);
						if(StringUtils.isEmpty(content)){
							content = "";
						}					
						successSheet.addRowColData(successRows, col, content);
					}
					successRows++;	
				}			
			}
			
			//error
			errorSheet.setRows(errorRows);
			errorSheet.setCols(errorCols);
			resultData.addTableData(errorSheet);
			//success
			successSheet.setRows(successRows);
			successSheet.setCols(successCols);
			resultData.addTableData(successSheet);		
		}
		resultData.setSheets(data.getSheets()*2);
		
		return resultData;
	}
	
	
	/**
	 * 将校验不通过的记录写入excel文件
	 * @param data
	 * @param errorTempletPath
	 * @return
	 */
	@Override
	public String writeErrorExcel(int sheetNo,SheetData data,String errorTempletPath,String errorTargetPath){
        HSSFWorkbook wb = new HSSFWorkbook();
        InputStream is = null;
        boolean isSuccess = true;
		if(sheetNo == 0){
			try {
				is = new FileInputStream(errorTempletPath);
			} catch (FileNotFoundException e) {
				isSuccess = false;
				_log.error("writeErrorExcel FileNotFoundException:",e.getMessage());
			}
		}else{
			File file=new File(errorTempletPath);    
			if(file.exists()){//文件是否存在
				try {
					is = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					isSuccess = false;
					_log.error("writeErrorExcel FileNotFoundException:",e.getMessage());
				}
			}else{
				try {
					is = new FileInputStream(errorTempletPath);
				} catch (FileNotFoundException e) {
					isSuccess = false;
					_log.error("writeErrorExcel FileNotFoundException:",e.getMessage());
				}
			}		
		}
		
		if(is != null){
			try {
				wb = new HSSFWorkbook(is);
			} catch (IOException e) {
				isSuccess = false;
				_log.error("writeErrorExcel IOException:",e.getMessage());
			} finally { 
				//关闭流
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						isSuccess = false;
						_log.error("writeErrorExcel IOException[流关闭]:",e.getMessage());
					}
				}
	        } 	
		}else{
			isSuccess = false;
			_log.error("writeErrorExcel:读取错误文件模版出错[硬盘]："+errorTempletPath);
		}
	
    	
    	if(!isSuccess){
			return null;
		}
        
        Sheet sheet = wb.getSheetAt(sheetNo);           
        Row row = null;          
        CellStyle style = wb.createCellStyle();  
        
        for (int i = 0; i < data.getRows(); i++) { 
    		row = sheet.createRow(i+1);    		
    		for(int j = 0; j < data.getCols(); j++){
            	Cell cell = row.createCell((int) j);
            	if(j==data.getCols()-1){
            		Font font = wb.createFont();
            		font.setColor(Font.COLOR_RED);
            		style.setFont(font);
            		cell.setCellStyle(style);
            	}
            	cell.setCellValue(data.getRowColData(i, j));
            }
        }  
        		
        try{      			
	        	FileOutputStream out = new FileOutputStream( errorTargetPath); 
	    		//写excel
	    		wb.write(out);  
	            out.close();  
        }catch (Exception e){  
        	_log.error("writeErrorExcel Exception:",e.getMessage());
        }  
        int ind = errorTargetPath.lastIndexOf("/");
        String  ret = errorTargetPath.substring(ind, errorTargetPath.length());
        //UploadFileCache.put(ret, errorTargetPath);
        return ret;
	}
	
//	@Resource
//	private CustomerService customerService;

	/**
	 * 校验
	 * @param sd
	 * @param row
	 * @param type
	 * @param data 存储其他数据Map类型  参考checkCommittee
	 * @return
	 */
	public  String check(int sheetNo,SheetData sd,int row,String type,HttpServletRequest request,Map<String, Object> dataMap){
		return "";
	}
	
	
	/**
	 * 批量导入数据
	 * @param sd
	 * @param type
	 * @return
	 */
	@Override
	public  int importData(int sheetNo,SheetData sd,String type,HttpServletRequest request,Map<String, Object> dataMap){
		return 0;
	}
}
