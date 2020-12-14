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
			<li> 禽类信息查询</li>
		</ul>
		<!-- / .breadcrumb -->
	</div>
	<!-- 新增用户内容Start -->
	<div class="row" id="inner_content"
		style="padding-right: 15px; padding-left: 15px;">
		<div class="col-sm-12">
			<section class="panel panel-default" style="margin-bottom: 5px;">
				<div class="panel-body">
					<div class="bs-example form-horizontal">
						<div class="line line-dashed b-b line-lg pull-in"></div>
						<div class="form-group">
							<label class="col-sm-1 control-label">脚号</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" autocomplete="off"  id="rfidNum" placeholder="输入脚号">
							</div>
							<div class="col-sm-1">
								 <button type="button" class="btn btn-default" onclick="Info()">查询</button>
							</div>
						</div>
					</div>
				</div>
			</section>


			<section class="panel panel-default" style="margin-bottom: 40px;">
				<div class="panel-body">
					<div class="bs-example form-horizontal">
						<div class="form-group">
							<label class="col-sm-1 control-label">脚号</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" autocomplete="off" id="rfid_num" disabled  >
							</div>
							<label class="col-sm-1 control-label">品种</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="chiken_type"  disabled>
							</div>
					    	<label class="col-sm-1 control-label">养殖户</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="org_name" disabled >
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-1 control-label">供应商</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" autocomplete="off" 	id="pupplier" disabled >
							</div>
							<label class="col-sm-1 control-label">入栏日期</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" autocomplete="off" 	id="create_time" disabled >
							</div>
							<label class="col-sm-1 control-label">销售日期</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" autocomplete="off" 	id="saleTime" disabled >
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-1 control-label">养殖周期</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" autocomplete="off" 	id="diff_date" disabled >
							</div>
						</div>
						
						</div>
					</div>
				</div>
			</section>
		</div>
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
	<script type="text/javascript">
	function Info(){
		$('#rfid_num').val('');
		$('#chiken_type').val('');
		$('#org_name').val('');
		$('#pupplier').val('');
		$('#create_time').val('');
		$('#saleTime').val('');
		$('#diff_date').val('');
		ajaxGetFunctionAsytrue("../chicken/ckInfo?rfidNum="+$('#rfidNum').val(),null,function(data){
			if(data.code==0){
				$('#rfid_num').val(data.data.rfid_num);
				if(data.data.chiken_type){
			     	$('#chiken_type').val(data.data.chiken_type);
				}
				if(data.data.org_name){
				$('#org_name').val(data.data.org_name);
				}	
				if(data.data.pupplier){
				$('#pupplier').val(data.data.pupplier);
				}	
				if(data.data.create_time){
				$('#create_time').val(formatDate(data.data.create_time));
				}	
				if(data.data.saleTime){
				$('#saleTime').val(formatDate(data.data.saleTime));
				}	
				if(data.data.diff_date){
				$('#diff_date').val(data.data.diff_date+'天');
				}	
			}else{
				set_message("auto-close-dialogBox","提示",data.data);
			}
		});
	}
	</script>
</body>
</html>