<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title><s:message code="store.setting.title" /></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${pageContext.request.contextPath}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css" />
<!-- END PAGE LEVEL STYLES -->

<!-- BEGIN THEME STYLES -->
<link href="${pageContext.request.contextPath}/assets/global/css/components.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="${pageContext.request.contextPath}/assets/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/media/image/favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
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
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<div class="page-bar">
					<%--<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a
							href="<c:url value="/"/>home"><s:message code="home" /></a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="<c:url value="/"/>order"><s:message code="store.setting.title"/></a></li>

					</ul>--%>
				</div>
				<!-- END PAGE TITLE & BREADCRUMB-->
				<div id="spin" class="display-hide"></div>
				<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<div class="col-md-12">

						<div class="col-md-6">
							<c:if test="${status==1}">
								<button id="enable" class="btn blue">编辑配置</button>
							</c:if>
						</div>
						<div class="col-md-6">
							<c:if test="${role==1}">
								<%-- <form action="search" method="post"> --%>
								<select name="storeId" style="width: 40%; height: 30px"
									id="storeId">
									<c:if test="${not empty stores}">
										<c:forEach var="store" items="${stores }">
											<option value="${store.storeId}">${store.storeName}</option>
										</c:forEach>
									</c:if>
								</select>
								<button id="search" class="btn blue">Search Store</button>
								<!-- <input type="submit" class="btn blue" value="Search Store"> -->
								<%-- </form> --%>
							</c:if>
						</div>
						<hr>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i>
									<s:message code="storesetting" />
								</div>
							</div>
							<div class="portlet-body">
								<table id="store_setting"
									class="table table-bordered table-striped">
									<tbody>
										<tr>
											<td style="width: 15%"><s:message code="restaurant.name" /></td>
											<td style="width: 50%"><a href="#" id="restaurant_name"
												data-type="text" data-pk="1"
												data-original-title="Enter Restaurant Name">
													${store.storeName} </a></td>
											<%-- <td style="width: 35%"><span class="text-muted" id="storeName">
													${store.storeName} </span></td> --%>
										</tr>
										<tr>
											<td style="width: 15%">门店编号</td>
											<td style="width: 50%">${store_code}</td>
											<%-- <td style="width: 35%"><span class="text-muted" id="publicKey">
													${store.publicKey} </span></td> --%>
										</tr>
										<tr>
											<td style="width: 15%"><s:message code="access.password" /></td>
											<td style="width: 50%"><a href="#" id="password"
												data-type="text" data-pk="1"
												data-original-title="Enter Access Password">
													${store.publicKey}</a>
												<div class="clearfix margin-top-10">
													<span class="label label-danger"> NOTE! </span> <span>
														用于客户端的访问密码 </span>
												</div></td>
											<%-- <td style="width: 35%"><span class="text-muted" id="publicKey">
													${store.publicKey} </span></td> --%>
										</tr>
										<tr>
											<td style="width: 15%"><s:message
													code="language.setting" /></td>
											<td style="width: 50%"><c:if test="${not empty langs}">
											<div class="checkbox-list">
													<c:forEach var="lan" items="${langs }">
														<c:if test="${langIds.contains(lan.idStr)==true}">
															<label class="checkbox-inline">
															<input type="checkbox" name="storeLangIds" value="${lan.id}" checked />${lan.name}
														    </label>
														</c:if>
														<c:if test="${langIds.contains(lan.idStr)==false}">
															<label class="checkbox-inline">
															<input type="checkbox" name="storeLangIds"
																value="${lan.id}" />${lan.name}
														    </label>
														</c:if>
													</c:forEach>
													<c:if test="${status==1}">
														&nbsp;&nbsp;<input type="button" value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" class="btn green btn-xs" id="editLan"/>
													</c:if>
												</c:if>
												</div>
											</td>
											<%-- <td style="width: 35%"><span class="text-muted" id="lanMsg" >
													Language Setting</span></td> --%>
										</tr>
										<tr>
											<td><s:message code="currency" /></td>
											<td><a href="#" id="currency" data-type="select2"
												data-value="${store.storeCurrency}" data-pk="1"
												data-original-title="Select Currency"></a></td>
											<%-- <td><span class="text-muted" id="storeCurrency">  ${store.storeCurrency}
													</span></td> --%>
										</tr>
										<tr>
											<td><s:message code="print" /></td>
											<td><input type="hidden" id="printV" value="${store.printType }"> 
												<div class="radio-list">												  
												  <label class="radio-inline"> <input type="radio" name="print_type" value="1" /><s:message code="print.one" /></label>
												  <label class="radio-inline"> <input type="radio" name="print_type" value="2" /><s:message code="print.two" /></label>
												  <c:if test="${status==1}">
														&nbsp;&nbsp;<input type="button" value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" class="btn green btn-xs" id="editPrint"/>
													</c:if>
												</div>  
											</td>
											
											<%-- <td><span class="text-muted" id="printType">  
											<c:if test="${store.printType eq 1 }">
												<s:message code="print.one"/>
											</c:if>
											<c:if test="${store.printType eq 2 }">
												<s:message code="print.two"/>
											</c:if>
													</span></td> --%>
										</tr>
										<tr>
											<td><s:message code="restaurant.logo" /></td>
											<td><input type="hidden" value="${store.storeId}"
												id="store_id">
												<div class="col-sm-9">
													<form action="" role="form" enctype="multipart/form-data"
														method="post" id="logo_change">
														<div class="form-group">
															<div class="fileinput fileinput-new"
																data-provides="fileinput">
																<div class="fileinput-new thumbnail"
																	style="width: 200px; height: 150px;">
																	<img src="<c:url value="/"/>${store.logoPath}" alt="" />
																</div>
																<div
																	class="fileinput-preview fileinput-exists thumbnail"
																	style="max-width: 200px; max-height: 200px;"></div>
																<div>
																	<c:if test="${status==1}">
																		<span class="btn default btn-file"> 
																		<span class="fileinput-new"> Select image </span> 
																		<span class="fileinput-exists"> Change </span> 
																		<input type="file" name="images" accept="image/*" id="logo_image">
																		</span>
																		<a href="#" class="btn default fileinput-exists"
																			data-dismiss="fileinput"> Remove </a>
																		<div class="clearfix margin-top-10">
																			<span class="label label-danger"> NOTE! </span> <span>
																				<s:message code="userprofile.changeavate.note" />
																			</span>
																		</div>

																		<div class="margin-top-10">
																			<input type="submit"
																				class="btn green fileinput-exists" value="Confirm"
																				class="form-control" />
																		</div>
																	</c:if>
																</div>

															</div>


														</div>
													</form>
												</div></td>
											<%-- <td><span class="text-muted">Store Logo Image
													</span></td> --%>
										</tr>
										<tr>
											<td><s:message code="page.background" /></td>
											<td>
												<div class="col-sm-9">
													<form action="" role="form" enctype="multipart/form-data"
														method="post" id="background_change">
														<div class="form-group">
															<div class="fileinput fileinput-new"
																data-provides="fileinput">
																<div class="fileinput-new thumbnail"
																	style="width: 200px; height: 200px;">
																	<img src="<c:url value="/"/>${store.backgroundPath}"
																		alt="" />
																</div>
																<div
																	class="fileinput-preview fileinput-exists thumbnail"
																	style="max-width: 200px; max-height: 150px;"></div>
																<div>
																	<c:if test="${status==1}">
																		<span class="btn default btn-file"> 
																		<span class="fileinput-new"> Select image </span> 
																		<span class="fileinput-exists"> Change </span> 
																		<input type="file" name="images" accept="image/*" id="back_image">
																		</span>
																		<a href="#" class="btn default fileinput-exists"
																			data-dismiss="fileinput"> Remove </a>
																		<div class="clearfix margin-top-10">
																			<span class="label label-danger"> NOTE! </span> <span>
																				<s:message code="userprofile.changeavate.note" />
																			</span>
																		</div>
																		<div class="margin-top-10">
																			<input type="submit"
																				class="btn green fileinput-exists" value="Confirm"
																				class="form-control" />
																		</div>
																	</c:if>
																</div>
															</div>
														</div>
													</form>
												</div>
											</td>
											<%-- <td><span class="text-muted"> Store Background Image</span></td> --%>
										</tr>
									</tbody>
								</table>
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
	<script src="${pageContext.request.contextPath}/assets/global/plugins/respond.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/excanvas.min.js"></script> 
	<![endif]-->
	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->

	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${pageContext.request.contextPath}/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/moment.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/jquery.mockjax.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-editable/inputs-ext/wysihtml5/wysihtml5.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js"
		type="text/javascript"></script>
	<script src="../media/js/ajaxfileupload.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->


	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${pageContext.request.contextPath}/assets/global/plugins/json/json2.js"
		type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/global/scripts/metronic.js"
		type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/assets/admin/layout/scripts/layout.js"
		type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/js/storeSetting.js"></script>
	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout	
			StoreSetting.init("<c:url value="/"/>");
		});
	</script>
	<c:import url="/common/notice" />
</body>
<!-- END BODY -->

</html>