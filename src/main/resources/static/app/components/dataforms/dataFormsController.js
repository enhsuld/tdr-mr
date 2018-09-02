angular
    .module('altairApp')
    	.controller("dataFormsController",['$scope','user_data','mainService','divisions','report_types','$ocLazyLoad',function ($scope,user_data,mainService,divisions,report_types,$ocLazyLoad) {
    		
    		$scope.divisions = divisions;
    		$scope.report_types = report_types;
    		
    		$scope.filter_data = {};
    		
    		$scope.forms = [];
    		
    		$scope.load_url = "";
    		
    		$scope.division_config = {
                create: false,
                maxItems: 1,
                placeholder: 'Хэлтэс сонгох...',
                valueField: 'id',
                labelField: 'name',
                searchField: 'text'
            };
    		
    		$scope.reporttype_config = {
                create: false,
                maxItems: 1,
                placeholder: 'Төрөл сонгох...',
                valueField: 'id',
                labelField: 'name',
                searchField: 'text'
            };
    		
    		$scope.form_config = {
                create: false,
                maxItems: 1,
                placeholder: 'Маягт сонгох...',
                valueField: 'id',
                labelField: 'name',
                searchField: 'text'
            };
    		
    		$scope.getFormNotes = function(){
    			if ($scope.filter_data.divisionid != undefined && $scope.filter_data.divisionid > 0 && $scope.filter_data.reporttype != undefined && $scope.filter_data.reporttype > 0){
    				mainService.withdata("POST","/getFormNotes",$scope.filter_data).then(function(data){
    					$scope.forms = data;
    				});
    			}
    			else{
    				$scope.forms = [];
    			}
    			
    		}
    		
    		$scope.loadFormDatas = function(){
    			$scope.load_url = null;
    			if ($scope.filter_data.formid > 0){
    				$ocLazyLoad.load('/app/components/dataforms/'+$scope.filter_data.formid+'/controller.js').then(function(){
    					$scope.load_url = '/app/components/dataforms/'+$scope.filter_data.formid+'/view.html';
    				});
    			}
    		}
    	}]);