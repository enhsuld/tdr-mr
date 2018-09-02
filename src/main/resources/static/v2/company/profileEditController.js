angular
    .module('altairApp')
    .controller("profileEditController",['$scope','$state','$stateParams','user_data','org_data','mainService','$filter',
        function ($scope,$state, $stateParams, user_data, org_data, mainService, $filter) {
            $scope.org_data = org_data;
            $scope.user_data = user_data;

            $scope.submitForm = function(){
                //console.log($scope.org_data);
                mainService.withdata('put','/user/service/useredit/',$scope.org_data)
                    .then(function(data){
                        $state.go('restricted.v2.company.dashboard');
                    });
            }
        }]
    );
