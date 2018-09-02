angular
    .module('altairApp')
    	.controller("annualNewCtrl",[
    	                          '$compile',
    	                          '$q',
    	                          '$scope',
    	                          '$http',	
    	                          '$timeout',
    	                          '$rootScope',
    	                          '$state',
    	                          'utils',
    	                          'mainService',
    	                          'sweet',    	                          
    	                          '$resource',
    	                          'DTOptionsBuilder',
    	                          'DTColumnBuilder',
    	                          'mineralData',
	        function ($compile,$q,$scope,$http,$timeout,$rootScope,$state,utils,mainService,sweet,$resource, DTOptionsBuilder, DTColumnBuilder,mineralData) {   
    	         

   	    	   	$scope.domain="com.peace.users.model.mram.SubLicenses";
	    	    $scope.rrid=0;

	    	    var xtype=[{text: "X-тайлан", value: 0}];
    	        var modal = UIkit.modal(".uk-modal");
    	        
    	        $scope.plan = function(id) {
    		    	$state.go('restricted.pages.planForm',{param:0},{ reload: true });    		    	
    		    };
    		    


 	            $scope.loadOnConfirm = function(i) {
	  		        sweet.show({
	  		        	title: 'Баталгаажуулалт',
	     		        text: 'Энэ файлыг устгах уу?',
	     		        type: 'warning',
	     		        showCancelButton: true,
	     		        confirmButtonColor: '#DD6B55',
	     		        confirmButtonText: 'Тийм!',
	     		        cancelButtonText: 'Үгүй!',
	     		        closeOnConfirm: false,
	     		        closeOnCancel: false,
	  		            showLoaderOnConfirm: true
	  		        }, function(inputvalue) {
	  		        	 if (inputvalue) {
	  		        		 mainService.withdomain('delete','/logic/removeBundle/'+i)
		 				   			.then(function(){
		 				   				$(".rep .k-grid").data("kendoGrid").dataSource.read(); 	      
		 				   				$(".lic .k-grid").data("kendoGrid").dataSource.read(); 	
		 				   				sweet.show('Анхаар', 'Амжилттай устлаа...', 'success');
					   			});	
	   		            }else{
	   		                sweet.show('Анхаар', 'Үйлдэл цуцлагдлаа...', 'error');
	   		            }    		        	
	  		        	 
	  		        });
	  		    };
	  		    
	  		    $scope.loadOnPlan = function(item,y,i) {
	  		    	var d = new Date();
	  		    	mainService.withdomain("GET","/getSelectedFormYear/1/" + y+'/'+item.divisionId+'/'+item.bundledLicenseNum).then(function(data){
	  		    		if (data != 0){
	  		    			var n = data; 
	  		    			if(item.lnkAnns>0 || item.groupid>0){
	  			  		    	swal({   title: "Тусгай зөвшөөрлийн дугаар: "+item.bundledLicenseNum,  
	  			  		    		text: "Та энэ тусгай зөвшөөрлийн "+n+" жилийн төлөвлөгөө үүсгэх гэж байна. Үргэлжлүүлэх үү?",
	  			  		    		type: "info",   
	  			  		    		showCancelButton: true,   
	  			  		    		closeOnConfirm: false,  
	  			  		    		showLoaderOnConfirm: true, 
	  			  		    		confirmButtonText: 'Тийм',
	  			  		    	    cancelButtonText: 'Үгүй',
	  			  		    		}, 
	  			  		    		function(){
	  			  		    			mainService.withdomain('put','/logic/createPlan/'+n+'/'+y+'/'+i)
	  		 				   			.then(function(data){
	  		 				   				if(data.subdate=='true'){
	  		 	 				   				sweet.show('Анхаар!', 'Төлөвлөгөө амжилттай үүслээ.', 'success');
	  		 	 				   				if(data.lic==1){
	  		 	 				   					if(data.reporttype==3){
	  		 	 				   						$state.go('restricted.pages.planFormH',{param:data.id,reqid:data.reqid});
	  		 	 				   					}
	  		 	 				   					else{
	  		 	 				   						$state.go('restricted.pages.reportFormH',{param:data.id,reqid:data.reqid});	  		 	 				   					
	  		 	 				   					}
	  		 	 				   					
	  		 	 				   				}
	  		 	 				   				else{
		  		 	 				   				if (data.mv == "true"){
	  		 	 				   						$state.go('restricted.pages.planFormH',{param:data.id,reqid:data.reqid});
	  		 	 				   					}
	  		 	 				   					else{
	  		 	 				   						$state.go('restricted.pages.planFormA',{param:data.id,reqid:data.reqid});
	  		 	 				   					}
	  		 	 				   				} 				   					
	  		 				   				}
		  		 				   			else if (data.subdate=='year'){
	  		 				   					sweet.show('Анхаар!', 'Төлөвлөгөөг хүлээн авах хугацааг түр хаасан байна.', 'error');
	  		 				   				}
	  		 				   				else{
	  		 				   				sweet.show('Анхаар!', 'Төлөвлөгөө үүссэн байна', 'error');
	  		 				   				}
	  			 				   				
	  						   			});	
	  		  		    			
	  				    			}); 		    
	  			  		    	}else{
	  			  		    		swal({    
	  			  		    			type: "warning",   
	  			  		    			title: "Анхааруулга !",   
	  			  		    			text: "<div style='margin-top:20px;'>Тусгай зөвшөөрлийн тохиргоо хийгдээгүй байна. <span style='color:#F8BB86'> Тайлангийн тохиргоо цэсрүү орж хиинэ үү<span>.<div>",  
	  			  		    			html: true
	  			    				});
	  			  		    	}
	  		    		}
	  		    		else{
	  		    			mainService.withdomain("GET","/getSelectedFormYear/0/" + y+'/'+item.divisionId+'/'+item.bundledLicenseNum).then(function(dataN){
	  		    				 mainService.withdomain('get','/user/service/rs/com.peace.users.model.mram.SubLicenses/licenseNum/'+item.bundledLicenseNum)
	 				   			.then(function(dataL){
	 				   				if(y==3 && dataL.lplan==true || dataL.ftime==true){
	 				   					var n = dataN; 
	 			  		    			if(item.lnkAnns>0 || item.groupid>0){
	 			  			  		    	swal({   title: "Тусгай зөвшөөрлийн дугаар: "+item.bundledLicenseNum,  
	 			  			  		    		text: "Та энэ тусгай зөвшөөрлийн "+n+" жилийн төлөвлөгөө үүсгэх гэж байна. Үргэлжлүүлэх үү?",
	 			  			  		    		type: "info",   
	 			  			  		    		showCancelButton: true,   
	 			  			  		    		closeOnConfirm: false,  
	 			  			  		    		showLoaderOnConfirm: true, 
	 			  			  		    		confirmButtonText: 'Тийм',
	 			  			  		    	    cancelButtonText: 'Үгүй',
	 			  			  		    		}, 
	 			  			  		    		function(){
	 			  			  		    			mainService.withdomain('put','/logic/createPlan/'+n+'/'+y+'/'+i)
	 			  		 				   			.then(function(data){
	 			  		 				   				if(data.subdate=='true'){
	 			  		 	 				   				sweet.show('Анхаар!', 'Төлөвлөгөө амжилттай үүслээ.', 'success');
	 			  		 	 				   				if(data.lic==1){
	 			  		 	 				   					
	 			  		 	 				   					$state.go('restricted.pages.planFormH',{param:data.id,reqid:data.reqid});
	 			  		 	 				   				}
	 			  		 	 				   				else{
	 				  		 	 				   				if (data.mv == "true"){
	 			  		 	 				   						$state.go('restricted.pages.planFormH',{param:data.id,reqid:data.reqid});
	 			  		 	 				   					}
	 			  		 	 				   					else{
	 			  		 	 				   						$state.go('restricted.pages.planFormA',{param:data.id,reqid:data.reqid});
	 			  		 	 				   					}
	 			  		 	 				   				} 				   					
	 			  		 				   				}
	 				  		 				   			else if (data.subdate=='year'){
	 			  		 				   					sweet.show('Анхаар!', 'Төлөвлөгөөг хүлээн авах хугацааг түр хаасан байна.', 'error');
	 			  		 				   				}
	 			  		 				   				else{
	 			  		 				   				sweet.show('Анхаар!', 'Төлөвлөгөө үүссэн байна', 'error');
	 			  		 				   				}
	 			  			 				   				
	 			  						   			});	
	 			  		  		    			
	 			  				    			}); 		    
	 			  			  		    	}else{
	 			  			  		    		swal({    
	 			  			  		    			type: "warning",   
	 			  			  		    			title: "Анхааруулга !",   
	 			  			  		    			text: "<div style='margin-top:20px;'>Тусгай зөвшөөрлийн тохиргоо хийгдээгүй байна. <span style='color:#F8BB86'> Тайлангийн тохиргоо цэсрүү орж хиинэ үү<span>.<div>",  
	 			  			  		    			html: true
	 			  			    				});
	 			  			  		    	}
	 				   				}	 				   			
	 				   				else{
	 				   					swal({    
	 				  		    			type: "warning",   
	 				  		    			title: "Анхааруулга !",   
	 				  		    			text: "<div style='margin-top:20px;'>Төлөвлөгөөг хүлээн авах хугацааг түр хаасан байна.<div>",  
	 				  		    			html: true
	 				    				});
	 				   				}
	 				   			});
	  		    			});	  		    	
	  		    		}
	  		    	});
	  		    	
	  		    };
	  		    
	  		    $scope.loadOnReport = function(item,y,i) {
	  		    	var d = new Date();
	  		    	mainService.withdomain("GET","/getSelectedFormYear/1/"+y+'/'+item.divisionId+'/'+item.bundledLicenseNum).then(function(data){
	  		    		if (data != 0){
	  		    			var n = data 
	  		  		    	if(item.lnkAnns>0 || item.lnkPvs>0 || item.groupid>0){
	  		  		    		swal({   title: "Тусгай зөвшөөрлийн дугаар: "+item.bundledLicenseNum,  
	  			  		    		text: "Та энэ тусгай зөвшөөрлийн "+n+" жилийн тайлан үүсгэх гэж байна. Үргэлжлүүлэх үү?",   
	  			  		    		type: "info",   
	  			  		    		showCancelButton: true,   
	  			  		    		closeOnConfirm: false,  
	  			  		    		showLoaderOnConfirm: true, 
	  			  		    	    confirmButtonText: 'Тийм',
	  			  		    	    cancelButtonText: 'Үгүй',
	  			  		    		}, 
	  			  		    		function(){
	  			  		    			mainService.withdomain('put','/logic/createPlan/'+n+'/'+y+'/'+i)
	  		 				   			.then(function(data){
	  		 				   				
	  		 				   				if(data.subdate=='true'){
	  		 				   					console.log(data);
	  		 	 				   				sweet.show('Анхаар!', 'Тайлан амжилттай үүслээ.', 'success');
	  		 	 				   				if(data.lic==1){
	  			 				   					$state.go('restricted.pages.reportFormH',{param:data.id,reqid:data.reqid});
	  			 				   				}
	  			 				   				else{
	  			 				   					$state.go('restricted.pages.reportFormA',{param:data.id,reqid:data.reqid});
	  			 				   				} 
	  		 				   				}
	  		 				   				else if (data.subdate=='year'){
	  		 				   					sweet.show('Анхаар!', 'Тайланг хүлээн авах хугацааг түр хаасан байна.', 'error');
	  		 				   				}
	  		 				   				else{
	  		 				   				sweet.show('Анхаар!', 'Тайлан үүссэн байна', 'error');
	  		 				   				}
	  			 				   				
	  						   			});	
	  		  		    			
	  				    			}); 
	  		  		    	}
	  		  		    	else{
	  		  		    		swal({    
	  		  		    			type: "warning",   
	  		  		    			title: "Анхааруулга !",   
	  		  		    			text: "<div style='margin-top:20px;'>Тусгай зөвшөөрлийн тохиргоо хийгдээгүй байна. <span style='color:#F8BB86'> Тайлангийн тохиргоо цэсрүү орж хиинэ үү<span>.<div>",  
	  		  		    			html: true
	  		    				});
	  		  		    	}
	  		    		}
	  		    		else{
	  		    			swal({    
		  		    			type: "warning",   
		  		    			title: "Анхааруулга !",   
		  		    			text: "<div style='margin-top:20px;'>Тайланг хүлээн авах хугацааг түр хаасан байна.<div>",  
		  		    			html: true
		    				});
	  		    		}
	  		    	});
	  		    	
	  		    };
		        
	  		    $scope.XloadOnReport = function(item,y,i) {
	  		    	var d = new Date();
	  		    	mainService.withdomain("GET","/getSelectedFormYear/1/"+y+'/'+item.divisionId+'/'+item.bundledLicenseNum).then(function(data){
	  		    		if (data != 0){
	  		    			var n = data 
	  		  		    	if(item.lnkAnns>0 || item.lnkPvs>0 || item.groupid>0){
	  		  		    		swal({   title: "Тусгай зөвшөөрлийн дугаар: "+item.bundledLicenseNum,  
	  			  		    		text: "Та энэ тусгай зөвшөөрлийн "+n+" жилийн x-тайлан үүсгэх гэж байна. Үргэлжлүүлэх үү?",   
	  			  		    		type: "info",   
	  			  		    		showCancelButton: true,   
	  			  		    		closeOnConfirm: false,  
	  			  		    		showLoaderOnConfirm: true, 
	  			  		    	    confirmButtonText: 'Тийм',
	  			  		    	    cancelButtonText: 'Үгүй',
	  			  		    		}, 
	  			  		    		function(){
	  			  		    			mainService.withdomain('put','/logic/createXplanXreport/'+n+'/'+y+'/'+i)
	  		 				   			.then(function(data){
	  		 				   				console.log(data);
	  		 				   				if(data.subdate=='true'){
	  		 				   					console.log(data);
	  		 	 				   				sweet.show('Анхаар!', 'X-Тайлан амжилттай үүслээ.', 'success');
	  		 	 				   				if(data.lic==1){
	  		 	 				   					$state.go('restricted.pages.zeroAnswer',{param:data.id,reqid:item.id,rtype:item.reporttype});
	  			 				   				}
	  			 				   				else{
	  			 				   					$state.go('restricted.pages.zeroAnswer',{param:data.id,reqid:item.id,rtype:item.reporttype});
	  			 				   				} 
	  		 				   				}
	  		 				   				else if (data.subdate=='year'){
	  		 				   					sweet.show('Анхаар!', 'Тайланг хүлээн авах хугацааг түр хаасан байна.', 'error');
	  		 				   				}
	  		 				   				else{
	  		 				   				sweet.show('Анхаар!', 'Тайлан үүссэн байна', 'error');
	  		 				   				}
	  			 				   				
	  						   			});	
	  		  		    			
	  				    			}); 
	  		  		    	}
	  		  		    	else{
	  		  		    		swal({    
	  		  		    			type: "warning",   
	  		  		    			title: "Анхааруулга !",   
	  		  		    			text: "<div style='margin-top:20px;'>Тусгай зөвшөөрлийн тохиргоо хийгдээгүй байна. <span style='color:#F8BB86'> Тайлангийн тохиргоо цэсрүү орж хиинэ үү<span>.<div>",  
	  		  		    			html: true
	  		    				});
	  		  		    	}
	  		    		}
	  		    		else{
	  		    			swal({    
		  		    			type: "warning",   
		  		    			title: "Анхааруулга !",   
		  		    			text: "<div style='margin-top:20px;'>Тайланг хүлээн авах хугацааг түр хаасан байна.<div>",  
		  		    			html: true
		    				});
	  		    		}
	  		    	});
	  		    	
	  		    };
	  		    
			    $scope.XloadOnPlan = function(item,y,i) {
			    	console.log(item);
	  		    	var d = new Date();
	  		    	mainService.withdomain("GET","/getSelectedFormYear/1/" + y+'/'+item.divisionId+'/'+item.bundledLicenseNum).then(function(data){
	  		    		if (data != 0){
	  		    			var n = data; 
	  		    			if(item.lnkAnns>0 || item.groupid>0){
	  			  		    	swal({   title: "Тусгай зөвшөөрлийн дугаар: "+item.bundledLicenseNum,  
	  			  		    		text: "Та энэ тусгай зөвшөөрлийн "+n+" жилийн x-төлөвлөгөө үүсгэх гэж байна. Үргэлжлүүлэх үү?",
	  			  		    		type: "info",   
	  			  		    		showCancelButton: true,   
	  			  		    		closeOnConfirm: false,  
	  			  		    		showLoaderOnConfirm: true, 
	  			  		    		confirmButtonText: 'Тийм',
	  			  		    	    cancelButtonText: 'Үгүй',
	  			  		    		}, 
	  			  		    		function(){
	  			  		    			mainService.withdomain('put','/logic/createXplanXreport/'+n+'/'+y+'/'+i)
	  		 				   			.then(function(data){
	  		 				   				if(data.subdate=='true'){
	  		 	 				   				sweet.show('Анхаар!', 'X-Төлөвлөгөө амжилттай үүслээ.', 'success');
	  		 	 				   				if(data.lic==1){
	  		 	 				   					if(data.reporttype==3){
	  		 	 				   						$state.go('restricted.pages.zeroAnswer',{param:data.id,reqid:item.id,rtype:item.reporttype});
	  		 	 				   					}
	  		 	 				   					else{
	  		 	 				   						$state.go('restricted.pages.zeroAnswer',{param:data.id,reqid:item.id,rtype:item.reporttype});				   					
	  		 	 				   					}
	  		 	 				   					
	  		 	 				   				}
	  		 	 				   				else{
		  		 	 				   				if (data.mv == "true"){
		  		 	 				   					$state.go('restricted.pages.zeroAnswer',{param:data.id,reqid:item.id,rtype:item.reporttype});
	  		 	 				   					}
	  		 	 				   					else{
	  		 	 				   						$state.go('restricted.pages.zeroAnswer',{param:data.id,reqid:item.id,rtype:item.reporttype});
	  		 	 				   					}
	  		 	 				   				} 				   					
	  		 				   				}
		  		 				   			else if (data.subdate=='year'){
	  		 				   					sweet.show('Анхаар!', 'Төлөвлөгөөг хүлээн авах хугацааг түр хаасан байна.', 'error');
	  		 				   				}
	  		 				   				else{
	  		 				   				sweet.show('Анхаар!', 'Төлөвлөгөө үүссэн байна', 'error');
	  		 				   				}
	  			 				   				
	  						   			});	
	  		  		    			
	  				    			}); 		    
	  			  		    	}else{
	  			  		    		swal({    
	  			  		    			type: "warning",   
	  			  		    			title: "Анхааруулга !",   
	  			  		    			text: "<div style='margin-top:20px;'>Тусгай зөвшөөрлийн тохиргоо хийгдээгүй байна. <span style='color:#F8BB86'> Тайлангийн тохиргоо цэсрүү орж хиинэ үү<span>.<div>",  
	  			  		    			html: true
	  			    				});
	  			  		    	}
	  		    		}
	  		    		else{
	  		    			mainService.withdomain("GET","/getSelectedFormYear/0/" + y+'/'+item.divisionId+'/'+item.bundledLicenseNum).then(function(dataN){
	  		    				 mainService.withdomain('get','/user/service/rs/com.peace.users.model.mram.SubLicenses/licenseNum/'+item.bundledLicenseNum)
	 				   			.then(function(dataL){
	 				   				if(y==3 && dataL.lplan==true || dataL.ftime==true){
	 				   					var n = dataN; 
	 			  		    			if(item.lnkAnns>0 || item.groupid>0){
	 			  			  		    	swal({   title: "Тусгай зөвшөөрлийн дугаар: "+item.bundledLicenseNum,  
	 			  			  		    		text: "Та энэ тусгай зөвшөөрлийн "+n+" жилийн x-төлөвлөгөө үүсгэх гэж байна. Үргэлжлүүлэх үү?",
	 			  			  		    		type: "info",   
	 			  			  		    		showCancelButton: true,   
	 			  			  		    		closeOnConfirm: false,  
	 			  			  		    		showLoaderOnConfirm: true, 
	 			  			  		    		confirmButtonText: 'Тийм',
	 			  			  		    	    cancelButtonText: 'Үгүй',
	 			  			  		    		}, 
	 			  			  		    		function(){
	 			  			  		    			mainService.withdomain('put','/logic/createXplanXreport/'+n+'/'+y+'/'+i)
	 			  		 				   			.then(function(data){
	 			  		 				   				if(data.subdate=='true'){
	 			  		 	 				   				sweet.show('Анхаар!', 'X-Төлөвлөгөө амжилттай үүслээ.', 'success');
	 			  		 	 				   				if(data.lic==1){
	 			  		 	 				   					
	 			  		 	 				   					$state.go('restricted.pages.planFormH',{param:data.id,reqid:data.reqid});
	 			  		 	 				   				}
	 			  		 	 				   				else{
	 				  		 	 				   				if (data.mv == "true"){
	 			  		 	 				   						$state.go('restricted.pages.planFormH',{param:data.id,reqid:data.reqid});
	 			  		 	 				   					}
	 			  		 	 				   					else{
	 			  		 	 				   						$state.go('restricted.pages.planFormA',{param:data.id,reqid:data.reqid});
	 			  		 	 				   					}
	 			  		 	 				   				} 				   					
	 			  		 				   				}
	 				  		 				   			else if (data.subdate=='year'){
	 			  		 				   					sweet.show('Анхаар!', 'Төлөвлөгөөг хүлээн авах хугацааг түр хаасан байна.', 'error');
	 			  		 				   				}
	 			  		 				   				else{
	 			  		 				   				sweet.show('Анхаар!', 'Төлөвлөгөө үүссэн байна', 'error');
	 			  		 				   				}
	 			  			 				   				
	 			  						   			});	
	 			  		  		    			
	 			  				    			}); 		    
	 			  			  		    	}else{
	 			  			  		    		swal({    
	 			  			  		    			type: "warning",   
	 			  			  		    			title: "Анхааруулга !",   
	 			  			  		    			text: "<div style='margin-top:20px;'>Тусгай зөвшөөрлийн тохиргоо хийгдээгүй байна. <span style='color:#F8BB86'> Тайлангийн тохиргоо цэсрүү орж хиинэ үү<span>.<div>",  
	 			  			  		    			html: true
	 			  			    				});
	 			  			  		    	}
	 				   				}	 				   			
	 				   				else{
	 				   					swal({    
	 				  		    			type: "warning",   
	 				  		    			title: "Анхааруулга !",   
	 				  		    			text: "<div style='margin-top:20px;'>Төлөвлөгөөг хүлээн авах хугацааг түр хаасан байна.<div>",  
	 				  		    			html: true
	 				    				});
	 				   				}
	 				   			});
	  		    			});	  		    	
	  		    		}
	  		    	});
	  		    	
	  		    };
	  		    
	  		    
	  	       $scope.loadOnSend = function(i) {
	  		        sweet.show({
	  		        	title: 'Мэдээлэл',
	     		        text: 'Та энэ тайлан / төлөвлөгөө илгээхдээ итгэлтэй байна уу?',
	     		        type: 'warning',
	     		        showCancelButton: true,
	     		        confirmButtonColor: '#DD6B55',
	     		        confirmButtonText: 'Тийм',
	     		        cancelButtonText: 'Үгүй',
	     		        closeOnConfirm: false,
	     		        closeOnCancel: false,
	  		            showLoaderOnConfirm: true
	  		        }, function(inputvalue) {
	  		        	 if (inputvalue) {
	  		        		 mainService.withdomain('put','/logic/sendform/1/'+i)
		 				   			.then(function(){
		 				   				$(".conf .k-grid").data("kendoGrid").dataSource.read(); 	
		 				   				sweet.show('Анхаар!', 'Амжилттай илгээлээ.', 'success');
					   			});	
	   		            }else{
	   		                sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
	   		            }    		        	
	  		        	 
	  		        });
	  		    };
	  		    
	  		    $scope.loadOnUpdate = function(item) {	  	
	  		    	if(item.reporttype==3){
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.planFormH',{param:item.id});
		   				}
		   				else{
		   					$state.go('restricted.pages.planFormA',{param:item.id});
		   				}	  		    		 
	  		    	}
	  		    	else{
	  		    		if(item.lictype==1){
		   					$state.go('restricted.pages.reportFormH',{param:item.id});
		   				}
		   				else{
		   					$state.go('restricted.pages.reportFormA',{param:item.id});
		   				}	 
	  		    	} 		    	
	  		    };
	  		    
	  		    
	
	            
	            $scope.REPBundle = {
		                dataSource: {
		                   
		                    transport: {
		                    	read:  {
		                            url: "/user/angular/RegReportReq",
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
		                groupable: true,
		                filterable: true,
		                sortable: true,
		                resizable: true,
		                columnMenu:true, 
		                pageable: {
		                	refresh:true,
		                    buttonCount: 3
		                },
		                columns: [		
		                          { field:"licenseXB",title: "<span data-translate='Primary license'></span>"},
		                        /*  { 
		                          	 template: kendo.template($("#lic").html()),  width: "240px" ,title: "<span data-translate='Нэмэлт ТЗ-ийн дугаар'></span>"	                                
		                          },*/
		                          /*{ field:"bundledLicenseNum", title: "<span data-translate='Primary license'></span>"},
		                          { field:"addBunLicenseNum", title: "<span data-translate='Secondary license'></span>"},	*/	   
		                         /* { field:"mineralid", title: "<span data-translate='Mineral group'></span>", values:mineralData},*/
		                          { field:"areaName", title: "<span data-translate='Area name'></span>"},
		                          { field:"latestChangeDateTime", title: "<span data-translate='Configured date'></span>"},
		                          { field:"cplan", title: "<span data-translate=''></span>",hidden:true},
		                          { field:"creport", title: "<span data-translate=''></span>",hidden:true},
		                          { 
		                          	 template: kendo.template($("#action").html()),  width: "240px" 	                                
		                           },
		                           { 
			                          	 template: kendo.template($("#xaction").html()),  width: "250px" 	                                
		                           }],
		                      editable: "popup"
		            };
	        }
    ]);
