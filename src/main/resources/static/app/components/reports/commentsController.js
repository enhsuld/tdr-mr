angular
    .module('altairApp')
    .controller("commentsController", [
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
        'comments',
        'comments_main',
        function ($scope, $rootScope, $state, utils, mainService, sweet, lic_type, app_status, rep_type, user_data, mineralData, p_deposit, comments, comments_main) {
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

            $scope.PlanExploration = {
                dataSource: {
                    data: comments,
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
                columns: [
                    {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                    {field: "note", title: "Ажил", width: "150px"},
                    {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                    {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                    {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                    {field: "repstatusid",values: $scope.repstatuses,title: "Төлөв",width: "100px"},
                    {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                    {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                    {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                    {field: "xtype",title: "X эсэх",template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",width: "80px"},
                    {field: "reportyear", title: "Он", width: "75px"},
                    {field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},
                    {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                    {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                    {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                    {field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},
                    {field: "locationaimag", title: "Аймаг", width: "100px"},
                    {field: "locationsoum", title: "Сум", width: "100px"},
                    {field: "areaname", title: "Талбай", width: "100px"},
                    {field: "areasize", title: "Талбайн хэмжээ", width: "100px"},
                    {field: "comnote", title: "Тайлбар", width: "250px", template:"#= comnote #"},
                    {field: "comdate", title: "Огноо", width: "150px"},
                    {field: "desicionid", title: "Шийдвэр", width: "100px", values:$scope.repstatuses},
                    {field: "officername", title: "Мэргэжилтэн", width: "125px"},
                ],
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

            $scope.PlanExploration2 = {
                dataSource: {
                    data: comments_main,
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
                columns: [
                    {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                    {field: "lpname", title: "Байгууллагын нэр", width: "150px"},
                    {field: "lpreg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                    {field: "licensexb", title: "Лицензийн дугаар", width: "100px"},
                    {field: "repstatusid",values: $scope.repstatuses,title: "Төлөв",width: "100px"},
                    {field: "divisionid", title: "Хэлтэс", width: "100px", values: $scope.divisions, hidden:true},
                    {field: "lictype",width: "100px",title: "Лицензийн төрөл",values: lic_type},
                    {field: "reporttype", title: "Төрөл", values: rep_type, width: "100px"},
                    {field: "xtype",title: "X эсэх",template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",width: "80px"},
                    {field: "reportyear", title: "Он", width: "75px"},
                    {field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},
                    {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "100px"},
                    {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "100px"},
                    {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "100px"},
                    {field: "add_bunlicensenum", title: "Нэмэлт ТЗ", width: "100px"},
                    {field: "locationaimag", title: "Аймаг", width: "100px"},
                    {field: "locationsoum", title: "Сум", width: "100px"},
                    {field: "areaname", title: "Талбай", width: "100px"},
                    {field: "areasize", title: "Талбайн хэмжээ", width: "100px"},
                    {field: "mcomment", title: "Тайлбар", width: "250px", template:"#= mcomment #"},
                    {field: "createddate", title: "Огноо", width: "150px"},
                    {field: "desid", title: "Шийдвэр", width: "100px", values:$scope.repstatuses},
                    {field: "username", title: "Мэргэжилтэн", width: "125px"},
                ],
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

        }
    ]);
