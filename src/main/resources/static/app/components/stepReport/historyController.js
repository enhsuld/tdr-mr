angular
    .module('altairApp')
    	.controller("historylistreport",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
	        function ($scope,$rootScope,$state,utils,mainService,sweet) {   
 
	  		    $scope.loadOnCheck = function(item) {	
		  		  	if(item.reporttype==3){
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.GovPlanFormH',{param:item.id});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovPlanFormA',{param:item.id, id:item.repstepid});
		   				}	  		    		 
	  		    	}
	  		    	else{
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.GovReportFormH',{param:item.id});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovReportFormA',{param:item.id});
		   				}	 
	  		    	}
	  		    		  		    	
	  		    };
	  		    
			    $scope.loadOnZero = function(item) {	  		    	
	  		    	$state.go('restricted.pages.zeroAnswer',{id:item.id});
	  		    };
	  		    
	  		  var xtype=[{text: "X - тайлан", value: 0}];

	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                    		url: "/user/step/1/4/history",
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
		                    serverFiltering: true,
		                    serverSorting: true
		                },
		                groupable: true,
		                filterable: true,
		                sortable: {
	                        mode: "multiple",
	                        allowUnsort: true
	                    },
		                scrollable: true,
		                pageable: {
		                	refresh:true,
		                    buttonCount: 3
		                },
		                columns: [
		                          { field:"lpName", title: "<span data-translate='Company name'></span>" },	
		                          { field:"licensenum", title: "<span data-translate='License number'></span>" },	
		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		   
		                          { field:"reportyear", title: "<span data-translate='Report year'></span>" },
		                          { 
		                          	 template: kendo.template($("#status").html()), title: "<span data-translate='Status'></span>"   	                                
		                           },
		                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
		                          { field:"approveddate", title: "<span data-translate='Received date'></span>" },		                         
		                          { 
		                          	 template: kendo.template($("#main").html()),  width: "120px" 	                                
		                           }],
		                      editable: "popup"
		            };

	        }
    ]);
