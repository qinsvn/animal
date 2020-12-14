<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en" class="app">

	<head>
		<meta charset="utf-8" />
		<title>农标科技</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<link rel="icon" type="image/png" href="${basePath}/resources/images/web/logo.ico">
		<link rel="stylesheet" href="${basePath}/resources/css/web/bootstrap.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/animate.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/font-awesome.min.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/simple-line-icons.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/font.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/app.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/jqpage.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/Style_user.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/loaders.css" type="text/css" />
		<link rel="stylesheet" href="${basePath}/resources/css/web/jquery.dialogbox.css" type="text/css" />
		<!--[if lt IE 9]>
    <script src="${basePath}/resources/js/web/ie/html5shiv.js"></script>
    <script src="${basePath}/resources/js/web/ie/respond.min.js"></script>
    <script src="${basePath}/resources/js/web/ie/excanvas.js"></script>

    <![endif]-->
		<style>

		</style>
	</head>

	<body class="">
		<div class="loadrtkuno">

			<div class="loader-inner ball-pulse-sync">

				<div></div>

				<div></div>

				<div></div>

			</div>

		</div>
		<div class="col-lg-12" style="padding: 0">
			<!-- .breadcrumb -->
			<ul class="breadcrumb">
				<li><i class="fa icon-users"></i> 基础数据</li>
				<li class="active">养殖人员管理</li>
			</ul>
			<!-- / .breadcrumb -->
		</div>
		<section class="vboxtable" style="padding-right: 15px;padding-left: 15px;">
			<section class="scrollable padder">
				<div class="row">
					<section class="panel panel-default">
						<header class="panel-heading" style="min-height: 40px;">
						</header>
						<div class="row m-l m-b m-t">
							<div class="form-group">
								<label>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<select id="fdLocked" class="form-control">
										<option value="">请选择状态</option>
										<option value="0">未锁定</option>
										<option value="1">锁定</option>
										
									</select>
								</div>
								<label>真实姓名  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="search" type="text" class="input-sm form-control">
								</div>
								</div>
							<div class="form-group">
								<label>所属养殖户  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="companyName" type="text" class="input-sm form-control">
								</div>
								<label>手机号码  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="fdPhone" type="text" class="input-sm form-control">
								</div>
							</div>
						</div>
						<div class="row m-l m-b">
							<div class=" m-b-xs">
								<shiro:hasPermission name="c:account:read">
									<button class="btn btn-sm btn-info" id="searchBtn" type="button" style="padding:5px 22px;">查询</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="c:account:create">
									<button class="btn btn-sm btn-info" onclick="addUser()">新增账号</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="c:account:delete">
									<button class="btn btn-sm btn-info" id="deleteUser">删除账号</button>
								</shiro:hasPermission>
							</div>
						</div>
						<div class="table-responsive xScrollBox">
							<table class="table table-striped b-t b-light ">
								<thead>
									<tr>
										<th style="width:20px;"><label class="checkbox m-n i-checks"><input id="selectAll" type="checkbox" ><i></i></label></th>
										<th>账号</th>
										<th>电子邮箱</th>
										<th class="th-sortable" data-toggle="class">真实姓名 </th>
										<th>手机号码</th>
										<th>身份证号码</th>
										<th>所属养殖户</th>
										<th>角色类型</th>
										<th>添加时间</th>
										<th>状态</th>
										<th width="140">操作</th>
									</tr>
								</thead>
								<tbody id="umantable">

								</tbody>
							</table>
						</div>
						<footer class="panel-footer">
							<div class="row">
								<div class="col-sm-12 text-right text-center-xs">
									<div id="pages">
										<div class="pages">
											<div class="count">共 <span id="itemCount">0</span> 条，每页 <span id="itemPage">10</span>条 </div>
											<input type="hidden" value="1" />
										</div>
									</div>
								</div>
							</div>
						</footer>
					</section>
				</div>
			</section>
		</section>
		<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
		<div id="auto-close-dialogBox"></div>

		<!-- 授权 -->
		<div class="modal fade in" id="modal-form">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body wrapper-lg">
						<div class="row">
							<section class="panel panel-default">
								<header class="panel-heading" style="min-height: 40px;">
									分配角色
								</header>
								<div class="table-responsive roleDiv">
									<input type="hidden" id="userid">
									<table class="table table-striped b-t b-light">
										<thead>
											<tr>
												<th style="width:20px;"><label class="checkbox m-n i-checks"><input id="selectAllRole" type="checkbox" ><i></i></label></th>
												<th class="th-sortable" data-toggle="class">角色名称 </th>
												<th>角色描述</th>
											</tr>
										</thead>
										<tbody id="rolesBody">
										</tbody>
									</table>
								</div>
								<div class="form-group" style="margin-bottom: 0">
									<div class="col-sm-12 col-sm-offset-2" style="margin-left: 0;text-align: center; margin-top: 20px;">
										<button type="submit" class="btn btn-default" id="cancel">取&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;消</button>
										<shiro:hasPermission name="c:account:distribution">
											<button type="submit" id="roles" class="btn btn-primary">确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;定</button>
										</shiro:hasPermission>
									</div>
								</div>
							</section>
						</div>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		
		<script src="${basePath}/resources/js/web/jquery.min.js"></script>
		<!-- Bootstrap -->
		<script src="${basePath}/resources/js/web/bootstrap.js"></script>
		<!-- App -->
		<script src="${basePath}/resources/js/web/app.js"></script>
		<script src="${basePath}/resources/js/web/app.plugin.js"></script>
		<script src="${basePath}/resources/js/web/jquery.page.js"></script>
		<script src="${basePath}/resources/js/web/user/list.js"></script>
		<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
		<script src="${basePath}/resources/js/web/base.js"></script>
	</body>

</html>