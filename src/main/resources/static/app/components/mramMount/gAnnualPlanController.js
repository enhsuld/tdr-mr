angular
    .module('altairApp')
    	.controller("annualReportCtrlGov",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'lic_type',
    	                          'app_status',
    	                          'officers',
    	                          'rep_type',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,lic_type,app_status,officers,rep_type) {   
 
	  		    $scope.loadOnCheck = function(item) {	
		  		  	if(item.reporttype==3){
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.GovPlanFormH',{param:item.id});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovPlanFormA',{param:item.id});
		   				}	  		    		 
	  		    	}
	  		    	else{
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.GovReportFormH',{param:item.id, id:item.repstepid});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovReportFormA',{param:item.id, id:item.repstepid});
		   				} 
	  		    	}
	  		  	
	  		    	/*if(item.reporttype==3){
	  		    		$state.go('restricted.pages.GovPlanForm',{param:item.id});
	  		    	}
	  		    	else{
	  		    		$state.go('restricted.pages.GovReportForm',{param:item.id});
	  		    	}*/
	  		    		  		    	
	  		    };
	  		  var xtype=[{text: "X - тайлан", value: 0}];

	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/AnnualRegistration",
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

		                filterable: {
	                        mode: "row"
	                    },
		                sortable: {
	                        mode: "multiple",
	                        allowUnsort: true
	                    },
		                resizable: true,
		                pageable: {
		                	refresh:true,
		                    buttonCount: 3
		                },
		                columns: [
		                          { field:"lpName", title: "<span data-translate='Company name'></span>" },	
		                          { field:"licensenum", title: "<span data-translate='License number'></span>" },		
		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		   
		                          { field:"reporttype", title: "<span data-translate='Report/Plan'></span>", values:rep_type },
		                          { field:"xtype", title: "<span data-translate='Report type'></span>" ,values:xtype},
		                          { field:"lictype", title: "<span data-translate='License type'></span>", values:lic_type  },
		                          { field:"reportyear", title: "<span data-translate='Report year'></span>" },
		                          { field:"repstatusid", title: "<span data-translate='Status'></span>", values:app_status  },
		                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
		                          { field:"officerid", title: "<span data-translate='Officer name'></span>",values:officers  },
		                          { field:"approveddate", title: "<span data-translate='Received date'></span>" },		                         
		                          { 
		                          	 template: kendo.template($("#main").html()),  width: "120px" 	                                
		                           }],
		                      editable: "popup"
		            };

	        }
    ]);
