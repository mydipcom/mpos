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
var MyLogTable = function () {
	var oTable;
	var selected = [];
	var handleTable = function () {
		
		var table=$('#mylog_table');
		oTable = table.dataTable({
			"lengthChange":false,
        	"filter":true,
        	"sort":false,
        	"info":true,
        	"processing":true,
        	"scrollX":"100%",
           	"scrollXInner":"100%",
            // set the initial value
            "displayLength": 10,
            "dom": "tr<'row'<'col-md-6'i><'col-md-6'p>>",
            "oLanguage": {
                "sProcessing": loadProperties("dataTable.page.process","zh_CN"),                
                "sZeroRecords":loadProperties("dataTable.page.data.zero","zh_CN"),
                "sEmptyTable": loadProperties("dataTable.page.data.empty","zh_CN"),
                "sInfo": loadProperties("dataTable.page.info","zh_CN"),
                "sInfoEmpty":loadProperties("dataTable.page.info.empty","zh_CN"),
            },
            "columnDefs": [{                    
                    'targets': 0,   
                    'render':function(data,type,row){
                    	return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
                    },
                }
            ],
            "columns":[
                      	{"orderable": false },
                      	//{data: "serviceOrderId", },
                      	{data: "email" },
                      	{data: "createTimeStr" },
                    	{data: "serviceName" },
                      	{data: "price" },
                      	{data: "status",
                      		 'render':function(data,type,row){
                      			 var temp = loadProperties("service.page.pay.true","zh_CN");
                      			 if(data=="0"){
                      				temp = loadProperties("service.page.pay.false","zh_CN");
                      			 }
                             	return temp;
                             }
                      	}
                      ],
	        "serverSide": true,
	        "serverMethod": "GET",
	        "ajaxSource": rootURI+"trade/logList?rand="+Math.random(),
//	        "fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
//	           $.ajax( {
//	             "dataType": 'json', 
//	             "type": "POST", 
//	             "url": sSource, 
//	             "data": aoData,
////	             "contentType":"application/json",
//	             "success": function(resp){ 		            	
//	            	 fnCallback(resp);
//	             },
//	             "error":function(XMLHttpRequest, textStatus, errorThrown){
//	            	 alert(errorThrown);
//	             }
//	           } );
//	         },
//	        "fnServerParams": function ( aoData ) {
//	           aoData.push( { "name": "more_data", "value": "my_value" } );
//	         },
//	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {				
//	        	$('td:eq(0)', nRow).html( '<input type="checkbox" class="checkboxes" value="1"/>' );
//				return nRow;
//			},

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

        
      //搜索表单提交操作
		$("#searchForm").on("submit", function(event) {
			event.preventDefault();
			var jsonData=$(this).serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
			return false;
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
          //查看日志详情 
        table.on('click', 'tbody tr a', function () {
            var data = oTable.api().row($(this).parents('tr')).data();
            $.ajax( {
                "dataType": 'json', 
                "type": "POST", 
                "url": rootURI+"managerlog/managerslogview/"+data.id, 
                "success": function(data,status){
               	 if(status == "success"){
               		var adminslogs=data.adminslog;
               		$("#viewMylogForm input[name='id']").val(adminslogs.id);
    	            $("#viewMylogForm input[name='adminId']").val(adminslogs.adminId);
    	            $("#viewMylogForm textarea[name='content']").val(adminslogs.content);
    	            $("#viewMylogForm input[name='level']").val(adminslogs.level);
    	            $("#viewMylogForm input[name='createdTime']").val(adminslogs.createdTimeStr);
    				}             	 
                },
                "error":function(XMLHttpRequest, textStatus, errorThrown){
               	 alert(errorThrown);
                }
              });
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
	 //initialize datepicker
    var datePicker = function(){
    	$('.date-picker').datepicker({
        rtl: Metronic.isRTL(),
        autoclose: true
        });
     };

    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	handleTable();  
        	datePicker();
        }

    };

}();