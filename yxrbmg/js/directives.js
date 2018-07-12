/***
GLobal Directives
***/

// Route State Load Spinner(used on page or content load)
MetronicApp.directive('ngSpinnerBar', ['$rootScope', '$state',
    function($rootScope, $state) {
        return {
            link: function(scope, element, attrs) {
                // by defult hide the spinner bar
                element.addClass('hide'); // hide spinner bar by default

                // display the spinner bar whenever the route changes(the content part started loading)
                $rootScope.$on('$stateChangeStart', function() {
                    element.removeClass('hide'); // show spinner bar
                });

                // hide the spinner bar on rounte change success(after the content loaded)
                $rootScope.$on('$stateChangeSuccess', function(event) {
                    element.addClass('hide'); // hide spinner bar
                    $('body').removeClass('page-on-load'); // remove page loading indicator
                    Layout.setAngularJsSidebarMenuActiveLink('match', null, event.currentScope.$state); // activate selected link in the sidebar menu
                   
                    // auto scorll to page top
                    setTimeout(function () {
                        App.scrollTop(); // scroll to the top on content load
                    }, $rootScope.settings.layout.pageAutoScrollOnLoad);     
                });

                // handle errors
                $rootScope.$on('$stateNotFound', function() {
                    element.addClass('hide'); // hide spinner bar
                });

                // handle errors
                $rootScope.$on('$stateChangeError', function() {
                    element.addClass('hide'); // hide spinner bar
                });
            }
        };
    }
])

// Handle global LINK click
MetronicApp.directive('a', function() {
    return {
        restrict: 'E',
        link: function(scope, elem, attrs) {
            if (attrs.ngClick || attrs.href === '' || attrs.href === '#') {
                elem.on('click', function(e) {
                    e.preventDefault(); // prevent link click for above criteria
                });
            }
        }
    };
});

// Handle Dropdown Hover Plugin Integration
MetronicApp.directive('dropdownMenuHover', function () {
  return {
    link: function (scope, elem) {
      elem.dropdownHover();
    }
  };
});

MetronicApp.directive('formBox',function(){
	return {
		restrict:'A',
		link:function(scope,ele,attr){
			scope.formSubParamObj = {'key1':1,'key2':2};
		}
	};
});

MetronicApp.directive('formSubmit', function() {
	return {
		restrict: 'AEC',
		scope: {
			formSubParamObj: "=formSubParamObj"
		},
		controller: function($scope,$http) {
			var scope = $scope.formSubParamObj._scope;
			var before = $scope.formSubParamObj._before;
			var back = $scope.formSubParamObj._back;
			var _url = $scope.formSubParamObj._url;
			var _type = $scope.formSubParamObj._type;
			var _data = $scope.formSubParamObj._data;
			var _cache = $scope.formSubParamObj._data;
			var _dataType = $scope.formSubParamObj._data;
			var sync = scope.formSubParamObj.sync;
			
			
			/*配置表单参数*/
			scope.formModels = {};
			scope.submitting = false;
		
			/*表单提交*/
			scope.onSubmit = function() {
			 	
				if(typeof before == "function"){
					var flag =  before();
					if(!flag){
						return false;
					}
				}
				
				/*开始提交*/
				if(null == _url || _url == undefined || "" == _url) return;
				if(null == _type || _type == undefined || "" == _type) _type = "post";
				if(null == _data || _data == undefined || "" == _data) _data = {};
				if(null == _cache || _cache == undefined || "" == _cache) _cache = true;
				if(null == _dataType || _dataType == undefined || "" == _dataType) _dataType = "json";
				if(null == sync || sync == undefined || "" === sync) sync = true;
			
				$.extend(true,_data,scope.formModels);//合并表单取值以及上送数据
				
				scope.submitting = true;
				App.blockUI({
					target: "#bodyContentId",
					animate: !0
				});
				$.ajax({
					url: _url,
					data: _data,
					//dataType: _dataType,
					type:_type,
					async: sync,
					cache: _cache,
					//timeout: isNaN(JSON.parse(_data).timeout) ? 1000 * 30 : parseInt(JSON.parse(_data).timeout) * 1000,
					success: function(res) {
						/*请求成功*/
						scope.submitting = false;//按钮禁用解除
						$scope.$apply();
						if(res.retCode =="AAAAAAA"){
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
						}else{
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
						/*if(typeof back == "function"){
							back(data);
						}*/
						App.unblockUI("#bodyContentId");
					},
					error: function(res) {
						/*请求失败*/
						scope.submitting = false;//按钮禁用解除
						$scope.$apply();
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
					    App.unblockUI("#bodyContentId");
					}
				})
				
				
			};
			
		},
		link: function(scope, element, attr) {
			
		}
	};
});