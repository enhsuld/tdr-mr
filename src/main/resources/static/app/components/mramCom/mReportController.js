angular
    .module('altairApp')
    .controller('annualReportACtrl', [
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
        'ann',
        'pv',
        'Upload',
        '$filter',
        '$ocLazyLoad',
        function ($scope,$rootScope,utils, $timeout,$window, $stateParams, mainService,fileUpload,sweet,org_data,$state,sgt,xonoff,note_data,ann,pv,Upload,$filter,$ocLazyLoad) {    
        	
         	var inpt=[
    		          {"text":"Хүлээн авах хэсэг","value":2 },
    		          {"text":"Бичвэр мэдээлэл","value": 20},
    		          {"text":"Нөөцийн хэсэг","value": 3},    		          
    		          {"text":"Технологийн хэсэг","value": 4},
    		          {"text":"Бүтээгдэхүүн борлуулалтын хэсэг","value": 5},
    		          {"text":"Эдийн засгийн хэсэг","value": 6}
		          ];
         	
        	$scope.inpt_options=inpt;
        	
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
         	
         	$('.dropify-excel').dropify({
                messages: {
                    default: 'Файлаа сонгоно уу',
                    replace: 'Өөр файлаар солих',
                    remove:  'Болих',
                    error:   'Зөвхөн MS Excel 2007 болон түүнээс хойшхи хувилбараар үүсгэсэн .xlsm өргөтгөлтэй файлыг хавсаргана уу!'
                }
            });
         	
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
        	
         	$scope.getZeroReportFiles = function(files, id){
         		if (files.length > 0){
         			$scope.note_form['zero'+id] = files;
         			$scope.submitZeroForm(null, id);
         		}
         	}
         	
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
        	if(pv==null){
        		$scope.cpv=false;
        	}
        	else{
        		$scope.cpv=true;
        		$scope.pv=pv;
        	}
        	$scope.sgt=sgt[0];
        	
        	if(ann==null){
        		$scope.cann=false;
        	}
        	else{
        		$scope.cann=true;
        		$scope.config=ann;
        	}
        	$scope.sendBtn=false;

	    	if($scope.sgt.xxx!=7){
	    		if($scope.sgt.xxx!=1){
	    			initButton();
	    		}
	    	}
	    	
	    	//$scope.sendBtn=$scope.sgt.send;
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
                                        if (data.lreport == true) {
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
   	       
 	       $scope.mineralDataSource = {
       	            serverFiltering: true,
       	            transport: {
       	                read: {
       	                    url: "/user/service/cascading/kendo/LutMinerals",
       	                    contentType:"application/json; charset=UTF-8",                                    
                               type:"POST"
       	                },
       	                parameterMap: function(options) {
                          	   return JSON.stringify(options);
                           }
       	             }
       	        };
 	       
 	       $scope.deposididDataSource = {
      	            serverFiltering: true,
      	            transport: {
      	                read: {
      	                    url: "/user/service/cascading/kendo/LutDeposit",
      	                    contentType:"application/json; charset=UTF-8",                                    
                              type:"POST"
      	                },
      	                parameterMap: function(options) {
                         	   return JSON.stringify(options);
                          }
      	             }
      	        };
     	   
     	   $scope.productsDataSource = {
    	            serverFiltering: true,
    	            transport: {
    	             serverFiltering: true,
    	                read: {
    	                    url: "/user/service/cascading/kendo/LutAdminunit",
    	                    contentType:"application/json; charset=UTF-8",                                    
                            type:"POST"
    	                },
    	                parameterMap: function(options) {
                       	   return JSON.stringify(options);
                        }
    	             }
    	        };
     	   
     	   $scope.aimags = {
      	            serverFiltering: true,
      	            transport: {
      	                read: {
      	                	 url: "/user/service/cascading/kendo/LutAdminunitParent",
       	                     contentType:"application/json; charset=UTF-8",                                    
                               type:"POST"
      	                },
      	                parameterMap: function(options) {
                         	   return JSON.stringify(options);
                          }
      	             }
      	        };
     	  
        	   var categories = $("#bomber").kendoDropDownList({
                   optionLabel: "Тэсэлгээний ажил...",
                   dataTextField: "name",
                   dataValueField: "id",
                   dataSource: {
                       serverFiltering: true,
                       data: [
      						{
      					       "id": "1",
      					       "name": "Тийм"
      					    },   
         			            {
         			                "id": "0",
         			                "name": "Үгүй"
         			            }   			            
         			        ]
                   }
               }).data("kendoDropDownList");

               var products = $("#bomberman").kendoDropDownList({
                   autoBind: false,
                   cascadeFrom: "bomber",
                   optionLabel: "Гүйцэтгэгч...",
                   dataTextField: "name",
                   dataValueField: "id",
                   dataSource: {
                       serverFiltering: true,
                       data: [
                              {"Id":1,"name":"Өөрөө гүйцэтгэх","parentid":1},
                              {"Id":1,"name":"Гэрээгээр гүйцэтгүүлэх","parentid":1} 			            
    			        ]
                   }
               }).data("kendoDropDownList");
               
               $scope.OneSource = {
     	            data: [
                          {"id":1,"name":"Тийм"},
                          {"id":2,"name":"Үгүй"} 			            
			        ]
     	       };
               
               $scope.TwoSource = {
          	            data: [
                               {"id":1,"name":"Өөрөө гүйцэтгэх","parentid":1},
                               {"id":2,"name":"Гэрээгээр гүйцэтгүүлэх","parentid":1},
                               {"id":3,"name":"Үгүй","parentid":2} 		
     			        ]
          	        };
               
               $scope.wer=true;
               $scope.ena=function(){
            	  
            	   if($scope.config.komissid==1){
            		   $scope.wer=false;
            	   }
            	   else{
            		   $scope.wer=true;
            		   $scope.config.komissdate="";
            		   $scope.config.komissakt="";
            	   }
               }
    		   
        	   $scope.mineType = {
       	            serverFiltering: true,
       	            filter: "startswith",
       	            transport: {
       	                read: {
       	                    url: "/user/service/cascading/kendo/LutMineType",
       	                    contentType:"application/json; charset=UTF-8",                                    
                               type:"POST"
       	                },
       	                parameterMap: function(options) {
                          	   return JSON.stringify(options);
                           }
       	             }
       	        };
        	   
        	   $scope.yesno = {
    			    dataTextField: 'name',
    			    dataValueField: 'id',
    			    data: [
						{
					       "id": "1",
					       "name": "Тийм"
					    },   
   			            {
   			                "id": "0",
   			                "name": "Үгүй"
   			            }   			            
   			        ]
    			};
        	   
        	   $scope.bomb = {
       			    dataTextField: 'name',
       			    dataValueField: 'id',
       			    data: [
   						{
   					       "id": "1",
   					       "name": "Өөрөө гүйцэтгэх"
   					    },   
      			            {
      			                "id": "0",
      			                "name": "Гэрээгээр гүйцэтгүүлэх"
      			            }   			            
      			        ]
       			};
         	
        	$scope.sgt=sgt[0];
        	$scope.mcom = note_data[0].mcom;
         
	    	$scope.xonoff = xonoff[0].xxx;	    	
	    	$scope.openDeactive = function(){
	    		 $scope.noteActive = false;
	    	}
	    	
            $scope.org_data = org_data;
	    	
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

        	$('.dropify').dropify();

            $('.dropify-fr').dropify({
                  messages: {
                      default: 'Glissez-déposez un fichier ici ou cliquez',
                      replace: 'Glissez-déposez un fichier ou cliquez pour remplacer',
                      remove:  'Supprimer',
                      error:   'Désolé, le fichier trop volumineux'
                  }
            });
            
            $('.dropify').dropify({
                messages: {
                    'default': 'Drag and drop a file here or click',
                    'replace': 'Drag and drop or click to replace',
                    'remove':  'Remove',
                    'error':   'Ooops, something wrong appended.'
                }
            });
            
            $('.dropify').dropify({
                error: {
                    'fileSize': 'The file size is too big ({{ value }} max).',
                    'minWidth': 'The image width is too small ({{ value }}}px min).',
                    'maxWidth': 'The image width is too big ({{ value }}}px max).',
                    'minHeight': 'The image height is too small ({{ value }}}px min).',
                    'maxHeight': 'The image height is too big ({{ value }}px max).',
                    'imageFormat': 'The image format is not allowed ({{ value }} only).'
                }
            });
            
            $('.dropify').dropify({
            	allowedFormats: ['portrait', 'square', 'landscape'],
            	allowedFileExtensions: ['pdf'],
                tpl: {
                    wrap:            '<div class="dropify-wrapper"></div>',
                    loader:          '<div class="dropify-loader"></div>',
                    message:         '<div class="dropify-message"><span class="file-icon" /> <p>{{ default }}</p></div>',
                    preview:         '<div class="dropify-preview"><span class="dropify-render"></span><div class="dropify-infos"><div class="dropify-infos-inner"><p class="dropify-infos-message">{{ replace }}</p></div></div></div>',
                    filename:        '<p class="dropify-filename"><span class="file-icon"></span> <span class="dropify-filename-inner"></span></p>',
                    clearButton:     '<button type="button" class="dropify-clear">{{ remove }}</button>',
                    errorLine:       '<p class="dropify-error">{{ error }}</p>',
                    errorsContainer: '<div class="dropify-errors-container"><ul></ul></div>'
                }
            });
            
            $('.dropify-mn').dropify({
                messages: {
                    default: 'Glissez-déposez un fichier ici ou cliquez',
                    replace: 'Glissez-déposez un fichier ou cliquez pour remplacer',
                    remove:  'Устгах',
                    error:   'Désolé, le fichier trop volumineux'
                }
          });
              
	    	$scope.notes_data = note_data;
			$scope.form = note_data[0].form;		   	
	    	function init(){
	    		 mainService.withdomain('get','/user/service/note/imp/'+$stateParams.param)
		   			.then(function(data){
		   				$scope.notes_data = data;
		   				$scope.form = data[0].form;		   				
		   		 });
	    	}
	    	
	    	$scope.loadOnSend = function(i) {
	    		if(i==1){
	    			
	    			  var json = [];
	    			
	    			  for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
			   			    if ($scope.notes_data[0].notes[i].issaved != "10" && $scope.notes_data[0].notes[i].isrequired==1) {
			   			    	json.push({"id":$scope.notes_data[0].notes[i].id, "title":$scope.notes_data[0].notes[i].title});
		   			      }
		   			  }
	    		  	  var modal = UIkit.modal("#modal_header_notyet");
	    
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
	    		}	
		    };
		    
/*	    	$scope.loadOnSend = function(i) {
	    	  	 var modal = UIkit.modal("#modal_header_notyet");
	     		 mainService.withdomain('get','/logic/valform/'+$stateParams.param)
		   			.then(function(data){
		   				console.log(data);
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
	    	  */

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
            $scope.openNote = function($event,$parentIndex,$index,note) {
                $event.preventDefault();             
                $scope.noteActive = true;
                
                $('.notes_list').children('li').removeClass('md-list-item-active');

                $($event.currentTarget).parent('li').addClass('md-list-item-active');
                var count=0;
                angular.forEach($scope.notes_data[0].notes, function(value, key){   
	                if(value.inptype == note.inptype){
	                	count++;
	                }
                });
                if($scope.xonoff){
              	  if(note.decision==2 || note.decision==0 || note.decision==undefined){
                    	$scope.fileUp=true;
                    }
                    else if(note.decision==1){
                    	$scope.fileUp=false;
                    }
                }
                if(note.isform==1){
                	$scope.fileExt="pdf";                	
                }
                
                $scope.note_form = {
                    parentIndex: $parentIndex,
                    index: $index,
                    id: note.id,
                    decision:note.decision,
                    title: note.title,
                    content: note.content,
                    comm:note.comment,
                    images: note.images,
                    isform: note.isform,
                    planid:$stateParams.param,
                    atfile:note.file,
                    tabid:note.inptype,
                    notesize:count
                };
                angular.element($window).resize();
            };

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
            
		    $scope.showExcelPopup = function(){
         		UIkit.modal("#excel_popup").show();
         		$scope.xslmFile=[];
         		//$timeout($scope.CallMe, 3000);
         	}
		    
		    $scope.getExcelFiles = function ($files) {
                if ($files != null && $files != undefined && $files.length > 0){
                	$scope.xslmFile = $files[0];
                }
            };
            
            $scope.uploadExcelForm = function(){
            	if ($scope.xslmFile != null && $scope.xslmFile != undefined){
            		$rootScope.content_preloader_show();
            		Upload.upload({
            			url: '/import/upload/'+$stateParams.param,
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
				                  /*  file.result = response.data;
				                	$scope.note_form.images.push(response.data.atdata[0]);
				   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');*/
				   					
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
  	                  /*  file.result = response.data;
  	                	$scope.note_form.images.push(response.data.atdata[0]);
  	   					sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');*/
  	   					
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
	   					init();
	   					$scope.note_form.images.push(data.atdata[0]);
	   					formdata = new FormData();
	   					angular.element('.dropify-clear').triggerHandler('click');
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
		    var modal = UIkit.modal("#zero_report");
            
            $scope.showZeroReportModal = function(){
            	//$scope.noteActiveZero = false;
            	modal.show();
            }
            
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
            
	         $scope.submitZeroForm = function(event, zeroid) {
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
		   					$scope.notes_data[0].notes[$scope.note_form.index].issaved="10";
			   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
			   				init();
		   				}
			   				
	   			});	
                
                         
            }
          
    	    $scope.wysiwyg_editor_content ="sdvavasvasv";
              
            $scope.wysiwyg_editor_options = {
               customConfig: '../../assets/js/custom/ckeditor_config.js'
            }
        	
        	$scope.content={
        			preface:''
        	}
            
       
		     }
        }
    ]);