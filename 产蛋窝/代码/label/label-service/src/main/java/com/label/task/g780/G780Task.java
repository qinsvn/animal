/**
 * 
 */
package com.label.task.g780;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.label.common.base.BaseThread;
import com.label.common.constant.RedisConstant;
import com.label.common.model.base.BsNestData;
import com.label.common.util.UtilString;
import com.label.common.util.redis.UtilRedis;
import com.label.service.web.BsNestDataService;
import com.label.service.web.BsWarnService;
import com.label.task.statistic.StatisticsTask;

/**
 * @author admin
 *
 */
public class G780Task implements BaseThread { 

	private Logger _log = LoggerFactory.getLogger(G780Task.class);
	/* (non-Javadoc)
	 * @see com.label.common.base.BaseThread#init()
	 */
 
	@Autowired
	private BsNestDataService bsNestDataService;
	@Autowired
	private BsWarnService bsWarnService;
		
	@Override
	public void init() {
		// TODO Auto-generated method stub
		new MessageThread().start();
	}
	private class MessageThread extends Thread {
		public void run() {
			while(true) {
				try {
					String message = null;
					while(UtilString.isNotEmpty(message = UtilRedis.rpop(RedisConstant.LABEL_G780_REC_DATA))) {
						_log.info("jolley>> MessageThread 解析数据: " + message);
						//1、解析接收到的数据
						BsNestData bsNestData =  bsNestDataService.analysisG780Data(message);
						
						if (bsNestData!=null) {
							//2、插入脚号数据
							//出窝更新
							if ("0".equals(bsNestData.getRemark1())) {
								bsNestData.setRemark1("1");
								bsNestDataService.updateBsNestData(bsNestData);
							}else{//进窝
								bsNestData.setRemark1("0");
								bsNestDataService.createBsNestData(bsNestData);
							}
							
							//3、记录20日统计信息
							StatisticsTask.recordStaticData(bsNestData.getRfidNum());
							
							//4、判断是否超过 4 8 12 个小时  查过就要产生告警信息
							if ("1".equals(bsNestData.getRemark1())) {
								bsWarnService.checkBsWarn(message,bsNestData);
							}
							
						}
					}
					Thread.sleep(1000l);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
