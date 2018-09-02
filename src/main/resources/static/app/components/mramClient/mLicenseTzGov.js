angular
    .module('altairApp')
    	.controller("tzs",['$scope','$rootScope','utils','mainService','sweet','user_data','$timeout',
	        function ($scope,$rootScope,utils,mainService,sweet,user_data,$timeout) {   

        	   var progressbar = $("#file_upload-progressbar"),
               bar         = progressbar.find('.uk-progress-bar'),
               settings    = {

                   action: '/user/upload/data', // upload url

                   allow : '*.(jpg|jpeg|gif|png|xlsx)', // allow only images

                   loadstart: function() {
                       bar.css("width", "0%").text("0%");
                       progressbar.removeClass("uk-hidden");
                   },

                   progress: function(percent) {
                       percent = Math.ceil(percent);
                       bar.css("width", percent+"%").text(percent+"%");
                   },

                   allcomplete: function(response) {

                       bar.css("width", "100%").text("100%");

                       setTimeout(function(){
                           progressbar.addClass("uk-hidden");
                       }, 250);

                       alert("Upload Completed")
                   }
               };

        	   var select = UIkit.uploadSelect($("#file_upload-select"), settings),
               drop   = UIkit.uploadDrop($("#file_upload-drop"), settings);                  	   
    	                        	   
    	              
        	   
	            $scope.licensetz = {
	                dataSource: {	
	                	pageSize: 10,
	                	serverPaging: true,
	                    serverSorting: true,
	                    serverFiltering: true,
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/SubLicenses",
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
                    toolbar: ["excel","pdf"],
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                		  {title: "#",template: "<span class='row-number'></span>", width:"60px"},
	                          { field:"licenseNum", title: "<span data-translate='License number'></span>" },	 
	                          { field:"licenseXB", title: "<span data-translate='License number'></span>"},		   
	                          { field:"areaNameMon", title: "<span data-translate='Area name'></span>" },		
	                          { field:"lpName", title: "<span data-translate='Holder name'></span>"},	
	                          { field:"areaSize", title: "<span data-translate='Area size (ha)'></span>" },	
	                          { field:"locationAimag", title: "<span data-translate='Aimag'></span>" },	
	                          { field:"locationSoum", title: "<span data-translate='Soum'></span>" }
	                         
	                ],
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
