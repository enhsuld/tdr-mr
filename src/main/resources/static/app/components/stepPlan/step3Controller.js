angular
    .module('altairApp')
    	.controller("annualPlanStep3CtrlGov",[
     	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'lic_type',
    	                          'p_deposit',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,lic_type,p_deposit) {    
 
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
	  		  var xtype=[{text: "X - тайлан", value: 0}];

	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                    		url: "/user/step/3/3/AnnualRegistrationPlan",
		                    		data: {"custom":"where reporttype=3", sort:[{"field":"id","dir":"desc"}]},
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
		                    pageSize: 8,
		                    serverPaging: true,
		                    serverFiltering: true,
		                    serverSorting: true
		                },
		                groupable: true,
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
		                    buttonCount: 10
		                },
		                columns: [
		                	 {title: "#",template: "<span class='row-number'></span>", width:"60px"},
		                          { field:"lpName", title: "<span data-translate='Company name'></span>" },	
		                          { field:"depositid", title: "АМ-ын төрөл", values:p_deposit },	               
		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		 
		                          { field:"reportyear", title: "<span data-translate='Report year'></span>" },
		                          { 
		                        	 field:"repstatusid", values:[{text:"Хадгалсан",value:"0"},{text:"Засварт буцаасан",value:"2"},{text:"Илгээсэн",value:"7"},{text:"Хүлээлгэн өгсөн",value:"1"}],
		                          	 template: kendo.template($("#status").html()), title: "<span data-translate='Status'></span>"   	                                
		                           },
		                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
		                          { field:"lastmodified", title: "Сүүлд хянасан огноо" },             
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
