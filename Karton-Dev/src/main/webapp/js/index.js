function initialize(){

	document.getElementById("btnUpload").onchange = function() {
		document.getElementById("uploadFile").value = this.value;
	};
	
	$('#num_minCombAmount').numberbox(numProperties);
	$('#num_minAmount').numberbox(numProperties);
	$('#num_minAmountPreferred').numberbox(numProperties);
	$('#num_minMeter').numberbox(numProperties);
	$('#num_minKilo').numberbox(numProperties);
	$('#num_minKiloPreferred').numberbox(numProperties);
	$('#num_kg').numberbox(numProperties);
	$('#num_kg').numberbox({precision:2});
	
	$("input[type='checkbox']").click(inputFieldsEnableDisableHandler)
	inputFieldsEnableDisableHandler();
	 
	 function inputFieldsEnableDisableHandler(){
		 var isDefaultParamsChecked = $('#chk_defaultParams').prop('checked');
		 var isKgModeChecked =  $('#chk_kgMode').prop('checked');
		 
		 //handle input fields' enable/disable properties
		 $("#num_minCombAmount").numberbox('readonly', isDefaultParamsChecked||isKgModeChecked)
		 $("#num_minAmount").numberbox('readonly', isDefaultParamsChecked||isKgModeChecked)
		 $("#num_minAmountPreferred").numberbox('readonly', isDefaultParamsChecked||isKgModeChecked)
		 $("#num_minMeter").numberbox('readonly', isDefaultParamsChecked||isKgModeChecked)
		 
		 $("#num_minKilo").numberbox('readonly', isDefaultParamsChecked || !isKgModeChecked)
		 $("#num_minKiloPreferred").numberbox('readonly', isDefaultParamsChecked || !isKgModeChecked)
		 $("#num_kg").numberbox('readonly', isDefaultParamsChecked)
					 
		 if(isDefaultParamsChecked = true){ 
			 setDefaultParamVals();
		 }
	 }
	 
	 function setDefaultParamVals(){
		 $('#num_minCombAmount').numberbox('setValue', num_minCombAmount_init);
		 $('#num_minAmount').numberbox('setValue', num_minAmount_init);
		 $('#num_minAmountPreferred').numberbox('setValue', num_minAmountPreferred_init);
		 $('#num_minMeter').numberbox('setValue', num_minMeter_init);

		 $('#num_minKilo').numberbox('setValue', num_minKilo_init);
		 $('#num_minKiloPreferred').numberbox('setValue', num_minKiloPreferred_init);
		 $('#num_kg').numberbox('setValue', num_kg_init);		 
	 }
}