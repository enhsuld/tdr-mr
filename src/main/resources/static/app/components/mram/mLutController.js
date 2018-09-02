angular
    .module('altairApp')
    	.controller("lutCtrl",['$scope','mainService','role_data','org_data',
	        function ($scope,mainService,role_data,org_data) {       	
	    		
    			var tool=[];
	    	    var com=[];
	    	    
	    	    if(role_data.length!=0){
    	    	    if(role_data[0].create>0){
	   	    	    	tool.push("create");
	   	    	    }
	   	    	    if(role_data[0].export>0){
	   	    	    	tool.push("excel");
	   	    	    	tool.push("pdf");
	   	    	    }
	   	    	    if(role_data[0].update>0){
	   	    	    	com.push("edit");
	   	    	    }
	   	    	    if(role_data[0].delete>0){
	   	    	    	com.push("destroy");
	   	    	    }
	    	    }
	    	 
	    	    
	    	    
	    	   
	        	$scope.LutReporttype="com.peace.users.model.mram.LutReporttype.";
	            $scope.pREPORTTYPE = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutReporttype",
	                            contentType:"application/json; charset=UTF-8",
	                            type:"POST"
	                            	
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutReporttype+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$("#notificationUpdate").trigger('click');
	                            	$(".pREPORTTYPE .k-grid").data("kendoGrid").dataSource.read(); 
	                    		}
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutReporttype+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$("#notificationDestroy").trigger('click');
	                            	$(".pREPORTTYPE .k-grid").data("kendoGrid").dataSource.read(); 
	                    		}
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutReporttype+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$("#notificationSuccess").trigger('click');
	                            	$(".pREPORTTYPE .k-grid").data("kendoGrid").dataSource.read(); 
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
	                              	reportTypeNameMon: { type: "string", validation: { required: true } },
	                              	reportTypeNameEng: { type: "string", validation: { required: true} }
	                              }
	                          }
	                     },
	                    pageSize: 5,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: tool,
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field: "reportTypeNameMon", title: "<span data-translate='Report name /Mon/'></span>"}, 
	                          { field:"reportTypeNameEng", title: "<span data-translate='Report name /Eng/'></span>" },              
	                         
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	        /*    $scope.division=function(){
	            	 mainService.withdomain('get','/user/service/resourse/publicOrgs')
			   			.then(function(data){
			   				
			   	            var orgs=data;
			   	            
				        
			   			});	
	            };*/
	            
	          	$scope.LutDivision="com.peace.users.model.mram.LutDivision.";
	            $scope.pDIVISION = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutDivision",
	                            contentType:"application/json; charset=UTF-8",    
	                           
	                            type:"POST"
	                            	
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutDivision+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutDivision+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutDivision+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	//$(".pDIVISION .k-grid").data("kendoGrid").dataSource.read(); 
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
	                              	divisionnamemon: { type: "string", validation: { required: true } },
	                              	divisionnameeng: { type: "string", validation: { required: true } },
	                              	lpreg: { type: "number", validation: { required: true } }
	                              }
	                          }
	                     },
	                    pageSize: 5,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: tool,
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
                          	  { field:"lpreg", title: "Байгууллага ", values:org_data}, 
	                          { field:"divisionnamemon", title:"Хэлтсийн нэр /Мон/"}, 
	                          { field:"divisionnameeng", title: "Хэлтсийн нэр /Eng/" },				                          
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	            $scope.forms=function(){
	            	 mainService.withdomain('get','/user/service/resourse/formdata')
			   			.then(function(data){
			   				
			   	            var filetypes=data.LutFiletype;
			   	            var reporttypes=data.LutReporttype;
			   	            
				          	$scope.LutForms="com.peace.users.model.mram.LutForms.";
				            $scope.pFORMS = {
				                dataSource: {
				                   
				                    transport: {
				                    	read:  {
				                            url: "/user/angular/LutForms",
				                            contentType:"application/json; charset=UTF-8",        
				                           
				                            type:"POST",				                            
				                            complete: function(e) {
				                            	$scope.forms();
				                    		}
				                            	
				                        },
				                        update: {
				                            url: "/user/service/editing/update/"+$scope.LutForms+"",
				                            contentType:"application/json; charset=UTF-8",                                    
				                            type:"POST"
				                        },
				                        destroy: {
				                            url: "/user/service/editing/delete/"+$scope.LutForms+"",
				                            contentType:"application/json; charset=UTF-8",                                    
				                            type:"POST"
				                        },
				                        create: {
				                            url: "/user/service/editing/create/"+$scope.LutForms+"",
				                            contentType:"application/json; charset=UTF-8",                                    
				                            type:"POST",
				                            complete: function(e) {
				                            	$(".pFORMS .k-grid").data("kendoGrid").dataSource.read(); 
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
				                              	formnamemon: { type: "string", validation: { required: true } },
				                              	formnameeng: { type: "string", validation: { required: true } },
				                              	fileTypeId: { type: "number", validation: { required: true } },
				                              	reportTypeId: { type: "number", validation: { required: true } },
				                              	divisionid: { type: "number", validation: { required: true} }
				                              }
				                          }
				                     },
				                    pageSize: 5,
				                    serverPaging: true,
				                    serverSorting: true
				                },
				                toolbar: tool,
				                filterable: true,
				                sortable: true,
				                columnMenu:true, 
				                pageable: {
				                    refresh: true,
				                    pageSizes: true,
				                    buttonCount: 5
				                },
				                columns: [
				                          { field: "formnamemon", title: "<span data-translate='Form name /Mon/'></span>"}, 
				                          { field:"formnameeng", title: "<span data-translate='Form name /Eng/'></span>" },              
				                          { field:"divisionid", title: "<span data-translate='Division'></span>" ,values:data.LutDivision},   
				                          { field:"reportTypeId", title: "<span data-translate='Report type'></span>", values:data.LutReporttype },   
				                          { field:"fileTypeId", title: "<span data-translate='File type'></span>", values:data.LutFiletype },   
				                          { command:com, title: "&nbsp;", width: "250px" }],
				                      editable: "popup"
				            };
			   			});	
	            };
	            
	           	$scope.LutFiletype="com.peace.users.model.mram.LutFiletype.";
	            $scope.pFILETYPE = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutFiletype",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutFiletype+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pFILETYPE .k-grid").data("kendoGrid").dataSource.read(); 
	                            	$(".pFORMS .k-grid").data("kendoGrid").dataSource.read(); 
	                            	$scope.forms();
	                    		}
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutFiletype+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pFILETYPE .k-grid").data("kendoGrid").dataSource.read(); 
	                            	$(".pFORMS .k-grid").data("kendoGrid").dataSource.read(); 
	                            	$scope.forms();
	                    		}
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutFiletype+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pFILETYPE .k-grid").data("kendoGrid").dataSource.read(); 
	                            	$(".pFORMS .k-grid").data("kendoGrid").dataSource.read(); 
	                            	$scope.forms();
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
	                toolbar: tool,
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
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	           	$scope.LutProducts="com.peace.users.model.mram.LutProducts.";
	            $scope.pPRODUCTS = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutProducts",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutProducts+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutProducts+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutProducts+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pPRODUCTS .k-grid").data("kendoGrid").dataSource.read(); 
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
	                             	productNameMon: { type: "string", validation: { required: true } },
	                             	productNameEng: { type: "string", validation: { required: true } }
	                             }
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: tool,
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"productNameMon", title: "<span data-translate='Product name /Mon/'></span>" },
	                          { field:"productNameEng", title: "<span data-translate='Product name /Eng/'></span>" },
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	           	$scope.LutMeasurements="com.peace.users.model.mram.LutMeasurements.";
	            $scope.pMEASUREMENTS = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutMeasurements",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutMeasurements+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutMeasurements+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutMeasurements+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pMEASUREMENTS .k-grid").data("kendoGrid").dataSource.read(); 
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
	                             	measNameMon: { type: "string", validation: { required: true } },
	                             	measNameEng: { type: "string", validation: { required: true } }
	                             }
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: tool,
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"measNameMon", title: "Бүтээгдэхүүний нэр /Мон/" },
	                          { field:"measNameEng", title: "Бүтээгдэхүүний нэр /Eng /" },
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	           	$scope.LutAppstatus="com.peace.users.model.mram.LutAppstatus.";
	            $scope.pAPPSTATUS = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutAppstatus",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutAppstatus+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutAppstatus+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutAppstatus+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pAPPSTATUS .k-grid").data("kendoGrid").dataSource.read(); 
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
	                             	statusNameMon: { type: "string", validation: { required: true } },
	                             	statusNameEng: { type: "string", validation: { required: true } }
	                             }
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: tool,
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"statusNameMon", title: " Нэр /Мон/" },
	                          { field:"statusNameEng", title: " Нэр /Eng /" },
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	           	$scope.LutAppsteps="com.peace.users.model.mram.LutAppsteps.";
	            $scope.pAPPSTEPS = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutAppsteps",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutAppsteps+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutAppsteps+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutAppsteps+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pAPPSTEPS .k-grid").data("kendoGrid").dataSource.read(); 
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
	                             	stepsNameMon: { type: "string", validation: { required: true } },
	                             	stepsNameEng: { type: "string", validation: { required: true } }
	                             }
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: tool,
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"stepsNameMon", title: " Үе шатны нэр /Мон/" },
	                          { field:"stepsNameEng", title: " Үе шатны нэр /Eng/" },
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	           	$scope.LutReserveLevel="com.peace.users.model.mram.LutReserveLevel.";
	            $scope.pRESERVE_LEVEL = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutReserveLevel",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutReserveLevel+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutReserveLevel+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutReserveLevel+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pRESERVE_LEVEL .k-grid").data("kendoGrid").dataSource.read(); 
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
	                             	stepsNameMon: { type: "string", validation: { required: true } },
	                             	stepsNameEng: { type: "string", validation: { required: true } }
	                             }
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: tool,
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"levelName", title: "Нөөцийн зэргийн нэр" },
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	           	$scope.LutMineType="com.peace.users.model.mram.LutMineType.";
	            $scope.pMINE_TYPE = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutMineType",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.LutMineType+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.LutMineType+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.LutMineType+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".pMINE_TYPE .k-grid").data("kendoGrid").dataSource.read(); 
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
	                             	mtypeNameMon: { type: "string", validation: { required: true } },
	                             	mtypeNameEng: { type: "string", validation: { required: true } }
	                             }
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },
	                toolbar: tool,
	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"mtypeNameMon", title: "Нөөцийн зэргийн нэр" },
	                          { field:"mtypeNameEng", title: "Нөөцийн зэргийн нэр" },
	                          { command:com, title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
	            
	        }]
    );
