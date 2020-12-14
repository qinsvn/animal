/**
 * 
 */
package com.label.web.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.label.task.statistic.StatisticsTask;

/**
 * @author admin
 *
 */
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private StatisticsTask statisticsTask;
	
	@RequestMapping(value = "/index")
	@ResponseBody
	public Object createBsWorkshop() {

//		 statisticsTask.init();
//		 statisticsTask.action();
//		 statisticsTask.recordStaticData("235455");
		return null;
	}
}
