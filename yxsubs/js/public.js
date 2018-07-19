/*
 *本文件用于放置公用的方法定义 
 * */

var main = {
	
}



/**
 * ajax请求
 * @param {String} _url  请求地址   默认return
 * @param {String} _type 请求类型   默认get
 * @param {function} back 成功回调
 * @param {Object} _data 上送消息   默认为空
 * @param {function} _error 失败回调
 * @param {String} _dataType 预计返回类型    默认为json
 * @param {Boolean} sync 是否异步
 * @param {Boolean} _cache   是否缓存   默认为 true
 */
main.ajax = function(param){
	
	var _url = param._url || "";
	var _type = param._type || "";
	var _back = param._back || "";
	var _data = param._data || "";
	var _cache = param._cache || "";
	var _dataType = param._dataType || "";
	var sync = param.sync || "";
	var _fileFlag = param._fileFlag;
	
	var processPar = true;
	var contentPar = "application/x-www-form-urlencoded";
	
	/*开始提交  参数默认值设置*/
	if(null == _url || _url == undefined || "" == _url) return;
	if(null == _type || _type == undefined || "" == _type) _type = "post";
	if(null == _data || _data == undefined || "" == _data) _data = {};
	if(null == _cache || _cache == undefined || "" == _cache) _cache = true;
	if(null == _dataType || _dataType == undefined || "" == _dataType) _dataType = "json";
	if(null == sync || sync == undefined || "" === sync) sync = true;
	if(_fileFlag === false){
		processPar = false;
		contentPar = false;
	}
	
	
	$.ajax({
		url: _url,
		type: _type,
		data: _data,
		async: sync,
		cache: _cache,
		processData: processPar,
		contentType: contentPar,
		//timeout: isNaN(JSON.parse(_data).timeout) ? 1000 * 30 : parseInt(JSON.parse(_data).timeout) * 1000,
		success: function(res) {
			document.title = "宇信共读-伴你左右";
		
			if(typeof res == "string" && res){
				res = JSON.parse(res);
			}else{
				console.dir(res)
			}
			if(res&&res.retCode == "AAAAAAA"){
				if(typeof _back == "function"){
					
					_back(res);
				}
			}else{
			}
		},
		error: function(res) {
			alert("ajax 请求异常!");
		}
	})
}
