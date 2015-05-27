var rest_prefix='/spring_web_template/';//接口工程名称
var rowEditingPlugin;//声明全局行编辑插件

//设置插件路径
Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', 'ext-4.2/ux');

//导出Excel公用方法
function exportExcel(store){
	var import_params={'exportFlag':'1'};
	Ext.apply(store.proxy.extraParams,import_params);
	store.load({
		callback:function(records, operation, success){
			import_params={'exportFlag':'0'};
			Ext.apply(store.proxy.extraParams,import_params);
			
			if(success&&operation.params.exportFlag==1){
				document.location=rest_prefix+"/exportData.xls";
			}else{
				Ext.MessageBox.alert('温馨提示', '导出异常,请稍后再试！');	
			}
		}
	});	
}