angular
    .module('altairApp')
    	.controller("orgNewCtrl", [
    	                           '$scope',
    	                           '$rootScope',
    	                           '$state',
    	                           'lptypes',
    	                           'role_data',
    	                           '$timeout',
	        function ($scope,$rootScope,$state,lptypes,role_data,$timeout) { 
    	                        	   
        	   var progressbar = $("#file_upload-progressbar"),
               bar         = progressbar.find('.uk-progress-bar'),
               settings    = {

                   action: '/user/upload/data', // upload url

                   allow : '*.(jpg|jpeg|gif|png|xlsx)', // allow only images

                   loadstart: function() {
                       bar.css("width", "0%").text("0%");
                       progressbar.removeClass("uk-hidden");
                   },

                   progress: function(percent) {
                       percent = Math.ceil(percent);
                       bar.css("width", percent+"%").text(percent+"%");
                   },

                   allcomplete: function(response) {

                       bar.css("width", "100%").text("100%");

                       setTimeout(function(){
                           progressbar.addClass("uk-hidden");
                       }, 250);

                       alert("Upload Completed")
                   }
               };

        	   var select = UIkit.uploadSelect($("#file_upload-select"), settings),
               drop   = UIkit.uploadDrop($("#file_upload-drop"), settings);                  	   
    	                        	   
    	                        	   
    	                        	   
	    	  $scope.domain="com.peace.users.model.mram.SubLegalpersons.";
	    	
	    	  var tool=[];
	    	    var com=[];
	    	/*    if(role_data[0].create>0){
	    	    	//tool.push("create");
	    	    	//tool.push("save");
	    	    //	tool.push("cancel");
	    	    }
	    	    if(role_data[0].export>0){
	    	    	tool.push("excel");
	    	    	tool.push("pdf");
	    	    }
	    	    if(role_data[0].update>0){
	    	    	com.push("edit");
	    	    }
	    	    if(role_data[0].delete>0){
	    	    //	com.push("destroy");
	    	    }*/
	    	    tool.push("excel");
    	    	tool.push("pdf");
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
	    	  console.log(role_data[0]);
	    	  var isa=[{"text":"Тийм","value":true},{"text":"Үгүй","value":false}]
	    	  if(role_data[0].read==1){
	          $scope.pmenuGrid = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/SubLegalpersons",
		                            contentType:"application/json; charset=UTF-8",                                    
		                            type:"POST"
		                        },
		                        update: {
		                            url: "/user/service/editing/update/"+$scope.domain+"",
		                            contentType:"application/json; charset=UTF-8",                                    
		                            type:"POST",
		                            complete: function(e) {
		                            	$(".orggrid .k-grid").data("kendoGrid").dataSource.read(); 
		                    		}
		                        },
		                        destroy: {
		                            url: "/user/service/editing/delete/"+$scope.domain+"",
		                            contentType:"application/json; charset=UTF-8",                                    
		                            type:"POST",
		                            complete: function(e) {
		                            	 $("#notificationDestroy").trigger('click');
		                    		}
		                        },
		                        create: {
		                            url: "/user/service/editing/create/"+$scope.domain+"",
		                            contentType:"application/json; charset=UTF-8",                                    
		                            type:"POST",
		                            complete: function(e) {
		                    			$(".orggrid .k-grid").data("kendoGrid").dataSource.read(); 
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
		                    			 lpReg: { type: "string",editable: false, validation: { required: true } },
		                    			 lpType: { type: "number",editable: false, validation: { required: true } },
		                    			 lpName: { type: "string",editable: false, validation: { required: true} },
		                    			 lpNameL1: { type: "string",editable: false,},
		                    			 phone: { type: "string",editable: false, validation: { required: true } },
		                    			 famName: { type: "string",editable: false, validation: { required: true } },
		                    			 famNameL1: { type: "string", editable: false,validation: { required: true} },
		                    			 givName: { type: "string",editable: false,},
		                    			 givNameL1: { type: "string",editable: false, validation: { required: true } },
		                    			 mobile: { type: "string",editable: false, validation: { required: true } },
		                    			 email: { type: "string"},
		                    			 updateddate: { type: "string",editable: false},
		                    			 personId: { type: "number",editable: false,defaultValue:0},
		                    			 confirmed: { type: "boolean"}
		                             }
		                         }
		                     },
		                    pageSize: 8,
		                    batch: true,
		                    autoSync: true,
		                    serverPaging: true,
		                    serverFiltering: true,
		                    serverSorting: true
		                },
		                filterable: {
                            mode: "row"
                        },
                        excel: {
                            allPages: true
                        },
		                sortable: true,
		                resizable: true,
		                toolbar: tool,
		                pageable: {
		                    refresh: true,
		                    pageSizes: true,
		                    buttonCount: 5
		                },
		                columns: [
								{ field: "lpReg", title: "<span data-translate='Reg.number'></span>" , locked: true,lockable: false,width: 150 },
								{ field: "lpName", title: "<span data-translate='Company name'></span>", locked: true,lockable: false,width: 150},
								{ field: "famName", title: "<span data-translate='Lastname /Mon/'></span>",width: 150 },
								{ field: "givName", title: "<span data-translate='Firstname /Mon/'></span>" ,width: 150},
								{ field: "famNameL1", title: "<span data-translate='Lastname /Eng/'></span>" ,width: 150},
								{ field: "givNameL1", title: "<span data-translate='Firstname /Mon/'></span>" ,width: 150},
								{ field: "lpNameL1", title: "<span data-translate='Company name'></span>" ,width: 150},
								{ field: "phone", title: "<span data-translate='Phone'></span>" ,width: 150},
								{ field: "lpType", title: "<span data-translate='Legal type'></span>", values:lptypes,width:350},
								{ field: "mobile", title: "<span data-translate='Mobile'></span>" ,width: 150},
								{ field: "email", title: "<span data-translate='Email'></span>", locked: true,lockable: false,width: 250},
								{ field: "personId", title: "<span data-translate='Responsible person'></span>",width: 150 },
								{ field: "confirmed", title: "<span data-translate='Active'></span>",filterable:false,locked: true,lockable: false,width: 150},
								{ field: "updateddate", title: "Эрх олгосон огноо", locked: true,lockable: false,width: 150, filterable:false},
	                      ],
	                      editable: true
		            }
	    	  }
	        }]
    )
