angular
    .module('altairApp')
    	.controller("annualStatusRejected2Ctrl",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          '$stateParams',
    	                          'p_deposit',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,$stateParams,p_deposit) {   
 
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
	  		    
	  		  $scope.downloadFile = function(){
	  		    	UIkit.modal.prompt('Тайлан татаж авах оныг оруулна уу:', '', function(val){
	  		    		if (!isNaN(val)){
	  		    			$("#downloadJS").attr("href","/logic/exportAnnualRegistration/"+(($stateParams.reporttype == 3) ? 6 : 7)+"/"+val).attr("download","");
	  		    			document.getElementById("downloadJS").click()
	  		    		}
	  		    		else{
	  		    			UIkit.modal.alert('Зөвхөн тоон утга оруулна уу!');
	  		    		}
	  		    	});	    
	  		    }
			    $scope.loadOnZero = function(item) {	  		    	
	  		    	$state.go('restricted.pages.zeroAnswer',{id:item.id});
	  		    };
	  		    
	  		  var xtype=[{text: "X - тайлан", value: 0}];

	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                    		url: "/user/angular/AnnualRegistrationColor",
		                            data: {"custom":"where repstatusid = 2 and rejectstep!=0 and reject!=0 and divisionid = 2 and reporttype = " + $stateParams.reporttype},
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
		                toolbar:kendo.template($("#export").html()),
		                groupable: false,
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
		                          { field:"depositid", title: "АМ-ын төрөл", values:p_deposit },		
		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		   
		                          { field:"reportyear", title: "<span data-translate='Report year'></span>" },
		                          { 
		                          	 template: kendo.template($("#status").html()), title: "<span data-translate='Status'></span>"   	                                
		                           },	                         
		                          { 
		                          	 template: kendo.template($("#main").html()),  width: "120px" 	                                
		                           }],
		                      editable: "popup"
		            };

	        }
    ]);
