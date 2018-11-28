angular
    .module('altairApp')
    .controller("todotgolController",[
        '$scope',
        '$rootScope',
        '$state',
        'utils',
        'mainService',
        'sweet',
        'lic_type',
        'p_deposit',
        'rep_type',
        'mineralData',
        'user_data',
        function ($scope,$rootScope,$state,utils,mainService,sweet,lic_type,p_deposit,rep_type,mineralData, user_data) {

            $scope.mingroups = [];
            $scope.repsteps = [];
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

            if (user_data.divisionid == 1) {
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

            $scope.PlanExploration = {
                dataSource: {

                    transport: {
                        read:  {
                            url: "/user/angular/AnnualRegistrationColor",
                            data: {"custom":"where istodotgol=1 and divisionid = "+user_data.divisionid + " and xtype!=0"},
                            contentType:"application/json; charset=UTF-8",
                            type:"POST"
                        },
                        parameterMap: function(options) {
                            return JSON.stringify(options);
                        }
                    },
                    schema: {
                        data:"data",
                        total:"total",
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
                height: function () {
                    return $(window).height() - 130;
                },
                filterable: {
                    mode:"row"
                },
                sortable: {
                    mode: "multiple",
                    allowUnsort: true
                },
                scrollable: true,
                pageable: {
                    refresh:true,
                    buttonCount: 10
                },
                columns: [
                    {title: "#", template: "<span class='row-number'></span>", width: "50px"},
                    {field: "lpName", title: "Байгууллагын нэр", width: "150px"},
                    {field: "lpReg", title: "Байгууллагын РД", hidden: true, width: "150px"},
                    {field: "licenseXB", title: "Лицензийн дугаар", width: "150px"},
                    /*{field: "divisionid", title: "Хэлтэс", width: "150px", values: $scope.divisions},*/
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
                    {field: "reportyear", title: "Он", width: "150px"},
                    {field: "groupid", title: "АМ-ын ангилал", values: $scope.mingroups, width: "150px"},
                    {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "150px"},
                    {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "150px"},
                    {
                        field: "repstatusid",
                        values: [{text: "Хадгалсан", value: "0"}, {
                            text: "Засварт буцаасан",
                            value: "2"
                        }, {text: "Илгээсэн", value: "7"}, {text: "Хүлээлгэн өгсөн", value: "1"}],
                        template: kendo.template($("#status").html()),
                        title: "Төлөв",
                        width: "150px"
                    },
                    /*{field: "repstepid", title: "Үе шат", values: $scope.repsteps, width: "150px"},
                    {field: "rejectstep", title: "Буцаасан шат", values: $scope.repsteps, width: "150px"},*/

                    {field:"submissiondate", title: "Илгээсэн огноо", width: "150px"},
                    {field:"lastmodified", title: "Сүүлд хянасан огноо", width: "150px"},
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
                editable: "popup"
            };

        }
    ]);
