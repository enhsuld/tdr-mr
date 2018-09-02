angular
    .module('altairApp')
    	.controller("annualCtrl",['$scope','$rootScope','utils','mainService','sweet',
	        function ($scope,$rootScope,utils,mainService,sweet) {   

	    	   	$scope.domain="com.peace.users.model.mram.SubLicenses";
	

    	        var modal = UIkit.modal(".uk-modal");
    	        
    	   /* 	$scope.submitForm = function(){
		    	   	 mainService.withdata('post','/user/service/form/config/',$scope.conf)
			   			.then(function(data){
			   				$scope.conf.monthly=false;
			   				$scope.conf.plan=false;
			   				$scope.conf.redemptionplan=false;
			   				$scope.conf.report=false;
			   				$scope.conf.weekly=false;
			   				$scope.res();
			   				modal.hide();
       				//	sweet.show('Амжилттай!', 'You wrote: ', 'success');	
       				
		   			});	
	    	   	}*/
	    	   	
	            $scope.license = {
	                dataSource: {	
	                	pageSize: 10,
	                	serverPaging: true,
	                    serverSorting: true,
	                    serverFiltering: true,
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/SubLicensesPlan",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                    	},  
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
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
	                     }
	                  
	                },
	                groupable: true,
	                filterable: {
                        mode: "row"
                    },
	                sortable: {
                        mode: "multiple",
                        allowUnsort: true
                    },
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          
	                          { field:"licenseNum", title: "<span data-translate='License number'></span>" },	
	                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		   
	                          { field:"areaNameMon", ttitle: "<span data-translate='Area name'></span>" },	
	                          { field:"lpReg", title: "<span data-translate='Holder name'></span>"},	
	                          { field:"areaSize", title: "<span data-translate='Area size (ha)'></span>" },	
	                          { field:"locationAimag", title: "<span data-translate='Aimag'></span>" },	
	                          { field:"locationSoum", title: "<span data-translate='Soum'></span>" },	
	                          { 
	                          	 template: kendo.template($("#action").html()),  width: "70px" 	                                
	                           }
	                ],
	                      editable: "popup"
	            };
	        }
    ]);
