$(function(){
	rangeTimetion('stime','etime');
})

function rangeTimetion(start,end){
	var sDate = laydate.render({
		elem: "#"+start,
		type: "date",
		format: "yyyy-MM-dd",
		istime: false,
		btns: ['confirm'],
		done : function(value,date){
			if(value != ""){
				eDate.config.min.year = date.year;
				eDate.config.min.month = date.month - 1;
				eDate.config.min.date = date.date;
				eDate.config.min.hours = date.hours ;
				eDate.config.min.minutes  = date.minutes ;
				eDate.config.min.seconds  = date.seconds ;
			}else{
				eDate.config.min.year = "";
				eDate.config.min.month = "";
				eDate.config.min.date = "";
				eDate.config.min.hours = "";
				eDate.config.min.minutes  = "";
				eDate.config.min.seconds  = "";
			}
		}
	})
	
	var eDate = laydate.render({
		elem:"#"+end,
		type: "date",
		format: "yyyy-MM-dd",
		istime: false,
		btns: ['confirm'],
		done : function (value, date){
			if(value != ""){
				sDate.config.max.year = date.year;
				sDate.config.max.month = date.month - 1;
				sDate.config.max.date = date.date;
				sDate.config.min.hours = date.hours ;
				sDate.config.min.minutes  = date.minutes ;
				sDate.config.min.seconds  = date.seconds ;
			}else{
				sDate.config.max.year = "";
				sDate.config.max.month = "";
				sDate.config.max.date = "";
				sDate.config.min.hours = "";
				sDate.config.min.minutes  = "";
				sDate.config.min.seconds  = "";
			}
		}
	})
}

function rangeTime(){
	laydate.render({
		elem: "#stime",
		format: "yyyy-MM-dd",
		type: "date"
	})
	
	laydate.render({
		elem:"#etime",
		type: "date",
		format: "yyyy-MM-dd"
	})
}