angular
    .module('altairApp')
    	.controller("annualCtrlGovBlackTypeAll",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'lic_type',
    	                          'app_status',
    	                          'rep_type',
    	                          'min_group',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,lic_type,app_status,rep_type,min_group) {   

 	           
 
            	  $scope.loadOnCheck = function(item) {	
  		  		  	if(item.reporttype==3){
  	  		    		if(item.lictype==1){
  		   					$state.go('restricted.pages.GovPlanFormH',{param:item.id, id:item.repstepid});
  		   				}
  		   				else{
  		   					$state.go('restricted.pages.GovPlanFormA',{param:item.id, id:item.repstepid});
  		   				}	  		    		 
  	  		    	}
  	  		    	else{
  	  		    		if(item.lictype==1){  	  		    		
  		   					$state.go('restricted.pages.GovReportFormH',{param:item.id,groupid:item.groupid});
  		   				}
  		   				else{
  		   					$state.go('restricted.pages.GovReportFormA',{param:item.id,groupid:item.groupid});
  		   				}	 
  	  		    	}
  	  		    		  		    	
  	  		    };

	  		    var xtype=[{text: "X - тайлан", value: 0}];
	  		    console.log(min_group);
	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/AnnualRegistrationColor",
		                            data: {"custom":"where reporttype=4 and divisionid=3 and repstepid=1 and repstatusid in (7,2) "},
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
		                	mode:"row"
		                },
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
		                	 {title: "#",template: "<span class='row-number'></span>", width:"60px"},
		                          { field:"lpName", title: "<span data-translate='Company name'></span>" },			                                
		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		    
		                          { field:"reportyear", title: "<span data-translate='Report year'></span>" },
		                          { field:"groupid", title: "АМ-ын ангилал", values:min_group },
		                          { 
		                        	  field:"repstatusid", values:[{text:"Засварт буцаасан",value:"2"},{text:"Илгээсэн",value:"7"},{text:"Хүлээлгэн өгсөн",value:"1"}],template: kendo.template($("#status").html()), title: "<span data-translate='Status'></span>"   	                                
		                           },
		                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
		                          { field:"approveddate", title: "<span data-translate='Received date'></span>" },		                         
		                          { 
		                          	 template: kendo.template($("#main").html()),  width: "120px" 	                                
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
