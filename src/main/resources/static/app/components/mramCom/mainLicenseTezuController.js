angular
    .module('altairApp')
    	.controller("tezu",[
    	                          '$compile',
    	                          '$q',
    	                          '$scope',
    	                          '$http',	
    	                          '$timeout',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          '$timeout',
    	                          'variables',
    	                          'users',
	        function ($compile,$q,$scope,$http,$timeout,$rootScope,$state,utils,mainService,sweet,$timeout,variables,users) {   
    	          
    	        
    	                          //    $rootScope.toBarActive = true;

    	                            //  $scope.messages = messages;

    	                              var $mailbox = $('#mailbox');
    	                              
    	                              var id=0;
    	                              $scope.message_compose={
    	                            		  licNum:0,
    	                            		  title:'',
    	                            		  recipient:'',
    	                            		  message:'',
    	                            		  tezu:1,
    	                            		  attachments:[]
    	                              }
    	                              $scope.loadOnConfig = function(i){
    	                            	   $scope.message_compose.licNum=i.licenseNum;
    	                            	   
    	                            	   $scope.message_compose.title="Тэзү "+i.licenseXB;
    	                            	   var licnum=i.licenseNum;
    	                            	   console.log(licnum);
    	                            	   $timeout(function() {
    	    	                                  var progressbar = $("#mail_progressbar"),
    	    	                                      bar         = progressbar.find('.uk-progress-bar'),
    	    	                                      settings    = {
    	    	                                          action: '/file/upload/', // upload url
    	    	                                          params:{"data": licnum},
    	    	                                          single: false,
    	    	                                          filelimit:10,
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
    	    	                                        	  UIkit.notify("<i class='uk-icon-check'></i> File uploaded...",{status:'success',pos:'bottom-right'});
    	    	                                              console.log(response);
    	    	                                          }
    	    	                                      };

    	    	                                  var select = UIkit.uploadSelect($("#mail_upload-select"), settings),
    	    	                                      drop   = UIkit.uploadDrop($("#mail_upload-drop"), settings);
    	    	                              })
    	    	                              
    	    	                          
    	    	                              
    	                              }
    	                              
    	                             	                              
    	                              
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
    	                              
    	                              var modal = UIkit.modal("#modal_main_tezu");
    	                              $scope.validator = function(){
    	                            	  mainService.withdata('put','/logic/tezu', $scope.message_compose)
	    	              		   			.then(function(data){		
	    	              		   				modal.hide();
	    	              		   				//sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
	    	              			   			$state.go("restricted.pages.companymailbox");	
	    	              		   		   });	
	                            	   }
             
	            $scope.PlanExploration = {
 	                dataSource: {	
 	                	pageSize: 8,
 	                	serverPaging: true,
 	                    serverSorting: true,
 	                    serverFiltering: true,
 	                    transport: {
 	                    	read:  {
 	                            url: "/user/angular/SubLicensesMainConfiguration",
 	                            contentType:"application/json; charset=UTF-8",                                    
 	                            type:"POST"
 	                    	},
 	                        parameterMap: function(options) {
 	                       	 return JSON.stringify(options);
 	                       }
 	                    },
 	                    schema: {
 	                     	data:"data",
 	                     	total:"total",
 	                     	 model: {                                	
 	                             id: "id"
 	                         }
 	                     }
 	                  
 	                },
 	                filterable:true,
 	                sortable: {
                         mode: "multiple",
                         allowUnsort: true
                     },
 	                pageable: {
 	                    refresh: true,
 	                    pageSizes: true,
 	                    buttonCount: 5
 	                },
 	                columns: [
 	                          { field:"licenseXB",title: "<span data-translate='License number'></span>"},	 
 	                          { field:"areaNameMon", title: "<span data-translate='Area name'></span>" },	
 	                          { field:"areaSize",title: "<span data-translate='Area size (ha)'></span>"},	
 	                          { field:"locationAimag",title: "<span data-translate='Aimag'></span>"},	
 	                          { field:"locationSoum", title: "<span data-translate='Soum'></span>" },	
 	                          { 
	                          	 template: kendo.template($("#Configuration").html()),  width: "110px" 	                                
		                      }
 	                ],
 	                      editable: "popup"
 	            };
	            
	        }
    ]);
