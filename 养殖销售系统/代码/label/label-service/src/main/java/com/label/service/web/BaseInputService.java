package com.label.service.web;

import com.label.common.constant.UpmsResult;
import com.label.common.model.base.ChickenBaseinput;
import com.label.common.model.base.ChickenType;
import com.label.common.model.base.ImmuneBaseinput;

/**
 * 设备service
 * @author jolley
 *
 */
public interface BaseInputService {

	/**
	 * 获取基础数据信息
	 */
	UpmsResult getBaseInputdata();
	/**
	 * 保存禽类基础数据信息
	 */
	UpmsResult saveChickenType(ChickenType chickenType);
	/**
	 * 保存禽类录入基础信息
	 */
	UpmsResult saveChickenBaseinput(ChickenBaseinput chickenBaseinput);
	/**
	 * 保存疫苗基础录入信息
	 */
	UpmsResult saveImmuneBaseinput(ImmuneBaseinput immuneBaseinput) ;
	
	
}
