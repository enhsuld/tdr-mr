angular
    .module('altairApp')
    .controller('configCtrl', [
        '$scope',
        '$rootScope',
        'utils',
        '$stateParams',
        'mainService',
        function ($scope,$rootScope,utils, $stateParams,mainService) {        	
            var $wizard_advanced_form = $('#wizard_advanced_form');

           
            $wizard_advanced_form
            .parsley()
            .on('form:validated',function() {
                $scope.$apply();
            })
            .on('field:validated',function(parsleyField) {
                if($(parsleyField.$element).hasClass('md-input')) {
                    $scope.$apply();
                }
            });

            
            
        	 mainService.withdomain('get','/user/service/resourse/bundled/'+$stateParams.param)
	   			.then(function(data){
	   				console.log(data);
	   				$scope.newIssue.assignee.options=data;
	   				$scope.selectize_planets_options =data;
	   				$scope.additional = [];
   			});	
        	 
            $scope.finishedWizard = function() {
                 var form_serialized = JSON.stringify( utils.serializeObject($wizard_advanced_form), null, 2 );
                 UIkit.modal.alert('<p>Wizard data:</p><pre>' + form_serialized + '</pre>');
            };

        }
    ]);