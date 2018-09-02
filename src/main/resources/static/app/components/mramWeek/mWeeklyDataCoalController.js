angular
    .module('altairApp')
    	.controller("weeklyNewDataCoal",['$scope','$state','$stateParams','p_indicator','p_measurement','mainService','sweet','$timeout','$filter','sgt',
	        function ($scope,$state,$stateParams,p_indicator,p_measurement,mainService,sweet,$timeout,$filter,sgt) {       	
	    		
	        	$scope.domain="com.peace.users.model.mram.RegWeeklyMontly.";
	        	var viewModel = kendo.observable({
	                param: {
	                    id:  $stateParams.id
	                }
	            });
	        	$scope.sgt=sgt[0];
	        		        	
	        	$scope.cher = {
	        			comment:''
	        	};
	        	
	        	init();
	    	    function init(){
		   	    	 mainService.withdomain('get','/user/service/getWeekDetail/'+$stateParams.id)
		   			.then(function(data){		   			
		   				console.log(data);
		   				$scope.cher.comment=data[0].comment;
		   				$scope.xonoff =data[0].onoff;		   				
			            $scope.pWeeklyGridCoal = {
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
				                    autoSync: true,
				                    serverPaging: true,
				                    serverSorting: true
				                },
				              /*  toolbar: ["save", "cancel"],*/
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
			                        width: 60
			                    },
			                        {
			                            field: "formIndex",
			                            title: "<span data-translate='Indicator'></span>",
			                            values:p_indicator,
			                            width: 200
			                        },
			                        {
			                            field: "measID",
			                            title: "<span data-translate='Measurements'></span>",
			                            values:p_measurement,
			                            width: 120
			                        },
			                        {
			                            field: "data1",
			                            title: "<span data-translate='Week'></span>"
			                        },
			                        {
			                            title: "<span data-translate='Plan-beginning from month'></span>",
			                            columns: [{
			                                field: "data2",
			                                title: "<span data-translate='Plan-beginning from month'></span>"
			                            },{
			                                field: "data3",
			                                title: "<span data-translate='Performance-beginning from month'></span>"
			                            }]
			                        },
			                        {
			                            title: "<span data-translate='Plan-from year'></span>",
			                            columns: [{
			                                field: "data4",
			                                title: "<span data-translate='Plan-from year'></span>"
			                            },{
			                                field: "data5",
			                                title: "<span data-translate='Performance-from year'></span>"
			                            }]
			                        },
			                        {
			                            field: "data6",
			                            title: "<span data-translate='Price of the product sales-1 ton'></span>"
			                        },
			                        {
			                            field: "data7",
			                            title: "<span data-translate='Sales income'></span>"
			                        }
		                        ],
				                          editable: true
				            };
				            
				            if($scope.xonoff){
			    		    	$scope.pWeeklyGridCoal.toolbar= ["save", "cancel","excel", "pdf"];
			    		    	$scope.pWeeklyGridCoal.editable=true;
			    		    }
			    		    else{
			    		    	 $scope.pWeeklyGridCoal.toolbar=["excel", "pdf"];
			    		    	 $scope.pWeeklyGridCoal.editable=false;
			    		    }
				            
		   			});			
		   		}
	    	    

	    	    $scope.loadOnPrevious = function() {   
	    	    	 var pwid=$scope.sgt.weekid-1;	    	    	
	    	          $scope.previousWeeklyGrid = {
				                dataSource: {				                   
				                    transport: {
				                    	read:  {
				                            url: "/user/angular/RegWeeklyMontlyPrevious",
				                            contentType:"application/json; charset=UTF-8",  
				                            data: {"custom":"where year="+$scope.sgt.year+" and lwid="+pwid+" and LicenseNum="+$scope.sgt.lcnum+""},
				                            type:"POST"
				                        },
				                        parameterMap: function(options, operation) {	                        	 
				                        	return JSON.stringify(options);                              
			                            }
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
				                toolbar:["excel", "pdf"],
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
				                            title: "<span data-translate='Week'></span>",
				                            width: 100
				                        },
				                        {
				                            title: "<span data-translate='Plan-beginning from month'></span>",
				                            columns: [{
				                                field: "data2",
				                                title: "<span data-translate='Plan-beginning from month'></span>",
				                                width: 200
				                            },{
				                                field: "data3",
				                                title: "<span data-translate='Performance-beginning from month'></span>",
				                                width: 200
				                            }]
				                        },
				                        {
				                            title: "<span data-translate='Plan-from year'></span>",
				                            columns: [{
				                                field: "data4",
				                                title: "<span data-translate='Plan-from year'></span>",
				                                width: 200
				                            },{
				                                field: "data5",
				                                title: "<span data-translate='Performance-from year'></span>",
				                                width: 200
				                            }]
				                        },
				                        {
				                            field: "data6",
				                            title: "<span data-translate='Price of the product sales-1 ton'></span>",
				                            width: 100
				                        },
				                        {
				                            field: "data7",
				                            title: "<span data-translate='Sales income'></span>",
				                            width: 100
				                        }
			                        ],
				                          editable: true
				            };
    		    };
	    	    
        	    $scope.loadOnConfirm = function() {    
        	    	if($scope.cher.comment.length>0){
        	    		swal({   
        		    		title: "Тайлан илгээх",  
        		    		text: "Та  долоо хоногийн тайлан үүсгэх үү?",  
        		    		type: "info",   
        		    		showCancelButton: true,   
        		    		closeOnConfirm: false,  
        		    		showLoaderOnConfirm: true, 
        		    	    confirmButtonColor: '#DD6B55',
    		     		    confirmButtonText: 'Тийм',
    		     		    cancelButtonText: 'Үгүй',
        		    		}, 
        		    		function(){
        		    			 mainService.withdata('put','/user/service/confirmWeekReport/'+$stateParams.id, $scope.cher)
    	 				   			.then(function(data){
    	 				   				if(data){
    	 	 				   				sweet.show('Амжилттай!', 'Тайлан амжилттай илгээгдлээ.', 'success');
    	 				   					$state.go('restricted.pages.weekstatus');
    	 				   				}
    	 				   				else{
    	 	 				   				sweet.show('update!', 'The lalala.', 'success');
    	 				   					$state.go('restricted.error');
    	 				   				}
    	 				   				
    				   			});	
        		    			
    		    			}); 
        	    	}  
        	    	else{
        	    		 sweet.show('Анхаар!', 'Тайлантай холбоотой тайлбар оруулна уу', 'warning');
        	    	}
    		    	
    		    };

	        }
    ]);
