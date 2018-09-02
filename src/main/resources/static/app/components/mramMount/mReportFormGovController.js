angular
    .module('altairApp')
    .controller('annualReportCtrl', [
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
        'user_data',
        'note_data',
        'tz',
        'ann',
        'mineralData',
        'rrq',
        'mineType',
        'concent',
        'sgt',
        function ($scope,$rootScope,$window, $state,$stateParams, mainService,fileUpload,sweet,org_data,sel_data,user_data,note_data,tz,ann,mineralData,rrq,mineType,concent,sgt) {        	
     
        	$scope.sgt=sgt;
            $scope.stdes1=false;
            $scope.stdes2=false;
            $scope.stdes3=false;
            $scope.stdes4=false;
            $scope.stdes5=false;
            $scope.stdes6=false;
            $scope.config=ann;
            $scope.todotgol_datas = {};
            if(ann==null){
        		$scope.cann=false;
        	}
        	else{
        		$scope.cann=true;
        		$scope.config=ann;
        	}
            $scope.tinymce_options = {
                skin_url: 'assets/skins/tinymce/material_design',
                toolbar: "undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
                menubar: false,
                plugins: [
                  'advlist autolink lists link charmap print preview anchor',
                  'searchreplace visualblocks code fullscreen',
                  'insertdatetime media table contextmenu paste code'
                ]
            }
            
            var modal = UIkit.modal("#modal_header_footer");
            $scope.form11=function(){
            	  modal.show();
            	  $scope.proleGrid = {
            		      dataSource: {

            		        transport: {
            		          read: {
            		            url: "/user/angular/DataMinPlan1",
            		            contentType: "application/json; charset=UTF-8",
            		            data:{"custom":"where planid='"+$stateParams.param+"'"},
            		            type: "POST"
            		          },
            		          parameterMap: function(options) {
            		            return JSON.stringify(options);
            		          }
            		        },
            		        schema: {
            		          data: "data",
            		          total: "total",
            		          model: {
            		            id: "id",
            		          }
            		        },
            		        /*group: {
            		          field: "annualRegistration.lpName",
            		          dir: "desc"
            		        },*/
            		        pageSize: 1000,
            		        serverPaging: true,
            		        serverFiltering: true,
            		        serverSorting: true,
            		        sort: {
            		          field: "id",
            		          dir: "asc"
            		        }
            		      },
            		      toolbar: ["excel","pdf"],
            		      excel: {
            		          fileName: "Report.xlsx",
            		          allPages: true
            		      },
            		      filterable: false,
            		      sortable: true,
            		      resizable: true,
            		      pageable: {
            		        refresh: true,
            		        pageSizes: false,
            		        buttonCount: 5
            		      },
            		      columns: [{
            		          field: "id",
            		          hidden: true
            		        },{
            		        field: "annualRegistration.lpName",
            		        title: "ААН",
            		        hidden: false,
            		        width: 200
            		      },{
            		          field: "annualRegistration.licenseXB",
            		          title: "Тусгай зөвшөөрлийн дугаар",
            		          hidden: false,
            		          width: 150
            		        }, {
            		        title: "Бүтээгдэхүүн",
            		        columns: [{
            		          field: "dataIndex",
            		          title: "#",
            		          width: 75
            		        }, {
            		          field: "data1",
            		          title: "Үзүүлэлт",
            		          width: 200
            		        }, {
            		          field: "data2",
            		          title: "Х/нэгж",
            		          width: 90
            		        }, {
            		          field: "data3",
            		          title: "Тоон утга",
            		          width: 75
            		        }]
            		      }, {
            		        title: "Ашигт малтмалын нэр",
            		        columns: [{
            		          field: "data4",
            		          title: "№",
            		          width: 75
            		        }, {
            		          field: "data5",
            		          title: "Ашигт малтмалын нэр",
            		          width: 120
            		        }, ]
            		      }, {
            		        title: "Агуулга",
            		        columns: [{
            		          field: "data6",
            		          title: "Х/нэгж",
            		          width: 90
            		        }, {
            		          field: "data7",
            		          title: "Тоон утга",
            		          width: 75
            		        }, ]
            		      }, {
            		        title: "Метал (эрдэс) хэмжээ",
            		        columns: [{
            		          field: "data8",
            		          title: "Х/нэгж",
            		          width: 90
            		        }, {
            		          field: "data9",
            		          title: "Тоон утга",
            		          width: 75
            		        }]
            		      }, ],
            		      editable: false
            		    };
        	}
            
            var mineral_data = $scope.selectize_mineral_options = mineralData;
 			
 			$scope.selectize_mineral_config = {
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
 	                    option: function(mineral_data, escape) {
 	                        return  '<div class="option">' +
 	                            '<span class="title">' + escape(mineral_data.text) + '</span>' +
 	                            '</div>';
 	                    }
 	                }
 	            };
 			
 			var deposidid_data = $scope.selectize_deposidid_options = [];
 			
 			$scope.selectize_deposidid_config = {
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
 	                    option: function(deposidid_data, escape) {
 	                        return  '<div class="option">' +
 	                            '<span class="title">' + escape(deposidid_data.text) + '</span>' +
 	                            '</div>';
 	                    }
 	                }
 	            };
 			
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
 		    var isfl=[{"text":"Тийм","value":1 },{"text":"Үгүй","value": 0}];
            
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
 			
 			var concent_data = $scope.selectize_concent_options = concent;
 			
 			$scope.selectize_concent_config = {
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
 	                    option: function(concent_data, escape) {
 	                        return  '<div class="option">' +
 	                            '<span class="title">' + escape(concent_data.text) + '</span>' +
 	                            '</div>';
 	                    }
 	                }
 	            };
 			
 			 var TwoSource=[{"value":1,"text":"Өөрөө гүйцэтгэх","parentid":1 },{"value": 2,"text":"Гэрээгээр гүйцэтгүүлэх","parentid":1}
				,{"value": 3,"text":"Үгүй","parentid":2}];
			
			var bombtype_data = $scope.selectize_bombtype_options = TwoSource;
			$scope.selectize_bombtype_config = {
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
			          option: function(bombtype_data, escape) {
			              return  '<div class="option">' +
			                  '<span class="title">' + escape(bombtype_data.text) + '</span>' +
			                  '</div>';
			          }
			      }
			  };
 		    mainService.withdomain('get','/user/service/resourse/data/deposididData/'+ann.mineralid)
  			.then(function(data){
  				deposidid_data = $scope.selectize_deposidid_options = data;
  			});	
            
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
	        
          
            if($stateParams.id==1){
        	    if(user_data.step==1){
        	    	$scope.stdes1=true;
        	    	$scope.tabid5=false;
        	    	$scope.tabid6=true;
        	    	$scope.special=true;
        	    }
            }
          
            if($stateParams.id==2){
            	$scope.tabid2=true;
            	$scope.tabid3=false;
            	$scope.tabid4=false;
            	$scope.tabid5=false;
            	$scope.tabid6=true;
            	$scope.tabid7=false;
            	if(user_data.step==2){
                  	$scope.stdes2=true;
                }
            }
            if($stateParams.id==3){
            	$scope.tabid2=true;
            	$scope.tabid3=true;
            	$scope.tabid4=false;
            	$scope.tabid5=false;
            	$scope.tabid6=true;
            	if(user_data.step==3){
            		$scope.stdes3=true;
                }            	
            }
            
            if($stateParams.id==4){
            	$scope.tabid2=true;
            	$scope.tabid3=true;
            	$scope.tabid4=true;
            	$scope.tabid5=false;
            	$scope.tabid6=true;
            	  if(user_data.step==4){
                  	$scope.stdes4=true;
                  }
            }
            
            if($stateParams.id==5){
            	$scope.tabid2=true;
            	$scope.tabid3=true;
            	$scope.tabid4=true;
            	$scope.tabid5=true;
            	$scope.tabid6=true;
                if(user_data.step==5){
                	$scope.stdes5=true;
                }
            }
            if($stateParams.id==6){
            	$scope.tabid2=true;
            	$scope.tabid3=true;
            	$scope.tabid4=true;
            	$scope.tabid5=true;
            	$scope.tabid6=true;
            	$scope.stdes1=false;
            	$scope.stdes2=false;
            	$scope.stdes3=false;
            	$scope.stdes4=false;
            	$scope.stdes5=false;
            	 if(user_data.step==6){
                 	$scope.stdes6=true;
                 }
            	
            	if($stateParams.des!=1){
            	//	 $scope.selectize_a_data.options=[{text: "Татгалзах",value: 3},{text: "Засварт буцаах", value: 2}];
            	}
            
            	
            }
            
            
            if(user_data.step==6){
        		$scope.stdes1=true;
        		$scope.stdes2=true;
        		$scope.stdes3=true;
        		$scope.stdes4=true;
        		$scope.stdes5=true;
        		$scope.tabid6=false;
        		$scope.tabid7=true;
              //	$scope.stdes6=true;
            }
            

	    	$scope.org_data=org_data;
	              
            $scope.notes_data = note_data;
			$scope.mns = note_data[0].mns;
			$scope.mcom = note_data[0].mcom;
			$scope.list=note_data[0].notes;
			
			var tabs=note_data[0].tabs;
		    angular.forEach(tabs,function(value,index){
		    	$('#tabid'+value.ext).removeClass('uk-disabled');		   			    	
            })                   
	    	
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
	    	function initdes(){
	    		 mainService.withdomain('get','/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/'+$stateParams.param)
		   			.then(function(data){
		   				if(data.rejectstep!=null){
		   					var a=data.rejectstep;
		   					if(a==1  && user_data.step==1){
		   						$scope.tabid3=false;
		   						$scope.tabid4=false;
		   						$scope.tabid5=false;
		   						$scope.tabid6=true;
		   						$scope.stdes1=true;
		   					}
		   					if(a==2  && user_data.step==2){
		   						$scope.tabid3=false;
		   						$scope.tabid4=false;
		   						$scope.tabid5=false;
		   						$scope.tabid6=true;
		   						$scope.stdes2=true;
		   					}
		   					if(a==3  && user_data.step==3){
		   						$scope.tabid5=false;
		   						$scope.tabid6=true;
		   						$scope.tabid4=false;
		   						$scope.stdes3=true;
		   						
		   					}
		   					if(a==4  && user_data.step==4){
		   						$scope.stdes4=true;
		   						$scope.tabid5=false;
		   						$scope.tabid6=true;
		   					}
		   					if(a==5 && user_data.step==5){
		   						$scope.stdes5=true;
		   						$scope.tabid6=false;
		   					}
		   					if(data.repstatusid==1){
		   						$scope.tabid3=false;
		   						$scope.tabid4=false;
		   						$scope.tabid5=false;
		   						$scope.tabid6=true;
		   						if(user_data.step==1){
		   							$scope.tabid7=true;
			   						$scope.stdes7=true;
		   						}
		   						else{
		   							$scope.tabid7=false;
			   						$scope.stdes7=false;
		   						}
		   						$scope.selectize_a_data.options=[{text: "Эцэслэх",value: 5}];
		   						a_data = $scope.selectize_a_options =[{text: "Эцэслэх",value: 5}];
		   					}
		   					if(data.repstatusid==5){
		   						$scope.stdes1=false;
		   		        		$scope.stdes2=false;
		   		        		$scope.stdes3=false;
		   		        		$scope.stdes4=false;
		   		        		$scope.stdes5=false;
		   		        		$scope.tabid1=true;
		   		        		$scope.tabid2=true;
		   		        		$scope.tabid3=true;
		   		        		$scope.tabid4=true;
		   		        		$scope.tabid5=true;
		   		        		$scope.tabid6=true;
		   		        		$scope.tabid7=false;
		   		              	$scope.stdes6=false;
		   					}
		   					if(data.repstatusid==1 && $stateParams.id==0){
		   						$scope.stdes1=false;
		   		        		$scope.stdes2=false;
		   		        		$scope.stdes3=false;
		   		        		$scope.stdes4=false;
		   		        		$scope.stdes5=false;
		   		        		$scope.tabid1=true;
		   		        		$scope.tabid2=true;
		   		        		$scope.tabid3=true;
		   		        		$scope.tabid4=true;
		   		        		$scope.tabid5=true;
		   		        		$scope.tabid6=true;
		   		        		$scope.tabid7=false;
		   		              	$scope.stdes6=false;
		   					}
		   					if(data.repstatusid==2){
		   						$scope.stdes1=false;
		   		        		$scope.stdes2=false;
		   		        		$scope.stdes3=false;
		   		        		$scope.stdes4=false;
		   		        		$scope.stdes5=false;
		   		        		$scope.tabid1=true;
		   		        		$scope.tabid2=true;
		   		        		$scope.tabid3=true;
		   		        		$scope.tabid4=true;
		   		        		$scope.tabid5=true;
		   		        		$scope.tabid6=true;
		   		        		$scope.tabid7=false;
			   		        	for(var i=1;i<=6;i++){
			   				    	$('#tabid'+i).removeClass('uk-disabled');		   			    	
			   		            };
		   					}
		   					
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
		   				$state.go('restricted.pages.reportstep6');	
	   			});	 
                         
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
                var json=0;
                for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
  				  if($scope.notes_data[0].notes[i].inptype==note.inptype){  					 
  					  if ($scope.notes_data[0].notes[i].decision == 0) {
		   			    	json=json+1;
   			      	  }
  				  }		   			  
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
                    issaved:note.issaved,
                    planid:$stateParams.param,
                    atfile:note.file,
                    tabid:note.inptype,
                    ccount:json,
                    notesize:count,
                    commentsize:0,
                    commenttrue:0,
                    isform: note.isform
                };
                console.log($scope.note_form);
                angular.element($window).resize();
            };
	    	
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
           
                var noteArray=[];
                var truecount=0
                
                for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
				  if($scope.notes_data[0].notes[i].inptype==$scope.note_form.tabid && $scope.notes_data[0].notes[i].decision!=0){  
					 noteArray.push($scope.notes_data[0].notes[i].id);
				  }	
				  if($scope.notes_data[0].notes[i].inptype==$scope.note_form.tabid && $scope.notes_data[0].notes[i].decision==1){  
					  truecount=truecount+1;
				  }	
 			    } 
                if(!noteArray.contains($scope.note_form.id)){
                	 noteArray.push($scope.note_form.id);
                }
                
                var parentIndex = $scope.note_form.parentIndex,
                index = $scope.note_form.index,                   
                file_form = $scope.noteForm.atfile;
                planid = $stateParams.param;
                desicion = $scope.noteForm.desicion;
                comment = $scope.noteForm.comment;
                $scope.note_form.commentsize=noteArray.length;
                $scope.note_form.commenttrue=truecount;
	          	var json = 0;    			
	            var data = JSON.stringify($scope.note_form, null, 2);
                
                if($scope.note_form.desicion != 1 && ($scope.note_form.comment!=null && $scope.note_form.comment!="")){
              
                    mainService.withdata('put','/logic/reportComment', data)
			   			.then(function(data){	
			   				$scope.notes_data[0].notes[$scope.note_form.index].decision=$scope.note_form.desicion;
			   				$scope.note_form.desicion=$scope.noteForm.desicion;
			   				$scope.note_form.comm.push(data.comdata[0]);
			   				$scope.mns.push(data.comdata[0]);
			   				$scope.note_form.comment="";
			   				var tabid=data.comdata[0].tabid;
			   				$('#submit_message').parent().removeClass('md-input-filled');
			   				if(data.comdata[0].step){
			   					sweet.show('Анхаар', 'Дараагийн шатанд шилжлээ.', 'success');	
			   					var stp=data.comdata[0].tabid-1;
			   					$state.go('restricted.pages.reportstep1'+stp);	
			   				}
			   				else{
			   					if(data.comdata[0].alldes){
			   						sweet.show('Анхаар', 'Тайлан эцсийн шийдвэрлүү шилжлээ. Эцсийн шийдвэр гаргаснаар жагсаалтнаас хасагдана', 'success');
			   					}
			   				}
		   			});	 
                }
                else{
                	if($scope.note_form.desicion == 1){
                		 mainService.withdata('put','/logic/reportComment', data)
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
				   					$state.go('restricted.pages.reportstep'+stp);	
				   				}
				   				else{
				   					if(data.comdata[0].alldes){
				   						sweet.show('Анхаар', 'Тайлан эцсийн шийдвэрлүү шилжлээ. Эцсийн шийдвэр гаргаснаар жагсаалтнаас хасагдана', 'success');
				   					}
				   				}
			   			});	 
                    }else{
                    	sweet.show('Анхаар', 'Мэдээлэл дутуу байна.', 'warning');	
                    }
                }
                         
            }

           

            $scope.submitTodotgol = function(){
            	$scope.todotgol_datas.planid = $stateParams.param;
            	sweet.show({
		        	   title: 'Баталгаажуулалт',
		            text: 'Сонгогдсон маягтуудад тодотгол илгээх хүсэлтийг баталгаажуулах уу?',
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
		        		 mainService.withdata('POST','/submit/todotgol',$scope.todotgol_datas)
				   			.then(function(response){
				   			  if (response.status == true){
				   				sweet.show('Мэдээлэл', 'Амжилттай хадгалагдлаа', 'success');
				   			  }
				   			  else{
				   				sweet.show('Алдаа үүслээ.', 'error');
				   			  }
				   			});
		            }else{
		                sweet.show('Цуцлагдлаа.', 'error');
		            }    		
		        });
            }
            
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
