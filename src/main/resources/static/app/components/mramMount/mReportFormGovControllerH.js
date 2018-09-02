angular
    .module('altairApp')
    .controller('annualReportHCtrl', [
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
        'app_steps',
        'user_data',
        'note_data',
        'tz',
        'sgt',
        '$ocLazyLoad',
        function ($scope,$rootScope,$window, $state,$stateParams, mainService,fileUpload,sweet,org_data,sel_data,app_status,app_steps,user_data,note_data,tz,sgt,$ocLazyLoad) {        	
        	
        	 $scope.stdes1=false;
             $scope.stdes2=false;
             $scope.stdes3=false;
             $scope.stdes4=false;
             $scope.stdes5=false;
             $scope.stdes6=false;
             $scope.config=null;
             $scope.cann=false;             
             $scope.sgt=sgt;
             console.log($scope.sgt);
             console.log(user_data);
             
             if($scope.sgt.repstatusid==7){
            	 $scope.geoplan=false;
                 
                 $scope.geo=false;
                 if(sgt.repstepid==8){
                	 if(user_data.step==9){
                		 $scope.geo=true;
                	 }
                 }
                 $scope.zardal=false;
                 if(sgt.repstepid==9){
                	 if(user_data.step==10){
                		 $scope.zardal=true;
                	 }
                 }
                 
                 if(sgt.repstepid==1 && user_data.group==6){
                	 $scope.geoplan=true;
                 }
             }
            
            
        	 var inpt=[
       			  {"text":"Хүлээн авах хэсэг","value": 8,"tabid": 8},    		          
  		          {"text":"Геологийн хэсэг","value": 9 ,"tabid": 9},
  		          {"text":"Зардлын доод хэмжээ","value": 10, "tabid": 10}
  	          ];
        	 
          	 $scope.inpt_options=inpt;
          	 $scope.userStep=user_data.step;
          	 $scope.user=user_data;
          	 
             $scope.randomProgress = function($event) {
                 var rand = Math.floor((Math.random() * 100) + 1);
                 $($event.currentTarget).closest('.md-card').attr('card-progress',rand);
             }
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

 	    	$scope.org_data=org_data;
 	              
            $scope.notes_data = note_data;
 			$scope.mns = note_data[0].mns;
 			$scope.mcom = note_data[0].mcom;
 			$scope.list=note_data[0].notes;
 			
 			/*var tabs=note_data[0].tabs;
 		    angular.forEach(tabs,function(value,index){
 		    	$('#tabid'+value.ext).removeClass('uk-disabled');		   			    	
             })   */                
 	    	
 	    	$scope.tz = tz[0];
 	    	function init(){
 	    		 mainService.withdomain('get','/user/service/note/imp/gov/'+$stateParams.param)
 		   			.then(function(data){
 		   				$scope.notes_data = data;
 		   				$scope.mns = data[0].mns;
 		   				var tabs=data[0].tabs;
 		   			    angular.forEach(tabs,function(value,index){
 		   			    	$('#tabid'+value.ext).removeClass('uk-disabled');		   			    	
 		                })
 		   			});	
 	    	}
 	    	initdes();
 	    	$scope.tabdes=false;
 	    	console.log($scope.userStep);
 	    	function initdes(){
 	    		 mainService.withdomain('get','/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/'+$stateParams.param)
 		   			.then(function(data){
 		   				if(data.repstatusid==7 && $scope.userStep==data.repstepid) {
 		   					$scope.tabdes=true; 		   					
 		   				}
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
 	    	
 	    	$scope.submitTabDesicion = function(i) {	    		
 	    		   mainService.withdata('put','/logic/submitTabDesicion/'+i, $scope.des)
 		   			.then(function(data){		   				
 		   				sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');	
 			   			init();	
 	   			});	        
             }
 	    	
 	    	
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
 	    	
 	    	$scope.openDeactive = function(){
 	    		 $scope.noteActive = false;
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
                     tabid=$scope.userStep;
                 var data = JSON.stringify($scope.note_form, null, 2);
                 if($scope.note_form.desicion != 1 && ($scope.note_form.comment!=null && $scope.note_form.comment!="")){
               
                     mainService.withdata('put','/logic/submitReportComment', data)
 			   			.then(function(data){	
 			   				$scope.notes_data[0].notes[$scope.note_form.index].decision=$scope.note_form.desicion;
 			   				$scope.note_form.desicion=$scope.noteForm.desicion;
 			   				$scope.note_form.comm.push(data.comdata[0]);
 			   				$scope.mns.push(data.comdata[0]);
 			   				$scope.note_form.comment='';
 			   				
 			   				var tabid=data.comdata[0].tabid;
 			   				$('#submit_message').parent().removeClass('md-input-filled');
 			   				if(data.comdata[0].step){
 			   					sweet.show('Анхаар', 'Дараагийн шатанд шилжлээ.', 'success');	
 			   					var stp=data.comdata[0].tabid-1;
 			   					$state.go($rootScope.previousState.name);	
 			   				}
 			   				else{
 			   					sweet.show('Анхаар', 'ААН-рүү засварт буцлаа', 'success');	
 			   					var stp=data.comdata[0].tabid-1;
 			   					//$state.go('restricted.pages.step'+$stateParams.id);	
 			   				}
 		   			});	 
                 }
                 else{
                 	if($scope.note_form.desicion == 1){
                 		mainService.withdata('put','/logic/submitReportComment', data)
 				   			.then(function(data){	
 				   				$scope.notes_data[0].notes[$scope.note_form.index].decision=$scope.note_form.desicion;
 				   				$scope.note_form.desicion=$scope.noteForm.desicion;
 				   				$scope.note_form.comm.push(data.comdata[0]);
 				   				$scope.mns.push(data.comdata[0]);
 				   				$scope.note_form.comment='';
 				   				var tabid=data.comdata[0].tabid;
 				   				$('#submit_message').parent().removeClass('md-input-filled');
 				   				if(data.comdata[0].step){
 				   					sweet.show('Анхаар', 'Дараагийн шатанд шилжлээ.', 'success');	
 				   					var stp=data.comdata[0].tabid-1;
 				   					$state.go($rootScope.previousState.name);	
 				   				}
 				   				//init();
 			   			});	 
                     }else{
                     	sweet.show('Анхаар', 'Мэдээлэл дутуу байна.', 'warning');	
                     }
                 }
                          
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
                 $scope.note_form = {
                     parentIndex: $parentIndex,
                     index: $index,
                     id: note.id,
                     decision:note.decision,
                     title: note.title,
                     content: note.content,
                     comm:note.comment,
                     images: note.images,
                     issaved:note.issaved,
                     planid:$stateParams.param,
                     atfile:note.file,
                     tabid:note.inptype,
                     notesize:count,
                     isform: note.isform
                 };
                 console.log($scope.note_form);
                 console.log(note);
                 angular.element($window).resize();
                 if (note.isform == 1){
                 	$scope.loadFormDatas(note.id);
                 }
             };

             $scope.loadFormDatas = function(id){
     			$scope.loadFormURL = null;
     			if (id > 0){
     				$ocLazyLoad.load('/app/components/dataforms/'+id+'/controller.js').then(function(){
     					$scope.loadFormURL = '/app/components/dataforms/'+id+'/view.html';
     				});
     			}
     		}
             
             $scope.openClose=function(){
            	 $scope.noteActive = false;
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