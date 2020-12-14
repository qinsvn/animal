package com.ts.animal.service;

import com.ts.animal.domain.SubstandardDataDO;

import java.util.List;
import java.util.Map;
import com.ts.common.utils.Result;

/**
 * 耗损数据表
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2020-02-04 22:04:36
 */
public interface SubstandardDataService {
	
	SubstandardDataDO get(Integer id);
	
	List<SubstandardDataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Result result,SubstandardDataDO substandardData);
	
	int saveByNum(Result result,SubstandardDataDO substandardData);
	
	 int update(Result result,SubstandardDataDO substandardData);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	int approval(Integer[] ids);
}
