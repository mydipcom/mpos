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
    
    $.validator.addMethod("sameArraySize", function(value, element) {   
        var arrSize = 0;
        var attrVal=jQuery.trim(value);
        if(attrVal!=""){
        	arrSize=attrVal.split(",").length;
        }
        var isSame=true;
        $.each( $(element.form).find(":text[name^='values_']"), function (index, obj) {
        	attrVal=jQuery.trim(obj.value);
        	if(attrVal!=""){
	        	var length=attrVal.split(",").length;
	        	if(length>0){
		        	if(arrSize!=length){	        		
		            	isSame=false;	            	
		        	}
	        	}
        	}
        });                       
        return this.optional(element) || isSame;
    }, "Please enter the attribute value with the same options size in Multi-language Form Items.");
    
})(jQuery);
var locale = "en_US";
var rootURI="/";
var CategoryTable = function () {
	var oTable;
	var selected = [];
	var attTable;
	var attSelected = [];	
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
            "dom": "tr<'row'<'col-md-6'i><'col-md-6'p>>",
            "oLanguage": {
                "sProcessing": loadProperties("dataTable.page.process",locale),                
                "sZeroRecords":loadProperties("dataTable.page.data.zero",locale),
                "sEmptyTable": loadProperties("dataTable.page.data.empty",locale),
                "sInfo": loadProperties("dataTable.page.info",locale),
                "sInfoEmpty":loadProperties("dataTable.page.info.empty",locale),
            },
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
                    	return '<div class="actions"><a class="btn btn-sm dark" data-toggle="modal"  href="#view_attribute" id="openrluesviewmodal">'+loadProperties("cate.page.attr",locale)+'</a></div>';
                    },
                    'class':'center'
                }
            ],
            "columns": [
               {"orderable": false },
	           //{ data: "categoryId"},
	           { data: "name" },
	           { data: "content" },
	           { 'render':function(data,type,row){
	               	if(row.type==0){
	            		return loadProperties("cate.page.spec.group",locale);
	            	}
	            	else{
	            		return  loadProperties("cate.page.order.group",locale);
	            	}	                	
	               } 
	           },
	           { "class":"center"}
	          ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"category/categoryList?rand="+Math.random()

		});
		
		$("#CloneSelectedCategory").on("click",function(){
			if(selected.length!=1){
				handleAlerts(loadProperties("error.clone.select",locale),"warning","");			
				return false;				
			}
			else{
				$.ajax( {
	             "dataType": 'json', 
	             "type": "GET", 
	             "url": rootURI+"category/clone/"+selected.join(), 
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
			}
		});

		//打开删除对话框前判断是否已选择要删除的行
		$("#openDeleteCategoryModal").on("click",function(event){
			if(selected.length==0){
				handleAlerts(loadProperties("error.delete.select",locale),"warning","");				
				return false;
			}
		});		

		//删除分类操作
		$('#deleteBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "DELETE", 
             "url": rootURI+"category/delete/"+selected.join(), 
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
		
		//打开编辑分类创窗口
		$("#openEditCategoryModal").on("click",function(event){
			if(selected.length!=1){
				handleAlerts(loadProperties("error.edit.select",locale),"warning","");			
				return false;				
			}
			else{
				var checkedObj=$("tr input:checked");
				var data = oTable.api().row(checkedObj.parents('tr')).data();
								
				var categoryId = data.categoryId;
	            var name  = data.name;
	            var content  = data.content;
	            var type = data.type;
	            var status = data.status;
	            var categoryNameLocaleList=data.categoryName_locale;
	            var categoryDescrLocaleList=data.categoryDescr_locale;
	            $("#editCategoryForm :radio").removeAttr("checked");
	            $("#editCategoryForm :radio").parents('span').removeClass("checked");
	            
	            $("#editCategoryForm input[name='storeId']").val(data.storeId);
	            $("#editCategoryForm input[name='categoryId']").val(categoryId);
	            $("#editCategoryForm input[name='storeId']").val(data.storeId);
	            
	            $("#editCategoryForm input[name='name']").val(name);	            
	            $.each(categoryNameLocaleList, function (index, categoryNameLocale) {
	            	 $.each($("#editCategoryForm input[name^='categoryName_locale'][name$='.language.id']"), function (index, obj) {
	            		 if($(obj).val()==categoryNameLocale.language.id){
	            			 $(obj).nextAll("input[name$='.localeValue']").val(categoryNameLocale.localeValue);
	            			 $(obj).nextAll("input[name$='.localeId']").val(categoryNameLocale.localeId);
	            		 }
	            	 });
				});	            	            	            
	            
	            $("#editCategoryForm input[name='content']").val(content);
	            $.each(categoryDescrLocaleList, function (index, categoryDescrLocale) {
	            	 $.each($("#editCategoryForm input[name^='categoryDescr_locale'][name$='.language.id']"), function (index, obj) {
	            		 if($(obj).val()==categoryDescrLocale.language.id){
	            			 $(obj).nextAll("input[name$='.localeValue']").val(categoryDescrLocale.localeValue);
	            			 $(obj).nextAll("input[name$='.localeId']").val(categoryDescrLocale.localeId);
	            		 }
	            	 });
				});
	            	            	            
	            $("#editCategoryForm :radio[name='status']").filter("[value='"+status+"']").attr("checked","true");
	            $("#editCategoryForm :radio[name='status']").filter("[value='"+status+"']").parents('span').addClass("checked");
	            $("#editCategoryForm :radio[name='type']").filter("[value='"+type+"']").attr("checked","true");
	            $("#editCategoryForm :radio[name='type']").filter("[value='"+type+"']").parents('span').addClass("checked");
	            
	            //移除复选框择中状态
	            checkedObj.parents('span').removeClass("checked");
	            checkedObj.parents('tr').removeClass("active");
	            checkedObj.removeAttr("checked");
	            selected=[];
			}
		});
						
		
		//搜索表单提交操作
		$("#searchForm").on("submit", function(event) {
			event.preventDefault();
			var jsonData=$(this).serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);		
			$("#addCategoryForm input[name='storeId']").val($("#storeId").val());
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
		
		table.on('click', 'tbody tr a',function(){
	           var data = oTable.api().row($(this).parents('tr')).data();
	           var categoryId=data.categoryId;
	           var categoryType=data.type;
	           if(attTable!=null){
	        	   attTable.fnDestroy();
	        	   $("#addAttributeForm input[name='categoryId.categoryId']").val(categoryId);
	        	   $("#addAttributeForm input[name='categoryId.type']").val(categoryType);
	        	   $("#editAttributeForm input[name='categoryId.categoryId']").val(categoryId);
	        	   $("#editAttributeForm input[name='categoryId.type']").val(categoryType);
	        	   viewTable(categoryId); 
	           }else{	        	   
	        	   $("#addAttributeForm input[name='categoryId.categoryId']").val(categoryId);
	        	   $("#addAttributeForm input[name='categoryId.type']").val(categoryType);
	        	   $("#editAttributeForm input[name='categoryId.categoryId']").val(categoryId);
	        	   $("#editAttributeForm input[name='categoryId.type']").val(categoryType);
	        	   viewTable(categoryId);
	           }
	     });
		
		//--------------------------end category---------------------------------------------
		
		//-------------------------begin---attribute-----------------------------------
		//数据载入		
		var viewTable = function(categoryId){			
			attTable = $('#att_table').dataTable({
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
		        "dom": "tr<'row'<'col-md-6'i><'col-md-6'p>>",
	            "oLanguage": {
	                "sProcessing": loadProperties("dataTable.page.process",locale),                
	                "sZeroRecords":loadProperties("dataTable.page.data.zero",locale),
	                "sEmptyTable": loadProperties("dataTable.page.data.empty",locale),
	                "sInfo": loadProperties("dataTable.page.info",locale),
	                "sInfoEmpty":loadProperties("dataTable.page.info.empty",locale),
	            },
		        "columnDefs": [
		          {                    
                    'targets': 0,   
                    'render':function(data,type,row){
                    	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
                     }                                        
                  }
		        ],
		        "columns": [
		               {"orderable": false },		 	           
		 	           { /*title: "Title",*/   data: "title" },
		 	           { /*title: "Type",*/  
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
		 	           {/* title: "Sort",*/ data: "sort" },		 	           
		 	           { /*title: "Required",*/ data: "required" },
		 	           { /*title: "Values",*/data: "values"}
		 	        ],
     	        "serverSide": true,
     	        "serverMethod": "GET",
     	        "ajaxSource": rootURI+"category/attributeList/"+categoryId+"?rand="+Math.random()
			});									
		};				
		
		//属性全选
        $(".attr-checkable").on('change',function () {
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
        
        //属性单选
        $("#att_table").on('change', 'tbody tr .checkboxes', function () {
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
		
		
		//打开添加分类属性窗口
		$("#openAddAttributeModal").on("click",function(event){
			$("#addAttributeForm :text[name^='values']").val("");
			$("#addAttributeForm :text[name^='values']").select2({tags:[],formatNoMatches: function () { return "&nbsp;"; }});
			var groupType=$("#addAttributeForm input[name='categoryId.type']").val();
			if(groupType=="1"){
				$("#addAttrType").hide();
				$("#addCategoryForm :radio[name='type']").filter("[value='"+groupType+"']").attr("checked","true");
	            $("#addCategoryForm :radio[name='type']").filter("[value='"+groupType+"']").parents('span').addClass("checked");
			}
			else{
				$("#addAttrType").show();
			}
			//当添加属性窗口关闭时隐藏属性输入框的下拉菜单
            $('#add_attribute').on("hide.bs.modal", function () {            	
            	$.each($("#addAttributeForm :text[name^='values']"), function (index, obj) {
            		$(obj).select2("close");
            	});
    		});
		});
		
		//打开分类属性编辑窗口
		$("#openEditAttributeModal").on("click",function(event){
			if(attSelected.length!=1){
				handleAlerts(loadProperties("error.edit.select",locale),"warning","#view_attributeMsg");		
				return false;				
			}
			else{
				var groupType=$("#editAttributeForm input[name='categoryId.type']").val();
				if(groupType=="1"){
					$("#editAttrType").hide();
					$("#editCategoryForm :radio[name='type']").filter("[value='"+groupType+"']").attr("checked","true");
		            $("#editCategoryForm :radio[name='type']").filter("[value='"+groupType+"']").parents('span').addClass("checked");
				}
				else{
					$("#editAttrType").show();
				}
				
				var checkedObj=$("tr input:checked");
				var data = attTable.api().row(checkedObj.parents('tr')).data();
				
				var attributeId = data.attributeId;
				var required = data.required;
				var sort = data.sort;
				var title = data.title;
				var values = data.values;
				var type = data.type;
				var titleLocaleList=data.title_locale;
				var valuesLocaleList=data.values_locale;
	            
				$("#editAttributeForm input[name='attributeId']").val(attributeId);					
				$("#editAttributeForm :radio").removeAttr("checked");
		        $("#editAttributeForm :radio").parents('span').removeClass("checked");
		        $("#editAttributeForm :radio[name='type']").filter("[value='"+type+"']").attr("checked","true");
		        $("#editAttributeForm :radio[name='type']").filter("[value='"+type+"']").parents('span').addClass("checked");
		        $("#editAttributeForm :radio[name='required']").filter("[value='"+required+"']").attr("checked","true");
		        $("#editAttributeForm :radio[name='required']").filter("[value='"+required+"']").parents('span').addClass("checked");
		           
		        $("#editAttributeForm input[name='sort']").val(sort);
		        $("#editAttributeForm input[name='title']").val(title);		        	        		            	            	           		        		                   
	            $.each(titleLocaleList, function (index, titleLocale) {
	            	 $.each($("#editAttributeForm input[name^='title_locale'][name$='.language.id']"), function (index, obj) {
	            		 if($(obj).val()==titleLocale.language.id){	            			 
	            			 $(obj).nextAll("input[name$='.localeValue']").val(titleLocale.localeValue);
	            			 $(obj).nextAll("input[name$='.localeId']").val(titleLocale.localeId);
	            			 	            			 
	            		 }
	            	 });
				});	
	            
	            $("#editAttributeForm input[name='values']").val(values);
	            $.each(valuesLocaleList, function (index, valuesLocale) {
	            	 $.each($("#editAttributeForm input[name^='values_locale'][name$='.language.id']"), function (index, obj) {
	            		 if($(obj).val()==valuesLocale.language.id){	            			 
	            			 $(obj).nextAll("input[name$='.localeValue']").val(valuesLocale.localeValue);	            			 	            			 
	            		 }
	            	 });
				});
	            $("#editAttributeForm :text[name^='values']").select2({tags:[],formatNoMatches: function () { return "&nbsp;"; }});
	            //当编辑属性窗口关闭时隐藏属性输入框的下拉菜单
	            $('#edit_attribute').on("hide.bs.modal", function () {
	            	$.each($("#editAttributeForm :text[name^='values']"), function (index, obj) {
	            		$(obj).select2("close");
	            	});
	            	
	    		});	
	            
	            //移除复选框择中状态
	            checkedObj.parents('span').removeClass("checked");
	            checkedObj.parents('tr').removeClass("active");
	            checkedObj.removeAttr("checked");
	            selected=[];
	            attSelected=[];
			}
		});
		
		
		
		//打开删除属性对话框前判断是否已选择要删除的行
		$("#openDeleteAttributeModal").on("click",function(event){
			if(attSelected.length==0){
				handleAlerts(loadProperties("error.delete.select",locale),"warning","#view_attributeMsg");				
				return false;
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
            		 var infoType = "danger";
					 if(data.status){
						 attSelected=[];
						 attTable.api().draw();	
						 attTable.$('th span').removeClass();
						 infoType = "success";
					 }
					handleAlerts(data.info,infoType,"#view_attributeMsg");
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        });        		
	};
	
	
	
	//添加操作
	var ajaxAddCategory=function(){	
		$.ajax( {
		"traditional":true,
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/addCategory", 
         "data": $('#addCategoryForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){ 
        		 var infoType = "danger";
        		 if(resp.status){						 
	            	 oTable.api().draw();
	            	 $('#addCategoryForm')[0].reset();
	            	 $("#add_category").modal('hide');
	            	 infoType = "success";
				 }
			  handleAlerts(resp.info,infoType,"#addFormMsg");						 
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });
    };
    
    
	//编辑分类操作
	var ajaxEditCategory=function(){	
		$.ajax({
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/editCategory", 
         "data": $('#editCategoryForm').serialize(),
         "success": function(resp,status){
        	 if(status == "success"){ 
        		 var infoType = "danger";
        		 if(resp.status){
        			 selected=[];
        			 oTable.api().draw();
        			 $('#editCategoryForm')[0].reset();
        			 $("#edit_category").modal('hide');
        			 infoType = "success";
				 }
			  handleAlerts(resp.info,infoType,"#editCategoryFormMsg");						 
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });		
    };
    
	//添加属性异步操作
	var ajaxAddAttribute=function(){
		$.ajax( {
		"traditional":true,
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/addAttribute", 
         "data": $("#addAttributeForm").serialize(),
         "success": function(resp,status){
        	 if(status == "success"){
        		 var infoType = "danger";
        		 if(resp.status){
        			 attTable.api().draw();
        			 $("#addAttributeForm")[0].reset();
	            	 $("#add_attribute").modal('hide');
	            	 infoType = "success";
				 }
        		 handleAlerts(resp.info,infoType,"#addAttributeFormMsg");						 
			}             	 
         },
         "error":function(XMLHttpRequest, textStatus, errorThrown){
        	 alert(errorThrown);
         }
       });		
    };
    
	//编辑属性异步操作
	var ajaxEditAttribute=function(){	
		$.ajax( {
         "dataType": 'json', 
         "type":'POST', 
         "url": rootURI+"category/editAttribute", 
         "data": $("#editAttributeForm").serialize(),
         "success": function(resp,status){
        	 if(status == "success"){ 
        		 var infoType="danger";
        		 if(resp.status){
        			 attSelected = [];
        			 attTable.api().draw();
        			 $("#editAttributeForm")[0].reset();
	            	 $("#edit_attribute").modal('hide');
	            	 infoType="success";
				 }
					handleAlerts(resp.info,infoType,"#editAttributeFormMsg");						 
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
    
    //处理分类表单验证方法
    var categoryFormValidation = function(formType) {
    	    var validationForm; 
	    	if(formType==0){
	    		validationForm=$('#addCategoryForm');
	        }
	        else{
	        	validationForm=$('#editCategoryForm');
	        }            
            var errorDiv = $('.alert-danger', validationForm);
            validationForm.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input                
                rules: {
                    name: {
                        minlength: 2,
                        required: true
                    }
                },
                invalidHandler: function (event, validator) { //display error alert on form submit 
                	validationForm.find("a[href$='_category_tab1']").trigger("click");
                    errorDiv.show();                    
                },
                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },
                unhighlight: function (element) { // revert the change done by hightlight
                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                },
                onfocusout: function (element) { // hightlight error inputs
                    $(element).valid();
                },
                success: function (label) {
                    label.closest('.form-group').removeClass('has-error'); // set success class to the control group
                },
                submitHandler: function (form) {                	
                    errorDiv.hide();
                    if(formType==0){
                    	ajaxAddCategory();
                    }
                    else{
                    	ajaxEditCategory();
                    }
                }
            });
    };
    
    
    //处理分类属性表单
    var AttributeFormValidation = function(formType) {
        var addform;
        var validationForm; 
    	if(formType==0){
    		validationForm=$('#addAttributeForm');
        }
        else{
        	validationForm=$('#editAttributeForm');
        }  
        var errorDiv = $('.alert-danger', validationForm);  
                
        validationForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {
                title: {
                    required: true
                },
                values: {
                	required: true,
                	sameArraySize:true
                },
                sort:{
                	required: true,
                	digits:true
                }
            },
            invalidHandler: function (event, validator) { //display error alert on form submit 
            	validationForm.find("a[href$='_attribute_tab1']").trigger("click");
                errorDiv.show();                    
            },
            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            onfocusout: function (element) { // hightlight error inputs
                $(element).valid();
            },
            success: function (label) {
                label.closest('.form-group').removeClass('has-error'); // set success class to the control group
            },
            submitHandler: function (form) {                	
                errorDiv.hide();
                if(formType==0){
                	ajaxAddAttribute(); 
                }else{                
                	ajaxEditAttribute();
                }
                                   
            }
        });
    };
    
    return {
        //main function to initiate the module
        init: function (rootPath,locale_value) {
        	rootURI=rootPath;
        	locale = locale_value;
        	handleTable();  
        	categoryFormValidation(0);
        	categoryFormValidation(1);        	
        	AttributeFormValidation(0);
        	AttributeFormValidation(1);
        	$(".select2_sample3").select2({tags:[],formatNoMatches: function () { return "&nbsp;"; }});
        }
    };

}();