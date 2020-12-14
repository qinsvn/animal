package com.label.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.label.common.model.base.SysParameter;
import com.label.dao.auto.SysParameterMapper;

public interface SysParameterCustomerMapper extends SysParameterMapper {
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int insertBatch(List<SysParameter> list);
	
	/**
	 * 删除
	 * @param cid
	 * @param syskeys
	 * @return
	 */
	public int delete(@Param("cid") int cid,@Param("appid") String appid, @Param("syskeys") List<String> syskeys);
	
	/**
	 * 列表
	 * @param cid
	 * @param category
	 * @param syskeys
	 * @return
	 */
	public List<SysParameter> list(@Param("cid") int cid, @Param("category") String category, @Param("appid") String appid, @Param("syskeys") String []syskeys);
	
}