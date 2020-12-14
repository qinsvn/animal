package com.ts.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ts.common.domain.LogDO;
import com.ts.common.domain.PageDO;
import com.ts.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
