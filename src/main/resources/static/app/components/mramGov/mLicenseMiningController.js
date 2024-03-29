angular
    .module('altairApp')
    .controller("mLicenseGOVCtrl", ['$scope', '$rootScope', 'utils', 'p_org', 'mainService', 'sweet', 'user_data', 'role_data',
        function ($scope, $rootScope, utils, p_org, mainService, sweet, user_data, role_data) {

            $scope.domain = "com.peace.users.model.mram.SubLicenses";

            $scope.configuration = user_data.config;


            $scope.conf = {
                licenseNum: 0,
                monthly: false,
                plan: false,
                redemptionplan: false,
                redemptionreport: false,
                report: false,
                weekly: false,
            }

            var $wizard_advanced_form = $('#wizard_advanced_form');

            $scope.res = function () {
                $scope.conf.monthly = false;
                $scope.conf.plan = false;
                $scope.conf.redemptionplan = false;
                $scope.conf.report = false;
                $scope.conf.weekly = false;
            }
            $scope.config = function (vdata) {
                $scope.conf.licenseNum = vdata.licenseNum;
                if (vdata.monthly) {
                    $scope.conf.monthly = true;
                }
                else {
                    $scope.conf.monthly = false;
                }
                if (vdata.plan) {
                    $scope.conf.plan = true;
                }
                else {
                    $scope.conf.plan = false;
                }
                if (vdata.redemptionplan) {
                    $scope.conf.redemptionplan = true;
                }
                else {
                    $scope.conf.redemptionplan = false;
                }
                if (vdata.redemptionreport) {
                    $scope.conf.redemptionreport = true;
                }
                else {
                    $scope.conf.redemptionreport = false;
                }
                if (vdata.report) {
                    $scope.conf.report = true;
                }
                else {
                    $scope.conf.report = false;
                }
                if (vdata.weekly) {
                    $scope.conf.weekly = true;
                }
                else {
                    $scope.conf.weekly = false;
                }
                if (vdata.haiguulreport) {
                    $scope.conf.haiguulreport = true;
                }
                else {
                    $scope.conf.haiguulreport = false;
                }
                if (vdata.ftime) {
                    $scope.conf.ftime = true;
                }
                else {
                    $scope.conf.ftime = false;
                }
                if (vdata.lplan) {
                    $scope.conf.lplan = true;
                }
                else {
                    $scope.conf.lplan = false;
                }
                if (vdata.lreport) {
                    $scope.conf.lreport = true;
                }
                else {
                    $scope.conf.lreport = false;
                }
            }
            var modalc = UIkit.modal("#modal_config_mlic");


            $scope.submitForm = function () {
                mainService.withdata('post', '/user/service/form/config/m', $scope.conf)
                    .then(function (data) {
                        $(".confcc .k-grid").data("kendoGrid").dataSource.read();
                        modalc.hide();

                    });
            }

            if (role_data[0].read == 1) {
                if (role_data[0].update == 1) {
                    $scope.configuration = true;
                }

                $scope.license = {
                    dataSource: {
                        pageSize: 15,
                        serverPaging: true,
                        serverSorting: true,
                        serverFiltering: true,
                        autoSync: true,
                        transport: {
                            read: {
                                url: "/user/angular/SubLicensesHeadMount",
                                contentType: "application/json; charset=UTF-8",
                                type: "POST"
                            },
                            update: {
                                url: "/user/service/editing/update/" + $scope.domain + "",
                                contentType: "application/json; charset=UTF-8",
                                type: "POST"
                            },
                            destroy: {
                                url: "/user/service/editing/delete/" + $scope.domain + "",
                                contentType: "application/json; charset=UTF-8",
                                type: "POST"
                            },
                            create: {
                                url: "/user/service/editing/create/" + $scope.domain + "",
                                contentType: "application/json; charset=UTF-8",
                                type: "POST",
                                complete: function (e) {
                                    $(".k-grid").data("kendoGrid").dataSource.read();
                                }
                            },
                            parameterMap: function (options) {
                                return JSON.stringify(options);
                            }
                        },
                        schema: {
                            data: "data",
                            total: "total",
                            model: {
                                id: "id"
                            }
                        }

                    },
                    toolbar: ["excel"],
                    excel: {
                        allPages: true,
                        fileName: "Export.xlsx",
                        filterable: true
                    },
                    height: function () {
                        return $(window).height() - 175;
                    },
                    filterable: {
                        mode: "row"
                    },
                    sortable: {
                        mode: "multiple",
                        allowUnsort: true
                    },
                    pageable: {
                        refresh: true,
                        pageSizes: true,
                        buttonCount: 5
                    },
                    columns: [
                        {title: "#", template: "<span class='row-number'></span>", width: "50px"},
                        {field: "licenseXB", title: "<span data-translate='License number'></span>"},
                        {field: "licenseNum", title: "<span data-translate='License number'></span>"},
                        {field: "areaNameMon", title: "<span data-translate='Area name'></span>"},
                        {field: "lpName", title: "<span data-translate='Holder name'></span>"},
                        {field: "areaSize", title: "<span data-translate='Area size (ha)'></span>"},
                        {field: "locationAimag", title: "<span data-translate='Aimag'></span>"},
                        {field: "locationSoum", title: "<span data-translate='Soum'></span>"},
                        {field: "configureddate", title: "Тохируулсан огноо"},
                        {
                            template: kendo.template($("#action").html()), width: "70px"
                        }
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


        }
    ]);
