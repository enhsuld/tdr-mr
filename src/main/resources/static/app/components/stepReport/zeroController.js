angular
    .module('altairApp')
    .controller('zeroCtrl', [
        '$scope',
        '$rootScope',
        'utils',
        '$timeout',
        '$window',
        'Upload',
        '$stateParams',
        'mainService',
        'fileUpload',
        'sweet',
        'org_data',
        '$state',
        'sgt',
        'xonoff',
        'note_data',
        'ann',
        'pv',
        function ($scope, $rootScope, utils, $timeout, $window, Upload, $stateParams, mainService, fileUpload, sweet, org_data, $state, sgt, xonoff, note_data, ann, pv) {
            var inpt = [
                {"text": "Хавсрах материал", "value": 11}
            ];

            $scope.inpt_options = inpt;


            var reasonoption = [
                {"text": "Хөрөнгө оруулалтгүй", "value": 1},
                {"text": "Нөөцийн зэрэглэлийг ахиулах ашиглалтын хайгуул хийх.", "value": 2},
                {"text": "ТЭЗҮ хийж байгаа эсвэл нэмэлт, тодотгол хийгдэж байгаа", "value": 3},
                {"text": "БОННҮ хийж байгаа эсвэл нэмэлт, тодотгол хийгдэж байгаа", "value": 4},
                {"text": "Ашигт малтмалын үнэ ханшийн өөрчлөлт, зах зээл", "value": 5},
                {"text": "Дэд бүтэц, барилга байгууламж барьж  байгаа", "value": 6},
                {"text": "Тоног төхөөрөмж шинэчлэлт хийж байгаа", "value": 7},
                {"text": "Нөөцгүй", "value": 8},
                {"text": "Нөөц дууссан", "value": 9},
                {"text": "Гол мөрний урсац бүрэлдэх эх, . . . ", "value": 10},
                {"text": "Шүүх, хуулийн маргаантай байгаа", "value": 11},
                {"text": "Орон нутаг, иргэний хөдөлгөөн", "value": 12},
                {"text": "Бусад", "value": 13}
            ];

            $scope.reasonoption = reasonoption;

            $scope.selectize_a_config = {
                create: false,
                maxItems: 1,
                placeholder: 'Сонгох...',
                optgroupField: 'parent_id',
                optgroupLabelField: 'text',
                optgroupValueField: 'ogid',
                valueField: 'value',
                labelField: 'text',
                searchField: 'text'
            };

            var drEvent = $('.dropify').dropify();
            $scope.sgt = sgt[0];
            $scope.mid = $stateParams.param;
            $scope.xonoff = xonoff[0].xxx;
            $scope.config = ann;

            console.log($scope.sgt);

            function init() {
                mainService.withdomain('get', '/user/service/note/imp/' + $stateParams.param)
                    .then(function (data) {
                        $scope.notes_data = data;
                        $scope.form = data[0].form;

                    });
            }

            $scope.lplan = false;
            $scope.sendBtn = false;

            if ($scope.sgt.xxx != 7) {
                if ($scope.sgt.xxx != 1) {
                    initButton();
                }
            }


            //$scope.sendBtn=$scope.sgt.send;
            function initButton() {

                mainService.withdomain('get', '/user/service/rs/com.peace.users.model.mram.LutYear/value/' + $scope.sgt.year + '/' + 'divisionid' + '/' + $scope.sgt.divisionid)
                    .then(function (data) {
                        if (data.isactive == true) {
                            $scope.sendBtn = true;
                        }
                        else {
                            mainService.withdomain('get', '/user/service/rs/com.peace.users.model.mram.SubLicenses/licenseNum/' + $scope.sgt.lcnum)
                                .then(function (data) {
                                    if ($scope.sgt.reporttype == 3 && data.lplan == true) {
                                        $scope.sendBtn = true;
                                    }
                                    else if ($scope.sgt.reporttype == 4 && data.lreport == true) {
                                        $scope.sendBtn = true;
                                    }
                                    else {
                                        mainService.withdomain('get', '/user/service/rs/com.peace.users.model.mram.AnnualRegistration/id/' + $stateParams.param)
                                            .then(function (ann) {
                                                if (ann.repstepid == 0 && ann.rejectstep != null) {
                                                    $scope.sendBtn = true;
                                                }
                                            });
                                    }

                                    if (data.ftime == true) {
                                        $scope.sendBtn = true;
                                    }

                                });
                        }
                    });
            }

            $scope.setPdf = function (iitem) {
                console.log(item);
            }

            $scope.planid = $stateParams.param;
            $scope.notes_data = note_data;
            $scope.form = note_data[0].form;

            $scope.mcom = note_data[0].mcom;

            for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
                if ($scope.notes_data[0].notes[i].decision == 2) {
                    for (var y = 0; y < $scope.notes_data[0].notes.length; y++) {
                        if ($scope.notes_data[0].notes[y].inptype > $scope.notes_data[0].notes[i].inptype) {
                            $scope.notes_data[0].notes[y].decision = 2;
                        }
                    }
                }
            }


            $scope.loadOnSend = function (i) {
                if (i == 1) {

                    var json = [];

                    for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
                        if ($scope.notes_data[0].notes[i].issaved != "10") {
                            json.push({
                                "id": $scope.notes_data[0].notes[i].id,
                                "title": $scope.notes_data[0].notes[i].title
                            });
                        }
                    }
                    var modal = UIkit.modal("#modal_header_notyet");

                    $scope.notyet = json;

                    if ($scope.notyet.length > 0) {
                        modal.show();
                    }
                    else {
                        if ($scope.sgt.reasonid == null || $scope.sgt.reasonid == undefined) {
                            sweet.show('Анхаар!', 'Үйл ажиллагаа явуулахгүй шалтгааныг сонгоно уу!', 'error');
                        } else {
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
                                    mainService.withdata('put', '/logic/sendform/' + i + '/' + $stateParams.param, $scope.sgt)
                                        .then(function () {
                                            sweet.show('Анхаар!', 'Амжилттай илгээлээ.', 'success');
                                            $state.go('restricted.pages.planStatus');
                                        });
                                } else {
                                    sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
                                }

                            });
                        }

                    }
                }
            };

            var formdata = new FormData();
            $scope.getTheFiles = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("files", value);
                });
            };
            $scope.getTheFilesFirst = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("firstFile", value);
                });
            };
            $scope.getTheFilesLast = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append("lastFile", value);
                });
            };

            $scope.showExcelPopup = function () {
                UIkit.modal("#excel_popup").show();
                //$timeout($scope.CallMe, 3000);
            }

            $scope.zeroForm = {
                textComment: '',
                planid: $stateParams.param
            }
            var modal = UIkit.modal("#zero_report");
            $scope.submitZeroForm = function () {
                formdata.append("planid", $stateParams.param);
                formdata.append("textComment", $scope.zeroForm.textComment);
                formdata.append("reasonid", $scope.zeroForm.reasonid);
                $(".dropify-cleart").trigger("click");
                fileUpload.uploadFileToUrl('/logic/zero/save', formdata)
                    .then(function (data) {
                        if (data.return) {
                            modal.hide();
                            formdata = new FormData();
                            $state.go('restricted.pages.planStatus');
                            sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
                        }

                    });
            }

            $scope.openDeactive = function () {
                $scope.noteActive = false;
            }
            $scope.openClose = function () {
                $scope.noteActive = false;
            }
            $scope.noteActive = false;

            $scope.onSelect = function (e) {
                var message = $.map(e.files, function (file) {
                    return file.name;
                }).join(", ");
            }

            $scope.fileAttachmentOptions = {
                async: {
                    saveUrl: '/logic/save',
                    removeUrl: '/logic/destroy',
                    removeVerb: 'DELETE',
                    autoUpload: false
                },
                upload: function (e) {
                    e.data = {"obj": da()};
                },
                remove: function (e) {
                    e.data = {"obj": da()};
                },
                localization: {
                    select: "Select a file",
                    uploadSelectedFiles: "Send"
                }
            }

            function da() {
                var parentIndex = $scope.note_form.parentIndex,
                    index = $scope.note_form.index,
                    title = $scope.note_form.title;
                content = $scope.note_form.content;
                expid = $stateParams.param;
                filename = $scope.note_form.content;
                if (parentIndex && index) {
                    init();
                }

                var data = JSON.stringify($scope.note_form, null, 2);
                return data;
            }

            // open a note
            $scope.fileUp = false;
            $scope.openNote = function ($event, $parentIndex, $index, note) {
                $event.preventDefault();
                $scope.noteActive = true;
                $('.notes_list').children('li').removeClass('md-list-item-active');

                $($event.currentTarget).parent('li').addClass('md-list-item-active');
                if ($scope.xonoff) {
                    if (note.decision == 2 || note.decision == 0 || note.decision == undefined) {
                        $scope.fileUp = true;
                    }
                    else if (note.decision == 1) {
                        $scope.fileUp = false;
                    }
                }

                $scope.note_form = {
                    parentIndex: $parentIndex,
                    index: $index,
                    id: note.id,
                    title: note.title,
                    content: note.content,
                    help: note.help,
                    comm: note.comment,
                    isform: note.isform,
                    decision: note.decision,
                    issaved: note.issaved,
                    size: note.size,
                    images: note.images,
                    planid: $stateParams.param,
                    atfile: note.file
                };

                angular.element($window).resize();
            };


            $scope.removeAttach = function (obj) {
                var parentIndex = $scope.note_form.parentIndex,
                    index = $scope.note_form.index;
                sweet.show({
                    title: 'Баталгаажуулалт',
                    text: 'Та энэ файлыг устгахдаа итгэлтэй байна уу?',
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
                        mainService.withdomain('delete', '/user/service/removeAttach/' + obj.id)
                            .then(function () {
                                var index = -1;
                                for (var i = 0; i < $scope.note_form.images.length; i++) {
                                    if ($scope.note_form.images[i].id == obj.id) {
                                        index = i;
                                    }
                                }
                                $scope.note_form.images.splice(index, 1)
                                sweet.show('Анхаар!', 'Файл амжилттай устлаа.', 'success');
                            });
                    } else {
                        sweet.show('Анхаар!', 'Устгах үйлдэл хийгдсэнгүй!!!', 'error');
                    }
                });
            };

            $scope.noteForm = {
                atfile: "default"
            };

            $scope.uploadPic = function (file) {
                if ($scope.note_form.isform == 1) {
                    $scope.note_form.images.splice(index, 1)
                }
                var parentIndex = $scope.note_form.parentIndex,
                    index = $scope.note_form.index,
                    title = $scope.note_form.title;
                content = $scope.note_form.content;
                file_form = $scope.noteForm.atfile;
                planid = $stateParams.param;
                var data = JSON.stringify($scope.note_form, null, 2);

                file.upload = Upload.upload({
                    url: '/logic/save',
                    data: {obj: data, files: file},
                });

                if ($scope.note_form.isform == 1) {
                    file.upload.then(function (response) {
                        $timeout(function () {
                            file.result = response.data;
                            $scope.note_form.issaved = "10";

                            if (response.data.rdata == "noeq") {
                                sweet.show('Анхаар', 'Маягт тохирохгүй байна.', 'warning');
                            }
                            else if (response.data.rdata == "nodata") {
                                sweet.show('Анхаар', 'Алдаа үүслээ.', 'warning');
                            }
                            else {
                                for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
                                    if ($scope.notes_data[0].notes[i].id == $scope.note_form.id) {
                                        $scope.notes_data[0].notes[i].issaved = "10";
                                        $scope.notes_data[0].notes[i].decision = 0;
                                    }
                                }
                                sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
                                $scope.note_form.images.push(response.data.atdata[0]);
                                //$scope.notes_data[0].notes[$scope.note_form.index].issaved="10";
                            }


                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = response.status + ': ' + response.data;
                    }, function (evt) {
                        // Math.min is to fix IE which reports 200% sometimes
                        file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                    });
                }
                else {
                    file.upload.then(function (response) {
                        $timeout(function () {
                            file.result = response.data;
                            $scope.note_form.issaved = "10";

                            if (response.data.rdata == "noeq") {
                                sweet.show('Анхаар', 'Маягт тохирохгүй байна.', 'warning');
                            }
                            else if (response.data.rdata == "nodata") {
                                sweet.show('Анхаар', 'Алдаа үүслээ.', 'warning');
                            }
                            else {
                                for (var i = 0; i < $scope.notes_data[0].notes.length; i++) {
                                    if ($scope.notes_data[0].notes[i].id == $scope.note_form.id) {
                                        $scope.notes_data[0].notes[i].issaved = "10";
                                        $scope.notes_data[0].notes[i].decision = 0;
                                    }
                                }
                                sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
                                $scope.note_form.images.push(response.data.atdata[0]);
                                //$scope.notes_data[0].notes[$scope.note_form.index].issaved="10";
                            }


                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = response.status + ': ' + response.data;
                    }, function (evt) {
                        // Math.min is to fix IE which reports 200% sometimes
                        file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                    });
                }

            }


            $scope.saveAttach = function ($event) {
                $event.preventDefault();

                if ($scope.note_form.isform == 1) {
                    $scope.note_form.images.splice(index, 1)
                }
                var parentIndex = $scope.note_form.parentIndex,
                    index = $scope.note_form.index,
                    title = $scope.note_form.title;
                content = $scope.note_form.content;
                file_form = $scope.noteForm.atfile;
                planid = $stateParams.param;
                var data = JSON.stringify($scope.note_form, null, 2);
                formdata.append("obj", data);
                $(".dropify-cleart").trigger("click");
                fileUpload.uploadFileToUrl('/logic/save', formdata)
                    .then(function (data) {
                        if (data.return) {
                            // called when you click on the "remove" button
                            drEvent.on('dropify.beforeClear', function (event, element) {
                                // do something
                            });

                            // called after the Dropify is clear
                            drEvent.on('dropify.afterClear', function (event, element) {
                                // do something
                            });
                            angular.element('.last2 .dropify-clear').triggerHandler('click');
                            angular.element('.last2 .dropify-clear').triggerHandler('click');
                            angular.element('.last3 .dropify-clear').triggerHandler('click');
                            angular.element('.last4 .dropify-clear').triggerHandler('click');
                            angular.element('.last5 .dropify-clear').triggerHandler('click');
                            angular.element('.last6 .dropify-clear').triggerHandler('click');
                            //init();
                            $scope.note_form.images.push(data.atdata[0]);
                            $scope.notes_data[0].notes[$scope.note_form.index].issaved = "10";
                            formdata = new FormData();
                            sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
                        }

                    });
            }

            // save note
            $scope.saveNote = function ($event) {
                $event.preventDefault();
                // get variables from active note
                var parentIndex = $scope.note_form.parentIndex,
                    index = $scope.note_form.index,
                    title = $scope.note_form.title;
                content = $scope.note_form.content;
                file_form = $scope.noteForm.atfile;
                planid = $stateParams.param;

                var data = JSON.stringify($scope.note_form, null, 2);


                mainService.withdata('put', '/logic/submitPlanDetail/', data)
                    .then(function (data) {
                        if (data) {
                            sweet.show('Мэдээлэл', 'Амжилттай хадгаллаа.', 'success');
                            $scope.notes_data[0].notes[$scope.note_form.index].issaved = "10";
                            init();
                        }

                    });


            }

            var lpreg = org_data.lpReg;

            $scope.tooloptions = {

                tools: [
                    "bold", "italic", "underline", "strikethrough", "justifyLeft", "justifyCenter", "justifyRight", "justifyFull",
                    "insertUnorderedList", "insertOrderedList", "indent", "outdent", "createLink", "unlink", "insertImage", "insertFile",
                    "subscript", "superscript", "createTable", "addRowAbove", "addRowBelow", "addColumnLeft", "addColumnRight", "deleteRow",
                    "deleteColumn", "viewHtml", "formatting", "cleanFormatting", "fontName", "fontSize", "foreColor", "backColor"
                ],

                imageBrowser: {
                    transport: {
                        read: {
                            url: "/imagebrowser/read",
                            type: "GET",
                            data: {"lpreg": lpreg},
                            dataType: "json"
                        },
                        destroy: {
                            url: "/imagebrowser/destroy/" + lpreg,
                            type: "POST",
                            dataType: "json"
                        },
                        uploadUrl: "/imagebrowser/upload/" + lpreg,
                        thumbnailUrl: "/imagebrowser/thumbnail",
                        imageUrl: function (e) {
                            return "/" + e;
                        }
                    },
                },
                fileBrowser: {
                    messages: {
                        dropFilesHere: "Drop files here"
                    },
                    transport: {
                        read: {
                            url: "/imagebrowser/read/file",
                            type: "POST",
                            dataType: "json"
                        },
                        destroy: {
                            url: "/imagebrowser/destroy",
                            type: "POST"
                        },
                        create: "/imagebrowser/create",
                        fileUrl: function (e) {
                            return "/" + e;
                        },
                        uploadUrl: "/imagebrowser/upload"
                    }
                }
            };


            if (pv == null) {
                $scope.cpv = false;
            }
            else {
                $scope.cpv = true;
                $scope.pv = pv;
            }


            if (ann == null) {
                $scope.cann = false;
            }
            else {
                $scope.cann = true;
                $scope.config = ann;
            }


            $scope.mineralDataSource = {
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/user/service/cascading/kendo/LutMinerals",
                        contentType: "application/json; charset=UTF-8",
                        type: "POST"
                    },
                    parameterMap: function (options) {
                        return JSON.stringify(options);
                    }
                }
            };

            $scope.deposididDataSource = {
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/user/service/cascading/kendo/LutDeposit",
                        contentType: "application/json; charset=UTF-8",
                        type: "POST"
                    },
                    parameterMap: function (options) {
                        return JSON.stringify(options);
                    }
                }
            };

            $scope.productsDataSource = {
                serverFiltering: true,
                transport: {
                    serverFiltering: true,
                    read: {
                        url: "/user/service/cascading/kendo/LutAdminunit",
                        contentType: "application/json; charset=UTF-8",
                        type: "POST"
                    },
                    parameterMap: function (options) {
                        return JSON.stringify(options);
                    }
                }
            };

            $scope.aimags = {
                serverFiltering: true,
                transport: {
                    read: {
                        url: "/user/service/cascading/kendo/LutAdminunitParent",
                        contentType: "application/json; charset=UTF-8",
                        type: "POST"
                    },
                    parameterMap: function (options) {
                        return JSON.stringify(options);
                    }
                }
            };

            var categories = $("#bomber").kendoDropDownList({
                optionLabel: "Тэсэлгээний ажил...",
                dataTextField: "name",
                dataValueField: "id",
                dataSource: {
                    serverFiltering: true,
                    data: [
                        {
                            "id": "1",
                            "name": "Тийм"
                        },
                        {
                            "id": "0",
                            "name": "Үгүй"
                        }
                    ]
                }
            }).data("kendoDropDownList");

            var products = $("#bomberman").kendoDropDownList({
                autoBind: false,
                cascadeFrom: "bomber",
                optionLabel: "Гүйцэтгэгч...",
                dataTextField: "name",
                dataValueField: "id",
                dataSource: {
                    serverFiltering: true,
                    data: [
                        {"Id": 1, "name": "Өөрөө гүйцэтгэх", "parentid": 1},
                        {"Id": 1, "name": "Гэрээгээр гүйцэтгүүлэх", "parentid": 1}
                    ]
                }
            }).data("kendoDropDownList");

            $scope.OneSource = {
                data: [
                    {"id": 1, "name": "Тийм"},
                    {"id": 2, "name": "Үгүй"}
                ]
            };

            $scope.TwoSource = {
                data: [
                    {"id": 1, "name": "Өөрөө гүйцэтгэх", "parentid": 1},
                    {"id": 2, "name": "Гэрээгээр гүйцэтгүүлэх", "parentid": 1},
                    {"id": 3, "name": "Үгүй", "parentid": 2}
                ]
            };

            $scope.wer = true;
            $scope.ena = function () {

                if ($scope.config.komissid == 1) {
                    $scope.wer = false;
                }
                else {
                    $scope.wer = true;
                    $scope.config.komissdate = "";
                    $scope.config.komissakt = "";
                }
            }

            $scope.mineType = {
                serverFiltering: true,
                filter: "startswith",
                transport: {
                    read: {
                        url: "/user/service/cascading/kendo/LutMineType",
                        contentType: "application/json; charset=UTF-8",
                        type: "POST"
                    },
                    parameterMap: function (options) {
                        return JSON.stringify(options);
                    }
                }
            };

            $scope.yesno = {
                dataTextField: 'name',
                dataValueField: 'id',
                data: [
                    {
                        "id": "1",
                        "name": "Тийм"
                    },
                    {
                        "id": "0",
                        "name": "Үгүй"
                    }
                ]
            };

            $scope.bomb = {
                dataTextField: 'name',
                dataValueField: 'id',
                data: [
                    {
                        "id": "1",
                        "name": "Өөрөө гүйцэтгэх"
                    },
                    {
                        "id": "0",
                        "name": "Гэрээгээр гүйцэтгүүлэх"
                    }
                ]
            };

            $scope.org_data = org_data;


            $scope.editorOptions = {
                language: 'en',
                'skin': 'moono',
                'extraPlugins': "imagebrowser,mediaembed",
                imageBrowser_listUrl: '/api/v1/ckeditor/gallery',
                filebrowserBrowseUrl: '/api/v1/ckeditor/files',
                filebrowserImageUploadUrl: '/api/v1/ckeditor/images',
                filebrowserUploadUrl: '/api/v1/ckeditor/files',
                toolbarLocation: 'bottom',
                toolbar: 'full',
                toolbar_full: [
                    {
                        name: 'basicstyles',
                        items: ['Bold', 'Italic', 'Strike', 'Underline']
                    },
                    {name: 'paragraph', items: ['BulletedList', 'NumberedList', 'Blockquote']},
                    {name: 'editing', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']},
                    {name: 'links', items: ['Link', 'Unlink', 'Anchor']},
                    {name: 'tools', items: ['SpellChecker', 'Maximize']},
                    {name: 'clipboard', items: ['Undo', 'Redo']},
                    {
                        name: 'styles',
                        items: ['Format', 'FontSize', 'TextColor', 'PasteText', 'PasteFromWord', 'RemoveFormat']
                    },
                    {name: 'insert', items: ['Image', 'Table', 'SpecialChar', 'MediaEmbed']}, '/',
                ]
            };

            $scope.content = {
                preface: ''
            }


        }
    ]);