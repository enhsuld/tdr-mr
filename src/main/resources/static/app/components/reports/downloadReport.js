angular
    .module('altairApp')
    .controller('downloadReportCtrl', [
        '$rootScope',
        '$scope',
        '$interval',
        '$window',
        '$timeout',
        'variables',
        'mainService',
        'sweet',
        '$http',
        'user_data',
        function ($rootScope,$scope,$interval,$window,$timeout,variables,mainService,sweet,$http, user_data) {
            $scope.user_data = user_data;
            var modalLoading = UIkit.modal("#modalLoading", {
                modal: false,
                keyboard: false,
                bgclose: false,
                center: false
            });
        	$scope.downloadReport = function(reportType,id,fname){
                var link = document.createElement('a');
                link.href = '/api/excel/download/' + reportType+'/'+id+'/'+$scope.yearSelected.value+'/'+fname;
                link.download = "Filename";
                link.click();
            };
            $scope.downloadPlan = function(reportType,id,fname){
                var link = document.createElement('a');
                link.href = '/api/excel/download/' + reportType+'/'+id+'/'+$scope.yearSelected.value+'/'+fname;
                link.download = "Filename";
                link.click();
            };

            $scope.downloadNewPlan = function(reportType,id,fname){
                var link = document.createElement('a');
                link.href = '/api/excel/new/download/' + reportType+'/'+id+'/'+$scope.yearSelected.value+'/'+fname;
                link.download = "Filename";
                link.click();
            };
        }
    ])

;