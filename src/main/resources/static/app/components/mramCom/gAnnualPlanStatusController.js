angular
    .module('altairApp')
    	.controller("annualStatusCtrl",[
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
    	                          'lic_type',
    	                          'app_status',
    	                          'rep_type',
    	                          'sel_data',
    	                          'user_data',
    	                          'officers',
    	                          '$resource',
    	                          'DTOptionsBuilder',
    	                          'DTColumnBuilder',
    	                          'mineralData',
    	                          '$filter',
	        function ($compile,$q,$scope,$http,$timeout,$rootScope,$state,utils,mainService,sweet,lic_type,app_status,rep_type,sel_data,user_data,officers,$resource, DTOptionsBuilder, DTColumnBuilder,mineralData,$filter) {   
    	         
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
    		    .withOption('initComplete', function() {
                    $timeout(function() {

                        var $dt_tableTools = $scope.dtInstance.dataTable;
                        dt_tabletTools = $scope.dtInstance.DataTable;

                        var tt = new $.fn.dataTable.TableTools( dt_tabletTools, {
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
                .withPaginationType('full_numbers')
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
                ]
   
	            
	            $scope.appstat=app_status;
	            init();
	            
	            $scope.loadOnZero = function(item) {	 
	            	$state.go('restricted.pages.zeroAnswer',{param:item.id,reqid:item.reqid,rtype:item.reporttype});
	  		    	//$state.go('restricted.pages.zeroAnswer',{id:item.id});
	  		    };
	  		    
	            function init(){
	            	  var reject=[{"text":"Татгалзсан","value":3},{"text":"Засварт буцаах","value":2},{"text":"Зөвшөөрсөн","value":1}];
	            	  var divs=[{"text":"Геологи","value":3},{"text":"Нүүрс","value":2},{"text":"Уул","value":1}];
	    	  		  var rejectstep=[{"text":"Хүлээн авах хэсэг","value":1},{"text":"Нөөцийн хэсэг","value":2},{"text":"Технологийн хэсэг","value":3},{"text":"Бүтээгдэхүүн борлуулалт","value":4},{"text":"Эдийн засгийн хэсэг","value":5}]
	            	  $scope.PlanExploration = {
	  		                dataSource: {
	  		                   
	  		                    transport: {
	  		                    	read:  {
	  		                            url: "/user/angular/AnnualRegistrationCom",
	  		                            contentType:"application/json; charset=UTF-8",       
	  		                            data:{"custom":"where lpReg='"+user_data.lpreg+"' and ishidden=0 and repstatusid!=1"},
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
	  		                     },
	  		                    pageSize: 8,
	  		                    serverPaging: true,
	  		                    serverFiltering: true,
	  		                    serverSorting: true
	  		                },

	  		                filterable: true,
	  		                sortable: true,
	  		                language: "English",
	  		                resizable: true,
	  		                pageable: {
	  		                	refresh:true,
	  		                    buttonCount: 3
	  		                },
	  		                columns: [
	  		                		  {title: "#",template: "<span class='row-number'></span>", width:"60px"},
	  		                          { field:"licensenum", title: "<span data-translate='License number'></span>"},	
	  		                          { field:"divisionid", title: "Хэлтэс", values:divs},
	  		                          { field:"reporttype", title: "<span data-translate='Report / Plan'></span>", values:rep_type },		                        
	  		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		    
	  		                          { field:"reportyear",title: "<span data-translate='Report year'></span>" },		  		                             
	  		                       /*   { field:"depositid",title: "<span data-translate='Mineral group'></span>", values:mineralData },		  	*/
	 		                           { 
	 		                          	 template: kendo.template($("#status").html()), title: "<span data-translate='repstatusid'></span> / <span data-translate='xtype'></span>", field:"repstatusid",values:app_status,  width: "170px" 	                                
	 		                           },
	  		                          { field:"submissiondate",title: "<span data-translate='Submitted date'></span>"},	  		                          
	  		                          { field:"repstepid", title: "<span data-translate='Decision step'></span>",values:rejectstep  },
			                          
			                         /* { field:"reject", title: "<span data-translate='Decision'></span>",values:reject  },*/
			                          { 
		  		                          	 template: kendo.template($("#division3").html()),  width: "250px" 	                                
		  		                           },
	  		                          { 
	  		                          	 template: kendo.template($("#main").html()),  width: "120px" 	                                
	  		                           }],
	  		                         dataBound: function () {
				   	   		                var rows = this.items();
				   	   		                  $(rows).each(function () {
				   	   		                      var index = $(this).index() + 1 
				   	   		                      + ($(".k-grid").data("kendoGrid").dataSource.pageSize() * ($(".k-grid").data("kendoGrid").dataSource.page() - 1));;
				   	   		                      var rowLabel = $(this).find(".row-number");
				   	   		                      $(rowLabel).html(index);
				   	   		                  });
				   	   		  	           },
	  		                      editable: "popup"
	  		            };
	    	  		  
	    	  		  	$scope.PlanExploration.columns.push();
	            }
	            $scope.finddeposit = function(id){
	            	if ($filter('filter')(mineralData, {value: id}).length > 0){
	            		return $filter('filter')(mineralData, {value: id})[0].text;
	            	}
	            	
	            }
	          
    	                        	  
   	    	   	$scope.domain="com.peace.users.model.mram.SubLicenses";
	    	    $scope.rrid=0;

	    	    var xtype=[{text: "X-тайлан", value: 0}];
    	        var modal = UIkit.modal(".uk-modal");
    	        
    	        $scope.plan = function(id) {
    		    	$state.go('restricted.pages.planForm',{param:0},{ reload: true });    		    	
    		    };
    		    
    		    
 
    		    $scope.conf = {
                        assignee: {
                            config: {
                                create:false,
                                maxItems: 1,
                                valueField: 'id',
                                labelField: 'id',
                                placeholder: 'Үндсэн тусгай зөшөөрөл...'
                            },
                            options: sel_data
                        }
                    }
 	           $scope.init=function(){
		   	    	 mainService.withdomain('get','/user/service/resourse/ownlicenses')
		   			.then(function(data){
		   				console.log(data);
		   				$scope.newIssue.assignee.options=data;
		   				$scope.selectize_planets_options =data;
		   				
		   				console
		   				$scope.additional = [];
		   			});			
		   		}
 	           
 	            $scope.loadOnConfirm = function(i) {
	  		        sweet.show({
	  		        	title: 'Баталгаажуулалт',
	     		        text: 'Энэ файлыг устгах уу?',
	     		        type: 'warning',
	     		        showCancelButton: true,
	     		        confirmButtonColor: '#DD6B55',
	     		        confirmButtonText: 'Тийм!',
	     		        cancelButtonText: 'Үгүй!',
	     		        closeOnConfirm: false,
	     		        closeOnCancel: false,
	  		            showLoaderOnConfirm: true
	  		        }, function(inputvalue) {
	  		        	 if (inputvalue) {
	  		        		 mainService.withdomain('delete','/logic/removeBundle/'+i)
		 				   			.then(function(){
		 				   				$(".rep .k-grid").data("kendoGrid").dataSource.read(); 	      
		 				   				$(".lic .k-grid").data("kendoGrid").dataSource.read(); 	
		 				   				sweet.show('Анхаар', 'Амжилттай устлаа...', 'success');
					   			});	
	   		            }else{
	   		                sweet.show('Анхаар', 'Үйлдэл цуцлагдлаа...', 'error');
	   		            }    		        	
	  		        	 
	  		        });
	  		    };
	  		    
	  		    $scope.loadOnPlan = function(item,y,i) {
	  		    	var d = new Date();
	  		    	var n = d.getFullYear(); 
	  		    	
	  		    	swal({   title: "Тусгай зөвшөөрлийн дугаар: "+item.bundledLicenseNum,  
	  		    		text: "Та энэ тусгай зөвшөөрлийн "+n+" жилийн тайлан үүсгэх гэж байна. Үргэлжлүүлэх үү?",
	  		    		type: "info",   
	  		    		showCancelButton: true,   
	  		    		closeOnConfirm: false,  
	  		    		showLoaderOnConfirm: true, 
	  		    		confirmButtonText: 'Тийм',
	  		    	    cancelButtonText: 'Үгүй',
	  		    		}, 
	  		    		function(){
	  		    			mainService.withdomain('put','/logic/createPlan/'+y+'/'+i)
 				   			.then(function(data){
 				   				if(data.subdate=='true'){
 	 				   				sweet.show('Анхаар!', 'Төлөвлөгөө амжилттай үүслээ.', 'success');
 	 				   				if(data.lic==1){
 	 				   					$state.go('restricted.pages.planFormH',{param:data.id});
 	 				   				}
 	 				   				else{
 	 				   					$state.go('restricted.pages.planFormA',{param:data.id});
 	 				   				} 				   					
 				   				}
 				   				else{
 				   				sweet.show('Анхаар!', 'Төлөвлөгөө үүссэн байна', 'error');
 				   				}
	 				   				
				   			});	
  		    			
		    			}); 		    
	  		    	
	  		    };
	  		    
	  		    $scope.loadOnReport = function(item,y,i) {
	  		    	var d = new Date();
	  		    	var n = d.getFullYear(); 
	  		    	
	  		    	swal({   title: "Тусгай зөвшөөрлийн дугаар: "+item.bundledLicenseNum,  
	  		    		text: "Та энэ тусгай зөвшөөрлийн "+n+" жилийн тайлан үүсгэх гэж байна. Үргэлжлүүлэх үү?",   
	  		    		type: "info",   
	  		    		showCancelButton: true,   
	  		    		closeOnConfirm: false,  
	  		    		showLoaderOnConfirm: true, 
	  		    	    confirmButtonText: 'Тийм',
	  		    	    cancelButtonText: 'Үгүй',
	  		    		}, 
	  		    		function(){
	  		    			mainService.withdomain('put','/logic/createPlan/'+y+'/'+i)
 				   			.then(function(data){
 				   				if(data.subdate=='true'){
 	 				   				sweet.show('Анхаар!', 'Тайлан амжилттай үүслээ.', 'success');
 	 				   				if(data.lic==1){
	 				   					$state.go('restricted.pages.reportFormH',{param:data.id});
	 				   				}
	 				   				else{
	 				   					$state.go('restricted.pages.reportFormA',{param:data.id});
	 				   				} 
 				   				}
 				   				else{
 				   				sweet.show('Анхаар!', 'Тайлан үүссэн байна', 'error');
 				   				}
	 				   				
				   			});	
  		    			
		    			}); 		    
	  		    	
	  		    };
		        
	  	       $scope.loadOnSend = function(i) {
	  		        sweet.show({
	  		        	title: 'Мэдээлэл',
	     		        text: 'Та энэ тайлан / төлөвлөгөө илгээхдээ итгэлтэй байна уу?',
	     		        type: 'warning',
	     		        showCancelButton: true,
	     		        confirmButtonColor: '#DD6B55',
	     		        confirmButtonText: 'Тийм',
	     		        cancelButtonText: 'Үгүй',
	     		        closeOnConfirm: false,
	     		        closeOnCancel: false,
	  		            showLoaderOnConfirm: true
	  		        }, function(inputvalue) {
	  		        	 if (inputvalue) {
	  		        		 mainService.withdomain('put','/logic/sendform/1/'+i)
		 				   			.then(function(){
		 				   				$(".conf .k-grid").data("kendoGrid").dataSource.read(); 	
		 				   				sweet.show('Анхаар!', 'Амжилттай илгээлээ.', 'success');
					   			});	
	   		            }else{
	   		                sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
	   		            }    		        	
	  		        	 
	  		        });
	  		    };
	  		    
	  		    $scope.loadOnUpdate = function(item) {	 
	  		    	if(item.reporttype==3){
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.planFormH',{param:item.id,reqid:item.reqid});
		   				}
		   				else{
		   					$state.go('restricted.pages.planFormA',{param:item.id,reqid:item.reqid});
		   				}	  		    		 
	  		    	}
	  		    	else{
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.reportFormH',{param:item.id,reqid:item.reqid});
		   				}
		   				else{
		   					$state.go('restricted.pages.reportFormA',{param:item.id,reqid:item.reqid});
		   				}	 
	  		    	} 		    	
	  		    };
	  		    
	  		    
	  		    $scope.selectOptions = {
	  				placeholder: "Сонголт...",
	  				cascadeFrom: "categories",
	  				dataTextField: "id",
	  				dataValueField: "id",
	  				valuePrimitive: true,
	  				autoBind: false,
	  				dataSource: sel_data
  				};
	  		    
/*	            $scope.submitForm=function(){
	            	 var data = [];
	            	 var land="";
	            	 if($scope.additional==undefined){
	            		 data.push($scope.main);
	            	 }
	            	 else{	            		
	            		 data=$scope.additional;
	            		 data.push($scope.main);
	            	 }
	            	 
	            	 if($scope.landname==undefined){
	            		 land="";
	            	 }
	            	 else{
	            		 land=$scope.landname;
	            	 }
	         		 obj = { 
	         			 id: $scope.rrid,
           			 main: $scope.main,
           			 additional: data,
           			 landname: land,
           			 wk:0,
           			 isRedemption: $scope.isRedemtion,
           			 priority: $scope.priority
   			     };

	              	 jQuery.ajax({
	              		url:"/user/service/RegReportReq",
	        			type:'POST',
	        			dataType:'json',
	        			contentType:'application/json',
	        			data:JSON.stringify(obj),	
	        		
	                 	complete: function(r){
	        				var data=r.responseText.trim();
	        				if(data=='true'){ 			
	        					$('.uk-modal-close').trigger('click');
	        					$(".rep .k-grid").data("kendoGrid").dataSource.read(); 	      
	    		   				$(".lic .k-grid").data("kendoGrid").dataSource.read(); 	
	    		   				
	    		   				$scope.init();
	    		   				$scope.additional={};
	    		   				$scope.isRedemtion={};
	    		   				$scope.landname='';
	    		   				$scope.priority={};
	        				}
	        				else{					
	        					//window.location.href = 'http://localhost:8080/user/dd/'+data+'';
	        				}
	        			}
	        		});
	                

	             }*/
 	           
	            $scope.submitForm=function(){
	            	var data = [];
	            	var land="";
	            	if($scope.additional==undefined){
	            		//data.push($scope.main);
	            	}
	            	else{
	            		data=$scope.additional;
//	            		 data.push($scope.main);
	            	}

	            	if($scope.landname==undefined){
	            		land="";
	            	}
	            	else{
	            		land=$scope.landname;
	            	}
	            	obj = {
	            		id: $scope.rrid,
	            		main: $scope.main.id,
	            		additional: data,
	            		landname: land,
	            		wk:0,
	            	isRedemption: $scope.isRedemtion,
	            	priority: $scope.priority
	            	};

	            	jQuery.ajax({
	            	url:"/user/service/RegReportReq",
	            	type:'POST',
	            	dataType:'json',
	            	contentType:'application/json',
	            	data:JSON.stringify(obj),

	            	complete: function(r){
	            	var data=r.responseText.trim();
	            	if(data=='true'){
		            	$('.uk-modal-close').trigger('click');
		            	$(".rep .k-grid").data("kendoGrid").dataSource.read();
		            	$(".lic .k-grid").data("kendoGrid").dataSource.read();
	
		            	$scope.additional={};
		            	$scope.isRedemtion={};
		            	$scope.landname='';
		            	$scope.priority={};
	            	}
	            	}
	            	});


            	}
	            

	            
	            $scope.license = {
	                dataSource: {	
	                	pageSize: 10,
	                	serverPaging: true,
	                    serverSorting: true,
	                    serverFiltering: true,
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/SubLicensesPlanCom",
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
	                             id: "id"
	                         }
	                     }
	                  
	                },
	                filterable: {
                        mode: "row"
                    },
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
	                        /*  { field:"lpReg", title: "Эзэмшигч нэр"},	*/
	                          { field:"areaSize",title: "<span data-translate='Area size (ha)'></span>"},	
	                          { field:"locationAimag",title: "<span data-translate='Aimag'></span>"},	
	                          { field:"locationSoum", title: "<span data-translate='Soum'></span>" },	
	                   /*       { 
	                           	 template: "<div class='k-edit-custom' ng-click='plan(#:id#)'   id='#:id#'" +
	                                 "><button class='md-btn'>Төлөвлөгөө</button>",
	                                 width: "160px" 
	                                 
	                          }*/
	                ],
	                      editable: "popup"
	            };
	            
	        }
    ]);
