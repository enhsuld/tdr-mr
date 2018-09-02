angular
    .module('altairApp')
    	.controller("weeklyCoalCtrl",['$scope','$state','$stateParams','p_indicator','p_measurement','mainService','sweet','$timeout',
	        function ($scope,$state,$stateParams,p_indicator,p_measurement,mainService,sweet,$timeout) {       	
	        	$scope.domain="com.peace.users.model.mram.RegWeeklyMontly.";
	        	var viewModel = kendo.observable({
	                param: {
	                    cus:  $stateParams.param,
	                    bund:  $stateParams.bund 
	                }
	            });
	        	
      
	        	
	            $scope.pWeeklyGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/RegWeeklyMontly",
	                            contentType:"application/json; charset=UTF-8",  
	                            data: JSON.parse(JSON.stringify(viewModel.param)),
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".k-grid").data("kendoGrid").dataSource.read(); 
	                    		}
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".k-grid").data("kendoGrid").dataSource.read(); 
	                    		}
	                        },
	                        parameterMap: function(options, operation) {	                        	 
	                        	return JSON.stringify(options);                              
                            }/*
	                        parameterMap: function(options) {
	                       	 return JSON.stringify(options);
	                       }*/
	                    },
	                    schema: {
	                     	data:"data",
	                     	total:"total",
	                     	 model: {                                	
	                             id: "id",
	                             fields: {   
	                             	id: { editable: false,nullable: true},
	                             	formIndex: {  type: "number", editable: false},
	                             	measID: {  type: "number",editable: false},
	                             	data1: {type: "number",  defaultValue: { CategoryID: 1, CategoryName: "Beverages"}, validation: { required: true } },
	                             	data2: {type: "number", defaultValue:"huhihi"},
	                             	data3: {type: "number", defaultValue:0},
	                             	data4: {type: "number", defaultValue:0}
	                             }
	                         }
	                     },
	                    batch: true,
	                    pageSize: 100,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                filterable: true,
                    navigatable: true,
	                resizable: true,
	                columnMenu:true, 
	                dataBinding: function() {
	                    record = (this.dataSource.page() -1) * this.dataSource.pageSize();
	                  },
	                columns: [{
                        title: "<span data-translate='N'></span>",
                        template: "#= ++record #",
                        width: 50
                    },
                        {
                            field: "formIndex",
                            title: "<span data-translate='Indicator'></span>",
                            values:p_indicator,
                            width: 250
                        },
                        {
                            field: "measID",
                            title: "<span data-translate='Measurements'></span>",
                            values:p_measurement,
                            width: 120
                        },
                        {
                            field: "data1",
                            title: "<span data-translate='Annual plan'></span>",
                            width: 200
                        },
                        {
                            title: "<span data-translate='Performance'></span>",
                            columns: [{
                                field: "data2",
                                title: "<span data-translate='Increased amount from year'></span>",
                                width: 200
                            },{
                                field: "data3",
                                title: "<span data-translate='Current week amount'></span>",
                                width: 200
                            },{
                                field: "data4",
                                title: "<span data-translate='Annual performance'></span>",
                                width: 200
                                },
                            ]
                        }]
	            };
	          
	        }
    ]);
