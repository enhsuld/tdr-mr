angular
    .module('altairApp')
    	.controller("weeklyCtrl",['$scope','$state','$stateParams','p_indicator','p_measurement','mainService','sweet','$timeout','$filter',
	        function ($scope,$state,$stateParams,p_indicator,p_measurement,mainService,sweet,$timeout,$filter) {       	
	    		console.log($stateParams.bund);
	    		console.log($stateParams.param);
	        	$scope.domain="com.peace.users.model.mram.RegWeeklyMontly.";
	        	var viewModel = kendo.observable({
	                param: {
	                    cus:  $stateParams.param,
	                    bund:  $stateParams.bund 
	                }
	            });
	        	
	        	$scope.today = function() {
	                $scope.dt = new Date();
	            };
	            $scope.today();

	            $scope.clear = function () {
	                $scope.dt = null;
	            };

	            // Disable weekend selection
	            $scope.disabled = function(date, mode) {
	                return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
	            };

	            $scope.toggleMin = function() {
	                $scope.minDate = $scope.minDate ? null : new Date();
	            };
	            $scope.toggleMin();

	            $scope.open = function($event) {
	                $event.preventDefault();
	                $event.stopPropagation();

	                $scope.opened = true;
	            };

	            $scope.dateOptions = {
	                formatYear: 'yy',
	                startingDay: 1
	            };

	            $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	            $scope.format = $scope.formats[0];

	            var tomorrow = new Date();
	            tomorrow.setDate(tomorrow.getDate() + 1);
	            var afterTomorrow = new Date();
	            afterTomorrow.setDate(tomorrow.getDate() + 2);
	            $scope.events =
	                [
	                    {
	                        date: tomorrow,
	                        status: 'full'
	                    },
	                    {
	                        date: afterTomorrow,
	                        status: 'partially'
	                    }
	                ];

	            $scope.getDayClass = function(date, mode) {
	                if (mode === 'day') {
	                    var dayToCheck = new Date(date).setHours(0,0,0,0);

	                    for (var i=0;i<$scope.events.length;i++){
	                        var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

	                        if (dayToCheck === currentDay) {
	                            return $scope.events[i].status;
	                        }
	                    }
	                }

	                return '';
	            };
	        	
	           // var _date = $filter('date')(new Date(input), 'MM dd yyyy');
	            var d = new Date();
	            var curr_date = d.getDate();
	            var curr_month = d.getMonth()+1;
	            var curr_year = d.getFullYear();
	            
	            var today= new Date();
	            today = $filter('date')(getMonday(new Date()), "dd-MM-yyyy"); 
	            	            
	            function getMonday(d) {
				  d = new Date(d);
				  var day = d.getDay(),
				      diff = d.getDate() - day + (day == 0 ? -6:1); // adjust when day is sunday
				  return new Date(d.setDate(diff));
				}
	            
	            function getMax(d) {
				  d = new Date(d);
				  var day = d.getDay(),
				      diff = d.getDate() - day + 11 + (day == 0 ? -6:1); // adjust when day is sunday
				  return new Date(d.setDate(diff));
				}
	            
	            var maxdate =  $filter('date')(getMax(new Date()), "dd-MM-yyyy"); 
				
	            console.log(today);
	            console.log(maxdate);
	            var $dp_start = $('#uk_dp_start'),
                $dp_end = $('#uk_dp_end');

	            var start_date = UIkit.datepicker($dp_start, {
	                format:'DD-MM-YYYY',
	                minDate:today,
	                maxDate:maxdate,
	                disabled: $scope.disabled()
	            });
	
	
	            
	            var end_date = UIkit.datepicker($dp_end, {
	                format:'DD.MM.YYYY',
	                minDate:today,
	                maxDate:maxdate,
	            });
	
	            $dp_start.on('change',function() {
	                end_date.options.minDate = $dp_start.val();
	            });
	
	            $dp_end.on('change',function() {
	                start_date.options.maxDate = $dp_end.val();
	            });
	        	
	        	
        	   $scope.loadOnConfirm = function() {
    		    	swal({   title: "Тайлан илгээх",  
    		    		text: $stateParams.param,   
    		    		type: "info",   
    		    		showCancelButton: true,   
    		    		closeOnConfirm: false,  
    		    		showLoaderOnConfirm: true, 
    		    		}, 
    		    		function(){
    		    			 mainService.withdomain('get','/user/service/confirmReport/'+$stateParams.bund+'/'+$stateParams.param)
	 				   			.then(function(data){
	 				   				if(data){
	 	 				   				sweet.show('Амжилттай!', 'Тайлан амжилттай илгээгдлээ.', 'success');
	 				   					$state.go('restricted.pages.license');
	 				   				}
	 				   				else{
	 	 				   				sweet.show('update!', 'The lalala.', 'success');
	 				   					$state.go('restricted.error');
	 				   				}
	 				   				
				   			});	
    		    			
		    			}); 		    
    		    	
    		    };
	        	
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
	                toolbar: ["save", "cancel"],
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
                        }],
	                          editable: true
	            };
	          
	        }
    ]);
