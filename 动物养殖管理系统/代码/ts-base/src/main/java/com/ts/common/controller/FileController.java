package com.ts.common.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ts.common.config.TsConfig;
import com.ts.common.domain.FileDO;
import com.ts.common.redis.RedisConstant;
import com.ts.common.redis.RedisUtil;
import com.ts.common.service.FileService;
import com.ts.common.utils.FileType;
import com.ts.common.utils.FileUtil;
import com.ts.common.utils.PageUtils;
import com.ts.common.utils.Query;
import com.ts.common.utils.Result;
import com.ts.common.utils.UtilByte;

/**
 * 文件上传
 * 
 * @author bobby
 * @email bobby@126.com
 * @date 2017-09-19 16:02:20
 */
@Controller
@RequestMapping("/common/sysFile")
public class FileController extends BaseController {

	@Autowired
	private FileService sysFileService;

	@GetMapping()
	@RequiresPermissions("common:sysFile:sysFile")
	String sysFile(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "common/file/file";
	}

	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("common:sysFile:sysFile")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<FileDO> sysFileList = sysFileService.list(query);
		int total = sysFileService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	// @RequiresPermissions("common:bComments")
	String add() {
		return "common/sysFile/add";
	}

	@GetMapping("/edit")
	// @RequiresPermissions("common:bComments")
	String edit(Long id, Model model) {
		FileDO sysFile = sysFileService.get(id);
		model.addAttribute("sysFile", sysFile);
		return "common/sysFile/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("common:info")
	public Result info(@PathVariable("id") Long id) {
		FileDO sysFile = sysFileService.get(id);
		return Result.ok().put("sysFile", sysFile);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:save")
	public Result save(FileDO sysFile) {
		if (sysFileService.save(sysFile) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("common:update")
	public Result update(@RequestBody FileDO sysFile) {
		sysFileService.update(sysFile);

		return Result.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	// @RequiresPermissions("common:remove")
	public Result remove(Long id, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = TsConfig.getUploadPath() + sysFileService.get(id).getUrl().replace("/files/", "");
		if (sysFileService.remove(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return Result.error("数据库记录删除成功，文件删除失败");
			}
			return Result.ok();
		} else {
			return Result.error();
		}
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:remove")
	public Result remove(@RequestParam("ids[]") Long[] ids) {
		if ("test".equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		sysFileService.batchRemove(ids);
		return Result.ok();
	}

	@ResponseBody
	@PostMapping("/upload")
	Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return Result.error(Result.FAIL, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
		try {
			FileUtil.uploadFile(file.getBytes(), TsConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			return Result.error();
		}

		if (sysFileService.save(sysFile) > 0) {
			return Result.ok().put("fileName",sysFile.getUrl());
		}
		return Result.error();
	}

	@RequestMapping("/file/{fileId}")
	public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileId") String fileId) throws IOException {
		String path = RedisUtil.get(RedisConstant.DOWNLOAD_DATA_KEY+fileId);
		String[] split = path.split("/");
		String fn = split[split.length-1];
		fn = new String(fn.getBytes("GB2312"), "ISO_8859_1");
		byte[] data = UtilByte.fileToBytes(path);
		 FileUtil.deleteFile(path);
		response.reset();
		response.setHeader("Content-Disposition", String.format( "attachment; filename=\"%s\"", fn));
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
	
}
