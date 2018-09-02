altairApp
    .config([
        '$stateProvider',
        '$urlRouterProvider',
        '$httpProvider',
        function ($stateProvider, $urlRouterProvider, $httpProvider) {
            $stateProvider
                .state("restricted.v2", {
                    abstract: true,
                    template: '<div ui-view autoscroll="false" ng-class="{ \'uk-height-1-1\': page_full_height }" />',
                    url: "/v2"
                })
                .state("restricted.v2.list", {
                    url: "/list/:view/:type",
                    //params: {myParam: null},
                    templateUrl: 'v2/lists/listView.html',
                    controller: 'listController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'v2/lists/listController.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        mineralData: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinerals'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        min_group: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        divisions: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDivision'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Жагсаалт'
                    }
                })
                .state("restricted.v2.company", {
                    abstract: true,
                    url: "/company",
                    template: '<div ui-view autoscroll="false" ng-class="{ \'uk-height-1-1\': page_full_height }" />',
                })
                .state("restricted.v2.company.dashboard", {
                    url: "/home",
                    //params: {myParam: null},
                    templateUrl: 'v2/company/dashboardView.html',
                    controller: 'dashboardController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'v2/company/dashboardController.js'
                            ]);
                        }],
                        org_data: function ($http, $state) {
                            return $http({method: 'GET', url: '/user/service/detail'})
                                .then(function (data) {
                                    return data.data;
                                })
                                .catch(function (response) {
                                    console.error('Gists error', response.status, response.data);
                                    $state.go("login");
                                    $state.reload();
                                });
                        },
                        messages: function ($http, $state) {
                            return $http({method: 'GET', url: '/allMessages'})
                                .then(function (data) {
                                    return data.data;
                                })
                                .catch(function (response) {
                                    console.error('Gists error', response.status, response.data);
                                    $state.go("login");
                                    $state.reload();
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Нүүр хуудас'
                    },
                    ncyBreadcrumb: {
                        label: 'Хэрэглэгчийн мэдээлэл'
                    }
                })
                .state("restricted.v2.company.edit", {
                    url: "/edit",
                    //params: {myParam: null},
                    templateUrl: 'v2/company/profileEditView.html',
                    controller: 'profileEditController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'v2/company/profileEditController.js'
                            ]);
                        }],
                        org_data: function ($http, $state) {
                            return $http({method: 'GET', url: '/user/service/detail'})
                                .then(function (data) {
                                    return data.data;
                                })
                                .catch(function (response) {
                                    console.error('Gists error', response.status, response.data);
                                    $state.go("login");
                                    $state.reload();
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Мэдээлэл засах'
                    }
                })
                .state("restricted.v2.company.planConfig", {
                    url: "/planconfig",
                    templateUrl: 'v2/company/gAnnualPlanView.html',
                    controller: 'annualCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'v2/company/gAnnualPlanController.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_status: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppstatus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutUsers'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sel_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/ownlicenses'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        group_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        mineralData: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },

                    },
                    data: {
                        pageTitle: 'Лицензийн тохиргоо'
                    }
                })
                .state("restricted.v2.company.crearePlan", {
                    url: "/create",
                    templateUrl: 'v2/company/gAnnualPlanNewView.html',
                    controller: 'annualNewCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'v2/company/gAnnualPlanNewController.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_status: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppstatus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        mineralData: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sel_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/ownlicenses'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },

                    },
                    data: {
                        pageTitle: 'Тайлан шинээр үүсгэх'
                    }
                })
                .state("restricted.v2.company.currentList", {
                    url: "/current",
                    templateUrl: 'v2/company/gAnnualPlanStatusView.html',
                    controller: 'annualStatusCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'v2/company/gAnnualPlanStatusController.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_status: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppstatus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutUsers'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sel_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/ownlicenses'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        mineralData: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },

                    },
                    data: {
                        pageTitle: 'Төлөвлөгөө/тайлангийн явц'
                    }
                })
                .state("restricted.v2.company.history", {
                    url: "/history",
                    templateUrl: 'v2/company/gAnnualPlanStatusView.html',
                    controller: 'annualHisCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'v2/company/gAnnualHistoryController.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_status: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppstatus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sel_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/ownlicenses'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutUsers'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Төлөвлөгөө/тайлангийн түүх'
                    }
                })
                .state("restricted.v2.company.detail", {
                    url: "/detail/:reqid/:param",
                    templateUrl: 'v2/company/mPlanView.html',
                    controller: 'annualplanFormCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_dropify',
                                'v2/company/mPlanController.js'
                            ], {serie: true});
                        }],
                        org_data: function ($http, $state) {
                            return $http({method: 'GET', url: '/user/service/detail'})
                                .then(function (data) {
                                    return data.data;
                                })
                                .catch(function (response) {
                                    console.error('Gists error', response.status, response.data);
                                    $state.go("login");
                                    $state.reload();
                                });
                        },
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sgt: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/sgt/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        xonoff: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/xonoff/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        note_data: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/note/imp/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        ann: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/rs/com.peace.users.model.mram.LnkReqAnn/reqid/' + $stateParams.reqid
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        pv: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/rs/com.peace.users.model.mram.LnkReqPv/reqid/' + $stateParams.reqid
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        minerals: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/resourse/LutMinerals'
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        concetrates: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutConcentration'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        minetypes: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMineType'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        deposits: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })
        }
    ]);
