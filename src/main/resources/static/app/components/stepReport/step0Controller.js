angular
    .module('altairApp')
    .controller("annualReportStep0CtrlGov", [
        '$scope',
        '$rootScope',
        '$state',
        'utils',
        'mainService',
        'sweet',
        'lic_type',
        'p_deposit',
        function ($scope, $rootScope, $state, utils, mainService, sweet, lic_type, p_deposit) {

            $scope.loadOnZero = function (item) {
                $state.go('restricted.pages.zeroAnswerGov', {
                    param: item.id,
                    reqid: item.reqid,
                    rtype: item.reporttype
                });
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
            };

            $scope.PlanExploration = {
                dataSource: {

                    transport: {
                        read: {
                            url: "/user/step/1/3/AnnualRegistrationXplan",
                            data: {"custom": "where reporttype=3"},
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
                toolbar: kendo.template($("#export").html()),
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
                    {field: "lpName", title: "Аж ахуйн нэгжийн нэр"},
                    {field: "depositid", title: "АМ-ын төрөл", values: p_deposit},
                    {field: "licenseXB", title: "Тусгай зөвшөөрлийн дугаар"},
                    {field: "reportyear", title: "Он"},

                    {field: "submissiondate", title: "Илгээсэн огноо"},
                    {
                        template: kendo.template($("#status").html()), title: "<span data-translate='Status'></span>"
                    },
                  /*  {field: "approveddate", title: "Хүлээн авсан огноо"},*/
                    {
                        template: kendo.template($("#main").html()), width: "90px"
                    }
                ],
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
