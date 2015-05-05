var rootURI="/";

var GetInfo = function(){
	var getRole = function(roleId){
		var res = "";
		$.ajax({
	        "dataType": 'json', 
	        "type":'GET',
	        "async":false,
	        "url": rootURI+"service/getRole/"+roleId+"?rand="+Math.random(), 
	        "success": function(resp,status){
	       	 if(status == "success"){  
	       		 if(resp.status){
	       			 res = resp.msg;
	       			 return  res;
					}
				}             	 
	        }
	      });
		return res;
	}
	
	 return {
	        get: function (id) {
	        	return getRole(id);
	        }
	    };
}();
var ServiceTable = function () {
	var oTable;
	var handleTable = function () {
		var selected = [];
		var table=$('#service_table');
		oTable = table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"scrollX":"100%",
        	"scrollXInner":"100%",         	
        	"processing":true,                
            "displayLength": 10,
            "dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",
            "columnDefs": [{                    
                'targets': 0,   
                'render':function(data,type,row){
                	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
                	},
            	},{                	
            	'targets':-1,
            	'data':null,//定义列名
            	'render':function(data,type,row){
                	return '<div class="actions"><a class="btn btn-sm dark" data-toggle="modal"  href="#edit_table">edit</a></div>';
                	},
                'class':'center'
            	}
            ],
            "columns": [
               {"orderable": false },
	           {data: "serviceId","bVisible":false},
	           {data: "serviceName" },
	           {data: "servicePrice"},
	           {data: "validDays" },
	           {data: "content" },
	          /* {data: "roleId",
	        	'render':function(data,type,row){
	        		var temp = "";
	        		temp = GetInfo.get(data);
	                return temp;
	                }
	           },*/
	           { data: "status",
	        	   'render':function(data,type,row){
		        		var res ='<font color="green">已发布</font>';
		        		if(data=="0"){
		        			res ='<font color="red">未发布</font>';
		        		}
		                return res;
		                }
	           },
	           {"class":"center"}
	          ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"service/serviceList?rand="+Math.random()

		});		

		//打开删除对话框前判断是否已选择要删除的行
		$("#openDeleteTableModal").on("click",function(event){
			if(selected.length==0){
				handleAlerts(loadProperties("error.delete.select",locale),"warning","");				
				return false;
			}
		});
		
		table.on('click', 'tbody tr a',function(){
	           var data = oTable.api().row($(this).parents('tr')).data();
	            $("#editTableForm option").removeAttr("selected");
	            $("#editTableForm select[name='roleId']").children("option[value='"+data.roleId+"']").attr("selected","true");
	            
	            $('#status_box').eq(0).attr("checked",'false');
            	$("#status_box").eq(0).parents('span').removeClass("checked");
	            if(data.status){
	            	$('#status_box').eq(0).attr("checked",'true');
	            	$("#status_box").eq(0).parents('span').addClass("checked");
	            }
	           $("#editTableForm input[name='serviceId']").val(data.serviceId);
	           $("#editTableForm input[name='serviceName']").val(data.serviceName);
	           $("#editTableForm input[name='servicePrice']").val(data.servicePrice);
	           $("#editTableForm input[name='validDays']").val(data.validDays);
	           $("#editTableForm textarea[name='content']").val(data.content);
	        });
		
		//删除操作
		$('#deleteBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "DELETE", 
             "url": rootURI+"service/delete/"+selected.join(), 
             "success": function(data,status){
            	 if(status == "success"){
            		 var infoType = "danger";
					 if(data.status){
						 selected=[];
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
		            	 infoType = "success";
					 }
					handleAlerts(data.info,infoType,"");
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        });  
		
		//搜索表单提交操作
		$("#searchForm").on("submit", function(event) {
			event.preventDefault();
			var jsonData=$(this).serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
			return false;
		});
				
                       
		//全选
        $(".group-checkable").on('change',function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            selected=[];
            if(checked){            	
	            var api=oTable.api();            
	            jQuery(set).each(function () {            	
	            	var data = api.row($(this).parents('tr')).data();
	            	var id = data.serviceId;
	                var index = $.inArray(id, selected);
	                selected.push( id );
                    $(this).attr("checked", true);
                    $(this).parents('tr').addClass("active");
                    $(this).parents('span').addClass("checked");
	            });
            }
            else{
            	jQuery(set).removeAttr("checked");
            	jQuery(set).parents('tr').removeClass("active");
            	jQuery(set).parents('span').removeClass("checked");
            }
            jQuery.uniform.update(set);
        });
        
        //单选
        table.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");            
            var data = oTable.api().row($(this).parents('tr')).data();
            var id = data.serviceId;
            var index = $.inArray(id, selected);     
            if ( index === -1 ) {
                selected.push( id );
                $(this).parents('span').addClass("checked");
                $(this).attr("checked","checked");
            } else {
                selected.splice( index, 1 );
                $(this).parents('span').removeClass("checked");
                $(this).removeAttr("checked");
            }
        });
                
        /* handle show/hide columns*/
        var tableColumnToggler = $('#column_toggler');		
		$('input[type="checkbox"]', tableColumnToggler).change(function () {
		    /* Get the DataTables object again - this is not a recreation, just a get of the object */
		    var iCol = parseInt($(this).attr("data-column"));
		    var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
		    oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
        
        
	};
	
	//添加操作
	var ajaxAddTable=function(formId){
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"service/addService", 
         "data": formId.serialize(),
         "success": function(resp,status){
        	 if(status == "success"){
        		 var infoType = "danger";
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 infoType = "success";
	            	 $('#addTableForm')[0].reset();
	            	 $("#add_table").modal('hide');
				 }
				handleAlerts(resp.info,infoType,"#addFormMsg");						 
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });		
    };
    
  //修改操作
	var ajaxEditTable=function(formId){
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"service/editService", 
         "data": formId.serialize(),
         "success": function(resp,status){
        	 if(status == "success"){ 
        		 var infoType = "danger";
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 $('#editTableForm')[0].reset();
	            	 $("#edit_table").modal('hide');
	            	 infoType="success";
				 }
				handleAlerts(resp.info,infoType,"#editFormMsg");						 
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });		
    };
	
	
	
	//提示信息处理方法（是在页面中指定位置显示提示信息的方式）
	var handleAlerts = function(msg,msgType,position) {
		if(position==""){
			position = $("#msg");
		}
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
    
    //处理表单验证方法
    var FormValidation = function(formId,type) {
            var errorDiv = $('.alert-danger', formId);            
            formId.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input                
                rules: {
                    "serviceName": {
                    	required: true
                    },
                    "servicePrice": {
                    	required: true,
                    	digits:true
                    },
                    "validDays":{
                    	required: true,
                    	digits:true,
                    	min:0
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
                onfocusout: function (element) { // hightlight error inputs
                    $(element).valid();
                },
                success: function (label) {
                    label
                        .closest('.form-group').removeClass('has-error'); // set success class to the control group
                },

                submitHandler: function (form) {                	
                    errorDiv.hide();
                    if(type=="edit"){
                    	ajaxEditTable(formId);
                    }else if(type=="add"){
                    	ajaxAddTable(formId); 
                    }
                }
            });
    };
    
    return {
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable(); 
        	FormValidation($('#addTableForm'),"add");
        	FormValidation($('#editTableForm'),"edit");
        }

    };

}();
