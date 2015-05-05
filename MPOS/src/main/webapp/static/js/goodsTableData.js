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
var GoodsTable = function () {
	var oTable;
	var oLogTable;
	var selected = [];
	var handleTable = function () {				
		var table=$('#goods_table');
		
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
                }
            ],
            "columns": [
               {"orderable": false },
	           /*{ title: "ID",   data: "id"  },
	           { title: "Category Name",   data: "menuname" },
	           { title: "Product Name",  data: "productName"},
	           { title: "Price", data: "price"},
	           { title: "Old Price", data: "oldPrice" },
	           { title: "Recommend",  data: "recommend" },
	           { title: "Sort",    data: "sort"},  */
               {   data: "id"  },
	           {   data: "menuname" },
	           {   data: "productName"},
	           {   data: "price"},
	           {   data: "oldPrice" },
	           {   data: "isPut",
	        	   'render':function(data,type,row){
	        		   var temp ="否";
	        		   if(data=="1"){
	        			   temp ="是";
	        		   }
               	return temp;
               }
	           },
	           {   data: "sort"},  
	           //  { title: "Status",    data: "status"},  
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"goods/goodslist?rand="+Math.random()
		});		
		 
		//打开删除对话框前判断是否已选择要删除的行
			$("#openDeletegoodsModal").on("click",function(event){
						if(selected.length==0){
							handleAlerts(loadProperties("error.delete.select",locale),"warning","");				
							return false;
						}				
				});
			//删除操作
			$('#deleteBtn').on('click', function (e) {
				$.ajax( {
	             "dataType": 'json', 
	             "type": "GET", 
	             "url": rootURI+"goods/deletegoods/"+selected.join(), 
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
			/*$("#openActivegoodsModal").on("click",function(event){
				if(selected.length==0){
					handleAlerts("Please select the rows which you want to Active.","warning","");				
					return false;
				}
			});*/
			$("#openPutgoodsModal").on("click",function(event){
				if(selected.length==0){
					handleAlerts(loadProperties("error.put.select",locale),"warning","");				
					return false;
				}				
		});
			
			//上架操作
			$('#putBtn').on('click', function (e) {
				$.ajax( {
	             "dataType": 'json', 
	             "type": "GET", 
	             "url": rootURI+"goods/putgoods/"+selected.join(), 
	             "data":{"type":1},
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
			$("#openOutgoodsModal").on("click",function(event){
				if(selected.length==0){
					handleAlerts(loadProperties("error.out.select",locale),"warning","");				
					return false;
				}				
		});
			
			//下架操作
			$('#outBtn').on('click', function (e) {
				$.ajax( {
	             "dataType": 'json', 
	             "type": "GET", 
	             "url": rootURI+"goods/putgoods/"+selected.join(), 
	             "data":{"type":0},
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
		$("#openEditgoodsModal").on("click",function(event){
			if(selected.length!=1){
				handleAlerts(loadProperties("error.edit.select",locale),"warning","");
				return false;
			}
			else{
				var data = oTable.api().row($("tr input:checked").parents('tr')).data();
	            var id = data.id;
	            var storeId = $("#storeId").val();
	            if(storeId==null||storeId==''||storeId<0){
	            	storeId = -1;
	            }
	            location.href=rootURI+"editgoods/"+id+"?storeId="+storeId;
			}
		});
		
		$("#openAddGoodModal").on("click",function(event){
	            var storeId = $("#storeId").val();
	            if(storeId==null||storeId==''||storeId<0){
	            	storeId = -1;
	            }
	            location.href=rootURI+"goods/addgoods?storeId="+storeId;
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
	            	var ids=data.id;
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
  
    

    return {
        //main function to initiate the module
        init: function (rootPath,locale_value) {
        	rootURI=rootPath;
        	locale=locale_value;
        	handleTable();  
        }

    };

}();