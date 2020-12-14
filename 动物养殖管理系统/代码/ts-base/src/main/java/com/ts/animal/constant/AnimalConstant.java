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
}
