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
  <meta charset="utf-8" />
  <title>鸡缘巧合养殖</title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="icon" type="image/png" href="${basePath}/resources/images/web/logo.ico">
     <link rel="stylesheet" href="${basePath}/resources/css/web/jquery.dialogbox.css" type="text/css" />
  <link rel="stylesheet" href="${basePath}/resources/css/web/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="${basePath}/resources/css/web/animate.css" type="text/css" />
  <link rel="stylesheet" href="${basePath}/resources/css/web/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="${basePath}/resources/css/web/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="${basePath}/resources/css/web/font.css" type="text/css" />
  <link rel="stylesheet" href="${basePath}/resources/css/web/app.css" type="text/css" />
  <link rel="stylesheet" href="${basePath}/resources/css/web/Style_signin.css" type="text/css" />
<link rel="stylesheet" href="${basePath}/resources/css/web/loaders.css" type="text/css" />
    <!--[if lt IE 9]>
  <script src="${basePath}/resources/js/web/ie/html5shiv.js"></script>
  <script src="${basePath}/resources/js/web/ie/respond.min.js"></script>
  <script src="${basePath}/resources/js/web/ie/excanvas.js"></script>
  <![endif]-->
</head>
<body class="bg-info dker">
<div class="loadershade">
    <div class="loader">
    
            <div class="loader-inner ball-pulse-sync">
    
              <div></div>
    
              <div></div>
    
              <div></div>
    
            </div>
    
    </div>
</div>

  <section id="content" class="m-t-lg wrapper-md animated fadeInUp">
    <div class="container aside-xl">
          <img src="${basePath}/resources/images/web/logo.png"  style="width: 20%;">
        <a href="javascript:void(0)" class="navbar-brand text-lt">
            <span class="hidden-nav-xs m-l-sm">鸡缘巧合养殖管理系统</span>
          </a>
      <section class="m-b-lg">
        <form action="index.html">
          <div class="form-group">
            <input type="text" placeholder="账&nbsp;&nbsp;号" class="form-control rounded input-lg text-center no-border" id="aNumber">
          </div>
          <div class="form-group">
             <input type="password" placeholder="密&nbsp;&nbsp;码" class="form-control rounded input-lg text-center no-border" id="apassword">
          </div>
          <button type="button" class="btn btn-lg btn-warning lt b-white b-2x btn-block btn-rounded" id="signin"><i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg">登&nbsp;&nbsp;录</span></button>
          <div class="text-center m-t m-b"><a href="#"><small><!--忘记密码?--></small></a></div>
          <div class="line line-dashed"></div>
        </form>
      </section>
    </div>
  </section>
  <!-- footer -->
  <footer id="footer">
    <div class="text-center padder">
      <p>
        <small>鸡缘巧合养殖版权所有<br>&copy;2019 <!-- 这里记得改一下  Inc.--> All Rights Reserved</small>
      </p>
    </div>
  </footer>
  <div id="auto-close-dialogBox"></div>
  <!-- / footer -->
  <script src="${basePath}/resources/js/web/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="${basePath}/resources/js/web/bootstrap.js"></script>
  <script src="${basePath}/resources/js/web/jquery.dialogBox.js"></script>
    <script src="${basePath}/resources/js/web/base.js"></script>
    <script src="${basePath}/resources/plugins/md5.js" charset="utf-8"></script>
    <script src="${basePath}/resources/js/web/login/login.js"></script>
  <!-- App -->
</body>
<script>

</script>
</html>