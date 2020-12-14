package com.label.web.controller.web.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.common.entity.CollectionData;
import com.label.common.entity.ResultApi;
import com.label.common.util.ModelUtil;
import com.label.common.util.UtilJson;
import com.label.service.web.AggService;
import com.label.service.web.CastrationDataService;
import com.label.service.web.ChickenService;
import com.label.service.web.ImmuneDataService;
import com.label.service.web.OrderService;
import com.label.service.web.StockDataService;
import com.label.service.web.SubstandardDataService;
import com.label.util.UserUtil;

@Controller
@RequestMapping("api")
public class ApiAction {

	@Resource
	private AggService aggService;
	@Resource
	private CastrationDataService castrationDataService;
	@Resource
	private ChickenService chickenService;
	@Resource
	private ImmuneDataService immuneDataService;
	@Resource
	private OrderService orderService;
	@Resource
	private StockDataService stockDataService;
	@Resource
	private SubstandardDataService substandardDataService;
	
	@RequestMapping(value = "/culture")
	@ResponseBody
	public String  cultureData(HttpServletRequest  requst){
		String code = requst.getParameter("code");
		String workModel = requst.getParameter("workmodel");
		String userName = requst.getParameter("username");
		if (code==null||"null".equals(code)||"".equals(code)) {
			ResultApi resultApi = new ResultApi(ResultApi.FAILCODE,"编码为空");
			return UtilJson.Obj2Str(resultApi);
		}
		if (workModel==null||"null".equals(workModel)||"".equals(workModel)) {
			ResultApi resultApi = new ResultApi(ResultApi.FAILCODE,"工作模式为空");
			return UtilJson.Obj2Str(resultApi);
		}
		if (userName==null||"null".equals(userName)||"".equals(userName)) {
			ResultApi resultApi = new ResultApi(ResultApi.FAILCODE,"账号为空");
			return UtilJson.Obj2Str(resultApi);
		}else{
			if (!UserUtil.checkUserEnabelByAccount(userName)) {
				ResultApi resultApi = new ResultApi(ResultApi.FAILCODE,"账号不存在或者被禁用");
				return UtilJson.Obj2Str(resultApi);
			}
		}
		CollectionData data = new CollectionData();
		data.setCode(code);
		data.setUserName(userName);
		data.setWorkModel(workModel);
		ResultApi resultApi =new ResultApi(ResultApi.SUCCESSCODE,ResultApi.SUCCESSMSG);
		try {
			if (workModel.equals(ModelUtil.collection_chicken)) {
				chickenService.collectionData(resultApi,data);
			}else if (workModel.equals(ModelUtil.collection_agg)) {
				 aggService.collectionData(resultApi,data);
			}else if (workModel.equals(ModelUtil.collection_castration)) {
				 castrationDataService.collectionData(resultApi,data);
			}else if (workModel.equals(ModelUtil.collection_immune)) {
				 immuneDataService.collectionData(resultApi,data);
			}else if (workModel.equals(ModelUtil.collection_ck_substandard)||workModel.equals(ModelUtil.collection_agg_substandard)) {
				 substandardDataService.collectionData(resultApi,data);
			}else if (workModel.equals(ModelUtil.collection_ck_order)||workModel.equals(ModelUtil.collection_agg_order)) {
				 orderService.collectionData(resultApi,data);
			}else{
				resultApi =  new ResultApi(ResultApi.FAILCODE,"暂不支持模式-  "+ModelUtil.map.get(workModel));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	return	UtilJson.Obj2Str(resultApi);
	}
}



