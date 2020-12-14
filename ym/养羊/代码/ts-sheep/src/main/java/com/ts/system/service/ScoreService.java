package com.ts.system.service;

import com.ts.system.domain.ScoreDO;

import java.util.List;
import java.util.Map;

/**
 * 评分模板
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2019-09-20 10:05:04
 */
public interface ScoreService {
	
	ScoreDO get(Integer cid);
	
	List<ScoreDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ScoreDO score);
	
	int update(ScoreDO score);
	
	int remove(Integer cid);
	
	int batchRemove(Integer[] cids);
}
