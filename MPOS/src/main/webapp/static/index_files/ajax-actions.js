//contact form
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
        				if(data.info[i].serviceId==0){	
        					str+="<div class='from-grounp'><label>"+data.info[i].content+"</label><input type='radio' name='serviceId' checked value='"+data.info[i].serviceId+"'/></div>";		
        				}else{
        				str+="<div class='from-grounp'><label>"+data.info[i].content+"</label><input type='radio' name='serviceId' value='"+data.info[i].serviceId+"'/></div>";	
        				}
        				}
        			$('#getSevice').append(str);
    			}else{
    				alert(data.info);
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
            var regmobile=/^[1][358][0-9]{9}$/;
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
                    url: "",
                    data: $('#contact-form').serialize(),
                    beforeSend:function(){
						$('#send-btn').val('注册中...').css('background','#333').attr('disabled','disabled');
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
					url: "storeManager/checkEmail",
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
					dataType:'json',
					url:'common/register',
					data:$('.ser_form').serialize(),
					beforeSend:function(){
						$('#sevice_btn').val('注册中...').css('background','#333').attr('disabled','disabled');
					},
					success:function(data,status){
						if(data.status){
							alert(data.info);
							location.href="http://www.baidu.com";
						}else{
							alert(data.info);
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