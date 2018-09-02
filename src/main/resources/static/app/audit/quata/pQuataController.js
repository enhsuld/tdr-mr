angular
    .module('altairApp')
    	.controller("quataCtrl",['$scope','user_data','p_year','p_cat','mainService',
	        function ($scope,user_data,p_year,p_cat,mainService) {       	
    		
    	    	$scope.selectize_a_data = {
                    options: []
                };
    	    	
    	    	$scope.selectize_b_data = {
                        options: []
                    };

    	    	$scope.selectize_a_data.options=p_year;
    	    	
    	    	$scope.selectize_b_data.options=p_cat;
    	    	
                $scope.selectize_a_config = {
                    plugins: {
                        'disable_options': {
                            disableOptions: ["c1","c2"]
                        }
                    },
                    create: false,
                    maxItems: 1,
                    placeholder: 'Сонголт',
                    optgroupField: 'parent_id',
                    optgroupLabelField: 'title',
                    optgroupValueField: 'ogid',
                    valueField: 'value',
                    labelField: 'text',
                    searchField: 'text'
                };
    			$scope.domain="com.netgloo.models.PreAuditRegistration.";
    			
    			
    			 var $formValidate = $('#quataform');

	            $formValidate
	                .parsley()
	                .on('form:validated',function() {
	                    $scope.$apply();
	                })
	                .on('field:validated',function(parsleyField) {
	                    if($(parsleyField.$element).hasClass('md-input')) {
	                        $scope.$apply();
	                    }
	                });
    			
	            
	            $scope.submitForm=function(){
	         	    mainService.withdata('put','/audit/au/create/quata', $scope.quata)
	  		   			.then(function(data){
	  		   				if(data){
	  			   				sweet.show('Мэдээлэл', 'Амжилттай илгээлээ.', 'success');
	  			   			    $state.go('restricted.pages.claim');	
	  		   				}
	  			   				
	  	   			});	
	            	
	            }
	            
	            $scope.puserGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/audit/au/list/PreAuditRegistration",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/audit/core/update/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                            url: "/audit/core/delete/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	 $("#notificationDestroy").trigger('click');
	                    		}
	                        },
	                        create: {
	                            url: "/audit/core/create/"+$scope.domain+"",
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
	                             	departmentid: { type: "number",  validation: { required: true } },	                             	
	                             	email: { type: "string"},
	                             	positionid: { type: "number"},
	                             	roleid: { type: "number"},
	                             	givenname: { type: "string"},
	                             	familyname: { type: "string"},
	                             	mobile: { type: "string"},	                                          	
	                            	username: { type: "string", validation: { required: true} },
	                            	password: { type: "string", validation: { required: true} },	                            
	                            	isactive: { type: "boolean" }
	                             }	                    
	                         }
	                     },
	                    pageSize: 8,
	                    serverPaging: true,
	                    serverSorting: true,
	                    serverFiltering: true
	                },
	                toolbar: kendo.template($("#add").html()),
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
	                          { field:"audityear", title: "Тайлангийн он", width: 150 },
	                          { field:"orgtype", title: "Ангилал", width: 150 },
	                          { field:"orgname", title: "Байгууллага",width: 250},
	                          { field:"total", title: "Нийт тоо" },     
	                          { field:"stepid", title: "Төлөв"},
	                          { command: ["edit", "destroy"], title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	            };
            
	        }
    ]);
