angular
    .module('altairApp')
    .controller('user_editCtrl', [
        '$rootScope',
        '$scope',
        '$state',
        'sweet',
        'org_data',
        'mainService',
        function ($rootScope,$scope,$state,sweet,org_data,mainService) {

   
            $scope.org_data = org_data;
            $scope.user_role_config = {
                    valueField: 'holderId',
                    labelField: 'reportingPersonName',
                    create: false,
                    maxItems: 1,
                    placeholder: 'Select...'
                };

            $scope.user_role_options = [];
                
            $scope.prompt = function() {
		        sweet.show({
		            title: 'Нэмэх!',
		            text: 'Тайлан илгээх хүний нэр:',
		            type: 'input',
		            showCancelButton: true,
		            closeOnConfirm: false,
		            animation: 'slide-from-top',
		            inputPlaceholder: ' '
		        }, function(inputValue){
		            if (inputValue === false){
		                return false;
		            }
		            if (inputValue === '') {
		                sweet.showInputError('Нэрээ бичнэ үү!');
		                return false;
		            }
		            if (inputValue != '') {
		            	 var data = { 
	                    	 name:inputValue,
	                    	 lpreg: org_data.lpReg
	        		     };
		            	 mainService.withdata('post','/user/service/lpinfo',data)
				   			.then(function(data){
				   				console.log($scope.user_role_options);
				   				$scope.user_role_options.push(data);
				   				console.log($scope.user_role_options);
				   				sweet.show('Амжилттай!', 'You wrote: ' + inputValue, 'success');
			   			});	
		            }
		            
		        });
		    };
		    
            var $formValidate = $('#user_edit_form');
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

            $scope.user_role_options= org_data.subAddLpInfos;
    
            $scope.submitForm = function(){
            	console.log($scope.org_data);
            	 mainService.withdata('put','/user/service/useredit/',$scope.org_data)
	   			.then(function(data){
	   				$state.go('restricted.pages.user_profile');    
	   			});	
            }
            
            // submit button
       /*     $('#user_edit_submit').on('click',function(e) {
                e.preventDefault();
                var data = JSON.stringify($scope.org_data, null, 2),
                    user_name = org_data.lpName;
                console.log(data);
             	jQuery.ajax({
              		url:"/user/service/useredit",
        			type:'POST',
        			dataType:'json',
        			contentType:'application/json',
        			data:data,	
        		
                 	complete: function(r){
        				var data=r.responseText.trim();
        				if(data=='true'){        					
        					$state.go("restricted.pages.user_profile");
        				}
        				else{					
        					//window.location.href = 'http://localhost:8080/user/dd/'+data+'';
        				}
        			}
        		});
             	
                //UIkit.modal.alert('<p>Data for ' + user_name + ':</p><pre>' + data + '</pre>');
            })*/
            
            $scope.domain="com.peace.users.model.mram.SubAddLpInfo.";
            
            $scope.subForm = {
                dataSource: {
                   
                    transport: {
                    	read:  {
                            url: "/user/angular/SubAddLpInfo",
                            contentType:"application/json; charset=UTF-8",                                    
                            type:"POST"
                    	},    
                        update: {
                            url: "/user/service/editing/update/"+$scope.domain+"",
                            contentType:"application/json; charset=UTF-8",                                    
                            type:"POST",
                            complete: function(e) {
                            	 $("#notificationUpdate").trigger('click');
                    		}
                        },
                        destroy: {
                            url: "/user/service/editing/delete/"+$scope.domain+"",
                            contentType:"application/json; charset=UTF-8",                                    
                            type:"POST",
                            complete: function(e) {
                            	 $("#notificationDestroy").trigger('click');
                    		}
                        },
                        create: {
                            url: "/user/service/editing/create/"+$scope.domain+"",
                            contentType:"application/json; charset=UTF-8",                                    
                            type:"POST",
                            complete: function(e) {
                            	$("#notificationSuccess").trigger('click');
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
                             
                         }
                     },
                    pageSize: 10,
                    serverPaging: true,
                    serverSorting: true
                },
                filterable: true,
                sortable: true,
                pageable: {
                    input: true,
                    refresh:true,
                    numeric: false
                },
                columns: [
                          { field:"lpReg", title: "Байгууллагын регистр" },	                      
                          { field:"licenseNum", title: "ТЗ-ийн дугаар" },
                          { field:"formID", title: "Төрөл" },
                          { field:"submissiondate", title: "Илгээсэн огноо" },
                          { field:"repStatusID", title: "Төлөв" },
                ]
            };

        }
    ])
;