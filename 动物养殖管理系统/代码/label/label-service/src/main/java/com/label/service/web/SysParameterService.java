package com.label.service.web;

import java.util.List;

import com.label.common.model.base.SysParameter;

public interface SysParameterService {

	/**
	 * 修改
	 * @param result
	 * @param cid
	 * @param data
	 */
	public void alter(int cid, String data);
	
	/**
	 * 列表
	 * @param result
	 * @param cid
	 * @param category
	 * @param syskeys
	 */
	public List<SysParameter> list(int cid, String category,String appid, String syskeys);
}
