package com.ts.animal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ts.animal.dao.CustomerDao;
import com.ts.animal.domain.CustomerDO;
import com.ts.animal.service.CustomerService;
import com.ts.common.utils.DataPerUitl;
import com.ts.common.utils.Result;
import com.ts.common.utils.ShiroUtils;


@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public CustomerDO get(Integer id){
		return customerDao.get(id);
	}
	
	@Override
	public List<CustomerDO> list(Map<String, Object> map){
		map.putAll(DataPerUitl.deptPer());
		return customerDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return customerDao.count(map);
	}
	
	@Override
	public int save(Result result,CustomerDO customer){
		customer.setCreateUser((int)ShiroUtils.getUserId().longValue());
		if (customer.getDeptId()==null) {
			customer.setDeptId((int)ShiroUtils.getUser().getDeptId().longValue());
		}
		customer.setCreateTime(new Date());
		return customerDao.save(customer);
	}
	
	@Override
	public int update(Result result,CustomerDO customer){
		customer.setUpdateUser((int)ShiroUtils.getUserId().longValue());
		customer.setUpdateTime(new Date());
		if (customer.getDeptId()==null) {
			customer.setDeptId((int)ShiroUtils.getUser().getDeptId().longValue());
		}
		return customerDao.update(customer);
	}
	
	@Override
	public int remove(Integer id){
		return customerDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return customerDao.batchRemove(ids);
	}
	
}
