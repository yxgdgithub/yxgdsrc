<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.1
Version: 3.3.0
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>mmanage</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->

<link href="${request.contextPath}/static/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${request.contextPath}/static/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="${request.contextPath}/static/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${request.contextPath}/static/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${request.contextPath}/static/css/login2.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="${request.contextPath}/static/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
<link href="${request.contextPath}/static/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${request.contextPath}/static/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link href="${request.contextPath}/static/layout/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="${request.contextPath}/static/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGO -->
<div class="logo">
	
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
	<!-- BEGIN LOGIN FORM -->
	<form class="login-form" action="/login" method="post">
		<div class="form-title">
			<span class="form-title">mmanage</span>
			<!-- <span class="form-subtitle">mmanage</span> -->
		</div>
		<div class="alert alert-danger display-hide">
			<button class="close" data-close="alert"></button>
			<span>请输入用户名或者密码. </span>
		</div>
		<#if errorMsg??>
			<div class="alert alert-danger">
				<button class="close" data-close="alert"></button>
				<span>${errorMsg} </span>
			</div>
		</#if>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">手机号/邮箱</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="手机号/邮箱" name="username"/>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password"/>
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-primary btn-block uppercase">登录</button>
		</div>
		
		<div class="login-options">
			<button type="button" class="btn btn btn-default btn-block uppercase wx-btn">微信登录</button>
		</div> 
		<div class="form-actions">
			<div class="pull-left">
				<label class="rememberme check">
				还没有帐号?  
				</label>
				<label class="rememberme check">
				<a href="javascript:;" id="register-btn" style="color:#4b8df8 !important;">  注册新帐号</a>
				</label>
			</div>
			<div class="pull-right forget-password-block">
				<a href="javascript:;" id="forget-password" class="forget-password">忘记密码?</a>
			</div>
		</div>
		<!-- <div class="create-account">
			<p>
				<a href="javascript:;" id="register-btn">Create an account</a>
			</p>
		</div> -->
	</form>
	<!-- END LOGIN FORM -->
	<!-- BEGIN FORGOT PASSWORD FORM -->
	<form class="forget-form" action="index.html" method="post">
		<div class="form-title">
			<p class="form-subtitle pull-left">请输入你的手机号或邮箱.</p>
		</div>
		<div class="form-group">
			<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="手机号/邮箱" name="phone_email"/>
		</div>
		<div class="form-actions">
			<!-- <button type="button" id="back-btn" class="btn btn-default">Back</button> -->
			<button type="submit" class="btn btn-primary btn-block uppercase ">下一步</button>
		</div>
		<div class="pwd-get-back">
			<p>
				<a href="javascript:;" id="back-btn"><i class="fa fa-hand-o-left"></i>  返回登录</a>
			</p>
		</div>
	</form>
	<!-- END FORGOT PASSWORD FORM -->
	<!-- BEGIN REGISTRATION FORM -->
	<form class="register-form" action="/users/save" method="post">
		<div class="form-title">
			<span class="form-title">注册用户</span>
		</div>

		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">姓名</label>
			<input class="form-control placeholder-no-fix" type="text" placeholder="姓名" name="userName"/>
		</div>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">手机号/邮箱</label>
			<input class="form-control placeholder-no-fix" type="text" placeholder="手机号/邮箱" name="phoneOrEmail"/>
		</div>
		
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="密码" name="password"/>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">确认密码</label>
			<input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="确认密码" name="rpassword"/>
		</div>
		<div class="form-actions">
			<button type="submit" id="register-submit-btn"  class="btn btn-primary btn-block uppercase">注册</button>
		</div>
		<div class="pwd-get-back">
			<p>
				<a href="javascript:;" id="register-back-btn"><i class="fa fa-hand-o-left"></i>   返回登录</a>
			</p>
		</div>
		
	</form>
	<!-- END REGISTRATION FORM -->
</div>
<div class="copyright hide">
	 2014 © Metronic. Admin Dashboard Template.
</div>
<!-- END LOGIN -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${request.contextPath}/static/global/plugins/respond.min.js"></script>
<script src="${request.contextPath}/static/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${request.contextPath}/static/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${request.contextPath}/static/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${request.contextPath}/static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() {     
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	Login.init();
	Demo.init();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>