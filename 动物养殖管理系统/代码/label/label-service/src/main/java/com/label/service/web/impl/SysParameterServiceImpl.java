package com.label.service.web.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.label.common.model.base.SysParameter;
import com.label.common.util.UtilJson;
import com.label.dao.custom.SysParameterCustomerMapper;
import com.label.service.web.SysParameterService;


@Service("sysParameterService")
public class SysParameterServiceImpl implements SysParameterService {
	
//	private static final Logger log = LoggerFactory.getLogger(SysParameterServiceImpl.class); // 日志对象
	
	@Resource
	private SysParameterCustomerMapper sysParameterManulMapper;
	
	@Override
	public void alter(int cid, String data) {
		List<String> syskeyList = new LinkedList<String>();
		List<SysParameter> sysParameterlist = new LinkedList<SysParameter>();
		
		JSONArray jsonArr = UtilJson.str2JSONArry(data);
		if(jsonArr != null && jsonArr.size() > 0) {
			for(int i = 0; i < jsonArr.size(); i++) {
				SysParameter sysParameter = UtilJson.str2Bean(jsonArr.get(i).toString(), SysParameter.class);
				if(sysParameter != null) {
					syskeyList.add(sysParameter.getSyskey());
					sysParameter.setCid(cid);
					sysParameterlist.add(sysParameter);
				}
			}
		}
		
		if(syskeyList.size() > 0) {
			sysParameterManulMapper.delete(cid,sysParameterlist.get(0).getAppid(), syskeyList);
		}
		if(sysParameterlist.size() > 0) {
			sysParameterManulMapper.insertBatch(sysParameterlist);
		}
	}
	
	@Override
	public List<SysParameter> list(int cid, String category,String appid, String syskeys) {
		category = StringUtils.isEmpty(category) ? null : category;
		String []syskeyArr = StringUtils.isEmpty(syskeys) ? null : syskeys.split(",");
		List<SysParameter> list = sysParameterManulMapper.list(cid, category,appid, syskeyArr);
		
		return list;
	}
	
}
