angular
    .module('altairApp')
    	.controller("menuCtrl",['$scope','p_menu','user_data',
	        function ($scope,p_menu,user_data) {       	
	    		var aj=p_menu;
	    		var init={"text":"ROOT","value":"0"};	    	
				aj.push(init);
	        	$scope.domain="com.peace.users.model.mram.LutMenu.";
	            $scope.pmenuGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutMenu",
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
	                             	namemn: { type: "string", validation: { required: true } },
	                             	nameen: { type: "string", validation: { required: true } },
	                             	stateurl: { type: "string", defaultValue:'#'},
	                                 icon: { type: "string"},
	                                 parentid: { type: "number",defaultValue:0},
	                                 orderid: { type: "number" }
	                             }
	                         }
	                     },
	                    pageSize: 10,
	                    serverFiltering: true,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: ["create"],
	                filterable: {
                        mode: "row"
                    },
	                sortable: true,
	                columnMenu:true, 
	                resizable: true,
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"namemn", title: "Нэр /Mn/" },
	                          { field:"nameen", title: "Нэр /En/" },
	                          { field: "stateurl", title:"URL" },
	                          { field: "icon", title:"IKON"},
	                          { field: "parentid", values: aj, title:"Эцэг цэс"},
	                          { field: "orderid", title:"Дараалал", width: "200px" },
	                          { command: ["edit", "destroy"], title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	          
	         //   alert(p_data);
	          //  $scope.sections =  p_data;
	        }
    ]);
