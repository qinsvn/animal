package com.ts.common.constant;

public class CommonConstant {
    //演示系统账户
    public static String DEMO_ACCOUNT = "test";
    //自动去除表前缀
    public static String AUTO_REOMVE_PRE = "true";
    //停止计划任务
    public static String STATUS_RUNNING_STOP = "stop";
    //开启计划任务
    public static String STATUS_RUNNING_START = "start";
    //通知公告阅读状态-未读
    public static String OA_NOTIFY_READ_NO = "0";
    //通知公告阅读状态-已读
    public static int OA_NOTIFY_READ_YES = 1;
    //部门根节点id
    public static Long DEPT_ROOT_ID = 0l;
    //缓存方式
    public static String CACHE_TYPE_REDIS ="redis";

    public static String LOG_ERROR = "error";

    public static String LOGIN_FROM = "from";

    public static String LOGIN_FROM_PC = "pc";

    public static String LOGIN_FROM_MOBILE = "mobile";

    //导出配置
    public static String EXPORT_ID_FLAG = "exportFileId"; //导出文件id
    public static String EXPORT_FILENAME_FLAG = "exportFileName"; //导出文件id
    public static String EXPORT_HEADER_FLAG = "exportHeader";//导出表头标识
    public static String EXPORT_KEYS_FLAG = "exportkeys";//导出字段标识
    public static String EXPORT_SPLIT_FLAG = "exportSplit";//字段或者表头分隔符
    
    
    
}
