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
var locale = "en_US";
var ManagersTable = function () {
	var oTable;
	var oLogTable;
	var selected = [];
	var handleTable = function () {				
		var viewTable = function(ids){			
			var logTable=$('#managerlog_table');
			oLogTable = logTable.dataTable({
				"lengthChange":false,
		    	"filter":false,
		    	"sort":false,
		    	"info":true,
		    	"bRetrieve": true,
		    	"processing":true,
		    	"bDestroy":true,
		    	"scrollX":"100%",
	           	"scrollXInner":"100%",
		        // set the initial value
		        "displayLength": 3,
		        "dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",
		        "columns": [
		 	           { data: "id" },
		 	           { data: "adminId" },
		 	           { data: "content"},
		 	           { data: "level"},
		 	           { data: "createdTimeStr" },
		 	        ],
     	        "serverSide": true,
     	        "serverMethod": "GET",
     	        "ajaxSource": rootURI+"managerlog/managerslogList/"+ids+"?rand="+Math.random()
			});	
		};
	
		var table=$('#adminusers_table');
		 oTable = table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"processing":true,
        	"scrollX":"100%",
           	"scrollXInner":"100%",
            "displayLength": 10,
            "dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",
            "columnDefs": [{                    
                    'targets': 0,   
                    'render':function(data,type,row){
                    	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
                    },
                    //'defaultContent':'<div class="checker"><span><input type="checkbox" class="checkboxes" value="1"/></span></div>'                    
                },
                {                	
                	'targets':-1,
                	'data':null,//定义列名
                	'render':function(data,type,row){
                    	//return '<div class="actions"><a class="btn btn-default btn-sm" data-toggle="modal"  href="#view_log" id="openrluesviewmodal">view</a></div>';
                		return '<div class="actions"><a  class="btn btn-sm dark" data-toggle="modal"  href="#view_log" id="openrluesviewmodal">查看</a></div>';
                    },
                    'class':'center'
                }
            ],
            "columns": [
               {"orderable": false },
	           {     data: "adminId"  },
	           {     data: "email" },
	           {     data: "roleName" },
	           {   
	 	        'render':function(data,status,row){
	        				var tem = row.status;
	        				var str = '';
	        				if(tem==1){
	        					str = '已激活';
	        				}else if(tem==0){
	        					str = '未激活';
	        				}
	        				return str;
	        			}
	           },

	           {	 data: "createdBy" ,"bVisible":false},
	           { 	 data: "createdTimeStr", "bVisible":false},
	           { 	 data: "updatedBy" ,"bVisible":false},
	           {     data: "updatedTimeStr" ,"bVisible":false},  
	           { title: "操作" ,"class":"center"},
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"manager/managersList?rand="+Math.random(),
	        "fnDrawCallback":function(oSetting){
	        	selected=[];
	        }
		});		
		 
		//打开删除对话框前判断是否已选择要删除的行
			$("#openDeleteadminsModal").on("click",function(event){
					if(selected.length==0){
						handleAlerts(loadProperties("error.delete.select",locale),"warning","");			
						return false;
					}
				});
			$("#openActiveadminsModal").on("click",function(event){
				if(selected.length==0){
					handleAlerts(loadProperties("error.active.select",locale),"warning","");				
					return false;
				}
			});
			$("#openDeactiveadminsModal").on("click",function(event){
				if(selected.length==0){
					handleAlerts(loadProperties("error.deactive.select",locale),"warning","");				
					return false;
				}
			});
		//删除操作
		$('#deleteBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "DELETE", 
             "url": rootURI+"manager/managers/"+selected.join(), 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
		            	 handleAlerts(data.info,"success","");
					 }
					 else{
						 handleAlerts(data.info,"danger","");
					 }
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        });  
		
		//激活用户
		$('#activateBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "GET", 
             "url": rootURI+"manager/"+selected.join()+"/activateusers", 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	oTable.api().draw();
		            	oTable.$('th span').removeClass();
		            	 handleAlerts(data.info,"success","");
					 }
					 else{
						 handleAlerts(data.info,"danger","");
					 }
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        }); 
		//禁用
		$('#deactivateBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "GET", 
             "url": rootURI+"manager/"+selected.join()+"/deactivateusers", 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
		            	 handleAlerts(data.info,"success","");
					 }
					 else{
						 handleAlerts(data.info,"danger","");
					 }
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
		$("#openEditRightModal").on("click",function(event){
			if(selected.length!=1){
				handleAlerts(loadProperties("error.edit.select",locale),"warning","");
				return false;
			}
			else{
				var data = oTable.api().row($("tr input:checked").parents('tr')).data();
				//$("#editUsersForm option").removeAttr("selected");
				$("#editUsersForm option").removeAttr("selected");
	            var adminId = data.adminId;
	            var email =data.email;
	            var createby=data.createdBy;
	            var creatime=data.createdTimeStr;
	            var roleId=data.roleId;
	            $("#editUsersForm select[name='adminRole.roleId']").children("option[value='"+roleId+"']").attr("selected","true");
	            $("#editUsersForm input[name='adminId']").val(adminId);
	            $("#editUsersForm input[name='email']").val(email);
	            $("#editUsersForm input[name='storeId']").val(data.storeId);
	            $("#editUsersForm input[name='createdBy']").val(createby);
	            $("#editUsersForm input[name='createdTimeStr']").val(creatime);
			}
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
	            	var ids=data.email;
	                var index = $.inArray(ids, selected);
	                selected.push( ids );
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
        
        
        table.on('click', 'tbody tr a',function(){
            var data = oTable.api().row($(this).parents('tr')).data();
           var ids=data.adminId;
           if(oLogTable!=null){
        	   oLogTable.fnDestroy();
        	   viewTable(ids); 
           }else{
        	   viewTable(ids);
           }
           });
        
        //单选
        table.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");            
            var data = oTable.api().row($(this).parents('tr')).data();
            var id = data.email;
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
            closeInSeconds: 10, // auto close after defined seconds, 0 never close
            icon: "warning" // put icon before the message, use the font Awesone icon (fa-[*])
        });        

    };
  //添加操作
	var AddUsers = function(){
		
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"manager/addUsers", 
         "data": $('#addUsersForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 handleAlerts(resp.info,"success","");		            	 
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
		$('#add_users').modal('hide');
    };
    
    var AddUsersValidation = function() {
    	var URL =  rootURI+"storeManager/checkEmail?rand="+Math.random();
        var form = $('#addUsersForm');
        var errorDiv = $('.alert-danger', form);            
        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {
             password: {
        		required: true,
        		minlength:6,
        		maxlength:12,
    				},
        	 email:  {
             	required: true,
            	remote: {
            	    url:URL,     //后台处理程序
            	    type: "post",               //数据发送方式
            	    dataType: "json",           //接受数据格式   
            	    data: {                     //要传递的数据
            	    	email: function() {
            	    		return $("#addUsersForm input[name='email']").val();
            	        }
            	    }
            	}
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
            	AddUsers();
            }
        });
    };
    
	//编辑表单提交操作
	var EditUsers= function() {
	  $.ajax( {
         "dataType": 'json', 
         "type": "POST", 
         "url": rootURI+"manager/editUsers", 
         "data" :$('#editUsersForm').serializeJson(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){
					 selected=[];
	            	 oTable.api().draw();
	            	 handleAlerts(resp.info,"success","");
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
	  $('#edit_users').modal('hide');
	};
		
            
	var EditUsersValidation = function() {
		var form = $('#editUsersForm');
		var errorDiv = $('.alert-danger', form);            
		form.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block help-block-error', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",  // validate all fields including form hidden input                
			rules: {
				adminId: {
					required: true,
					minlength:4,
            			},
            	password: {
            		minlength:6,
            		maxlength:12,
					},
				email: {
					required: true,
					email:true,
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
        	EditUsers();
        }
    });
};
    

    return {
        //main function to initiate the module
        init: function (rootPath,locale_value) {
        	rootURI=rootPath;
        	locale=locale_value;
        	handleTable();  
        	AddUsersValidation();
        	EditUsersValidation();        	
        }

    };

}();
