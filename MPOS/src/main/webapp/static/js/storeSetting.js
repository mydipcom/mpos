//jquery插件把表单序列化成json格式的数据start 
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
})(jQuery);
var rootURI = "/";
var StoreSetting = function() {

	var initEditables = function() {

		// set editable mode based on URL parameter
		if (Metronic.getURLParameter('mode') == 'inline') {
			$.fn.editable.defaults.mode = 'inline';
			$('#inline').attr("checked", true);
			jQuery.uniform.update('#inline');
		} else {
			$('#inline').attr("checked", false);
			jQuery.uniform.update('#inline');
		}

		// global settings
		$.fn.editable.defaults.inputclass = 'form-control';
		$.fn.editable.defaults.url = '/post';

		$('#restaurant_name').editable({
			url : rootURI + "settings/editstoresetting?rand=" + Math.random(),
			type : 'text',
			pk : 1,
			name : 'Restaurant_Name',
			title : 'Enter Restaurant Name',
			success : function(data) {
				handleAlerts("modify the restaurant name of success.","success","");
			}
		});

		$('#password').editable({
			url : rootURI + "settings/editstoresetting?rand=" + Math.random(),
			type : 'text',
			pk : 1,
			name : 'Access_Password',
			title : 'Enter Password',
			success : function(value) {
				handleAlerts("modify the access password of success.","success","");
			}
		});

		$('#token').editable({
			url : rootURI + "settings/editstoresetting?rand=" + Math.random(),
			type : 'text',
			pk : 1,
			name : 'Token',
			title : 'Enter Token',
			success : function(value) {
				handleAlerts("modify the token of success.","success","");
			}
		});
		var currency = [];
		$.each({
			"HK$" : "HKD",
			"$" : "USD",
			"¥" : "MCY"
		}, function(k, v) {
			currency.push({
				id : k,
				text : v
			});
		});

		$('#currency').editable({
			url : rootURI + "settings/editstoresetting?rand=" + Math.random(),
			name : 'Currency',
			inputclass : 'form-control input-medium',
			source : currency,
			success : function(value) {
				handleAlerts("modify the currency of success.","success","");
			}
		});
		
		//修改商店背景
		$("#background_change").on("submit", function(event) {
			 $.ajaxFileUpload( {
	             "type": "POST", 
	             "url": rootURI+"settings/editstoreimage?flag=1&rand="+Math.random(), 
	             "secureuri": false,
	             "fileElementId":"backgroundimages", 
	             "dataType": "json",
//	             "processData":false,
//	             "contentType":"application/json",
	             "success": function(resp,status){
	            	 if(status == "success"){  
	            		 if(resp.status){
							 handleAlerts("modify the restaurant background successfully.","success","","10");
							 $('#background_change').html("<div class=\"form-group\"><div class=\"fileinput fileinput-new\" data-provides=\"fileinput\">" +
								 		"<div class=\"fileinput-new thumbnail\" style=\"width: 200px; height: 150px;\">"+
	                                     "<img src=\""+$('#background_change').find('img').attr("src")+"?rand="+Math.random()+"\" alt=\"\" /></div>"+
										 "<div class=\"fileinput-preview fileinput-exists thumbnail\" style=\"max-width: 200px; max-height: 150px;\"></div>"+
	                                     "<div><span class=\"btn default btn-file\"> <span class=\"fileinput-new\"> Select image </span>"+ 
	                                     "<span class=\"fileinput-exists\"> Change </span> <input type=\"file\" name=\"images\" accept=\"image/*\" id=\"backgroundimages\">"+
										 "</span> <a href=\"#\" class=\"btn default fileinput-exists\" data-dismiss=\"fileinput\"> Remove </a>"+
	                                     "<div class=\"clearfix margin-top-10\"> <span class=\"label label-danger\"> NOTE! </span> <span>"+$('#background_change').find('.clearfix').find('span:eq(1)').text()+
	                                     "</span></div><div class=\"margin-top-10\"><input type=\"submit\" class=\"btn green fileinput-exists\" value=\"Confirm\" class=\"form-control\"/></div></div></div></div>"
						                 );
			              }
						 else{
						     handleAlerts("modify the restaurant background fail.","danger","","10");
						 }
	            		 
					} 
	            	 
	             },
	             "error":function(XMLHttpRequest, textStatus, errorThrown){
	            	 alert(errorThrown);
	             }
	           });
			  return false;
		}); 
		
		//修改商店logo
		$("#logo_change").on("submit", function(event) {
			 $.ajaxFileUpload( {
	             "type": "POST", 
	             "url": rootURI+"settings/editstoreimage?flag=0&rand="+Math.random(), 
	             "secureuri": false,
	             "fileElementId":"logo_image", 
	             "dataType": "json",
//	             "processData":false,
//	             "contentType":"application/json",
	             "success": function(resp,status){
	            	 if(status == "success"){  
	            		 if(resp.status){
							 handleAlerts("modify the restaurant logo successfully.","success","","10");
							 $('#logo_change').html("<div class=\"form-group\"><div class=\"fileinput fileinput-new\" data-provides=\"fileinput\">" +
							 		"<div class=\"fileinput-new thumbnail\" style=\"width: 200px; height: 50px;\">"+
                                     "<img src=\""+$('#logo_change').find('img').attr("src")+"?rand="+Math.random()+"\" alt=\"\" /></div>"+
									 "<div class=\"fileinput-preview fileinput-exists thumbnail\" style=\"max-width: 200px; max-height: 150px;\"></div>"+
                                     "<div><span class=\"btn default btn-file\"> <span class=\"fileinput-new\"> Select image </span>"+ 
                                     "<span class=\"fileinput-exists\"> Change </span> <input type=\"file\" name=\"images\" accept=\"image/*\" id=\"logo_image\">"+
									 "</span> <a href=\"#\" class=\"btn default fileinput-exists\" data-dismiss=\"fileinput\"> Remove </a>"+
                                     "<div class=\"clearfix margin-top-10\"> <span class=\"label label-danger\"> NOTE! </span> <span>"+$('#background_change').find('.clearfix').find('span:eq(1)').text()+
                                     "</span></div><div class=\"margin-top-10\"><input type=\"submit\" class=\"btn green fileinput-exists\" value=\"Confirm\" class=\"form-control\"/></div></div></div></div>"
					                 );
						}
						 else{
						     handleAlerts("modify the restaurant logo fail.","danger","","10");
						 }
	            		 
					} 
	            	 
	             },
	             "error":function(XMLHttpRequest, textStatus, errorThrown){
	            	 alert(errorThrown);
	             }
	           });
			  return false;
		}); 
	}
	// 提示信息处理方法（是在页面中指定位置显示提示信息的方式）
	var handleAlerts = function(msg, msgType, position) {
		Metronic.alert({
			container : position, // alerts parent container(by default placed
									// after the page breadcrumbs)
			place : "prepent", // append or prepent in container
			type : msgType, // alert's type (success, danger, warning, info)
			message : msg, // alert's message
			close : true, // make alert closable
			reset : true, // close all previouse alerts first
			focus : false, // auto scroll to the alert after shown
			closeInSeconds : 10, // auto close after defined seconds, 0 never
									// close
			icon : "warning" // put icon before the message, use the font
								// Awesone icon (fa-[*])
		});

	};
	
	

	return {
		// main function to initiate the module
		init : function(rootPath) {
			rootURI = rootPath;
          
			// init editable elements
			initEditables();

			// init editable toggler
			$('#enable').click(function() {
				$('#store_setting .editable').editable('toggleDisabled');
			});

			// handle editable elements on hidden event fired
			$('#store_setting .editable').on('hidden', function(e, reason) {
				if (reason === 'save' || reason === 'nochange') {
					var $next = $(this).closest('tr').next().find('.editable');
					if ($('#autoopen').is(':checked')) {
						setTimeout(function() {
							$next.editable('show');
						}, 300);
					} else {
						$next.focus();
					}
				}
			});
			
			

		}

	};

}();
