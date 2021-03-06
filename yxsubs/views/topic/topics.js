﻿angular.module('ioniclub')
.controller('TopicsCtrl', function($scope, $rootScope, $log, $timeout,
    $ionicTabsDelegate, $ionicPopover, $ionicModal, $ionicLoading,
    $location, $state,$ionicPopup,
    $cordovaNetwork, $cordovaGoogleAnalytics,
    Topics, Tabs, My, User, ENV) {
	
    // get current user
    var currentUser = User.getCurrentUser();
    var userToken=$("#userToken").val();
   
    /*倒计时计算*/
	function countDown(end_time,day_elem,hour_elem,minute_elem,second_elem,current_time){
		//if(typeof end_time == "string")

//		var end_time = new Date(time).getTime(),//月份是实际月份-1   lhl windows

//		var end_time = new Date(time).getTime(),//月份是实际月份-1   lhl mac提交   zcc提交

		//current_time = new Date().getTime(),
		sys_second = (end_time-current_time)/1000;
		var timer = setInterval(function(){
			if (sys_second > 0) {     
				sys_second -= 1;
				var day = Math.floor((sys_second / 3600) / 24);
				var hour = Math.floor((sys_second / 3600) % 24);
				var minute = Math.floor((sys_second / 60) % 60);
				var second = Math.floor(sys_second % 60);
				day_elem && $(day_elem).text(day);//计算天
				$(hour_elem).text(hour<10?"0"+hour:hour);//计算小时
				$(minute_elem).text(minute<10?"0"+minute:minute);//计算分
				$(second_elem).text(second<10?"0"+second:second);// 计算秒
			} else { 
				clearInterval(timer);
			}
		}, 1000);
	}
	/*本期共读*/
	main.ajax({
		"_url":"/subser/gdplan/now",
		"_type":"post",
		"_data":{'userToken': userToken},
		"_back":function(res){
			var resData = res.content;
			var gdTitle = resData.gdTitle;
			var gdSlogan = resData.gdSlogan;
			
			// 如果页面是通过微信分享页面进入，则需要走一次微信授权认证。
			if (resData.shareEntryFlag == "1") {
				$scope.shareEnter();
			}
			else {
			
				resData.gdBeginDate = resData.gdBeginDate.replace(/-/g,'/');
				resData.gdEndDate = resData.gdEndDate.replace(/-/g,'/');
				$scope.now = resData;
				$scope.$apply();

				//1.结束时间-当前时间
				//2.转换为天时分秒
				if(resData.nowFlag == 0){
					var end_time = new Date(resData.signupEndDate.replace("-","/").replace("-","/") +" 23:59:59").getTime();
					var current_time = new Date(res.nowTime.replace("-","/").replace("-","/")).getTime();
					countDown(end_time,"#demo01 .day","#demo01 .hour","#demo01 .minute","#demo01 .second",current_time);
					$("#gdapply").attr("disabled",false);
					$("#down_").hide();
					if(resData.applyFlag == "0"){
						$("#confirm").show();
						$("#cancel").hide();
					}else{
						
						$("#confirm").hide();
						$("#cancel").show();
					}
				}else if(resData.nowFlag == 1){
					$("#gdapply").attr("disabled",true);
					$("#down_").text('未开始').show();
					$("#confirm").hide();
					$("#cancel").hide();
				}else{
					$("#gdapply").attr("disabled",true);
					$("#confirm").hide();
					$("#cancel").hide();
					$("#down_").text('已结束').show();
				}
				$scope.$apply();
				$scope.shareMenu(gdTitle, gdSlogan);
			}
		}
    })
	
	/* 微信授权重定向页面 */
	$scope.shareEnter = function() {
		
		$("#gdPlanIonView").empty();
		$("#gdPlanIonView").text("loading.....");
		window.location.assign("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb89606f58305f38d&redirect_uri=http://yxgd.yusys.com.cn/subser/wxcontent&response_type=code&scope=snsapi_userinfo&state=STAT#wechat_redirect")
	}
	
	/*微信分享页面*/
	$scope.shareMenu = function(gdTitle, gdSlogan) {
		
		// 微信分享必须是当前在微信注册的域名下的页面
		var url = window.location.href; 
	    
	    var shareImgUrl=""; 
	    var timestamp; 
	    var noncestr; 
	    var signature; 
	    var appId;
	
		$.ajax({
			    type: "GET",  
			    url: "subser/WebChatShareController/getSignature",  
			    data:{url:url},  
			    success: function(data){
			        console.log("success");  
			        var objData=JSON.parse(data);  
			        timestamp=objData.timestamp;  
			        noncestr=objData.noncestr;  
			        signature=objData.signature;  
			        url=objData.url;
			        appId=objData.appId;
			        
			        console.log(objData);
			        
			        wx.config({  
			        	debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。  
			        	appId: appId, // 和获取Ticke的必须一样------必填，公众号的唯一标识  
			        	timestamp:timestamp, // 必填，生成签名的时间戳  
			        	nonceStr: noncestr, // 必填，生成签名的随机串  
			        	signature: signature,// 必填，签名，见附录1  
			       		jsApiList: [  
			            	'onMenuShareTimeline','onMenuShareAppMessage','hideMenuItems' 
			        	] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2  
			    	});
				 	wx.ready(function(){
				  			
				  		wx.hideMenuItems({
	                    	menuList: ['menuItem:share:qq',
	                               'menuItem:share:weiboApp',
	                               'menuItem:favorite',
	                               'menuItem:share:facebook',
	                               '/menuItem:share:QZone'], // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
	                    success:function(res){
	                        // alert("隐藏");
	                    }
	                });
				  	
					// 获取“分享给朋友”按钮点击状态及自定义分享内容接口
			        wx.onMenuShareAppMessage({
			              title: gdTitle,
			              desc: gdSlogan,
			              link: url,
			              imgUrl: 'http://yxgd.yusys.com.cn/img/login_logo.png',
			              type: 'link',
			              dataUrl: '',
			              success: function () {
			                  alert("分享给朋友成功");
			              },
			              cancel: function () {   
				            // 用户取消分享后执行的回调函数  
				            alert("分享给朋友失败");  
					      }
					 });
					 
				     //------------"分享到朋友圈"  
				     wx.onMenuShareTimeline({  
				        title: gdTitle,
			            desc: gdSlogan, 
				        link: url, // 分享链接  
				       	imgUrl: 'http://yxgd.yusys.com.cn/img/login_logo.png', // 分享图标  
				        type: '', // 分享类型,music、video或link，不填默认为link  
				        dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空  
				        success: function () {  
				            alert("分享朋友圈成功");  
				        },  
				        cancel: function () {  
				           alert("分享朋友圈失败");
				        }  
				     }); 
				     wx.error(function(res){
			              // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
			              alert("微信分享组件存在异常:"+res);
				     });
	  			  });
			    }, 
			    statusCode: {404: function(){  
			        alert('page not found');  
			    }}  
			});  
		
		}
		
		/*立即报名*/
		$scope.apply = function(gdId,applyFlag,userToken) {
			
			var confirmMsg = "确认提交吗?";
			
			if (applyFlag == 0) {
				confirmMsg = "确认报名本期共读吗？";
			}
			else {
				confirmMsg = "确认取消本期共读报名吗？"
			}
			
	     var confirmPopup = $ionicPopup.confirm({
	       title: '操作提示',
	       template: confirmMsg
	     });
	     confirmPopup.then(function(res) {
	       if(res) {
	         console.log('You are sure');
	         
	         main.ajax({
						"_url": "/subser/gdplan/apply",
						"_type":"post",
						"_data":{
							gdId:gdId,
							operateType:applyFlag==1?0:1,
							userToken:userToken
						},
						"_back":function(res){
							
							var resData = res.content;
							var applyResult = resData.applyFlag;
							var applyNum = resData.applyNum;
							
							if(applyFlag=="0" && res.retCode == "AAAAAAA"){
								$("#confirm").hide();
								$("#cancel").show();
								$scope.now.applyFlag="1";
							}else if(applyFlag=="1" && res.retCode == "AAAAAAA"){
								$("#confirm").show();
								$("#cancel").hide();
								$scope.now.applyFlag="0";
							}
							$scope.now.applyNum=applyNum;
							$scope.$apply();
							
							$scope.myPopup = $ionicPopup.show({
								title: '提示信息',
								template: applyResult,
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
					})
	         
	       } else {
	         console.log('You are not sure');
	       }
	     });
	   };
		
    $scope.loginName = currentUser.loginname || null;

    $scope.$on('$ionicView.beforeEnter', function() {
      // get user settings
      $scope.settings = My.getSettings();
      $rootScope.hideTabs = '';
    });

    $scope.$on('$ionicView.afterEnter', function() {

      document.addEventListener("deviceready", function() {
        // trackView
        $cordovaGoogleAnalytics.trackView('topics view');
      }, false);

      $timeout(function() {
        $scope.topics = Topics.getTopics();
      }, 100);
    });

    // $scope.title = "全部话题";
    // assign tabs
    $scope.tabs = Tabs;
    $scope.currentTab = Topics.getCurrentTab();

    // Topics.fetchTopStories();

    $scope.$on('ioniclub.topicsUpdated', function() {
      // $timeout(function() {
      $scope.topics = Topics.getTopics();
      $scope.$broadcast('scroll.refreshComplete');
      // }, 100);
    });

    // logout
    $rootScope.$on('ioniclub.logout', function() {
      $log.debug('logout broadcast handle');
      $scope.loginName = null;
      $scope.messagesCount = 0;
      // setBadge(0);
    });

    $scope.doRefresh = function() {
      // Topics.fetchTopStories();
    };


    $scope.loadMore = function() {
      // console.log("loadMore");
      Topics.increaseNewTopicsCount(15);
      $scope.$broadcast('scroll.infiniteScrollComplete');
    };

    $scope.moreDataCanBeLoaded = function() {
      // console.log(Topics.hasNextPage());
      return Topics.hasNextPage();
    };

    $scope.newTopicData = {
      tab: 'share',
      title: '',
      content: ''
    };
    $scope.newTopicId = null;

    // save new topic
    $scope.saveNewTopic = function() {
      $log.debug('new topic data:', $scope.newTopicData);
      $ionicLoading.show();
      Topics.saveNewTopic($scope.newTopicData).$promise.then(function(response) {
        $ionicLoading.hide();
        $scope.newTopicId = response.topic_id;
        $scope.closeNewTopicModal();
        $timeout(function() {
          $state.go('tab.topic-detail', {
            id: $scope.newTopicId
          });
          $timeout(function() {
            $scope.doRefresh();
          }, 300);
        }, 300);
      }, $rootScope.requestErrorHandler);
    };
    $scope.$on('modal.hidden', function() {
    	
      // Execute action
      if ($scope.newTopicId) {
        $timeout(function() {
          $location.path('#/tab/topics/' + $scope.newTopicId);
        }, 300);
      }
    });
    // show new topic modal
    $scope.showNewTopicModal = function() {

      // track view
      if (window.analytics) {
        window.analytics.trackView('new topic view');
      }

      if (window.StatusBar) {
        StatusBar.styleDefault();
      }
      $scope.newTopicModal.show();
    };

    // close new topic modal
    $scope.closeNewTopicModal = function() {
      if (window.StatusBar) {
        StatusBar.styleLightContent();
      }
      $scope.newTopicModal.hide();
    };
  })
  .controller('TopicCtrl', function($scope, $rootScope, $state, $timeout, $log,
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

.factory('Topics', function($resource, $rootScope, Storage, User, ENV) {
    var APIUrl = ENV.api + '/topics',
	  // 用来存储话题类别的数据结构，包含了下一页、是否有下一页等属性
	  topics = {},
	  currentTab = "all";

    var resource = $resource(APIUrl, {}, {
      query: {
        method: 'get',
        header: {'Access-Control-Allow-Origin': "*"},
        params: {
          tab: '@tab',
          page: 1,
          limit: 20,
          mdrender: true
        },
        timeout: 200
      }
    });

    return {
      fetchTopStories: function() {
        // console.log("currentTab: " + currentTab);
        var hasNextPage = true;
        
        resource.query({
          tab: currentTab
        }, function(r) {
        
          console.log(r);
          if (r.data.length < 20) {
            hasNextPage = false;
          }
          topics[currentTab] = {
            'nextPage': 2,
            'hasNextPage': hasNextPage,
            'data': r.data
          };
          // topics[currentTab] = r.data;
//        $rootScope.$broadcast('ioniclub.topicsUpdated', topics[currentTab].data);
          // console.table(topics);

        });

      },
      getTopics: function() {
       // return topics[currentTab].data;
      },
      setCurrentTab: function(tab) {
        currentTab = tab;
        // this.fetchTopStories();
        // $rootScope.$broadcast('ioniclub.topicsUpdated', topics[currentTab]);
      },
      getCurrentTab: function() {
        return currentTab;
      }
      //,
//    increaseNewTopicsCount: function() {
//      var nextPage = topics[currentTab].nextPage;
//      
//      var hasNextPage = topics[currentTab].hasNextPage;
//      var topicsData = topics[currentTab].data;
//      resource.query({
//        tab: currentTab,
//        page: nextPage,
//        limit: 20,
//        mdrender: true
//
//      }, function(r) {
//        // console.log(r);
//        nextPage++;
//        if (r.data.length < 20) {
//          hasNextPage = false;
//        }
//        topicsData = topicsData.concat(r.data);
//        topics[currentTab] = {
//          'nextPage': nextPage,
//          'hasNextPage': hasNextPage,
//          'data': topicsData
//        };
//        // topics[currentTab] = r.data;
//        $rootScope.$broadcast('ioniclub.topicsUpdated', topics[currentTab]);
//        // console.table(topics);
//
//      });
//    },
//    hasNextPage: function() {
//      if (topics[currentTab] === undefined) {
//        return false;
//      }
//      return topics[currentTab].hasNextPage;
//    },
//    saveNewTopic: function(newTopicData) {
//      var currentUser = User.getCurrentUser();
//      return resource.save({
//        accesstoken: currentUser.accesstoken
//      }, newTopicData);
//    }

    };
})
.factory('Topic', function($resource, $rootScope, $q, Storage, User, My, ENV) {
    var APIUrl = ENV.api + '/topic/:id',
      topic,
      currentTab = "all";
     
    var resource = $resource(ENV.api + '/topic/:id', {
      id: '@id',
    }, {
      reply: {
        method: 'post',
        url: ENV.api + '/topic/:topicId/replies'
      },
      upReply: {
        method: 'post',
        url: ENV.api + '/reply/:replyId/ups'
      },
      collect: {
        method: 'post',
        url: ENV.api + '/topic/collect'
      },
      de_collect: {
        method: 'post',
        url: ENV.api + '/topic/de_collect'
      }
    });
    return {
      getById: function(id) {
        // console.log("id:" + id + "   topic:" + topic);
        if (topic !== undefined && topic.id === id) {
          var topicDefer = $q.defer();
          topicDefer.resolve({
            data: topic
          });
          return {
            $promise: topicDefer.promise
          };
        }
        return this.get(id);
      },
      get: function(id) {
        return resource.get({
          id: id
        }, function(response) {
          topic = response.data;
        });

      },
//    saveReply: function(topicId, replyData) {
//      var reply = angular.extend({}, replyData);
//      var currentUser = User.getCurrentUser();
//      // add send from
//      if (My.getSettings().sendFrom) {
//        reply.content = replyData.content + '\n<br/> 发自 [Ioniclub](https://github.com/IonicChina/ioniclub)';
//      }
//      return resource.reply({
//        topicId: topicId,
//        accesstoken: currentUser.accesstoken
//      }, reply);
//    },
//    upReply: function(replyId) {
//      var currentUser = User.getCurrentUser();
//      return resource.upReply({
//        replyId: replyId,
//        accesstoken: currentUser.accesstoken
//      }, null, function(response) {
//        if (response.success) {
//          angular.forEach(topic.replies, function(reply, key) {
//            if (reply.id === replyId) {
//              if (response.action === 'up') {
//                reply.ups.push(currentUser.id);
//              } else {
//                reply.ups.pop();
//              }
//            }
//          });
//        }
//      });
//    },
      collect: function(topicId) {
        var currentUser = User.getCurrentUser();
        return resource.collect({
          topic_id: topicId,
          accesstoken: currentUser.accesstoken
        });
      },
      de_collect: function(topicId) {
        var currentUser = User.getCurrentUser();
        return resource.de_collect({
          topic_id: topicId,
          accesstoken: currentUser.accesstoken
        });
      }
    };

}

);
