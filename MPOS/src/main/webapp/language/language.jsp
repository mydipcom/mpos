<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->

<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->

<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->

<!-- BEGIN HEAD -->

<head>

<meta charset="utf-8"/>

<title><s:message code="language.title"/></title>

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

				<!-- END PAGE TITLE & BREADCRUMB-->

					

				<!-- BEGIN SEARCH FORM -->
				<form id="searchForm" name="searchForm" action="rightslist" class="form-horizontal" method="post">
				
				

			</form>

				<!-- END SEARCH FORM -->
				<!-- END PAGE CONTENT -->

			
						 
					<div class="row" >
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet  box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-edit"></i><s:message code="language.title"></s:message>
								</div>
								<div class="actions">									
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#add_language"><i class="fa fa-plus"></i> <s:message code="all.table.add" /></a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#edit_language" id="openEditlanguageModal" ><i class="fa fa-pencil"></i> <s:message code="all.table.edit" /></a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#active_language" id="openActivelanguageModal"><i class="fa fa-key"></i> <s:message code="all.table.activate"/></a>
								    <a class="btn btn-default btn-sm" data-toggle="modal" href="#delete_language" id="openDeletelanguageModal"><i class="fa fa-trash-o"></i> <s:message code="all.table.deactivate" /></a>
								    <%-- <div class="btn-group">
										<a class="btn default" href="#" data-toggle="dropdown">
										<s:message code="system.column"/> <i class="fa fa-angle-down"></i>
										</a>
										<div id="column_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
											<label><input type="checkbox" checked data-column="0"><s:message code="system.checkbox"/></label>
											<label><input type="checkbox" checked data-column="1"><s:message code="language.id"/></label>
											<label><input type="checkbox" checked data-column="2"><s:message code="language.name"/></label>
											<label><input type="checkbox" checked data-column="3"><s:message code="language.local"/></label>
											<label><input type="checkbox" checked data-column="4"><s:message code="language.flagimage"/></label>
											<label><input type="checkbox" checked data-column="5"><s:message code="language.status"/></label>
											<label><input type="checkbox" checked data-column="6"><s:message code="language.sort"/></label>
										</div>
									</div> --%>								    																
								</div>
							</div>							
							<div class="portlet-body">																
								<table class="table table-striped table-hover table-bordered" id="languages_table">
									<thead>
										<tr>
											<th class="table-checkbox">
												<input type="checkbox" class="group-checkable" data-set="#languages_table .checkboxes"/>
											</th>
											<%-- <th><s:message code="language.id"/></th> --%>
											<th><s:message code="language.name"/></th>
											<th><s:message code="language.local"/></th>
											<th><s:message code="language.flagimage"/></th>
											<th><s:message code="language.status"/></th>
											<th><s:message code="language.sort"/></th>
										</tr>
										</thead>
							<!--
										<tr>
										<td class="table-checkbox">
												<input type="checkbox" class="group-checkable" data-set="#adminusers_table .checkboxes"/>
										</td>
											<td>1</td>
											<td>language</td>
											<td>zh_CN</td>
											<td>test</td>
											<td>true</td>
											<td>0</td>
										</tr>	
							  -->																
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT --> 
				<div class="modal" id="add_language" tabindex="-1" data-width="760">
					<div class="modal-header">
						<button id="closeAddModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title"><s:message code="language.add.title"/></h4>
					</div>
					<div id="addFormMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="addLanguagesForm" action="" method="post" name="addLanguagesForm" class="form-horizontal form-bordered">
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									<s:message code="system.management.user.adduser.message"/>
								</div>								
								<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.name"/><span class="required"> * </span></label>
									<div class="col-md-9">										
										<input name="name" class="form-control" maxLength="20"/>										
									</div>
								</div>
								<div class="form-group">
								<label class="control-label col-md-3"><s:message code="language.flagimage"/><span class="required"> * </span></label>
									<div class="col-md-9">
										<select name="flagImage" id="country_list" class="form-control">
															<option value=""></option>
															<option value="tt_AF">Afghanistan</option>
															<option value="sq_AL">Albania</option>
															<option value="ar_DZ">Algeria</option>
															<option value="es_AR">Argentina</option>
															<option value="sv_SE">Sweden</option>
															<option value="fr_CH">Switzerland</option>
															<option value="ar_SY">Syrian Arab Republic</option>
															<option value="zh_TW">Taiwan, Province of China</option>
															<option value="en_AU">Australia</option>
															<option value="de_AT">Austria</option>
															<option value="en_AM">Armenia</option>
															<option value="en_BS">Bahamas</option>
															<option value="ar_BH">Bahrain</option>
															<option value="be_BY">Belarus</option>
															<option value="fr_BE">Belgium</option>
															<option value="pt_BR">Brazil</option>
															<option value="bg_BG">Bulgaria</option>
															<option value="en_CA">Canada</option>
															<option value="es_CL">Chile</option>
															<option value="zh_CN">China</option>
															<option value="es_CO">Colombia</option>
															<option value="cs_CZ">Czech Republic</option>
															<option value="da_DK">Denmark</option>
															<option value="es_DO">Dominican Republic</option>
															<option value="es_EC">Ecuador</option>
															<option value="ar_EG">Egypt</option>
															<option value="fi_FI">Finland</option>
															<option value="fr_FR">France</option>
															<option value="de_DE">Germany</option>
															<option value="et_EE">Estonia</option>
															<option value="el_GR">Greece</option>
															<option value="es_HN">Honduras</option>
															<option value="en_NZ">New Zealand</option>
															<option value="zh_HK">Hong Kong</option>
															<option value="hu_HU">Hungary</option>
															<option value="is_IS">Iceland</option>
															<option value="ar_IQ">Iraq</option>
															<option value="en_IE">Ireland</option>
															<option value="iw_IL">Israel</option>
															<option value="en_ZA">South Africa</option>
															<option value="sk_SK">Slovakia (Slovak Republic)</option>
															<option value="sl_SI">Slovenia</option>
															<option value="it_IT">Italy</option>
															<option value="es_GT">Guatemala</option>
															<option value="ja_JP">Japan</option>
															<option value="ar_JO">Jordan</option>
															<option value="ko_KR">Korea, Republic of</option>
															<option value="ar_KW">Kuwait</option>
															<option value="lv_LV">Latvia</option>
															<option value="no_NO">Norway</option>
															<option value="ar_OM">Oman</option>
															<option value="ar_LB">Lebanon</option>
															<option value="pl_PL">Poland</option>
															<option value="pt_PT">Portugal</option>
															<option value="es_PR">Puerto Rico</option>
															<option value="ar_QA">Qatar</option>
															<option value="ar_SD">Sudan</option>
															<option value="th_TH">Thailand</option>
															<option value="ar_TN">Tunisia</option>
															<option value="tr_TR">Turkey</option>
															<option value="uk_UA">Ukraine</option>
															<option value="ar_YE">Yemen</option>
															<option value="en_US">United States</option>
															<option value="es_UY">Uruguay</option>
															<option value="es_VE">Venezuela</option>
															<option value="ar_AE">United Arab Emirates</option>
															<option value="es_PY">Paraguay</option>
															<option value="es_PE">Peru</option>
															<option value="nl_NL">Netherlands</option>
															<option value="lt_LT">Lithuania</option>
															<option value="de_LU">Luxembourg</option>
															<option value="es_MX">Mexico</option>
															<option value="ar_MA ">Morocco</option>
															<option value="es_PA">Panama</option>
															<option value="ar_SA">Saudi Arabia</option>
															<option value="ar_LY">Libyan Arab Jamahiriya</option>
															<option value="mk_MK">Macedonia, The Former Yugoslav Republic of</option>
													<!--  	<option value="AS">American Samoa</option>
															<option value="AD">Andorra</option>
															<option value="AO">Angola</option>
															<option value="AI">Anguilla</option>
															<option value="AQ">Antarctica</option>
															<option value="AW">Aruba</option>
															<option value="AZ">Azerbaijan</option>
															<option value="BD">Bangladesh</option>
															<option value="BB">Barbados</option>
															<option value="BZ">Belize</option>
															<option value="BJ">Benin</option>
															<option value="BM">Bermuda</option>
															<option value="BT">Bhutan</option>
															<option value="es_BO">Bolivia</option>
															<option value="BA">Bosnia and Herzegowina</option>
															<option value="BW">Botswana</option>
															<option value="BV">Bouvet Island</option>
															<option value="IO">British Indian Ocean Territory</option>
															<option value="BN">Brunei Darussalam</option>
															<option value="BF">Burkina Faso</option>
															<option value="BI">Burundi</option>
															<option value="KH">Cambodia</option>
															<option value="CM">Cameroon</option>
															<option value="CV">Cape Verde</option>
															<option value="KY">Cayman Islands</option>
															<option value="CF">Central African Republic</option>
															<option value="TD">Chad</option>
															<option value="CX">Christmas Island</option>
															<option value="CC">Cocos (Keeling) Islands</option>
															<option value="KM">Comoros</option>
															<option value="CG">Congo</option>
															<option value="CD">Congo, the Democratic Republic of the</option>
															<option value="CK">Cook Islands</option>
															<option value="CR">Costa Rica</option>
															<option value="CI">Cote d'Ivoire</option>
															<option value="HR">Croatia (Hrvatska)</option>
															<option value="CU">Cuba</option>
															<option value="CY">Cyprus</option>
															<option value="DJ">Djibouti</option>
															<option value="DM">Dominica</option>
															<option value="SV">El Salvador</option>
															<option value="GQ">Equatorial Guinea</option>
															<option value="ER">Eritrea</option>
															<option value="ET">Ethiopia</option>
															<option value="FK">Falkland Islands (Malvinas)</option>
															<option value="FO">Faroe Islands</option>
															<option value="FJ">Fiji</option>
															<option value="GF">French Guiana</option>
															<option value="PF">French Polynesia</option>
															<option value="TF">French Southern Territories</option>
															<option value="GA">Gabon</option>
															<option value="GM">Gambia</option>
															<option value="GE">Georgia</option>
															<option value="GH">Ghana</option>
															<option value="GI">Gibraltar</option>
															<option value="GL">Greenland</option>
															<option value="GD">Grenada</option>
															<option value="GP">Guadeloupe</option>
															<option value="GU">Guam</option>
															<option value="GN">Guinea</option>
															<option value="GW">Guinea-Bissau</option>
															<option value="GY">Guyana</option>
															<option value="HT">Haiti</option>
															<option value="HM">Heard and Mc Donald Islands</option>
															<option value="VA">Holy See (Vatican City State)</option>
															<option value="IN">India</option>
															<option value="ID">Indonesia</option>
															<option value="IR">Iran (Islamic Republic of)</option>
															<option value="JM">Jamaica</option>
															<option value="KZ">Kazakhstan</option>
															<option value="KE">Kenya</option>
															<option value="KI">Kiribati</option>
															<option value="KP">Korea, Democratic People's Republic of</option>
															<option value="KG">Kyrgyzstan</option>
															<option value="LA">Lao People's Democratic Republic</option>
															<option value="LS">Lesotho</option>
															<option value="LR">Liberia</option>
															<option value="LI">Liechtenstein</option>
															<option value="MO">Macau</option>
															<option value="MG">Madagascar</option>
															<option value="MW">Malawi</option>
															<option value="MY">Malaysia</option>
															<option value="MV">Maldives</option>
															<option value="ML">Mali</option>
															<option value="MT">Malta</option>
															<option value="MH">Marshall Islands</option>
															<option value="MQ">Martinique</option>
															<option value="MR">Mauritania</option>
															<option value="MU">Mauritius</option>
															<option value="YT">Mayotte</option>
															<option value="FM">Micronesia, Federated States of</option>
															<option value="MD">Moldova, Republic of</option>
															<option value="MC">Monaco</option>
															<option value="MN">Mongolia</option>
															<option value="MS">Montserrat</option>
															<option value="MZ">Mozambique</option>
															<option value="MM">Myanmar</option>
															<option value="NA">Namibia</option>
															<option value="NR">Nauru</option>
															<option value="NP">Nepal</option>
															<option value="AN">Netherlands Antilles</option>
															<option value="NC">New Caledonia</option>
															<option value="NI">Nicaragua</option>
															<option value="NE">Niger</option>
															<option value="NG">Nigeria</option>
															<option value="NU">Niue</option>
															<option value="NF">Norfolk Island</option>
															<option value="MP">Northern Mariana Islands</option>
															<option value="PK">Pakistan</option>
															<option value="PW">Palau</option>
															<option value="PG">Papua New Guinea</option>
															<option value="PH">Philippines</option>
															<option value="PN">Pitcairn</option>
															<option value="RE">Reunion</option>
															<option value="ro_RO">Romania</option>
															<option value="ru_RU">Russian Federation</option>
															<option value="RW">Rwanda</option>
															<option value="KN">Saint Kitts and Nevis</option>
															<option value="LC">Saint LUCIA</option>
															<option value="VC">Saint Vincent and the Grenadines</option>
															<option value="WS">Samoa</option>
															<option value="SM">San Marino</option>
															<option value="ST">Sao Tome and Principe</option>
															<option value="SN">Senegal</option>
															<option value="SC">Seychelles</option>
															<option value="SL">Sierra Leone</option>
															<option value="SG">Singapore</option>
															<option value="SB">Solomon Islands</option>
															<option value="SO">Somalia</option>
															<option value="GS">South Georgia and the South Sandwich Islands</option>
															<option value="ES">Spain</option>
															<option value="LK">Sri Lanka</option>
															<option value="SH">St. Helena</option>
															<option value="PM">St. Pierre and Miquelon</option>
															<option value="SR">Suriname</option>
															<option value="SJ">Svalbard and Jan Mayen Islands</option>
															<option value="SZ">Swaziland</option>
															<option value="TJ">Tajikistan</option>
															<option value="TZ">Tanzania, United Republic of</option>
															<option value="TG">Togo</option>
															<option value="TK">Tokelau</option>
															<option value="TO">Tonga</option>
															<option value="TT">Trinidad and Tobago</option>
															<option value="TM">Turkmenistan</option>
															<option value="TC">Turks and Caicos Islands</option>
															<option value="TV">Tuvalu</option>
															<option value="UG">Uganda</option>
															<option value="GB">United Kingdom</option>
															<option value="UM">United States Minor Outlying Islands</option>
															<option value="UZ">Uzbekistan</option>
															<option value="VU">Vanuatu</option>
															<option value="VN">Viet Nam</option>
															<option value="VG">Virgin Islands (British)</option>
															<option value="VI">Virgin Islands (U.S.)</option>
															<option value="WF">Wallis and Futuna Islands</option>
															<option value="EH">Western Sahara</option>
															<option value="ZM">Zambia</option>
															<option value="ZW">Zimbabwe</option>-->
														</select>
													</div>
												</div>
									<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.local"/><span class="required"> * </span></label>
									<div class="col-md-9">										
										<input name="local"  class="form-control" readonly="ture"/>										
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.sort"/><span class="required">* </span></label>
									<div class="col-md-9">																				
										<input name="sort" class="form-control"/>
									</div>
								</div>								
						
								<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.sort"/><span class="required">* </span></label>
									<div class="col-md-9">										
										<div class="radio-list">
											<label>
											<input type="radio" name="status" value="1" checked/><s:message code="all.status.enable"/></label>
											<label>
											<input type="radio" name="status" value="0"/><s:message code="all.status.disable"/> </label>
										</div>
									</div>
								</div>			 
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div class="col-md-offset-6 col-md-6">
										<button type="submit" class="btn green" id="addFormSubmit"><i class="fa fa-check"></i> <s:message code="system.submit"/> </button>
										<button type="button" class="btn btn-default" data-dismiss="modal"><s:message code="system.close"/></button>
									</div>
								</div>
							</div>
							</div>
						</form>
							
						<!-- END FORM-->
					</div>					
				</div>
				
				<!-- END PAGE CONTENT --> 
				<div class="modal" id="edit_language" tabindex="-1" data-width="760">
					<div class="modal-header">
						<button id="closeAddModal" type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title"><s:message code="language.edit.title"/></h4>
					</div>
					<div id="addFormMsg"></div>
					<!-- <div class="modal-body"> -->
					<div class="portlet-body form">
						<!-- BEGIN FORM	-->					
						<form id="editLanguagesForm" action="" method="post" name="editLanguagesForm" class="form-horizontal form-bordered">
							<div class="form-body">
								<div class="alert alert-danger display-hide">
									<button class="close" data-close="alert"></button>
									<s:message code="system.management.user.adduser.message"/>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.id"/><span class="required"> * </span></label>
									<div class="col-md-9">										
										<input name="id" class="form-control"  readonly="ture"/>										
									</div>
								</div>							
								<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.name"/><span class="required"> * </span></label>
									<div class="col-md-9">										
										<input name="name" class="form-control" maxLength="20"/>										
									</div>
								</div>
								<div class="form-group">
								<label class="control-label col-md-3"><s:message code="language.flagimage"/></label>
									<div class="col-md-9">
										<select name="flagImage" id="country_list2" class="form-control">
															<option value=""></option>
															<option value="tt_AF">Afghanistan</option>
															<option value="sq_AL">Albania</option>
															<option value="ar_DZ">Algeria</option>
															<option value="es_AR">Argentina</option>
															<option value="sv_SE">Sweden</option>
															<option value="fr_CH">Switzerland</option>
															<option value="ar_SY">Syrian Arab Republic</option>
															<option value="zh_TW">Taiwan, Province of China</option>
															<option value="en_AU">Australia</option>
															<option value="de_AT">Austria</option>
															<option value="en_AM">Armenia</option>
															<option value="en_BS">Bahamas</option>
															<option value="ar_BH">Bahrain</option>
															<option value="be_BY">Belarus</option>
															<option value="fr_BE">Belgium</option>
															<option value="pt_BR">Brazil</option>
															<option value="bg_BG">Bulgaria</option>
															<option value="en_CA">Canada</option>
															<option value="es_CL">Chile</option>
															<option value="zh_CN">China</option>
															<option value="es_CO">Colombia</option>
															<option value="cs_CZ">Czech Republic</option>
															<option value="da_DK">Denmark</option>
															<option value="es_DO">Dominican Republic</option>
															<option value="es_EC">Ecuador</option>
															<option value="ar_EG">Egypt</option>
															<option value="fi_FI">Finland</option>
															<option value="fr_FR">France</option>
															<option value="de_DE">Germany</option>
															<option value="et_EE">Estonia</option>
															<option value="el_GR">Greece</option>
															<option value="es_HN">Honduras</option>
															<option value="en_NZ">New Zealand</option>
															<option value="zh_HK">Hong Kong</option>
															<option value="hu_HU">Hungary</option>
															<option value="is_IS">Iceland</option>
															<option value="ar_IQ">Iraq</option>
															<option value="en_IE">Ireland</option>
															<option value="iw_IL">Israel</option>
															<option value="en_ZA">South Africa</option>
															<option value="sk_SK">Slovakia (Slovak Republic)</option>
															<option value="sl_SI">Slovenia</option>
															<option value="it_IT">Italy</option>
															<option value="es_GT">Guatemala</option>
															<option value="ja_JP">Japan</option>
															<option value="ar_JO">Jordan</option>
															<option value="ko_KR">Korea, Republic of</option>
															<option value="ar_KW">Kuwait</option>
															<option value="lv_LV">Latvia</option>
															<option value="no_NO">Norway</option>
															<option value="ar_OM">Oman</option>
															<option value="ar_LB">Lebanon</option>
															<option value="pl_PL">Poland</option>
															<option value="pt_PT">Portugal</option>
															<option value="es_PR">Puerto Rico</option>
															<option value="ar_QA">Qatar</option>
															<option value="ar_SD">Sudan</option>
															<option value="th_TH">Thailand</option>
															<option value="ar_TN">Tunisia</option>
															<option value="tr_TR">Turkey</option>
															<option value="uk_UA">Ukraine</option>
															<option value="ar_YE">Yemen</option>
															<option value="en_US">United States</option>
															<option value="es_UY">Uruguay</option>
															<option value="es_VE">Venezuela</option>
															<option value="ar_AE">United Arab Emirates</option>
															<option value="es_PY">Paraguay</option>
															<option value="es_PE">Peru</option>
															<option value="nl_NL">Netherlands</option>
															<option value="lt_LT">Lithuania</option>
															<option value="de_LU">Luxembourg</option>
															<option value="es_MX">Mexico</option>
															<option value="ar_MA ">Morocco</option>
															<option value="es_PA">Panama</option>
															<option value="ar_SA">Saudi Arabia</option>
															<option value="ar_LY">Libyan Arab Jamahiriya</option>
															<option value="mk_MK">Macedonia, The Former Yugoslav Republic of</option>
													<!--  	<option value="AS">American Samoa</option>
															<option value="AD">Andorra</option>
															<option value="AO">Angola</option>
															<option value="AI">Anguilla</option>
															<option value="AQ">Antarctica</option>
															<option value="AW">Aruba</option>
															<option value="AZ">Azerbaijan</option>
															<option value="BD">Bangladesh</option>
															<option value="BB">Barbados</option>
															<option value="BZ">Belize</option>
															<option value="BJ">Benin</option>
															<option value="BM">Bermuda</option>
															<option value="BT">Bhutan</option>
															<option value="es_BO">Bolivia</option>
															<option value="BA">Bosnia and Herzegowina</option>
															<option value="BW">Botswana</option>
															<option value="BV">Bouvet Island</option>
															<option value="IO">British Indian Ocean Territory</option>
															<option value="BN">Brunei Darussalam</option>
															<option value="BF">Burkina Faso</option>
															<option value="BI">Burundi</option>
															<option value="KH">Cambodia</option>
															<option value="CM">Cameroon</option>
															<option value="CV">Cape Verde</option>
															<option value="KY">Cayman Islands</option>
															<option value="CF">Central African Republic</option>
															<option value="TD">Chad</option>
															<option value="CX">Christmas Island</option>
															<option value="CC">Cocos (Keeling) Islands</option>
															<option value="KM">Comoros</option>
															<option value="CG">Congo</option>
															<option value="CD">Congo, the Democratic Republic of the</option>
															<option value="CK">Cook Islands</option>
															<option value="CR">Costa Rica</option>
															<option value="CI">Cote d'Ivoire</option>
															<option value="HR">Croatia (Hrvatska)</option>
															<option value="CU">Cuba</option>
															<option value="CY">Cyprus</option>
															<option value="DJ">Djibouti</option>
															<option value="DM">Dominica</option>
															<option value="SV">El Salvador</option>
															<option value="GQ">Equatorial Guinea</option>
															<option value="ER">Eritrea</option>
															<option value="ET">Ethiopia</option>
															<option value="FK">Falkland Islands (Malvinas)</option>
															<option value="FO">Faroe Islands</option>
															<option value="FJ">Fiji</option>
															<option value="GF">French Guiana</option>
															<option value="PF">French Polynesia</option>
															<option value="TF">French Southern Territories</option>
															<option value="GA">Gabon</option>
															<option value="GM">Gambia</option>
															<option value="GE">Georgia</option>
															<option value="GH">Ghana</option>
															<option value="GI">Gibraltar</option>
															<option value="GL">Greenland</option>
															<option value="GD">Grenada</option>
															<option value="GP">Guadeloupe</option>
															<option value="GU">Guam</option>
															<option value="GN">Guinea</option>
															<option value="GW">Guinea-Bissau</option>
															<option value="GY">Guyana</option>
															<option value="HT">Haiti</option>
															<option value="HM">Heard and Mc Donald Islands</option>
															<option value="VA">Holy See (Vatican City State)</option>
															<option value="IN">India</option>
															<option value="ID">Indonesia</option>
															<option value="IR">Iran (Islamic Republic of)</option>
															<option value="JM">Jamaica</option>
															<option value="KZ">Kazakhstan</option>
															<option value="KE">Kenya</option>
															<option value="KI">Kiribati</option>
															<option value="KP">Korea, Democratic People's Republic of</option>
															<option value="KG">Kyrgyzstan</option>
															<option value="LA">Lao People's Democratic Republic</option>
															<option value="LS">Lesotho</option>
															<option value="LR">Liberia</option>
															<option value="LI">Liechtenstein</option>
															<option value="MO">Macau</option>
															<option value="MG">Madagascar</option>
															<option value="MW">Malawi</option>
															<option value="MY">Malaysia</option>
															<option value="MV">Maldives</option>
															<option value="ML">Mali</option>
															<option value="MT">Malta</option>
															<option value="MH">Marshall Islands</option>
															<option value="MQ">Martinique</option>
															<option value="MR">Mauritania</option>
															<option value="MU">Mauritius</option>
															<option value="YT">Mayotte</option>
															<option value="FM">Micronesia, Federated States of</option>
															<option value="MD">Moldova, Republic of</option>
															<option value="MC">Monaco</option>
															<option value="MN">Mongolia</option>
															<option value="MS">Montserrat</option>
															<option value="MZ">Mozambique</option>
															<option value="MM">Myanmar</option>
															<option value="NA">Namibia</option>
															<option value="NR">Nauru</option>
															<option value="NP">Nepal</option>
															<option value="AN">Netherlands Antilles</option>
															<option value="NC">New Caledonia</option>
															<option value="NI">Nicaragua</option>
															<option value="NE">Niger</option>
															<option value="NG">Nigeria</option>
															<option value="NU">Niue</option>
															<option value="NF">Norfolk Island</option>
															<option value="MP">Northern Mariana Islands</option>
															<option value="PK">Pakistan</option>
															<option value="PW">Palau</option>
															<option value="PG">Papua New Guinea</option>
															<option value="PH">Philippines</option>
															<option value="PN">Pitcairn</option>
															<option value="RE">Reunion</option>
															<option value="ro_RO">Romania</option>
															<option value="ru_RU">Russian Federation</option>
															<option value="RW">Rwanda</option>
															<option value="KN">Saint Kitts and Nevis</option>
															<option value="LC">Saint LUCIA</option>
															<option value="VC">Saint Vincent and the Grenadines</option>
															<option value="WS">Samoa</option>
															<option value="SM">San Marino</option>
															<option value="ST">Sao Tome and Principe</option>
															<option value="SN">Senegal</option>
															<option value="SC">Seychelles</option>
															<option value="SL">Sierra Leone</option>
															<option value="SG">Singapore</option>
															<option value="SB">Solomon Islands</option>
															<option value="SO">Somalia</option>
															<option value="GS">South Georgia and the South Sandwich Islands</option>
															<option value="ES">Spain</option>
															<option value="LK">Sri Lanka</option>
															<option value="SH">St. Helena</option>
															<option value="PM">St. Pierre and Miquelon</option>
															<option value="SR">Suriname</option>
															<option value="SJ">Svalbard and Jan Mayen Islands</option>
															<option value="SZ">Swaziland</option>
															<option value="TJ">Tajikistan</option>
															<option value="TZ">Tanzania, United Republic of</option>
															<option value="TG">Togo</option>
															<option value="TK">Tokelau</option>
															<option value="TO">Tonga</option>
															<option value="TT">Trinidad and Tobago</option>
															<option value="TM">Turkmenistan</option>
															<option value="TC">Turks and Caicos Islands</option>
															<option value="TV">Tuvalu</option>
															<option value="UG">Uganda</option>
															<option value="GB">United Kingdom</option>
															<option value="UM">United States Minor Outlying Islands</option>
															<option value="UZ">Uzbekistan</option>
															<option value="VU">Vanuatu</option>
															<option value="VN">Viet Nam</option>
															<option value="VG">Virgin Islands (British)</option>
															<option value="VI">Virgin Islands (U.S.)</option>
															<option value="WF">Wallis and Futuna Islands</option>
															<option value="EH">Western Sahara</option>
															<option value="ZM">Zambia</option>
															<option value="ZW">Zimbabwe</option>-->
														</select>
													</div>
												</div>
								<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.local"/><span class="required"> * </span></label>
									<div class="col-md-9">										
										<input name="local"  class="form-control" readonly="ture"/>										
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.sort"/><span class="required">* </span></label>
									<div class="col-md-9">																				
										<input name="sort" class="form-control"/>
									</div>
								</div>								
						
								<div class="form-group">
									<label class="control-label col-md-3"><s:message code="language.status"/><span class="required">* </span></label>
									<div class="col-md-9">										
										<div class="radio-list">
											<label>
											<input type="radio" name="status" value="1" checked/><s:message code="all.status.enable"/> </label>
											<label>
											<input type="radio" name="status" value="0"/><s:message code="all.status.disable"/> </label>
										</div>
									</div>
								</div>			 
							<div class="form-actions" style="border-top:0;">
								<div class="row">
									<div class="col-md-offset-6 col-md-6">
										<button type="submit" class="btn green" id="editFormSubmit"><i class="fa fa-check"></i> <s:message code="system.submit"/></button>
										<button type="button" class="btn btn-default" data-dismiss="modal"><s:message code="system.close"/></button>
									</div>
								</div>
							</div>
							</div>
						</form>
							
						<!-- END FORM-->
					</div>					
				</div>
				
				<!-- BEGIN DELETE MODAL FORM-->
				<div class="modal" id="delete_language" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>
							 <s:message code="language.deactive.message" />
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">Cancel</button>
						<button id="deleteBtn" type="button" data-dismiss="modal" class="btn blue">Confirm</button>
					</div>					
				</div>				
				<!-- END DELETE MODAL FORM-->	
				
				<!-- BEGIN DELETE MODAL FORM-->
				<div class="modal" id="active_language" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<div class="modal-body">
						<p>
							 <s:message code="language.active.message" />
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default"><s:message code="system.close"/></button>
						<button id="activeBtn" type="button" data-dismiss="modal" class="btn blue"><s:message code="system.submit"/></button>
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
	<script type="text/javascript" src="../assets/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<script src="../assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js"></script>
	<script src="../assets/global/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<script src="../assets/global/plugins/jquery-i18n/jquery.i18n.properties-1.0.9.js" type="text/javascript"></script>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
	<script src="../assets/global/plugins/json/json2.js" type="text/javascript"></script>
	<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="../assets/admin/pages/scripts/form-wizard.js"></script>
	<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>	
<!--<script src="../static/js/rolesTableData.js"></script>  -->
	<script src="../static/js/common.js"></script>	
	<script src="../static/js/LanguageTableData.js"></script>
	<script>

	jQuery(document).ready(function() {       

	   Metronic.init(); // init metronic core components
	   Layout.init(); // init current layout	

	   //Demo.init(); // init demo features
		FormWizard.init();
		LanguagesTable.init("<c:url value="/"/>","${sessionScope.locale}");	   
	});

	</script>
<c:import url="/common/notice"/>
</body>

<!-- END BODY -->


</html>