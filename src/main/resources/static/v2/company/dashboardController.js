angular
    .module('altairApp')
    .controller("dashboardController",['$scope','$state','$stateParams','user_data','org_data','messages','mainService','rep_type','$filter', '$rootScope',
        function ($scope,$state, $stateParams, user_data, org_data, messages, mainService, rep_type, $filter, $rootScope) {
            $scope.org_data = org_data;
            $scope.messages = messages;
            $scope.user_data = user_data;

            $rootScope.toBarActive = true;

            $scope.$on('$destroy', function() {
                $rootScope.toBarActive = false;
            });

            $scope.showMessage = function(news){
                if (news.isread == false){
                    mainService.withdomain("get","/markAsReadMessage/" + news.id)
                        .then(function(response) {
                            if (response == true){
                                news.isread = true;
                                if (news.status == 1){
                                    $scope.unread_alerts = $scope.unread_alerts - 1;
                                }
                                else{
                                    $scope.unread_msgs = $scope.unread_msgs - 1;
                                }
                            }
                        });
                }

                UIkit.modal.alert("<h2>" + news.title + "</h2><p class='uk-overflow-container' style='white-space: pre-line;text-align: justify;'>"+news.description+"<br><span class='uk-text-muted'>"+news.createdat+"</span></p>")
            }

            /*if ($scope.messages.messages.filter(function(x){ return x.isread == false; }).length > 0){
                $scope.showMessage($scope.messages.messages.filter(function(x){ return x.isread == false; })[0]);
            }
            else if ($scope.messages.alerts.filter(function(x){ return x.isread == false; }).length > 0){
                $scope.showMessage($scope.messages.alerts.filter(function(x){ return x.isread == false; })[0]);
            }
*/

            $scope.loadReportData = function(){
                mainService.withdomain("get","/user/service/rs/AnnualRegistrationDashboard/lpReg/"+user_data.lpreg).then(function(data){
                    $scope.report_data = data;
                });
            }

            $scope.repstatuses = [{text: "Хадгалсан", value: 0, class:"uk-badge-primary"}, {text: "Засварт буцаасан",value: 2, class: "uk-badge-danger"}, {text: "Илгээсэн", value: 7, class: "uk-badge-warning"},{text: "Баталгаажсан", value: 1, class: "uk-badge-success"},{text: "Татгалзсан", value: 3, class:"uk-badge-danger"}];

            $scope.divisions = [{text: "УУҮТХ",value: 1}, {text: "НСХ", value: 2}, {text: "ГХХ", value: 3}];

            $scope.getAnStatus = function(item){
                return ($filter('filter')($scope.repstatuses, { value: item.repstatusid }, true).length > 0) ? $filter('filter')($scope.repstatuses, { value: item.repstatusid }, true)[0].text : "";
            }

            $scope.getAnDivision = function(item){
                return ($filter('filter')($scope.divisions, { value: item.divisionid }, true).length > 0) ? $filter('filter')($scope.divisions, { value: item.divisionid }, true)[0].text : "";
            }

            $scope.getReportType = function(typeid){
                return ($filter('filter')(rep_type, { value: typeid }, true).length > 0) ? $filter('filter')(rep_type, { value: typeid }, true)[0].text : "";
            }

            $scope.getAnStatusClass = function(item){
                return ($filter('filter')($scope.repstatuses, { value: item.repstatusid }, true).length > 0) ? $filter('filter')($scope.repstatuses, { value: item.repstatusid }, true)[0].class : "";
            }
        }]
    );
