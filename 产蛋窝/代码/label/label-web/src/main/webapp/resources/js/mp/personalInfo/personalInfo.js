var getphone="";
var flag=true;
(function($, doc) {
	$.init();
	$.ready(function() {
		/**
		 * 获取对象属性的值
		 * 主要用于过滤三级联动中，可能出现的最低级的数据不存在的情况，实际开发中需要注意这一点；
		 * @param {Object} obj 对象
		 * @param {String} param 属性名
		 */
		var _getParam = function(obj, param) {
			return obj[param] || '';
		};
		
		var userPicker = new $.PopPicker();
		userPicker.setData([{
			value: '1',
			text: '男'
		}, {
			value: '2',
			text: '女'
		}]);
		var showUserPickerButton = doc.getElementById('showUserPicker');
		var userResult = doc.getElementById('showUserPicker');
		showUserPickerButton.addEventListener('tap', function(event) {
			userPicker.show(function(items) {
				userResult.value = items[0].text;
				
			});
		}, false);
		
		
		var cityPicker3 = new $.PopPicker({
			layer: 3
		});
		cityPicker3.setData(cityData3);
		var showCityPickerButton = doc.getElementById('showCityPicker3');
		var cityResult3 = doc.getElementById('showCityPicker3');
		showCityPickerButton.addEventListener('tap', function(event) {
			cityPicker3.show(function(items) {
				cityResult3.value =_getParam(items[0], 'text') + " " + _getParam(items[1], 'text') + " " + _getParam(items[2], 'text');
				//返回 false 可以阻止选择框的关闭
				//return false;
			});
		}, false);
		
	});
})(mui, document);

var dtpicker ;
window.onload=function(){
	$("#userage").bind("click",function(){
		var nowdata="";
		if($("#userage").val()!=""){
			nowdata=$("#userage").val()
		}
		dtpicker= new mui.DtPicker({
	        type: "date",//设置日历初始视图模式
	        beginDate: 1880,//设置开始日期
	        endDate:new Date(),//设置结束日期
	        value:nowdata,
	        labels: ['年', '月', '日'],//设置默认标签区域提示语
	        customData: {
	        }//时间/日期别名
	    })
	    dtpicker.show(function(e) {
	        if(e==undefined){
	            mui.toast("请重新选择");
	            return
	        }
	        $("#userage").val(e.value)
	    })
	});
	var useragek=$("#userage").val()
	if(useragek!=""){
		dtpicker.setSelectedValue(useragek); 
	}
	getuserinfo();
}
//获取用户信息
function getuserinfo(){
	ajaxPostFunctionAsytrue('info',{},function(data){
		if(data.code == '0'){
			$(".contentImg").attr("src",data.data.headImgUrl);//用户头像
			$("#username").val(data.data.fdName);//用户真实姓名
			$("#nickname").val(data.data.nickname);//用户微信昵称
			$("#email").val(data.data.email)//用户邮箱
			var sexval="";
			var typeval="";
			//性别转换
			if(data.data.sex==1){
				sexval="男"
			}else if(data.data.sex==2){
				sexval="女"
			}else{
				sexval=""
			}
			if(data.data.fdLeve&&data.data.fdLeve==1){
				typeval="机构用户"
			}else{
				typeval="个人用户"
			}
			var birthday_=data.data.birthday;//时间格式转换
			$("#showUserPicker").val(sexval);//用户性别
			$("#userage").val(birthday_);//用户生日
			$("#showCityPicker3").val(data.data.fdAddress);//用户地址
			$("#phone").val(data.data.phone);//用户手机
			$("#usertype").val(typeval)//用户类别
			$("#number").val(data.data.fdCode)//工号
			$("#idcard").val(data.data.fdIdCard)//身份证
		}
	})	
}
//提交用户信息
function save(){
	var username=$("#username").val();
	var showUserPicker=$("#showUserPicker").val();
	var userage=$("#userage").val();
	var showCityPicker3=$("#showCityPicker3").val();
	showCityPicker3=showCityPicker3.split(" ");
	showCityPicker3 = showCityPicker3.join("_");
	var phone=$("#phone").val();
	var email=$("#email").val()//用户邮箱
	var idcard=$("#idcard").val();
	if(username==""||showUserPicker==""||userage==""||showCityPicker3==""||email==""||idcard==""){
		mui.toast("您还有信息未填写");
		return ;
	}
	// 验证身份证
	function isCardNo(card) {
		var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		return pattern.test(card);
	}
	var regex = /^[a-zA-Z\·\u4E00-\u9FA5]+$/;
	if(!regex.test(username)){
		mui.toast("请正确输入真实姓名");
		return
	}
	if ($("#idcard").val()!=""&&isCardNo($("#idcard").val()) == false){
			mui.toast("身份证号码不正确！");
			$("#idcard").focus();
			return;
	}
	var sexk;
	if(showUserPicker=="男"){
		sexk=1;
	}else{
		sexk=2
	}
	var param={
			"fdName":username,
			"sex":sexk,
			"birthday":userage,
			"fdAddress":showCityPicker3,
			"email":email,
			"fdIdCard":idcard
	}
	ajaxPostFunctionAsytrue('update',param,function(data){
		if(data.code == '0'){
			mui.toast("保存成功");
			setTimeout(function(){
				//getuserinfo();
				window.location.href="p_info#/personal";
			},800)
		}else{
			mui.toast(data.data);	
		}
	})
}
function add0(m){
	return m<10?'0'+m:m 
}
//时间格式转换
function format(shijianchuo){
	//时间戳是整数，否则要parseInt转换
	var time = new Date(shijianchuo);
	var y = time.getFullYear();
	var m = time.getMonth()+1;
	var d = time.getDate();
	return y+'-'+add0(m)+'-'+add0(d);
}