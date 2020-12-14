package com.label.web.controller.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.label.common.base.BaseController;
import com.label.common.constant.UpmsResult;
import com.label.common.constant.UpmsResultConstant;
import com.label.common.entity.PageBean;
import com.label.common.model.base.AdminUser;
import com.label.common.model.custom.upms.UpmsUserInfo;
import com.label.common.util.UtilJson;
import com.label.service.web.UserService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("web/user")
public class UserController extends BaseController{

	Logger _log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
    @ApiOperation(value = "列表页面")
    @RequiresPermissions("label:user:read")
    @RequestMapping(value = "/p_list", method = RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return "/web/user/list.jsp";
    }
    
    @ApiOperation(value = "详情页面")
    @RequiresPermissions("label:user:read")
    @RequestMapping(value = "/p_info", method = RequestMethod.GET)
    public String info(HttpServletRequest request) {
        return "/web/user/info.jsp";
    }
	
	@ApiOperation("分页显示数据")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@RequiresPermissions("label:user:read")
	@ResponseBody
	public UpmsResult listUser(@RequestBody PageBean<JSONObject> pageBean){
		try{
			UpmsUserInfo user = getUpmsUserInfo();
			if(user!=null){
				return userService.listUser(user,pageBean);				
			}else{
				return new UpmsResult(UpmsResultConstant.FAILED, "登录信息错误");
			}
		}catch(Exception e){
			_log.error("jolley >> list 查询失败:"+UtilJson.Obj2Str(pageBean), e);
			return new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		}
	}
	

	@ApiOperation("创建帐号")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@RequiresPermissions("label:user:create")
	@ResponseBody
	public UpmsResult createUser(AdminUser info){
		try{
			UpmsUserInfo user = getUpmsUserInfo();
			if(user!=null){
				return userService.createUser(user,info);				
			}else{
				return new UpmsResult(UpmsResultConstant.FAILED, "用户登录信息错误");
			}
		}catch(Exception e){
			_log.error("jolley >> create 添加失败:"+UtilJson.Obj2Str(info), e);
			return new UpmsResult(UpmsResultConstant.FAILED, "创建失败");
		}
	}
	
	@ApiOperation("依据id查询数据")
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	@RequiresPermissions("label:user:read")
	@ResponseBody
	public UpmsResult infoUser(Integer id){
		try{
			UpmsUserInfo user = getUpmsUserInfo();
			if(user!=null){
				if(id>0){
					return userService.infoUser(id);								
				}else{
					return new UpmsResult(UpmsResultConstant.FAILED, "id不存在");
				}				
			}else{
				return new UpmsResult(UpmsResultConstant.FAILED, "用户登录信息错误");
			}
		}catch(Exception e){
			_log.error("jolley >> info 查询失败,id:"+id, e);
			return new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		}
	}
	
	@ApiOperation("依据帐号查询数据")
	@RequestMapping(value = "/infoByAccount", method = RequestMethod.POST)
	@RequiresPermissions("label:user:read")
	@ResponseBody
	public UpmsResult infoUser(String fdAccount){
		try{
			if(!StringUtils.isEmpty(fdAccount)){
				return userService.infoByAccount(fdAccount);								
			}else{
				return new UpmsResult(UpmsResultConstant.FAILED, "帐号为空");
			}				
		}catch(Exception e){
			_log.error("jolley >> info 查询失败,fdAccount:"+fdAccount, e);
			return new UpmsResult(UpmsResultConstant.FAILED, "查询失败");
		}
	}
	
	@ApiOperation("修改数据")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@RequiresPermissions("label:user:update")
	@ResponseBody
	public UpmsResult updateProduct(AdminUser info){
		try{
			UpmsUserInfo user = getUpmsUserInfo();
			if(user!=null&&user.getCompanyId()>0){//商家
				info.setFdConpanyId(user.getCompanyId());
			}
			if (StringUtils.isEmpty(info.getId())) {
				_log.info("jolley >> update 缺少id  info:" + UtilJson.Obj2Str(info));
				return new UpmsResult(UpmsResultConstant.FAILED, "id不存在");
			} else {
				return userService.updateUser(user,info);
			}	
		}catch(Exception e){
			_log.error("jolley >> list 修改失败:"+UtilJson.Obj2Str(info), e);
			return new UpmsResult(UpmsResultConstant.FAILED, "修改失败");
		}
	}
	
	@ApiOperation("批量删除")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@RequiresPermissions("label:user:delete")
	@ResponseBody
	public Object deleteUser(@RequestBody Map<String,List<Integer>> map){
		try{
			UpmsUserInfo user = getUpmsUserInfo();
			if(user!=null&&user.getCompanyId()>=0){
				List<Integer> list = map.get("ids");
				if (list.size()<0) {
					_log.info("jolley >> delete 缺少参数");
					return new UpmsResult(UpmsResultConstant.FAILED, "没有参数");
				} else {
					//List<Integer> ids = new ArrayList<>();
					//是否允许被删除
					boolean allowDelete = true;
					for (Integer id : list) {
						if (id.intValue() == user.getId()) {
							allowDelete = false;
						}
					}
					//包含本次登陆的账号就不允许被删除
					if (!allowDelete) {
						return UpmsResult.FAILED("不能删除当前正在使用的账号，" + user.getAccount());
					}
					// 执行删除
					return userService.deleteUser(list);
				}				
			}else{
				return new UpmsResult(UpmsResultConstant.FAILED, "用户商家信息错误");
			}
		}catch(Exception e){
			_log.error("jolley >> delete 删除失败  参数:" + UtilJson.Obj2Str(map) + "; error", e);
			return new UpmsResult(UpmsResultConstant.FAILED, "删除失败");
		}
	}
	
	@ApiOperation("绑定角色")
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@RequiresPermissions("label:user:update")
	@ResponseBody
	public UpmsResult bindRole(@RequestParam(required = true, defaultValue="-1", value = "userId") Integer userId,
			@RequestParam(required = true, value = "ids") String roleIds
			){
		try{
			if(StringUtils.isEmpty(roleIds)||StringUtils.isEmpty(userId)||userId<1){
				_log.info("jolley >> bind 缺少参数");
				return new UpmsResult(UpmsResultConstant.FAILED, "缺少参数");
			}else{
				if(roleIds.endsWith(",")){
					roleIds=roleIds.substring(0, roleIds.length()-1);
				}
				return userService.bind(userId,roleIds);
			}
		}catch(Exception e){
			_log.error("jolley >> 绑定失败  error", e);
			return new UpmsResult(UpmsResultConstant.FAILED, "绑定失败");
		}
	}
	
	@ApiOperation("修改密码")
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@RequiresPermissions("label:password:update")
	@ResponseBody
	public UpmsResult updatePwd(
			@RequestParam(required = true, value = "oldPassword") String oldPassword,
			@RequestParam(required = true, value = "newPassword") String newPassword
			){
		try{
			if(StringUtils.isEmpty(oldPassword)||StringUtils.isEmpty(newPassword)){
				_log.info("jolley >> 修改密码 缺少参数，oldPassword:"+oldPassword+";newPassword:"+newPassword);
				return new UpmsResult(UpmsResultConstant.FAILED, "缺少参数");
			}else{
				UpmsUserInfo user = getUpmsUserInfo();
				return userService.updatePassword(user,oldPassword,newPassword);
			}
		}catch(Exception e){
			_log.error("jolley >> 修改密码失败  error", e);
			return new UpmsResult(UpmsResultConstant.FAILED, "修改密码失败");
		}
	}
	
	//
	@ApiOperation("获取当前登录人员信息")
	@RequestMapping(value="userInfo", method = RequestMethod.POST)
	@ResponseBody
	public UpmsResult userInfo(){
		try{
			return new UpmsResult(UpmsResultConstant.SUCCESS, getUpmsUserInfo());
		}catch(Exception e){
			_log.error("jolley >> 修改密码失败  error", e);
			return new UpmsResult(UpmsResultConstant.FAILED, "修改密码失败");
		}
	}
}
