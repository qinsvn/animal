var sys;//top.sys.userName(1);
var stompClient = null;
$(function(){
	 sys = sysInfo();
	 //默认打开控制台页面
	 open('templates/animal/control/index.html');
	 connect();
	 
	 //获取登录信息 start
	 function sysInfo() {
	 	var users;
	 	var depts;
	 	var dicts;
	 	$.ajax({
	 		cache : true,
	 		type : "GET",
	 		url : top.ctx + "/sysInfo",
	 		async : false,
	 		error : function(request) {
	 			parent.layer.alert("Connection error");
	 		},
	 		success : function(data) {
	 			users = data.users;
	 			depts =data.depts;
	 			dicts =data.dicts;
	 		}
	 	});
	 	return{
	 		userName:function(id){
	 			try{
	 				return users[id].name;
	 			}catch(e){
	 				return '';
	 			}
	 		},userAttr:function(id,field){
	 			try{
	 				return users[id].name;
	 			}catch(e){
	 				return '';
	 			}
	 			return users[id][field];
	 		},user:function(id){
	 			try{
	 				return users[id];
	 			}catch(e){
	 				return '';
	 			}
	 		
	 		},
	 		deptName:function(id){
	 			try{
	 				return depts[id].name;
	 			}catch(e){
	 				return '';
	 			}
	 			
	 		},deptAttr:function(id,field){
	 			try{
	 				return depts[id][field];
	 			}catch(e){
	 				return '';
	 			}
	 		
	 		},dept:function(id){
	 			try{
	 				return depts[id];
	 			}catch(e){
	 				return '';
	 			}
	 			
	 		},dictName:function(type,value){
	 			try{
	 				return dicts[type][value].name;
	 			}catch(e){
	 				return '';
	 			}
	 			
	 		}
	 	}
	 }//获取登录信息 end
	 
	//websoket 连接 start
	 function connect() {
	     var sock = new SockJS(ctx+"/endpointChat");
	     var stomp = Stomp.over(sock);
	     stomp.connect('guest', 'guest', function(frame) {

	         /**  订阅了/user/queue/notifications 发送的消息,这里雨在控制器的 convertAndSendToUser 定义的地址保持一致, 
	          *  这里多用了一个/user,并且这个user 是必须的,使用user 才会发送消息到指定的用户。 
	          *  */
//	         stomp.subscribe("/user/queue/notifications", handleNotification);
	         stomp.subscribe('/topic/getResponse', function (response) { //订阅/topic/getResponse 目标发送的消息。这个是在控制器的@SendTo中定义的。
	             toastr.options = {
	                 "closeButton": true,
	                 "debug": false,
	                 "progressBar": true,
	                 "positionClass": "toast-bottom-right",
	                 "onclick": null,
	                 "showDuration": "400",
	                 "hideDuration": "1000",
	                 "timeOut": "7000",
	                 "extendedTimeOut": "1000",
	                 "showEasing": "swing",
	                 "hideEasing": "linear",
	                 "showMethod": "fadeIn",
	                 "hideMethod": "fadeOut"
	             }
	             toastr.info(JSON.parse(response.body).responseMessage);
	         });
	     });
//	     function handleNotification(message) {
//	         wrapper.notify();
//	         toastr.info(message.body);
//	     }
	 }//websoket 连接 end
	 
});