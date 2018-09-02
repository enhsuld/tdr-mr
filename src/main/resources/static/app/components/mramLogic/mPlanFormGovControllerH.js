angular
    .module('altairApp')
    .controller('annualFormCtrl', [
        '$scope',
        '$rootScope',
        '$window',
        '$state',
        '$stateParams',
        'mainService',
        'fileUpload',
        'sweet',
        'org_data',  
        'sel_data',
        'app_status',
        'user_data',
        'note_data',
        'tz',
        'xonoff',
        function ($scope,$rootScope,$window, $state,$stateParams, mainService,fileUpload,sweet,org_data,sel_data,app_status,user_data,note_data,tz,xonoff) {        	

      
        	console.log(user_data);
        	console.log(tz[0]);
	    	$scope.org_data=org_data;
	        $scope.tab=user_data.tab;
	        $scope.filter_status_options = [];
	    	$scope.openDeactive = function(){
	    		 $scope.noteActive = false;
	    	}
            $scope.noteActive = false;

	    //    $scope.xonoff = xonoff[0].xxx;
	  //  	$scope.xonoff=tz[0].xxx;
	        $scope.stdes1=true;
            
	        console.log($scope.xonoff);
	        
           /* if(user_data.step==7){
            	$scope.stdes1=true;
            }*/
            
            if(user_data.group==6){
            	$scope.xonoff=true;
            	$scope.stdes1=true;
            	$scope.tab=true;
            }
	          $scope.filter_status_config = {
	              create: false,
	              valueField: 'value',
	              labelField: 'text',
	              placeholder: 'Status...'
	          };
	
	          $scope.filterData = {
	              status: ["in_stock","out_of_stock","ships_3_5_days"]
	          };
	          
	          console.log(app_status);
	    	                              
	    	$scope.app_status = app_status;
	    	//$scope.app_steps = app_steps;
	    	
	    	
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

            // colors
            $scope.chat_colors = 'chat_box_colors_d';

            $scope.changeColor = function($event,colors) {
                $event.preventDefault();
                $scope.chat_colors = colors;
                $($event.currentTarget)
                    .closest('li').addClass('uk-active')
                    .siblings('li').removeClass('uk-active');
            };
                    
	    	
	    	$scope.tz = tz[0];		 
	    	$scope.notes_data = note_data;
			$scope.mns = note_data[0].mns;
				
	    	function init(){
	    		 mainService.withdomain('get','/user/service/note/imp/gov/'+$stateParams.param)
		   			.then(function(data){
		   				$scope.notes_data = data;
		   				$scope.mns = data[0].mns;
		   			});	
	    	}
	    	
	    	$scope.main={
	    			appstatus:0,
	    			appstep:0,
	    			comtext:"",
	    			appid:$stateParams.param
	    	}
	    	
	    	$scope.submitMainForm = function() {	    		
	    		   mainService.withdata('put','/logic/submitMainComment/', $scope.main)
		   			.then(function(data){		   				
		   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
			   				
	   			});	 
                         
            }
	    	
    	    $scope.selectize_c_data = {
                    options: []
            };
	    	  
    	    $scope.selectize_c_config = {
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
	    	  
	    	$scope.selectize_c_data.options= sel_data;    
	    	
	    	$scope.des={
	    			appstatus:0,
	    			appstep:0,
	    			comtext:"",
	    			appid:$stateParams.param
	    	}
	    	
	    	$scope.submitMainDesicion = function() {	    		
	    		   mainService.withdata('put','/logic/submitMainComment/', $scope.des)
		   			.then(function(data){		   				
		   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
		   				$state.go('restricted.pages.step6');	
	   			});	     
            }
	    	
            $scope.noteActive = false;

            $scope.onSelect = function(e) {
                var message = $.map(e.files, function(file) { return file.name; }).join(", ");
            }
            

            // save note
            $scope.saveComment = function($event) {
               $event.preventDefault();         
                // get variables from active note
                var parentIndex = $scope.note_form.parentIndex,
                    index = $scope.note_form.index,                   
                    file_form = $scope.noteForm.atfile;
                    planid = $stateParams.param;
                    desicion = $scope.noteForm.desicion;
                    comment = $scope.noteForm.comment;

                var data = JSON.stringify($scope.note_form, null, 2);
                console.log($('#submit_comment').val());
                console.log(data);
                if($('#submit_comment').val()!="" && $('#submit_message').val()!=""){
                    mainService.withdata('put','/logic/submitComment/', data)
			   			.then(function(data){
			   				$scope.notes_data[0].notes[$scope.note_form.index].decision=$scope.note_form.desicion;
			   				$scope.note_form.desicion=$scope.noteForm.desicion;
			   				$scope.note_form.comm.push(data.comdata[0]);
			   				$scope.note_form.comment="";
			   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
				   				
		   			});	 
                }
                else{
                	sweet.show('Анхаар', 'Мэдээлэл дутуу байна.', 'warning');	
                }
                         
            }

            // open a note
            $scope.openNote = function($event,$parentIndex,$index,note) {
                $event.preventDefault();             
                $scope.noteActive = true;
                
                $('.notes_list').children('li').removeClass('md-list-item-active');

                $($event.currentTarget).parent('li').addClass('md-list-item-active');

                $scope.note_form = {
                    parentIndex: $parentIndex,
                    index: $index,
                    id: note.id,
                    title: note.title,
                    decision:note.decision,
                    content: note.content,
                    comm:note.comment,
                    images: note.images,
                    planid:$stateParams.param,
                    atfile:note.file
                };
                angular.element($window).resize();
            };

       
            
            $scope.removeAttach = function(obj) {
            	var parentIndex = $scope.note_form.parentIndex,
                index = $scope.note_form.index;            
		        sweet.show({
		        	   title: 'Confirm',
   		            text: 'Delete this file?',
   		            type: 'warning',
   		            showCancelButton: true,
   		            confirmButtonColor: '#DD6B55',
   		            confirmButtonText: 'Yes, delete it!',
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
 				   				sweet.show('Deleted!', 'The file has been deleted.', 'success');
				   			});	
 		            }else{
 		                sweet.show('Cancelled', 'Your imaginary file is safe :)', 'error');
 		            }    		
		        });
		    };
            

            $scope.noteForm = {
            		atfile: "default"
                };
  
            var formdata = new FormData();
            $scope.getTheFiles = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("files", value);
                });
            };
	    	
        }
    ]);

Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
};