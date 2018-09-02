altairApp
    .service('detectBrowser', [
        '$window',
        'sweet',
        function($window,sweet) {
            // http://stackoverflow.com/questions/22947535/how-to-detect-browser-using-angular
            return function() {
                var userAgent = $window.navigator.userAgent,
                    browsers  = {
                        chrome  : /chrome/i,
                        safari  : /safari/i,
                        firefox : /firefox/i,
                        ie      : /internet explorer/i
                    };

                for ( var key in browsers ) {
                    if ( browsers[key].test(userAgent) ) {
                        return key;
                    }
                }
                return 'unknown';
            }
        }
    ])
    .factory('customLoader', function ($http, $q) {
		    return function (options) {
		      var deferred = $q.defer();
		      
		/*      $http({
			        method:'GET',
			        url:'/api/lang/' + options.key 
			      })*/
		   /*   method:'GET',
	          url: 'app/i18n/'+ options.key + '.json'*/
		      $http({
		    	   method:'GET',
			       url:'/api/lang/' + options.key 
		      }).success(function (data) {
		        deferred.resolve(data);
		      }).error(function () {
		        deferred.reject(options.key);
		      });
		      
		      return deferred.promise;
		    }
		})

   .factory('sessionService',[
       '$rootScope',
       '$http',
       '$location',
       function($rootScope,$http,$location,$state) {
	    var session = {};
	    session.login = function(data) {
	        return $http.post("/login", "username=" + data.name +
	        "&password=" + data.password, {
	            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	        } ).then(function(data) {
	        	var success=data.data;
	        	if(success!=false){
	        		// alert("hooo noo success");
	        		 $rootScope.authenticated = true;
	        		 localStorage.setItem("session", {});
	        	}
	        	else{
	        		  $rootScope.authenticated = false;
	        		// $location.path("restricted.login");
	        		// $state.go("/login");
	        		// $state.go("/login");
	        		 //alert("error logging in 404 lalalla");
	        	}
	        }, function(data) {
	            alert("error logging in 11");
	        });
	    };

	    session.logout = function() {
   		  $http.post("/logout", {}).success(function() {
   		    $rootScope.authenticated = false;
   		    $location.path("/login");
   		    localStorage.setItem("session", false);
   		  }).error(function(data) {
   		    $rootScope.authenticated = false;
   		  });
   		};
	    session.isLoggedIn = function() {
	        return localStorage.getItem("session") !== null;
	    };
	    return session;
	}])
	
	.factory('XSRFInterceptor', function ($cookies, $log) {
	
	    var XSRFInterceptor = {
	
	      request: function(config) {
	
	        var token = $cookies.get('XSRF-TOKEN');
	
	        if (token) {
	          config.headers['X-XSRF-TOKEN'] = token;
	          $log.info("X-XSRF-TOKEN: " + token);
	        }
	
	        return config;
	      }
	    };
	    return XSRFInterceptor;
	  })
	
	.service('fileUpload', ['$http','$q','sweet', function ($http,$q,sweet) {
	    this.uploadFileToUrl = function(uploadUrl, file){
	    	var deferred = $q.defer();
	        var fd = new FormData();
	        fd.append('file', file);
	        $http.post(uploadUrl, file, {
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	        })
           .then(function (response) {
        	   deferred.resolve(response.data);
	        });
	        
	        return deferred.promise;
	    }
	}])

	.service('mainService', function ($http, $q) {
		var master= {};
		
	    this.add=function(item){    	
	    	master= angular.copy(item);
	    	return master;
	    }
	    this.view=function(){    
	    	
	    	return master;
	    }
	    
	    this.getDetail=function(curl){
			var deferred = $q.defer();

	        $http({
				method:'GET',
	        	url:curl
	        })
	        .then(function (response) {
	            if (response.status == 200) {
	                deferred.resolve(response.data);
	            }
	            else {
	                deferred.reject('Error getting');
	            }
	        });

	        return deferred.promise;
		}
	    
	    this.withdomain=function(method,url){
	    	var deferred = $q.defer();
	        $http({
	            method:method,           
	            url:url           
	        })
	        .then(function (response) {
	        	 deferred.resolve(response.data);
	        });
	
	        return deferred.promise;
	    }
	    
	    this.withdata=function(method,url,data){
	    	var deferred = $q.defer();
	
	        $http({
	        	 method:method,
	             url:url,
	             data: data
	        })
	        .then(function (response) {
	            if (response.status == 200) {
	                deferred.resolve(response.data);
	            }
	            else {
	            	deferred.reject('Error occured doing action withdata');
	            }
	        });
	
	        return deferred.promise;
	    }
	    
	    this.http=function(method,url){
	    	var deferred = $q.defer();
	
	        $http({
	            method:method,
	            url:url
	        })
	        .then(function (response) {
	            if (response.status == 200) {
	                deferred.resolve(response.data);
	            }
	            else {
	                deferred.reject('Error occured doing action withoutdata');
	            }
	        });
	
	        return deferred.promise;
	    }
	    
	    this.http=function(method,url,data){
	    	var deferred = $q.defer();
	
	        $http({
	        	 method:method,
	             url:url,
	            data: data
	        })
	        .then(function (response) {
	            if (response.status == 200) {
	                deferred.resolve(response.data);
	            }
	            else {
	            	deferred.reject('Error occured doing action withdata');
	            }
	        });
	
	        return deferred.promise;
	    }
	})
    .service('preloaders', [
        '$rootScope',
        '$timeout',
        'utils',
        function($rootScope,$timeout,utils) {
            $rootScope.content_preloader_show = function(style,container) {
                var $body = $('body');
                if(!$body.find('.content-preloader').length) {
                    var image_density = utils.isHighDensity() ? '@2x' : '' ;

                    var preloader_content = (typeof style !== 'undefined' && style == 'regular')
                        ? '<img src="assets/img/spinners/spinner' + image_density + '.gif" alt="" width="32" height="32">'
                        : '<div class="md-preloader"><svg xmlns="http://www.w3.org/2000/svg" version="1.1" height="32" width="32" viewbox="0 0 75 75"><circle cx="37.5" cy="37.5" r="33.5" stroke-width="8"/></svg></div>';

                    var thisContainer = (typeof container !== 'undefined') ? container : $body;

                    thisContainer.append('<div class="content-preloader">' + preloader_content + '</div>');
                    $timeout(function() {
                        $('.content-preloader').addClass('preloader-active');
                    });
                }
            };
            $rootScope.content_preloader_hide = function() {
                var $body = $('body');
                if($body.find('.content-preloader').length) {
                    // hide preloader
                    $('.content-preloader').removeClass('preloader-active');
                    // remove preloader
                    $timeout(function() {
                        $('.content-preloader').remove();
                    }, 500);
                }
            };

        }
    ])
;