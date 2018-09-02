altairApp
    .config([
        '$stateProvider',
        '$urlRouterProvider',        
        function ($stateProvider, $urlRouterProvider) {

    	   $stateProvider   
    	   		
	    	  .state("restricted.pages.audityear", {
	               url: "/au",
	               templateUrl: 'app/audit/quata/pYearView.html',
	               controller: 'auditYearCtrl',
	               resolve: {                    	
	                   deps: ['$ocLazyLoad', function($ocLazyLoad) {
	                       return $ocLazyLoad.load([
	                           'app/audit/quata/pYearController.js'
	                       ]);
	                   }],	                   
		                user_data: function($http){
		                    return $http({ method: 'GET', url: '/audit/user' })
		                        .then(function (data) {                                	
		                            return data.data;
		                        });
		                }
	               },	                
	               data: {
	                   pageTitle: 'Байгууллагын жагсаалт үүсгэх'
	               }
	          })
        	  .state("restricted.pages.quata", {
                  url: "/quata",
                  templateUrl: 'app/audit/quata/pQuataView.html',
                  controller: 'quataCtrl',
                  resolve: {                    	
                      deps: ['$ocLazyLoad', function($ocLazyLoad) {
                          return $ocLazyLoad.load([
                              'lazy_parsleyjs',
                              'app/audit/quata/pQuataController.js'
                          ]);
                      }],
                       p_year: function($http){
		                    return $http({ method: 'GET', url: '/audit/au/resource/LutAuditYear' })
		                        .then(function (data) {                                	
		                            return data.data;
		                        });
		                },
		                p_cat: function($http){
		                    return $http({ method: 'GET', url: '/audit/au/resource/LutCategory' })
		                        .then(function (data) {                                	
		                            return data.data;
		                        });
		                },
		                user_data: function($http){
		                    return $http({ method: 'GET', url: '/audit/user' })
		                        .then(function (data) {                                	
		                            return data.data;
		                        });
		                }
                  },	                
                  data: {
                      pageTitle: 'Байгууллагын жагсаалт үүсгэх'
                  }
              })
        }
    ]);
