angular
    .module('altairApp')
    	.controller("mfileCtrl",
	        function ($scope) {       	
	    		
	        	$scope.domain="com.peace.users.model.mram.LutFiletype.";
	            $scope.pmenuGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutFiletype",
	                            contentType:"application/json; charset=UTF-8",                                    
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
	                             	id: { editable: false,nullable: true},
	                             	fileTypeName: { type: "string", validation: { required: true } },
	                             	fileMaxSize: { type: "number", validation: { required: true } }
	                             }
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: ["create"],
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"fileTypeName", title: "<span data-translate='File name'></span>" },
	                          { field:"fileMaxSize", title: "<span data-translate='File size'></span>" },
	                          { command: ["edit", "destroy"], title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	          
	        }
    );
