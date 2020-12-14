package com.label.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.label.common.model.base.ChickenBaseinput;
import com.label.common.model.base.ChickenBaseinputExample;
import com.label.common.model.base.ImmuneBaseinput;
import com.label.common.model.base.ImmuneBaseinputExample;
import com.label.common.util.UtilSpringContext;
import com.label.dao.auto.ChickenBaseinputMapper;
import com.label.dao.auto.ImmuneBaseinputMapper;

public class BaseInputdataUtil {

	private static Map<Integer, ChickenBaseinput> chickenBaseinputsByUserId = new HashMap<>();
	private static Map<Integer, ImmuneBaseinput> ImmuneBaseinputsById = new HashMap<>();


	/**
	 * 所有未锁定的用户信息
	 */
	public static void initBaseInputdata() {
		ChickenBaseinputMapper chickenBaseinputMapper = (ChickenBaseinputMapper) UtilSpringContext .getBean("chickenBaseinputMapper");
		//禽类录入基础信息
		ChickenBaseinputExample ckE = new ChickenBaseinputExample();
		ckE.createCriteria().andRemark1EqualTo("0");
		List<ChickenBaseinput> chickenBaseinputs= chickenBaseinputMapper.selectByExample(ckE);
		for (ChickenBaseinput chickenBaseinput : chickenBaseinputs) {
			chickenBaseinputsByUserId.put(chickenBaseinput.getUserId(), chickenBaseinput);
		}
		
		ImmuneBaseinputMapper immuneBaseinputMapper = (ImmuneBaseinputMapper) UtilSpringContext .getBean("immuneBaseinputMapper");
		//疫苗录入基础信息
		ImmuneBaseinputExample idE = new ImmuneBaseinputExample();
		idE.createCriteria().andRemark1EqualTo("0");
		List<ImmuneBaseinput> immuneBaseinputs = immuneBaseinputMapper.selectByExample(idE );
		for (ImmuneBaseinput immuneBaseinput : immuneBaseinputs) {
			ImmuneBaseinputsById.put(immuneBaseinput.getUserId(), immuneBaseinput);
		}
	}

	public static ChickenBaseinput getChickenBaseinputByUserId(int userId) {
		return chickenBaseinputsByUserId.get(userId)==null?new ChickenBaseinput(): chickenBaseinputsByUserId.get(userId);
	}
	public static ImmuneBaseinput getImmuneBaseinputByUserId(int userId) {
		return ImmuneBaseinputsById.get(userId)==null?new ImmuneBaseinput():ImmuneBaseinputsById.get(userId);
	}
}

