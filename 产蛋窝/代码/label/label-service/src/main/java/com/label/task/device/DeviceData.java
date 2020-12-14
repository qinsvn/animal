package com.label.task.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.label.common.base.BaseThread;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilSpringContext;
import com.label.dao.custom.BsDeviceCustomMapper;

public class DeviceData implements BaseThread {

	private static  Map<String, Map<String, Object>> DATA;
	private static Object lock = new Object();

	private static  Logger _log = LoggerFactory.getLogger(DeviceData.class);
	
	@Override
	public void init() {
		initData();
	}

	public static void initData(){
		synchronized (lock) {
			try {
				_log.info("jolley ===>加载设备数据到缓存-开始");
				DATA  = new HashMap<String, Map<String, Object>>();
				BsDeviceCustomMapper bsDeviceCustomMapper = (BsDeviceCustomMapper) UtilSpringContext.getBean("bsDeviceCustomMapper");
				List<Map<String, Object> > rows = bsDeviceCustomMapper.select(null);
				for (Map<String, Object> deviceData : rows) {
					DATA.put(deviceData.get("deviceNum").toString(), deviceData);
				}
				_log.info("jolley ===>加载设备数据到缓存-结束 DATA：{}",UtilJson.Obj2Str(DATA));
			} catch (Exception e) {
				_log.info("jolley ===>加载设备数据到缓存出错：{}",e);
				e.printStackTrace();
			}
		}
	}
	
	public static String getData(String deviceNum,String word){
		synchronized (lock) {
			Map<String, Object> deviceData = DATA.get(deviceNum);
			_log.info("jolley ===>获取设备数据 deviceNum:{},word:{},deviceData：{}",deviceNum,word,UtilJson.Obj2Str(DATA));
			if (deviceData!=null) {
				Object obj = deviceData.get(word);
				if (obj!=null) {
					return obj.toString();
				}
			}
			return null;
		}
	}
}
