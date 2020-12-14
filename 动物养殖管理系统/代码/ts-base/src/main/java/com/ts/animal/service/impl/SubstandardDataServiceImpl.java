package com.ts.animal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ts.animal.constant.AnimalConstant;
import com.ts.animal.dao.InputsDao;
import com.ts.animal.dao.SubstandardDataDao;
import com.ts.animal.domain.InputsDO;
import com.ts.animal.domain.SubstandardDataDO;
import com.ts.animal.service.SubstandardDataService;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;


@Transactional
@Service
public class SubstandardDataServiceImpl implements SubstandardDataService {
	@Autowired
	private SubstandardDataDao substandardDataDao;
	@Autowired
	private InputsDao inputsDao;
	
	@Override
	public SubstandardDataDO get(Integer id){
		return substandardDataDao.get(id);
	}
	
	@Override
	public List<SubstandardDataDO> list(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return substandardDataDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return substandardDataDao.count(map);
	}

	@Override
	public int save(Result result,SubstandardDataDO substandardData){
		String inputsIds = substandardData.getRemark1();
		String [] inputsIdArray = inputsIds.substring(1).split(",");
		for (String idStr : inputsIdArray) {
			 InputsDO inputsDO = inputsDao.get(Integer.valueOf(idStr));
			 substandardData.setDeptId(inputsDO.getDeptId());
			 substandardData.setInputsId(inputsDO.getId());
			 substandardData.setNum(inputsDO.getNum());
			 substandardData.setBatchNum(inputsDO.getBatchNum());
			 substandardData.setTypeName(inputsDO.getTypeName());
			 substandardData.setVarietyName(inputsDO.getVarietyName());
			 substandardData.setCreateUser((int)ShiroUtils.getUserId().longValue());
			substandardData.setDeptId(inputsDO.getDeptId());
			substandardData.setCreateTime(new Date());
			substandardData.setRemark1(null);
			substandardData.setStatus(AnimalConstant.SUBSTANDAR_STATUS_NUNAPPROVAL);
			substandardDataDao.save(substandardData);
		}
		return 1;
	}

	@Override
	public int saveByNum(Result result,SubstandardDataDO substandardData){
		int ret =0;
		Map<String, Object> map = Maps.newHashMap();
		map.put("num", substandardData.getNum());
		List<InputsDO>  list = inputsDao.list(map);
		if(list.size()==0)
		{
			 result.set(Result.FAIL, "不存在投入品编号【"+substandardData.getNum()+"】", null);
			 return ret;
		}else{
			
			InputsDO inputsDO = list.get(0);
			if (!AnimalConstant.INPUTS_STATUS_FEEDING.equals(inputsDO.getStatus())) {
				result.set(Result.FAIL, "该投入品【"+substandardData.getNum()+"】不在饲养状态", null);
				 return ret;
			}
			
			map = Maps.newHashMap();
			map.put("num", substandardData.getNum());
			List<SubstandardDataDO>  list0 = substandardDataDao.list(map);
			if(list0.size()!=0)
			{
				 result.set(Result.FAIL, "编号【"+substandardData.getNum()+"】不能重复加入耗损", null);
				 return ret;
			}
			 substandardData.setDeptId(inputsDO.getDeptId());
			 substandardData.setInputsId(inputsDO.getId());
			 substandardData.setNum(inputsDO.getNum());
			 substandardData.setBatchNum(inputsDO.getBatchNum());
			 substandardData.setTypeName(inputsDO.getTypeName());
			 substandardData.setVarietyName(inputsDO.getVarietyName());
			 substandardData.setCreateUser((int)ShiroUtils.getUserId().longValue());
			substandardData.setDeptId(inputsDO.getDeptId());
			substandardData.setCreateTime(new Date());
			substandardData.setRemark1(null);
			substandardData.setStatus(AnimalConstant.SUBSTANDAR_STATUS_NUNAPPROVAL);
			substandardDataDao.save(substandardData);
		}
		return 1;
	}
	
	@Override
	public int update(Result result,SubstandardDataDO substandardData){
		substandardData.setUpdateUser((int)ShiroUtils.getUserId().longValue());
		substandardData.setUpdateTime(new Date());
		return substandardDataDao.update(substandardData);
	}
	
	@Override
	public int remove(Integer id){
		return substandardDataDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return substandardDataDao.batchRemove(ids);
	}
	
	@Override
	public int approval(Integer[] ids) {
		// TODO Auto-generated method stub
		for (Integer id : ids) {
			SubstandardDataDO substandardDataDO = substandardDataDao.get(id);
			InputsDO inputsDO =inputsDao.get(substandardDataDO.getInputsId());
			substandardDataDO.setId(id);
			substandardDataDO.setApprovalId((int)ShiroUtils.getUserId().longValue());
			substandardDataDO.setApprovalTime(new Date());
			substandardDataDO.setStatus(AnimalConstant.SUBSTANDAR_STATUS_APPROVAL);
			substandardDataDao.update(substandardDataDO);
			inputsDO.setStatus(AnimalConstant.INPUTS_STATUS_SUBSTANDARD);
			inputsDao.update(inputsDO);
		}
		return 1;
	}
}
