package com.ts.animal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ts.animal.dao.SupplierDao;
import com.ts.animal.domain.SupplierDO;
import com.ts.animal.service.SupplierService;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;


@Transactional
@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierDao supplierDao;
	
	@Override
	public SupplierDO get(Integer id){
		return supplierDao.get(id);
	}
	
	@Override
	public List<SupplierDO> list(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return supplierDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return supplierDao.count(map);
	}
	
	@Override
	public int save(Result result,SupplierDO supplier){
		supplier.setCreateUser((int)ShiroUtils.getUserId().longValue());
		if (supplier.getDeptId()==null) {
			supplier.setDeptId((int)ShiroUtils.getUser().getDeptId().longValue());
		}
		supplier.setCreateTime(new Date());
		return supplierDao.save(supplier);
	}
	
	@Override
	public int update(Result result,SupplierDO supplier){
		if (supplier.getDeptId()==null) {
			supplier.setDeptId((int)ShiroUtils.getUser().getDeptId().longValue());
		}
		supplier.setUpdateUser((int)ShiroUtils.getUserId().longValue());
		supplier.setUpdateTime(new Date());
		return supplierDao.update(supplier);
	}
	
	@Override
	public int remove(Integer id){
		return supplierDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return supplierDao.batchRemove(ids);
	}
	
}
