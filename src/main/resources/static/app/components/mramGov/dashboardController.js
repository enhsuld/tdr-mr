angular
    .module('altairApp')
    .controller('statisticCtrl', [
        '$rootScope',
        '$scope',
        '$interval',
        '$window',
        '$timeout',
        'user_data',
        'stats',
        'variables',
        function ($rootScope,$scope,$interval,$window,$timeout,user_data,stats,variables) {
        // statistics
            $scope.user=user_data;
            console.log($scope.user);
            $scope.dynamicStats = [
                {
                    id: '1',
                    title: 'Жилийн төлөвлөгөө илгээх тусгай зөвшөөрөл',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: ["#d84315"],
                        padding: 0.2
                    }
                },
                {
                    id: '2',
                    title: 'Жилийн тайлан илгээх тусгай зөвшөөрөл',
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: "#d1e4f6",
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '3',
                    title: 'Х төлөвлөгөө ирүүлсэн',
                    count: '0',
                    chart_data: [ '64/100' ],
                    chart_options: {
                        height: 24,
                        width: 24,
                        fill: ["#8bc34a", "#eee"]
                    }
                },
                {
                    id: '4',
                    title: 'Х тайлан ирүүлсэн',
                    count: '1',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2,5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: "#d1e4f6",
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '5',
                    title: 'Нийт ирүүлсэн төлөвлөгөө',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: "#d1e4f6",
                        padding: 0.2
                    }
                },
                {
                    id: '6',
                    title: 'ААН-д буцаагдсан төлөвлөгөө',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: "#d1e4f6",
                        padding: 0.2
                    }
                },
                {
                    id: '7',
                    title: 'Танилцагдаж байгаа төлөвлөгөө',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: "#d1e4f6",
                        padding: 0.2
                    }
                },
                {
                    id: '8',
                    title: 'Нийт зөвшөөрсөн төлөвлөгөө',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: "#d1e4f6",
                        padding: 0.2
                    }
                },
                {
                    id: '9',
                    title: 'Хүлээн авах хэсэг',
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: "#d1e4f6",
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '10',
                    title: 'Нөөцийн хэсэг',
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: "#d1e4f6",
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '11',
                    title: 'Технологийн хэсэг',
                    count: '1',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2,5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: "#d1e4f6",
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '12',
                    title: 'Бүтээгдэхүүн борлуулалтын хэсэг',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: "#d1e4f6",
                        padding: 0.2
                    }
                },
                {
                    id: '13',
                    title: 'Эдийн засгийн хэсэг',
                    count: '1',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2,5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '14',
                    title: 'Эцсийн шийдвэр хэсэг',
                    count: '0',
                    chart_data: [ 5,2,4,7,12,9,7,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '15',
                    title: 'Хүлээлгэн өгөх хэсэг',
                    count: '1',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2,5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '16',
                    title: 'Нийт ирүүлсэн тайлан',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: ["#d84315"],
                        padding: 0.2
                    }
                },
                {
                    id: '17',
                    title: 'ААН-д буцаагдсан тайлан',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: ["#d84315"],
                        padding: 0.2
                    }
                },
                {
                    id: '18',
                    title: 'Танилцагдаж байгаа тайлан',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: ["#d84315"],
                        padding: 0.2
                    }
                },
                {
                    id: '19',
                    title: 'Нийт зөвшөөрөгдсөн тайлан',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: ["#d84315"],
                        padding: 0.2
                    }
                },
                {
                    id: '20',
                    title: 'Хүлээн авах хэсэг',
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '21',
                    title: 'Нөөцийн хэсэг',
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '22',
                    title: 'Технологийн хэсэг',
                    count: '1',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2,5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '23',
                    title: 'Бүтээгдэхүүн борлуулалтын хэсэг',                  
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7 ],
                    chart_options: {
                        height: 28,
                        width: 48,
                        fill: ["#d84315"],
                        padding: 0.2
                    }
                },
                {
                    id: '24',
                    title: 'Эдийн засгийн хэсэг',
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '25',
                    title: 'Эцсийн шийдвэр хэсэг',
                    count: '0',
                    chart_data: [ 5,3,9,6,5,9,7,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '26',
                    title: 'Хүлээлгэн өгөх хэсэг',
                    count: '1',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2,5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '27',
                    title: 'Нууц дугаар авсан хэрэглэгч',
                    count: '1',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2,5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                },
                {
                    id: '28',
                    title: 'Тохиргоо хийсэн',
                    count: '1',
                    chart_data: [ 5,3,9,6,5,9,7,3,5,2,5,3,9,6,5,9,7,3,5,2 ],
                    chart_options: {
                        height: 28,
                        width: 64,
                        fill: ["#d84315"],
                        stroke: "#0288d1"
                    }
                }
            ];
            

            // countUp update
                $scope.$on('onLastRepeat', function (scope, element, attrs) {
                    $scope.dynamicStats[0].count = stats.plan;
                    $scope.dynamicStats[1].count = stats.report;
                    $scope.dynamicStats[2].count = stats.xtul;
                    $scope.dynamicStats[3].count = stats.xtal;
                    $scope.dynamicStats[4].count = stats.anplan;
                    $scope.dynamicStats[5].count = stats.butssantuluwluguu;
                    $scope.dynamicStats[6].count = stats.tushalgajbga;
                    $scope.dynamicStats[7].count = stats.huleenavsantuluwluguu;
                    $scope.dynamicStats[8].count = stats.anpstep1;
                    $scope.dynamicStats[9].count = stats.anpstep2;
                    $scope.dynamicStats[10].count = stats.anpstep3;
                    $scope.dynamicStats[11].count = stats.anpstep4;
                    $scope.dynamicStats[12].count = stats.anpstep5;
                    $scope.dynamicStats[13].count = stats.anpstep6;
                    $scope.dynamicStats[14].count = stats.anpstep7;
                    $scope.dynamicStats[15].count = stats.anreport;
                    $scope.dynamicStats[16].count = stats.repstatus2t;
                    $scope.dynamicStats[17].count = stats.tashalgajbga;
                    $scope.dynamicStats[18].count = stats.huleenavsantailan;
                    $scope.dynamicStats[19].count = stats.anrstep1;
                    $scope.dynamicStats[20].count = stats.anrstep2;
                    $scope.dynamicStats[21].count = stats.anrstep3;
                    $scope.dynamicStats[22].count = stats.anrstep4;
                    $scope.dynamicStats[23].count = stats.anrstep5;
                    $scope.dynamicStats[24].count = stats.anrstep6;
                    $scope.dynamicStats[25].count = stats.anrstep7;
                    $scope.dynamicStats[26].count = stats.activeUser;
                    $scope.dynamicStats[27].count = stats.configPlan;

                });

        }
    ])

;