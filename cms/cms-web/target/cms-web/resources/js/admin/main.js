$(function(){
	$("#listTable").trColorChange();
	$("a.delete").confirmOperator();
	$("a.clearUser").confirmOperator({msg:"清空用户操作不可逆，确定操作吗？"});
});