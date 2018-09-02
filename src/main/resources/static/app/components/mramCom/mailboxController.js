angular
    .module('altairApp')
    .controller('mailboxCtrl', [
        '$rootScope',
        '$scope',
        '$timeout',
        'messages',
        'variables',
        'mainService',
        'sweet',
        'users',
        function ($rootScope,$scope,$timeout,messages,variables,mainService,sweet,users) {

        	
            $rootScope.toBarActive = true;
            $scope.messages =[];
            $scope.messages = messages;

            var $mailbox = $('#mailbox');

            // select message
            $mailbox
                .on('ifChanged', '.select_message', function() {
                    $(this).is(':checked') ? $(this).closest('li').addClass('md-card-list-item-selected') : $(this).closest('li').removeClass('md-card-list-item-selected');
            });

            // select all messages
            $('#mailbox_select_all').on('ifChanged',function() {
                var $this = $(this);
                $mailbox.find('.select_message').each(function() {
                    $this.is(':checked') ? $(this).iCheck('check') : $(this).iCheck('uncheck');
                })
            });

            // show message details
            $mailbox.on('click', '.md-card-list ul > li', function(e) {

                if ( !$(e.target).closest('.md-card-list-item-menu').length && !$(e.target).closest('.md-card-list-item-select').length ) {

                    var $this = $(this);

                    if (!$this.hasClass('item-shown')) {
                        // get height of clicked message
                        var el_min_height = $this.height() + $this.children('.md-card-list-item-content-wrapper').actual("height");

                        // hide opened message
                        $mailbox.find('.item-shown').velocity("reverse", {
                            begin: function (elements) {
                                $(elements).removeClass('item-shown').children('.md-card-list-item-content-wrapper').hide().velocity("reverse");
                            }
                        });

                        // show message
                        $this.velocity({
                            marginTop: 40,
                            marginBottom: 40,
                            marginLeft: -20,
                            marginRight: -20,
                            minHeight: el_min_height
                        }, {
                            duration: 300,
                            easing: variables.easing_swiftOut,
                            begin: function (elements) {
                                $(elements).addClass('item-shown');
                            },
                            complete: function (elements) {
                                // show: message content, reply form
                                $(elements).children('.md-card-list-item-content-wrapper').show().velocity({
                                    opacity: 1
                                });

                                // scroll to message
                                var container = $('body'),
                                    scrollTo = $(elements);
                                container.animate({
                                    scrollTop: scrollTo.offset().top - $('#page_content').offset().top - 8
                                }, 1000, variables.bez_easing_swiftOut);

                            }
                        });
                    }
                }

            });
            
            $scope.verify=function(item){
            	if(item.verified==false && $rootScope.user.id!=item.userid ){
            		 item.verified=true;
            		 item.senderColor="green";
	               	 mainService.withdata('put','/user/service/entity/DataTezuMail/'+item.id,item)
	   		   			.then(function(data){
	   		   				if(data){
	   		   					
	   		   				}		   			
	   	   			});
            	}            	 
            }
            
            // hide message on: outside click, esc button
            $(document).on('click keydown', function(e) {
                if (
                    ( !$(e.target).closest('li.item-shown').length ) || e.which == 27
                ) {
                    $mailbox.find('.item-shown').velocity("reverse", {
                        begin: function(elements) {
                            $(elements).removeClass('item-shown').children('.md-card-list-item-content-wrapper').hide().velocity("reverse");
                        }
                    });
                }
            });
            
            var $formValidate = $('#form_validation');

            $formValidate
                .parsley()
                .on('form:validated',function() {
                    $scope.$apply();
                })
                .on('field:validated',function(parsleyField) {
                    if($(parsleyField.$element).hasClass('md-input')) {
                        $scope.$apply();
                    }
                });
            $scope.users_options = [];
            $scope.users_options =users;
            $scope.users_config = {
                maxItems: 1,
                valueField: 'id',
                labelField: 'name',
                create: false,
                placeholder: 'Мэргэжилтэн...'
            };
            
            $scope.message_compose={
          		  licNum:0,
          		  title:'',
          		  recipient:'',
          		  message:'',
        		  tezu:0,
          		  attachments:[]
            }
            
           

            // file upload (new message)
            $timeout(function() {
                var progressbar = $("#mail_progressbar"),
                    bar         = progressbar.find('.uk-progress-bar'),
                    settings    = {
                	    action: '/file/upload/', // upload url
                	    params:{"data": ''},
                        single: false,
                        loadstart: function() {
                            bar.css("width", "0%").text("0%");
                            progressbar.removeClass("uk-hidden uk-progress-danger");
                        },
                        progress: function(percent) {
                            percent = Math.ceil(percent);
                            bar.css("width", percent+"%").text(percent+"%");
                            if(percent == '100') {
                                setTimeout(function(){
                                    progressbar.addClass("uk-hidden");
                                }, 1500);
                            }
                        },
                        error: function(event) {
                            progressbar.addClass("uk-progress-danger");
                            bar.css({'width':'100%'}).text('100%');
                        },
                        abort: function(event) {
                            console.log(event);
                        },
                        complete: function(response, xhr) {
                        	$scope.message_compose.attachments=response;
                            console.log(response);
                        }
                    };

                var select = UIkit.uploadSelect($("#mail_upload-select"), settings),
                    drop   = UIkit.uploadDrop($("#mail_upload-drop"), settings);
            })
            
            var modal = UIkit.modal("#modal_main_tezu");
            $scope.validator = function(){
        		mainService.withdata('put','/logic/tezu', $scope.message_compose)
		   			.then(function(data){		
		   				if(data){
		   					$scope.messages.push(data);
			   				 $scope.message_compose={
			   	          		  licNum:0,
			   	          		  title:'',
			   	          		  recipient:'',
			   	          		  message:'',
			   	        		  tezu:0,
			   	          		  attachments:[]
			   	            }
		   				}
		   				modal.hide();	
	   		   });	
      	   }
          
            $scope.deleteMail=function(id){
            	 mainService.withdomain('delete','/user/service/entity/DataTezuMail/'+id)
		   			.then(function(data){
		   				if(data){
		   					console.log($scope.messages.length);
		   					for(i=0;i<$scope.messages.length;i++){
		   					    if($scope.messages[i].id == id){
		   					      $scope.messages.splice(i,1);
		   					    }
		   					}
		   				}		   			
	   			});	
            }

        }
    ])
;