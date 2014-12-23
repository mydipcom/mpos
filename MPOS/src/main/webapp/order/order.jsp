<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>Order List</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="../assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="../assets/global/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />

<!-- END GLOBAL MANDATORY STYLES -->

<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-datepicker/css/datepicker3.css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-datetimepicker/css/datetimepicker.css"/>


<!-- BEGIN PAGE LEVEL STYLES -->
<link href="../assets/global/plugins/select2/select2.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
<link
	href="../assets/global/plugins/datatables/extensions/TableTools/css/dataTables.tableTools.min.css"
	rel="stylesheet" type="text/css" />

<!-- END PAGE LEVEL STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="../assets/global/css/components.css" rel="stylesheet"
	type="text/css" />
<link href="../assets/global/css/plugins.css" rel="stylesheet"
	type="text/css" />
<link href="../assets/admin/layout/css/layout.css" rel="stylesheet"
	type="text/css" />
<link id="style_color"
	href="../assets/admin/layout/css/themes/default.css" rel="stylesheet"
	type="text/css" />
<link href="../assets/admin/layout/css/custom.css" rel="stylesheet"
	type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="../media/image/favicon.ico" />
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
				<h3 class="page-title">
		       Order <small>orderlist</small></h3>
				<div class="page-bar">
					<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="<c:url value="/"/>home">XXXXXXX</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="<c:url value="/"/>point">XXXXXXX</a></li>

					</ul>
				</div>
				<!-- END PAGE TITLE & BREADCRUMB-->
				<div id="spin" class="display-hide"></div>
				<!-- BEGIN PAGE CONTENT-->

				<!-- BEGIN SEARCH FORM -->
				<div class="portlet-body">
					<form id="searchForm" name="searchForm" action=""
						class="form-horizontal" method="post">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-3 control-label">Order Status：</label>
									<div class="col-md-9">
										<div class="radio-list">
											<label class="radio-inline"> <input type="radio"
												name="orderStatus" value="0" checked/>No Payment Order
											</label> <label class="radio-inline"> <input type="radio"
												name="orderStatus" value="1" />History Order
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-3 control-label">Order Id：</label>
									<div class="col-md-6">
										<input name="orderId" type="text" class="form-control">
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-3 control-label">Creater：</label>
									<div class="col-md-6">
										<input name="creater" type="text" class="form-control">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-3 control-label">Create Time：</label>
									
										<div class="col-md-4">
											<div class="input-group date form_datetime">
												<input type="text" size="16" readonly class="form-control" name="startTime">
                                                <span class="input-group-btn">
												<button class="btn default date-reset" type="button"><i class="fa fa-times"></i></button>
												</span>
												<span class="input-group-btn">
												<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
												</span>
												
									   </div>
										</div>
										<div class="col-md-1">TO</div>
										<div class="col-md-4">
											<div class="input-group date form_datetime">
												<input type="text" size="16" readonly class="form-control" name="endTime">
                                                <span class="input-group-btn">
												<button class="btn default date-reset" type="button"><i class="fa fa-times"></i></button>
												</span>
												<span class="input-group-btn">
												<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
												</span>
												
									   </div>
										</div>
									
								</div>
							</div>
						</div>
						

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<div class="col-md-offset-3 col-md-9">
										<button type="submit" class="btn blue">
											Search <i class="fa fa-search"></i>
										</button>
										<button type="reset" class="btn grey-cascade">
											Reset <i class="fa fa-reply"></i>
										</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<!-- END SEARCH FORM -->

				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>Order List
								</div>
								<div class="actions">
									<a class="btn btn-default btn-sm" href="#payment_order" id="confirm_payment" data-toggle="modal">Confirm
										Payment</a> <a class="btn btn-default btn-sm"
										href="#cancel_order_model" id="cancel_order" data-toggle="modal">Cancel Order</a> <a
										class="btn btn-default btn-sm" id="order_detail_btn">Order
										Details</a>
									<div class="btn-group">
										<a class="btn default" href="#" data-toggle="dropdown">
											Columns <i class="fa fa-angle-down"></i>
										</a>
										<div id="column_toggler"
											class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
											<label><input type="checkbox" checked data-column="0">Checkbox</label>
											<label><input type="checkbox" checked data-column="1">Order ID</label>
											<label><input type="checkbox" checked data-column="2">Order Status</label>
											<label><input type="checkbox" checked data-column="3">Order Total</label>
											<label><input type="checkbox" checked data-column="4">Order Discount</label>
											<label><input type="checkbox" checked data-column="5">Create Time</label>
											<label><input type="checkbox" checked data-column="6">Creater</label>
											<label><input type="checkbox" checked data-column="7">Comment</label>
												
										</div>
									</div>
								</div>
							</div>
							<div class="portlet-body">
								<table class="table table-striped table-hover table-bordered"
									id="order_list_table">
									<thead>
										<tr>
											<th class="table-checkbox"><input type="checkbox"
												class="group-checkable"
												data-set="#order_list_table .checkboxes" /></th>
											<th>Order ID</th>
											<th>Order Status</th>
											<th>Order Total</th>
											<th>Order Discount</th>
											<th>Create Time</th>
											<th>Creater</th>	
											<th>Comment</th>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT -->

                <!-- BEGIN Activate MODAL FORM-->
				<div class="modal" id="payment_order" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>
							 Are you sure the payment option order.
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">Cancel</button>
						<button id="paymentBtn" type="button" data-dismiss="modal" class="btn blue">Confirm</button>
					</div>					
				</div>	
				<!-- END Activate MODAL FORM-->
				
				<!-- BEGIN DEActivate MODAL FORM-->
				<div class="modal" id="cancel_order_model" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>
							Are you sure the cancel option order.
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">Cancel</button>
						<button id="cancelBtn" type="button" data-dismiss="modal" class="btn blue">Confirm</button>
					</div>					
				</div>				
				<!-- END DELETE MODAL FORM-->



			</div>
		</div>
	</div>
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


	<script src="../assets/global/plugins/jquery-1.11.0.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-migrate-1.2.1.min.js"
		type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script
		src="../assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery.cokie.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
		type="text/javascript"></script>
	<script src="../assets/global/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="../assets/global/plugins/select2/select2.min.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/datatables/media/js/jquery.dataTables.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js"
		type="text/javascript"></script>
	<script
		src="../assets/global/plugins/datatables/extensions/TableTools/js/dataTables.tableTools.min.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
    <script type="text/javascript" src="../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>

	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="../assets/global/plugins/json/json2.js"
		type="text/javascript"></script>
	<script src="../assets/global/scripts/metronic.js"
		type="text/javascript"></script>
	<script src="../assets/admin/layout/scripts/layout.js"
		type="text/javascript"></script>
	<script src="../static/js/orderlist.js" type="text/javascript"></script>
	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout	
			OrderList.init("<c:url value="/"/>");
		});
	</script>
</body>
<!-- END BODY -->

</html>