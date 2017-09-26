//表单提交
function resubmit(action){
	var form = document.form1;
	if(action){
		form.action=action;
	}
	form.submit();
}

function deleteUrl(url){
	if(confirm("确定删除所选的吗?")){
		resubmit(url);
	}
	return false;
}

//input自动计算总量
function calSum(obj){
	var _tr=$(obj).closest("tr");
	var ams=parseFloat(_tr.find(".AMs").val()||0);
	var bms=parseFloat(_tr.find(".BMs").val()||0);
	var cms=parseFloat(_tr.find(".CMs").val()||0);
	var dms=parseFloat(_tr.find(".DMs").val()||0);
	_tr.find(".sumMs").val((ams+bms+cms+dms));
	
}

//增加行数据
function addTr(){
	var index=parseInt($("#dataTable tr:last").find(".indexNo").text());
	for(var i = 0 ; i< 5 ;i ++ ){
		$("#dataTable").append($("#dataTableModel").html());
	}
	$("#dataTable tr").find(".indexNo").each(function(index,item){
		if($(item).text()=="0"){
			$(item).text(++index);			
		}
	});
}

//计算出勾选的总量
function calCheckSumAmount(){
	var sum = 0.0;
	
	var inputNumber=$("#inputNumber").val();
	if(inputNumber.indexOf("-")!=-1){
		arr=inputNumber.split("-");
		arr[0]=parseInt(arr[0]);
		arr[1]=parseInt(arr[1]);
		$("#dataTable").find(".indexNo").each(function(index,item){
			var no=parseInt($(item).text());
			if(no>=arr[0] && no <=arr[1]){
				$(item).closest("tr").find("[name=ids]").attr("checked","checked");
			}
			
		});
	}
	
	$("[name=checkBox]").each(function(index,item){
		if($(item).attr("checked")){
			sum+=parseFloat($(item).closest("tr").find(".sumMs").val());
		}
	});
	$("#partSum").text(sum);
	alert(sum+"米");
}