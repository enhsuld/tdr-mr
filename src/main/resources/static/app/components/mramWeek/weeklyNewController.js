angular
    .module('altairApp')
    	.controller("weeklyNew",['$scope','$rootScope','$state','mainService','sweet','$timeout','user_data',
	        function ($scope,$rootScope,$state,mainService,sweet,$timeout,user_data) {       	


    			init();

	    	    function init(){
		   	    	 mainService.withdomain('get','/user/service/resourse/getDate')
		   			.then(function(data){
		   				$scope.repdate=data[0];
		   				$scope.start=data[0].start;
		   				$scope.end=data[0].end;
		   				$scope.week=data[0].week;
		   			});
		   		}
	    	    
	    	  var reject=[{"text":"Татгалзсан","value":3},{"text":"Засварт буцаах","value":2},{"text":"Зөвшөөрсөн","value":1}];
          	  var divs=[{"text":"Геологи","value":3},{"text":"Нүүрс","value":2},{"text":"Уул","value":1}];
  	  		  var rejectstep=[{"text":"Хүлээн авах хэсэг","value":1},{"text":"Нөөцийн хэсэг","value":2},{"text":"Технологийн хэсэг","value":3},{"text":"Бүтээгдэхүүн борлуулалт","value":4},{"text":"Эдийн засгийн хэсэг","value":5}]
          	  $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/AnnualRegistrationCom",
		                            contentType:"application/json; charset=UTF-8",       
		                            data:{"custom":"where lpReg='"+user_data.lpreg+"' and repstatusid=1 and reporttype=3 and divisionid!=3"},
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
		                          { field:"reporttype", title: "<span data-translate='Report / Plan'></span>" },		                        
		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		    
		                          { field:"reportyear",title: "<span data-translate='Report year'></span>" },		  		                             
		                       /*   { field:"depositid",title: "<span data-translate='Mineral group'></span>", values:mineralData },		  	*/
		                           { 
		                          	 template: kendo.template($("#status").html()), title: "<span data-translate='repstatusid'></span> / <span data-translate='xtype'></span>", field:"repstatusid", width: "170px" 	                                
		                           },
		                          { field:"submissiondate",title: "<span data-translate='Submitted date'></span>"},	  
		                           { 
		                          	 template: kendo.template($("#action").html()),  width: "200px" 	                                
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
  	  		  
  	  		  
  	  		  
  	  		  

	    	   	$scope.loadOnCreate = function(item) {
		    	   	 sweet.show({
	   			  		title:"Үндсэн ТЗ-ийн дугаар: "+item.bundledLicenseNum+"",  
	 		    		text: "Та  "+$scope.start+" - "+$scope.end+" хугацааны  "+$scope.week+"-р долоо хоногийн тайлан үүсгэх үү?",  
		     		        type: 'info',
		     		        showCancelButton: true,
		     		        confirmButtonColor: '#DD6B55',
		     		        confirmButtonText: 'Тийм',
		     		        cancelButtonText: 'Үгүй',
		     		        closeOnConfirm: false,
		     		        closeOnCancel: false,
		  		            showLoaderOnConfirm: true
		  		        }, function(inputvalue) {
		  		        	 if (inputvalue) {
		  		        		 mainService.withdata('put','/user/service/createReport/1/'+item.id,$scope.repdate)
 	 				   			.then(function(data){	 	 				   				
 	 				   				if(data.report=='true'){
	 	 				   				sweet.show('', 'Тайлан амжилттай үүслээ', 'success');
	 	 				   				if(data.minid==1){
	 	 				   					$state.go('restricted.pages.weeklyData',{id:data.wrid});
	 	 				   				}
	 	 				   				else{
	 	 				   					$state.go('restricted.pages.weeklyDataCoal',{id:data.wrid});
	 	 				   				}	 	 				   					
 	 				   				}
 	 				   				else{
 	 				   					sweet.show('Анхаар!', 'Тайлан үүссэн байна...', 'error');
 	 				   				}
	 	 				   				
	 				   			})
		   		            }else{
		   		                sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
		   		            }    		        	
		  		        	 
		  		        });

			    };

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
	                pageable: {
	                	refresh:true,
	                    buttonCount: 3
	                },
	                columns: [
	                          { field:"licenseXB",title: "<span data-translate='License number'></span>"},
	                          { field:"bundledLicenseNum", title: "<span data-translate='Primary license'></span>" },
	                          { field:"addBunLicenseNum", title: "<span data-translate='Secondary license'></span>" },	                        
	                          { field:"areaName", title: "<span data-translate='Area name'></span>" },
	                          { field:"latestChangeDateTime", title: "<span data-translate='Configured date'></span>" },
	                          { 
	                          	 template: kendo.template($("#action").html()),  width: "220px" 	                                
	                           }],
	                      editable: "popup"
	            };
	        }
    ]);
