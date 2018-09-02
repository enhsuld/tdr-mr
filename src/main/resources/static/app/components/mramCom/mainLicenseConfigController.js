angular
    .module('altairApp')
    	.controller("MainConfiguration",[
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
               
               
/*  	        var vm =this;
  	        vm.dtInstance ={};
    	    $scope.dtInstance = {}; 
    	    $scope.dtOptions = DTOptionsBuilder.newOptions()             
            .withOption('ajax', {
            	dataSrc: "data",
                url: "/user/data/service/AnnualRegistration",
                type: "POST"
            })          
            .withDataProp('data')
            .withOption('createdRow', function(row, data, dataIndex) {
                $compile(angular.element(row).contents())($scope);
            })
            .withOption('processing', true) //for show progress bar
    		.withOption('serverSide', true)	   
            .withPaginationType('full_numbers')
            .withOption('initComplete', function() {
                    $timeout(function() {

                        var $dt_tableTools = vm.dtInstance.dataTable;
                        $dt_tableTools = vm.dtInstance.DataTable;

                        var tt = new $.fn.dataTable.TableTools( $dt_tableTools, {
                            "sSwfPath": "bower_components/datatables-tabletools/swf/copy_csv_xls_pdf.swf"
                        });
                        $( tt.fnContainer() ).insertBefore( $dt_tableTools.closest('.dt-uikit').find('.dt-uikit-header'));

                        $('body').on('click',function(e) {
                            if($('body').hasClass('DTTT_Print')) {
                                if ( !$(e.target).closest(".DTTT").length && !$(e.target).closest(".uk-table").length) {
                                    var esc = $.Event("keydown", { keyCode: 27 });
                                    $('body').trigger(esc);
                                }
                            }
                        });
                        // md inputs
                        $compile($('.dt-uikit .md-input'))($scope);
                    })
                })
            .withDisplayLength(10)
    	    .withOption('aaSorting',[0,'asc']);
             $scope.dtColumns = [
                DTColumnBuilder.newColumn("licensenum", "Тусгай зөвшөөрлийн дугаар"),
                DTColumnBuilder.newColumn("reporttype", "Тайлан / Төлөвлөгөө"),
                DTColumnBuilder.newColumn("xtype", "Төрөл"),
                DTColumnBuilder.newColumn("lictype", "Тз-ийн төрөл"),
                DTColumnBuilder.newColumn("reportyear", "Тайлант огноо"),
                DTColumnBuilder.newColumn("repstatusid", "Төлөв"),
                DTColumnBuilder.newColumn("submissiondate", "Илгээсэн огноо"),
                DTColumnBuilder.newColumn("officerid", "Хүлээн авсан  мэргэжилтэн"),
                DTColumnBuilder.newColumn("approveddate", "Хүлээн авсан огноо")
            ]*/

       /*      $scope.productsDataSource = { 
        		  dataSource: {	
	                   serverFiltering: true,
		               read:  {
		                     url: "/user/angular/123",
		                     contentType:"application/json; charset=UTF-8",                                    
		                     type:"POST"
		               }
        		  }
             };*/
             $scope.productsDataSource = {                    
                     serverFiltering: true,
                     transport: {
                         read: {
                        	 url: "/user/service/resourse/LutFormindicators",
		                     contentType:"application/json; charset=UTF-8",                                    
		                     type:"GET"
                         }
                     }
             };
               
             $scope.loadOnConfig = function(i){
            	// $state.go('restricted.pages.licAddPro',{id:i});    		    	
             }
             
	            $scope.PlanExploration = {
 	                dataSource: {	
 	                	pageSize: 8,
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
 	                          { field:"licenseXB",title: "<span data-translate='License number'></span>"},
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
