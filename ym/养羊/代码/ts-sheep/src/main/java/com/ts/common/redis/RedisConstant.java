package com.ts.common.redis;

/**
 * redis常量值
 * @author jolley
 */
public class RedisConstant {
	
	// ＝＝＝＝＝＝＝＝  公共管理  ＝＝＝＝＝＝＝＝
	
	public final static String PREFIX="base:";
	// 临时数据
	public final static String BASE_TEMP_ = PREFIX+"temp:";
	
    // 下载文件 数据内容key
    public final static String DOWNLOAD_DATA_CONTENT_KEY = PREFIX+ "download_file_contents:";
    
    //下载文件 数据路径key
    public final static  String DOWNLOAD_DATA_KEY = PREFIX+ "download_file_paths:";
}
