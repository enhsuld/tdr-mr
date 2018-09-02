angular
    .module('altairApp')
    	.controller("userCtrl",['$scope','$state','p_org','p_div','p_role','p_group',
	        function ($scope,$state,p_org,p_div,p_role,p_group) {       	
    		  /*  var aj=p_uni;
	    		var init={"text":"ROOT","value":"0"};	    	
				aj.push(init);*/
			
    			var posarr=[];
    			var pos1={"text":"Удирдлага","value":1};
    			var pos2={"text":"Ахлах мэргэжилтэн","value":2};
    			var pos3={"text":"Мэргэжилтэн","value":3};
    			var pos4={"text":"Бусад","value":0};
    			posarr.push(pos1);
    			posarr.push(pos2);
    			posarr.push(pos3);
    			posarr.push(pos4);
    			
    			$scope.update=function(item){    				
    				$state.go('restricted.pages.newMetaUser',{userid:item.id});    		   
    			}
    			
    			var steps=[{"text":"Хүлээн авах хэсэг","value":1},{"text":"Нөөцийн хэсэг","value":2},{"text":"Технологийн хэсэг","value":3},{"text":"Бүтээгдэхүүн борлуулалтын хэсэг","value":4},{"text":"Эдийн засгийн хэсэг","value":5},{"text":"Эцсийн шийдвэр","value":6},{"text":"Хүлээн авах","value":8},{"text":"Геологийн хэсэг","value":9},{"text":"Зардлын доод хэмжээ","value":10}]
	        	$scope.domain="com.peace.users.model.mram.LutUsers.";
	            $scope.puserGrid = {
	                dataSource: {
	                   
	                    transport: {
	                    	read:  {
	                            url: "/user/angular/LutUsers",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            data:{"custom":"where ispublic=1"}
	                        },
	                        update: {
	                            url: "/user/service/editing/update/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".user  .k-grid").data("kendoGrid").dataSource.read(); 
	                    		}
	                        },
	                        destroy: {
	                            url: "/user/service/editing/delete/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST"
	                        },
	                        create: {
	                            url: "/user/service/editing/create/"+$scope.domain+"",
	                            contentType:"application/json; charset=UTF-8",                                    
	                            type:"POST",
	                            complete: function(e) {
	                            	$(".user .k-grid").data("kendoGrid").dataSource.read(); 
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
	                             fields: {   
	                             	id: { type: "number", editable: false,nullable: false},
	                             	lpreg: { type: "string",  validation: { required: true } },	                             	
	                             	famnamemon: { type: "string"},
	                             	famnameeng: { type: "string"},
	                             	givnamemon: { type: "string"},
	                             	givnameeng: { type: "string"},
	                             	email: { type: "string"},
	                             	groupid: { type: "number"},
	                             	divisionid: { type: "number"},
	                            	positionid: { type: "number",defaultValue:0},
	                             	mobile: { type: "string"},
	                            	roleid: { type: "number", validation: { required: true} },	                             	
	                            	username: { type: "string", validation: { required: true} },
	                            	userpass: { type: "string", validation: { required: true} },	                            
	                             	isactive: { type: "boolean"},
	                            	ispublic: { type: "number",defaultValue:1 },
	                            	stepid: { type: "number",defaultValue:0 }
	                             }
	                         }
	                     },
	                    pageSize: 7,
	                    serverPaging: true,
	                    serverSorting: true,
	                    serverFiltering: true
	                },
	                toolbar: ["create"],
	                filterable: {
	                	mode:'row'
	                },
	                sortable: true,
	                resizable: true,
	                columnMenu:true, 
	                pageable: {
	                    refresh: true,
	                    pageSizes: true,
	                    buttonCount: 5
	                },
	                columns: [
	                          { field:"lpreg", title: "Байгууллага",values:p_org, locked: true,lockable: false,width: 150 },
	                          { field:"divisionid", title: "Хэлтэс",values:p_div, locked: true,lockable: false,width: 250 },
	                          { field:"positionid", title: "Албан тушаал", locked: true,lockable: false, values:posarr, width: 150 },
	                          { field:"famnamemon", title: "Овог",width: 150},
	                          { field:"givnamemon", title: "Нэр",width: 150 },
	                          { field:"famnameeng", title: "Surname" ,width: 150},	                          
	                          { field:"givnameeng", title: "Name",width: 150 },	                        
	                          { field:"mobile", title: "Утас",width: 150 },
	                          { field:"groupid", title: "Group",width: 150, values:p_group},
	                          { field:"email", title: "И мэйл",width: 150 },
	                          { field:"roleid", title: "Эрх" ,values:p_role,width: 150},	 
	                          { field:"stepid", title: "Ажиллах үе шат" ,values:steps,width: 150},	    
	                          { field:"username", title: "Нэвтрэх нэр" ,width: 150},	                          
	                          { field:"userpass", title: "Нууц үг" ,width: 150},  
	                          { field:"isactive",title: "Идэвхитэй эсэх" ,width: 150},  
	                          { 
	                          	 template: kendo.template($("#update").html()),  width: "250px" 
	                                
	                           },
	                          { command: ["edit","destroy"], title: "&nbsp;", width: "250px" }],
	                      editable: "popup"
	               };
            
	        }
    ]);
