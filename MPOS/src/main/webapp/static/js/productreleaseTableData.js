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
var locale = "zh_CN";
var ReleaseTable = function () {
	var oTable;
	var oLogTable;
	var selected = [];
	var handleTable = function () {							
		var table=$('#releases_table');
		 oTable = table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"processing":true,
        	"scrollX":"100%",
           	"scrollXInner":"100%",
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
                },
            		{                	
            	'targets':-1,
            	'data':null,//定义列名
            	'render':function(data,type,row){
                	return '<div class="actions"><a class="btn btn-sm dark" data-toggle="modal"  href="#view_products" id="viewmodal">view</a></div>';
                },
                'class':'center'
            		}
            ],
            "columns": [
               {"orderable": false },
	           { title: "Release Id",   data: "id"  },
	           { title: "Is Public",   data: "isPublic" },
	           { title: "Product Ids",    data: "productString" },
	           { title: "Public Time",  data: "publicTimeStr"},
	           { title: "Options","class":"center"},
	        ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"productrelease/productreleaselist?rand="+Math.random()
		});		
		 
		//打开发布对话框前判断是否已选择要发布的行
			
			$("#openPublicModal").on("click",function(event){
				
				if(selected.length==0){
					handleAlerts("Please select the rows which you want to public.","warning","");				
					return false;
				}else if(selected.length>1){
					handleAlerts("just can select one row.","warning","");
					return false;
				}else {
					var data = oTable.api().row($("tr input:checked").parents('tr')).data();
					var ispublic=data.isPublic;
					if(ispublic){
					handleAlerts("this release is publicde.","warning","");
					return false;
					}
					}
			});
		//发布操作
		$('#PublicBtn').on('click', function (e) {
			var tt=selected.join();
			$.ajax( {
             "dataType": 'json', 
             "type": "POST", 
             "url": rootURI+"productrelease/publicrelease/"+tt, 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
		            	 handleAlerts("delete the adminusers successfully.","success","");
					 }
					 else{
						 handleAlerts("Failed to delete the adminusers. " +data.info,"danger","");
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
        
        
		 table.on('click', 'tbody tr a',function(){
	           var data = oTable.api().row($(this).parents('tr')).data();
	           var ids=data.id;
				$.ajax( {
	             "dataType": 'json', 
	             "type": "GET", 
	             "url": rootURI+"productrelease/viewrelease/"+ids, 
	             "success": function(data,status){
	            	 if(status == "success"){					
						 if(data.status){
							 var productRelease=data.productRelease;
					         $("#viewProduct input[name='id']").val(productRelease.id);
					         $("#viewProduct input[name='isPublic']").val(productRelease.isPublic);
					         $("#viewProduct textarea[name='products']").val(productRelease.products);
						 }
					}             	 
	             },
	             "error":function(XMLHttpRequest, textStatus, errorThrown){
	            	 alert(errorThrown);
	             }
	           });
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
               
 
      //  handle show/hide columns
        var tableColumnToggler = $('#column_toggler');		
		$('input[type="checkbox"]', tableColumnToggler).change(function () {
		   //  Get the DataTables object again - this is not a recreation, just a get of the object 
		    var iCol = parseInt($(this).attr("data-column"));
		    var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
		    oTable.fnSetColumnVis(iCol, (bVis ? false : true));
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
            closeInSeconds: 10, // auto close after defined seconds, 0 never close
            icon: "warning" // put icon before the message, use the font Awesone icon (fa-[*])
        });        

    };

   
	

    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable();  
        	       	
        }

    };
}();
