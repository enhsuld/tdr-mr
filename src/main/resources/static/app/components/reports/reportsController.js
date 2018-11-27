angular
    .module('altairApp')
    .controller("reportsViewController", [
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
        function ($scope, $rootScope, $state, utils, mainService, sweet, lic_type, app_status, rep_type, user_data, mineralData, p_deposit) {
            //console.log(mineralData);
            $scope.mingroups = [];
            $scope.repsteps = [];

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

            if (user_data.divisionid == 1 || user_data.divisionid == 2) {
                $scope.repsteps = [{text: "Хүлээн авах хэсэг", value: 1}, {
                    text: "Нөөцийн хэсэг",
                    value: 2
                }, {text: "Технологийн хэсэг", value: 3}, {
                    text: "Бүтээгдэхүүн борлуулалтын хэсэг",
                    value: 4
                }, {text: "Эдийн засгийн хэсэг", value: 5}, {text: "Эцсийн шийдвэр", value: 6}];
            }
            else if (user_data.divisionid == 3) {
                $scope.repsteps = [{text: "Хүлээн авах хэсэг", value: 1}, {
                    text: "Геологийн хэсэг",
                    value: 8
                }, {text: "Зардлын доод хэмжээ", value: 9}, {
                    text: "Хүлээлгэн өгөх",
                    value: 10
                }];
            }
            $scope.repsteps.unshift({text: "Хоосон", value: 0})

            $scope.loadOnCheck = function (item) {
                if (item.reporttype == 3) {
                    if (item.lictype == 1) {
                        $state.go('restricted.pages.GovPlanFormH', {param: item.id, id: item.repstepid});
                    }
                    else {
                        $state.go('restricted.pages.GovPlanFormA', {param: item.id, id: item.repstepid});
                    }
                }
                else {
                    if (item.lictype == 1) {
                        $state.go('restricted.pages.GovReportFormH', {param: item.id, groupid: item.groupid});
                    }
                    else {
                        $state.go('restricted.pages.GovReportFormA', {param: item.id, groupid: item.groupid});
                    }
                }

            };

            $scope.checkTransferRole = function () {
                return ((user_data.username == 'Лхагвабаатар' || user_data.username == 'Төрмөнх') && user_data.lpreg == 9999999);
            }

            $scope.transferSteps = function (item) {
                if ($scope.checkTransferRole() && item.xtype != 0) {
                    $scope.currentItem = item;
                    UIkit.modal("#transferModal").show();
                }
            }

            $scope.selectize_config = {
                plugins: {
                    'disable_options': {
                        disableOptions: ["c1", "c2"]
                    }
                },
                create: false,
                maxItems: 1,
                placeholder: 'Сонгох...',
                valueField: 'value',
                labelField: 'text',
                searchField: 'text'
            };

            $scope.transferItemSubmit = function () {
                mainService.withdata('post', '/submit/transfer', $scope.currentItem).then(function (data) {
                    if (data.status == true) {
                        sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
                        $(".k-grid").data("kendoGrid").dataSource.read();
                        UIkit.modal("#transferModal").hide();
                    }
                    else {
                        sweet.show('Анхаар', 'Амжилтгүй.', 'warning');
                    }
                });
            }

            $scope.PlanExploration = {
                dataSource: {

                    transport: {
                        read: {
                            url: "/user/angular/AnnualRegistrationColor",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST",
                            data: function () {
                                if (user_data.divisionid == 1) {
                                    return {"custom": "where repstatusid != 0 and minid!= 5 and ((divisionid = 1) or (DIVISIONID = 3 and LICENSEXB like '%MV-%' and xtype != 0))"};
                                }
                                else if (user_data.divisionid == 2) {
                                    return {"custom": "where repstatusid != 0 and minid= 5 and ((divisionid = 2) or (DIVISIONID = 3 and LICENSEXB like '%MV-%' and xtype != 0))"};
                                }
                                else {
                                    return {"custom": "where repstatusid != 0 and divisionid = " + user_data.divisionid};
                                }
                            },
                        },
                        parameterMap: function (options) {
                            return JSON.stringify(options);
                        }
                    },
                    schema: {
                        data: "data",
                        total: "total",
                        model: {
                            id: "id",
                            fields: {
                                id: {editable: false, nullable: true},
                                istodotgol: {type: "boolean"}
                            }
                        }
                    },
                    pageSize: 20,
                    serverPaging: true,
                    serverFiltering: true,
                    serverSorting: true,
                    filter: []
                },
                excel: {
                    fileName: "Export.xlsx",
                    proxyURL: "https://demos.telerik.com/kendo-ui/service/export",
                    filterable: true,
                    allPages: true
                },
                toolbar: ["excel"],
                filterable: {
                    mode: "row"
                },
                columnMenu: true,
                sortable: {
                    mode: "multiple",
                    allowUnsort: true
                },
                scrollable: true,
                resizable: true,
                pageable: {
                    refresh: true,
                    buttonCount: 3
                },
                columns: [
                    {title: "#", template: "<span class='row-number'></span>", width: "50px"},
                    {field: "lpName", title: "Байгууллагын нэр", width: "150px"},
                    {field: "lpReg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                    {field: "licenseXB", title: "Лицензийн дугаар", width: "150px"},
                    {field: "divisionid", title: "Хэлтэс", width: "150px", values: $scope.divisions},
                    {
                        field: "lictype",
                        width: "150px",
                        title: "Лицензийн төрөл",
                        filterable: {
                            multi: true,
                            dataSource: [{
                                lictype: 'Хайгуулын тусгай зөвшөөрөл',
                                value: 1
                            }, {lictype: 'Ашиглалтын тусгай зөвшөөрөл', value: 2}, {
                                lictype: 'Баяжуулах үйлдвэр',
                                value: 3
                            }]
                        },
                        values: lic_type
                    },
                    {field: "reporttype", title: "Төрөл", values: rep_type, width: "150px"},
                    {
                        field: "xtype",
                        title: "X эсэх",
                        template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",
                        width: "150px"
                    },
                    {field: "reportyear", title: "Он", width: "150px"},
                    {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "150px"},
                    {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "150px"},
                    {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "150px"},
                    {
                        field: "repstatusid",
                        values: $scope.repstatuses,
                        template: kendo.template($("#status").html()),
                        title: "Төлөв",
                        width: "150px"
                    },
                    {
                        field: "istodotgol",
                        title: "Тодотгосон эсэх",
                        template: "# if(istodotgol == 0) { # Үгүй #} else {# Тийм #}#",
                        width: "150px"
                    },
                    {field: "repstepid", title: "Үе шат", values: $scope.repsteps, width: "150px"},
                    {field: "rejectstep", title: "Буцаасан шат", values: $scope.repsteps, width: "150px"},
                    {field: "submissiondate", title: "Илгээсэн огноо", width: "150px"},
                    {
                        template: kendo.template($("#main").html()),
                        width: ($scope.checkTransferRole() ? "230px" : "120px")
                    }],
                height: function () {
                    return $(window).height() - 130;
                },
                dataBound: function () {
                    var rows = this.items();
                    $(rows).each(function () {
                        var index = $(this).index() + 1
                            + ($(".k-grid").data("kendoGrid").dataSource.pageSize() * ($(".k-grid").data("kendoGrid").dataSource.page() - 1));
                        var rowLabel = $(this).find(".row-number");
                        $(rowLabel).html(index);
                    });
                },
                editable: "popup"
            };

        }
    ]);
