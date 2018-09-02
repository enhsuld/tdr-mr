angular
    .module('altairApp')
    	.controller("orgPublicCtrl", ['$scope','lptypes','role_data',
	        function ($scope,lptypes,role_data) {  
	    	  $scope.domain="com.peace.users.model.mram.SubLegalpersons.";
	    	
	    	    var tool=[];
	    	    var com=[];
	    	    if(role_data[0].create>0){
	    	    	tool.push("create");
	    	    //	tool.push("save");
	    	    //	tool.push("cancel");
	    	    }
	    	    if(role_data[0].export>0){
	    	    //	tool.push("excel");
	    	    //	tool.push("pdf");
	    	    }
	    	    if(role_data[0].update>0){
	    	    	com.push("edit");
	    	    }
	    	    if(role_data[0].delete>0){
	    	    	com.push("destroy");
	    	    }
	    	    
	    	  $scope.confirmed = [];
	    	  $scope.change = function(i){
	    		  alert($scope.confirmed[i]);
	    		  if($scope.confirmed[i]){ //If it is checked
	    		      // alert('test'+i);
	    		   }
	    	  }
	    	  $scope.isChecked=function(item){
	    		  if(item.confirmed){
	    			  var id=item.id
	    			  $scope.confirmed[id] = 'true';
	    		  }	   
	    		  else{
	    			  console.log("end");
	    			  var id=item.id
	    			  $scope.confirmed[id] = 'false';
	    		  }
	    	  }
	          $scope.pPublicGrid = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/SubLegalpersonsPublic",
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
		                    			 lpReg: { type: "string", validation: { required: true } },
		                    			 lpType: { type: "number", validation: { required: true } },
		                    			 lpName: { type: "string", validation: { required: true} },
		                    			 lpNameL1: { type: "string"},
		                    			 phone: { type: "string", validation: { required: true } },
		                    			 famName: { type: "string", validation: { required: true } },
		                    			 famNameL1: { type: "string", validation: { required: true} },
		                    			 givName: { type: "string"},
		                    			 givNameL1: { type: "string", validation: { required: true } },
		                    			 mobile: { type: "string", validation: { required: true } },
		                    			 email: { type: "string", validation: { required: true} },
		                    			 personId: { type: "number", defaultValue:0},
		                    			 ispublic: { type: "number",defaultValue:1},
		                    			 confirmed: { type: "boolean",defaultValue:true}
		                             }
		                         }
		                     },
		                    pageSize: 8,
		                    serverPaging: true,
		                    serverSorting: true
		                },
		                filterable: true,
		                sortable: true,
		                columnMenu:true, 
		                resizable: true,
		                toolbar: tool,
		                groupable:true,
		                pageable: {
		                    refresh: true,
		                    pageSizes: true,
		                    buttonCount: 5
		                },
		                columns: [
		                          { field:"lpReg", title: "<span data-translate='Reg.number'></span>" , locked: true,lockable: false,width: 150 },
		                          { field: "lpName", title: "<span data-translate='Company name'></span>", locked: true,lockable: false,width: 150},
		                          { field: "famName", title: "<span data-translate='Lastname /Mon/'></span>",width: 150 },
		                          { field: "givName", title: "<span data-translate='Firstname /Mon/'></span>" ,width: 150},
		                          { field: "famNameL1", title: "<span data-translate='Lastname /Eng/'></span>" ,width: 150},
		                          { field: "givNameL1", title: "<span data-translate='Firstname /Mon/'></span>" ,width: 150},
		                          { field: "lpNameL1", title: "<span data-translate='Company name'></span>" ,width: 150},
		                          { field: "phone", title: "<span data-translate='Phone'></span>" ,width: 150},
		                          { field:"lpType", title: "<span data-translate='Legal type'></span>", values:lptypes,width:350},
		                          { field: "mobile", title: "<span data-translate='Mobile'></span>" ,width: 150},
		                          { field: "email", title: "<span data-translate='Email'></span>",width: 150 },
		                          { command:com, title: "&nbsp;", width: "250px" }
		                       /*   { 
		                          	 template: kendo.template($("#update").html()), 	width: 150                                
		                          }*/
	                      ],
	                      editable: "popup"
		            }
	        }]
    )
