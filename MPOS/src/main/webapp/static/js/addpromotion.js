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
var AddPromotion = function () {
	
	var promotionProductSelect = function(){
		var product_data= [];
		var menu_data = [];
		var categroy_data = [];
		$.ajax({
			type:"GET",
			url:rootURI+"promotion/promotion_bind_product?rand="+Math.random(),
			dataType:"json",
			success:function(data){
                product_data=data.product;
				menu_data=data.menu;
			    categroy_data=data.category;
			},"error":function(XMLHttpRequest, textStatus, errorThrown){
           	 alert(errorThrown);
            }
			
		});
		
		$("input:radio[name=bindType]").click(function(){
			$("#classification").addClass("hidden");
			$("#goods").addClass("hidden");
			$("#menu").addClass("hidden");
			switch(parseInt($(this).val())){
			  case 1:
				  $("#classification").removeClass("hidden")
				  ;
			  break;
			  case 2:
				  $("#menu").removeClass("hidden")
				  ;
			  break;
			  case 3:
				  $("#goods").removeClass("hidden")
				  ;
			  break;
			}
			
		});
		
		
		$('#cla').select2({
			 placeholder: "Select Classification",
		      multiple: true
		      ,query: function (query){
		          var data = {results: []};
		 
		          $.each(categroy_data, function(){
		              if(query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0 ){
		                  data.results.push({id: this.id, text: this.text });
		              }
		          });
		 
		          query.callback(data);
		      }});
//		 $('#cla').select2('data', categroy_data ); 设置初始值
		
		
		 $('#goo').select2({
			 placeholder: "Select Good",
		      multiple: true
		      ,query: function (query){
		          var data = {results: []};
		 
		          $.each(product_data, function(){
		              if(query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0 ){
		                  data.results.push({id: this.id, text: this.text });
		              }
		          });
		 
		          query.callback(data);
		      }});
		 
		 $('#men').select2({
			 placeholder: "Select Menu",
		      multiple: true,
		      query: function (query){
		          var data = {results: []};
		 
		          $.each(menu_data, function(){
		              if(query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0 ){
		                  data.results.push({id: this.id, text: this.text });
		              }
		          });
		 
		          query.callback(data);
		      }});
	}
	
	var promotionRule=function(){
		$("input:radio[name=way]").click(function(){
		   $("#type_SalesRule_show").empty();
		   switch(parseInt($(this).val())){
		    case 0:$("#type_SalesRule_show").append("<div class=\"col-md-5\"><label>Straight down: the rules of commodity, single commodity in the actual price basis, reduction&nbsp;</label></div>" +
		    		"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramOne\"></div>" +
		    		"<div class=\"col-md-1\"><label>$</label></div>");
		    break;
		   	case 1:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-4\"><label>Full: this rule under the single order goods, purchase amount</label></div>" +
		   				"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramOne\"></div>" +
		   				"<div class=\"col-md-1\"><label>$, reduce</label></div>"+
		   				"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramTwo\"></div>"+
		   				"<div class=\"col-md-1\"><label>$</label></div>");
		   		 ;
		   	   break;
		    case 2:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-4\"><label>Discount: the rules of goods, according to the commodity price</label></div>" +
		   				"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramOne\"></div>" +
		   				"<div class=\"col-md-1\"><label>discount.</label></div>");
		   		  ;
		   	   break;
		   
		    case 3:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-5\"><label>Combination: this rule combination of goods, to work together to buy a further reduction of</label></div>" +
		   				"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramOne\"></div>" +
		   				"<div class=\"col-md-3\"><label>$ each group contains at most 5 commodity.</label></div>");
		   	   break;
			case 4:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-3\"><label>To participate in the activities of goods, buy</label></div>" +
		   				"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramOne\"></div>" +
		   				"<div class=\"col-md-1\"><label> get</label></div>"+
		   				"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramTwo\"></div>"+
		   				"<div class=\"col-md-1\"><label>free.</label></div>");
		   		 ;
		   	   break;
			case 5:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-3\"><label>To participate in the activities of goods, buy</label></div>" +
		   				"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramOne\"></div>" +
		   				"<div class=\"col-md-1\"><label> get</label></div>"+
		   				"<div class=\"col-md-1\"><input  type=\"text\" class=\"form-control\" name=\"paramTwo\"></div>"+
		   				"<div class=\"col-md-1\"><label>free.</label></div>");
		   		 ;
		   	   break;
             case 6:
				 $("#type_SalesRule_show").append("<div class=\"col-md-9\"><label>Custom promotion: this rule is not carried out under the commodity price concessions, only as the literal meaning of promotional activities</label></div>");
			   				
		   	   break;
		   }
		  
		});
		
		
		
		
		 
		 
		 
		 
		
	};
	var handleBootstrapSelect = function() {
		$('#spinner3').spinner({value:5, min: 0, max: 10});
		
        $('.bs-select').selectpicker({
            iconBase: 'fa',
            tickIcon: 'fa-check'
        });
    };
    
	 /*var uploader = new plupload.Uploader({
         runtimes : 'html5,flash,silverlight,html4',
          
         browse_button : document.getElementById('tab_images_uploader_pickfiles'), // you can pass in id...
         container: document.getElementById('tab_images_uploader_container'), // ... or DOM Element itself
          
         url : "assets/plugins/plupload/examples/upload.php",
          
         filters : {
             max_file_size : '10mb',
             mime_types: [
                 {title : "Image files", extensions : "jpg,gif,png"},
                 {title : "Zip files", extensions : "zip"}
             ]
         },
      
         // Flash settings
         flash_swf_url : 'assets/plugins/plupload/js/Moxie.swf',
  
         // Silverlight settings
         silverlight_xap_url : 'assets/plugins/plupload/js/Moxie.xap',             
      
         init: {
             PostInit: function() {
                 $('#tab_images_uploader_filelist').html("");
      
                 $('#tab_images_uploader_uploadfiles').click(function() {
                     uploader.start();
                     return false;
                 });

                 $('#tab_images_uploader_filelist').on('click', '.added-files .remove', function(){
                     uploader.removeFile($(this).parent('.added-files').attr("id"));    
                     $(this).parent('.added-files').remove();                     
                 });
             },
      
             FilesAdded: function(up, files) {
                 plupload.each(files, function(file) {
                     $('#tab_images_uploader_filelist').append('<div class="alert alert-warning added-files" id="uploaded_file_' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ') <span class="status label label-info"></span>&nbsp;<a href="javascript:;" style="margin-top:-5px" class="remove pull-right btn btn-sm red"><i class="fa fa-times"></i> remove</a></div>');
                 });
             },
      
             UploadProgress: function(up, file) {
                 $('#uploaded_file_' + file.id + ' > .status').html(file.percent + '%');
             },

             FileUploaded: function(up, file, response) {
                 var response = $.parseJSON(response.response);

                 if (response.result && response.result == 'OK') {
                     var id = response.id; // uploaded file's unique name. Here you can collect uploaded file names and submit an jax request to your server side script to process the uploaded files and update the images tabke

                     $('#uploaded_file_' + file.id + ' > .status').removeClass("label-info").addClass("label-success").html('<i class="fa fa-check"></i> Done'); // set successfull upload
                 } else {
                     $('#uploaded_file_' + file.id + ' > .status').removeClass("label-info").addClass("label-danger").html('<i class="fa fa-warning"></i> Failed'); // set failed upload
                     Metronic.alert({type: 'danger', message: 'One of uploads failed. Please retry.', closeInSeconds: 10, icon: 'warning'});
                 }
             },
      
             Error: function(up, err) {
                 Metronic.alert({type: 'danger', message: err.message, closeInSeconds: 10, icon: 'warning'});
             }
         }
     });*/
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
    
    var addFormValidation = function() {
        var form = $('#promotion_form');
        var errorDiv = $('.alert-danger', form);            
        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input                
            rules: {
             promotionName: {
            	 required: true,
            	 },
             paramOne:{
            	 required: true,
            	 digits:true,
            	 min:0,
             },
             paramTwo:{
                	 required: true,
                	 digits:true,
                	 min:0,
                 },
             startTime:{
            	 required: true
             },
             endTime:{
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

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },
            onfocusout:function(element){
            	$(element).valid();
            },
            submitHandler: function (form) { 
            	errorDiv.hide();
            	addPromotionSubmit();
            }
        });
    } 
    
    var addPromotionSubmit= function(){
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:rootURI+"promotion/addPromotion?rand="+Math.random(),
    		data:$('#promotion_form').serializeJson(),
    		success:function(resp,status){
    			
    		}
    		
    		
    	});
    }
	
    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	promotionRule();
 //       	uploader.init();
        	promotionProductSelect();
        	handleBootstrapSelect();
         	handleDatetimePicker();
         	addFormValidation();
           }

    };

}();