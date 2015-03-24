<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>Add Product</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="../assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css" rel="stylesheet" type="text/css">
<link href="../assets/admin/pages/css/profile.css" rel="stylesheet" type="text/css" />
<link href="../assets/global/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="../assets/global/css/components.css" rel="stylesheet" type="text/css" />
<link href="../assets/global/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="../assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css" />
<link id="style_color" href="../assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css" />
<link href="../assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css" />
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
		<c:import url="/common/left" />
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">
				
				<!-- <div class="page-bar">
					<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a href="index.html">Home</a>
							<i class="fa fa-angle-right"></i></li>
						<li><a href="#">goodsList</a> <i class="fa fa-angle-right"></i>
						</li>
						<li><a href="#">addGoods</a></li>
					</ul>
				</div> -->
				<div class="portlet box green">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-gift"></i><s:message code="product.table.title" />
						</div>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form:form id="addGoodsForm"  action="setgoods" commandName="product" cssClass="form-horizontal" name="addGoodsForm" method="POST">
							<div class="form-body">
								<div class="alert alert-danger display-hide">
								<lable>There are some errors below</lable>
									<button class="close" data-close="alert"></button>									
								</div>
								<c:if test="${not empty Msg}">
								<div class="alert alert-danger">
								<button class="close" data-close="alert"></button>
									<span>${Msg}</span>
								</div>
								</c:if>
								<!--Begin Multi-language Form   -->
								<div class="portlet box grey-silver tabbable">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>Multi-language Form Items
										</div>
									</div>
									<div class="portlet-body">
										<div class="portlet-tabs">																														
											<ul class="nav nav-tabs">
												<c:forEach var="lan" items="${lanList}" varStatus="status">											
												<li><a href="#portlet_tab${status.index+2}" data-toggle="tab"><img src="${lan.flagImage}"> ${lan.name}</a></li>												
												</c:forEach>	
												<li class="active"><a href="#portlet_tab1" data-toggle="tab"> Standard</a></li>
											</ul>
											<div class="tab-content">												
												<c:forEach var="lan" items="${lanList}" varStatus="status">
												<div class="tab-pane" id="portlet_tab${status.index+2}">													
													<div class="row">
														<div class="col-md-12">
															<div class="form-group">
																<label class="control-label col-md-2"><s:message code="product.product.name"/></label>
																<div class="col-md-5">
																	<form:input path="productName_locale[${status.index}].localeValue" cssClass="form-control"/>
																	<form:hidden path="productName_locale[${status.index}].language.id"  value="${lan.id}"/>													                
																</div>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-12">
															<div class="form-group">
																<label class="control-label col-md-2"><s:message code="product.shortdescr"/></label>
																<div class="col-md-5">
																	<form:textarea path="shortDescr_locale[${status.index}].localeValue" rows="4" cols="90"/>																																		
																	<form:hidden path="shortDescr_locale[${status.index}].language.id"  value="${lan.id}"/>																																		
																</div>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-12">
															<div class="form-group">
																<label class="control-label col-md-2"><s:message code="product.fulldescr"/></label>
																<div class="col-md-8">																																		
																	<form:textarea path="fullDescr_locale[${status.index}].localeValue" rows="10" data-provide="markdown" data-error-container="#editor_error_${status.index}"/>																																		
																	<form:hidden path="fullDescr_locale[${status.index}].language.id"  value="${lan.id}"/>																	
																	<div id="editor_error_${status.index}"></div>
																</div>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-12">															
															<div class="form-group">
																<label class="control-label col-md-2"><s:message code="product.unitname"/></label>
																<div class="col-md-5">
																	<form:input path="unitName_locale[${status.index}].localeValue" cssClass="form-control"/>
																	<form:hidden path="unitName_locale[${status.index}].language.id"  value="${lan.id}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
												</c:forEach>
												<div class="tab-pane active" id="portlet_tab1">
													<div class="row">
														<div class="col-md-12">
															<div class="form-group">
																<label class="control-label col-md-2"><s:message code="product.product.name"/><span class="required"> * </span></label>
																<div class="col-md-5">																	
																	<form:input path="productName" cssClass="form-control" maxLength="300"/>																	
																</div>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-12">
															<div class="form-group">
																<label class="control-label col-md-2"><s:message code="product.shortdescr"/><span class="required"> * </span></label>
																<div class="col-md-5">																	
																	<form:textarea path="shortDescr" rows="4" cols="90"/>
																</div>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-12">
															<div class="form-group">
																<label class="control-label col-md-2"><s:message code="product.fulldescr"/></label>
																<div class="col-md-8">
																	<form:textarea path="fullDescr" rows="10" data-provide="markdown" data-error-container="#editor_error"/>																	
																	<div id="editor_error"></div>
																</div>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-12">															
															<div class="form-group">
																<label class="control-label col-md-2"><s:message code="product.unitname"/></label>
																<div class="col-md-5">
																	<form:input path="unitName" cssClass="form-control"/>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!--End Multi-language Form   -->
							
								<h3 class="form-section">Basic Info</h3>
								<div class="row">
								<div class="col-md-6">										
										<div class="form-group">
											<label class="control-label col-md-3"><s:message code="product.category.name"/><span class="required"> * </span></label>
											<div class="col-md-9">
												<select class="select2_category form-control" data-placeholder="Choose a Category" tabindex="1"	name="menu.menuId">
													<c:if test="${not empty menu}">
														<c:forEach items="${menu}" var="menuitem">
															<option value="${menuitem.id}">${menuitem.title}</option>
														</c:forEach>
													</c:if>
												</select>
												<span class="help-block">Select the product menu for mobile machine.</span>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<!--/span-->
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-3"><s:message code="product.price"/><span class="required"> * </span></label>
											<div class="col-md-9">
												<form:input path="price" cssClass="form-control" id="price"  onblur="addrules()" placeholder="0.00"/>												
											</div>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-3"><s:message code="product.oldprice"/></label>
											<div class="col-md-9">
												<form:input path="oldPrice" cssClass="form-control" placeholder="0.00"/>												
											</div>
										</div>
									</div>

								</div>
								<div class="row">
									
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-3"><s:message code="product.sort"/></label>
											<div class="col-md-9">
												<form:input path="sort" cssClass="form-control"/>												
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-3"><s:message code="product.sku"/></label>
											<div class="col-md-9">
												<form:input path="sku" cssClass="form-control"/>												
											</div>
										</div>
									</div>	
									</div>
									<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-3"><s:message code="product.recommend"/></label>
											<div class="col-md-9">
												<div class="radio-list">
													<label class="radio-inline"> 
													<form:radiobutton path="recommend" value="true"/> True
													</label> 
													<label class="radio-inline"> 
													<form:radiobutton path="recommend" value="false"/> False
													</label>
												</div>
											</div>
										</div>
																									
								</div>
								</div>

								
								 <div class="portlet light bg-inverse form-fit">
									<div class="portlet-title">
										<div class="caption">
											<i class="icon-equalizer font-red-sunglo"></i>
											<span class="caption-subject font-red-sunglo bold">Spec Attributes</span>											
										</div>
										<div class="actions">
											<div class="portlet-input input-inline">
												<form:select path="specid" cssClass="form-control" name="category" id="chooseSpecCategory">												
													<option value="0">-- Please product spec attribute group --</option>
													<form:options items="${speccategory}"/> 													
												</form:select>												
											</div>											
										</div>
									</div>
									<div class="portlet-body form">
										<div id="specattributeGroup" class="form-horizontal form-row-seperated">
											
										</div>
									</div>
								</div>															
								
									<div class="portlet light bg-inverse form-fit">
									<div class="portlet-title">
										<div class="caption">
											<i class="icon-equalizer font-red-sunglo"></i>
											<span class="caption-subject font-red-sunglo bold">Order Attributes</span>											
										</div>
										<div class="actions">
											<div class="portlet-input input-inline">
												<form:select path="attributeGroup.categoryId" cssClass="form-control" name="category" id="chooseorderCategory">												
													<option value="0">-- Please product order attribute group --</option>
													<form:options items="${ordercategory}"/> 													
												</form:select>												
											</div>											
										</div>
									</div>
									<div class="portlet-body form">
										<div id="orderattributeGroup" class="form-horizontal form-row-seperated">
											
										</div>
									</div>
								</div>
								
								<!--Begin images upload form-->
								<h3 class="form-section">Upload Images</h3>
								<div class="row">
									<div class="col-md-12">	
										
										<div id="uploadMsg">												
										</div>
										<div id="tab_images_uploader_container" class="text-align-reverse margin-bottom-10">
											<a id="tab_images_uploader_pickfiles" href="javascript:;" class="btn yellow">
											<i class="fa fa-plus"></i> Select Files </a>
											<a id="tab_images_uploader_uploadfiles" href="javascript:;" class="btn green">
											<i class="fa fa-share"></i> Upload Files </a>
										</div>
										<div class="row">
											<div id="tab_images_uploader_filelist" class="col-md-6 col-sm-12">
											</div>
										</div>
										<table class="table table-bordered table-hover">
										<thead>
										<tr role="row" class="heading">
											<th width="10%">
												 Image
											</th>
											<th width="25%">
												 Image Name
											</th>
											<th width="25%">
												 Image Size
											</th>												
											<th width="30%">
												 Status
											</th>
											<th width="10%">
											</th>
										</tr>
										</thead>
										<tbody id="uploadedImagesList">											
										
										</tbody>
										</table>
									
									</div>
								</div>
								<!--End images upload form-->								
							</div>
							<div class="form-actions">
								<div class="row">
									<div class="col-md-offset-3 col-md-9">
										<button type="submit" class="btn green">Submit</button>
										<button type="button" class="btn default" onclick="location.href='javascript:history.go(-1);'">Cancel</button>
									</div>
								</div>
							</div>

						</form:form>
						<!-- END FORM-->
					</div>
				</div>
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
	<script src="../assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="../assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="../assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>	
	<script src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
	<!--<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>-->
	<script src="../assets/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-markdown/js/bootstrap-markdown.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-markdown/lib/markdown.js" type="text/javascript"></script>
	
	<script src="../assets/global/plugins/fancybox/source/jquery.fancybox.pack.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/plupload/js/plupload.full.min.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS-->	

	<script src="../assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
	<script src="../static/js/addgoods.js"></script>		

	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout	
			Addgoods.init("<c:url value="/"/>");
		});
	</script>
<c:import url="/common/notice"/>
</body>

<!-- END BODY -->


</html>