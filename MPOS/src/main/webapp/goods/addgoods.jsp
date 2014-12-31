<%@page import="java.nio.file.Files"%>
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

<title>Add Goods</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>

<meta content="" name="description"/>

<meta content="" name="author"/>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="../assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet"/>

<link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>

<link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>

<link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

<link href="../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>

<link href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>

<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL STYLES -->

<link href="../assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
<link href="../assets/admin/pages/css/profile.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css"/>

<link href="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
<link href="../assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
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

			
				<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			Goods<small>Add Goods</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="index.html">Home</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">goodsList</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">addGoods</a>
					</li>
				</ul>
			
			</div>
																<div class="portlet box green">
									<div class="portlet-title">
										<div class="caption">
											<i class="fa fa-gift"></i>Add goods
										</div>
									</div>
									<div class="portlet-body form">
										<!-- BEGIN FORM-->
										<form action="setgoods" role="form" class="form-horizontal" enctype="multipart/form-data" id="addGoodsForm" method="POST">
											<div class="form-body">
												<h3 class="form-section">BasicGoods Info</h3>
												<div class="row">
												<!--/span-->
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">Price</label>
															<div class="col-md-9">
																<input type="text" name="price" class="form-control" placeholder="0.00">
															</div>
														</div>
													</div>
													<!--/span-->
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">OldPrice</label>
															<div class="col-md-9">
																<input type="text" name="oldPrice" class="form-control" placeholder="0.00">
															</div>
														</div>
													</div>
													
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">recommend</label>
															<div class="col-md-9">
																<div class="radio-list">
																	<label class="radio-inline">
																	<input type="radio"  name="recommend" value="ture"/>
																	True </label>
																	<label class="radio-inline">
																	<input type="radio"  name="recommend" value="false"  checked/>
																	Flase </label>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">menuName</label>
															<div class="col-md-9">
																<select class="select2_category form-control" data-placeholder="Choose a Category" tabindex="1" name="menuId">
																
																	<c:if test="${not empty menu}">
																		<c:forEach items="${menu}" var="menuitem">
																			<option value="${menuitem.menuId}">${menuitem.title}</option>
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
															<label class="control-label col-md-3">unitName</label>
															<div class="col-md-6">
																<input type="text" name="unitName" class="form-control">
															</div>	
														</div>
													</div>	
												
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">sort</label>
															<div class="col-md-9">
																<input type="text" name="sort" class="form-control" >
															</div>
														</div>
													</div>
													<!--/span-->
													
													<!--/span-->
												</div>
												<!--/row-->
												
						
								<input type="hidden" id="lan_len" value="${fn:length(lanList)}">
									<ul class="nav nav-tabs">
									<c:forEach var="lan" items="${lanList}" varStatus="status">
									 	<c:choose>   
									 		<c:when test="${lan.sort == 0}">
									 			<li class="active"><a href="#cate_add_${status.index}" data-toggle="tab"><img src="${lan.flagImage}">${lan.name}</a></li>
									 		</c:when> 
									 		<c:otherwise>          
									 			<li><a href="#cate_add_${status.index}" data-toggle="tab"><img src="${lan.flagImage}">${lan.name}</a></li>
									 		</c:otherwise> 
									 	</c:choose> 
                  					</c:forEach> 
								</ul>
									<div class="tab-content">
								
									<c:forEach var="lan" items="${lanList}" varStatus="status">
									 	<c:choose>   
									 		<c:when test="${lan.sort == 0}">
									 			<div class="tab-pane fade in active" id="cate_add_${status.index}">
									 			<div class="row">
									 			<div class="col-md-6">
									 				<div class="form-group">
														<label class="control-label col-md-3">GoodsName<span class="required"> * </span></label>
														<div class="col-md-9">										
															<input name="productName" class="form-control" onblur="setValue('${lan.id}')" id="good_add_name"/>
															<input name="productNames[${status.index}].localeValue" type="hidden" id="good_addt_${lan.id}"/>
															<input name="productNames[${status.index}].language.id" type="hidden" value="${lan.id}"/>		
															<input name="productNames[${status.index}].tableField" type="hidden" value="productName"/>										
														</div>
													</div>
												</div>
												</div>
												<div class="row">
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-3">ShortDescribe<span class="required"> * </span></label>
														<div class="col-md-9">
															<!-- <input id="tags_1" type="text" class="form-control tags" name="content"/> -->										
															<textarea name="shortDescr" class="form-control" rows="4" onblur="setValue('${lan.id}')" id="good_add_scon"></textarea>	
															<input name="shortDescrs[${status.index}].localeValue" type="hidden" id="good_addc_${lan.id}"/>
															<input name="shortDescrs[${status.index}].language.id" type="hidden" value="${lan.id}"/>		
															<input name="shortDescrs[${status.index}].tableField" type="hidden" value="shortDescr"/>										
														</div>
													</div>
												</div>
											</div>
									<div class="row">
									<div class="col-md-9">
										<div class="form-group">
										<label class="control-label col-md-2">FullDescribe</label>
										<div class="col-md-9">
											<textarea name="fullDescr" data-provide="markdown" rows="10" onblur="setValue('${lan.id}')" id="good_add_fcon"></textarea>
											<input name="fullDescrs[${status.index}].localeValue" type="hidden" id="good_addft_${lan.id}"/>
											<input name="fullDescrs[${status.index}].language.id" type="hidden" value="${lan.id}"/>		
											<input name="fullDescrs[${status.index}].tableField" type="hidden" value="fullDescr"/>	
										</div>
										</div>
										</div>
									</div>
									 			</div>
									 		</c:when> 
									 		<c:otherwise>          
									 			<div class="tab-pane fade" id="cate_add_${status.index}">
									 			<div class="row">
									 			<div class="col-md-6">
									 				<div class="form-group">
														<label class="control-label col-md-3">GoodsName<span class="required"> * </span></label>
														<div class="col-md-9">										
															<input name="productNames[${status.index}].localeValue" class="form-control"/>
															<input name="productNames[${status.index}].language.id" type="hidden" value="${lan.id}"/>		
															<input name="productNames[${status.index}].tableField" type="hidden" value="productName"/>	
														</div>
													</div>
												</div>
												</div>	
													<div class="row">
													<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-3">ShortDescribe<span class="required"> * </span></label>
														<div class="col-md-9">
															<%-- <input id="tags_1" type="text" class="form-control tags" name="contents[${status.index}].localeValue"/> --%>
															<textarea name="shortDescrs[${status.index}].localeValue" class="form-control" rows="4"></textarea>											
															<input name="shortDescrs[${status.index}].language.id" type="hidden" value="${lan.id}"/>		
															<input name="shortDescrs[${status.index}].tableField" type="hidden" value="shortDescr"/>										
														</div>
													</div>
													</div>
											</div>
									<div class="row">
									<div class="col-md-9">
										<div class="form-group">
										<label class="control-label col-md-2">FullDescribe</label>
										<div class="col-md-9">
											<textarea name="fullDescrs[${status.index}].localeValue" data-provide="markdown" rows="10" ></textarea>
											<input name="fullDescrs[${status.index}].language.id" type="hidden" value="${lan.id}"/>		
											<input name="fullDescrs[${status.index}].tableField" type="hidden" value="fullDescr"/>	
										</div>
										</div>
										</div>
									</div>
									 			</div>
									 		</c:otherwise> 
									 	</c:choose> 
                  					</c:forEach> 
								
								</div>
							
												<h3 class="form-section">Choose Category</h3>
												<!--/row-->
												<div class="row">
													<div class="col-md-6" >
														<div class="form-group">
															<label class="control-label col-md-3">Category</label>
															<div class="col-md-9">
															<!--  select name="categoryId"  class="form-control"  id="testsid">-->	
															<select name="categoryId"  class="form-control" id="choosecategory">
																	<option value="">ALL</option>
																		<c:if test="${not empty category}">
																			<c:forEach items="${category}" var="categoryitem">
																				<option value="${categoryitem.categoryId}">${categoryitem.name}</option>
																			</c:forEach>
																		</c:if>		
																</select>
															</div>
														</div>
													</div>
													<div class="col-md-6" >
														<div class="form-group" id="title">
															<label class="control-label col-md-3">title</label>
															<div class="col-md-9" id=fortest>
															<select name="attributeId"  class="form-control" id="chooseattribute">
															<option value="" id="attributetitle">ALL</option>
															
																</select>
															</div>
														</div>
													</div>
												</div>	
											  
										<div class="form-group">
										 <div id="type1"></div>
										 <div id="type2"></div>
										 <div id="type3"></div>
										
										</div> 
						<h3 class="form-section">Images</h3>
							<!-- The global progress information -->
								
								
								<div class=row>
								<div class="col-md-12" >
									
								
								<div class="fileinput fileinput-new" data-provides="fileinput">
									<div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
										<img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image" alt=""/>
									</div>
										<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;">
										</div>
											<div>
												<span class="btn default btn-file">
												<span class="fileinput-new">
													Select image </span>
												<span class="fileinput-exists">
												Change </span>
												<input type="file" name="files" accept="image/*">
												</span>
												<a href="#" class="btn default fileinput-exists" data-dismiss="fileinput">
												Remove </a>
												</div>
											</div>
									<div class="fileinput fileinput-new" data-provides="fileinput">
									<div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
										<img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image" alt=""/>
									</div>
										<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;">
										</div>
											<div>
												<span class="btn default btn-file">
												<span class="fileinput-new">
													Select image </span>
												<span class="fileinput-exists">
												Change </span>
												<input type="file" name="files" accept="image/*">
												</span>
												<a href="#" class="btn default fileinput-exists" data-dismiss="fileinput">
												Remove </a>
												</div>
											</div>
											<div class="fileinput fileinput-new" data-provides="fileinput">
									<div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
										<img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image" alt=""/>
									</div>
										<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;">
										</div>
											<div>
												<span class="btn default btn-file">
												<span class="fileinput-new">
													Select image </span>
												<span class="fileinput-exists">
												Change </span>
												<input type="file" name="files" accept="image/*">
												</span>
												<a href="#" class="btn default fileinput-exists" data-dismiss="fileinput">
												Remove </a>
												</div>
											</div>
											<div class="fileinput fileinput-new" data-provides="fileinput">
									<div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
										<img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image" alt=""/>
									</div>
										<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;">
										</div>
											<div>
												<span class="btn default btn-file">
												<span class="fileinput-new">
													Select image </span>
												<span class="fileinput-exists">
												Change </span>
												<input type="file" name="files" accept="image/*">
												</span>
												<a href="#" class="btn default fileinput-exists" data-dismiss="fileinput">
												Remove </a>
												</div>
											</div>
										</div>
										
										</div>
										
									
										
										
										
												<!--/row-->
											
											<div class="form-actions">
												<div class="row">
													<div class="col-md-6">
														<div class="row">
															<div class="col-md-offset-3 col-md-9">
																<button type="submit" class="btn green">Submit</button>
																<button type="button" class="btn default">Cancel</button>
															</div>
														</div>
													</div>
													
												</div>
											</div>
											
											</div>
											
										
										</form>
										<!-- END FORM-->
									</div>
								</div>
								</div>
								</div>

				</div>
			
			
			<!-- END PAGE CONTENT-->
				
			<!-- BEGIN Edit Role Rights MODAL FORM-->
				<div class="modal" id="categoryAttributes" tabindex="-1" data-width="760">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">Add categoryAttributes value</h4>
					</div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">

						<!-- BEGIN FORM-->						

						<form id="categoryAttributesForm"  class="form-horizontal form-bordered">
														
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									<s:message code="system.management.user.adduser.message"/>
								</div>
							</div>
								<div  id=name>
								
								</div>
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div class="col-md-offset-6 col-md-6">
										<button type="submit" class="btn green" id="categoryAttributesFormSubmit"><i class="fa fa-check"></i> Submit</button>
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									</div>
								</div>
							</div>
						</form>
						<!-- END FORM-->

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
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
		
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger label label-danger"></strong>
        </td>
      
        <td>
            {% if (!i) { %}
                <button class="btn red cancel">
                    <i class="fa fa-ban"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
            <tr class="template-download fade">
                <td>
                    <span class="preview">
                        {% if (file.thumbnailUrl) { %}
                            <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                        {% } %}
                    </span>
                </td>
                <td>
                    <p class="name">
                        {% if (file.url) { %}
                            <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                        {% } else { %}
                            <span>{%=file.name%}</span>
                        {% } %}
                    </p>
                    {% if (file.error) { %}
                        <div><span class="label label-danger">Error</span> {%=file.error%}</div>
                    {% } %}
                </td>
                <td>
                    <span class="size">{%=o.formatFileSize(file.size)%}</span>
                </td>
                <td>
                    {% if (file.deleteUrl) { %}
                        <button class="btn red delete btn-sm" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                            <i class="fa fa-trash-o"></i>
                            <span>Delete</span>
                        </button>
                        <input type="checkbox" name="delete" value="1" class="toggle">
                    {% } else { %}
                        <button class="btn yellow cancel btn-sm">
                            <i class="fa fa-ban"></i>
                            <span>Cancel</span>
                        </button>
                    {% } %}
                </td>
            </tr>
        {% } %}
    </script>
    
    
    
    
   
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->

<!-- END PAGE LEVEL PLUGINS-->
<!-- BEGIN:File Upload Plugin JS files-->
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="../assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="../assets/global/plugins/jquery-file-upload/js/vendor/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="../assets/global/plugins/jquery-file-upload/js/vendor/load-image.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->

<!-- blueimp Gallery script -->
 <script src="../assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->

<!-- The basic File Upload plugin -->
<script src="../assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="../assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="../assets/global/plugins/jquery-file-upload/js/jquery.fileupload-image.js"></script>



<!-- The File Upload user interface plugin -->
<script src="../assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
    
    
    
    
    
    
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
	<!-- END PAGE LEVEL PLUGINS -->

<script type="text/javascript" src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<script type="text/javascript" src="../assets/global/plugins/bootstrap-markdown/js/bootstrap-markdown.js"></script>
<script type="text/javascript" src="../assets/global/plugins/bootstrap-markdown/lib/markdown.js"></script>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->


	<script src="../assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
	<!--<script src="../assets/admin/pages/scripts/form-wizard.js"></script>-->
	<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>	
	<script src="../assets/admin/pages/scripts/form-fileupload.js"></script>
	<script src="../static/js/addgoods.js"></script>
	<script src="../assets/admin/pages/scripts/form-validation.js"></script>

	<script>

	jQuery(document).ready(function() {       

	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout	

	   //Demo.init(); // init demo features
	 
	   Addgoods.init("<c:url value="/"/>");	
	   FormFileUpload.init();
//	   FormValidation.init();
	});

	</script>

</body>

<!-- END BODY -->


</html>