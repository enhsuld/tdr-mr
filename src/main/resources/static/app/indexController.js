/**
 * Created by TULGAA on 4/7/2017.
 */
angular
    .module('altairApp')
    .controller('indexController', [
        '$scope','mainService','$filter','$rootScope','$http','$state',
        function ($scope,mainService,$filter,$rootScope,$http,$state) {
            $scope.searchForm = {};
            $scope.searchInProgress = false;
            $scope.isSearched = false;
            $scope.searchResult = [];
            $scope.repstatuses = [{name:"Зөвшөөрөгдсөн",value:1, class:"uk-badge uk-badge-success"}, {name:"ААН-д засвартай байгаа",value:2, class:"uk-badge uk-badge-danger"}, {name:"АМГТГ-т хянагдаж байгаа",value:7, class:"uk-badge uk-badge-warning"}];

            var authenticate = function(credentials, callback) {

                var headers = credentials ? {
                    authorization : "Basic "
                    + btoa(unescape(encodeURIComponent(credentials.username)) + ":"
                        + unescape(encodeURIComponent(credentials.password)))
                } : {};

                $http.get('user', {
                    headers : headers
                }).then(function(response) {
                    if (response.data.name) {
                        //$rootScope.user=response.data;
                        $rootScope.authenticated = true;
                    } else {
                        $rootScope.authenticated = false;
                    }
                    callback && callback($rootScope.authenticated);
                }, function() {
                    $rootScope.authenticated = false;
                    callback && callback(false);
                });


            }

            //authenticate();
            $scope.credentials = {};

            $scope.login = function() {
                $rootScope.loggedout=false;
                authenticate($scope.credentials, function(authenticated) {
                    if (authenticated) {
                        console.log("Login succeeded");
                        var promise = $http.get("/defaultSuccess").success(
                            function (data) {
                                var response = data;
                                $state.go(response.url);
                                // $state.go(response);
                            })
                        $rootScope.authenticated = true;
                    } else {
                        $scope.error="Хэрэглэгчийн нэр эсвэл нууц үг буруу байна!!!";
                        console.log("Login failed")
                        $rootScope.authenticated = false;
                    }
                })
            };

            $scope.setReporttype = function (reporttype) {
                $scope.searchForm.reporttype = reporttype;
            }

            //uk-icon-spinner uk-icon-spin
            $scope.doSearch = function () {
                $scope.searchInProgress = true;
                $scope.isSearched = true;
                //$rootScope.content_preloader_show();
                $scope.searchResult = [];
                mainService.withdata("POST","/public/search",$scope.searchForm).then(function (data) {
                    $scope.searchResult = data;
                    $scope.searchInProgress = false;
                    //$rootScope.content_preloader_hide();
                });
            }

            $scope.getRepStatus = function (id) {
                return $filter('filter')($scope.repstatuses, {value:id})[0];
            }
        }
    ]);