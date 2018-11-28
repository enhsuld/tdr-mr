angular
    .module('altairApp')
    	.controller("annualCtrlGovClient",[
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
 
	  	
	  		    var xtype=[{text: "X - тайлан", value: 0}];

	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/AnnualRegistrationClient",
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
		                          { field:"licensenum", title: "<span data-translate='License number'></span>" },		                        
		                          { field:"reporttype", title: "<span data-translate='Report/Plan'></span>", values:rep_type },
		                          { field:"xtype", title: "<span data-translate='Report type'></span>" ,values:xtype},
		                          { field:"lictype", title: "<span data-translate='License type'></span>", values:lic_type  },
		                          { field:"reportyear", title: "<span data-translate='Report year'></span>" },
		                          { field:"repstatusid", title: "<span data-translate='Status'></span>", values:app_status  },
		                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
		                          { field:"officerid", title: "<span data-translate='Officer name'></span>",values:officers  },
		                          { field:"approveddate", title: "<span data-translate='Received date'></span>" }],
		                      editable: "popup"
		            };

	        }
    ]);
