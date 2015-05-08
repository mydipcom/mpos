<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->


<!-- BEGIN HEAD -->

<head>
	<meta charset="utf-8" />
	<title>Register Page</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<meta content="" name="description" />

	<meta content="" name="author" />

	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
	<link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link href="assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
	<link href="assets/admin/pages/css/login.css" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME STYLES -->
	<link href="assets/global/css/components.css" rel="stylesheet" type="text/css"/>
	<link href="assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
	<link id="style_color" href="assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css"/>
	<link href="assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="login">

	<!-- BEGIN LOGO -->
	<div class="logo">
		<img src="assets/admin/layout/img/logo-big.png" alt=""/> 
	</div>
	<!-- END LOGO -->	

	<div class="content">
		<!-- BEGIN REGISTRATION FORM -->
		<form:form action="register" method="post" commandName="user" cssClass="register-form" cssStyle="display:block">
			<h3>Sign Up</h3>
			<p>Enter your account details below:</p>
			
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">Username</label>				
				<div class="input-icon">
					<i class="fa fa-user"></i>
					<form:input path="adminId" cssClass="form-control placeholder-no-fix" placeholder="Username"/>
					<form:errors path="adminId"/>						
				</div>
			</div>
			<div class="form-group">				
				<div class="input-icon">
					<i class="fa fa-lock"></i>
					<form:password path="password" cssClass="form-control placeholder-no-fix" placeholder="Password" id="register_password"/>						
					<form:errors path="password"/>
				</div>									
			</div>	
            <div class="form-group">
				<div class="input-icon">
					<i class="fa fa-check"></i>
					<input class="form-control placeholder-no-fix" type="password" placeholder="Re-type Your Password" name="rpassword"/>						
				</div>
			</div>	
			<div class="form-group">		
				<label class="control-label visible-ie8 visible-ie9">Email</label>			
			    <div class="input-icon">
					<i class="fa fa-envelope"></i>
					<form:input path="email" cssClass="form-control placeholder-no-fix" placeholder="Email"/>						
				</div>
				<form:errors path="email"/>									
			</div>			
			<div class="form-group">				
				<label>					
				<input type="checkbox" name="tnc"/> I agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a>
				</label>  
				<div id="register_tnc_error"></div>				
			</div>
			<div class="form-actions">
				<button id="register-back-btn" type="button" class="btn">
				<i class="fa fa-ban"></i>  Clear
				</button>
				<button type="submit" id="register-submit-btn" class="btn green pull-right">
				Sign Up <i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
		</form:form>
		<!-- END REGISTRATION FORM -->

	</div>	

	<!-- BEGIN COPYRIGHT -->

	<div class="copyright">
		2014 &copy; CampRay. Points Management System.
	</div>

	<!-- END COPYRIGHT -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script src="assets/global/plugins/respond.min.js"></script>
	<script src="assets/global/plugins/excanvas.min.js"></script> 
	<![endif]-->
	<script src="assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="assets/global/plugins/select2/select2.min.js"></script>
	<script src="../assets/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
	<script src="assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
	<script src="assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
	<script src="assets/admin/pages/scripts/login.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script>

		jQuery(document).ready(function() {     
		  Metronic.init(); // init metronic core components
		  Layout.init(); // init current layout
		  QuickSidebar.init(); // init quick sidebar
		  Demo.init(); // init demo features
		  Login.init();

		});

	</script>

	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->

</html>