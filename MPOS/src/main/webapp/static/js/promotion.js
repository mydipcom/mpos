//jquery插件把表单序列化成json格式的数据start 
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
})(jQuery);

var rootURI = "/";
var Promotion = function() {
  var promotionTable = function() {
		var oTable;
		var selected = [];
        var table = $('#promotion_list_table');
		oTable = table.dataTable({
					"lengthChange" : false,
					"filter" : true,
					"sort" : false,
					"info" : true,
					"processing" : true,
					"scrollX" : "100%",
					"scrollXInner" : "100%",
					"displayLength" : 10,
					"dom" : "t<'row'<'col-md-6'i><'col-md-6'p>>",
					"columnDefs" : [ {
						'targets' : 0,
						'render' : function(data, type, row) {
							return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
						},
					} ],
					"columns" : [ {
						"orderable" : false
					}, {
						title : "Promotion ID",
						data : "promotionId"
					}, {
						title : "Promotion Name",
						data : "promotionName"
					},{
						title : "Promotion Rule",
						data : "promotionRule"
					},  {
						title : "Promotion Type",
						data : "promotionType"
					} ,{
						title : "Start Time",
						data : "startTime",
						
					}, {
						title : "End Time",
						data : "endTime",
						
					}, {
						title : "Shared",
						data : "shared",
						
					}, {
						title : "Priority",
						data : "priority",
						
					},{
						title : "Status",
						data : "status"
					} ],
					"serverSide" : true,
					"serverMethod" : "GET",
					"ajaxSource" : rootURI + "promotion/promotionList?rand="
							+ Math.random()
				});
		
		//搜索表单提交操作
		$("#searchForm").on("submit", function(event) {
			event.preventDefault();
			var jsonData=$(this).serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
			return false;
		});	
		$("input:radio[name=promotionType]").click(function(){
			event.preventDefault();
			var jsonData=$(this).serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);			
			oTable.fnFilter(jsonDataStr);
			return false;
         });
		
		//禁用启用选择判断
		$("#activatePromotion").on("click",function(event){
			if(selected.length==0){
				handleAlerts("Please select the rows which you want to Active.","warning","");				
				return false;
			}
		});
		$("#deactivatePromotion").on("click",function(event){
			if(selected.length==0){
				handleAlerts("Please select the rows which you want to deactive.","warning","");				
				return false;
			}
		});
		
		//开启活动
        $('#activateBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "POST", 
             "url": rootURI+"promotion/activaOrDeactiva/"+selected.join()+"?flag=1&rand="+ Math.random(), 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
		            	 var checked = $(".group-checkable").is(":checked");
		  			     if(checked){
		  				   $(".group-checkable").parents('span').removeClass("checked");
		  				   $(".group-checkable").attr("checked", false);;
		  			     }
		            	 handleAlerts("Activate the promotion successfully.","success","");
					 }
					 else{
						 handleAlerts("Activate the promotion failure.","danger","");
					 }
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	 alert(errorThrown);
             }
           });
        }); 
		
        //关闭活动
        $('#deactivateBtn').on('click', function (e) {
			$.ajax( {
             "dataType": 'json', 
             "type": "POST", 
             "url": rootURI+"promotion/activaOrDeactiva/"+selected.join()+"?flag=0&rand="+ Math.random(), 
             "success": function(data,status){
            	 if(status == "success"){					
					 if(data.status){
						 selected=[];						 
		            	 oTable.api().draw();
		            	 oTable.$('th span').removeClass();
		            	 var checked = $(".group-checkable").is(":checked");
		      		     if(checked){
		      			   $(".group-checkable").parents('span').removeClass("checked");
		      			   $(".group-checkable").attr("checked", false);;
		      		     }
		            	 handleAlerts("Deactivate the promotion successfully.","success","");
		            	 
					 }
					 else{
						 handleAlerts("Deactivate the promotion failure.","danger","");
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
            selected=[];
            if(checked){            	
	            var api=oTable.api();            
	            jQuery(set).each(function () {            	
	            	var data = api.row($(this).parents('tr')).data();
	            	var ids=data.promotionId;
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
            var id = data.promotionId;
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
	}
	
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

    }
	
	var handleDatetimePicker = function() {
		$(".form_datetime").datetimepicker(
				{
					isRTL : Metronic.isRTL(),
					format : "dd/mm/yyyy hh:ii",
					autoclose : true,
					todayBtn : false,
					pickerPosition : (Metronic.isRTL() ? "bottom-right"
							: "bottom-left"),
					minuteStep : 1
				});
	}

	return {
		// main function to initiate the module
		init : function(rootPath) {
			rootURI = rootPath;
			promotionTable();
			handleDatetimePicker();
		}

	};

}();