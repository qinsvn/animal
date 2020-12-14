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
<link rel="stylesheet" href="${basePath}/resources/css/web/font.css" type="text/css" />
<link rel="stylesheet" href="${basePath}/resources/css/web/app.css" type="text/css" />
<style>
html, .vboxtable {
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
			<li><i class="fa icon-users"></i> 系统管理</li>
			<li><a>命令行</a></li>
		</ul>
		<!-- / .breadcrumb -->
	</div>
	<!-- 新增用户内容Start -->
	<div class="row" id="inner_content"
		style="padding-right: 15px; padding-left: 15px;">
		<div class="col-sm-7">
			<section class="panel panel-default" style="margin-bottom: 40px;">
				<div class="panel-body">
					<div class="bs-example form-horizontal">
						<div class="form-group">
							<div class="col-sm-10">
								<input type="text" class="form-control" autocomplete="off" id="cmd" >
							</div>
								 <button type="button"  class="btn btn-primary" onclick="sendMgs()">发送</button>
						</div>
						<div class="line line-dashed b-b line-lg pull-in"></div>
						<div class="form-group">
							<div class="col-sm-12">
								<textarea class="form-control"  id="ctx" name="ctx" rows="20" style="min-width: 90%"></textarea>
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
	<script src="${basePath}/resources/js/web/base.js"></script>
	<script type="text/javascript">
		function recMgs(mgs){
			var ctx = mgs+'\r\n'+$('#ctx').val();
			$('#ctx').val(ctx)
		}
		function sendMgs() {
			var message = $('#cmd').val();
			if(message==null||message==''){
				return;
			}
			var obj = {type:'0',message:message};
			window.parent.sendMgs(JSON.stringify(obj));
			$('#cmd').val('');
		}
	</script>
</body>
</html>