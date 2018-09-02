angular
    .module('altairApp')
    	.controller("annualHisCtrl",[
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
    	                          'officers',
	        function ($compile,$q,$scope,$http,$timeout,$rootScope,$state,utils,mainService,sweet,lic_type,app_status,rep_type,sel_data,officers) {   
    	       
        	  console.log($state.current.name);

	            $scope.appstat=app_status;
	            
    	                        	  
   	    	   	$scope.domain="com.peace.users.model.mram.SubLicenses";
	    	    $scope.rrid=0;

	    	    var xtype=[{text: "X-тайлан", value: 0}];
    	        var modal = UIkit.modal(".uk-modal");
    	        
    	        $scope.config = function(item) {
    	        	console.log(item);
    	        	if(item.mv==0){
    	        		$state.go('restricted.pages.planMVConfig',{id:item.id}); 
    	        	}
    	        	else{
    	        		$state.go('restricted.pages.planPVConfig',{id:item.id}); 
    	        	}   		    	
    		    };
    		    
    		    
    	

 	           
    		    $scope.conf = {
                        assignee: {
                            config: {
                                create:false,
                                maxItems: 1,
                                valueField: 'id',
                                labelField: 'id',
                                placeholder: 'Үндсэн тусгай зөвшөөрөл...'
                            },
                            options: sel_data
                        }
                    }
    		    
    		    $scope.chang=function(){
    		    	console.log($scope.main);
    		    	$scope.landname=$scope.main.title;
	            }
 	      /*     $scope.init=function(){
		   	    	 mainService.withdomain('get','/user/service/resourse/ownlicenses')
		   			.then(function(data){
		   				console.log(data);
		   				$scope.newIssue.assignee.options=data;
		   				$scope.selectize_planets_options =data;
		   				
		   				console
		   				$scope.additional = [];
		   			});			
		   		}*/
 	           
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
	  		    
	  		   $scope.loadOnZero = function(item) {	  		    	
	  		    	$state.go('restricted.pages.zeroAnswer',{id:item.id});
	  		    };
	            
	  		    
	  		 /* Тайлан / төлөвлөгөө*/
	  	  	  $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/AnnualRegistrationComHistory",
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
		                     },
		                    pageSize: 8,
		                    serverPaging: true,
		                    serverFiltering: true,
		                    serverSorting: true
		                },
		                groupable: true,
		                filterable: true,
		                sortable: true,
		                language: "English",
		                resizable: true,
		                pageable: {
		                	refresh:true,
		                    buttonCount: 3
		                },
		                columns: [
		                          { field:"licensenum", title: "<span data-translate='License number'></span>"},		                        
		                          { field:"reporttype", title: "<span data-translate='Report / Plan'></span>", values:rep_type },		                        
		                          { field:"lictype",title: "<span data-translate='License type'></span>", values:lic_type  },
		                          { field:"reportyear",title: "<span data-translate='Report year'></span>" },		  		                             
		                          { 
		                          	 template: kendo.template($("#xtype").html()), title: "<span data-translate='xtype'></span>", field:"xtype",values:xtype,  width: "120px" 	                                
		                           },
		                          { 
		                          	 template: kendo.template($("#status").html()), title: "<span data-translate='repstatusid'></span>", field:"repstatusid",values:app_status,  width: "120px" 	                                
		                           },
		                          { field:"submissiondate",title: "<span data-translate='Submitted date'></span>"},
		                          { field:"officerid",title: "<span data-translate='Officer'></span>", values:officers },
		                       /*   { field:"approveddate", title: "Хүлээн авсан огноо" },	*/
		                          { field:"approveddate", title:"<span data-translate='Received date'></span>" },	
		                          { 
		                          	 template: kendo.template($("#main").html()),  width: "120px" 	                                
		                           }],
		                      editable: "popup"
		            };
	        }
    ]);
