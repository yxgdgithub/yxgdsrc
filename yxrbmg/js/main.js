/***
Metronic AngularJS App Main Script
***/

/* Metronic App */
var MetronicApp = angular.module("MetronicApp", [
    "ui.router", 
    "ui.bootstrap", 
    "oc.lazyLoad",  
    "ngSanitize",
    'jcs-autoValidate',
    'localize',
    'angular-ladda'
]); 

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
MetronicApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        // global configs go here
    });
}]);

//AngularJS v1.3.x workaround for old style controller declarition in HTML
MetronicApp.config(['$controllerProvider', function($controllerProvider) {
  // this option might be handy for migrating old apps, but please don't use it
  // in new ones!
  $controllerProvider.allowGlobals();
}]);

/********************************************
 END: BREAKING CHANGE in AngularJS v1.3.x:
*********************************************/

/* Setup global settings */
MetronicApp.factory('settings', ['$rootScope', function($rootScope) {
    // supported languages
    var settings = {
        layout: {
            pageSidebarClosed: false, // sidebar menu state
            pageContentWhite: true, // set page content layout
            pageBodySolid: false, // solid body color state
            pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
        },
        assetsPath: 'libs/assets',
        globalPath: 'libs/assets/global',
        layoutPath: 'libs/assets/layouts/layout2',
        echarts: 'libs/assets/global/scripts/echarts.js',
        echarts_all: 'libs/assets/global/scripts/echarts-all.js',
        knob:'libs/scripts/jquery.knob.js',
        throttle:'libs/scripts/jquery.throttle.js',
        classycountdown:'libs/scripts/jquery.classycountdown.js',
        classycount:'libs/scripts/jquery.classycountdown.min.js'
    };

    $rootScope.settings = settings;

    return settings;
}]);

/* Setup App Main Controller */
MetronicApp.controller('AppController', ['$scope', '$rootScope', function($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function() {
        //App.initComponents(); // init core components
        //Layout.init(); //  Init entire layout(header, footer, sidebar, etc) on page load if the partials included in server side instead of loading with ng-include directive 
    });
}]);

/***
Layout Partials.
By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial 
initialization can be disabled and Layout.init() should be called on page load complete as explained above.
***/

/* Setup Layout Part - Header */
MetronicApp.controller('HeaderController', ['$scope', function($scope) {
	$scope.iptFlag = false;		//输入框校验是否成功
	
	
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
    });
    $scope.pwdSubmit = function(){
    	if(!$scope.iptFlag){
    		return false;
    	}
    	
    	var _url = "login/mofifyPwd";
    	var data = {
    		"oldPassword":$("#loginPwd1").val(),
    		"password":$("#loginPwd2").val()
    	}
    	console.log("请求数据",data)
		main.ajax({
    		"_url":_url,
    		"_type":"post",
    		"_data":data,
    		"_back":function(res){
    			console.log(res)
    			$(".changePwd").text("").siblings("p").text("")
    			$("#pwdModal").modal("hide");
    		}
    	})
    	
    	
    }
    
    $scope.closeModal = function(){
    	$(".changePwd").text("").siblings("p").text("")
    	$("#pwdModal").modal("hide");
    }
    
    $scope.pwdBlur = function($event){
    	var iptList = $(".changePwd");
    	var that = $event.target;
    	var index = $(that).attr("data-tabindex");
    	$scope.iptFlag = false;
    	
    	iptList.siblings("p").text("")
    	//密码框非空以及长度校验
    	if($(that).val() == ""){
    		switch (index){
				case "1" : {
					$(that).siblings("p").text("请输入您的原密码")
				}
				break;
				case "2" : {
					$(that).siblings("p").text("请输入您的新密码")
				}
				break;
				case "3" : {
					$(that).siblings("p").text("请确认您的新密码")
				}
				break;
			}
    		return false;
    	}
		
		//密码框长度校验
		if($(that).val().length<6){
			$(that).siblings("p").text("您输入的密码不符合规则");
			return false;
		}
		
		//校验新密码与确认密码是否一致
		if($("#loginPwd2").val() == ""){
			$("#loginPwd2").siblings("p").text("请输入您的新密码");
			return false;
		}else if($("#loginPwd3").val() == ""){
			$("#loginPwd3").siblings("p").text("请确认您的新密码");
			return false;
		}else{
			if( $("#loginPwd2").val() != $("#loginPwd3").val()){
				$("#loginPwd3").siblings("p").text("您输入的确认密码与新密码不一致");
				return false;
			}
		}
		
		$scope.iptFlag = true;
    }
    
    $scope.loginOut = function(){
    	var _url = "login/toLogin";
    	var data = {};
		main.ajax({
    		"_url":_url,
    		"_type":"post",
    		"_data":data,
    		"_back":function(res){
    			$("#logOutModal").modal("hide");
    			location.href = "login.html"
    		}
    	})
    }
}]);

/* Setup Layout Part - Sidebar */
MetronicApp.controller('SidebarController', ['$state', '$scope', function($state, $scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initSidebar($state); // init sidebar
    });
}]);

/* Setup Layout Part - Quick Sidebar */
MetronicApp.controller('QuickSidebarController', ['$scope', function($scope) {    
    $scope.$on('$includeContentLoaded', function() {
       setTimeout(function(){
            QuickSidebar.init(); // init quick sidebar        
        }, 2000)
    });
}]);

/* Setup Layout Part - Theme Panel */
MetronicApp.controller('ThemePanelController', ['$scope', function($scope) {    
    $scope.$on('$includeContentLoaded', function() {
        Demo.init(); // init theme panel
    });
}]);

/* Setup Layout Part - Footer */
MetronicApp.controller('FooterController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initFooter(); // init footer
    });
}]);

/* Setup Rounting For All Pages */
MetronicApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/dashboard.html");  
    $stateProvider
        // Dashboard
        .state('dashboard', {
            url: "/dashboard.html",
            templateUrl: "views/dashboard.html",            
            data: {pageTitle: '首页'},
            controller: "dashboard",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'libs/public-css/morris.css',                            
                            'libs/morris.min.js',
                            'libs/raphael-min.js',                            
                            'libs/jquery.sparkline.min.js',
                            'libs/assets/global/scripts/echarts-all.js',
                            'libs/public-css/jquery.classycountdown.css',
                            'libs/public-css/normalize.css',
                            'libs/scripts/jquery.knob.js',
                            'libs/scripts/jquery.throttle.js',
                            'libs/scripts/jquery.classycountdown.js',
                            'libs/scripts/jquery.classycountdown.min.js',
                            'libs/dashboard.min.js',
                            'js/controllers/dashboard.js'
                        ] 
                    });
                }]
            }
        })

		// maintenance
        .state('maintenance', {
            url: "/maintenance",
            templateUrl: "views/maintenance.html",            
            data: {pageTitle: '共读维护'},
            controller: "maintenance",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            "libs/moment.min.js",
                            /*日期*/
                        	//"libs/bootstrap-daterangepicker/daterangepicker.min.css",
                        	//"libs/bootstrap-daterangepicker/daterangepicker.min.js",
				            "libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js",
					        "libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" ,
					        "libs/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" ,
				            "libs/bootstrap-datepicker/js/bootstrap-datepicker.js",
					        "libs/bootstrap-timepicker/css/bootstrap-timepicker.min.css" ,
				            "libs/bootstrap-timepicker/js/bootstrap-timepicker.min.js",
					        "libs/clockface/css/clockface.css" ,
				            "libs/clockface/js/clockface.js",
				            "libs/components-date-time-pickers.min.js",
				            /*image*/
					    	"libs/cubeportfolio/js/jquery.cubeportfolio.js",
					    	"libs/cubeportfolio/css/cubeportfolio.css",
					    	"libs/portfolio.min.css",
                        	/*弹框*/
                        	/*"libs/bootstrap-modal/css/bootstrap-modal-bs3patch.css",
        					"libs/bootstrap-modal/css/bootstrap-modal.css" ,
        					"libs/bootstrap-confirmation/bootstrap-confirmation.js",
        					"libs/ui-confirmations.js",*/
        					/*弹框列表*/
        					/*"libs/datatables/datatables.min.css" ,
        					"libs/datatables/plugins/bootstrap/datatables.bootstrap.css" ,
        					"libs/datatable.js" ,
					        "libs/datatables/datatables.min.js" ,
					        "libs/datatables/plugins/bootstrap/datatables.bootstrap.js" ,
					        "libs/table-datatables-managed.js",*/
					       /*error*/
					       	"libs/codemirror/lib/codemirror.js",
        					
                            'js/controllers/maintenance.js' 
                        ]
                    });
                }]
            }
        })
        
        // add_newbooks
        .state('add_newbooks', {
            url: "/add_newbooks.html",
            templateUrl: "views/add_newbooks.html",
            data: {pageTitle: '图书新增'},
            controller: "add_newbooks",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            'libs/ckeditor/ckeditor.js',
                            'libs/select2/js/select2.full.min.js',
                            'libs/angularjs/plugins/angular-file-upload/angular-file-upload.min.js',
                            'js/controllers/add_newbooks.js'
        					/*"../assets/global/plugins/select2/css/select2.min.css",
        					"../assets/global/plugins/select2/css/select2-bootstrap.min.css",
        					"../assets/global/plugins/select2/js/select2.full.min.js",
            				"../assets/global/plugins/jquery-validation/js/jquery.validate.min.js",
            				"../assets/global/plugins/jquery-validation/js/additional-methods.min.js",
            				"../assets/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js",
            				"../assets/global/scripts/app.min.js",
							"../assets/pages/scripts/form-wizard.js"*/
                        ] 
                    }]);
                }] 
            }
        })
        .state('query_newbooks', {
            url: "/query_newbooks",
            templateUrl: "views/query_newbooks.html",
            data: {pageTitle: '图书查询'},
            controller: "general_page",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
                        	/*列表*/
            				"libs/datatables/datatables.min.css" ,
        					"libs/datatables/plugins/bootstrap/datatables.bootstrap.css" ,
                            'js/controllers/general_page.js'
                        ] 
                    }]);
                }] 
            }
        }) 
        // 新增 echars 饼状图
        .state('echarts', {
            url: "/echars.html",
            templateUrl: "views/echars.html",
            data: {pageTitle: 'UI echars'},
            controller: "echars",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            'libs/assets/global/scripts/echarts-all.js',
                            'libs/assets/global/scripts/pie.js',
                            'js/controllers/echars.js',
                        ] 
                    }]);
                }] 
            }
        })
}]);

/* Init global settings and run the app */
MetronicApp.run(["$rootScope", "settings", "$state","defaultErrorMessageResolver", function($rootScope, settings, $state,defaultErrorMessageResolver) {
    $rootScope.$state = $state; // state to be accessed from view
    $rootScope.$settings = settings; // state to be accessed from view
    
    defaultErrorMessageResolver.getErrorMessages().then(function(errorMessages) {
		errorMessages['tooYoung'] = '年龄必须小于{0}';
		errorMessages['tooOld'] = '年龄不能大于{0}';
		errorMessages['badUsername'] = '用户名只能包含数字、字母或下划线';
		errorMessages['minErr'] = '用户名最少为{0}位';
		errorMessages['maxErr'] = '最大输入字符不能超过{0}位';
	});
}]);