var rootURI="/";
var Addgoods = function () {

	$('#chooseCategory').on('change',function(e){
		$("#attributeGroup").empty();
		var id=$(this).val();							
		$.ajax({
			"dataType": 'json', 
             "type"   : "GET", 
             "url"    : rootURI+"goods/getAttributesGroupById/"+id,
             "success": function(data,status){
            	 if(status == "success"){ 
            		 if(data.status){
            			 var rows = $();	
            			 var list=data.list;
            			 for(var i=0;i<list.length;i++){
            				 var attributeId=list[i].attributeId;
            				 var title=list[i].title;
            				 var type=list[i].type;
            				 var content=list[i].content;
            				 
            				 var row=$('<div class="form-group">'+
            							'<label class="control-label col-md-2"></label>'+
            							'<div class="col-md-10"></div>'+						
            						    '</div>');
            				 row.find('.control-label').text(title+": ");
            				 switch (type) {
								case 0:
									row.find('.col-md-10').append('<p class="form-control-static">'+content+'</p>');
									break;
								case 1:
									var radioGroup=$('<div class="radio-list"></div>');
									var attrArr=content.split(",");
									for(var n=0;n<attrArr.length;n++){
									    var attr=attrArr[n];
										var radioObj='<label class="radio-inline"><input type="radio" name="attr_'+attributeId+'" value="'+attr+'"/>'+attr+'</label>';
										radioGroup.append(radioObj);
									}
									row.find('.col-md-10').append(radioGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									break;
								case 2:
									var checkboxGroup=$('<div class="checkbox-list"></div>');
									var tableGroup=$('<div class="col-md-6"><br/><table class="table table-striped table-bordered">'+
										        '<thead><tr><th width="40%">Attribute Name</th><th width="60%">Attribute Price</th></tr></thead>'+
										        '<tbody></tbody></table></div>');													
									var checkboxes=$();												
									var attrArr=content.split(",");
									for(var n=0;n<attrArr.length;n++){
									    var attr=attrArr[n];
										var checkboxObj=$('<label class="checkbox-inline"><input type="checkbox" name="attr_'+attributeId+'" value="'+attr+'" />'+attr+'</label>');
										checkboxes=checkboxes.add(checkboxObj);
									}
									checkboxes.appendTo(checkboxGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									row.find('.col-md-10').append(checkboxGroup);
									row.append(tableGroup);
									checkboxGroup.on('change', ':checkbox', function () {
										row.find("tbody").empty();
										$.each(checkboxGroup.find(":checked"), function (index, obj) {
											row.find("tbody").append('<tr><td><span>'+obj.value+'</span></td><td><span>HK$:</span> <input type="text" name="attrPrice_'+attributeId+'" placeholder="0.00"/></td></tr>');
										});
							        });
									
									break;
								case 3:											
									var selectGroup=$('<select class="form-control" name="attr_'+attributeId+'"></select>');
									var attrArr=content.split(",");
									for(var n=0;n<attrArr.length;n++){
									    var attr=attrArr[n];
										var selectObj='<option value="'+attr+'"/>'+attr+'</option>';
										selectGroup.find('select').append(selectObj);
									}
									row.find('.col-md-10').append(selectGroup);
									break;
								default:
									row.find('.col-md-10').append('<p class="form-control-static">'+content+'</p>');
									break;
							}
            				 
            				rows = rows.add(row);
            			 }
            			 $("#attributeGroup").append(rows);		            			 
					 }
					 else{
						 alert("Failed to query the attirbute group.");
					 } 
				}             	 
             },
             "error":function(XMLHttpRequest, textStatus, errorThrown){
            	alert(errorThrown);
              }
           
		});
	});
	
	//处理表单验证方法
    var addFormValidation = function() {
            var addform = $('#addGoodsForm');
            var errorDiv = $('.alert-danger', addform);            
            addform.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input                
                rules: {                	
	                price: {
	                	required: true,
	                	number:true				            	
	    			},
	    			unitName: {
			            required: true,
				        maxlength:4
			    	},
			    	productName: {
					   required: true,
					   maxlength:20
					},
					shortDescr: {
					  required: true,							
					},												  
	    			sort: {	        		
	        		  required: true,
	        		  digits:true        		
	    			}                   
                },
                invalidHandler: function (event, validator) { //display error alert on form submit                	
                    errorDiv.show();                    
                },
                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },
                unhighlight: function (element) { // revert the change done by hightlight
                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                },
                onfocusout: function (element) { // hightlight error inputs
                    $(element).valid();
                },
                success: function (label) {
                    label.closest('.form-group').removeClass('has-error'); // set success class to the control group
                },
                submitHandler: function (form) {                	
                    errorDiv.hide();
                    $.each($(form).find("input[name='attributeId']"), function (index, attrIdObj) {
	            		var attributeId=attrIdObj.value;
	            		var attrArr=new Array();
	            		var attrPriceArr=new Array();
	            		$.each($(form).find("input[name='attr_"+attributeId+"']:checked"), function (index, attrObj) {
	            			attrArr[index]=attrObj.value;
	            		});
	            		$.each($(form).find("input[name='attr_"+attributeId+"']:selected"), function (index, attrObj) {
	            			attrArr[index]=attrObj.value;
	            		});
	            		$.each($(form).find("input[name='attrPrice_"+attributeId+"']"), function (index, attrPriceObj) {
	            			attrPriceArr[index]=attrPriceObj.value;
	            		});
	            		$(form).append('<input type="hidden" name="attributes['+index+'].id" value="'+attributeId+'"/>');
	            		$(form).append('<input type="hidden" name="attributes['+index+'].attributeValue" value="'+attrArr.join(",")+'"/>');
	            		$(form).append('<input type="hidden" name="attributes['+index+'].attributePrice" value="'+attrPriceArr.join(",")+'"/>');
	            		
	            	});
	            	form.submit();                    
                }
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
            closeInSeconds: 5, // auto close after defined seconds, 0 never close
            icon: "warning" // put icon before the message, use the font Awesone icon (fa-[*])
        });        

    };
    
    
    var handleImages = function() {

        // see http://www.plupload.com/
        var uploader = new plupload.Uploader({
            runtimes : 'html5,flash,silverlight,html4',
             
            browse_button : document.getElementById('tab_images_uploader_pickfiles'), // you can pass in id...
            container: document.getElementById('tab_images_uploader_container'), // ... or DOM Element itself
             
            url : rootURI+"goods/uploadImages",

            filters : {
                max_file_size : '10mb',
                mime_types: [
                    {title : "Image files", extensions : "jpg,jpeg,gif,png"}
                    //{title : "Zip files", extensions : "zip"}
                ]
            },
//            max_file_count: 5,
            unique_names: true,             
//            resize: {width: 640, height: 480, quality: 90},               
//            multipart_params: {'user': 'Rocky', 'time': '2012-06-12'}, 
//            chunk_size: '1mb',
            
            multiple_queues:true,

            // Flash settings
            flash_swf_url : rootURI+'assets/plugins/plupload/js/Moxie.swf',
     
            // Silverlight settings
            silverlight_xap_url : rootURI+'assets/plugins/plupload/js/Moxie.xap',             
         
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
//                        previewImage(file,function(imgsrc){
//                        	$('#uploaded_file_' + file.id).append('<img width="80px" height="60px" src="'+imgsrc+'" />' );
//                        });
                    });
                },
         
                UploadProgress: function(up, file) {
                    $('#uploaded_file_' + file.id + ' > .status').html(file.percent + '%');
                },

                FileUploaded: function(up, file, response) {
                    var data = $.parseJSON(response.response);

                    if (data.status) {                        
                        $('#uploaded_file_' + file.id + ' > .status').removeClass("label-info").addClass("label-success").html('<i class="fa fa-check"></i> Done'); // set successfull upload
                        var rows = $();
                        $.each(data.files, function (index, file) {
                            var row = $('<tr>' +
                                '<td><a href="'+file.url+'" class="fancybox-button" data-rel="fancybox-button">'+
                                '<img class="img-responsive" src="'+file.url+'" alt=""></a></td>'+                            	
                                '<td><p class="name"></p></td>' + 
                                '<td><p class="size"></p></td>' +                                
                                '<td>Uploaded Success</td>' +                                 
                                '<td><a href="javascript:;" class="btn default btn-sm"><i class="fa fa-times"></i> Remove </a></td>' +                                
                                '</tr>');                            
                            row.find('.name').text(file.fileName);
                            row.find('.size').text(file.fileSize);
                            row.find('a.btn').on("click",function(){                            	                            	
                            	$.get(rootURI+"goods/deleteImage/"+file.id, function(data){
                            		var res=$.parseJSON(data);
                        		  if(res.status){
                        			  row.remove();
                        		  }
                        		});
                            });
                            rows = rows.add(row);                            
                        });
                        $("#uploadedImagesList").append(rows);
                        
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
        
        //plupload中提供了mOxie对象，可以实现文件预览
        function previewImage(file,callback){
        	if(!file||!/image\//.test(file.type)) retrun;
        	if(file.type=='image/gif'){//gif图片只能使用FileReader进行预览
        		var fr=new mOxie.FileReader();
        		fr.onload=function(){
        			callback(fr.result);
        			fr.destroy();
        			fr=null;
        		}
        		fr.readAsDataURL(file.getSource);
        	}else{
        		var preloader=new mOxie.Image();
        		preloader.onload=function(){
        			preloader.downsize(300,300);//压缩要预览的图片
        			var imgsrc=preloader.type=='image/jpeg'?preloader.getAsDataURL('image/jpeg',80):preloader.getAsDataURL();
        			callback&&callback(imgsrc);
        			preloader.destroy();
        			preloader=null;
        		};
        		preloader.load(file.getSource());
        	}
        }

        uploader.init();

    }
    
  
    
    return {
        //main function to initiate the module
        init: function (rootPath) {
        	rootURI=rootPath;
        	addFormValidation();        	
        	handleImages();
        }

    };

}();