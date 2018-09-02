angular
    .module('altairApp')
    	.controller("MainAddPro",[
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
    	                          'mv',
    	                          'mineralData',
    	                          'mineType',
    	                          'concent',
    	                          'rrq',
	        function ($compile,$q,$scope,$http,$timeout,$rootScope,$state,$stateParams,utils,mainService,sweet,mv,mineralData,mineType,concent,rrq) {   
    	      	                        	  
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
	            var isfl=[{"text":"Тийм","value":1 },{"text":"Үгүй","value": 0}];
	            
	            var isfile_data = $scope.selectize_isfile_options = isfl;
     			$scope.selectize_isfile_config = {
     	                plugins: {
     	                    'remove_button': {
     	                        label     : ''
     	                    }
     	                },
     	                maxItems: 1,
     	                minItems:1,
     	                valueField: 'value',
     	                labelField: 'text',
     	                searchField: 'text',
     	                create: false,
     	                render: {
     	                    option: function(isfile_data, escape) {
     	                        return  '<div class="option">' +
     	                            '<span class="title">' + escape(isfile_data.text) + '</span>' +
     	                            '</div>';
     	                    }
     	                }
     	            };
     			 var TwoSource=[{"value":1,"text":"Өөрөө гүйцэтгэх","parentid":1 },{"value": 2,"text":"Гэрээгээр гүйцэтгүүлэх","parentid":1}
     			 				,{"value": 3,"text":"Үгүй","parentid":2}];

     			var bombtype_data = $scope.selectize_bombtype_options = TwoSource;
     			$scope.selectize_bombtype_config = {
     	                plugins: {
     	                    'remove_button': {
     	                        label     : ''
     	                    }
     	                },
     	                maxItems: 1,
     	                minItems:1,
     	                valueField: 'value',
     	                labelField: 'text',
     	                searchField: 'text',
     	                create: false,
     	                render: {
     	                    option: function(bombtype_data, escape) {
     	                        return  '<div class="option">' +
     	                            '<span class="title">' + escape(bombtype_data.text) + '</span>' +
     	                            '</div>';
     	                    }
     	                }
     	            };
     			var concent_data = $scope.selectize_concent_options = concent;
     			
     			$scope.selectize_concent_config = {
     	                plugins: {
     	                    'remove_button': {
     	                        label     : ''
     	                    }
     	                },
     	                maxItems: 1,
     	                minItems:1,
     	                valueField: 'value',
     	                labelField: 'text',
     	                searchField: 'text',
     	                create: false,
     	                render: {
     	                    option: function(concent_data, escape) {
     	                        return  '<div class="option">' +
     	                            '<span class="title">' + escape(concent_data.text) + '</span>' +
     	                            '</div>';
     	                    }
     	                }
     	            };
	          // $scope.config.reqid=$stateParams.id;
	            var mineral_data = $scope.selectize_mineral_options = mineralData;
     			
     			$scope.selectize_mineral_config = {
     	                plugins: {
     	                    'remove_button': {
     	                        label     : ''
     	                    }
     	                },
     	                maxItems: 1,
     	                minItems:1,
     	                valueField: 'value',
     	                labelField: 'text',
     	                searchField: 'text',
     	                create: false,
     	                render: {
     	                    option: function(mineral_data, escape) {
     	                        return  '<div class="option">' +
     	                            '<span class="title">' + escape(mineral_data.text) + '</span>' +
     	                            '</div>';
     	                    }
     	                }
     	            };
     			var mineType_data = $scope.selectize_mineType_options = mineType;
     			
     			$scope.selectize_mineType_config = {
     	                plugins: {
     	                    'remove_button': {
     	                        label     : ''
     	                    }
     	                },
     	                maxItems: 1,
     	                minItems:1,
     	                valueField: 'value',
     	                labelField: 'text',
     	                searchField: 'text',
     	                create: false,
     	                render: {
     	                    option: function(mineType_data, escape) {
     	                        return  '<div class="option">' +
     	                            '<span class="title">' + escape(mineType_data.text) + '</span>' +
     	                            '</div>';
     	                    }
     	                }
     	            };
     			var deposidid_data = $scope.selectize_deposidid_options = [];
     			
     			$scope.selectize_deposidid_config = {
     	                plugins: {
     	                    'remove_button': {
     	                        label     : ''
     	                    }
     	                },
     	                maxItems: 1,
     	                minItems:1,
     	                valueField: 'value',
     	                labelField: 'text',
     	                searchField: 'text',
     	                create: false,
     	                render: {
     	                    option: function(deposidid_data, escape) {
     	                        return  '<div class="option">' +
     	                            '<span class="title">' + escape(deposidid_data.text) + '</span>' +
     	                            '</div>';
     	                    }
     	                }
     	            };
     			
     			$scope.mineralChange = function(id){
     				mainService.withdomain('get','/user/service/resourse/data/deposididData/'+id)
 		   			.then(function(data){
 		   				deposidid_data = $scope.selectize_deposidid_options = data;
 		   				if(data.length>0){
 		   					//$scope.config.deposidid = data[0].value;
 		   				}
 		   			});	
     			}
     			$scope.showcom=false;
     			$scope.enacom = function(){
     				if($scope.config.komissid==0){
     					$scope.showcom=false;
     				}
     				else{
     					$scope.showcom=true;
     				}
     			}
     			
    	       $scope.submitForm = function() {	   
    	    	   if($scope.config.statebudgetid==1){
    	    		   $scope.config.subContracts.push($scope.subContract);
    	    		   $scope.config.subNoContracts= [];
    	    	   }
    	    	   else{
    	    		   $scope.config.subNoContracts.push($scope.subNoContract);
    	    		   $scope.config.subContracts= [];
    	    	   }
    	    	   mainService.withdata('put','/logic/submitMVConfig/'+$stateParams.id, $scope.config)
  		   			.then(function(data){		   				
  		   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
  			   			$state.go("restricted.pages.planConfig");	
  		   		   })
  		   		  .catch(function(data, status) {
  		   			  sweet.show('Алдаа үүслээ', 'Мэдээлэл дутуу байна.', 'error');	
					  console.error('Repos error', status, data);
				   })
    	    	   
    	    	/*   if($scope.config.subNoContracts.length>0 || $scope.config.subContracts.length>0){
    	    		   mainService.withdata('put','/logic/submitMVConfig/'+$stateParams.id, $scope.config)
	   		   			.then(function(data){		   				
	   		   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
	   			   			$state.go("restricted.pages.planConfig");	
	   		   		   })
	   		   		  .catch(function(data, status) {
	   		   			  sweet.show('Алдаа үүслээ', 'Мэдээлэл дутуу байна.', 'error');	
						  console.error('Repos error', status, data);
					   })
    	    	   }
    	    	   else{
    	    		   sweet.show('Алдаа үүслээ', 'Мэдээлэл дутуу байна.', 'error');	
    	    	   } */   	                      
	           }
    	                        	  
      	       
    	       $scope.mineralDataSource = {
          	            serverFiltering: true,
          	            transport: {
          	                read: {
          	                    url: "/user/service/cascading/kendo/LutMinerals",
          	                    contentType:"application/json; charset=UTF-8",                                    
                                  type:"POST"
          	                },
          	                parameterMap: function(options) {
                             	   return JSON.stringify(options);
                              }
          	             }
          	        };
    	       
    	       $scope.deposididDataSource = {
         	            serverFiltering: true,
         	            transport: {
         	                read: {
         	                    url: "/user/service/cascading/kendo/LutDeposit",
         	                    contentType:"application/json; charset=UTF-8",                                    
                                 type:"POST"
         	                },
         	                parameterMap: function(options) {
                            	   return JSON.stringify(options);
                             }
         	             }
         	        };
    	       
    	       $scope.statebudget = function(){
    	    	   if($scope.config.statebudgetid==1){
    	    		   $scope.contract=true;
    	    		   $scope.nocontract=false;
    	    	   }
    	    	   else{
    	    		   $scope.nocontract=true;
    	    		   $scope.contract=false;
    	    	   }
    	       }
    	       
    		
    			
    	       
    	       $scope.concentrationDataSource = {
    	            serverFiltering: true,
    	            transport: {
    	                read: {
    	                    url: "/user/service/cascading/kendo/LutConcentration",
    	                    contentType:"application/json; charset=UTF-8",                                    
                            type:"POST"
    	                },
    	                parameterMap: function(options) {
                       	   return JSON.stringify(options);
                        }
    	             }
    	        };
        	   
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
                              {"Id":1,"name":"Өөрөө гүйцэтгэх","parentid":1},
                              {"Id":1,"name":"Гэрээгээр гүйцэтгүүлэх","parentid":1} 			            
    			        ]
                   }
               }).data("kendoDropDownList");
               
               $scope.OneSource = {
     	            data: [
                          {"id":1,"name":"Тийм"},
                          {"id":2,"name":"Үгүй"} 			            
			        ]
     	       };
               
               $scope.TwoSource = {
          	            data: [
                               {"id":1,"name":"Өөрөө гүйцэтгэх","parentid":1},
                               {"id":2,"name":"Гэрээгээр гүйцэтгүүлэх","parentid":1},
                               {"id":3,"name":"Үгүй","parentid":2} 		
     			        ]
          	        };
               $scope.wer=true;
               $scope.ena=function(){
            	   if($scope.config.komissid==1){
            		   $scope.wer=false;
            	   }
            	   else{
            		   $scope.wer=true;
            		   $scope.config.komissdate="";
            		   $scope.config.komissakt="";
            	   }
               }
    		   
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
        	   
        	   console.log(rrq);
        	   
        	   $scope.rrq=rrq;
        	   
	           $scope.config=mv;
	           console.log(mv);
	           console.log(mv);
	           if(mv!=null && mv!=false){
	        	   $scope.config.deposidid=mv.deposidid;
	        	   $scope.subContract=mv.subContracts[0];
	        	   $scope.subNoContract=mv.subNoContracts[0];
	        	   if(mv.subContracts.length>0){
		        	   $scope.contract=true;
		           }
		           if(mv.subNoContracts.length>0){
		    		   $scope.nocontract=true;
		           }
	           }
	           else{
	        	   $scope.config={};
	        	
	        	   mainService.withdomain('get','/user/service/resourse/data/deposididData/'+rrq.mineralid)
		   			.then(function(data){
		   				deposidid_data = $scope.selectize_deposidid_options = data;
		   				if(data.length>0){
		   					//$scope.config.deposidid = data[0].value;
		   				}
		   			});	
	        	   $scope.config={
	        			   subNoContracts:[],
	        			   subContracts:[],
	        			   reqid: $stateParams.id,
	        			   mineralid:rrq.mineralid,
	        			   minetypeid:mineType[0].value,
	        			   statebudgetid:isfl[0].value,
	        			   concetrate:concent[0].value,
	        			   komissid: isfl[0].value,
	        			   bombid:isfl[0].value,
	        			   bombtype:TwoSource[0].value,
	        	   }
	           }
	        }
    ]);
