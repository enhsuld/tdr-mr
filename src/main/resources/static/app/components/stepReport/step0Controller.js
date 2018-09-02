angular
    .module('altairApp')
    	.controller("annualReportStep0CtrlGov",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'lic_type',
    	                          'p_deposit',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,lic_type,p_deposit) {   

            	$scope.loadOnZero = function(item) {	  		    	
  	  		    	$state.go('restricted.pages.zeroAnswerGov',{param:item.id,reqid:item.reqid,rtype:item.reporttype});
  	  		    };
  	  		    
  	  		    $scope.downloadFile = function(type){
  	  		    	UIkit.modal.prompt('Тайлан татаж авах оныг оруулна уу:', '', function(val){
  	  		    		if (!isNaN(val)){
  	  		    			$("#downloadJS").attr("href","/logic/exportAnnualRegistration/"+type+"/"+val).attr("download","");
  	  		    			document.getElementById("downloadJS").click()
  	  		    		}
	  	  		    	else{
	  	  		    		UIkit.modal.alert('Зөвхөн тоон утга оруулна уу!');
	  		    		}
  	  		    	});	    
  	  		    }
  	  		
	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/step/1/3/AnnualRegistrationXplan",
		                            data: {"custom":"where reporttype=3"},
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
		                filterable: true,
		                sortable: {
	                        mode: "multiple",
	                        allowUnsort: true
	                    },
		                scrollable: true,
		                toolbar:kendo.template($("#export").html()),
		                pageable: {
		                	refresh:true,
		                    buttonCount: 3
		                },
		                excel: {
		                    fileName: "Export.xlsx",
		                    allPages: true
		                },
		                columns: [
		                          { field:"lpName", title: "Аж ахуйн нэгжийн нэр" },	
		                          { field:"depositid", title: "АМ-ын төрөл", values:p_deposit },	                      
		                          { field:"licenseXB", title: "Тусгай зөвшөөрлийн дугаар"},		 
		                          { field:"reportyear", title: "Он" },

		                          { field:"submissiondate", title: "Илгээсэн огноо" },
		                          { field:"approveddate", title: "Хүлээн авсан огноо" },		
		                          { 
			                          	 template: kendo.template($("#main").html()),  width: "120px" 	                                
			                           }
		                          ],
		                      editable: "popup"
		            };

	        }
    ]);
