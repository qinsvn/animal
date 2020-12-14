/**
 * 
 */
package com.label.task.user;

import com.label.common.base.BaseThread;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
public class BaseInputTask implements BaseThread {

	@Override
	public void init() {
		UserUtil.initDeptsInfo();
		UserUtil.initUsersInfo();
	}
}
