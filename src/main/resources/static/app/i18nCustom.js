(function () {

    angular.module('app.i18n', ['pascalprecht.translate'])
        .config(['$translateProvider', i18nConfig])
        .controller('LangCtrl', ['$scope', '$translate', LangCtrl]);

   	
        function i18nConfig($translateProvider) {

            $translateProvider.useLoader('customLoader',{});
            $translateProvider.preferredLanguage('mn');
            $translateProvider.useSanitizeValueStrategy('sanitizeParameters');
    	 /* $translateProvider.useLoader('customLoader', {});*/
    	  
    	 // $translateProvider.uses('en');
        }


        function LangCtrl($scope, $translate) {

            // language switcher
            $scope.langSwitcherModel = 'mn';
           
            $('#lang_switcher').on('change', function() {  
           	 var lang='mn';
           	 lang = $('#lang_switcher').val();            	 
           	 switch (lang) {
	                 case 'gb':
	                     $translate.use('en');
	                     break;	          
	                 case 'mn':
	                     $translate.use('mn');
	                     break;
	             }
           });
            
            var langData = $scope.langSwitcherOptions = [
                {id: 1, title: 'Монгол', value: 'mn'},
                {id: 2, title: 'English', value: 'gb'}
            ];
            $scope.langSwitcherConfig = {
                maxItems: 1,
                render: {
                    option: function(langData, escape) {
                        return  '<div class="option">' +
                            '<i class="item-icon flag-' + escape(langData.value).toUpperCase() + '"></i>' +
                            '<span>' + escape(langData.title) + '</span>' +
                            '</div>';
                    },
                    item: function(langData, escape) {
                        return '<div class="item"><i class="item-icon flag-' + escape(langData.value).toUpperCase() + '"></i></div>';
                    }
                },
                valueField: 'value',
                labelField: 'title',
                searchField: 'title',
                create: false,
                onInitialize: function(selectize) {
                    $('#lang_switcher').next().children('.selectize-input').find('input').attr('readonly',true);
                }
                
            };
            
            $scope.langChange = function(){              	
            	/*if($scope.langSwitcherModel=='gb'){
            		kendo.ui.progress($(".k-grid .k-widget"), true);
                    $.getScript("kendoui/js/messages/kendo.messages.en-US.min.js", function () {
                        kendo.ui.progress($("#page_content_inner .k-grid .k-widget"), false);
                    });
            	}
            	else{
            		kendo.ui.progress($(".k-grid .k-widget"), true);
                    $.getScript("kendoui/js/messages/localeMN.js", function () {
                        kendo.ui.progress($(".k-grid .k-widget"), false);
                    });
            	}*/
            	 
            }
            
        }
       
})(); 
