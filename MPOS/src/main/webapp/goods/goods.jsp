<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>Product List</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="../assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css"/>
<link href="../assets/admin/pages/css/profile.css" rel="stylesheet" type="text/css"/>
<link href="../assets/admin/pages/css/portfolio.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>

<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="../assets/global/css/components.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="../assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="../assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css"/>
<link href="../assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="../media/image/favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<c:import url="/common/header"/>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<c:import url="/common/left"/>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">	
			<div class="page-content">								
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				
				<div id="msg"></div>
					<c:if test="${not empty Msg}">
								<div class="alert alert-success">
								<button class="close" data-close="alert"></button>
									<span><s:message code="${Msg}"/></span>
								</div>
								</c:if>
				<!-- END PAGE TITLE & BREADCRUMB-->
				<!-- BEGIN SEARCH FORM -->
				<div class="portlet-body">
					<form id="searchForm" name="searchForm" action="adminuserlist1" class="form-horizontal" method="post">
					<div class="row">
						<div class="col-md-6">					
							<div class="form-group">
								<label class="col-md-3 control-label"><s:message code="product.product.name"/></label>
								<div class="col-md-9">
									<input name="productName" type="text" class="form-control">							
								</div>
							</div>
						</div>
						<div class="col-md-6">	
							<div class="form-group">
								<label class="col-md-3 control-label"><s:message code="product.category.name"/></label>
								<div class="col-md-9">
								<select name="tmenu.menuId" class="form-control">
										<option value="">ALL</option>
										<c:if test="${not empty menu}">
											<c:forEach items="${menu}" var="menuitem">
												<option value="${menuitem.id}">${menuitem.title}</option>
											</c:forEach>
										</c:if>							
									</select>
									</div>
							</div>
						</div>
					</div>
					<div class="row">					
						<div class="col-md-6">	
							<div class="form-group">
								<label class="col-md-3 control-label"><s:message code="product.recommend"/></label>
								<div class="col-md-9">
									<div class="radio-list">
										<label class="radio-inline">
										<input type="radio" name="recommend" value="" checked/>All </label>
										<label class="radio-inline">
										<input type="radio" name="recommend" value="1"/>True</label>
										<label class="radio-inline">
										<input type="radio" name="recommend" value="0"/>False</label>
									</div>									
								</div>
							</div>
						</div>
					</div>
					<!-- 
					<div class="row">
					<div class="col-md-6">	
							<div class="form-group">
								<label class="col-md-3 control-label">Stutas</label>
								<div class="col-md-9">
									<div class="radio-list">
										<label class="radio-inline">
										<input type="radio" name="status" value="" checked/>All </label>
										<label class="radio-inline">
										<input type="radio" name="status" value="true"/>True</label>
										<label class="radio-inline">
										<input type="radio" name="status" value="false"/>False</label>
									</div>									
								</div>
							</div>
						</div>
						</div>
						 -->
					<div class="row">	
						<div class="col-md-6">	
							<div class="form-group">								
								<div class="col-md-offset-3 col-md-9">
									<button type="submit" class="btn blue">Search <i class="fa fa-search"></i></button>
									<button type="reset" class="btn grey-cascade">Reset <i class="fa fa-reply"></i></button>
								</div>
							</div>					
						</div>
					</div>	
					</form>
				</div>
				<!-- END SEARCH FORM -->
				
				
				
				<!-- BEGIN PAGE CONTENT-->
				<!-- 
				<div class="row profile">
				  <div class="col-md-12">
					<div class="nav nav-tabs">
					   <div class="col-md-4">
					     <div class="portlet-title tabbable-lin">
						     <ul class="nav nav-tabs" style="text-align: center;">
						        <li class="active">
								   <a href="#tab_1_1" data-toggle="tab">
								   List</a>
							    </li>
							   <li>
								 <a href="#tab_1_2" data-toggle="tab">
								  Grid </a>
							   </li>
							  
							   </ul>
						  </div>
						</div>
						<div class="portlet-body">
						  <div class="tab-content">
							<div class="tab-pane active" id="tab_1_1">
							<div class="row"></div>	 -->
												<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet  box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i><s:message code="product.list.table.title"/>
								</div>
								<div class="actions">									
								    <a class="btn btn-default btn-sm"  href="<c:url value="/"/>goods/addgoods"><i class="fa fa-plus"></i> <s:message code="product.add" /></a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#edit_users" id="openEditgoodsModal"><i class="fa fa-pencil"></i> <s:message code="product.edit" /></a>
								    <%-- <a class="btn btn-default btn-sm" data-toggle="modal" href="#active_goods" id="openActivegoodsModal"><i class="fa fa-key"></i> <s:message code="product.activate" /></a> --%>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#delete_goods" id="openDeletegoodsModal"><i class="fa fa-trash-o"></i> <s:message code="product.delete" /></a>
								    <div class="btn-group">
										<a class="btn default" href="#" data-toggle="dropdown">
										Columns <i class="fa fa-angle-down"></i>
										</a>
										<div id="column_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
											<label><input type="checkbox" checked data-column="0">Checkbox</label>
											<label><input type="checkbox" checked data-column="1"><s:message code="system.management.user.searchform.id"/></label>
											<label><input type="checkbox" checked data-column="2"><s:message code="product.category.name"/></label>
											<label><input type="checkbox" checked data-column="3"><s:message code="product.product.name"/></label>
											<label><input type="checkbox" checked data-column="4"><s:message code="product.price"/></label>
											<label><input type="checkbox" checked data-column="5"><s:message code="product.oldprice"/></label>
											<label><input type="checkbox" checked data-column="6"><s:message code="product.recommend"></s:message></label>
											<label><input type="checkbox" checked data-column="7"><s:message code="product.sort"/></label>
											
										</div>
									</div>								    																
								</div>
							</div>							
							<div class="portlet-body">																
								<table class="table table-striped table-hover table-bordered" id="goods_table">
									<thead>
										<tr>
											<th class="table-checkbox">
												<input type="checkbox" class="group-checkable" data-set="#goods_table .checkboxes"/>
											</th>
											<th><s:message code="system.management.user.searchform.id"/></th>
											<th><s:message code="product.category.name"/></th>
											<th><s:message code="product.product.name"/></th>
											<th><s:message code="product.price"/></th>
											<th><s:message code="product.oldprice"/></th>
											<th><s:message code="product.recommend"/></th>
											<th><s:message code="product.sort"/></th>
										<!--  	<th>Status</th>-->
										</tr>
										</thead>																
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT -->    						
			</div>
					
						</div>
						
						</div>
					
				<!-- END PAGE CONTENT -->
				
			<!-- BEGIN DELETE MODAL FORM-->
				<div class="modal" id="delete_goods" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>
							 <s:message code="system.management.user.deletemessage" />
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">Cancel</button>
						<button id="deleteBtn" type="button" data-dismiss="modal" class="btn blue">Confirm</button>
					</div>					
				</div>				
				<!-- END DELETE MODAL FORM-->
				
				<!-- BEGIN DELETE MODAL FORM-->
				<div class="modal" id="active_goods" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>
							 <s:message code="system.management.user.deletemessage" />
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">Cancel</button>
						<button id="activeBtn" type="button" data-dismiss="modal" class="btn blue">Confirm</button>
					</div>					
				</div>				
				<!-- END DELETE MODAL FORM-->
		
	
	
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<c:import url="/common/footer"/>
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script src="../assets/global/plugins/respond.min.js"></script>
	<script src="../assets/global/plugins/excanvas.min.js"></script> 
	<![endif]-->
	<script src="../assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="../assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="../assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
    <script type="text/javascript" src="../assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
    <script src="../assets/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/typeahead/handlebars.min.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/typeahead/typeahead.bundle.min.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/jquery-i18n/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
   
    <!-- END PAGE LEVEL PLUGINS -->
   	<script type="text/javascript" src="../assets/global/plugins/jquery-mixitup/jquery.mixitup.min.js"></script>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="../assets/admin/pages/scripts/components-form-tools.js"></script>
    <script src="../assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>	
	<script src="../assets/admin/pages/scripts/portfolio.js"></script>
	<%-- <script src="../static/js/UserProfile.js"></script>
	<script src="../static/js/managersTableData.js"></script> --%>
	<script src="../static/js/common.js"></script>
	 <script src="../static/js/goodsTableData.js"></script>
	<script>
	   jQuery(document).ready(function() {       
	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout	
	   //Demo.init(); // init demo features
	 
	   Portfolio.init();
	   GoodsTable.init("<c:url value="/"/>","${sessionScope.locale}"); 
	   //ComponentsFormTools.init();
	});
	</script>
	<c:import url="/common/notice"/>
</body>
<!-- END BODY -->

</html>