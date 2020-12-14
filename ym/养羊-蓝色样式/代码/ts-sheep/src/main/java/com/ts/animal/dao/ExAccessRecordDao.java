package com.ts.animal.dao;

import com.ts.animal.domain.AccessRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 进出记录表
 * @author bobby
 * @email bobby@126.com
 * @date 2020-06-06 15:45:53
 */
@Mapper
public interface ExAccessRecordDao {

	List<AccessRecordDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);

}
