var rootURI="/";

var addrules = function(){
	var price=$('#price').val();
	var re=/^[0-9]*$/;
	if(price!=''&&re.test(price)){
		var prices=price*1.0;
		$("#addGoodsForm input[name='oldPrice']").rules("add",{min:prices});
	}
}

var Addgoods = function () {
	
	$('#chooseSpecCategory').on('change',function(e){
		$("#specattributeGroup").empty();
		var id=$(this).val();							
		$.ajax({
			"dataType": 'json', 
             "type"   : "GET", 
             "url"    : rootURI+"goods/getAttributesGroupByid/"+id,
             "success": function(data,status){
            	 if(status == "success"){ 
            		 if(data.status){
            			 var rows = $();	
            			 var list=data.list;
            			 var k=0;
            			 var attributeIds=new Array();
            			 for(var i=0;i<list.length;i++){
            				 var attributeId=list[i].attributeId;
            				 var title=list[i].title;
            				 var type=list[i].type;
            				 var attributevalue=list[i].attributeValue;
            				 var required=list[i].required;
            				 
            				 var row=$('<div class="form-group">'+
            							'<label class="control-label col-md-2" ></label>'+
            							'<div class="col-md-10"></div>'+						
            						    '</div>');
            				 row.find('.control-label').text(title+": ");
            				
            				 switch (type) {
								
								case 1:
									var radioGroup=$('<div class="radio-list"></div>');
									
									for(var n=0;n<attributevalue.length;n++){
									    var attr=attributevalue[n].value;
										var radioObj='<label class="radio-inline"><input type="radio" name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'"/>'+attr+'</label>';
										
										radioGroup.append(radioObj);
									}
									if(required){
										attributeIds[attributeId]=attributeId;
										row.find('.control-label').append('<span class="required"> * </span>');
										}
									row.find('.col-md-10').append(radioGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									break;
								case 2:
									var checkboxGroup=$('<div class="checkbox-list"></div>');
									var checkboxes=$();												
									for(var n=0;n<attributevalue.length;n++){
										var attr=attributevalue[n].value;
										var checkboxObj=$('<label class="checkbox-inline"><input type="checkbox" name="attr_'+attributeId+'" value="'+attributevalue[n].valueId+'" data="'+attr+'"/>'+attr+'</label>');
										checkboxes=checkboxes.add(checkboxObj);
									}
									if(required){
										attributeIds[attributeId]=attributeId;
										row.find('.control-label').append('<span class="required"> * </span>');
										}
									checkboxes.appendTo(checkboxGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									row.find('.col-md-10').append(checkboxGroup);
									break;
								case 3:											
									var selectGroup=$('<select class="form-control input-xlarge" name="attr_'+attributeId+'"></select>');
									for(var n=0;n<attributevalue.length;n++){
										var attr=attributevalue[n].value;
										var selectObj='<option value="'+attributevalue[n].valueId+'">'+attr+'</option>';
										selectGroup.append(selectObj);
									}
									row.find('.col-md-10').append(selectGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									break;
								default:
									break;
							}
            				 
            				rows = rows.add(row);
            			 }
            			 $("#specattributeGroup").append(rows);
            			for(var i=0;i<attributeIds.length;i++){
            				if(attributeIds[i]!=undefined){
            				 $("#addGoodsForm input[name='attr_"+attributeIds[i]+"']").rules("add",{required: true});
            					}
            				}
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
	$('#chooseorderCategory').on('change',function(e){
		$("#orderattributeGroup").empty();
		var id=$(this).val();							
		$.ajax({
			"dataType": 'json', 
             "type"   : "GET", 
             "url"    : rootURI+"goods/getAttributesGroupByid/"+id,
             "success": function(data,status){
            	 if(status == "success"){ 
            		 if(data.status){
            			 var rows = $();	
            			 var list=data.list;
            			 var attributeIds=new Array();
            			 for(var i=0;i<list.length;i++){
            				 var attributeId=list[i].attributeId;
            				 var attributevalue=list[i].attributeValue;
            				 var title=list[i].title;
            				 var required=list[i].required;
            				 attributeIds[attributeId]=attributeId;
            				 var row=$('<div class="form-group"><br/>'+
            							'<label class="control-label col-md-2"></label><br/>'+
            							'<div class="col-md-10"></div>'+
            							'<label class="control-label col-md-3"></label><br/>'+
            						    '</div>');
            				 row.find('.col-md-2').text(title+":");
            				 row.find('.col-md-2').append('<span class="required"> * </span>');
            				 //row.find('.col-md-3').text( "");
									var page=$();
									var checkboxGroup=$('<div class="checkbox-list"></div>');
									var tableGroup=$('<div class="col-md-6"><br/><table class="table table-striped table-bordered">'+
										        '<thead><tr><th width="40%">Options</th><th width="60%">Price adjustment</th></tr></thead>'+
										        '<tbody></tbody></table><lable>请为客户端默认属性价格输入一个0</lable></div>');
									var Requiredlabel=$('<div class="checkbox"></div>');
									var Requiredlabelbox=$();
									var RequiredObj=$('<label class="control-label col-md-2">Required:</label><label class="control-label class="col-md-10">'+list[i].required+'</label>');
									Requiredlabelbox=Requiredlabelbox.add(RequiredObj);
									Requiredlabelbox.appendTo(Requiredlabel);
									page=page.add(Requiredlabel);
									var checkallboxGroup=$('<div class="checkbox"></div>');
									var checkallboxes=$();
									var checkboxObj=$('<label class="control-label col-md-2">Options:</label><label class="checkbox-inline class="col-md-10"">&nbsp&nbsp&nbsp<input type="checkbox" />Select All</label>');
									checkallboxes=checkallboxes.add(checkboxObj);
									checkallboxes.appendTo(checkallboxGroup);
									var checkboxes=$();
									page=page.add(checkallboxGroup);
									var checkboxes=$();
									for(var n=0;n<attributevalue.length;n++){
									    var attr=attributevalue[n].value;
										var checkboxObj=$('<label class="checkbox-inline">&nbsp&nbsp&nbsp<input type="checkbox" name="attr_'+attributeId+'"  value="'+attributevalue[n].valueId+'" data="'+attr+'"/>'+attr+'</label>');
										checkboxes=checkboxes.add(checkboxObj);
									}
									checkboxes.appendTo(checkboxGroup);
									page=page.add(checkboxGroup);
									row.append('<input type="hidden" name="attributeId" value="'+attributeId+'"/>');
									row.find('.col-md-10').append(page);
									row.append(tableGroup);
									checkallboxGroup.on('change',':checkbox',function(){
										attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
										var checkedallObj=$(this).parents(".checkbox").find(":checked");
										var prices=new Array();
										var pricess=$(this).parents(".form-group").find("tbody").find("input[name='attrPrice_"+attributeId+"']");
										var checkedObjs=$(".checkbox-list").find(".checkbox-inline").find("input[name='attr_"+attributeId+"']");
										$(checkedObjs).parents(".form-group").find("tbody").empty();
										if(checkedallObj.length==1){
											$.each(checkedObjs, function (index, obj) {
												$(obj).attr("checked", true);
							                    $(obj).parents('span').addClass("checked");
												attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
												for(var i=0;i<pricess.length;i++){
													if($(obj).attr("value")==$(pricess[i]).attr("data")){
														prices[$(obj).attr("value")]=$(pricess[i]).attr("value");
													}
													}
												if(prices[$(obj).attr("value")]==undefined){
													$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  /></td></tr>');	
												}else{
												$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  value="'+prices[$(obj).attr("value")]+'"/></td></tr>');
												}
											});
										}else{
											$.each(checkedObjs, function (index, obj) {
												$(obj).removeAttr("checked");
							                    $(obj).parents('span').removeClass("checked");
							                   
										});
										}
									});
									checkboxGroup.on('change', ':checkbox', function () {
										attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
										var prices=new Array();
										var pricess=$(this).parents(".form-group").find("tbody").find("input[name='attrPrice_"+attributeId+"']");
										$(this).parents(".form-group").find("tbody").empty();
										var checkedObj=$(this).parents(".checkbox-list").find(":checked");
										$.each(checkedObj, function (index, obj) {
											attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
											for(var i=0;i<pricess.length;i++){
												if($(obj).attr("value")==$(pricess[i]).attr("data")){
													prices[$(obj).attr("value")]=$(pricess[i]).attr("value");
												}
												}
											/*attributeId=$(this).parents(".form-group").find("input[name=attributeId]").val();
											$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" name="attrPrice_'+attributeId+'" placeholder="0.00"/></td></tr>');*/
											if(prices[$(obj).attr("value")]==undefined){
												$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  /></td></tr>');	
											}else{
											$(obj).parents(".form-group").find("tbody").append('<tr><td><span>'+$(obj).attr("data")+'</span></td><td><span>HK$:</span> <input type="text" data="'+$(obj).attr("value")+'" name="attrPrice_'+attributeId+'" placeholder="0.00"  value="'+prices[$(obj).attr("value")]+'"/></td></tr>');
											}
										});
							        });
            				rows = rows.add(row);
            			 }
            			 $("#orderattributeGroup").append(rows);
            			 for(var i=0;i<attributeIds.length;i++){
             				if(attributeIds[i]!=undefined){
             				 $("#addGoodsForm input[name='attr_"+attributeIds[i]+"']").rules("add",{required: true});
             					}
             				}
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
	    			oldPrice: {
	                	number:true
	    			},
			    	productName: {
					   required: true,
					   maxlength:20
					},
					shortDescr: {
					  required: true,							
					}             
                },
                errorPlacement: function(error, element) {
                    if ( element.is(":radio") )
                        error.appendTo( element.parent().next().next() );
                    else if ( element.is(":checkbox") )
                        error.appendTo ( element.next() );
                    else
                        error.appendTo( element.parent());
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
                    //$(form).find("input[name='attributeId']")
                  var imagetd= $('#uploadedImagesList').find('td');
                    $.each($(form).find("input[name='attributeId']"), function (index, attrIdObj) {
	            		var attributeId=attrIdObj.value;
	            		var attrArr=new Array();
	            		var attrPriceArr=new Array();
	            		$.each($(form).find("input[name='attr_"+attributeId+"']:checked"), function (index, attrObj) {
	            			attrArr[index]=attrObj.value;
	            		});
	            		$.each($(form).find("select[name='attr_"+attributeId+"'] option:selected"), function (index, attrObj) {
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
                    $('#imageerror').html("");
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
                        rows.find("a.fancybox-button").fancybox();
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
