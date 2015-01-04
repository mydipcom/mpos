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

function setValue(id){
	//var addc = $("#addAttributeForm input[name='content']").val();
	//var editc = $("#edditAttributeForm input[name='content']").val();
	//category copy
	$("#cate_copyt_"+id).val($("#cate_copy_title").val());
	$("#cate_copyc_"+id).val($("#cate_copy_con").val());
	$("#cate_copyt_"+id).val($("#cate_copy_title").val());
	$("#cate_copyc_"+id).val($("#cate_copy_con").val());
	//category add
	$("#cate_addt_"+id).val($("#cate_add_title").val());
	$("#cate_addc_"+id).val($("#cate_add_con").val());
	$("#cate_editt_"+id).val($("#cate_edit_title").val());
	$("#cate_editc_"+id).val($("#cate_edit_con").val());
	//attribute add
	$("#addt_"+id).val($("#add_title").val());
	$("#addc_"+id).val($("#add_con").val());
	$("#editt_"+id).val($("#edit_title").val());
	$("#editc_"+id).val($("#edit_con").val());
}
/**
 * 编辑属性 表单赋值
 * @param list
 */
function setFromValue(list){
	var le = list.length/2;
	if(list!=null&&list.length>0){
		for (var int = 0; int < le; int++) {
			var lan = list[int];
			$("#editAttributeForm input[name='titles["+int+"].localeValue']").val(lan.localeValue);
			$("#editAttributeForm input[name='titles["+int+"].language.id']").val(lan.languageId);
			$("#editAttributeForm input[name='titles["+int+"].tableField']").val(lan.tableField);
			$("#editAttributeForm input[name='titles["+int+"].localeId']").val(lan.localeId);
		}
		for (var int = le; int < list.length; int++) {
			var lan = list[int];
			var tem = int - le;
			$("#editAttributeForm textarea[name='contents["+tem+"].localeValue']").val(lan.localeValue);
			$("#editAttributeForm input[name='contents["+tem+"].localeValue']").val(lan.localeValue);
			$("#editAttributeForm input[name='contents["+tem+"].language.id']").val(lan.languageId);
			$("#editAttributeForm input[name='contents["+tem+"].tableField']").val(lan.tableField);
			$("#editAttributeForm input[name='contents["+tem+"].localeId']").val(lan.localeId);
		}
	}
	
}
/**
 * 编辑分类表单赋值
 * @param list
 */
function setCateFromValue(list){
	var le = list.length/2;
	if(list!=null&&list.length>0){
		for (var int = 0; int < le; int++) {
			var lan = list[int];
			$("#editCategoryForm input[name='names["+int+"].localeValue']").val(lan.localeValue);
			$("#editCategoryForm input[name='names["+int+"].language.id']").val(lan.languageId);
			$("#editCategoryForm input[name='names["+int+"].tableField']").val(lan.tableField);
			$("#editCategoryForm input[name='names["+int+"].localeId']").val(lan.localeId);
		}
		for (var int = le; int < list.length; int++) {
			var lan = list[int];
			var tem = int - le;
			$("#editCategoryForm textarea[name='contents["+tem+"].localeValue']").val(lan.localeValue);
			$("#editCategoryForm input[name='contents["+tem+"].localeValue']").val(lan.localeValue);
			$("#editCategoryForm input[name='contents["+tem+"].language.id']").val(lan.languageId);
			$("#editCategoryForm input[name='contents["+tem+"].tableField']").val(lan.tableField);
			$("#editCategoryForm input[name='contents["+tem+"].localeId']").val(lan.localeId);
		}
	}
	
}
/**
 * 复制添加分类表单赋值
 * @param list
 */
function setCateCopyFromValue(list){
	var le = list.length/2;
	if(list!=null&&list.length>0){   
		for (var int = 0; int < le; int++) {
			var lan = list[int];
			$("#copyCategoryForm input[name='names["+int+"].localeValue']").val(lan.localeValue);
			$("#copyCategoryForm input[name='names["+int+"].language.id']").val(lan.languageId);
			$("#copyCategoryForm input[name='names["+int+"].tableField']").val(lan.tableField);
		}
		for (var int = le; int < list.length; int++) {
			var lan = list[int];
			var tem = int - le;
			$("#copyCategoryForm textarea[name='contents["+tem+"].localeValue']").val("copy of "+ lan.localeValue);
			$("#copyCategoryForm input[name='contents["+tem+"].localeValue']").val("copy of "+ lan.localeValue);
			$("#copyCategoryForm input[name='contents["+tem+"].localeValue']").val("copy of "+ lan.localeValue);
			$("#copyCategoryForm input[name='contents["+tem+"].language.id']").val(lan.languageId);
			$("#copyCategoryForm input[name='contents["+tem+"].tableField']").val(lan.tableField);
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
				var categoryId = data.categoryId;
	            var name  = data.name;
	            var content  = data.content;
	            var status = data.status;
	            $("#editCategoryForm :radio").removeAttr("checked");
	            $("#editCategoryForm :radio").parents('span').removeClass("checked");
	            
	            $("#editCategoryForm input[name='categoryId']").val(categoryId);
	            
	            $("#editCategoryForm input[name='name']").val(name);
	            $("#editCategoryForm textarea[name='content']").val(content);
	            	            	            
	            $("#editCategoryForm :radio[name='status']").filter("[value='"+status+"']").attr("checked","true");
	            $("#editCategoryForm :radio[name='status']").filter("[value='"+status+"']").parents('span').addClass("checked");
	            
	            $.ajax( {
	                 "dataType": 'json', 
	                 "type":'POST', 
	                 "url": rootURI+"category/getCategory", 
	                 "data": {"categoryId":categoryId},
	                 "success": function(resp,status){
	                	 if(status == "success"){  
	                		 if(resp.status){						 
	                			 setCateFromValue(resp.list);
	        				 }
	        				 else{
	        					 handleAlerts("Failed to add the data.","danger","#editCategoryFormMsg");						 
	        				 }
	        			}             	 
	                 },
	                 "error":function(XMLHttpRequest, textStatus, errorThrown){
	                	 alert(errorThrown);
	                 }
	               });
	            
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
	            var name  = data.name;
	            var content  = data.content;
	            var status = data.status;
	            //$("#copy_add_title").html("copy of " + name);
	            $("#copyCategoryForm :radio").removeAttr("checked");
	            $("#copyCategoryForm :radio").parents('span').removeClass("checked");
	            
	            $("#copyCategoryForm input[name='name']").val("copy of "+ name);
	            $("#copyCategoryForm textarea[name='content']").val("copy of "+ content);
	            	            	            
	            $("#copyCategoryForm :radio[name='status']").filter("[value='"+status+"']").attr("checked","true");
	            $("#copyCategoryForm :radio[name='status']").filter("[value='"+status+"']").parents('span').addClass("checked");
	            $.ajax( {
	                 "dataType": 'json', 
	                 "type":'POST', 
	                 "url": rootURI+"category/getCategory", 
	                 "data": {"categoryId":data.categoryId},
	                 "success": function(resp,status){
	                	 if(status == "success"){  
	                		 if(resp.status){						 
	                			 setCateCopyFromValue(resp.list);
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
	        	   viewTable(ids); 
	           }else{
	        	   viewTable(ids);
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
				var attributeId = data.attributeId;
				var sort = data.sort;
				var title = data.title;
				var content = data.content;
				var type = data.type;
				$("#editAttributeForm input[name='attributeId']").val(attributeId);
				$("#editAttributeForm input[name='categoryId.categoryId']").val(att_cate_id);
				
				$("#editAttributeForm :radio").removeAttr("checked");
		        $("#editAttributeForm :radio").parents('span').removeClass("checked");
		           
		        $("#editAttributeForm input[name='sort']").val(sort);
		        $("#editAttributeForm input[name='title']").val(title);
		        $("#editAttributeForm textarea[name='content']").val(content);
		        //$(".form-control tags").val(content);
		            	            	            
		        $("#editAttributeForm :radio[name='type']").filter("[value='"+type+"']").attr("checked","true");
		        $("#editAttributeForm :radio[name='type']").filter("[value='"+type+"']").parents('span').addClass("checked");
				 $.ajax( {
	                 "dataType": 'json', 
	                 "type":'POST', 
	                 "url": rootURI+"category/getAttribute", 
	                 "data": {"attributeId":attributeId},
	                 "success": function(resp,status){
	                	 if(status == "success"){  
	                		 if(resp.status){						 
	        	            	 setFromValue(resp.list);
	        				 }
	        				 else{
	        					 handleAlerts("Failed to load the data.","danger","#editAttributeFormMsg");						 
	        				 }
	        			}             	 
	                 },
	                 "error":function(XMLHttpRequest, textStatus, errorThrown){
	                	 alert(errorThrown);
	                 }
	               });
			}
		});
		
		//打开删除属性对话框前判断是否已选择要删除的行
		$("#openAddAttributeModal").on("click",function(event){
			if(selected.length==0){
				handleAlerts("Please select the rows which you want to add attribute.","warning","");				
				return false;
			}else{
				var data = oTable.api().row($("tr input:checked").parents('tr')).data();
				var categortyId = data.categoryId;
				$("#addAttributeForm input[name='categoryId.categoryId']").val(categortyId);
			}
		});
		
		//打开删除属性对话框前判断是否已选择要删除的行
		$("#openDeleteAttributeModal").on("click",function(event){
			if(attSelected.length==0){
				handleAlerts("Please select the rows which you want to delete.","warning","");				
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
					 if(data.status){
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
	            	 oTable.api().draw();
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
                    "name": {
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
                    "name": {
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
                "name": {
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
                "title": {
                    minlength: 2,
                    required: true
                },
                "content": {
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