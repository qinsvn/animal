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
import com.label.common.model.base.CastrationData;
import com.label.common.model.base.CastrationDataExample;
import com.label.common.model.base.ChickenData;
import com.label.common.model.base.ChickenDataExample;
import com.label.common.model.base.ImmuneData;
import com.label.common.util.UtilExportExcel;
import com.label.common.util.UtilExportFileName;
import com.label.common.util.UtilJson;
import com.label.common.util.UtilPropertiesFile;
import com.label.common.util.redis.UtilRedis;
import com.label.dao.auto.CastrationDataMapper;
import com.label.dao.auto.ChickenDataMapper;
import com.label.service.web.CastrationDataService;
import com.label.util.UserUtil;

/**
 * @author admin
 *
 */
@Service
@Transactional(rollbackFor=Exception.class) 
public class CastrationDataServiceImpl extends CommonServiceImpl implements CastrationDataService {

	private Logger _log = LoggerFactory.getLogger(getClass());
	@Autowired
	CastrationDataMapper castrationDataMapper;
	
	@Autowired
	ChickenDataMapper chickenDataMapper;
	
	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) {
		// TODO Auto-generated method stub
		_log.info("查询阉割列表，参数map：{}",UtilJson.Obj2Str(map));
		return breedDataCustomMapper.selectCastrationDatas(map);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		_log.info("castrationDataMapper删除阉割记录id：{}",id);
		return castrationDataMapper.deleteByPrimaryKey(id);
	}

	@Override
	public UpmsResult update(CastrationData castrationData) {
		// TODO Auto-generated method stub
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "修改成功 ");
		_log.info("castrationDataMapper修改阉割记录：{}",UtilJson.Obj2Str(castrationData));
		castrationDataMapper.updateByPrimaryKeySelective(castrationData);
		return result;
	}


	@Override
	public void collectionData(ResultApi resultApi,CollectionData data) throws Exception
	{
				ChickenDataExample chickenDataExample = new ChickenDataExample();
				chickenDataExample.createCriteria().andStatusEqualTo(0).andRfidNumEqualTo(data.getCode());
			    List<ChickenData> chickenDatas = chickenDataMapper.selectByExample(chickenDataExample);
				if (chickenDatas.size()>0) {
					CastrationDataExample example = new CastrationDataExample();
					ChickenData chickenData = chickenDatas.get(0);
					example.createCriteria().andChikenIdEqualTo(chickenData.getId());
					if (castrationDataMapper.selectByExample(example ).size()==0) {
						AdminUser  user = UserUtil.getUserInfoByAccount(data.getUserName());
						CastrationData castrationData = new CastrationData();
						castrationData.setCreateUserId(user.getId());
						castrationData.setOrgId(user.getFdConpanyId());
						castrationData.setChikenId(chickenData.getId());
						castrationData.setResult("正常");
						Date now = new Date();
						castrationData.setCreateTime(now);
						int ret = castrationDataMapper.insertSelective(castrationData );
						if (ret==0) {
							resultApi.set(ResultApi.FAILCODE, "阉割数据错误！");
							throw new Exception("阉割数据错误！");
						} else{
							resultApi.set(ResultApi.SUCCESSCODE, ResultApi.SUCCESSMSG);
						}
					}else{//已经阉割过了
						resultApi.set(ResultApi.FAILCODE, "重复阉割！");
					}
				}else{//没有该禽类 不能进行阉割记录
					resultApi.set(ResultApi.FAILCODE, "没有该禽类！");
				}
	}
	
	/**
	 * 修改
	 * @param result
	 * @param map
	 */
	public UpmsResult upate(CastrationData castrationData){
		int ret = castrationDataMapper.updateByPrimaryKeySelective(castrationData);
		if(ret>0){
			return new UpmsResult(UpmsResultConstant.SUCCESS, castrationData);
		}
		return   new UpmsResult(UpmsResultConstant.FAILED, "更新失败 ");
	}

	@Override
	public UpmsResult exportCastrationData(Map<String, Object> params) {
		UpmsResult result = new UpmsResult(UpmsResultConstant.SUCCESS, "导出成功 ");
		try {
			if(!UserUtil.isAdmin()){
				params.put("dptId", UserUtil.getUpmsUserInfo().getCompanyId());
			}
			//获取数据库中的蛋类
			List<Map<String, Object> > datas = breedDataCustomMapper.selectCastrationDatas(params);
			// 表格属性列名数组
			String[] strHeader = {"脚号","品种","阉割时间","操作人","养殖户","阉割结果"};
			// 表格属性列名key数组
			String[] keys = {"rfidNum","chikenType","createTime","createUser","orgName","result"};
				
			// 标题
			String title = "castrationData";
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
			String fileName = "阉割数据"+now+".xls";
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
