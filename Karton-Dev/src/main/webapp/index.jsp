<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/index.css">
<title>Karton Optimizer</title>
<script type="text/javascript">
	window.onload = function() {
		document.getElementById("btnUpload").onchange = function() {
			document.getElementById("uploadFile").value = this.value;
		};
	}
</script>
</head>
<body>


	<form action="uploadServlet" method="post"
		enctype="multipart/form-data">
		<center>
			<div style="padding-top: 100px;">
				<div style="padding-bottom: 100px;">
					<span style="font-size: 40px; font-family: 'Verdana'; color: gray;">Welcome
						To Karton Optimizer !</span>
				</div>
				<div style="position: relative; left: 40%;">
					<div
						style="float: left; position: relative; text-align: left; padding-right: 20px">
						<input id="uploadFile" placeholder="Choose File"
							disabled="disabled" style="height: 30px;" />
					</div>
					<div style="float: left; position: relative;">
						<div class="fileUpload btn btn-primary">
							<span>Select File</span> <input type="file" id="btnUpload"
								name="file" class="upload" />
						</div>
					</div>
				</div>
				<div style="padding-top: 70px;">
					<input type="submit" value="Go!" class="color blue button"
						style="width: 150px; height: 50px; font-size: 25px;" />
				</div>
				<div id="div_result" style="padding-top: 30px;">
					 ${requestScope.resultHtml}
				</div>
			</div>
		</center>
	</form>

</body>
</html>