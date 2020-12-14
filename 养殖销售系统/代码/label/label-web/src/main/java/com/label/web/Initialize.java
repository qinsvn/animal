package com.label.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.label.common.base.BaseInterface;

/**
 * 系统接口
 * @author jolley
 */
public class Initialize implements BaseInterface {

	private static Logger _log = LoggerFactory.getLogger(Initialize.class);
	
	@Override
	public void init() {
		_log.info("jolley>> label-web 系统初始化");
		
	}

}
