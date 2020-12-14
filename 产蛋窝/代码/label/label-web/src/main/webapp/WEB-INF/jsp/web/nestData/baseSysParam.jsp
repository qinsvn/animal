<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>农标科技</title>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="icon" type="image/png"
	href="${basePath}/resources/images/web/logo.ico">
<link rel="stylesheet"
	href="${basePath}/resources/css/web/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="${basePath}/resources/css/web/animate.css"
	type="text/css" />
<link rel="stylesheet"
	href="${basePath}/resources/css/web/font-awesome.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="${basePath}/resources/css/web/simple-line-icons.css"
	type="text/css" />
<link rel="stylesheet" href="${basePath}/resources/css/web/font.css"
	type="text/css" />
<link rel="stylesheet" href="${basePath}/resources/css/web/app.css"
	type="text/css" />
<link rel="stylesheet" href="${basePath}/resources/css/web/jqpage.css"
	type="text/css" />
<link rel="stylesheet" href="${basePath}/resources/css/web/loaders.css"
	type="text/css" />
<link rel="stylesheet"
	href="${basePath}/resources/css/web/Style_user.css" type="text/css" />
<link rel="stylesheet"
	href="${basePath}/resources/css/web/jquery.dialogbox.css"
	type="text/css" />
<link rel="stylesheet"
	href="${basePath}/resources/js/web/datepicker/bootstrap-datepicker.css"
	type="text/css" />

<!--[if lt IE 9]>
    <script src="${basePath}/resources/js/web/ie/html5shiv.js"></script>
    <script src="${basePath}/resources/js/web/ie/respond.min.js"></script>
    <script src="${basePath}/resources/js/web/ie/excanvas.js"></script>
    <![endif]-->
<style>
html,.vboxtable {
	width: 100%;
	height: 100%;
}
</style>
</head>
<body class="infoBody">
	<div class="loadrtkuno" style="display: none">
		<div class="loader-inner ball-pulse-sync">
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>
	<div class="col-lg-12" style="padding: 0">
		<!-- .breadcrumb -->
		<ul class="breadcrumb">
			<li><i class="fa icon-users"></i> 数据管理</li>
			<li id="liTitle" class="active">参数设置</li>
		</ul>
		<!-- / .breadcrumb -->
	</div>
	<!-- 新增用户内容Start -->
	<div class="row" id="inner_content"
		style="padding-right: 15px; padding-left: 15px;">
		<div class="col-sm-12">

			<section class="panel panel-default" style="margin-bottom: 40px;">
				<header class="panel-heading font-bold" id="headTitle">参数设置</header>
				<div class="panel-body">
					<div class="bs-example form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">鉴定产蛋最小时长</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" autocomplete="off" id="layMinDuration" >
							</div>
							<label class="col-sm-1 control-label" style="padding-left:0px;text-align:left;">单位：分钟</label>
							
							<label class="col-sm-2 control-label">鉴定产蛋最大时长</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="layMaxDuration" >
							</div>
							<label class="col-sm-1 control-label" style="padding-left:0px;text-align:left;">单位：分钟</label>
						</div>
					
						<div class="form-group">
							<label class="col-sm-2 control-label">鉴定产蛋最小体重</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" autocomplete="off"  id="layMinWeight">
							</div>
							<label class="col-sm-1 control-label" style="padding-left:0px;text-align:left;">单位：克</label>
							<label class="col-sm-2 control-label">鉴定抱窝最小时长</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" autocomplete="off"  id="layMinNest">
							</div>
							<label class="col-sm-1 control-label" style="padding-left:0px;text-align:left;">单位：分钟</label>
						</div>
							<div class="line line-dashed b-b line-lg pull-in"></div>
						<div class="form-group" style="margin-bottom: 0">
							<div class="col-sm-1"></div>
							<div class="col-sm-4">
									<button type="button" class="btn btn-primary" id="submit">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
							</div>
						</div>
					</div>
				</div>
			</section>

	<script src="${basePath}/resources/js/web/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="${basePath}/resources/js/web/bootstrap.js"></script>
	<!-- App -->
	<script src="${basePath}/resources/js/web/app.js"></script>
	<script src="${basePath}/resources/js/web/app.plugin.js"></script>
	<script src="${basePath}/resources/js/web/jquery.page.js"></script>
	<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
	<script src="${basePath}/resources/js/web/base.js"></script>
	<script
		src="${basePath}/resources/js/web/slimscroll/jquery.slimscroll.min.js"></script>
	<script
		src="${basePath}/resources/js/web/datepicker/bootstrap-datepicker.js"></script>
	<script
		src="${basePath}/resources/js/web/datepicker/bootstrap-datepicker.zh-CN.js"></script>
	<script
		src="${basePath}/resources/js/web/distpickerCity/distpicker.data.js"></script>
	<script src="${basePath}/resources/js/web/distpickerCity/distpicker.js"></script>
	<script src="${basePath}/resources/js/web/common/textlimit.js"></script>
	<script src="${basePath}/resources/js/web/nestData/sysParam.js"></script>
</body>
</html>