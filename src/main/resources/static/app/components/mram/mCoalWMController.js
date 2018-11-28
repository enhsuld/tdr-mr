angular
    .module('altairApp')
    	.controller("mCoalCtrl",['$scope','$state',
	        function ($scope,$state) {       	
	    		
    		  var init=[{"text":"7 хоног","value":"1"},{"text":"Сар","value":"2"}];	
	            var status=[{"text":"Шинэ","value":"1"},{"text":"Хүлээн авсан","value":"2"}];	
	            
	            $scope.readMe=function(i){
	            	$state.go('restricted.weekly.coal',{param:0,bund:i});
	            }
	            $scope.subForm = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LnkWeekly",
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
	                             id: "id",
	                             
	                         }
	                     },
	                    pageSize: 10,
	                    serverPaging: true,
	                    serverFiltering: true,
	                    serverSorting: true
	                },

	                filterable: true,
	                sortable: true,
	                columnMenu:true, 
	                pageable: {
                      input: true,
                      refresh:true,
                      numeric: false
                  },
	                columns: [
	                          { field:"lpReg", title: "<span data-translate='Reg.number'></span>"},	                      
	                          { field:"licenseNum", title: "<span data-translate='License number'></span>"},
	                          { field:"formID", title: "<span data-translate='Report type'></span>", values:init  },
	                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
	                          { field:"repStatusID", title: "Төлөв", values:status },
	                          { 
	                           	 template: "<div class='k-edit-custom' ng-click='readMe(#:id#)'   id='#:id#'" +
	                                 "><button class='md-btn'>Дэлгэрэнгүй</button>",
	                                 width: "170px" 
	                                 
	                            }
	                ]
	            };
	          
	         //   alert(p_data);
	          //  $scope.sections =  p_data;
	        }]
    );
