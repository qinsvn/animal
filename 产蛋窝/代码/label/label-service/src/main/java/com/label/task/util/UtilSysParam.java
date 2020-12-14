/**
 * 
 */
package com.label.task.util;

import org.springframework.stereotype.Service;

import com.label.common.model.base.BsNestparam;
import com.label.common.util.UtilSpringContext;
import com.label.dao.auto.BsNestparamMapper;

/**
 * @author Administrator
 *
 */
@Service
public class UtilSysParam {

	private static BsNestparam bsNestparam;
	
	public static BsNestparam getBsNestparam() {
		if(bsNestparam==null){
			BsNestparam nestparam = new BsNestparam();
			nestparam.setId(1);
			BsNestparamMapper bsNestparamMapper = (BsNestparamMapper) UtilSpringContext.getBean("bsNestparamMapper");
			 nestparam = bsNestparamMapper.selectByPrimaryKey(1);
			UtilSysParam.setBsNestparam(nestparam);
		}
		return bsNestparam;
	}

	public static void setBsNestparam(BsNestparam bsNestparam) {
		UtilSysParam.bsNestparam = bsNestparam;
	}
	
	
}
