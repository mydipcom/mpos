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
		
		$.ajax({
			type:"GET",
			url:"",
			dataType:"json",
			success:function(data){
				
			},"error":function(XMLHttpRequest, textStatus, errorThrown){
           	 alert(errorThrown);
            }
			
		});
		
		$("input:radio[name=product_type]").click(function(){
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
		var product_data = [
		                      { id: 'user0', text: 'Disabled User'}
		                    , { id: 'user1', text: 'Jane Doe'}
		                    , { id: 'user2', text: 'John Doe'}
		                    , { id: 'user3', text: 'Robert Paulson'}
		                    , { id: 'user5', text: 'Spongebob Squarepants'}
		                    , { id: 'user6', text: 'Planet Bob' }
		                    , { id: 'user7', text: 'Inigo Montoya' }
		                  ];
		var menu_data = [
		                      { id: 'user0', text: 'Disabled User'}
		                    , { id: 'user1', text: 'Jane Doe'}
		                    , { id: 'user2', text: 'John Doe'}
		                    , { id: 'user3', text: 'Robert Paulson'}
		                    , { id: 'user5', text: 'Spongebob Squarepants'}
		                    , { id: 'user6', text: 'Planet Bob' }
		                    , { id: 'user7', text: 'Inigo Montoya' }
		                  ];
		var categroy_data = [
		                      { id: 'user0', text: 'Disabled User'}
		                    , { id: 'user1', text: 'Jane Doe'}
		                    , { id: 'user2', text: 'John Doe'}
		                    , { id: 'user3', text: 'Robert Paulson'}
		                    , { id: 'user5', text: 'Spongebob Squarepants'}
		                    , { id: 'user6', text: 'Planet Bob' }
		                    , { id: 'user7', text: 'Inigo Montoya' }
		                  ];
		
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
		      multiple: true
		      ,query: function (query){
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
		    		"<div class=\"col-md-1\"><input name=\"\" type=\"text\" class=\"form-control\"></div>" +
		    		"<div class=\"col-md-1\"><label>$</label></div>");
		    break;
		   	case 1:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-4\"><label>Full: this rule under the single order goods, purchase amount</label></div>" +
		   				"<div class=\"col-md-1\"><input name=\"gg\" type=\"text\" class=\"form-control\"></div>" +
		   				"<div class=\"col-md-1\"><label>$, reduce</label></div>"+
		   				"<div class=\"col-md-1\"><input name=\"gg\" type=\"text\" class=\"form-control\"></div>"+
		   				"<div class=\"col-md-1\"><label>$</label></div>");
		   		 ;
		   	   break;
		    case 2:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-4\"><label>Discount: the rules of goods, according to the commodity price</label></div>" +
		   				"<div class=\"col-md-1\"><input name=\"gg\" type=\"text\" class=\"form-control\"></div>" +
		   				"<div class=\"col-md-1\"><label>discount.</label></div>");
		   		  ;
		   	   break;
		   
		    case 3:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-5\"><label>Combination: this rule combination of goods, to work together to buy a further reduction of</label></div>" +
		   				"<div class=\"col-md-1\"><input name=\"gg\" type=\"text\" class=\"form-control\"></div>" +
		   				"<div class=\"col-md-3\"><label>$ each group contains at most 5 commodity.</label></div>");
		   	   break;
			case 4:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-3\"><label>To participate in the activities of goods, buy</label></div>" +
		   				"<div class=\"col-md-1\"><input name=\"gg\" type=\"text\" class=\"form-control\"></div>" +
		   				"<div class=\"col-md-1\"><label> get</label></div>"+
		   				"<div class=\"col-md-1\"><input name=\"gg\" type=\"text\" class=\"form-control\"></div>"+
		   				"<div class=\"col-md-1\"><label>free.</label></div>");
		   		 ;
		   	   break;
			case 5:
		   		$("#type_SalesRule_show").append("<div class=\"col-md-3\"><label>To participate in the activities of goods, buy</label></div>" +
		   				"<div class=\"col-md-1\"><input name=\"gg\" type=\"text\" class=\"form-control\"></div>" +
		   				"<div class=\"col-md-1\"><label> get</label></div>"+
		   				"<div class=\"col-md-1\"><input name=\"gg\" type=\"text\" class=\"form-control\"></div>"+
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
    
	 var uploader = new plupload.Uploader({
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
     });
	 
	
    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	promotionRule();
 //       	uploader.init();
        	promotionProductSelect();
        	handleBootstrapSelect();
 //       	handleDatetimePicker();
           }

    };

}();