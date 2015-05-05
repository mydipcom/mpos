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
var storeId = -1;
var button = 0;
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
		//$.fn.editable.defaults.url = '/post';
		$.fn.editable.defaults.ajaxOptions={ type:'POST',dataType: 'json'};
		$('#restaurant_name').editable({
			url : rootURI + "storeSetting/changeStoreName?rand=" + Math.random(),
			disabled:true,
			name:storeId,
			success : function(obj) {
				if(obj.status){
					handleAlerts(obj.info,"success","");
					$('#storeName').html(obj.msg);
				}else{
					handleAlerts(obj.info,"danger","");
				}
			}
		});

		$('#password').editable({
			url : rootURI + "storeSetting/changeKey?rand=" + Math.random(),
			disabled:true,
			name:storeId,
			success : function(res) {
				if(res.status){
					handleAlerts(res.info,"success","");
					$('#publicKey').html(res.msg);
				}else{
					handleAlerts(res.info,"danger","");
				}
			}
		});

		$('#token').editable({
			url : rootURI + "settings/editsetting?rand=" + Math.random(),
			disabled:true,
			success : function(res) {
				if(res.status){
					handleAlerts(res.info,"success","");
					$('#storeCurrency').html(res.msg);
				}else{
					handleAlerts(res.info,"danger","");
				}
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
			url : rootURI + "storeSetting/changeStoreCurrency?rand=" + Math.random(),
			disabled:true,
			name:storeId,
			inputclass : 'form-control input-medium',
			source : currency,
			success : function(res) {
				if(res.status){
					handleAlerts(res.info,"success","");
					$('#storeCurrency').html(res.msg);
				}else{
					handleAlerts(res.info,"danger","");
				}
			},
			  error: function(data) {
			        var msg = '';
			        if(data.errors) {              //validation error
			            $.each(data.errors, function(k, v) { msg += k+": "+v+"<br>"; });  
			        } else if(data.responseText) {   //ajax error
			            msg = data.responseText; 
			        }
			        alert(msg);
			    }
		});
		
		//修改商店背景
		$("#background_change").on("submit", function(event) {
			alert(storeId);
			 $.ajaxFileUpload( {
	             "type": "POST", 
	             "url": rootURI+"storeSetting/uploadBackground?rand="+Math.random(), 
	             "secureuri": false,
	             "fileElementId":"back_image", 
	             "dataType": "json",
	             "data":{"storeId":storeId},
//	             "processData":false,
//	             "contentType":"application/json",
	             "success": function(resp,status){
	            	 if(status == "success"){  
	            		 if(resp.status){
	            			 var path = rootURI+resp.path;
	            			 alert(path);
	            			 handleAlerts(resp.info,"success","");
							 $('#background_change').html("<div class=\"form-group\"><div class=\"fileinput fileinput-new\" data-provides=\"fileinput\">" +
								 		"<div class=\"fileinput-new thumbnail\" style=\"width: 200px; height: 200px;\">"+
	                                     "<img src=\""+path+"?rand="+Math.random()+"\" alt=\"\" /></div>"+
										 "<div class=\"fileinput-preview fileinput-exists thumbnail\" style=\"max-width: 200px; max-height: 150px;\"></div>"+
	                                     "<div><span class=\"btn default btn-file\"> <span class=\"fileinput-new\"> Select image </span>"+ 
	                                     "<span class=\"fileinput-exists\"> Change </span> <input type=\"file\" name=\"images\" accept=\"image/*\" id=\"backgroundimages\">"+
										 "</span> <a href=\"#\" class=\"btn default fileinput-exists\" data-dismiss=\"fileinput\"> Remove </a>"+
	                                     "<div class=\"clearfix margin-top-10\"> <span class=\"label label-danger\"> NOTE! </span> <span>"+$('#background_change').find('.clearfix').find('span:eq(1)').text()+
	                                     "</span></div><div class=\"margin-top-10\"><input type=\"submit\" class=\"btn green fileinput-exists\" value=\"Confirm\" class=\"form-control\"/></div></div></div></div>"
						                 );
			              }
						 else{
							 handleAlerts(resp.info,"danger","");
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
	             "url": rootURI+"storeSetting/uploadLogo?rand="+Math.random(), 
	             "secureuri": false,
	             "fileElementId":"logo_image", 
	             "dataType": "json",
	             "data":{"storeId":storeId},
//	             "processData":false,
//	             "contentType":"application/json",
	             "success": function(resp,status){
	            	 if(status == "success"){  
	            		 if(resp.status){
	            			 var path = rootURI+resp.path;
	            			 handleAlerts(resp.info,"success","");
							 $('#logo_change').html("<div class=\"form-group\"><div class=\"fileinput fileinput-new\" data-provides=\"fileinput\">" +
							 		"<div class=\"fileinput-new thumbnail\" style=\"width: 200px; height: 200px;\">"+
                                     "<img src=\""+path+"?rand="+Math.random()+"\" alt=\"\" /></div>"+
									 "<div class=\"fileinput-preview fileinput-exists thumbnail\" style=\"max-width: 200px; max-height: 150px;\"></div>"+
                                     "<div><span class=\"btn default btn-file\"> <span class=\"fileinput-new\"> Select image </span>"+ 
                                     "<span class=\"fileinput-exists\"> Change </span> <input type=\"file\" name=\"images\" accept=\"image/*\" id=\"logo_image\">"+
									 "</span> <a href=\"#\" class=\"btn default fileinput-exists\" data-dismiss=\"fileinput\"> Remove </a>"+
                                     "<div class=\"clearfix margin-top-10\"> <span class=\"label label-danger\"> NOTE! </span> <span>"+$('#background_change').find('.clearfix').find('span:eq(1)').text()+
                                     "</span></div><div class=\"margin-top-10\"><input type=\"submit\" class=\"btn green fileinput-exists\" value=\"Confirm\" class=\"form-control\"/></div></div></div></div>"
					                 );
						}
						 else{
							 handleAlerts(resp.info,"danger","");
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
	
	$("#search").click(function(){
		var storeId = "-1";
		if($("#storeId").val()!=null&&$("#storeId").val()!=''){
			storeId = $("#storeId").val();
		}
		window.location.href=rootURI + "storeSetting/search/"+storeId;
	});
	$('#editLan').click(function() {
		var str = document.getElementsByName("storeLangIds");
		var chestr=",";
		for (i=0;i<str.length;i++)
		{
		  if(str[i].checked == true)
		  {
		   chestr+=str[i].value+",";
		  }
		}
		$.ajax({
	        "dataType": 'json', 
	        "type":'GET',
	        "async":false,
	        "url": rootURI+"storeSetting/changeLangSet/"+chestr+"?rand="+Math.random(), 
	        "data":{"storeId":storeId},
	        "success": function(resp,status){
	       	 if(status == "success"){  
	       		 if(resp.status){
	       			/*for (i=0;i<str.length;i++){
	       				var che = str[i];
	       				if(chestr.indexOf(che.value)>0){
	       					che.checked=true;
	       				}else{
	       					che.checked=false;
	       				}
	       			}*/
	       				handleAlerts(resp.info,"success","");
					}else{
						handleAlerts(obj.info,"danger","");
					}
				}             	 
	        }
	      });
	});
	
	
	return {
		// main function to initiate the module
		init : function(rootPath) {
			rootURI = rootPath;
			storeId = $("#store_id").val();
         // alert(rootPath);
			//button = 0;
			// init editable elements
			initEditables();
			var inp = $("#printV").val();
			$("input[name='print_type']").filter("[value='"+inp+"']").attr("checked",true);
			$("input[name='print_type']").filter("[value='"+inp+"']").parents('span').addClass("checked");
			//$('#enable').html("点此解锁");
			$('#enable').click(function() {
				$('#store_setting .editable').editable('toggleDisabled');
				
				if(button == 1){
					$('#enable').html("点此解锁");
					button =0;
				} else{
					$('#enable').html("点此锁定");
					button =1;
				}
				
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
