package com.ts.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.ts.common.config.TsConfig;
import com.ts.common.domain.Tree;
import com.ts.common.utils.BuildTree;
import com.ts.common.utils.DataPerUitl;
import com.ts.system.dao.DeptDao;
import com.ts.system.domain.DeptDO;
import com.ts.system.service.DeptService;
import com.ts.system.util.SystemUtil;


@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao sysDeptMapper;

    @Override
    public DeptDO get(Long deptId) {
        return sysDeptMapper.get(deptId);
    }

    @Override
    public List<DeptDO> list(Map<String, Object> map) {
        return sysDeptMapper.list(map);
    }
    @Override
    public List<DeptDO> subList(Map<String, Object> map) {
    	map.putAll(DataPerUitl.deptPer());
    	map.put("sort", "select_order");
//    	map.put("order", "select_order");
    	 List<DeptDO> list = sysDeptMapper.subList(map);
        return list;
    }

    @Override
    public int count(Map<String, Object> map) {
        return sysDeptMapper.count(map);
    }

    @Override
    public int save(DeptDO sysDept) {
    	DeptDO pSysDept = sysDeptMapper.get(sysDept.getParentId());
    	sysDept.setLevel(pSysDept.getLevel()+1);
    	String pre = "000";
    	String end ="000";
    	if(sysDept.getOrderNum()!=null){
    		pre = pre+sysDept.getOrderNum();
    		pre = pre.substring(pre.length()-3, pre.length());
    	}
    	int maxEnd = 0;
    	Map<String, Object> map = Maps.newHashMap();
    	map.put("parentId", sysDept.getParentId());
    	List<DeptDO> list = sysDeptMapper.list(map );
    	for (DeptDO deptDO : list) {
    		String cSelctOrder = deptDO.getSelectOrder();
            cSelctOrder = cSelctOrder.substring(cSelctOrder.length()-6, cSelctOrder.length());
        	String cPre = cSelctOrder.substring(0, 3);
        	String cEnd =  cSelctOrder.substring(3, 6);
			if(cPre.equals(pre)){
				int cEndInt = Integer.valueOf(cEnd);
				if (maxEnd<=cEndInt) {
					maxEnd = cEndInt;
				}
			}
		}
    	end = end+(maxEnd+1);
    	end = end.substring(end.length()-3, end.length());
    	String selectOrder = pSysDept.getSelectOrder();
    	selectOrder=selectOrder+pre+end;
    	sysDept.setSelectOrder(selectOrder);
    	int ret = sysDeptMapper.save(sysDept);
    	SystemUtil.setDepts(depts());
        return ret ;
    }

    @Override
    public int update(DeptDO sysDept) {

    	DeptDO oldSysDept = sysDeptMapper.get(sysDept.getDeptId());
    	if(oldSysDept.getOrderNum()!=sysDept.getOrderNum()&&oldSysDept.getDeptId()!= Long.valueOf(TsConfig.getProperty("ts.manage.maxOrg"))){
    		String pre = "000";
    		String end ="000";
    		if(sysDept.getOrderNum()!=null){
    			pre = pre+sysDept.getOrderNum();
    			pre = pre.substring(pre.length()-3, pre.length());
    		}
    		int maxEnd = 0;
    		Map<String, Object> map = Maps.newHashMap();
    		map.put("parentId", sysDept.getParentId());
    		List<DeptDO> list = sysDeptMapper.list(map );
    		for (DeptDO deptDO : list) {
    			String cSelctOrder = deptDO.getSelectOrder();
    			cSelctOrder = cSelctOrder.substring(cSelctOrder.length()-6, cSelctOrder.length());
    			String cPre = cSelctOrder.substring(0, 3);
    			String cEnd =  cSelctOrder.substring(3, 6);
    			if(cPre.equals(pre)){
    				int cEndInt = Integer.valueOf(cEnd);
    				if (maxEnd<=cEndInt&&deptDO.getDeptId()!=sysDept.getDeptId()) {
    					maxEnd = cEndInt;
    				}
    			}
    		}
    		end = end+(maxEnd+1);
    		end = end.substring(end.length()-3, end.length());
    		String selectOrder = oldSysDept.getSelectOrder();
    		selectOrder=selectOrder.substring(0, selectOrder.length()-6)+pre+end;
    		sysDept.setSelectOrder(selectOrder);
    	}
    	
    	int ret =sysDeptMapper.update(sysDept);
    	SystemUtil.setDepts(depts());
        return ret;
    }

    @Override
    public int remove(Long deptId) {
    	int ret =sysDeptMapper.remove(deptId);
         SystemUtil.setDepts(depts());
        return ret;
    }

    @Override
    public int batchRemove(Long[] deptIds) {
    	int ret = sysDeptMapper.batchRemove(deptIds);
         SystemUtil.setDepts(depts());
        return ret;
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String, Object>(16));
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }


    @Override
    public Map<Long, DeptDO> depts() {
    	Map<Long, DeptDO> ret =new HashMap<Long, DeptDO>();
        List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String, Object>(16));
        for (DeptDO sysDept : sysDepts) {
        	ret.put(sysDept.getDeptId(), sysDept);
        }
        for (DeptDO sysDept : sysDepts) {
        		String name = sysDeptNames(sysDept.getDeptId(), sysDept.getName(), ret);
        		sysDept.setName(name); 
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return ret;
    }

    private String sysDeptNames(long id,String name,Map<Long, DeptDO> cpDeptMap  ){
    	DeptDO sysDept = cpDeptMap.get(id);
         	if (sysDept.getParentId()!=0) {
         		DeptDO parentDeptDO = cpDeptMap.get(sysDept.getParentId());
         		name = parentDeptDO.getName()+" - "+name;
         		name = sysDeptNames(parentDeptDO.getDeptId(), name, cpDeptMap);
 			} 
    	return name;
    }
    
    
    
    @Override
    public boolean checkDeptHasUser(Long deptId) {
        // TODO Auto-generated method stub
        //查询部门以及此部门的下级部门
        int result = sysDeptMapper.getDeptUserNumber(deptId);
        return result == 0 ? true : false;
    }

    @Override
    public List<Long> listChildrenIds(Long parentId) {
        List<DeptDO> deptDOS = list(null);
        return treeMenuList(deptDOS, parentId);
    }

    List<Long> treeMenuList(List<DeptDO> menuList, long pid) {
        List<Long> childIds = new ArrayList<>();
        for (DeptDO mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId() == pid) {
                //递归遍历下一级
                treeMenuList(menuList, mu.getDeptId());
                childIds.add(mu.getDeptId());
            }
        }
        return childIds;
    }

}
