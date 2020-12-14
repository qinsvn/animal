package com.label.service.web.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.label.common.model.base.Role;
import com.label.common.model.base.RoleExample;
import com.label.common.model.base.RoleUserRef;
import com.label.common.model.base.RoleUserRefExample;
import com.label.dao.auto.RoleMapper;
import com.label.dao.auto.RoleUserRefMapper;
import com.label.service.web.RoleService;
import com.github.pagehelper.PageHelper;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
//	private static Logger _log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleMapper roleMapper;
    
    @Autowired
    RoleUserRefMapper roleUserRefMapper;

    @Override
    public long count(RoleExample example) {
    	return roleMapper.countByExample(example);
    }
    
	@Override
	public List<Role> list(int pageNum, int pageSize, RoleExample example) {
		PageHelper.startPage(pageNum, pageSize, false);
		return roleMapper.selectByExample(example);
	}

	@Override
	public List<RoleUserRef> selectRoleUserRef(RoleUserRefExample example) {
		return roleUserRefMapper.selectByExample(example);
	}
	
	@Override
	public Role info(int id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int create(Role role) {
		return roleMapper.insert(role);
	}

	@Override
	public int createRoleUserRef(RoleUserRef roleUserRef) {
		return roleUserRefMapper.insert(roleUserRef);
	}

	@Override
	public int delete(RoleExample example) {
		return roleMapper.deleteByExample(example);
	}

	@Override
	public int deleteRoleUserRef(RoleUserRefExample example) {
		return roleUserRefMapper.deleteByExample(example);
	}

	@Override
	public int alter(Role record, RoleExample example) {
		return roleMapper.updateByExampleSelective(record, example);
	}

}
