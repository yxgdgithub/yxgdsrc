/* Setup general page controller */
angular.module('MetronicApp').controller('general_page', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, settings) {
    $scope.$on('$viewContentLoaded', function() {
 
    	// initialize core components
    	App.initAjax();
        //$('.progress').find('div').css('width','0');
    	// set default layout mode
    	$rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;
        
       	$scope.delBook = function(list){
       		var data = {
       			"bookId":list.bookId
       		}
       		main.ajax({
        		"_url":"/book/remove/"+list.bookId,
        		"_type":"post",
        		"_data":data,
        		"_back":function(res){
        			if(res){
        				$scope.queryBook()
        			}
        		}
	       	})
       	}
       	
       	
       	$scope.amendBook = function(list){
       		window.localStorage.setItem("bookInfo",JSON.stringify(list));
       		window.location.href="index.html#/add_newbooks.html";
       	}
       	
       	
       	
       	function scopeBind(){
       		/*定义图书表单值*/
       		$scope.bookData = {
       			"bookName":"",
       			"bookAuthor":"",
       			"page":"1",
       			"rows":"10"
       		};
       		/*图书数据list*/
       		$scope.bookList = {};
       		/*点击查询按钮*/
       		$scope.queryBook = function(){
            	var subFormDom = document.getElementById("bookForm");
            	var subForm = new FormData(subFormDom);
            	
	       		main.ajax({
        		"_url":"/book",
        		"_type":"post",
        		"_data":subForm,
        		"_fileFlag":false,
        		"_back":function(res){
	        			if(res){
	        				$scope.bookList = res.content;
	        				$("#page").createPage({
	        					"ajaxPar":{
	        						"_url":"/book",
					        		"_type":"post"
	        					},
	        					"formId":"bookForm",
	        					"totalNum":res.count,
	        					"pageNm":$(".rows").val()||"",
	        					"current":1,
	        					"backFn":function(data){
	        						console.log(JSON.stringify(data.content))
	        						$scope.bookList = data.content;
	        						$scope.$apply()
	        					}
	        				})
	        				$scope.$apply()
	        			}
	        		}
	        	})
	       	}
       		$scope.queryBook();
       	}
       	
       	
       	(function pageInit(){
       		scopeBind()
       	})()
    });
}]);
