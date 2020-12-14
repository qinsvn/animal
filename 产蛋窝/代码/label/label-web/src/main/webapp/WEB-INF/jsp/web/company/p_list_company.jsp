<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
    <link rel="stylesheet" href="${basePath}/resources/css/web/Style_company.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/jqpage.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/loaders.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}/resources/css/web/jquery.dialogbox.css" type="text/css" />
    
    <style>
    .select_type{
    height:35px; width: 150px;
    }
    
    </style>
</head>
<body class="">
<input id="basePath" value="${basePath}" type="hidden">
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
            <li><a href="#"><i class="fa icon-users"></i> 机构管理</a></li>
            <li class="active">机构列表</li>
        </ul>
        <!-- / .breadcrumb -->
    </div>
    <section class="vboxtable"  style="padding-right: 15px;padding-left: 15px;">
        <section class="scrollable padder" >
            <div class="row">
            <section class="panel panel-default">
                <header class="panel-heading" style="min-height: 40px;">
                </header>
                <div class="row m-l m-b m-t">
                	<div class="form-group ">
                		<label>机构类型  ：</label>
						<div class="inline width-300" style="padding-right: 30px;">
                			<select id="seach_type" class="form-control">
                				<option value="" >请选择机构类型</option>
								<option value="1" >集团机构</option>
								<option value="2" >内部</option>
							</select>
                		</div>
                		<label>机构名称  ：</label>
                		<div class="inline width-300">
                			<input type="text" id="seach_name" class="form-control"/>
                		</div>
					</div>
                	
                </div>
                 
                <div class="row m-l m-b">
                    <div class=" m-b-xs">
                    	<shiro:hasPermission name="label:company:read">
	                        <button id="seach_btn" class="btn btn-sm btn-info" type="button" style="padding:5px 22px;">查询</button>
	                    </shiro:hasPermission>
	                    <shiro:hasPermission name="label:company:create">
	                    <button id="create_company" class="btn btn-sm btn-info">新增机构</button>
	                    </shiro:hasPermission>
	                    <shiro:hasPermission name="label:company:delete">
	                    <button id="del_company" class="btn btn-sm btn-info">删除机构</button>
	                    </shiro:hasPermission>
                    </div>
                </div>
                
                <div id="alertMsg"></div>
                <div class="table-responsive">
                    <table id="datas" class="table table-striped b-t b-light">
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
	<div class="modal fade in" id="modal-formedit">
	    <div class="modal-dialog">
	        <div class="modal-content" style="width: 700px;height: 700px;overflow: auto;">
	            <div class="modal-body wrapper-lg">
	                <div class="row">
	                    <section class="panel panel-default" style="    margin-bottom: 40px;">
	                        <header class="panel-heading font-bold" id="headTitleEdit">新增机构</header>
	                        <div class="panel-body">
	                            <form class="bs-example form-horizontal" autocomplete="off">
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label ">机构名称<span class="red-color">*</span></label>
	                                    <div class="col-sm-10">
	                                        <input type="text" class="form-control edit_name" id="edit_name" placeholder="机构名称">
	                                        <input type="text" class="hidden" id="edit_cid">
	                                    </div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                <div class="form-group">
	                                	<label class="col-sm-2 control-label">机构类型</label>
	                                	<div class="col-sm-10">
		                                	<select id="typeselect" class="select_type">
												<option value="1" >集团机构</option>
												<option value="2" >内部</option>
											</select>
	                                	</div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label">领域</label>
	                                    <div class="col-sm-10">
	                                        <input type="text" class="form-control edit_domain" id="edit_domain" placeholder="机构所属行业">
	                                    </div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label">电话</label>
	                                    <div class="col-sm-10">
	                                        <input type="tel" class="form-control edit_phone" id="edit_phone" placeholder="例：电话号码xxx-xxxxxxxx，手机号11位">
	                                    </div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label">邮箱</label>
	                                    <div class="col-sm-10">
	                                        <input type="text" class="form-control edit_email" id="edit_email" placeholder="例：xxx@xxx.xxx">
	                                    </div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                <div class="form-group">
	                                    <label class="col-sm-2 control-label">传真</label>
	                                    <div class="col-sm-10">
	                                        <input type="tel" class="form-control edit_fax" id="edit_fax" placeholder="例：xxx-xxxxxxxx">
	                                    </div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                <div class="form-group">
	                                	<label class="col-sm-2 control-label">状态</label>
	                                	<div class="col-sm-10">
		                                	<select id="comboselect" class="select_type">
		                                		<option value="0" >启用</option>
												<option value="1" >冻结</option>
											</select>
	                                	</div>
	                                </div>
	                                <div class="line line-dashed b-b line-lg pull-in"></div>
	                                <div class="form-group" style="margin-bottom: 0">
	                                    <div class="col-sm-4 col-sm-offset-2">
	                                        <a id="cancel" class="btn btn-default">取&nbsp;&nbsp;&nbsp;&nbsp;消</a>
	                                        <shiro:hasPermission name="label:company:update">
	                                        <a id="save" class="btn btn-primary fd_btn hidden">确&nbsp;&nbsp;&nbsp;&nbsp;定</a>
	                                        </shiro:hasPermission>
	                                        <shiro:hasPermission name="label:company:create">
	                                        <a id="create" class="btn btn-primary fd_btn hidden">创&nbsp;&nbsp;&nbsp;&nbsp;建</a>
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
    <script src="${basePath}/resources/js/web/company/app.plugin.js"></script>
    <script src="${basePath}/resources/js/web/jquery.page.js"></script>
    <script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
    <script src="${basePath}/resources/js/web/base.js"></script>
    <script src="${basePath}/resources/js/web/common/textlimit.js"></script>
    <script src="${basePath}/resources/js/web/ajaxfileupload.js"></script>
    <script src="${basePath}/resources/js/web/company/p_list_company.js"></script>
    <script src="${basePath}/resources/js/web/bootstrap-msg.js"></script>
    
</body>
</html>