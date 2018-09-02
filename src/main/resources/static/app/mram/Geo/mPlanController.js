angular
    .module('altairApp')
    .controller('annualFormCtrl', [
        '$scope',
        '$rootScope',        
        'utils',     
        '$timeout',
        '$window',
        'Upload',
        '$stateParams',
        'mainService',
        'fileUpload',
        'sweet',
        'org_data',   
        '$state',
        'sgt',
        'xonoff',
        'note_data',
        'user_data',
        'sel_data',
        function ($scope,$rootScope,utils, $timeout,$window, Upload ,$stateParams, mainService,fileUpload,sweet,org_data,$state,sgt,xonoff,note_data,user_data,sel_data) {        	
        	var inpt=[
    		          {"text":"Хүлээн авах хэсэг","value":2 },
    		          {"text":"Бичвэр мэдээлэл","value": 20},
    		          {"text":"Нөөцийн хэсэг","value": 3},    		          
    		          {"text":"Технологийн хэсэг","value": 4},
    		          {"text":"Бүтээгдэхүүн борлуулалтын хэсэг","value": 5},
    		          {"text":"Эдийн засгийн хэсэг","value": 6}
		          ];
        	
        
        	$scope.geoplan=false;
                	
        	var a_data = $scope.selectize_a_options = sel_data;
    			
   			 $scope.selectize_a_config = {
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
   	                    option: function(a_data, escape) {
   	                        return  '<div class="option">' +
   	                            '<span class="title">' + escape(a_data.text) + '</span>' +
   	                            '</div>';
   	                    }
   	                }
   	            };
            
              $scope.selectize_a_config = {
  	                create: false,
  	                maxItems: 1,
  	                placeholder: 'Сонголт...',
  	                optgroupField: 'parent_id',
  	                optgroupLabelField: 'text',
  	                optgroupValueField: 'ogid',
  	                valueField: 'value',
  	                labelField: 'text',
  	                searchField: 'text'
  	            };

              
  	        $scope.selectize_a_data = {
                  options: []
              };
  	        
  	        $scope.selectize_a_data.options= sel_data;   
        	        	
	    	var drEvent = $('.dropify').dropify();

	    	$scope.mid=$stateParams.param;
	    	$scope.xonoff = xonoff[0].xxx;
		    	

	    	function init(){
	    		 mainService.withdomain('get','/user/service/note/imp/'+$stateParams.param)
		   			.then(function(data){
		   				$scope.notes_data = data;
		   				$scope.form = data[0].form;
		   				
		   			});	
	    	}

	    	
	    	$scope.planid=$stateParams.param;
	    	$scope.notes_data = note_data;
			$scope.form = note_data[0].form;
	    	
			$scope.mcom = note_data[0].mcom;
			
		    for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
   			    if ($scope.notes_data[0].notes[i].decision == 2) {
   			        for (var y = 0; y < $scope.notes_data[0].notes.length; y++) {
   			    	  if($scope.notes_data[0].notes[y].inptype> $scope.notes_data[0].notes[i].inptype){
   			    		 $scope.notes_data[0].notes[y].decision=2;
   			    	  } 
   			        }
		        }
		    }

		    
		    var formdata = new FormData();
            $scope.getTheFiles = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("files", value);
                });
            };
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
			    
	    	$scope.openDeactive = function(){
	    		 $scope.noteActive = false;
	    	}
	    	$scope.openClose = function(){
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
                	  if(note.decision==2 || note.decision==0){
                      	$scope.fileUp=true;
                      }
                      else if(note.decision==1){
                      	$scope.fileUp=false;
                      }
                }
                var count=0;
                angular.forEach($scope.notes_data[0].notes, function(value, key){   
	                if(value.inptype == note.inptype){
	                	count++;
	                }
                });
                $scope.note_form = {
                    parentIndex: $parentIndex,
                    index: $index,
                    id: note.id,
                    title: note.title,
                    content: note.content,
                    decision:note.decision,
                    help: note.help,
                    comm:note.comment,
                    isform: note.isform,
                    decision:note.decision,
                    issaved:note.issaved,
                    size: note.size,
                    images: note.images,
                    planid:$stateParams.param,
                    tabid:note.inptype,                  
                    notesize:$scope.notes_data[0].notes.length,
                    atfile:note.file
                };
                console.log($scope.note_form);
                angular.element($window).resize();
            };
            
            $scope.notes_data = note_data;
 			$scope.mns = note_data[0].mns;
 			$scope.mcom = note_data[0].mcom;
 			$scope.list=note_data[0].notes;
 			$scope.sgt=sgt;
 			
         	if(sgt.divisionid==3 && sgt.reporttype==3){
        	  	var inpt=[
       			  {"text":"Бичвэр мэдээлэл","value": 2},    		          
  		          {"text":"Хавсаргах мэдээлэл","value": 3}
  	            ];           	
        	  	$scope.inpt_options=inpt;
        	  	if(user_data.group==6){
        	  		$scope.geoplan=true;
                    $scope.saveComment = function($event) {
                         $event.preventDefault();      
                         var data = JSON.stringify($scope.note_form, null, 2);
                         if($scope.note_form.desicion != undefined && ($scope.note_form.comment!=null && $scope.note_form.comment!="")){                       
                             mainService.withdata('put','/logic/submitGeoPlanComment', data)
         			   			.then(function(data){	
         			   				$scope.notes_data[0].notes[$scope.note_form.index].decision=$scope.note_form.desicion;
         			   				//$scope.note_form.desicion=$scope.noteForm.desicion;
         			   				$scope.note_form.comm.push(data.comdata[0]);
         			   				$scope.mns.push(data.comdata[0]);
         			   				$scope.note_form.comment='';
         			   				$scope.note_form.desicion=0;
         			   				var tabid=data.comdata[0].tabid;
         			   				$('#submit_message').parent().removeClass('md-input-filled');
         			   				if(data.comdata[0].step){
         			   					sweet.show('Анхаар', 'Тайлан/Төлөвлөгөөний түүхрүү шилжлээ.', 'success');	
         			   					$state.go($rootScope.previousState.name);	
         			   				}
         			   				else{
         			   					if($scope.note_form.desicion==2 || $scope.note_form.desicion==3){
         			   						sweet.show('Анхаар', 'ААН-рүү засварт буцлаа', 'success');	 
         			   						$state.go($rootScope.previousState.name);	
         			   					}
         			   				}
         		   			});	 
                         }
                         else{
                        	 sweet.show('Анхаар', 'Мэдээлэл дутуу байна.', 'warning');	
                         }
                                  
                     }
        	  	}
        	}


            var lpreg=org_data.lpReg;
            $scope.org_data = org_data;
            
       

        }
    ]);