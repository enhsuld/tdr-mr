angular
    .module('altairApp')
    	.controller("pvconfig",[
    	                          '$compile',
    	                          '$q',
    	                          '$scope',
    	                          '$http',	
    	                          '$timeout',
    	                          '$rootScope',
    	                          '$state',
    	                          '$stateParams',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'pv',
	        function ($compile,$q,$scope,$http,$timeout,$rootScope,$state,$stateParams,utils,mainService,sweet,pv) {   
             
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
	            $scope.config={
	            		reqid:$stateParams.id
        		};
	            $scope.config=pv;
	          
	          
    	       
    	       $scope.submitForm = function() {	    		
	    		   mainService.withdata('put','/logic/submitPVConfig/'+$stateParams.id, $scope.config)
		   			.then(function(data){		   				
		   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
		   				$state.go("restricted.pages.planConfig");	
			   				
		   		   });	 	                         
	           }
    	            
    		   $scope.productsDataSource = {
          	            serverFiltering: true,
          	            transport: {
          	             serverFiltering: true,
          	                read: {
          	                    url: "/user/service/cascading/kendo/LutAdminunit",
          	                    contentType:"application/json; charset=UTF-8",                                    
                                  type:"POST"
          	                },
          	                parameterMap: function(options) {
                             	   return JSON.stringify(options);
                              }
          	             }
          	        };
    		   
        	   
        	   var categories = $("#bomber").kendoDropDownList({
                   optionLabel: "Тэсэлгээний ажил...",
                   dataTextField: "name",
                   dataValueField: "id",
                   dataSource: {
                       serverFiltering: true,
                       data: [
      						{
      					       "id": "1",
      					       "name": "Тийм"
      					    },   
         			            {
         			                "id": "0",
         			                "name": "Үгүй"
         			            }   			            
         			        ]
                   }
               }).data("kendoDropDownList");

               var products = $("#bomberman").kendoDropDownList({
                   autoBind: false,
                   cascadeFrom: "bomber",
                   optionLabel: "Гүйцэтгэгч...",
                   dataTextField: "name",
                   dataValueField: "id",
                   dataSource: {
                       serverFiltering: true,
                       data: [
 						{
 					       "id": "1",
 					       "name": "Өөрөө гүйцэтгэх"
 					    },   
    			            {
    			                "id": "0",
    			                "name": "Гэрээгээр гүйцэтгүүлэх"
    			            }   			            
    			        ]
                   }
               }).data("kendoDropDownList");
               
    		   
        	   $scope.mineType = {
       	            serverFiltering: true,
       	            filter: "startswith",
       	            transport: {
       	                read: {
       	                    url: "/user/service/cascading/kendo/LutMineType",
       	                    contentType:"application/json; charset=UTF-8",                                    
                               type:"POST"
       	                },
       	                parameterMap: function(options) {
                          	   return JSON.stringify(options);
                           }
       	             }
       	        };
        	   
        	   $scope.yesno = {
    			    dataTextField: 'name',
    			    dataValueField: 'id',
    			    data: [
						{
					       "id": 1,
					       "name": "Тийм"
					    },   
   			            {
   			                "id": 0,
   			                "name": "Үгүй"
   			            }   			            
   			        ]
    			};
        	   
        	   $scope.bomb = {
       			    dataTextField: 'name',
       			    dataValueField: 'id',
       			    data: [
   						{
   					       "id": "1",
   					       "name": "Өөрөө гүйцэтгэх"
   					    },   
      			            {
      			                "id": "0",
      			                "name": "Гэрээгээр гүйцэтгүүлэх"
      			            }   			            
      			        ]
       			};
        	   
	        }
    ]);
