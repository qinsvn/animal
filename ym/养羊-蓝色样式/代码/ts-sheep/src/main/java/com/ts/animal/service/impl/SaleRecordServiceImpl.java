package com.ts.animal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ts.animal.constant.AnimalConstant;
import com.ts.animal.dao.ExSaleRecordDao;
import com.ts.animal.dao.InputsDao;
import com.ts.animal.dao.SaleRecordDao;
import com.ts.animal.domain.InputsDO;
import com.ts.animal.domain.SaleRecordDO;
import com.ts.animal.service.SaleRecordService;
import com.ts.common.utils.CaseFormatUtil;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;


@Transactional
@Service
public class SaleRecordServiceImpl implements SaleRecordService {
	@Autowired
	private SaleRecordDao saleRecordDao;
	@Autowired
	private ExSaleRecordDao exSaleRecordDao;
	@Autowired
	private InputsDao inputsDao;
	
	@Override
	public SaleRecordDO get(Integer id){
		return saleRecordDao.get(id);
	}

	@Override
	public List<SaleRecordDO> list(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return saleRecordDao.list(map);
	}
	
	@Override
	public List<Map<String, Object> > exList(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return CaseFormatUtil.lowerUnderscore(exSaleRecordDao.list(map));
	}
	
	@Override
	public int count(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return saleRecordDao.count(map);
	}

	@Override
	public int save(Result result,SaleRecordDO saleRecord){
		String inputsIds = saleRecord.getRemark1();
		String [] inputsIdArray = inputsIds.substring(1).split(",");
		for (String idStr : inputsIdArray) {
			 InputsDO inputsDO = inputsDao.get(Integer.valueOf(idStr));
			 saleRecord.setDeptId(inputsDO.getDeptId());
			 saleRecord.setSaleNum(String.valueOf(System.currentTimeMillis()));
			 saleRecord.setInputsId(inputsDO.getId());
			 saleRecord.setNum(inputsDO.getNum());
			 saleRecord.setBatchNum(inputsDO.getBatchNum());
			 saleRecord.setTypeName(inputsDO.getTypeName());
			 saleRecord.setVarietyName(inputsDO.getVarietyName());
			 saleRecord.setCreateUser((int)ShiroUtils.getUserId().longValue());
			 saleRecord.setDeptId(inputsDO.getDeptId());
			 saleRecord.setCreateTime(new Date());
			 saleRecord.setRemark1(null);
			 saleRecordDao.save(saleRecord);
			 inputsDO.setStatus(AnimalConstant.INPUTS_STATUS_SALED);
			 inputsDao.update(inputsDO);
		}
		return 1;
	}
	
	@Override
	public int saveByNum(Result result,SaleRecordDO saleRecord){
		int ret =0;
		Map<String, Object> map = Maps.newHashMap();
		map.put("num", saleRecord.getNum());
		List<InputsDO>  list = inputsDao.list(map);
		if(list.size()==0)
		{
			 result.set(Result.FAIL, "不存在该产品【"+saleRecord.getNum()+"】", null);
			 return ret;
		}else{
			InputsDO inputsDO = list.get(0);
			if (!AnimalConstant.INPUTS_STATUS_FEEDING.equals(inputsDO.getStatus())) {
				result.set(Result.FAIL, "不存在该产品【"+saleRecord.getNum()+"】", null);
				 return ret;
			}
			
			 saleRecord.setDeptId(inputsDO.getDeptId());
			 saleRecord.setSaleNum(String.valueOf(System.currentTimeMillis()));
			 saleRecord.setInputsId(inputsDO.getId());
			 saleRecord.setNum(inputsDO.getNum());
			 saleRecord.setBatchNum(inputsDO.getBatchNum());
			 saleRecord.setTypeName(inputsDO.getTypeName());
			 saleRecord.setVarietyName(inputsDO.getVarietyName());
			 saleRecord.setCreateUser((int)ShiroUtils.getUserId().longValue());
			 saleRecord.setDeptId(inputsDO.getDeptId());
			 saleRecord.setCreateTime(new Date());
			 saleRecord.setRemark1(null);
			 saleRecordDao.save(saleRecord);
			 inputsDO.setStatus(AnimalConstant.INPUTS_STATUS_SALED);
			 inputsDao.update(inputsDO);
			return 1;
		}
	}
	
	@Override
	public int update(Result result,SaleRecordDO saleRecord){
		saleRecord.setUpdateUser((int)ShiroUtils.getUserId().longValue());
		saleRecord.setUpdateTime(new Date());
		return saleRecordDao.update(saleRecord);
	}
	
	@Override
	public int remove(Integer id){
		return saleRecordDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return saleRecordDao.batchRemove(ids);
	}

}
