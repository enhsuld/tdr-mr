angular
    .module('altairApp')
    	.controller("mLicenseCtrl",['$scope','$rootScope','$state','mainService','sweet','$timeout',
	        function ($scope,$rootScope,$state,mainService,sweet,$timeout) {       	
            
    			 
    		
    		 	$scope.basic = function() {
    		        sweet.show('Simple right?');
    		    };

    		    $scope.checkIfShown = function(){
    		        alert(sweet.isShown());
    		    };
    		    
    		    $scope.config = function(dataItem) {
    		    	$state.go('restricted.pages.config',{obj:dataItem});    		    	
    		    };
    		    
    		    $scope.prompt = function() {
    		        sweet.show({
    		            title: 'An input!',
    		            text: 'Write something interesting:',
    		            type: 'input',
    		            showCancelButton: true,
    		            closeOnConfirm: false,
    		            animation: 'slide-from-top',
    		            inputPlaceholder: 'Write something'
    		        }, function(inputValue){
    		            if (inputValue === false){
    		                return false;
    		            }
    		            if (inputValue === '') {
    		                sweet.showInputError('You need to write something!');
    		                return false;
    		            }
    		            sweet.show('Nice!', 'You wrote: ' + inputValue, 'success');
    		        });
    		    };
    		    
    		    $scope.loadOnConfirm = function(i) {
    		        sweet.show({
    		        	   title: 'Confirm',
       		            text: 'Delete this file?',
       		            type: 'warning',
       		            showCancelButton: true,
       		            confirmButtonColor: '#DD6B55',
       		            confirmButtonText: 'Yes, delete it!',
       		            closeOnConfirm: false,
       		            closeOnCancel: false,
    		            showLoaderOnConfirm: true
    		        }, function(inputvalue) {
    		        	 if (inputvalue) {
    		        		 mainService.withdomain('delete','/user/service/removeBundle/'+i)
	 				   			.then(function(){
	 				   				$(".rep .k-grid").data("kendoGrid").dataSource.read(); 	      
	 				   				$(".lic .k-grid").data("kendoGrid").dataSource.read(); 	
	 				   				sweet.show('Deleted!', 'The file has been deleted.', 'success');
 				   			});	
     		            }else{
     		                sweet.show('Cancelled', 'Your imaginary file is safe :)', 'error');
     		            }    		        	
    		        	 
    		          /*  $timeout(function() {
    		                sweet.show('Ajax request finished!');
    		            }, 2000);*/
    		        });
    		    };
    		    
    		    $scope.loadOnCreate = function(i) {
    		    	swal({   title: "Ajax request example",  
    		    		text: "Submit to run ajax request",   
    		    		type: "info",   
    		    		showCancelButton: true,   
    		    		closeOnConfirm: false,  
    		    		showLoaderOnConfirm: true, 
    		    		}, 
    		    		function(){
    		    			 mainService.withdomain('put','/user/service/createReport/1/'+i)
	 				   			.then(function(data){
	 				   				if(data.subdate=='true'){
	 	 				   				sweet.show('Deleted!', 'The file has been deleted.', 'success');
	 				   					$state.go('restricted.weekly',{param:0,bund:i});
	 				   				}
	 				   				else if(data.subdate=='false'){
	 				   					
	 				   				}
	 				   				else{
	 	 				   				sweet.show('', 'update', 'success');
	 				   					$state.go('restricted.weekly',{param:data.subdate,bund:i});
	 				   				}
	 				   				
				   			});	
    		    			
		    			}); 		    
    		    	
    		    };
    		    
    		    $scope.confirmCancel = function() {
    		        sweet.show({
    		            title: 'Confirm',
    		            text: 'Delete this file?',
    		            type: 'warning',
    		            showCancelButton: true,
    		            confirmButtonColor: '#DD6B55',
    		            confirmButtonText: 'Yes, delete it!',
    		            closeOnConfirm: false,
    		            closeOnCancel: false
    		        }, function(isConfirm) {
    		            if (isConfirm) {
    		                sweet.show('Deleted!', 'The file has been deleted.', 'success');
    		            }else{
    		                sweet.show('Cancelled', 'Your imaginary file is safe :)', 'error');
    		            }
    		        });
    		    };
    		    
	            $scope.newIssue = {
                    assignee: {
                        config: {
                            create:false,
                            maxItems: 1,
                            valueField: 'id',
                            labelField: 'id',
                            placeholder: 'Үндсэн тусгай зөшөөрөл...'
                        },
                        options: []
                    }
                };

	            var obj = { 
         				id: 0,
        			 main: '',
        			 additional: [],
        			 landname:' ',
        			 wk:0,
        			 isRedemption: '',
        			 priority: ''
			     };
	            
	            $scope.init=function(){
		   	    	 mainService.withdomain('get','/user/service/resourse/ownlicenses')
		   			.then(function(data){
		   				console.log(data);
		   				$scope.newIssue.assignee.options=data;
		   				$scope.selectize_planets_options =data;
		   				$scope.additional = [];
		   			});			
		   		}

	            
	            $scope.selectize_planets_config = {
	                    plugins: {
	                        'remove_button': {
	                            label     : ''
	                        }
	                    },
	                    maxItems: null,
	                    valueField: 'id',
	                    labelField: 'id',
	                    searchField: 'id',
	                    placeholder: "Нэмэлт тусгай зөвшөөрөл",
	                    create: false,
	                    render: {
	                        option: function(planets_data, escape) {
	                            return  '<div class="option">' +
	                                '<span class="title">' + escape(planets_data.title) + '</span>' +
	                                '</div>';
	                        },
	                        item: function(planets_data, escape) {
	                            return '<div class="item"><a href="' + escape(planets_data.url) + '" target="_blank">' + escape(planets_data.title) + '</a></div>';
	                        }
	                    }
	                };
	            
	            var planets_data = $scope.selectize_planets_options=[];
	            $scope.rrid=0;
	            
	            
	            var $formValidate = $('#mlicense');

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
	            
	           	           
	            $scope.chang=function(){
	            	for(var i=0; i < $scope.newIssue.assignee.options.length; i++){
	            		if($scope.newIssue.assignee.options[i].id==$scope.main){
	            			$scope.landname=$scope.newIssue.assignee.options[i].title;
	            		}
	            	}
	            }
	            
	            $scope.delMe=function(i){
		   	    	 mainService.withdomain('delete','/user/service/removeBundle/'+i)
		   			.then(function(){
		   				$(".rep .k-grid").data("kendoGrid").dataSource.read(); 	      
		   				$(".lic .k-grid").data("kendoGrid").dataSource.read(); 	
		   			});			
		   		}
	            
	            
	            $scope.submitForm=function(){
	            	 var data = [];
	            	 var land="";
	            	 if($scope.additional==undefined){
	            		 data.push($scope.main);
	            	 }
	            	 else{
	            		 data=$scope.additional;
	            	 }
	            	 
	            	 if($scope.landname==undefined){
	            		 land="";
	            	 }
	            	 else{
	            		 land=$scope.landname;
	            	 }
	         		 obj = { 
	         			 id: $scope.rrid,
            			 main: $scope.main,
            			 additional: data,
            			 landname: land,
            			 wk:0,
            			 isRedemption: $scope.isRedemtion,
            			 priority: $scope.priority
    			     };

	              	 jQuery.ajax({
	              		url:"/user/service/RegReportReq",
	        			type:'POST',
	        			dataType:'json',
	        			contentType:'application/json',
	        			data:JSON.stringify(obj),	
	        		
	                 	complete: function(r){
	        				var data=r.responseText.trim();
	        				if(data=='true'){ 			
	        					$('.uk-modal-close').trigger('click');
	        					$(".rep .k-grid").data("kendoGrid").dataSource.read(); 	      
	    		   				$(".lic .k-grid").data("kendoGrid").dataSource.read(); 	
	    		   				
	    		   				$scope.additional={};
	    		   				$scope.isRedemtion={};
	    		   				$scope.landname='';
	    		   				$scope.priority={};
	        				}
	        				else{					
	        					//window.location.href = 'http://localhost:8080/user/dd/'+data+'';
	        				}
	        			}
	        		});
	                

	             }
	            
	        	$scope.domain="com.peace.users.model.mram.SubLicenses.";
	            $scope.license = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/SubLicensesConfig",
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
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },

	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
                        input: true,
                        refresh:true,
                        numeric: false
                    },
	                columns: [
	                          { field:"areaNameMon", title: "<span data-translate='Area name'></span>" },	                      
	                          { field:"licenseNum", title: "<span data-translate='License number'></span>" }
	                ],
	                      editable: "popup"
	            };
	   
	            var init=[{"text":"7 хоног","value":"1"},{"text":"Сар","value":"2"}];	
	            var status=[{"text":"Илгээсэн","value":"1"},{"text":"Хүлээн авсан","value":"2"}];	
	            
	            $scope.subForm = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LnkWeekly",
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
	                             id: "id",
	                             
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },

	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
                        input: true,
                        refresh:true,
                        numeric: false
                    },
	                columns: [
	                          { field:"lpReg", title: "<span data-translate='Reg.number'></span>" },	                      
	                          { field:"licenseNum", title: "<span data-translate='License number'></span>" },
	                          { field:"formID", title: "<span data-translate='Report type'></span>", values:init  },
	                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
	                          { field:"repStatusID", title: "<span data-translate='Status'></span>", values:status },
	                ]
	            };
	            
	            
	            
	           
	            
	            $scope.REPBundle = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/RegReportReq",
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
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },

	                filterable: true,
	                sortable: true,
	                resizable: true,
	                columnMenu:true, 
	                pageable: {
	                	refresh:true,
	                    buttonCount: 3
	                },
	                columns: [
	                          { field:"bundledLicenseNum", title: "<span data-translate='Primary license'></span>" },
	                          { field:"addBunLicenseNum", title: "<span data-translate='Secondary license'></span>" },
	                          { field:"areaName", title: "<span data-translate='Area name'></span>" },
	                          { field:"latestChangeDateTime", title: "<span data-translate='Configured date'></span>" },
	                          { 
	                          	 template: kendo.template($("#action").html()),  width: "220px" 	                                
	                           }],
	                      editable: "popup"
	            };
	        }
    ]);
