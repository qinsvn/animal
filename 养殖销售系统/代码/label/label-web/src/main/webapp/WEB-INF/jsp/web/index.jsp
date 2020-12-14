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
    <title>鸡缘巧合养殖管理系统</title>
    <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link rel="icon" type="image/png" href="${basePath}/resources/images/web/logo.ico">
    <link rel="stylesheet" href="${basePath}/resources/css/web/bootstrap.css" type="text/css"/>
<%--     <link rel="stylesheet" href="${basePath}/resources/css/web/bootoast.css"> --%>
    <link rel="stylesheet" href="${basePath}/resources/css/web/animate.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/font-awesome.min.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/simple-line-icons.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/jquery.dialogbox.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}/resources/css/web/font.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/css/web/app.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/resources/dg/css/bootoast.css">
    
    <link rel="stylesheet" href="${basePath}/resources/plugins/Hui-iconfont_v1.0.9/H-ui.min.css" type="text/css"/>
    <link rel="stylesheet" href="${basePath}/resources/plugins/Hui-iconfont_v1.0.9/iconfont.min.css" type="text/css"/>
    <!--[if lt IE 9]>
    <script src="${basePath}/resources/js/web/ie/html5shiv.js"></script>
    <script src="${basePath}/resources/js/web/ie/respond.min.js"></script>
    <script src="${basePath}/resources/js/web/ie/excanvas.js"></script>
    <![endif]-->
    <style type="text/css">
		.Hui-iconfont{font-size: 18px;}
	</style>
</head>
<body class="">
<section class="vbox">
    <header class="bg-white-only header header-md navbar navbar-fixed-top-xs">
        <div class="navbar-header aside bg-info nav-xs">
            <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
                <i class="icon-list"></i>
            </a>
            <!-- logo start -->
            <a href="index.html" class="navbar-brand text-lt">
                <i class="">
                    <img src="${basePath}/resources/images/web/logo.png">
                </i>
                <img src="${basePath}/resources/images/web/logo.png" alt="." class="hide">
                <span class="hidden-nav-xs m-l-sm">鸡缘巧合养殖</span>
            </a>
            <!-- logo end -->
            <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".user">
                <i class="icon-settings"></i>
            </a>
        </div>
        <ul class="nav navbar-nav hidden-xs">
            <li>
                <a href="#nav,.navbar-header" data-toggle="class:nav-xs,nav-xs" class="text-muted" id="collspan">
                    <i class="fa fa-indent text"></i>
                    <i class="fa fa-dedent text-active"></i>
                </a>
            </li>
        </ul>
        <div class="navbar-right ">
            <ul class="nav navbar-nav m-n hidden-xs nav-user user">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle bg clear" data-toggle="dropdown">
                      <%--   <span class="thumb-sm avatar pull-right m-t-n-sm m-b-n-sm m-l-sm">
                            <img src="${basePath}/resources/images/web/a0.png" alt="...">
                         </span> --%>
                        <input id="_account" type="hidden" value="${account}">
                        <span class="sysUserName">${name}</span> <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight">
<!--                         <li> -->
<!--                             <span class="arrow top"></span> -->
<!--                             <a href="#modal-form" data-toggle="modal">修改密码</a> -->
<!--                         </li> -->
                        <li>
                            <a href="#"> 帮助 </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="../sso/logout">退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </header>
    <section class="seconwarp">
        <section class="hbox stretch" style="display:-webkit-box;display:-webkit-flex;display:-ms-flexbox;display: flex;flex-direction: row;">
            <!-- .aside -->
            <aside class="bg-black dk nav-xs aside hidden-print" id="nav">
                <section class="vbox">
                    <section class="w-f-md scrollable">
                        <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0"
                             data-size="10px" data-railOpacity="0.2">
                            <!-- nav 菜单在这里 -->
                            <nav class="nav-primary hidden-xs">
                                <ul class="nav" data-ride="collapse" style="padding-top: 15px">
                                
                                   <shiro:hasPermission name="c:system:manage">
                                    <li id="showPermiss">
                                        <a href="#" class="auto">
                                            <span class="pull-right text-muted">
                                              <i class="fa fa-angle-left text"></i>
                                              <i class="fa fa-angle-down text-active"></i>
                                            </span>
                                            <i class="icon Hui-iconfont">&#xe62e;</i>
                                            <span>系统管理</span>
                                        </a>
                                        <ul class="nav dk text-sm" id="permissions">
                                        <shiro:hasPermission name="c:role:read">
                                            <li>
                                                <a href="${basePath}/web/role/p_list" target="mainFrame" class="auto">
                                                     <i class="icon Hui-iconfont">&#xe705;</i>
                                                    <span>角色管理</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        </ul>
                                    </li>
                                    </shiro:hasPermission>
                                    
                                      <shiro:hasPermission name="c:basedata:manage">
                                      <li id="showPermiss">
                                        <a href="#" class="auto">
                                            <span class="pull-right text-muted">
                                              <i class="fa fa-angle-left text"></i>
                                              <i class="fa fa-angle-down text-active"></i>
                                            </span>
                                            <i class="icon Hui-iconfont">&#xe62e;</i>
                                            <span>基础数据</span>
                                        </a>
                                        <ul class="nav dk text-sm" id="permissions">
                                        
	                                        <shiro:hasPermission name="c:dpt:read">
			                                    <li>
			                                        <a href="${basePath}/web/company/pagecompany" target="mainFrame">
			                                           <i class="icon Hui-iconfont">&#xe643;</i>
			                                            <span>养殖户管理</span>
			                                        </a>
			                                    </li>
			                                </shiro:hasPermission>
	                                
                                        <shiro:hasPermission name="c:account:read">
                                            <li>
                                                <a href="${basePath}/web/user/p_list" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe611;</i>
                                                    <span>养殖人员管理</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="c:inputbase:read">
                                            <li>
                                                <a href="${basePath}/web/input/basePage" target="mainFrame" class="auto">
                                                     <i class="icon Hui-iconfont">&#xe705;</i>
                                                    <span>数据录入设置</span> 
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        </ul>
                                    </li>
                                    </shiro:hasPermission>
                                    
                                     <!-- / 开始 -->
                                      <shiro:hasPermission name="c:type:manage">
                                       <li id="showPermiss">
                                        <a href="#" class="auto">
                                            <span class="pull-right text-muted">
                                              <i class="fa fa-angle-left text"></i>
                                              <i class="fa fa-angle-down text-active"></i>
                                            </span>
                                             <i class="icon Hui-iconfont">&#xe70a;
                                            </i>
                                            <span>品种管理</span>
                                        </a>
                                        <ul class="nav dk text-sm" id="permissions">
                                        
										<shiro:hasPermission name="c:chicken:read">
                                            <li>
                                                <a href="${basePath}/web/chicken/listPage" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe667;</i>
                                                    <span>禽类管理</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="c:agg:read">
                                            <li>
                                                <a href="${basePath}/web/agg/listPage" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe667;</i>
                                                    <span>蛋类管理</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        </ul>
                                    </li>
                                    </shiro:hasPermission>
									
                                    <shiro:hasPermission name="c:health:manage">
                                       <li id="showPermiss">
                                        <a href="#" class="auto">
                                            <span class="pull-right text-muted">
                                              <i class="fa fa-angle-left text"></i>
                                              <i class="fa fa-angle-down text-active"></i>
                                            </span>
                                             <i class="icon Hui-iconfont">&#xe70c;
                                            </i>
                                            <span>健康管理</span>
                                        </a>
                                        <ul class="nav dk text-sm" id="permissions">
                                        <shiro:hasPermission name="c:castration:read">
                                            <li>
                                                <a href="${basePath}/web/castrationData/listPage" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe6ef;</i>
                                                    <span>阉割管理</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="c:immune:read">
                                            <li>
                                                <a href="${basePath}/web/immuneData/listPage" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe62a;</i>
                                                    <span>免疫管理</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="c:substandard:read">
                                            <li>
                                                <a href="${basePath}/web/substandard/listPage" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe667;</i>
                                                    <span>次品管理</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        </ul>
                                    </li>
                                    </shiro:hasPermission>
									
                                    <shiro:hasPermission name="c:sale:manage">
                                       <li id="showPermiss">
                                        <a href="#" class="auto">
                                            <span class="pull-right text-muted">
                                              <i class="fa fa-angle-left text"></i>
                                              <i class="fa fa-angle-down text-active"></i>
                                            </span>
                                             <i class="icon Hui-iconfont">&#xe66a;
                                            </i>
                                            <span>销售管理</span>
                                        </a>
                                        <ul class="nav dk text-sm" id="permissions">
										
                                        <shiro:hasPermission name="c:chicken:info">
                                            <li>
                                                <a href="${basePath}/web/chicken/info" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe623;</i>
                                                    <span>信息查询</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        
                                        <shiro:hasPermission name="c:stock:read">
                                            <li>
                                                <a href="${basePath}/web/stockData/listPage" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe6b5;</i>
                                                    <span>库存管理</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="c:order:read">
                                            <li>
                                                <a href="${basePath}/web/order/listPage" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe61e;</i>
                                                    <span>订单明细报表</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="c:ordersum:read">
                                            <li>
                                                <a href="${basePath}/web/order/listSumPage" target="mainFrame" class="auto">
                                                    <i class="icon Hui-iconfont">&#xe61e;</i>
                                                    <span>汇总订单报表</span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                        </ul>
                                    </li>
                                    </shiro:hasPermission>
                                     <!-- / 结束 -->
                                    
                                   </ul>
                            </nav>
                            <!-- / nav -->
                        </div>
                    </section>
                </section>
            </aside>
            <!-- /.aside -->
            <!--中间内容 -->
            <section id="content" style="-webkit-box-flex:1; -webkit-flex: 1;-ms-flex: 1;flex: 1;display:-webkit-box;display:-webkit-flex;display:-ms-flexbox;display: flex;">
                <iframe id="mainFrame" name="mainFrame" style="border: 0;-webkit-box-flex:1; -webkit-flex: 1;-ms-flex: 1;flex: 1;" src=""></iframe><!-- ${basePath}/web/customer/p_list -->
            </section>
            <!--中间内容结束-->
        </section>
    </section>
</section>
<div id="auto-close-dialogBox"></div> <!-- 确定提示窗盒子 -->
<div class="modal fade in" id="modal-form">
   <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-body wrapper-lg">
               <div class="row">
                   <section class="panel panel-default" style="    margin-bottom: 40px;">
                       <header class="panel-heading font-bold" id="headTitle">修改密码</header>
                       <div class="panel-body">
                           <div class="bs-example form-horizontal">
                           	 <div class="form-group">
                                <label class="col-sm-2 control-label manager_account">原&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control manager_account" id="oldpassword" maxlength="20" >
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label manager_account">新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control manager_account" id="newpassword" maxlength="20" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label manager_account">确认密码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control manager_account" id="checkePassword" maxlength="20" >
                                </div>
                            </div>
                               <div class="line line-dashed b-b line-lg pull-in"></div>
                               <div class="form-group" style="margin-bottom: 0">
                                   <div class="col-sm-5 col-sm-offset-2">
                                       <button type="submit" id="passwordCancel" style="min-width:82px;" class="btn btn-default">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
                                       <shiro:hasPermission name="label:password:update">
                                       		<button type="submit" id="passwordConfirm" class="btn btn-primary">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
                                       </shiro:hasPermission>
                                   </div>
                               </div>
                           </div>
                       </div>
                   </section>
               </div>
           </div>
       </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div>
</body>
<script src="${basePath}/resources/js/web/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="${basePath}/resources/js/web/bootstrap.js"></script>
<!-- bootoast -->
<%-- <script src="${basePath}/resources/js/web/bootoast.js"></script> --%>
<!-- App -->
<script src="${basePath}/resources/js/web/app.js"></script>
<script src="${basePath}/resources/js/web/base.js"></script>
<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>

<script src="${basePath}/resources/js/web/index.js"></script>

<!-- <script>window.jQuery || document.write('<script src="${basePath}/resources/dg/js/jquery-1.11.0.min.js"><\/script>')</script> -->
<script type="text/javascript" src="${basePath}/resources/dg/js/bootoast.js"></script>
	
<script>

    $(function(){
        getLoginInfo();
        $("#collspan").trigger("click");
       	$("#collspan").bind("click",function(){
       		if($("#collspan").hasClass("active")){
       			$(".pull-left").parent().append('<i class="iconkuno fa fa-cog text" style="font-size: 16px;"></i>');
       		}else{
       			$(".pull-left").parent().find(".iconkuno").remove()
       		}
       	})
       	setTimeout(function(){
       		
       	},500)
       	/* var permissions = $("#userions").find("li").length;
       	if(!permissions||permissions<=0){
       		$("#showUser").addClass("hidden");
       	} */
       	var permissions = $("#permissions").find("li").length;
       	if(!permissions||permissions<=0){
       		$("#showPermiss").addClass("hidden");
       	}
       	var report = $("#report").find("li").length;
       	if(!report||report<=0){
       		$("#reports").addClass("hidden");
       	}
       	
       	// 客服管理
       	var customerService = $("#customerService").find("li").length;
       	if(!customerService||customerService<=0){
       		$("#customerServices").addClass("hidden");
       	}
       	
    	var customerUpms = $("#customerUpms").find("li").length;
       	if(!customerUpms||customerUpms<=0){
       		/* $("#customerUpms").addClass("hidden"); */
       		$("#userions").addClass("hidden");
       	}else{
       		var newurl=window.location.href.split("")
       		$("#mainFrame").attr("src","../web/customer/p_list")
       		
       	}
    })
    function getLoginInfo(){
   		var loginInfo={
   	            "account":'${account}',
   	            "name":'${name}',
   	            "organId":'${organId}',
   	            "phone":'${phone}',
   	            "email":'${email}',
   	            "idCard":'${idCard}',
   	            "roles":'${roles}'
   	        }
           localStorage.removeItem("loginInfo");
           localStorage.setItem("loginInfo",JSON.stringify(loginInfo));
    }
   
</script>
</html>