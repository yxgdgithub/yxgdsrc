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
	var _cache = param._cache;
	var _dataType = param._dataType || "";
	var sync = param.sync;
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
	
	openMask();
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
			console.log(_url,res)
			if(typeof res == "string"){
				res = JSON.parse(res);
			}
			if(res&&res.retCode == "AAAAAAA"){
				closeMask()
				/*请求成功*/
				App.alert({
			        container: "", // alerts parent container(by default placed after the page breadcrumbs)
			        place: "append", // append or prepent in container 
			        type: "success",  // alert's type
			        message:  res.retMsg,  // alert's message
			        close: true, // make alert closable
			        reset: true, // close all previouse alerts first
			        focus: true, // auto scroll to the alert after shown
			        closeInSeconds: 0, // auto close after defined seconds
			        icon: "" // put icon before the message   
			    });
				if(typeof _back == "function"){
					_back(res);
				}
			}else{
				closeMask()
				
			   	if(res.retCode == "IMBK006"){
			   		alert(res.retMsg);
			   		location.href = "login.html";
			   		return false;
			   	}
			   	App.alert({
			        container: "", // alerts parent container(by default placed after the page breadcrumbs)
			        place: "append", // append or prepent in container 
			        type: "danger",  // alert's type
			        message: res.retMsg,  // alert's message
			        close: true, // make alert closable
			        reset: true, // close all previouse alerts first
			        focus: true, // auto scroll to the alert after shown
			        closeInSeconds: 0, // auto close after defined seconds
			        icon: "" // put icon before the message   
			    });
			}
			
		},
		error: function(res) {
			closeMask()
			App.alert({
		        container: "", // alerts parent container(by default placed after the page breadcrumbs)
		        place: "append", // append or prepent in container 
		        type: "danger",  // alert's type
		        message:  res.retMsg,  // alert's message
		        close: true, // make alert closable
		        reset: true, // close all previouse alerts first
		        focus: true, // auto scroll to the alert after shown
		        closeInSeconds: 0, // auto close after defined seconds
		        icon: "" // put icon before the message   
		    });
		}
	})
}

//打开蒙版层
function openMask() {
	var mask = '<div class="layerMask" style="position:fixed;_position:absolute;top:0;left:0;width:100%;height:100%;text-algin:center;z-index:999999999;background:#fff;opacity:0.8;filter: alpha(opacity=80);"><div style="position:absolute;top:50%;left:50%;margin:-30px 0 0 -30px;">加载中....</div></div>';
	if($("body").find(".layerMask").length > 0) {
		$(".layerMask").show();
	} else {
		$("body").append(mask);
	}
	$("body").css("overflow", "hidden");
}

//关闭蒙板层
function closeMask() {
	if($("body").find(".layerMask").length > 0) {
		$(".layerMask").hide();
	}
	$("body").css("overflow", "auto")
}

(function($) {
	var ms = {
		init: function(obj, args) {
			return (function() {
				ms.fillHtml(obj, args);	//调用填充html方法 
				ms.bindEvent(obj, args);	//为html绑定时间
			})();
		},
		//填充html
		fillHtml: function(obj, args) {
			return (function() {
				obj.empty();	//清除生成分页容器的html内容
				if(args.pageNm==""){
					args.pageNm = 5;
				}
				var totalPage = Math.ceil(args.totalNum/args.pageNm);
				//slect
				var selectHtml = '<div class="col-md-4 col-sm-4" style="padding-top: 11.5px;">当前展示&nbsp;'+
									'<select class="form-control input-sm input-xsmall input-inline pageSel">'+
										'<option value="5">5</option>'+
										'<option value="10">10</option>'+
										'<option value="20">20</option>'+
										'<option value="50">50</option>'+
									'</select>&nbsp;条'+
								'</div>';
				obj.append(selectHtml);
				obj.find(".pageSel").val(args.pageNm)
                
                var pageContent = 	'<div class="col-md-8 col-sm-8">'+
                						'<div class="" style="line-height: 53px; float: right; padding: 0 10px;">'+
	                						'到第<input type="text" class="input-sm input-inline skipIpt" style="width: 50px;margin: 0 5px;">页'+
	                						'<span class="btn btn-info pageSkip" style="margin-left:5px;" href="jvascript:;">确定</span>'+
	                					'</div>'+
                						'<div class="paging_bootstrap_full_number" id="">'+
                						'<div class="" style="line-height: 53px; float: right; padding: 0 10px;" id="" role="status" aria-live="polite">共<span>'+args.totalNum+'</span>条&nbsp;共<span>'+totalPage+'</span>页</div>'+
	                						'<ul class="pagination" style="visibility: visible; float: right;">'+
	                						'</ul>'+
                						'</div>'+
                					'</div>';
                obj.append(pageContent);  		
				
				var prevHtml =	'<li class="prev first"><a href="javascript:;" title="First"><i class="fa fa-angle-double-left"></i></a></li>'+
								'<li class="prev prevBtn"><a href="javascript:;" title="Prev"><i class="fa fa-angle-left"></i></a></li>';
				obj.find("ul").append(prevHtml); 	
				
				
				
				for(var i=-2;i<3;i++){
					var index =  Number(args.current)+i;
					if(index<1||index>totalPage){
						continue;
					}
					if(index!=args.current){
						obj.find("ul").append('<li><a class="pageNumBtn" href="javascript:;">'+index+'</a></li>');
					}else{
						obj.find("ul").append('<li class="active"><a href="javascript:;">'+index+'</a></li>');
					}
				}
				
				var prevHtml =	'<li class="next nextBtn"><a href="javascript:;" title="Next"><i class="fa fa-angle-right"></i></a></li>'+
								'<li class="next lastBtn" data-value="'+totalPage+'"><a href="javascript:;" title="Last"><i class="fa fa-angle-double-right"></i></a></li>';
				obj.find("ul").append(prevHtml); 
				
				if(args.current == 1){
					obj.find(".prev").addClass("disabled");
				}
				
				if(args.current == totalPage || totalPage == 0){
					obj.find(".next").addClass("disabled");
				}
				
				
			})();
		},
		//绑定事件
		bindEvent: function(obj, args) {
			return (function() {
				obj.off();
				/*下拉框选中条数*/
				obj.on("change",".pageSel",function(e){
					var pageNm = $(e.target).val();
					args.pageNm = pageNm;
					args.current = 1;
					ms.snedAjax(obj,args)
				
				})
				
				/*点击首页按钮*/
				obj.on("click",".first",function(e){
					if($(this).hasClass("disabled")){
						return false;
					}
					var pageNm = obj.find(".pageSel").val();
					args.pageNm = pageNm;
					args.current = 1;
					ms.snedAjax(obj,args)
				})
				
				/*点击上一页按钮*/
				obj.on("click",".prevBtn",function(e){
					if($(this).hasClass("disabled")){
						return false;
					}
					var pageNm = obj.find(".pageSel").val();
					var current = obj.find("li.active").text()-1;
					args.pageNm = pageNm;
					args.current = current;
					ms.snedAjax(obj,args)
				})
				
				/*点击页码按钮*/
				obj.on("click",".pageNumBtn",function(e){
					var pageNm = obj.find(".pageSel").val();
					var current = $(this).text();
					args.pageNm = pageNm;
					args.current = current;
					ms.snedAjax(obj,args)
				});
				
				/*点击下一页按钮*/
				obj.on("click",".nextBtn",function(e){
					if($(this).hasClass("disabled")){
						return false;
					}
					var pageNm = obj.find(".pageSel").val();
					var current = Number(obj.find("li.active").text())+1;
					args.pageNm = pageNm;
					args.current = current;
					ms.snedAjax(obj,args)
				})
				
				/*点击尾页按钮*/
				obj.on("click",".lastBtn",function(e){
					if($(this).hasClass("disabled")){
						return false;
					}
					var pageNm = obj.find(".pageSel").val();
					var current = $(this).attr('data-value');
					args.pageNm = pageNm;
					args.current = current;
					ms.snedAjax(obj,args)
				
				})
				
				/*点击跳转页码按钮*/
				obj.on("click",".pageSkip",function(e){
					var current = obj.find(".skipIpt").val();	/*调转页*/
					var pageNm = obj.find(".pageSel").val();	/*每页展示条数*/
					var totalPage = Number($(".lastBtn").attr('data-value'));	/*总页数*/
					
					var reg=/^[1-9]\d*$/;
					if(!reg.test(current)){
						return false;
					}
					
					if (current <= totalPage && current >= 1) {
						args.pageNm = pageNm;
						args.current = current;
						ms.snedAjax(obj,args)
						
					} else {
						alert("请输入正确的页码！");
					}
					
				
				})
				
			})();
		},
		snedAjax:function(obj,args){
			return (function(){
				
				$("#"+args.formId).find(".page").val(args.current)
				$("#"+args.formId).find(".rows").val(args.pageNm)
				
				var subFormDom = document.getElementById(args.formId);
            	var subForm = new FormData(subFormDom);
            	
            	
				main.ajax({
	        		"_url":args.ajaxPar._url,
	        		"_type":args.ajaxPar._type,
	        		"_data":subForm,
	        		"_fileFlag":false,
	        		"_back":function(res){
	        			ms.fillHtml(obj, {
							totalNum: res.count,
							pageNm:args.pageNm,
							current: args.current
						});
	        			args.backFn(res);
	        		}
	        	})
				
				
				
			})()
		}
	}
	$.fn.createPage = function(options) {
		var args = $.extend({
			ajaxPar:{},
			formId:"",
			totalNum: 15,
			pageNm:5,
			current: 1,
			backFn: function() {}
		}, options);
		ms.init(this, args);
	}
})(jQuery);
