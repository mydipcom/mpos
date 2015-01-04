<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title><s:message code="order.details.title"/></title>
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
	<c:import url="/common/header" />
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<c:import url="/common/left" />
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">
			    <div class="page-bar">
					<%--<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="<c:url value="/"/>home"><s:message code="home"/></a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="<c:url value="/"/>order"><s:message code="order" /></a><i
							class="fa fa-angle-right"></i></li>
						<li><a href=""><s:message code="order.details.title" /></a></li>

					</ul>--%>
				</div> 
				<!-- END PAGE TITLE & BREADCRUMB-->
				<div id="spin" class="display-hide"></div>
				<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<div class="col-md-12">


						<div class="row">
							<div class="col-md-12">
								<!-- Begin: life time stats -->
								<div class="portlet">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-shopping-cart"></i><s:message code="order" /> #
											${order_details['order_id']}<span class="hidden-480">
												| ${order_details['create_time']} </span>
											<input type="hidden" value="${order_details['order_id']}"/>
										</div>
										<div class="actions">
											<a href="<c:url value="/"/>order"
												class="btn default yellow-stripe"> <span
												class="hidden-480"> <s:message code="back"/> </span>
											</a> <a  class="btn default yellow-stripe"> <span
												class="hidden-480" id="paymentBtn"><s:message code="payment"/></span>
											</a> <a  class="btn default yellow-stripe"><span
												class="hidden-480" id="cancelBtn"> <s:message code="cancel"/> </span> </a>
											<!-- 										<a href="#" class="btn default yellow-stripe"><span class="hidden-480">
													Print </span>
											</a>
-->
										</div>
									</div>
									<div class="portlet-body">
										<div class="tabbable">


											<div class="row">
												<div class="col-md-6 col-sm-12">
													<div class="portlet yellow-crusta box">
														<div class="portlet-title">
															<div class="caption">
																<i class="fa fa-cogs"></i><s:message code="order.details.title"/>
															</div>
														</div>
														<div class="portlet-body">
															<div class="row static-info">
																<div class="col-md-5 name"><s:message code="order.id"/>:</div>
																<div class="col-md-7 value">
																	${order_details['order_id']}</div>
															</div>
															<div class="row static-info">
																<div class="col-md-5 name"><s:message code="order.time"/>:</div>
																<div class="col-md-7 value">${order_details['create_time']}</div>
															</div>

															<div class="row static-info">
																<div class="col-md-5 name"><s:message code="discount.total"/>:</div>
																<div class="col-md-7 value">${order_details['discount_total']}</div>
															</div>
															<div class="row static-info">
																<div class="col-md-5 name"><s:message code="payment.total"/>:</div>
																<div class="col-md-7 value">${order_details['payment_total']}</div>
															</div>
															<div class="row static-info">
																<div class="col-md-5 name"><s:message code="oreder.creater"/>:</div>
																<div class="col-md-7 value">${order_details['creater']}</div>
															</div>
															<div class="row static-info">
																<div class="col-md-5 name"><s:message code="order.status"/>:</div>
																<div class="col-md-7 value">
																	<span class="label label-success">
																		${order_details['order_status']} </span> 
																		<input type="hidden" value="${order_details['order_status_id']}" name="order_status"/>
																</div>
															</div>
														</div>
													</div>
												</div>
												<c:set var="promotions" scope="session"
													value="${order_details['order_promotion']}" />
												<%-- <div class="col-md-6 col-sm-12">
													<div class="portlet blue-hoki box">
														<div class="portlet-title">
															<div class="caption">
																<i class="fa fa-cogs"></i><s:message code="promotion.info"/>
															</div>

														</div>
														<div class="portlet-body">
															<c:if test="${empty promotions}">
																<div class="row static-info">
																	<div class="col-md-5 name"></div>
																	<div class="col-md-7 value">no preferential
																		activities</div>
																</div>
															</c:if>
															<c:forEach var="promotion" items="${promotions}">
																<div class="row static-info">
																	<div class="col-md-5 name">${promotion.key}</div>
																	<div class="col-md-7 value">${promotion.value}</div>
																</div>
															</c:forEach>


														</div>
													</div>
												</div> --%>
											</div>
											<div class="row">
												<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i><s:message code="order product"/>
								</div>
								<div class="actions">
									<div class="btn-group">
										<a class="btn default" href="#" data-toggle="dropdown">
											Columns <i class="fa fa-angle-down"></i>
										</a>
										<div id="column_toggler"
											class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
											<label><input type="checkbox" checked data-column="0">Checkbox</label>
											<label><input type="checkbox" checked data-column="1">Product ID</label>
											<label><input type="checkbox" checked data-column="2">Quantity</label>
											<label><input type="checkbox" checked data-column="3">Unit Price</label>
											<label><input type="checkbox" checked data-column="4">Discount</label>
											<label><input type="checkbox" checked data-column="5">Curr Price</label>
											<label><input type="checkbox" checked data-column="6">Attributes</label>
											<label><input type="checkbox" checked data-column="7">Is Gift</label>
											<label><input type="checkbox" checked data-column="7">Product Promotion</label>
										</div>
									</div>
								</div>
							</div>
							<div class="portlet-body">
								<table class="table table-striped table-hover table-bordered"
									id="order_product_table">
									<thead>
										<tr>
											<th class="table-checkbox"><input type="checkbox"class="group-checkable " data-set="#order_product_table .checkboxes" disabled/></th>
											<th>Product ID</th>
											<th>Quantity</th>
											<th>Unit Price</th>
											<th>Discount</th>
											<th>Curr Price</th>
											<th>Attributes</th>
											<th>Is Gift</th>
											<th>Product Promotion</th>	
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

										</div>


									</div>
								</div>
								<!-- End: life time stats -->
							</div>
						</div>
					</div>
				</div>
				<!-- END PAGE CONTENT -->

			</div>
		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<c:import url="/common/footer" />
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
	<script src="../static/js/orderdetails.js" type="text/javascript"></script>
	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout	
			OrderDetails.init("<c:url value="/"/>");
		});
	</script>
</body>
<!-- END BODY -->

</html>