/**
 * 
 */
package com.label.service.web.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.label.common.entity.CollectionData;
import com.label.common.entity.ResultApi;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.AggData;
import com.label.common.model.base.AggDataExample;
import com.label.common.model.base.StockData;
import com.label.common.model.base.StockDataExample;
import com.label.common.util.UtilJson;
import com.label.dao.auto.AggDataMapper;
import com.label.dao.auto.StockDataMapper;
import com.label.service.web.AggService;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
@Service 
@Transactional(rollbackFor=Exception.class) 
public class AggServiceImpl extends CommonServiceImpl implements AggService {

	private Logger _log = LoggerFactory.getLogger(getClass());
	@Autowired
	AggDataMapper aggDataMapper;
	@Autowired
	StockDataMapper stockDataMapper;
	
	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) {
		// TODO Auto-generated method stub
		_log.info("查询蛋类列表，参数map：{}",UtilJson.Obj2Str(map));
		return breedDataCustomMapper.selectAggs(map);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		_log.info("chickenDataMapper删除蛋类id：{}",id);
		AggData aggData = new AggData();
		aggData.setId(id);
		aggData.setStatus(1);
		aggDataMapper.updateByPrimaryKeySelective(aggData );
		
		StockData stockData = new StockData();
		stockData.setStatus(1);//减少库存
		StockDataExample example = new StockDataExample();
		example.createCriteria().andDataTypeEqualTo(1).andDataIdEqualTo((Integer)aggData.getId());
		stockDataMapper.updateByExampleSelective(stockData, example);
		
		return 1;
	}

	@Override
	public void collectionData(ResultApi resultApi,CollectionData data) throws Exception
	{
			AggDataExample example = new AggDataExample();
			example.createCriteria().andStatusNotEqualTo(1).andBarCodeEqualTo(data.getCode());
			if (aggDataMapper.selectByExample(example ).size()==0){
				AdminUser  user = UserUtil.getUserInfoByAccount(data.getUserName());
				AggData aggData = new AggData();
				aggData.setBarCode(data.getCode());
				aggData.setCreateUserId(user.getId());
				aggData.setOrgId(user.getFdConpanyId());
				aggData.setStatus(0);
				Date now = new Date();
				aggData.setCreateTime(now);
				int ret = aggDataMapper.insertSelective(aggData);
				//库存
				if (ret>0) {
					StockData stockData = new StockData();
					stockData.setDataId(aggData.getId());
					stockData.setDataType(1);
					stockData.setStatus(0);
					ret = stockDataMapper.insert(stockData );
					if (ret==0) {
						resultApi.set(ResultApi.FAILCODE, "入库错误！");
						throw new Exception("蛋类入库错误！");
					}else{
						resultApi.set(ResultApi.SUCCESSCODE, ResultApi.SUCCESSMSG);
					}
				}else{
					resultApi.set(ResultApi.FAILCODE, "采集错误！");
					throw new Exception("蛋类采集错误！");
				}
			}else{
				resultApi.set(ResultApi.FAILCODE, "重复采集！");
			}
	}
}
