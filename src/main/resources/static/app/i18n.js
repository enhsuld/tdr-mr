(function () {

    angular.module('app.i18n', ['pascalprecht.translate'])
        .config(['$translateProvider', i18nConfig])
        .controller('LangCtrl', ['$scope', '$translate', LangCtrl]);

        // English, Español, 日本語, 中文, Deutsch, français, Italiano, Portugal, Русский язык, 한국어
        // Note: Used on Header, Sidebar, Footer, Dashboard
        // English:          EN-US
        // Spanish:          Español ES-ES
        // Chinese:          简体中文 ZH-CN
        // Chinese:          繁体中文 ZH-TW
        // French:           français FR-FR

        // Not used:
        // Portugal:         Portugal PT-BR
        // Russian:          Русский язык RU-RU
        // German:           Deutsch DE-DE
        // Japanese:         日本語 JA-JP
        // Italian:          Italiano IT-IT
        // Korean:           한국어 KO-KR


    	
        function i18nConfig($translateProvider) {

            $translateProvider.useStaticFilesLoader({
                prefix: 'app/i18n/',
                suffix: '.json'
            });

            $translateProvider.preferredLanguage('en');
            $translateProvider.useSanitizeValueStrategy('sanitizeParameters');
        }


        function LangCtrl($scope, $translate) {
          //  $scope.lang = 'English';
           
        	
        
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
	                     $translate.use('ru');
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
        }

})(); 
