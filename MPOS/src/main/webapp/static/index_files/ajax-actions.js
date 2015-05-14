//contact form

function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
$(document).ready(function () {
    $(function () {
		//serviceList
    	$.ajax({
    		type:'POST',
    		dataType:'json',
    		url:'common/getServices',
    		data:'',
    		success:function(data,status){
    			if(data.status){
        			var str='';
        			for(var i=0;i<data.info.length;i++){
        				var checkedStr="";
        				if(i==0){
        					checkedStr="checked";        						
        				}
        				str+="<div class='from-grounp'><input type='radio' name='serviceId' "+checkedStr+" value='"+data.info[i].serviceId+"'/><label>"
        				+data.info[i].serviceName+": "+data.info[i].servicePrice+"元   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data.info[i].content+"</label></div>";	
        			}
        			$('#getSevice').append(str);
    			}else{
    				alert(data.payUrl);
    			}
    		},
    		error:function(){
    			alert('数据获取失败！请重试！');
    		}
    	});
    	//$(':radio').first().attr('checked','checked');
		//contact form
        $("#send-btn").click(function () {
        	var flag = true;
        	var flag1 = true;
            var name = $("#name").val();
            var phoneNum = $("#number").val();
            var message = $("#message").val();
            var regmobile=/^[1][3-8][0-9]{9}$/;
            if($("#message").val().length == 0) {
                    $('#message').css({
                        "background-color": "rgba(238,12,76,0.2)"
                    });
                    flag=false;
                } 
            if($("#number").val().length != 0) {
            	if(!regmobile.test(phoneNum)){
                    $('#number').css({
                        "background-color": "rgba(238,12,76,0.2)"
                    });
                    flag1=false;
            	}

            }
            if(flag&&flag1){
               $.ajax({
                   	type: "POST",
                   	dataType:'json',
                    url: "common/addMsg",
                    data: $('#contact-form').serialize(),
                    beforeSend:function(){
						$('#send-btn').val('留言中...').css('background','#333').attr('disabled','disabled');
					},
                    success: function (data,status) {
                    	if(data.status){
                            $('.success').css({
                                "display": "inline-block"
                            });
                            $('input[type=text],textarea').val('');
                    	}else{
                    		alert(data.info);
                    	}

                    },
					error:function(){
						alert('留言失败，请重试！');
					},
					complete:function(){
						$('#send-btn').val('提交').css('background','#ED0C4C').attr('disabled',false);
					}
                });
            }
        });
        $("#close_pay").on('click',function(){
        	$("#body_content").html("您确定要取消服务订购？");
        	$('#pay_done').modal({backdrop: 'static', keyboard: false});
   	});
        $("#qrb").on('click',function(){
        	 $("#pay_id").modal('hide');
   	});
        $("#rePayButton").on('click',function(){
        	window.open(getRootPath()+"/common/alipay");
   	});
    	$("#doneButton").on('click',function(){
    		 $("#pay_id").modal('hide');
    		 var htm="<div>稍后服务将自动开通，请查收邮件</div>";
    		 htm +="<div>在确认服务已开通情况下，请在您的支付宝中确认收货，谢谢！</div>";
    		 $("#body_content").html(htm);
    		 $('#pay_done').modal({backdrop: 'static', keyboard: false});
    	});
    	$("#payButton").on('click',function(){
    		$("#payButton").css("display","none"); 
    		$("#button_info").css("display",'block');
    		window.open(getRootPath()+"/common/alipay");
   	});
    	//register form
		$('#sevice_btn').on('click',function(){
			var flag=true;
			var flag1=true;
			var flag2=true;
			var flag3=true;
			var reg=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			var regpass=/[a-zA-Z\d]{6,16}/;
			var regmobile=/^[1][358][0-9]{9}$/;
			var emailValue=$('input[name="email"]').val();
			var mobileValue=$('input[name="mobile"]').val();
			var passValue=$('input[name="password"]').val();
			var rePassValue=$('input[name="repassword"]').val();
			//email vedicate
			if(emailValue==''){
				$('input[name="email"]').next('.error').html('电子邮箱不能为空！');
				flag=false;
			}else if(!reg.test(emailValue)){
				$('input[name="email"]').next('.error').html('请输入有效的电子邮箱！');
				flag=false;
			}else{
				$.ajax({
					async:false,
					type: "POST",
					url: "common/checkEmail",
					data: "email="+emailValue,
					success: function(text){
						if(text=='false'){
							$('input[name="email"]').next('.error').html('邮箱已存在，请重新输入！');
							flag=false;
						}else{
							$('input[name="email"]').next('.error').text('');
							flag=true;	
							}
							return flag;
					}
				})
			};
			//password vedicate
			if(passValue==''){
				$('input[name="password"]').next('.error').html('密码不能为空！');
				flag1=false;
			}else if(!regpass.test(passValue)){
				$('input[name="password"]').next('.error').html('密码应由6到16位的字母、数字或下划线组成！');	
				flag1=false;
			}else{
				$('input[name="password"]').next('.error').html('');
				flag1=true;	
			}
			
			//mobilePhone vedicate
			if(mobileValue==''){
				$('input[name="mobile"]').next('.error').html('电话号码不能为空！');
				flag3=false;
			}else if(!regmobile.test(mobileValue)){
				$('input[name="mobile"]').next('.error').html('电话号码格式不正确！');	
				flag3=false;
			}else{
				$('input[name="mobile"]').next('.error').html('');
				flag3=true;	
			}
		
			//repassword vedicate
			if(rePassValue!=passValue){
				$('input[name="repassword"]').next('.error').html('两次密码输入不一致！');		
				flag2=false;
			}else{
				$('input[name="repassword"]').next('.error').html('');
				flag2=true;	
			}
			if(flag&&flag1&&flag2&&flag3){
				
				$.ajax({
					type:'POST',
					async: false, 
					dataType:'json',
					url:'common/register',
					data:$('.ser_form').serialize(),
					beforeSend:function(){
						$('#sevice_btn').val('注册中...').css('background','#333').attr('disabled','disabled');
					},
					success:function(data,status){
						if(data.status){
							if(data.isPay){
								$('#pay_id').modal({backdrop: 'static', keyboard: false});
				 				$("#payButton").css("display","block"); 
				 				$("#button_info").css("display",'none');
						    	var data = data.data;
						    	$("#orderNum").html(data.orderNum);
						    	$("#goodName").html(data.subject);
						    	$("#goodPrice").html(data.price+"元");
							}else{
								$("#body_content").html("恭喜您"+data.service+"注册成功，您可以登录后台开始管理菜单");
					        	$('#pay_done').modal({backdrop: 'static', keyboard: false});
							}
						}else{
							$("#body_content").html(data.info);
				        	$('#pay_done').modal({backdrop: 'static', keyboard: false});
						};
					},
					error:function(){
						alert('注册失败，请重试！');
					},
					complete:function(){
						$('#sevice_btn').val('提交').css('background','#ED0C4C').attr('disabled',false);
					}
				})
		}
		});
    });
});