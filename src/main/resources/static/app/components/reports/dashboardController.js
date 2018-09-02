angular
    .module('altairApp')
    .controller('statisticCtrl', [
        '$rootScope',
        '$scope',
        '$interval',
        '$window',
        '$timeout',
        'variables',
        'statscount',
        'statscount2',
        'statscount3',
        function ($rootScope, $scope, $interval, $window, $timeout, variables, statscount, statscount2, statscount3) {
            $scope.statGeo = statscount3;
            $scope.planchartoption = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: function (params) {
                        return params[0].name + "<br>" + params[0].seriesName + " : " + params[0].value + "<br>" + params[1].seriesName + " : " + ((-1) * params[1].value);
                    }
                },
                color: ["#ffa000", "#e53935"],
                grid: {
                    containLabel: true
                },
                legend: {
                    data: ['Засварт буцаасан төлөвлөгөө', 'Илгээсэн төлөвлөгөө'],
                    y: "bottom"
                },
                title: {
                    text: 'Жилийн төлөвлөгөөний явцын статистик',
                    textStyle: {
                        fontFamily: "Roboto",
                        fontWeight: "normal"
                    }
                },
                yAxis: {
                    type: 'category',
                    data: ["Хүлээн авах хэсэг", "Нөөцийн хэсэг", "Технологийн хэсэг", "Бүт/борлуулалтын хэсэг", "Эдийн засгийн хэсэг", "Эцсийн шийдвэр"],
                    axisLabel: {
                        textStyle: {
                            fontFamily: "Roboto",
                            fontWeight: "normal"
                        }
                    }
                },
                xAxis: {
                    type: 'value'
                },
                toolbox: {
                    right: 30,
                    feature: {
                        saveAsImage: {
                            title: 'Зургаар татаж авах'
                        }
                    }
                },
            };
            $scope.plandata = [
                {
                    name: 'Илгээсэн төлөвлөгөө',
                    type: 'bar',
                    data: statscount.stat3,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'right'
                        }
                    },
                    stack: 'group1'
                },
                {
                    name: 'Засварт буцаасан төлөвлөгөө',
                    type: 'bar',
                    data: statscount.stat23,
                    itemStyle: {
                        normal: {
                            label: {
                                formatter: function (params) {
                                    return (-1) * params.value;
                                },
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'left'
                        }
                    },
                    stack: 'group1'
                }
            ];

            $scope.reportchartoption = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: function (params) {
                        return params[0].name + "<br>" + params[0].seriesName + " : " + params[0].value + "<br>" + params[1].seriesName + " : " + ((-1) * params[1].value);
                    }
                },
                color: ["#ffa000", "#e53935"],
                grid: {
                    containLabel: true
                },
                legend: {
                    data: ['Засварт буцаасан тайлан', 'Илгээсэн тайлан'],
                    y: "bottom"
                },
                title: {
                    text: 'Жилийн тайлангийн явцын статистик',
                    textStyle: {
                        fontFamily: "Roboto",
                        fontWeight: "normal"
                    }
                },
                yAxis: {
                    type: 'category',
                    data: ["Хүлээн авах хэсэг", "Нөөцийн хэсэг", "Технологийн хэсэг", "Бүтээгдэхүүн борлуулалтын хэсэг", "Эдийн засгийн хэсэг", "Эцсийн шийдвэр"],
                    axisLabel: {
                        textStyle: {
                            fontFamily: "Roboto",
                            fontWeight: "normal"
                        }
                    }
                },
                xAxis: {
                    type: 'value'
                },
                toolbox: {
                    right: 30,
                    feature: {
                        saveAsImage: {
                            title: 'Зургаар татаж авах'
                        }
                    }
                },
            };
            $scope.reportdata = [
                {
                    name: 'Илгээсэн тайлан',
                    type: 'bar',
                    data: statscount.stat4,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'right'
                        }
                    },
                    stack: 'group2'
                },
                {
                    name: 'Засварт буцаасан тайлан',
                    type: 'bar',
                    data: statscount.stat24,
                    itemStyle: {
                        normal: {
                            label: {
                                formatter: function (params) {
                                    return (-1) * params.value;
                                },
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'left'
                        }
                    },
                    stack: 'group2'
                }
            ];

            $scope.xplanchartoption = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                color: ["#3f51b5"],
                grid: {
                    containLabel: true
                },
                title: {
                    text: 'X тайлан/төлөвлөгөөний статистик',
                    textStyle: {
                        fontFamily: "Roboto",
                        fontWeight: "normal"
                    }
                },
                yAxis: {
                    type: 'category',
                    data: ["X төлөвлөгөө", "X тайлан"],
                    axisLabel: {
                        textStyle: {
                            fontFamily: "Roboto",
                            fontWeight: "normal"
                        }
                    }
                },
                xAxis: {
                    type: 'value'
                },
                toolbox: {
                    right: 30,
                    feature: {
                        saveAsImage: {
                            title: 'Зургаар татаж авах'
                        }
                    }
                },
            };
            $scope.xplandata = [
                {
                    type: 'bar',
                    data: statscount.statx,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    },
                }
            ];

            $scope.planchartrepstatusoption = {
                tooltip: {
                    trigger: 'item',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                color: ["#7cb342", "#e53935", "#ffa000","#2196f3"],
                grid: {
                    /*containLabel: true*/
                },
                title: {
                    text: 'Жилийн тайлангийн статистик /төлөвөөр/',
                    textStyle: {
                        fontFamily: "Roboto",
                        fontWeight: "normal"
                    }
                },
                /*yAxis: {
                 type : 'category',
                 data: ["Баталгаажсан", "Засварт буцаасан","Илгээсэн"],
                 },
                 xAxis: {
                 type : 'value'
                 },*/
                toolbox: {
                    right: 30,
                    feature: {
                        saveAsImage: {
                            title: 'Зургаар татаж авах'
                        }
                    }
                },
            };
            $scope.planrepstatusdata = [
                {
                    type: 'pie',
                    data: statscount.statrepstatus4,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                }
            ];

            $scope.planchartrepstatus3option = {
                tooltip: {
                    trigger: 'item',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                color: ["#7cb342", "#e53935", "#ffa000","#2196f3"],
                grid: {
                    /*containLabel: true*/
                },
                title: {
                    text: 'Жилийн төлөвлөгөөний статистик /төлөвөөр/',
                    textStyle: {
                        fontFamily: "Roboto",
                        fontWeight: "normal"
                    }
                },
                /*yAxis: {
                 type : 'category',
                 data: ["Баталгаажсан", "Засварт буцаасан","Илгээсэн"],
                 },
                 xAxis: {
                 type : 'value'
                 },*/
                toolbox: {
                    right: 30,
                    feature: {
                        saveAsImage: {
                            title: 'Зургаар татаж авах'
                        }
                    }
                },
            };
            $scope.planrepstatus3data = [
                {
                    type: 'pie',
                    data: statscount.statrepstatus3,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                }
            ];

            $scope.planchartoptionbydeposit = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                dataZoom: {
                    show: true,
                    realtime: true,
                    start: 20,
                    end: 80
                },
                color: ["#2196f3", "#009688"],
                grid: {
                    containLabel: true
                },
                legend: {
                    data: ['Зөвшөөрсөн төлөвлөгөө', 'Нийт төлөвлөгөө'],
                },
                title: {
                    text: 'Жилийн төлөвлөгөөний явцын статистик',
                    subtext: '/ашигт малтмалын төрлөөр/',
                    textStyle: {
                        fontFamily: "Roboto",
                        fontWeight: "normal"
                    }
                },
                xAxis: {
                    type: 'category',
                    data: statscount2.depslabels,
                    axisLabel: {
                        textStyle: {
                            fontFamily: "Roboto",
                            fontWeight: "normal"
                        },
                    },
                    axisTick: {
                        alignWithLabel: true
                    }
                },
                yAxis: {
                    type: 'value'
                },
                toolbox: {
                    right: 30,
                    feature: {
                        saveAsImage: {
                            title: 'Зургаар татаж авах'
                        }
                    }
                },
            };
            $scope.plandatabydeposit = [
                {
                    name: 'Нийт төлөвлөгөө',
                    type: 'bar',
                    data: statscount2.deps3all,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                },
                {
                    name: 'Зөвшөөрсөн төлөвлөгөө',
                    type: 'bar',
                    data: statscount2.deps31,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                }
            ];

            $scope.reportchartoptionbydeposit = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                dataZoom: {
                    show: true,
                    realtime: true,
                    start: 20,
                    end: 80
                },
                color: ["#2196f3", "#009688"],
                grid: {
                    containLabel: true
                },
                legend: {
                    data: ['Зөвшөөрсөн тайлан', 'Нийт тайлан'],
                },
                title: {
                    text: 'Жилийн тайлангийн явцын статистик',
                    subtext: '/ашигт малтмалын төрлөөр/',
                    textStyle: {
                        fontFamily: "Roboto",
                        fontWeight: "normal"
                    }
                },
                xAxis: {
                    type: 'category',
                    data: statscount2.depslabels,
                    axisLabel: {
                        textStyle: {
                            fontFamily: "Roboto",
                            fontWeight: "normal"
                        },
                    },
                    axisTick: {
                        alignWithLabel: true
                    }
                },
                yAxis: {
                    type: 'value'
                },
                toolbox: {
                    right: 30,
                    feature: {
                        saveAsImage: {
                            title: 'Зургаар татаж авах'
                        }
                    }
                },
            };
            $scope.reportdatabydeposit = [
                {
                    name: 'Нийт тайлан',
                    type: 'bar',
                    data: statscount2.deps4all,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                },
                {
                    name: 'Зөвшөөрсөн тайлан',
                    type: 'bar',
                    data: statscount2.deps41,
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                }
            ];

            $scope.reportchartoptionbydepositgeo = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: function (params) {
                        var returnStr = "";
                        for(var iIterator=0; iIterator<params.length; iIterator++){
                            returnStr = returnStr + (params[iIterator].data.name + " - " + params[iIterator].value + "<br>");
                        }
                        return returnStr;
                    }
                },
                color:["#2196f3", "#009688"],
                grid: {
                    containLabel: true
                },
                title: {
                    text: 'Ашиглалтын тусгай зөвшөөрөлд хайгуулын ажлын',
                    subtext: 'тайлан/төлөвлөгөө илгээсэн ажлын статистик',
                    textStyle: {
                        fontFamily: "Roboto",
                        fontWeight: "normal"
                    }
                },
                xAxis : [
                    {
                        type : 'category',
                        data : ['Тайлан','Төлөвлөгөө']
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ]
            };
            $scope.reportdatabydepositgeo = [
                {
                    type:'bar',
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    data:$scope.statGeo.geomining3,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                },
                {
                    type:'bar',
                    itemStyle: {
                        normal: {
                            label: {
                                textStyle: {
                                    fontFamily: "Roboto",
                                    fontWeight: "normal"
                                }
                            }
                        }
                    },
                    data:$scope.statGeo.geomining4,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                }
            ];
        }
    ])

;