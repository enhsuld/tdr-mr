angular
    .module('altairApp')
    	.controller("gWeekConfigCtrl",['$scope','$rootScope','$state','mainService','sweet','$timeout','lut_countries','lut_lics',
	        function ($scope,$rootScope,$state,mainService,sweet,$timeout,lut_countries,lut_lics) {       	
            
    		   
    		    
    		    $scope.loadOnCreate = function(i) {
    		    	swal({   title: "Ajax request example",  
    		    		text: "Submit to run ajax request",   
    		    		type: "info",   
    		    		showCancelButton: true,   
    		    		closeOnConfirm: false,  
    		    		showLoaderOnConfirm: true, 
    		    		}, 
    		    		function(){
    		    			 mainService.withdomain('put','/user/service/createReport/1/'+i)
	 				   			.then(function(data){
	 				   				if(data.subdate=='true'){
	 	 				   				sweet.show('Deleted!', 'The file has been deleted.', 'success');
	 				   					$state.go('restricted.weekly',{param:0,bund:i});
	 				   				}
	 				   				else if(data.subdate=='false'){
	 				   					
	 				   				}
	 				   				else{
	 	 				   				sweet.show('', 'update', 'success');
	 				   					$state.go('restricted.weekly',{param:data.subdate,bund:i});
	 				   				}
	 				   				
				   			});	
    		    			
		    			}); 		    
    		    	
    		    };
 
	            $scope.newIssue = {
                    assignee: {
                        config: {
                            create:false,
                            maxItems: 1,
                            valueField: 'id',
                            labelField: 'id',
                            placeholder: 'Үндсэн тусгай зөшөөрөл...'
                        },
                        options: []
                    }
                };

	            var obj = { 
         				id: 0,
        			 main: '',
        			 additional: [],
        			 landname:' ',
        			 isRedemption: '',
        			 priority: ''
			     };
	            
	            $scope.primary = lut_lics;
	            
	            $scope.selectOptions = {
	  				placeholder: "Сонголт...",
	  				cascadeFrom: "categories",
	  				dataTextField: "id",
	  				dataValueField: "id",
	  				valuePrimitive: true,
	  				autoBind: false,
	  				dataSource: lut_lics
  				};
	            
	            $scope.selectCountry = {
		  				placeholder: "Сонголт...",
		  				cascadeFrom: "categories",
		  				dataTextField: "name",
		  				dataValueField: "id",
		  				valuePrimitive: true,
		  				autoBind: false,
		  				dataSource: lut_countries
	  				};
	            
	    
	            $scope.selectize_additional_config = {
	                    plugins: {
	                        'remove_button': {
	                            label     : ''
	                        }
	                    },
	                    maxItems: null,
	                    valueField: 'id',
	                    labelField: 'id',
	                    searchField: 'id',
	                    placeholder: "Нэмэлт тусгай зөвшөөрөл",
	                    create: false,
	                    render: {
	                        option: function(planets_data, escape) {
	                            return  '<div class="option">' +
	                                '<span class="title">' + escape(planets_data.lcnum) + '</span>' +
	                                '</div>';
	                        },
	                        item: function(planets_data, escape) {
	                            return '<div class="item"><a href="' + escape(planets_data.url) + '" target="_blank">' + escape(planets_data.lcnum) + '</a></div>';
	                        }
	                    }
	                };
	            
	            var planets_data = $scope.selectize_additional_options;
	            $scope.rrid=0;
	            
	            $scope.newIssue.assignee.options = lut_lics;
  				$scope.selectize_additional_options = lut_lics;
  				$scope.additional = [];
  				
    		    $scope.loadOnConfirm = function(i) {
    		        sweet.show({
    		        	   title: 'Confirm',
       		            text: 'Delete this file?',
       		            type: 'warning',
       		            showCancelButton: true,
       		            confirmButtonColor: '#DD6B55',
       		            confirmButtonText: 'Yes, delete it!',
       		            closeOnConfirm: false,
       		            closeOnCancel: false,
    		            showLoaderOnConfirm: true
    		        }, function(inputvalue) {
    		        	 if (inputvalue) {
    		        		 mainService.withdomain('delete','/logic/removeBundle/'+i)
	 				   			.then(function(){	 				   				
	 				   				$(".rep .k-grid").data("kendoGrid").dataSource.read(); 	      
	 				   				$(".lic .k-grid").data("kendoGrid").dataSource.read(); 	
		 				   			 mainService.withdomain('get','/user/service/resourse/weeklicenses')
		 				   			.then(function(data){
		 				   				$scope.primary = data;
		 				   				$scope.selectOptions = data;
		 				   			});	
	 				   				sweet.show('Deleted!', 'The file has been deleted.', 'success');
	 				   				//$state.go($state.current, {}, {reload: true});
 				   			});	
     		            }else{
     		                sweet.show('Cancelled', 'Your imaginary file is safe :)', 'error');
     		            }    	
    		        });
    		    };
	           	  
    		   
    		    
	            $scope.chang=function(){
	            	for(var i=0; i < $scope.newIssue.assignee.options.length; i++){
	            		if($scope.newIssue.assignee.options[i].id==$scope.main){
	            			$scope.landname=$scope.newIssue.assignee.options[i].title;
	            		}
	            	}	
	            }
	            
	            $scope.delMe=function(i){
		   	    	 mainService.withdomain('delete','/user/service/removeBundle/'+i)
		   			.then(function(){		   				
		   				$(".rep .k-grid").data("kendoGrid").dataSource.read(); 	      
		   				$(".lic .k-grid").data("kendoGrid").dataSource.read(); 	
		   			});			
		   		}
	            
	            $scope.validate = function(event) {
                    event.preventDefault();
                	console.log($scope.main.id);
                    if ($scope.validator.validate()) {
                         var data = [];
	   	            	 var land="";
	   	            	 if($scope.additional==undefined){
	   	            		// data.push($scope.main);
	   	            	 }
	   	            	 else{
	   	            		 data=$scope.additional;
	   	            	 }
	   	            	 
	   	            	 if($scope.landname==undefined){
	   	            		 land=$scope.main.title;
	   	            	 }
	   	            	 else{
	   	            		 land=$scope.landname;
	   	            	 }
	   	         		 obj = { 
	   	         			 id: $scope.rrid,
	               			 main: $scope.main.id,
	               			 additional: data,
	               			 landname: land,
	               			 isRedemption: $scope.isRedemtion,
	               			 wk: 1,
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

		    		   				mainService.withdomain('get','/user/service/resourse/weeklicenses')
		 				   			.then(function(dt){
		 				   				$scope.primary = dt;
		 				   				$scope.selectOptions = dt;
		 				   			});
		    		   				
	   	    		   				$scope.additional={};
	   	    		   				$scope.isRedemtion={};
	   	    		   				$scope.landname='';
	   	    		   				$scope.priority={};
	   	        				}
	   	        			}
	   	        		});
                        
                    } 
                }
	            
	            $scope.submitForm=function(){
	            	 var data = [];
	            	 var land="";
	            	 if($scope.additional==undefined){
	            		// data.push($scope.main);
	            	 }
	            	 else{
	            		 data=$scope.additional;
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
	            
	        	$scope.domain="com.peace.users.model.mram.SubLicenses.";
	            $scope.license = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/SubLicensesConfig",
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
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },

	                filterable: true,
	                sortable: true,
	                scrollable: true,
	                resizable: true,
	                columnMenu:true, 
	                pageable: {
                        input: true,
                        refresh:true,
                        numeric: false
                    },
	                columns: [
	                          { field:"areaNameMon", title: "<span data-translate='Area name'></span>" },	                      
	                          { field:"licenseNum", title: "<span data-translate='License number'></span>" }
	                ],
	                      editable: "popup"
	            };
	   
	            var init=[{"text":"7 хоног","value":"1"},{"text":"Сар","value":"2"}];	
	            var status=[{"text":"Илгээсэн","value":"1"},{"text":"Хүлээн авсан","value":"2"}];	
          
    
	            $scope.REPBundle = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/RegReportReqWeek",
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
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverSorting: true
	                },

	                filterable: true,
	                sortable: true,
	                resizable: true,
	                columnMenu:true, 
	                scrollable: true,
	                resizable: true,
	                pageable: {
	                	refresh:true,
	                    buttonCount: 3
	                },
	                columns: [
	                          { field:"licenseXB",title: "<span data-translate='License number'></span>"},
	                          { field:"areaName", title: "<span data-translate='Area name'></span>" },
	                          { field:"bundledLicenseNum", title: "<span data-translate='Primary license'></span>" },
	                          { field:"addBunLicenseNum", title: "<span data-translate='Secondary license'></span>" },
	                         
	                          { field:"latestChangeDateTime", title: "<span data-translate='Configured date'></span>" },
	                          { 
	                          	 template: kendo.template($("#action").html()),  width: "60px" 	                                
	                           }],
	                      editable: "popup"
	            };
	        }
    ]);
