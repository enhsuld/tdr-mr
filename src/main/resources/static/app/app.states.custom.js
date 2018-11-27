altairApp
    .config([
        '$stateProvider',
        '$urlRouterProvider',
        '$httpProvider',
        function ($stateProvider, $urlRouterProvider, $httpProvider) {

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

            // weekly
            $stateProvider

                .state("restricted.pages.govmailbox", {
                    url: "/mailbox",
                    templateUrl: 'app/components/mramCom/mailboxView.html',
                    controller: 'mailboxCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramCom/mailboxController.js'
                            ], {serie: true});
                        }],
                        users: function ($http) {
                            return $http({method: 'GET', url: '/user/service/cascading/LutMramUser'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        messages: function ($http) {
                            return $http({method: 'GET', url: '/user/service/entity/messages'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Mailbox'
                    }
                })
                .state("restricted.pages.reportStatus5", {
                    url: "/annual/report/history",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'historylistreport',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/historyController.js'
                            ]);
                        }]

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.planStatus5", {
                    url: "/annual/plan/history",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'historylistplan',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/historyController.js'
                            ]);
                        }]

                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })
                .state("restricted.pages.todotgol", {
                    url: "/annual/plan/todotgol",
                    templateUrl: 'app/components/todotgol/todotgolView.html',
                    controller: 'todotgolController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/todotgol/todotgolController.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
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
                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })
                .state("restricted.pages.worklist", {
                    url: "/annual/gov/worklist",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'worklist',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/workController.js'
                            ]);
                        }],
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/data/officers/1'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.reportlist", {
                    url: "/annual/gov/reportlist",
                    templateUrl: 'app/components/reports/reportsView.html',
                    controller: 'reportsViewController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/reports/reportsController.js'
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
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.commentslist", {
                    url: "/annual/gov/commentslist",
                    templateUrl: 'app/components/reports/commentsView.html',
                    controller: 'commentsController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/reports/commentsController.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        comments: function ($http) {
                            return $http({method: 'POST', url: '/import/comments/LNK_COMMENT'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        comments_main: function ($http) {
                            return $http({method: 'POST', url: '/import/comments/LNK_COMMENT_MAIN'})
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
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.report_viewer", {
                    url: "/annual/gov/report_viewer/:formname/:reporttype",
                    templateUrl: 'app/components/reports/reportViewerView.html',
                    controller: 'reportViewerController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/reports/reportViewerController.js'
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
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.statistics", {
                    url: "/statistics",
                    templateUrl: 'app/components/mramGov/dashboardView.html',
                    controller: 'statisticCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                // ocLazyLoad config (app/app.js)
                                'lazy_countUp',
                                'lazy_charts_peity',
                                'lazy_charts_easypiechart',
                                'lazy_charts_metricsgraphics',
                                'lazy_charts_chartist',
                                'lazy_weathericons',
                                'lazy_clndr',
                                'lazy_echarts',
                                'app/components/mramGov/dashboardController.js'
                            ], {serie: true});
                        }],

                        stats: function ($http) {
                            return $http({method: 'GET', url: '/user/service/custom/data/stats'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Dashboard'
                    }

                })
                .state("restricted.pages.statcharts", {
                    url: "/statistics/charts",
                    templateUrl: 'app/components/reports/dashboardView.html',
                    controller: 'statisticCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_echarts',
                                'app/components/reports/dashboardController.js'
                            ], {serie: true});
                        }],

                        statscount: function ($http) {
                            return $http({method: 'GET', url: '/stats/dashboard/1'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        statscount2: function ($http) {
                            return $http({method: 'GET', url: '/stats/dashboard/2'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        statscount3: function ($http) {
                            return $http({method: 'GET', url: '/stats/dashboard/3'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Dashboard'
                    }

                })
                .state("restricted.pages.exportdata", {
                    url: "/statistics/export",
                    templateUrl: 'app/components/reports/downloadReport.html',
                    controller: 'downloadReportCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_echarts',
                                'app/components/reports/downloadReport.js'
                            ], {serie: true});
                        }]
                    },
                    data: {
                        pageTitle: 'Dashboard'
                    }

                })
                .state("restricted.pages.statsgov", {
                    url: "/statistics/gov",
                    templateUrl: 'app/components/reports/statisticsView.html',
                    controller: 'statisticsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_echarts',
                                'app/components/reports/statisticsController.js'
                            ], {serie: true});
                        }],
                        stat1: function ($http) {
                            return $http({method: 'GET', url: '/import/gov/1'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        stat2: function ($http) {
                            return $http({method: 'GET', url: '/import/gov/2'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        stat3: function ($http) {
                            return $http({method: 'GET', url: '/import/gov/5'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        stat4: function ($http) {
                            return $http({method: 'GET', url: '/import/gov/6'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        minerals: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinerals'})
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
                        lnkbutdep: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LnkButDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Статистик'
                    }

                })
                .state("restricted.pages.companymailbox", {
                    url: "/mailbox",
                    templateUrl: 'app/components/mramCom/mailboxView.html',
                    controller: 'mailboxCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramCom/mailboxController.js'
                            ], {serie: true});
                        }],
                        users: function ($http) {
                            return $http({method: 'GET', url: '/user/service/cascading/LutMramUser'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        messages: function ($http) {
                            return $http({method: 'GET', url: '/user/service/entity/messages'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Mailbox'
                    }
                })
                .state("restricted.pages.tezu", {
                    url: "/tezu",
                    templateUrl: 'app/components/mramCom/mainLicenseTezuView.html',
                    controller: 'tezu',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramCom/mainLicenseTezuController.js'
                            ], {serie: true});
                        }],
                        users: function ($http) {
                            return $http({method: 'GET', url: '/user/service/cascading/LutMramUser'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Тэзү'
                    }
                })
                .state("restricted.pages.stat", {
                    url: "/statistic",
                    templateUrl: 'app/components/pages/helpView.html',
                    controller: 'statisticCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/pages/helpController.js'
                            ], {serie: true});
                        }],
                        cats: function ($http) {
                            return $http({method: 'GET', url: '/user/service/custom/data/HelpCategory'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        help_data: function ($http) {
                            return $http({method: 'GET', url: 'data/help_faq.json'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Help Center'
                    }
                })
                .state("restricted.pages.help", {
                    url: "/help",
                    templateUrl: 'app/components/pages/helpView.html',
                    controller: 'helpCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/pages/helpController.js'
                            ], {serie: true});
                        }],
                        cats: function ($http) {
                            return $http({method: 'GET', url: '/user/service/custom/data/HelpCategory'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        help_data: function ($http) {
                            return $http({method: 'GET', url: 'data/help_faq.json'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Help Center'
                    }
                })
                .state("restricted.pages.helpCenter", {
                    url: "/help/center",
                    templateUrl: 'app/components/mramGov/helpView.html',
                    controller: 'helpCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGov/helpController.js'
                            ]);
                        }]
                    },
                    data: {
                        pageTitle: 'Тусгай зөвшөөрөл эзэмшигч'
                    }
                })

                .state("restricted.pages.holders", {
                    url: "/holders",
                    templateUrl: 'app/components/mramGov/holdersView.html',
                    controller: 'holderCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGov/holders.js'
                            ]);
                        }],
                        lptypes: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLptype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        role_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/mrole/restricted.pages.organizations/'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Тусгай зөвшөөрөл эзэмшигч'
                    }
                })

                .state("restricted.pages.zeroAnswer", {
                    url: "/annual/zero/:reqid/:param/:rtype",
                    templateUrl: 'app/components/stepReport/slideshowView.html',
                    controller: 'zeroCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/zeroController.js'
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
                    },
                    data: {
                        pageTitle: 'Zero report'
                    }
                })
                .state("restricted.pages.reportGovHistory", {
                    url: "/annual/report/gov/h",
                    templateUrl: 'app/components/mramGovGeo/gAnnualView.html',
                    controller: 'annualCtrlReportGovHHistory',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGovGeo/gAnnualReportGovHistoryController.js'
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
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutUsers'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.planGovHistory", {
                    url: "/annual/plan/gov/h",
                    templateUrl: 'app/components/mramGovGeo/gAnnualView.html',
                    controller: 'annualCtrlGovHHistory',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGovGeo/gAnnualPlanGovHistoryController.js'
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
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutUsers'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })

                .state("restricted.pages.annualReportGeoStep1", {
                    url: "/annual/gov/b/report",
                    templateUrl: 'app/components/mramGovGeo/gAnnualView.html',
                    controller: 'annualCtrlGovBlackTypeAll',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGovGeo/gAnnualStep1TypeAllController.js'
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
                        min_group: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
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
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.annualReportGeoStep2Type1", {
                    url: "/annual/gov/c/report",
                    templateUrl: 'app/components/mramGovGeo/gAnnualView2.html',
                    controller: 'annualCtrlGovStep2Type1',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGovGeo/gAnnualStep2Type1Controller.js'
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
                        p_group: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
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
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.annualReportGeoStep2Type2", {
                    url: "/annual/gov/n/report",
                    templateUrl: 'app/components/mramGovGeo/gAnnualView2.html',
                    controller: 'annualCtrlGovStep2Type2',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGovGeo/gAnnualStep2Type2Controller.js'
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
                        p_group: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
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
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.annualReportGeoStep2Type3", {
                    url: "/annual/gov/m/report",
                    templateUrl: 'app/components/mramGovGeo/gAnnualView2.html',
                    controller: 'annualCtrlGovStep2Type3',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGovGeo/gAnnualStep2Type3Controller.js'
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
                        p_group: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
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
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.annualReportGeoStep2Type4", {
                    url: "/annual/gov/r/report",
                    templateUrl: 'app/components/mramGovGeo/gAnnualView2.html',
                    controller: 'annualCtrlGovStep2Type4',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGovGeo/gAnnualStep2Type4Controller.js'
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
                        p_group: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
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
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.annualReportGeoStep3TypeAll", {
                    url: "/annual/gov/a/report",
                    templateUrl: 'app/components/mramGovGeo/gAnnualView2.html',
                    controller: 'annualCtrlGovStep3TypeAll',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGovGeo/gAnnualStep3TypeAllController.js'
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
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/data/officersgeo/5'})
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
                        p_group: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.reportstep1", {
                    url: "/annual/gov/R/step/1",
                    templateUrl: 'app/components/stepReport/vstep1View.html',
                    controller: 'annualReportStep1CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/step1Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.reportstep2", {
                    url: "/annual/gov/R/step/2",
                    templateUrl: 'app/components/stepReport/vstep1View.html',
                    controller: 'annualReportStep2CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/step2Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.reportstep3", {
                    url: "/annual/gov/R/step/3",
                    templateUrl: 'app/components/stepReport/vstep1View.html',
                    controller: 'annualReportStep3CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/step3Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.reportstep4", {
                    url: "/annual/gov/R/step/4",
                    templateUrl: 'app/components/stepReport/vstep1View.html',
                    controller: 'annualReportStep4CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/step4Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.reportstep5", {
                    url: "/annual/gov/R/step/5",
                    templateUrl: 'app/components/stepReport/vstep1View.html',
                    controller: 'annualReportStep5CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/step5Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.reportstep6", {
                    url: "/annual/gov/R/step/6",
                    templateUrl: 'app/components/stepReport/vstep1View.html',
                    controller: 'annualReportStep6CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/step6Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.reportstep7", {
                    url: "/annual/gov/R/step/7",
                    templateUrl: 'app/components/stepReport/vstep1View.html',
                    controller: 'annualReportStep7CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/step7Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })
                .state("restricted.pages.reportstepx", {
                    url: "/annual/gov/report/x",
                    templateUrl: 'app/components/stepReport/step0View.html',
                    controller: 'annualReportStep0CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepReport/step0Controller.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.step1", {
                    url: "/annual/gov/step/1",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'annualPlanStep1CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/step1Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.step2", {
                    url: "/annual/gov/step/2",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'annualPlanStep2CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/step2Controller.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
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

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.step3", {
                    url: "/annual/gov/step/3",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'annualPlanStep3CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/step3Controller.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
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

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.step4", {
                    url: "/annual/gov/step/4",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'annualPlanStep4CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/step4Controller.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
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

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.step5", {
                    url: "/annual/gov/step/5",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'annualPlanStep5CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/step5Controller.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_deposit: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDeposit'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.step6", {
                    url: "/annual/gov/step/6",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'annualPlanStep6CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/step6Controller.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
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

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.step7", {
                    url: "/annual/gov/step/7",
                    templateUrl: 'app/components/stepPlan/vstep1View.html',
                    controller: 'annualPlanStep7CtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/stepPlan/step7Controller.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
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
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })


                .state("restricted.pages.planMVConfig", {
                    url: "/mv/config/:id",
                    templateUrl: 'app/components/mramCom/mvConfigView.html',
                    controller: 'MainAddPro',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramCom/mvConfigController.js'
                            ]);
                        }],
                        mv: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/rs/com.peace.users.model.mram.LnkReqAnn/reqid/' + $stateParams.id
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rrq: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/rs/com.peace.users.model.mram.RegReportReq/id/' + $stateParams.id
                            })
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
                        mineType: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMineType'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        concent: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutConcentration'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'MV-Config'
                    }
                })

                .state("restricted.pages.planPVConfig", {
                    url: "/pp/config/:id",
                    templateUrl: 'app/components/mramCom/pvConfigView.html',
                    controller: 'pvconfig',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramCom/pvConfigController.js'
                            ]);
                        }],
                        pv: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/rs/com.peace.users.model.mram.LnkReqPv/reqid/' + $stateParams.id
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'PP-Config'
                    }
                })

                .state("restricted.pages.planConfig", {
                    url: "/annual/plan/g",
                    templateUrl: 'app/components/mramCom/gAnnualPlanView.html',
                    controller: 'annualCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'app/components/mramCom/gAnnualPlanController.js'
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
                        pageTitle: 'Annual plan'
                    }
                })
                .state("restricted.pages.planNew", {
                    url: "/annual/plan/n",
                    templateUrl: 'app/components/mramCom/gAnnualPlanNewView.html',
                    controller: 'annualNewCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'app/components/mramCom/gAnnualPlanNewController.js'
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
                        p_div: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDivision'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },

                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })
                .state("restricted.pages.planStatusRejected1", {
                    url: "/annual/plan/rejected/1/:reporttype",
                    templateUrl: 'app/components/mramCom/gAnnualPlanStatusRejected1View.html',
                    controller: 'annualStatusRejected1Ctrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'app/components/mramCom/gAnnualPlanStatusRejected1Controller.js'
                            ]);
                        }],
                        p_deposit: function ($http) {
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
                .state("restricted.pages.planStatusRejected2", {
                    url: "/annual/plan/rejected/2/:reporttype",
                    templateUrl: 'app/components/mramCom/gAnnualPlanStatusRejected2View.html',
                    controller: 'annualStatusRejected2Ctrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'app/components/mramCom/gAnnualPlanStatusRejected2Controller.js'
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
                        p_deposit: function ($http) {
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
                .state("restricted.pages.planStatusRejected3", {
                    url: "/annual/plan/rejected/3",
                    templateUrl: 'app/components/mramCom/gAnnualPlanStatusRejected3View.html',
                    controller: 'annualStatusRejected3Ctrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'app/components/mramCom/gAnnualPlanStatusRejected3Controller.js'
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
                        p_deposit: function ($http) {
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
                .state("restricted.pages.planStatus", {
                    url: "/annual/plan/s",
                    templateUrl: 'app/components/mramCom/gAnnualPlanStatusView.html',
                    controller: 'annualStatusCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'app/components/mramCom/gAnnualPlanStatusController.js'
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
                        pageTitle: 'Annual plan'
                    }
                })
                .state("restricted.pages.licMainConfig", {
                    url: "/license/configuration",
                    templateUrl: 'app/components/mramCom/mainLicenseConfigView.html',
                    controller: 'MainConfiguration',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'lazy_parsleyjs',
                                'app/components/mramCom/mainLicenseConfigController.js'
                            ]);
                        }]
                    },
                    data: {
                        pageTitle: 'Тз тохиргоо'
                    }
                })

                .state("restricted.pages.planComHistory", {
                    url: "/annual/history",
                    templateUrl: 'app/components/mramCom/gAnnualPlanStatusView.html',
                    controller: 'annualHisCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramCom/gAnnualHistoryController.js'
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
                        pageTitle: 'Annual plan'
                    }
                })

                .state("restricted.pages.licAddPro", {
                    url: "/add/product/:id",
                    templateUrl: 'app/components/mramCom/mainAddProView.html',
                    controller: 'MainAddPro',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'lazy_parsleyjs',
                                'app/components/mramCom/mainAddProController.js'
                            ]);
                        }]
                    },
                    data: {
                        pageTitle: 'Тз тохиргоо'
                    }
                })
                .state("restricted.pages.weeklynew", {
                    url: "/weekly/new",
                    templateUrl: 'app/components/mramWeek/weeklyNew.html',
                    controller: 'weeklyNew',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramWeek/weeklyNewController.js'
                            ]);
                        }]
                    },
                    data: {
                        pageTitle: '7 хоног тохиргоо'
                    }
                })

                .state("restricted.pages.weeklyDataGov", {
                    url: "/g/weekly/data/:id",
                    templateUrl: 'app/components/mramWeekCoal/mWeeklyDataView.html',
                    controller: 'weeklyNewData',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramWeekCoal/mWeeklyDataController.js'
                            ], {serie: true});
                        }],
                        sgt: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/wsgt/' + $stateParams.id})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_indicator: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutFormindicators'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_measurement: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMeasurements'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        weekDetail: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/getWeekDetail/' + $stateParams.id})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: '7 хоног тайлан'
                    }
                })

                .state("restricted.pages.weeklyDataCoalGov", {
                    url: "/g/weekly/data/c/:id",
                    templateUrl: 'app/components/mramWeekCoal/mWeeklyDataCoalView.html',
                    controller: 'weeklyNewDataCoal',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramWeekCoal/mWeeklyDataCoalController.js'
                            ], {serie: true});
                        }],
                        sgt: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/wsgt/' + $stateParams.id})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_indicator: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutFormindicators'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_measurement: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMeasurements'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: '7 хоног тайлан'
                    }
                })

                .state("restricted.pages.weeklyData", {
                    url: "/weekly/data/:id",
                    templateUrl: 'app/components/mramWeek/mWeeklyDataView.html',
                    controller: 'weeklyNewData',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramWeek/mWeeklyDataController.js'
                            ], {serie: true});
                        }],
                        sgt: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/wsgt/' + $stateParams.id})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_indicator: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutFormindicators'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_measurement: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMeasurements'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        weekDetail: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/getWeekDetail/' + $stateParams.id})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: '7 хоног тайлан'
                    }
                })

                .state("restricted.pages.weeklyDataCoal", {
                    url: "/weekly/data/c/:id",
                    templateUrl: 'app/components/mramWeek/mWeeklyDataCoalView.html',
                    controller: 'weeklyNewDataCoal',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramWeek/mWeeklyDataCoalController.js'
                            ], {serie: true});
                        }],
                        sgt: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/wsgt/' + $stateParams.id})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_indicator: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutFormindicators'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_measurement: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMeasurements'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: '7 хоног тайлан'
                    }
                })

                .state("restricted.pages.weekstatus", {
                    url: "/weekly/status",
                    templateUrl: 'app/components/mramWeek/gWeekStatusView.html',
                    controller: 'weekstatusCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramWeek/gWeekStatusController.js'
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
                        weeks: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutWeeks'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },

                    },
                    data: {
                        pageTitle: 'Reporting status'
                    }
                })

                .state("restricted.pages.weeklyhistorygov", {
                    url: "/g/weekly/history",
                    templateUrl: 'app/components/mramWeekCoal/gWeekStatusView.html',
                    controller: 'weekhistoryGovCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramWeekCoal/gWeekHistoryController.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
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
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Reporting status'
                    }
                })
                .state("restricted.pages.weekstatusgov", {
                    url: "/g/weekly/status",
                    templateUrl: 'app/components/mramWeekCoal/gWeekStatusView.html',
                    controller: 'weekstatusGovCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramWeekCoal/gWeekStatusController.js'
                            ]);
                        }],
                        lic_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
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
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Reporting status'
                    }
                })

                .state("restricted.pages.weeklyConfig", {
                    url: "/weekly/config",
                    templateUrl: 'app/components/mramWeek/gWeekConfigView.html',
                    controller: 'gWeekConfigCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramWeek/gWeekConfigController.js'
                            ]);
                        }],
                        lut_countries: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutCountries'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lut_lics: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/weeklicenses'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Weekly configuration'
                    }
                })

                .state("restricted.pages.weeklyhistory", {
                    url: "/weekly/history",
                    templateUrl: 'app/components/mramWeek/gWeekHistoryView.html',
                    controller: 'weekhistoryCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramWeek/gWeekHistoryController.js'
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
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutUsers'})
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
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Report history'
                    }
                })

                .state("restricted.client", {
                    url: "/public",
                    template: '<ui-view autoscroll="false"/>',
                    abstract: true
                })

                .state("restricted.client.annualplanGov", {
                    url: "/report",
                    templateUrl: 'app/components/mramClient/gAnnualPlanView.html',
                    controller: 'annualCtrlGovClient',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramClient/gAnnualPlanController.js'
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
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutUsers'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Тайлан төлөвлөгөө'
                    }
                })
                .state("restricted.news", {
                    url: "/news",
                    templateUrl: 'app/components/news/newsList.html',
                    controller: 'newsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/news/newsController.js'
                            ]);
                        }],
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Мэдээлэл'
                    }
                })
                .state("restricted.viewformsdetail", {
                    url: "/viewsforms",
                    templateUrl: 'app/components/dataforms/dataForms.html',
                    controller: 'dataFormsController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/dataforms/dataFormsController.js'
                            ]);
                        }],
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        divisions: function ($http) {
                            return $http({method: 'GET', url: '/getDivisionList'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        report_types: function ($http) {
                            return $http({method: 'GET', url: '/getReportTypes'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Мэдээлэл'
                    }
                })
                .state("restricted.yearConfig", {
                    url: "/pages/configYear",
                    templateUrl: 'app/components/pages/yearConfigView.html',
                    controller: 'yearConfigController',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/pages/yearConfigController.js'
                            ], {serie: true});
                        }],
                        report_types: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype.'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Тайлангийн огнооны тохиргоо'
                    }
                })
                .state("restricted.pages.tzs", {
                    url: "/gov/tz",
                    templateUrl: 'app/components/mramClient/mLicenseTzGov.html',
                    controller: 'tzs',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramClient/mLicenseTzGov.js'
                            ]);
                        }],
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Тусгай зөвшөөрөл'
                    }
                })

                .state("restricted.client.tz", {
                    url: "/tz",
                    templateUrl: 'app/components/mramClient/mLicenseTz.html',
                    controller: 'tz',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramClient/mLicenseTz.js'
                            ]);
                        }],
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Тайлан төлөвлөгөө'
                    }
                })

                .state("restricted.pages.formsform", {
                    url: "/forms/:id",
                    templateUrl: 'app/components/mramLogic/mFormAddView.html',
                    controller: 'formAddCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'lazy_dropify',
                                'app/components/mramLogic/mFormAddController.js'
                            ]);
                        }],
                        division: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDivision'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        reptype: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lictype: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Маягт'
                    }
                })
                .state("restricted.pages.forms", {
                    url: "/forms",
                    templateUrl: 'app/components/mramLogic/mFormView.html',
                    controller: 'formCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramLogic/mFormController.js'
                            ]);
                        }],
                        division: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDivision'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        reptype: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        lictype: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLictype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Маягт'
                    }
                })


                .state("restricted.pages.annualplan", {
                    url: "/annualplan",
                    templateUrl: 'app/components/mramGov/gAnnualPlanView.html',
                    controller: 'annualCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramGov/gAnnualPlanController.js'
                            ]);
                        }],

                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })

                .state("restricted.pages.annualplanGov", {
                    url: "/annual/plan/gov",
                    templateUrl: 'app/components/mramLogic/gAnnualPlanView.html',
                    controller: 'annualCtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramLogic/gAnnualPlanController.js'
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
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutUsers'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rep_type: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutReporttype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })


                .state("restricted.pages.planFormA", {
                    url: "/annual/plan/a/:reqid/:param",
                    templateUrl: 'app/components/mramCom/mPlanView.html',
                    controller: 'annualplanFormCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_dropify',
                                'app/components/mramCom/mPlanController.js'
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
                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })

                .state("restricted.pages.planFormH", {
                    url: "/annual/plan/h/:reqid/:param",
                    templateUrl: 'app/components/mramCom/mPlanView.html',
                    controller: 'annualFormCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'lazy_ckeditor',
                                'app/components/mramCom/mPlanControllerH.js'
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
                        }
                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })

                .state("restricted.pages.reportFormA", {
                    url: "/annual/report/a/:reqid/:param",
                    templateUrl: 'app/components/mramCom/mPlanView.html',
                    controller: 'annualReportACtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'lazy_ckeditor',
                                'app/components/mramCom/mReportController.js'
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
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.reportFormH", {
                    url: "/annual/report/h/:reqid/:param",
                    templateUrl: 'app/components/mramCom/mPlanView.html',
                    controller: 'annualReportHCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramCom/mReportControllerH.js'
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
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })


                .state("restricted.pages.annualReportMountain", {
                    url: "/annual/gov/report",
                    templateUrl: 'app/components/mramMount/gAnnualPlanView.html',
                    controller: 'annualReportCtrlGov',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramMount/gAnnualPlanController.js'
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
                        officers: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/officers'})
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
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.GovReportFormA", {
                    url: "/annual/gov/report/a/:param/:id",
                    templateUrl: 'app/components/mramLogic/mPlanFormGovView.html',
                    //templateUrl: 'app/components/mramMount/mReportFormGovView.html',
                    controller: 'annualReportCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'lazy_tinymce',
                                'app/components/mramMount/mReportFormGovController.js'
                            ]);
                        }],
                        org_data: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/lp/detail/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sgt: function ($http, $stateParams) {
                            return $http({
                                method: 'get',
                                url: '/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/' + $stateParams.param
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sel_data: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDecisions'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_status: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppstatus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_steps: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppsteps'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        note_data: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/note/imp/gov/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        tz: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/sgt/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        ann: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/ann/com.peace.users.model.mram.LnkReqAnn/reqid/' + $stateParams.param
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rrq: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/ann/com.peace.users.model.mram.RegReportReq/id/' + $stateParams.param
                            })
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
                        mineType: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMineType'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        concent: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutConcentration'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })


                .state("restricted.pages.GovReportFormH", {
                    url: "/annual/gov/report/h/:param/:groupid",
                    templateUrl: 'app/mram/Geo/mPlanView.html',
                    controller: 'annualReportHCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramMount/mReportFormGovControllerH.js'
                            ]);
                        }],
                        org_data: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/lp/detail/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sel_data: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDecisions'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_status: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppstatus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_steps: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppsteps'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        note_data: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/note/imp/gov/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        tz: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/tz/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sgt: function ($http, $stateParams) {
                            return $http({
                                method: 'get',
                                url: '/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/' + $stateParams.param
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Annual report'
                    }
                })

                .state("restricted.pages.GovPlanFormA", {
                    url: "/annual/plan/gov/a/:param/:id",
                    templateUrl: 'app/components/mramLogic/mPlanFormGovView.html',
                    controller: 'annualGOVFormCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mramLogic/mPlanFormGovController.js',
                                'lazy_tinymce'
                            ]);
                        }],
                        org_data: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/lp/detail/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sgt: function ($http, $stateParams) {
                            return $http({
                                method: 'get',
                                url: '/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/' + $stateParams.param
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sel_data: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDecisions'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_status: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppstatus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_steps: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppsteps'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        note_data: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/note/imp/gov/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        tz: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/sgt/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        ann: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/ann/com.peace.users.model.mram.LnkReqAnn/reqid/' + $stateParams.param
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        rrq: function ($http, $stateParams) {
                            return $http({
                                method: 'GET',
                                url: '/user/service/ann/com.peace.users.model.mram.RegReportReq/id/' + $stateParams.param
                            })
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
                        mineType: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMineType'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        concent: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutConcentration'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })

                .state("restricted.pages.GovPlanFormH", {
                    url: "/annual/plan/gov/h/:param/:id",
                    templateUrl: 'app/mram/Geo/mPlanView.html',
                    controller: 'annualFormCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/mram/Geo/mPlanController.js'
                            ]);
                        }],
                        org_data: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/lp/detail/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        sel_data: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDecisions'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        app_status: function ($http, $stateParams) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutAppstatus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        note_data: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/note/imp/gov/' + $stateParams.param})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        tz: function ($http, $stateParams) {
                            return $http({method: 'get', url: '/user/service/sgt/' + $stateParams.param})
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
                        sgt: function ($http, $stateParams) {
                            return $http({
                                method: 'get',
                                url: '/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/' + $stateParams.param
                            })
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Annual plan'
                    }
                })


                .state("restricted.pages.formConfig", {
                    url: "/form/config",
                    templateUrl: 'app/components/mramGov/mLicenseView.html',
                    controller: 'mLicenseGOVCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramGov/mLicenseController.js'
                            ]);
                        }],
                        p_org: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LicOrgs111'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Тусгай зөвшөөрөл'
                    }
                })

                .state("restricted.pages.formMiningConfig", {
                    url: "/form/mining/config",
                    templateUrl: 'app/components/mramGov/mLicenseMiningView.html',
                    controller: 'mLicenseGOVCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramGov/mLicenseMiningController.js'
                            ]);
                        }],
                        p_org: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LicOrgs111'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        role_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/mrole/restricted.pages.formMiningConfig.'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }

                    },
                    data: {
                        pageTitle: 'Тусгай зөвшөөрөл'
                    }
                })

                .state("restricted.pages.formConfigDivision", {
                    url: "/division/config",
                    templateUrl: 'app/components/mramGov/mLicenseConfigView.html',
                    controller: 'mLicenseGOVCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mramGov/mLicenseConfigController.js'
                            ]);
                        }],
                        p_org: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LicOrgs111'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        user_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/ujson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Тусгай зөвшөөрөл'
                    }
                })

                .state("restricted.pages.config", {
                    url: "/config",
                    templateUrl: 'app/components/mram/mConfigView.html',
                    controller: 'configCtrl',
                    params: {
                        obj: null
                    },
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'lazy_wizard',
                                'app/components/mram/mConfigController.js'
                            ], {serie: true});
                        }]

                    },
                    data: {
                        pageTitle: 'Config'
                    }
                })

                .state("restricted.pages.publicOrganization", {
                    url: "/public/organizations",
                    templateUrl: 'app/components/mram/pPublicOrgView.html',
                    controller: 'orgPublicCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mram/pPublicOrgController.js'
                            ]);
                        }],
                        lptypes: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLptype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        role_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/mrole/restricted.pages.organizations/'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Төрийн байгууллага'
                    }
                })
                .state("restricted.pages.organizations", {
                    url: "/legalpersons",
                    templateUrl: 'app/components/mram/pOrganizationView.html',
                    controller: 'orgNewCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mram/pOrganizationController.js'
                            ]);
                        }],
                        lptypes: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutLptype'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        role_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/mrole/restricted.pages.organizations.'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Тусгай зөвшөөрөл эзэмшигч'
                    }
                })
                .state("restricted.pages.luts", {
                    url: "/lookup",
                    templateUrl: 'app/components/mram/mLutView.html',
                    controller: 'lutCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mram/mLutController.js'
                            ]);
                        }],
                        role_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/mrole/restricted.pages.luts/'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        org_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/publicOrgs'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'look up tables'
                    }
                })

                .state("restricted.weekly", {
                    url: "/weekly/:bund/:param",
                    templateUrl: 'app/components/mram/mWeeklyView.html',
                    controller: 'weeklyCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mram/mWeeklyController.js'
                            ], {serie: true});
                        }],
                        p_indicator: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutFormindicators'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_measurement: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMeasurements'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Weekly Report'
                    }
                })

                .state("restricted.weekly.coal", {
                    url: "/weekly/:bund/:param",
                    templateUrl: 'app/components/mram/mWeeklyViewCoal.html',
                    controller: 'weeklyCoalCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/mram/mWeeklyCoalController.js'
                            ], {serie: true});
                        }],
                        p_indicator: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutFormindicators'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_measurement: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMeasurements'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Weekly Report Mram'
                    }
                })

                .state("restricted.pages.coal", {
                    url: "/wm/coal",
                    templateUrl: 'app/components/mram/mCoalWMView.html',
                    controller: 'mCoalCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mram/mCoalWMController.js'
                            ]);
                        }]
                    },
                    data: {
                        pageTitle: 'Нүүрс тайлын'
                    }
                })

                .state("restricted.pages.license", {
                    url: "/license",
                    templateUrl: 'app/components/mram/mLicenseView.html',
                    controller: 'mLicenseCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'app/components/mram/mLicenseController.js'
                            ]);
                        }]
                    },
                    data: {
                        pageTitle: 'Тусгай зөвшөөрөл'
                    }
                })

                .state("restricted.pages.user_profile", {
                    url: "/user_profile/:param",
                    templateUrl: 'app/components/pages/user_profileView.html',
                    controller: 'user_profileCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/pages/user_profileController.js'
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
                        pageTitle: 'User profile'
                    }
                })
                .state("restricted.pages.user_edit", {
                    url: "/user_edit",
                    templateUrl: 'app/components/pages/user_editView.html',
                    controller: 'user_editCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_parsleyjs',
                                'bower_components/angular-resource/angular-resource.min.js',
                                'lazy_datatables',
                                'app/components/pages/user_editController.js'
                            ]);
                        }],
                        user_data: function ($http) {
                            return $http({method: 'GET', url: 'data/user_data.json'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        org_data: function ($http) {
                            return $http({method: 'GET', url: '/user/service/detail'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'User edit'
                    }
                })


                .state("restricted.pages.pmenu", {
                    url: "/pmenu",
                    templateUrl: 'app/components/pages/pPmenuView.html',
                    controller: 'menuCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/pages/pPmenuController.js'
                            ]);
                        }],
                        p_menu: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMenu'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        user_data: function ($http) {
                            return $http({method: 'GET', url: 'data/user_data.json'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Цэс'
                    }
                })

                .state("restricted.pages.prole", {
                    url: "/prole",
                    templateUrl: 'app/components/pages/pProleView.html',
                    controller: 'roleCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/pages/pProleController.js'
                            ]);
                        }],
                        sections: function ($http) {
                            return $http({method: 'GET', url: '/user/service/mjson'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        donelist: function ($http) {
                            return $http({method: 'GET', url: '/user/service/parentmenus'})
                                .then(function (data) {
                                    return data.data;
                                });
                        }
                    },
                    data: {
                        pageTitle: 'Эрх'
                    }
                })

                .state("restricted.pages.puser", {
                    url: "/user",
                    templateUrl: 'app/components/pages/pPuserView.html',
                    controller: 'userCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/components/pages/pPuserController.js'
                            ]);
                        }],
                        p_org: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/publicOrgs'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_div: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutDivision'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_role: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutRole'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                        p_group: function ($http) {
                            return $http({method: 'GET', url: '/user/service/resourse/LutMinGroup'})
                                .then(function (data) {
                                    return data.data;
                                });
                        },
                    },
                    data: {
                        pageTitle: 'Хэрэглэгч'
                    }
                })


        }
    ]);
