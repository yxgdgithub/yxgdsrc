angular.module('ioniclub')
/*共度计划*/
.controller('ExploreCtrl', function($scope, $timeout, $cordovaGoogleAnalytics, $ionicSlideBoxDelegate) {

	var userToken=$("#userToken").val();
	document.title = "宇信共读-伴你左右";
	$scope.$on('$ionicView.afterEnter', function() {
	    document.addEventListener("deviceready", function() {
	      // trackView
	      $cordovaGoogleAnalytics.trackView('explore view');
	    }, 
	    true);
	
	})
	
	/*正在进行、已完成、下期预告*/
	$scope.index = 0;
	explore(0);
	$scope.slideChanged = function(index){
		$scope.index = index;
	
		if (index == 0) {
			main.ajax({
			"_url": "/subser/gdplan/list/"+0,
			"_type":"post",
			"_data":{'userToken': userToken},
			"_back":function(res){
					document.title = "宇信共读-伴你左右";
					$scope.underway = res.content;
					$scope.$apply();
			}
		})
		}
		else if (index == 1) {
			main.ajax({
			"_url": "/subser/gdplan/list/"+1,
			"_type":"post",
			"_data":{'userToken': userToken},
			"_back":function(res){
					document.title = "宇信共读-伴你左右";
					$scope.accomplish = res.content;
					$scope.$apply();
			}
		})
		}
		else if (index == 2) {
			main.ajax({
			"_url": "/subser/gdplan/list/"+2,
			"_type":"post",
			"_data":{'userToken': userToken},
			"_back":function(res){
					document.title = "宇信共读-伴你左右";
					$scope.advance = res.content;
				    $scope.$apply();
			}
		})
		}
	};
	
	// lhl 20180618将ajax查询部分提至slideChanged，实现点击+滑动都刷新页面的操作。
	$scope.activeSlide= function(index){
		
		$ionicSlideBoxDelegate.slide(index);
		
	};
	
	/*共读计划接口*/
	function explore(){
		main.ajax({
			"_url": "/subser/gdplan/list/"+0,
			"_type":"post",
			"_data":{'userToken': userToken},
			"_back":function(res){
					document.title = "宇信共读-伴你左右";
					$scope.underway = res.content;
					$scope.$apply();
					
			}
		})
	}
	
	//查看签到
	// lhl 0610 日历组件传入userToken
	$scope.buttonQian= function(gdBeginDate,gdEndDate){
		main.ajax({
			"_url": "/subser/gdplan/signdetail",
			"_type":"post",
			"_data":{
				beginDate:gdBeginDate,
				endDate:gdEndDate,
				userToken:userToken
			},
			"_back":function(res){
				var signDayList = res.content;
				var signDayArr = [];
				for(var i = 0;i < signDayList.length;i++){
					signDayArr[i] = {"signDay":signDayList[i].signInDate.slice(-2)};
				}
				calUtil.init(signDayArr,"calendar",new Date(gdEndDate),userToken);
			}
		})
		$(".masking").show();
		$(".exploreSign").show();
	};
	
	//查看签到弹出框-关闭
	$scope.codeClose= function(){
		$(".masking").hide();
		$(".exploreSign").hide();
		};
  
		/*关闭按钮*/
  	$(".codeClose").click(function(){
		$(".masking").hide();
		$(".exploreSign").hide();
	})
})

.controller('ExploreBookDetailCtrl', function($scope, $rootScope, $state, $timeout, $log,
    $ionicTabsDelegate, $stateParams, $ionicLoading,
    $ionicScrollDelegate, $ionicActionSheet,
    $cordovaSocialSharing, $cordovaGoogleAnalytics,
    Topic, User) {
    // console.table($ionicHistory.viewHistory());
    $scope.finished = false;
    // get current user
    var currentUser = User.getCurrentUser();
    $scope.loginName = currentUser.loginname || null;
    
	/*图书详情*/
	var bookId = $stateParams.id;
	main.ajax({
		"_url": "/subser/book/"+bookId,
		"_type":"post",
		"_data":"",
		"_back":function(res){
			
			$scope.book = res.content;
			
		}
	})
})