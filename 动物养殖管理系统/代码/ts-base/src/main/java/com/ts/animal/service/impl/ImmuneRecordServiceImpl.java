package com.ts.animal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ts.animal.constant.AnimalConstant;
import com.ts.animal.dao.ImmuneRecordDao;
import com.ts.animal.dao.InputsDao;
import com.ts.animal.domain.ImmuneRecordDO;
import com.ts.animal.domain.InputsDO;
import com.ts.animal.service.ImmuneRecordService;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;
import com.ts.common.utils.StringUtils;


@Transactional
@Service
public class ImmuneRecordServiceImpl implements ImmuneRecordService {
	@Autowired
	private ImmuneRecordDao immuneRecordDao;
	@Autowired
	private InputsDao inputsDao;
	
	@Override
	public ImmuneRecordDO get(Integer id){
		return immuneRecordDao.get(id);
	}
	
	@Override
	public List<ImmuneRecordDO> list(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return immuneRecordDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return immuneRecordDao.count(map);
	}
	
	@Override
	public int save(Result result,ImmuneRecordDO immuneRecord){
		String inputsIds = immuneRecord.getRemark1();
		String [] inputsIdArray = inputsIds.substring(1).split(",");
		for (String idStr : inputsIdArray) {
			 InputsDO inputsDO = inputsDao.get(Integer.valueOf(idStr));
			 immuneRecord.setId(null);
			 immuneRecord.setDeptId(inputsDO.getDeptId());
			 immuneRecord.setInputsId(inputsDO.getId());
			 immuneRecord.setNum(inputsDO.getNum());
			 immuneRecord.setBatchNum(inputsDO.getBatchNum());
			 immuneRecord.setTypeName(inputsDO.getTypeName());
			 immuneRecord.setVarietyName(inputsDO.getVarietyName());
			immuneRecord.setCreateUser((int)ShiroUtils.getUserId().longValue());
			immuneRecord.setDeptId(inputsDO.getDeptId());
			immuneRecord.setCreateTime(new Date());
			immuneRecord.setRemark1(null);
			immuneRecordDao.save(immuneRecord);
		}
		return 1 ;
	}
	

	@Override
	public int saveByNum(Result result,ImmuneRecordDO immuneRecord){
		int ret =0;
		if (StringUtils.isEmpty(immuneRecord.getImmuneDrug())) {
			 result.set(Result.FAIL, "请填写检疫药物名称", null);
			 return ret;
		}
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("num", immuneRecord.getNum());
		List<InputsDO>  list = inputsDao.list(map);
		if(list.size()==0)
		{
			 result.set(Result.FAIL, "不存在投入品编号【"+immuneRecord.getNum()+"】", null);
			 return ret;
		}else{
			InputsDO inputsDO = list.get(0);
			if (!AnimalConstant.INPUTS_STATUS_FEEDING.equals(inputsDO.getStatus())) {
				result.set(Result.FAIL, "该投入品【"+immuneRecord.getNum()+"】不在饲养状态", null);
				 return ret;
			}
			
				map = Maps.newHashMap();
				map.put("num", immuneRecord.getNum());
				map.put("immuneDrug", immuneRecord.getImmuneDrug());
				map.put("immuneAge", immuneRecord.getImmuneAge());
				List<ImmuneRecordDO>  list0 = immuneRecordDao.list(map);
				if(list0.size()!=0)
				{
					 result.set(Result.FAIL, "编号【"+immuneRecord.getNum()+"】已经检疫过药物【"+immuneRecord.getImmuneDrug()+"】", null);
					 return ret;
				}
				
			 immuneRecord.setId(null);
			 immuneRecord.setDeptId(inputsDO.getDeptId());
			 immuneRecord.setInputsId(inputsDO.getId());
			 immuneRecord.setNum(inputsDO.getNum());
			 immuneRecord.setBatchNum(inputsDO.getBatchNum());
			 immuneRecord.setTypeName(inputsDO.getTypeName());
			 immuneRecord.setVarietyName(inputsDO.getVarietyName());
			immuneRecord.setCreateUser((int)ShiroUtils.getUserId().longValue());
			immuneRecord.setDeptId(inputsDO.getDeptId());
			immuneRecord.setCreateTime(new Date());
			immuneRecord.setRemark1(null);
			immuneRecordDao.save(immuneRecord);
		}
		return ret ;
	}
	
	@Override
	public int update(Result result,ImmuneRecordDO immuneRecord){
		immuneRecord.setUpdateUser((int)ShiroUtils.getUserId().longValue());
		immuneRecord.setUpdateTime(new Date());
		return immuneRecordDao.update(immuneRecord);
	}
	
	@Override
	public int remove(Integer id){
		return immuneRecordDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return immuneRecordDao.batchRemove(ids);
	}
	
}
