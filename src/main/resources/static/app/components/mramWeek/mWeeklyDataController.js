angular
    .module('altairApp')
    	.controller("weeklyNewData",['$scope','$state','$stateParams','p_indicator','p_measurement','mainService','sweet','sgt','weekDetail',
	        function ($scope,$state,$stateParams,p_indicator,p_measurement,mainService,sweet,sgt,weekDetail) {       	
	    		
	        	$scope.domain="com.peace.users.model.mram.WeeklyMainData.";
	        	var viewModel = kendo.observable({
	                param: {
	                    id:  $stateParams.id
	                }
	            });
	        	$scope.sgt=sgt[0];
	        		        	
	        	$scope.cher = {
	        			comment:''
	        	};
	        	
	        	$scope.cher.comment='';
   				
   				$scope.cher.comment=weekDetail[0].comment;
   				$scope.xonoff =weekDetail[0].onoff;
   			
   				if(weekDetail[0].mcom.length>0){
   					$scope.mcom =weekDetail[0].mcom;
   				}
   				
   				var groups=[{"text":"1. Олборлолт","value":"1"},{"text":"2. Бүтээгдэхүүн гаргалт","value":"2"},{"text":"3. Бүтээгдэхүүн борлуулалт","value":"3"},{"text":"а. Дотоод","value":"4"},{"text":"б. Гадаад","value":"5"}];	 

   				$scope.pWeeklyGrid = {
   		                dataSource: {
   		                	autoSync:true,
   		                    transport: {
   		                    	read:  {
   		                            url: "/user/angular/WeeklyMainData",
   		                            contentType:"application/json; charset=UTF-8",  
   		                            data:{"custom":"where planid="+$stateParams.id+"","sort":[{field: 'id', dir: 'asc'}]},
   		                            //data: JSON.parse(JSON.stringify(viewModel.param)),
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
   		                             	measurement: {  type: "number",editable: false},
   		                             	indicator: {  type: "string",editable: false},
   		                             	execution: {type: "number", editable: false, defaultValue:0 },
   		                             	wpercentage: {type: "number", editable: false, defaultValue:0 },
   		                             	data1: {type: "number", defaultValue:0},
   		                             	data2: {type: "number", defaultValue:0},
   		                             	data3: {type: "number", defaultValue:0},
   		                             	data4: {type: "number", defaultValue:0},
   		                              	data5: {type: "number", defaultValue:0},
   		                             	data6: {type: "number", defaultValue:0},
   		                              	data7: {type: "number", defaultValue:0},
   		                              	data8: {type: "number", defaultValue:0},
   		                              	data9: {type: "number", defaultValue:0},
   		                              	data10: {type: "number", defaultValue:0},
   		                              	data11: {type: "number", defaultValue:0},
   		                              	data12: {type: "number", defaultValue:0},
   		                              	data13: {type: "number", defaultValue:0},
   		                              	data14: {type: "number", defaultValue:0},
   		                              	data15: {type: "number", defaultValue:0},
   		                              	data16: {type: "number", defaultValue:0},
   		                              	data17: {type: "number", defaultValue:0},
   		                              	data18: {type: "number", defaultValue:0},
   		                              	data19: {type: "number", defaultValue:0},
   		                              	data20: {type: "number", defaultValue:0},
   		                              	data21: {type: "number", defaultValue:0},
   		                              	data22: {type: "number", defaultValue:0},
   		                              	data23: {type: "number", defaultValue:0},
   		                              	data24: {type: "number", defaultValue:0},
   		                              	data25: {type: "number", defaultValue:0},
   		                              	data26: {type: "number", defaultValue:0},
   		                              	data27: {type: "number", defaultValue:0},
   		                              	data28: {type: "number", defaultValue:0},
   		                              	data29: {type: "number", defaultValue:0},
   		                              	data30: {type: "number", defaultValue:0},
   		                              	data31: {type: "number", defaultValue:0},
   		                              	data32: {type: "number", defaultValue:0},
   		                              	data33: {type: "number", defaultValue:0},
   		                              	data34: {type: "number", defaultValue:0},
   		                              	data35: {type: "number", defaultValue:0},
   		                              	data36: {type: "number", defaultValue:0},
   		                              	data37: {type: "number", defaultValue:0},
   		                              	data38: {type: "number", defaultValue:0},
   		                              	data39: {type: "number", defaultValue:0},
   		                              	data40: {type: "number", defaultValue:0},
   		                              	data41: {type: "number", defaultValue:0},
   		                              	data42: {type: "number", defaultValue:0},
   		                              	data43: {type: "number", defaultValue:0},
   		                              	data44: {type: "number", defaultValue:0},
   		                              	data45: {type: "number", defaultValue:0},
   		                              	data46: {type: "number", defaultValue:0},
   		                              	data47: {type: "number", defaultValue:0},
   		                              	data48: {type: "number", defaultValue:0},
   		                              	data49: {type: "number", defaultValue:0},
   		                              	data50: {type: "number", defaultValue:0},
   		                              	data51: {type: "number", defaultValue:0},
   		                              	data52: {type: "number", defaultValue:0},
   		                              	data53: {type: "number", defaultValue:0},
   		                              	data54: {type: "number", defaultValue:0},
   		                              	data55: {type: "number", defaultValue:0},
   		                              	data56: {type: "number", defaultValue:0},
   		                              	data57: {type: "number", defaultValue:0},
   		                              	data58: {type: "number", defaultValue:0},
   		                              	data59: {type: "number", defaultValue:0},
   		                              	data60: {type: "number", defaultValue:0},
   		                              	data61: {type: "number", defaultValue:0},
   		                              	data62: {type: "number", defaultValue:0},
   		                              	data63: {type: "number", defaultValue:0},
   		                              	data64: {type: "number", defaultValue:0},
   		                              	data65: {type: "number", defaultValue:0},
   		                              	data66: {type: "number", defaultValue:0},
   		                              	data67: {type: "number", defaultValue:0},
   		                              	data68: {type: "number", defaultValue:0},
   		                              	data69: {type: "number", defaultValue:0},
   		                              	data70: {type: "number", defaultValue:0},
   		                              	data71: {type: "number", defaultValue:0},
   		                              	data72: {type: "number", defaultValue:0},
   		                              	data73: {type: "number", defaultValue:0},
   		                              	data74: {type: "number", defaultValue:0},
   		                              	data75: {type: "number", defaultValue:0, editable: false},
   		                             }
   		                         }
   		                     },
   		                    batch: true,
   		                    pageSize: 100,
   		                    autoSync: true,
   		                    serverPaging: true,
   		                    serverSorting: true,
   		                 	group: {
   		                	 field: "groupid", values:groups
                           },
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
   	                        width: 50
   	                    },
   	                        {
   	                            field: "indicator",
   	                            title: "<span data-translate='Indicator'></span>",
   	      			                            width: 250
   	                        },
   	                        {
   	                            field: "measurement",
   	                            title: "<span data-translate='Measurements'></span>",
   	                            values:p_measurement,
   	                            width: 120
   	                        },
   	                        {
                                field: "groupid",
                                values:groups,
                                title:' ',
                                hidden: true
                            },
   	                        {
   	                            field: "data75",
   	                            format: "{0:n}",
   	                            title: "<span data-translate='Annual plan'></span>",
   	                            width: 200
   	                        },
   	                        {
   	                            title: "<span data-translate='Performance'></span>",
   	                            columns: [{
   	                                field: "execution",
   	                                format: "{0:n}",
   	                                title: "<span data-translate='Increased amount from year'></span>",
   	                                width: 200
   	                            },{
   	                                field:"data"+$scope.sgt.weekid+"",title: ""+$scope.sgt.weekid+"-р долоо хоногийн дүн", format: "{0:n}",  template:"# if (data"+$scope.sgt.weekid+" != 0) { # <span class='tulgaltRed'>#=kendo.format('{0:n}', data"+$scope.sgt.weekid+")#</span> # } else { #<span class='tulgaltRed'> #=kendo.format('{0:n}', data"+$scope.sgt.weekid+")#</span> # }  #",
   	                                width: 200
   	                            },{
   	                                field: "wpercentage",
   	                                title: "<span data-translate='Annual performance'></span>",
   	                                width: 200,
   	                                format: "{0:p}",
   	                                },
   	                            ]
   	                        }],
   		                          editable: true,
   		                       dataBound: function(){
	    	                    	  $(".tulgaltRed").parent().addClass("md-bg-red-100");
	    	    	                },
   		            };
	          
		            
		            if($scope.xonoff){
	    		    	$scope.pWeeklyGrid.toolbar= ["excel", "pdf"];
	    		    	$scope.pWeeklyGrid.editable=true;
	    		    }
	    		    else{
	    		    	 $scope.pWeeklyGrid.toolbar=["excel", "pdf"];
	    		    	 $scope.pWeeklyGrid.editable=false;
	    		    }
	        	
	        	/*init();
	    		
	    	    function init(){
		   	    	 mainService.withdomain('get','/user/service/getWeekDetail/'+$stateParams.id)
		   			.then(function(data){		   	
		   				console.log(data);
		   				
		   				
				            
		   			});			
		   		}*/
	    	    
	    	    
	    		$scope.tooloptions = {

	  					imageBrowser: {
	  	    			transport: {
	  	    				read: {
	  	    					url:"/imagebrowser/read",	        			
	  	    						type: "POST",
	  	    						dataType: "json"
	  	    					},
	  	    				destroy: "/imagebrowser/destroy",
	  	    				create: "/imagebrowser/create",
	  	    				uploadUrl: "/imagebrowser/upload",
	  	    				thumbnailUrl: "/imagebrowser/thumbnail",	  
	  	    				imageUrl:function(e){
	  	    					return "/" + e;
	  	    				} 
	  	    				},        		
	  	    				path:"/uploads/"
	  	    				}  
	  	    		};
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
				                             	data4: {type: "number", defaultValue:0},
				                              	data5: {type: "number", defaultValue:0},
				                             	data6: {type: "number", defaultValue:0},
				                              	data7: {type: "number", defaultValue:0}
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
			                            title: "<span data-translate='Annual plan'></span>",
			                            width: 200
			                        },
			                        {
			                            title: "<span data-translate='Performance'></span>",
			                            columns: [{
			                                field: "execution",
			                                title: "<span data-translate='Increased amount from year'></span>",
			                                width: 200
			                            },{
			                                
			                                columns:[{field:"data3", format: "{0:n}", title:"10", template:"# if (data3 != 0) { # <span class='tulgaltRed'>#=kendo.format('{0:n}', data3)#</span> # } else { # #=kendo.format('{0:n}', data3)# # } #"}],
			                                title: "<span data-translate='Current week amount'></span>",
			                                width: 200
			                            },{
			                                field: "wpercentage",
			                                title: "<span data-translate='Annual performance'></span>",
			                                width: 200
			                                },
			                            ]
			                        }],
				                          editable: true,
				                          dataBound: function(){
			    	                    	  $(".tulgaltRed").parent().addClass("md-bg-red-100");
			    	    	                },
				            };
    		    };
    		    
        	    $scope.loadOnConfirm = function() {    
        	    	if($scope.cher.comment!=null){
        	    		swal({   
        		    		title: "Тайлан илгээх",  
        		    		text: "Та  итгэлтэй байна уу?",  
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
        	    		 sweet.show({
        	    			  title: "Анхаар <small>.</small>!",
        	    			  type: 'warning',
        	    			  text: "Тайлантай холбоотой тайлбар оруулна уу <br/> <span>Бичих хэсэг харагдахгүй бол f5 дарна уу<span>.",
        	    			  html: true
        	    			});
        	    	}
    		    	
    		    };

	        }
    ]);
