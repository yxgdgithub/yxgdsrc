angular.module('MetronicApp').controller('maintenance', ['$rootScope', '$scope', 'settings','$http', function($rootScope, $scope, settings,$http) {
    var bookImg = [];
    /*期*/
   	$scope.year = function(x){
   		deleteData();
   		var year = $("#year").val();
   		$.ajax({
   			url: "gdplan/history/"+year,
   			success:function(res){
   				$scope.periodsNumber = res.content;
   			}
   		})
   	}
   	/*查询图书*/
   	$scope.queryBook = function(gdId){
   		$scope.gdId = gdId;
   		var year = $("#year").val();
   		$.ajax({
   			url: "/gdplan/getGdPlan/"+gdId,
   			success:function(res){
   				$("#selectBook").removeClass("hide").show();
   				if(res.content.gdBook.length){
   					$("#footer").css({"height":"300px"})
   				}
   				$("#registerSignIn").show();
   				$scope.formModels = {
   					gdTitle:res.content.gdTitle,
   					signupBeginDate:res.content.signupBeginDate,
   					signupEndDate:res.content.signupEndDate,
   					gdBeginDate:res.content.gdBeginDate,
   					gdEndDate:res.content.gdEndDate,
   					sponsorUser:res.content.sponsorUser,
   					gdSlogan:res.content.gdSlogan,
   					gdInstr:res.content.gdInstr,
   					gdId:res.content.gdId,
   					signNum:res.content.signNum,
   					applyNum:res.content.applyNum,
   				};
   				bookImg = res.content.gdBook;
   				$scope.queryBookImg = bookImg;
   				loadedBookImg();
   				$("#delete").show();
   				$("#js-grid-juicy-projects").show();
   			}
   		})
   	}
   	/*保存*/
    $rootScope.formSubParamObj = {
		_url:"gdplan/addOrModify",
		_before:formBefore,
		_back:formBack,
		_scope:$scope,
		_type:"post",
		_data:{
			
		}
	}
    /*删除图书*/
   	$scope.removeBook = function(){
   		var gdId = $scope.gdId;
   		if(gdId){
   			$.ajax({
	   			url: "/gdplan/remove/"+gdId,
	   			success:function(res){
	   				location.reload();
	   			}
	   		})
   		}
   		
   	}
   	/*删除图书图片*/
   	
   	$scope.removeImg = function(bookId){
   		var gdId = $scope.gdId;
		$.ajax({
   			url: "/gdplan/delBookRel",
   			data:{
   				"gdId":gdId,
   				"bookId":bookId
   			},
   			success:function(res){
   				$("#"+bookId+"").parents(".cbp-item.graphic").hide();
   			}
   		})
   		
   	}
   	/*选择图书*/
   	$scope.selectBook= function(){
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
    				$scope.$apply()
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
    						$scope.bookList = data.content;
    						$scope.$apply()
    					}
    				})
    			}
    		}
    	})
   	}
   	/*添加图书*/
   	$("#return").on("click",function(){
   		var bookList = [];
   		var bookId = "";
   		$("#responsive tbody").find('input[name="batch"]').each(function(i) {
			if($(this).is(":checked")){
				if(bookImg.length + bookList.length  <  3){
					bookList.push($scope.bookList[i]);
					bookId+=$scope.bookList[i].bookId+",";
				}else{
					alert("最多只能添加三本书");
					bookList = [];
					return false
				}
			};
		});
		if(bookList){
			$.ajax({
	   			url: "/gdplan/bookRel",
	   			data:{
	   				"gdId":$scope.gdId,
	   				"books":bookId.substring(0,bookId.length-1)
	   			},
	   			success:function(res){
	   				var list = bookImg.concat(bookList);
					$scope.queryBookImg = list;
					$scope.$apply();
	   			}
	   		})
		}
		
   	})
   	
   	/**加载图书图片******************************************/
   	function loadedBookImg(){
   		$scope.$on('$viewContentLoaded', function() {
	        $('#js-grid-juicy-projects').cubeportfolio({
		        filters: '#js-filters-juicy-projects',
		        loadMore: '#js-loadMore-juicy-projects',
		        loadMoreAction: 'click',
		        layoutMode: 'grid',
		        defaultFilter: '*',
		        animationType: 'quicksand',
		        gapHorizontal: 35,
		        gapVertical: 30,
		        gridAdjustment: 'responsive',
		        mediaQueries: [{
		            width: 1500,
		            cols: 5
		        }, {
		            width: 1100,
		            cols: 4
		        }, {
		            width: 800,
		            cols: 3
		        }, {
		            width: 480,
		            cols: 2
		        }, {
		            width: 320,
		            cols: 1
		        }],
		        caption: 'overlayBottomReveal',
		        displayType: 'sequentially',
		        displayTypeSpeed: 80,
		        // lightbox
		        lightboxDelegate: '.cbp-lightbox',
		        lightboxGallery: true,
		        lightboxTitleSrc: 'data-title',
		        lightboxCounter: '<div class="cbp-popup-lightbox-counter">{{current}} of {{total}}</div>',
		        // singlePage popup
		        singlePageDelegate: '.cbp-singlePage',
		        singlePageDeeplinking: true,
		        singlePageStickyNavigation: true,
		        singlePageCounter: '<div class="cbp-popup-singlePage-counter">{{current}} of {{total}}</div>',
		        singlePageCallback: function(url, element) {
		            // to update singlePage content use the following method: this.updateSinglePage(yourContent)
		            var t = this;
		            $.ajax({
	                    url: url,
	                    type: 'GET',
	                    dataType: 'html',
	                    timeout: 10000
	                })
	                .done(function(result) {
	                    t.updateSinglePage(result);
	                })
	                .fail(function() {
	                    t.updateSinglePage('AJAX Error! Please refresh the page!');
	                });
		        },
		    });
	    	$("document").find('img').style({'width':'246px'})
   		});
   	}
    function formBefore(){
    	var gdBeginDate = $("#gdBeginDate").val().replace("/-/","");
    	var signupEndDate = $("#signupEndDate").val().replace("/-/","");
    	if(gdBeginDate<=signupEndDate){
    		alert("共读起始日期必须大于报名结束日期");
    		/*App.alert({
		        container: "", // alerts parent container(by default placed after the page breadcrumbs)
		        place: "append", // append or prepent in container 
		        type: "danger",  // alert's type
		        message:  "共读起始日期必须大于报名结束日期",  // alert's message
		        close: true, // make alert closable
		        reset: true, // close all previouse alerts first
		        focus: true, // auto scroll to the alert after shown
		        closeInSeconds: 0, // auto close after defined seconds
		        icon: "" // put icon before the message   
		    });*/
    		return false;
    	}
    	return true;
    }
    function formBack(data){
    	console.log(data);
    	$("#selectBook").removeClass("hide").show();
    }
    
    function deleteData(){
    	$(".form-body").find("input").val("");
    	$(".form-body").find("textarea").val("");
    	$("#registerSignIn").hide();
    	$("#delete").hide();
    	$("#js-grid-juicy-projects").hide();
    	$("#selectBook").addClass("hide").hide();
    }
 
    /*点击新增*/
    $("#addBook").on("click",function(){
    	deleteData();
    })
    $(document).on("click",function(){
    	$(".confirmation ").hide();
    })
}]);











