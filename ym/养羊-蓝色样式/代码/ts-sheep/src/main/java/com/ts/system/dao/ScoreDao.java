package com.ts.system.dao;

import com.ts.system.domain.ScoreDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 评分模板
 * @author bobby
 * @email bobby@126.com
 * @date 2019-09-20 10:05:04
 */
@Mapper
public interface ScoreDao {

	ScoreDO get(Integer cid);
	
	List<ScoreDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ScoreDO score);
	
	int update(ScoreDO score);
	
	int remove(Integer cid);
	
	int batchRemove(Integer[] cids);
}
