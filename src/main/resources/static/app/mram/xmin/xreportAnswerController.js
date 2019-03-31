angular
    .module('altairApp')
    .controller('zeroAnswerCtrl', [
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
        'sel_data',
        'user_data',
        function ($scope,$rootScope,utils, $timeout,$window, Upload ,$stateParams, mainService,fileUpload,sweet,org_data,$state,sgt,xonoff,note_data,sel_data,user_data) {        	
        	var inpt=[
    		          {"text":"Хавсрах материал","value":11 }
		          ];
         	
        	$scope.inpt_options=inpt;
        	$scope.role=user_data.role;
        	$scope.step=user_data.step;
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

            mainService.withdomain('get','/user/service/lp/detail/'+$stateParams.param)
                .then(function(data){
                    $scope.org_data=data;

			});

   	        $scope.selectize_a_options= sel_data;    
        	
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
	
	    	var drEvent = $('.dropify').dropify();
	    	$scope.sgt=sgt[0];
	    	$scope.mid=$stateParams.param;
	    	$scope.xonoff = xonoff[0].xxx;
	    	
	    	function init(){
	    		 mainService.withdomain('get','/user/service/note/imp/'+$stateParams.param)
		   			.then(function(data){
		   				$scope.notes_data = data;
		   				$scope.form = data[0].form;
		   				
		   			});	
	    	}

	    	$scope.des={
	    			appid:$stateParams.param
	    	}
	    	
	    	$scope.submitMainDesicion = function() {	    		
	    		   mainService.withdata('put','/logic/submitXreport', $scope.des)
		   			.then(function(data){		   				
		   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
		   				$state.go('restricted.pages.xreportmin');	
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

	    	$scope.openDeactive = function(){
	    		 $scope.noteActive = false;
	    	}
	    	$scope.openClose = function(){
	    		 $scope.noteActive = false;
	    	}
            $scope.noteActive = false;

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
            };

        	$scope.content={
        			preface:''
        	}
       
        }
    ]);