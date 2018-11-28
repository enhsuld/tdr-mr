angular
    .module('altairApp')
    .controller("reportViewerController", [
        '$scope',
        '$rootScope',
        '$state',
        'utils',
        'mainService',
        'sweet',
        'lic_type',
        'app_status',
        'rep_type',
        'user_data',
        'mineralData',
        'p_deposit',
        '$stateParams',
        function ($scope, $rootScope, $state, utils, mainService, sweet, lic_type, app_status, rep_type, user_data, mineralData, p_deposit, $stateParams) {
            $scope.mingroups = [];

            $scope.todotgolVals = [{
                text: "Үгүй",
                value: false
            }, {text: "Тийм", value: true}];

            $scope.repstatuses = [{
                text: "Засварт буцаасан",
                value: "2"
            }, {text: "Илгээсэн", value: "7"}, {text: "Баталгаажсан", value: "1"}, {text: "Татгалзсан", value: "3"}];
            $scope.divisions = [{
                text: "Уул уурхайн үйлдвэрлэл, технологийн хэлтэс",
                value: 1
            }, {text: "Нүүрсний судалгааны хэлтэс", value: 2}, {text: "Геологи, хайгуулын хэлтэс", value: 3}];
            if (user_data.divisionid == 1) {
                $scope.mingroups = [{text: "Металл", value: 1}, {text: "Металл бус", value: 2}];
            }
            else if (user_data.divisionid == 2) {
                $scope.mingroups = [{text: "Нүүрс", value: 8}];
            }
            else if (user_data.divisionid == 3) {
                $scope.mingroups = [{text: "Үнэт, өнгөт металл", value: 10}, {
                    text: "ЦИМ, ГХЭ, хар ховор металл",
                    value: 11
                }, {
                    text: "Металл бус, түгээмэл тархацтай ашигт малтмал",
                    value: 12
                }, {text: "Нүүрс,  уламжлалт бус газрын тос", value: 13}];
            }

            $scope.columnHeaders = {};



            $scope.columnHeaders['DATA_MIN_PLAN_5'] = [
                {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                /*{field: "repstatusid",values: $scope.repstatuses,title: "Төлөв",width: "100px"},*/
                {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                /*{field: "xtype",title: "X эсэх",template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",width: "80px"},*/
                {field: "reportyear", title: "Он", width: "75px"},
                /*{field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},*/
                {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                {field: "data1", title: "Д/д", width: "100px"},
                {field: "data1", title: "Урвалжын нэр", width: "150px"},
                {field: "data2", title: "Химийн томьео", width: "150px"},
                {field: "data3", title: "Зориулалт", width: "150px"},
                {title:"Зарцуулалт", columns:[
                    {field: "data4", title: "Хэмжих нэгж", width: "150px"},
                    {field: "data5", title: "Тоон утга", width: "150px"}]},
                {title:"Нийт хэрэглээ", columns:[
                    {field: "data6", title: "Хэмжих нэгж", width: "150px"},
                        {field: "data7", title: "Тоон утга", width: "150px"}]},
                {field: "data8", title: "Тайлбар", width: "150px"},

            ];

            $scope.columnHeaders['DATA_EXCEL_MINREP5'] = [
                {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                /*{field: "repstatusid",values: $scope.repstatuses,title: "Төлөв",width: "100px"},*/
                {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                /*{field: "xtype",title: "X эсэх",template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",width: "80px"},*/
                {field: "reportyear", title: "Он", width: "75px"},
                /*{field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},*/
                {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                {field: "data1", title: "Д/д", width: "100px"},
                {field: "data1", title: "Урвалжын нэр", width: "150px"},
                {field: "data2", title: "Химийн томьео", width: "150px"},
                {field: "data3", title: "Зориулалт", width: "150px"},
                {title:"Зарцуулалт", columns:[
                        {field: "data4", title: "Хэмжих нэгж", width: "150px"},
                        {field: "data5", title: "Тоон утга", width: "150px"}]},
                {title:"Нийт хэрэглээ", columns:[
                        {field: "data6", title: "Хэмжих нэгж", width: "150px"},
                        {field: "data7", title: "Тоон утга", width: "150px"}]},
                {field: "data8", title: "Тайлбар", width: "150px"},
                {field: "data9", title: "Тайлбар", width: "150px"},
                {field: "data10", title: "Тайлбар", width: "150px"},
                {field: "data11", title: "Тайлбар", width: "150px"},
            ];

            $scope.columnHeaders['DATA_EXCEL_MINREP6_1'] = [
                {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                {field: "reportyear", title: "Он", width: "75px"},
                {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                {field: "data1", title: "Д/д", width: "100px"},
                {field: "data2", title: "Хэрэглэгчдийн жагсаалт", width: "150px"},
                {field: "data3", title: "Хэмжих нэгж", width: "150px"},
                {field: "data4", title: "Тоон утга", width: "150px"},
                {field: "data5", title: "Тайлбар", width: "150px"},
                {field: "data6", title: "Тайлбар", width: "150px"},
            ];

            $scope.columnHeaders['DATA_MIN_PLAN_6_1'] = [
                {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                {field: "reportyear", title: "Он", width: "75px"},
                {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                {field: "data1", title: "Д/д", width: "100px"},
                {field: "data2", title: "Хэрэглэгчдийн жагсаалт", width: "150px"},
                {field: "data3", title: "Хэмжих нэгж", width: "150px"},
                {field: "data4", title: "Тоон утга", width: "150px"},
                {field: "data5", title: "Тайлбар", width: "150px"},
            ];

            $scope.columnHeaders['DATA_MIN_PLAN_6_2'] = [
                {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                /*{field: "repstatusid",values: $scope.repstatuses,title: "Төлөв",width: "100px"},*/
                {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                /*{field: "xtype",title: "X эсэх",template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",width: "80px"},*/
                {field: "reportyear", title: "Он", width: "75px"},
                /*{field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},*/
                {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                /*{field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},
                {field: "locationaimag", title: "Аймаг", width: "100px"},
                {field: "locationsoum", title: "Сум", width: "100px"},
                {field: "areaname", title: "Талбай", width: "100px"},
                {field: "areasize", title: "Талбайн хэмжээ", width: "100px"}*/
                {field: "data1", title: "Д/д", width: "100px"},
                {field: "data2", title: "Хэрэглэгчдийн жагсаалт", width: "150px"},
                {field: "data3", title: "Усны эх үүсвэр", width: "150px"},
                {field: "data4", title: "Хэмжих нэгж", width: "150px"},
                {field: "data5", title: "Тоон утга", width: "150px"},
                {field: "data6", title: "Шоо метр дэхь үнэ тариф", width: "150px"},
                {field: "data7", title: "Төлбөрийн хэмжээ (сая.төг)", width: "150px"},
                {field: "data8", title: "Тайлбар", width: "150px"},
                {field: "data9", title: "Усны сав газар", width: "150px"},

            ];

            $scope.columnHeaders['DATA_MIN_PLAN_8'] = [
                {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                /*{field: "repstatusid",values: $scope.repstatuses,title: "Төлөв",width: "100px"},*/
                {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                /*{field: "xtype",title: "X эсэх",template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",width: "80px"},*/
                {field: "reportyear", title: "Он", width: "75px"},
                /*{field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},*/
                {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                /*{field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},
                {field: "locationaimag", title: "Аймаг", width: "100px"},
                {field: "locationsoum", title: "Сум", width: "100px"},
                {field: "areaname", title: "Талбай", width: "100px"},
                {field: "areasize", title: "Талбайн хэмжээ", width: "100px"}*/
                {title:"Тоног төхөөрөмжийн нэр", columns:[
                    {field: "data1", title: "Төрөл", width: "100px"},
                    {field: "data2", title: "Марк", width: "150px"},
                    {field: "data3", title: "Загвар", width: "150px"},
                    {field: "data4", title: "Үйлдвэрлэсэн улс", width: "150px"},
                ]},
                {title:"Хүчин чадал багтаамж", columns:[
                    {field: "data5", title: "Хэмжих нэгж", width: "150px"},
                    {field: "data6", title: "Хэмжээ", width: "150px"},
                ]},
                {title:"Ашиглалтын хугацаа", columns:[
                    {field: "data7", title: "Үйлдвэрлэсэн он", width: "150px"},
                    {field: "data8", title: "Ашиглалтанд өгсөн он", width: "150px"},
                    {field: "data9", title: "Ашиглалтын хугацаа", width: "150px"},
                ]},
                {title:"Цахилгаан зарцуулалт", columns:[
                    {field: "data10", title: "Суурилагдсан чадал кВт (kW)", width: "150px"},
                ]},
                {title:"Түлш зарцуулалт", columns:[
                    {field: "data11", title: "Хэмжих нэгж", width: "150px"},
                    {field: "data12", title: "Тоон утга (литр)", width: "150px"},
                    {field: "data13", title: "Нийт ажиллах гүйлт (мото/цаг)", width: "150px"},
                    {field: "data14", title: "Зарцуулах түлшний хэмжээ", width: "150px"},
                ]},

                {field: "data15", title: "Өмчлөлийн хэлбэр", width: "150px"},
                {field: "data16", title: "Тайлбар", width: "150px"},
            ];

            $scope.columnHeaders['DATA_MIN_PLAN_9'] = [
                {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                /*{field: "repstatusid",values: $scope.repstatuses,title: "Төлөв",width: "100px"},*/
                {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                /*{field: "xtype",title: "X эсэх",template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",width: "80px"},*/
                {field: "reportyear", title: "Он", width: "75px"},
                /*{field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},*/
                {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                {field: "data1", title: "Д/д", width: "100px"},
                {field: "data2", title: "Хэрэглэгчдийн жагсаалт", width: "150px"},
                {field: "data3", title: "Усны эх үүсвэр", width: "150px"},
                {field: "data4", title: "Хэмжих нэгж", width: "150px"},
                {field: "data5", title: "Тоон утга", width: "150px"},
                {field: "data6", title: "Шоо метр дэхь үнэ тариф", width: "150px"},
                {field: "data7", title: "Төлбөрийн хэмжээ (сая.төг)", width: "150px"},
                {field: "data8", title: "Тайлбар", width: "150px"},
                {field: "data9", title: "Усны сав газар", width: "150px"},

            ];
            mainService.withdomain("POST","/import/comments/"+$stateParams.formname).then(function(data){
                $scope.PlanExploration = {
                    dataSource: {
                        data: data,
                        pageSize: 20
                    },
                    excel: {
                        fileName: "Export.xlsx",
                        proxyURL: "https://demos.telerik.com/kendo-ui/service/export",
                        filterable: true,
                        allPages: true
                    },
                    toolbar: ["excel"],
                    sortable: {
                        mode: "multiple",
                        allowUnsort: true
                    },
                    scrollable: true,
                    height: function () {
                        return $(document).height() * 0.7;
                    },

                    resizable: true,
                    pageable: {
                        refresh: true,
                        buttonCount: 3
                    },
                    columns: $scope.columnHeaders[$stateParams.formname],
                    dataBound: function () {
                        var rows = this.items();
                        $(rows).each(function () {
                            var index = $(this).index() + 1
                                + ($(".k-grid").data("kendoGrid").dataSource.pageSize() * ($(".k-grid").data("kendoGrid").dataSource.page() - 1));
                            ;
                            var rowLabel = $(this).find(".row-number");
                            $(rowLabel).html(index);
                        });
                    },
                    editable: "popup"
                };
            });

        }
    ]);
