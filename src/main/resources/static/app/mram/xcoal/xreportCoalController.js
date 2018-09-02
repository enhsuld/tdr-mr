angular
    .module('altairApp')
    	.controller("xreportCoal",[
    	                          '$scope',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',
    	                          'mineralData',
    	                          '$filter',
	        function ($scope,$rootScope,$state,utils,mainService,sweet,mineralData,$filter) {   
 	  		    
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
	
	  		    $scope.loadOnZero = function(item) {	 
	            	$state.go('restricted.pages.zeroAnswerGov',{param:item.id,reqid:item.reqid,rtype:item.reporttype});
	  		    };
	  		    
	  		    var xtype=[{text: "X - тайлан", value: 0}];

	            $scope.PlanExploration = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                    		url: "/user/angular/AnnualRegistrationXreportMin",
		                    		data: {"custom":"where reporttype=4 and divisionid=2 and xtype=0 and repstatusid=7", sort:[{"field":"submissiondate","dir":"asc"}]},
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
		               // toolbar:kendo.template($("#export").html()),
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
		                    buttonCount: 10
		                },
		                columns: [
		                	 	  {title: "#",template: "<span class='row-number'></span>", width:"60px"},
		                          { field:"lpName", title: "<span data-translate='Company name'></span>" },	
		                          { field:"depositid", title: "АМ-ын төрөл", values:mineralData },	
		                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		   
		                          { field:"reportyear", title: "<span data-translate='Report year'></span>" },
		                          { 
		                          	 template: kendo.template($("#status").html()), title: "<span data-translate='Status'></span>"   	                                
		                           },	  
		                          /* { 
	  		                          	 template: kendo.template($("#division3").html()),  width: "250px" 	                                
	  		                           },*/
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
	            
	            $scope.finddeposit = function(id){
	            	console.log(id);
	            	if ($filter('filter')(mineralData, {value: id}).length > 0){
	            		return $filter('filter')(mineralData, {value: id})[0].text;
	            	}
	            	
	            }
	        }
    ]);
