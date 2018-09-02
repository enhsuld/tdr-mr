angular
    .module('altairApp')
    .controller('downloadReportCtrl', [
        '$rootScope',
        '$scope',
        '$interval',
        '$window',
        '$timeout',
        'variables',
        'sweet',
        '$http',
        'user_data',
        function ($rootScope,$scope,$interval,$window,$timeout,variables,sweet,$http, user_data) {
            $scope.user_data = user_data;
        	$scope.downloadReport = function(event){
                $(event.currentTarget).addClass("disabled");
                $(event.currentTarget).prop('disabled',true);
                $(event.currentTarget).text('Түр хүлээнэ үү…');
                $(event.currentTarget).parent().children("a").click();
            };

        }
    ])

;