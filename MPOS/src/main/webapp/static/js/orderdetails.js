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

function getAttributes(id){
	var res = "";
	$.ajax({
        "dataType": 'json', 
        "type":'GET',
        "async":false,
        "url": rootURI+"order/getAtts/"+id+"?rand="+Math.random(), 
        "success": function(resp,status){
       	 if(status == "success"){  
       		 if(resp.status){
       			 res = resp.attributes;
				}
			}             	 
        }
      });
	return res;
}
function getProductName(id){
	var res = "";
	$.ajax({
        "dataType": 'json', 
        "type":'GET',
        "async":false,
        "url": rootURI+"order/getProName/"+id+"?rand="+Math.random(), 
        "success": function(resp,status){
       	 if(status == "success"){  
       		 if(resp.status){
       			 res = resp.name;
				}
			}             	 
        }
      });
	return res;
}
var rootURI="/";
var OrderDetails = function () {
	var oTable;
	var selected = [];
	 var orderProductTable = function() {
            var table = $('#order_product_table');
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
					/*title : "Product ID",*/
					data : "productId"
				},{
					//title : "Unit Price",
					data : "name",
					'render' : function(data, type, row) {
						var temp = "";
						var xx = getProductName(row.productId);
						if(xx!=null){
							temp = xx;
						}
						return temp;
					}
				},{
					//title : "Unit Price",
					data : "unitPrice"
				} ,{
					//title : "Curr Price",
					data : "currPrice",						
				}, {
					//title : "Quantity",
					data : "quantity"
				} 
				/*, {
					//title : "Discount",
					data : "discount"				
				}
				,{
					//title : "Attributes",
					data : "attributes",
					'render' : function(data, type, row) {
						var temp = "";
						var xx = getAttributes(row.id);
						if(xx!=null){
							temp = xx;
						}
						return temp;
					}
				},{
					//title : "Is Gift",
					data : "isGift"
				} */
				],
				"serverSide" : true,
				"serverMethod" : "GET",
				"ajaxSource" : rootURI + "order/order_product?rand="+ Math.random(),
				"fnServerParams": function( aoData )
				{
					aoData.push({"name":"order_id","value":$('input:hidden').val()});
				}
			}); 
			
			//确认支付
	        $('#paymentBtn').on('click', function (e) {
				$.ajax( {
	             "dataType": 'json', 
	             "type": "POST", 
	             "url": rootURI+"order/paymentOrder/"+$('input:hidden').val()+"?rand="+ Math.random(), 
	             "success": function(data,status){
	            	 if(status == "success"){					
						 if(data.status){
							 $('.actions').find('a:eq(1)').addClass("disabled");
							 $('.actions').find('a:eq(2)').addClass("disabled");
							 $('.static-info:eq(5)').find('span').text("Payment Complete");
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
	             "url": rootURI+"order/cancelOrder/"+$('input:hidden').val()+"?rand="+ Math.random(), 
	             "success": function(data,status){
	            	 if(status == "success"){					
						 if(data.status){
							 $('.actions').find('a:eq(1)').addClass("disabled");
							 $('.actions').find('a:eq(2)').addClass("disabled");
							 $('.static-info:eq(5)').find('span').text("Rejected");
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
	 
	var hideActions = function(){
		if(parseInt($('input:hidden[name=order_status]').val()) != 0){
			$('.actions').find('a:eq(1)').addClass("disabled");
			$('.actions').find('a:eq(2)').addClass("disabled");
		}
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
	
	
	
    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	orderProductTable();
        	hideActions();
        	
     }

    };

}();