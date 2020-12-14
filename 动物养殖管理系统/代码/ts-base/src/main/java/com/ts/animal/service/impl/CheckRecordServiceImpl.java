package com.ts.animal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ts.animal.constant.AnimalConstant;
import com.ts.animal.dao.CheckRecordDao;
import com.ts.animal.dao.InputsDao;
import com.ts.animal.domain.CheckRecordDO;
import com.ts.animal.domain.InputsDO;
import com.ts.animal.service.CheckRecordService;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;


@Transactional
@Service
public class CheckRecordServiceImpl implements CheckRecordService {
	@Autowired
	private CheckRecordDao checkRecordDao;
	@Autowired
	private InputsDao inputsDao;
	
	@Override
	public CheckRecordDO get(Integer id){
		return checkRecordDao.get(id);
	}
	
	@Override
	public List<CheckRecordDO> list(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return checkRecordDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return checkRecordDao.count(map);
	}
	
	@Override
	public int save(Result result,CheckRecordDO checkRecord){
		checkRecord.setCreateUser((int)ShiroUtils.getUserId().longValue());
		String inputsIds = checkRecord.getRemark1();
		String [] inputsIdArray = inputsIds.substring(1).split(",");
		for (String idStr : inputsIdArray) {
			 InputsDO inputsDO = inputsDao.get(Integer.valueOf(idStr));
			 checkRecord.setId(null);
			 checkRecord.setDeptId(inputsDO.getDeptId());
			 checkRecord.setInputsId(inputsDO.getId());
			 checkRecord.setNum(inputsDO.getNum());
			 checkRecord.setBatchNum(inputsDO.getBatchNum());
			 checkRecord.setTypeName(inputsDO.getTypeName());
			 checkRecord.setVarietyName(inputsDO.getVarietyName());
			checkRecord.setCreateUser((int)ShiroUtils.getUserId().longValue());
			checkRecord.setDeptId(inputsDO.getDeptId());
			checkRecord.setCreateTime(new Date());
			checkRecord.setRemark1(null);
			checkRecordDao.save(checkRecord);
			if (checkRecord.getCheckWeight()!=null) {
				inputsDO.setCurWeight(checkRecord.getCheckWeight());
				inputsDao.update(inputsDO);
			}
		}
		return 1;
	}
	
	@Override
	public int saveByNum(Result result,CheckRecordDO checkRecord){
		int ret =0;
		Map<String, Object> map = Maps.newHashMap();
		map.put("num", checkRecord.getNum());
		List<InputsDO>  list = inputsDao.list(map);
		if(list.size()==0)
		{
			 result.set(Result.FAIL, "不存在投入品编号【"+checkRecord.getNum()+"】", null);
			 return ret;
		}else{
			InputsDO inputsDO = list.get(0);
			if (!AnimalConstant.INPUTS_STATUS_FEEDING.equals(inputsDO.getStatus())) {
				result.set(Result.FAIL, "该投入品【"+checkRecord.getNum()+"】不在饲养状态", null);
				 return ret;
			}
			
			 checkRecord.setId(null);
			 checkRecord.setDeptId(inputsDO.getDeptId());
			 checkRecord.setInputsId(inputsDO.getId());
			 checkRecord.setNum(inputsDO.getNum());
			 checkRecord.setBatchNum(inputsDO.getBatchNum());
			 checkRecord.setTypeName(inputsDO.getTypeName());
			 checkRecord.setVarietyName(inputsDO.getVarietyName());
			checkRecord.setCreateUser((int)ShiroUtils.getUserId().longValue());
			checkRecord.setDeptId(inputsDO.getDeptId());
			checkRecord.setCreateTime(new Date());
			checkRecord.setRemark1(null);
			ret = checkRecordDao.save(checkRecord);
			if (checkRecord.getCheckWeight()!=null) {
				inputsDO.setCurWeight(checkRecord.getCheckWeight());
				inputsDao.update(inputsDO);
			}
		}
		return ret;
	}
	
	@Override
	public int update(Result result,CheckRecordDO checkRecord){
		checkRecord.setUpdateUser((int)ShiroUtils.getUserId().longValue());
		checkRecord.setUpdateTime(new Date());
		return checkRecordDao.update(checkRecord);
	}
	
	@Override
	public int remove(Integer id){
		return checkRecordDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return checkRecordDao.batchRemove(ids);
	}
	
}
