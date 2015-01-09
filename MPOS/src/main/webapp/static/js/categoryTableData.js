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


/**
 * 加载多语言化
 */
var loadLocal = function(type,entityId){
	var res = "";
	$.ajax({
        "dataType": 'json', 
        "type":'GET',
        "async":false,
        "url": rootURI+"category/getLocal/"+type+"/"+entityId+"?rand="+Math.random(), 
        "success": function(resp,status){
       	 if(status == "success"){  
       		 if(resp.status){
       			 res = resp;
				}
			}             	 
        }
      });
	return res;
}

var setLocal = function(localTitles,id,formId){
	var setId = "First"
	if(id==1){
		setId = "First";
	}else if(id=2){
		setId = "Second";
	}
	if(localTitles!=""&&localTitles.length!=null&&localTitles.length>0){
		for (var int = 0; int < localTitles.length; int++) {
			var lan = localTitles[int];
			if(id==1){
				$("#"+formId+" input[name='localizedField"+setId+"["+int+"].localeValue']").val(lan.localeValue);
			}else{
				$("#"+formId+" textarea[name='localizedField"+setId+"["+int+"].localeValue']").val(lan.localeValue);
			}
			
			$("#"+formId+" input[name='localizedField"+setId+"["+int+"].language.id']").val(lan.languageId);
			$("#"+formId+" input[name='localizedField"+setId+"["+int+"].tableField']").val(lan.tableField);
			$("#"+formId+" input[name='localizedField"+setId+"["+int+"].localeId']").val(lan.localeId);
		}
	}
}

var setCopyLocal = function(localTitles,id,formId,str){
	var setId = "First"
	if(id==1){
		setId = "First";
	}else if(id=2){
		setId = "Second";
	}
	if(localTitles!=""&&localTitles.length!=null&&localTitles.length>0){
		for (var int = 0; int < localTitles.length; int++) {
			var lan = localTitles[int];
			if(id==1){
				$("#"+formId+" input[name='localizedField"+setId+"["+int+"].localeValue']").val(str + " " +lan.localeValue);
			}else{
				$("#"+formId+" textarea[name='localizedField"+setId+"["+int+"].localeValue']").val(str + " " + lan.localeValue);
			}
			
			$("#"+formId+" input[name='localizedField"+setId+"["+int+"].language.id']").val(lan.languageId);
			$("#"+formId+" input[name='localizedField"+setId+"["+int+"].tableField']").val(lan.tableField);
			$("#"+formId+" input[name='localizedField"+setId+"["+int+"].localeId']").val(lan.localeId);
		}
	}
}

var rootURI="/";
var CategoryTable = function () {
	var oTable;
	var selected = [];
	var attTable;
	var attSelected = [];
	var att_cate_id = '';
	var handleTable = function () {
		
		//-----------------------begin category-----------------------------------
		var table=$('#category_table');
		//加载分类信息
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
                    	return '<div class="actions"><a class="btn btn-sm dark" data-toggle="modal"  href="#view_attribute" id="openrluesviewmodal">view_attribute</a></div>';
                    },
                    'class':'center'
                }
            ],
            "columns": [
               {"orderable": false },
	           { title: "ID",   data: "categoryId"},
	           { title: "Name",    data: "name" },
	           { title: "Description",  data: "content" },
	           /*{ title: "Status",    data: "status" },*/
	           { title: "Action" ,"class":"center"}
	          ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"category/categoryList?rand="+Math.random()

		});		

		//打开删除对话框前判断是否已选择要删除的行
		$("#openDeleteCategoryModal").on("click",function(event){
			if(selected.length==0){
				handleAlerts("Please select the rows which you want to delete.","warning","");				
				return false;
			}
		});
		
		$('#view_attribute').on('shown',function(e){
			$('#view_attribute').css({
				"margin-top": function () {
					return  -($(window).height()/3 + 50 );
				}
			});
		 })

		
		//删除分类操作
		$('#deleteBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "DELETE", 
             "url": rootURI+"category/category/"+selected.join(), 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
					 }
					 else{
						 handleAlerts("Failed to delete the data. " +data.info,"danger","");
					 }
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        });  
		
		//打开编辑分类创窗口
		$("#openEditCategoryModal").on("click",function(event){
			if(selected.length!=1){
				handleAlerts("One and only one row can be edited.","warning","");		
				return false;				
			}
			else{
				var data = oTable.api().row($("tr input:checked").parents('tr')).data();
				$("tr input:checked").parents('span').removeClass("checked");
				$("tr input:checked").removeAttr("checked");
				var categoryId = data.categoryId;
	            var name  = data.name;
	            var content  = data.content;
	            var status = data.status;
	            $("#editCategoryForm :radio").removeAttr("checked");
	            $("#editCategoryForm :radio").parents('span').removeClass("checked");
	            
	            $("#editCategoryForm input[name='category.categoryId']").val(categoryId);
	            
	            $("#editCategoryForm input[name='category.name']").val(name);
	            $("#editCategoryForm textarea[name='category.content']").val(content);
	            	            	            
	            $("#editCategoryForm :radio[name='category.status']").filter("[value='"+status+"']").attr("checked","true");
	            $("#editCategoryForm :radio[name='category.status']").filter("[value='"+status+"']").parents('span').addClass("checked");
	            selected = [];
	            var list = loadLocal(1,categoryId);
	            setLocal(list.localName,1,"editCategoryForm");
	            setLocal(list.localContent,2,"editCategoryForm");
			}
		});
		
		
		//打开复制窗口并赋值
		$("#openCopyAddCategoryModal").on("click",function(event){
			if(selected.length!=1){
				handleAlerts("One and only one row can be copy add.","warning","");
				return false;				
			}
			else{
				var data = oTable.api().row($("tr input:checked").parents('tr')).data();
				$("tr input:checked").parents('span').removeClass("checked");
				$("tr input:checked").removeAttr("checked");
	            var name  = data.name;
	            var content  = data.content;
	            var status = data.status;
	            $("#copyCategoryForm input[name='id']").val(data.categoryId);
	            var str = "copy of ";
	            //$("#copy_add_title").html("copy of " + name);
	            $("#copyCategoryForm :radio").removeAttr("checked");
	            $("#copyCategoryForm :radio").parents('span').removeClass("checked");
	            
	            $("#copyCategoryForm input[name='category.name']").val(str + name);
	            $("#copyCategoryForm textarea[name='category.content']").val(str + content);
	            	            	            
	            $("#copyCategoryForm :radio[name='category.status']").filter("[value='"+status+"']").attr("checked","true");
	            $("#copyCategoryForm :radio[name='category.status']").filter("[value='"+status+"']").parents('span').addClass("checked");
	            selected=[];
	            var list = loadLocal(1,data.categoryId);
	            setCopyLocal(list.localName,1,"copyCategoryForm",str);
	            setCopyLocal(list.localContent,2,"copyCategoryForm",str);
			}
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
	            	var id = data.categoryId;
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
            var id = data.categoryId;
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
		
		
		//--------------------------end category---------------------------------------------
		
		//-------------------------begin---attribute-----------------------------------
		//数据载入
		var logTable=$('#att_table');
		var viewTable = function(ids){
			att_cate_id = ids;
			attTable = logTable.dataTable({
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
		        "displayLength": 5,
		        "dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",
		        "columnDefs": [{                    
                    'targets': 0,   
                    'render':function(data,type,row){
                    	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
                    },
                    //'defaultContent':'<div class="checker"><span><input type="checkbox" class="checkboxes" value="1"/></span></div>'                    
                	}
		        ],
		        "columns": [
		               {"orderable": false },
		 	           { title: "ID",   data: "attributeId" },
		 	           { title: "Title",   data: "title" },
		 	           { title: "Type",  
		 	        	'render':function(data,type,row){
		 	        				var tem = row.type;
		 	        				var str = '';
		 	        				if(tem==0){
		 	        					str = 'Editbox';
		 	        				}else if(tem==1){
		 	        					str = 'Radio Button';
		 	        				}else if(tem==2){
		 	        					str = 'Checkbox';
		 	        				}else{
		 	        					str = 'Dropdown List';
		 	        				}
		 	        				return str;
		 	        			}
		 	           },
		 	           { title: "Content", data: "content"},
		 	           { title: "Sort", data: "sort" }
		 	        ],
     	        "serverSide": true,
     	        "serverMethod": "GET",
     	        "ajaxSource": rootURI+"category/attributeList/"+ids+"?rand="+Math.random()
			});	
		};
		
		
		table.on('click', 'tbody tr a',function(){
	           var data = oTable.api().row($(this).parents('tr')).data();
	           var ids=data.categoryId;
	           if(attTable!=null){
	        	   attTable.fnDestroy();
	        	   $("#addAttributeForm input[name='attribute.categoryId.categoryId']").val(ids);
	        	   viewTable(ids); 
	           }else{
	        	   viewTable(ids);
	        	   $("#addAttributeForm input[name='attribute.categoryId.categoryId']").val(ids);
	           }
	          });
		
		//打开分类属性编辑窗口
		$("#openEditAttributeModal").on("click",function(event){
			if(attSelected.length!=1){
				handleAlerts("One and only one row can be add attribute.","warning","#view_attributeMsg");		
				return false;				
			}
			else{
				var data = attTable.api().row($("tr input:checked").parents('tr')).data();
				$("tr input:checked").parents('span').removeClass("checked");
				$("tr input:checked").removeAttr("checked");
				var attributeId = data.attributeId;
				var sort = data.sort;
				var title = data.title;
				var content = data.content;
				var type = data.type;
				$("#editAttributeForm input[name='attribute.attributeId']").val(attributeId);
				$("#editAttributeForm input[name='attribute.categoryId.categoryId']").val(att_cate_id);
				if(type==0){
					$("#edit_required").html("");
					$("#editAttributeForm textarea[name='attribute.content']").rules("remove","required");
				}
				$("#editAttributeForm :radio").removeAttr("checked");
		        $("#editAttributeForm :radio").parents('span').removeClass("checked");
		           
		        $("#editAttributeForm input[name='attribute.sort']").val(sort);
		        $("#editAttributeForm input[name='attribute.title']").val(title);
		        $("#editAttributeForm textarea[name='attribute.content']").val(content);
		        //$(".form-control tags").val(content);
		            	            	            
		        $("#editAttributeForm :radio[name='attribute.type']").filter("[value='"+type+"']").attr("checked","true");
		        $("#editAttributeForm :radio[name='attribute.type']").filter("[value='"+type+"']").parents('span').addClass("checked");
		        attSelected = [];
		        var list = loadLocal(2,attributeId);
	            setLocal(list.localTitle,1,"editAttributeForm");
	            setLocal(list.localContent,2,"editAttributeForm");
			}
		});
		
		
		//打开删除属性对话框前判断是否已选择要删除的行
		$("#openDeleteAttributeModal").on("click",function(event){
			if(attSelected.length==0){
				handleAlerts("Please select the rows which you want to delete.","warning","");				
				return false;
			}
		});
		
		$("#addAttributeForm :radio").on("change",function(event){
			if($(this).val()==0){
				$("#add_required").html("");
				$("#addAttributeForm textarea[name='attribute.content']").rules("remove","required");
			}else{
				$("#add_required").html(" * ");
				$("#addAttributeForm textarea[name='attribute.content']").rules("add",{required: true});
			}
			
		});
		
		$("#editAttributeForm :radio").on("change",function(event){
			if($(this).val()==0){
				$("#edit_required").html("");
				$("#editAttributeForm textarea[name='attribute.content']").rules("remove","required");
			}else{
				$("#edit_required").html(" * ");
				$("#editAttributeForm textarea[name='attribute.content']").rules("add",{required: true});
			}
			
		});
		
		//删除分类操作
		$('#deleteAttBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "DELETE", 
             "url": rootURI+"category/attribute/"+attSelected.join(),
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 attSelected=[];
						 attTable.api().draw();
						 attTable.$('th span').removeClass();
					 }
					 else{
						 handleAlerts("Failed to delete the data. " +data.info,"danger","#view_attributeMsg");
					 }
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        });
        
		//全选
        $(".group-checkable").on('change',function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            attSelected=[];
            if(checked){            	
	            var api=attTable.api();            
	            jQuery(set).each(function () {            	
	            	var data = api.row($(this).parents('tr')).data();
	            	var id = data.attributeId;
	                var index = $.inArray(id, attSelected);
	                attSelected.push( id );
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
        logTable.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");            
            var data = attTable.api().row($(this).parents('tr')).data();
            var id = data.attributeId;
            var index = $.inArray(id, attSelected);     
            if ( index === -1 ) {
            	attSelected.push( id );
                $(this).parents('span').addClass("checked");
                $(this).attr("checked","checked");
            } else {
            	attSelected.splice( index, 1 );
                $(this).parents('span').removeClass("checked");
                $(this).removeAttr("checked");
            }
        });
        
        
		
        
	};
	
	
	
	//添加操作
	var ajaxAddCategory=function(form){	
		$.ajax( {
		"traditional":true,
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/addCategory", 
         "data": form.serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 form[0].reset();
	            	 $("#add_category").modal('hide');
	            	 handleAlerts("Added the data successfully.","success","#addFormMsg");		            	 
				 }
				 else{
					 handleAlerts("Failed to add the data.","danger","#addFormMsg");						 
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
    };
    
  //复制添加操作
	var ajaxCopyCategory=function(form){	
		$.ajax( {
		"traditional":true,
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/addCategory", 
         "data": form.serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 form[0].reset();
	            	 selected=[];
	            	 $("#copy_add_category").modal('hide');
	            	 handleAlerts("Added the data successfully.","success","#copyFormMsg");		            	 
				 }
				 else{
					 handleAlerts("Failed to add the data.","danger","#copyFormMsg");						 
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });		
    };
    
	//编辑分类操作
	var ajaxEditCategory=function(){	
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/editCategory", 
         "data": $('#editCategoryForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){
        			 selected=[];
        			 oTable.api().draw();
        			 $('#editCategoryForm')[0].reset();
        			 $("#edit_category").modal('hide');
	            	 handleAlerts("Edit the data successfully.","success","#editFormMsg");		            	 
				 }
				 else{
					 handleAlerts("Failed to edit the data.","danger","#editCategoryFormMsg");						 
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });		
    };
    
	//添加属性异步操作
	var ajaxAddAttribute=function(from){
		$.ajax( {
		"traditional":true,
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/addAttribute", 
         "data": from.serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){
        			 attTable.api().draw();
	            	 from[0].reset();
	            	 $("#add_attribute").modal('hide');
	            	 handleAlerts("Added the data successfully.","success","#addAttributeFormMsg");		            	 
				 }
				 else{
					 handleAlerts("Failed to add the data.","danger","#addAttributeFormMsg");						 
				 }
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });		
    };
    
	//编辑属性异步操作
	var ajaxEditAttribute=function(form){	
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/editAttribute", 
         "data": form.serialize(),
         "success": function(resp,status){
        	 if(status == "success"){  
        		 if(resp.status){
        			 attSelected = [];
        			 attTable.api().draw();
        			 $("#editAttributeForm")[0].reset();
	            	 $("#edit_attribute").modal('hide');
	            	 handleAlerts("Edit the data successfully.","success","#editAttributeFormMsg");		            	 
				 }
				 else{
					 handleAlerts("Failed to edit the data.","danger","#editAttributeFormMsg");						 
				 }
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
    var addFormValidation = function() {
            var addform = $('#addCategoryForm');
            var errorDiv = $('.alert-danger', addform);            

            addform.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input                
                rules: {
                    "category.name": {
                        minlength: 2,
                        required: true
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
                    ajaxAddCategory(addform);                    
                }
            });
    };
    
  //处理表单验证方法
    var editFormValidation = function() {
            var addform = $('#editCategoryForm');
            var errorDiv = $('.alert-danger', addform);            

            addform.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input                
                rules: {
                    "category.name": {
                        minlength: 2,
                        required: true
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
                    ajaxEditCategory();                    
                }
            });
    };
    
    var copyFormValidation = function() {
        var copyform = $('#copyCategoryForm');
        var errorDiv = $('.alert-danger', copyform);            

        copyform.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {
                "category.name": {
                    minlength: 2,
                    required: true
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
                ajaxCopyCategory(copyform);                      
            }
        });
    };
    //处理分类属性表单
    var AttributeFormValidation = function(aform,type) {
        var addform = aform;
        var errorDiv = $('.alert-danger', addform);            

        addform.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {
                "attribute.title": {
                    required: true
                },
                "attribute.content": {
                	required: true
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
                if(type=='1'){
                	ajaxAddAttribute(addform); 
                }
                if(type=='2'){
                	ajaxEditAttribute(addform);
                }
                                   
            }
        });
    };
    
    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable();  
        	addFormValidation();
        	editFormValidation();
        	copyFormValidation();
        	AttributeFormValidation($("#addAttributeForm"),'1');
        	AttributeFormValidation($("#editAttributeForm"),'2');
        }

    };

}();