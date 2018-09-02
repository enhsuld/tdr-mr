angular
    .module('altairApp')
    	.controller("helpCtrl",['$scope','$rootScope','utils','mainService','sweet',
	        function ($scope,$rootScope,utils,mainService,sweet) {   

	    	   	$scope.domain="com.peace.users.model.mram.HelpData.";
	    	   	
	    	   	$scope.catDomain="com.peace.users.model.mram.HelpCategory.";
	
	    	   	var arr =[{"text":"Тусламж АМГ","value":"0"},{"text":"Тусламж Компани","value":"1"}];	
	    	   	
	            $scope.category = {
		                dataSource: {	
		                	pageSize: 5,
		                	serverPaging: true,
		                    serverSorting: true,
		                    serverFiltering: true,
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/HelpCategory",
		                            contentType:"application/json; charset=UTF-8",                                    
		                            type:"POST"
		                    	},  
		                        update: {
		                            url: "/user/service/editing/update/"+$scope.catDomain+"",
		                            contentType:"application/json; charset=UTF-8",                                    
		                            type:"POST"
		                        },
		                        destroy: {
		                            url: "/user/service/editing/delete/"+$scope.catDomain+"",
		                            contentType:"application/json; charset=UTF-8",                                    
		                            type:"POST"
		                        },
		                        create: {
		                            url: "/user/service/editing/create/"+$scope.catDomain+"",
		                            contentType:"application/json; charset=UTF-8",                                    
		                            type:"POST",
		                            complete: function(e) {
		                    			$(".cat .k-grid").data("kendoGrid").dataSource.read(); 
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
	  	                             	catname: { type: "string", validation: { required: true } },
	  	                                cattype: { type: "number",defaultValue:0},	  	                               
	  	                             }
		                         }
		                     }
		                  
		                },
		                filterable: true,
		                toolbar: ["create"],
		                sortable: {
	                        mode: "multiple",
	                        allowUnsort: true
	                    },
		                pageable: {
		                    refresh: true,
		                    pageSizes: true,
		                    buttonCount: 5
		                },
		                columns: [
		                          
		                          { field:"cattype", title: "<span data-translate='Төрөл'></span>",values:arr },	
		                          { field:"catname", title: "<span data-translate='Нэр'></span>"},		
		                          { command: ["edit", "destroy"], title: "&nbsp;", width: "250px" }
		                ],
		                      editable: "popup"
		            };
	            
	            $scope.license = {
	                dataSource: {	
	                	pageSize: 5,
	                	serverPaging: true,
	                    serverSorting: true,
	                    serverFiltering: true,
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/HelpData",
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
	                    			$(".data .k-grid").data("kendoGrid").dataSource.read(); 
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
	                             id: "id"
	                         }
	                     }
	                  
	                },
	                toolbar: kendo.template($("#add").html()),
	                filterable: true,
	                sortable: {
                        mode: "multiple",
                        allowUnsort: true
                    },
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [	               
	
	                          { field:"helptitle", title: "<span data-translate='Төрөл'></span>"},	
	                          { command: ["edit", "destroy"], title: "&nbsp;", width: "250px" }
	                ],
	                      editable: "popup"
	            };
	            
	    	    var lpreg=9999999;
	    	    
	 	                
	    	    $scope.init =function(){
	    	    	 mainService.withdomain('get','/user/service/resourse/HelpCategory')
			   			.then(function(data){
			   			   console.log(data);  
			   			     //  $scope.selectize_a_data.options=data;
				                
			   		 });
	    	    }
	    	    
	    	    $scope.usersDataSource = {
	                    serverFiltering: true,
	                    transport: {
 	                    	read:  {
 	                            url: "/user/service/resourse/HelpCategory",
 	                            contentType:"application/json; charset=UTF-8",                                    
 	                            type:"get"
 	                    	},
 	                        parameterMap: function(options) {
 	                       	 return JSON.stringify(options);
 	                       }
 	                    },
	                };
		    	
	    	    $scope.help={
	    	    		id:0,
	    	    		content:'',
	    	    		title:''
	    	    }
	    	    var modal = UIkit.modal(".uk-modal");
	    	    $scope.submitForm = function($event) {	                 
	    	    	event.preventDefault();
	    	    	    
                    if ($scope.validator.validate()) {
                    	mainService.withdata('put','/logic/help/save/'+$scope.help.id,$scope.help)
			   			.then(function(data){
			   			    console.log(data);  
				   			modal.hide();
				   			$(".data .k-grid").data("kendoGrid").dataSource.read(); 
				                
			   		 });
                    }             
	             }
	    	    
	        	$scope.tooloptions = {

	                    tools: [
	    					"bold","italic","underline","strikethrough","justifyLeft","justifyCenter","justifyRight","justifyFull",
	    					"insertUnorderedList","insertOrderedList","indent","outdent","createLink","unlink","insertImage","insertFile",
	    					"subscript","superscript","createTable","addRowAbove","addRowBelow","addColumnLeft","addColumnRight","deleteRow",
	    					"deleteColumn","viewHtml","formatting","cleanFormatting","fontName","fontSize","foreColor","backColor"
	                    ],
	                    imageBrowser: {
	                        messages: {
	                         dropFilesHere: "Drop files here"
	                        },
	                        transport: {
	               				read: {
	               					url:"/imagebrowser/read",	        			
	               						type: "GET",
	               						data: {"lpreg":lpreg},
	               						dataType: "json"
	               					},
	               				destroy: {
	               					url:"/imagebrowser/destroy/"+lpreg,	        			
	               						type: "POST",
	               						dataType: "json"
	               					},
	           					uploadUrl:"/imagebrowser/upload/"+lpreg,	
	               				thumbnailUrl: "/imagebrowser/thumbnail",
	               				imageUrl:function(e){
	               					return "/" + e;
	               				}
	           				 },
	                     },
	                     fileBrowser: {
	                         messages: {
	                             dropFilesHere: "Drop files here"
	                         },
	                         transport: {
	                     		 read: {
	                     			 	url:"/imagebrowser/read/file",	        			
	        	                        type: "POST",
	        	                        dataType: "json"
	        	        			},
	                             destroy: {
	                                 url: "/imagebrowser/destroy",
	                                 type: "POST"
	                             },
	                         	 create: "/imagebrowser/create",
	                         	 fileUrl:function(e){
	         	        		    return "/" + e;
	         	        		 }, 
	                             uploadUrl: "/imagebrowser/upload"
	                        }
	                     }
	                };
	        }
    ]);
