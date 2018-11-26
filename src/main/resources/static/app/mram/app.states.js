altairApp
    .config([
        '$stateProvider',
        '$urlRouterProvider',     
        '$httpProvider',
        function ($stateProvider, $urlRouterProvider,$httpProvider) {

        	 $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        	 // weekly
        	 $stateProvider   
        	 
             .state("restricted.pages.newMetaUser", {
                 url: "/user/:userid",
                 templateUrl: 'app/mram/admin/user_editView.html',
                 controller: 'user_editCtrl',
                 resolve: {                    	
                     deps: ['$ocLazyLoad', function($ocLazyLoad) {
                         return $ocLazyLoad.load([
                             'app/mram/admin/user_editController.js'
                         ]);
                     }],
                     p_org: function($http){
	                    return $http({ method: 'GET', url: '/user/service/resourse/publicOrgs' })
	                        .then(function (data) {                                	
	                            return data.data;
	                        });
	                },
	                p_div: function($http){
	                    return $http({ method: 'GET', url: '/user/service/resourse/LutDivision' })
	                        .then(function (data) {                                	
	                            return data.data;
	                        });
	                },
	                p_role: function($http){
	                    return $http({ method: 'GET', url: '/user/service/resourse/LutRole' })
	                        .then(function (data) {                                	
	                            return data.data;
	                        });
	                },
	                p_group: function($http){
	                    return $http({ method: 'GET', url: '/user/service/resourse/LutMinGroup' })
	                        .then(function (data) {                                	
	                            return data.data;
	                        });
	                },
                 },	                
                 data: {
                     pageTitle: 'Хэрэглэгч'
                 }
             })
          	.state("restricted.pages.xreportmin", {
 	            url: "/annual/x/report",
 	            templateUrl: 'app/mram/xmin/xreportMinView.html',
 	            controller: 'xreportMin',
 	            resolve: {
 	                deps: ['$ocLazyLoad', function($ocLazyLoad) {
 	                    return $ocLazyLoad.load([
                              'app/mram/xmin/xreportMinController.js'
 	                    ]);
 	                }],
 	               mineralData: function($http){
			            return $http({ method: 'GET', url: '/user/service/resourse/LutDeposit' })
			                .then(function (data) {                                	
			                    return data.data;
			                });
			        },
 	               
 	            },
 	            data: {
 	                pageTitle: 'Annual report'
 	            }
     	     })
     	     
          	.state("restricted.pages.xreportcoal", {
 	            url: "/annual/x/creport",
 	            templateUrl: 'app/mram/xcoal/xreportCoalView.html',
 	            controller: 'xreportCoal',
 	            resolve: {
 	                deps: ['$ocLazyLoad', function($ocLazyLoad) {
 	                    return $ocLazyLoad.load([
                              'app/mram/xcoal/xreportCoalController.js'
 	                    ]);
 	                }],
 	               mineralData: function($http){
			            return $http({ method: 'GET', url: '/user/service/resourse/LutDeposit' })
			                .then(function (data) {                                	
			                    return data.data;
			                });
			        }
 	               
 	            },
 	            data: {
 	                pageTitle: 'Annual report'
 	            }
     	     })
     	     
     	    .state("restricted.pages.rejected", {
 	            url: "/annual/rejected/report",
 	            templateUrl: 'app/components/stepReport/vstep1View.html',
 	            controller: 'xreportRejectedCoal',
 	            resolve: {
 	                deps: ['$ocLazyLoad', function($ocLazyLoad) {
 	                    return $ocLazyLoad.load([
                              'app/mram/rejected/reportRejectedController.js'
 	                    ]);
 	                }],
 	                p_deposit: function($http){
	                    return $http({ method: 'GET', url: '/user/service/resourse/LutDeposit' })
	                        .then(function (data) {                                	
	                            return data.data;
	                        });
	                },
 	                mineralData: function($http){
			            return $http({ method: 'GET', url: '/user/service/resourse/LutDeposit' })
			                .then(function (data) {                                	
			                    return data.data;
			                });
			        },
 	               
 	            },
 	            data: {
 	                pageTitle: 'Annual-x report/plan'
 	            }
     	     })
     	     .state("restricted.pages.xhistory", {
 	            url: "/annual/h/x/report",
 	            templateUrl: 'app/mram/xcoal/xreportCoalView.html',
 	            controller: 'xreporthistoryCoal',
 	            resolve: {
 	                deps: ['$ocLazyLoad', function($ocLazyLoad) {
 	                    return $ocLazyLoad.load([
                              'app/mram/xcoal/xreportCoalHistoryController.js'
 	                    ]);
 	                }],
 	               mineralData: function($http){
			            return $http({ method: 'GET', url: '/user/service/resourse/LutDeposit' })
			                .then(function (data) {                                	
			                    return data.data;
			                });
			        },
 	               
 	            },
 	            data: {
 	                pageTitle: 'Annual-x report/plan'
 	            }
     	     })
     	     
             .state("restricted.pages.zeroAnswerGov", {
	        	 url: "/annual/zero/answer/:reqid/:param/:rtype",
	             templateUrl: 'app/mram/xmin/xreportAnswerView.html',
	             controller: 'zeroAnswerCtrl',
	             resolve: {
	                deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                    return $ocLazyLoad.load([
	                    	'app/mram/xmin/xreportAnswerController.js'
	                    ]);
	                }],
	                org_data: function($http,$state){
                        return $http({ method: 'GET', url: '/user/service/detail'})
                            .then(function (data) {
                                return data.data;
                            })
                            .catch(function(response) {
                            	console.error('Gists error', response.status, response.data);
							    $state.go("login");
							    $state.reload();
							});
                    },
                    user_data: function($http){
                        return $http({ method: 'GET', url: '/user/service/ujson' })
                            .then(function (data) {
                                return data.data;
                            });
                    } ,
                    sgt: function($http,$stateParams){
                        return $http({ method: 'GET', url: '/user/service/sgt/'+$stateParams.param })
                            .then(function (data) {
                                return data.data;
                            });
                    },
                    xonoff: function($http,$stateParams){
                        return $http({ method: 'GET', url: '/user/service/xonoff/'+$stateParams.param })
                            .then(function (data) {
                                return data.data;
                            });
                    },
                    note_data: function($http, $stateParams){
                        return $http({ method: 'get', url: '/user/service/note/imp/'+$stateParams.param})
                            .then(function (data) {
                                return data.data;
                            });
                    },
                	sel_data: function($http,$stateParams){
	                  return $http({ method: 'GET', url: '/user/service/resourse/LutDecisions'})
	                      .then(function (data) {
	                          return data.data;
	                      });
	                },
	            },
	            data: {
	                pageTitle: 'Zero report'
	            }
	        })
          
        }
    ]);
