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
var OrderList = function () {
	var oTable;
	var selected = [];
	 var orderListTable = function() {
			
	        var table = $('#order_list_table');
	        oTable = table.dataTable({
				"lengthChange" : false,
				"filter" : true,
				"sort" : false,
				"info" : true,
				"processing" : true,
				"scrollX" : "100%",
				"scrollXInner" : "100%",
				"displayLength" : 10,
				 "dom": "tr<'row'<'col-md-6'i><'col-md-6'p>>",
				  "oLanguage": {
		                "sProcessing": loadProperties("dataTable.page.process",locale),                
		                "sZeroRecords":loadProperties("dataTable.page.data.zero",locale),
		                "sEmptyTable": loadProperties("dataTable.page.data.empty",locale),
		                "sInfo": loadProperties("dataTable.page.info",locale),
		                "sInfoEmpty":loadProperties("dataTable.page.info.empty",locale),
		            },
				"columnDefs" : [ {
					'targets' : 0,
					'render' : function(data, type, row) {
						return '<div class="checker"><span><input type="checkbox" class="checkboxes"/></span></div>';
					},
				} ],
				"columns" : [ {
					"orderable" : false
				}, {
					/*title : "Order ID",*/
					data : "orderId"
				}, {
					/*title : "Order Status",*/
					data : "orderStatus",
					'render' : function(data, type, row) {
						var temp = "已取消";
						if(data=='Paid'){
							temp="已支付";
						}else if(data=='Pending'){
							temp="未支付";
						}
						return temp;
					},
				},{
					/*title : "Order Total",*/
					data : "orderTotal"
				},  {
					/*title : "Order Discount",*/
					data : "orderDiscount"
				} ,{
					/*title : "Create Time",*/
					data : "createTime",
					
				}, {
					/*title : "Creater",*/
					data : "creater",
					
				},{
					/*title : "Comment",*/
					data : "peopleNum",
				} ],
				"serverSide" : true,
				"serverMethod" : "GET",
				"ajaxSource" : rootURI + "order/orderlist?rand="
						+ Math.random()
			}); 
			
	       $("#status_select").change(function(){
				var jsonData=$(this).serializeJson();
				var jsonDataStr=JSON.stringify(jsonData);			
				oTable.fnFilter(jsonDataStr);
				if(parseInt($(this).val()) == 0){
				   $(".actions").find("a:eq(0)").removeClass("hidden");
				   $(".actions").find("a:eq(1)").removeClass("hidden");
				}else{
					$(".actions").find("a:eq(0)").addClass("hidden");
					$(".actions").find("a:eq(1)").addClass("hidden");
				}
				return false;
	         });
			
			//选择判断
			$("#confirm_payment").on("click",function(event){
				if(selected.length==0){
					handleAlerts(loadProperties("error.orderlist.payment.select",locale),"warning","");			
					return false;
				}
			});
			$("#cancel_order").on("click",function(event){
				if(selected.length==0){
					handleAlerts(loadProperties("error.orderlist.cancel.select",locale),"warning","");					
					return false;
				}
			});
			
			$("#order_detail_btn").on("click",function(event){
				if(selected.length != 1){
					handleAlerts(loadProperties("error.orderlist.orderdetail.select",locale),"warning","");				
					return false;
				}else{
					window.location.href="order_details?order_id="+selected.join();
				}
				});
			
			//确认支付
	        $('#paymentBtn').on('click', function (e) {
				$.ajax( {
	             "dataType": 'json', 
	             "type": "POST", 
	             "url": rootURI+"order/paymentOrder/"+selected.join()+"?rand="+ Math.random(), 
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
			
	        //取消订单
	        $('#cancelBtn').on('click', function (e) {
				$.ajax( {
	             "dataType": 'json', 
	             "type": "POST", 
	             "url": rootURI+"order/cancelOrder/"+selected.join()+"?rand="+ Math.random(), 
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
	        
			//全选
			$(".group-checkable").on('change',function () {
				var set = jQuery(this).attr("data-set");
	            var checked = jQuery(this).is(":checked");
	            selected=[];
	            if(checked){            	
		            var api=oTable.api();            
		            jQuery(set).each(function () {            	
		            	var data = api.row($(this).parents('tr')).data();
		            	var ids=data.orderId;
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
	            var id = data.orderId;
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
    
	var handleDatetimePicker = function() {
		/*$(".form_datetime").datetimepicker(
				{
					isRTL : Metronic.isRTL(),
					format : "dd/mm/yyyy hh:ii",
					autoclose : true,
					todayBtn : false,
					pickerPosition : (Metronic.isRTL() ? "bottom-right"
							: "bottom-left"),
					minuteStep : 1
				});*/
		if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: Metronic.isRTL(),
                orientation: "left",
                autoclose: true
            });
            //$('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
        }
	}
	
	 var handleBootstrapSelect = function() {
	        $('.bs-select').selectpicker({
	            iconBase: 'fa',
	            tickIcon: 'fa-check'
	        });
	    }
    
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

    }
	
	var searchSubmit = function(event){
			//event.preventDefault();
			var jsonData=$('#searchForm').serializeJson();
			var jsonDataStr=JSON.stringify(jsonData);
			oTable.fnFilter(jsonDataStr);
			return false;
    }
	//搜索验证方法
    var searchFormValidation = function() {
            var form = $('#searchForm');
            var errorDiv = $('.alert-danger', form);            

            form.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input                
                rules: {
                	orderId: {
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
                    searchSubmit();                   
                }
            });
    };
    return {
        //main function to initiate the module
        init: function (rootPath,locale_value) {
        	rootURI=rootPath;
        	locale=locale_value;
        	orderListTable();
        	searchFormValidation();
        	handleDatetimePicker();
        	handleBootstrapSelect();
           }

    };

}();