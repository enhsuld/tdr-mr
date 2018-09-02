angular
    .module('altairApp')    	
    	.controller("formCtrl",['$scope','mainService','$state','fileUpload','sweet','division','reptype','lictype',
	        function ($scope,mainService,$state,fileUpload,sweet,division,reptype,lictype) {     
	    		var original;
	    		var iscon=[{"text":"Тийм","value":"1" },{"text":"Үгүй","value": "0"}];
	    		$('.dropify').dropify();
	          	
	    		$scope.add = function(x){
	        		$state.go('restricted.pages.formsform',{id: x});	
	        	}
	    		
	    		var inpt=[
	    		          {"text":"Хүлээн авах хэсэг","value":2 },
	    		          {"text":"Нөөцийн хэсэг","value": 3},
	    		          {"text":"Бичвэр мэдээлэл","value": 20},
	    		          {"text":"Технологийн хэсэг","value": 4},
	    		          {"text":"Бүтээгдэхүүн борлуулалтын хэсэг","value": 5},
	    		          {"text":"Эдийн засгийн хэсэг","value": 6},
	    		          {"text":"Хүлээн авах","value": 8},
	    		          {"text":"Геологийн хэсэг","value": 9},
	    		          {"text":"Зардлын доод хэмжээ","value": 10},
	    		          {"text":"Хавсрах материал","value": 11}
    		          ];
	    		
	        	$scope.domain="com.peace.users.model.mram.LutFormNotes";
	            $scope.proleGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutFormNotes",
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
	                             id: "id",
	                             fields: {   
	                            	 id: { editable: false,nullable: true, defaultValue:0},
	                              	roleNameMon: { type: "roleNameMon", validation: { required: true } },
	                              	roleNameEng: { type: "roleNameEng", validation: { required: true }}                           
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
	                          { field:"note", title: "<span data-translate='Name'></span>",width:500 },
	                          { field:"reporttype", title: "<span data-translate='Report type'></span>",values:reptype,width:200 },
	                          { field:"lictype", title: "<span data-translate='License type'></span>",values:lictype,width:200 },
	                          { field:"inptype", title: "<span data-translate='Located tab'></span>" },
	                          { field:"divisionid", title: "<span data-translate='Division'></span>",values:division,width:200 },	                         
	                          { field:"onoffid", title: "<span data-translate='Is related'></span>",values:iscon },
	                          { field:"inptype", title: "Харяалагдах бүлэг",width:150, values:inpt },
	                     /*     { field:"url", title: "<span data-translate='File name'></span>",width:150 },*/
	                        /*  { field:"fsize", title: "<span data-translate='File size'></span>",width:100 },*/
	                          { 
	                          	 template: kendo.template($("#update").html()),  width: "130px"	                                
	                           },
	                           { 
	                           	 template: "<div class='k-edit-custom' ng-click='delMe(#:id#)'   id='#:id#'" +
	                                 "><button class='md-btn'>Устгах</button>",
	                                 width: "130px" 
	                                 
	                            }],
	                      editable: "popup"
	            };
	            
	            
	            $scope.form = {                  
                    id: 0,
                    fsize: 0,
                    atfile:''
                };
	            
	            var formdata = new FormData();
	            $scope.getTheFiles = function ($files) {
	                angular.forEach($files, function (value, key) {
	                    formdata.append("files", value);
	                });
	            }
	            
	            var modal = UIkit.modal(".uk-modal");
	            $scope.submitForm = function() {	                 
	                 var data = JSON.stringify($scope.form, null, 2);               
	                 formdata.append("obj", data);	                   
	                 fileUpload.uploadFileToUrl('/logic/form/save', formdata)
	              	.then(function(data){  
	              		console.log(data);
		   				if(data.return){	   					
		   					formdata = new FormData();
		   					modal.hide();
		   					$(".k-grid").data("kendoGrid").dataSource.read(); 
		   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
		   				}
				   				
		   			});	                
	             }
        
	            $scope.update=function(vdata){	            	
	            	$scope.form = vdata;	
    				console.log($scope.form);	    			    	    	
	    		}
	            
	            $scope.delMe=function(i){
		   	    	 mainService.withdomain('delete','/user/service/general/delete/'+$scope.domain+'/'+i)
		   			.then(function(){
		   				$(".k-grid").data("kendoGrid").dataSource.read(); 
		   			});			
		   		}
	            

	        }
    ]);
