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
				<li><i class="fa icon-users"></i> 告警通知</li>
				<li class="active">告警通知列表</li>
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
								<label>脚号 ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="search_rfid_num" type="text" class="input-sm form-control">
								</div>
								<label>厂房  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="search_name" type="text" class="input-sm form-control">
								</div>
								
								<label>超时  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="search_startExceed" type="text" class="input-sm form-control" placeholder="可以根据超时时间查询,填写数字(小时)">
								</div>
							</div>
						</div>
						<div class="row m-l m-b">
							<div class=" m-b-xs">
								<shiro:hasPermission name="label:bsWorkshop:read">
									<button class="btn btn-sm btn-info" id="searchBtn" type="button" style="padding:5px 22px;">查询</button>
								</shiro:hasPermission>
							</div>
						</div>
						<div class="table-responsive xScrollBox">
							<table class="table table-striped b-t b-light ">
								<thead>
									<tr>
										<th style="width:20px;"><label class="checkbox m-n i-checks"><input id="selectAll" type="checkbox" ><i></i></label></th>
										<th>脚号</th>
										<th>厂房</th>
										<th>超时</th>
										<th>负责人</th>
										<th>告警时间</th>
										<th >告警信息 </th>
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


		<script src="${basePath}/resources/js/web/jquery.min.js"></script>
		<!-- Bootstrap -->
		<script src="${basePath}/resources/js/web/bootstrap.js"></script>
		<!-- App -->
		<script src="${basePath}/resources/js/web/app.js"></script>
		<script src="${basePath}/resources/js/web/app.plugin.js"></script>
		<script src="${basePath}/resources/js/web/jquery.page.js"></script>
		<script src="${basePath}/resources/js/web/warn/p_list_warn.js"></script>
		<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
		<script src="${basePath}/resources/js/web/base.js"></script>
	</body>

</html>
