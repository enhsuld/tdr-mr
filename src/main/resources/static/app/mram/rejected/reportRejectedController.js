angular
    .module('altairApp')
    .controller("xreportRejectedCoal", [
        '$scope',
        '$rootScope',
        '$state',
        'utils',
        'mainService',
        'sweet',
        'mineralData',
        '$filter',
        'user_data',
        'p_deposit',
        function ($scope, $rootScope, $state, utils, mainService, sweet, mineralData, $filter, user_data, p_deposit) {

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
                        $state.go('restricted.pages.GovReportFormH', {param: item.id, id: item.repstepid});
                    }
                    else {
                        $state.go('restricted.pages.GovReportFormA', {param: item.id, id: item.repstepid});
                    }
                }

            };

            $scope.downloadFile = function (type) {
                UIkit.modal.prompt('Тайлан татаж авах оныг оруулна уу:', '', function (val) {
                    if (!isNaN(val)) {
                        $("#downloadJS").attr("href", "/logic/exportAnnualRegistration/" + type + "/" + val).attr("download", "");
                        document.getElementById("downloadJS").click()
                    }
                    else {
                        UIkit.modal.alert('Зөвхөн тоон утга оруулна уу!');
                    }
                });
            }

            $scope.user_data = user_data;
            $scope.loadOnZero = function (item) {
                $state.go('restricted.pages.zeroAnswerGov', {
                    param: item.id,
                    reqid: item.reqid,
                    rtype: item.reporttype
                });
            };

            var xtype = [{text: "X - тайлан", value: 0}];

            $scope.PlanExploration = {
                dataSource: {
                    transport: {
                        read: {
                            url: "/user/angular/AnnualRegistrationXreportHistory",
                            data: {
                                "custom": "where divisionid=" + user_data.divisionid + " and repstatusid=3",
                                sort: [{"field": "submissiondate", "dir": "asc"}]
                            },
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
                toolbar: ["excel"],
                excel: {
                    allPages: true,
                    fileName: "Export.xlsx",
                    filterable: true
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
                    buttonCount: 10
                },
                columns: [
                    {title: "#", template: "<span class='row-number'></span>", width: "50px"},
                    {field: "lpName", title: "<span data-translate='Company name'></span>"},
                    {field: "depositid", title: "АМ-ын төрөл", values: p_deposit},
                    {
                        field: "reporttype",
                        values: [{text: "Тайлан", value: "4"}, {text: "Төлөвлөгөө", value: "3"}],
                        title: "Төрөл"
                    },
                    {field: "licenseXB", title: "<span data-translate='License number'></span>"},
                    {field: "reportyear", title: "<span data-translate='Report year'></span>"},
                    {
                        field: "repstatusid",
                        values: [{text: "Хадгалсан", value: "0"}, {
                            text: "Засварт буцаасан",
                            value: "2"
                        }, {text: "Татгалзсан", value: "3"}, {text: "Илгээсэн", value: "7"}, {
                            text: "Хүлээлгэн өгсөн",
                            value: "1"
                        }],
                        template: kendo.template($("#status").html()),
                        title: "<span data-translate='Status'></span>"
                    },
                    {field: "submissiondate", title: "<span data-translate='Submitted date'></span>"},
                    {field: "lastmodified", title: "Сүүлд хянасан огноо"},
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

            $scope.finddeposit = function (id) {
                console.log(id);
                if ($filter('filter')(mineralData, {value: id}).length > 0) {
                    return $filter('filter')(mineralData, {value: id})[0].text;
                }

            }
        }
    ]);
