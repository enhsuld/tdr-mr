angular
    .module('altairApp')
    .controller("historylistplan", [
        '$scope',
        '$rootScope',
        '$state',
        'utils',
        'mainService',
        'sweet',
        function ($scope, $rootScope, $state, utils, mainService, sweet) {

            $scope.loadOnCheck = function (item) {
                if (item.reporttype === 3) {
                    if (item.lictype === 1) {
                        $state.go('restricted.pages.GovPlanFormH', {param: item.id});
                    }
                    else {
                        $state.go('restricted.pages.GovPlanFormA', {param: item.id, id: item.repstepid});
                    }
                }
                else {
                    if (item.lictype === 1) {
                        $state.go('restricted.pages.GovReportFormH', {param: item.id});
                    }
                    else {
                        $state.go('restricted.pages.GovReportFormA', {param: item.id});
                    }
                }

            };

            $scope.loadOnZero = function (item) {
                $state.go('restricted.pages.zeroAnswer', {id: item.id});
            };

            var xtype = [{text: "X - тайлан", value: 0}];

            $scope.PlanExploration = {
                dataSource: {

                    transport: {
                        read: {
                            url: "/user/step/1/3/history",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST"
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
                    },
                    pageSize: 15,
                    serverPaging: true,
                    serverFiltering: true,
                    serverSorting: true
                },
                filterable: {
                    mode: "row"
                },
                sortable: {
                    mode: "multiple",
                    allowUnsort: true
                },
                scrollable: true,
                pageable: {
                    refresh: true,
                    buttonCount: 3
                },
                columns: [
                    {
                        title: "#",
                        headerAttributes: {
                            "class": "columnHeader",
                            style: "white-space: normal;vertical-align:middle;"
                        },
                        template: "<span class='row-number' style='float:left;width:100%; text-align:center'></span>",
                        width: 50
                    },
                    {field: "lpName", title: "<span data-translate='Company name'></span>"},
                    {field: "licensenum", title: "<span data-translate='License number'></span>"},
                    {field: "licenseXB", title: "<span data-translate='License number'></span>"},
                    {field: "regReportReq.addBunLicenseNum",template:"#if(regReportReq!=null){# #=regReportReq.addBunLicenseNum# #}#", title: "Нэмэлт ТЗ"},
                    {field: "reportyear", title: "<span data-translate='Report year'></span>"},
                    {
                        template: kendo.template($("#status").html()), title: "<span data-translate='Status'></span>"
                    },
                    {field: "submissiondate", title: "<span data-translate='Submitted date'></span>"},
                    {field: "approveddate", title: "<span data-translate='Received date'></span>"},
                    {
                        template: kendo.template($("#main").html()), width: "90px"
                    }],
                dataBound: function () {
                    var rows = this.items();
                    $(rows).each(function () {
                        var index = $(this).index() + 1
                            + ($(".k-grid").data("kendoGrid").dataSource.pageSize() * ($(".k-grid").data("kendoGrid").dataSource.page() - 1));
                        var rowLabel = $(this).find(".row-number");
                        $(rowLabel).html(index);
                    });
                },
                height: function () {
                    return $(window).height() - 130;
                },
                editable: "popup"
            };

        }
    ]);
