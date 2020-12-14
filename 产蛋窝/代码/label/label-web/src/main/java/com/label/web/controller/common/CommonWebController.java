/**
 * @company 广州天迅网络科技有限公司 copyright
 * @author jolley
 * @date 2017年11月23日
 * @describe  xxx
 *
 */
package com.label.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/web/common")
public class CommonWebController {  

	private static Logger log = LoggerFactory.getLogger(CommonWebController.class);
	
	
	 @ApiOperation(value = "重定向页面")
	 @RequestMapping(value = "/to/{filePath}", method = RequestMethod.GET)
	    public String to(@PathVariable String filePath, HttpServletRequest request, HttpServletResponse response) {
			 if (StringUtils.isEmpty(filePath)) {
				 log.info("进入页面不存在：{}",filePath);
				return null;
			}else{
				filePath = filePath.replace("_", "/").concat(".jsp");
				 log.info("进入页面：{}",filePath);
				 return filePath;
			}
	    }
}
