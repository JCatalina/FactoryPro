//表单提交
function resubmit(action){
	var form = document.form1;
	if(action){
		form.action=action;
	}
	form.submit();
	form.action="admin/machineDailyRecord!calculateMachineMonthlyReport.action";
}

//计算总量
function calSum(obj){
	var _tr=$(obj).closest("tr");
	var ams=parseFloat(_tr.find(".AMs").val());
	var bms=parseFloat(_tr.find(".BMs").val());
	var cms=parseFloat(_tr.find(".CMs").val());
	var dms=parseFloat(_tr.find(".DMs").val());
	if(!ams) ams=0;
	if(!bms) bms=0;
	if(!cms) cms=0;
	if(!dms) dms=0;
	_tr.find(".sumMs").val((ams+bms+cms+dms));
	
}

//批量保存修改的数据
function batchUpdate(obj){
	var singleTable = $(obj).closest(".singleTable");
    var form = $('<form />', {action :"", method:"post", style:"display:none;"}).appendTo('body');
    var copy=singleTable.clone();
	form.append(copy);
	
	$.ajax({
		type:"post",
		dataType: "json",
		url:"admin/machineDailyRecord!batchEditMachineDailyRecordOnMonth.action",
		data:form.serialize(),
		success:function(data){
			if(data.success == 0){
				if(data.hasNew == true){
					var arr=data.dataArr;
						console.log(arr);
					for(var i=0;i < arr.length ; i++){
						singleTable.find(".dataTr").eq(arr[i].index).find("[name='ids']").val(arr[i].id);
					}
				}
				alert("修改成功");
			}else{
				
				alert(data.errMsg);
			}
		},
		error:function(){
			alert("发送错误");
		}
	});
	
	return false;
}


//批量设置机台的品种号
function batchSetVariety(obj){
	var singleTable = $(obj).closest(".singleTable");
	var inputDates= $(obj).closest("tr").find(".noText").val();
	var text= $(obj).closest("tr").find(".varietiesText").val();
	var arr ;
	
	console.log("inputDates:"+inputDates);
	console.log("text:"+text);
	console.log("singleTable:"+singleTable);
	
	var dateTds=singleTable.find(".dateTd");
	
	if(inputDates.indexOf("-")!=-1){
		arr=inputDates.split("-");
		arr[0]=parseInt(arr[0]);
		arr[1]=parseInt(arr[1]);
		
		dateTds.each(function(index,item){
			var date =parseInt($(item).text());
			if(date>=arr[0] && date <=arr[1]){
				var varietyNames=$(item).closest("tr").find(".varietyNames");
				varietyNames.val(text);
			}
		});
		
	}else if(inputDates.indexOf("/")!=-1){
		dateTds.each(function(index,item){
			var date =parseInt($(item).text());
			if(inputDates.indexOf(date)!=-1){
				$(item).closest("tr").find(".varietyNames").val(text);
				console.log($(item).closest("tr"));
				console.log($(item).closest("tr").find(".varietyNames"));
			}
		});
		
	}else{
		alert("输入格式有误:连续:1-12,非连续：10/12/15,单个以/或-结尾");
	}
	
}


function partCal(obj){
	var singleTable = $(obj).closest(".singleTable");
	var inputDates= $(obj).closest("tr").find(".partNo").val();
	var calRowName= $(obj).closest("tr").find(".rowName").val();
	
	var dateTds=singleTable.find(".dateTd");
	var partCalResult = 0;
	//日期连续遍历
	if(inputDates.indexOf("-")!=-1){
		arr=inputDates.split("-");
		arr[0]=parseInt(arr[0]);
		arr[1]=parseInt(arr[1]);
		
		dateTds.each(function(index,item){
			var date =parseInt($(item).text());
			if(date>=arr[0] && date <=arr[1]){
				if(calRowName == 'A'){
					partCalResult+=parseInt($(item).closest("tr").find(".AMs").val());
				}else if(calRowName == 'B'){
					partCalResult+=parseInt($(item).closest("tr").find(".BMs").val());
				}
				else if(calRowName == 'C'){
					partCalResult+=parseInt($(item).closest("tr").find(".CMs").val());
				}
				else if(calRowName == 'D'){
					partCalResult+=parseInt($(item).closest("tr").find(".DMs").val());
				}
				else if(calRowName == 'SUM'){
					partCalResult+=parseInt($(item).closest("tr").find(".sumMs").val());
				}else{
					alert("输入列名有误");
					return ;
				}
				
			}
		});
		alert("计算["+inputDates +"]"+calRowName+"列:"+partCalResult);
	
	//日期 断续遍历
	}else if(inputDates.indexOf("/")!=-1){
		dateTds.each(function(index,item){
			var date =parseInt($(item).text());
			if(inputDates.indexOf(date)!=-1){
				if(calRowName == 'A'){
					partCalResult+=parseInt($(item).closest("tr").find(".AMs").val());
				}else if(calRowName == 'B'){
					partCalResult+=parseInt($(item).closest("tr").find(".BMs").val());
				}
				else if(calRowName == 'C'){
					partCalResult+=parseInt($(item).closest("tr").find(".CMs").val());
				}
				else if(calRowName == 'D'){
					partCalResult+=parseInt($(item).closest("tr").find(".DMs").val());
				}
				else if(calRowName == 'SUM'){
					partCalResult+=parseInt($(item).closest("tr").find(".sumMs").val());
				}else{
					alert("输入列名有误");
					return ;
				}
				
			}
		});
		alert("计算["+inputDates +"]"+calRowName+"列===="+partCalResult);
		
	}else{
		alert("输入格式有误:连续:1-12,非连续：10/12/15,单个以/或-结尾");
	}
	
	
}

$(function() {  
    $("#dataTable").find("input").bind("change", function() {  
           this.defaultValue = this.value;     
    });  
    
    $("#dataTable").find("select").bind("change", function() {
    		var s = $(this).find("option:selected").val()
    		$(this).find("option").attr("selected",false);
    		var arr = $(this).find("option");
    		for(var i = 0;i < arr.length;i++){
    			if($(arr[i]).val() == s){
    				$(arr[i]).attr("selected",true);
    			}
    		}
    		//$(this).find("option:selected").attr("selected",true);
            //alert($(this).find("option:selected").text());
    });  

});  