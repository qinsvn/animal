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
		<shiro:hasPermission name="c:stock:substandard">
		<script type="text/javascript">
			  var c_stock_substandard = "0";
			</script>
		</shiro:hasPermission>
								
								
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
				<li><i class="fa icon-users"></i> 健康管理</li>
				<li class="active">库存管理</li>
			</ul>
			<!-- / .breadcrumb -->
		</div>
		<section class="vboxtable" style="padding-right: 15px;padding-left: 15px;">
			<section class="scrollable padder">
				<div class="row">
					<section class="panel panel-default">
						<header class="panel-heading" style="min-height: 40px;">
							<div id="sumInfo" style="color: #f90707;font-size:16pt;font-weight: bold;"></div>
						</header>
						<div class="row m-l m-b m-t">
							<div class="form-group">
								<label>&nbsp;&nbsp;&nbsp;&nbsp;编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号 ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="dataCode" type="text" class="input-sm form-control">
								</div>
								<label>&nbsp;&nbsp;&nbsp;&nbsp;种类 ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="dataTypeDes" type="text" class="input-sm form-control">
								</div>
							</div>
							<div class="form-group">
								<label>入库时间 ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="startCreateTime" type="text" class="input-sm  form-control input-inline">
								</div>
								<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="endCreateTime" type="text" class="input-sm  form-control input-inline">
								</div>
								
							</div>
						</div>
						<div class="row m-l m-b">
							<div class=" m-b-xs">
								<shiro:hasPermission name="c:stock:read">
									<button class="btn btn-sm btn-info" id="searchBtn" type="button" style="padding:5px 22px;">查询</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="c:stock:delete">
									<button class="btn btn-sm btn-info" id="deleteDeviceButt">删除库存记录</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="c:stock:export">
									<button class="btn btn-sm btn-info" id="export">导出</button>
								</shiro:hasPermission>
								
							</div>
						</div>
						<div class="table-responsive xScrollBox">
							<table class="table table-striped b-t b-light">
								<thead>
									<tr>
										<th style="width:20px;"><label class="checkbox m-n i-checks"><input id="selectAll" type="checkbox" ><i></i></label></th>
										<th>编号</th>
										<th>种类</th>
										<th>操作人</th>
										<th>入库时间</th>
										<th>养殖户</th>
										<th>操作</th>
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
		<script src="${basePath}/resources/js/web/stockData/p_list_stockData.js"></script>
		<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
	   <script src="${basePath}/resources/plugins/laydate/laydate.js"></script>
		<script src="${basePath}/resources/js/web/base.js"></script>
	</body>

</html>
