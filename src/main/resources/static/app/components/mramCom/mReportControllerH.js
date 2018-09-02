angular
    .module('altairApp')
    .controller('annualReportHCtrl', [
    	'$ocLazyLoad',
        '$scope',
        '$rootScope',        
        'utils',     
        '$timeout',
        '$window',
        '$stateParams',
        'mainService',
        'fileUpload',
        'sweet',
        'org_data',   
        '$state',
        'sgt',
        'xonoff',
        'note_data',
        'Upload',
        '$rootScope',
        '$http',
        function ($ocLazyLoad,$scope,$rootScope,utils, $timeout,$window, $stateParams, mainService,fileUpload,sweet,org_data,$state,sgt,xonoff,note_data,Upload,$rootScope,$http) {
            sweet.show('Санамж', 'Тайлангийн зардлын нэгжийн тоон утгыг мян.төгрөг нэгжээр оруулахыг анхааруулъя!', 'warning');
         	$scope.reasonoption=[
		          {"text":"Хөрөнгө оруулалтгүй","value":1 },
		          {"text":"Нөөцийн зэрэглэлийг ахиулах ашиглалтын хайгуул хийх.","value": 2},
		          {"text":"ТЭЗҮ хийж байгаа эсвэл нэмэлт, тодотгол хийгдэж байгаа","value": 3},    		          
		          {"text":"БОННҮ хийж байгаа эсвэл нэмэлт, тодотгол хийгдэж байгаа","value": 4},
		          {"text":"Ашигт малтмалын үнэ ханшийн өөрчлөлт, зах зээл","value": 5},
		          {"text":"Дэд бүтэц, барилга байгууламж барьж  байгаа","value": 6},
		          {"text":"Тоног төхөөрөмж шинэчлэлт хийж байгаа","value": 7},
		          {"text":"Нөөцгүй","value": 8},
		          {"text":"Нөөц дууссан","value": 9},
		          {"text":"Гол мөрний урсац бүрэлдэх эх, . . . ","value": 10},
		          {"text":"Шүүх, хуулийн маргаантай байгаа","value": 11},
		          {"text":"Орон нутаг, иргэний хөдөлгөөн","value": 12},
		          {"text":"Бусад","value": 13}
	          ];
         	
         	$scope.zero_notes=[
		          {"title":"Үйл ажиллагаа явуулаагүй тухай болон түүний шалтгааныг дурдсан албан тоот","id":1 },
		          {"title":"Ашиглалтын үйл ажиллагаа явуулаагүйг нотолсон орон нутгийн мэргэжлийн хяналтын байгууллагын албан дүгнэлт","id": 2},
		          {"title":"Ашигт малтмалын нөөц хүлээн авсан Эрдэс баялгийн мэргэжлийн зөвлөлийн тэмдэглэл эсхүл дүгнэлт, Ашигт малтмал, газрын тосны газрын даргын тушаал","id": 3},    		          
		          {"title":"Техник, эдийн засгийн үндэслэлийг хүлээн авсан Эрдэс баялгийн мэргэжлийн зөвлөлийн дүгнэлт, Ашигт малтмал, газрын тосны газрын даргын тушаал","id": 4},
		          {"title":"Аж ахуйн нэгжийн хөрөнгө оруулалт (Тайлангийн Маягт-19)","id": 5},
		          {"title":"Улс, орон нутгийн төсөвт оруулсан орлого (Тайлангийн Маягт-15)","id": 6},
		          {"title":"Үйл ажиллагаа явуулаагүй шалтгааныг нотолсон баримт","id": 7},
		          {"title":"Бусад","id": 8}
	        ];
         	
         	$scope.openZeroNote = function($event,$parentIndex,$index,note) {
                $event.preventDefault();             
                $scope.noteActiveZero = true;
                
                $('.notes_list').children('li').removeClass('md-list-item-active');

                $($event.currentTarget).parent('li').addClass('md-list-item-active');
                $scope.note_form = {
                    index: $index,
                    id: note.id,
                    title: note.title,
                    planid:$stateParams.param,
                    files: []
                };
                
                mainService.withdomain("POST","/zero/get/"+$stateParams.param+"/"+note.id).then(function(response){
                	$scope.note_form.files = response;
            	});
                angular.element($window).resize();
            };
            
            $scope.getZeroReportFiles = function(files, id){
         		if (files.length > 0){
         			$scope.note_form['zero'+id] = files;
         			$scope.submitZeroForm(null, id);
         		}
         	}
            
            $scope.submitZeroForm = function(event, zeroid) {
            	console.log($scope.note_form['zero'+zeroid]);
	        	 if ($scope.note_form['zero'+zeroid] != null && $scope.note_form['zero'+zeroid] != undefined){
	        		 if (zeroid < 8){
	        			 formdata.append("planid", $stateParams.param);
		                 formdata.append("zeroid", zeroid);
		                 formdata.append("zerofile", $scope.note_form['zero'+zeroid][0]);
		                 $(".dropify-clear").trigger("click"); 
		                 fileUpload.uploadFileToUrl('/logic/zero/save', formdata)
		              	.then(function(data){              		
			   				if(data.return){	 
			   					//modal.hide();
		   						$scope.note_form.files = [];
		   						$scope.note_form.files.push(data.attachment);
			   					
			   					formdata = new FormData();
			   					//$state.go('restricted.pages.planStatus');
			   					sweet.show('Мэдээлэл', 'Файл амжилттай хадгалагдлаа.', 'success');
			   				}	
			   			});
	        		 }
	        		 else{
	        			 for(var i=0;i<$scope.note_form['zero'+zeroid].length;i++){
	        				 formdata.append("planid", $stateParams.param);
			                 formdata.append("zeroid", zeroid);
			                 formdata.append("zerofile", $scope.note_form['zero'+zeroid][i]);
			                 $(".dropify-clear").trigger("click"); 
			                 fileUpload.uploadFileToUrl('/logic/zero/save', formdata)
			              	.then(function(data){              		
				   				if(data.return){	 
				   					$scope.note_form.files.push(data.attachment);
				   					formdata = new FormData();
				   					//$state.go('restricted.pages.planStatus');
				   					sweet.show('Мэдээлэл', 'Файл амжилттай хадгалагдлаа.', 'success');
				   				}	
				   			});
	        			 }
	        		 }
	        	 }
            }
	         
	         $scope.submitZeroFormFinal = function(){
	        	 
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
	  		        		mainService.withdomain("POST","/zero/submit/final/"+$stateParams.param).then(function(response){
	  		        			
	  			        		 if (response.status){
	  			        			 $state.go('restricted.pages.planStatus');
	  			        			 sweet.show('Мэдээлэл', 'Тайлан амжилттай илгээгдлээ.', 'success');
	  			        		 }
	  			        		 else{
	  			        			 $scope.missing = response.missing;
	  			        			
	  			        			 var message="";
	  			        			 for(var i=0;i<response.missing.length;i++){
	  			        				 if (i!=response.missing.length-1){
	  			        					message = message +$filter('filter')($scope.zero_notes, {id: response.missing[i]})[0].title+", ";
	  			        				 }
	  			        				 else{
	  			        					message = message + $filter('filter')($scope.zero_notes, {id: response.missing[i]})[0].title+" файлуудыг хавсаргана уу.";
	  			        					sweet.show('Анхаар!', message, 'error');
	  			        				 }
	  			        			 }
	  			        		 }
	  			        	 })
	   		            }else{
	   		                sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
	   		            }    		        	
	  		        	 
	  		        });
	        	 
	         }
         	
         	$scope.showExcelPopup = function(){
         		UIkit.modal("#excel_popup").show();
         		//$timeout($scope.CallMe, 3000);
         	}
         	
         	$scope.IntroOptions = {
     	        steps:[
     	        {
     	            element: document.querySelector('#xlsmFormBtn'),
     	            intro: "Энд дарж маягтыг татаж авна уу."
     	        }
     	        ],
     	        showStepNumbers: false,
     	        showBullets: false,
     	        exitOnOverlayClick: true,
     	        exitOnEsc:true,
     	        nextLabel: '<strong>ДАРААХ!</strong>',
     	        prevLabel: '<span style="color:green">Өмнөх</span>',
     	        skipLabel: 'Хаах',
     	        doneLabel: 'Хаах'
     	    };
         	
        	$('.dropify').dropify();

            $('.dropify-fr').dropify({
                  messages: {
                      default: 'Glissez-déposez un fichier ici ou cliquez',
                      replace: 'Glissez-déposez un fichier ou cliquez pour remplacer',
                      remove:  'Supprimer',
                      error:   'Désolé, le fichier trop volumineux'
                  }
            });
            
            $('.dropify-excel').dropify({
                messages: {
                    default: 'Файлаа сонгоно уу',
                    replace: 'Өөр файлаар солих',
                    remove:  'Болих',
                    error:   'Зөвхөн MS Excel 2007 болон түүнээс хойшхи хувилбараар үүсгэсэн .xlsm өргөтгөлтэй файлыг хавсаргана уу!'
                }
            });
            
           	var inpt=[
           			  {"text":"Хүлээн авах хэсэг","value": 8},    		          
    		          {"text":"Геологийн хэсэг","value": 9},
    		          {"text":"Зардлын доод хэмжээ","value": 10}
		          ];
           	
   
        	$scope.inpt_options=inpt;
              
            $scope.notes_data = note_data;
			$scope.form = note_data[0].form;
			$scope.mcom = note_data[0].mcom;
	    	$scope.xonoff = xonoff[0].xxx;
	    	
	    	$scope.event = note_data[0].mcom;
	    	
	    	var modalHistory = UIkit.modal("#modal_history");
	    	if($scope.mcom.length>0){
	    		modalHistory.show();
	    	}
	    	    
	    	$scope.planid = $stateParams.param;
	    	
	    	function init(){
	    		 mainService.withdomain('get','/user/service/note/imp/'+$stateParams.param)
		   			.then(function(data){
		   				$scope.notes_data = data;
		   				$scope.form = data[0].form;
		   			});		    		
	    	}
	    	
	    	$scope.sgt=sgt[0];
	    	$scope.vid=$stateParams.param;
	    	
	    	$scope.sendBtn=false;

	    	if($scope.sgt.xxx!=7){

	    		initButton();
	    	}
	    	
	    	$scope.files=[];
	  	    $scope.onSuccess = function(d) {
	  	    	
	  	    	 $scope.files.push(d.response);
	  	    	 var blob = d.response.fileurl;
	      	     console.log(blob);
	              
	              var xhr = new XMLHttpRequest();
	          	xhr.open('GET', d.response.fileurl, true);
	          	xhr.responseType = 'blob';
	          	 
	          	xhr.onload = function(e) {
	          	  if (this.status == 200) {
	          	    // get binary data as a response
	          	    var blob = this.response;
	          	    var spreadsheet = $("#spreadSheetView").data("kendoSpreadsheet");
	  		            spreadsheet.fromFile(blob);
	          	  }
	          	  else{
	          		  sweet.show('Анхаар!', 'Файл устгагдсан байна.', 'error');
	          	  }
	          	};
	          	xhr.send();
	  	            
	         }
	    	
	    	
	    	//$scope.sendBtn=$scope.sgt.send;
	  	  function initButton(){
              if ($scope.sgt.xxx==2){
                  $scope.sendBtn=true;
              }
              else{
	    		mainService.withdomain('get',"/getSelectedFormYear/1/"+$scope.sgt.reporttype+'/'+$scope.sgt.divisionid+'/'+$scope.sgt.lcnum)
	   			.then(function(data){		   				
	   				if(data!=0){
	   					$scope.sendBtn=true;
	   				}
	   				else{
	   					mainService.withdomain('get','/user/service/rs/com.peace.users.model.mram.SubLicenses/licenseNum/'+$scope.sgt.lcnum)
			   			.then(function(data){
			   				if(data.lplan==true){
			   					$scope.sendBtn=true;
			   				}

			   				else{
			   					mainService.withdomain('get','/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/'+$stateParams.param)
					   			.then(function(ann){
					   				//$scope.sendBtn=true;
					   				if(ann.repstatusid==2 && ann.rejectstep!=null){
					   					$scope.sendBtn=true;
					   				}
					   			});
			   				}

			   				if(data.ftime==true){
			   					$scope.sendBtn=true;
			   				}

			   			});
	   				}
	   			});
              }
	       }
	    	
	    	$scope.loadOnSend = function(i) {
	    	  	 var modal = UIkit.modal("#modal_header_notyet");
	     		 mainService.withdomain('get','/logic/valform/'+$stateParams.param)
		   			.then(function(data){
		   				$scope.notyet=data;
		   				
		   				if($scope.notyet.length>0){
		   					modal.show();
		   				}
		   				else{
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
		  		        		 mainService.withdomain('put','/logic/sendform/'+i+'/'+$stateParams.param)
		 	 				   			.then(function(){	
		 	 				   				sweet.show('Анхаар!', 'Амжилттай илгээлээ.', 'success');
		 	 				   				$state.go('restricted.pages.planStatus');
		 				   			});	
		   		            }else{
		   		                sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
		   		            }    		        	
		  		        	 
		  		        });
	   				}
		   				
	   			});	

 		    };

 	    	$scope.openDeactive = function(){
	    		 $scope.noteActive = false;
	    	}
            $scope.noteActive = false;

            $scope.onSelect = function(e) {
                var message = $.map(e.files, function(file) { return file.name; }).join(", ");
            }
            
            $scope.fileAttachmentOptions = {
            	 async: {
            		  saveUrl: '/logic/save',
            	      removeUrl: '/logic/destroy',
            	      removeVerb: 'DELETE',
            	      autoUpload: false
            	  },
            	  upload: function (e) {
            	        e.data = { "obj":  da()};
            	    },
        	      remove: function (e) {
	          	        e.data = { "obj":  da()};
	          	  },
                  localization: {
                      select: "Select a file",
                      uploadSelectedFiles: "Send"
                  }
            }

            function da(){
        	   var parentIndex = $scope.note_form.parentIndex,
               index = $scope.note_form.index,
               title = $scope.note_form.title;
               content = $scope.note_form.content;
               expid=$stateParams.param;
               filename=$scope.note_form.content;
               if(parentIndex && index) {                  
            	   init();    
               }
               
               var data = JSON.stringify($scope.note_form, null, 2);
               return data;
            }
            // open a note
            $scope.fileUp=false;
            $scope.openNote = function($event,$parentIndex,$index,note) {
                $event.preventDefault();             
                $scope.noteActive = true;
                $('.notes_list').children('li').removeClass('md-list-item-active');

                $($event.currentTarget).parent('li').addClass('md-list-item-active');
                if($scope.xonoff){
                	  if(note.decision==2 || note.decision==0 || note.decision==undefined){
                      	$scope.fileUp=true;
                      }
                      else if(note.decision==1){
                      	$scope.fileUp=false;
                      }
                }
                
                $scope.note_form = {
                    parentIndex: $parentIndex,
                    index: $index,
                    id: note.id,
                    title: note.title,
                    content: note.content,
                    help: note.help,
                    comm:note.comment,
                    isform: note.isform,
                    decision:note.decision,
                    issaved:note.issaved,
                    size: note.size,
                    images: note.images,
                    planid:$stateParams.param,
                    atfile:note.file
                };
                
                angular.element($window).resize();
                if (note.isform == 1){
                	$scope.loadFormDatas(note.id);
                }
               
            };
            
            var progressbar = $("#file_upload-progressbar"),
            bar         = progressbar.find('.uk-progress-bar'),
            settings    = {

                action: '/import/upload/'+0, // upload url

                allow : '*.(xlsm)', // allow only images
                
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

                    /*setTimeout(function(){
                        progressbar.addClass("uk-hidden");
                    }, 250);*/

                    alert("Upload Completed")
                }
            };

            var select = UIkit.uploadSelect($("#file_upload-select"), settings),
            drop   = UIkit.uploadDrop($("#file_upload-drop"), settings);

            $scope.removeAttach = function(obj) {
            	var parentIndex = $scope.note_form.parentIndex,
                index = $scope.note_form.index;            
		        sweet.show({
		        	   title: 'Баталгаажуулалт',
   		            text: 'Та энэ файлыг устгахдаа итгэлтэй байна уу?',
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
		        		 mainService.withdomain('delete','/user/service/removeAttach/'+obj.id)
 				   			.then(function(){
 				   			  var index = -1;
 				   			  for (var i = 0; i < $scope.note_form.images.length; i++) {
 				   			    if ($scope.note_form.images[i].id == obj.id) {
	 				   			      index = i;
				   			      }
 				   			  }
 				   			  $scope.note_form.images.splice(index,1)
 				   				sweet.show('Анхаар!', 'Файл амжилттай устлаа.', 'success');
 				   			  init();
				   			});	
 		            }else{
 		                sweet.show('Анхаар!', 'Устгах үйлдэл хийгдсэнгүй!!!', 'error');
 		            }    		
		        });
		    };

            $scope.noteForm = {
            		atfile: "default"
                };
            
            $scope.uploadPic = function(file) {
			    if($scope.note_form.isform==1){
			    	$scope.note_form.images.splice(index, 1)
			    }
			    var parentIndex = $scope.note_form.parentIndex,
			    index = $scope.note_form.index,
			    title = $scope.note_form.title;
			    content = $scope.note_form.content;
			    file_form = $scope.noteForm.atfile;
			    planid = $stateParams.param;
			    var data = JSON.stringify($scope.note_form, null, 2); 
			    file.upload = Upload.upload({
                	url: '/logic/save',
                    data: {obj: data, files: file},
                });
			    if ($scope.note_form.isform==1){
			    	if (file.type != "application/pdf"){
			    		sweet.show('Анхаар', 'Зөвхөн PDF файл сонгоно уу.', 'warning');
			    	}
			    	else{
			    		file.upload.then(function (response) {
		                    $timeout(function () {
		                      file.result = response.data;
		                      $scope.note_form.issaved="10";
		                  
		                      if(response.data.rdata=="noeq"){
		                      	sweet.show('Анхаар', 'Маягт тохирохгүй байна.', 'warning');
		                      }
		                      else if(response.data.rdata=="nodata"){
		                      	sweet.show('Анхаар', 'Алдаа үүслээ.', 'warning');
		                      }
		                      else{
		                          for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
		  			   			    if ($scope.notes_data[0].notes[i].id == $scope.note_form.id) {
		  			   			    	$scope.notes_data[0].notes[i].issaved = "10";
		  			   			    	$scope.notes_data[0].notes[i].decision = 0;
		  			   			      }
		  			   			  }
		                      	sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
		                      	$scope.note_form.images.push(response.data.atdata[0]);
		                      	//$scope.notes_data[0].notes[$scope.note_form.index].issaved="10";
		                      }
		                  	
		     					
		                    });
		                  }, function (response) {
		                    if (response.status > 0)
		                      $scope.errorMsg = response.status + ': ' + response.data;
		                  }, function (evt) {
		                    // Math.min is to fix IE which reports 200% sometimes
		                    file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
		                  });
			    	}
			    }
			    else{
			    	file.upload.then(function (response) {
	                    $timeout(function () {
	                      file.result = response.data;
	                      $scope.note_form.issaved="10";
	                  
	                      if(response.data.rdata=="noeq"){
	                      	sweet.show('Анхаар', 'Маягт тохирохгүй байна.', 'warning');
	                      }
	                      else if(response.data.rdata=="nodata"){
	                      	sweet.show('Анхаар', 'Алдаа үүслээ.', 'warning');
	                      }
	                      else{
	                          for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
	  			   			    if ($scope.notes_data[0].notes[i].id == $scope.note_form.id) {
	  			   			    	$scope.notes_data[0].notes[i].issaved = "10";
	  			   			    	$scope.notes_data[0].notes[i].decision = 0;
	  			   			      }
	  			   			  }
	                      	sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
	                      	$scope.note_form.images.push(response.data.atdata[0]);
	                      	//$scope.notes_data[0].notes[$scope.note_form.index].issaved="10";
	                      }
	                  	
	     					
	                    });
	                  }, function (response) {
	                    if (response.status > 0)
	                      $scope.errorMsg = response.status + ': ' + response.data;
	                  }, function (evt) {
	                    // Math.min is to fix IE which reports 200% sometimes
	                    file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
	                  });
			    }
                

                
            }
  
            var formdata = new FormData();
            $scope.getTheFiles = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("files", value);
                });
            };
            
            $scope.getExcelFiles = function ($files) {
                if ($files != null && $files != undefined && $files.length > 0){
                	$scope.xslmFile = $files[0];
                }
            };
            
            $scope.uploadExcelForm = function(){
            	if ($scope.xslmFile != null && $scope.xslmFile != undefined){
            		$rootScope.content_preloader_show();
            		Upload.upload({
            			url: '/import/upload/'+$scope.vid,
            		    data: {files: $scope.xslmFile},
            		}).then(function (response) {
            			if (response.data.status){
            				$rootScope.content_preloader_hide();
            				init();
            				//UIkit.modal("#excel_popup").hide();
    	   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
    	   					//$("#excel_popup .dropify-clear").click();
    	   					$scope.xslmFile = null;
    	   				}
    	   				else{
    	   					$rootScope.content_preloader_hide();
    	   					sweet.show('Алдаа', 'Алдаа үүслээ.', 'warning');
    	   				}
        			}, function (response) {
        				$rootScope.content_preloader_hide();
        				sweet.show('Алдаа', 'Алдаа үүслээ.', 'warning');
        				
        			}, function (evt) {
        			// Math.min is to fix IE which reports 200% sometimes
        			//	$scope.xslmFile.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
        			});
            	}
            }
            
            $scope.saveAttach = function($event) {
                 $event.preventDefault();    
                 var parentIndex = $scope.note_form.parentIndex,
                 index = $scope.note_form.index,
                 title = $scope.note_form.title;
                 content = $scope.note_form.content;
                 file_form = $scope.noteForm.atfile;
                 planid = $stateParams.param;
                 var data = JSON.stringify($scope.note_form, null, 2);               
                 formdata.append("obj", data);
                 if($scope.note_form.isform==1){
                	 $scope.note_form.images.splice(index, 1)
                 }
                 fileUpload.uploadFileToUrl('/logic/save', formdata)
              	.then(function(data){  
	   				if(data.return){	   					
	   					$scope.note_form.images.push(data.atdata[0]);
	   					//init();
	   					formdata = new FormData();
	   					angular.element('.dropify-clear').triggerHandler('click');
	   					angular.element('.lala .dropify-clear').triggerHandler('click');
	   					angular.element('.last1 .dropify-clear').triggerHandler('click');
	              		angular.element('.last2 .dropify-clear').triggerHandler('click');
	              		angular.element('.last3 .dropify-clear').triggerHandler('click');
	              		angular.element('.last4 .dropify-clear').triggerHandler('click');
	              		angular.element('.last5 .dropify-clear').triggerHandler('click');
	              		angular.element('.last6 .dropify-clear').triggerHandler('click');
	   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
	   				}
			   				
	   			});	                
             }
           
            $scope.getTheFilesFirst = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("firstFile", value);
                });
            };
            $scope.getTheFilesLast = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("lastFile", value);
                });
            };
            
            $scope.zeroForm={
		    		textComment:'',
		    		planid:$stateParams.param
		    }
		    /*var modal = UIkit.modal("#zero_report");
	         $scope.submitZeroForm = function() {   
	        	 
                 formdata.append("planid", $stateParams.param);
	        	 formdata.append("reasonid", $scope.zeroForm.reasonid);
                 formdata.append("textComment", $scope.zeroForm.textComment);
                 $(".dropify-cleart").trigger("click"); 
                 fileUpload.uploadFileToUrl('/logic/zero/save', formdata)
              	.then(function(data){              		
	   				if(data.return){	 
	   					modal.hide();
	   					formdata = new FormData();
	   					$state.go('restricted.pages.planStatus');
	   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
	   				}
			   				
	   			});	                
             }*/
            
            // save note
            
            $scope.deleteZeroAttachment = function(file, index){
            	sweet.show({
  		        	title: 'Мэдээлэл',
     		        text: 'Та энэ файлыг устгах уу?',
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
  		        		 mainService.withdomain('delete','/zero/deleteAtt/'+$stateParams.param+"/"+file.noteid+"/"+file.id)
 	 				   			.then(function(response){
 	 				   				if (response){
 	 				   					sweet.show('Анхаар!', 'Амжилттай устгагдлаа.', 'success');
 	 				   					$scope.note_form.files.splice(index,1);
 	 				   				}
 	 				   				else{
 	 				   					sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
 	 				   				}
 				   			});	
   		            }else{
   		                sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
   		            }    		        	
  		        	 
  		        });
            }
            
            $scope.saveNote = function($event) {
               $event.preventDefault();         
                // get variables from active note
                var parentIndex = $scope.note_form.parentIndex,
                    index = $scope.note_form.index,
                    title = $scope.note_form.title;
                    content = $scope.note_form.content;
                    file_form = $scope.noteForm.atfile;
                    planid = $stateParams.param;

                var data = JSON.stringify($scope.note_form, null, 2);
                
               
                mainService.withdata('put','/logic/submitPlanDetail/', data)
		   			.then(function(data){
		   				if(data){
			   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
			   				init();
		   				}
			   				
	   			});	
                
                         
            }
            $scope.org_data = org_data;
            console.log(org_data);
            
            var lpreg=org_data.lpReg;
            
            console.log(lpreg);
            
        	$scope.tooloptions = {

    			tools: [
    				"bold","italic","underline","strikethrough","justifyLeft","justifyCenter","justifyRight","justifyFull",
    				"insertUnorderedList","insertOrderedList","indent","outdent","createLink","unlink","insertImage","insertFile",
    				"subscript","superscript","createTable","addRowAbove","addRowBelow","addColumnLeft","addColumnRight","deleteRow",
    				"deleteColumn","viewHtml","formatting","cleanFormatting","fontName","fontSize","foreColor","backColor"
    			],

    		imageBrowser: {
    			 messages: {
                     dropFilesHere: "Drop files here"
                    },
                    transport: {
           				read: {
           					url:"/imagebrowser/read",	        			
           						type: "GET",
           						data: {"lpreg":lpreg},
           						dataType: "json"
           					},
           				destroy: {
           					url:"/imagebrowser/destroy/"+lpreg,	        			
           						type: "POST",
           						dataType: "json"
           					},
       					uploadUrl:"/imagebrowser/upload/"+lpreg,	
           				thumbnailUrl: "/imagebrowser/thumbnail",
           				imageUrl:function(e){
           					return "/" + e;
           				}
       				 },
				},
				fileBrowser: {
                    messages: {
                        dropFilesHere: "Drop files here"
                    },
                    transport: {
                		 read: {
                			 	url:"/imagebrowser/read/file",	        			
   	                        type: "POST",
   	                        dataType: "json"
   	        			},
                        destroy: {
                            url: "/imagebrowser/destroy",
                            type: "POST"
                        },
                    	 create: "/imagebrowser/create",
                    	 fileUrl:function(e){
    	        		    return "/" + e;
    	        		 }, 
                        uploadUrl: "/imagebrowser/upload"
                   }
                }
    		};
            
    	    $scope.wysiwyg_editor_content ="sdvavasvasv";
              
            $scope.wysiwyg_editor_options = {
               customConfig: '../../assets/js/custom/ckeditor_config.js'
            }
        	
        	$scope.content={
        			preface:''
        	}
            $scope.loadFormDatas = function(id){
    			$scope.loadFormURL = null;
    			if (id > 0){
    				$ocLazyLoad.load('/app/components/dataforms/'+id+'/controller.js').then(function(){
    					$scope.loadFormURL = '/app/components/dataforms/'+id+'/view.html';
    				});
    			}
    		}
        }
    ]);