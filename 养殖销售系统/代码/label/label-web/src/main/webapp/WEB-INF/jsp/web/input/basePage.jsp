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
			<li><i class="fa icon-users"></i> 基础数据</li>
			<li id="liTitle" class="active">数据录入设置</li>
		</ul>
		<!-- / .breadcrumb -->
	</div>
	<!-- 新增用户内容Start -->
	<div class="row" id="inner_content"
		style="padding-right: 15px; padding-left: 15px;">
		<div class="col-sm-12">
<!-- 			<section class="panel panel-default" style="margin-bottom: 40px;"> -->
<!-- 				<header class="panel-heading font-bold" id="headTitle">品种数据参数</header> -->
<!-- 				<div class="panel-body"> -->
<!-- 					<div class="bs-example form-horizontal"> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-sm-1 control-label">品种数据</label> -->
<!-- 							<div class="col-sm-11"> -->
<!-- 								<input type="text" class="form-control" autocomplete="off" id="name" > -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="form-group" style="margin-bottom: 0"> -->
<!-- 							<div class="col-sm-1"></div> -->
<!-- 							<div class="col-sm-4"> -->
<%-- 								<shiro:hasPermission name="c:inputbase:type"> --%>
<!-- 									<button type="button" class="btn btn-primary" id="submit">确&nbsp;&nbsp;&nbsp;&nbsp;定</button> -->
<%-- 								</shiro:hasPermission> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</section> -->

			<section class="panel panel-default" style="margin-bottom: 40px;">
				<header class="panel-heading font-bold" id="headTitle">设置采集禽类基本参数</header>
				<div class="panel-body">
					<div class="bs-example form-horizontal">
						<div class="form-group">
							<label class="col-sm-1 control-label">体重</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" autocomplete="off" id="weight" >
							</div>
							<label class="col-sm-1 control-label">品种</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="chickenType" >
							</div>
						</div>
						<div class="line line-dashed b-b line-lg pull-in"></div>
						<div class="form-group">
							<label class="col-sm-1 control-label">供应商</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" autocomplete="off"  id="supplier">
							</div>
							<div class="col-sm-1">
								 <button type="button" class="btn btn-default" onclick="$('#supplierHis').modal('show')">历史数据</button>
							</div>
							<label class="col-sm-1 control-label">其他情况</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" autocomplete="off"  id="otherInfo">
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0">
							<div class="col-sm-1"></div>
							<div class="col-sm-4">
								<shiro:hasPermission name="c:inputbase:chicken">
									<button type="button" class="btn btn-primary" id="submit2">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
								</shiro:hasPermission>
							</div>
						</div>
					</div>
				</div>
			</section>


			<section class="panel panel-default" style="margin-bottom: 40px;">
				<header class="panel-heading font-bold" id="headTitle">设置采集免疫基本参数</header>
				<div class="panel-body">
					<div class="bs-example form-horizontal">
						<div class="form-group">
							<label class="col-sm-1 control-label">日龄</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" autocomplete="off" id="age" >
							</div>
								<label class="col-sm-1 control-label">体重</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="i_weight" >
							</div>
						</div>
						<div class="line line-dashed b-b line-lg pull-in"></div>
						<div class="form-group">
							<label class="col-sm-1 control-label">疫苗种类</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" autocomplete="off" 	id="immuneType">
							</div>
							<div class="col-sm-1">
								 <button type="button" class="btn btn-default" onclick="$('#immuneTypeHis').modal('show')">历史数据</button>
							</div>
							<label class="col-sm-1 control-label">疫苗结果</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" autocomplete="off" 	id="result">
							</div>
						</div>
						<div class="line line-dashed b-b line-lg pull-in"></div>
							<div class="col-sm-1"></div>
							<div class="col-sm-4">
								<shiro:hasPermission name="c:inputbase:immune">
									<button type="button" class="btn btn-primary" id="submit3">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
								</shiro:hasPermission>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
	

   <!-- 供应商历史 -->
	<div class="modal fade in" id="supplierHis">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body wrapper-lg">
					<div class="row">
						<section class="panel panel-default">
							<div class="table-responsive roleDiv">
								<input type="hidden" id="userid">
								<table class="table table-striped b-t b-light">
									<thead>
										<tr>
											<th class="th-sortable" data-toggle="class">供应商 </th>
											<th class="th-sortable" data-toggle="class">操作 </th>
										</tr>
									</thead>
									<tbody id="supplierHisBody">
									</tbody>
								</table>
							</div>
							<div class="form-group" style="margin-bottom: 0">
								<div class="col-sm-12 col-sm-offset-2" style="margin-left: 0;text-align: center; margin-top: 20px;">
									<button type="submit" class="btn btn-default" onclick="$('#supplierHis').modal('hide')">取&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;消</button>
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


   <!-- 疫苗历史 -->
	<div class="modal fade in" id="immuneTypeHis">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body wrapper-lg">
					<div class="row">
						<section class="panel panel-default">
<!-- 							<header class="panel-heading" style="min-height: 40px;"> -->
<!-- 								疫苗种类 -->
<!-- 							</header> -->
							<div class="table-responsive roleDiv">
								<input type="hidden" id="userid">
								<table class="table table-striped b-t b-light">
									<thead>
										<tr>
											<th class="th-sortable" data-toggle="class">疫苗种类 </th>
											<th class="th-sortable" data-toggle="class">操作 </th>
										</tr>
									</thead>
									<tbody id="immuneTypeHisBody">
									</tbody>
								</table>
							</div>
							<div class="form-group" style="margin-bottom: 0">
								<div class="col-sm-12 col-sm-offset-2" style="margin-left: 0;text-align: center; margin-top: 20px;">
									<button type="submit" class="btn btn-default" onclick="$('#immuneTypeHis').modal('hide')">取&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;消</button>
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
	<script src="${basePath}/resources/js/web/input/info.js"></script>
</body>
</html>