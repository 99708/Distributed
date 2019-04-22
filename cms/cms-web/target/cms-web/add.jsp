<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/uploadify/uploadify.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#attach").uploadify({
			swf:$("#ctx").val()+"/resources/uploadify/uploadify.swf",
			uploader:"upload",
			fileObjName:"attach",
			auto:false,
			fileTypeExts:"*.jpg;*.gif;*.png;*.doc;*.docx;*.txt;*.xls;*.xlsx;*.rar;*.zip;*.pdf;*.flv;*.swf",
			onUploadSuccess:function(file, data, response) {
				alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
				var ao = $.parseJSON(data);
				alert(ao.result);
			}
		});
		$("#upload").click(function() {
			$("#attach").uploadify("upload","*");
		})
	})
</script>
</head>
<body>
	<input type="hidden" id="ctx" value="<%=request.getContextPath()%>"/>
	<input type="file" name="attach" id="attach" value=""/>
	<input type="button" value="上传文件" id="upload"/>
</body>
</html>