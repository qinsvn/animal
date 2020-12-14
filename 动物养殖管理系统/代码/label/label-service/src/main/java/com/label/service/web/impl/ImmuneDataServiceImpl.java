/**
 * 
 */
package com.label.service.web.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.label.common.constant.RedisConstant;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.CollectionData;
import com.label.common.entity.ResultApi;
import com.label.common.model.base.AdminUser;
import com.label.common.model.base.ChickenData;
import com.label.common.model.base.ChickenDataExample;
import com.label.common.model.base.ImmuneBaseinput;
import com.label.common.model.base.ImmuneData;
import com.label.common.model.base.ImmuneDataExample;
import com.label.common.util.UtilExportExcel;
import com.label.common.util.UtilExportFileName;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.ChickenDataMapper;
import com.label.dao.auto.ImmuneDataMapper;
import com.label.service.web.ImmuneDataService;
import com.label.util.BaseInputdataUtil;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
@Service
@Transactional(rollbackFor=Exception.class) 
public class ImmuneDataServiceImpl extends CommonServiceImpl implements ImmuneDataService {

	private Logger _log = LoggerFactory.getLogger(getClass());
	@Autowired
	ImmuneDataMapper immuneDataMapper;
	@Autowired
	ChickenDataMapper chickenDataMapper;
	
	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) {
		// TODO Auto-generated method stub
		_log.info("查询蛋类列表，参数map：{}",UtilJson.Obj2Str(map));
		return breedDataCustomMapper.selectImmuneDatas(map);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		_log.info("chickenDataMapper删除免疫id：{}",id);
		return immuneDataMapper.deleteByPrimaryKey(id);
	}
	@Override
	public UpmsResult update(ImmuneData immuneData) {
		// TODO Auto-generated method stub
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "修改成功 ");
		_log.info("castrationDataMapper修改免疫记录：{}",UtilJson.Obj2Str(immuneData));
		immuneDataMapper.updateByPrimaryKeySelective(immuneData);
		return result;
	}
	

	@Override
	public void collectionData(ResultApi resultApi,CollectionData data) throws Exception
	{
				ChickenDataExample chickenDataExample = new ChickenDataExample();
				chickenDataExample.createCriteria().andStatusEqualTo(0).andRfidNumEqualTo(data.getCode());
				List<ChickenData>  chickenDatas = chickenDataMapper.selectByExample(chickenDataExample);
				if (chickenDatas.size()>0) {//存在禽类才能进行免疫
					ChickenData chickenData = chickenDatas.get(0);
					AdminUser  user = UserUtil.getUserInfoByAccount(data.getUserName());
					ImmuneBaseinput immuneBaseinput = BaseInputdataUtil.getImmuneBaseinputByUserId(user.getId());
					ImmuneDataExample example = new ImmuneDataExample();
					example.createCriteria().andChikenIdEqualTo(chickenData.getId()).andImmuneTypeEqualTo(immuneBaseinput.getImmuneType());
					if (immuneDataMapper.selectByExample(example ).size()==0) {//对该药物没有免疫过才能进行免疫
						ImmuneData immuneData = new ImmuneData();
						immuneData.setAge(immuneBaseinput.getAge());
						immuneData.setChikenId(chickenData.getId());
						immuneData.setCreateUserId(user.getId());
						immuneData.setImmuneType(immuneBaseinput.getImmuneType());
						immuneData.setOrgId(user.getFdConpanyId());
						immuneData.setResult(immuneBaseinput.getResult());
						immuneData.setWeight(immuneBaseinput.getWeight());
						Date now = new Date();
						immuneData.setCreateTime(now);
						int ret =immuneDataMapper.insertSelective(immuneData );
						if (ret==0) {
							resultApi.set(ResultApi.FAILCODE, "免疫数据错误！");
							throw new Exception("免疫数据错误！");
						}else{
							resultApi.set(ResultApi.SUCCESSCODE, ResultApi.SUCCESSMSG);
						}
					}else{
						resultApi.set(ResultApi.FAILCODE, "重复免疫！");
					}
				}else{
					resultApi.set(ResultApi.FAILCODE, "没有该禽类！");
				}
	}
	/**
	 * 修改
	 * @param result
	 * @param map
	 */
	public UpmsResult upate(ImmuneData immuneData){
		int ret = immuneDataMapper.updateByPrimaryKeySelective(immuneData);
		if(ret>0){
			return new UpmsResult(UpmsResultConstant.SUCCESS, immuneData);
		}
		return   new UpmsResult(UpmsResultConstant.FAILED, "更新失败 ");
	}
	

@Override
public UpmsResult exportImmuneData(Map<String, Object> params) {
	UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
	try {

		if(!UserUtil.isAdmin()){
			params.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
		}
		//获取数据库中的蛋类
		List<Map<String, Object> > datas = breedDataCustomMapper.selectImmuneDatas(params);
		// 表格属性列名数组
		String[] strHeader = {"脚号","品种","日龄","免疫药物","免疫时间","操作人","养殖户","免疫结果"};
		// 表格属性列名key数组
		String[] keys = {"rfidNum","chikenType","age","immuneType","createTime","createUser","orgName","result"};
			
		// 标题
		String title = "immuneData";
		// 保存的文件夹路径
		String sys_save_path = UtilPropertiesFile.getInstance().get("sys_save_path");
		String sys_exportpath = UtilPropertiesFile.getInstance().get("sys_exportpath");
		String savePath = sys_save_path + sys_exportpath + UtilExportFileName.getName();
		//测试地址
		//savePath = "C:\\actionTool\\export" + UtilExportFileName.getName();
		File fileDic = new File(savePath);
		if (!fileDic.exists()) {
			// 无此文件夹时创建
			fileDic.mkdirs();
			
			//设置权限
			fileDic.getParentFile().setReadable(true);
			fileDic.getParentFile().setExecutable(true);
			fileDic.setReadable(true);
			fileDic.setExecutable(true);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = df.format(new Date());
		// 文件保存的名字
		String fileName = "免疫数据"+now+".xls";
		// 保存的文件路径
		String filePath = savePath + fileName;
		_log.info("jolley >> exportExcel:{}", filePath);
		// 保存文件
		OutputStream out = new FileOutputStream(filePath);
		
		UtilExportExcel.exportExcels(title, strHeader, keys, datas, out, "yyyy-MM-dd HH:mm:ss",null);
		out.close();
		String fileId = UUID.randomUUID().toString();
		UtilRedis.hset(RedisConstant.LABEL_EXPORT_EXCEL_PATH, fileId, filePath);
		
		result.setData(fileId);
		return result;
		
	} catch (Exception e) {
		_log.error("jolley --> exportExcelErr:{}", e);
	}
	
	return null;
}
}
