<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta charset="utf-8"/>
    <title>农标科技</title>
    <meta name="description"  content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link rel="icon" type="image/png" href="${basePath}/resources/images/web/logo.ico">
    <link rel="stylesheet" href="${basePath}/resources/css/web/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/animate.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/font-awesome.min.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/simple-line-icons.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/font.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/app.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/jqpage.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/loaders.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}/resources/css/web/jquery.dialogbox.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}/resources/css/web/Style_role.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}/resources/plugins/Ztree/css/demo.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}/resources/plugins/Ztree/css/zTreeStyle.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}/resources/js/web/datepicker/bootstrap-datepicker.css" type="text/css" />

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
<body class="">
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
        <li><i class="fa icon-users"></i> 权限管理</li>
        <li><a href="p_list"><i class="fa fa-list-ul"></i> 角色列表</a></li>
        <li class="active">编辑角色</li>
    </ul>
    <!-- / .breadcrumb -->
</div>
<!-- 新增用户内容Start -->
<section class="vboxtable" style="padding-left:15px; padding-right:15px;">
	<div class="row" id="inner_content" style="padding-right: 15px;padding-left: 15px;">
	    <div class="col-sm-7">
	        <section class="panel panel-default " style="    margin-bottom: 40px;">
	            <header class="panel-heading font-bold" id="headTitle">新增角色信息</header>
	            <div class="panel-body">
	                <form class="bs-example form-horizontal"  autocomplete="off">
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">角色名称<span class="red-color">*</span></label>
	                        <div class="col-sm-10">
	                            <input type="text" class="form-control" id="rolename">
	                        </div>
	                    </div>
	                    <div class="line line-dashed b-b line-lg pull-in"></div>
	                    <!-- <div class="form-group">
	                        <label class="col-sm-2 control-label">状态</label>
	                        <div class="col-sm-10">
	                            <div class="btn-group m-r">
	                                <button data-toggle="dropdown" class="btn btn-sm btn-default dropdown-toggle" style="width: 120px">
	                                    <span class="dropdown-label">启用</span>
	                                    <span class="caret"></span>
	                                </button>
	                                <ul class="dropdown-menu dropdown-select" id="targetRole">
	                                    <li class="active"><input type="radio" name="d-s-r" checked=""><a href="#" data-val="0">启用</a></li>
	                                    <li class=""><input type="radio" name="d-s-r"><a href="#" data-val="1">禁用</a></li>
	                                </ul>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="line line-dashed b-b line-lg pull-in"></div> -->
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">角色描述<span class="red-color">*</span></label>
	                        <div class="col-sm-10">
	                            <textarea class="form-control" rows=5 id="roledesc" style="resize: none;"></textarea>
	                        </div>
	                    </div>
	                    <div class="line line-dashed b-b line-lg pull-in"></div>
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">权限管理</label>
	                    	<div class="col-sm-10">
		                    	<ul id="dptList" class="ztree"></ul>
	                    	</div>
	                    </div>
	                    <div class="line line-dashed b-b line-lg pull-in"></div>
	                    <div class="form-group" style="margin-bottom: 0">
	                        <div class="col-sm-4 col-sm-offset-2">
	                            <button type="button" class="btn btn-default" onclick="closeBtn()">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
	                            <shiro:hasPermission name="label:role:update">
	                            <button type="button" class="btn btn-primary" id="saveBtn">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
	                            </shiro:hasPermission>
	                        </div>
	                    </div>
	                </form>
	            </div>
	        </section>
	    </div>
	</div>
</section>
<!-- 新增用户内容End -->
<div id="auto-close-dialogBox"></div>
<script src="${basePath}/resources/js/web/jquery-2.0.3.min.js"></script>
<!-- Bootstrap -->
<script src="${basePath}/resources/js/web/bootstrap.js"></script>
<!-- App -->
<script src="${basePath}/resources/js/web/app.js"></script>
<script src="${basePath}/resources/js/web/app.plugin.js"></script>
<script src="${basePath}/resources/js/web/jquery.page.js"></script>
<script src="${basePath}/resources/js/web/common/textlimit.js"></script>
<script src="${basePath}/resources/js/web/authManagement/alter.js"></script>
<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
<script src="${basePath}/resources/js/web/base.js"></script>
<script src="${basePath}/resources/js/web/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${basePath}/resources/plugins/Ztree/js/jquery.ztree.core-3.2.js"></script>
<script src="${basePath}/resources/plugins/Ztree/js/jquery.ztree.exedit-3.2.js"></script>
<script src="${basePath}/resources/plugins/Ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script src="${basePath}/resources/js/web/datepicker/bootstrap-datepicker.js"></script>
<script src="${basePath}/resources/js/web/datepicker/bootstrap-datepicker.zh-CN.js"></script>
<script src="${basePath}/resources/js/web/distpickerCity/distpicker.data.js"></script>
<script src="${basePath}/resources/js/web/distpickerCity/distpicker.js"></script>
</body>
</html>