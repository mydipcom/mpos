<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>    
<title><s:message code="category"/></title>
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
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/jquery-tags-input/jquery.tagsinput.css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/typeahead/typeahead.css">
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
				<%-- <div class="page-bar">
					<ul class="page-breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="<c:url value="/"/>home"><s:message code="home"/></a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="<c:url value="/"/>category/category"><s:message code="category"/></a>
						</li>
					</ul>					
				</div> --%>
				<div id="msg"></div>
				<!-- END PAGE TITLE & BREADCRUMB-->
				
				<!-- BEGIN SEARCH FORM -->
				<div class="portlet-body">
					<form id="searchForm" name="searchForm" action="categoryList" class="form-horizontal" method="post">
					<input type="hidden" name="status" value="true"/>
					<div class="row">
						<div class="col-md-4">					
							<div class="form-group">
								<label class="col-md-3 control-label"><s:message code="category.name"/></label>
								<div class="col-md-9">
									<input name="name" type="text" class="form-control">
								</div>
							</div>
						</div>
						
							<div class="col-md-4">					
							<div class="form-group">
							<c:if test="${role==1}">
							<label class="col-md-3 control-label"><s:message code="store.name"/></label>
								<div class="col-md-9">
									<select name="storeId"  class="form-control" id="storeId">
										<c:if test="${not empty stores}">
										<c:forEach var="store" items="${stores }">
										<option value="${store.storeId}">${store.storeName}</option>
										</c:forEach>
										</c:if>
									</select>					
								</div>
								</c:if>
							</div>
						</div>
						
						<div class="col-md-4">	
							<div class="form-group">								
								<div class="col-md-offset-3 col-md-9">
									<button type="submit" class="btn blue"><s:message code="system.search"/><i class="fa fa-search"></i></button>
									<button type="reset" class="btn grey-cascade"><s:message code="system.reset"/> <i class="fa fa-reply"></i></button>
								</div>
							</div>					
						</div>
					</div>
					
					</form>
				</div>
				<!-- END SEARCH FORM -->
				
				<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i><s:message code="category"/>
								</div>
								<div class="actions">
									<a class="btn btn-default btn-sm" href="#" id="CloneSelectedCategory"><i class="fa fa-plus"></i><s:message code="category.copy.add" /></a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#add_category" id="openAddCategoryModal"><i class="fa fa-plus"></i> <s:message code="all.table.add" /></a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#edit_category" id="openEditCategoryModal"><i class="fa fa-pencil"></i> <s:message code="all.table.edit" /></a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#delete_category" id="openDeleteCategoryModal"><i class="fa fa-trash-o"></i> <s:message code="all.table.delete" /></a>
								    <%-- <div class="btn-group">
										<a class="btn default" href="#" data-toggle="dropdown">
										<s:message code="system.column" /> <i class="fa fa-angle-down"></i>
										</a>
										<div id="column_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
											<label><input type="checkbox" checked data-column="0"><s:message code="system.checkbox" /> </label>
											<label><input type="checkbox" checked data-column="1"><s:message code="category.id" /></label>
											<label><input type="checkbox" checked data-column="2"><s:message code="category.name" /></label>
											<label><input type="checkbox" checked data-column="3"><s:message code="category.content" /></label>
										</div>
									</div> --%>								    																
								</div>
							</div>							
							<div class="portlet-body">																
								<table class="table table-striped table-hover table-bordered" id="category_table">
									<thead>
										<tr>
											<th class="table-checkbox">
												<input type="checkbox" class="group-checkable" data-set="#category_table .checkboxes"/>
											</th>
											<%-- <th><s:message code="category.id" /></th> --%>
											<th><s:message code="category.name" /></th>
											<th><s:message code="category.content" /></th>
											<th><s:message code="category.type" /></th>
											<th><s:message code="system.action" /></th>
										</tr>
									</thead>
																						
								</table>
							</div>
							<div style="border: 1px solid rgb(75, 199, 94);"></div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT -->
				
				<!-- BEGIN ADD MODAL FORM-->
				<div class="modal" id="add_category" tabindex="-1" data-width="960">
					<div class="modal-header">
						<button id="closeAddModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title"><s:message code="category.add"/></h4>
					</div>
					<div id="addFormMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="addCategoryForm" action="addCategory" method="post" name="addCategoryForm" class="form-horizontal">							
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									<s:message code="system.management.user.adduser.message"/>
								</div>	
								<div class="portlet box grey-silver tabbable">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i><s:message code="system.lan.form"/>
										</div>
									</div>
									<div class="portlet-body">
										<div class="portlet-tabs">																														
											<ul class="nav nav-tabs">
												<c:forEach var="lan" items="${lanList}" varStatus="status">											
												<li><a href="#add_category_tab${status.index+2}" data-toggle="tab"><img src="${lan.flagImage}"> ${lan.name}</a></li>												
												</c:forEach>	
												<li class="active"><a href="#add_category_tab1" data-toggle="tab"> <s:message code="system.standard" /> </a></li>
											</ul>
											<div class="tab-content">												
												<c:forEach var="lan" items="${lanList}" varStatus="status">
												<div class="tab-pane" id="add_category_tab${status.index+2}">													
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-3"><s:message code="category.name" /></label>
															<div class="col-md-5">
																<input type="text" name="categoryName_locale[${status.index}].localeValue" class="form-control"/>	
																<input type="hidden" name="categoryName_locale[${status.index}].language.id"  value="${lan.id}"/>																											               
															</div>
														</div>														
													</div>
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-3"><s:message code="category.content" /></label>
															<div class="col-md-9">
																<input type="text" name="categoryDescr_locale[${status.index}].localeValue" class="form-control"/>	
																<input type="hidden" name="categoryDescr_locale[${status.index}].language.id"  value="${lan.id}"/>																																
															</div>
														</div>														
													</div>
													
												</div>
												</c:forEach>
												<div class="tab-pane active" id="add_category_tab1">
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-3"><s:message code="category.name" /><span class="required"> * </span></label>
															<div class="col-md-5">																	
																<input type="text" name="name" class="form-control"/>																
															</div>
														</div>														
													</div>
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-3"><s:message code="category.content" /></label>
															<div class="col-md-9">																	
																<input type="text" name="content" class="form-control"/>
																<input type="hidden" name="storeId" value="-1"/>
															</div>
														</div>														
													</div>
													
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4"><s:message code="category.type" /></label>
									<div class="col-md-8">
										<div class="radio-list">
											<label class="radio-inline"><input type="radio" name="type" value="0" checked/><s:message code="category.type.specification" /></label>
											<label class="radio-inline"><input type="radio" name="type" value="1"/><s:message code="category.type.order" /> </label>											
										</div>																																							
									</div>
								</div>
								<input type="hidden" name="status" value="true"/>	
								<%-- <div class="form-group">
									<label class="control-label col-md-2"><s:message code="category.status" /></label>
									<div class="col-md-10">
										<div class="radio-list">
											<label class="radio-inline"><input type="radio" name="status" value="true" checked/>True</label>
											<label class="radio-inline"><input type="radio" name="status" value="false"/>False </label>											
										</div>																																							
									</div>
								</div> --%>													
								
							</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div style="text-align: center;">
										<button type="submit" class="btn green" id="addFormSubmit"><i class="fa fa-check"></i> <s:message code="system.submit" /></button>
										<button type="button" class="btn btn-default" data-dismiss="modal"><s:message code="system.close" /></button>
									</div>
								</div>
							</div>
						</form>
						<!-- END FORM-->
					</div>					
				</div>				
				<!-- END ADD MODAL FORM-->
				
				<!-- BEGIN Edit MODAL FORM-->
				<div class="modal" id="edit_category" tabindex="-1" data-width="960">
					<div class="modal-header">
						<button id="closeEditModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title"><s:message code="category.edit" /></h4>
					</div>
					<div id="editFormMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="editCategoryForm" action="editCategoryForm" method="post" name="editCategoryForm" class="form-horizontal">
							<input type="hidden" name="categoryId"/>	
							<input type="hidden" name="storeId"/>							
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									<s:message code="system.management.user.adduser.message"/>
								</div>	
								<div class="portlet box grey-silver tabbable">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i><s:message code="system.lan.form"/>
										</div>
									</div>
									<div class="portlet-body">
										<div class="portlet-tabs">																														
											<ul class="nav nav-tabs">
												<c:forEach var="lan" items="${lanList}" varStatus="status">											
												<li><a href="#edit_category_tab${status.index+2}" data-toggle="tab"><img src="${lan.flagImage}"> ${lan.name}</a></li>												
												</c:forEach>	
												<li class="active"><a href="#edit_category_tab1" data-toggle="tab"> <s:message code="system.standard" /></a></li>
											</ul>
											<div class="tab-content">												
												<c:forEach var="lan" items="${lanList}" varStatus="status">
												<div class="tab-pane" id="edit_category_tab${status.index+2}">												
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-3"><s:message code="category.name" /></label>
															<div class="col-md-5">
																<input type="hidden" name="categoryName_locale[${status.index}].language.id"  value="${lan.id}"/>
																<input type="hidden" name="categoryName_locale[${status.index}].localeId" class="form-control"/>																
																<input type="text" name="categoryName_locale[${status.index}].localeValue" class="form-control"/>																																												               
															</div>
														</div>														
													</div>
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-3"><s:message code="category.content" /></label>
															<div class="col-md-9">																	
																<input type="hidden" name="categoryDescr_locale[${status.index}].language.id"  value="${lan.id}"/>
																<input type="hidden" name="categoryDescr_locale[${status.index}].localeId" class="form-control"/>
																<input type="text" name="categoryDescr_locale[${status.index}].localeValue" class="form-control"/>																																															
															</div>
														</div>														
													</div>													
												</div>
												</c:forEach>
												<div class="tab-pane active" id="edit_category_tab1">
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-3"><s:message code="category.name" /><span class="required"> * </span></label>
															<div class="col-md-5">																	
																<input type="text" name="name" class="form-control"/>																
															</div>
														</div>														
													</div>
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-3"><s:message code="category.content" /></label>
															<div class="col-md-9">																	
																<input type="text" name="content" class="form-control"/>
															</div>
														</div>														
													</div>
													
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4"><s:message code="category.type" /></label>
									<div class="col-md-8">
										<div class="radio-list">
											<label class="radio-inline"><input type="radio" name="type" value="0" disabled/><s:message code="category.type.specification" /></label>
											<label class="radio-inline"><input type="radio" name="type" value="1" disabled/><s:message code="category.type.order" /> </label>											
										</div>																																							
									</div>
								</div>
								<input type="hidden" name="status" value="true"/>
								<%-- <div class="form-group">
									<label class="control-label col-md-2"><s:message code="category.status" /></label>
									<div class="col-md-10">
										<div class="radio-list">
											<label class="radio-inline"><input type="radio" name="status" value="true"/>True</label>
											<label class="radio-inline"><input type="radio" name="status" value="false"/>False </label>											
										</div>																																							
									</div>
								</div> --%>													
								
							</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div style="text-align: center;">
										<button type="submit" class="btn green" id="editFormSubmit"><i class="fa fa-check"></i> <s:message code="system.submit" /></button>
										<button type="button" class="btn btn-default" data-dismiss="modal"><s:message code="system.close" /></button>
									</div>
								</div>
							</div>
						</form>
						<!-- END FORM-->
					</div>					
				</div>		
				<!-- END EDIT MODAL FORM-->
				
				<!-- BEGIN DELETE MODAL FORM-->
				<div class="modal" id="delete_category" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p><s:message code="system.management.user.deletemessage" /></p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default"><s:message code="system.close" /></button>
						<button id="deleteBtn" type="button" data-dismiss="modal" class="btn blue"><s:message code="system.submit" /></button>
					</div>					
				</div>				
				<!-- END DELETE MODAL FORM-->
				
				
				
				<!--BEGIN VIEW ATTRIBUTE  -->
				<div class="modal" id="view_attribute" tabindex="-1" data-width="960">
					<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						 <h4 class="modal-title"><s:message code="attribute" /></h4>
					</div>
				    
					<div class="modal-body">
					    <div id="view_attributeMsg"></div>
						<div class="col-md-12">
						<input type="hidden" value="" id="cate_id">
							<!-- BEGIN EXAMPLE TABLE PORTLET-->
								<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
									<div class="caption">
										<!-- <i class="fa fa-edit"></i> --><%-- <s:message code="attribute" /> --%>
									</div>
							<div class="actions">
								<a class="btn btn-default btn-sm" data-toggle="modal" href="#add_attribute" id="openAddAttributeModal"><i class="fa fa-plus"></i><s:message code="category.add.attribute" /></a>
								<a class="btn btn-default btn-sm" data-toggle="modal" href="#edit_attribute" id="openEditAttributeModal"><i class="fa fa-pencil"></i> <s:message code="all.table.edit" /></a>
								<a class="btn btn-default btn-sm" data-toggle="modal" href="#delete_attribute" id="openDeleteAttributeModal"><i class="fa fa-trash-o"></i> <s:message code="all.table.delete" /></a>
							</div>
							</div>
							
							<div class="portlet-body">																
									<table class="table table-striped table-hover table-bordered" id="att_table">
										<thead>
											<tr>
												<th class="table-checkbox">
													<input type="checkbox" class="attr-checkable" data-set="#att_table .checkboxes"/>
												</th>											    
												<th><s:message code="attribute.title" /></th>
												<th><s:message code="attribute.type" /></th>												
												<th><s:message code="attribute.sort" /></th>
												<th><s:message code="attribute.required" /></th>
												<th><s:message code="attribute.values" /></th>
											</tr>
										</thead>
																							
									</table>
								</div>
						
							<!-- END EXAMPLE TABLE PORTLET-->
						</div> 
						</div>
					</div>
			   </div>
				<!--END VIEW ATTRIBUTE  -->
	
				<!--BEGIN ADD ATTRIBUTE  -->
				<div class="modal" id="add_attribute" tabindex="-1" data-width="960" data-height="450">
					<div class="modal-header">
						<button id="closeAddAttributeModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title"><s:message code="category.add.attribute" /></h4>
					</div>
					<div id="addAttributeFormMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="addAttributeForm" action="addAttribute" method="post" name="addAttributeForm" class="form-horizontal">
							<div class="form-body modal-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									<s:message code="system.management.user.adduser.message"/>
								</div>
								<input type="hidden" value="" name="categoryId.categoryId"/>
								<input type="hidden" value="" name="categoryId.type"/>
								<input type="hidden" value="true" name="status">
								<div class="form-group" id="addAttrType" style="margin:0 0 15px;">
									<label class="control-label col-md-2"><s:message code="attribute.type" /> <span class="required">* </span></label>
										<div class="col-md-10">										
											<div class="radio-list">
												<!-- <label class="radio-inline"><input type="radio" name="type" value="0"/>Editbox</label> -->
												<label class="radio-inline"><input type="radio" name="type" value="1"/><s:message code="system.radio" /> </label>
												<label class="radio-inline"><input type="radio" name="type" value="2"checked/><s:message code="system.checkbox" /> </label>
												<label class="radio-inline"><input type="radio" name="type" value="3"/><s:message code="system.select" /> </label>
											</div>
										</div>
								</div>
								<div class="form-group" style="margin:0 0 15px;">
									<label class="control-label col-md-2"><s:message code="attribute.required" /> <span class="required">* </span></label>
									<div class="col-md-10">										
										<div class="radio-list">
											<label class="radio-inline"><input type="radio" name="required" value="true"/><s:message code="all.status.enable" /></label>
											<label class="radio-inline"><input type="radio" name="required" value="false" checked/><s:message code="all.status.disable" /></label>											
										</div>									
									</div>
								</div>
								<div class="form-group" style="margin:0 0 15px;">
									<label class="control-label col-md-2"><s:message code="attribute.sort" /> <span class="required">* </span></label>
									<div class="col-md-10">																				
										<input name="sort" class="form-control input-small" value=""/>									
									</div>
								</div>	
								<div class="portlet box grey-silver tabbable">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i><s:message code="system.lan.form"/>
										</div>
									</div>
									<div class="portlet-body">
										<div class="portlet-tabs">																														
											<ul class="nav nav-tabs">
												<c:forEach var="lan" items="${lanList}" varStatus="status">											
												<li><a href="#add_attribute_tab${status.index+2}" data-toggle="tab"><img src="${lan.flagImage}"> ${lan.name}</a></li>												
												</c:forEach>	
												<li class="active"><a href="#add_attribute_tab1" data-toggle="tab"> <s:message code="system.standard" /></a></li>
											</ul>
											<div class="tab-content">												
												<c:forEach var="lan" items="${lanList}" varStatus="status">
												<div class="tab-pane" id="add_attribute_tab${status.index+2}">													
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-2"><s:message code="attribute.title" /></label>
															<div class="col-md-4">
																<input type="text" name="title_locale[${status.index}].localeValue" class="form-control"/>	
																<input type="hidden" name="title_locale[${status.index}].language.id"  value="${lan.id}"/>																													               
															</div>
														</div>														
													</div>
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-2"><s:message code="attribute.values" /></label>
															<div class="col-md-9">																
																<input type="hidden" name="values_locale[${status.index}].language.id"  value="${lan.id}"/>
																<input type="text" name="values_locale[${status.index}].localeValue" class="form-control select2_sample3"/>																																	
															</div>
														</div>														
													</div>
													
												</div>
												</c:forEach>
												<div class="tab-pane active" id="add_attribute_tab1">
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-2"><s:message code="attribute.title" /><span class="required"> * </span></label>
															<div class="col-md-4">																	
																<input type="text" name="title" class="form-control"/>																
															</div>
														</div>														
													</div>
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-2"><s:message code="attribute.values" /><span class="required"> * </span></label>
															<div class="col-md-9">																	
																<input type="text" name="values" class="form-control select2_sample3" value=""/>
															</div>
														</div>														
													</div>
													
												</div>
											</div>
										</div>
									</div>
								</div>																							
								
							</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div style="text-align: center;">
										<button type="submit" class="btn green" id="addAttributeFormSubmit"><i class="fa fa-check"></i> <s:message code="system.submit" /></button>
										<button type="button" class="btn btn-default" data-dismiss="modal"><s:message code="system.close" /></button>
									</div>
								</div>
							</div>
						</form>
						<!-- END FORM-->
					</div>					
				</div>
				<!--END ADD ATTRIBUTE  -->
	
	
	
				<!--BEGIN EDIT ATTRIBUTE  -->
				<div class="modal" id="edit_attribute" tabindex="-1" data-width="960">
					<div class="modal-header">
						<button id="closeEditAttributeModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title"><s:message code="attribute.edit" /></h4>
					</div>
					<div id="editAttributeFormMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="editAttributeForm" action="editAttribute" method="post" name="editAttributeForm" class="form-horizontal">
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									<s:message code="system.management.user.adduser.message"/>
								</div>
								<input type="hidden" value="" name="categoryId.categoryId"/>
								<input type="hidden" value="" name="categoryId.type"/>
								<input type="hidden" value="" name="attributeId"/>
								<input type="hidden" value="true" name="status">
								<div class="form-group" id="editAttrType">
									<label class="control-label col-md-2"><s:message code="attribute.type" /> <span class="required">* </span></label>
										<div class="col-md-10">										
											<div class="radio-list">
												<!-- <label class="radio-inline"><input type="radio" name="type" value="0"/>Editbox</label> -->
												<label class="radio-inline"><input type="radio" name="type" value="1"/><s:message code="system.radio" /> </label>
												<label class="radio-inline"><input type="radio" name="type" value="2"checked/><s:message code="system.checkbox" /> </label>
												<label class="radio-inline"><input type="radio" name="type" value="3"/><s:message code="system.select" /> </label>
											</div>
										</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2"><s:message code="attribute.required" /> <span class="required">* </span></label>
									<div class="col-md-10">										
										<div class="radio-list">
											<label class="radio-inline"><input type="radio" name="required" value="true"/><s:message code="all.status.enable" /></label>
											<label class="radio-inline"><input type="radio" name="required" value="false" checked/><s:message code="all.status.disable" /></label>													
										</div>									
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2"><s:message code="attribute.sort" /> <span class="required">* </span></label>
									<div class="col-md-10">																				
										<input name="sort" class="form-control input-small" value=""/>									
									</div>
								</div>	
								<div class="portlet box grey-silver tabbable">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i><s:message code="system.lan.form"/>
										</div>
									</div>
									<div class="portlet-body">
										<div class="portlet-tabs">																														
											<ul class="nav nav-tabs">
												<c:forEach var="lan" items="${lanList}" varStatus="status">											
												<li><a href="#edit_attribute_tab${status.index+2}" data-toggle="tab"><img src="${lan.flagImage}"> ${lan.name}</a></li>												
												</c:forEach>	
												<li class="active"><a href="#edit_attribute_tab1" data-toggle="tab"> <s:message code="system.standard" /></a></li>
											</ul>
											<div class="tab-content">												
												<c:forEach var="lan" items="${lanList}" varStatus="status">
												<div class="tab-pane" id="edit_attribute_tab${status.index+2}">													
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-2"><s:message code="attribute.title" /></label>
															<div class="col-md-4">
																<input type="hidden" name="title_locale[${status.index}].language.id"  value="${lan.id}"/>
																<input type="hidden" name="title_locale[${status.index}].localeId" class="form-control"/>	
																<input type="text" name="title_locale[${status.index}].localeValue" class="form-control"/>																																													               
															</div>
														</div>														
													</div>
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-2"><s:message code="attribute.values" /></label>
															<div class="col-md-9">
																<input type="hidden" name="values_locale[${status.index}].language.id"  value="${lan.id}"/>																	
																<input type="text" name="values_locale[${status.index}].localeValue" class="form-control select2_sample3"/>																																													               
															</div>
														</div>														
													</div>																										
												</div>
												</c:forEach>
												<div class="tab-pane active" id="edit_attribute_tab1">
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-2"><s:message code="attribute.title" /><span class="required"> * </span></label>
															<div class="col-md-4">																	
																<input type=text name="title" class="form-control"/>																
															</div>
														</div>														
													</div>
													<div class="form-body">														
														<div class="form-group">
															<label class="control-label col-md-2"><s:message code="attribute.values" /><span class="required"> * </span></label>
															<div class="col-md-9">																	
																<input type="text" name="values" class="form-control select2_sample3"/>
															</div>
														</div>														
													</div>
													
												</div>
											</div>
										</div>
									</div>
								</div>																															
							</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div style="text-align: center;">
										<button type="submit" class="btn green" id="editAttributeFormSubmit"><i class="fa fa-check"></i> <s:message code="system.submit" /></button>
										<button type="button" class="btn btn-default" data-dismiss="modal"><s:message code="system.close" /></button>
									</div>
								</div>
							</div>
						</form>
						<!-- END FORM-->
					</div>					
				</div>
				<!--END EDIT ATTRIBUTE  -->
				
				<!-- BEGIN DELETE ATTRIBUTE FORM-->
				<div class="modal" id="delete_attribute" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>
							 	<s:message code="system.management.user.deletemessage" />
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default"><s:message code="system.close" /></button>
						<button id="deleteAttBtn" type="button" data-dismiss="modal" class="btn blue"><s:message code="system.submit" /></button>
					</div>					
				</div>				
				<!-- END DELETE ATTRIBUTE FORM-->
	
	
				
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
	<script src="../assets/global/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
    <script src="../assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
    
    <script type="text/javascript" src="../assets/global/plugins/fuelux/js/spinner.min.js"></script>
	<script type="text/javascript" src="../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js"></script>
	<script type="text/javascript" src="../assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
	<script src="../assets/global/plugins/bootstrap-pwstrength/pwstrength-bootstrap.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/jquery-tags-input/jquery.tagsinput.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/typeahead/handlebars.min.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/typeahead/typeahead.bundle.min.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/jquery-i18n/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="../assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>	
	<script src="../assets/admin/pages/scripts/components-form-tools.js"></script>
	<script src="../static/js/common.js"></script>
	<script src="../static/js/categoryTableData.js"></script>
	<script>
	jQuery(document).ready(function() {       
	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout	
	   //Demo.init(); // init demo features
	   CategoryTable.init("<c:url value="/"/>","${sessionScope.locale}");	
	   //ComponentsFormTools.init();
	});
	</script>
	<c:import url="/common/notice"/>
</body>
<!-- END BODY -->

</html>
