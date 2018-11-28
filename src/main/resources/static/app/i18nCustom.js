angular.module('app.i18n', ['pascalprecht.translate'])
    .config(['$translateProvider', function ($translateProvider) {
        $translateProvider.preferredLanguage('mn');
        $translateProvider.useLoader('asyncLoader');
    }])
    .controller(
        'LangCtrl',
        [
            '$rootScope',
            '$scope',
            '$translate',
            function ($rootScope, $scope, $translate) {
                $scope.changeLanguage = function (langKey) {
                    $translate.use(langKey);
                };
                // language switcher
                $scope.langSwitcherModel = 'mn';

                var langData = $scope.langSwitcherOptions = [
                    {id: 1, title: 'Монгол', value: 'mn'},
                    {id: 3, title: 'Англи', value: 'gb'}
                ];

                $scope.langSwitcherConfig = {
                    maxItems: 1,
                    render: {
                        option: function (langData, escape) {
                            return '<div class="option">' +
                                '<i class="item-icon flag-' + escape(langData.value).toUpperCase() + '"></i>' +
                                '<span>' + escape(langData.title) + '</span>' +
                                '</div>';
                        },
                        item: function (langData, escape) {
                            return '<div class="item"><i class="item-icon flag-' + escape(langData.value).toUpperCase() + '"></i></div>';
                        }
                    },
                    valueField: 'value',
                    labelField: 'title',
                    searchField: 'title',
                    create: false,
                    onInitialize: function (selectize) {
                        $('#lang_switcher').next().children('.selectize-input').find('input').attr('readonly', true);
                    },
                    onChange: function (value) {
                        console.log(value);
                        switch (value) {
                            case 'gb':
                                $translate.use('en');
                                break;
                            case 'mn':
                                $translate.use('mn');
                                break;
                        }
                    }

                };
            }
        ]
    );
