package com.ts.animal.constant;

public class AnimalConstant {

    //投入品状态
    public static String INPUTS_STATUS_FEEDING = "0";//饲养中
    public static String INPUTS_STATUS_SALED = "1";//售卖
    public static String INPUTS_STATUS_SUBSTANDARD = "2";//次品

    //审核状态
    public static String SUBSTANDAR_STATUS_NUNAPPROVAL = "0";//未审核
    public static String SUBSTANDAR_STATUS_APPROVAL = "1";//已审核

    //工作模式
    public static String WORK_MOEL_1001 = "1001";//入库采集
    public static String WORK_MOEL_1002 = "1002";//检疫采集
    public static String WORK_MOEL_1003 = "1003";//检验采集
    public static String WORK_MOEL_1004 = "1004";//耗损采集
    public static String WORK_MOEL_1005 = "1005";//出库采集


    public static String DICT_ACCESS_DIRECTION = "time_boundary";//出栏数据字典 key
    public static String ACCESS_IN = "in";//进
    public static String ACCESS_OUT = "out";//出
    public static String DICT_ACCESS_INTERVAL = "collection_interval";//数据采集时间间隔

//    redis
    public static String ROOT = "ts:animal:";//根
    public static String REDIS_DICT_ACCESS_INTERVAL_KEY = ROOT+"expire:key:";//数采集数据key
}
