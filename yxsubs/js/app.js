// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'ioniclub' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'ioniclub.controllers' is found in controllers.js
angular.module('ioniclub', ['ionic', 'ngCordova', 'ngResource',
  'angularMoment',
  'ioniclub.config', 'ioniclub.services', 'ioniclub.controllers'
])

//.run(function($ionicPlatform, $rootScope, $state, $ionicLoading, $log,
//$cordovaAppVersion, $cordovaGoogleAnalytics,
//amMoment, My, User, Push) {
//
////// push notification callback
////var notificationCallback = function(data, isActive) {
////  $log.debug(data);
////  var notif = angular.fromJson(data);
////  if (notif.extras) {
////    // android
////    if (notif.extras['cn.jpush.android.EXTRA']['topicId']) {
////      $state.go('tab.my-messages-topic', {
////        id: notif.extras['cn.jpush.android.EXTRA']['topicId']
////      });
////    } else {
////      $state.go('tab.my-messages');
////    }
////  } else {
////    // ios
////    if (notif.topicId) {
////      if (isActive) {
////        // $rootScope.getMessageCount();
////      } else {
////        $state.go('tab.my-messages-topic', {
////          id: notif.topicId
////        });
////      }
////    } else {
////      $state.go('tab.my-messages');
////    }
////  }
////};
////// push notification callback
////var notificationCallback2 = function(data, isActive) {
////  // $log.debug(data);
////  var notif = angular.fromJson(data);
////  // {"aps":{"badge":8,"sound":"default","alert":"HeyIonic 回复了你的主题"},"_j_msgid":1590987161,"topicId":"555d5175aaa7025033c2894d"}
////  if (notif.topicId) {
////    if (isActive) {
////      // $rootScope.getMessageCount();
////    } else {
////      $state.go('tab.topic-detail', {
////        id: notif.topicId
////      });
////    }
////  } else {
////    $state.go('tab.my-messages');
////  }
////
////
////};
////$ionicPlatform.ready(function() {
////  // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
////  // for form inputs)
////  if (window.cordova && window.cordova.plugins.Keyboard) {
////    cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
////    cordova.plugins.Keyboard.disableScroll(true);
////  }
////  if (window.StatusBar) {
////    // org.apache.cordova.statusbar required
////    StatusBar.styleDefault();
////  }
////
////
////
////  // set moment locale
////  amMoment.changeLocale('zh-cn');
////
////  // push handler
////  Push.init(notificationCallback);
////
////  // detect current user have not set alias of jpush
////  var currentUser = User.getCurrentUser();
////  if (currentUser.id) {
////    Push.setAlias(currentUser.id);
////  }
////
////});
//
////// error handler
////var errorMsg = {
////  0: '网络出错啦，请再试一下',
////  'wrong accessToken': '授权失败'
////};
//
////$rootScope.requestErrorHandler = function(options, callback) {
////  return function(response) {
////    var error;
////    if (response.data && response.data.error_msg) {
////      error = errorMsg[response.data.error_msg];
////    } else {
////      error = errorMsg[response.status] || 'Error: ' + response.status + ' ' + response.statusText;
////    }
////    var o = options || {};
////    angular.extend(o, {
////      template: error,
////      duration: 1000
////    });
////    $ionicLoading.show(o);
////    return callback && callback();
////  };
////};
//
////var currentUser = User.getCurrentUser();
////document.addEventListener("deviceready", function() {
////
////  // get the version
////  $cordovaAppVersion.getAppVersion().then(function(version) {
////    My.setSettings('version', version);
////  });
////
////  // turn on debug mode
////  // https://github.com/danwilson/google-analytics-plugin#javascript-usage
////  // $cordovaGoogleAnalytics.debugMode();
////
////  // start tracker
////  // https://developers.google.com/analytics/devguides/collection/analyticsjs/
////  $cordovaGoogleAnalytics.startTrackerWithId('UA-59800656-3');
////
////  if (currentUser) {
////    // set user id
////    // https://developers.google.com/analytics/devguides/collection/analyticsjs/user-id
////    $cordovaGoogleAnalytics.setUserId(currentUser.id);
////  }
////
////  // track a view
////  // https://developers.google.com/analytics/devguides/collection/analyticsjs/screens
////  // Hint: Currently no support for appName, appId, appVersion, appInstallerId
////  //       If you need support for it, please create an issue on github:
////  //       https://github.com/driftyco/ng-cordova/issues
////
////  $cordovaGoogleAnalytics.trackView('Home Screen');
////
////
////}, false);
//
//// 只有在激活时候才会被调用
////document.addEventListener("jpush.receiveNotification", function(data, isActive) {
////
////
////  notificationCallback2(data, true);
////
////
////
////}, false);
//
//// 打开通知栏时候调用
////document.addEventListener("jpush.openNotification", function(data) {
////
////
////  notificationCallback2(data, false);
////
////
////}, false);
//
//
//
//})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

    .state('tab', {
	    url: "/tab",
	    cache:'false',
	    abstract: true,
	    templateUrl: "views/tabs.html"
	  })
    
		/*当期共读*/
  .state('tab.topics', {
      url: '/topics',
      cache:'false',
      views: {
        'tab-topics': {
          templateUrl: 'views/topic/topics.html',
          controller: 'TopicsCtrl'
          
        }
      }
    })
  /*当期共读内容书详情*/
    .state('tab.topic-detail', {
      url: '/topics/:id',
      cache:'false',
      views: {
        'tab-topics': {
          templateUrl: 'views/topic/topic.html',
          controller: 'TopicCtrl'
        }
      }
    })
    
    /*共读计划*/
    .state('tab.explore', {
      url: '/explore',
      cache:'false',
      views: {
        'tab-explore': {
          templateUrl: 'views/explore/explore.html',
          controller: 'ExploreCtrl'
        }
      }
    })
    
    /*我的共读*/
    .state('tab.my', {
      url: '/my',
      cache:'false',
      views: {
        'tab-my': {
          templateUrl: 'views/my/my.html',
          controller: 'MyCtrl'
        }
      }
    })
    
    /*共读计划图书详情*/
    .state('tab.explorebook-detail', {
      url: '/explore/:id',
      cache:'false',
      views: {
        'tab-explore': {
          templateUrl: 'views/explore/explorebookdetail.html',
          controller: 'ExploreBookDetailCtrl'
        }
      }
    })
    
    /*我的签到*/
    .state('tab.signIn', {
      url: '/signIn',
      cache:'false',
      views: {
        'tab-signIn': {
          templateUrl: 'views/signIn/signIn.html',
          controller: 'signInCtrl'
        }
      }
    });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/tab/topics');
});