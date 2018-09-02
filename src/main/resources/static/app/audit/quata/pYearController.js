angular
    .module('altairApp')
    	.controller("auditYearCtrl",['$scope','user_data',
	        function ($scope,user_data) {       	
    		
    			$scope.domain="com.netgloo.models.LutAuditYear.";
	            $scope.puserGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/audit/au/list/LutAuditYear",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/audit/au/update/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/audit/au/delete/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	 $("#notificationDestroy").trigger('click');
	                    		}
	                        },
	                        create: {
	                            url: "/audit/au/create/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$("#notificationSuccess").trigger('click');
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
	                             id: "id",
	                             fields: {   
	                             	id: { type: "number", editable: false,nullable: false},                                       	
	                             	audityear: { type: "number", validation: { required: true} },                            
	                            	isactive: { type: "boolean" }
	                             }	                    
	                         }
	                     },
	                    pageSize: 8,
	                    serverPaging: true,
	                    serverSorting: true,
	                    serverFiltering: true
	                },
	                toolbar: ["create"],
	                filterable: true,
	                sortable: true,
	                resizable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"audityear", title: "Огноо"},
	                          { field:"isactive", title: "Идэвхитэй эсэх"},
	                          { command: ["edit", "destroy"], title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
            
	        }
    ]);
