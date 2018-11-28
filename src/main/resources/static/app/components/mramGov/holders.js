angular
    .module('altairApp')
    .controller("holderCtrl", [
        '$scope',
        '$rootScope',
        '$state',
        'lptypes',
        'role_data',
        function ($scope, $rootScope, $state, lptypes, role_data) {
            $scope.domain = "com.peace.users.model.mram.SubLegalpersons.";


            $scope.pmenuGrid = {
                dataSource: {

                    transport: {
                        read: {
                            url: "/user/angular/SubLegalpersons",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST"
                        },
                        update: {
                            url: "/user/service/editing/update/" + $scope.domain + "",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST",
                            complete: function (e) {
                                $("#notificationUpdate").trigger('click');
                            }
                        },
                        destroy: {
                            url: "/user/service/editing/delete/" + $scope.domain + "",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST",
                            complete: function (e) {
                                $("#notificationDestroy").trigger('click');
                            }
                        },
                        create: {
                            url: "/user/service/editing/create/" + $scope.domain + "",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST",
                            complete: function (e) {
                                $("#notificationSuccess").trigger('click');
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
                resizable: true,
                toolbar: ["excel"],
                excel: {
                    allPages: true,
                    fileName: "Export.xlsx",
                    filterable: true
                },
                height: function () {
                    return $(window).height() - 175;
                },
                reorderable: true,
                pageable: {
                    refresh: true,
                    pageSizes: true,
                    buttonCount: 5
                },
                columns: [
                    {title: "#", template: "<span class='row-number'></span>", width: "60px"},
                    {field: "lpReg", title: "<span data-translate='Reg.number'></span>", width: 150},
                    {field: "lpName", title: "<span data-translate='Company name'></span>", width: 250},
                    {field: "givName", title: "<span data-translate='Firstname /Mon/'></span>", width: 150},
                    {field: "phone", title: "<span data-translate='Phone'></span>", width: 150},
                    {field: "street", title: "<span data-translate='Хаяг'></span>", width: 350},
                    {field: "GENGINEER", title: "<span data-translate='Ерөнхий инженерийн нэр'></span>", width: 350},
                    {field: "GENGINEERPHONE", title: "<span data-translate='Утас'></span>", width: 150},
                    {field: "GENGINEERMAIL", title: "<span data-translate='И-мэйл'></span>", width: 150},
                    {field: "minehead", title: "<span data-translate='Уурхайн даргын нэр'></span>", width: 350},
                    {field: "minephone", title: "<span data-translate='Утас'></span>", width: 150},
                    {field: "mineemail", title: "<span data-translate='И-мэйл'></span>", width: 150},
                    {field: "ACCOUNTANT", title: "<span data-translate='Нягтлан бодогчийн нэр'></span>", width: 350},
                    {field: "ACCOUNTANTPHONE", title: "<span data-translate='Утас'></span>", width: 150},
                    {field: "ACCOUNTANTEMAIL", title: "<span data-translate='И-мэйл'></span>", width: 150},
                    {field: "ECONOMIST", title: "<span data-translate='Эдийн засагчийн нэр'></span>", width: 350},
                    {field: "ECONOMISTPHONE", title: "<span data-translate='Утас'></span>", width: 150},
                    {field: "ECONOMISTEMAIL", title: "<span data-translate='И-мэйл'></span>", width: 150},
                    {field: "KEYMAN", title: "<span data-translate='Тайлан хариуцсан хүний нэр'></span>", width: 350},
                    {field: "KEYMANPHONE", title: "<span data-translate='Утас'></span>", width: 150},
                    {field: "KEYMANEMAIL", title: "<span data-translate='И-мэйл'></span>", width: 150}
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
            }
        }]
    )
