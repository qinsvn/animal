package com.ts.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ts.animal.constant.AnimalConstant;
import com.ts.animal.domain.CheckRecordDO;
import com.ts.animal.domain.ImmuneRecordDO;
import com.ts.animal.domain.InputsDO;
import com.ts.animal.domain.SaleRecordDO;
import com.ts.animal.domain.SubstandardDataDO;
import com.ts.animal.service.CheckRecordService;
import com.ts.animal.service.ImmuneRecordService;
import com.ts.animal.service.InputsService;
import com.ts.animal.service.SaleRecordService;
import com.ts.animal.service.SubstandardDataService;
import com.ts.common.utils.Result;
import com.ts.common.utils.StringUtils;

@Controller
@RequestMapping("/animal/api")
public class ApiController {

	@Autowired
	private CheckRecordService checkRecordService;
	@Autowired
	private ImmuneRecordService immuneRecordService;
	@Autowired
	private InputsService inputsService;
	@Autowired
	private SaleRecordService saleRecordService;
	@Autowired
	private SubstandardDataService substandardDataService;

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/work/{workMoel}")
	public Result save(@PathVariable("workMoel") String workMoel,InputsDO inputs, CheckRecordDO checkRecord, ImmuneRecordDO immuneRecord, SaleRecordDO saleRecord,
			SubstandardDataDO substandardData) {
		Result result = Result.ok();
		if (StringUtils.isEmpty(inputs.getNum())) {
			result.set(Result.FAIL,"未采集到任何数据",null);
			return result;
		}
		if (AnimalConstant.WORK_MOEL_1001.equals(workMoel)) {
			result.setMsg("编号【"+inputs.getNum()+"】入库成功！");
			inputsService.save(result, inputs);
		}else if (AnimalConstant.WORK_MOEL_1002.equals(workMoel)) {
			result.setMsg("编号【"+immuneRecord.getNum()+"】检疫成功！");
			immuneRecordService.saveByNum(result, immuneRecord);
		}else if (AnimalConstant.WORK_MOEL_1003.equals(workMoel)) {
			result.setMsg("编号【"+checkRecord.getNum()+"】检验成功！");
			checkRecordService.saveByNum(result, checkRecord);
		}else if (AnimalConstant.WORK_MOEL_1004.equals(workMoel)) {
			result.setMsg("编号【"+substandardData.getNum()+"】加入耗损成功！");
			substandardDataService.saveByNum(result, substandardData);
		}else if (AnimalConstant.WORK_MOEL_1005.equals(workMoel)) {
			result.setMsg("编号【"+saleRecord.getNum()+"】出售成功！");
			saleRecordService.saveByNum(result, saleRecord);
		}
		return result;
	}
	
}
