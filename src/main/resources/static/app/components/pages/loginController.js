angular
    .module('altairApp')
    .controller('loginCtrl', [
        '$scope',
        '$rootScope',
        '$http',
        'utils',
        '$state',
        'sessionService',
        'mainService',
        'sweet',
        function ($scope, $rootScope, $http, utils, $state, sessionService, mainService, sweet) {

            $scope.registerFormActive = false;

            $rootScope.authenticated = true;

            var $formValidate = $('#form_login');

            $formValidate
                .parsley()
                .on('form:validated', function () {
                    $scope.$apply();
                })
                .on('field:validated', function (parsleyField) {
                    if ($(parsleyField.$element).hasClass('md-input')) {
                        $scope.$apply();
                    }
                });


            var $respassword = $('#form_reset');

            $respassword
                .parsley()
                .on('form:validated', function () {
                    $scope.$apply();
                })
                .on('field:validated', function (parsleyField) {
                    if ($(parsleyField.$element).hasClass('md-input')) {
                        $scope.$apply();
                    }
                });

            var $login_card = $('#login_card'),
                $login_form = $('#login_form'),
                $login_help = $('#login_help'),
                $register_form = $('#register_form'),
                $login_password_reset = $('#login_password_reset');

            // show login form (hide other forms)
            var login_form_show = function () {
                $login_form
                    .show()
                    .siblings()
                    .hide();
            };

            // show register form (hide other forms)
            var register_form_show = function () {
                $register_form
                    .show()
                    .siblings()
                    .hide();
            };

            // show login help (hide other forms)
            var login_help_show = function () {
                $login_help
                    .show()
                    .siblings()
                    .hide();
            };

            // show password reset form (hide other forms)
            var password_reset_show = function () {
                $login_password_reset
                    .show()
                    .siblings()
                    .hide();
            };

            $scope.loginHelp = function ($event) {
                $event.preventDefault();
                utils.card_show_hide($login_card, undefined, login_help_show, undefined);
            };

            $scope.backToLogin = function ($event) {
                $event.preventDefault();
                $scope.registerFormActive = false;
                utils.card_show_hide($login_card, undefined, login_form_show, undefined);
            };

            $scope.registerForm = function ($event) {
                $event.preventDefault();
                $scope.registerFormActive = true;
                utils.card_show_hide($login_card, undefined, register_form_show, undefined);
            };

            $scope.passwordReset = function ($event) {
                $event.preventDefault();
                utils.card_show_hide($login_card, undefined, password_reset_show, undefined);
            };

            var authenticate = function (credentials, callback) {

                var headers = credentials ? {
                    authorization: "Basic "
                        + btoa(unescape(encodeURIComponent(credentials.username)) + ":"
                            + unescape(encodeURIComponent(credentials.password)))
                } : {};

                $http.get('user', {
                    headers: headers
                }).then(function (response) {
                    if (response.data.name) {
                        //$rootScope.user=response.data;
                        $rootScope.authenticated = true;
                    } else {
                        $rootScope.authenticated = false;
                    }
                    callback && callback($rootScope.authenticated);
                }, function () {
                    $rootScope.authenticated = false;
                    callback && callback(false);
                });
            };

            //authenticate();
            $scope.credentials = {};

            $scope.login = function () {
                $rootScope.loggedout = false;
                authenticate($scope.credentials, function (authenticated) {
                    if (authenticated) {
                        console.log("Login succeeded");
                        var promise = $http.get("/defaultSuccess").then(
                            function (data) {
                                $state.go(data.data.url);
                            });
                        $rootScope.authenticated = true;
                    } else {
                        $scope.error = "Хэрэглэгчийн нэр эсвэл нууц үг буруу байна!!!";
                        $rootScope.authenticated = false;
                    }
                })
            };

            $scope.res = {};
            $scope.resetPassword = function () {
                mainService.withdata('put', '/service/send-mail', $scope.res)
                    .then(function (data) {
                        console.log(data);
                        if (data) {
                            sweet.show('Амжилттай илгээлээ', 'Бүртгэлтэй и-мэйлээ шалгана уу!!!', 'success');
                        }

                    });
            };

        }
    ]);


