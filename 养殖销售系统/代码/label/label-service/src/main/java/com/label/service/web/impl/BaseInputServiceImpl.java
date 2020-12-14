/**
 * 
 */
package com.label.service.web.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.model.base.ChickenBaseinput;
import com.label.common.model.base.ChickenBaseinputExample;
import com.label.common.model.base.ChickenType;
import com.label.common.model.base.ImmuneBaseinput;
import com.label.common.model.base.ImmuneBaseinputExample;
import com.label.common.util.UtilJson;
import com.label.dao.auto.ChickenBaseinputMapper;
import com.label.dao.auto.ChickenTypeMapper;
import com.label.dao.auto.ImmuneBaseinputMapper;
import com.label.service.web.BaseInputService;
import com.label.util.BaseInputdataUtil;
import com.label.util.UserUtil;

/**
 * @author jolley
 *
 */
@Service
@Transactional
public class BaseInputServiceImpl implements BaseInputService {

	private Logger _log = LoggerFactory.getLogger(getClass());
	
	 @Autowired
	 ChickenTypeMapper chickenTypeMapper;

	@Autowired
	 ChickenBaseinputMapper chickenBaseinputMapper;

	@Autowired
	 ImmuneBaseinputMapper immuneBaseinputMapper;

	@Override
	public UpmsResult getBaseInputdata() {
		// TODO Auto-generated method stub
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "获取失败 ");
		try {
				//品种信息
				ChickenType chickenType = chickenTypeMapper.selectByPrimaryKey(1);
				//禽类录入基础信息
				ChickenBaseinputExample ckE = new ChickenBaseinputExample();
				ckE.createCriteria().andUserIdEqualTo(UserUtil.getUpmsUserInfo().getId());
				ckE.setOrderByClause("id desc");
				List<ChickenBaseinput> chickenBaseinputs= chickenBaseinputMapper.selectByExample(ckE );
				//疫苗录入基础信息
				ImmuneBaseinputExample idE = new ImmuneBaseinputExample();
				idE.createCriteria().andUserIdEqualTo(UserUtil.getUpmsUserInfo().getId());
				idE.setOrderByClause("id desc");
				List<ImmuneBaseinput> immuneBaseinputs = immuneBaseinputMapper.selectByExample(idE );
				
				Map<String, Object> data = new HashMap<String, Object>();
				if (chickenType!=null) {
					data.put("chickenType", chickenType);
				}
				data.put("chickenBaseinputs", chickenBaseinputs);
//				if (chickenBaseinputs.size()>0) {
//					data.put("chickenBaseinput", chickenBaseinputs.get(0));
//				}else{
//					data.put("chickenBaseinput", new ChickenBaseinput());
//				}
				data.put("immuneBaseinputs", immuneBaseinputs);
//				if (immuneBaseinputs.size()>0) {
//					data.put("immuneBaseinput", immuneBaseinputs.get(0));
//				}else{
//					data.put("immuneBaseinput", new ImmuneBaseinput());
//				}
				result.setCode(UpmsResultConstant.SUCCESS.getCode());
				result.setMessage("获取成功");
				result.setData(data);
				_log.info("获取到基础数据信息包括品种信息chickenType，禽类录入基础信息chickenBaseinput，疫苗录入基础信息immuneData：  {}",UtilJson.Obj2Str(data));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public UpmsResult saveChickenType(ChickenType chickenType) {
		// TODO Auto-generated method stub
				UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "保存失败 ");
				try {
						chickenType.setId(1);
						int ret = chickenTypeMapper.updateByPrimaryKey(chickenType);
						if (ret>0) {
							result.setCode(UpmsResultConstant.SUCCESS.getCode());
							result.setMessage("保存成功");
						}else{
							chickenTypeMapper.insert(chickenType);
							if (ret>0) {
								result.setCode(UpmsResultConstant.SUCCESS.getCode());
								result.setMessage("保存成功");
							}
						}
						_log.info("品种基础信息chickenType：  {}",UtilJson.Obj2Str(chickenType));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return result;
	}

	@Override
	public UpmsResult saveChickenBaseinput(ChickenBaseinput chickenBaseinput) {
		// TODO Auto-generated method stub
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "保存失败 ");
		try {
				ChickenBaseinputExample chickenBaseinputExample = new ChickenBaseinputExample();
				chickenBaseinputExample.createCriteria().andUserIdEqualTo(UserUtil.getUpmsUserInfo().getId()).andSupplierEqualTo(chickenBaseinput.getSupplier());
				chickenBaseinputMapper.deleteByExample(chickenBaseinputExample );
				
				chickenBaseinputExample =new ChickenBaseinputExample();
				chickenBaseinputExample.createCriteria().andUserIdEqualTo(UserUtil.getUpmsUserInfo().getId());
				ChickenBaseinput record = new ChickenBaseinput();
				record.setRemark1("1");
				chickenBaseinputMapper.updateByExampleSelective(record , chickenBaseinputExample);
				
				chickenBaseinput.setUserId(UserUtil.getUpmsUserInfo().getId());
				chickenBaseinput.setOrgId(UserUtil.getUpmsUserInfo().getCompanyId());
				chickenBaseinput.setRemark1("0");
				int ret = chickenBaseinputMapper.insert(chickenBaseinput);
				if (ret>0) {
					result.setCode(UpmsResultConstant.SUCCESS.getCode());
					result.setMessage("保存成功");
				}
				BaseInputdataUtil.initBaseInputdata();
				_log.info("禽类录入基础信息chickenBaseinput：  {}",UtilJson.Obj2Str(chickenBaseinput));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public UpmsResult saveImmuneBaseinput(ImmuneBaseinput immuneBaseinput) {
		// TODO Auto-generated method stub
		UpmsResult result = new UpmsResult(UpmsResultConstant.FAILED, "保存失败 ");
		try {
				ImmuneBaseinputExample immuneDataExample = new ImmuneBaseinputExample();
				immuneDataExample.createCriteria().andUserIdEqualTo(UserUtil.getUpmsUserInfo().getId()).andImmuneTypeEqualTo(immuneBaseinput.getImmuneType());
				immuneBaseinputMapper.deleteByExample(immuneDataExample );
				
				 immuneDataExample = new ImmuneBaseinputExample();
				 immuneDataExample.createCriteria().andUserIdEqualTo(UserUtil.getUpmsUserInfo().getId());
				ImmuneBaseinput record = new ImmuneBaseinput();
				record.setRemark1("1");
				immuneBaseinputMapper.updateByExampleSelective(record , immuneDataExample);
				
				immuneBaseinput.setUserId(UserUtil.getUpmsUserInfo().getId());
				immuneBaseinput.setOrgId(UserUtil.getUpmsUserInfo().getCompanyId());
				immuneBaseinput.setRemark1("0");
				int ret = immuneBaseinputMapper.insert(immuneBaseinput);
				
				if (ret>0) {
					result.setCode(UpmsResultConstant.SUCCESS.getCode());
					result.setMessage("保存成功");
				}
				BaseInputdataUtil.initBaseInputdata();
				_log.info("疫苗基础录入信息immuneData：  {}",UtilJson.Obj2Str(immuneBaseinput));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
}
