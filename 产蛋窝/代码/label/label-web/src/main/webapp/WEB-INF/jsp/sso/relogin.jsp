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
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
  <link rel="icon" type="image/png" href="${basePath}/resources/images/web/logo.ico">
      <link rel="stylesheet" href="${basePath}/resources/css/web/jquery.dialogbox.css" type="text/css" />
  <link rel="stylesheet" href="${basePath}/resources/css/web/bootstrap.css" type="text/css"/>
  <link rel="stylesheet" href="${basePath}/resources/css/web/animate.css" type="text/css"/>
  <link rel="stylesheet" href="${basePath}/resources/css/web/font-awesome.min.css" type="text/css"/>
  <link rel="stylesheet" href="${basePath}/resources/css/web/simple-line-icons.css" type="text/css"/>
  <link rel="stylesheet" href="${basePath}/resources/css/web/font.css" type="text/css"/>
  <link rel="stylesheet" href="${basePath}/resources/css/web/app.css" type="text/css"/>
  <link rel="stylesheet" href="${basePath}/resources/css/web/loaders.css" type="text/css" />
  <!--[if lt IE 9]>
  <script src="${basePath}/resources/js/web/ie/html5shiv.js"></script>
  <script src="${basePath}/resources/js/web/ie/respond.min.js"></script>
  <script src="${basePath}/resources/js/web/ie/excanvas.js"></script>
  <![endif]-->
  <style>
  	.useravatar{
  		width:64px;
  	}
  </style>
</head>
<body class="">
<div class="loadershade">
	<div class="loader">
	
	        <div class="loader-inner ball-pulse-sync">
	
	          <div></div>
	
	          <div></div>
	
	          <div></div>
	
	        </div>
	
	</div>
</div>
<div class="modal-over">
  <div class="modal-center animated fadeInUp text-center" style="width:200px;margin:-80px 0 0 -100px;">
 <%--    <div class="thumb-md"><img src="${basePath}/resources/images/web/a0.png" class="img-circle b-a b-light b-3x useravatar"></div> --%>
    <p class="text-white h4 m-t m-b username">昵称</p>
    <div class="input-group">
      <input type="password" class="form-control text-sm btn-rounded" id="apassword" placeholder="输入密码重新登录">
      <span class="input-group-btn">
        <button class="btn btn-success btn-rounded" id="signin" type="button" data-dismiss="modal"><i class="fa fa-arrow-right"></i></button>
      </span>
    </div>
    <p class="text-white m-t m-b"><a href="login"  target="_top" class="exchaange text-white m-t m-b" style="cursor: pointer;">切换账号</a></p>
  </div>
</div>
 <div id="auto-close-dialogBox"></div>
<script src="${basePath}/resources/js/web/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="${basePath}/resources/js/web/bootstrap.js"></script>
<!-- App -->
<script src="${basePath}/resources/js/web/app.js"></script>
<script src="${basePath}/resources/js/web/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${basePath}/resources/js/web/app.plugin.js"></script>
<script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
<script src="${basePath}/resources/js/web/base.js"></script>
<script src="${basePath}/resources/plugins/md5.js" charset="utf-8"></script>
<script>
  $(function (){
    getStroger();
  })
  function getStroger(){
   var loginInfo= localStorage.getItem("loginInfo");
   loginInfo=JSON.parse(loginInfo);
    if(loginInfo!=null&&loginInfo!=undefined){
      $(".useravatar").attr("src",loginInfo.avatar);
      $(".username").html(loginInfo.account);
    }else{
     window.parent.frames.location.href='login'
    }
  }

  $("#signin").bind("click",function(){
    var aNumber=   $(".username").html();
    aNumber=="Kuno"?aNumber="admin":aNumber=aNumber
    var apassword=$("#apassword").val();
    if(apassword==""){
      set_message("auto-close-dialogBox","提示","请输入密码!");
      return false;
    }
    $(".loadershade").css("display","block");
    var params={
      'account':aNumber,
      'password':hex_md5(aNumber+apassword),
      'backurl':getUrlParam('backurl')
    }
    ajaxPostFunctionAsytrue("../sso/login",params,function(data){
      if(data.code=="0"){
        setTimeout(function(){
        /*   window.location.href=data.data.url;  */
        	parent.location.reload();
        },800)
      }else{
        $(".loadershade").css("display","none");
        set_message("auto-close-dialogBox","提示",data.data);
      }
    })
    //window.location.href="index.html"
  })

  $('#apassword').keyup(function(event){
    if(event.keyCode ==13){
      $('#signin').trigger("click");
    }
  });
</script>
</body>
</html>