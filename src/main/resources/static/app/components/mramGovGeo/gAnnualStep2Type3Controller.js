angular
    .module('altairApp')
    	.controller("annualCtrlGovStep2Type3",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'lic_type',
    	                          'app_status',
    	                          'rep_type',
    	                          'p_group',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,lic_type,app_status,rep_type,p_group) {   

	    	   	$scope.domain="com.peace.users.model.mram.SubLicenses";
	    	    $scope.rrid=0;

    	        var modal = UIkit.modal(".uk-modal");
    	        
    	        $scope.plan = function(id) {
    		    	$state.go('restricted.pages.planForm',{param:0},{ reload: true });    		    	
    		    };
    		    
    		    
    		     $scope.selectize_planets_config = {
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
 	                                '<span class="title">' + escape(planets_data.title) + '</span>' +
 	                                '</div>';
 	                        },
 	                        item: function(planets_data, escape) {
 	                            return '<div class="item"><a href="' + escape(planets_data.url) + '" target="_blank">' + escape(planets_data.title) + '</a></div>';
 	                        }
 	                    }
 	                };
 	            
 	            var planets_data = $scope.selectize_planets_options=[];
 	            
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
 	           
 	           $scope.init=function(){
		   	    	 mainService.withdomain('get','/user/service/resourse/ownlicenses')
		   			.then(function(data){
		   				console.log(data);
		   				$scope.newIssue.assignee.options=data;
		   				$scope.selectize_planets_options =data;
		   				$scope.additional = [];
		   			});			
		   		}
 	           
 
 	          $scope.loadOnCheck = function(item) {	
		  		  	if(item.reporttype==3){
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.GovPlanFormH',{param:item.id, id:item.repstepid});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovPlanFormA',{param:item.id, id:item.repstepid});
		   				}	  		    		 
	  		    	}
	  		    	else{
	  		    		if(item.lictype==1){
	  		    			console.log(item);
		   					$state.go('restricted.pages.GovReportFormH',{param:item.id,groupid:item.groupid});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovReportFormA',{param:item.id,groupid:item.groupid});
		   				}	 
	  		    	}
	  		    		  		    	
	  		    };
/* 	           
 	          $scope.loadOnCheck = function(item) {	
		  		  	if(item.reporttype==3){
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.GovPlanFormH',{param:item.id});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovPlanFormA',{param:item.id});
		   				}	  		    		 
	  		    	}
	  		    	else{
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.GovReportFormH',{param:item.id});
		   				}
		   				else{
		   					$state.go('restricted.pages.GovReportFormA',{param:item.id});
		   				}	 
	  		    	} 		    	
	  		    };*/
 	           
	  	       $scope.loadOnSend = function(i) {
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
	  		        		 mainService.withdomain('put','/logic/sendform/'+i)
		 				   			.then(function(){
		 				   				$(".conf .k-grid").data("kendoGrid").dataSource.read(); 	
		 				   				sweet.show('Deleted!', 'The file has been deleted.', 'success');
					   			});	
	   		            }else{
	   		                sweet.show('Cancelled', 'Your imaginary file is safe :)', 'error');
	   		            }    		        	
	  		        	 
	  		        });
	  		    };

	  		    var xtype=[{text: "X - тайлан", value: 0}];
	  		    
	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/AnnualRegistrationColor",
		                            data: {"custom":"where reporttype=4 and divisionid=3 and groupid=12 and repstepid=8 and repstatusid in (7,2) "},
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
		                    serverFiltering: true,
		                    serverSorting: true
		                },
		                groupable: true,
		                filterable: {
		                	mode:"row"
		                },
		                sortable: {
	                        mode: "multiple",
	                        allowUnsort: true
	                    },
		                scrollable: true,
		                pageable: {
		                	refresh:true,
		                    buttonCount: 3
		                },
		                columns: [
		                	 {title: "#",template: "<span class='row-number'></span>", width:"60px"},
		                          { field:"lpName", title: "<span data-translate='Company name'></span>" },	
		                        /*  { field:"licensenum", title: "<span data-translate='License number'></span>" },		   */                             
		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		    
		                          { field:"reportyear", title: "<span data-translate='Report year'></span>" },
		                          { field:"groupid", title: "АМ-ын ангилал", values:p_group },
		                          { 
		                        	  field:"repstatusid", values:[{text:"Засварт буцаасан",value:"2"},{text:"Илгээсэн",value:"7"},{text:"Хүлээлгэн өгсөн",value:"1"}],template: kendo.template($("#status").html()), title: "<span data-translate='Status'></span>"   	                                
		                           },
		                          { field:"submissiondate", title: "<span data-translate='Submitted date'></span>" },
		                          { field:"approveddate", title: "<span data-translate='Received date'></span>" },		                         
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

	        }
    ]);
