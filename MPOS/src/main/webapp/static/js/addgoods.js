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

var rootURI="/";
var Addgoods = function () {

	   
			$('#categoryAttributesFormSubmit').on('click',function(e){
				alert("test");
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
								$(html).appendTo($('#attribute'));
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
				alert("test");
				var id=$(this).val();
				alert(id);
				var html;
			//	html+="<select name=\"attributeId\"  class=\"form-control\" id=\"testid\"><option value=\"\">ALL</option>";
				html+="<option value=\"\">ALL</option>";
				$.ajax({
					"dataType": 'json', 
		             "type"   : "POST", 
		             "url"    : rootURI+"goods/getcategorybyid/"+id,
		             "success": function(data,status){
		            	 if(status == "success"){ 
		            		 if(data.status){
		            			 alert(data.list);
		            			 var test=data.list;
		            			 for(var i=0;i<test.length;i++){
		            				 alert(test[i].content);
		            				 var content=(test[i].content).split("?");
		            				 html+="<option value="+test[i].attributeId+">"+test[i].title+"</option>"
		            				 /*
		            				 if((test[i].type)==1){
		            					 html="<div class=\"form-group\"><label class=\"control-label col-md-3\">"+test[i].title+"</label><div class=\"col-md-9\"><div class=\"checkbox-list\">"
		            					 for(var j=0;j<content.length;j++){
		            						 html=html+"<label class=\"checkbox-inline\"><input type=\"checkbox\" name=\"content\" value="+content[j]+"/>"+content[j]+"</label>";
		            					 }
		            					 html+="</div></div></div>";
		            					
		            				 }else if((test[i].type)==2){
		            					 
		            				 }else {
		            					 
		            				 }*/
		            				 
		            			 }
		            			//html+="</select>";
		            			 	$(html).appendTo($('#chooseattribute'));
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
				alert("test");
				var id=$(this).val();
				alert(id);
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
		            			 var content=(test.content).split("?");
		            			 if((test.type)==1){
	            					 html="<div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">"+test.title+"</label><div class=\"col-md-9\"><div class=\"checkbox-list\">";
	            					 for(var j=0;j<content.length;j++){
	            						 html=html+"<label class=\"checkbox-inline\"><input type=\"checkbox\" name=\"content\" value="+content[j]+">"+content[j]+"</label>";
	            					 }
	            					 html+="</div></div></div></div><div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">Price</label><div class=\"col-md-9\"><input name=\"price\" class=\"form-control\"/>";
	            					 html+="<div class=\"row\"><input name=\"title\" type=\"hidden\" value="+test.title+"></div>";
	            					 html+="<div class=\"row\"><input name=\"attributeId\" type=\"hidden\" value="+test.attributeId+"></div>";
	            					 html+="</div></div></div>";
	            					
	            				 }else if((test.type)==2){
	            					 html="<div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">"+test.title+"</label><div class=\"col-md-9\"><div class=\"radio-list\">";
	            					 for(var j=0;j<content.length;j++){
	            						 html+="<label class=\"radio-inline\"><input type=\"radio\"  name=\"recommend\" value="+content[j]+"/>"+content[j]+"</label>";
	            					 }
	            					 html+="</div></div></div></div><div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">Price</label><div class=\"col-md-9\"><input name=\"price\" class=\"form-control\"/>";
	            					 html+="<div class=\"row\"><input name=\"title\" type=\"hidden\" value="+test.title+"></div>";
	            					 html+="<div class=\"row\"><input name=\"attributeId\" type=\"hidden\" value="+test.attributeId+"></div>";
	            					 html+="</div></div></div>";
	            					
	            				 }else {
	            					 html="<div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">"+test.title+"</label><div class=\"col-md-9\"><select class=\"select2_category form-control\"  name=\"content\">";
	            					 for(var j=0;j<content.length;j++){
	            					 html+="<option value="+content[j]+">"+content[j]+"</option>";
	            					 }
	            					 html+="</select></div></div></div><div class=\"row\"><div class=\"form-group\"><label class=\"control-label col-md-3\">Price</label><div class=\"col-md-9\"><input name=\"price\" class=\"form-control\"/>";
	            					 html+="<div class=\"row\"><input name=\"title\" type=\"hidden\" value="+test.title+"></div>";
	            					 html+="<div class=\"row\"><input name=\"attributeId\" type=\"hidden\" value="+test.attributeId+"></div>";
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
        	
        }

    };

}();