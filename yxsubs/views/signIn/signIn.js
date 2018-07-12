angular.module('ioniclub')
/*我的签到*/
// lhl 0610 日历组件传入userToken
.controller('signInCtrl', function($scope, $state, $rootScope, $timeout,
    $cordovaClipboard, $cordovaEmailComposer, $cordovaGoogleAnalytics,
    $ionicHistory, $ionicLoading, $ionicPopup, $log) {
    var userToken=$("#userToken").val();
    /*日期*/
	$(function(){
		
		main.ajax({
			"_url": "/subser/gdplan/mysign",
			"_type":"post",
			"_data":{
				userToken:userToken
			},
			"_back":function(res){
				$scope.mysign = res.content;
				var mysignObject = res.content;
				var dateString = mysignObject.gdEndDate;
				var pattern = /(\d{4})(\d{2})(\d{2})/;
				var formatedDate = dateString.replace(pattern, '$1-$2-$3');
				var gdTime = new Date(formatedDate);
				var getGdyear = gdTime.getFullYear().toString();
				var getGdmonth = (gdTime.getMonth()+1)<10?"0"+(gdTime.getMonth()+1):(gdTime.getMonth()+1);
				
				var signDayList = res.content.list;
				var signDayArr = [];
				for(var i = 0;i < signDayList.length;i++){
					signDayArr[i] = {"signDay":signDayList[i].slice(-2)};
				}
				calUtil.init(signDayArr,"calendarSign",gdTime,userToken);
				
				$scope.$apply();
				
				if (mysignObject.errorCode != '') {
					$scope.myPopup = $ionicPopup.show({
					title: '提示信息',
					template: mysignObject.errorMessage,
					templateUrl: '', 
					scope: $scope,
					buttons: [{
							text: '确定',
							type: 'sale-sure',
							onTap: function (e) {
								e.preventDefault();
								$scope.myPopup.close();
							}
						}]	
					})
				}
			}
		})
	});

  })