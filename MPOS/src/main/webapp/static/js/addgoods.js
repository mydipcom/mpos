//jquery插件把表单序列化成json格式的数据start 
(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var str=this.serialize();
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);
function setGoodsFromValue(list){
	//var le = $("#lan_len").val();
	var le=list.length/3;
	if(list!=null&&list.length>0){
		for (var int = 0; int < le; int++) {
			var lan = list[int];
			$("#editGoodsForm input[name='productNames["+int+"].localeValue']").val(lan.localeValue);
			$("#editGoodsForm input[name='productNames["+int+"].language.id']").val(lan.languageId);
			$("#editGoodsForm input[name='productNames["+int+"].tableField']").val(lan.tableField);
			$("#editGoodsForm input[name='productNames["+int+"].localeId']").val(lan.localeId);
		}
		for (var int = le; int < 2*le; int++) {
			var lan = list[int];
			var tem = int - le;
			$("#editGoodsForm textarea[name='shortDescrs["+tem+"].localeValue']").val(lan.localeValue);
			$("#editGoodsForm input[name='shortDescrs["+tem+"].localeValue']").val(lan.localeValue);
			$("#editGoodsForm input[name='shortDescrs["+tem+"].language.id']").val(lan.languageId);
			$("#editGoodsForm input[name='shortDescrs["+tem+"].tableField']").val(lan.tableField);
			$("#editGoodsForm input[name='shortDescrs["+tem+"].localeId']").val(lan.localeId);
		}
		for (var int = 2*le; int < list.length; int++) {
			var lan = list[int];
			var tem = int - 2*le;
			$("#editGoodsForm textarea[name='fullDescrs["+tem+"].localeValue']").val(lan.localeValue);
			$("#editGoodsForm input[name='fullDescrs["+tem+"].localeValue']").val(lan.localeValue);
			$("#editGoodsForm input[name='fullDescrs["+tem+"].language.id']").val(lan.languageId);
			$("#editGoodsForm input[name='fullDescrs["+tem+"].tableField']").val(lan.tableField);
			$("#editGoodsForm input[name='fullDescrs["+tem+"].localeId']").val(lan.localeId);
		}
	}
	
}
function setValue(id){
	//var addc = $("#addAttributeForm input[name='content']").val();
	//var editc = $("#edditAttributeForm input[name='content']").val();

	//goods add
	$("#good_addt_"+id).val($("#good_add_name").val());
	$("#good_addc_"+id).val($("#good_add_scon").val());
	$("#good_addft_"+id).val($("#good_add_fcon").val());
	$("#good_editn_"+id).val($("#good_edit_name").val());
	$("#goods_editsc_"+id).val($("#good_edit_scon").val());
	$("#goods_editfc_"+id).val($("#good_edit_fcon").val());

}

var rootURI="/";
var Addgoods = function () {

	   
			$('#categoryAttributesFormSubmit').on('click',function(e){
				var html;
				$.ajax( {
					 "dataType": 'json', 
					 "type": "POST", 
					 "url": rootURI+"goods/addattributes", 
					 "data": $('#categoryAttributesForm').serialize(),
					 "success": function(data,status){
						 if(status == "success"){  
							 if(data.status){
								var attributes=data.attributeModel;
								html+="<div class=\"row\"><div class=\"col-md-6\"><div class=\"form-group\"><label class=\"control-label col-md-3\">"+attributes.title+"</label><div class=\"col-md-9\">";
								html+="<label class=\"control-label col-md-3\">Content:"+attributes.content+"</label><div class=\"col-md-9\">";
								//html+="<label class=\"control-label col-md-3\">Price</label><input  type=\"text\" class=\"form-control\" value="+attributes.price+">";
								html+="<label class=\"control-label col-md-3\">Price:"+attributes.price+"</label><div class=\"col-md-9\">";
								html+="</div></div></div></div>";
								$('#categoryAttributes').modal('hide');
								if(attributes.content==undefined){
									$('#type'+attributes.type).empty();
								}else{
								if(attributes.type==1){
									$('#type1').empty();
									$(html).appendTo($('#type1'));
								}else if(attributes.type==2){
									$('#type2').empty();
									$(html).appendTo($('#type2'));
								}else{
									$('#type3').empty();
									$(html).appendTo($('#type3'));
								}
								}
							 }
							 else{
								 
							 }
						}             	 
					 },
					 "error":function(XMLHttpRequest, textStatus, errorThrown){
					    	 alert(errorThrown);
					  }
				});
				// $(html).appendTo($('#attribute'));
				return false;
			});

			$('#choosecategory').on('change',function(e){
				var id=$(this).val();
				var html;
				//html+="<select name=\"attributeId\"  class=\"form-control\" id=\"chooseattribute\">";
				//html+="<option value=\"\">ALL</option>";
				$.ajax({
					"dataType": 'json', 
		             "type"   : "POST", 
		             "url"    : rootURI+"goods/getcategorybyid/"+id,
		             "success": function(data,status){
		            	 if(status == "success"){ 
		            		 if(data.status){
		            			 var list=data.list;
		            			 for(var i=0;i<list.length;i++){
		            				 var content=(list[i].content).split(",");
		            			//	 html+="<option value="+list[i].attributeId+">"+list[i].title+"</option>"
		            			//	 var id=list[i].attributeId;
		            				 if((list[i].type)==2){
		            					 html+="<div class=\"row\"><div class='col-md-6' ><div class=\"form-group\"><label class=\"control-label col-md-3\">"+list[i].title+"</label><div class=\"col-md-9\"><div class=\"checkbox-list\">";
		            					 for(var j=0;j<content.length;j++){
		            						 html=html+"<label class=\"checkbox-inline\"><input type=\"checkbox\" name=\"content\" value='"+content[j]+'-'+list[i].attributeId+"'>"+content[j]+"</label>";
		            					 }
		            					 html+="</div></div></div></div></div>";
		            					
		            					
		            				 }else if((list[i].type)==1){
		            					 html+="<div class='row'><div class='col-md-6' ><div class='form-group'><label class='control-label col-md-3'>"+list[i].title+"</label><div class='col-md-9'><div class='radio-list'>";
		            					 for(var j=0;j<content.length;j++){
		            						// html+="<label class='radio-inline'><div class='radio'><span><input type='radio'  name='content' value="+content[j]+"></span></div>"+content[j]+"</label>";
		            						 html+="<label class='radio-inline'><span><input type='radio'  name='content' value='"+content[j]+'-'+list[i].attributeId+"'></span>"+content[j]+"</label>";
		            					 }
		            					 html+="</div></div></div></div></div>";
		            					
		            				 }else  if((list[i].type==3)){
		            					 html+="<div class='row'><div class='col-md-6' ><div class='form-group'><label class='control-label col-md-3'>"+list[i].title+"</label><div class='col-md-9'><select class='select2_category form-control'  name='content'>";
		            					 for(var j=0;j<content.length;j++){
		            					 html+="<option value='"+content[j]+'-'+list[i].attributeId+"'>"+content[j]+"</option>";
		            					 }
		            					 html+="</select></div></div></div></div>";
		            				 }else if((list[i].type==0)){
		            					 html+="<div class='row'><div class='col-md-6' ><div class='form-group'><label class='control-label col-md-3'>"+list[i].title+"</label>";
		            					 html+="<div class='col-md-9'><input name='content' type='text' class='form-control'  />"; 
		            					 html+="<div class='row'><input name='attributeId' type='hidden' value='"+list[i].attributeId+"'></div>";
		            					 html+="</div></div></div></div>";
		            					 
		            				 }
		            			 }
		            			// html+="<button type='button' class='btn default' id='tesssss'>Add Attribute</button>";
		            			 $('#chooseattribute').empty();
		            			 $('#attributeshow').empty();
		            			// $(html).appendTo($('#chooseattribute'));
		            			 $(html).appendTo($('#attributeshow'));
		            			 $('#attributebutn').removeAttr("hidden");
		            			// $(html).appendTo($('#name'));
		            			// $('#categoryAttributes').modal('show');
		            			 	//$('#chooseattribute').replaceWith(html);
		            			 //$(html).replaceAll($('#attributetitle'));
		            			 	//$(html).replaceAll($('#chooseattribute'));
		            			 	//$('#categoryAttributes').modal('show');
		            				
							 }
							 else{
								 
							 } 
						}             	 
		             },
		             "error":function(XMLHttpRequest, textStatus, errorThrown){
		            	alert(errorThrown);
		              }
		           
				});
			});
			$('#chooseattribute').on('change',function(e){
				
				var id=$(this).val();
				
				$('#name').empty();
				
				$.ajax({
					"dataType": 'json', 
		             "type"   : "POST", 
		             "url"    : rootURI+"goods/getcategoryattribbyid/"+id,
		             "success": function(data,status){
		            	 if(status == "success"){ 
		            		 if(data.status){
		            			 var test=data.list;
		            			 var html;
		            			 var content=(test.content).split(",");
		            			 if((test.type)==2){
	            					 html="<div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">"+test.title+"</label><div class=\"col-md-9\"><div class=\"checkbox-list\">";
	            					 for(var j=0;j<content.length;j++){
	            						 html=html+"<label class=\"checkbox-inline\"><input type=\"checkbox\" name=\"content\" value="+content[j]+">"+content[j]+"</label>";
	            					 }
	            					 html+="</div></div></div></div><div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">Price</label><div class=\"col-md-9\"><input name=\"price\" class=\"form-control\"/>";
	            					 html+="<div class=\"row\"><input name=\"title\" type=\"hidden\" value="+test.title+"></div>";
	            					 html+="<div class=\"row\"><input name=\"attributeId\" type=\"hidden\" value="+test.attributeId+"></div>";
	            					 html+="<div class=\"row\"><input name=\"type\" type=\"hidden\" value="+test.type+"></div>";
	            					 html+="</div></div></div>";
	            					
	            					
	            				 }else if((test.type)==1){
	            					 html="<div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">"+test.title+"</label><div class=\"col-md-9\"><div class=\"radio-list\">";
	            					 for(var j=0;j<content.length;j++){
	            						 html+="<label class=\"radio-inline\"><input type=\"radio\"  name=\"content\" value="+content[j]+">"+content[j]+"</label>";
	            					 }
	            					 html+="</div></div></div></div><div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">Price</label><div class=\"col-md-9\"><input name=\"price\" class=\"form-control\"/>";
	            					 html+="<div class=\"row\"><input name=\"title\" type=\"hidden\" value="+test.title+"></div>";
	            					 html+="<div class=\"row\"><input name=\"attributeId\" type='hidden' value="+test.attributeId+"></div>";
	            					 html+="<div class=\"row\"><input name=\"type\" type=\"hidden\" value="+test.type+"></div>";
	            					 html+="</div></div></div>";
	            					
	            				 }else  if((test.type==3)){
	            					 html="<div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">"+test.title+"</label><div class=\"col-md-9\"><select class=\"select2_category form-control\"  name=\"content\">";
	            					 for(var j=0;j<content.length;j++){
	            					 html+="<option value="+content[j]+">"+content[j]+"</option>";
	            					 }
	            					 html+="</select></div></div></div><div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">Price</label><div class=\"col-md-9\"><input name=\"price\" class=\"form-control\"/>";
	            					 html+="<div class=\"row\"><input name=\"title\" type=\"hidden\" value="+test.title+"></div>";
	            					 html+="<div class=\"row\"><input name=\"attributeId\" type=\"hidden\" value="+test.attributeId+"></div>";
	            					 html+="<div class=\"row\"><input name=\"type\" type=\"hidden\" value="+test.type+"></div>";
	            					 html+="</div></div></div>";
	            				 }
		            			 $(html).appendTo($('#name'));
		            			 $('#categoryAttributes').modal('show');
		            		 		}
		            	 		}
		             		},
		             		"error":function(XMLHttpRequest, textStatus, errorThrown){
				            	alert(errorThrown);
		             		}
		            	 });
		             
			});
			$('#addnewAttribute').on('click', function (e){
				
			});
			/*
				var AddGoods = function(){
					event.stopPropagation();
					var test=$('#fileupload').serialize();
					$.ajax( {
			         "dataType": 'json', 
			         "type":'POST', 
			         "url": rootURI+"goods/addGoods", 
			         "data": $('#fileupload').serialize(),
			         "success": function(resp,status){
			        	 if(status == "success"){  
			        		 if(resp.status){						 
				            
				            	 handleAlerts("Added the data successfully.","success","");		            	 
							 }
							 else{
								 handleAlerts("Failed to add the data."+resp.info+"the name or email exist","danger","");						 
							 }
						}             	 
			         },
			         "error":function(XMLHttpRequest, textStatus, errorThrown){
			        	 alert(errorThrown);
			         }
			       });
					//$('#add_users').modal('hide');
			    };
			    
*/
				
				/*
				$('#name').empty();
				var obj=document.getElementById("testsid");
				if(obj.value==1){
					var html="<div class=\"form-group\"><label class=\"control-label col-md-3\">PreferentialPrice<span class=\"required\">* </span></label><div class=\"col-md-9\"><input name=\"email\" class=\"form-control\"/></div></div>";
					$(html).appendTo($('#id'));
				}
				if(obj.value==2){
					var html="<div class=\"form-group\"><label class=\"control-label col-md-3\">Inline Checkboxes<span class=\"required\">* </span></label><div class=\"col-md-9\"><div class=\"checkbox-list\"><label class=\"checkbox-inline\"><input type=\"checkbox\" id=\"inlineCheckbox1\" value=\"option1\"> Checkbox 1 </label><label class=\"checkbox-inline\"><input type=\"checkbox\" id=\"inlineCheckbox2\" value=\"option2\"> Checkbox 2 </label></div></div></div>"
					$(html).appendTo($('#id'));	
				}
				*/
			

	    


	   
			 var AddGoodsValidation = function() {
			        var form = $('#addGoodsForm');
			        var errorDiv = $('.alert-danger', form);   
			        var t=$('#price').val();
			        form.validate({
			            errorElement: 'span', //default input error message container
			            errorClass: 'help-block help-block-error', // default input error message class
			            focusInvalid: false, // do not focus the last invalid input
			            ignore: "",  // validate all fields including form hidden input                
			            rules: {
			            	price: {
			                	required: true,
			                	number:true,
				            	maxlength:6,
			    				},
			            	oldPrice: {
			            	
			            	required: true,
			            	maxlength:6,
			            	number:true,
			            
			                		},
			                
			    			unitName: {
					               required: true,
						           maxlength:6,
					    			},
					    	productName: {
							       required: true,
								   minlength:3,
							    		},
							shortDescr: {
									required: true,
									
									    },
							fullDescr: {
								required: true,
								
							           },
									  
			    			sort: {
			        		
			        		required: true,
			        		integer:true,
			        		
			    				}

			            },
			           invalidHandler: function (event, validator) { //display error alert on form submit              
			                errorDiv.show();                    
			            },

			                highlight: function (element) { // hightlight error inputs
			                    $(element)
			                        .closest('.form-group').addClass('has-error'); // set error class to the control group
			                },

			                unhighlight: function (element) { // revert the change done by hightlight
			                    $(element)
			                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
			                },

			            success: function (label) {
			                label
			                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
			            },
			            onfocusout:function(element){
			            	$(element).valid();
			            },
			            submitHandler: function (form) { 
			            	errorDiv.hide();
			            	$('#testbutn').on(submit,true);
			            	
			            }
			        });
			    };
			    
			    var EditGoodsValidation = function() {
			        var form = $('#editGoodsForm');
			        var errorDiv = $('.alert-danger', form);            
			        form.validate({
			            errorElement: 'span', //default input error message container
			            errorClass: 'help-block help-block-error', // default input error message class
			            focusInvalid: false, // do not focus the last invalid input
			            ignore: "",  // validate all fields including form hidden input                
			            rules: {
			            	price: {
			                	required: true,
			                	number:true,
				            	maxlength:6,
			    				},
			            	oldPrice: {
			            	required: true,
			            	maxlength:6,
			            	number:true,
			            	min:"#price",
			                		},
			    			unitName: {
					               required: true,
						           maxlength:6,
					    			},
					    	productName: {
							       required: true,
								   minlength:6,
							    		},
							shortDescr: {
									required: true,
									
									    },
							fullDescr: {
								required: true,
								
							           },
									  
			    			sort: {
			        		
			        		required: true,
			        		integer:true,
			        		
			    				}

			            },
			           invalidHandler: function (event, validator) { //display error alert on form submit              
			                errorDiv.show();                    
			            },

			                highlight: function (element) { // hightlight error inputs
			                    $(element)
			                        .closest('.form-group').addClass('has-error'); // set error class to the control group
			                },

			                unhighlight: function (element) { // revert the change done by hightlight
			                    $(element)
			                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
			                },

			            success: function (label) {
			                label
			                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
			            },
			            onfocusout:function(element){
			            	$(element).valid();
			            },
			            submitHandler: function (form) { 
			            	errorDiv.hide();
			            	
			            }
			        });
			    };
    //提示信息处理方法（是在页面中指定位置显示提示信息的方式）
	var handleAlerts = function(msg,msgType,position) {         
        Metronic.alert({
            container: position, // alerts parent container(by default placed after the page breadcrumbs)
            place: "prepent", // append or prepent in container 
            type: msgType,  // alert's type (success, danger, warning, info)
            message: msg,  // alert's message
            close: true, // make alert closable
            reset: true, // close all previouse alerts first
            focus: false, // auto scroll to the alert after shown
            closeInSeconds: 5, // auto close after defined seconds, 0 never close
            icon: "warning" // put icon before the message, use the font Awesone icon (fa-[*])
        });        

    };
    
    
 
    
  
    
    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	AddGoodsValidation();
        	EditGoodsValidation();
        	
        }

    };

}();