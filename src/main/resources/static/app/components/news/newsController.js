angular
    .module('altairApp')
    	.controller("newsCtrl",['$scope','user_data','mainService',function ($scope,user_data,mainService) {
    		$scope.domain = "com.peace.users.model.mram.LutNews";
    		$scope.message_types = [{"text": "Message", "value":"0"},{"text": "Alert", "value":"1"}];
    		$scope.selectize_a_config = {
                create: false,
                maxItems: 1,
                placeholder: 'Төрөл сонгох...',
                valueField: 'value',
                labelField: 'text',
                searchField: 'text'
            };
    		
    		
    		$scope.submitNews = function(){
    			mainService.withdata("POST","/updateNewsData", $scope.selected_news).then(function(response){
    				if (response == true){
    					$('#newsGrid').data('kendoGrid').dataSource.read();
    					UIkit.modal("#newsEditDialog").hide();
    				}
    			});
    		}
    		
    		$scope.proleGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutNews",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.domain+".",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        destroy: {
	                        	url: function (item) {
	    			                return '/deleteNews/' + item.id;
	    			            },
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"GET"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.domain+".",
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
	                             id: "id",
	                             fields: {   
	                            	id: { editable: false,nullable: true, defaultValue:0},
	                              	title: { type: "string", validation: { required: true } },
	                              	status: { type: "number", validation: { required: true } },
	                              	description: { type: "string", validation: { required: true },editor: function(container, options) {
	  	                              $('<textarea md-input textarea-autosize cols="30" rows="4" class="md-input" data-bind="value: ' + options.field + '"></textarea>').appendTo(container);
	  	                          }}                           
	                              }
	                         }
	                     },
	                    pageSize: 8,
	                    serverPaging: true,
	                    serverFiltering: true,
	                    serverSorting: true
	                },
	                toolbar: kendo.template($("#add").html()),
	                filterable: {
                        mode: "row"
                    },
	                sortable: true,
	                resizable: true,
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"title", title: "Гарчиг"},
	                          { field:"status", title: "Төрөл", values:$scope.message_types},
	                          
	                          ],
	                      editable: "popup"
	            };
    		$scope.showNewsDialog = function(id, title, description, status){
    			if (id > 0){
    				$scope.selected_news = {'id':id, 'title':title, 'description':description,'status':status};
    			}
    			else{
    				$scope.selected_news = {};
    			}
    			UIkit.modal("#newsEditDialog").show();
    		}
    		$scope.proleGrid.columns.push({ template: kendo.template($("#update").html()), width: "140px"}); 
    		$scope.proleGrid.columns.push({ template: kendo.template($("#delete").html()), width: "140px"}); 
    	}
    	                          
]);