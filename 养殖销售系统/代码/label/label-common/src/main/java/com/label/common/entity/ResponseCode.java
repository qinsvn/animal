package com.label.common.entity;

import java.util.HashMap;
import java.util.Map;

public class ResponseCode {

	public static final String CODE_UNDEFINE = ""; // 未定义
	public static final String CODE_00 = "00"; // 成功
	
	// A开头表示action逻辑控制层出错
	public static final String CODE_A101 = "A101"; // 缺少请求参数
	public static final String CODE_A102 = "A102"; // 请求参数格式出错
	public static final String CODE_A103 = "A103"; // 您已离线，请重新登录
	public static final String CODE_A104 = "A104"; // 请输入用户名
	public static final String CODE_A105 = "A105"; // 请输入密码
	public static final String CODE_A106 = "A106"; // 权限不足
	public static final String CODE_A107 = "A107"; // 操作失败
	public static final String CODE_A108 = "A108"; // 获取数据失败
	public static final String CODE_A109 = "A109"; // 添加数据失败
	public static final String CODE_A110 = "A110"; // 修改数据失败
	public static final String CODE_A111 = "A111"; // 删除数据失败
	
	// B开头表示services业务逻辑控制层出错
	// 企业后台
	public static final String CODE_B101 = "B101"; // 账号不存在
	public static final String CODE_B102 = "B102"; // 密码错误
	public static final String CODE_B103 = "B103"; // 登录账号被锁定
	public static final String CODE_B104 = "B104"; // 用户数据不存在
	public static final String CODE_B105 = "B105"; // 用户登陆异常
	public static final String CODE_B106 = "B106"; // 公司不存在
	public static final String CODE_B107 = "B107"; // 账号或密码错误
	public static final String CODE_B108 = "B108"; // 账号已存在
	public static final String CODE_B109 = "B109"; // 工号已存在
	public static final String CODE_B110 = "B110"; // 组号已存在
	public static final String CODE_B201 = "B201"; // 已存在该数据
	public static final String CODE_B202 = "B202"; // 数据不存在
	public static final String CODE_B203 = "B203"; // 名称已存在
	public static final String CODE_B204 = "B204"; // 父节点不存在
	public static final String CODE_B205 = "B205"; // 您已评分或链接已失效！
	public static final String CODE_B206 = "B206"; // 消息已被处理
	public static final String CODE_B207 = "B207"; // 您已留言或链接已失效！
	public static final String CODE_B208 = "B208"; // 电话号码已存在
	
	public static final String CODE_B215 = "B215"; // 取消同步信息失败
	public static final String CODE_B216 = "B216"; // 同步信息失败
	
	public static final String CODE_B210 = "B210"; // 获取信息失败
	public static final String CODE_B222 = "B222"; // 该关键字已被使用
	public static final String CODE_B223 = "B223"; // 父节点已被删除
	// C开头表示dao数据库操作层出错
	public static final String CODE_C101 = "C101"; // 查询数据失败
	public static final String CODE_C102 = "C102"; // 修改数据失败
	public static final String CODE_C103 = "C103"; // 删除数据失败
	public static final String CODE_C104 = "C104"; // 添加数据失败
	
	// D开头表示文件操作失败
	public static final String CODE_D101 = "D101"; // 文件不存在异常
	public static final String CODE_D102 = "D102"; // 修改文件异常
	public static final String CODE_D103 = "D103"; // 删除文件异常
	public static final String CODE_D104 = "D104"; // 上传文件失败
	public static final String CODE_D105 = "D105"; // 上传文件为空文件
	public static final String CODE_D106 = "D106"; // 上传文件类型不支持
	public static final String CODE_D107 = "D107"; // 上传文件大小不符合
	
	private static final Map<String, String> MAP_MESSAGE  = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(CODE_00, "成功");
			
			put(CODE_A101, "缺少请求参数");
			put(CODE_A102, "请求参数格式出错");
			put(CODE_A103, "您已离线，请重新登录");
			put(CODE_A104, "请输入用户名");
			put(CODE_A105, "请输入密码");
			put(CODE_A106, "权限不足");
			put(CODE_A107, "操作失败");
			put(CODE_A108, "获取数据失败");
			put(CODE_A109, "添加数据失败");
			put(CODE_A110, "修改数据失败");
			put(CODE_A111, "删除数据失败");
			
			put(CODE_B101, "账号不存在");
			put(CODE_B102, "密码错误");
			put(CODE_B103, "登录账号被锁定");
			put(CODE_B104, "用户数据不存在");
			put(CODE_B105, "用户登陆异常");
			put(CODE_B106, "公司不存在");
			put(CODE_B107, "账号或密码错误");
			put(CODE_B108, "账号已存在");
			put(CODE_B109, "工号已存在");
			put(CODE_B110, "组号已存在");
			put(CODE_B201, "已存在该数据");
			put(CODE_B202, "数据不存在");
			put(CODE_B203, "名称已存在");
			put(CODE_B204, "父节点不存在");
			put(CODE_B205, "您已评分或链接已失效！");
			put(CODE_B206, "消息已被处理");
			put(CODE_B207, "您已留言或链接已失效！");
			put(CODE_B208, "电话号码已存在");
			put(CODE_B210, "获取信息失败");
			
			put(CODE_B215, "取消同步信息失败");
			put(CODE_B216, "同步信息失败");
			
			put(CODE_B222, "该关键字已被使用");
			put(CODE_B223, "父节点已被删除");
			
			put(CODE_C101, "查询数据失败");
			put(CODE_C102, "修改数据失败");
			put(CODE_C103, "删除数据失败");
			put(CODE_C104, "添加数据失败");
			
			put(CODE_D101, "文件不存在异常");
			put(CODE_D102, "修改文件异常");
			put(CODE_D103, "删除文件异常");
			put(CODE_D104, "上传文件失败");
			put(CODE_D105, "上传文件为空文件");
			put(CODE_D106, "上传文件类型不支持");
			put(CODE_D107, "上传文件大小不符合");
			
		}
	};
	
	/** 返回code对应的信息 */
	public static String getMessage(String code) {
		String message = null;
		
		if(code != null && code.length() > 0) {
			message = MAP_MESSAGE.get(code);
		}
		
		return message;
	}
}
