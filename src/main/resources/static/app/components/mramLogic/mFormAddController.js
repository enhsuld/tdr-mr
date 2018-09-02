angular
    .module('altairApp')    	
    	.controller("formAddCtrl",['$scope','mainService','$state','$stateParams','fileUpload','sweet','division','reptype','lictype',
	        function ($scope,mainService,$state,$stateParams,fileUpload,sweet,division,reptype,lictype) {     
	    		var original;
	    		var iscon=[{"text":"Тийм","value":1 },{"text":"Үгүй","value": 0}];
	    		var isfl=[{"text":"Тийм","value":1 },{"text":"Үгүй","value": 0}];
	    		var mineType=[{"text":"Ил","value":1 },{"text":"Далд","value": 2},{"text":"Хосолсон","value": 3}];
	    		var cons=[{"text":"Үгүй","value": 0},{"text":"Химийн бодистой","value":1 },{"text":"Химийн бодисгүй","value": 2},{"text":"Баяжуулахгүй","value": 3}];
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
	    		$('.dropify').dropify();

	        	$scope.domain="com.peace.users.model.mram.LutFormNotes";
	        	
	        	$scope.inpt_options=inpt;
	        	$scope.cons_options=cons;
	        	
	        	
	 			
	            var $formValidate = $('#work_add_form');

	            $formValidate
	                .parsley()
	                .on('form:validated',function() {
	                    $scope.$apply();
	                })
	                .on('field:validated',function(parsleyField) {
	                    if($(parsleyField.$element).hasClass('md-input')) {
	                        $scope.$apply();
	                    }
	                });
	        	
	            init();
	            function init(){
	    			if($stateParams.id!=0){
	    				mainService.withdomain('get', '/user/service/rs/com.peace.users.model.mram.LutFormNotes/id/'+$stateParams.id).
		    			then(function(data){
		    				$scope.formdata=data;
		    				console.log($scope.formdata.mintypeid);
		    			});
	    			}
	    			else{
	    				$scope.formdata = {};
	    				$scope.formdata.id=0;
	    				$scope.formdata.isfile = 0;
	    			}
		    	}
	            
            	var mineType_data = $scope.selectize_mineType_options = mineType;
	 			
	 			$scope.selectize_mineType_config = {
	 	                plugins: {
	 	                    'remove_button': {
	 	                        label     : ''
	 	                    }
	 	                },
	 	                maxItems: 1,
	 	                minItems:1,
	 	                valueField: 'value',
	 	                labelField: 'text',
	 	                searchField: 'text',
	 	                create: false,
	 	                render: {
	 	                    option: function(mineType_data, escape) {
	 	                        return  '<div class="option">' +
	 	                            '<span class="title">' + escape(mineType_data.text) + '</span>' +
	 	                            '</div>';
	 	                    }
	 	                }
	 	            };
	            
	            $scope.isexplo = function(){
	            	if($scope.formdata.isexplosion==1){		            	
		            	$scope.formdata.isconcentrate=0;
		            	$scope.formdata.ismatter=0;
		            }
	            }
	            $scope.isconcent = function(){
	            	if($scope.formdata.isconcentrate==1){
		            	$scope.formdata.isexplosion=0;
		            	$scope.formdata.ismatter=0;
		            }
	            }
	            $scope.ismatt = function(){
	            	if($scope.formdata.ismatter==1){
		            	$scope.formdata.isexplosion=0;
		            	$scope.formdata.isconcentrate=0;
		            }
	            }
	             
	            var formdataDir = new FormData();
	            $scope.getTheFiles = function ($files) {
	                angular.forEach($files, function (value, key) {
	                	formdataDir.append("files", value);
	                	$scope.formdata.removeFile = 1; 
	                });
	            }
	            
	            $scope.submitWorkadd = function(event) {
	       		   event.preventDefault();
	       		   
	       		   if($scope.formdata.isform=='0'){
	       			   var x = {isform: "0"};
	       			   formdataDir.append("files", x);
	      		   }
	       		   if($state.params.id!=0 && $scope.formdata.removeFile !=1){
	       			   var x = {isform: "0"};
	       			   formdataDir.append("files", x);   
	       		   }
	       		   if($scope.formdata.removeFile !=1 && $scope.formdata.isform==1 && $scope.formdata.filepath==null){
	       			   sweet.show('Мэдээлэл', 'Файлаа оруулна уу!!!.', 'error');
	       		   }else{
	       			   
	       			var data = JSON.stringify($scope.formdata, null, 2);               
	                 formdataDir.append("obj", data);	 
	                 fileUpload.uploadFileToUrl('/logic/form/save', formdataDir)
	              	.then(function(data){  
		   				if(data.return){	   					
		   					formdataDir = new FormData();
		   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
		   					$state.go('restricted.pages.forms');	
		   				}
				   				
		   			})
	 	        	}
	            }
	            
	            var isfile_data = $scope.selectize_isfile_options = isfl;
	   			
	   			$scope.selectize_isfile_config = {
	   	                plugins: {
	   	                    'remove_button': {
	   	                        label     : ''
	   	                    }
	   	                },
	   	                maxItems: 1,
	   	                minItems:1,
	   	                valueField: 'value',
	   	                labelField: 'text',
	   	                searchField: 'text',
	   	                create: false,
	   	                render: {
	   	                    option: function(isfile_data, escape) {
	   	                        return  '<div class="option">' +
	   	                            '<span class="title">' + escape(isfile_data.text) + '</span>' +
	   	                            '</div>';
	   	                    }
	   	                }
	   	            };
	   			var reptype_data = $scope.selectize_reptype_options = reptype;
	   			$scope.selectize_reptype_config = {
	   	                plugins: {
	   	                    'remove_button': {
	   	                        label     : ''
	   	                    }
	   	                },
	   	                maxItems: 1,
	   	                minItems:1,
	   	                valueField: 'value',
	   	                labelField: 'text',
	   	                searchField: 'text',
	   	                create: false,
	   	                render: {
	   	                    option: function(reptype_data, escape) {
	   	                        return  '<div class="option">' +
	   	                            '<span class="title">' + escape(reptype_data.text) + '</span>' +
	   	                            '</div>';
	   	                    }
	   	                }
	   	            };
	   			
	   			var division_data = $scope.selectize_division_options = division;
	   			$scope.selectize_division_config = {
	   	                plugins: {
	   	                    'remove_button': {
	   	                        label     : ''
	   	                    }
	   	                },
	   	                maxItems: 1,
	   	                minItems:1,
	   	                valueField: 'value',
	   	                labelField: 'text',
	   	                searchField: 'text',
	   	                create: false,
	   	                render: {
	   	                    option: function(division_data, escape) {
	   	                        return  '<div class="option">' +
	   	                            '<span class="title">' + escape(division_data.text) + '</span>' +
	   	                            '</div>';
	   	                    }
	   	                }
	   	            };
	   			
	   			var lictype_data = $scope.selectize_lictype_options = lictype;
	   			$scope.selectize_lictype_config = {
	   	                plugins: {
	   	                    'remove_button': {
	   	                        label     : ''
	   	                    }
	   	                },
	   	                maxItems: 1,
	   	                minItems:1,
	   	                valueField: 'value',
	   	                labelField: 'text',
	   	                searchField: 'text',
	   	                create: false,
	   	                render: {
	   	                    option: function(lictype_data, escape) {
	   	                        return  '<div class="option">' +
	   	                            '<span class="title">' + escape(lictype_data.text) + '</span>' +
	   	                            '</div>';
	   	                    }
	   	                }
	   	            };
	   			
	        
	            
	            $scope.submitForm = function() {	                 
	                 var data = JSON.stringify($scope.formdata, null, 2);               
	                 formdataDir.append("obj", data);	                   
	                 fileUpload.uploadFileToUrl('/logic/form/save', formdataDir)
	              	.then(function(data){  
	              		console.log(data);
		   				if(data.return){	   					
		   					formdataDir = new FormData();
		   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
		   				}
				   				
		   			});	                
	             }
        
	            $scope.update=function(vdata){	            	
	            	$scope.form = vdata;	
    				console.log($scope.form);	    			    	    	
	    		}
	           
	        }
    ]);
