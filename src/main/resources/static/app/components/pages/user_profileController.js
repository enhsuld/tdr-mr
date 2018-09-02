angular
    .module('altairApp')
    .controller('user_profileCtrl', [
        '$rootScope',
        '$scope',
        'user_data',
        'org_data',
        function ($rootScope,$scope,user_data,org_data) {
            $scope.user_data = user_data[0];
            $scope.org_data = org_data;
            //$scope.user_data_contacts = user_data[0].contact;

        }
    ])
;