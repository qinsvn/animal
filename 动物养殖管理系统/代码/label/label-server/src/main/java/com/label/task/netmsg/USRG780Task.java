/**
 * 
 */
package com.label.task.netmsg;

import com.label.common.base.BaseThread;
import com.label.server.netmsg.mina.MINAMsgServerStarter;

/**
 * @author admin
 *
 */
public class USRG780Task implements BaseThread {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		MINAMsgServerStarter.start();
	}

}
