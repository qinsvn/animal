package com.label.common.constant;

/**
 * redis常量值
 * @author jolley
 */
public class RedisConstant {
	
	// ＝＝＝＝＝＝＝＝  公共管理  ＝＝＝＝＝＝＝＝
	// 临时数据
	public final static String LABEL_TEMP_ = "label:temp_";
	
	// ＝＝＝＝＝＝＝＝  session管理  ＝＝＝＝＝＝＝＝
	// 会话key
    public final static String LABEL_UPMS_SHIRO_SESSION_ID_ = "label:upms-shiro-session-id_";
    // 全局会话key
    public final static String LABEL_UPMS_SERVER_SESSION_ID_ = "label:upms-server-session-id_";
    
    // ＝＝＝＝＝＝＝＝  导出  ＝＝＝＝＝＝＝＝
    // 导出EXECL地址 > hset web_export_excel_path , fileId(随机生成)
    public final static String LABEL_EXPORT_EXCEL_PATH = "label_export_excel_path";

    // ＝＝＝＝＝＝＝＝  接收数据  ＝＝＝＝＝＝＝＝
    // 设备接收数据
    public final static String LABEL_G780_REC_DATA = "label_g780_rec_data";

    // ＝＝＝＝＝＝＝＝  接收数据  ＝＝＝＝＝＝＝＝
    //cmd数据
    public final static String LABEL_CMD_LOG_DATA = "label_cmd_log_data";

    // 告警数据
    public final static String LABEL_OVERTIME_WARN_DATA = "label_overtime_warn_data";
    
    
}
