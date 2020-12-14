package com.label.web.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.label.common.base.BaseController;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.service.web.UploadService;

import io.swagger.annotations.ApiOperation;

/**
 * 文件上传管理
 * @author jolley
 */
@Controller
@RequestMapping("/common/upload")
public class UploadController extends BaseController {

//    private static Logger _log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "新增文件上传")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(
    		@RequestParam(required = true, value = "file") MultipartFile file,
    		@RequestParam(required = false, defaultValue = "0", value = "type") int type) {
		Object data = uploadService.create(file, type);
		if(data != null) {
			return new UpmsResult(UpmsResultConstant.SUCCESS, data);
		}
		else {
			return new UpmsResult(UpmsResultConstant.FAILED, "上传失败");
		}
    }

}
