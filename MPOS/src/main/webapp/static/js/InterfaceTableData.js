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
var InterfaceTable = function () {
	var oTable;
	var selected;
	var handleTable = function () {
		selected = [];
		var oInparamTable;
	    var table=$('#interface_table');
		oTable = table.dataTable({
			    "lengthChange":false,
				"filter":false,
				"sort":false,
				"info":true,
				"bRetrieve": true,
				"processing":true,
				"bDestroy":true,
                "displayLength": 10,
				"dom": "t",
				//"columns": [
	 	        //   { title: "Interface Name",   data: "name" },
	 	        //   { title: "Describe", data: "descr"}
	 	        //   ],
	 	          // "serverSide": true,
	 	          // "serverMethod": "GET",
	 	         //  "ajaxSource": rootURI+"interfaceList?rand="+Math.random()
	 	        });
		 var inparamTable = function(ids){		
			var logTable=$('#inparam_table');
				oInparamTable = logTable.dataTable({
					"lengthChange":false,
					"filter":false,
					"sort":false,
					"info":true,
					"bRetrieve": true,
					"processing":true,
					"bDestroy":true,
	                // set the initial value
					"displayLength": 3,
					"dom": "t<'row'<'col-md-6'i><'col-md-6'p>>",
					"columns": [
		 	           { title: "ID",   data: "id" },
		 	           { title: "Param Name",   data: "name" },
		 	           { title: "Type",  data: "type"},
		 	           { title: "Describe", data: "description"},
		 	           ],
		 	           "serverSide": true,
		 	           "serverMethod": "GET",
		 	           "ajaxSource": rootURI+"interfaceinparam/"+ids+"?rand="+Math.random()
					});	
			};
	
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
	var handleAlerts = function(msg,msgType,position,closeInSeconds) {         
        Metronic.alert({
            container: position, // alerts parent container(by default placed after the page breadcrumbs)
            place: "prepent", // append or prepent in container 
            type: msgType,  // alert's type (success, danger, warning, info)
            message: msg,  // alert's message
            close: true, // make alert closable
            reset: true, // close all previouse alerts first
            focus: false, // auto scroll to the alert after shown
            closeInSeconds: closeInSeconds, // auto close after defined seconds, 0 never close
            icon: "warning" // put icon before the message, use the font Awesone icon (fa-[*])
        });        

    };
    
  
 
    
    
    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;  
        	   
          
        }

    };

}();