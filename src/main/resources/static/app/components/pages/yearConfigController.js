angular
    .module('altairApp')
    	.controller("yearConfigController",['$scope','user_data','mainService','$ocLazyLoad','report_types',function ($scope,user_data,mainService,$ocLazyLoad,report_types) {
    		$scope.domain="com.peace.users.model.mram.LutYear.";
    		
    		$scope.selectYear = function(id){
    			mainService.withdomain("GET","/selectFormYear/"+id).then(function(data){
    				if (data == true){
    					$(".k-grid").data("kendoGrid").dataSource.read(); 
    					alert("Амжилттай!");
    				}
    				else{
    					alert("Амжилтгүй");
    				}
    			});
    		}
    		
    		var division=[{"text":"Уул уурхайн үйлдвэрлэл, технологийн хэлтэс","value":1},{"text":"Нүүрсний судалгааны хэлтэс","value":2},{"text":"Геологи, хайгуулын хэлтэс","value":3}]
    		
    		$scope.proleGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutYear",
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
	                             id: "id",
	                             fields: {   
	                            	id: { editable: false,nullable: true},
	                            	value: { type: "number", validation: { required: true } },
	                            	divisionid: { type: "number", validation: { required: true } },
	                            	isactive: { type: "boolean"},
	                            	type: { type: "number", validation: { required: true }},
	                              }
	                         }
	                     },
	                    pageSize: 5,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: ["create"],
	                filterable: false,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                	{ field:"type", title: "Төрөл",values: report_types},
	                          { field:"value", title: "Он" },
	                          { field:"divisionid", title: "Хэлтэс",values: division },
	                          { field:"isactive", title: "Идэвхитэй эсэх" },
	                          
	                          
	                           /*{ 
	                           	 template: "<div class='k-edit-custom' ng-click='selectYear(#:id#)'" +
	                                 "><button class='md-btn'>Сонгох</button>",
	                                 width: "130px" 
	                                 
	                            }*/
	                          { command: ["edit", "destroy"], title: "&nbsp;", width: "250px" },	
	                          ],
	                            editable: "popup"
	            };

    	}]);