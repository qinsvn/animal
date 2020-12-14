package com.label.task.statistic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.label.common.base.BaseThread;
import com.label.common.model.base.BsNestData20;
import com.label.common.model.base.BsNestData20Example;
import com.label.common.model.base.BsStaRecord;
import com.label.common.model.base.BsStaRecordExample;
import com.label.common.util.UtilDate;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilSpringContext;
import com.label.dao.auto.BsNestData20Mapper;
import com.label.dao.auto.BsNestDataMapper;
import com.label.dao.auto.BsStaRecordMapper;
import com.label.dao.custom.BsNestDataCustomMapper;

public class StatisticsTask implements BaseThread {

	private static  Map<String, BsStaRecord> DATA;
	private static Object lock = new Object();
	
	 @Autowired
	private  BsStaRecordMapper bsStaRecordMapper;
	 @Autowired
	private  BsNestDataMapper bsNestDataMapper;
	 @Autowired
	private  BsNestData20Mapper bsNestData20Mapper;
	 @Autowired
	private  BsNestDataCustomMapper bsNestDataCustomMapper;
	
	private static  Logger _log = LoggerFactory.getLogger(StatisticsTask.class);
	
	@Override
	public void init() {
		synchronized (lock) {
			try {
				_log.info("jolley ===>加载20日统计数据到缓存-开始");
				DATA  = new HashMap<String, BsStaRecord>();
				BsStaRecordExample example = new BsStaRecordExample();
				List<BsStaRecord> rows = bsStaRecordMapper.selectByExample(example);
				for (BsStaRecord data : rows) {
					DATA.put(data.getRfidNum(), data);
				}
				_log.info("jolley ===>加载20日统计数据到缓存-结束 DATA：{}",UtilJson.Obj2Str(DATA));
			} catch (Exception e) {
				_log.info("jolley ===>20日统计数据到缓存出错：{}",e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 每插入一条数据 需要计算一次20统计时间
	 * @param rfidNum
	 */
	public static void recordStaticData(String rfidNum){
		synchronized (lock) {
			BsStaRecord bsStaRecord = DATA.get(rfidNum);
			if (bsStaRecord==null) {
				Date now = new Date();
				Date end = UtilDate.getDateAfter(now , 19);
				String statiText = UtilDate.formatDate("yyyy年MM月dd号", now)+"---"+ UtilDate.formatDate("yyyy年MM月dd号 ", end);
				bsStaRecord = new BsStaRecord();
				bsStaRecord.setRfidNum(rfidNum);
				bsStaRecord.setNextStatisticsDate(end);		
				bsStaRecord.setStatisticsText(statiText);
				BsStaRecordMapper bsStaRecordMapper = (BsStaRecordMapper) UtilSpringContext.getBean("bsStaRecordMapper");
				bsStaRecordMapper.insertSelective(bsStaRecord);
				_log.info("jolley ===>第一次记录脚号，登记统计时间 DATA：{}",UtilJson.Obj2Str(bsStaRecord));
				DATA.put(rfidNum, bsStaRecord);
			}
		}
	}
	
	/**
	 * 每天执行 统计任务。每个脚号每20天统计一次   
	 */
	public void action(){
		Date now = new Date();
		Date startDate = UtilDate.getDateBefore(now, 20);//这里其实不够严谨，但是定时任务在23.59分执行 所以不考虑极端数据
		_log.info("jolley ===>计算统计时间范围从{}到{}",UtilDate.formatDate("yyyy-MM-dd HH:mm:ss", startDate),UtilDate.formatDate("yyyy-MM-dd HH:mm:ss", now));
		 for (String rfidNum:DATA.keySet()) {
				try {
					//获取需要统计的脚号信息 包括脚号、统计时间范围，下一次统计时间
					BsStaRecord bsStaRecord = DATA.get(rfidNum);
					Date date = bsStaRecord.getNextStatisticsDate();
					//对比如果统计时间和当天时间是同一天则进行统计
					if (UtilDate.getIntervalDays(now, date)==0) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("startTime", startDate);
						map.put("endTime", now);
						map.put("rfidNum", rfidNum);
						//获取当前20天范围内的所有脚号 的 统计数据
						Map<String, Object> ret =  bsNestDataCustomMapper.select20Static(map);
						
						_log.info("jolley ===>获取脚号[{}]当前20天范围内的所有脚号 的 统计数据:{}",UtilJson.Obj2Str(map),UtilJson.Obj2Str(ret));
						if (ret!=null) {
							Integer workshopId = (Integer) ret.get("workshopId");//厂房号
							Double eatMtWeightDouble = (Double) ret.get("eatMtWeight");//吃重
							Double inBodyWeightDouble = (Double) ret.get("inBodyWeight");//开始重
							Double outBodyWeightDouble =  (Double) ret.get("outBodyWeight");//结束重
							Double incBodyWeightDouble = (Double) ret.get("incBodyWeight");//增加重
						
							//肉料比
							String ratio  =null;
							if (incBodyWeightDouble!=null&&eatMtWeightDouble!=null&&incBodyWeightDouble!=0&&eatMtWeightDouble!=0) {
								ratio  = String.format("%.2f", (incBodyWeightDouble*100/eatMtWeightDouble))+"%";
							}
							//肉料比
							//设置、写入统计数据
							BsNestData20 bsNestData20 = new BsNestData20();
							bsNestData20.setWorkshopId(workshopId);
							bsNestData20.setRfidNum(rfidNum);
							bsNestData20.setDateRange(bsStaRecord.getStatisticsText());
							bsNestData20.setStartWeight(inBodyWeightDouble);
							bsNestData20.setEndWeight(outBodyWeightDouble);
							bsNestData20.setIncBodyWeight(incBodyWeightDouble);
							bsNestData20.setEatMtWeight(eatMtWeightDouble);
							bsNestData20.setMtBodyRatio(ratio);
							//bsNestData20.setLastUpdateTime(new Date());
							//获取最大统计编号
							Integer order = bsNestDataCustomMapper.maxOrder20(rfidNum);
							if (order==null||order==0) {
								bsNestData20.setInOrder(1);
							}else{
								bsNestData20.setInOrder(order+1);
							}
							bsNestData20Mapper.insertSelective(bsNestData20);
							
							//更新统计的更新时间   列表排序用到
							BsNestData20 updateNestData  = new BsNestData20();
							updateNestData.setLastUpdateTime(now);
							BsNestData20Example updateNestCondition = new BsNestData20Example();
							updateNestCondition.createCriteria().andRfidNumEqualTo(rfidNum);
							bsNestData20Mapper.updateByExampleSelective(updateNestData , updateNestCondition );
							
							//更新统计 统计的脚号信息til
							Date start = UtilDate.getDateAfter(now, 1);
							Date end = UtilDate.getDateAfter(now, 20);
							String text = UtilDate.formatDate("yyyy年MM月dd号", start)+"--"+UtilDate.formatDate("yyyy年MM月dd号", end);
							bsStaRecord.setNextStatisticsDate(end);
							bsStaRecord.setStatisticsText(text);
							bsStaRecord.setRfidNum(rfidNum);
							bsStaRecordMapper.updateByPrimaryKeySelective(bsStaRecord);
						}else{
							_log.info("jolley ===>不存在脚号:{}",rfidNum);
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
		    }//end for
		 //所有统计完了  重新加载
		 init();
	}
	
	public static void main(String[] args) {
	}
}
