angular
    .module('altairApp')
    	.controller("weekhistoryCtrl",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'lic_type',
    	                          'officers',
    	                          'app_status',
    	                          'rep_type',
    	                          'user_data',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,lic_type,officers,app_status,rep_type,user_data) {   

	    	   	$scope.domain="com.peace.users.model.mram.SubLicenses";
	    	    $scope.rrid=0;

    	        var modal = UIkit.modal(".uk-modal");
    	        
    	        $scope.plan = function(id) {
    		    	$state.go('restricted.pages.planForm',{param:0},{ reload: true });    		    	
    		    };
    		    
    		    var reg = user_data.lpreg;
    		 
    		    console.log(reg);
  	           
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
		   					$state.go('restricted.pages.GovReportFormH',{param:item.id});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovReportFormA',{param:item.id});
		   				}	 
	  		    	} 		    	
	  		    };
	  		    
	  		   $scope.loadOnView = function(item) {	
	  			   $state.go('restricted.pages.weeklyData',{id:item.id}); 		    	
	  		   };
 	           
	  	       $scope.loadOnSend = function(i) {
	  		        sweet.show({
	  		        	title: 'Confirm',
	     		        text: 'Delete this file?',
	     		        type: 'warning',
	     		        showCancelButton: true,
	     		        confirmButtonColor: '#DD6B55',
	     		        confirmButtonText: 'Yes, delete it!',
	     		        closeOnConfirm: false,
	     		        closeOnCancel: false,
	  		            showLoaderOnConfirm: true
	  		        }, function(inputvalue) {
	  		        	 if (inputvalue) {
	  		        		 mainService.withdomain('put','/logic/sendform/'+i)
		 				   			.then(function(){
		 				   				$(".conf .k-grid").data("kendoGrid").dataSource.read(); 	
		 				   				sweet.show('Deleted!', 'The file has been deleted.', 'success');
					   			});	
	   		            }else{
	   		                sweet.show('Cancelled', 'Your imaginary file is safe :)', 'error');
	   		            }    		        	
	  		        	 
	  		        });
	  		    };
	  		    
	            $scope.gWeekStatus = {
		                dataSource: {		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/WeeklyRegistration",
		                            data:{"custom":"where lpReg="+reg+" and repstatusid=1"},
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
		                          	 template: kendo.template($("#main").html()),  width: "120px" 	                                
		                           }],
		                      editable: "popup"
		            };


	        }
    ]);
