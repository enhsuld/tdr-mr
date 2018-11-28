angular
    .module('altairApp')
    .controller("annualCtrl", [
        '$compile',
        '$q',
        '$scope',
        '$http',
        '$timeout',
        '$rootScope',
        '$state',
        '$window',
        'utils',
        'mainService',
        'sweet',
        'lic_type',
        'app_status',
        'rep_type',
        'sel_data',
        'officers',
        'group_data',
        '$resource',
        'DTOptionsBuilder',
        'DTColumnBuilder',
        'mineralData',
        function ($compile, $q, $scope, $http, $timeout, $rootScope, $state, $window, utils, mainService, sweet, lic_type, app_status, rep_type, sel_data, officers, group_data, $resource, DTOptionsBuilder, DTColumnBuilder, mineralData) {

            $scope.appstat = app_status;
            var mgroup = [{text: "Үнэт, өнгөт металл", value: 10}, {
                text: "ЦИМ, ГХЭ, харь ховор металл",
                value: 11
            }, {
                text: "Металл бус, түгээмэл тархацтай ашигт малтмал",
                value: 12
            }, {text: "Нүүрс,  уламжлалт бус газрын тос", value: 13}];

            $scope.domain = "com.peace.users.model.mram.SubLicenses";
            $scope.rrid = 0;

            var xtype = [{text: "X-тайлан", value: 0}];
            var modal = UIkit.modal(".uk-modal");

            $scope.config = function (item) {
                if (item.mv == 0) {
                    $state.go('restricted.pages.planMVConfig', {id: item.id});
                }
                else {
                    $state.go('restricted.pages.planMVConfig', {id: item.id});
                    //$state.go('restricted.pages.planPVConfig',{id:item.id});
                }
            };

            $scope.chngDID = function (id) {
                $scope.divisionid = id;
            }

            $scope.conf = {
                assignee: {
                    config: {
                        create: false,
                        maxItems: 1,
                        valueField: 'id',
                        labelField: 'id',
                        placeholder: 'Үндсэн тусгай зөвшөөрөл...'
                    },
                    options: sel_data
                }
            }
            $scope.sublic = [];
            var sublic_data = $scope.selectize_sublic_options = sel_data;

            $scope.selectize_sublic_config = {
                plugins: {
                    'remove_button': {
                        label: ''
                    }
                },
                maxItems: null,
                minItems: 1,
                valueField: 'value',
                labelField: 'text',
                searchField: 'text',
                create: false,
                render: {
                    option: function (sublic_data, escape) {
                        return '<div class="option">' +
                            '<span class="title">' + escape(sublic_data.text) + '</span>' +
                            '</div>';
                    }
                }
            };

            $scope.chang = function () {
                $scope.landname = $scope.main.title;
                if ($scope.main.haiguulreport == true) {
                    $scope.divisionid = 1;
                }
                else {
                    $scope.divisionid = 0;
                }
                mainService.withdomain('get', '/user/service/resourse/data/ownlicenses/' + $scope.main.id)
                    .then(function (data) {
                        $scope.sublic = data;
                        sublic_data = $scope.selectize_sublic_options = $scope.sublic;

                        $scope.selectize_sublic_config = {
                            plugins: {
                                'remove_button': {
                                    label: ''
                                }
                            },
                            maxItems: null,
                            minItems: 1,
                            valueField: 'value',
                            labelField: 'text',
                            searchField: 'text',
                            create: false,
                            render: {
                                option: function (sublic_data, escape) {
                                    return '<div class="option">' +
                                        '<span class="title">' + escape(sublic_data.text) + '</span>' +
                                        '</div>';
                                }
                            }
                        };
                    });
            }
            var modalConfigUpdate = UIkit.modal("#new_issue_config");
            $scope.initConfig = function () {

                modalConfigUpdate.show();
                /* mainService.withdomain('get','/user/service/resourse/ownlicenses')
                .then(function(data){
                    console.log(data);
                    $scope.newIssue.assignee.options=data;
                    $scope.selectize_planets_options =data;

                    $scope.additional = [];
                });	*/
            }

            $scope.loadOnPlan = function (item, y, i) {
                var d = new Date();
                var n = d.getFullYear();

                swal({
                        title: "Тусгай зөвшөөрлийн дугаар: " + item.bundledLicenseNum,
                        text: "Та энэ тусгай зөвшөөрлийн " + n + " жилийн тайлан үүсгэх гэж байна. Үргэлжлүүлэх үү?",
                        type: "info",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        showLoaderOnConfirm: true,
                        confirmButtonText: 'Тийм',
                        cancelButtonText: 'Үгүй',
                    },
                    function () {
                        mainService.withdomain('put', '/logic/createPlan/' + y + '/' + i)
                            .then(function (data) {
                                if (data.subdate == 'true') {
                                    sweet.show('Анхаар!', 'Төлөвлөгөө амжилттай үүслээ.', 'success');
                                    if (data.lic == 1) {
                                        $state.go('restricted.pages.planFormH', {param: data.id});
                                    }
                                    else {
                                        $state.go('restricted.pages.planFormA', {param: data.id});
                                    }
                                }
                                else {
                                    sweet.show('Анхаар!', 'Төлөвлөгөө үүссэн байна', 'error');
                                }

                            });

                    });

            };

            $scope.loadOnReport = function (item, y, i) {
                var d = new Date();
                var n = d.getFullYear();

                swal({
                        title: "Тусгай зөвшөөрлийн дугаар: " + item.bundledLicenseNum,
                        text: "Та энэ тусгай зөвшөөрлийн " + n + " жилийн тайлан үүсгэх гэж байна. Үргэлжлүүлэх үү?",
                        type: "info",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        showLoaderOnConfirm: true,
                        confirmButtonText: 'Тийм',
                        cancelButtonText: 'Үгүй',
                    },
                    function () {
                        mainService.withdomain('put', '/logic/createPlan/' + y + '/' + i)
                            .then(function (data) {
                                if (data.subdate == 'true') {
                                    sweet.show('Анхаар!', 'Тайлан амжилттай үүслээ.', 'success');
                                    if (data.lic == 1) {
                                        $state.go('restricted.pages.reportFormH', {param: data.id});
                                    }
                                    else {
                                        $state.go('restricted.pages.reportFormA', {param: data.id});
                                    }
                                }
                                else {
                                    sweet.show('Анхаар!', 'Тайлан үүссэн байна', 'error');
                                }

                            });

                    });

            };

            $scope.loadOnSend = function (i) {
                sweet.show({
                    title: 'Мэдээлэл',
                    text: 'Та энэ тайлан / төлөвлөгөө илгээхдээ итгэлтэй байна уу?',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#DD6B55',
                    confirmButtonText: 'Тийм',
                    cancelButtonText: 'Үгүй',
                    closeOnConfirm: false,
                    closeOnCancel: false,
                    showLoaderOnConfirm: true
                }, function (inputvalue) {
                    if (inputvalue) {
                        mainService.withdomain('put', '/logic/sendform/1/' + i)
                            .then(function () {
                                $(".conf .k-grid").data("kendoGrid").dataSource.read();
                                sweet.show('Анхаар!', 'Амжилттай илгээлээ.', 'success');
                            });
                    } else {
                        sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
                    }

                });
            };

            $scope.loadOnUpdate = function (item) {
                if (item.reporttype == 3) {
                    if (item.lictype == 1) {
                        $state.go('restricted.pages.planFormH', {param: item.id});
                    }
                    else {
                        $state.go('restricted.pages.planFormA', {param: item.id});
                    }
                }
                else {
                    if (item.lictype == 1) {
                        $state.go('restricted.pages.reportFormH', {param: item.id});
                    }
                    else {
                        $state.go('restricted.pages.reportFormA', {param: item.id});
                    }
                }
            };

            $scope.selectOptions = {
                placeholder: "Сонголт...",
                cascadeFrom: "categories",
                dataTextField: "id",
                dataValueField: "id",
                valuePrimitive: true,
                autoBind: false,
                dataSource: sel_data
            };

            $scope.license = {
                dataSource: {
                    pageSize: 4,
                    serverPaging: true,
                    serverSorting: true,
                    serverFiltering: true,
                    transport: {
                        read: {
                            url: "/user/angular/SubLicensesPlanCom",
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
                    {field: "licenseXB", title: "<span data-translate='License number'></span>"},
                    {field: "areaNameMon", title: "<span data-translate='Area name'></span>"},
                    /*  { field:"lpReg", title: "Эзэмшигч нэр"},	*/
                    {field: "areaSize", title: "<span data-translate='Area size (ha)'></span>"},
                    {field: "locationAimag", title: "<span data-translate='Aimag'></span>"},
                    {field: "locationSoum", title: "<span data-translate='Soum'></span>"},
                    /*       {
                                 template: "<div class='k-edit-custom' ng-click='plan(#:id#)'   id='#:id#'" +
                                  "><button class='md-btn'>Төлөвлөгөө</button>",
                                  width: "160px"

                           }*/
                ],
                editable: "popup"
            };
            $scope.domain = "com.peace.users.model.mram.RegReportReq.";
            $scope.REPBundle = {

                dataSource: {

                    transport: {
                        read: {
                            url: "/user/angular/RegReportReq",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST"
                        },
                        update: {
                            url: "/user/service/editing/update/" + $scope.domain + "",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST",
                            complete: function (e) {
                                //	$(".k-grid").data("kendoGrid").dataSource.read();
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
                            id: "id",
                            fields: {
                                id: {editable: false, nullable: true},
                                bundledLicenseNum: {type: "string", editable: false},
                                addBunLicenseNum: {type: "string", editable: false},
                                licenseXB: {type: "string"},
                                areaName: {type: "string", editable: false},
                                latestChangeDateTime: {type: "string", editable: false},
                                groupid: {type: "number", defaultValue: 1},
                                mineralid: {type: "string", editable: false},
                                isconfiged: {type: "number"}
                            }
                        }
                    },
                    pageSize: 4,
                    serverPaging: true,
                    serverSorting: true
                },
                filterable: {
                    mode: "row"
                },
                sortable: true,
                resizable: true,
                pageable: {
                    refresh: true,
                    buttonCount: 3
                },
                columns: [
                    {field: "licenseXB", title: "<span data-translate='Primary license'></span>"},
                    /*  { field:"addBunLicenseNum", title: "<span data-translate='Secondary license'></span>"},*/
                    {field: "areaName", title: "<span data-translate='Area name'></span>"},
                    {field: "latestChangeDateTime", title: "<span data-translate='Configured date'></span>"},
                    {field: "groupid", title: "<span data-translate='Mineral group'></span>", values: mgroup},
                    /*          { field:"mineralid", title: "<span data-translate='Mineral'></span>", values:mineralData},*/
                    {
                        template: kendo.template($("#isconfiged").html()),
                        title: "<span data-translate='Тохиргоо хийсэн эсэх'></span>"
                    },
                    {
                        template: kendo.template($("#action").html()), editable: false, width: "110px"
                    }],
                editable: "popup"
            };

            $scope.submitForm = function () {
                var data = [];
                var land = "";
                if ($scope.additional == undefined) {
                    //data.push($scope.main);
                }
                else {
                    data = $scope.additional;
//	            		 data.push($scope.main);
                }

                if ($scope.landname == undefined) {
                    land = "";
                }
                else {
                    land = $scope.landname;
                }
                obj = {
                    id: $scope.rrid,
                    main: $scope.main.id,
                    additional: data,
                    landname: land,
                    wk: 0,
                    isRedemption: $scope.isRedemtion,
                    priority: $scope.priority,
                    divisionid: ($scope.divisionid != null && $scope.divisionid != undefined) ? $scope.divisionid : 0
                };

                jQuery.ajax({
                    url: "/user/service/RegReportReq",
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify(obj),

                    complete: function (r) {
                        var data = r.responseText.trim();
                        if (data == 'true') {
                            modalConfigUpdate.hide();
                            $scope.main = {};
                            $scope.additional = {};
                            $scope.isRedemtion = {};
                            $scope.landname = '';
                            $scope.priority = {};
                            $(".rep .k-grid").data("kendoGrid").dataSource.read();
                            $(".lic .k-grid").data("kendoGrid").dataSource.read();
                            //$state.reload();
                        }
                    }
                });
            };

            $scope.loadOnConfirm = function (i) {
                sweet.show({
                    title: 'Баталгаажуулалт',
                    text: 'Энэ тохируулгыг устгах уу?',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#DD6B55',
                    confirmButtonText: 'Тийм!',
                    cancelButtonText: 'Үгүй!',
                    closeOnConfirm: false,
                    closeOnCancel: false,
                    showLoaderOnConfirm: true
                }, function (inputvalue) {
                    if (inputvalue) {
                        mainService.withdomain('delete', '/logic/removeBundle/' + i)
                            .then(function (data) {
                                /*$("#rep .k-grid").data("kendoGrid").dataSource.read();
                                $("#lic .k-grid").data("kendoGrid").dataSource.read();*/
                                if (data.success == "success") {
                                    sweet.show('Анхаар', 'Амжилттай устлаа...', 'success');
                                    $state.reload();
                                } else {
                                    sweet.show('Анхаар', 'Тайлан/төлөвлөгөө үүссэн байна..', 'error');
                                }

                            });
                    } else {
                        sweet.show('Анхаар', 'Үйлдэл цуцлагдлаа...', 'error');
                    }

                });
            };
        }
    ]);
