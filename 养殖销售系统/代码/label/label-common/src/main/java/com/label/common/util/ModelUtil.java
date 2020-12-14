package com.label.common.util;

import java.util.HashMap;
import java.util.Map;

public class ModelUtil {
  
    public static String collection_chicken="1001";
    public static String collection_agg="1002";
    public static String collection_castration="1003";
    public static String collection_immune="1004";
    public static String collection_agg_substandard="1005";
    public static String collection_ck_substandard="1006";
    public static String collection_agg_order="1007";
    public static String collection_ck_order="1008";
   
    public static Map<String,String> map = new HashMap<>();
    static{
        map.put("1001" ,"禽类入库");
        map.put("1002" ,"蛋类入库");
        map.put("1003" ,"阉割采集");
        map.put("1004" ,"免疫采集");
        map.put("1005" ,"蛋类次品采集");
        map.put("1006" ,"禽类次品采集");
        map.put("1007" ,"蛋类出库采集");
        map.put("1008" ,"禽类出库采集");
        map.put("1009","备用模式1");
        map.put("1010","备用模式2");
        map.put("1011","备用模式3");
        map.put("1012","备用模式4");
        map.put("1013","备用模式5");
        map.put("1014","备用模式6");
        map.put("1015","备用模式7");
        map.put("1016","备用模式8");
    }

}
