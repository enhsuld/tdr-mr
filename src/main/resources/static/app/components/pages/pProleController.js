angular
    .module('altairApp')
    .controller("roleCtrl", ['$scope', 'mainService', '$state', 'donelist',
        function ($scope, mainService, $state, donelist) {
            var original;


            //console.log(sections.mjson);
            console.log(donelist.options);

            $scope.sections = donelist.options;

            var $ts_pager_filter = $("#ts_pager_filter"),
                $ts_align = $('#ts_align');

            // select/unselect table rows
            $('.ts_checkbox_all')
                .iCheck({
                    checkboxClass: 'icheckbox_md',
                    radioClass: 'iradio_md',
                    increaseArea: '20%'
                })
                .on('ifChecked', function () {
                    $ts_pager_filter
                        .find('.ts_checkbox')
                        // check all checkboxes in table
                        .prop('checked', true)
                        .iCheck('update')
                        // add highlight to row
                        .closest('tr')
                        .addClass('row_highlighted');
                })
                .on('ifUnchecked', function () {
                    $ts_pager_filter
                        .find('.ts_checkbox')
                        // uncheck all checkboxes in table
                        .prop('checked', false)
                        .iCheck('update')
                        // remove highlight from row
                        .closest('tr')
                        .removeClass('row_highlighted');
                });

            $('.ts_read_all')
                .iCheck({
                    checkboxClass: 'icheckbox_md',
                    radioClass: 'iradio_md',
                    increaseArea: '20%'
                })
                .on('ifChecked', function () {
                    $ts_pager_filter
                        .find('.ts_read_checkbox')
                        // check all checkboxes in table
                        .prop('checked', true)
                        .iCheck('update')
                        // add highlight to row
                        .closest('tr')
                        .addClass('row_highlighted');
                })
                .on('ifUnchecked', function () {
                    $ts_pager_filter
                        .find('.ts_read_checkbox')
                        // uncheck all checkboxes in table
                        .prop('checked', false)
                        .iCheck('update')
                        // remove highlight from row
                        .closest('tr')
                        .removeClass('row_highlighted');
                });

            $('.ts_update_all')
                .iCheck({
                    checkboxClass: 'icheckbox_md',
                    radioClass: 'iradio_md',
                    increaseArea: '20%'
                })
                .on('ifChecked', function () {
                    $ts_pager_filter
                        .find('.ts_update_checkbox')
                        // check all checkboxes in table
                        .prop('checked', true)
                        .iCheck('update')
                        // add highlight to row
                        .closest('tr')
                        .addClass('row_highlighted');
                })
                .on('ifUnchecked', function () {
                    $ts_pager_filter
                        .find('.ts_update_checkbox')
                        // uncheck all checkboxes in table
                        .prop('checked', false)
                        .iCheck('update')
                        // remove highlight from row
                        .closest('tr')
                        .removeClass('row_highlighted');
                });

            $('.ts_delete_all')
                .iCheck({
                    checkboxClass: 'icheckbox_md',
                    radioClass: 'iradio_md',
                    increaseArea: '20%'
                })
                .on('ifChecked', function () {
                    $ts_pager_filter
                        .find('.ts_delete_checkbox')
                        // check all checkboxes in table
                        .prop('checked', true)
                        .iCheck('update')
                        // add highlight to row
                        .closest('tr')
                        .addClass('row_highlighted');
                })
                .on('ifUnchecked', function () {
                    $ts_pager_filter
                        .find('.ts_delete_checkbox')
                        // uncheck all checkboxes in table
                        .prop('checked', false)
                        .iCheck('update')
                        // remove highlight from row
                        .closest('tr')
                        .removeClass('row_highlighted');
                });

            $('.ts_export_all')
                .iCheck({
                    checkboxClass: 'icheckbox_md',
                    radioClass: 'iradio_md',
                    increaseArea: '20%'
                })
                .on('ifChecked', function () {
                    $ts_pager_filter
                        .find('.ts_export_checkbox')
                        // check all checkboxes in table
                        .prop('checked', true)
                        .iCheck('update')
                        // add highlight to row
                        .closest('tr')
                        .addClass('row_highlighted');
                })
                .on('ifUnchecked', function () {
                    $ts_pager_filter
                        .find('.ts_export_checkbox')
                        // uncheck all checkboxes in table
                        .prop('checked', false)
                        .iCheck('update')
                        // remove highlight from row
                        .closest('tr')
                        .removeClass('row_highlighted');
                });

            $scope.selectize_a_config = {
                create: false,
                maxItems: 1,
                placeholder: 'Select...',
                optgroupField: 'parent_id',
                optgroupLabelField: 'title',
                optgroupValueField: 'ogid',
                valueField: 'value',
                labelField: 'title',
                searchField: 'title'
            };

            var planets_data = $scope.selectize_planets_options = [
                {id: 1, title: 'Харах', url: ''},
                {id: 2, title: 'Нэмэх', url: ''},
                {id: 3, title: 'Засах', url: ''},
                {id: 4, title: 'Устгах', url: ''},
                {id: 5, title: 'Хэвлэх', url: ''}
            ];

            $scope.selectize_planets_config = {
                plugins: {
                    'remove_button': {
                        label: ''
                    }
                },
                maxItems: null,
                valueField: 'id',
                labelField: 'title',
                searchField: 'title',
                create: false,
                render: {
                    option: function (planets_data, escape) {
                        return '<div class="option">' +
                            '<span class="title">' + escape(planets_data.title) + '</span>' +
                            '</div>';
                    },
                    item: function (planets_data, escape) {
                        return '<div class="item"><a href="' + escape(planets_data.url) + '" target="_blank">' + escape(planets_data.title) + '</a></div>';
                    }
                }
            };


            $scope.domain = "com.peace.users.model.mram.LutRole";
            $scope.proleGrid = {
                dataSource: {

                    transport: {
                        read: {
                            url: "/user/angular/LutRole",
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
                            id: "id",
                            fields: {
                                id: {editable: false, nullable: true, defaultValue: 0},
                                roleNameMon: {type: "roleNameMon", validation: {required: true}},
                                roleNameEng: {type: "roleNameEng", validation: {required: true}}
                            }
                        }
                    },
                    pageSize: 15,
                    serverPaging: true,
                    serverSorting: true
                },
                toolbar: kendo.template($("#add").html()),
                filterable: true,
                sortable: true,
                columnMenu: true,
                pageable: {
                    refresh: true,
                    pageSizes: true,
                    buttonCount: 5
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
                    {field: "roleNameMon", title: "Эрх (mn)"},
                    {field: "roleNameEng", title: "Эрх (en)"},
                    {
                        template: kendo.template($("#update").html()), width: "130px"

                    },
                    {
                        template: "<div class='k-edit-custom' ng-click='delMe(#:id#)'   id='#:id#'" +
                            "><button class='md-btn'>Устгах</button>",
                        width: "130px"

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

            $scope.selectize_a_data = {
                options: donelist.options
            };
            //  $scope.selectize_a_data.options = donelist[0];

            $scope.roleid = 0;

            $scope.row_create = {
                menuid: 0,
                ids: []
            }
            $scope.row_read = {
                menuid: 0,
                ids: []
            }
            $scope.row_update = {
                menuid: 0,
                ids: []
            }
            $scope.row_delete = {
                menuid: 0,
                ids: []
            }
            $scope.row_export = {
                menuid: 0,
                ids: []
            }

            $scope.res = function () {
                $scope.roleid = 0,
                    $scope.role.definition = "",
                    $scope.role.name = "",
                    $scope.role.selectize_a = [],
                    $scope.row_create.ids = [];
                $scope.row_read.ids = [];
                $scope.row_update.ids = [];
                $scope.row_delete.ids = [];
                $scope.row_export.ids = [];
                $scope.createAll = false;
                $scope.readAll = false;
                $scope.updateAll = false;
                $scope.deleteAll = false;
                $scope.exportAll = false;
            }

            $scope.role = {
                "definition": " ",
                "name": " ",
                selectize_a: []
            };

            $scope.update = function (vdata) {
                $scope.res();
                $scope.roleid = vdata.id;

                $scope.role = {
                    "definition": vdata.roleNameEng,
                    "name": vdata.roleNameMon,
                    selectize_a: vdata.access
                };


                mainService.getDetail('/user/service/read/' + $scope.domain + '/' + vdata.id).then(function (data) {
                    $scope.data = data;
                    angular.forEach($scope.data, function (value, key) {
                        if (value.create == 1) {
                            $scope.row_create.ids[value.menuid] = true;
                        }
                        if (value.read == 1) {
                            $scope.row_read.ids[value.menuid] = true;
                        }
                        if (value.update == 1) {
                            $scope.row_update.ids[value.menuid] = true;
                        }
                        if (value.delete == 1) {
                            $scope.row_delete.ids[value.menuid] = true;
                        }
                        if (value.export == 1) {
                            $scope.row_export.ids[value.menuid] = true;
                        }
                    });
                });

            }

            $scope.delMe = function (i) {
                mainService.withdomain('delete', '/user/service/general/delete/' + $scope.domain + '/' + i)
                    .then(function () {
                        $(".k-grid").data("kendoGrid").dataSource.read();
                    });
            }

            var vm = this;
            vm.selected = ['sdssc'];

            $scope.submitForm = function () {

                var phrases = [];

                var ss = [];
                var menuid;
                var ids = [];
                var role = {
                    "menuid": menuid,
                    "ids": ids
                }


                $('.uk-table-align-vertical').each(function () {
                    $(this).find('tbody tr').each(function () {
                        var current = $(this);

                        var foo = [];


                        if ($(this).find('td').find('.ts_checkbox:checked').is(":checked")) {
                            foo.push(1);
                        }
                        if ($(this).find('td').find('.ts_read_checkbox:checked').is(":checked")) {
                            foo.push(2);
                        }
                        if ($(this).find('td').find('.ts_update_checkbox:checked').is(":checked")) {
                            foo.push(3);
                        }
                        if ($(this).find('td').find('.ts_delete_checkbox:checked').is(":checked")) {
                            foo.push(4);
                        }
                        if ($(this).find('td').find('.ts_export_checkbox:checked').is(":checked")) {
                            foo.push(5);
                        }

                        if (foo.length > 0) {
                            role = {
                                "menuid": $(this).find('td').find('.menu').val(),
                                "ids": foo
                            }
                            ss.push(role);
                        }

                    });
                });


                var data = [];
                var rrr = $scope.roleid;
                if (ss.length > 0) {
                    var obj = {
                        roleid: rrr,
                        rolename: $scope.role.name,
                        definition: $scope.role.definition,
                        access: $scope.role.selectize_a,
                        ilist: ss
                    };
                }


                data.push(obj);

                jQuery.ajax({
                    url: "/user/service/rolesubmit",
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify(data),

                    complete: function (r) {
                        var data = r.responseText.trim();
                        if (data == 'true') {
                            $('#closemodal').trigger('click');
                            $(".k-grid").data("kendoGrid").dataSource.read();
                            $scope.rolename = null;
                            $scope.definition = null;
                            $scope.roleid = 0;
                            $scope.res();
                        }
                        else {
                            //window.location.href = 'http://localhost:8080/user/dd/'+data+'';
                        }
                    }
                });


            }
        }
    ]);
