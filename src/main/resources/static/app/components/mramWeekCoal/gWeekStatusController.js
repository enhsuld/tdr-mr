angular
    .module('altairApp')
    	.controller("weekstatusGovCtrl",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'lic_type',
    	                          'officers',
    	                          'rep_type',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,lic_type,officers,rep_type) {   

	    	   	$scope.domain="com.peace.users.model.mram.SubLicenses";
	    	    $scope.rrid=0;

    	        var modal = UIkit.modal(".uk-modal");
    	        
    	        $scope.plan = function(id) {
    		    	$state.go('restricted.pages.planForm',{param:0},{ reload: true });    		    	
    		    };
    	
	  		    
	  		   $scope.loadOnView = function(item) {	
	  			    if(item.divisionid==1){
	   					$state.go('restricted.pages.weeklyDataGov',{id:item.id});
	   				}
	   				else{
	   					$state.go('restricted.pages.weeklyDataCoalGov',{id:item.id});
	   				}	  			    	
	  		   };
 

	            $scope.gWeekStatus = {
		                dataSource: {		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/WeeklyRegistrationGov",
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
		                    serverFiltering: true,
		                    serverPaging: true,
		                    serverSorting: true
		                },
		                filterable:true,
		                groupable: true,
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
		                	 {title: "#",template: "<span class='row-number'></span>", width:"60px"},
		                          { field:"lpReg", title: "<span data-translate='Register'></span>"},		                       
		                          { field:"lpName", title: "<span data-translate='Name'></span>"},
		                          { field:"licensenum", title: "<span data-translate='License number'></span>" },   
		                          { field:"weekstr", title: "<span data-translate='Report year'></span>",width:200 },
		                          { field:"weekid", title: "<span data-translate='Report year'></span>" },		              
		                          { 
		                          	 template: kendo.template($("#status").html()), field:"repstatusid", title: "<span data-translate='Status'></span>"                                
		                          },
		                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
		                          { field:"officerid", title: "<span data-translate='Officer name'></span>", values:officers },
		                          { field:"approveddate", title: "<span data-translate='Received date'></span>" },		                         
		                          { 
		                          	 template: kendo.template($("#main").html()),  width: "160px" 	                                
		                           }],
		                           dataBound: function () {
			   	   		                var rows = this.items();
			   	   		                  $(rows).each(function () {
			   	   		                      var index = $(this).index() + 1 
			   	   		                      + ($(".k-grid").data("kendoGrid").dataSource.pageSize() * ($(".k-grid").data("kendoGrid").dataSource.page() - 1));;
			   	   		                      var rowLabel = $(this).find(".row-number");
			   	   		                      $(rowLabel).html(index);
			   	   		                  });
			   	   		  	           },
		                      editable: "popup"
		            };

	        }
    ]);
