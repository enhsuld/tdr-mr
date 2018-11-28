angular
    .module('altairApp')
    .controller('annualFormCtrl', [
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
        '$ocLazyLoad',
        function ($scope,$rootScope,utils, $timeout,$window, $stateParams, mainService,fileUpload,sweet,org_data,$state,sgt,xonoff,note_data,Upload,$ocLazyLoad) {

            sweet.show('Санамж', 'Төлөвлөгөөний зардлын нэгжийн тоон утгыг мян.төгрөг нэгжээр оруулахыг анхааруулъя!', 'warning');

        	var inpt=[
    		          {"text":"Бичвэр мэдээлэл","value": 2},
    		          {"text":"Хавсаргах мэдээлэл","value": 3}
		          ];
         	
        	$scope.inpt_options=inpt;
        	
        	        	
	    	var $wizard_advanced_form = $('#wizard_advanced_form');
	    	$('.dropify').dropify();
	    	
	    	$('.dropify-excel').dropify({
                messages: {
                    default: 'Файлаа сонгоно уу',
                    replace: 'Өөр файлаар солих',
                    remove:  'Болих',
                    error:   'Зөвхөн MS Excel 2007 болон түүнээс хойшхи хувилбараар үүсгэсэн .xlsm өргөтгөлтэй файлыг хавсаргана уу!'
                }
            });
	    	
	    	
	       	$scope.sgt=sgt[0];
	    	$scope.xonoff = xonoff[0].xxx;
	    	$scope.notes_data=note_data;
	    	$scope.org_data = org_data;
	    	$scope.mcom = note_data[0].mcom;
	    	$scope.form = note_data[0].form;
	    	
	    	
	    	 $scope.showExcelPopup = function(){
	         		UIkit.modal("#excel_popup").show();
	         		//$timeout($scope.CallMe, 3000);
	         	}
			    
			    $scope.getExcelFiles = function ($files) {
	                if ($files != null && $files != undefined && $files.length > 0){
	                	$scope.xslmFile = $files[0];
	                }
	            };
	            
	            $scope.uploadExcelForm = function(){
	            	if ($scope.xslmFile != null && $scope.xslmFile != undefined){
	            		
	            		Upload.upload({
	            			url: '/import/upload/'+$stateParams.param,
	            		    data: {files: $scope.xslmFile},
	            		}).then(function (response) {
	            			if (response.data.status){
	            				
	            				init();
	            				//UIkit.modal("#excel_popup").hide();
	    	   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
	    	   					//$("#excel_popup .dropify-clear").click();
	    	   					$scope.xslmFile = null;
	    	   				}
	    	   				else{
	    	   					
	    	   					sweet.show('Алдаа', 'Алдаа үүслээ.', 'warning');
	    	   				}
	        			}, function (response) {
	        				
	        				sweet.show('Алдаа', 'Алдаа үүслээ.', 'warning');
	        				
	        			}, function (evt) {
	        			// Math.min is to fix IE which reports 200% sometimes
	        			//	$scope.xslmFile.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
	        			});
	            	}
	            }
	            
	    	function init(){
	    		 mainService.withdomain('get','/user/service/note/imp/'+$stateParams.param)
		   			.then(function(data){
		   				$scope.notes_data = data;
		   				$scope.form = data[0].form;
		   			
		   			})
	    	}
	    	
	    	var reasonoption=[
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
      	
      	$scope.reasonoption=reasonoption;

          $scope.selectize_a_config = {
             create: false,
             maxItems: 1,
             placeholder: 'Сонгох...',
             optgroupField: 'parent_id',
             optgroupLabelField: 'text',
             optgroupValueField: 'ogid',
             valueField: 'value',
             labelField: 'text',
             searchField: 'text'
          };
	    	
	    	$scope.planid=$stateParams.param;
	    	
	    	$scope.setPdf = function(item) {
	    		mainService.withdomain('get','/logic/pdf/'+$stateParams.param+'/'+item.id)
	   			.then(function(data){
	   				$scope.notes_data = data;
	   				$scope.form = data[0].form;
	   			
	   			});	
	    	}
	    	$scope.sendBtn=false;
	    	if($scope.sgt.xxx!=7){
	    		if($scope.sgt.xxx!=1){
	    			initButton();
	    		}
	    	}
	    	function initButton(){
                if ($scope.sgt.xxx==2){
                    $scope.sendBtn=true;
                }
                else {
                    mainService.withdomain('get', '/user/service/rs/com.peace.users.model.mram.LutYear/value/' + $scope.sgt.year + '/' + 'divisionid' + '/' + $scope.sgt.divisionid)
                        .then(function (data) {
                            if (data.isactive == true) {
                                $scope.sendBtn = true;
                            }
                            else {
                                mainService.withdomain('get', '/user/service/rs/com.peace.users.model.mram.SubLicenses/licenseNum/' + $scope.sgt.lcnum)
                                    .then(function (data) {
                                        if (data.lplan == true) {
                                            $scope.sendBtn = true;
                                        }

                                        else {
                                            mainService.withdomain('get', '/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/' + $stateParams.param)
                                                .then(function (ann) {
                                                    //$scope.sendBtn=true;
                                                    if (ann.repstatusid == 2 && ann.rejectstep != null) {
                                                        $scope.sendBtn = true;
                                                    }
                                                });
                                        }

                                        if (data.ftime == true) {
                                            $scope.sendBtn = true;
                                        }

                                    });
                            }
                        });
                }
	       }
	    	 
	    	$scope.loadOnSend = function(i) {
	    	  	 var modal = UIkit.modal("#modal_header_notyet");
	     	/*	 mainService.withdomain('get','/logic/valform/'+$stateParams.param)
		   			.then(function(data){
		   			
		   				
	   			});	*/
	    	  	  var json = [];
	    			
    			  for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
    				  console.log($scope.notes_data[0].notes[i].isrequired);
		   			    if ($scope.notes_data[0].notes[i].issaved != "10" && $scope.notes_data[0].notes[i].isrequired==1) {
		   			    	json.push({"id":$scope.notes_data[0].notes[i].id, "title":$scope.notes_data[0].notes[i].title});
	   			      }
	   			  }
	    	 	console.log(json);
   				$scope.notyet=json;
   				
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

		    };

            $scope.noteActive = false;
        	$scope.openDeactive = function(){
	    		 $scope.noteActive = false;
	    	}
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
                    comm:note.comment,
                    comment: note.comment,
                    decision:note.decision,
                    isform: note.isform,
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
				                    
				                    console.log(response.data);
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
							   			      }
							   			  }
				                    	sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
				                    	$scope.note_form.images.push(response.data.atdata[0]);
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
	  	                    
	  	                    console.log(response.data);
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
	  				   			      }
	  				   			  }
	  	                    	sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
	  	                    	$scope.note_form.images.push(response.data.atdata[0]);
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

            $scope.noteForm = {
            		atfile: "default"
                };
  
            var formdata = new FormData();
            $scope.getTheFiles = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("files", value);
                });
            };
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
	   				//	init();
	   					$scope.note_form.images.push(data.atdata[0]);
	   					angular.element('.lala .dropify-clear').triggerHandler('click');
	   					angular.element('.dropify-clear').triggerHandler('click');
	   					formdata = new FormData();
	   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
	   					
	   				}
			   				
	   			});	                
             }
            
            // save note
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
            var modal = UIkit.modal("#zero_report");
	         $scope.submitZeroForm = function() {   
                formdata.append("planid", $stateParams.param);
                formdata.append("textComment", $scope.zeroForm.textComment);
                formdata.append("reasonid", $scope.zeroForm.reasonid);
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
            }
      
	         var lpreg=org_data.lpReg;
	         
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
            
           /* $scope.wysiwyg_editor_options = {
               customConfig: '../../assets/js/custom/ckeditor_config.js'
            } */
        	
        	  $scope.editorOptions = {
                      language: 'en',
                      'skin': 'material_design,../../assets/skins/ckeditor/material_design/',
                      'extraPlugins': "imagebrowser,mediaembed",
                      imageBrowser_listUrl: '/imagebrowser/read',
                      filebrowserBrowseUrl: '/api/v1/ckeditor/files',
                      filebrowserImageUploadUrl: '/api/v1/ckeditor/images',
                      filebrowserUploadUrl: '/api/v1/ckeditor/files',
                      toolbarLocation: 'top',
                      toolbar: 'full',
                      toolbar_full: [
                          { name: 'basicstyles',
                              items: [ 'Bold', 'Italic', 'Strike', 'Underline' ] },
                          { name: 'paragraph', items: [ 'BulletedList', 'NumberedList', 'Blockquote' ] },
                          { name: 'editing', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
                          { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
                          { name: 'tools', items: [ 'SpellChecker', 'Maximize' ] },
                          { name: 'clipboard', items: [ 'Undo', 'Redo' ] },
                          { name: 'styles', items: [ 'Format', 'FontSize', 'TextColor', 'PasteText', 'PasteFromWord', 'RemoveFormat' ] },
                          { name: 'insert', items: [ 'Image', 'Table', 'SpecialChar', 'MediaEmbed' ] },'/',
                      ]
                  };
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

