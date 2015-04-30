var rootURI="/";
var locale = "en_US";
var TableTable = function () {
	var oTable;
	var handleTable = function () {
		var selected = [];
		var table=$('#table_table');
		oTable = table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"scrollX":"100%",
        	"scrollXInner":"100%",         	
        	"processing":true,                
            // set the initial value
            "displayLength": 10,
            "dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",
            "columnDefs": [{                    
                'targets': 0,   
                'render':function(data,type,row){
                	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
                	},
                //'defaultContent':'<div class="checker"><span><input type="checkbox" class="checkboxes" value="1"/></span></div>'                    
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
	           { /*title: "ID",*/   data: "id","bVisible":false},
	           { /*title: "Table Name",*/    data: "tableName" },
	           { /*title: "Seat Numbers",*/    data: "seatingNumber"},
	           { /*title: "Description",*/    data: "descr",
	        	   'render':function(data,type,row){
	        		   var temp = row.descr;
	        		   if(temp==null||temp==""){
	        			   temp = "";
	        		   }
	                	return temp;
	                	}
	           },
	           { /*title: "Action" ,*/"class":"center"}
	          ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"table/tableList?rand="+Math.random()

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
	           $("#editTableForm input[name='id']").val(data.id);
	           $("#editTableForm input[name='storeId']").val(data.storeId);
	           $("#editTableForm input[name='tableName']").val(data.tableName);
	           $("#editTableForm input[name='createTime']").val(data.createTime);
	           $("#editTableForm input[name='seatingNumber']").val(data.seatingNumber);
	           $("#editTableForm textarea[name='descr']").val(data.descr);
	           $("#editTableForm input[name='status']").val(data.status);
	        });
		
		//删除操作
		$('#deleteBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "GET", 
             "url": rootURI+"table/"+selected.join(), 
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
			$("#addTableForm input[name='storeId']").val($("#storeId").val());
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
	            	var id = data.id;
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
            var id = data.id;
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
         "url": rootURI+"table/addTable", 
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
    
  //添加操作
	var ajaxEditTable=function(formId){
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"table/editTable", 
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
    	    var URL =  rootURI+"table/checkTableName?rand="+Math.random();
    		if(type=="edit"){
    			URL =  rootURI+"table/verification?rand="+Math.random();
    		}
            var errorDiv = $('.alert-danger', formId);            
            formId.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input                
                rules: {
                    "tableName": {
                    	required: true,
                    	remote: {
                    	    url:URL,     //后台处理程序
                    	    type: "post",               //数据发送方式
                    	    dataType: "json",           //接受数据格式   
                    	    data: {                     //要传递的数据
                    	    	tableName: function() {
                    	    		if(type=="edit"){
                    	    			return $("#"+type+"TableForm input[name='tableName']").val()+","+$("#"+type+"TableForm input[name='id']").val()+","+$("#"+type+"TableForm input[name='storeId']").val();
                    	    		}else{
                    	    			return $("#"+type+"TableForm input[name='tableName']").val()+","+$("#"+type+"TableForm input[name='storeId']").val();
                    	    		}
                    	        }
                    	    }
                    	}
                    },
                    "seatingNumber": {
                    	required: true,
                    	digits:true,
                    	min:1 
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
        init: function (rootPath,locale_value) {
        	rootURI=rootPath;
        	locale = locale_value;
        	handleTable(); 
        	FormValidation($('#addTableForm'),"add");
        	FormValidation($('#editTableForm'),"edit");
        }

    };

}();
