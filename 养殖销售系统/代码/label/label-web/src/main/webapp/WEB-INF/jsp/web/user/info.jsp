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
    <link rel="stylesheet" href="${basePath}/resources/css/web/Style_user.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/jquery.dialogbox.css" type="text/css" />
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
        <li><i class="fa icon-users"></i> 权限管理</li>
        <li><a href="./p_list">账号管理</a></li>
        <li id="liTitle" class="active">新增账号</li>
    </ul>
    <!-- / .breadcrumb -->
</div>
<!-- 新增用户内容Start -->
<div class="row" id="inner_content" style="padding-right: 15px;padding-left: 15px;">
    <div class="col-sm-7">
        <section class="panel panel-default" style="    margin-bottom: 40px;">
            <header class="panel-heading font-bold" id="headTitle">新增用户信息</header>
            <div class="panel-body">
            	<div class="bs-example form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">账号<span class="red-color">*</span></label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" autocomplete="off" id="fdAccount" maxlength="20">
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码<span class="red-color span_pwd">*</span></label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="fdPassword" autocomplete="off" maxlength="20">
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">确认密码<span class="red-color span_pwd">*</span></label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" autocomplete="off" id="checkedpwd" maxlength="20">
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                  		<div  id="organBox" class="hidden">
                    	<div class="form-group">
                        <label class="col-sm-2 control-label">养殖户</label>
                        <div class="col-sm-10">
                            <div class="btn-group m-r">
                                <button id="organBtn" data-toggle="dropdown" class="btn btn-sm btn-default dropdown-toggle" style="width: 200px">
                                    <span class="dropdown-label">请选择养殖户</span>
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu dropdown-select" id="organ" style="height:260px;overflow:auto;width:100%;">
                                	<li dataid="-1" class="active"><input type="radio" name="d-s-r" dd="dd"><a href="#">请选择养殖户</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">真实姓名<span class="red-color">*</span></label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="fdName" maxlength="30">
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">电子邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" autocomplete="off" id="fdEmail">
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">手机号码</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" autocomplete="off" id="fdPhone" maxlength="11">
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">身份证号</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="fdIdcard" maxlength="18">
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色分配</label>
                        <div class="col-sm-10">
                        	<div class="btn-group m-r">
                                <button type="button" class="btn btn-default" id="selectRole">请选择角色</button>
                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                        	<div class="btn-group m-r">
                                <button data-toggle="dropdown" class="btn btn-sm btn-default dropdown-toggle" style="width: 120px">
                                    <span class="dropdown-label" id="fdLockedName">未锁定</span>
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu dropdown-select" id="fdLocked">
                                	<li id="unLock" class="active"><input type="radio" name="d-s-r"><a href="javascript:;" data-val="0">未锁定</a></li>
                                    <li id="isLock" class=""><input type="radio" name="d-s-r"><a href="javascript:;" data-val="1">锁定</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>
                    <div class="form-group" style="margin-bottom: 0">
                        <div class="col-sm-4 col-sm-offset-2">
                            <button type="button" class="btn btn-default" id="cancel">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
                            <shiro:hasPermission name="c:account:update">
	                            <button type="button" class="btn btn-primary"  id="update">更&nbsp;&nbsp;&nbsp;&nbsp;新</button>
                            </shiro:hasPermission>
                             <shiro:hasPermission name="c:account:create">
	                            <button type="button" class="btn btn-primary" id="submit">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
                            </shiro:hasPermission>
                        </div>
                    </div>
                 </div>   
            </div>
        </section>
    </div>
</div>
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
                                    <button type="submit" class="btn btn-default" id="cancelRole">取&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;消</button>
                                    <button type="submit" id="roles" class="btn btn-primary">确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;定</button>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
<!-- 新增用户内容End -->
<div id="auto-close-dialogBox"></div>
<script src="${basePath}/resources/js/web/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="${basePath}/resources/js/web/bootstrap.js"></script>
<!-- App -->
<script src="${basePath}/resources/js/web/app.js"></script>
<script src="${basePath}/resources/js/web/app.plugin.js"></script>
<script  src="${basePath}/resources/js/web/jquery.page.js"></script>
<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
<script src="${basePath}/resources/js/web/base.js"></script>
<script src="${basePath}/resources/js/web/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${basePath}/resources/js/web/datepicker/bootstrap-datepicker.js"></script>
<script src="${basePath}/resources/js/web/datepicker/bootstrap-datepicker.zh-CN.js"></script>
<script src="${basePath}/resources/js/web/distpickerCity/distpicker.data.js"></script>
<script src="${basePath}/resources/js/web/distpickerCity/distpicker.js"></script>
<script src="${basePath}/resources/js/web/common/textlimit.js"></script>
<script src="${basePath}/resources/js/web/user/info.js"></script>
</body>
</html>