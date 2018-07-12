var FormWizard = function () {

    return {
        //main function to initiate the module
        init: function () {
        	
            if (!jQuery().bootstrapWizard) {
                return;
            }

            function format(state) {
                if (!state.id) return state.text; // optgroup
                return "<img class='flag' src='../../assets/global/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
            }

            $("#country_list").select2({
                placeholder: "Select",
                allowClear: true,
                formatResult: format,
                width: 'auto', 
                formatSelection: format,
                escapeMarkup: function (m) {
                    return m;
                }
            });

            var form = $('#tab1');
            var error = $('.alert-danger', form);
            var success = $('.alert-success', form);
            var bookInfo = JSON.parse(window.localStorage.getItem("bookInfo")) || "";
            if(bookInfo != ""){
            	showBook();
            }

            $('#tab1').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	            	bookName:"required",
	            	bookAuthor:"required",
	            	bookTag:"required"
	            },
	            messages: {
	            	bookName:"请输入图书名称",
	            	bookAuthor:"请输入图书作者",
	            	bookTag:"请输入图书标签"
	            },
	            invalidHandler: function(event, validator) { //display error alert on form submit   
	                $('.alert-danger', $('.login-form')).show();
	            },
	            highlight: function(element) { // hightlight error inputs
	                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	            },
	            success: function(label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },
	            errorPlacement: function(error, element) {
	                //error.insertAfter(element.closest('.input-icon'));
	                error.appendTo(element.parent());
	            },
	            submitHandler: function(form) {
	            	//loginSub();
	                form.submit(); // form validation success, call ajax form submit
	            }
	        });
	        
	        $('#tab2').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {},
	            messages: {},
	            invalidHandler: function(event, validator) { //display error alert on form submit   
	                $('.alert-danger', $('.login-form')).show();
	            },
	            highlight: function(element) { // hightlight error inputs
	                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	            },
	            success: function(label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },
	            errorPlacement: function(error, element) {
	                //error.insertAfter(element.closest('.input-icon'));
	                error.appendTo(element.parent());
	            },
	            submitHandler: function(form) {
	            	//loginSub();
	                form.submit(); // form validation success, call ajax form submit
	            }
	        });

            var displayConfirm = function() {
                $('#tab4 .form-control-static', form).each(function(){
                    var input = $('[name="'+$(this).attr("data-display")+'"]', form);
                    if (input.is(":radio")) {
                        input = $('[name="'+$(this).attr("data-display")+'"]:checked', form);
                    }
                    if (input.is(":text") || input.is("textarea")) {
                        $(this).html(input.val());
                    } else if (input.is("select")) {
                        $(this).html(input.find('option:selected').text());
                    } else if (input.is(":radio") && input.is(":checked")) {
                        $(this).html(input.attr("data-title"));
                    } else if ($(this).attr("data-display") == 'payment[]') {
                        var payment = [];
                        $('[name="payment[]"]:checked', form).each(function(){ 
                            payment.push($(this).attr('data-title'));
                        });
                        $(this).html(payment.join("<br>"));
                    }
                });
            }

            var handleTitle = function(navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title', $('#form_wizard_1')).text('第 ' + (index + 1) + ' 步，共 ' + total + ' 步 ');
                // set done steps
                jQuery('li', $('#form_wizard_1')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }

                if (current == 1) {
                    $('#form_wizard_1').find('.button-previous').hide();
                } else {
                    $('#form_wizard_1').find('.button-previous').show();
                }
                
                if (current >= total) {
                    $('#form_wizard_1').find('.button-next').hide();
                    $('#form_wizard_1').find('.button-submit').show();
                    displayConfirm();
                }else if(current == 3){
                	$('#form_wizard_1').find('.button-next').show();
                	$('#form_wizard_1').find('.button-submit').show();
                } else {
                    $('#form_wizard_1').find('.button-next').show();
                    $('#form_wizard_1').find('.button-submit').hide();
                }
                App.scrollTo($('.page-title'));
            }

            // default form wizard
            $('#form_wizard_1').bootstrapWizard({
                'nextSelector': '.button-next',
                'previousSelector': '.button-previous',
                onTabClick: function (tab, navigation, index, clickedIndex) {
                    return false;
                    
                    success.hide();
                    error.hide();
                    if ($('#tab1').valid() == false) {
                        return false;
                    }
                    
                    handleTitle(navigation, clickedIndex);
                },
                onNext: function (tab, navigation, index) {
                    if ($('#tab'+index).valid() == false) {
                        return false;
                    }
                    if(index == 1){
                    	if($("#chooseImg").css("display") != "none"){
                    		$("#chooseErr").show();
                    		return false;
                    	}else{
                    		$("#chooseErr").hide();
                    	}
                    }
					formSub(index,navigation);
					return false;
                },
                onPrevious: function (tab, navigation, index) {
                    success.hide();
                    error.hide();
                    handleTitle(navigation, index);
                },
                onTabShow: function (tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    var $percent = (current / total) * 100;
                    $('#bar').find('.progress-bar').css({
                        width: $percent + '%'
                    });
                }
            });
           

            $('#form_wizard_1').find('.button-previous').hide();
            $('#form_wizard_1 .button-submit').click(function () {
            	if($("#fileTab").find("input").length==0){
    				return;
    			}else{
    				if($("#fileTab").find("input").attr("disabled")!="disabled"){
        				window.location.href="index.html#/query_newbooks";
        			}
    			}
            }).hide();

            //apply validation on select2 dropdown value change, this only needed for chosen dropdown integration.
            $('#country_list', form).change(function () {
                form.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
            });
            
            
            
            /*表单提交函数、
             * index 当前需要提交表单标志
             * navigation
             * 
             * */
            function formSub(index,navigation){
            	var data;	//表单提交数据对象
            	var _url; 	//请求路径
            	var curForm = $('#tab'+index);
            	
            	
            	
            	switch (index) {
            		case 1 :{
            			_url = "book/addOrModify";
            			var subFormDom = document.getElementById("tab1");
            			var subForm = new FormData(subFormDom);
            			main.ajax({
			        		"_url":_url,
			        		"_type":"post",
			        		"_data":subForm,
			        		"_fileFlag":false,
			        		"_back":function(res){
			        			$(".bookId").val(res.content);
			        			$('#form_wizard_1').bootstrapWizard('show',index);
			        			handleTitle(navigation, index);
			        			setTimeout(function(){
			        				$("iframe").contents().find(".cke_show_borders").html(bookInfo.bookSummary);
			        			})
			        		}
			        	})
            		}break;
            		case 2 :{
            			data = {
            				"bookId":$("#tab2").find(".bookId").val(),
			        		"bookSummary":$("iframe").contents().find(".cke_show_borders").html()
			        	}
            			_url = "book/summaryModify"
            			main.ajax({
			        		"_url":_url,
			        		"_type":"post",
			        		"_data":data,
			        		"_back":function(res){
			        			$('#form_wizard_1').bootstrapWizard('show',index);
			        			handleTitle(navigation, index);
			        		}
			        	})
            		}break;
            		case 3 :{
            			if($("#fileTab").find("input").length==0){
            				return;
            			}else{
            				if($("#fileTab").find("input").attr("disabled")!="disabled"){
	            				$('#form_wizard_1').bootstrapWizard('show',index);
	            				handleTitle(navigation, index);
	            			}
            				return;
            			}
            		}
            	}
            	
            }
            
            
            
            /*
             *当此页面为点击修改跳转时执行 
             * */
            function showBook(){
            	main.ajax({
	        		"_url":"book/"+bookInfo.bookId,
	        		"_type":"post",
	        		"_data":"",
	        		"_back":function(res){
	        			$(".bookId").val(res.content.bookId).attr("amend","true");
	        			$("#bookNm").val(res.content.bookName);
	        			$("#bookAu").val(res.content.bookAuthor);
	        			$("#bookTag").val(res.content.bookTag);
	        			var imgStr = '<img src="'+res.content.bookSltPath+'"/>';
	        			$(".thumbnail").html(imgStr);
	        			$("[data-provides='fileinput']").addClass("fileinput-exists").removeClass("fileinput-new")
	        			$("#imgFile").attr('aria-invalid','false').addClass("valid").before('<input type="hidden" value name>');
	        		}
	        	})
            }
            
        }

    };

}();

jQuery(document).ready(function() {
    FormWizard.init();
});