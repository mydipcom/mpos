
function dealCall(id){
	$.ajax({
        "dataType": 'json', 
        "type":'GET',
        "async":false,
        "url": getRootPath()+"/callWaiter/callWaiter/"+id+"?rand="+Math.random(), 
        "success": function(resp,status){
       	 if(status == "success"){  
       		 if(resp.status){
       			//$("#"+id).modal('hide');
       			//$("#"+id).remove();
       			$("#"+id).remove();
       			return true;
				}
			}             	 
        }
      });
}
var loadTime = 8000;
var loadInterval;
function checkCall(){
	loadInterval = setInterval("checkNotice()",loadTime);
}
var flag = 0;
var xx;

function getHtml(infos,index){
	var html="";
	//呼叫桌号
	 var appId = infos[index].callMan;
	 //呼叫时间
	 var call_time = infos[index].callTime;
	 //呼叫类型 0 买单 1帮助
	 var call_type = infos[index].type
	 var call_info = "Need Help!";
	 if(call_type == 0){
		 call_info = "I Want to Pay!";
	 }
	 html ='<div style="color:green">Number:'+appId+'</div>'
		+'<div style="color:green">Type:'+call_info+'</div>'
		+'<div style="color:green">Time:'+call_time+'</div>'
		+'<div style="text-align: right;">'
		+'<button type="button" class="btn blue" onclick="dealCall(\''+appId+'\')">OK</button>'
		+'</div>';
	 return html;
}
var createMsg = function(infos){
	var info_length = infos.length;
	if(info_length==1){
		showStickyNoticeToast(getHtml(infos,0),5000,"top-right",infos[0].callMan);
	}else if(info_length==2){
		showStickyNoticeToast(getHtml(infos,0),5000,"top-right",infos[0].callMan);
		setTimeout(function(){
			showStickyNoticeToast(getHtml(infos,1),5000,"top-right",infos[1].callMan)
		},500);
	}else if(info_length==3){
		showStickyNoticeToast(getHtml(infos,0),5000,"top-right",infos[0].callMan);
		setTimeout(function(){
			showStickyNoticeToast(getHtml(infos,1),5000,"top-right",infos[1].callMan)
		},500);
		setTimeout(function(){
			showStickyNoticeToast(getHtml(infos,2),5000,"top-right",infos[2].callMan)
		},1000);
	}else if(info_length==4){
		showStickyNoticeToast(getHtml(infos,0),5000,"top-right",infos[0].callMan);
		setTimeout(function(){
			showStickyNoticeToast(getHtml(infos,1),5000,"top-right",infos[1].callMan)
		},500);
		setTimeout(function(){
			showStickyNoticeToast(getHtml(infos,2),5000,"top-right",infos[2].callMan)
		},1000);
		setTimeout(function(){
			showStickyNoticeToast(getHtml(infos,3),5000,"top-right",infos[3].callMan)
		},1500);
	}
	
}
 

function showNoticeToast(modal_html) {
		$().toastmessage('showNoticeToast', modal_html);
    
}
/**
 * 
 * @param modal_html
 * @param time 显示时间
 * @param option 位置 top-right
 */
function showStickyNoticeToast(modal_html,time,option,id) {
		$().toastmessage('showToast', {
	         text     : modal_html,
	         sticky   : false,
	         position : option,
	         type     : 'notice',
	         //closeText: '',
	         stayTime : time,
	         close    : null,
	         id		  : id
	    });
	
    
}


function checkNotice(){
	$.ajax({
        "dataType": 'json', 
        "type":'GET',
        "async":false,
        "url": getRootPath()+"/callWaiter?rand="+Math.random(), 
        "success": function(resp,status){
       	 if(status == "success"){  
       		 if(resp.status){
       			res = resp.info;
       			createMsg(res);
				}
			}             	 
        }
      });
}

//js获取项目根路径，如： http://localhost:8083/uimcardprj
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