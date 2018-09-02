angular
    .module('altairApp')
    	.controller("MainAddPro",[
    	                          '$compile',
    	                          '$q',
    	                          '$scope',
    	                          '$http',	
    	                          '$timeout',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          '$resource',
    	                          'DTOptionsBuilder',
    	                          'DTColumnBuilder',
	        function ($compile,$q,$scope,$http,$timeout,$rootScope,$state,utils,mainService,sweet,$resource, DTOptionsBuilder, DTColumnBuilder) {   
    	                        	  
    	                        	  
        	   var $formValidate = $('#form_validation');

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

               $scope.selectize_val_options = [
                   { value: 'press', label: 'Press' },
                   { value: 'net', label: 'Internet' },
                   { value: 'mouth', label: 'Word of mouth' },
                   { value: 'other', label: 'Other...' }
               ];

               $scope.selectize_val_config = {
                   maxItems: 1,
                   valueField: 'value',
                   labelField: 'label',
                   create: false,
                   placeholder: 'Choose...'
               };
               

             $scope.loadOnConfig = function(i){
            	 $state.go('restricted.pages.planForm',{id:i});    		    	
             }
             
	         $scope.AddPro = {
 	                dataSource: {	
 	                	pageSize: 10,
 	                	serverPaging: true,
 	                    serverSorting: true,
 	                    serverFiltering: true,
 	                    transport: {
 	                    	read:  {
 	                            url: "/user/angular/SubLicensesMainConfiguration",
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
 	                     }
 	                  
 	                },
 	                filterable:true,
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
 	                          
 	                          { field:"licenseNum", title: "<span data-translate='License number'></span>"},	 
 	                          { field:"areaNameMon", title: "<span data-translate='Area name'></span>" },	
 	                          { field:"areaSize",title: "<span data-translate='Area size (ha)'></span>"},	
 	                          { field:"locationAimag",title: "<span data-translate='Aimag'></span>"},	
 	                          { field:"locationSoum", title: "<span data-translate='Soum'></span>" },	
 	                          { 
	                          	 template: kendo.template($("#Configuration").html()),  width: "160px" 	                                
		                      }
 	                ],
 	                      editable: "popup"
 	            };
	            
	        }
    ]);
