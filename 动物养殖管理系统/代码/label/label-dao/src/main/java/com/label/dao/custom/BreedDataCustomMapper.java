package com.label.dao.custom;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 自定义userMapper
 * @author Allen
 *
 */
public interface BreedDataCustomMapper {
	
	List<Map<String, Object>> selectChikens(Map<String, Object> map);

	List<Map<String, Object>> selectAggs(Map<String, Object> map);
	
	/**
	 * 阉割
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectCastrationDatas(Map<String, Object> map);
	/**
	 * 免疫
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectImmuneDatas(Map<String, Object> map);
	/**
	 * 次品
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectSubstandardDatas(Map<String, Object> map);

	/**
	 * 次品汇总
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectSubstandardDataSum(Map<String, Object> map);
	
	
	/**
	 * 库存
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectStockDatas(Map<String, Object> map);
	/**
	 * 订单明细
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectOrderDeail(Map<String, Object> map);
	/**
	 * 订单汇总
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectOrderSum(Map<String, Object> map);
	 
	
	Map<String, Object> getChickenInfoByRFID(@Param("rfid_num") String rfid);
}

