<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="data.Constraints" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css"
	href="./jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="./jquery-easyui-1.4.1/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="./jquery-easyui-1.4.1/themes/color.css">
<script type="text/javascript"
	src="./jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="./jquery-easyui-1.4.1/jquery.easyui.min.js"></script>

<script src="./js/index.js"></script>
	
<!-- <link rel="stylesheet" href="./jquery-ui-1.11.3.custom/jquery-ui.min.css">
<script src="./jquery-easyui-1.4.1/jquery.min.js"></script>
<script src="./jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
 -->
<title>Karton Optimizer</title>
<script type="text/javascript">
	var num_minCombAmount_init = <%= data.Constraints.MIN_ADET_KOMBINASYON %>;
	var num_minAmount_init = <%= data.Constraints.MIN_ADET %>;
	var num_minAmountPreferred_init = <%= data.Constraints.MIN_ADET_TERCIH %>;
	var num_minMeter_init = <%= data.Constraints.MIN_METRE %>;
	var num_minKilo_init = <%= data.Constraints.MIN_KILO %>;
	var num_minKiloPreferred_init = <%= data.Constraints.MIN_KILO %>;
	var num_kg_init = <%= data.Constraints.m2kg %>;
	var numProperties = {};
	
	numProperties.min = 0;
	numProperties.max = 999999;
	numProperties.width = '100px';
	
	$(function() {
		initialize();
	});
</script>
</head>
<body>
	<form action="uploadServlet" method="post"
		enctype="multipart/form-data">
		<div style="padding-top: 50px;">
			<div style="padding-bottom: 70px; text-align: center;">
				<span style="font-size: 40px; font-family: 'Verdana'; color: gray;">Karton
					Optimizer !</span>
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
			
			<div id="div_formParams" style="position:inherit; padding-top: 70px; display: inline-block;">
				<div style="float: left; position: relative;">
					<input type="checkbox" name="chk_defaultParams" id="chk_defaultParams" class="" checked/>
					<span class="cl_lbl_checkboxes"> Varsayilan Parametreler</span><br />
					
					<span class="cl_parameterLabels">Min Kombinasyon Adet</span>
					<input type="text" id="num_minCombAmount" name="num_minCombAmount" value="" /><br>
					
					<span class="cl_parameterLabels">Min Adet</span>
					<input type="text" id="num_minAmount" name="num_minAmount" value="" /><br>
					
					<span class="cl_parameterLabels">Min Adet (Tercih)</span>
					<input id="num_minAmountPreferred" name="num_minAmountPreferred" value="" /><br>
					
					<span class="cl_parameterLabels">Minimum Metre</span>
					<input id="num_minMeter" name="num_minMeter" value="" /><br>
				</div>
				
				<div style="float: left; position: relative; padding-left: 100px;">
					<input type="checkbox" name="chk_kgMode" id="chk_kgMode" class="" />
					<span class="cl_lbl_checkboxes"> Kilogram Modu</span><br /> 
					
					<span class="cl_parameterLabels">Min Kilo</span>
					<input id="num_minKilo" name="num_minKilo" value="" class="inputField" /><br> 
					
					<span class="cl_parameterLabels">Min Kilo (Tercih)</span>
					<input id="num_minKiloPreferred" name="num_minKiloPreferred" value="" /><br>
					
					<span class="cl_parameterLabels">Kilogram/Metrekare</span>
					<input id="num_kg" name="num_kg" value="" class="inputField" /><br>
				</div>
			</div>
			<br>
			<div style="padding-top: 30px;text-align: center;">
				<input type="submit" value="Go!" class="color blue button"
					style="width: 150px; height: 50px; font-size: 25px;" />
			</div>
			<div id="div_result" style="padding-top: 30px;text-align: center;">
				${requestScope.resultHtml}</div>
		</div>
	</form>
</body>
</html>