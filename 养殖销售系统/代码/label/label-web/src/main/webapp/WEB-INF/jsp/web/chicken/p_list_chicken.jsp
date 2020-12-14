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
				<li><i class="fa icon-users"></i> 品种管理</li>
				<li class="active">禽类管理</li>
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
								<label>&nbsp;&nbsp;&nbsp;&nbsp;脚&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号 ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="rfidNum" type="text" class="input-sm form-control">
								</div>
								<label>品种  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="chikenType" type="text" class="input-sm form-control">
								</div>
								<label>供应商  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="pupplier" type="text" class="input-sm form-control">
								</div>
							</div>
							<div class="form-group">
								<label>采集时间 ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="startCreateTime" type="text" class="input-sm  form-control input-inline">
								</div>
								<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="endCreateTime" type="text" class="input-sm  form-control input-inline">
								</div>
								<label>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
										<select id="status" class="form-control">
												<option value="-1" >请选择状态</option>
		                                		<option value="0" >饲养中</option>
												<option value="2" >已出库</option>
												<option value="3" >次品</option>
											</select>
								</div>
							</div>
							
							<div class="form-group">
								<label>未打疫苗  ：</label>
								<div class="inline width-300" style="padding-right: 37px;">
									<input id="unImmu" type="text" class="input-sm form-control">
								</div>
							</div>
							
						</div>
						<div class="row m-l m-b">
							<div class=" m-b-xs">
								<shiro:hasPermission name="c:chicken:read">
									<button class="btn btn-sm btn-info" id="searchBtn" type="button" style="padding:5px 22px;">查询</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="c:chicken:delete">
									<button class="btn btn-sm btn-info" id="deleteDeviceButt">删除禽类</button>
								</shiro:hasPermission>
								
								<shiro:hasPermission name="c:chicken:export">
									<button class="btn btn-sm btn-info" id="export">导出</button>
								</shiro:hasPermission>
							</div>
						</div>
						<div class="table-responsive xScrollBox">
							<table class="table table-striped b-t b-light">
								<thead>
									<tr>
										<th style="width:20px;"><label class="checkbox m-n i-checks"><input id="selectAll" type="checkbox" ><i></i></label></th>
										<th>脚号</th>
										<th>品种</th>
										<th>体重</th>
										<th>供应商</th>
										<th>采集日期</th>
										<th>采集人</th>
										<th>所属养殖户</th>
										<th>状态</th>
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



<!--  编辑 -->
	<div class="modal fade in" id="edit_win">
	    <div class="modal-dialog">
	        <div class="modal-content" style="width: 700px;height: 470px;overflow: auto;">
	            <div class="modal-body wrapper-lg">
	                <div class="row">
	                    <section class="panel panel-default" style=" margin-bottom: 40px;">
	                        <header class="panel-heading font-bold" >修改</header>
	                        <div class="panel-body">
	                            <form class="bs-example form-horizontal" autocomplete="off">
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label ">脚号</label>
	                                    <div class="col-sm-10">
	                                        <input type="text" class="form-control edit_name" id="edit_rfidNum"  disabled>
	                                        <input type="hidden"  id="edit_id" >
	                                    </div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label ">品种</label>
	                                    <div class="col-sm-10">
	                                        <input type="text" class="form-control edit_name" id="edit_chikenType" >
	                                    </div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label ">体重</label>
	                                    <div class="col-sm-10">
	                                        <input type="text" class="form-control edit_name" id="edit_weight" >
	                                    </div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label ">供应商</label>
	                                    <div class="col-sm-10">
	                                        <input type="text" class="form-control edit_name" id="edit_pupplier" >
	                                    </div>
	                                </div>
	                                <div class="form-group" style="margin-bottom: 0">
	                                    <div class="col-sm-4 col-sm-offset-2">
	                                        <a id="edit_cancel" class="btn btn-default">取&nbsp;&nbsp;&nbsp;&nbsp;消</a>
	                                        	<shiro:hasPermission name="c:chicken:update">
	                                        <a id="edit_create" class="btn btn-primary fd_btn ">修&nbsp;&nbsp;&nbsp;&nbsp;改</a>
	                                        </shiro:hasPermission>
	                                    </div>
	                                </div>
	                            </form>
	                        </div>
	                    </section>
	                </div>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	</div>
	


		<script src="${basePath}/resources/js/web/jquery.min.js"></script>
		<!-- Bootstrap -->
		<script src="${basePath}/resources/js/web/bootstrap.js"></script>
		<!-- App -->
		<script src="${basePath}/resources/js/web/app.js"></script>
		<script src="${basePath}/resources/js/web/app.plugin.js"></script>
		<script src="${basePath}/resources/js/web/jquery.page.js"></script>
		<script src="${basePath}/resources/js/web/chicken/p_list_ckicken.js"></script>
		<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
	   <script src="${basePath}/resources/plugins/laydate/laydate.js"></script>
		<script src="${basePath}/resources/js/web/base.js"></script>
	</body>

</html>
