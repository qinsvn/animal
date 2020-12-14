/**
 * 
 */
package com.label.task.input;

import com.label.common.base.BaseThread;
import com.label.util.BaseInputdataUtil;

/**
 * @author admin
 *
 */
public class BaseInputdataTask implements BaseThread {

	@Override
	public void init() {
		BaseInputdataUtil.initBaseInputdata();
	}
}
