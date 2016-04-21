$(function(){
	GetOnlineUser();
	GetMessList();
	InFace();
	AutoRefresh();
	$("#btnSend").click(function(){
		var $strMess =$("#txtInput");
		if($strMess.val()!=""){
			SendMessages($strMess.val());
			
		}else{
			$("#chat-input-tip").val("发送消息不能为空");
		}
	});
});
function GetOnlineUser(){
	$.ajax({
		type: "POST",
		url:"chat",
		data:"action=getOnlineUser&d="+Math.random(),
		success:function(data){
			$("#chat-user-con").html(data);
		}
	});
}
function InFace(){
	var strHtml="";
	for(var i=1;i<=10;i++){
		strHtml+="<img src='image/"+i+".gif' id='"+i+"'/>";  
	}
	$("#chat-input-expr").html(strHtml);
}
$(document).ready(function (){
	$("img").click(function (){
	var $txtInput= $("#txtInput");
	if($txtInput.val()!=undefined){
		var strMess = $txtInput.val()+"<:"+this.id+":>";
	}else{
		var strMess ="<:"+this.id+":>";
	}
	$("#txtInput").val(strMess);
		})
});
function SendMessages(strMess){
	$.ajax({
		type: "POST",
		url:"chat",
		data:"action=sendMessage&d="+Math.random()+"&strMess="+strMess,
		success:function(data){
			$("#txtInput").val("");
			if(data=="success"){
				GetMessList();
			}else{
				$("#chat-input-tip").val("发送失败");
			}
		}
	});
}
function GetMessList(){
	$.ajax({
		type: "POST",
		url: "chat",
		data:"action=getMessageList&d="+Math.random(),
		success:function(data){
			$("#chat-dialog-con").html(data);
		}
	});
}
function AutoRefresh(){
	setInterval(GetOnlineUser, 2000);
	setInterval(GetMessList, 1000);
}